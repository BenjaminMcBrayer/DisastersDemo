<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
<script>

var map, infoWindow;
function initMap() {
var markers = ${gmarkers};

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
]

var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 2,
    center: {
        lat: 0,
        lng: 0,
    }            
    //gestureHandling: 'none',
    //zoomControl: false,
    //disableDefaultUI: true,
});
map.setOptions({
    styles: style
});


var infowindow = new google.maps.InfoWindow();
var marker, i;

for (i = 0; i < markers.length; i++) {

    marker = new google.maps.Marker({
        position: new google.maps.LatLng(markers[i][1], markers[i][2]),
        map: map,
        animation: google.maps.Animation.DROP
    });
    google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
          infowindow.setContent(markers[i][0]);
          infowindow.open(map, marker);
        }
      })(marker, i));

}
};
</script>

<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB6Ye_VKh4Q8ewJl2p7mhvUZafcMN5aWZE
&libraries=places&callback=initMap"
	async defer></script>

<style>
	#map {
            height: 768px;
            width: 1024px;
        }
</style>

<title>Google Map Test</title>

</head>

<body>
    
<div id="map"></div>

</body>

</html>