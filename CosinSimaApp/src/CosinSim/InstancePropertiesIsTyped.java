package CosinSim;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class InstancePropertiesIsTyped {
	public boolean isTyped;
	
	public TreeSet<String> propertySet=new TreeSet<String>();
	
	 
	
	public InstancePropertiesIsTyped(String properties, boolean typevalue)
	{
		//this.propertyId = id;
		
		if (properties!= "")propertySet.add(properties);
		this.isTyped = typevalue;
	}
	

}