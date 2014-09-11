package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.WebSessionManager;

/**
 * Servlet implementation class SSendRequest
 */
@WebServlet("/SSendRequest")
public class SSendRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SSendRequest() {
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
		String tripdriver = (String)request.getParameter("tripdriver");
		String tripname = (String)request.getParameter("tripname");
		String requestname = (String)request.getSession().getAttribute("requestname");
		System.out.println(WebSessionManager.getInstance().submitPassengerRequest(requestname, tripname, tripdriver, useremail));
		
		/*
		PrintWriter out = response.getWriter();
	    out.println("<html><body>");
	    out.println("<script type=\"text/javascript\">");
	    out.println("var popwin = window.open(\"RequestSentPopup.jsp\")");
	    out.println("</script>");
	    out.println("</body></html>");*/
		
		String url="/RequestSentPopup.jsp"; 
	    ServletContext sc = getServletContext();
	    RequestDispatcher rd = sc.getRequestDispatcher(url);
	    rd.forward(request, response);
	}

}
