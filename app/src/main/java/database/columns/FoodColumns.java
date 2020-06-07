package database.columns;

public enum FoodColumns {

	Category("category"),
	Name("name"),
	Unit("unit"),
	Carbohydrate("carbohydrate"),
	Calorie("calorie"),
	Protein("protein"),
	Fat("fat"),
	Frequency("frequency");
	
	
	private String name;
	private FoodColumns(String s)
	{
		name=s;
	}
	
	public String getString()
	{
		return name;
	}
	
}
