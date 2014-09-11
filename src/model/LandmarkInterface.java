package model;

/**
 * Interface implemented by the Landmark class. Abstracts implementation of Landmark methods.
 *  
 *	@authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public interface LandmarkInterface {

	//Get methods
	
	/**
	 * @return The name of the landmark.
	 */
	public String getName();
	
	/**
	 * @return the city of the Landmark backing this interface.
	 */
	public String getCity();
	
	/** 
	 * @return the human-readable description of the Landmark backing this interface.
	 */
	public String getDescription();
	
	/**
	 * Get this Landmark's latitude.  Note that latitude ranges from -90 degrees 
	 * to +90 degrees, where '-' denotes "South" and '+' denotes "North".
	 * Specifically, these are the degrees north/south from the equator.
	 * 
	 * @return the double representing this landmark's latitude.
	 */
	public double getLatitude();
	
	/**
	 * Get this Landmark's longitude.  Note that longitude
	 * ranges from -180 degress to +180 degrees, where '-' denotes "West" and '+' denotes 
	 * "East" with respect to the Prime (Greenwich) Meridian.
	 * 
	 * @return the double representing this landmark's longitude.
	 */
	public double getLongitude();
	
	/**
	 * Get the region associated with this Landmark.  In general, a region is a subsection
	 * of a city (i.e. Busch Campus is a subsection of Piscataway ).
	 * 
	 * @return the string representing this landmark's region.
	 */
	public String getRegion();
	
	/**
	 * @return the U.S. State wherein this Landmark resides.
	 */
	public String getState();
	
	/**
	 * @return the street address of this Landmark; this value excludes city, state, and zip code.
	 */
	public String getStreetAddress();
	
	/**
	 * @return the 5-digit ZIP code of the area wherein this Landmark resides.
	 */
	public String getZipCode();
	
	/**
	 * @return The owner of the Landmark.
	 */
	public String getOwner();
	
	public int getLMID();
	
	//Set methods
	
	/**
	 * Set the city for this landmark to the given parameter.
	 * @return true if successfully updated; false otherwise.
	 */
	public boolean setCity(String newCity);
	
	/**
	 * Set the human-readable description of this landmark to the given parameter.
	 * @return true if successfully updated; false otherwise.
	 */
	public boolean setDescription(String newDescr);
	
	/**
	 * Set the latitude of this landmark to the given parameter.  Note that latitude
	 * ranges from -90 degrees to +90 degrees, where '-' denotes "South" and '+' denotes 
	 * "North".  Specifically, these are the degrees north/south from the equator.
	 * 
	 * @return true if successfully updated; false otherwise.
	 */
	public boolean setLatitude(double newLatitude);
	
	/**
	 * Set the longitude of this landmark to the given parameter.  Note that longitude
	 * ranges from -180 degress to +180 degrees, where '-' denotes "West" and '+' denotes 
	 * "East" with respect to the Prime (Greenwich) Meridian.
	 * 
	 * @return true if successfully updated; false otherwise.
	 */
	public boolean setLongitude(double newLongitude);
	
	/**
	 * Set this landmark's region to the given parameter.
	 * @return true if updated successfully; false otherwise.
	 */
	public boolean setRegion(String newRegion);
	
	/**
	 * Set the U.S. state wherein this landmark resides to the given parameter.
	 * @return true if successfully updated; false otherwise.
	 */
	public boolean setState(String newState);
	
	/**
	 * Set the street address for this landmark to the given parameter.
	 * @return true if successfully updated; false otherwise.
	 */
	public boolean setStreetAddress(String newAddress);
	
	/**
	 * Set the ZIP code for the area wherein this landmark resides to the given parameter.
	 * This method assumes the use of 5-digit ZIP codes only, without the 4 digit
	 * ZIP extension codes.
	 * 
	 * @return true if successfully updated; false otherwise.
	 */
	public boolean setZipCode(String newZipCode );
	
	public boolean setLMID(int LMID);
	
}
