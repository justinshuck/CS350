import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Date;

import data.Analyzer;
import runner.Console;
import runner.RunConfigurator;
import runner.TestDataSaver;
import runner.TestRunner;

public class Program
{
	/**
	 * The relative path to the storage location for test data.
	 */
	public static final String TEST_RESULT_DIRECTORY = "./results";
	
	/**
	 * The main entry point for the test runner.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			if (args.length == 2 && args[0].equals("-d"))
			{
				executeDir(args[1]);
				return;
			}
			
			execute(args);
		}
		catch (Throwable e)
		{
			Console.errprintln();
			Console.errprintln("An uncaught exception occurred.");
			Console.errprintln("Exception:   " + e.getClass().getCanonicalName());
			Console.errprintln("Message:     " + e.getMessage());
			Console.errprintln("Stack Trace: ");
			e.printStackTrace();
			
			if (Console.getPrintStream() == null)
			{
				return;
			}
			
			e.printStackTrace(Console.getPrintStream());
		}
	}
	
	/**
	 * Executes a directory of files.
	 * 
	 * @param dirName
	 * @throws Exception
	 */
	private static void executeDir(String dirName) throws Exception
	{
		File dir = new File(dirName);
		
		if (!dir.exists() || !dir.isDirectory())
		{
			throw new IllegalArgumentException("The directory does not exist, or is not a directory: " + dirName);
		}
		
		File[] files = dir.listFiles();
		
		if (files == null || files.length == 0)
		{
			throw new IllegalArgumentException("No files were found in the directory: " + dirName);
		}
		
		File outFile = new File(Paths.get(".", "dir.log").toString());
		BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
		
		for (File file : files)
		{
			if (!file.isFile())
			{
				continue;
			}
			
			out.write("[" + (new Date()).toString() + "] Starting to run: " + file.getCanonicalPath());
			out.newLine();
			out.flush();
			
			main(new String[] { file.getCanonicalPath() });
			
			out.write("[" + (new Date()).toString() + "] Finished run (with" + (Console.hasError ? "" : "out") + " error): " + file.getCanonicalPath());
			out.newLine();
			out.write("\tSee output here: " + Console.outputFile);
			out.newLine();
			out.flush();
		}
		
		out.write("[" + (new Date()).toString() + "] All done!");
		out.newLine();
		out.close();
	}
	
	/**
	 * Sets up and actually executes the test runner.
	 * 
	 * @param args
	 * @throws Exception
	 */
	private static void execute(String[] args) throws Exception
	{
		if (args.length != 1)
		{
			System.err.println("Please specify the run configuration file path to use.");
			return;
		}
		
		RunConfigurator config = RunConfigurator.getRunConfigurator(args[0]);
		
		File resultDirectory = getResultDirectory();
		TestDataSaver saver = new TestDataSaver(resultDirectory);
		
		Console.init(new File(saver.getDirectory()));
		
		Console.println("Test Runner Configuration:");
		Console.println("\tSource:\t\t" + config.getSourceFile());
		Console.println();
		Console.println("\tSort Name:\t" + config.getSortName());
		Console.println("\tSort Config:\t" + config.getSortConfig());
		Console.println();
		Console.println("\tData Type:\t" + config.getDataSetType());
		Console.println("\tData Config:\t" + config.getDataSetConfig());
		Console.println("\tData Size:\t" + config.getDataSetSize());
		Console.println("\tIterations:\t" + config.getTotalIterations());
		Console.println();
		Console.println("\tTest Results will be stored in the directory:");
		Console.println("\t" + saver.getDirectory());
		
		TestRunner.run(config, saver);
		
		Analyzer.analyze(saver.getFileName(), new File(saver.getDirectory()));
	}
	
	/**
	 * Returns an instance of a File representing the result directory. If it
	 * doesn't exist, an attempt will be made to create it.
	 * 
	 * @return
	 */
	private static File getResultDirectory()
	{
		File resultDirectory = new File(TEST_RESULT_DIRECTORY);
		
		if (resultDirectory.exists() && resultDirectory.isDirectory())
		{
			return resultDirectory;
		}
		else if (resultDirectory.exists() && !resultDirectory.isDirectory())
		{
			throw new RuntimeException("The specified result path is not a directory: " + resultDirectory.getAbsolutePath());
		}
		
		if (resultDirectory.mkdirs())
		{
			return resultDirectory;
		}
		
		throw new RuntimeException("Attempted to create result directory, but failed: " + TEST_RESULT_DIRECTORY);
	}
}
