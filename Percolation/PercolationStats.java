public class PercolationStats {
    private double[] threshold;
    public PercolationStats(int N, int T) {
        int openCount, row, column;
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("Arguments out of bound");
        threshold = new double[T];                       
        openCount = 0;
        for (int i = 0; i < T; i++) {
            Percolation pl = new Percolation(N);
            do {
                row = StdRandom.uniform(1, N+1); 
                column  = StdRandom.uniform(1, N+1);
                if (pl.isOpen(row, column))
                    continue;
                pl.open(row, column);
                openCount++;
            } while (!pl.percolates());
            threshold[i] = (double) openCount / (N * N);
            openCount = 0;
        }                                                            
    }
    public double mean() {
        return StdStats.mean(threshold);
    }
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    private double halfInterval() {
            return 1.96 * stddev() / Math.sqrt(threshold.length);
    }
    public double confidenceLo() {
        return mean() - halfInterval();
    }
    public double confidenceHi() {
        return mean() + halfInterval();
    }
    public static void main(String[] args) {
        PercolationStats pls = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.printf("mean = %f\n", pls.mean());
        System.out.printf("stddev = %f\n", pls.stddev());
        System.out.printf("95%% confidence Interval = %f, %f\n",pls.confidenceLo(), pls.confidenceHi());
    }
}
