package model;

/**
 * @authors Yuriy Garnaev, Andrew Gufford, Prashant Patel, and Thomas Travis
 */

import java.util.ArrayList;
import java.util.Date;

public class Email implements EmailInterface
{
	private ArrayList<String> from;
	private String subject, message;
	private Date sentDate, recievedDate;
	
	public Email()
	{
		from = new ArrayList<String>();
		subject = "";
		message = "";
		sentDate = new Date();
		recievedDate = new Date();
	}
	
	public Email(ArrayList<String> inFrom, String inMessage, Date inSentDate)
	{
		from = inFrom;
		message = inMessage;
		sentDate = inSentDate;
	}

	public ArrayList<String> getFrom() 
	{
		return from;
	}

	public void setFrom(ArrayList<String> from) 
	{
		this.from = from;
	}
	
	public String getSubject() 
	{
		return subject;
	}

	public void setSubject(String subject) 
	{
		this.subject = subject;
	}

	public String getMessage() 
	{
		return message;
	}

	public void setMessage(String message) 
	{
		this.message = message;
	}

	public Date getSentDate() 
	{
		return sentDate;
	}

	public void setSentDate(Date sentDate) 
	{
		this.sentDate = sentDate;
	}

	public Date getRecievedDate() 
	{
		return recievedDate;
	}

	public void setRecievedDate(Date recievedDate) 
	{
		this.recievedDate = recievedDate;
	}
	
	
}
