package request;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Scanner;

public class DataRequest extends Request {
	
	private String[] expectedFiles = new String[3];
	private static Scanner fileInput = new Scanner(System.in);
	private static String[] ReqList = {"GEOLOGY", "STRUCTURE", "OCCURRENCE"};

	public DataRequest() {	
	}
	/**
	 * @checkEntry() Runs the request and validation process.
	 * Returns a String[]-list of validated input file names.
	 */
	public String[] checkEntry() {
		String[] userEntry = getData();
		displayEntry(userEntry);
		while (!isvalidEntry(userEntry)) {
			System.out.println("\nValidate files and try again\n");
			userEntry = getData();
			displayEntry(userEntry);
			}
		uploadReport(userEntry);
		return userEntry;
	}
	public static String[] getReqList() {
		return ReqList;
	}
	/**
	 * @getData() Allows user to enter file names
	 * returns a list of entered names.
	 * @ReqList - Mimics a HASHMAP, skips the process
	 */
	public String[] getData() {
		String[] ReqList = {"Geology", "Structure", "Occurrence"};
		System.out.println("ENTER FILE NAMES\n");
		for(int i=0; i<ReqList.length;i++) {
			System.out.println(ReqList[i].toUpperCase() + " FILE===>: ");
			
			String fileEntered = fileInput.next().toLowerCase();
			
			if(fileEntered.endsWith(".csv")) {
				expectedFiles[i] = fileEntered;
			}else {
				expectedFiles[i] = fileEntered.concat(".csv");
				}
			}
//		fileInput.close();
		return expectedFiles;
	}
	
	/**
	 * @isValidEntry() Validates user entry list created in @getData()
	 * returns a boolean 
	 * @param - list created from @getData()
	 */
	public boolean isvalidEntry(String[] userEntry) {
		ArrayList<File> fileNotFound = new ArrayList<>();
		for(int i=0;i<userEntry.length; i++)
		{
			File link = new File(userEntry[i]);	
			if(link.exists()) {
				continue;
			} else {
				fileNotFound.add(link);
			}
		}
		if(fileNotFound.isEmpty()) {
			return true;
		} else {
			for(File failedLinks: fileNotFound) {
			System.out.println();
			System.out.println(failedLinks + " file were not found");
		}
			return false;
		}
	}		
	/**
	 * @displayEntry() Returns items in the list from @getData()
	 * @param - list created from @getData()
	 */
	public void displayEntry(String[] userEntry) {
		String[] ReqList = {"Geology", "Structure", "Occurrence"};
		for(int i=0; i<userEntry.length;i++) {
			System.out.println();
			System.out.println("For REFERENCE " + ReqList[i].toUpperCase() +", Entered File is: "+ userEntry[i]);
			}
		}	
	/**
	 * @uploadReport() Creates text report to console with information
	 * from @getData()
	 * returns a void 
	 * @param - list created from @getData()
	 */
	public void uploadReport(String[] userEntry) {				
		Formatter sysReport = null;
			try {
				 sysReport = new Formatter("GISSTATReport.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sysReport.format("==============================GISSTAT SYSTEM REPORT===================================\n");
	        sysReport.format("@"+System.getProperty("user.dir"));
			sysReport.format("Date: %tD, Local time: %tT%n", Calendar.getInstance(), Calendar.getInstance());
			for(int i=0; i<userEntry.length;i++) {
				sysReport.format("GISSTAT Successfully loaded: %s%n", userEntry[i]);
				}
			sysReport.close();
	}		
};



