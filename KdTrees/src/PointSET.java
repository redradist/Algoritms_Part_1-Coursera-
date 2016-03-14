/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author REDRADIST
 */
public class PointSET {
    private SET<Point2D> set;
    
    private class PointIterator implements Iterator<Point2D>
    {
        private final LinkedList<Point2D> iterator;
        private int next;
        
        PointIterator(RectHV rect)
        {
            iterator = new LinkedList<>();
            for (Point2D point : set)
            {
                if (rect.contains(point)) iterator.add(point);
            }
            next = 0;
        }
        
        @Override
        public boolean hasNext() {
             return next < iterator.size();
        }

        @Override
        public Point2D next() {
            return iterator.get(next++);
        }
    }
    
    public PointSET()
    {
        set = new SET<>();
    }
    
    public boolean isEmpty()
    {
        return set.isEmpty();
    }
    
    public int size()
    {
        return set.size();
    }

    public void insert(Point2D p)
    {
        set.add(p);
    }
    
    public boolean contains(Point2D p)
    {
        return set.contains(p);
    }
    
    public void draw()
    {
        for (Point2D point : set)
        {
            point.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)
    {
        if (set == null) return null;
        else return () -> new PointIterator(rect);
    }
    
    public Point2D nearest(Point2D p)
    {
        if (!set.isEmpty() && p != null)
        {
            Point2D temp = null;
            for (Point2D point : set)
                if (temp == null) temp = point;
                else if (point.distanceTo(p) < temp.distanceTo(p)) temp = point;
            return temp;
        }
        else
        {
            return null;
        }
    }
    
    public static void main(String[] args)
    {
        
    }
}
