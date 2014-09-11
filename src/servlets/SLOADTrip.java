package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TripInterface;
import model.UserInterface;

import control.TripControl;
import control.UserControl;
import control.WebSessionManager;

/**
 * Servlet implementation class SLOADTrip
 */
@WebServlet("/SLOADTrip")
public class SLOADTrip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SLOADTrip() {
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
		String tripname = request.getParameter("tripname");

		TripInterface t = TripControl.getInstance().getTrip(useremail, tripname);
		
		//set session attributes to this trip
		Map<String, String> deniedRequests = new LinkedHashMap<String, String>();
		request.getSession().setAttribute("deniedRequests", deniedRequests);
		request.getSession().setAttribute("tripname", tripname);
		
		String url="/trip2.jsp"; 
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(url);
	    rd.forward(request, response);
	}

}
