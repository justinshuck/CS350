package runner;

import java.util.Calendar;

public class TestResult
{
	private String sortName;
	private long swapCount;
	private long basicOpCount;
	private long elapsedTime;
	private Calendar ranAt;
	
	public String getSortName()
	{
		return sortName;
	}
	
	public void setSortName(String sortName)
	{
		this.sortName = sortName;
	}
	
	public long getSwapCount()
	{
		return swapCount;
	}
	
	public void setSwapCount(long swapCount)
	{
		this.swapCount = swapCount;
	}
	
	public long getBasicOpCount()
	{
		return basicOpCount;
	}
	
	public void setBasicOpCount(long basicOpCount)
	{
		this.basicOpCount = basicOpCount;
	}

	public long getElapsedTime()
	{
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime)
	{
		this.elapsedTime = elapsedTime;
	}

	public Calendar getRanAt()
	{
		return ranAt;
	}

	public void setRanAt(Calendar ranAt)
	{
		this.ranAt = ranAt;
	}
}
