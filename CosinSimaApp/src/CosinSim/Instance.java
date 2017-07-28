package CosinSim;

import java.io.Serializable;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author kenza Kellou-Menouer
 */

public class Instance implements Serializable {
	public int instanceIds;
    public String  instanceNames;
//    public String rdfTypes ;
    public ArrayList<String> typeNames;
    public ArrayList<String> propertyNames;
   // public ArrayList<AbstractMap.SimpleEntry<String, String>> typeValues;
    //arralist list properties. 
    
    public  Instance(int instanceIds, String instanceNames, String typeNames, String propertyName) {
    	this.instanceIds=instanceIds;
    	this.instanceNames=instanceNames;
    	//this.typeValues = new ArrayList<AbstractMap.SimpleEntry<String, String>>();
    	//this.typeValues.add(new AbstractMap.SimpleEntry<String, String>(rdfTypes, typeNames));
    	//this.rdfTypes=rdfTypes;
    	
    	this.typeNames = new ArrayList<String>();
    	this.propertyNames = new ArrayList<String>();
    	if (typeNames != "") this.typeNames.add(typeNames);
    	if (propertyName != "") this.propertyNames.add(propertyName);
    	//this.typeNames=typeNames;
		
	}
    
   

	public int getIds(){
    	return instanceIds;
    }

    public String getInstanceNames(){
    	return instanceNames;
    }
    
//    public String getRdfTypes(){
//    	return rdfTypes;
//    }
//    
//    public String getTypeNames(){
//    	return typeNames;
//    }
    
    }
    
   
    
    

