package controller;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import com.example.diabetes.name.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import database.TestAdapter;
import database.query.Queries;
import entity.ConsumedItem;
import entity.Item;
import entity.ParameterDay;
import entity.Parameters;
import entity.Recent;
import utility.Converter;

public class Controller {


	public static void createNewParameterDay(Context context) {
		String currentDate = getTodayDate();
		TestAdapter ta = new TestAdapter(context);
		List<String[]> temp = ta.getData(Queries.selectAllTotalValuesInDay());
		if (temp.size() < 3) {
			ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
			ta.InsertData(pd);
		} else {
			String days2ago=getDaysBefore(-2);
			String yesterday=getDaysBefore(-1);
			 if(ParameterDay.containsdate(temp, yesterday))
			{
				List<ParameterDay> pds = new LinkedList<ParameterDay>();
				for (String[] a : temp)
					pds.add(new ParameterDay(a));
				ParameterDay min = ParameterDay.findMin(pds);
				ta.deleteData(min);
				ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
				ta.InsertData(pd);
			}
			else if(ParameterDay.containsdate(temp, days2ago)) {
				 List<ParameterDay> pds = new LinkedList<ParameterDay>();
				 for (String[] a : temp)
					 pds.add(new ParameterDay(a));
				 ParameterDay min = ParameterDay.findMin(pds);
				 ta.deleteData(min);
				 ParameterDay min2 = ParameterDay.findMin(pds);
				 ta.deleteData(min2);

				 ParameterDay pdyest = new ParameterDay(yesterday, 0, 0, 0, 0);
				 ta.InsertData(pdyest);
				 ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
				 ta.InsertData(pd);
			}
			else
			 {
				 ta.executeQuery(Queries.deleteAllParameterDay());
				 ta.executeQuery(Queries.deleteAllItems());
				 ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
				 ta.InsertData(pd);
			 }

		}
	}
	/*public static void createNewParameterDay(Context context) {
		String currentDate = getTodayDate();
		TestAdapter ta = new TestAdapter(context);
		List<String[]> temp = ta.getData(Queries.selectAllTotalValuesInDay());
		if (temp.size() < 3) {
			ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
			ta.InsertData(pd);
		} else {
			String days2ago=getDaysBefore(-2);
			String yesterday=getDaysBefore(-1);
			if(!ParameterDay.containsdate(temp, days2ago))
			{
				ta.executeQuery(Queries.deleteAllParameterDay());
				ta.executeQuery(Queries.deleteAllItems());
				ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
				ta.InsertData(pd);
			}
			else if (!ParameterDay.containsdate(temp, currentDate)) {

				List<ParameterDay> pds = new LinkedList<ParameterDay>();
				for (String[] a : temp)
					pds.add(new ParameterDay(a));
				ParameterDay min = ParameterDay.findMin(pds);
				ta.deleteData(min);
				ta.executeQuery(Queries.deleteAllItems());
				ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
				ta.InsertData(pd);
			}
			else if(!ParameterDay.containsdate(temp, yesterday))
			{
				List<ParameterDay> pds = new LinkedList<ParameterDay>();
				for (String[] a : temp)
					pds.add(new ParameterDay(a));
				ParameterDay min = ParameterDay.findMin(pds);
				ta.deleteData(min);
				ParameterDay min2 = ParameterDay.findMin(pds);
				ta.deleteData(min2);
				ParameterDay pdyest = new ParameterDay(yesterday, 0, 0, 0, 0);
				ta.InsertData(pdyest);
				ParameterDay pd = new ParameterDay(currentDate, 0, 0, 0, 0);
				ta.InsertData(pd);


			}

		}
	}*/
	
	
	
	public static List<String> getAllfoods(Context context) {
		TestAdapter ta = new TestAdapter(context);
		return Converter.stringArrayToString(ta.getData(Queries.getAllfoods()));
		
	}

	public static void addNewFood(String category, String name, String unit,
			int carbo, int calorie, int protein, int fat, Context context) {
		TestAdapter ta = new TestAdapter(context);
		Item item = new Item(category, name, unit, carbo, calorie, protein,
				fat, 0);
		ta.InsertData(item);
	}



	public static boolean addNormalItem(String meal, String name, int amount,
			Context context) {
		String currentDate = getTodayDate();
		TestAdapter ta = new TestAdapter(context);
		String[] temp = ta.getData(Queries.selectItem(name)).get(0);

		try{
		ConsumedItem citem = insertConsumedItem(currentDate, meal, amount,
				temp, ta);
		if(citem==null)
			throw new SQLiteConstraintException();
		updateTodayParameters(currentDate, citem, ta);
		updateFrequency(temp, ta);
		updateRecent(currentDate, name, ta);
		return true;
		}
		catch(SQLiteConstraintException e)
		{
		return false;	
		}


	}

	public static void quickAdd(String meal, int carbo, int calerie,
			int protein, int fat, Context context) {
		TestAdapter ta = new TestAdapter(context);
		String currentDate = getTodayDate();
		ConsumedItem citem = insertConsumedItem(currentDate, meal, carbo,
				calerie, protein, fat, ta);
		updateTodayParameters(currentDate, citem, ta);
	}

	public static List<String> getRecentItems( Context context) {
		TestAdapter ta = new TestAdapter(context);
		List<String> recentList= Converter.stringArrayToString(ta.getData(Queries
				.selectRecetnItems()));
		if(recentList.size()<10)
			for(int i=0;recentList.size()<10;i++)
				recentList.add("");
		return recentList;
	}

	public static boolean addRecentItem(String meal, String name, int amount,
			Context context) {
		return addNormalItem(meal, name, amount, context);
	}

	public static List<String> getFrequentItems(Context context) {
		TestAdapter ta = new TestAdapter(context);
		List<String> frequentList= Converter.stringArrayToString(ta.getData(Queries
				.selectFrequentFood()));
		if(frequentList.size()<10)
			for(int i=0;frequentList.size()<10;i++)
				frequentList.add("");
		return frequentList;
	}

	public static Item getSelectedItem(String name, Context context) {
		TestAdapter ta = new TestAdapter(context);
		return new Item(ta.getData(Queries.selectItem(name)).get(0));
	}

	public static boolean addFrequentItem(String meal, String name, int amount,
			Context context) {
		return addNormalItem(meal, name, amount, context);
	}

	public static void deleteItem(ConsumedItem citem, Context context) {
		TestAdapter ta = new TestAdapter(context);
		ta.deleteData(citem);
		ParameterDay pd=new ParameterDay(ta.getData(Queries.selectTotalValuesInDay(getTodayDate())).get(0));
		pd.removeItem(citem);
		ta.updateData(pd);
	}

	public static void updateItem(ConsumedItem citem, int amount, Context context)
	{
		TestAdapter ta = new TestAdapter(context);
		ta.deleteData(citem);
		ParameterDay pd=new ParameterDay(ta.getData(Queries.selectTotalValuesInDay(getTodayDate())).get(0));
		pd.removeItem(citem);
		citem.setAmount(amount);
		ta.InsertData(citem);
		pd.addItem(citem);
		ta.updateData(pd);

	}

	public static List<ParameterDay> getTotalReport(Context context)
	{
		TestAdapter ta=new TestAdapter(context);
		ArrayList<String[]>data=ta.getData(Queries.getParameterDay());
		List<ParameterDay>pd=new ArrayList<ParameterDay>();
		for(String[] temp:data)
			pd.add(new ParameterDay(temp));
		return pd;
	}
	
	public static List<ConsumedItem>[] getEatenItems(Context context)
	{
		TestAdapter ta=new TestAdapter(context);
		String today=getTodayDate();
		
		List<ConsumedItem>[] cons=(ArrayList<ConsumedItem>[])new ArrayList[6];
		for(int i=0;i<6;i++)
			cons[i]=new ArrayList<ConsumedItem>();
		
		List<String[]> data=ta.getData(Queries.getTodayConsumedFoods(today));
		for(String[] temp:data)
		{
			ConsumedItem ci=new ConsumedItem(temp);
			if(ci.getMeal().compareTo(context.getResources().getStringArray(R.array.array_name)[0])==0)
				cons[0].add(ci);
			else if(ci.getMeal().compareTo(context.getResources().getStringArray(R.array.array_name)[1])==0)
				cons[1].add(ci);
			else if(ci.getMeal().compareTo(context.getResources().getStringArray(R.array.array_name)[2])==0)
				cons[2].add(ci);
			else if(ci.getMeal().compareTo(context.getResources().getStringArray(R.array.array_name)[3])==0)
				cons[3].add(ci);
			else if(ci.getMeal().compareTo(context.getResources().getStringArray(R.array.array_name)[4])==0)
				cons[4].add(ci);
			else if(ci.getMeal().compareTo(context.getResources().getStringArray(R.array.array_name)[5])==0)
				cons[5].add(ci);
		}
		return cons;
	}

	public static Parameters[] computeTotalParamsInMeals(List<ConsumedItem>[] eaten)
	{
		Parameters[] mealParams=new Parameters[6];
		for(int i=0;i<mealParams.length;i++)
		{
			mealParams[i]=new Parameters();
			for(int j=0;j<eaten[i].size();j++)
			{
				mealParams[i].addParams(eaten[i].get(j).getParameters());
			}
		}
		return mealParams;
	}

	private static String getDaysBefore(int days)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,days);
		return dateFormat.format(cal.getTime());
	}

	
	private static String getTodayDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	private static ConsumedItem insertConsumedItem(String currentDate,
			String meal, int carbohydrate, int calorie, int protein, int fat,
			TestAdapter ta) {
		ConsumedItem citem = new ConsumedItem(currentDate, meal, carbohydrate,
				calorie, protein, fat);
		ta.InsertData(citem);
		return citem;
	}

	private static ConsumedItem insertConsumedItem(String currentDate,
			String meal, int amount, String[] foodData, TestAdapter ta) {
		ConsumedItem citem = new ConsumedItem(currentDate, meal, amount,
				foodData);
		boolean temp=ta.InsertData(citem);
		if(temp)
			return citem;
		return null;
	}

	private static void updateTodayParameters(String currentDate,
			ConsumedItem citem, TestAdapter ta) {
		ParameterDay pd = new ParameterDay(ta.getData(
				Queries.selectTotalValuesInDay(currentDate)).get(0));
		pd.addItem(citem);// /////PD ready to be shown in UI
		ta.updateData(pd);// PD updates

	}

	private static void updateFrequency(String[] foodData, TestAdapter ta) {
		Item item = new Item(foodData);
		ta.updateData(item);// frequency updates
	}

	private static void updateRecent(String curentDate,
			String name, TestAdapter ta) {
		Recent recent = new Recent(curentDate, "", name);
		if (ta.getData(Queries.selectRecentItems(name)).size() == 0) {
			ta.InsertData(recent);
			List<String[]> temp1 = ta.getData(Queries.selectSortedRecentItems(name));
			if (temp1.size() > 10) {
				Recent r = new Recent(temp1.get(temp1.size() - 1));
				ta.deleteData(r);
			}
		} else
			ta.updateData(recent);
	}
}
