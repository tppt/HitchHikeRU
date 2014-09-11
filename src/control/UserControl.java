package control;


import java.util.ArrayList;

import model.*;

/**
 * User control is responsible for creating, authenticating, and modifying user accounts and while
 * doing so vetting all data.
 * 
 * @author Kyle Waranis
 */
public class UserControl {
	
	private static UserControl userControl = null;
	
	private UserControl() {

	}
	
	/**
	 * Singleton pattern constructor. This should be called instead of the standard constructor when needing
	 * a UserControl object.
	 * 
	 * @return The global UserControl object.
	 */
	public static synchronized UserControl getInstance() {
		if(userControl == null)
			userControl = new UserControl();
		
		return userControl;
	}
	
	/**
	 * Queries Transactor to see if the user exists and if so returns the interface to it.
	 * 
	 * @param emailAddress The unique key for the desired user.
	 * @return Returns the desired user, or null if it does not exist.
	 */
	public UserInterface getUser(String rutgersEmail) {
		
		//Check for garbage
		if(rutgersEmail == null)
			return null;
		if(rutgersEmail.equals(""))
			return null;
		
		//Retrieve and return the UserInterface.
		UserInterface user = Main.getTransactor().getUser(rutgersEmail);
		
		if(user == null)
			return null;
		else
			return user;
	}
	
	/**
	 * Generates a user from the given inputs.
	 * 
	 * @param rutgersEmail The primary email address for the account.
	 * @param fname The first name of the user.
	 * @param lname The last name of the user.
	 * @param password the desired password for the account.
	 * @return Returns the desired user, or null if it does not exist.
	 */
	public UserInterface createUser( String firstName, String lastName, String rutgersEmail, 
			String password, String carDescription, int numSeats, double deviationDistance ) {
		
		//Check for garbage
		if(firstName == null || lastName == null || rutgersEmail == null || password == null || carDescription == null || numSeats < 0 || deviationDistance < 0)
			return null;
		if(firstName.equals("") || lastName.equals("") || rutgersEmail.equals("") || password.equals(""))
			return null;
		
		//Create and return the UserInterface.
		UserInterface user = Main.getTransactor().registerUser(firstName, lastName, rutgersEmail, password, carDescription, numSeats, deviationDistance);
		
		if(user == null)
			return null;
		else
			return user;
	}
	
	/**
	 * Removes the user from the database.
	 * 
	 * @returns Returns true if the user was removed successfully, else false.
	 */
	public boolean removeUser( String rutgersEmail) {
		
		//Check for garbage.
		if(rutgersEmail == null)
			return false;
		if(rutgersEmail.equals(""))
			return false;
		
		//Remove the user object and return.
		Main.getTransactor().removeUser(rutgersEmail);
		
		return true;
	}
	
	/**
	 * Tests the user's password and if correct assigns them as logged in for the given session.
	 * 
	 * @param user The user attempting login.
	 * @param password The password the user has provided.
	 * @param session The session that will be assigned to the user on success.
	 * @return Returns true if the login procedures have completed successfully, else false.
	 */
	public boolean login(UserInterface user, String password) {
		
		//Check for garbage
		if(user== null || password == null)
			return false;
		if(password.equals(""))
			return false;
		
		//Get the correct password.
		String correctPass = user.getPassword();

		return password.equals(correctPass);
	}
	
	/**
	 * Logs the user out and closes the session if applicable.
	 * 
	 * @param user the user requesting logout.
	 * @return Returns true if the user has successfully logged out of the system.
	 */
	public boolean logout(UserInterface user) {
		
		//Check for garbage
		if(user == null)
			return false;
		
		//Logout and return.
		return true;
	}

	/**
	 * Checks to see if the given data is valid for generating a user account, including verifying that the
	 * current email address is not in use.
	 * 
	 * @param rutgersEmail The Rutgers email address, must be unique to be validated.
	 * @param fname The first name of the user.
	 * @param lname The last name of the user.
	 * @param password the desired password for the account.
	 * @return Returns true if all the data is valid to create a user object.
	 */
	public boolean validateUserData(String rutgersEmail, String fname, String lname, String password) {
		
		//Check for garbage
		if(rutgersEmail == null || fname == null || lname == null || password == null)
			return false;
		if(rutgersEmail.equals("") || fname.equals("") || lname.equals("") || password.equals(""))
			return false;
		
		//Check to see if the email is already taken and return.
		return Main.getTransactor().validateUser(rutgersEmail);
	}

	/**
	 * Retrieves the user's saved routes
	 * 
	 * @param rutgersEmail
	 * @return list of saved routes belonging to the user
	 */
	public ArrayList<RouteInterface> getSavedRoutes(String rutgersEmail){
		ArrayList<RouteInterface> routes = Main.getTransactor().getUserRoutes(rutgersEmail);
				
		return routes;
	}
	
	
	/**
	 * Retrieves the user's saved landmarks
	 * 
	 * @param rutgersEmail
	 * @return list of saved landmarks belonging to the user
	 */
	public ArrayList<LandmarkInterface> getSavedLandmarks(String rutgersEmail){
		ArrayList<LandmarkInterface> landmarks = Main.getTransactor().getUserLandmarks(rutgersEmail);
				
		return landmarks;
	}
	
	public ArrayList<TripInterface> getUserTrips(String rutgersEmail){
		UserInterface u = UserControl.getInstance().getUser(rutgersEmail);
		String email = u.getRutgersEmail();
		ArrayList<TripInterface> trips = Main.getTransactor().getUserTrips(email); 
		return trips;
	}
	
	public ArrayList<RequestInterface> getUserRequests(String rutgersEmail){
		UserInterface u = UserControl.getInstance().getUser(rutgersEmail);
		String email = u.getRutgersEmail();
		ArrayList<RequestInterface> requests = Main.getTransactor().getUserRequests(email); 
		return requests;
	}

}
