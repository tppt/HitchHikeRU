<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Request Sent</title>
</head>
<body>
<table>
<tbody>
<tr><td>
Request sent. When accepted, the offer will appear on the previous page.
<p></p>
Press "Ok" to return to previous page. You may send other requests in the meantime, but we recommend staying on that page and giving the driver some time to respond
<p></p>
<form method="post" action="request2.jsp">
<input type="submit" name="Ok" value="Ok" /> 
</form>
</td></tr>
</tbody> 
</table>
</body>
</html>