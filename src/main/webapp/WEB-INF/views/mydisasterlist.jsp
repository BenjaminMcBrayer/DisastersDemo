<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>My Disaster List</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>

	<div class="container">
		<h3>My Disasters</h3>
		<form>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Title</th>
						<th>Coordinates</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="disaster" items="${disasters}">
						<tr>
							<td>${disaster.id}</td>
							<td>${disaster.title}</td>
							<td>${disaster.geometries}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<a href="/mygmarkers">See the GMarkers</a>
		</form>
	</div>
</body>
</html>