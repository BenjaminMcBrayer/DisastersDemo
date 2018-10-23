<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link rel="stylesheet"
	href="https://bootswatch.com/4/lumen/bootstrap.css" media="screen">
<link rel="stylesheet"
	href="https://bootswatch.com/_assets/css/custom.min.css">
<link rel="stylesheet" href="/style.css" /> -->
<style>
#map {
	height: 768px;
	width: 1024px;
}
</style>
<title>Google Map Test</title>
</head>
<body>
	<%-- <%@include file="partials/header.jsp"%>
	<p class="message">${ message }</p> --%>
	<div id="map"></div>
	<script>
		var gmarkers = ${gmarkers};
		var markers = [];
		var map;
		//var image = 'src/main/resources/volcano-png-2.png';
		var style = {
			"featureType" : "administrative",
			"elementType" : "geometry",
			"stylers" : [ {
				"visibility" : "off"
			} ]
		}
		map.setOptions({
			styles : style
		});
		function initMap() {
			map = new google.maps.Map(document.getElementById('map'), {
				center : {
					lat : 0,
					lng : 0,
				},
				zoom : 2,
				gestureHandling : 'none',
				zoomControl : false,
				disableDefaultUI : true,
			});
		}

		var infowindow = new google.maps.InfoWindow();
	    var marker, i;
	    
	    for (i = 0; i < gmarkers.length; i++) { 
	    	
	      marker = new google.maps.Marker({
	        position: new google.maps.LatLng(gmarkers[i][1], gmarkers[i][2]),
	        map: map,
	        animation: google.maps.Animation.DROP
	      });
	        marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png');

	      /* google.maps.event.addListener(marker, 'click', (function(marker, i) {
	        return function() {
	          infowindow.setContent(myLatLng[i][0]);
	          infowindow.open(map, marker);
	        }
	        
	      })(marker, i)); */
	      
	    } 
	    
	    /* marker = new google.maps.Marker({
	        position: new google.maps.LatLng(myLatLng1),
	        map: map
	      });
	    initAutocomplete(); */
		// Drop each marker after a timed interval.
/* 		 function drop() {
			clearMarkers();
			for (var i = 0; i < geocoordinates.length; ++i) {
				addMarkerWithTimeout(geocoordinates[i], i * 200);
			}
		}

		function addMarkerWithTimeout(position, timeout) {
			window.setTimeout(function() {
				markers.push(new google.maps.Marker({
					position : position,
					map : map,
					//	icon : image,
					animation : google.maps.Animation.DROP
				}));
			}, timeout);
		}

		function clearMarkers() {
			for (var i = 0; i < markers.length; ++i) {
				markers[i].setMap(null);
			}
			markers = [];
		} */
	</script>
	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCd-7BfjzGokHDoI-mxupjt2mfJSXDfIeo&callback=initMap">
		
	</script>
</body>
</body>
</html>