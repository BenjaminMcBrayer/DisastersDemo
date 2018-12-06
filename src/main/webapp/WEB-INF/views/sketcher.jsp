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
<title>Event Sketcher</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
	<div>
		<p>Access Token: ${ accessToken }</p>
	</div>
	<div class="container">
		<form action="/returnmydisasterlist">
			<select name="usercat">
				<option value="6">Drought</option>
				<option value="7">Dust and Haze</option>
				<option value="16">Earthquakes</option>
				<option value="9">Floods</option>
				<option value="14">Landslides</option>
				<option value="19">Manmade</option>
				<option value="15">Sea and Lake Ice</option>
				<option value="10">Severe Storms</option>
				<option value="17">Snow</option>
				<option value="18">Temperature Extremes</option>
				<option value="12">Volcanoes</option>
				<option value="13">Water Color</option>
				<option value="8">Wildfires</option>
			</select> 
			Enter start date: <input type="date" name="userstartdate" /> 
			Enter end date: <input type="date" name="userenddate" /> 
			Soundscape: <select name="tag">
				<option value = "popular">Generic</option>
				
			</select>
			<input type="submit" value="Submit" />
		</form>
	</div>
	<div class=container>
		<a href="https://accounts.spotify.com/authorize?client_id=f03957080db2440c8d93f34eacb90144&response_type=code&redirect_uri=http://localhost:8080/spotifycallback&scope=user-modify-playback-state">Login
			to Spotify.</a> <br> <a href="spotifysearchtest">Spotify Search Results</a>
	</div>
</body>
</html>