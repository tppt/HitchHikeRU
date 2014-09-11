package model;

//import java.util.Calendar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**Object containing all trip information, such as the driver, route, etc.
 * This object contains an instance of the Route object.
 * A trip can only be initiated by a Driver.
 * 
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public class Trip implements TripInterface {

	private String name = "";
	private String driver = "";
	private ArrayList<RequestInterface> passengers;
	private ArrayList<RequestInterface> passengerRequests;
	private int maxPassengers = 0;
	private Route route = null;
	private TimeIntervalInterface timeOfDeparture = null;
	private TimeIntervalInterface timeOfArrival = null;
	private int TID;

	protected Trip( String name, String driver, int maxPassengers, RouteInterface route, 
			 TimeIntervalInterface timeOfDeparture, TimeIntervalInterface timeOfArrival, int TID ){
		
		if(name == "" || name == null)
			throw new IllegalArgumentException("No name provided for Trip constructor");
		else
			this.name = name;
		
		if(driver == "" || driver == null)
			throw new IllegalArgumentException("No driver provided for Trip constructor");
		else
			this.driver = driver;
		
		//if(maxPassengers < 1 || maxPassengers > 7)
		//	throw new IllegalArgumentException("Invalid number of passengers provided for Trip constructor");
		//else
			this.maxPassengers = maxPassengers;
		
		if(route == null || route == null)
			throw new IllegalArgumentException("Null route provided to Trip constructor");
		else
			this.route = (Route) route;
		
		if(timeOfDeparture == null)
			throw new IllegalArgumentException("Invalid timeOfDeparture given to Trip constructor");
		else
			this.timeOfDeparture = timeOfDeparture;
		
		if( timeOfArrival == null )
			throw new IllegalArgumentException("Invalid timeOfArrival given to Trip constructor");
		else
			this.timeOfArrival = timeOfArrival;
		
		passengerRequests = new ArrayList<RequestInterface>();
		passengers = new ArrayList<RequestInterface>();
		System.out.println("Trip "+TID +" created");
		this.TID = TID;
	}
	
	
	
	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public TimeIntervalInterface getDepartureTimeInterval() {
		return timeOfDeparture;
	}
	
	@Override
	public TimeIntervalInterface getArrivalTimeInterval() {
		return timeOfArrival;
	}
	
	@Override
	public int getCurrentNumberOfPassengers() {
		return passengers.size();
	}

	@Override
	public String getDriver() { 
		return driver;
	}

	@Override
	public int getMaxPassengers() {
		return maxPassengers;
	}

	@Override
	public ArrayList<RequestInterface> getPassengers() {
		return passengers;
	}

	@Override
	public LandmarkInterface getPointOfArrival() {
		return route.getPointOfArrival();
	}

	@Override
	public LandmarkInterface getPointOfDeparture() {
		return route.getPointOfDeparture();
	}

	@Override
	public RouteInterface getRoute() {
		return route;
	}

	/**
	 * @return Returns the unique identifier for the user object that is the owner of this trip.
	 */
	public String getOwner() {
		return driver;
	}
	
	@Override
	public boolean setName(String newName)
	{
		if(newName != "")
		{
			name = newName;
			return true;
		}
		return false;
	}

	@Override
	public boolean addPassenger(RequestInterface newPassenger) {
		
		if( newPassenger == null )
			throw new NullPointerException();
		
		int RQID = newPassenger.getRQID();
		int index = -1;
		Iterator<RequestInterface> i = passengerRequests.iterator();
		RequestInterface listItem = null;
		boolean inList = false;
		while( i.hasNext() ){
			listItem = i.next();
			index++;
			if( RQID == listItem.getRQID() ){
				inList = true;
				break;
			}
		}
		
		String query = "";
		
		if( inList ){
			passengerRequests.remove(index);
			query = "UPDATE tPassengers SET Accepted = '1' WHERE RQID = '"+RQID+"' AND '"+TID+"';";
		}
		else{
			query = "INSERT INTO tPassengers(RQID, TID, Accepted) "+
					"VALUES ('"+RQID+"', '"+TID+"', '1');";
		}
		
		passengers.add(newPassenger);
		ObjectManager.getDBConn().DBUpdate(query);
		return true;
	}

	@Override
	public boolean removePassenger(RequestInterface targetPassenger) {
		
		if( targetPassenger == null )
			throw new NullPointerException();
		if( passengers == null )
			throw new NullPointerException();
		if( passengers.isEmpty() )
			return false;
		
		int RQID = targetPassenger.getRQID();
		String query = "SELECT COUNT(*) AS total FROM tPassengers WHERE RQID = '"+RQID+"' AND '"+TID+"';";
		ResultSet rs = ObjectManager.getDBConn().queryDB(query);
		
		boolean inDB = true;
		int count = 0;
		try{
			int check = 0;
			while( rs.next() ){
				check++;
				if( rs.isAfterLast() )
					break;
				count = rs.getInt("total");
			}
			rs.close();
			if( check > 1 )
				throw new SQLException("Multiple records returned where unique record expected.");
			if( count == 0 )
				inDB = false;
		}
		catch( SQLException e ){
			e.printStackTrace();
		}
		
		int index = -1;
		Iterator<RequestInterface> i = passengers.iterator();
		RequestInterface listItem = null;
		boolean inList = false;
		while( i.hasNext() ){
			listItem = i.next();
			index++;
			if( targetPassenger.getRQID() == listItem.getRQID() ){
				inList = true;
				break;
			}
		}
		
		if( !inDB && !inList )
			return false;
		
		if( inDB ){
			query = "DELETE FROM tPassengers WHERE RQID = '"+RQID+"' AND TID = '"+TID+"';";
			ObjectManager.getDBConn().DBUpdate(query);
		}
		
		if( inList ){
			passengers.remove(index);
		}
		
		return true;
	}
	
	public boolean setPassengers(ArrayList<RequestInterface> passengers) {
		
		this.passengers = passengers;
		return true;
	}

	@Override
	public boolean setDepartureTimeInterval(TimeIntervalInterface newDepartureTime) {
		if(newDepartureTime != null)
		{
			timeOfDeparture = newDepartureTime;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setArrivalTimeInterval(TimeIntervalInterface newArrivalTime) {
		if(newArrivalTime != null)
		{
			timeOfArrival = newArrivalTime;
			return true;
		}
		return false;
	}

	@Override
	public boolean setDriver(String newDriver) {
		if(newDriver != "")
		{
			driver = newDriver;
			return true;
		}
		return false;
	}

	@Override
	public boolean setMaxPassengers(int newMax) {
		if(newMax > 0 && newMax < 7)
		{
			maxPassengers = newMax;
			return true;
		}
		return false;
	}

	@Override
	public boolean setPointOfArrival(LandmarkInterface newPointOfArrival) {
		if(newPointOfArrival != null)
		{
			return route.setPointOfArrival(newPointOfArrival);
		}
		return false;
	}

	//@Override
	/*public boolean setPointOfArrival(String pointOfArrName, String streetAddress, String city, String state, 
			String zipCode) {
		
		return route.setPointOfArrival(pointOfArrName, streetAddress, city, state, zipCode);
	}*/

	@Override
	public boolean setPointOfDeparture(LandmarkInterface newPointOfDeparture) {
		
		return route.setPointOfDeparture(newPointOfDeparture);
	}

	//@Override
	/*public boolean setPointOfDeparture(String pointOfDepName, String streetAddress, String city, String state,
			String zipCode) {

		return route.setPointOfDeparture(pointOfDepName, streetAddress, city, state, zipCode);
	}*/

	@Override
	public boolean setRoute(RouteInterface newRoute) {
		if(newRoute != null)
		{
			route = (Route) newRoute;
			return true;
		}
		return false;
	}

	//@Override
	/*public boolean setRoute(String routeName, String pointOfDepName, String streetAddressDep,
			String cityDep, String stateDep, String zipCodeDep,
			String pointOfArrName, String streetAddressArr, String cityArr, String stateArr,
			String zipCodeArr) {
		
		/* TEMPORARY */
	/*	route = new Route(name, pointOfDepName, streetAddressDep, cityDep, stateDep, zipCodeDep,
				pointOfArrName, streetAddressArr, cityArr, stateArr, zipCodeArr);
		return false;
	}*/
	
	public ArrayList<RequestInterface> getPassengerRequests(){
		return passengerRequests;
	}
	
	public boolean setPassengerRequests( ArrayList<RequestInterface> newRequests ){

		this.passengerRequests = newRequests;
		return true;
	}
	
	public boolean addAPassengerRequest( RequestInterface ri ){
		
		if( ri == null )
			throw new NullPointerException("Param passed in is null.");
		
		Iterator<RequestInterface> i = passengerRequests.iterator();
		RequestInterface listItem = null;
		while( i.hasNext() ){ //Checking for duplicates, which we don't allow.
			listItem = i.next();
			if( ri.getName().equals(listItem.getName()) ){
				if( ri.getOwner().equals(listItem.getOwner()) ){
					return false;
				}
			}
		}
		
		int RQID = ri.getRQID();
		String query = "INSERT INTO tPassengers(RQID, TID, Accepted) "+
				"VALUES ('"+RQID+"', '"+TID+"' , '0');";
		
		ObjectManager.getDBConn().DBUpdate(query);
		
		passengerRequests.add( ri );
		return true;
	}
	
	public RequestInterface getAPassengerRequest( String name, String owner ){
		
		if( name == null || owner == null )
			throw new NullPointerException("Param passed in is null.");
		
		if( name.isEmpty() || owner.isEmpty() )
			throw new IllegalArgumentException("Param passed in is an empty string.");
		
		Iterator<RequestInterface> i = passengerRequests.iterator();
		RequestInterface listItem = null;
		while( i.hasNext() ){
			listItem = i.next();
			if( name.equals(listItem.getName()) ){
				if( owner.equals(listItem.getOwner()) ){
					return listItem;
				}
			}
		}
		
		return null;
	}
	
	public int getTID(){
		return TID;
	}
	
	public RequestInterface removeAPassengerRequest( String name, String owner ){
		
		if( name == null || owner == null )
			throw new NullPointerException("Param passed in is null.");
		
		if( name.isEmpty() || owner.isEmpty() )
			throw new IllegalArgumentException("Param passed in is an empty string.");
		
		int index = -1;
		
		Iterator<RequestInterface> i = passengerRequests.iterator();
		RequestInterface listItem = null;
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
		
		int RQID = listItem.getRQID();
		
		String query = "DELETE FROM tPassengers WHERE RQID = '"+RQID+"' AND '"+TID+"';";
		ObjectManager.getDBConn().DBUpdate(query);
		
		passengerRequests.remove(index);
		return listItem;
	}
}