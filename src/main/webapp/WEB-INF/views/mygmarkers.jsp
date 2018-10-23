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
<title>My GMarkers</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
	<div class="container">
		<h3>My GMarkers</h3>
		<form>
			<table>
				<thead>
					<tr>
						<th>Title</th>
						<th>Lat</th>
						<th>Lng</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="gmarker" items="${gmarkers}">
						<tr>
							<td>${gmarker.name}</td>
							<td>${gmarker.lat}</td>
							<td>${gmarker.lng}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>
