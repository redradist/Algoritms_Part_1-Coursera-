/*
 * To change this license firster, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queues_deques;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author REDRADIST
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;
    private boolean iterorder = false;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    private class NodeIterator implements Iterator<Item>
    {
        private final boolean _order;
        private Node current;

        NodeIterator()
        {
            _order = iterorder;
            if(_order == false)
            {
                current = first;
                iterorder = true;
            }
            else
            {
                current = last;
                iterorder = false;
            }
        }
        
        @Override
        public boolean hasNext()
        {
            return (current != null);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Deqeu does not support remove by iterator");
        }

        @Override
        public Item next()
        {
            if(current == null)
            {
                throw new NoSuchElementException("No more element in Deque");
            }
            Item item = current.item;
            current = (_order == false) ? current.next : current.previous;
            return item;
        }
    }
    
    public RandomizedQueue()
    {
        first = null;
        last = null;
        size = 0;
    }
   
    public boolean isEmpty()
    {
        return (size == 0);
    }
   
    public int size()
    {
        return size;
    }
   
    public void enqueue(Item item)
    {
        if(item == null)
        {
            throw new java.lang.NullPointerException("item is null");
        }
        
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldlast;
        if(isEmpty()) first = last;
        else oldlast.next = last;
        size++;
    }
    
    private Node takeNode(int number)
    {
        Node random;
        if(number < size/2)
        {
            random = first;
            while(number-- > 0)
            {
                if(random.next != null) random = random.next;
                else break;
            } 
        }
        else
        {
            number = size - number - 1;
            random = last;
            while(number-- > 0)
            {
                if(random.previous != null) random = random.previous;
                else break;
            }
        }
        return random;
    }
    
    public Item dequeue()
    {
        if(isEmpty())
        {
           throw new NoSuchElementException("Deqeu is empty");
        }
        
        int number = StdRandom.uniform(0, size);
        Node random = takeNode(number);
        Item item = random.item;
        if(random.previous != null)
        {
            random.previous.next = random.next;
        }
        if(random.next != null)
        {
            random.next.previous = random.previous;
        }
        size--;
        
        if(random == first) first = first.next;
        else if(random == last) last = last.previous;
        
        // Edge if statement
        if(isEmpty())
        {
            first = last = null;
        }
        return item;
    }

    public Item sample()
    {
        if(isEmpty())
        {
           throw new NoSuchElementException("Deqeu is empty");
        }
        
        int number = StdRandom.uniform(0, size);
        Node random = takeNode(number);
        return random.item;
    }

    @Override
    public Iterator<Item> iterator()
    {
        return new NodeIterator();
    }

    public static void main(String[] args)
    {
        
    }
}
