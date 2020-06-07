package database.columns;

public enum RecentColumns {

	Date("date"),
	Meal("meal"),
	Name("name");
	
	
	private String name;
	private RecentColumns(String s)
	{
		name=s;
	}
	
	public String getName()
	{
		return name;
	}
}
