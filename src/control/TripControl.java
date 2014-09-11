package control;

import java.util.Date;

import model.*;

/**
 * Trip control is responsible for creating, authenticating, and modifying trips and while
 * doing so vetting all data.
 * 
 * @author Kyle Waranis
 */
public class TripControl 
{
	
	private static TripControl tripControl = null;
	
	private TripControl() {

	}
	
	/**
	 * Singleton pattern constructor. This should be called instead of the standard constructor when needing
	 * a TripControl object.
	 * 
	 * @return The global TripControl object.
	 */
	public static synchronized TripControl getInstance() {
		if(tripControl == null)
			tripControl = new TripControl();
		
		return tripControl;
	}
	
	/**
	 * Checks if the given trip is present in Model.
	 * 
	 * @param name
	 * @param rutgersEmail
	 * @return True if the object exists, else false.
	 */
	public boolean validateTrip(String rutgersEmail, String name) {
		
		//Check for garbage.
		if(name == null || rutgersEmail == null)
			return false;
		if(name.equals("") || rutgersEmail.equals(""))
			return false;
		
		//Attempt to retrieve the request.
		TripInterface trip = Main.getTransactor().getTrip(rutgersEmail, name);
		
		//Check for null and return.
		if(trip == null)
			return false;
		else
			return true;
	}
	
	/**
	 * This verifies the correctness of all incoming parameters and queries Transactor for an interface
	 * to the required object.
	 * 
	 * @param user The owner of the trip.
	 * @param name The name of the trip.
	 * @returns Returns the generated TripInterface.
	 */
	//TODO The attributes to uniquely identify a trip are inconsistent between Model and Control.
	public TripInterface getTrip(UserInterface user, String name) {
		
		//Check for garbage
		if(user == null || name == null)
			return null;
		if(name.equals(""))
			return null;
		
		//Retrieve the trip and return it.
		TripInterface trip = Main.getTransactor().getTrip(user.getRutgersEmail(), name);
		
		if(trip == null){
			return null;
		}else
			return trip;
	}
	
	
	public TripInterface getTrip(String useremail, String name) {
		TripInterface trip = Main.getTransactor().getTrip(useremail, name);
		return trip;
	}
	
	/**
	 * This verifies the correctness of all incoming parameters and queries Transactor to create a Trip
	 * based on the given parameters.
	 * 
	 * @param user The owner of the trip.
	 * @param from The starting point of the trip.
	 * @param to The end point of the trip.
	 * @param date The time at which the user plans to arrive at "to".
	 * @param passengers The maximum number of passengers for this trip.
	 * @returns Returns the generated TripInterface.
	 */
	//TODO The attributes to create a trip are inconsistent between Model and Control.
	public TripInterface createTrip(UserInterface user, String name, LandmarkInterface from, LandmarkInterface to, TimeIntervalInterface timeInterval, int passengers) {
		System.out.println("in CreateTrip");
		//Check for garbage
		if(user == null || from == null || to == null || timeInterval == null){System.out.println("Null prams");
			return null;}
		
				
		//Get the route that describes this start/end pair.
		//TODO Model may want to consider accepting request generation from landmarkInterfaces, not just routes.

		RouteInterface route = Main.getTransactor().registerRoute(user.getRutgersEmail(), name, from, to);
		
		if(route == null){System.out.println("Create route failed");
			return null;}
		
		//Create and return the trip.
		//TODO The method already asks for the rutgers email, why also ask for the users name?
		//TODO Model should accept TimeInterval objects.
		//TripInterface trip = Main.getTransactor().registerTrip(user.getRutgersEmail(), name, user.getFirstName(), passengers, route, timeInterval);
		
		//Set both start, end times to the same timeInterval - this was to facilitate the constructor in model. - TT
		TripInterface trip = Main.getTransactor().registerTrip(name, user.getRutgersEmail(), passengers, route, timeInterval, timeInterval);
		
		if(trip == null){System.out.println("Create trip failed");
			return null;
		}else
			return trip;
	}
	
	/**
	 * Cancels the given trip object.
	 * NOTE: The caller is responsible for re-matching all requests that may have been
	 * matched to this request.
	 * 
	 * @param user The owner of the trip.
	 * @param request The trip to be cancelled.
	 * @returns Returns true if the trip as been successfully removed, else false.
	 */
	public boolean cancelTrip(String useremail, String tripname){
		UserInterface u = UserControl.getInstance().getUser(useremail);
		//Trip does not have any way to retrieve the name of the trip.
		Main.getTransactor().cancelTrip(u.getRutgersEmail(), tripname);
		
		return true;
	}
	
	public boolean cancelTrip(int TID){

		//Trip does not have any way to retrieve the name of the trip.
		int result = Main.getTransactor().cancelTrip(TID);
		if (result == 0)
			return true;
		else
			return false;
	}
}
