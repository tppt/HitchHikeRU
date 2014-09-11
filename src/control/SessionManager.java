package control;

import java.util.ArrayList;
import java.util.Date;

import model.*;

/**
 * SessionManager oversees the general flow of a users interaction with the program. It manages
 * when data is sent back and forth and responds to external requests for data.
 * 
 * @author Kyle Waranis
 */
public abstract class SessionManager {
	
	/**
	 * Retrieves the User object and tests login information.
	 * 
	 * @param rutgersEmail 
	 * @param password
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public abstract int login(String rutgersEmail, String password);
	
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
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public abstract int registerUser(String firstName, String lastName, String rutgersEmail, 
			String password, String carDescription, int numSeats, double deviationDistance);
	
	/**
	 * Takes in the trip parameters and generates a Trip object.
	 * 
	 * @param rutgersEmail
	 * @param name
	 * @param fromLName
	 * @param toLName
	 * @param start
	 * @param passengers
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public abstract int submitTrip(String tripName, String rutgersEmail, String fromLName, String toLName, String start, int passengers);
	
	/**
	 * This takes in a requestInterface, registers it through Control, then matches it and returns the 
	 * suggested matches.
	 * 
	 * @return The ArrayList of matched trips.
	 */
	public abstract ArrayList<TripInterface> submitRequest(String requestName, String rutgersEmail, String fromLName, String toLName, String start, int passengers, int deviation);
	
	/**
	 * This takes in a list of trips that the passenger has requested rides from and records the information 
	 * through Control.
	 * 
	 * @return
	 */
	public abstract int submitPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail);
	
	
	/**
	 * Removes the requests passenger request to the driver from consideration.
	 * 
	 * @param requestName
	 * @param tripName
	 * @param tripRutgersEmail
	 * @param requestRutgersEmail
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public abstract int withdrawPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail);
	
	/**
	 * This retrieves a list of requests that have asked the driver of the given trip for a ride.
	 * 
	 * @return The list of requests that are asking the trip for a ride.
	 */
	public abstract ArrayList<RequestInterface> getPassengerRequests(String tripName, String rutgersEmail);
	
	/**
	 * This records that the given driver has accepted the given request
	 * 
	 * @return True if the operations are successful, else false.
	 */
	public abstract int acceptPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail);
	
	/**
	 * This records that the given driver has denied the given request.
	 * 
	 * @param requestName
	 * @param tripName
	 * @param tripRutgersEmail
	 * @param requestRutgersName
	 * @return 0 if successful, -1 on bad input, and 1 for other error.
	 */
	public abstract int denyPassengerRequest(String requestName, String tripName, String tripRutgersEmail, String requestRutgersEmail);

	public abstract ArrayList<RequestInterface> getPassengers(String tripName, String rutgersEmail);
		
	
	public abstract ArrayList<TripInterface> submitRequest(String requestName, String rutgersEmail, String fromLName, String toLName, Date date, int passengers, int deviation);

}
