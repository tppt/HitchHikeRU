package control;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import model.*;

/**
 * WebSessionManager oversees the general flow of a users interaction with the website. It manages
 * when data is sent back and forth and responds to website requests for data.
 * 
 * @author Kyle Waranis
 */
public class WebSessionManager extends SessionManager {
	
	private static WebSessionManager webSessionManager = null;
	
	/**
	 * The constructor for session manager initializes the given variables but does not launch 
	 * threads.
	 */
	private WebSessionManager() {
		
	}
	
	/**
	 * Singleton pattern constructor. This should be called instead of the standard constructor when needing
	 * a SessionManager object.
	 * 
	 * @return The global SessionManager object.
	 */
	public static WebSessionManager getInstance()
	{
		if( webSessionManager == null )
			webSessionManager = new WebSessionManager();
		
		return webSessionManager;
	}
	
	/**
	 * Retrieves the User object and tests login information.
	 * 
	 * @param rutgersEmail 
	 * @param password
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public int login(String rutgersEmail, String password)
	{
		//Check for garbage
		if(rutgersEmail == null || password == null)
			return -1;
		if(rutgersEmail.equals("") || password.equals(""))
			return -1;
		
		//Load the user object.
		UserInterface user = UserControl.getInstance().getUser(rutgersEmail);
		
		//Perform login.
		boolean sucessful = UserControl.getInstance().login(user, password);
		
		//Return appropriately.
		if(sucessful)
			return 0;
		else
			return 1;
	}
	
	/**
	 * Validates the user data and sends the registration email.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param rutgersEmail
	 * @param password
	 * @param carDescription
	 * @param numSeats
	 * @param deviationDistance
	 * @return 0 if successful, otherwise 1.
	 */
	public int registerUser(String firstName, String lastName, String rutgersEmail, 
			String password, String carDescription, int numSeats, double deviationDistance) {
		
		//Check for garbage.
		if(firstName == null || lastName == null || rutgersEmail == null || password == null || carDescription == null || numSeats < 0 || deviationDistance < 0)
			return -1;
		if(firstName.equals("") || lastName.equals("") || rutgersEmail.equals("") || password.equals("") || carDescription.equals(""))
			return -1;
		
		//Check to see if the email address is an incorrect email address.
		String rutgersEmailTmp = rutgersEmail.toLowerCase();
		if(!rutgersEmailTmp.matches("[^@]@[^@]"))
			if(!rutgersEmailTmp.endsWith("rutgers.edu"))
				return 1;
		
		//Validate the user data to see if its already registered.
		if(UserControl.getInstance().validateUserData(rutgersEmail, firstName, lastName, password))
			return 1;
		
		//TODO Send the registration email.
		
		//Create the new user.
		UserControl.getInstance().createUser(firstName, lastName, rutgersEmail, password, carDescription, numSeats, deviationDistance);
		
		return 0;
	}
	
	/**
	 * Takes in the trip parameters and generates a Trip object.
	 * 
	 * @param rutgersEmail
	 * @param name
	 * @param fromLName
	 * @param toLName
	 * @param departure
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public int submitTrip(String tripName, String rutgersEmail, String fromLName, String toLName, String start, int passengers) {
		
		//Check for garbage.
		if(rutgersEmail == null || tripName == null || fromLName == null || toLName == null || start == null || passengers < 0)
			return -1;
		if(rutgersEmail.equals("") || tripName.equals("") || fromLName.equals("") || toLName.equals(""))
			return -1;
		
		//Generate the 'child' objects.
		UserInterface user = UserControl.getInstance().getUser(rutgersEmail);
		//Get the 'admin' user profile.
		UserInterface adminUser = UserControl.getInstance().getUser("testuser@rutgers.edu");
		
		//Check for errors on User creation.
		if(user == null || adminUser == null)
			return -1;
		System.out.println(3);
		LandmarkInterface fromLandmark = LandmarkControl.getInstance().getLandmark(fromLName, adminUser);
		LandmarkInterface toLandmark = LandmarkControl.getInstance().getLandmark(toLName, adminUser);
		
		if(fromLandmark == null || toLandmark == null)
			return 1;
		
		//Check for landmark equality.
		if(fromLandmark.getStreetAddress().equals(toLandmark.getStreetAddress()))
			if(fromLandmark.getZipCode().equals(toLandmark.getZipCode()))
				return 1;
		
		//Generate a Date from the String.
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
	    Date date;
		try {date = df.parse(start);} 
		catch (ParseException e) {
			return -1;}
		System.out.println(4);
		
		//Check if the date is before 5 minutes ago.
		Date now = new Date();
		Date FiveMinAgo = new Date(now.getTime() - 600000);
		if(date.before(FiveMinAgo))
			return -1;
		
		TimeIntervalInterface time = TimeIntervalControl.getInstance().createTimeInterval(date, 30);
		
		if(fromLandmark == null || toLandmark == null)
			return 1;
		System.out.println(5);
		//Generate the Trip object.
		TripInterface trip = TripControl.getInstance().createTrip(user, tripName, fromLandmark, toLandmark, time, passengers);
		
		if(trip == null){
			return 1;
		}else
			return 0;
	}
	
	/**
	 * This takes in a requestInterface matches it and returns the suggested matches.
	 * 
	 * @return The ArrayList of matched trips. Null if the request already exists.
	 */
	public ArrayList<TripInterface> submitRequest(String requestName, String rutgersEmail, String fromLName, String toLName, String start, int passengers, int deviation) {
		
		//Check for garbage.
		if(requestName == null || rutgersEmail == null || fromLName == null || toLName == null || start == null || passengers < 0)
			return null;
		
		//Load the user object.
		UserInterface user = UserControl.getInstance().getUser(rutgersEmail);
		//Get the 'admin' user profile.
		UserInterface adminUser = UserControl.getInstance().getUser("testuser@rutgers.edu");
		
		if(user == null || adminUser == null)
			return null;
		
		//Load the remaining child objects.
		LandmarkInterface fromLandmark = LandmarkControl.getInstance().getLandmark(fromLName, adminUser);
		LandmarkInterface toLandmark = LandmarkControl.getInstance().getLandmark(toLName, adminUser);

		if(fromLandmark == null || toLandmark == null)
			return null;
		
		//Check for landmark equality.
		if(fromLandmark.getStreetAddress().equals(toLandmark.getStreetAddress()))
			if(fromLandmark.getZipCode().equals(toLandmark.getZipCode()))
				return null;
		
		//Generate a Date from the String.
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
		Date date;
		try {date = df.parse(start);} 
		catch (ParseException e) {return null;}
		
		//Check if the date is before 5 minutes ago.
		Date now = new Date();
		Date FiveMinAgo = new Date(now.getTime() - 600000);
		if(date.before(FiveMinAgo))
			return null;
		
		TimeIntervalInterface time = TimeIntervalControl.getInstance().createTimeInterval(date, deviation);
		
		//Retrieve the request from Control.
		RequestInterface request = RequestControl.getInstance().createRequest(user, requestName, fromLandmark, toLandmark, time);
		
		//Submit it to matchmaker.
		ArrayList<TripInterface> trips = MatchMaker.getInstance().findTripMatches(request);
		
		//Return the list of recommended Trips to take.
		return trips;
		
	}
	
	/**
	 * This takes in a list of trips that the passenger has requested rides from and records the information 
	 * through Control.
	 * 
	 * @return
	 */
	public int submitPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail) {
		
		//check for garbage.
		if(requestName == null || tripName == null || tripRutgersEmail == null || requestRutgersEmail == null)
			return -1;
		
		//Retrieve the objects from Control.
		UserInterface tripUser = UserControl.getInstance().getUser(tripRutgersEmail);
		UserInterface requestUser = UserControl.getInstance().getUser(requestRutgersEmail);
		
		if(tripUser == null || requestUser == null)
			return -1;
		
		RequestInterface request = RequestControl.getInstance().getRequest(requestUser, requestName);
		
		if(request == null)
			return 1;
		
		TripInterface trip = TripControl.getInstance().getTrip(tripUser, tripName);
		
		if(trip == null)
			return 1;
		
		//Record that passenger has requested to be on this trip.
		trip.addAPassengerRequest(request);
		request.addAPassengerRequest(trip);
		
		return 0;
	}
	
	/**
	 * Removes the requests passenger request to the driver from consideration.
	 * 
	 * @param requestName
	 * @param tripName
	 * @param tripRutgersEmail
	 * @param requestRutgersEmail
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public int withdrawPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail) {
		
		//check for garbage.
		if(requestName == null || tripName == null || tripRutgersEmail == null || requestRutgersEmail == null)
			return -1;
		
		//Retrieve the objects from Control.
		UserInterface tripUser = UserControl.getInstance().getUser(tripRutgersEmail);
		UserInterface requestUser = UserControl.getInstance().getUser(requestRutgersEmail);
		
		if(tripUser == null || requestUser == null)
			return -1;
		
		RequestInterface request = RequestControl.getInstance().getRequest(requestUser, requestName);
		
		if(request == null)
			return 1;
		
		TripInterface trip = TripControl.getInstance().getTrip(tripUser, tripName);
		
		if(trip == null)
			return 1;
		
		//Record that passenger has withdrawn their request to be on this trip.
		trip.removeAPassengerRequest(request.getName(), request.getOwner());
		request.removeAPassengerRequest(trip.getName(), trip.getOwner());
		
		return 0;
	}
	
	public ArrayList<RequestInterface> getPassengers(String tripName, String rutgersEmail) {
		UserInterface user = UserControl.getInstance().getUser(rutgersEmail);
		TripInterface trip = TripControl.getInstance().getTrip(user, tripName);
		System.out.println("Getting passengers for: "+user.getRutgersEmail() + trip.getName()+ " "+trip.getTID());
		System.out.println("num passengers = "+trip.getCurrentNumberOfPassengers());
		return trip.getPassengers();
	}
	/**
	 * This retrieves a list of requests that have asked the driver of the given trip for a ride.
	 * 
	 * @return The list of requests that are asking the trip for a ride.
	 */
	public ArrayList<RequestInterface> getPassengerRequests(String tripName, String rutgersEmail) {
		
		//Check for garbage.
		//if(tripName == null)
		//	return null;
		
		//Retrieve the user.
		UserInterface user = UserControl.getInstance().getUser(rutgersEmail);
		
		//if(user == null)
		//	return null;
		
		//Retrieve the trip.
		TripInterface trip = TripControl.getInstance().getTrip(user, tripName);
		
		//if(trip == null)
		//	return null;
		
		//Return the list of requests.
		return trip.getPassengerRequests();
	}

	/**
	 * 
	 */
	public synchronized int acceptPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail) {

		//Check for garbage
		if(requestName == null || tripName == null || tripRutgersEmail == null || requestRutgersEmail == null)
			return 11;
		
		//Retrieve the objects from Control.
		UserInterface tripUser = UserControl.getInstance().getUser(tripRutgersEmail);
		UserInterface requestUser = UserControl.getInstance().getUser(requestRutgersEmail);
		
		if(tripUser == null || requestUser == null)
			return 12;
		
		RequestInterface request = RequestControl.getInstance().getRequest(requestUser, requestName);
		
		if(request == null)
			return 13;
		
		TripInterface trip = TripControl.getInstance().getTrip(tripUser, tripName);
		
		if(trip == null)
			return 14;
		
		//Set the confirmed entry for the request.
		if(request.getConfirmed() == null)
			request.setConfirmed(trip);
		
		
		
		//Add the request to the trips list of passengers.
		trip.addPassenger(request);
		
		//Remove the request from the list of PassengerRequests.
		request.removeAPassengerRequest(trip.getName(), trip.getOwner());
		trip.removeAPassengerRequest(request.getName(), request.getOwner());
		
		return 0;
	}
	
	/**
	 * This records that the given driver has denied the given request.
	 * 
	 * @param requestName
	 * @param tripName
	 * @param tripRutgersEmail
	 * @param requestRutgersName
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public int denyPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail)
	{
		
		//Check for garbage
		if(requestName == null || tripName == null || tripRutgersEmail == null || requestRutgersEmail == null)
			return -1;
		
		//Retrieve the objects from Control.
		UserInterface tripUser = UserControl.getInstance().getUser(tripRutgersEmail);
		UserInterface requestUser = UserControl.getInstance().getUser(requestRutgersEmail);
		
		if(tripUser == null || requestUser == null)
			return -1;
		
		RequestInterface request = RequestControl.getInstance().getRequest(requestUser, requestName);
		
		if(request == null)
			return 1;
		
		TripInterface trip = TripControl.getInstance().getTrip(tripUser, tripName);
		
		if(trip == null)
			return 1;
		
		//Set the confirmed entry for the request.
		if(request.getConfirmed() == null)
			return 1;
		
		request.setConfirmed(null);
		
		//Add the request to the trips list of passengers.
		trip.removePassenger(request);
		
		//Remove the request from the list of PassengerRequests.
		request.removeAPassengerRequest(trip.getName(), trip.getOwner());
		trip.removeAPassengerRequest(request.getName(), request.getOwner());
		
		return 0;
	}
	
	
	
public ArrayList<TripInterface> submitRequest(String requestName, String rutgersEmail, String fromLName, String toLName, Date date, int passengers, int deviation) {
		
		//Check for garbage.
		if(requestName == null || rutgersEmail == null || fromLName == null || toLName == null || passengers < 0)
			return null;
		
		//Load the user object.
		UserInterface user = UserControl.getInstance().getUser(rutgersEmail);
		//Get the 'admin' user profile.
		UserInterface adminUser = UserControl.getInstance().getUser("testuser@rutgers.edu");
		
		if(user == null || adminUser == null)
			return null;
		
		//Load the remaining child objects.
		LandmarkInterface fromLandmark = LandmarkControl.getInstance().getLandmark(fromLName, adminUser);
		LandmarkInterface toLandmark = LandmarkControl.getInstance().getLandmark(toLName, adminUser);

		if(fromLandmark == null || toLandmark == null)
			return null;
		

		TimeIntervalInterface time = TimeIntervalControl.getInstance().createTimeInterval(date, deviation);
		
		//Retrieve the request from Control.
		RequestInterface request = RequestControl.getInstance().createRequest(user, requestName, fromLandmark, toLandmark, time);
		
		//Submit it to matchmaker.
		ArrayList<TripInterface> trips = MatchMaker.getInstance().findTripMatches(request);
		
		//Return the list of recommended Trips to take.
		return trips;
		
	}
	
}
