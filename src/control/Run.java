package control;

/**
 * @author Yuriy Garnaev
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import model.*;

@WebListener
public class Run implements ServletContextListener{
	static Connection conn;
	private static Transactor t = null;
	
	public static Transactor getTransactor(){
		if( t == null )
			t = new ObjectManager();
		return t;
	}
	

    @Override
    //MAIN METHOD
    public void contextInitialized(ServletContextEvent arg0) {
    	System.out.println("Starting HHRU Back-end");

    	//connectToDatabaseRUVM();
    	//This is the equivalent of the Main method
    	
    	//InitializeALLtheThings();
    	
    	//test1();
    	//test2();
    	//System.out.println(WebSessionManager.getInstance().registerUser("Yuriy", "Garnaev", "ygarnaev@gmail.com", "123", "black bmw", 2, 10));

    	EmailListener EL = new EmailListener();
    	
    }
    
    
    
    
    
    
    
    private void InitializeALLtheThings(){
    	t = getTransactor();
    	
    }
    
    private void test2(){
    	UserInterface user = UserControl.getInstance().getUser("testuser@rutgers.edu");
    	LandmarkControl.getInstance().createLandmark("Busch - Campus Center", "1", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Busch - Hill Center","2", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Busch - Werblin Recreation Center","3", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Busch - Stadium Lot", "4", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Busch - ARC", "5", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Highland Park - River Rd X Main St", "6", "Highland Park", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("New Brunwick - Hamilton X Central", "7", "New Brunswick", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("New Brunwick - George St X Rt 27", "8", "New Brunswick", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Livingston - Campus Center", "9", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Livingston - Recreation Center", "10", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("Livingston - Business School", "11", "Piscataway", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("College Ave - Campus Center", "12", "New Brunswick", "NJ", "08901", user);
    	LandmarkControl.getInstance().createLandmark("College Ave - Scott Hall", "13", "New Brunswick", "NJ", "08901", user);

    }
    
    //This is a test method. Create methods like this to test Model/Control stuff
    private void test1(){
    	System.out.println(WebSessionManager.getInstance().registerUser("User", "A", "userA@rutgers.edu", "a", "pink unicorn", 2, 10));
    	System.out.println(WebSessionManager.getInstance().registerUser("User", "B", "userB@rutgers.edu", "b", "green camry", 2, 10));
    	System.out.println(WebSessionManager.getInstance().registerUser("User", "C", "userC@rutgers.edu", "c", "silver prius", 2, 10));
    	System.out.println(WebSessionManager.getInstance().registerUser("User", "D", "userD@rutgers.edu", "d", "green subaru outback", 2, 10));

    }
    
    

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    	System.out.println("Shutting down HHRU Back-end");

    }
    private static void connectToDatabaseHeliohost(){
		String url = "jdbc:mysql://hitchhikeru.heliohost.org/"; 
        String dbName = "hhruweb_db2"; 
        String driver = "com.mysql.jdbc.Driver";
        String userName = "hhruweb_admin2";
        String password = "fatninja";
        try {
        	Class.forName(driver).newInstance();
        	Connection conn = DriverManager.getConnection(url+dbName,userName,password);
        	System.out.println("Connected to Heliohost Database");
        	conn.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	
	}
    private static void connectToDatabaseRUVM(){
		String url = "jdbc:mysql://cs431-3.cs.rutgers.edu:3306/"; 
        String dbName = "hhru1"; 
        String driver = "com.mysql.jdbc.Driver";
        String userName = "yuriy";
        String password = "cs731c2e";
        try {
        	Class.forName(driver).newInstance();
        	conn = DriverManager.getConnection(url+dbName,userName,password);
        	System.out.println("Connected to Rutgers Virtual Machine Database");
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
	
	}
    
    private static void close(){
    	try {conn.close(); } catch (Exception e) {
        	//e.printStackTrace();
        }
    }



}
