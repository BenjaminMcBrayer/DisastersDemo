<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet"
	href="https://bootswatch.com/4/lumen/bootstrap.css" media="screen">
<link rel="stylesheet"
	href="https://bootswatch.com/_assets/css/custom.min.css">
<link rel="stylesheet" href="/style.css" />
<style>
#map {
	height: 460px;
	width: 1380px;
}
</style>
<title>Google Map Test</title>
</head>
<body>
	<%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p>
	<!--The div element for the map -->
	<div id="map"></div>
	<script>
		function initMap() {

			// load the map
			var mapOptions = {
				center : {
					lat : 0,
					lng : 0,
				},
				zoom : 2.3,
				gestureHandling: 'none',
		        zoomControl: false,
				disableDefaultUI : true,
				mapTypeId : 'hybrid'
			};
			map = new google.maps.Map(document.getElementById('map'),
					mapOptions);

		}
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCd-7BfjzGokHDoI-mxupjt2mfJSXDfIeo&callback=initMap">
		
	</script>
</body>
</body>
</html>