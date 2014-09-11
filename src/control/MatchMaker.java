package control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

import model.*;

/**
 * Matchmaker is responsible for generating connections between requests and trips based on their 
 * common attributes. It does not do any assignment, but rather makes suggestions for assignments.
 * 
 * @author Kyle Waranis
 */
public class MatchMaker {
	
	private static MatchMaker matchMaker = null;
	private final int MAX_MATCHED_RETURN_COUNT = 10;
	private MatchMaker() {

	}
	
	/**
	 * Singleton pattern constructor. This should be called instead of the standard constructor when needing
	 * a MatchMaker object.
	 * 
	 * @return The global MatchMaker object.
	 */
	public static synchronized MatchMaker getInstance() {
		
		if(matchMaker == null)
			matchMaker = new MatchMaker();
		
		return matchMaker;
	}
	
	/**
	 * This will find all trips that could potentially fill the given request. It then selects 
	 * the most appropriate trips to use and returns them.
	 * 
	 * @param request The request to be matched.
	 * @return The trip that the request was matched to.
	 */
	public  ArrayList<TripInterface> findTripMatches(RequestInterface request) {
		
		//Check for garbage
		if(request == null)
			return null;
		
		//Get the trips that match geographically.
		Iterator<TripInterface> iterator = Main.getTransactor().searchTrips(request.getRoute());
		
		//Prune the trips to only consider those that overlap temporally.
		//Also, ignore trips from the same user as the owner of the request.
		TripInterface trip = null;
		ArrayList<TripInterface> trips = new ArrayList<TripInterface>();
		while((trip = iterator.next()) != null)
			if(TimeIntervalControl.getInstance().detectOverlap(trip.getDepartureTimeInterval(), request.getDepartureTime()))
				if(!request.getOwner().equals(trip.getOwner()))
					trips.add(trip);
		
		//Sort the trips by distance.
		SortByTemporalDistance distanceSort = new SortByTemporalDistance();
		distanceSort.master = request.getDepartureTime();
		Collections.sort(trips, distanceSort);
		
		//Read up to 10 elements and return them.
		ArrayList<TripInterface> topMatches = new ArrayList<TripInterface>();
		for(int i = 0; i < trips.size() && i < MAX_MATCHED_RETURN_COUNT; i++)
			topMatches.add(trips.get(i));
			
		return topMatches;
	}
	

	//-------------------------------------------------
	// Inner classes to allow temporal sorting.
	//-------------------------------------------------
	class SortByTemporalDistance implements Comparator<TripInterface> {
		public TimeIntervalInterface master = null;
		
		public int compare(TripInterface trip1, TripInterface trip2) {
			if(master == null)
				throw new NullPointerException();
			
			long distance1 = TimeIntervalControl.getInstance().computeDistance(master, trip1.getDepartureTimeInterval());
			long distance2 = TimeIntervalControl.getInstance().computeDistance(master, trip2.getDepartureTimeInterval());
			return (int)(distance1 - distance2);
		}
	}
	
}