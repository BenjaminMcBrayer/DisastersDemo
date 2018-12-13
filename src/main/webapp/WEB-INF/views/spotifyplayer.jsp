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
<script type="text/javascript">
	function load() {
		window.open('${ play }', '', 'width=,height=,resizable=no');
		window.open().close();
	}
</script>
<title>Insert title here</title>
</head>
<body onload="load()">
</body>
</html>