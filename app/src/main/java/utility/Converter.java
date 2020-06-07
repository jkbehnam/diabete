package utility;

import java.util.LinkedList;
import java.util.List;

public class Converter {
	
	public static List<String> stringArrayToString(List<String[]> a)
	{
		List<String> temp=new LinkedList<String>();
		for(String[] b:a)
			temp.add(b[0]);
		return temp;
	}

}
