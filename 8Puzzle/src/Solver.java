/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Iterator;

/**
 *
 * @author REDRADIST
 */
public class Solver {
    private final int moves;
    private PriorBoard prio, addprio;
    private final boolean isSolvable;
    
        
    private class IteratorBoard implements Iterator<Board> {
        private int steps = 0;
        private final PriorBoard prior;
        
        IteratorBoard(PriorBoard priority)
        {
            prior = priority;
        }
        
        @Override
        public boolean hasNext() {
            return steps <= prior.moves;
        }

        @Override
        public Board next() {
            if (!hasNext()) return null;
            PriorBoard temp = prior;
            while (temp.moves != steps) temp = temp.last;
            steps++;
            return temp.board;
        }
        
    }
    
    private class PriorBoard implements Comparable<PriorBoard> {
        public Board board;
        public PriorBoard iterator = null;
        public PriorBoard last = null;
        private int priority;
        private int moves;
        
        PriorBoard(Board board, int moves)
        {
            this.board = board;
            this.moves = moves;
            priority = board.manhattan();
            priority += this.moves;
        }
        
        public PriorBoard addNext(Board o) {
            PriorBoard prio = new PriorBoard(o, moves+1);
            prio.last = this;
            prio.iterator = prio;
            return prio;
        }
        
        @Override
        public int compareTo(PriorBoard o) {
            if (priority == o.priority)
            {
                return 0;
            }
            else if (priority > o.priority)
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
    }
    
    public Solver(Board initial)
    {
        MinPQ<PriorBoard> queue;
        MinPQ<PriorBoard> addqueue;
        queue = new MinPQ<>();
        addqueue = new MinPQ<>();
        queue.insert(new PriorBoard(initial, 0));
        prio = queue.delMin();
        Board tinitial = prio.board.twin();
        addqueue.insert(new PriorBoard(tinitial, 0));
        addprio = addqueue.delMin();
        StdOut.println(prio.board);
        StdOut.println(addprio.board);
        while (!prio.board.isGoal())
        {
            if (addprio.board.isGoal())
                break;
            //--------------------------------------------------------------
            StdOut.println("Next prio:");
            StdOut.println(prio.board);
            StdOut.println("Next addprio:");
            StdOut.println(addprio.board);
            //--------------------------------------------------------------
            Iterator<Board> iterator = prio.board.neighbors().iterator();
            while (iterator.hasNext())
            {
                Board next = iterator.next();
                if (prio.last == null || 
                   (prio.last != null && !prio.last.board.equals(next)))
                {
                    PriorBoard nextStep = prio.addNext(next);
                    queue.insert(nextStep);
                }
            }
            prio = queue.delMin();
            //--------------------------------------------------------------
            Iterator<Board> additerator = addprio.board.neighbors().iterator();
            while (additerator.hasNext())
            {
                Board next = additerator.next();
                if (addprio.last == null || 
                   (addprio.last != null && !addprio.last.board.equals(next)))
                {
                    PriorBoard nextStep = addprio.addNext(next);
                    addqueue.insert(nextStep);
                }
            }
            addprio = addqueue.delMin();
            if(prio.board.equals(initial)) break;
        }
        if (prio.board.isGoal())
        {
            isSolvable = true;
            this.moves = prio.moves;
        }
        else
        {
            isSolvable = false;
            this.moves = -1;
        }
    }
    
    public boolean isSolvable()
    {
        return isSolvable;
    }
    
    public int moves()
    {
        return moves;
    }
    
    public Iterable<Board> solution()
    {
        if (isSolvable()) return () -> new IteratorBoard(prio);
        else return null;
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
    }
    }
}
