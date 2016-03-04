/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queues_deques;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author REDRADIST
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
   
    private int size;
    private Node front;
    private Node end;
    
    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }
   
    private class NodeIterator implements Iterator<Item>
    {
        private Node current = front;
        
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
            current = current.previous;
            return item;
        }
    }
 
    public Deque()                           // construct an empty deque
    {
        front = null;
        end = null;
        size = 0;
    }
   
    public boolean isEmpty()                 // is the deque empty?
    {
        return (size == 0);
    }
   
    public int size()                        // return the number of items on the deque
    {
        return size;
    }
   
    public void addFirst(Item item)          // add the item to the front
    {
        if(item == null)
        {
            throw new java.lang.NullPointerException("item is null");
        }
        
        if(front == null)
        {
            front = new Node();
            end = front;
            front.item = item;
        }
        else
        {
             front.next = new Node();
             front.next.item = item;
             front.next.previous = front;
             front.next.next = null;
             front = front.next;
        }
        size++;
    }
   
    public void addLast(Item item)           // add the item to the end
    {
        if(item == null)
        {
            throw new java.lang.NullPointerException("item is null");
        }
        
        if(end == null)
        {
            end = new Node();
            front = end;
            end.item = item;
        }
        else
        {
            end.previous = new Node();
            end.previous.item = item;
            end.previous.next = end;
            end.previous.previous = null;
            end = end.previous;
        }
        size++;
    }
   
    public Item removeFirst()                // remove and return the item from the front
    {
        if(front == null || end == null)
        {
            throw new NoSuchElementException("Deqeu is empty");
        }
        
        Item item = front.item;
        if(front.previous != null)
        {
            front = front.previous;
            front.next = null;
        }
        else
        {
           front = null;
           end = null;
        }
        size--;
        return item;
    }
   
    public Item removeLast()                 // remove and return the item from the end
    {
        if(front == null || end == null)
        {
           throw new NoSuchElementException("Deqeu is empty");
        }
        
        Item item = end.item;
        if(end.next != null)
        {
           end = end.next;
           end.previous = null;
        }
        else
        {
            front = null;
            end = null; 
        }
        size--;
        return item;
    }
   
    @Override
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new NodeIterator();
    }
    
    public static void main(String[] args)   // unit testing
    {
        
    }
}
