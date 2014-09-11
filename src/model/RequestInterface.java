package model;

import java.util.Calendar;
import java.util.List;

/**
 * Interface implemented by the Request class. Abstracts implementation of Request methods. 
 *
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public interface RequestInterface {

	//Get Methods
	public int getRQID();
	
	public TimeIntervalInterface getArrivalTime();
	
	public TimeIntervalInterface getDepartureTime();
	
	public LandmarkInterface getPointOfArrival();
	
	public LandmarkInterface getPointOfDeparture();
	
	public RouteInterface getRoute();
	
	public boolean addAPassengerRequest(TripInterface trip);
	
	public TripInterface removeAPassengerRequest(String name, String owner);
	
	public List<TripInterface> getPassengerRequests();
	
	public boolean setPassengerRequests(List<TripInterface> passengerRequests);
	
	//public boolean getMatched();
	
	public TripInterface getConfirmed();
	
	public boolean getStatus();
	
	/**
	 * @return Returns the unique identifier for the user object that is the owner of this trip.
	 */
	public String getOwner();
	
	public String getName();
	
	//Set Methods
	
	public boolean setArrivalTime(TimeIntervalInterface newTime);
	
	public boolean setDepartureTime(TimeIntervalInterface newTime);
	
	public boolean setPointOfArrival(LandmarkInterface newPointOfArrival);
	
	//public boolean setPointOfArrival(String name, String streetAddress, String city, String state, String zipCode );
	
	public boolean setPointOfDeparture(LandmarkInterface newPointOfDeparture );
	
	//public boolean setPointOfDeparture(String name, String streetAddress, String city, String state, String zipCode );
	
	public boolean setRoute(RouteInterface newRoute );
	
	//public boolean setRoute(String depName, String streetAddressDep, String cityDep, String stateDep, String zipCodeDep,
	//		String arrName, String streetAddressArr, String cityArr, String stateArr, String zipCodeArr );
	
	public void setStatus(boolean newValue );
	
	public boolean setConfirmed(TripInterface newTrip);
	
	//Matched/Confirmed methods
}

