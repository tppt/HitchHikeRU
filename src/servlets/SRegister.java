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

import control.EmailHandler;
import control.WebSessionManager;

/**
 * Servlet implementation class SRegister
 */
@WebServlet("/SRegister")
public class SRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SRegister() {
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
	 * OUT TO CONTROL: username, password, fname, lname, car desc, dev distance, num seats
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String useremail = request.getParameter("useremail");
		String password = request.getParameter("password");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String car = request.getParameter("car");
		int i = WebSessionManager.getInstance().registerUser(fname, lname, useremail, password, car, 2, 10);
		if (i==0){
			EmailHandler.getInstance().sendMessage(useremail, "HitchhikeRU registration", "You have been registered successfully! You may now log in.");

			String url="/registered.jsp"; 
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(url);

		    request.setAttribute("useremail", useremail );
		    rd.forward(request, response);
		}else if (i == 1){
			PrintWriter out = response.getWriter();
			//out.print("<html><head>");
			out.print("That user name already exists. ");
			
		} else {
			
			PrintWriter out = response.getWriter();
			//out.print("<html><head>");
			out.print("Please fill out all fields. ");
				
		}
	}

}
