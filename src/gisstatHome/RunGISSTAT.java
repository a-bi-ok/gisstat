package gisstatHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import calculator.PercentageCalculator;
import calculator.DataComputer;
import parser.GeologyParser;
import parser.OccurrenceParser;
import parser.Parser;
import parser.StructureParser;
import request.GeologyAttrRequest;
import request.OccurrenceAttrRequest;
import request.StructureAttrRequest;
import request.DataRequest;
import table.GISSTATDBase;
import table.GeologyTable;
import weka.core.converters.CSVLoader;

public class RunGISSTAT {	
	List<String> headerList = new ArrayList<String>();
	static String welcome = "WELCOME TO THE GISSTAT APPLICATION "
			+ "\nGISSTAT is a simple Geostatical"
			+ "\nutilizing Machine Learning to make predictions.\n";
	static String setup = "\nGISSTAT'S 5 SIMPLE STEPS"
			+ "\n1.Prepare your CSV data."
			+ "\n2.Upload required data."
			+ "\n3.Select desired fields."
			+ "\n4.Repeat Steps 1-3 for Test Data."
			+ "\n5.Run Model and View Prediction.\n";
	public static String ref = "\nREFERENCE DATA\n";
	public static String lineBreak = "--------------------------------------------------------------------------------------------------------------------------"
			+ "--------------------------\n";				
	public static void slowPrint(String message, long millisperChar) {
		for(int i=0; i<message.length(); i++) {
			System.out.print(message.charAt(i));
			try {
				Thread.sleep(millisperChar);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
				
		slowPrint(welcome, 10);
		slowPrint(setup, 10);
		slowPrint(ref, 5);
		slowPrint(lineBreak, 1);

		DataRequest dataRequest = new DataRequest();
		String[] validEntry = dataRequest.checkEntry();
		System.out.println("\n***System Report Generated to Directory***\n");
        System.out.println("@"+System.getProperty("user.dir"));
		
		GeologyParser geoData = new GeologyParser();
		GeologyAttrRequest attRequest = new GeologyAttrRequest();
//		 
		Parser.setLink(validEntry[0]);
        
		String[] headers = geoData.getHeader(Parser.getLink());
//		
		System.out.println("\nHEADERS FOR REFERENCE " + DataRequest.getReqList()[0]+ " DATA");
		slowPrint(lineBreak, 0);

		geoData.printHeader(headers);
		slowPrint(lineBreak, 0);

		attRequest.checkKeyEntry(GeologyAttrRequest.listToArray(headers));
		attRequest.checkValueEntry(GeologyAttrRequest.listToArray(headers));		
//						
        PercentageCalculator percentCal = new PercentageCalculator();        
        percentCal.PercentCalReport(percentCal.PercentCalculator(Parser.getLink()));
        HashMap<String, Double> geologySummary = percentCal.PercentCalculator(Parser.getLink());     	
		
//		Thread geoDataThread = new Thread(new Runnable(){
//			public void run()
//			{		
//				System.out.println("**END OF CURRENT THREAD**\n");
//			}
//			});
//		GISSTATDBase gApp  = new GISSTATDBase();
//		GeologyTable geoTable  = new GeologyTable();
//		GeologyTable.createConnection();
//		geoTable.dropTable("refgeology");
		
		StructureParser strParser = new StructureParser();
		Parser.setLink(validEntry[1]);
		String[] strHeaders = strParser.getHeader(Parser.getLink());
		System.out.println("\nHEADERS FOR REFERENCE " 
		+ DataRequest.getReqList()[1]+ " DATA");
		slowPrint(lineBreak, 0);
		strParser.printHeader(strHeaders);
		slowPrint(lineBreak, 0);
		StructureAttrRequest attReq = new StructureAttrRequest();
		attReq.FirstEntry(GeologyAttrRequest.listToArray(strHeaders)); 		// getting useful structure attributes
		attReq.SecondeEntry(GeologyAttrRequest.listToArray(strHeaders));		// getting useful structure attributes		
					
		Map<String, Integer> structureSummary=strParser.csvRecordParser(Parser.getLink());
		
		OccurrenceParser occParser = new OccurrenceParser();
		Parser.setLink(validEntry[2]);
		String[] occHeaders = occParser.getHeader(Parser.getLink());
		System.out.println("\nHEADERS FOR REFERENCE " 
		+ DataRequest.getReqList()[2]+ " DATA");
		slowPrint(lineBreak, 0);

		strParser.printHeader(occHeaders);
		slowPrint(lineBreak, 0);
		OccurrenceAttrRequest occAttReq = new OccurrenceAttrRequest();
		occAttReq.FirstEntry(GeologyAttrRequest.listToArray(strHeaders)); 		// getting useful structure attributes
		occAttReq.SecondeEntry(GeologyAttrRequest.listToArray(strHeaders));		// getting useful structure attributes	
					
		Map<String, String> occurrenceSummary=occParser.csvRecordParser(Parser.getLink());
		Thread proThread = new Thread(new Runnable(){
			public void run()
					{
				System.out.println("STARTING DATA PROCESSING THREAD");
				slowPrint(lineBreak, 10);

				try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					DataComputer dataCom = new DataComputer();
					dataCom.csvBuilder(geologySummary, structureSummary, occurrenceSummary);
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					DataComputer.arffBuilder();
					DataComputer.startWek();
					
					System.out.println("*********************************************************************END OF CURRENT THREAD********************************************"
							+ "*********************************\n");
				}
				});
		
		proThread.start();			    
		
	};
		
};
					

