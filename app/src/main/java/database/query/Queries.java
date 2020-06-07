package database.query;



public class Queries {

	public static String selectCategory(String category)
	{
		return "Select name from Food where category='"+category+"'";
	}
	
	public static String selectItem(String name)
	{
		return "Select * from Food where name='"+name+"'";
	}
	
	public static String selectTotalValuesInDay(String date)
	{
		return "Select * from Parameter_Day where date='"+date+"'";
	}
	
	public static String selectAllTotalValuesInDay()
	{
		return "Select * from Parameter_Day";
	}
	
	public static String selectRecentItems(String name )
	{
		return "Select * from Recent where name='"+name+"'";
	}
	
	public static String selectSortedRecentItems(String name)
	{
		return "Select * from Recent order by date asc";
	}
	
	public static String selectRecetnItems()
	{
		return" Select name from Recent ";
	}
	
	public static String selectFrequentFood()
	{
		return "Select name from Food where frequency<>0 order by frequency desc limit 10";
	}

	public static String getAllfoods() {
		return "Select name from food";
	}

	public static String getTodayConsumedFoods(String today) {
		return "Select * from Consumed_Item where date='"+today+"'";
	}

	public static String deleteAllParameterDay()
	{
		return "Delete from Parameter_Day";
	}

	
	public static String getParameterDay()
	{
		return "Select * from Parameter_Day order by date desc";
	}

	public static String deleteAllItems(){
		return "Delete from Consumed_Item";
	}


}
