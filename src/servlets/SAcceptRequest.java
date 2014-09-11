package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class SAcceptRequest
 */
@WebServlet("/SAcceptRequest")
public class SAcceptRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SAcceptRequest() {
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
	 * OUT TO CONTROL: username, requester name, trip name
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String useremail = (String)request.getSession().getAttribute("useremail");
		String requester = (String)request.getSession().getAttribute("requester");
		String tripname = (String)request.getSession().getAttribute("tripname");
		String requestname = (String)request.getSession().getAttribute("requestname");
		
		if (WebSessionManager.getInstance().acceptPassengerRequest(requestname, tripname, useremail, requester)==0){
			System.out.println();
			
			PrintWriter out = response.getWriter();
		    //out.println("<html><body>");

		    out.println("Accepted Request. You may close this window.");
		    
		    //out.println("</body></html>");
			
		} else {
			PrintWriter out = response.getWriter();
			//out.println("<html><body>");

		    out.println("The passanger has cancelled their request. You may close this window.");
		    
		    //out.println("</body></html>");
		}
	}

}
