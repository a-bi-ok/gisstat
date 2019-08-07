package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
	
	private static String link;
	
	public Parser() {
	}

	public static String getLink() {
		return link;
	}


	public static void setLink(String link) {
		Parser.link = link;
	}



	public String[] getHeader(String link)  {
		Scanner geoReader = null;
		String[] header = null;
		try {
			geoReader = new Scanner(new File(link));
			header = geoReader.nextLine().split(",");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    geoReader.close();
		return header;
	}
	
	
	public <T> void printHeader(T[] headers) {
		int i=0;
		for(T headerItem: headers) {
			i++;
			System.out.format("%d. %s%n", i, headerItem);
		} 
    }
	
};
