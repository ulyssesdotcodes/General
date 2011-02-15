package random;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


public class MaxTowerOfHanoi {
	
	public static int MTH(int n, Stack<Integer> A, Stack<Integer> B, Stack<Integer> C, boolean order, int count){
		if(n > 1) count = MTH(n-1, A, B, C, order, count);
		C.push(A.pop());
		count ++;
		if(order) printTower(A, B, C);
		else printTower(B, A, C);
		if(n > 1) count = MTH(n-1, B, A, C, !order, count);
		B.push(C.pop());
		count ++;
		if(order) printTower(A, B, C);
		else printTower(B, A, C);
		if(n > 1) count = MTH(n-1, A, B, C, order, count);
		return count;
	}
	
	public static void printTower(Stack<Integer> A, Stack<Integer> B, Stack<Integer> C){
		Iterator<Integer> iter = A.iterator();
		System.out.println("Tower of Hanoi:");
		while(iter.hasNext()){
			System.out.print(iter.next());
		}
		if(A.isEmpty()) System.out.print("0");
		System.out.println();
		
		iter = B.iterator();
		while(iter.hasNext()){
			System.out.print(iter.next());
		}
		if(B.isEmpty()) System.out.print("0");
		System.out.println();
		
		iter = C.iterator();
		while(iter.hasNext()){
			System.out.print(iter.next());
		}
		if(C.isEmpty()) System.out.print("0");
		System.out.println();
		
		
		System.out.println();
	}
	
	public static void main(String[] args){
		int rings = 6;
		
		Stack<Integer> A = new Stack<Integer>(),
			B = new Stack<Integer>(),
			C = new Stack<Integer>();
		
		for(int i=rings;i>=1;i--){
			A.push(i);
		}
		printTower(A, B, C);
		System.out.println(MTH(rings, A, B, C, true, 0));
	}
}
