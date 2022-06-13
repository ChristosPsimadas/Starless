package com.shlad.berserk;

import java.util.ArrayList;
import java.util.Arrays;

public class SajivPUBGFortniteTrollface
{

    public static void main(String[] args)
    {
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayList<Integer> ints2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        
        System.out.println(fibonacci(20));
        System.out.println(fibonacci2(20));
        traverse(ints, 0);
        traverse(ints2, 0);
        
        int[] ints3 = randomInts();
        sort(ints3, 0, ints3.length - 1);
        
        int search = (int) (Math.random() * 1000);
        System.out.println(search);
        System.out.println(binarySearch(ints3, search, 0, ints3.length - 1));
    }
    
    // Uses a recursive method, i.e. one that calls itself.
    // Calls a recursive method with more than one base case.
    public static int fibonacci(int n)
    {
        if (n == 0)
        {
            return 0;
        }
        else if (n == 1)
        {
            return 1;
        }
        else
        {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }
    
    public static int fibonacci2(int n)
    {
        int first = 0;
        int second = 1;
        
        for (int i = 0; i < n; i++)
        {
            int temp = first;
            first = second;
            second = temp + second;
        }
        
        return first;
    }
    
    public static void traverse(int[] ints, int i)
    {
        if (i >= ints.length)
        {
            return;
        }
        
        System.out.println(ints[i]);
        traverse(ints, i + 1);
    }
    
    public static void traverse(ArrayList<Integer> ints, int i)
    {
        if (i >= ints.size())
        {
            return;
        }
        
        System.out.println(ints.get(i));
        traverse(ints, i + 1);
    }
    
    public static int[] randomInts()
    {
        int[] ints = new int[1000];
        
        for (int i = 0; i < ints.length; i++)
        {
            ints[i] = (int) (Math.random() * 1000);
        }
        
        return ints;
    }
    
    public static void merge(int[] ints, int left, int mid, int right)
    {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];
        
        for (int i = 0; i < leftSize; i++)
        {
            leftArray[i] = ints[left + i];
        }
        
        for (int i = 0; i < rightSize; i++)
        {
            rightArray[i] = ints[mid + i + 1];
        }
        
        int i = 0, j = 0, k = left;
        
        while (i < leftSize && j < rightSize)
        {
            if (leftArray[i] <= rightArray[j])
            {
                ints[k] = leftArray[i];
                i++;
            }
            else
            {
                ints[k] = rightArray[j];
                j++;
            }
            k++;
        }
        
        while (i < leftSize)
        {
            ints[k] = leftArray[i];
            i++;
            k++;
        }
        
        while (j < rightSize)
        {
            ints[k] = rightArray[j];
            j++;
            k++;
        }
    }
    
    public static void sort(int[] ints, int left, int right)
    {
        if (left < right) {
            int mid = (left + right) / 2;
            
            sort(ints, left, mid);
            sort(ints, mid + 1, right);
            
            merge(ints, left, mid, right);
        }
    }
    
    public static int binarySearch(int[] ints, int target, int left, int right)
    {
        if (left > right)
        {
            return -1;
        }
        
        int mid = (left + right) / 2;
        
        if (ints[mid] == target)
        {
            return mid;
        }
        else if (ints[mid] < target)
        {
            return binarySearch(ints, target, mid + 1, right);
        }
        else
        {
            return binarySearch(ints, target, left, mid - 1);
        }
    }
}
