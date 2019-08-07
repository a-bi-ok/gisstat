package request;

import java.util.ArrayList;
import java.util.Scanner;

import parser.GeologyParser;
import parser.Parser;

public class GeologyAttrRequest{	
	private String[] selectedAttributeString = new String[1];
	private static int selectedValueInt;
	private static int intElement;
	

	protected static Scanner attributeInput = new Scanner(System.in);

	/**
	 * @AttributeRequest() Empty Constructor 
	 */
	public GeologyAttrRequest() {	
	}
	/**
	 * @uploadReport() Runs the request and validation process.
	 * Returns an Int of validated input file names.
	 */
	
	public int getIntElement() {
		return intElement;
	}
	public int getSelectedValueInt() {
		return selectedValueInt;
	}
		
	public int checkValueEntry(ArrayList<String> headerItems) {
			while(true) {
				System.out.println("\nENTER NUMBER TO SELECT VALUE FIELD:");			
				try {
					int value = Integer.parseInt(attributeInput.next());
					if(value > 0 && value< headerItems.size()+1) {
						System.out.println("You selected: " + headerItems.get(value-1) + " for your value field");
						selectedValueInt += value-1;
						return selectedValueInt;
						}
					else {
						System.out.println("invalid entry, try again!!");
						continue;
					}
					} catch (Exception e) {
						System.out.println("invalid entry, try again!!");	
						 }
			}
	}
	/**
	 * @checkKeyEntry() validates user selection for our of bounds errors and invalid entries
	 * allows user retry attempts until right name or number entry matches printed header list
	 * @param - is the header items list and returns user entry as list. 
	 */
	public String[] checkKeyEntry(ArrayList<String> headerItems) {
		String[] userEntry = getData();
		ArrayList<Integer> numEntry = listToArray(NumberEntry(userEntry));
		
		while(isvalidEntry(userEntry)) {
			if(numEntry.get(0)== -1 && !headerItems.contains(userEntry[0])){ 
				System.out.println("\nReview the LIST NAMES and try again!!");
				userEntry = getData();
				numEntry = listToArray(NumberEntry(userEntry));
					}		
			else if (numEntry.get(0)> headerItems.size() || numEntry.get(0)<-1 || numEntry.get(0)== 0){
				System.out.println("\nReview the LIST NUMBERS and try again!!");
				userEntry = getData();
				numEntry = listToArray(NumberEntry(userEntry));
			}	
			else if((numEntry.get(0)== -1) && headerItems.contains(userEntry[0])){ 
				System.out.println(userEntry[0] + " is a valid selection");
				intElement = headerItems.indexOf(userEntry[0]);
				break;
					}
			else if (numEntry.get(0)< headerItems.size()+1 && numEntry.get(0)> 0)
			{
				System.out.println(headerItems.get(numEntry.get(0)-1)+ " is a valid selection");
				intElement = headerItems.indexOf(headerItems.get(numEntry.get(0)-1));
				break;
				} 
		} 
			return userEntry;
	};
	/**
	 * @getData() Allows user to enter file names
	 * returns a list of entered names.
	 * @ReqList - Mimics a HASHMAP, skips the process
	 */
	public String[] getData() {
		System.out.println("\nENTER ITEM NAME OR DESIRED ITEM NUMBER: ");
		for(int i =0; i<selectedAttributeString.length;i++) {	
			selectedAttributeString[i] = attributeInput.next().toUpperCase();
			}
		System.out.println("You ENTERED: " + selectedAttributeString[0]);

		return selectedAttributeString;
	}
	
	/**
	 * @isValidEntry() Validates user entry list created in @getData()
	 * returns a boolean 
	 * @param - list created from @getData()
	 */
	public Integer[] NumberEntry(String[] userEntry) {
		Integer[] selectedAttributeInteger = {-1};
			try {
				for(int i=0;i <userEntry.length;i++) {
					selectedAttributeInteger[i]= Integer.parseInt(userEntry[i]);
				}	
				}catch(NumberFormatException e) {
				}
			return selectedAttributeInteger;
	};
	
	public boolean isvalidEntry(String[] userEntry) {
		for(String element: userEntry) {
			if(element != null) {
		}	
		}
		return true;
	}
	/**
	 * @uploadReport() Creates text report to console with information
	 * from @getData()
	 * returns a void 
	 * @param - list created from @getData()
	 */
	public static <T> ArrayList<T> listToArray(T[] list){
		ArrayList<T> genericArrayList = new ArrayList<T>();
		for(T element: list) {
			genericArrayList.add(element);
		}
		return genericArrayList;	
	}

	
}
