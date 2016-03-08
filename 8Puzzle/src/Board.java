/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author REDRADIST
 */
public class Board {
    private final int N;
    private final int[][] blocks;
    
    private class BoardIterator implements Iterator<Board>
    {
        private final Iterator<ArrayIndex> steps;
        private int posN;
        private int posM;
        
        private class ArrayIndex {
            int n;
            int m;
        }
        
        BoardIterator()
        {
            for (int n = 0; n < N; ++n)
            {
                for (int m = 0; m < N; ++m)
                {
                    if (blocks[n][m] == 0)
                    {
                        posN = n;
                        posM = m;
                    }
                }
            }
            LinkedList<ArrayIndex> allSteps = new LinkedList<>();
            //up
            if (posN-1 >= 0)
            {
                ArrayIndex index = new ArrayIndex();
                index.n = posN-1;
                index.m = posM;
                allSteps.add(index);
            }
            //down
            if (posN+1 < N)
            {
                ArrayIndex index = new ArrayIndex();
                index.n = posN+1;
                index.m = posM;
                allSteps.add(index);
            }
            //left
            if (posM-1 >= 0)
            {
                ArrayIndex index = new ArrayIndex();
                index.n = posN;
                index.m = posM-1;
                allSteps.add(index);
            }
            //right
            if (posM+1 < N)
            {
                ArrayIndex index = new ArrayIndex();
                index.n = posN;
                index.m = posM+1;
                allSteps.add(index);
            }
            steps = allSteps.iterator();
        }
        
        @Override
        public boolean hasNext()
        {
            return steps.hasNext();
        }
        
        @Override
        public Board next()
        {
            if (!steps.hasNext())
            {
                throw new NoSuchElementException("No next Board");
            }
            ArrayIndex index = steps.next();
            int[][] temp = new int[N][N];
            for (int n = 0; n < N; ++n)
            {
                for (int m = 0; m < N; ++m)
                {
                    temp[n][m] = blocks[n][m];
                }
            }
            temp[posN][posM] = blocks[index.n][index.m];
            temp[index.n][index.m] = 0;
            return new Board(temp);
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException(
                    "Deqeu does not support remove by iterator");
        }
    }
    
    public Board(int[][] blocks)
    {
        if (blocks == null)
        {
            throw new java.lang.NullPointerException("Initial board is empty !"); 
        }
        
        N = blocks.length;
        for (int[] block : blocks)
        {
            if (block.length != N)
            {
                throw new java.lang.NullPointerException("Initial board is empty !");
            }
        }
        
        this.blocks = new int[N][N];
        for (int n = 0; n < N; ++n)
        {
            for (int m = 0; m < N; ++m)
            {
                this.blocks[n][m] = blocks[n][m];
            }
        }
    }
    
    public int dimension()
    {
        return N;
    }
    
    public int hamming()
    {
        int hamming = 0;
        for (int n = 0; n < N; ++n)
        {
            for (int m = 0; m < N; ++m)
            {
                if (blocks[n][m] != (N*n+m+1) &&
                    blocks[n][m] != 0) 
                {
                    hamming++;
                }
            }
        }
        return hamming;
    }
    
    public int manhattan()
    {
        int manhattan = 0;
        for (int n = 0; n < N; ++n)
        {
            for (int m = 0; m < N; ++m)
            {
                if (blocks[n][m] != (N*n+m+1) &&
                    blocks[n][m] != 0) 
                {
                    int posN = (blocks[n][m]-1)/N;
                    int posM = (blocks[n][m]-1) % N;
                    manhattan += Math.abs(n-posN) + Math.abs(m-posM);
                }
            }
        }
        return manhattan;
    }
    
    public boolean isGoal()
    {
        for (int n = 0; n < N; ++n)
        {
            for (int m = 0; m < N; ++m)
            {
                if (blocks[n][m] != (N*n+m+1) &&
                    blocks[n][m] != 0) 
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Board twin()
    {            
        int[][] temp = new int[N][N];
        for (int n = 0; n < N; ++n)
        {
            for (int m = 0; m < N; ++m)
            {
                temp[n][m] = blocks[n][m];
            }
        }
        
        int firstN;
        int firstM;
        do
        {
            int firstRandom = StdRandom.uniform(0, N*N);
            firstN = firstRandom/N;
            firstM = firstRandom % N;
        } while (temp[firstN][firstM] == 0);
        
        int secondN = firstN;
        int secondM = firstM;
        LinkedList<Integer> seconds = new LinkedList<>();
        seconds.add(0);
        seconds.add(1);
        seconds.add(2);
        seconds.add(3);
        do {
            secondN = firstN;
            secondM = firstM;
            int size = seconds.size();
            int secondRandom = StdRandom.uniform(0, size);
            switch (seconds.get(secondRandom))
            {
                case 0:
                    if (firstN-1 >= 0) secondN = firstN-1;
                    break;
                case 1:
                    if (firstN+1 < N) secondN = firstN+1;
                    break;
                case 2:
                    if (firstM-1 >= 0) secondM = firstM-1;
                    break;
                default:
                    if (firstM+1 < N) secondM = firstM+1;
                    break;                               
            }
            seconds.remove(secondRandom);
        } while (temp[secondN][secondM] == 0 ||
                 temp[secondN][secondM] == temp[firstN][firstM]);
        int temporary = temp[firstN][firstM];
        temp[firstN][firstM] = temp[secondN][secondM];
        temp[secondN][secondM] = temporary;
        return new Board(temp);
    }
    
    @Override
    public boolean equals(Object y)
    {
        if (y instanceof Board)
        {
            Board checking;
            checking = (Board) y;
            if (N != checking.dimension())
                return false;
            for (int n = 0; n < N; ++n)
            {
                for (int m = 0; m < N; ++m)
                {
                    if (blocks[n][m] != checking.blocks[n][m]) 
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public Iterable<Board> neighbors()
    {
        return () -> new BoardIterator();
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(Integer.toString(N)).append("\n");
        for (int n = 0; n < N; ++n)
        {
            for (int m = 0; m < N; ++m)
            {
                builder.append(" ");
                builder.append(Integer.toString(blocks[n][m]));
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
    
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("hamming = "+initial.hamming());
        StdOut.println("manhattan = "+initial.manhattan());
        StdOut.println("This board is "+((initial.isGoal()) ? "goal" : "not goal"));
        StdOut.println(initial.toString());
        StdOut.println("This board has the next neighbors:");
        Iterable<Board> neighbors = initial.neighbors();
        Iterator<Board> iterator = neighbors.iterator();
        StdOut.println("------------- start ---------------");
        while (iterator.hasNext())
        {
            StdOut.println(iterator.next().toString());
        }
        StdOut.println("-------------- end ----------------");
        StdOut.println("This board has a twin:");
        StdOut.println(initial.twin().toString());
    }
}
