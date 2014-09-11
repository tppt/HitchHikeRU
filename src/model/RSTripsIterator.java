package model;

import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.SQLException;
import control.Main;

/**
 * This is meant to be used to provide an iterator return
 * for SearchTrips. This should allow MatchMaker to make a
 * more educated decision about what it loads into memory.
 *
 * @author Kyle Waranis
 */

 class RSTripsIterator implements Iterator<TripInterface> {

	ResultSet results;
	boolean closed = false;

	/**
	 * An open result from the tTrips table must be
	 * passed. There is no consistency checking on the
	 * resultSet so it is the callers responsibility to
	 * have passed something of the correct format.
	 *
	 * @param results The ResultSet from tTrips.
	 */
	public RSTripsIterator(ResultSet results) {

		//Check for garbage
		if(results == null)
			throw new NullPointerException();

		this.results = results;
	}

	/**
	 * This checks if a call to next() will pass or fail.
	 *
	 * @return True if there is something else to iterate to, else, false.
	 */
	public boolean hasNext() {
		
		try {
			return !results.isLast();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Gets the next element in the Resultset, casts it as a TripInterface
	 * and returns it.
	 *
	 * @return The trip interface in the next row of the ResultSet.
	 */
	public TripInterface next() {

		//Pull out the next trip.
		TripInterface trip = null;
		try {
			if(results.next()) {
				try {
					trip = Main.getTransactor().getTrip(results.getString("Owner"), results.getString("Name"));
				}
				catch(SQLException e) {
					System.out.println("SQLException thrown when iterating on ResultSet.");
					e.printStackTrace();
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}

		return trip;
	}

	/**
	 * Not implemented since its optional and not applicable to working on a resultset.
	 */
	public void remove() {
		
	}
}