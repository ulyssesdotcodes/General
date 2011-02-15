
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
	
}
