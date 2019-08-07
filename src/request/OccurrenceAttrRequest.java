package request;

import java.util.ArrayList;

public class OccurrenceAttrRequest extends GeologyAttrRequest{
	
 	private static int firstSelectedValues;
	private static int secondSelectedValues;	
	/**
	 * 
	 */
	public OccurrenceAttrRequest() {
	}
	
	public int getFirstSelectedValues() {
		return firstSelectedValues;
	}

	public int getSecondSelectedValues() {
		return secondSelectedValues;
	}

	public int FirstEntry(ArrayList<String> headerItems) {
		System.out.println("\nENTER 2 NUMBERS FOR DESIRED FILEDS:");			
		while(true) {
			try {
					System.out.println("\nENTER FIRST NUMBER FOR MINERAL DESPOSIT INFORMATION:");
					int value = Integer.parseInt(attributeInput.next());
					if(value > 0 && value < headerItems.size()+1) {
						System.out.println("You selected: " + headerItems.get(value-1) + " for your first value field");
						firstSelectedValues= value;
						break;
						}
					else {
					System.out.println("invalid entry, try again!!");
						}				
				} catch (Exception e) {
					System.out.println("You did not enter a number, try again!!");
				}		
		}
		return firstSelectedValues;

}
	public int SecondeEntry(ArrayList<String> headerItems) {
		while(true) {
			try {
				System.out.println("\nENTER SECOND NUMBER FOR ASSOCIATED ROCKTYPE INFO:");
					int value = Integer.parseInt(attributeInput.next());
					if(value > 0 && value < headerItems.size()+1 && value!=firstSelectedValues) {
						System.out.println("You selected: " + headerItems.get(value-1) + " for your second value field");
					    secondSelectedValues= value;
						break;
						}
					else {
					System.out.println("invalid entry, try again!!");
						}
				
				} catch (Exception e) {
					System.out.println("You did not enter a number, try again!!");
				}			
		}
		return secondSelectedValues;

}

}
