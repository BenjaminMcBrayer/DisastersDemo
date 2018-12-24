<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<a href="/">Home</a>
	<span>
	<c:choose>
		<c:when test="${ not empty user }">
			Welcome ${ user.displayName }
			<a href="/logout">Logout</a>
		</c:when>
		<c:otherwise>
			<a href="https://accounts.spotify.com/authorize?client_id=f03957080db2440c8d93f34eacb90144&response_type=code&redirect_uri=http://localhost:8080/spotifycallback&scope=user-read-currently-playing%20user-read-playback-state%20user-modify-playback-state%20user-read-email">Login</a>
		</c:otherwise>
	</c:choose>
	</span>
</header>