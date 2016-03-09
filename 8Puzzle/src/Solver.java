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
    private PriorityBoard solving, additional;
    private final boolean isSolvable;
    
    private class IteratorBoard implements Iterator<Board> {
        private int steps = 0;
        private final PriorityBoard current;
        
        IteratorBoard(PriorityBoard priority)
        {
            current = priority;
        }
        
        @Override
        public boolean hasNext() {
            return steps <= current.moves;
        }

        @Override
        public Board next() {
            if (!hasNext()) return null;
            PriorityBoard temp = current;
            while (temp.moves != steps) temp = temp.last;
            steps++;
            return temp.board;
        }
        
    }
    
    private class PriorityBoard implements Comparable<PriorityBoard> {
        private Board board;
        private PriorityBoard last = null;
        private int priority;
        private int moves;
        
        PriorityBoard(Board board, int moves)
        {
            this.board = board;
            this.moves = moves;
            priority = board.manhattan();
            priority += this.moves;
        }
        
        public PriorityBoard addNext(Board next) {
            if (last == null || !last.board.equals(next))
            {
                PriorityBoard nextBoard = new PriorityBoard(next, moves+1);
                nextBoard.last = this;
                return nextBoard;
            }
            else
            {
                return null;
            }
        }
        
        public Board getBoard() {
            return board;
        }
        
        @Override
        public int compareTo(PriorityBoard o) {
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
        MinPQ<PriorityBoard> solveQueue;
        MinPQ<PriorityBoard> additionalQueue;
        solveQueue = new MinPQ<>();
        additionalQueue = new MinPQ<>();
        solveQueue.insert(new PriorityBoard(initial, 0));
        solving = solveQueue.delMin();
        Board twinInitial = solving.getBoard().twin();
        additionalQueue.insert(new PriorityBoard(twinInitial, 0));
        additional = additionalQueue.delMin();
        while (!solving.getBoard().isGoal())
        {
            if (additional.getBoard().isGoal())
                break;
            Iterator<Board> iterator = 
                    solving.getBoard().neighbors().iterator();
            while (iterator.hasNext())
            {
                Board next = iterator.next();
                PriorityBoard nextStep = solving.addNext(next);
                if (nextStep != null) solveQueue.insert(nextStep);
            }
            solving = solveQueue.delMin();
            //--------------------------------------------------------------
            Iterator<Board> additerator = 
                    additional.getBoard().neighbors().iterator();
            while (additerator.hasNext())
            {
                Board next = additerator.next();
                PriorityBoard nextStep = additional.addNext(next);
                if (nextStep != null) additionalQueue.insert(nextStep);
            }
            additional = additionalQueue.delMin();
        }
        if (solving.getBoard().isGoal())
        {
            isSolvable = true;
            this.moves = solving.moves;
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
        if (isSolvable()) return () -> new IteratorBoard(solving);
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
