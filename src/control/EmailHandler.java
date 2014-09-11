package control;

/**
 * @author Andrew Gufford
 */

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.PasswordAuthentication;
import javax.mail.Flags.Flag;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;

import model.Email;
import model.RequestInterface;
import model.TripInterface;

public class EmailHandler 
{
	private static EmailHandler thisHandler = null;
	
	private static String fromAddr = "hitchhikeru@gmail.com";
	private final static String username = "hitchhikeru@gmail.com";
	private final static String password = "fatninja";
	private static ArrayList<Email> readEmails = new ArrayList<Email>();

	private EmailHandler(){}
	
	public static synchronized EmailHandler getInstance()
	{
		if(thisHandler == null)
		{
			thisHandler = new EmailHandler();
		}
		return thisHandler;
	}
	
	/**
	 * Sends an email to the passed address
	 * 
	 * @param toAddress The email address of the person to which the email needs to be sent
	 * @param subject Subject of the email
	 * @param message Message associated with the email
	 * @return True if email has been sent False otherwise
	 */
	public boolean sendMessage(String toAddress, String subject, String message)
	{

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() 
		{
					protected PasswordAuthentication getPasswordAuthentication() 
					{
						return new PasswordAuthentication(username, password);
					}
		});

	    String msgBody = message;

	    try 
	    {
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress("hitchhikeru@gmail.com"));
	        msg.addRecipient(Message.RecipientType.TO,
	                         new InternetAddress(toAddress));
	        msg.setSubject(subject);
	        msg.setText(message);
	        Transport.send(msg);
	        return true;
	    }
	    catch (AddressException addrExc) 
	    {
	    	addrExc.printStackTrace();
	    	return false;
	    }
	    catch (MessagingException msgEx) 
	    {
	    	msgEx.printStackTrace();
	    	return false;
	    }
	}
	
	/**
	 * Calls retrieveUnreadMessages to ensure that all new emails have been read from the inbox. It then iterates through
	 * the email cache to retrieve all messages sent by the username provided returning a list of those emails to the calling
	 * thread. 
	 * 
	 * @param emailaddress The username of the person
	 * @return The unread emails in the inbox of hhru gmail account associated with that user
	 */
	public synchronized ArrayList<Email> getUnreadEmails()
	{
		readEmails.clear();
		retrieveUnreadMessages();
		return readEmails;
	}
	
	public synchronized String sendUserTrips(String username)
	{
		System.out.println("Attempting to pull trips for user " + username + "...");
		StringBuilder message = new StringBuilder();
		message.append("Trip Name     ");
		message.append("TripID \n");
		
		ArrayList<TripInterface> trips = null;
		try{
			trips = UserControl.getInstance().getUserTrips(username);
		}
		catch( Exception e ){
			//e.printStackTrace();
			//DO NOTHING
		}
		
		if( trips == null ){
			return null;
		}
		
		try {Thread.sleep(5000);}catch(Exception e){}
		System.out.println("Found " + trips.size() + "trips for user");
		for(int x = 0; x < trips.size(); x++)
		{
			message.append(trips.get(x).getName() + "     ");
			message.append(trips.get(x).getTID() + "\n");
		}
		message.append("To cancel a trip, reply back with 'CANCEL TRIP: <TripID>'");
		System.out.println("Returning reply message to listener...");
		return message.toString();
	}
	
	public synchronized String sendUserRequests(String username)
	{
		System.out.println("Attempting to pull requests for user " + username + "...");
		StringBuilder message = new StringBuilder();
		message.append("Request Name     ");
		message.append("RequestID \n");
		
		ArrayList<RequestInterface> requests = null;
		try{
			requests = UserControl.getInstance().getUserRequests(username);
		}
		catch( Exception e ){
			//DO NOTHING
		}
		
		if( requests == null )
			return null;
		
		
		try {Thread.sleep(5000);}catch(Exception e){}
		System.out.println("Found " + requests.size() + "trips for user");
		for(int x = 0; x < requests.size(); x++)
		{
			message.append(requests.get(x).getName() + "     ");
			message.append(requests.get(x).getRQID() + "\n");
		}
		message.append("To cancel a trip, reply back with 'CANCEL REQUEST: <RequestID>'");
		System.out.println("Returning reply message to listener...");
		return message.toString();
	}
	
	private void retrieveUnreadMessages()
	{
		Folder inbox;
		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		
		try
		{
		 
			 Session session = Session.getDefaultInstance(props, null);
			 Store store = session.getStore("imaps");
			 store.connect("imap.gmail.com",username, password);
			 inbox = store.getFolder("Inbox");
			 
			 inbox.open(Folder.READ_WRITE);
			 Message messages[] = inbox.search(new FlagTerm(new Flags(Flag.SEEN), false));
			 
			 FetchProfile fp = new FetchProfile();
	         fp.add(FetchProfile.Item.ENVELOPE);
	
	         fp.add(FetchProfile.Item.CONTENT_INFO);
	
	         inbox.fetch(messages, fp);
	         
	         try
	         {
                readAllMessages(messages);
                
                inbox.close(true);
                store.close();
	         }
	         catch(Exception e)
	         {
	        	 e.printStackTrace();
	         }
	         
		}
		catch (MessagingException e)
        {
            System.out.println("Exception while connecting to server: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void readAllMessages(Message[] messages) throws Exception
	{
		 System.out.println("Thread wants unread emails...");
		 for (int i = 0; i < messages.length; i++)
	     {
	            Email thisEmail = new Email();
	            thisEmail.setMessage("");
	            readEnvelope(messages[i], thisEmail);
	            readEmails.add(thisEmail);
	     }
		 System.out.println("Read " + messages.length + " emails...");
	}

	private void readEnvelope(Message message, Email email) throws MessagingException, IOException 
	{
		 	Address[] a;

	        // FROM

	        if ((a = message.getFrom()) != null) 
	        {
	            for (int j = 0; j < a.length; j++) 
	            {
	            	email.getFrom().add(a[j].toString());
	                //System.out.println("FROM: " + a[j].toString());
	            }
	        }
	        
	        // TO
	        /*
	        if ((a = message.getRecipients(Message.RecipientType.TO)) != null) 
	        {
	            for (int j = 0; j < a.length; j++) 
	            {
	               // System.out.println("TO: " + a[j].toString());
	            	email.getFrom().add(a[j].toString());
	            }
	        }
	        */
	        
	        
	        String subject = message.getSubject();
	        email.setSubject(subject);

	        Date recievedDate = message.getReceivedDate();
	        Date sentDate = message.getSentDate(); 
	        email.setRecievedDate(recievedDate);
	        email.setSentDate(sentDate);
	        
	        String content = message.getContent().toString();
	        //System.out.println("Subject : " + subject);
	        
	        //System.out.println("Sent Date : " + sentDate.toString());
	        //System.out.println("Content : " + content);

	        getContent(message,email);
	}

	private void getContent(Message message, Email email) 
	{
		try 
		{
            String contentType = message.getContentType();
            //System.out.println("Content Type : " + contentType);
            Multipart mp = (Multipart) message.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) 
            {
                dumpPart(mp.getBodyPart(i), email);
            }
        } 
		catch (Exception ex) 
		{
            System.out.println("Exception arise at get Content");
            ex.printStackTrace();
        }
	}

	private void dumpPart(Part part, Email email) throws IOException, MessagingException 
	{
		// Dump input stream ..
        InputStream is = part.getInputStream();
        
        // If "is" is not already buffered, wrap a BufferedInputStream
        // around it.
        if (!(is instanceof BufferedInputStream)) 
        {
            is = new BufferedInputStream(is);
        }
        
        byte[] contents = new byte[1024];

        int bytesRead=0;
        String strFileContents = ""; 
        while( (bytesRead = is.read(contents)) != -1)
        { 
        	strFileContents = new String(contents, 0, bytesRead);               
        }
        //System.out.print(strFileContents);
        email.setMessage(email.getMessage() + strFileContents);	
	}
}



