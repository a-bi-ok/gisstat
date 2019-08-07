package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import request.GeologyAttrRequest;


public class GeologyParser extends Parser {
	
	private static ArrayList<String> availableGeologyTypes = new ArrayList<>();
	private static List<List<String>> masterGeologyItems = new ArrayList<>();
	private static double sumOfAreas;
	private List<String> geologyItems;
	private ArrayList<Double> areaItems = new ArrayList<>();
	private double areaSumValue;
	protected List<List<String>> filteredList;
	private String element;
	protected String inputLine;
	protected BufferedReader geoReader;
	private ArrayList<Double> allAreaItems = new ArrayList<>();
	private HashMap<String, Double> geologyAreaItems = new HashMap<String, Double>();
	
	GeologyAttrRequest attRequest = new GeologyAttrRequest();

	public GeologyParser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @csvParser() implements class methods to import, parse, stream and compute all data count
	 * @param - accepts csv name and returns Map. 
	 */
	public HashMap<String, Double> csvParser(String link) throws IOException {
	    System.out.println("Importing "+ link + " file.");
		geoReader = new BufferedReader(new FileReader(new File(link)));
		geoReader.readLine();
			// TODO Auto-generated catch block
		while ((inputLine = geoReader.readLine()) != null ) {	
			geologyItems = Arrays.asList(inputLine.split(","));
			if(!masterGeologyItems.contains(geologyItems)) {
				masterGeologyItems.add(geologyItems);
			}
			availableGeologyTypes=csvTypes(geologyItems);
		}
		for(String elem:availableGeologyTypes) {
			element=elem;
			filteredList = csvParserFilter(masterGeologyItems);
			areaSumValue+=areaSum(filteredList);
			geologyAreaItems.put(element, new Double(areaSumValue));
			allAreaItems.add(areaSumValue);
		}
         sumOfAreas = allAreaItems
				.stream()
				.reduce((double) 0, (x,y) -> x+y);
		geoReader.close();
	return geologyAreaItems; 	
	}
	
	
	/**
	 * @csvTypes()filters data for occurrences
	 * @param - takes each parsed list from csv as param 
	 * and returns a list of special items 
	 */
	public ArrayList<String> csvTypes(List<String> geologyItems){
		if(!availableGeologyTypes.contains(geologyItems.get(attRequest.getIntElement()))) {
					availableGeologyTypes.add(geologyItems.get(attRequest.getIntElement()));
					}
		return availableGeologyTypes;	
		}
	
	/**
	 * @getAvailableGeologyTypes() getter
	 */
//	public ArrayList<String> getAvailableGeologyTypes() {
//		return availableGeologyTypes;
//	}

	public static ArrayList<String> getAvailableGeologyTypes() {
		return availableGeologyTypes;
	}

	public static List<List<String>> getMasterGeologyItems() {
		return masterGeologyItems;
	}

	
	/**
	 * @getSumOfAreas() getter
	 */
	public double getSumOfAreas() {
		return sumOfAreas;
	}
	
	/**
	 * @csvParserFilter()filters csv file based on elements in csvTypes
	 * @param - takes a list of list as param and 
	 * returns a list of list for every element
	 */
	public List<List<String>> csvParserFilter(List<List<String>> masterGeologyItems) {

			filteredList = masterGeologyItems
					.stream()
					.filter(s -> s.get(attRequest.getIntElement()).equals(element))
					.collect(Collectors.toList());
			return filteredList;
}
	/**
	 * @areaSum() streams and parses to double field with user value selection
	 * @param - takes a list of list of user element groupings 
	 * as param and returns the sum of each element group
	 * returns 
	 */
	public double areaSum(List<List<String>> filteredList) {
		
		for(List<String> listedItem:filteredList) {	
			try {
				Double areaValue=Double.parseDouble(listedItem.get(attRequest.getSelectedValueInt()));
				areaItems.add(areaValue);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		areaSumValue = areaItems
							.stream()
							.reduce((double) 0, (x,y) -> x+y);	
	return (double)	Math.round(areaSumValue*100)/100;
	
	}
	@Override
	public String[] getHeader(String link) {
		// TODO Auto-generated method stub
		return super.getHeader(link);
	}
	@Override
	public <T> void printHeader(T[] headers) {
		// TODO Auto-generated method stub
		super.printHeader(headers);
	}	
	
	public void printMap(Map<String, Double> dictionary) {
    	for (String key : dictionary.keySet()) 
             System.out.printf("%s: %d%n", key, dictionary.get(key));
    	}	
}	
       