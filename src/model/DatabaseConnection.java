package model;

/**
 * @author Thomas Travis
 */

import java.sql.*;

/**
 * DatabaseConnection is the class that performs all database interaction based on commands send by Control, 
 * and returns objects to Control as required
 * 
 * @author Thomas Travis
 *
 */
public class DatabaseConnection {

	private static Connection conn;
	
	/** No Arg Constructor */
	public DatabaseConnection(){
		
		String url = "jdbc:mysql://cs431-3.cs.rutgers.edu:3306/"; 
        String dbName = "hhru1"; 
        String driver = "com.mysql.jdbc.Driver";
        String userName = "hhruweb";
        String password = "cs731c2e";
         
        try {
        	Class.forName(driver).newInstance();
        	conn = DriverManager.getConnection(url+dbName,userName,password);
        	System.out.println("Connected to Rutgers Virtual Machine Database");
        	//conn.close();
        }
        catch (Exception e){
        	System.out.println("Unable to establish connection to RUVM DB.");
        	e.printStackTrace();
        }
	}

	//DB Connection Methods
	
	public synchronized ResultSet queryDB( String query ){
			
		if( query.isEmpty() || query == null )
			throw new IllegalArgumentException("Empty or null query string in queryDB.");
		
		ResultSet rs = null;
		
		try{
			rs = conn.createStatement().executeQuery( query );
		}
		catch( SQLException e ){
			//TODO
			System.out.println("Failed to execute query in queryDB");
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public synchronized void DBUpdate( String query ){
		
		if( query.isEmpty() || query == null )
			throw new IllegalArgumentException("Empty or null query string in queryDB.");
		
		try{
			conn.createStatement().executeUpdate( query );
		}
		catch( SQLException e ){
			//TODO
			System.out.println("Failed to execute query in DBUpdate.");
			e.printStackTrace();
		}

	}
	
	/*	
	 * 
	 * TABLE REFERENCE
	 * 
	 * DB Administrator - Thomas Travis
	 * 
	 */
	
	/*
	mysql> describe tUsers;
	+----------------+---------------+------+-----+---------+-------+
	| Field          | Type          | Null | Key | Default | Extra |
	+----------------+---------------+------+-----+---------+-------+
	| EMail          | varchar(256)  | NO   | PRI | NULL    |       | //PRIMARY KEY
	| FirstName      | varchar(32)   | NO   |     | NULL    |       |
	| LastName       | varchar(32)   | NO   |     | NULL    |       |
	| Password       | varchar(32)   | NO   |     | NULL    |       |
	| CarDescription | varchar(2048) | YES  |     | NULL    |       |
	| NumSeats       | int(11)       | YES  |     | NULL    |       |
	| DeviationDist  | float         | YES  |     | NULL    |       |
	+----------------+---------------+------+-----+---------+-------+
	7 rows in set (0.01 sec)

	mysql> describe tLandmarks;
	+---------------+---------------+------+-----+---------+----------------+
	| Field         | Type          | Null | Key | Default | Extra          |
	+---------------+---------------+------+-----+---------+----------------+
	| Name          | varchar(64)   | YES  | MUL | NULL    |                |
	| Owner         | varchar(256)  | NO   | MUL | NULL    |                | //FOREIGN KEY Owner REFERENCES tUsers(EMail)
	| StreetAddress | varchar(32)   | NO   |     | NULL    |                | 	//UNIQUE INDEX( Name, Owner )
	| State         | varchar(32)   | NO   |     | NULL    |                |
	| City          | varchar(32)   | NO   |     | NULL    |                |
	| ZipCode       | varchar(5)    | NO   |     | NULL    |                |
	| Region        | varchar(256)  | YES  |     | NULL    |                |
	| Description   | varchar(2048) | YES  |     | NULL    |                |
	| Latitude      | float         | YES  |     | NULL    |                |
	| Longitude     | float         | YES  |     | NULL    |                |
	| LMID          | int(11)       | NO   | PRI | NULL    | auto_increment | //PRIMARY KEY
	+---------------+---------------+------+-----+---------+----------------+
	11 rows in set (0.00 sec)

	mysql> describe tRoutes;
	+---------------+--------------+------+-----+---------+----------------+
	| Field         | Type         | Null | Key | Default | Extra          |
	+---------------+--------------+------+-----+---------+----------------+
	| Name          | varchar(32)  | NO   | MUL | NULL    |                |
	| Owner         | varchar(256) | NO   | MUL | NULL    |                | //FOREIGN KEY Owner REFERENCES tUsers(EMail)
	| StartPosition | int(11)      | NO   | MUL | NULL    |                | 	//UNIQUE INDEX( Name, Owner )
	| EndPosition   | int(11)      | NO   | MUL | NULL    |                |
	| RID           | int(11)      | NO   | PRI | NULL    | auto_increment | //PRIMARY KEY
	+---------------+--------------+------+-----+---------+----------------+
	5 rows in set (0.00 sec)

	mysql> describe tTrips;
	+----------------+--------------+------+-----+---------+----------------+
	| Field          | Type         | Null | Key | Default | Extra          |
	+----------------+--------------+------+-----+---------+----------------+
	| TID            | int(11)      | NO   | PRI | NULL    | auto_increment | //PRIMARY KEY
	| Name           | varchar(32)  | NO   | MUL | NULL    |                | 
	| Owner          | varchar(256) | NO   | MUL | NULL    |                | //FOREIGN KEY Owner REFERENCES tUsers(EMail)
	| Route          | int(11)      | NO   | MUL | NULL    |                | //FOREIGN KEY Route REFERENCES tRoutes(RID)
	| MaxPassengers  | int(11)      | NO   |     | NULL    |                | 	//UNIQUE INDEX( Name, Owner )
	| AvailableSeats | int(11)      | NO   |     | NULL    |                |
	| Start          | bigint(20)   | NO   |     | NULL    |                |
	| StartDuration  | bigint(20)   | NO   |     | NULL    |                |
	| End            | bigint(20)   | NO   |     | NULL    |                |
	| EndDuration    | bigint(20)   | NO   |     | NULL    |                |
	+----------------+--------------+------+-----+---------+----------------+
	10 rows in set (0.00 sec)

	mysql> describe tRequests;
	+-------------------+--------------+------+-----+---------+----------------+
	| Field             | Type         | Null | Key | Default | Extra          |
	+-------------------+--------------+------+-----+---------+----------------+
	| RQID              | int(11)      | NO   | PRI | NULL    | auto_increment | //PRIMARY KEY
	| Name              | varchar(32)  | NO   | MUL | NULL    |                | 
	| Owner             | varchar(256) | NO   | MUL | NULL    |                | //FOREIGN KEY Owner REFERENCES tUsers(EMail)
	| Route             | int(11)      | NO   | MUL | NULL    |                | //FOREIGN KEY Route REFERENCES tRoutes(RID)
	| Departure         | bigint(20)   | NO   |     | NULL    |                | 	//UNIQUE INDEX( Name, Owner )
	| DepartureDuration | bigint(20)   | NO   |     | NULL    |                |
	| Arrival           | bigint(20)   | NO   |     | NULL    |                |
	| ArrivalDuration   | bigint(20)   | NO   |     | NULL    |                |
	| Trip              | int(11)      | YES  | MUL | NULL    |                | //FOREIGN KEY Trip REFERENCES tTrips(TID)
	| Status            | tinyint(1)   | NO   |     | NULL    |                |
	+-------------------+--------------+------+-----+---------+----------------+
	10 rows in set (0.00 sec)

	mysql> describe tPassengers;
	+----------+---------+------+-----+---------+----------------+
	| Field    | Type    | Null | Key | Default | Extra          |
	+----------+---------+------+-----+---------+----------------+
	| PID      | int(11) | NO   | PRI | NULL    | auto_increment |
	| RQID     | int(11) | NO   | MUL | NULL    |                | //FOREIGN KEY RQID REFERENCES tRequests(RQID)
	| TID      | int(11) | NO   | MUL | NULL    |                | //FOREIGN KEY TID REFERENCES tTrips(TID)
	| Accepted | int(11) | YES  |     | NULL    |                | 		//UNIQUE INDEX( RQID, TID )
	+----------+---------+------+-----+---------+----------------+
	4 rows in set (0.00 sec)

	*/

	
	
}