/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author REDRADIST
 */
public class Board {
    public Board(int[][] blocks)
    {
        
    }
    
    public int dimension()
    {
        return 1;
    }
    
    public int hamming()
    {
        return 1;
    }
    
    public int manhattan()
    {
        return 1;
    }
    
    public boolean isGoal()
    {
        return true;
    }
    
    public Board twin()
    {
        return new Board(new int[5][8]);
    }
    
    public boolean equals(Object y)
    {
        return true;
    }
    
    public Iterable<Board> neighbors()
    {
        return new Iterable<Board>() {

            @Override
            public Iterator<Board> iterator() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
    public String toString()
    {
        return new String("");
    }

    public static void main(String[] args)
    {
        
    }
}
