<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

 <%
   String email = (String)session.getAttribute("useremail");
%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HitchikeRU - Trip Registration</title>    

		<!--Requirement jQuery-->
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <!--Load Script and Stylesheet -->
        <script type="text/javascript" src="addons/jquery.simple-dtpicker.js"></script>
        <link type="text/css" href="addons/jquery.simple-dtpicker.css" rel="stylesheet" />
        <!---->


            
</head>
<body>
    <h2><strong>HitchikeRU</strong>
    </h2>

    <table border="0">
        <thead>
       		<tr>
                <th>Welcome, <%= session.getAttribute( "useremail" ) %></th>
            </tr>
            <tr>
                <th>Enter Trip Information</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>	
					<p></p>	
                    <form method="post" action="SRegisterTrip">
                    <input type="hidden" name="useremail" value=<%= email %> />
                    	<strong>Time of departure:</strong>
                    	<input type="text" name="date10" value="">
                        <script type="text/javascript">
                                $(function(){
                                        $('*[name=date10]').appendDtpicker({
                                                "closeOnSelected": true
                                        });
                                });
                        </script>
                        <p></p>
						<strong>From:</strong>
                		<select name="from">
    						<c:forEach items="${landmarks}" var="landmarks">
       							<option value="${landmarks.value}">${landmarks.value}</option>
    						</c:forEach>
						</select>
						<p></p>
						<strong>To:</strong>
                		<select name="to">
    						<c:forEach items="${landmarks}" var="landmarks">
       							<option value="${landmarks.value}">${landmarks.value}</option>
    						</c:forEach>
						</select>
						<p></p>
                        <strong># of seats:</strong>
                        <input type="text" name="numseats" size="25">
                        <p></p>
                        <strong>Trip name(for your reference)</strong>
                        <input type="text" name="tripname" size="25">
                        <p></p>
                        <input type="hidden" name="useremail" value=<%= email %> />
                        <input type="submit" value="Go" name="Go" />

                    </form>
					<form method="post" action="SCancel">
						<input type="hidden" name="useremail" value=<%= email %> />
						<input type="hidden" name="type" value="trip" />
						<input type="submit" value="Cancel" name="Cancel" />
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