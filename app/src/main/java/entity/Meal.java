package entity;

import java.util.LinkedList;
import java.util.List;

public class Meal {

	String name;
	List<Item> items;
	AddableParameters totalParams;
	
	public Meal(String name) {
		this.name=name;
		items=new LinkedList<Item>();
		totalParams=new AddableParameters();
	}
	
	public void addItem(Item item)
	{
		items.add(item);
		totalParams.addUp(item.getParams());
	}
}
