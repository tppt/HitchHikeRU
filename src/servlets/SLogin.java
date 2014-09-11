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

import control.UserControl;
import control.WebSessionManager;

import model.UserInterface;

/**
 * Servlet implementation class SLogin
 */
@WebServlet("/SLogin")
public class SLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SLogin() {
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
	 * OUT TO CONTROL: username, password
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String useremail = request.getParameter("useremail");
		String password = request.getParameter("password");
		
    	//WebSessionManager.getInstance().login(useremail, password);
		//Contact control, retrieve the user. Create a session for that user.
		//userControl.login
		if (WebSessionManager.getInstance().login(useremail, password)==0){
			System.out.println("Logging in:"+useremail+", "+password);
			
			String url="/home.jsp"; 
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(url);
		    
		    request.setAttribute("useremail", useremail );
		    rd.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			//out.print("<html><head>");
			out.print("Invalid Login");
			//String url="/index.jsp";
			//ServletContext sc = getServletContext();
		   // RequestDispatcher rd = sc.getRequestDispatcher(url);
		   // rd.forward(request, response);
		    //SOMEHOW PRINT ERROR MESSAGE
		    
			
		}
	}

}
