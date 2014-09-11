package model;


/**
 * Interface implemented by the Route class. Abstracts implementation of Route methods. 
 *
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public interface RouteInterface {

	//Get Methods
	
	public String getName();
	
	public LandmarkInterface getPointOfArrival();
	
	public LandmarkInterface getPointOfDeparture();
	
	//Set Methods
	
	public boolean setName( String newName );
	
	public boolean setPointOfArrival(LandmarkInterface newPointOfArrival );
	
	//public boolean setPointOfArrival(String pointOfArrName, String streetAddress, String city, String state, String zipCode );
	
	public boolean setPointOfDeparture(LandmarkInterface newPointOfDeparture );
	
	//public boolean setPointOfDeparture(String pointOfDepName, String streetAddress, String city, String state, String zipCode );
	
	public String getOwner();
}
