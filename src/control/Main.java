package control;

import model.ObjectManager;
import model.Transactor;

/**
 * This kickstarts the application by starting a WebListener but provides no control logic.
 * @author Kyle Waranis
 */
public class Main {

	private static Transactor t = null;
	
	public static Transactor getTransactor(){
		if( t == null )
			t = new ObjectManager();
		return t;
	}
	
	/**
	 * Launches a WebListener object to start the application.
	 * @param args Arguments are ignored.
	 */
	public static void main(String[] args) {
		
		//TODO Call getInstance on any of the desired listeners.
		
		//TODO Call start all listeners.
	}

}
