package control;

/**
 * @author Andrew Gufford
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import model.Email;
import model.LandmarkInterface;
import model.ObjectManager;
import model.Request;
import model.RequestInterface;
import model.TimeInterval;
import model.TripInterface;
import model.UserInterface;
import control.EmailHandler;



public class EmailListener implements Runnable 
{
	HashMap<Integer, String> landmarkMap = new HashMap<Integer, String>();
	HashMap<Integer, String> requestMap = new HashMap<Integer, String>();

	Thread t;
	EmailListener()
	{
		t = new Thread(this);
		t.start();		
	}
	
	
	@Override
	public void run() 
	{
		try
		{
			populateLandmarkList();
			while(true)
			{
				System.out.println("...");
				try{
					checkEmails();
				} catch (Exception e){
					e.printStackTrace();
				}
				try {Thread.sleep(20000);}catch(Exception e){}
			}
		} catch (Exception e){
			System.out.println("Email listener thread interrupted");
		}
		
	}
	
	private void populateLandmarkList()
	{
		ArrayList<LandmarkInterface> landmarks = UserControl.getInstance().getSavedLandmarks("testuser@rutgers.edu");
		for(int x = 1; x <= landmarks.size(); x++)
		{
			landmarkMap.put(x, landmarks.get(x-1).getName());
		}
	}
	
	private void populateRequestList(String emailAddress)
	{
		ArrayList<RequestInterface> requests = UserControl.getInstance().getUserRequests(emailAddress);
		for(int x = 1; x <= requests.size(); x++)
		{
			requestMap.put(x, requests.get(x-1).getName());
		}
	}
	
	private void checkEmails()
	{
		System.out.println("Checking emails...");
		ArrayList<Email> emailList = EmailHandler.getInstance().getUnreadEmails();
		for (Email email : emailList)
		{
			System.out.println("Found email...");
			TripInterface bestMatch = null;
			String from = "";
			String message = "";
			String emailAddress = "";
			String command = "";
			String argument = "";
			if(email.getFrom().size() == 1)
			{
				from = email.getFrom().get(0);
				if (from.contains("<") && from.contains(">"))
				from = from.substring(from.indexOf("<")+1, from.indexOf(">"));	
				System.out.println("From: "+from);
				message = email.getMessage();
				System.out.println("Message: " +message);
				emailAddress = from;
				
				message = message.toLowerCase();
				StringTokenizer tk = new StringTokenizer(message, ":<");
				command = tk.nextToken().trim();
				argument = tk.nextToken().trim();
				
				if(command.equalsIgnoreCase("cancel trip") && !argument.equals(""))
				{
					try {int intargument = Integer.parseInt(argument);} catch (Exception e){
						System.out.println("Invalid syntax");
						break;
					}
					System.out.println("Attempting to cancel user trip");
					if(TripControl.getInstance().cancelTrip(Integer.parseInt(argument)))
					{
						EmailHandler.getInstance().sendMessage(emailAddress, "Trip Cancellation", "Your trip has been canceled");
					}
					else
						EmailHandler.getInstance().sendMessage(emailAddress, "Trip Cancellation", "Your trip cannot be canceled as the ID is invalid"+
					"please retry by sending 'CANCEL TRIP: <TripID>'");
				}
				else if(command.equalsIgnoreCase("cancel request") && !argument.equals(""))
				{
					populateRequestList(emailAddress);
					try 
					{
						int intargument = Integer.parseInt(argument);} catch (Exception e){
						System.out.println("Invalid syntax");
						break;
					}
					System.out.println("Attempting to cancel user request");
					if(RequestControl.getInstance().cancelRequest(Integer.parseInt(argument)))
					{
						EmailHandler.getInstance().sendMessage(emailAddress, "Request Cancellation", "Your request has been canceled");
					}
					else
						EmailHandler.getInstance().sendMessage(emailAddress, "Request Cancellation", "Your request cannot be canceled as the ID is invalid"+
					"please retry by sending 'CANCEL REQUEST: <RequestID>'");
					requestMap.clear();
				}
				else if(message.contains("show trips") || command.equalsIgnoreCase("show trips"))
				{
					System.out.println("Attempting to show user trips...");
					String sendString = EmailHandler.getInstance().sendUserTrips(emailAddress);
					
					if( sendString == null || sendString.isEmpty() ){
						continue;
					}
					
					System.out.println("Attempting to send email back to user...");
					boolean hasSent = EmailHandler.getInstance().sendMessage(emailAddress, "Trips", sendString);
					System.out.println(hasSent + "- value returned from send...");	
				}
				else if(message.contains("show requests") || command.equalsIgnoreCase("show requests"))
				{
					System.out.println("Attempting to show user requests...");
					String sendString = EmailHandler.getInstance().sendUserRequests(emailAddress);
					
					if( sendString == null || sendString.isEmpty() )
						continue;
					
					System.out.println("Attempting to send email back to user...");
					boolean hasSent = EmailHandler.getInstance().sendMessage(emailAddress, "Trips", sendString);
					System.out.println(hasSent + "- value returned from send...");	
				}
				else if(message.contains("show valid landmarks") || command.equalsIgnoreCase("show valid landmarks"))
				{
					System.out.println("Sending user a list of valid landmarks");
					StringBuilder landmarkNames = new StringBuilder();
					for(int x = 1; x <= landmarkMap.size(); x++)
					{
						if(x < landmarkMap.size())
						{
							landmarkNames.append(landmarkMap.get(x) + "\n");
						}
						else
							landmarkNames.append(landmarkMap.get(x));
					}
					System.out.println("Attempting to send the user the list of valid landmarks");
					boolean hasSent = EmailHandler.getInstance().sendMessage(emailAddress, "List of valid Landmarks",
							landmarkNames.toString());
					if(hasSent)
						System.out.println("Message sent");
					else
						System.out.println("Message not sent - error occured");
				}
				else if(command.equalsIgnoreCase("create request") && !message.equals(""))
				{
					System.out.println("Attempting to create a new user request...");
					Request thisRequest;
					String requestName;
					LandmarkInterface fromLoc, toLoc;
					TimeInterval thisTimeInterval;
					Date emailDateTime = email.getSentDate();
					StringTokenizer argumentTok = new StringTokenizer(argument);
					while(argumentTok.hasMoreElements())
					{
						requestName = argumentTok.nextToken();
						System.out.println("requestName: " + requestName);
						System.out.println("Attempting to retrieve passed landmarks...");
						int toIndex=0; int fromIndex=0;
						try {
							toIndex = Integer.parseInt(argumentTok.nextToken());
							fromIndex = Integer.parseInt(argumentTok.nextToken());
						} catch (Exception e){System.out.println("Couldn't parse landmark indexes");}
						//Parse the from and to location from values we got in our map
						fromLoc = LandmarkControl.getInstance().getLandmark(landmarkMap.get(fromIndex),
								UserControl.getInstance().getUser("testuser@rutgers.edu"));
						toLoc = LandmarkControl.getInstance().getLandmark(landmarkMap.get(toIndex),
								UserControl.getInstance().getUser("testuser@rutgers.edu"));
						
						//Send an email with an error if they provided a location that is not valid
						if(fromLoc == null || toLoc == null)
						{
							EmailHandler.getInstance().sendMessage(emailAddress, "Error Creating Request",
									"An invalid landmark has been passed. To see a list of valid landmarks send an email to " +
							"hitchhikeru@gmail.com with the keyword \"show valid landmarks\"");
						}
						
						//Attempt to create a TimeInterval from the date the email was sent with a duration of 60 as default
						//System.out.println("Attempting to create time interval...");
						//thisTimeInterval = (TimeInterval) TimeIntervalControl.getInstance().createTimeInterval(emailDateTime, 60);
						
						//Attempt to make a request
						System.out.println("Creating new request with these values...");
						UserInterface user = UserControl.getInstance().getUser(emailAddress);
						

						ArrayList<TripInterface> matchingtrips = WebSessionManager.getInstance().submitRequest(requestName, user.getRutgersEmail(), toLoc.getName(), fromLoc.getName(), emailDateTime, 1, 60);
						
						if(matchingtrips.size() == 0)
						{
							EmailHandler.getInstance().sendMessage(emailAddress, "No Matches", "There are currently no matches to your request, please try again later! ;)");	
						}
						else
						{
							Date thisDate = new Date();
							for(int x = 0; x < matchingtrips.size(); x++)
							{
								
								if(thisDate.compareTo(matchingtrips.get(x).getDepartureTimeInterval().getStart()) < 0)
								{
									bestMatch = matchingtrips.get(x);
								}
							}
							if(bestMatch != null)
							{
								if(WebSessionManager.getInstance().submitPassengerRequest(requestName, bestMatch.getName(), bestMatch.getDriver(), emailAddress) == 0)
								{
									EmailHandler.getInstance().sendMessage(emailAddress, "Matches Found", "A trip has been found! A request has been submitted on your behalf. Here is the information regarding this trip: \n"+
											"Name of Driver: " + UserControl.getInstance().getUser(bestMatch.getDriver()).getFirstName() +
											" " + UserControl.getInstance().getUser(bestMatch.getDriver()).getLastName().substring(0,1) +
											"\n" + "At Time: " + bestMatch.getDepartureTimeInterval().getStart().toString());
								}
								else
								{
									EmailHandler.getInstance().sendMessage(emailAddress, "Error Finding Matches", "An error has unfortunately occured finding a match to your request" + 
											" please try again later.");
								}
							}
							else
							{
								EmailHandler.getInstance().sendMessage(emailAddress, "No matches found", "There are currently no matches to your request, please try again later! ;)");
							}
						}
						if(matchingtrips == null)
						{
							System.out.print("Something has gone horribly wrong...");
						}
					}
				}
				else 
				{
					EmailHandler.getInstance().sendMessage(emailAddress, "Invalid Syntax", 
							"That is not valid syntax. Please respond with \"show trips\", \"cancel trip:<TripID>\"," +
							" \"show valid landmarks\", \"create request: <RequestName> <LandmarkIDStart> <LandmarkIDEnd>\" \n" +
									"**To find a LandmarkID please use the \"show valid landmarks\" command.**");
				}
			}
		}
	}
}
