package entity;


public class Day {

	String date;
	Meal[] meals;
	AddableParameters params;
	
	public Day(String date) {
		this.date=date;
		meals=new Meal[4];
		meals[0]=new Meal("Breakfast");
		meals[1]=new Meal("Launch");
		meals[2]=new Meal("Dinner");
		meals[3]=new Meal("Snack");
		
		params=new AddableParameters();
		
	}
}
