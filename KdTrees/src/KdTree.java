
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
    private int     size = 0;
    private Node    twodTree = null;
    private double      best_distance = Double.POSITIVE_INFINITY;
    private Point2D     best_point = null;
    
    private enum LineType
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
             if (top != null)
             {
                if (top.type == LineType.Vertical)
                {
                    if (top.item.x() >= rect.xmin() &&
                        top.item.x() < rect.xmax())
                    {
                        if (rect.contains(top.item)) iterator.add(top.item);
                        checkTree(top.left, rect);
                        checkTree(top.right, rect);
                    }
                    else if (top.item.x() >= rect.xmax())
                    {
                        if (rect.contains(top.item)) iterator.add(top.item);
                        checkTree(top.left, rect);
                    }
                    else
                    {
                        checkTree(top.right, rect);
                    }
                }
                else
                {
                    if (top.item.y() >= rect.ymin()&&
                        top.item.y() < rect.ymax())
                    {
                        if (rect.contains(top.item)) iterator.add(top.item);
                        checkTree(top.left, rect);
                        checkTree(top.right, rect);
                    }
                    else if (top.item.y() >= rect.ymax())
                    {
                        if (rect.contains(top.item)) iterator.add(top.item);
                        checkTree(top.left, rect);
                    }
                    else
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
            if (next >= iterator.size()) return null;
            else return iterator.get(next++);
        }
    }
    
    private class Node
    {
        Point2D     item;
        LineType    type;
        Node        left;
        Node        right;
    }
    
    public void insert(Point2D p)
    {
        if (p == null) return;
        if (twodTree == null)
        {
            twodTree = new Node();
            twodTree.item = p;
            twodTree.type = LineType.Vertical;
            twodTree.left = null;
            twodTree.right = null;
            ++size;
        }
        else
        {
            add(twodTree, p);
        }
    }
    
    private void add(Node top, Point2D p)
    {
        if (top.item.compareTo(p) == 0) return;
        if (top.type == LineType.Vertical)
        {
            if (top.item.x() < p.x())
            {
                if (top.right != null) 
                {
                    add(top.right, p);
                }
                else
                {
                    top.right = new Node();
                    top.right.item = p;
                    top.right.type = LineType.Horizontal;
                    top.right.left = null;
                    top.right.right = null;
                    ++size;
                }
            }
            else
            {
                if (top.left != null) 
                {
                    add(top.left, p);
                }
                else
                {
                    top.left = new Node();
                    top.left.item = p;
                    top.left.type = LineType.Horizontal;
                    top.left.left = null;
                    top.left.right = null;
                    ++size;
                }
            }
        }
        else
        {
            if (top.item.y() < p.y())
            {
                if (top.right != null) 
                {
                    add(top.right, p);
                }
                else
                {
                    top.right = new Node();
                    top.right.item = p;
                    top.right.type = LineType.Vertical;
                    top.right.left = null;
                    top.right.right = null;
                    ++size;
                }
            }
            else
            {
                if (top.left != null) 
                {
                    add(top.left, p);
                }
                else
                {
                    top.left = new Node();
                    top.left.item = p;
                    top.left.type = LineType.Vertical;
                    top.left.left = null;
                    top.left.right = null;
                    ++size;
                }
            }
        }
    }
    
    public Point2D nearest(Point2D p)
    {
        best_point = null;
        best_distance = Double.POSITIVE_INFINITY;
        RectHV rect = new RectHV(0, 
                                 0, 
                                 Double.POSITIVE_INFINITY, 
                                 Double.POSITIVE_INFINITY);
        near(twodTree, p, rect);
        return best_point;
    }
    
    private void near(Node top, Point2D p, RectHV rect)
    {
        if (top != null)
        {
            double dist_rect = rect.distanceTo(p);
            double dist_point = top.item.distanceTo(p);
            if (best_point == null)
            {
                best_distance = dist_point;
                best_point = top.item;
            }
            
            if (dist_rect > best_distance)
                return;
            
            if (dist_point < best_distance)
            {
                best_distance = dist_point;
                best_point = top.item;
            }
            
            RectHV left;
            RectHV right;
            if(top.type == LineType.Vertical)
            {
                left = new RectHV(rect.xmin(),rect.ymin(),top.item.x(),rect.ymax());
                right = new RectHV(top.item.x(),rect.ymin(),rect.xmax(),rect.ymax());
            }
            else
            {
                left = new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),top.item.y());
                right = new RectHV(rect.xmin(),top.item.y(),rect.xmax(),rect.ymax());
            }
            
            if((top.type == LineType.Vertical && top.item.x() >= p.x()) ||
               (top.type == LineType.Horizontal && top.item.y() >= p.y()))
            {
                near(top.left, p, left);
                near(top.right, p, right);
            }
            else
            {
                near(top.right, p, right);
                near(top.left, p, left);
            }
        }
    }
    
    public void draw()
    {
        paint(twodTree);
    }
    
    private void paint(Node top)
    {
        if (top != null)
        {
            if (top.left != null)
            {
                paint(top.left);
            }
            if (top.right != null)
            {
                paint(top.right);
            }
            top.item.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)
    {
        return new Iterable<Point2D>() {

            @Override
            public Iterator<Point2D> iterator() {
                return new PointIterator(rect);
            }
        };
    }
    
    public boolean contains(Point2D point)
    {
        return nodeSize(twodTree, point);
    }
    
    private boolean nodeSize(Node top, Point2D point)
    {
        if (top == null) return false;
        if (top.item.compareTo(point) == 0) return true;
        
        boolean result;
        if ((top.type == LineType.Vertical && top.item.x() < point.x()) ||
            (top.type == LineType.Horizontal && top.item.y() < point.y())) 
            result = nodeSize(top.right, point);
        else 
            result = nodeSize(top.left, point);
        return result;
    }
    
    public boolean isEmpty()
    {
        return twodTree == null;
    }

    public int size()
    {
        return size;
    }
}
