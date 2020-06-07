package database.columns;

public enum ConsumedItemColumns {

	Date("date"),
	Meal("meal"),
	Item_Name("item_name"),
	Amount("amount"),
	Carbohydrate("carbohydrate"),
	Calorie("calorie"),
	Protein("protein"),
	Fat("fat")
	;
	
	private String name;
	private ConsumedItemColumns(String s)
	{
		name=s;
	}
	
	public String getString()
	{
		return name;
	}
}
