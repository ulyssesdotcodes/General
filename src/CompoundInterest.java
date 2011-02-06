import java.util.Calendar;
import java.util.Date;



public class CompoundInterest {
	
	public static long daysBetween(Calendar startDate, Calendar endDate){
		long daysBetween = 0;
		Calendar date = (Calendar) startDate.clone();
		while(date.before(endDate)){
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;
	}
	
	public static long weeksBetween(Calendar startDate, Calendar endDate){
		long weeksBetween = 0;
		Calendar date = (Calendar) startDate.clone();
		while(date.before(endDate)){
			date.add(Calendar.WEEK_OF_MONTH, 1);
			weeksBetween++;
		}
		return weeksBetween;
	}

	public static long monthsBetween(Calendar startDate, Calendar endDate){
		long monthsBetween = 0;
		Calendar date = (Calendar) startDate.clone();
		while(date.before(endDate)){
			date.add(Calendar.MONTH, 1);
			monthsBetween++;
		}
		System.out.println(monthsBetween);
		return monthsBetween;
	}
	
	public static double computeDailyInterest(double principle, double current, Calendar startDate){
		double interest = Math.pow(Math.E, (Math.log(current)-Math.log(principle))/monthsBetween(startDate, Calendar.getInstance())) - 1;
		return interest;
	}
	
	public static void main(String[] args){
		Calendar startDate = Calendar.getInstance();
		startDate.set(2010,8,26);
		System.out.println(computeDailyInterest(5000.0, 6301.0, startDate));
	}
}
