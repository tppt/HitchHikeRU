package control;

import model.*;
import java.util.Date;

/**
 * TimeIntervalControl is responsible for validating and determining equality amongst TimeIntervals
 * 
 * @author Kyle Waranis
 */
public class TimeIntervalControl {
	
	private static TimeIntervalControl timeIntervalControl = null;
	
	private TimeIntervalControl() {

	}
	
	/**
	 * Singleton pattern constructor. This should be called instead of the standard constructor when needing
	 * a TimeIntervalControl object.
	 * 
	 * @return The global TimeIntervalControl object.
	 */
	public static synchronized TimeIntervalControl getInstance() {
		if(timeIntervalControl == null)
			timeIntervalControl = new TimeIntervalControl();
		
		return timeIntervalControl;
	}
	
	/**
	 * This queries the Transactor to generate a time interval with the given arguments and return it.
	 */
	public TimeIntervalInterface createTimeInterval(Date start, long duration) {
		
		//Check for garbage
		if(start == null)
			return null;
		if(duration <= 0)
			return null;
		
		//Register and return the timeInterval.
		TimeIntervalInterface timeInterval = Main.getTransactor().createTimeInterval(start, duration);
		
		if(timeInterval == null)
			return null;
		else
			return timeInterval;
	}
	
	/**
	 * This detects overlap between the two given TimeIntervals and returns true if they overlap.
	 * 
	 * @return True if there is an overlap between the TimeIntervals.
	 */
	public boolean detectOverlap(TimeIntervalInterface timeOne, TimeIntervalInterface timeTwo) {
		int requestDeviation = (int)timeTwo.getDuration();
		Date timeOfDeparture = timeTwo.getStart();
		Date timeOfTrip = timeOne.getStart();
		
		
		long t=timeOfDeparture.getTime();
		Date earliestDeparture=new Date(t - (requestDeviation * 60000));
		Date latestDeparture=new Date(t + (requestDeviation * 60000));

		if (earliestDeparture.before(timeOfTrip) && latestDeparture.after(timeOfTrip))
			return true;
		else
			return false;
	}
	

	/**
	* Determins the smallest distance between two timeIntervals.
	*
	* @return The distance between the timeIntervals, or -1 if an error has occured.
	*/
	public long computeDistance(TimeIntervalInterface timeOne, TimeIntervalInterface timeTwo) {

		//Check for garbage.
		if(timeOne == null || timeTwo == null)
			return -1;

		//Compute the distance.
		return Math.abs(timeOne.getStartLong() - timeTwo.getStartLong());
	}
	
	/**
	 * This detects overlap between the two given TimeIntervals and returns true if they overlap.
	 * 
	 * @return Returns the total amount of overlap between the TimeIntervals in miliseconds.
	 */
	public long computeIntersection(TimeIntervalInterface timeOne, TimeIntervalInterface timeTwo) {
		
		//Check for garbage
		if(timeOne == null || timeTwo == null)
			return -1;

		//Compute and return the intersection
		long intersection = 0;
		if(timeOne.getStart().before(timeTwo.getStart()) && timeOne.getEnd().after(timeTwo.getStart()))
			if(timeOne.getEnd().after(timeTwo.getEnd()) || timeOne.getEnd().equals(timeTwo.getEnd()))
				intersection = timeTwo.getDuration();
			else
				intersection = timeOne.getEndLong() - timeTwo.getStartLong();
		else if(timeTwo.getStart().before(timeOne.getStart()) && timeTwo.getEnd().after(timeOne.getStart()))
			if(timeOne.getEnd().after(timeTwo.getEnd()) || timeOne.getEnd().equals(timeTwo.getEnd()))
				intersection = timeOne.getDuration();
			else
				intersection = timeTwo.getEndLong() - timeOne.getStartLong();
		System.out.println(intersection);
		return intersection;
	}


}
