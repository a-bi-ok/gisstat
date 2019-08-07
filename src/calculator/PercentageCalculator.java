package calculator;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import parser.GeologyParser;

public class PercentageCalculator extends Calculator{
	GeologyParser geoData = new GeologyParser();
	
	/**
	 * @constructor
	 */

	public PercentageCalculator() {		
	}
	/**
	 * @param link
	 * @return
	 */
	public HashMap<String, Double> PercentCalculator(String link) {

		HashMap<String, Double> geologyAreaPercentages = null;
		try {
			geologyAreaPercentages = geoData.csvParser(link);
			
			for(String areaItem: geologyAreaPercentages.keySet()) {
				geologyAreaPercentages.computeIfPresent(areaItem, 
						(Key,Value) ->((Value*100)/geoData
								.getSumOfAreas()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return geologyAreaPercentages;	
	}		
	/**
	 * @param geologyAreaPercentages
	 */
	public void PercentCalReport(HashMap<String, Double> geologyAreaPercentages) {
		System.out.println("System Processing....................");
	    System.out.println("Started Processing Geology Data.");
			 try(DataOutputStream sysOutReporter= new DataOutputStream(
					 new FileOutputStream("DataProcessingReport.dat"));)
			{
				 for(String areaItem: geologyAreaPercentages.keySet()) {			 
					 sysOutReporter.writeUTF(areaItem);
					 sysOutReporter.writeDouble(geologyAreaPercentages.get(areaItem));
				 }
			}
				 catch(IOException ex){
						ex.printStackTrace();
					}
		     System.out.println("Geology Data Processing Completed.\n");

//			  try(DataInputStream sysInReporter= new DataInputStream(
//					  
//					  new FileInputStream("DataProcessingReport.dat"));)
//				{  
//				     System.out.println("Started Processing Geology Data");
//
//					 System.out.println("\nGEOLOGY DATA PERCENTILE SUMMARY");
//					 System.out.println("--------------------------------------------------------------------------------------------------------------------------"
//								+ "---------------------------------------------------------");					 
//					 for(int i=0; i<geologyAreaPercentages.size();i++) {
//						 System.out.printf("%-20s : %f%n", sysInReporter.readUTF(), sysInReporter.readDouble());
//					 }
//				}
//				catch(IOException ex){
////					ex.printStackTrace();
//				}		
		     }
}