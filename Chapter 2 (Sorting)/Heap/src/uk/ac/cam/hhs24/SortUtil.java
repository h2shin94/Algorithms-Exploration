package uk.ac.cam.hhs24;

import java.util.Random;
import java.util.ArrayList;

public class SortUtil{
	public static void main(String[] args) {
		int[] a = randomArray(20, 100);
		printArray(a);
		ArrayList<Integer> arr = randomArraylist(20, 100);
		printArray(arr);
	}

	public static int[] randomArray(int n, int r){
		int[] a = new int[n];
		Random random = new Random();
		for (int i = 0;i < n ;i++ ) {
			a[i] = random.nextInt(r);
		}
		return a;
	}

	public static int[] randomArray(int n){
		int[] a = new int[n];
		Random random = new Random();
		for (int i = 0;i < n ;i++ ) {
			a[i] = random.nextInt();
		}
		return a;
	}

	public static ArrayList<Integer> randomArraylist(int n, int r){
		ArrayList<Integer> a = new ArrayList<Integer>();
		Random random = new Random();
		for (int i = 0; i < n;i++ ) {
			a.add(random.nextInt(r));
		}
		return a;
	}

	public static void printArray(int[] a){
		System.out.print("{" + a[0]);
		for (int i = 1;i < a.length ;i++ ) {
			System.out.print(", " + a[i]);
		}
		System.out.println("}");
	}

	public static void printArray(ArrayList<Integer> a){
		System.out.print("{" + a.get(1));
		for (int i = 2;i < a.size() ;i++ ) {
			System.out.print(", " + a.get(i));
		}
		System.out.println("}");
	}
}