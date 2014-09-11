package model;

import java.util.Date;

/**
 * 
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 *
 */
public interface TimeIntervalInterface {

	/** 
	 * Get the Date representing the start time of a trip. 
	 */
	public Date getStart();
	public long getStartLong();
	public Date getEnd();
	public long getEndLong();
	
	/** 
	 * Set the start time Date object to the new Date object.  Maintains original duration length; that is, the original end time 
	 * is pushed back.
	 * @param new_start
	 * @return true if successful; false otherwise.
	 */
	public boolean setStart( Date new_start );
		
	public long getDuration();
	
	public boolean setDuration( long new_duration );
}
