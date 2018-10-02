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
<title>Soundscape Test</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
	<div class="container">
		<form>
			<table>
				<thead>
					<tr>
						<th>Name</th>
						<th>Duration</th>
						<th>URL</th>
						<th>Rank</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="track" items="${tracks}">
						<tr>
							<td>${track.name}</td>
							<td>${track.duration}</td>
							<td><a href="${track.url}">${track.url}</a></td>
							<td>${track.attribution.rank}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>