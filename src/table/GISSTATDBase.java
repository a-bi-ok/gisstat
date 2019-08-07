package table;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import parser.GeologyParser;

public class GISSTATDBase {
	
	/* the default framework is embedded */
    protected static String framework = "embedded";
    protected String protocol = "jdbc:derby:";
    protected static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	protected static String dbURL = "jdbc:derby:GISSTATDB;create=true;user=user1;password=user1";
	
//	conn = DriverManager.getConnection(protocol + dbName
//            + ";create=true", props);
    
    static Connection conn = null;
    static ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
    static PreparedStatement psInsert;
    static PreparedStatement psUpdate;
    static Properties props = new Properties(); // connection properties

    static Statement s;
    static ResultSet rs = null;
    static String dbName = "GISSTATDB"; // the name of the database

	GeologyParser geoData = new GeologyParser();   
	  /**
	 * 
	 */
	public GISSTATDBase() {
		
	}
	
	public void go(String[] args){
		
		 /* parse the arguments to determine which framework is desired*/
        parseArguments(args);

        System.out.println("\nGISSTATApp starting in " + framework + " mode");

        
        try
        {
            props.put("user", "user1");
            props.put("password", "user1");

         
            conn = DriverManager.getConnection(protocol + dbName
                    + ";create=true", props);

            System.out.println("Connected to and created database " + dbName);

            conn.setAutoCommit(false);
            s = conn.createStatement();
            statements.add(s);
 

        }
        catch (SQLException sqle)
        {
            printSQLException(sqle);
        }    
	}

    /**
     * Reports a data verification failure to System.err with the given message.
     *
     * @param message A message describing what failed.
     */
    public void reportFailure(String message) {
        System.err.println("\nData verification failed:");
        System.err.println('\t' + message);
    }
    
    public void dbShutDownProcesses() {
    	
      if (framework.equals("embedded"))
      {
          try
          {
              // the shutdown=true attribute shuts down Derby
              DriverManager.getConnection("jdbc:derby:;shutdown=true");

          }
          catch (SQLException se)
          {
              if (( (se.getErrorCode() == 50000)
                      && ("XJ015".equals(se.getSQLState()) ))) {
                  // we got the expected exception
                  System.out.println("Derby shut down normally");
             } else {
                 System.err.println("Derby did not shut down normally");
                  printSQLException(se); 	
    	
          try {
               if (rs != null) {
                   rs.close();
                   rs = null;
               }
           } catch (SQLException sqle) {
               printSQLException(sqle);
           }

           // Statements and PreparedStatements
//           int i = 0;
//           while (!statements.isEmpty()) {
//               // PreparedStatement extend Statement
//               Statement st = (Statement)statements.remove(i);
//               try {
//                   if (st != null) {
//                       st.close();
//                       st = null;
//                   }
//               } catch (SQLException sqle) {
//                   printSQLException(sqle);
//               }
//           }

           //Connection
           try {
               if (conn != null) {
                   conn.close();
                   conn = null;
               }
           } catch (SQLException sqle) {
               printSQLException(sqle);
           }
              }
          }
      }
    }

    /**
     * Prints details of an SQLException chain to <code>System.err</code>.
     * Details included are SQL State, Error code, Exception message.
     *
     * @param e the SQLException from which to print details.
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }
 
    public void parseArguments(String[] args)
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("derbyclient"))
            {
                framework = "derbyclient";
                protocol = "jdbc:derby://localhost:1527/";
            }
        }	
		
	}
    
    

}
