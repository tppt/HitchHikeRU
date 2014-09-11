<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <h2>Welcome to <strong>IFPWAAAAFCAD</strong>, the International Former
        Professional Wrestlers' Association for Counseling and Development!
    </h2>

    <table border="0">
        <thead>
            <tr>
                <th>IFPWAFCAD offers expert counseling in a wide range of fields.</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>To view the contact details of an IFPWAFCAD certified former
                    professional wrestler in your area, select a subject below:</td>
            </tr>
            <tr>
                <td>
                    <form action="response.jsp">
                        <strong>Select a subject:</strong>
                        <select name="subject_id">
                            <option></option>
                        </select>
                        <input type="submit" value="submit" name="submit" />
                    </form>
                </td>
            </tr>
        </tbody>
    </table>
</body>
</html>