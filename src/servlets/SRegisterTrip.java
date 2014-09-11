package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TimeInterval;
import model.UserInterface;

import control.TimeIntervalControl;
import control.TripControl;
import control.UserControl;
import control.WebSessionManager;

/**
 * Servlet implementation class SRegisterTrip
 */
@WebServlet("/SRegisterTrip")
public class SRegisterTrip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SRegisterTrip() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**IN FROM CONTROL: Confirmation
	 * OUT TO CONTROL: username, trip name
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String useremail = request.getParameter("useremail");
		String tripname = request.getParameter("tripname");
		int numseats = -1;
		try{
		numseats = Integer.parseInt(request.getParameter("numseats"));
		if (numseats > 7)
			numseats = -1;
		} catch (Exception e){
		}
		String departureTime = request.getParameter("date10");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		if (from.equals(to))
			numseats = -1;
		System.out.println("|"+useremail+"|"+tripname+"|"+numseats+"|"+departureTime+"|"+from+"|"+to);
		
		//UserInterface user = UserControl.getInstance().getUser(useremail);
		int i = WebSessionManager.getInstance().submitTrip(tripname, useremail, from, to, departureTime, numseats);
		
		if (i==0){
			Map<String, String> deniedRequests = new LinkedHashMap<String, String>();
			request.getSession().setAttribute("deniedRequests", deniedRequests);
			request.getSession().setAttribute("tripname", tripname);
			
			String url="/trip2.jsp"; 
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(url);
		    rd.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.println("Invalid Parameters(trip name must be unique. All values must be filled in.)");
		}
	}

}
