<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet"
	href="https://bootswatch.com/4/lumen/bootstrap.css" media="screen">
<link rel="stylesheet"
	href="https://bootswatch.com/_assets/css/custom.min.css">
<link rel="stylesheet" href="/style.css" />
<title>Disasters by Category</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
</head>
<body>
	<div class="container">
		<h3>EONet Events by Category</h3>
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
	</div>
</body>
</html>