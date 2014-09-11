<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>You have a new Request!</title>
</head>
<body>


<table border="0">
        <thead>
       		<tr>
                <th>You have a new request</th>
            </tr>
            <tr>
                <th><%= session.getAttribute( "requester" ) %> would like a ride. Accept?</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>	
					<p></p>	
                    <form method="post" action="SAcceptRequest">
                        <input type="submit" value="Yes" name="Yes" />
                    </form>
					<form method="post" action="SDenyRequest">
						<input type="submit" value="No" name="No" />
                    </form>
                </td>
            </tr>
        </tbody>
    </table>

</body>
</html>