/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queues_deques;

import edu.princeton.cs.algs4.StdIn;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author REDRADIST
 */
public class Subset {
    public static void main(String[] args)   // unit testing
    {   
        // Checking number of arguments
        if (args.length != 1)
        {
            throw new IllegalArgumentException("Number of arguments must be only 2");
        }
        else
        {
            int k = Integer.parseInt(args[0]);
            RandomizedQueue<String> queue = new RandomizedQueue<>();
            while(true)
            {
                try
                {
                    String str = StdIn.readString();
                    queue.enqueue(str);
                }
                catch(NoSuchElementException e)
                {
                    break;
                }
            }
            while(k-- > 0)
            {
                StdOut.println(queue.dequeue());
            }
        }
    }
}
