package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Interface implemented by the Trip class. Abstracts implementation of Trip methods. 
 *
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public interface TripInterface {

	//Get methods
	
	public String getName();
	
	public int getCurrentNumberOfPassengers();
	
	public TimeIntervalInterface getDepartureTimeInterval();
	
	public TimeIntervalInterface getArrivalTimeInterval();
	
	public String getDriver();
	
	public int getMaxPassengers();
	
	public ArrayList<RequestInterface> getPassengers();
	
	public LandmarkInterface getPointOfArrival();
	
	public LandmarkInterface getPointOfDeparture();
		
	public RouteInterface getRoute();

	/**
	 * @return Returns the unique identifier for the user object that is the owner of this trip.
	 */
	public String getOwner();
	
	//Set methods
	
	public boolean setName(String newName);
	
	public boolean addPassenger( RequestInterface newPassenger);
	
	public boolean removePassenger( RequestInterface targetPassenger );
	
	public boolean setDepartureTimeInterval(TimeIntervalInterface newDepartureTime);
	
	public boolean setArrivalTimeInterval(TimeIntervalInterface newArrivalTime);
		
	public boolean setDriver(String newDriver);
	
	public boolean setMaxPassengers(int newMax);
	
	public boolean setPointOfArrival(LandmarkInterface newPointOfArrival);
	
	//public boolean setPointOfArrival(String pointOfArrName, String streetAddress, String city, String state, String zipCode );
	
	public boolean setPointOfDeparture(LandmarkInterface newPointOfDeparture);
	
	//public boolean setPointOfDeparture(String pointOfDepName, String streetAddress, String city, String state, String zipCode );
	
	public boolean setRoute(RouteInterface newRoute );
	
	//public boolean setRoute(String name, String pointOfDepName, String streetAddressDep, String cityDep, String stateDep, String zipCodeDep,
	//		String pointOfArrName, String streetAddressArr, String cityArr, String stateArr, String zipCodeArr );

	public ArrayList<RequestInterface> getPassengerRequests();
	public boolean setPassengerRequests( ArrayList<RequestInterface> newRequests );
	public boolean addAPassengerRequest( RequestInterface ri );
	public RequestInterface getAPassengerRequest( String name, String owner );
	public RequestInterface removeAPassengerRequest( String name, String owner );
	
	public int getTID();

}