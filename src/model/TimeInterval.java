package model;

import java.util.Date;

/**
 * Class representing a time interval, encapsulating a Date/Time and a deviation about the time
 * (i.e. +/- 15 minutes).
 * 
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 *
 */
public class TimeInterval implements TimeIntervalInterface {

	protected Date start;
	protected long duration;
	
	protected TimeInterval( Date start, long duration_ms ){
		if(start != null)
			this.start = start;
		else
			throw new IllegalArgumentException("Null start date given to TimeInterval constructor");
		
		this.duration = duration_ms;
		//Commented out since we are currently not using duration and this was causing an error
		/*
		if(duration > 0)
			this.duration = duration_ms;
		else
			throw new IllegalArgumentException("Invalid duration given to TimeInterval constructor");*/
	}
	
	public Date getStart(){
		return start;
	}
	
	/**
	 * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
	 */
	public long getStartLong(){
		long l = start.getTime();
		return l;
	}
	
	public Date getEnd(){
		Date end = new Date( start.getTime()+duration );
		return end;
	}
	
	public long getEndLong(){
		return start.getTime()+duration;
	}
	
	public boolean setStart( Date new_start ){
		if(new_start != null)
		{
			start = new_start;
			return true;	
		}
		else
			return false;
	}
	
	public long getDuration(){
		return duration;
	}
	
	public boolean setDuration( long new_duration ){
		if(!(duration < 0))
		{
			duration = new_duration;
			return true;
		}
		else
			return true;
	}
}
