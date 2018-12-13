<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<script type="text/javascript">
	function load() {
		window.open('${ play }', '', 'width=,height=,resizable=no');
		window.open().close();
	}
</script>
<script>
var map, marker, i, infoWindow;
var style = [
    {
        "featureType": "administrative",
        "elementType": "geometry",
        "stylers": [
            {
                "visibility": "off"
            }
        ]
    }
];

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: {
            lat: 0,
            lng: 0,
        },
        styles: style
    });
    /* map.setOptions({
        styles: style
    }); */
    const gmarkers = ${ gmarkers };
    var markers;
    for (i = 0; i < gmarkers.length; i++) {
    	window.setTimeout(function () { markers.push(new google.maps.Marker({
            position: new google.maps.LatLng(gmarkers[i][1], gmarkers[i][2]),
            map: map,
            animation: google.maps.Animation.DROP
        }));
        }, i * 200);
    }
}
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB6Ye_VKh4Q8ewJl2p7mhvUZafcMN5aWZE&callback=initMap"
	async defer></script>

<style>
#map {
	height: 768px;
	width: 1024px;
}
</style>

<title>Google Maps Test</title>

</head>

<body>

	<div id="map"></div>

</body>

</html>