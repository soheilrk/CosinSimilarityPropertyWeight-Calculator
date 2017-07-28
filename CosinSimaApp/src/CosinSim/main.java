package CosinSim;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;


public class main {
	static TreeMap<String, Property> map = new TreeMap<String, Property>();
	static int noTotalOccurances = 0; 
	public static void main(String[] args) throws IOException {
		
		FileExits();

	  }
	
	
	//function to read the first dataset
	public static void readDataSet1(String N3DataSet) throws IOException {
		
		String[] data = readLines(N3DataSet);
		
		//TreeMap<String, Integer>map = new TreeMap<String, Integer>();
		for (String line : data){
			String[] s = line.split(" ");
			if (s.length<3) continue;
			if (s[1].contains("<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>")) continue;
			
			noTotalOccurances++;
			String mapkey = s[1];
			//check if the propertyname was in out TreeMap before or not
			if (map.get(mapkey)== null){
				Property entryProperty = new Property(1, mapkey);
				map.put(entryProperty.getKey(),entryProperty);
			}
			else{
				Property value=map.get(mapkey);
				 value.occurances++ ;
				map.put(mapkey,value);
			}
			

	}
		
//		System.out.println(map.get("<http://dbpedia.org/ontology/abstract>").propertyName);
//		System.out.println(map.size());
		
	}
		
	public static void readDataSet2(String N3DataSet) throws IOException {
		
		String[] data = readLines(N3DataSet);
		TreeMap<String, List<String>> mapInstanceProperties = new TreeMap<String, List<String>>();
		TreeMap<String, Integer> mapInstanceNoOfTypes = new TreeMap<String, Integer>();

		int typeCount = 0;
		for (String line : data){
			String[] s = line.split(" ");
			
			if (s.length<3) continue;
			if (s[1].contains("<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>")) typeCount++;
			
			String mapkey = s[0];
			
			List<String> lisOfPropertiesPerInstance = new ArrayList<String>();

			if (mapInstanceProperties.get(mapkey)!= null)
				lisOfPropertiesPerInstance=mapInstanceProperties.get(mapkey);

			
			if (s[1].contains("<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>"))
			{
				mapInstanceNoOfTypes.put(mapkey, mapInstanceNoOfTypes.get(mapkey)==null?1:(mapInstanceNoOfTypes.get(mapkey)+1));		
			}
			else if (map.get(s[1])!=null && !lisOfPropertiesPerInstance.contains(s[1]))
			{
				lisOfPropertiesPerInstance.add(s[1]);
			}
			mapInstanceProperties.put(mapkey,lisOfPropertiesPerInstance);
			
			}
	    System.out.println(typeCount);
		
		System.out.println(mapInstanceProperties.get("<http://dbpedia.org/resource/Akron_Art_Museum>").size());
		System.out.println(mapInstanceNoOfTypes.get("<http://dbpedia.org/resource/Akron_Art_Museum>"));
		
		
		//Third HashMap including the Property namme from the Fist MapTree and totalNumber of types that it in DBpedia***********************************************
		
		HashMap<String, Integer> noTypesPerProperties = new HashMap<String, Integer>();
		
		int ntypesDBP = 0;
		Iterator mapIt = map.entrySet().iterator();
		
		while(mapIt.hasNext())
		{
			int value = 0;
			
			Map.Entry<String, Property> entry = (Entry<String, Property>) mapIt.next();

			Iterator mapInstPropIt = mapInstanceProperties.entrySet().iterator();

			try
			{
				
				while(mapInstPropIt.hasNext())
				{
					Map.Entry<String, List<String>> instPropsEntry = (Entry<String, List<String>>) mapInstPropIt.next();
					
					try
					{
					if (instPropsEntry.getValue()!=null && instPropsEntry.getValue().contains(entry.getKey()))
					{
						value += mapInstanceNoOfTypes.get(instPropsEntry.getKey())==null?
									0:
									(mapInstanceNoOfTypes.get(instPropsEntry.getKey())==null?
											0:mapInstanceNoOfTypes.get(instPropsEntry.getKey()));
					}
					}
					catch (Exception ex)
					{
					throw ex;
					}
					//mapInstPropIt.remove();
				}
				
				noTypesPerProperties.put(entry.getKey(), value);
				
				ntypesDBP += value;
				
				//mapIt.remove();
			}
			catch (Exception exeption)
			{
				throw exeption;
			}
		}
		mapInstanceProperties.clear();
		mapInstanceNoOfTypes.clear();
		
		System.out.println(noTypesPerProperties);
		System.out.println(ntypesDBP);

		//PropertyWeighCalculation*****************************************************************************************************************************
		HashMap<String, Double> weightPerProperty = new HashMap<String, Double>();

		double propertyWeight = 0, totalWeight = 0;
		int foundProps = 0;
		Iterator propIt = map.entrySet().iterator();
		
		ArrayList<String> notFoundProps = new ArrayList<String>();
		
		while(propIt.hasNext())
		{
			
			Map.Entry<String, Property> entry = (Entry<String, Property>) propIt.next();

			try
			{
				if(noTypesPerProperties.get(entry.getKey())!=0)
				{
					foundProps++;
					propertyWeight = ((Math.log(entry.getValue().occurances)/Math.log(2))/(Math.log(noTotalOccurances)/Math.log(2)))/((Math.log(noTypesPerProperties.get(entry.getKey()))/Math.log(2))/(Math.log(ntypesDBP)/Math.log(2)));
					totalWeight += propertyWeight;
					weightPerProperty.put(entry.getKey(), propertyWeight);

				}
				else
				{
					notFoundProps.add(entry.getKey());
				}
				

			}
			catch (Exception exeption)
			{
				throw exeption;
			}
		}
		
		double averageWeight = totalWeight / foundProps;
		
		for (String prop : notFoundProps) weightPerProperty.put(prop, averageWeight);
		
		System.out.println("Property Weight= " + weightPerProperty);
		
	}

	
	//"C:\Users\rosha\OneDrive\Documents\db\museum_redNoBAL"
	//C:\Users\rosha\OneDrive\Documents\db\museum
			
		
		

		//Calculating the Weight of Prperties weight_prop_i = log2(nprop_i) / log2(nprops)   /   (log2(ntypesdbp(prop_i) / log2(ntypesdbp)),
		//
//				double propertyWeight = 0;
//				Set<Map.Entry<String, Integer>> entrySet = noTypesPerProperties.entrySet();
//				for (Entry<String, Integer> propEnty: entrySet){
//					
//					propertyWeight = (Math.log(map.get(propEnty.getKey()).occurances)/Math.log(noTotalOccurances))/(Math.log(noTypesPerProperties.get(propEnty.getKey()))/Math.log(ntypesDBP));
//					
//				}
//				System.out.println(propertyWeight);}

//				double propertyWeight = 0;
//				Set<Map.Entry<String, Property>> entrySet = map.entrySet();
//				for (Entry<String, Property> propEnty: entrySet){
//					
//					propertyWeight = (Math.log(propEnty.getValue().occurances)/Math.log(noTotalOccurances))/(Math.log(noTypesPerProperties.get(propEnty))/Math.log(ntypesDBP));
//					
//				}
//				System.out.println(propertyWeight);

		
	

	
	//Readlines Function is used to read the input file line by line 
		public static String[] readLines(String filename) throws IOException {
		    FileReader fileReader = new FileReader(filename);
		    BufferedReader bufferedReader = new BufferedReader(fileReader);
		    List<String> lines = new ArrayList<String>();
		    String line = null;
		    while ((line = bufferedReader.readLine()) != null) {
		        lines.add(line);
		    }
		    bufferedReader.close();
		    return lines.toArray(new String[lines.size()]);
		}
  
		
		//This function first check if it is out put results from before and will delete them before running the app and then read the directory for input dataset
		public static void FileExits() throws IOException {
		    
			//Reading the N3 Dataset Path
			    		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			    	    System.out.print("Enter the PATH of your First Dataset: ");
			    	        String dataPath = br.readLine();    	
			    	readDataSet1(dataPath);
			    	 BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			    	    System.out.print("Enter the PATH of your Second Dataset: ");
			    	        String dataPath2 = br2.readLine(); 
			    	        readDataSet2(dataPath2);
			    	
			    }
}
