package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import java.util.Calendar;

/**Object containing all request information, such as the requester, route, etc.
 * This object contains an instance of the Route object.
 * A request can only be initiated by a Passenger.
 * 
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public class Request implements RequestInterface {
	
	private String name = "";
	private String requester = "";
	private RouteInterface route = null;
	private boolean status = false;
	private List<TripInterface> passengerRequests;
	private TimeIntervalInterface timeOfDeparture = null;
	private TimeIntervalInterface timeOfArrival = null;
	private TripInterface confirmed;
	private int RQID;
    
   /**
    * Constructor for the Request class
    * 
    * @param name Name of the Request
    * @param requester Name of the Requester
    * @param route Route associated with this Request
    * @param timeOfDeparture TimeInteval representation of the time of departure
    * @param timeOfArrival TimeInterval representation of the time of arrival
    */
    protected Request(String name, String requester, RouteInterface route, TimeIntervalInterface timeOfDeparture, TimeIntervalInterface timeOfArrival, int RQID ){
    	
    	if(name != "")
			this.name = name;
		else
			throw new IllegalArgumentException("No name given to Route constructor");
    	
    	if(requester != "")
    		this.requester = requester;
    	else
    		throw new IllegalArgumentException("Name of requester not given to Route constructor");
    	
    	if(timeOfDeparture == null)
			throw new IllegalArgumentException("Invalid time of departure given to Trip constructor");
		else
			this.timeOfDeparture = timeOfDeparture;
		
		if(timeOfArrival == null)
			throw new IllegalArgumentException("Invalid time of Arrival given to Trip constructor");
		else
			this.timeOfArrival = timeOfArrival;
		
		this.route = route;
		this.RQID = RQID;
		confirmed = null;
    }

	@Override
	public TimeIntervalInterface getArrivalTime() {
		
		return timeOfArrival;
	}
	
	
	@Override
	public TimeIntervalInterface getDepartureTime() {
		
		return timeOfDeparture;
	}
	
	@Override
	public LandmarkInterface getPointOfArrival() {
		
		return route.getPointOfArrival();
	}
	
	@Override
	public LandmarkInterface getPointOfDeparture() {
		
		return route.getPointOfDeparture();
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public RouteInterface getRoute() {
		
		return route;
	}
	
	@Override
	public boolean getStatus() {
		
		return status;
	}

	@Override
	public TripInterface getConfirmed() {
		
		return confirmed;
	}
	
	/**
	 * @return Returns the unique identifier for the user object that is the owner of this landmark.
	 */
	public String getOwner() {
		
		return requester;
	}
	
	@Override
	public boolean setArrivalTime(TimeIntervalInterface newTime) {
		
		boolean retVal = false;
		if(newTime != null)
		{
			timeOfArrival = newTime;
			retVal = true;
		}
		return retVal;
	}
	
	@Override
	public boolean setDepartureTime(TimeIntervalInterface newTime) {
		
		boolean retVal = false;
		if(newTime != null)
		{
			timeOfDeparture = newTime;
			retVal = true;
		}
		return retVal;
	}
	
	@Override
	public boolean setPointOfArrival(LandmarkInterface newPointOfArrival) {
		
		boolean retVal = false;
		if(newPointOfArrival != null)
		{
			route.setPointOfArrival(newPointOfArrival);
			retVal = true;
		}
		return retVal;
	}
	
	/*@Override
	public boolean setPointOfArrival(String name, String streetAddress,
			String city, String state, String zipCode) {
		
		boolean retVal = false;
		try
		{
			if(route.setPointOfDeparture(name, streetAddress, city, state, zipCode))
				retVal = true;
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Invalid arguemtns for landmark passed to Request setPointOfArrival method");
		}
		return retVal;
	}*/
	
	@Override
	public boolean setPointOfDeparture(LandmarkInterface newPointOfDeparture) {
			
		boolean retVal = false;
		if(route.setPointOfDeparture(newPointOfDeparture))
		{
			retVal = true;
		}
		return retVal;
	}
	
	/*@Override
	public boolean setPointOfDeparture(String name, String streetAddress,
			String city, String state, String zipCode) {
		
		boolean retVal = false;
		try
		{
			if(route.setPointOfDeparture(name, streetAddress, city, state, zipCode))
				retVal = true;
		}
		catch(IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Invalid arguemtns for landmark passed to Request setPointOfArrival method");
		}
		return retVal;
	}*/
	
	@Override
	public boolean setRoute(RouteInterface newRoute) {
		
		boolean retVal = false;
		if(newRoute != null)
		{
			route = (Route) newRoute;
			retVal = true;
		}
		return retVal;
	}
	
	/*public boolean setRoute(String depName, String streetAddressDep, String cityDep,
			String stateDep, String zipCodeDep, String arrName, String streetAddressArr,
			String cityArr, String stateArr, String zipCodeArr) {
		
		boolean retVal = false;
		if(route.setPointOfDeparture(depName, streetAddressDep, cityDep, stateDep, zipCodeDep) &&
				route.setPointOfArrival(depName, streetAddressArr, cityArr, stateArr, zipCodeArr))
		{
			retVal = true;
		}
		return retVal;
	}*/

	@Override
	public boolean setConfirmed(TripInterface newTrip) {
		
		boolean retVal = false;
		if(newTrip != null)
		{
			confirmed = newTrip;
			retVal = true;
		}
		return retVal;
	}
	
	@Override
	public void setStatus(boolean newValue) {
		
		status = newValue;
	}
	
	public boolean addAPassengerRequest(TripInterface trip) {
		
		if( trip == null )
			throw new NullPointerException("Param passed in is null.");
		
		if (passengerRequests!=null){
			Iterator<TripInterface> i = passengerRequests.iterator();
			TripInterface listItem = null;
			while( i.hasNext() ){ //Checking for duplicates, which we don't allow.
				listItem = i.next();
				if( trip.getName().equals(listItem.getName()) ){
					if( trip.getOwner().equals(listItem.getOwner()) ){
						return false;
					}
				}
			}
		} else {
			passengerRequests = new ArrayList<TripInterface>();
		}
		passengerRequests.add( trip );
		
		return true;
	}
	
	public TripInterface removeAPassengerRequest(String name, String owner) {
		
		if( name == null || owner == null )
			throw new NullPointerException("Param passed in is null.");
		
		if( name.isEmpty() || owner.isEmpty() )
			throw new IllegalArgumentException("Param passed in is an empty string.");
		
		int index = -1;
		
		Iterator<TripInterface> i = passengerRequests.iterator();
		TripInterface listItem = null;
		boolean found = false;
		while( i.hasNext() ){
			listItem = i.next();
			index++;
			if( name.equals(listItem.getName()) ){
				if( owner.equals(listItem.getOwner()) ){
					found = true;
					break;
				}
			}
		}
		
		if( !found )
			return null;
		
		passengerRequests.remove(index);
		return listItem;
	}
	
	public List<TripInterface> getPassengerRequests() {
		return passengerRequests;
	}
	
	public boolean setPassengerRequests(List<TripInterface> passengerRequests) {
		this.passengerRequests = passengerRequests;
		
		return true;
	}
	
	public int getRQID(){
		return RQID;
	}
}