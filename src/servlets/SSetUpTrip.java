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

import model.LandmarkInterface;

import control.LandmarkControl;
import control.UserControl;

/**
 * Servlet implementation class SSetUpTrip
 */
@WebServlet("/SSetUpTrip")
public class SSetUpTrip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SSetUpTrip() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**IN FROM CONTROL: User routes, user landmarks, user trips
	 * OUT TO CONTROL: username
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String useremail = request.getParameter("useremail");

		ArrayList<LandmarkInterface> LandmarkAL = UserControl.getInstance().getSavedLandmarks(useremail);
		ArrayList<LandmarkInterface> AdminLandmarkAL = UserControl.getInstance().getSavedLandmarks("testuser@rutgers.edu");
		Map<String, String> landmarks = new LinkedHashMap<String, String>();
		for (int i = 0; i < LandmarkAL.size(); i++){
			landmarks.put(i+"", LandmarkAL.get(i).getName());
		}
		for (int i = LandmarkAL.size(); i < LandmarkAL.size()+AdminLandmarkAL.size(); i++){
			landmarks.put(i+"", AdminLandmarkAL.get(i).getName());
		}
		
		String currentTrip = (String)request.getSession().getAttribute("tripname");
		/*if (currentTrip!= null && !currentTrip.equals("")){
			//RESUME CURRENT TRIP
			String url="/trip2.jsp"; 
		    ServletContext sc = getServletContext();
		    RequestDispatcher rd = sc.getRequestDispatcher(url);
		    rd.forward(request, response);
		}
		else {*/
			if (useremail != null){
				request.getSession().setAttribute("tripname", "");
				String url="/trip.jsp"; 
			    ServletContext sc = getServletContext();
			    RequestDispatcher rd = sc.getRequestDispatcher(url);
			    
			    request.setAttribute("useremail", useremail );
			    
			    request.setAttribute("landmarks", landmarks);
			    rd.forward(request, response);
			} else {
				PrintWriter out = response.getWriter();
				out.println("Error");
			}
		//}
	}

}
