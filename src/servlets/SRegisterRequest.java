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

import control.WebSessionManager;

import model.TripInterface;

/**
 * Servlet implementation class SRegisterTrip
 */
@WebServlet("/SRegisterRequest")
public class SRegisterRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SRegisterRequest() {
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
	 * OUT TO CONTROL: username, requestname
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String useremail = request.getParameter("useremail");
		String requestname = request.getParameter("requestname");
		int numseats = 1;
		int deviation = 0;
		String departureTime = request.getParameter("date10");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		try{deviation = Integer.parseInt(request.getParameter("deviation"));}
		catch (Exception e){numseats = -1;}
		if (from.equals(to))
			numseats = -1;
		ArrayList<TripInterface> matchingTips = WebSessionManager.getInstance().submitRequest(requestname, useremail, from, to, departureTime, numseats, deviation);
		//requestControl.createRequest
		if (matchingTips!=null){
			Map<String, TripInterface> matchingTrips = new LinkedHashMap<String, TripInterface>();
			request.getSession().setAttribute("matchingTrips", matchingTrips);
			request.getSession().setAttribute("requestname", requestname);
			
			String url="/request2.jsp"; 
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(url);
		    
		    rd.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.println("Invalid Parameters(Request name must be unique. All fields must be filled in.)");
		}
	}

}
