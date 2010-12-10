// Author: Chee Yap (Data Structure Class, Fall 2010)
//
// Homework 6:
//	Find the error in this program for MergeSort (it occurs in only one place).
// 	Please tell us how you go about debugging it.
//  
// Answer:
// 	On the first run through, it gave a stack overflow error because m was never greater than j in the second sort, so I changed m-1 to m and m to m+1
// 	Because I made this change, I also had to change the merge call to m+1
// 	Since now all cases of m were now m+1, I just added 1 to m when it was declared.

public class Mergesort {

	public static void merge(int[] A, int[] B, int n, int i, int j, int k){
		int ii=i; int jj=j; int kk=i;
		while (ii<j && jj<=k)
			if (A[ii]<A[jj])
				B[kk++] = A[ii++];
			else
				B[kk++] = A[jj++];
		while (ii<j) B[kk++] = A[ii++];
		while (jj<=k) B[kk++] = A[jj++];
		for (int l=i; l<=k; l++) A[l]=B[l];
	}
	public static void sort(int[] A, int [] B, int n, int i, int j){
		if (i<j) {
			int m= (i+j)/2 + 1; 
			System.out.println(i+" "+j+" "+m);
			sort(A, B, n,i,m-1);
			sort(A, B, n,m,j);
			merge(A, B, n, i,m,j);
		}
	}
	public static void main(String[] args){
		final int n=131;
		final int a=73;
		final int b=127;
		int[] A = new int[n];
		int[] B = new int[n];
		for (int i=0; i<n; i++) {
			A[i] = (i*a+b) %n;
			B[i]=-1;
		}
		System.out.println("Original Array A:");
		for (int l=0; l<n; l++) 
			System.out.println(" A[" + l + "] = " + A[l]);
		//merge(A,B,n,0, 5, 9);
		sort(A,B,n,0,n-1);
		System.out.println("Sorted Array A:");
		for (int l=0; l<n; l++) 
			System.out.println(" A[" + l + "] = " + A[l]);
	}
}
