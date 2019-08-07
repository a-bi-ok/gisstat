package requestTest;

import java.io.File;
import java.util.ArrayList;

public class DataRequestTest_NONJU {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] userEntry = {"NV_refgeology.csv", "NV_refstructure.csv", "NV_refoccurrence.csv"};
		String[] userEntry1 = {"geology.csv", "NV_refstructure.csv", "NV_refoccurrence.csv"};
		String[] userEntry2= {"geology.csv", "structure.csv", "NV_refoccurrence.csv"};
		String[] userEntry3 = {"NV_refgeology.csv", "structure.csv", "NV_refoccurrence.csv"};
		String[] userEntry4= {"NV_refgeology.csv", "NV_refstructure.csv", "occurrence.csv"};
		String[] userEntry5 = {"NV_refgeology.csv", "structure.csv", "occurrence.csv"};
		
		System.out.println("MAIN TEST SHOULD PASS: " +isvalidEntry(userEntry));
		System.out.println("TEST 1 SHOULD FAIL: "+isvalidEntry(userEntry1));
		System.out.println("TEST 2 SHOULD FAIL: "+isvalidEntry(userEntry2));
		System.out.println("TEST 3 SHOULD FAIL: "+ isvalidEntry(userEntry3));
		System.out.println("TEST 4 SHOULD FAIL: "+ isvalidEntry(userEntry4));
		System.out.println("TEST 5 SHOULD FAIL: "+ isvalidEntry(userEntry5));


		}
		public static boolean isvalidEntry(String[] userEntry) {
			ArrayList<File> fileNotFound = new ArrayList<>();

			for(int i=0;i<userEntry.length; i++)
			{
				File link = new File(userEntry[i]);	
				if(link.exists()) {
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
		

}
