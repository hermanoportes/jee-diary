<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Diary | Contacts</title>
</head>
<body>
	<c:import url="header.html" />
	<form action="ListContactsServlet" method="get">
		<input type="text" name="filter" />
		<input type="submit" value="Search" />
	</form>
	 
	<table>
		<tr>
			<th>Name</th>		
			<th>Email</th>		
			<th>Phone</th>		
			<th>Address</th>		
			<th>Birthday</th>		
		</tr>
		<c:forEach var="contact" items="${contactList}">
			<tr>
				<td><c:out value="${contact.name}" /></td>
				<td><c:out value="${contact.email}" /></td>
				<td><c:out value="${contact.phone}" /></td>
				<td><c:out value="${contact.address}" /></td>
				<td><c:out value="${contact.birthday}" /></td>
				<td><a href="ContactFormServlet?id=${contact.id}">Edit</a></td>
				<td><a href="RemoveContactServlet?id=${contact.id}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
	<a href="ContactFormServlet">Add</a>
</body>
</html>