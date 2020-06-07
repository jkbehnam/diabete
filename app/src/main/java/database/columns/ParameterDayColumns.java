package database.columns;

public enum ParameterDayColumns {

	Date("date"),
	Total_Carbohydrate("total_carbohydrate"),
	Total_Calorie("total_calorie"),
	Total_protein("total_protein"),
	Total_fat("total_fat");
	
	
	private String name;
	private ParameterDayColumns(String s)
	{
		name=s;
	}
	
	public String getString()
	{
		return name;
	}
}
