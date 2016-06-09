/*
 * Algorithms, Part I. Programming Assignments. Week 1
 */
package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author REDRADIST: Denis Kotov
 */
public class Percolation {
    private WeightedQuickUnionUF quickUnion;
    private WeightedQuickUnionUF unionFull;
    private final int top, bottom; // Ids of TOP and BOTTOM virtual element
    private boolean[][] site;
    private final int gridSize;
    
    /* 
        Create N-by-N grid, with all sites blocked.
    */
    public Percolation(int N)
    {
        // Checking N
        if (N <= 0)
        {
            throw new IllegalArgumentException("N less or equal to 0");
        }
        else
        {
            gridSize = N;
            top = 0;
            bottom = N*N+1;
            site = new boolean [N][N];
            quickUnion = new WeightedQuickUnionUF(N*N+2);
            unionFull = new WeightedQuickUnionUF(N*N+1);
            for (int i = 0; i < N; ++i)
            {
                for (int k = 0; k < N; ++k)
                {
                   site[i][k] = false;
                }
                if (N != 1)
                {
                    quickUnion.union(top, i + 1);
                    unionFull.union(top, i + 1);
                    quickUnion.union(bottom, N * (N - 1) + i + 1);
                }
            }
        }
    }
    
    /* 
        Open site (row i, column j) if it is not open already.
    */
    public void open(int i, int j) 
    {
        int row = i-1;
        int column = j-1;
        int item = row * gridSize + column + 1;
        
        // Checking, is an item range ?
        if (item >= 1 && item <= gridSize*gridSize && 
            !site[row][column])
        {
            if (gridSize == 1)
            {
                quickUnion.union(item, top);
                unionFull.union(item, top);
                quickUnion.union(item, bottom);
                site[row][column] = true;
                return;
            }
            
            int _left = row * gridSize + (column - 1) + 1;
            int _right = row * gridSize + (column + 1) + 1;
            int _top = (row - 1) * gridSize + column + 1;
            int _bottom = (row + 1) * gridSize + column + 1;
            
            // Checking state of top element relative to this element
            if (row-1 >= 0 && site[row-1][column])
            {
                quickUnion.union(item, _top);
                unionFull.union(item, _top);
            }
            
            // Checking state of bottom element relative to this element
            if (row+1 < gridSize && site[row+1][column])
            {
                quickUnion.union(item, _bottom);
                unionFull.union(item, _bottom);
            }
            
            // Checking state of left element relative to this element
            if (column-1 >= 0 && site[row][column-1])
            {
                quickUnion.union(item, _left);
                unionFull.union(item, _left);
            }
           
            // Checking state of right element relative to this element
            if (column+1 < gridSize && site[row][column+1])
            {
                quickUnion.union(item, _right);
                unionFull.union(item, _right);
            }
            
            site[row][column] = true;
        }
        else if (!site[row][column])
        {
            throw new IllegalArgumentException("Indexes are invalid");
        }
    }
    
    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        return (site[i-1][j-1]);
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        int row = i-1;
        int column = j-1;
        int item = row * gridSize + column + 1;
        return (site[row][column] && unionFull.connected(top, item));
    }
    
    public boolean percolates()             // does the system percolate?
    {
        return quickUnion.connected(top, bottom);
    }
}

