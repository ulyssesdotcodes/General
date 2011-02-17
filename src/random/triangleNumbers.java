package random;

import random.UsefulStuff;

public class triangleNumbers {
	public static void main(String[] args){
		int currentNaturalNumber = 8, currentSum = 28, currentDivisors = 0;
		
		while(currentDivisors < 500){
			currentSum += currentNaturalNumber;
			if(currentNaturalNumber % 2 == 0)
				currentDivisors = UsefulStuff.numberOfFactors(currentNaturalNumber/2) * UsefulStuff.numberOfFactors((++currentNaturalNumber));
			else
				currentDivisors = UsefulStuff.numberOfFactors(currentNaturalNumber) * UsefulStuff.numberOfFactors((++currentNaturalNumber)/2);
			System.out.println(currentNaturalNumber + " " + currentSum +" " + currentDivisors);
		}
		
		System.out.println(currentSum);
	}
}
