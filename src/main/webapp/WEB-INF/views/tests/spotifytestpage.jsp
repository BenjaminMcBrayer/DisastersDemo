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
<div class=container>
		<a
			href="https://accounts.spotify.com/authorize?client_id=f03957080db2440c8d93f34eacb90144&response_type=code&redirect_uri=http://localhost:8080/spotifycallback&scope=user-read-currently-playing%20user-read-playback-state%20user-modify-playback-state%20user-read-email">Login
			to Spotify.</a> <br> <a href="spotifysearchtest">Spotify Search
			Results</a> <br> <a href="getdeviceid">Get Device Ids</a>
	</div>
</body>
</html>