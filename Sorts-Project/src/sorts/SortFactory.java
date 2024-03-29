package sorts;

import partitions.PartitionFactory;

public class SortFactory
{
	/**
	 * Returns an instance of the specified sort.
	 * @param sortName
	 * @param sortConfig
	 * @return
	 */
	public static Sort getInstance(String sortName, String sortConfig)
	{
		switch (sortName.toLowerCase())
		{
			case "quicksort":
				return new QuickSort(PartitionFactory.getInstance(sortConfig));
				
			case "mergesort":
				return new MergeSort();
				
			case "parallel mergesort":
				return new ParallelMergeSort();

			case "insertionsort":
				return new InsertionSort();
			default:
				throw new IllegalArgumentException("Unknown sort type: " + sortName + ".");
		}
	}
	
}
