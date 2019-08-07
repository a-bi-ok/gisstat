package table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import parser.GeologyParser;

import java.sql.ResultSetMetaData;



//Driver name org.apache.derby.jdbc.EmbeddedDriver
//Database connection URL jdbc:derby:myDB;create=true;user=me;password=mine


public class StrcutureTable extends GISSTATDBase {
    
    private static String tableName = "refstructure";
    // jdbc Connection
    private static Connection conn = null;
	private static List<String> strlithoTypes = new ArrayList<String>();
	
	
	
	 
	    
//	    public static void main(String[] args)
//	    {
//	        createConnection();
//	        insertRestaurants(5, "LaVals", "Berkeley");
//	        selectRestaurants();
//	        shutdown();
//	    }
	    
	/**
	 * 
	 */
	public StrcutureTable() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public static void createConnection()
    {
        System.out.println("\nGISSTATApp starting in " + framework + " mode");

        try
        {
            Class.forName(driver).newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
   	 System.out.println("Connected to " + dbName + " & " + tableName+ " table");

    }
	
	public void createTable() {	
		 try
	        {
	    		conn.setAutoCommit(false);

	            System.out.println("Connected to and created database " + dbName);

	            conn.setAutoCommit(false);
	            s = conn.createStatement();
	            statements.add(s);
	                       
//	             We create a table...
	            s.execute("create table refstructure "
	            		+ "(jid int not null,"
	            		+ "zone varchar(30) not null,"
	            		+ "rocktype1 varchar(30) not null,"
	            		+ "rocktype2 varchar(30) not null,"
	            		+ "primary key (jid))"
	            		);
	            		System.out.println("Created table 'refgeology'");
	            conn.commit();
	            System.out.println("Committed the transaction");            
	        }
	        catch (SQLException sqle)
	        {
	            printSQLException(sqle);
	        } 
	       }  
    public static void insertRecords()
    { 	
    	try {
    		conn.setAutoCommit(false);
    		
    		psInsert = conn.prepareStatement("insert into refgeology "
		      		+ "(rid, zone, rocktype, area) values (?, ?, ?, ?)"); 
    	
        	int j=0;
			for(int i= 0; i<GeologyParser.getMasterGeologyItems().size();i++) {	
				j++;
				          psInsert.setInt(1, i+1);
					      psInsert.setString(2, "Nevada" );
					      psInsert.setString
					      (3, GeologyParser.getMasterGeologyItems().get(i).get(10));
								try {
									Double doubleValue=Double.parseDouble
											(GeologyParser.getMasterGeologyItems().get(i).get(0));
			    				      psInsert.setDouble(4,doubleValue);

								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						  psInsert.addBatch();
				}
			 System.out.println("Successfully Inserted " + j + " Records");	 

			conn.commit();
			psInsert.executeBatch();
            s.close();

	                		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}             
        System.out.println("Committed the transaction");
        
    }
    public static void selectByType()
    {
        try
        {
        	System.out.println("Connected to and created database " + dbName);
            conn.setAutoCommit(false);
            s = conn.createStatement();
//            statements.add(s);
            rs = s.executeQuery(
     			   "select area from refgeology where rocktype='shale'order by jid");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberCols = rsmd.getColumnCount();
          for (int i=1; i<=numberCols; i++)
          {
              //print Column Names
            System.out.print(rsmd.getColumnLabel(i)+"\t\t"); 
            System.out.println("\n-------------------------------------------------");
            while (rs.next()) {
                Double area = rs.getDouble(4);
                System.out.println(area + "\t\t");
            }
            rs.close();
            s.close();
          }             
        }
    catch (SQLException sqle)
    {
        printSQLException(sqle);
    }
   
    }
         
    public static void selectAll()
    {
        try
        {
        	System.out.println("Connected to and created database " + dbName);
            conn.setAutoCommit(false);
            s = conn.createStatement();
            rs = s.executeQuery(
                    "select * from refgeology");
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberCols = rsmd.getColumnCount();
          for (int i=1; i<=numberCols; i++)
          {
              //print Column Names
            System.out.print(rsmd.getColumnLabel(i).toUpperCase()+"\t\t"); 
            System.out.println("\n-------------------------------------------------");
          }
            while (rs.next()) {
            	int rid = rs.getInt(1);
                String zone = rs.getString(2);
                String rocktype = rs.getString(3);
                Double area = rs.getDouble(4);
                System.out.println(rid + "\t\t" + zone + "\t\t" + rocktype + "\t\t" + area + "\t\t" );
            }
            rs.close();
            s.close();
        }
    catch (SQLException sqle)
    {
        printSQLException(sqle);
    }
   
    }
    
    public static void selectDistinct()
    {
        try
        {
        	System.out.println("Connected to and created database " + dbName);
            conn.setAutoCommit(false);
            s = conn.createStatement();
//            statements.add(s);
            rs = s.executeQuery(
       		   "select distinct rocktype from refstructure");
            ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnLabel(1));       
//            System.out.println("------------");
            while (rs.next()) {
            	strlithoTypes.add(rs.getString(1));
//              System.out.println(rs.getString(1));
//            System.out.printf("%s%n ",rs.getString(1));
            }
            
            rs.close();
            s.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
//    public static void shutdown()
//    {
//        try
//        {
//            if (stmt != null)
//            {
//                stmt.close();
//            }
//            if (conn != null)
//            {
//                DriverManager.getConnection(dbURL + ";shutdown=true");
//                conn.close();
//            }           
//        }
//        catch (SQLException sqlExcept)
//        {
//            
//        }

//    }
    public void dropTable(String tableName) {
        try {
        	System.out.println("Connected to and created database " + dbName);
            conn.setAutoCommit(false);
            s = conn.createStatement();
            s.execute("drop table refstructure");
      		System.out.println("Dropped table 'refstructure'");
            conn.commit();
            System.out.println("Committed the transaction");
  	} catch (SQLException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
  		
      }

}		
