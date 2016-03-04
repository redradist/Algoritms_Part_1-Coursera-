/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

/**
 *
 * @author REDRADIST
 */
public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments;
    
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
        {
            throw new NullPointerException("points is null");
        }
        segments = new ArrayList<>();
        int size = points.length;
        for (int i = 0; i < (size-1); ++i)
        {
            Point first;
            Point last;
            for (int k = i+1; k < size; ++k)
            {
                if (points[i].compareTo(points[k]) == 0)
                {
                    throw new IllegalArgumentException("repeated points");
                }
                int num = 2;
                if (points[i].compareTo(points[k]) > 0)
                {
                    first = points[k];
                    last = points[i];
                }
                else
                {
                    first = points[i];
                    last = points[k];
                }
                double slope = points[i].slopeTo(points[k]);
                for (int m = k+1; m < size; ++m)
                {
                    if (points[k].compareTo(points[m]) == 0)
                    {
                        throw new IllegalArgumentException("repeated points");
                    }
                    if (slope == points[i].slopeTo(points[m]))
                    {
                        num++;
                        if (points[m].compareTo(last) > 0)
                        {
                            last = points[m];
                        }
                        else if (points[m].compareTo(first) < 0)
                        {
                            first = points[m];
                        }
                    }
                }
                if (num >= 4)
                {
                    segments.add(new LineSegment(first, last));
                }
            }
        }
    }
    
    public int numberOfSegments()        // the number of line segments
    {
        return segments.size();
    }
    
    public LineSegment[] segments()                // the line segments
    {
        return (LineSegment[]) segments.toArray(new LineSegment[segments.size()]);
    }
    
    public static void main(String[] args) {
        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
