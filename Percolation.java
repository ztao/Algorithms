public class Percolation
{
    private boolean[][] grid;
    private int destination;
    private WeightedQuickUnionUF sets;
    private int side;
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)
    {
        side = N + 1;
        grid        = new boolean[N+1][N+1];
        destination = N * N + 1;
        sets        = new WeightedQuickUnionUF(N*N+2);
        side        = N;
    }
    // open site (row i, column j) if it is not already
    public void open(int i, int j)
    {
        grid[i][j] = true;
        // Define joint sites
        int current_site = i * side + j + 1;
        int      up_site = current_site - side;
        int    down_site = current_site + side;
        int    left_site = current_site - 1;
        int   right_site = current_site + 1;
        // Test whether there is a joint site;
        if                           (i == 0) {
            sets.union(0, current_site);
        }              else if (grid[i-1][j]) {
            sets.union(up_site, current_site);
        }
        if                      (i == side-1) {
            sets.union(current_site, side * side + 1);
        }              else if (grid[i+1][j]) {
            sets.union(current_site, down_site);
        }
        if        (!(j == 0) && grid[i][j-1]) {
            sets.union(current_site, left_site);
        }
        if (!(j == side - 1) && grid[i][j+1]) {
            sets.union(current_site, right_site);
        }
        // StdOut.println(sets.count() + " components");
    }
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j)
    {
        return grid[i][j];
    }
    // is site (row i, column j) full?
    public boolean isFull(int i, int j)
    {
        return sets.connected(0, i * side + j + 1);
    }
    // does the system percolate?
    public boolean percolates()
    {
        return sets.connected(0, side*side +1);
    }
}


