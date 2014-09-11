package control;

import model.*;

/**
 * Request control is responsible for creating, authenticating, and modifying requests and while
 * doing so vetting all data.
 * 
 * @author Kyle Waranis
 */
public class RequestControl {
	
	private static RequestControl requestControl = null;
	
	private RequestControl() {

	}
	
	/**
	 * Singleton pattern constructor. This should be called instead of the standard constructor when needing
	 * a RequestControl object.
	 * 
	 * @return The global RequestControl object.
	 */
	public static synchronized RequestControl getInstance() {
		
		if(requestControl == null)
			requestControl = new RequestControl();
		
		return requestControl;
	}

	/**
	 * This verifies the correctness of all incoming parameters and queries Transactor for an interface
	 * to the required object. If none exists, one is created.
	 * 
	 * @param user The owner of the request.
	 * @param name The name of the request.
	 * @returns Returns the generated RequestInterface.
	 */
	public RequestInterface getRequest(UserInterface user, String name) {
		
		//Check for garbage
		if(user == null || name == null)
			return null;
		if(name.equals(""))
			return null;
		
		//Get and return the requestInterface.
		RequestInterface request = Main.getTransactor().getRequest(user.getRutgersEmail(), name);
		
		if(request == null)
			return null;
		else
			return request;
	}
	
	public RequestInterface getRequest(String email, String name) {
		RequestInterface request = Main.getTransactor().getRequest(email, name);
		return request;
		
	}
	
	/**
	 * Checks if the given request is present in Model.
	 * 
	 * @param name
	 * @param rutgersEmail
	 * @return True if the object exists, else false.
	 */
	public boolean validateRequest(String rutgersEmail, String name) {
		
		//Check for garbage.
		if(name == null || rutgersEmail == null)
			return false;
		if(name.equals("") || rutgersEmail.equals(""))
			return false;
		
		//Attempt to retrieve the request.
		RequestInterface request = Main.getTransactor().getRequest(rutgersEmail, name);
		
		//Check for null and return.
		if(request == null)
			return false;
		else
			return true;
	}
	
	/**
	 * This verifies the correctness of all incoming parameters and queries Transactor to create it.
	 * 
	 * @param user The owner of the request.
	 * @param from The starting point of the request.
	 * @param to The end point of the request.
	 * @param date The time at which the requester wishes to arrive at "to".
	 * @returns Returns the generated RequestInterface.
	 */
	public RequestInterface createRequest(UserInterface user, String requestName, LandmarkInterface from, LandmarkInterface to, TimeIntervalInterface timeInterval) {
		
		//Check for garbage
		if(user == null || requestName == null || from == null || to == null || timeInterval == null)
			return null;
		if(requestName.equals(""))
			return null;
		
		//Check for uniqueness.
		if(validateRequest(user.getRutgersEmail(), requestName))
			return null;
		
		//Get the route that describes this start/end pair.
		//TODO Model may want to consider accepting request generation from landmarkInterfaces, not just routes.
		RouteInterface route = Main.getTransactor().registerRoute(user.getRutgersEmail(), requestName, from, to);
		
		if(route == null)
			return null;
		
		//Get and return the requestInterface.
		RequestInterface request = Main.getTransactor().registerRequest(user.getRutgersEmail(), requestName, route, timeInterval, timeInterval);
		
		if(request == null)
			return null;
		else
			return request;
	}
	
	/**
	 * Cancels the given request object.
	 * 
	 * @param user - the user owning the request
	 * @param request The request to be cancelled.
	 * @returns Returns true if the request as been successfully removed, else false.
	 */
	public boolean cancelRequest(String useremail, String requestname) {
		UserInterface u = UserControl.getInstance().getUser(useremail);
		//Remove the request.
		Main.getTransactor().cancelRequest(u.getRutgersEmail(), requestname);
		
		return true;
	}	
	
	public boolean cancelRequest(int RQID){

		//Trip does not have any way to retrieve the name of the trip.
		int result = Main.getTransactor().cancelRequest(RQID);
		if (result == 0)
			return true;
		else
			return false;
	
	}
}
