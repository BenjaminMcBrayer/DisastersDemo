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
<!-- <link rel="stylesheet" href="/style.css" /> -->


<script>

var map, infoWindow;
//var image = 'src/main/resources/volcano-png-2.png';
function initMap() {
var markers = ${gmarkers};
var gmarkers = [["A",42,-83],["B",21,-78],["C",21,-49],["D",42,-41],["E",56,-62]];
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
    // gestureHandling: 'none',
    // zoomControl: false,
    // disableDefaultUI: true,
});
map.setOptions({
    styles: style
});


var infowindow = new google.maps.InfoWindow();
var marker, i;

for (i = 0; i < gmarkers.length; i++) {

    marker = new google.maps.Marker({
        position: new google.maps.LatLng(gmarkers[i][1], gmarkers[i][2]),
        map: map,
        animation: google.maps.Animation.DROP
    });
    google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
          infowindow.setContent(gmarkers[i][0]);
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

<!-- <script async defer
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCd-7BfjzGokHDoI-mxupjt2mfJSXDfIeo&callback=initMap"> -->

</script>
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

<div id="map">


</body>

</html>