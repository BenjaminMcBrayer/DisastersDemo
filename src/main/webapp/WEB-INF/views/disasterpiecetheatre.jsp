<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- <script type="text/javascript">
	function load() {
		window.open('${ play }', '', 'width=,height=,resizable=no');
		window.open().close();
	}
</script> -->

<style>
#map {
	height: 768px;
	width: 1024px;
}
</style>

<title>Google Maps Test</title>

</head>

<body>
<div id="floating-panel">
      <button id="drop" onclick="drop()">Sketch the Disasters</button>
    </div>
    <div id="map"></div>

<script>
var gmarkers = ${ gmarkers };
var positions = [];
var markers = [];
var map;

function createPositions() {
    for (var i = 0; i < gmarkers.length; ++i) {
        var subArray = gmarkers[i], item = {
            lat: subArray[1],
            lng: subArray[2]
        };
        positions.push(item);
    }
}

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: {
            lat: 0,
            lng: 0,
        }
    });
    createPositions();
}

function drop() {
    for (var i = 0; i < positions.length; ++i) {
        addMarkerWithTimeout(positions[i], i * 200);
    }
}

function addMarkerWithTimeout(position, timeout) {
    window.setTimeout(function () {
        markers.push(new google.maps.Marker({
            position: position,
            map: map,
            animation: google.maps.Animation.DROP
        }));
    }, timeout);
}
</script>
<script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB6Ye_VKh4Q8ewJl2p7mhvUZafcMN5aWZE
&callback=initMap">
    </script>
</body>

</html>