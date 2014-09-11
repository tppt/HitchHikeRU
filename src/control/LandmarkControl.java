package control;

import model.*;

/**
 * landmark control is responsible for creating, authenticating, and modifying landmarks and while
 * doing so vetting all data.
 * 
 * @author Kyle Waranis
 */
public class LandmarkControl {
	
	private static LandmarkControl landmarkControl = null;
	
	private LandmarkControl() {

	}
	
	/**
	 * Singleton pattern constructor. This should be called instead of the standard constructor when needing
	 * a LandmarkControl object.
	 * 
	 * @return The global LandmarkControl object.
	 */
	public static synchronized LandmarkControl getInstance() {
		if( landmarkControl == null )
			landmarkControl = new LandmarkControl();
		
		return landmarkControl;
	}

	/**
	 * This queries Transactor for a landmark for the given attributes and returns it.
	 * 
	 * @param name The unique identifier for the landmark.
	 * @return A landmark identified by the given unique identifiers, null if none exists.
	 */
	public LandmarkInterface getLandmark(String name, UserInterface user) {
		
		//Check for garbage
		if(name == null || user == null)
			return null;
		
		//Retrieve and return the landmark.
		LandmarkInterface landmark = Main.getTransactor().getLandmark(user.getRutgersEmail(), name);

		if(landmark == null)
			return null;
		else
			return landmark;
	}
	
	/**
	 * This checks for a valid address and generates a landmark with the given 
	 * attributes if none exists.
	 * 
	 * @param name The name of the new landmark.
	 * @param address The address of the new landmark.
	 * @return A landmark identified by the given unique identifiers, null if none exists.
	 */
	public LandmarkInterface createLandmark(String name, String streetAddress, String city, String state, String zipCode, UserInterface user) {
		
		//Check for garbage
		if(name == null || streetAddress == null || city == null || state == null || zipCode == null || user == null)
			return null;
		if(name.equals("") || streetAddress.equals("") || city.equals("") || state.equals("") || zipCode.equals(""))
			return null;
		if(zipCode.length() != 5 || !(zipCode.matches("[0-9]+")))
			return null;
		
		//Register and return the landmark.
		LandmarkInterface landmark = Main.getTransactor().registerLandmark(user.getRutgersEmail(), name, streetAddress, city, state, zipCode, "region", 0, 0);
		if(landmark == null)
			return null;
		else
			return landmark;
	}
	
	/**
	 * Generates a landmark from the given arguments if none exists.
	 * 
	 * @param name The name of the new landmark.
	 * @param latitude The latitude of the landmark as a double.
	 * @param longitude The longitude of the landmark as a double.
	 * @return
	 */
	/* COMMENTED OUT AS WE ARE CURRENTLY USING THE OTHER createLandmark METHOD. 
	public LandmarkInterface createLandmark(String name, double latitude, double longitude, UserInterface user) {
		
		//Check for garbage.
		if(name == null || user == null)
			return null;
		if(name.equals(""))
			return null;
		if(latitude < -180 || latitude > 180)
			return null;
		if(longitude < -180 || longitude > 180)
			return null;
		
		//Register and return the landmark.
		LandmarkInterface landmark = Main.getTransactor().registerLandmark(user.getRutgersEmail(), name, latitude, longitude);
		
		if(landmark == null)
			return null;
		else
			return landmark;
	}*/
	
	/**
	 * Removes the given landmark from the system.
	 * 
	 * @param landmark The landmark to be removed.
	 * @return Returns true if the landmark is removed successfully.
	 */
	public boolean removeLandmark(LandmarkInterface landmark, UserInterface user) {
		
		//Check for garbage
		if(landmark == null || user == null)
			return false;
		
		//Remove the landmark and return.
		Main.getTransactor().removeLandmark(landmark.getName(), user.getRutgersEmail());
		
		return true;
	}

}
