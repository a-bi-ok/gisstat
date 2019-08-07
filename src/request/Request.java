/**
 * 
 */
package request;

/**
 * @author tim_abiok
 *
 */
public abstract class Request {
	
	public Request() {	
	}
	/**
	 * @uploadReport() Runs the request and validation process.
	 * Returns a String[]-list of validated input file names.
	 */
	public abstract String[] checkEntry();
	/**
	 * @getData() Allows user to enter file names
	 * returns a list of entered names.
	 * @ReqList - Mimics a HASHMAP, skips the process
	 */
	public abstract String[] getData();
	
	/**
	 * @isValidEntry() Validates user entry list created in @getData()
	 * returns a boolean 
	 * @param - list created from @getData()
	 */
	public abstract boolean isvalidEntry(String[] userEntry);
	
	/**
	 * @displayEntry() Returns items in the list from @getData()
	 * @param - list created from @getData()
	 */
	public abstract void displayEntry(String[] userEntry);
	
	/**
	 * @uploadReport() Creates text report to console with information
	 * from @getData()
	 * returns a void 
	 * @param - list created from @getData()
	 */
	public abstract void uploadReport(String[] userEntry);

}
