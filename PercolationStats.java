public class PercolationStats
{
    private double[] threshold;
    private Percolation p;
    private int trials;
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T)
    {
        // StdOut.println("OK?");
        threshold = new double[T];
        trials = T;
        simulatePercolation(N, trials);    
    }
    // sample mean of percolation threshold
    public double mean()
    {
        double sum = 0.0;
        for (int i = 0; i < trials; i++) {
            sum += threshold[i];
            // StdOut.println("The " + i + "th threshold is: " + threshold[i]);
            // StdOut.println("The " + i + "th sum is: " + sum);
        }
        // StdOut.println("Mean: " + sum * 1.0 / trials);
        return sum / trials;
    }
    // sample standard deviation of percolation threshold
    public double stddev()
    {
        double u = mean();
        double  sum = 0.0;
        for (int i = 0; i < trials; i++) {
            sum += (threshold[i] - u) * (threshold[i] - u);
        }
        // StdOut.println ("Sum is: " + sum);
        return Math.sqrt(sum / (trials - 1)); // return the standard deviation
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo()
    {
        double u = mean();
        double e = Math.sqrt(stddev()); 
        return u - (1.96 * e)/Math.sqrt(trials);
    }
    // returns upper bound of the 95% confidence interval
    public double confidenceHi()
    {
        double u = mean();
        double e = stddev();
        return u + (1.96 * e)/Math.sqrt(trials);      
    }
    private void simulatePercolation(int N, int trials) 
    {
        for (int t = 0; t < trials; t++) {
            // StdOut.println("t = " + t);
            p = new Percolation(N);
            int count = 0;
            while (!p.percolates()) {
                int i = StdRandom.uniform(N);
                int j = StdRandom.uniform(N);
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    // StdOut.println("i: " + i + ", j: " + j);
                    count++;
                }
            }
            threshold[t] = count * 1.0 / (N*N);
            // StdOut.println("The threshold of the " + t + "th time: " + threshold[t]);
        }
    }
    // test client, described below
    public static void main(String[] args)
    {
        // StdOut.println("??RUN??");
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        // StdOut.println("N = " + N +",T = " + T);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.println("mean                    = " +                                stats.mean());
        StdOut.println("stddev                  = " +                              stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}