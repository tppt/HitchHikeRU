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

<title>Awaiting departure</title>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
   	 			<script type="text/javascript">
		   	 		$(document).ready(function() { 
		    		    	$.ajax({
		    		    		type: "post",
			                    url: "SGetRequests2", 
			                    data: "input="+"",
			                    success: function(msg){    
			                            $('#output').append(msg);
			                    }
		            		})
		    		});
			    </script>
				<script type="text/javascript">
		   	 		$(document).ready(function() {
		    		    interval = window.setInterval(function(){
		    		    	$.ajax({
		    		    		type: "post",
			                    url: "SGetRequests2", 
			                    data: "input="+"",
			                    success: function(msg){    
			                            $('#output').append(msg);
			                    }
		            		})
		    		    },15000);
		    		});
			    </script>
			    
   	 			<script type="text/javascript">
		   	 		$(document).ready(function() {
		    		    interval = window.setInterval(function(){
		    		    	$.ajax({
		    		    		type: "post",
			                    url: "SgetPassengers", 
			                    data: "input="+"",
			                    success: function(msg){    
			                    		$("#output").html("");
			                            $('#output').append(msg);
			                    }
		            		})
		    		    },3000);
		    		});
			    </script>

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
                <th>Awaiting departure</th>
            </tr>
        </thead>
        <tbody>
            <tr>
            <td>
				<p></p>
    			<div id="output"></div>  
    			<p></p>
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