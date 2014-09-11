package model;

//import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * DBUI is the interface into the Model package.  Specifically abstracts the implementation
 * of the class representing the connection to the database backing the HitchHikeRU web- 
 * and SMS-application.
 * 
 * @author Thomas Travis
 *
 */
public interface Transactor {

	//User methods
	
	/**
	 * Return an interface to the user object wrapping the given email address.
	 * 
	 * @param emailAddress
	 * @return the requested UserInterface if it exists; null otherwise
	 */
	public UserInterface getUser( String emailAddress );
	
	/**
	 * Register a user into the database.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param password
	 * @param carDescription
	 * @param numSeats
	 * @param deviationDistance
	 * @return the interface to the newly created user if inserted successfully; null otherwise
	 * @author Thomas Travis
	 */
	public UserInterface registerUser( String firstName, String lastName, String emailAddress, 
			String password, String carDescription, int numSeats, double deviationDistance );
	
	/**
	 * Remove the user with the given e-mail address from the database.
	 * 
	 * @param emailAddress
	 * @return the UserInterface for the target user if successfully removed; null otherwise
	 * @author Thomas Travis
	 */
	public UserInterface removeUser( String emailAddress );
	
	/**
	 * Check whether the user with the given e-mail address is in the database.
	 * 
	 * @param emailAddress
	 * @return true if the user is in the database; false otherwise.
	 * @author Thomas Travis
	 */
	public boolean validateUser( String emailAddress );
	
	
	/**
	 * Return an interface to the landmark object wrapping the given name.
	 * 
	 * @param name
	 * @return the LandmarkInterface, if the Landmark exists; null otherwise
	 * @author Thomas Travis
	 */
	public LandmarkInterface getLandmark( String rutgersEmail, String landmarkName );
	
	/**
	 * Register a landmark in the database.
	 * 
	 * @param name
	 * @param streedAddress
	 * @param City
	 * @param State
	 * @param zipCode
	 * @return the LandmarkInterface to the newly registered Landmark, if inserted successfully; null otherwise
	 * @author Thomas Travis
	 */
	public LandmarkInterface registerLandmark( String rutgersEmail, String landmarkName, String streedAddress, String City, String State, String zipCode, String region, double longitude, double latitude );
	
	/**
	 * Remove the landmark with the given name from the database.
	 * 
	 * @param name
	 * @return the LandmarkInterface if the Landmark is successfully removed; null otherwise
	 * @author Thomas Travis
	 */
	public LandmarkInterface removeLandmark( String rutgersEmail, String landmarkName );
	
	/**
	 * Check whether the landmark is in the database.
	 * 
	 * @param name
	 * @return true if the landmark is in the database; false otherwise
	 * @author Thomas Travis
	 */
	public boolean validateLandmark( String rutgersEmail, String landmarkName );
	
	//Route methods
	
	/**
	 * Return an interface to the specified Route object.
	 * 
	 * @param routeName
	 * @return the RouteInterface, if the Route exists; null otherwise
	 * @author Thomas Travis
	 */
	public RouteInterface getRoute( String rutgersEmail, String routeName );
	
	/**
	 * Register a new route in the database.
	 * 
	 * @param routeName
	 * @param pointOfDeparture
	 * @param pointOfArrival
	 * @return the RouteInterface for the Route if successfully inserted; null otherwise
	 * @author Thomas Travis
	 */
	public RouteInterface registerRoute( String rutgersEmail, String routeName, LandmarkInterface pointOfDeparture, LandmarkInterface pointOfArrival );
	
	/**
	 * Regiser a new route in the database.
	 * 
	 * @param routeName
	 * @param streetAddressDep
	 * @param cityDep
	 * @param stateDep
	 * @param zipCodeDep
	 * @param streetAddressArr
	 * @param cityArr
	 * @param stateArr
	 * @param zipCodeArr
	 * @return the RouteInterface for the Route if successfully inserted; null otherwise
	 * @author Thomas Travis
	 */
	//public RouteInterface registerRoute( String rutgersEmail,String routeName, String streetAddressDep, String cityDep, String stateDep, String zipCodeDep,
	//		String streetAddressArr, String cityArr, String stateArr, String zipCodeArr );
	
	/**
	 * Remove a route from the database.
	 * 
	 * @param routeName
	 * @return the RouteInterface to be removed, if it exists; null otherwise
	 * @author Thomas Travis
	 */
	public RouteInterface removeRoute( String rutgersEmail, String routeName );
	
	/**
	 * Check if the target route is in the database.
	 * 
	 * @param routeName
	 * @return true if the Route is in the database; false otherwise
	 */
	public boolean validateRoute( String rutgersEmail, String routeName );
	
	//Request methods
	
	/**
	 * Return an interface to the target Request
	 * 
	 * @param requestName
	 * @return the RequestInterface, if the request exists; null otherwise
	 * @author Thomas Travis
	 */
	public RequestInterface getRequest( String rutgersEmail, String requestName );
	
	/**
	 * Register a new request in the database.
	 * 
	 * @param requestName
	 * @param requester
	 * @param route
	 * @param timeOfDeparture
	 * @param timeOfArrival
	 * @return the interface to the newly registered request, if successful; null otherwise
	 * @author Thomas Travis
	 */
	public RequestInterface registerRequest(String emailAddress, String requestName,
            RouteInterface route, TimeIntervalInterface timeOfDeparture,
            TimeIntervalInterface timeOfArrival) ;
	/**
	 * Register a new request in the database.
	 * 
	 * @param requestName
	 * @param streetAddressDep
	 * @param cityDep
	 * @param stateDep
	 * @param zipCodeDep
	 * @param streetAddressArr
	 * @param cityArr
	 * @param stateArr
	 * @param zipCodeArr
	 * @param timeOfDeparture
	 * @param timeOfArrival
	 * @return the interface to the newly registered request, if successful; null otherwise
	 * @autho Thomas Travis
	 */
	/* LANDAMRKS ARE REQUIRED
	public RequestInterface registerRequest( String rutgersEmail, String requestName, String streetAddressDep, String cityDep, String stateDep, String zipCodeDep,
			String streetAddressArr, String cityArr, String stateArr, String zipCodeArr, TimeIntervalInterface timeOfDeparture, TimeIntervalInterface timeOfArrival );
			*/
	
	/**
	 * Cancel the target request, removing it from the database.
	 * 
	 * @param requestName
	 * @return the interface to the cancelled request, if it exists; null otherwise
	 * @authot Thomas Travis
	 */
	public RequestInterface cancelRequest( String rutgersEmail, String requestName );
	
	/**
	 * Determine whether the target request is in the database.
	 * 
	 * @param requestName
	 * @return true if the request is in the database; false otherwise
	 * @author Thomas Travis
	 */
	public boolean validateRequest( String rutgersEmail, String requestName );
	
	//Trip methods
	
	/**
	 * Return an interface for the target trip.
	 * 
	 * @param tripName
	 * @return the TripInterface if the target trip exists; null otherwise.
	 */
	public TripInterface getTrip( String rutgersEmail, String tripName );
	
	/**
	 * Register a trip in the database.
	 * 
	 * @param tripName
	 * @param driver
	 * @param maxPassengers
	 * @param route
	 * @param timeOfDeparture
	 * @param timeOfArrival
	 * @return the interface to the newly inserted Trip, if successful; null otherwise
	 * @author Thomas Travis
	 */
	public TripInterface registerTrip( String name, String driver, int maxPassengers, RouteInterface route, 
			 TimeIntervalInterface timeOfDeparture, TimeIntervalInterface timeOfArrival );
	
	/**
	 * Cancel a trip, removing it from the database.
	 * 
	 * @param tripName
	 * @return the interface to the removed Trip, if successful; null otherwise
	 * @author Thomas Travis
	 */
	public TripInterface cancelTrip( String rutgersEmail, String tripName );
	
	/**
	 * Determine whether a Trip is in the database.
	 * 
	 * @param tripName
	 * @return true if the Trip is in the database; false otherwise
	 * @author Thomas Travis
	 */
	public boolean validateTrip( String rutgersEmail, String tripName );
	
	/**
	 * Returns a new TimeIntervalInterface
	 * 
	 * @param start The start date
	 * @param duration Duration from the start date
	 * @return A time interval 
	 */
	public TimeIntervalInterface createTimeInterval(Date start, long duration);

	/**
	 * Returns a ResultSet from the User table that matches field name and field value when queried to the database 
	 * ** MUST BE ADDRESSED FURTHER CURRENTLY DESIGN BY CONTRACT IMPLEMENTATION ON FIELD NAMES ASSOCIATED WITH
	 * CORRESPONDING OBJECTS**
	 * 
	 * @param fieldName The objects field
	 * @param fieldValue The value of that field
	 * @return Result sets containing information
	 */
	//public ResultSet searchUser(String fieldName, String fieldValue);


	/**
	 * Returns an Iterator from the Trips table that matches field name and field value when queried to the database 
	 * ** MUST BE ADDRESSED FURTHER CURRENTLY DESIGN BY CONTRACT IMPLEMENTATION ON FIELD NAMES ASSOCIATED WITH
	 * CORRESPONDING OBJECTS**
	 * 
	 * @param fieldName The objects field
	 * @param fieldValue The value of that field
	 * @return Result sets containing information
	 */
	public Iterator<TripInterface> searchTrips(RouteInterface route);
	

	/**
	 * Returns a ResultSet from the Routes table that matches field name and field value when queried to the database 
	 * ** MUST BE ADDRESSED FURTHER CURRENTLY DESIGN BY CONTRACT IMPLEMENTATION ON FIELD NAMES ASSOCIATED WITH
	 * CORRESPONDING OBJECTS**
	 * 
	 * @param fieldName The objects field
	 * @param fieldValue The value of that field
	 * @return Result sets containing information
	 */
	//public ResultSet searchRoutes(String fieldName, String fieldValue);
	

	/**
	 * Returns a ResultSet from the Landmarks table that matches field name and field value when queried to the database 
	 * ** MUST BE ADDRESSED FURTHER CURRENTLY DESIGN BY CONTRACT IMPLEMENTATION ON FIELD NAMES ASSOCIATED WITH
	 * CORRESPONDING OBJECTS**
	 * 
	 * @param fieldName The objects field
	 * @param fieldValue The value of that field
	 * @return Result sets containing information
	 */
	//public ResultSet searchLandmarks(String fieldName, String fieldValue);
	

	/**
	 * Returns a ResultSet from the Request table that matches field name and field value when queried to the database 
	 * ** MUST BE ADDRESSED FURTHER CURRENTLY DESIGN BY CONTRACT IMPLEMENTATION ON FIELD NAMES ASSOCIATED WITH
	 * CORRESPONDING OBJECTS**
	 * 
	 * @param fieldName The objects field
	 * @param fieldValue The value of that field
	 * @return Result sets containing information
	 */
	//public ResultSet searchRequests(String fieldName, String fieldValue);
	
	/**
	 * Retrieves the user's saved routes
	 * 
	 * @param rutgersEmail
	 * @return list of saved routes belonging to the user
	 */
	public ArrayList<RouteInterface> getUserRoutes(String rutgersEmail);
	
	
	/**
	 * Retrieves the user's saved landmarks
	 * 
	 * @param rutgersEmail
	 * @return list of saved landmarks belonging to the user
	 */
	public ArrayList<LandmarkInterface> getUserLandmarks(String rutgersEmail);
	
	public ArrayList<TripInterface> getUserTrips(String rutgersEmail);
	
	public ArrayList<RequestInterface> getUserRequests(String rutgersEmail);
	
	public int cancelTrip(int TID);
	
	public int cancelRequest(int RQID);
}
