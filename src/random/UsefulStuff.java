package random;

public class UsefulStuff {

	public static boolean[] EratosthenesSieve(int m){
		boolean[] notPrimeNumber = new boolean[m];
		
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
		
		return notPrimeNumber;
		
	}
	
	public static int nextPrimeInSieve(boolean[] notPrimeNumber, int n, int m){
		int i = n;
		for(;i < m && notPrimeNumber[i]; i++);
		if(i>=m) return -1;
		else return i;
	}
	
	static int nGoesIntoM(int n, int m){
		if(m % n == 0) return 1 + nGoesIntoM(n, m/n);
		else return 0;
	}
	
	public static int numberOfFactors(int n){
		boolean[] notPrimeNumber = EratosthenesSieve(n+1);
		int factorCount = 1, currentFactorCount = 0;
		
		for(int i=2; i <= n; i++){
			if(!notPrimeNumber[i]){
				currentFactorCount = nGoesIntoM(i,n);
				if(currentFactorCount != 0){
					factorCount *= currentFactorCount + 1;
					n /= Math.pow(i,currentFactorCount);
				}
			}
		}
		
		return factorCount;
	}
	
	public static void main(String[] args){
		System.out.println(numberOfFactors(105));
	}
	
}
