package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

//import org.apache.log4j.Logger;

/**
 * Class handling construction and storage of datatype objects.
 * 
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 *
 */
public class ObjectManager implements Transactor {

	//private static Logger logger = Logger.getLogger(ObjectManager.class);
	private static DatabaseConnection dbConn;
	
	private static ConcurrentHashMap<String, User> users;
	private static ConcurrentHashMap<String, Landmark> landmarks;
	private static ConcurrentHashMap<String, Route> routes;
	private static ConcurrentHashMap<String, Request> requests;
	private static ConcurrentHashMap<String, Trip> trips;
	
	public ObjectManager()
	{
		
		//logger.debug("Attempting to create a database connection...");
		dbConn = new DatabaseConnection();
		//logger.debug("Database connection created...");
		
		users = new ConcurrentHashMap<String, User>();
		landmarks = new ConcurrentHashMap<String, Landmark>();
		routes = new ConcurrentHashMap<String, Route>();
		requests = new ConcurrentHashMap<String, Request>();
		trips = new ConcurrentHashMap<String, Trip>();
	}
	
	//Public, interface-defined methods
	
	@Override
	public UserInterface getUser(String emailAddress) {
		
		//Check if the user already exists in memory.
		//logger.debug("Attempting to retrieve User from Database...");
		User user = users.get( emailAddress );
		
		if(user != null)
			return user;
		
		//Generate query and retrieve result set from database.
		//logger.debug("Executing retrieve query with values: " + emailAddress + "...");
		String query = "SELECT * FROM tUsers WHERE EMail = '" + emailAddress + "';";
		ResultSet rs = dbConn.queryDB( query );
		
		try {
			
			int check = 0;
			
			//Assumed schema = 
			//<UserID, first, last, password, email, cardescription, numseats, devdist>
			while( rs.next() ){
				
				//Safety check - if we get more than one record then there is an issue with
				//the DB; we can't trust the result set
				check++;
				if( check > 1 )
					throw new SQLException();
				
				//logger.debug("Creating user from query resultset...");
;				user = new User();
				user.setFirstName(rs.getString("FirstName"));
				user.setLastName(rs.getString("LastName"));
				user.setPassword(rs.getString("Password"));
				user.setRutgersEmail(rs.getString("EMail"));
				user.setCarDescription(rs.getString("CarDescription"));
				user.setNumSeats(rs.getInt("NumSeats"));
				user.setDeviationDistance(rs.getDouble("DeviationDist"));
				
				users.put( emailAddress, user );
			}
			
			rs.close();
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading user.");
			//logger.error("ERROR: SQL Error while loading user...", e);
			System.exit(1);
		}	
		
		//logger.debug("Returning user...");
		return user;
	}
	
	@Override
	public UserInterface registerUser(String firstName, String lastName,
	                String emailAddress, String password, String carDescription,
	                int numSeats, double deviationDistance) {
		
		//logger.debug("Attempting to register a User...");
		
		//Check to see if the user already exists.
		boolean userExists = validateUser( emailAddress );
		if( userExists )
		{		
			//logger.debug("User exists within database, exiting regiterUser method...");
			return null;
		}

		String query = "INSERT INTO tUsers (FirstName, LastName, Password, EMail, CarDescription, NumSeats, DeviationDist) " +
				"VALUES ('"+firstName+"', '"+lastName+"', '"+password+"', '"+emailAddress+"', '"+carDescription+
				"', '"+numSeats+"', '"+deviationDistance+"')" + ";";
		
		//Insert into DB
		dbConn.DBUpdate( query );
		
		//Create new user object.
		User user = new User( firstName, lastName, emailAddress, password, 
				carDescription, numSeats, deviationDistance );
	
		//Put user into table.
		users.put( emailAddress, user );
		
		return user;
	}
	
	@Override
	public UserInterface removeUser(String emailAddress) {
		
		//Check to see if the user exists or not.
		boolean userExists = validateUser( emailAddress );
		
		if( !userExists )
			return null;

		UserInterface ui = getUser( emailAddress );
		
		users.remove( emailAddress );
		//TODO CHECK THAT THIS IS CORRECT
		String query = "DELETE FROM tUsers WHERE EMail = '" + emailAddress + "';";
		dbConn.DBUpdate(query);
		return ui;
	}
	
	@Override
	public boolean validateUser(String emailAddress) {
		
		//Check to see if the user is already in memory.
		User user = users.get( emailAddress );
		
		if(user != null)
			return true;
		
		//If not, attempt to load the user from the DB.
		//TODO CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tUsers WHERE EMail = '" + emailAddress + "';";
		ResultSet rs = dbConn.queryDB( query );
		boolean found = false;
			
		try{
				
			int check = 0;
			while( rs.next() ){
				check++;
				if( check > 1 )
					throw new SQLException("More than one result returned on a unique query.");
				found = true;
			}

			rs.close();
			
			if( !found )
				return false;
			
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading user.");
			System.exit(1);
		}
	
		return true;
	}
	
	@Override
	public LandmarkInterface getLandmark(String emailAddress, String landmarkName) {
		
		//Check if the landmark already exists in memory.
		Landmark landmark = landmarks.get( emailAddress + landmarkName );
				
		if(landmark != null)
			return landmark;
				
		//Generate query and retrieve result set from database.
		//CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tLandmarks WHERE Owner = '" + emailAddress + "' AND Name = '" + landmarkName + "';";
		ResultSet rs = dbConn.queryDB( query );
		
		try {
			
			int check = 0;
					
			//Assumed schema = 
			//<Name, Owner, StretAddrress, State, City, ZipCode, Region, Description, Latitude, Longitude>
			while( rs.next() ){
						
				//Safety check - if we get more than one record then there is an issue with
				//the DB; we can't trust the result set
				check++;
				if( check > 1 )
					throw new SQLException();
						
				landmark = new Landmark(rs.getString("Owner"), rs.getString("Name"), rs.getString("StreetAddress"), rs.getString("City"), rs.getString("State"), rs.getString("ZipCode"));
				landmark.setRegion(rs.getString("Region"));
				landmark.setLatitude(rs.getDouble("Latitude"));
				landmark.setLongitude(rs.getDouble("Longitude"));
				landmark.setLMID(rs.getInt("LMID"));
						
				landmarks.put( emailAddress + landmarkName, landmark );
			}
					
			rs.close();
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading landmark.");
			//logger.error("ERROR: SQL Error while loading landmark...", e);
			e.printStackTrace();
			System.exit(1);
		}	
				
		return landmark;
	}
	
	@Override
	public LandmarkInterface registerLandmark(String emailAddress, String landmarkName,
	                String streetAddress, String city, String state, String zipCode, 
	                String region, double longitude, double latitude) {
		
		//Check to see if the landmark already exists.
		boolean landmarkExists = validateLandmark( emailAddress, landmarkName );
		if( landmarkExists )
			return null;
		
		//Assumed schema 
		//<Name, Owner, StretAddrress, State, City, ZipCode, Region, Description, Latitude, Longitude>
		
		
		//Generate query string.
		//TODO CHECK THAT THIS IS CORRECT
		String query = "INSERT INTO tLandmarks (Name, Owner, StreetAddress, State, City, ZipCode, Region, Description, Latitude, Longitude) " +
				"VALUES ('"+landmarkName+"', '"+emailAddress+"', '"+streetAddress+"', '"+state+"', '"+city+
				"', '"+zipCode+"', '"+region+"', '"+""+"', '"+longitude+"', '"+latitude+"')" + ";";
		
		//Insert into DB
		dbConn.DBUpdate( query );
		
		//Create new landmark object.
		Landmark landmark = new Landmark( emailAddress, landmarkName, streetAddress, city, state, zipCode);
		landmark.setLatitude(latitude);
		landmark.setLongitude(longitude);
	
		//Put landmark into table.
		landmarks.put( emailAddress + landmarkName, landmark );
		
		return landmark;
	}
	
	@Override
	public LandmarkInterface removeLandmark(String emailAddress, String landmarkName) {
	
		//Check to see if the landmark already exists.
		boolean landmarkExists = validateLandmark( emailAddress, landmarkName );
		if( !landmarkExists )
			return null;

		LandmarkInterface landmark = getLandmark( emailAddress, landmarkName );
		
		landmarks.remove( emailAddress + landmarkName );
		//TODO CHECK THAT THIS IS RIGHT
		String query = "DELETE FROM tLandmarks WHERE Owner = '" + emailAddress + "' AND Name = '" + landmarkName + "';";
		dbConn.DBUpdate(query);
		
		return landmark;
	}
	
	@Override
	public boolean validateLandmark(String emailAddress, String landmarkName) {
	
		//Check to see if the landmark is in memory.
		LandmarkInterface landmark = landmarks.get(emailAddress + landmarkName);
		
		if(landmark != null)
			return true;
		
		//If not, attempt to load the landmark from the DB.
		//TODO CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tLandmarks WHERE Owner = '" + emailAddress + "' AND Name = '" + landmarkName + "';";
		ResultSet rs = dbConn.queryDB( query );
		boolean found = false;
			
		try{
				
			int check = 0;
			while( rs.next() ) {
				check++;
				if( check > 1 )
					throw new SQLException("More than one result returned on a unique query.");
				found = true;
			}

			rs.close();
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading landmark.");
			e.printStackTrace();
			System.exit(1);
		}
	
		return found;
	}
	
	@Override
	public RouteInterface getRoute(String emailAddress, String routeName) {
	
		//Check if the route already exists in memory.
		Route route = routes.get( emailAddress + routeName );
				
		if(route != null)
			return route;
				
		//Generate query and retrieve result set from database.
		//TODO CHECK THAT THIS IS RIGHT
		String query = "SELECT * FROM tRoutes WHERE Owner = '" + emailAddress + "' AND Name = '" + routeName + "';";
		ResultSet rs = dbConn.queryDB( query );
		
		try {
			
			int check = 0;
					
			//Assumed schema = 
			//<Name, Owner, StartPosition, EndPosition>
			while( rs.next() ){
						
				//Safety check - if we get more than one record then there is an issue with
				//the DB; we can't trust the result set
				check++;
				if( check > 1 )
					throw new SQLException();
				
				//Load the departure landmark into memory.
				query = "SELECT * FROM tLandmarks WHERE LMID = " + rs.getInt("StartPosition") + ";";
				ResultSet rsStart = dbConn.queryDB(query);
				if(!rsStart.next())
					throw new SQLException();
				
				LandmarkInterface start = getLandmark(rsStart.getString("Owner"), rsStart.getString("Name"));
				
				//Load the arrival landmark into memory.
				query = "SELECT * FROM tLandmarks WHERE LMID = " + rs.getInt("EndPosition") + ";";
				ResultSet rsEnd = dbConn.queryDB(query);
				if(!rsEnd.next())
					throw new SQLException();
				
				LandmarkInterface end = getLandmark(rsEnd.getString("Owner"), rsEnd.getString("Name"));
				
				//Load the route into memory.
				route = new Route(emailAddress, routeName, start, end);
						
				routes.put( emailAddress + routeName, route );
			}
					
			rs.close();
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading route.");
			System.exit(1);
		}	
				
		return route;
	}
	
	@Override
	public RouteInterface registerRoute(String emailAddress, String routeName,
	                LandmarkInterface pointOfDeparture, LandmarkInterface pointOfArrival) {
	    
		//Check to see if the route already exists.
		boolean routeExists = validateLandmark( emailAddress, routeName );
		if( routeExists )
			return null;
		
		//Assumed schema = 
		//<Name, Owner, StartPosition, EndPosition>
		
		//Generate query string.
		String query = "INSERT INTO tRoutes (Name, Owner, StartPosition, EndPosition) VALUES ('" + routeName + "' , '"
				+ emailAddress + "' , '" + pointOfDeparture.getLMID() + "', '" + pointOfArrival.getLMID() + "');";
		
		//Insert into DB
		dbConn.DBUpdate( query );
		
		//Create new route object.
		Route route = new Route(emailAddress, routeName, pointOfDeparture, pointOfArrival);
	
		//Put route into table.
		routes.put( emailAddress + routeName, route );
		
		return route;
	}
	
	@Override
	public RouteInterface removeRoute(String emailAddress, String routeName) {
	
		//Check to see if the landmark already exists.
		boolean routeExists = validateRoute( emailAddress, routeName );
		if( !routeExists )
			return null;

		RouteInterface route = getRoute( emailAddress, routeName );
		
		routes.remove( emailAddress + routeName );
		//TODO CHECK THAT THIS IS RIGHT
		String query = "DELETE FROM tRoutes WHERE Owner = '" + emailAddress + "' AND Name = '" + routeName + "';";
		dbConn.DBUpdate(query);
		
		return route;
	}
	
	@Override
	public boolean validateRoute(String emailAddress, String routeName) {
	
		//Check to see if the route is in memory.
		RouteInterface route = routes.get(emailAddress + routeName);
		
		if(route != null)
			return true;
		
		//If not, attempt to load the route from the DB.
		//TODO CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tRoutes WHERE Owner = '" + emailAddress + "' AND Name = '" + routeName + "';";
		ResultSet rs = dbConn.queryDB( query );
		boolean found = false;
			
		try{
				
			int check = 0;
			while( rs.next() ) {
				check++;
				if( check > 1 )
					throw new SQLException("More than one result returned on a unique query.");
				found = true;
			}

			rs.close();
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading landmark.");
			e.printStackTrace();
			System.exit(1);
		}
	
		return found;
	}
	
	@Override
	public RequestInterface getRequest(String emailAddress, String requestName) {
	
		//Check if the request already exists in memory.
		Request request = requests.get( emailAddress + requestName );
				
		if(request != null)
			return request;
				
		//Generate query and retrieve result set from database.
		//CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tRequests WHERE Owner = '" + emailAddress + "' AND Name = '" + requestName + "';";
		ResultSet rs = dbConn.queryDB( query );
		
		try {
			
			int check = 0;
					
			//Assumed schema = 
			//
			while( rs.next() )
			{
				 TimeIntervalInterface startTime = createTimeInterval(new Date(rs.getLong("Departure")), rs.getLong("DepartureDuration"));
				 TimeIntervalInterface endTime = createTimeInterval(new Date(rs.getLong("Arrival")), rs.getLong("ArrivalDuration"));
				 
				 String routeQuery = "SELECT * FROM tRoutes WHERE RID = '" + rs.getInt("Route")+"';";
				 ResultSet routeRS = dbConn.queryDB(routeQuery);
				 routeRS.next();
				 RouteInterface thisRoute = getRoute(routeRS.getString("Owner"), routeRS.getString("Name"));
				 
				//Safety check - if we get more than one record then there is an issue with
				//the DB; we can't trust the result set
				check++;
				if( check > 1 )
					throw new SQLException();
				
				request = new Request(rs.getString("Name"), rs.getString("Owner"),
						thisRoute, startTime, endTime, rs.getInt("RQID"));				
				routeRS.close();
			}
				
			rs.close();
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading landmark.");
			e.printStackTrace();
			System.exit(1);
		}	
				
		return request;
	}
	
	public RequestInterface registerRequest(String emailAddress, String requestName,
	                RouteInterface route, TimeIntervalInterface timeOfDeparture,
	                TimeIntervalInterface timeOfArrival) {
	
		/* 
		 * Check to see if Route with combination of this requestName+requester 
		 * already exist in db
		 * 
		 * if either exists already, we need some dialogue to popup and say it 
		 * so we avoid duplicate Requests by same User
		 * 
		 * if not, use Database connection class to start a connection, 
		 * and call method DBUpdate to write info into db
		 */
		if( emailAddress == null )
			throw new NullPointerException();
		if( emailAddress.isEmpty() )
			throw new IllegalArgumentException();
		if( requestName == null )
			throw new NullPointerException();
		if( requestName.isEmpty() )
			throw new IllegalArgumentException();
		if( route == null )
			throw new NullPointerException();
		if( timeOfDeparture == null )
			throw new NullPointerException();
		if( timeOfArrival == null )
			throw new NullPointerException();
		
		RequestInterface ri = getRequest(emailAddress, requestName);
		if( ri != null )
			return null; //TODO better handling?
		
		String query = "SELECT RID FROM tRoutes WHERE Owner = '"+route.getOwner()+"' AND " +
				"Name = '"+route.getName()+"';";
		
		ResultSet rs = dbConn.queryDB( query );
		int check = 0;
		int RID = -1;
		
		try{
			while( rs.next() ){
				
				check++;
				if( check > 1 )
					throw new SQLException();
				
				RID = rs.getInt("RID");
			}
		}
		catch( SQLException e ){
			System.out.println("ZOMG SO EXCEPTION.  SUCH CAUGHT.");
			e.printStackTrace();
		}
		
		if( RID == -1 || check == 0 ){
			return null; //TODO better way?
		}
		
	
		
		
		
		query = "INSERT INTO tRequests (Name, Owner, Route, Departure, DepartureDuration, Arrival, ArrivalDuration, Status)VALUES "+
				"('"+requestName+"', '"+emailAddress+"', '"+RID+"', '"+timeOfDeparture.getStartLong()+
				"', '"+timeOfDeparture.getDuration()+"', '"+timeOfArrival.getStartLong()+"', '"+timeOfArrival.getDuration()+
				"', '0');";
		
		dbConn.DBUpdate(query);
		//GET TID
		int RQID = 0;
		query = "SELECT * FROM tRequests WHERE Owner = '" + emailAddress + "' AND Name = '" + requestName + "';";
		ResultSet rss = dbConn.queryDB( query );
		try{
			rss.next();
			RQID = rss.getInt("RQID");
			rss.close();
		} catch (Exception e){System.out.println("This should never happen");}
		
		Request request = new Request(requestName, emailAddress, route, timeOfDeparture, timeOfArrival, RQID );
		requests.put(emailAddress+requestName, request);
		
		return request;
				
		
		
		
		/*if(emailAddress != "" && route != null && timeOfDeparture != null && timeOfArrival != null)
		{
			int routeID = -1;
			try 
			{
				String checkQuery = "SELECT * FROM tRequests WHERE Owner = '" + emailAddress + "' AND Name = '" + requestName + "';";
				ResultSet checkSet = dbConn.queryDB(checkQuery);
		
				// Check to see if the result set is empty without iterating to the first value and needing to backtrack if it isnt
				//if (!checkSet.isBeforeFirst()) 
				while( checkSet.next() )
				{   
					String routeIDQuery = "SELECT * FROM tRoutes WHERE Owner = '" + route.getOwner() + "' AND Name = '" + route.getName() + "';";
					ResultSet routeDB = dbConn.queryDB(routeIDQuery);
					while(routeDB.next())
						routeID = routeDB.getInt("RID");
					//TODO 12/4/13 AJG Finish this Query!!!!
					String updateQuery = "INSERT INTO tRequests (Name, Owner, Route, Departure, DepartureDuration, " +
							"Arrival, ArrivalDuration, Trip, Status) " + "VALUES ('" + requestName + "', '" + emailAddress + "', '" + routeID + "','" 
							+ timeOfDeparture.getStartLong() + "','" + timeOfDeparture.getDuration() + "','" +
							timeOfArrival.getStartLong() + "','" + timeOfArrival.getDuration() + "','" + "', 'null'" +
							"'false'" + "')" + "';";
					dbConn.DBUpdate(updateQuery);
				}
				else
				{
					return getRequest(emailAddress, requestName);
				}
			} 
			catch (SQLException e) 
			{ 
				e.printStackTrace();
			} 
		}
		else
		{
			return null;
		}

 		return null;
	*/
	}
	
	
	/*
	public RequestInterface registerRequest(String emailAddress, String requestName,
	                String streetAddressDep, String cityDep, String stateDep,
	                String zipCodeDep, String streetAddressArr, String cityArr,
	                String stateArr, String zipCodeArr, TimeIntervalInterface timeOfDeparture,
	                TimeIntervalInterface timeOfArrival) {
	*/
		
		/*
		 * Check to see if Route with combination of this requestName+requester 
		 * already exist in db
		 * 
		 * if either exists already, we need some dialogue to popup and say 
		 * it so we avoid duplicate Requests by same User
		 * 
		 * if not, use Database connection class to start a connection, 
		 * and call method DBUpdate to write info into db
		 */
		/*
		return null;
	}
	*/

	
	@Override
	public RequestInterface cancelRequest(String emailAddress, String requestName) {
	
		//Check to see if the request already exists.
		boolean requestExists = validateRequest( emailAddress, requestName );
		if( !requestExists )
			return null;
		
		RequestInterface request = getRequest( emailAddress, requestName );
		int RQID = request.getRQID();
		requests.remove(emailAddress + requestName);
		//TODO CHECK THAT THIS IS RIGHT
		String query = "DELETE FROM tRequests WHERE Owner = '" + emailAddress + "' AND Name = '" + requestName + "';";
		dbConn.DBUpdate(query);
		query = "DELETE FROM tPassengers WHERE RQID = '" + RQID + "';";
		dbConn.DBUpdate(query);		
		return request;
	}
	
	@Override
	public boolean validateRequest(String emailAddress, String requestName) {
	
		/*
		 * Check if request is in hashtable requests
		 * if so, return true
		 * else
		 * 		Generate queryString to find request in DB
		 * 		Pass queryString to dbConn.queryDB; retrieve ResultSet rs
		 * 		if rs ! empty
		 * 			return true
		 * 		else
		 * 			return false
		 */
		
		//Check to see if the route is in memory.
		RequestInterface request = requests.get(emailAddress + requestName);
				
		if(request != null)
			return true;
				
		//If not, attempt to load the route from the DB.
		//TODO CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tRequests WHERE Owner = '" + emailAddress + "' AND Name = '" + requestName + "';";
		ResultSet rs = dbConn.queryDB( query );
		boolean found = false;
			
		try
		{
			int check = 0;
			while( rs.next() ) 
			{
				check++;
				if( check > 1 )
					throw new SQLException("More than one result returned on a unique query.");
				found = true;
			}
		

			rs.close();
		}
		catch( SQLException e )
		{
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading landmark.");
			e.printStackTrace();
			System.exit(1);
		}

		return found;
	}
	
	@Override
	public TripInterface getTrip(String emailAddress, String tripName) {
	
		//Check if the trip already exists in memory.
		Trip trip = trips.get( emailAddress + tripName );
	
		/*if(trip != null)
			return trip;
		System.out.println("not in trips hashmap");		*/
		//Generate query and retrieve result set from database.
		//CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tTrips WHERE Owner = '" + emailAddress + "' AND Name = '" + tripName + "';";
		ResultSet rs = dbConn.queryDB( query );
		
		try {
			
			int check = 0;
					
			//Assumed schema for Trips = 
			//<TID, Name, Owner, RID, int MaxPassengers, int AvailableSeats, long Start, long StartDuration, long End, long EndDuration>
			//Schema for Passengers = 
			//<PID, Passenger, TID>
			while( rs.next() ) {
						
				//Safety check - if we get more than one record then there is an issue with
				//the DB; we can't trust the result set
				check++;
				if( check > 1 )
					throw new SQLException();
						
				//Load the startTimeInterval
				TimeIntervalInterface startTimeInterval = new TimeInterval(new Date(rs.getLong("Start")), rs.getLong("StartDuration"));
				TimeIntervalInterface endTimeInterval = new TimeInterval(new Date(rs.getLong("End")), rs.getLong("EndDuration"));
				
				//Load the Route
				query = "SELECT * FROM tRoutes WHERE RID = '" + rs.getInt("Route") + "';";
				ResultSet rsRoute = dbConn.queryDB(query);
				if(!rsRoute.next())
					throw new SQLException();
				
				RouteInterface route = getRoute(emailAddress, rsRoute.getString("Name"));
				
				//Load the Trips passengers
				query = "SELECT * FROM tPassengers WHERE TID = '" + rs.getInt("TID") + "';";
				ResultSet rsPassengers = dbConn.queryDB(query);
				
				ArrayList<RequestInterface> passengers = new ArrayList<RequestInterface>();
				ArrayList<RequestInterface> passengerRequests = new ArrayList<RequestInterface>();
				System.out.println("Getting passengers for trip "+ rs.getInt("TID"));
				while(rsPassengers.next()) {
					//if (!rsPassengers.isAfterLast())
					//	break;
					System.out.println("Getting passenger with RQID"+rsPassengers.getInt("RQID") );
					String RQQuery = "SELECT * FROM tRequests WHERE RQID = '"+ rsPassengers.getInt("RQID")+"';";
					ResultSet rsRequest = dbConn.queryDB(RQQuery);
					while (rsRequest.next()){
					
						RequestInterface RQ = getRequest(rsRequest.getString("Owner"), rsRequest.getString("Name"));
						int accepted = rsPassengers.getInt("Accepted");
						if (accepted==1){
							passengers.add(RQ);
						}else if (accepted==0)
							passengerRequests.add(RQ);
					}
					rsRequest.close();
				}
				
				//Load the Trip into memory.
				trip = new Trip(rs.getString("Name"), rs.getString("Owner"), rs.getInt("MaxPassengers"), route, startTimeInterval, endTimeInterval, rs.getInt("TID"));
				trip.setPassengers(passengers);
				System.out.println("# accepted pass: "+passengers.size());
				trip.setPassengerRequests(passengerRequests);
				trips.put( emailAddress + tripName, trip );
				rsRoute.close();
				rsPassengers.close();
			}
			System.out.println(trip.getCurrentNumberOfPassengers());
			rs.close();
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading landmark.");
			e.printStackTrace();
			System.exit(1);
		}	
			
		return trip;
	}
	
	@Override
	public TripInterface registerTrip( String name, String driver, int maxPassengers, RouteInterface route, 
			 TimeIntervalInterface timeOfDeparture, TimeIntervalInterface timeOfArrival ) {
	
		boolean tripExists = validateTrip( driver, name );
		if( tripExists )
			return null;

		String queryR = "SELECT RID FROM tRoutes WHERE Owner = '" + driver + "' AND Name = '" + route.getName() + "';";
		
		String rid = "";
		try {
			ResultSet rsRoute = dbConn.queryDB(queryR);
			rsRoute.next();
			rid = rsRoute.getString("RID");
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading landmark.");
			e.printStackTrace();
			System.exit(1);
		}	
		
		String query = "INSERT INTO tTrips (Name, Owner, Route, MaxPassengers, AvailableSeats, Start, StartDuration, End, EndDuration)VALUES" +
				"('"+name+"', '"+driver+"', '"+rid+"', '"+maxPassengers+"', '"+maxPassengers+"', '"+timeOfDeparture.getStartLong()+
				"', '"+timeOfDeparture.getDuration()+"', '"+timeOfArrival.getStartLong()+"', '"+timeOfArrival.getDuration()+"');";
		
		dbConn.DBUpdate(query);
		
		//GET TID
		int TID = 0;
		query = "SELECT * FROM tTrips WHERE Owner = '" + driver + "' AND Name = '" + name + "';";
		ResultSet rs = dbConn.queryDB( query );
		try{
			rs.next();
			TID = rs.getInt("TID");
			rs.close();
		} catch (Exception e){System.out.println("This should never happen");}
		
		//ERROR: java.sql.SQLException: Incorrect integer value: 'model.Route@52cff73d' for column 'Route' at row 1
		Trip trip = new Trip( name, driver, maxPassengers, route, timeOfDeparture, timeOfArrival, TID);
		
		trips.put( driver+name, trip );
		
		return trip;
	}
	
	@Override
	public TripInterface cancelTrip(String emailAddress, String tripName) {
	
		//Check to see if the trip already exists.
		boolean tripExists = validateTrip( emailAddress, tripName );
		if( !tripExists )
			return null;

		TripInterface trip = getTrip( emailAddress, tripName );
		int TID = trip.getTID();
		trips.remove(emailAddress + tripName);
		/*
		System.out.println("Current trips in hashmap:");
		Iterator<Entry<String, Trip>> it = trips.entrySet().iterator();
		while (it.hasNext()){
			TripInterface t = (TripInterface)it.next().getValue();
			System.out.println(t.getName());
		}*/
		
		
		//TODO CHECK THAT THIS IS RIGHT
		String query = "DELETE FROM tTrips WHERE Owner = '" + emailAddress + "' AND Name = '" + tripName + "';";
		dbConn.DBUpdate(query);
		
		query = "DELETE FROM tPassengers WHERE TID = '" + TID + "';";
		dbConn.DBUpdate(query);
				
		return trip;
	}
	
	@Override
	public int cancelTrip(int TID) {

		Iterator<Entry<String, Trip>> it = trips.entrySet().iterator();
		while (it.hasNext()){
			TripInterface t = (TripInterface)it.next().getValue();
			if (t.getTID()==TID){
				it.remove();
				break;
			}
		}
		
		String test = "SELECT * FROM tTrips WHERE TID = '" + TID + "';";
	
		ResultSet rs = dbConn.queryDB(test);
		
		try{
			rs.next();
			TID = rs.getInt("TID");
			rs.close();
		} catch (Exception e){return 1;}
		
		
		String query = "DELETE FROM tTrips WHERE TID = '" + TID + "';";
		dbConn.DBUpdate(query);
	
		query = "DELETE FROM tPassengers WHERE TID = '" + TID + "';";
		dbConn.DBUpdate(query);
		
		
		return 0;
	}
	
	@Override
	public int cancelRequest(int RQID) {
		
		Iterator<Entry<String, Request>> it = requests.entrySet().iterator();
		while (it.hasNext()){
			RequestInterface t = (RequestInterface)it.next().getValue();
			if (t.getRQID()==RQID){
				it.remove();
				break;
			}
		}
	
		String test = "SELECT * FROM tRequests WHERE RQID = '" + RQID + "';";
		try {
			dbConn.queryDB(test);
		} catch (Exception e){return 1;}
		
		
		String query = "DELETE FROM tRequests WHERE RQID = '" + RQID + "';";
		dbConn.DBUpdate(query);
		
		query = "DELETE FROM tPassengers WHERE RQID = '" + RQID + "';";
		dbConn.DBUpdate(query);
		return 0;
	}
	
	@Override
	public boolean validateTrip(String emailAddress, String tripName) {
	
		//Check to see if the user is already in memory.
		Trip trip = trips.get( emailAddress+tripName );
		System.out.println(11);
		if(trip != null){
			System.out.println("Trip found in the trips hashmap, validate returning true");
			return true;
		}
		System.out.println(12);
		//If not, attempt to load the user from the DB.
		//TODO CHECK THAT THIS IS CORRECT
		String query = "SELECT * FROM tTrips WHERE Owner = '" + emailAddress + "' AND Name ='"+tripName+"';";
		ResultSet rs = dbConn.queryDB( query );
		boolean found = false;
		System.out.println(13);	
		try{
			
			int check = 0;
			while( rs.next() ){
				check++;
				if( check > 1 )
					throw new SQLException("More than one result returned on a unique query.");
				System.out.println("Trip found in the database, validate returning true");
				found = true;
				System.out.println(14);
			}

			rs.close();
			System.out.println(15);
			if( !found )
				return false;
			
		}
		catch( SQLException e ){
			//Print an error and exit.
			//TODO be more graceful.
			System.err.println("ERROR: SQL Error while loading trip.");
			System.exit(1);
		}
		System.out.println(16);
		return found;
		
	}
        
	public ArrayList<RouteInterface> getUserRoutes(String rutgersEmail){
		
		/*
		 * Use DatabaseConnection class to start a connection, and call queryDB to setup query
		 * 
		 * databaseconnection will query the db for routes belonging to the 
		 * user and return them in an arraylist
		 * 
		 * Return the ArrayList (ArrayList may be empty if user has no routes; or null if the 
		 * user doesn't exist).
		 */
		
		String query = "SELECT * FROM tRoutes where Owner = '"+rutgersEmail+"';";
    	
    	ResultSet rs = null;
    	ArrayList<RouteInterface> al = new ArrayList<RouteInterface>();
    	
    	try{
    		rs = dbConn.queryDB( query );
    		Route route;
	    	while( rs.next() ){
	    		
	    		LandmarkInterface start = getLandmark( rutgersEmail, rs.getString("StartPosition"));
	    		LandmarkInterface end = getLandmark( rutgersEmail, rs.getString("EndPosition"));
	    		
	    		route = new Route( rutgersEmail, rs.getString("Name"), start, end);
	    		
						
				routes.put( rutgersEmail + rs.getString("Name"), route );
				al.add(route);
	    	}
    	}
    	catch( SQLException e ){
    		//TODO
    		e.printStackTrace();
    	}
    	return al;
    }
    	
    	
    public ArrayList<LandmarkInterface> getUserLandmarks(String rutgersEmail){
    	
    	/*
    	 * Use DatabaseConnection class to start a connection, and call queryDB to setup query
    	 * 
    	 * databaseconnection will query the db for landmarks belonging to the 
    	 * user and return them in an arraylist
    	 * 
    	 * Return the ArrayList (ArrayList may be empty if user has no routes; or null if the 
		 * user doesn't exist).
    	 */
    	
    	String query = "SELECT * FROM tLandmarks where Owner = '"+rutgersEmail+"';";
    	
    	ResultSet rs = null;
    	ArrayList<LandmarkInterface> al = new ArrayList<LandmarkInterface>();
    	
    	try{
    		rs = dbConn.queryDB( query );
    		Landmark landmark;
	    	while( rs.next() ){
	    		
	    		landmark = new Landmark(rs.getString("Owner"), rs.getString("Name"), rs.getString("StreetAddress"), rs.getString("City"), rs.getString("State"), rs.getString("ZipCode"));
				landmark.setRegion(rs.getString("Region"));
				landmark.setLatitude(rs.getDouble("Latitude"));
				landmark.setLongitude(rs.getDouble("Longitude"));
				landmark.setLMID(rs.getInt("LMID"));
						
				landmarks.put( rutgersEmail + rs.getString("Name"), landmark );
				al.add(landmark);
	    	}
    	}
    	catch( SQLException e ){
    		//TODO
    		e.printStackTrace();
    	}
    	
    	
    	
    	return al;
    }

	@Override
	public TimeIntervalInterface createTimeInterval(Date start, long duration) {
		
		/*
		 * Ensure params are valid.
		 * 
		 * Generate a TimeInterval wrapping the start time and duration.
		 * Call the associated TimeInterval constructor
		 * 
		 * Return interface if creation successful; null otherwise
		 */
		
		//TEMP METHOD BY YURIY. Doesn't store the TI in DB because we don't have a table for it
		//System.out.println(duration);
		TimeInterval ti = new TimeInterval(start, duration);
		
		return ti;
	} 

	
	public Iterator<TripInterface> searchTrips(RouteInterface route) {
		/*	
	public ArrayList<TripInterface> searchTrips( RouteInterface route ){
	*/	
		if( route == null )
			return null;
		
		//This selects trips that match the addresses of the route attached to the request.
		String queryA = "SELECT tTrips.Name, tTrips.Owner " +
				"FROM tTrips "+
				"INNER JOIN tRoutes ON tTrips.Route = tRoutes.RID "+
				"INNER JOIN tLandmarks start ON tRoutes.StartPosition = start.LMID "+
				"INNER JOIN tLandmarks end ON tRoutes.EndPosition = end.LMID "+
				"WHERE start.StreetAddress = '"+route.getPointOfDeparture().getStreetAddress()+"' "+
				"AND start.ZipCode = '"+route.getPointOfDeparture().getZipCode()+"' "+
				"AND end.StreetAddress = '"+route.getPointOfArrival().getStreetAddress()+"' "+
				"AND end.ZipCode = '"+route.getPointOfArrival().getZipCode()+"';";
		
		//Submit the request
		ResultSet rs = dbConn.queryDB(queryA);
		
		//Generate and return the iterator.
		RSTripsIterator iterator = new RSTripsIterator(rs);
		
		return iterator;
	}
	
	protected static DatabaseConnection getDBConn(){
		return dbConn;
	}
	
	public ArrayList<TripInterface> getUserTrips(String rutgersEmail){
		String query = "SELECT * FROM tTrips where Owner = '"+rutgersEmail+"';";
    	
    	ResultSet rs = null;
    	ArrayList<TripInterface> al = new ArrayList<TripInterface>();
    
    	
    	try{
    		rs = dbConn.queryDB( query );

    		while( rs.next() ){
    			Trip trip = trips.get( rs.getString("Owner") + rs.getString("Name") );
    			if (trip==null){
    			
	    			//GET ROUTE
	    			int routeId = rs.getInt("Route");
	    			String routeQuery = "SELECT * FROM tRoutes where RID = '" + routeId + "';";
	    			ResultSet routeRS = dbConn.queryDB(routeQuery);
	    			routeRS.next();
	    			String routeName = routeRS.getString("Name");
	    			RouteInterface r = getRoute(rutgersEmail, routeName);
	    			routeRS.close();
	    			
	    			//CREATE TIME INTERVALS
	    			TimeIntervalInterface timeOfDeparture = new TimeInterval(new Date(rs.getLong("Start")), rs.getLong("StartDuration"));
	    			TimeIntervalInterface timeOfArrival = new TimeInterval(new Date(rs.getLong("End")), rs.getLong("EndDuration"));
	    			//GET TID
	    			int TID = 0;
	    			query = "SELECT * FROM tTrips WHERE Owner = '" + rutgersEmail + "' AND Name = '" + rs.getString("Name")  + "';";
	    			ResultSet rss = dbConn.queryDB( query );
	    			try{
	    				rss.next();
	    				TID = rss.getInt("TID");
	    				rss.close();
	    			} catch (Exception e){System.out.println("This should never happen");}
	    			//CREATE TRIP
		    		Trip t = new Trip(rs.getString("Name"), rs.getString("Owner"), rs.getInt("maxPassengers"), r, timeOfDeparture, timeOfArrival, TID);
							
					trips.put( rutgersEmail + rs.getString("Name"), t );
					al.add(t);
    			} else {
    				al.add(trip);
    			}
    			
	    	}
    		rs.close();
    	}
    	catch( SQLException e ){
    		//TODO
    		e.printStackTrace();
    	}
    	return al;
		
		
	}
	
	public ArrayList<RequestInterface> getUserRequests(String rutgersEmail){
		String query = "SELECT * FROM tRequests where Owner = '"+rutgersEmail+"';";
    	
    	ResultSet rs = null;
    	ArrayList<RequestInterface> al = new ArrayList<RequestInterface>();
    
    	
    	try{
    		rs = dbConn.queryDB( query );

    		while( rs.next() ){
    			Request request = requests.get( rs.getString("Owner") + rs.getString("Name") );
    			if (request==null){
    			
	    			//GET ROUTE
	    			int routeId = rs.getInt("Route");
	    			String routeQuery = "SELECT * FROM tRoutes where RID = '" + routeId + "';";
	    			ResultSet routeRS = dbConn.queryDB(routeQuery);
	    			routeRS.next();
	    			String routeName = routeRS.getString("Name");
	    			RouteInterface r = getRoute(rutgersEmail, routeName);
	    			routeRS.close();
	    			
	    			//CREATE TIME INTERVALS
	    			TimeIntervalInterface timeOfDeparture = new TimeInterval(new Date(rs.getLong("Departure")), rs.getLong("DepartureDuration"));
	    			TimeIntervalInterface timeOfArrival = new TimeInterval(new Date(rs.getLong("Arrival")), rs.getLong("ArrivalDuration"));
	    			
	    		    //protected Request(String name, String requester, RouteInterface route, TimeIntervalInterface timeOfDeparture, TimeIntervalInterface timeOfArrival ){
	
	    			
	    			//GET RQID
	    			int RQID = 0;
	    			query = "SELECT * FROM tRequests WHERE Owner = '" + rutgersEmail + "' AND Name = '" + rs.getString("Name")  + "';";
	    			ResultSet rss = dbConn.queryDB( query );
	    			try{
	    				rss.next();
	    				RQID = rss.getInt("RQID");
	    				rss.close();
	    			} catch (Exception e){System.out.println("This should never happen");}
	    			
	    			//CREATE REQUEST
		    		Request t = new Request(rs.getString("Name"), rs.getString("Owner"), r, timeOfDeparture, timeOfArrival, RQID);
							
					requests.put( rutgersEmail + rs.getString("Name"), t );
					al.add(t);
    			}else {
					al.add(request);	
				}
	    	}
    		rs.close();
    	}
    	catch( SQLException e ){
    		//TODO
    		e.printStackTrace();
    	}
    	return al;
		
		
		
	}
	/*
	@Override
	public RouteInterface registerRoute(String rutgersEmail, String routeName,
			String streetAddressDep, String cityDep, String stateDep,
			String zipCodeDep, String streetAddressArr, String cityArr,
			String stateArr, String zipCodeArr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<TripInterface> searchTrips2(RouteInterface route) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet searchRoutes(String fieldName, String fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet searchLandmarks(String fieldName, String fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet searchRequests(String fieldName, String fieldValue) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}