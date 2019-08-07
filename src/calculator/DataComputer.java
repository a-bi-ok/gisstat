package calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import gisstatHome.RunGISSTAT;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.evaluation.output.prediction.PlainText;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class DataComputer extends Calculator {
	
    private static String tempLink = System.getProperty("user.dir");
    private static String csvFile = "TEMP.csv";
    private static String arffFile = "TEMP.arff"; 
    private static String arffFileTest = "TEMPtest.arff"; 

    public static String csvLink = tempLink +"/"+ csvFile;
    public static String arffLink = tempLink +"/"+ arffFile;
	private Formatter sysReport = null;

    public static String arffLinkTest = tempLink +"/"+ arffFileTest;
      
	public static void startWek() {
	    System.out.println("Running Machine Training");		
		try {
			BufferedReader geoReader1 = null;
			BufferedReader geoReader2 = null;

			try {
				geoReader1 = new BufferedReader(new FileReader(new File(arffFile)));
				geoReader2 = new BufferedReader(new FileReader(new File(arffFileTest)));

//				geoReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Instances train = new Instances(geoReader1);
			Instances test = new Instances(geoReader2);
			StringBuffer buffer = new StringBuffer();
			PlainText plainText = new PlainText();
			plainText.setHeader(train);
			plainText.setBuffer(buffer);
		
			train.setClassIndex(train.numAttributes()-1);
			test.setClassIndex(train.numAttributes()-1);
			
//			Classifier cls = new Logistic();
			Classifier cls = new MultilayerPerceptron();

			cls.buildClassifier(train);
			
			
			
			// evaluate classifier and print some statistics
			Evaluation eval = new Evaluation(train);
			
		    System.out.println("Evaluating Test Data");
		    
			System.out.println("\nMODEL RESULTS");
			System.out.println(RunGISSTAT.lineBreak);
			System.out.println(cls.toString());
			
			eval.evaluateModel(cls, test, plainText);
			System.out.println(eval.toSummaryString("\nTEST DATA PREDICTED RESULTS"
					+ "\n========================================================================================================"
					+ "==============================================================================", false));
		    System.out.format("%10s %10s %5s %10s%n", "INST#", "ACTUAL", "ERROR(PRED.)", "PREDICTION");
			System.out.println(buffer.toString());
		    System.out.println("Machine Prediction Completed");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
	public void csvBuilder(HashMap<String, Double> geologyAreaPercentages, Map<String, Integer> structureSummary, Map<String, String> occurrenceSummary) {
		
			try {
				 sysReport = new Formatter(csvFile);
				 System.out.println("ROCKTYPE   "+"          SIZE(%)   "+"  FAULT_COUNT "+"  MINERAL");
				 System.out.println("--------------------------------------------------------------------------------------------------------------------------"
							+ "---------------------------------------------------------");	
				 sysReport.format("%s, %s, %s, %s%n", "rocktype","size", "strCount", "occurrence");						
				 for(String key: geologyAreaPercentages.keySet()) {
					    String rocktype = key.replaceAll("\\s+","");
					    Double size = geologyAreaPercentages.get(key);
					    String occurrence = occurrenceSummary.get(key);
				        Integer strCount = structureSummary.get(key);
					    if(strCount==null) {
					    	strCount=0;
					    }
						sysReport.format("%s, %f, %d, %s%n", rocktype,size, strCount, occurrence );						
						System.out.format("%-20s %f %10d %10s%n", rocktype, size, strCount, occurrence);
					 }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println("Loaded Data Summary Report.");
		    System.out.println("temp csv file generated:"+tempLink);
			sysReport.close();
	}
	
	public static void arffBuilder() {
	    System.out.println("--------------------------------------------------------------------------------------------------------------------------"
				+ "---------------------------------------------------------");	
	    System.out.println("\nGenerating ARFF file.");
		try {
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File(csvLink));
			Instances data = loader.getDataSet();
			
			//saving arff file
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			
			saver.setFile(new File(arffLink));
			saver.writeBatch();
		    System.out.println("ARFF file successfully created.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
	@Override
	public void PercentCalReport(HashMap<String, Double> importItems) {
		// TODO Auto-generated method stub
		
	}
	
}

	

