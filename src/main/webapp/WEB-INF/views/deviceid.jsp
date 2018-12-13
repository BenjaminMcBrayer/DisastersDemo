<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet"></link>
<title>Insert title here</title>
</head>
<body>
	Device ID (web): ${deviceId}
	<br> Status: Is_Active = ${is_active}
	<br> Session deviceId: ${deviceId}
	<br>
	<a href="playspotifytrack">Spotify Track Preview URL</a>
	<br>
	<a href="testspotifyplayer">Spotify Player Test</a>
</body>
</html>