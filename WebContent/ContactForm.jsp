<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:import url="header.html" />
	
	<form action="SaveContactServlet" method="post">
		<input type="hidden" name="id" value="${contact.id}" />
		<input type="text" name="name" value="${contact.name}" />
		<input type="email" name="email" value="${contact.email}" />
		<input type="text" name="phone" value="${contact.phone}" />
		<input type="text" name="address" value="${contact.address}" />
		<input type="date" name="birthday" value="${contact.birthday}" />
		<input type="submit" value="Save">
	</form>

</body>
</html>