package servlets;

/**
 * @author Yuriy Garnaev
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.WebSessionManager;

import model.RequestInterface;

/**
 * Servlet implementation class SgetPassengers
 */
@WebServlet("/SgetPassengers")
public class SgetPassengers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SgetPassengers() {
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
		
		ArrayList<RequestInterface> passengers = WebSessionManager.getInstance().getPassengers(tripname, useremail);

		PrintWriter out = response.getWriter();
		//System.out.println(useremail + tripname + passengers.size());
		out.println("<table>");
		out.println("<th>Passengers:</th>");		
		for (int i = 0; i < passengers.size(); i++){
			out.println("<tr><td>"+passengers.get(i).getOwner()+"</td></tr>");
		}
		out.println("</table>");
	}

}


