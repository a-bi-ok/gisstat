package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import request.StructureAttrRequest;


public class StructureParser extends GeologyParser {
		
	private Map<String, Integer> structureMapItems = new HashMap<String, Integer>();
	private static ArrayList<String> distinctTypes= new ArrayList<>();
	private static List<List<String>> masterStructureItems = new ArrayList<>();
	private List<String> structureItems;
	private String element;
	StructureAttrRequest strAttrReq = new StructureAttrRequest();
	BufferedReader strReader;	
	/**
	 * 
	 */
	public StructureParser() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<String> getDistinctTypes(List<String> record, int pickedNumber){
		if(!distinctTypes.contains(record.get(pickedNumber)) && record.get(pickedNumber)!= null) {
			distinctTypes.add(record.get(pickedNumber));
					}
		return distinctTypes;	
		}
	//Filtering items in user selected fields into separate dictionaries and later merging the 2 dictionaries.
	public Map<String, Integer> csvRecordParser(String strLink)  {
	    System.out.println("\nImporting "+ strLink + " file");
		HashMap<String, Integer> strMapItems1 = new HashMap<String, Integer>();
		HashMap<String, Integer> strMapItems2= new HashMap<String, Integer>();
		int number1 = (strAttrReq.getFirstSelectedValues()-1);
		int number2 = (strAttrReq.getSecondSelectedValues()-1);
		ArrayList<String> distinct1 = null;
		ArrayList<String> distinct2 = null;
		
		try {
			strReader = new BufferedReader(new FileReader(new File(strLink)));
			strReader.readLine();
				// TODO Auto-generated catch block
			while ((inputLine = strReader.readLine()) != null ) {	
				structureItems = Arrays.asList(inputLine.split(","));
				if(!masterStructureItems.contains(structureItems)) {
					masterStructureItems.add(structureItems);

				}
				distinct1 = getDistinctTypes(structureItems,number1);
				distinct2 = getDistinctTypes(structureItems,number2);
			}	
			for(String elem1:distinct1) {
				element=elem1;
				filteredList = getFilteredList(masterStructureItems,number1);
				strMapItems1.put(element, filteredList.size());
			}
			for(String elem2:distinct2) {
				element=elem2;
				filteredList = getFilteredList(masterStructureItems,number2);
				strMapItems2.put(element, filteredList.size());		
			}
			System.out.println("System Processing....................");
		    System.out.println("Started Processing Structure Data.");
			structureMapItems = Stream.of(strMapItems1, strMapItems2)
			        .map(Map::entrySet)          // converts each map into an entry set
			        .flatMap(Collection::stream) // converts each set into an entry stream, then "concatenates" it in place of the original set
			        .collect(
			            Collectors.toMap(        // collects into a map
			                Map.Entry::getKey,   // where each entry is based
			                Map.Entry::getValue, // on the entries in the stream
			                Integer::sum));        // such that if a value already exist for
			                                     // a given key, the sum of the old
   	                                     // and new value is taken		            
		    System.out.println("Structure Data Processing Completed.\n");      		
			strReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return structureMapItems;
	}	
	
	public static List<List<String>> getMasterStructureItems() {
		return masterStructureItems;
	}
	/**
	 * @csvParserFilter()filters csv file based on elements in csvTypes
	 * @param - takes a list of list as param and 
	 * returns a list of list for every element
	 */
	public List<List<String>> getFilteredList(List<List<String>> masterGeologyItems, int number) {
			filteredList = masterGeologyItems
					.stream()
					.filter(s -> s.get(number).equals(element))
					.collect(Collectors.toList());
			return filteredList;
	}
}
	