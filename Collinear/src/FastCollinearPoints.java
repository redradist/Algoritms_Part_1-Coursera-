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
public class FastCollinearPoints {
    private ArrayList<LineSegment> segments;
    
    public FastCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
        {
            throw new NullPointerException("points is null");
        }
        segments = new ArrayList<>();
        int size = points.length;
        for (int i = 0; i < (size-1); ++i)
        {
            ArrayList<Point> slopes = new ArrayList<>();
            for (int k = i+1; k < size; ++k)
            {
                if (points[i].compareTo(points[k]) == 0)
                {
                    throw new IllegalArgumentException("repeated points");
                }
                slopes.add(points[k]);
            }
            slopes.sort(points[i].slopeOrder());
            Point first = points[i];
            Point last = points[i];
            int num = 0;
            double slope = 0;
            int lenght = slopes.size();
            for (int k = 0; k < lenght; ++k)
            {
                double temp = slopes.get(k).slopeTo(points[i]);
                if (slope == temp)
                {
                    num++;
                    if (slopes.get(k).compareTo(last) > 0)
                    {
                        last = slopes.get(k);
                    }
                    else if (slopes.get(k).compareTo(first) < 0)
                    {
                        first = slopes.get(k);
                    }
                    if (num >= 3)
                    {
                        segments.add(new LineSegment(first, last));
                    }
                }
                else
                {
                    num = 1;
                    if (slopes.get(k).compareTo(points[i]) > 0)
                    {
                        first = points[i];
                        last = slopes.get(k);
                    }
                    else if (slopes.get(k).compareTo(points[i]) < 0)
                    {
                        first = slopes.get(k);
                        last = points[i];
                    }
                    slope = temp;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }
}
