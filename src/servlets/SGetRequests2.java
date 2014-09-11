package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.RequestInterface;
import control.WebSessionManager;

/**
 * Servlet implementation class SGetRequests2
 */
@WebServlet("/SGetRequests2")
public class SGetRequests2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SGetRequests2() {
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
		String tripname = (String)request.getSession().getAttribute("tripname");
		String requestemail = null;
		String requestname = null;
		ArrayList<RequestInterface> RequestAL = WebSessionManager.getInstance().getPassengerRequests(tripname, useremail);
		if (RequestAL.size()>0){
			RequestInterface req = RequestAL.get(0);
			requestemail = req.getOwner();
			requestname = req.getName();
		
			request.getSession().setAttribute("requester", requestemail);
			request.getSession().setAttribute("requestname", requestname);
			PrintWriter out = response.getWriter();
		    out.println("<html><body>");
		    out.println("<script type=\"text/javascript\">");
		    out.println("var popwin = window.open(\"NewRequest.jsp\")");
		    
		    out.println("</script>");
		    out.println("</body></html>");
		
		} 
	}

}
