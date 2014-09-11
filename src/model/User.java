package model;

import java.util.ArrayList;

/**
 * Object storing known user information for passing between system components either set by user at
 * registration or defaulted values as well as car description if available
 * 
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public class User implements UserInterface
{
	
	private String rutgersEmail = null;								// Unique primary key for each User
	private ArrayList<String> emails = new ArrayList<String>();     // List of email addresses associated with this user
	private String password = null;									// The user's password
	private String firstName = null;								// First name of the User
	private String lastName = null;									// Last name of the User
	private double deviationDistance = 0.0;							// Distance willing to deviate
	private int numSeats = 0;										// Number of seats in the users car (IF DRIVER)
	private String carDescription = null;							// Description of users vehicle (IF DRIVER)
	
	/** Constructor for the User object
	 * 
	 * @param emailAddr The users email address (database primary key)
	 * @param psswrd The users password
	 * @param frstName First name of the user
	 * @param lstName Last name of the user
	 * @param deviationDistance Distance that the user is willing to deviate their course (DEFAULTED IF NOT GIVEN)
	 * @param numSeats Number of seats in the the car (IF DRIVER & DEFAULTED IF NOT SET AT REGISTRATION)
	 * @param carDescription Description of the users car (IF DRIVER & NULL IF NOT GIVEN)
	 */
	protected User( String firstName, String lastName, String emailAddress, 
			String password, String carDescription, int numSeats, double deviationDistance ){
		
		if(firstName == "" || firstName == null)
			throw new IllegalArgumentException("No first name provided for User constructor");
		else
			this.firstName = firstName;
		
		if(lastName == "" || lastName == null)
			throw new IllegalArgumentException("No last name provided for User constructor");
		else
			this.lastName = lastName;
		
		if(emailAddress == "" || emailAddress == null)
			throw new IllegalArgumentException("No email address provided for User constructor");
		else
			this.rutgersEmail = emailAddress;
		
		if(password == "" || password == null)
			throw new IllegalArgumentException("No password provided for User constructor");
		else
			this.password = password;
		
		//MIGHT NEED USER TYPE TO CHECK OTHER ARGUMENTS!
		this.carDescription = carDescription;
		this.numSeats = numSeats;
		this.deviationDistance = deviationDistance; 
		
	}
	
	//No arg constructor
	protected User(){
		
	}
	
	@Override
	public String getCarDescription() {
		return carDescription;
	}

	@Override
	public double getDeviationDistance() {
		return deviationDistance;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public int getNumSeats() {
		return numSeats;
	}
	
	@Override
	public String getRutgersEmail() {
		return rutgersEmail;
	}
	
	@Override
	public ArrayList<String> getEmailAddresses() {
		return emails;
	}

	public String getPassword(){
		return password;
	}
	
	//Set methods
	
	@Override
	public boolean setCarDescription(String newDescription) {
		if(newDescription != "")
		{
			carDescription = newDescription;
			return true;
		}
		return false;
	}

	@Override
	public boolean setDeviationDistance(double newDistance) {
		if(newDistance != 0)
		{
			deviationDistance = newDistance;
		}
		return false;
	}

	@Override
	public boolean setRutgersEmail(String newRutgersEmail) {
		if(newRutgersEmail != "")
		{
			rutgersEmail = newRutgersEmail;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addEmail(String newEmailAddress) {
		if(newEmailAddress != "")
		{
			emails.add(newEmailAddress);
			return true;
		}
		return false;
	}

	@Override
	public boolean setFirstName(String newName) {
		if(newName != "")
		{
			firstName = newName;
			return true;
		}
		return false;
	}

	@Override
	public boolean setLastName(String newName) {
		if(newName != "")
		{
			lastName = newName;
			return true;
		}
		return false;
	}

	@Override
	public boolean setNumSeats(int newNumSeats) {
		if(newNumSeats < 5 && newNumSeats > 0)
		{
			numSeats = newNumSeats;
			return true;
		}
		return false;
	}

	@Override
	public boolean setPassword(String password) {
		if(password != "")
		{
			this.password = password;
			return true;
		}
		return false;
	}

}
