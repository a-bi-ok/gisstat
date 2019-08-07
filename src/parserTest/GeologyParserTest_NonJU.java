package parserTest;

import java.io.IOException;
import java.util.ArrayList;

import calculator.PercentageCalculator;
import parser.GeologyParser;
import request.GeologyAttrRequest;

public class GeologyParserTest_NonJU {
	
	public static void main(String[] args) throws IOException {
		
		String link = "NV_refgeology.csv";
		
		//@Test GeologyParser()
		GeologyParser geodata = new GeologyParser();
		
		//@Test printMap()
		System.out.println("==========printMap() Test===========");
//		geodata.printMap();
				System.out.println("Header Length: " + geodata.getHeader(link).length);
		
		//@Test getHeader(link)
		System.out.println("==========getHeader() Test===========");
		String[] geoHeader = geodata.getHeader(link);
		for(int i=0; i<geoHeader.length; i++) {;
			System.out.printf("%d. %s%n", i+1, geoHeader[i]);
		}
		
        System.out.println(System.getProperty("user.dir"));
        
        System.out.println(geodata.csvParser(link));     
        PercentageCalculator percentCal = new PercentageCalculator();
        percentCal.PercentCalculator(link);    
        
		GeologyAttrRequest attRequest = new GeologyAttrRequest();

        
//        percentCal.PercentCalReport(percentCal.PercentCalculator(link));
        

        
	}
	
	
}