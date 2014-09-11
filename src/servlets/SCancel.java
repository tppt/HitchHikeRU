package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.RequestControl;
import control.TripControl;

/**
 * Servlet implementation class SCancel
 */
@WebServlet("/SCancel")
public class SCancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SCancel() {
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
		String useremail = request.getParameter("useremail");
		String type = request.getParameter("type");
		
		if (type.equals("request")){
			//CANCEL CURRENT REQUEST
			String requestName = (String)request.getSession().getAttribute("requestname");
			if (!requestName.equals("")){
				RequestControl.getInstance().cancelRequest(useremail, requestName);
			}
			request.getSession().setAttribute("requestname", "");
		} else if (type.equals("trip")){
			//CANCEL CURRENT TRIP
			String tripName = (String)request.getSession().getAttribute("tripname");
			if (!tripName.equals("")){
				TripControl.getInstance().cancelTrip(useremail, tripName);
			}
			request.getSession().setAttribute("tripname", "");
		}
			
		
		
		String url="/home.jsp"; 
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(url);

	    request.setAttribute("useremail", useremail );
	    rd.forward(request, response);
	}

}
