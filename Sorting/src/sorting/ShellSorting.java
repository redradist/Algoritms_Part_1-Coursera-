/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

/**
 *
 * @author REDRADIST
 */
public class ShellSorting {
    private static boolean less(int v,  int w)
    {  return v < w;  }
    
    private static void exch (int [ ]  a,  int i,  int j)
    {  int t = a[i]; a[i]  = a[j];  a[j]  = t;  }
    
    public static void sort (int []  a)
    {  // Сортировка a[]  по возрастанию,
        int N = a. length; 
        int h = 1;
        int k = h;
        do {
            h = k;
            k = 3*h + 1;
        } while  (k < N/3);  // 1,  4,  13,  40,  121,  364,  1093,  ...
        while  (h >= 1)
        {  // h-сортировка массива.
            for (int i = h; i < N; i++)
            {  // Вставка a[i] между a[i-h], a[i-2*h], a[i-3*h]...  .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h) 
                    exch(a, j, j-h);
            }
            h = h/3;
        }
        // Реализации less(), exch(), isSorted() и main() см.  в   листинге 2.1.1.
    }
}
