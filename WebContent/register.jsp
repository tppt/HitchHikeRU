<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HitchikeRU - Register</title>
</head>
<body>
    <h2><strong>HitchikeRU</strong>
    </h2>

    <table border="0">
        <thead>
            <tr>
                <th>Register</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    <form method="post" action="SRegister">
                        <strong>First Name:</strong>
                        <input type="text" name="fname" size="25">
                        <p></p>
                        <strong>Last Name:</strong>
                        <input type="text" name="lname" size="25">
                        <p></p>
                        <strong>Username(Your Rutgers Email):</strong>
                        <input type="text" name="useremail" size="25">
                        <p></p>
                        <strong>Password:</strong>
                        <input type="text" name="password" size="25">
                        <p></p>
                        <strong>Car Description(or "none" if no car):</strong>
                        <input type="text" name="car" size="25">
                        <p></p>
                        <input type="submit" value="Register" name="Register" />
                    </form>
                    <form method="get" action="index.jsp">
                        <input type="submit" value="Cancel" name="Cancel" />
                    </form>
                </td>
            </tr>
        </tbody>
    </table>
</body>
</html>