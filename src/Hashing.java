import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Hashing {
	private static final double GOLDEN_RATIO = (1 + Math.sqrt(5))/2;
	
	public static int h(long x, int m){
		int hashVal = 0;
		hashVal = (int)x % m;
		
		if(hashVal < 0){
			hashVal += m;
		}
		
		return hashVal;
	}
	
	public static long string2num (String str){
		long hashVal = 0;
		
		for(int i=0; i < str.length(); i++){
			hashVal += 37*hashVal + str.charAt(i);
		}

		return hashVal;
	}
	
	public static int g(long x, int m){
		return (int) Math.floor(computeFraction(GOLDEN_RATIO*x)*m);
	}
	
	public static double computeFraction(double x){
		return x - Math.floor(x);
	}
	
	public static void main(String[] args){
		File small = new File("small.txt");
		File med = new File("med.txt");
		File dic = new File("dic.txt");
		Scanner scanFile;
		int m = 10000;
		if(args.length > 0){
			m = Integer.parseInt(args[0]);
		}
		
		try {
			scanFile = new Scanner(med);
			String word;
			
			//Compute conflicts and time for h
			int[] takenValues = new int[m];
			for(int i=0;i<m;i++){
				takenValues[i] = 0;
			}
			int conflicts = 0;
			
			long startTime = System.nanoTime();
			int result;
			while(scanFile.hasNextLine()){
				word = scanFile.next();
				scanFile.nextLine();
				
				result = h(string2num(word),m);
				if(takenValues[result] != 0){
					conflicts++;
				}
				takenValues[result]++;
			}
			int computeConflicts = 0;
			for(int i=0;i<m;i++){
				computeConflicts += takenValues[i]^2;
			}

			System.out.println("Computed conflicts for g: " + computeConflicts);
			System.out.println("Actual conflicts for h: " + conflicts);
			System.out.println("Time taken in seconds for h: " + (System.nanoTime()-startTime)/Math.pow(10,9) +" seconds.");
			
			
			//Compute conflicts and time for g
			for(int i=0;i<m;i++){
				takenValues[i] = 0;
			}
			conflicts = 0;
			scanFile = new Scanner(med);
			
			startTime = System.nanoTime();
			while(scanFile.hasNextLine()){
				word = scanFile.next();
				scanFile.nextLine();
				
				result = g(string2num(word),m);
				if(takenValues[result] != 0){
					conflicts++;
				}
				takenValues[result]++;
			}
			computeConflicts = 0;
			for(int i=0;i<m;i++){
				computeConflicts += takenValues[i]^2;
			}

			System.out.println("Computed conflicts for g: " + computeConflicts);
			System.out.println("Actual conflicts for g: " + conflicts);
			System.out.println("Time taken in seconds for g: " + (System.nanoTime()-startTime)/Math.pow(10,9) +" seconds.");
			

			//Compute conflicts and time for j
//			for(int i=0;i<m;i++){
//				takenValues[i] = 0;
//			}
//			conflicts = 0;
			scanFile = new Scanner(med);
			
			startTime = System.nanoTime();
			while(scanFile.hasNextLine()){
				word = scanFile.next();
				scanFile.nextLine();
				
//				result = g(string2num(word),m);
//				if(takenValues[result] != 0){
//					conflicts++;
//				} else {
//					takenValues[result] = 1;
//				}
			}
			
			System.out.println("Could not find the function that j uses, and so could not compute the conflicts.");
			System.out.println("Time taken in seconds for j: " + (System.nanoTime()-startTime)/Math.pow(10,9) +" seconds.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
