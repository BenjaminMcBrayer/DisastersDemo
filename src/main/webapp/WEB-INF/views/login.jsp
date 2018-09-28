<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <link rel="stylesheet" href="https://bootswatch.com/4/lumen/bootstrap.css" media="screen">
    <link rel="stylesheet" href="https://bootswatch.com/_assets/css/custom.min.css">
<link rel="stylesheet" href="/style.css" />
<title>Login</title>
</head>
<body>
	<%@include file="partials/header.jsp" %>
	<div class = "container">
	<p class="message">${ message }</p>
	<h3>Login</h3>
	<form action="/login" method="post">
		<p>
			<label for="username">Username:</label> <input id="username" name="username"/>
		</p>		<p>
			<label for="password">Password:</label> <input id="password" type="password" name="password"/>
		</p>
		<p>
			<button>Submit</button>
		</p>
	</form>
	</div>
</body>
</html>