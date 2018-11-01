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
<title>Spotify Test</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
	<form action="/" method="post"> 
		<input	type="submit" value="Submit"></input>
	</form>
	<a href="https://accounts.spotify.com/authorize?client_id=f03957080db2440c8d93f34eacb90144&response_type=code&redirect_uri=http://localhost:8080/spotifycallback">Click here, please.</a>
</head>
<body>

</body>
</html>