/*
 * Algorithms, Part I. Programming Assignments. Week 1
 */
package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author REDRADIST: Denis Kotov
 */
public class PercolationStats {
    private int times;
    private double[] means;
            
    /* 
        Perform T independent experiments on an N-by-N grid.
    */
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
        {
            throw new IllegalArgumentException("N and T are less or equal to 0");
        }
        else
        {
            times = T;
            means = new double[T];
            Percolation percolate;
            for (int n = 0; n < T; ++n)
            {
                int mean = 0;
                means[n] = 0;
                percolate = new Percolation(N);
                // Calculate until has became percolate
                while (!percolate.percolates())
                {
                    int item = StdRandom.uniform(0, N*N);
                    int i = item / N;
                    int j = item % N;
                    if (!percolate.isOpen(i+1, j+1))
                    {
                        percolate.open(i+1, j+1);
                        mean++;
                    }
                }
                // Calculate means for n attempt
                means[n] = ((double) mean)/(N*N);
            } 
        }
    }
    
    /* 
        Sample mean of percolation threshold.
    */
    public double mean()
    {
        return StdStats.mean(means);
    }
    
    /* 
        Sample standard deviation of percolation threshold.
    */
    public double stddev()
    {
        return StdStats.stddev(means);
    }
    
    /* 
        Low endpoint of 95% confidence interval.
    */
    public double confidenceLo()
    {
        return (mean() - 1.96 * stddev()/Math.sqrt(times));
    }
    
    /* 
        High endpoint of 95% confidence interval.
    */
    public double confidenceHi()
    {
        return (mean() + 1.96 * stddev()/Math.sqrt(times));
    }

    /* 
        Test client (described below).
    */
    public static void main(String[] args)
    {
        // Checking number of arguments
        if (args.length != 2)
        {
            throw new IllegalArgumentException("Number of arguments must be only 2");
        }
        else
        {
            try
            {
                int N = Integer.parseInt(args[0]);
                int T = Integer.parseInt(args[1]);
            
                PercolationStats pre = new PercolationStats(N, T);
                StdOut.println("mean                    = " + pre.mean());
                StdOut.println("stddev                  = " + pre.stddev());
                StdOut.println("95% confidence interval = " + pre.confidenceLo()
                             + ", " + pre.confidenceHi());
            }
            catch (Exception e)
            {
                StdOut.println("Unhandled exception: " + e.toString());
            }
            
        }
    }
}
