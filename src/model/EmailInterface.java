package model;

/**
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */

import java.util.ArrayList;
import java.util.Date;

public interface EmailInterface 
{
	public ArrayList<String> getFrom();
	public void setFrom(ArrayList<String> from);
	public String getSubject(); 
	public void setSubject(String subject); 
	public String getMessage();
	public void setMessage(String message);
	public Date getSentDate();
	public void setSentDate(Date sentDate);
	public Date getRecievedDate();
	public void setRecievedDate(Date recievedDate);
}
