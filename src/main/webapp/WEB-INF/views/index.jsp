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
<title>Welcome</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
	<h3>Welcome</h3>
	<a href="/disastertest">Disasters by Event</a>
	<br>
	<a href="disastercategorytest">Disasters by Category</a>
	<br>
	<a href="/soundscapetest">Soundscapes</a>
	<br>
	<a href="sketcher">Sketcher</a>
</body>
</html>