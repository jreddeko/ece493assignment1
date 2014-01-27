package com.example.ece493assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.graphics.Color;

public class ImageFilter
{
	private int[][] pixels;
	private int size;
	
	public ImageFilter(int[][] arr, int size)
	{
		this.pixels = arr;
		this.size = size;
	}
	
	public int[][] applyMedianFilter() 
	{
		int newpic[][] = new int[pixels.length][pixels[0].length];

		for(int i = 0; i < pixels.length; i++)
		{
			for(int j = 0; j <pixels[i].length; j++)
			{
				List<Integer> n = getSquareFilter(size, i, j);
				List<Integer> A = new ArrayList<Integer>();
				List<Integer> R = new ArrayList<Integer>();
				List<Integer> G = new ArrayList<Integer>();
				List<Integer> B = new ArrayList<Integer>();

				for(Integer x:n)
				{
					A.add(Color.alpha(x));
					R.add(Color.red(x));
					G.add(Color.green(x));
					B.add(Color.blue(x)); 
				}

				Collections.sort(A);
				Collections.sort(R);
				Collections.sort(G);
				Collections.sort(B);
				int p = Color.argb(A.get(n.size()/2), R.get(n.size()/2), G.get(n.size()/2),B.get(n.size()/2) );
				newpic[i][j] = p;
			}
		}
		return newpic;
	}
	private List<Integer> getSquareFilter(int size, int x, int y)
	{
		int startRow = x - size/2;
		int startCol = y - size/2;

		List<Integer> n = new ArrayList<Integer>();
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{	
				if(!(startRow + i < 0 || startRow + i >= pixels.length))
					if(!(startCol+j < 0 || startCol + j >= pixels[i].length))
						n.add(pixels[startRow+i][startCol+j]);
			}
		}
		return n;
	}
	public int[][] applyMeanFilter() 
	{
		int newpic[][] = new int[pixels.length][pixels[0].length];

		// TODO Auto-generated method stub
		for(int i = 0; i < pixels.length; i++)
		{
			for(int j = 0; j <pixels[i].length; j++)
			{
				List<Integer> n = getSquareFilter(size, i, j);

				int A=0,R=0,G=0,B=0;
				for(Integer x:n)
				{
					A += Color.alpha(x);
					R += Color.red(x);
					G += Color.green(x);
					B += Color.blue(x); 

				}

				int p = Color.argb(A/n.size(), R/n.size(), G/n.size(), B/n.size());
				newpic[i][j] = p;
			}
		}

		return newpic;
	}
}
