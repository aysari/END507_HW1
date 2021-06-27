package END507_HW1;

public class Location {
	private int locationNumber;
	
	/** The number of the objects created */
	private static int numberOfObjects = 0;
	
	// Constructor.
	public Location(int locationNumber){
		this.locationNumber = locationNumber;
		numberOfObjects++;
	}
	
	// Accessor for depNumber.
	public int getLocationNumber(){
		return locationNumber;
	}
	/** Return numberOfObjects */
	public static int getNumberOfObjects() {
		return numberOfObjects;
	}	
	
	public String toString(){
		String str = "Location number: " + locationNumber;
		return str;
	} // end toString

}
