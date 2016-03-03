import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] x;
	private int t;
	
	//// perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T)
	{
		if (N <= 0 || T <= 0)
		{
			throw new java.lang.IllegalArgumentException("N or T was negative!");
		}
		x = new double[T];
		t = T;
		for (int i = 0; i < T; i++)
		{
			x[i] = ((double) computationalExperiment(N)) / ((double) (N*N));
		}		
	}
	
	// sample mean of percolation threshold
	public double mean()
	{
		return StdStats.mean(x);
	}
	
	// sample standard deviation of percolation threshold
	public double stddev()
	{
		if (t == 1)
		{
			return Double.NaN;
		}
		return StdStats.stddev(x);
	}
	
	// low  endpoint of 95% confidence interval
	public double confidenceLo()
	{
		return mean() - (1.96)*stddev()/Math.sqrt((double) t);
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi()
	{
		return mean() + (1.96)*stddev()/Math.sqrt((double) t);		
	}
	
	// test client
	public static void main(String[] args)
	{
		String args0 = "2";
		String args1 = "100000";
		int N = Integer.valueOf(args0);
		int T = Integer.valueOf(args1);
		PercolationStats percStat = new PercolationStats(N, T);
		String mean = Double.toString(percStat.mean());
		String stdev = Double.toString(percStat.stddev());
		String ciLow = Double.toString(percStat.confidenceLo());
		String ciHigh = Double.toString(percStat.confidenceHi());
		System.out.println("mean = " + mean);
		System.out.println("stdev = " + stdev);
		System.out.println("95% confidence interval = " + ciLow + "," + ciHigh);		
	}
	
	//performs a single computational experiment on an N x N grid. Returns the number of steps it took to percolate.
	private int computationalExperiment(int N)
	{
		boolean[] usedPoints = new boolean[N*N];
		Percolation perc = new Percolation(N);
		int numSteps = 0;
		while (!perc.percolates())
		{
			int index = generateRandomUnblockedPair(usedPoints);
			usedPoints[index] = true;
			int i = (index % N) == 0 ? index / N : (index / N) + 1;
			int j = (index - (i-1)*N);
			perc.open(i, j);
			numSteps++;
		}
		return numSteps;
	}
	
	//gets a random index that has not been chosen
	private int generateRandomUnblockedPair(boolean [] gridPoints)
	{
		boolean successFul = false;
		int result = -1;
		int numInts = gridPoints.length;
		while (!successFul)
		{
			result = StdRandom.uniform(1, numInts);
			if (!gridPoints[result])
			{
				successFul = true;
			}
		}
		return result;		
	}
}