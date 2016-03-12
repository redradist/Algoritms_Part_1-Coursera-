
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.Iterator;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author REDRADIST
 */
public class KdTree {
    private Node    twodTree = null;
    
    enum LineType
    {
        Vertical,
        Horizontal
    }
    
    private class PointIterator implements Iterator<Point2D>
    {
        private final LinkedList<Point2D> iterator;
        private int next;
        
        PointIterator(RectHV rect)
        {
            iterator = new LinkedList<>();
            checkTree(twodTree, rect);
            next = 0;
        }
        
        private void checkTree(Node top, RectHV rect) {
             if(top != null)
             {
                if(top.type == LineType.Vertical)
                {
                    if(top.item.x() >= rect.xmin() &&
                       top.item.x() <= rect.xmax())
                    {
                        if(rect.contains(top.item)) iterator.add(top.item);
                        checkTree(top.left, rect);
                        checkTree(top.right, rect);
                    }
                    else if(top.item.x() > rect.xmin())
                    {
                        checkTree(top.left, rect);
                    }
                    else if(top.item.x() < rect.xmax())
                    {
                        checkTree(top.right, rect);
                    }
                }
                else
                {
                    if(top.item.y() >= rect.ymin()&&
                       top.item.y() <= rect.ymax())
                    {
                        if(rect.contains(top.item)) iterator.add(top.item);
                        checkTree(top.left, rect);
                        checkTree(top.right, rect);
                    }
                    else if(top.item.y() > rect.ymin())
                    {
                        checkTree(top.left, rect);
                    }
                    else if(top.item.y() < rect.ymax())
                    {
                        checkTree(top.right, rect);
                    }
                }
             }
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
    
    private class Node
    {
        Point2D     item;
        LineType    type;
        Node        parent;
        Node        left;
        Node        right;
    }
    
    public void insert(Point2D p)
    {
        if(twodTree == null)
        {
            twodTree = new Node();
            twodTree.item = p;
            twodTree.type = LineType.Vertical;
            twodTree.parent = null;
            twodTree.left = null;
            twodTree.right = null;
        }
        else
        {
            add(twodTree, p);
        }
    }
    
    private void add(Node top, Point2D p)
    {
        Node temp;
        if(top.type == LineType.Vertical)
        {
            if(top.item.x() < p.x())
            {
                if(top.right != null) 
                {
                    add(top.right, p);
                    return;
                }
                else
                {
                    top.right = new Node();
                    temp = top.right;
                }
            }
            else
            {
                if(top.left != null) 
                {
                    add(top.left, p);
                    return;
                }
                else
                {
                    top.left = new Node();
                    temp = top.left;
                }
            }
            temp.type = LineType.Horizontal;
        }
        else
        {
            if(top.item.y() < p.y())
            {
                if(top.right != null) 
                {
                    add(top.right, p);
                    return;
                }
                else
                {
                    top.right = new Node();
                    temp = top.right;
                }
            }
            else
            {
                if(top.left != null) 
                {
                    add(top.left, p);
                    return;
                }
                else
                {
                    top.left = new Node();
                    temp = top.left;
                }
            }
            temp.type = LineType.Vertical;
        }
        
        temp.parent = top;
        temp.item = p;
        temp.left = null;
        temp.right = null;
    }
    
    public Point2D nearest(Point2D p)
    {
        if(twodTree != null)
        {
            Point2D point = twodTree.item;
            double near = twodTree.item.distanceTo(p);
            if(twodTree.item.x() == p.x())
            {
                Point2D temp;
                temp = nearest(twodTree.left, p, near);
                if(temp != null && temp.distanceTo(p) < near) point = temp;
                temp = nearest(twodTree.right, p, near);
                if(temp != null && temp.distanceTo(p) < near) point = temp;
            }
            else if(twodTree.item.x() < p.x())
            {
                Point2D temp;
                temp = nearest(twodTree.left, p, near);
                if(temp != null && temp.distanceTo(p) < near) point = temp;
            }
            else
            {
                Point2D temp;
                temp = nearest(twodTree.right, p, near);
                if(temp != null && temp.distanceTo(p) < near) point = temp;
            }
            return point;
        }
        else
        {
            return null;
        }
    }
    
    private Point2D nearest(Node top, Point2D p, double prenear)
    {
        if(top != null)
        {
            Point2D point = top.item;
            double near = top.item.distanceTo(p);
            if(near == prenear)
            {
                return point;
            }
            else if(near < prenear)
            {
                Point2D temp;
                temp = nearest(top.left, p, near);
                if(temp != null && temp.distanceTo(p) < near) point = temp;
                temp = nearest(top.right, p, near);
                if(temp != null && temp.distanceTo(p) < near) point = temp;
            }
            else
            {
                if(top.type == LineType.Vertical)
                {
                    if(top.item.x() == p.x())
                    {
                        Point2D temp;
                        temp = nearest(top.left, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                        temp = nearest(top.right, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                    }
                    else if(top.item.x() > p.x())
                    {
                        Point2D temp;
                        temp = nearest(top.left, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                    }
                    else
                    {
                        Point2D temp;
                        temp = nearest(top.right, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                    }
                }
                else
                {
                    if(top.item.y() == p.y())
                    {
                        Point2D temp;
                        temp = nearest(top.left, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                        temp = nearest(top.right, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                    }
                    else if(top.item.y() > p.y())
                    {
                        Point2D temp;
                        temp = nearest(top.left, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                    }
                    else
                    {
                        Point2D temp;
                        temp = nearest(top.right, p, prenear);
                        if(temp != null && temp.distanceTo(p) < prenear) point = temp;
                    }
                }
            }
            return point;
        }
        else
        {
            return null;
        }
    }
    
    public void draw()
    {
        draw(twodTree);
    }
    
    private void draw(Node top)
    {
        if(top != null)
        {
            if(top.left != null)
            {
                draw(top.left);
            }
            if(top.right != null)
            {
                draw(top.right);
            }
            top.item.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)
    {
        if(twodTree == null) return null;
        else return () -> new PointIterator(rect);
    }
}
