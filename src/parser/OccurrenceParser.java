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

import request.OccurrenceAttrRequest;


public class OccurrenceParser extends GeologyParser {
		
	private Map<String, String> occurrenceMapItems = new HashMap<String, String>();
	private static ArrayList<String> distinctTypes= new ArrayList<>();
	private static List<List<String>> masterOccurrenceItems = new ArrayList<>();
	private List<String> occurrenceItems;
	private String element;
	OccurrenceAttrRequest occAttReq = new OccurrenceAttrRequest();
	BufferedReader occReader;	
	/**
	 * 
	 */
	public OccurrenceParser() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<String> getDistinctTypes(List<String> record, int pickedNumber){
		if(!distinctTypes.contains(record.get(pickedNumber)) && record.get(pickedNumber)!= null) {
			distinctTypes.add(record.get(pickedNumber));
					}
		return distinctTypes;	
		}
	//Filtering items in user selected fields into separate dictionaries and later merging the 2 dictionaries.
	public Map<String, String> csvRecordParser(String strLink)  {
	    System.out.println("\nImporting "+ strLink + " file");
		HashMap<String, String> strMapItems = new HashMap<String, String>();
//		HashMap<String, Integer> strMapItems2= new HashMap<String, Integer>();
		int number1 = (occAttReq.getFirstSelectedValues()-1);
		int number2 = (occAttReq.getSecondSelectedValues()-1);

		ArrayList<String> distinct = null;
//		ArrayList<String> distinct2 = null;
		
		try {
			occReader = new BufferedReader(new FileReader(new File(strLink)));
			occReader.readLine();
				// TODO Auto-generated catch block
			while ((inputLine = occReader.readLine()) != null ) {	
				occurrenceItems = Arrays.asList(inputLine.split(","));
				if(!masterOccurrenceItems.contains(occurrenceItems)) {
					masterOccurrenceItems.add(occurrenceItems);

				}
				distinct = getDistinctTypes(occurrenceItems,number2);
			}	
			System.out.println("System Processing....................");
		    System.out.println("Started Processing" +  strLink + "file Data.");
			for(int i=0;i<distinct.size(); i++) {
				element=distinct.get(i);
				filteredList = getFilteredList(masterOccurrenceItems,number2);
				String mineral = filteredList.get(i).get(number1);
				strMapItems.put(element, mineral);
			}
			
			for (String key : strMapItems.keySet()) {  
				if(strMapItems.get(key).contains("Au")){
					occurrenceMapItems.put(key, "Au");
				}
				else{
					occurrenceMapItems.put(key, null);
				}
			}
		    System.out.println(strLink + " Data Processing Completed.\n");      		
			occReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return occurrenceMapItems;
	}			
	public static List<List<String>> getMasterOccurrenceItems() {
		return masterOccurrenceItems;
	}

	/**
	 * @csvParserFilter()filters csv file based on elements in csvTypes
	 * @param - takes a list of list as param and 
	 * returns a list of list for every element
	 */
	public List<List<String>> getFilteredList(List<List<String>> masterOccurrenceItems, int number2) {
			filteredList = masterOccurrenceItems
					.stream()
					.filter(s -> s.get(number2).equals(element))
					.collect(Collectors.toList());
			return filteredList;
	}
}
	