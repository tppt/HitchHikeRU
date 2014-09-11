<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String email = (String)session.getAttribute( "useremail");
	if (email==null || email.equals("")){
   		email = String.valueOf(request.getAttribute("useremail"));
   		session.setAttribute( "useremail", email );
	}
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HitchikeRU - Home</title>
</head>
<body>

<h2><strong>HitchikeRU</strong>
    </h2>

    <table border="0">
        <thead>
            <tr>
                <th>Welcome, <%= email %></th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <form method="post" action="SSetUpTrip">
                    	<input type="hidden" name="useremail" value=<%= email %> />
                        <input type="submit" value="Driving?" name="Driving?" />
                    </form>
                    <p></p>
                    <p>or...</p>
                    <p></p>
					<form method="post" action="SSetUpRequest">
						<input type="hidden" name="useremail" value=<%= email %>  />
						<input type="submit" value="Hitchhiking?" name="Request" />
                    </form>
                </td>
            </tr>
        <tr><td>
        <p></p>
        <form method="post" action="Schedule.jsp">
        <input type="submit" name="Upcoming Trips" value="Upcming Trips" />  
        </form>
        <form method="post" action="home.jsp">
        <input type="hidden" name="useremail" value=<%= email %>  />
        <input type="submit" name="Home" value="Home" />  
        </form>
        <form method="post" action="SLogout">
        <input type="hidden" name="useremail" value=<%= email %>  />
        <input type="submit" name="Log Out" value="Log Out" />  
        </form>
        </td></tr>
        </tbody>
      
    </table>
 
</body>
</html>