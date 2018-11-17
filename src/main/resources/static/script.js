var map, infoWindow
function initMap() {
	var markers = ${ gmarkers };
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
    	
    });
    map.setOptions({
        styles: style
    });
    
    var infowindow = new google.maps.InfoWindow();
    var marker, i;
    for (i = 0; i < markers.length; i++) {
        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                infowindow.setContent(markers[i][0]);
                infowindow.open(map, marker);
            }
        })(marker, i));
    }
};

function addMarkerWithTimeout(position, timeout) {
    window.setTimeout(function () {
        markers.push(new google.maps.Marker({
            position: new google.maps.LatLng(markers[i][1], markers[i][2]),
            map: map,
            animation: google.maps.Animation.DROP
        }));
    }, timeout);
}