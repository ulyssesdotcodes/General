package random;
import java.util.Iterator;
import java.util.LinkedList;


public class sumPrime {
	
	public static double mySumPrime(int m){
		int limit = 2000000;
		LinkedList<Integer> primes = new LinkedList<Integer>();
		int n = 2;
		double sum = 1;
		boolean prime = true;
		Iterator<Integer> iter = primes.iterator();
		while(n < limit){
			while(iter.hasNext()){
				if(n % iter.next()==0){
					prime = false;
					break;
				}
			}
			if(prime){
				System.out.println(n+" "+sum);
				primes.add(n);
				sum += n;
			}
			prime = true;
			iter = primes.iterator();
			n++;
		}
		return sum;
	}
	
	public static double EratosthenesSievePrime(int m){
		boolean[] notPrimeNumber = new boolean[m];
		double sum = 0;
		
		for(int i=2;i<m;i++){
			if(notPrimeNumber[i]) continue;
			else{
				int n = 2*i;
				while(n<m){
					notPrimeNumber[n]=true;
					n += i;
				}
			}
		}
		
		for(int i=2;i<m;i++){
			if(!notPrimeNumber[i]) sum += i;
		}
		
		return sum;
		
	}
	
	public static void main(String[] args){
		System.out.println(EratosthenesSievePrime(2000000));
	}
}
