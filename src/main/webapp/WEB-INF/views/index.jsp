<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index</title>
</head>
<body>
	<h3>EONet Events</h3>

	<form action="search">
		<table>
			<thead>
				<tr>
					<th>ID</th>
					<th>Title</th>
					<th>Coordinates</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="event" items="${events}">
					<tr>
						<td>${event.id}</td>
						<td>${event.title}</td>
						<td>${event.geometries}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</body>
</html>
