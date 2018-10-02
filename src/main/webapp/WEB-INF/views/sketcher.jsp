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
<title>Insert title here</title>
</head>
<body>
<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
	<div class="container">
	<form action="/sketch">
		<select name="category">
			<option>Drought</option>
			<option>Dust and Haze</option>
			<option>Earthquakes</option>
			<option>Floods</option>
			<option>Landslides</option>
			<option>Manmade</option>
			<option>Sea and Lake Ice</option>
			<option>Severe Storms</option>
			<option>Snow</option>
			<option>Temperature Extremes</option>
			<option>Volcanoes</option>
			<option>Water Color</option>
			<option>Wildfires</option>
		</select>
	</form>
	</div>
</body>
</html>