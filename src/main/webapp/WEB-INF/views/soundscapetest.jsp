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
		<form action="/searchbytag">
			<p>
				<label for="tag">Search for Top Tracks by Tag</label> <br> <input
					type="text" name="tag" />
			</p>
			<p>
				<button>Submit</button>
			</p>
		</form>
	</div>
	<div class="container">
		<p>
			<a href="gettoptags">See Top Tags</a>
		</p>
	</div>
</body>
</html>