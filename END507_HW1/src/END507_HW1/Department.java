package END507_HW1;

public class Department {
	private int depNumber;
	
	/** The number of the objects created */
	private static int numberOfObjects = 0;
	  
	// Constructor.
	public Department(int depNumber){
		this.depNumber = depNumber;
		numberOfObjects++;
	}
	
	// Accessor for depNumber.
	public int getDepNumber(){
		return depNumber;
	}
	
	/** Return numberOfObjects */
	public static int getNumberOfObjects() {
		return numberOfObjects;
	}
	
	public String toString()
	{
		String str = "Department number" + depNumber;
		return str;
	} // end toString

}
