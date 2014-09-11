package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RequestInterface;
import model.TripInterface;
import model.UserInterface;
import control.UserControl;

/**
 * Servlet implementation class SgetUserSchedule
 */
@WebServlet("/SgetUserSchedule")
public class SgetUserSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SgetUserSchedule() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String useremail = (String)request.getSession().getAttribute("useremail");
		ArrayList<TripInterface> trips = UserControl.getInstance().getUserTrips(useremail);
		ArrayList<RequestInterface> requests = UserControl.getInstance().getUserRequests(useremail);
		
		PrintWriter out = response.getWriter();
		
		out.println("<table>");
		out.println("<tbody>");
	
		out.println("<tr><td><b>Rides you're offering:</b></td></tr>");

		for (int i = 0; i < trips.size(); i++){
			TripInterface t = trips.get(i);
			if (t!=null){
				out.println("<tr><td>");
				out.println("<form method=\"post\" action=\"SLOADTrip\">");
				out.println(t.getName());
				out.println(" : FROM");
				out.println(t.getPointOfDeparture().getName());
				out.println(" TO ");
				out.println(t.getPointOfArrival().getName());
				out.println(" ON ");
				out.println(t.getDepartureTimeInterval().getStart());
				out.println("<input type=\"hidden\" name=\"tripname\" value=\""+t.getName()+"\" />");
				out.println("<input type=\"submit\" value=\"View Trip\" name=\"View Trip\" />");
				out.println("</form>");
				out.println("</td></tr>");
			}
		}

		out.println("<tr><td><b>Rides you're requesting:</b></td></tr>");

		for (int i = 0; i < requests.size(); i++){
			RequestInterface t = requests.get(i);
			if (t!=null){
				out.println("<form method=\"post\" action=\"SLOADRequest\">");
				out.println("<tr><td>");
				out.println(t.getName());
				out.println(" : FROM");
				out.println(t.getPointOfDeparture().getName());
				out.println(" TO ");
				out.println(t.getPointOfArrival().getName());
				out.println(" ON ");
				out.println(t.getDepartureTime().getStart());
				out.println("<input type=\"hidden\" name=\"requestname\" value=\""+t.getName()+"\" />");
				out.println("<input type=\"submit\" value=\"View Request\" name=\"View Request\" />");
				out.println("</form>");
				out.println("</td></tr>");
			}
		}
		out.println("</tbody>");
		out.println("</table>");
	}

}
