package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.MatchMaker;
import control.RequestControl;
import control.UserControl;

import model.RequestInterface;
import model.TripInterface;
import model.UserInterface;

/**
 * Servlet implementation class SgetTrip
 */
@WebServlet("/SgetTrips")
public class SgetTrips extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SgetTrips() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**Calls findTripMatches - STILL NEED THIS ONE
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String useremail = (String)request.getSession().getAttribute("useremail");
		String requestname = (String)request.getSession().getAttribute("requestname");
		UserInterface user = UserControl.getInstance().getUser(useremail);
		RequestInterface RI = RequestControl.getInstance().getRequest(user, requestname);
				
		ArrayList<TripInterface> matchingTrips = MatchMaker.getInstance().findTripMatches(RI);

		PrintWriter out = response.getWriter();
		
		out.println("<form method=\"post\" action=\"SSendRequest\">");
		
		TripInterface accepted = null;
		UserInterface driver;
		
		for (int i = 0; i < matchingTrips.size(); i++){
			if (accepted!=null)
				if (!matchingTrips.contains(accepted))
					accepted = null;
			if (matchingTrips.get(i)!=null && accepted==null){
				out.println("<input type = \"radio\" name = \"tripdriver\" value = \""+ matchingTrips.get(i).getDriver() 
						+ "\"> "+  matchingTrips.get(i).getDriver() 
						+ " is making this trip on " 
						+ matchingTrips.get(i).getDepartureTimeInterval().getStart()
						+"</input></td></tr>"  );
				out.println("<input type=\"hidden\" name=\"tripname\" value=\""+matchingTrips.get(i).getName()+"\" />");
				out.println("<p></p>");
				ArrayList<RequestInterface> acceptedRequests = matchingTrips.get(i).getPassengers();
				if (acceptedRequests.contains(RI)){
					accepted = matchingTrips.get(i);
				}
			}
		}

		out.println("<input type=\"submit\" value=\"Send Request\" name=\"Send Request\" />");
		out.println("</form>");
		
		
		//Check if any of these trips ACCEPTED.
		if (accepted!=null){
			driver = UserControl.getInstance().getUser(accepted.getDriver());
			out.println("<p></p>");
			out.println(driver.getFirstName()+ " "+driver.getLastName()+ " has accepted your request!");
			out.println("<p></p>");
			out.println("He/she will be driving a "+driver.getCarDescription());
			out.println("<p></p>");
			out.println("Please meet him/her by "+accepted.getPointOfDeparture().getName());
			out.println("<p></p>");
			out.println(" at "+ accepted.getDepartureTimeInterval().getStart());
			out.println("<p></p>");
			out.println("<p>(note: Please don't send any further requests if you already have a ride!)</p>");
			
			//String url="/request3.jsp"; 
		   // ServletContext sc = getServletContext();
		   // RequestDispatcher rd = sc.getRequestDispatcher(url);
		   // request.setAttribute("AcceptedTripInterface", accepted);
		   // request.setAttribute("AcceptedTripDriver", accepted.getDriver());
		    
		    //rd.forward(request, response);
		}
	}

}
