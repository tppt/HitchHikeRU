package model;

import java.util.ArrayList;

/**
 * Interface implemented by the User class. Abstracts implementation of User methods. 
 *
 *
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */
public interface UserInterface {

	//Get methods

	public String getCarDescription();
	
	public double getDeviationDistance();
	
	public String getFirstName();
	
	public String getLastName();
	
	public int getNumSeats();
	
	public String getRutgersEmail();
	
	public ArrayList<String> getEmailAddresses();
	
	public String getPassword();
	
	//Set methods
	
	public boolean setCarDescription(String newDescription);
	
	public boolean setDeviationDistance(double newDistance);
	
	public boolean setRutgersEmail(String newEmailAddress);
	
	public boolean addEmail(String newEmailAddress);
	
	public boolean setFirstName(String newName);
	
	public boolean setLastName(String newName);
	
	public boolean setNumSeats(int newNumSeats );
	
	public boolean setPassword(String password );
}
