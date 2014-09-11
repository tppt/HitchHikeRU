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

import model.RequestInterface;
import model.TripInterface;
import control.RequestControl;
import control.TripControl;

/**
 * Servlet implementation class SLOADRequest
 */
@WebServlet("/SLOADRequest")
public class SLOADRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SLOADRequest() {
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
		String requestname = request.getParameter("requestname");

		RequestInterface t = RequestControl.getInstance().getRequest(useremail, requestname);
		
		//set session attributes to this request
		request.getSession().setAttribute("requestname", requestname);
		Map<String, TripInterface> matchingTrips = new LinkedHashMap<String, TripInterface>();
		request.getSession().setAttribute("matchingTrips", matchingTrips);
		
		String url="/request2.jsp"; 
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(url);
	    rd.forward(request, response);
	}

}
