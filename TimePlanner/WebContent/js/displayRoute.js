
var map;
var directionsDisplay1, directionsDisplay2;
var markersArray = [];
var infowindow;
var directionsService;
var geocoder;

function drawMarker(point, map, title, text) {
	var marker = new google.maps.Marker({
		position : point,
		map : map,
		title : title,
		icon : 'http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld='
				+ text + '|FE6256|000000'
	});
	visible: true;

	var contentString = '<div style="width:120px;height:60px" id="bubble">'
			+ '<b>Restaurant</b>'
			+ '<div id="fay">' + '<p>dadadadadada</p>'
			+ '</div>' 
			+ '</div>';

	//if there is another open InfowWindow, close it.
	if (infowindow)
		infowindow.close();
	addInfoWindow(marker, contentString);
	//add marker to the Marker's array
	markersArray.push(marker);
}

function addInfoWindow(marker, contentString) {

	infoWindow = new google.maps.InfoWindow({
		content : contentString
	});

	google.maps.event.addListener(marker, 'click', function() {
		infoWindow.open(map, marker);
	});
}

function clearMarkers() {
	for (var i = 0; i < markersArray.length; i++) {
		markersArray[i].setMap(null);
	}
	markersArray = [];
}

function initializeMap() {

	//alert("initialize");
	var mapOptions = {
		zoom : 6,
		center : new google.maps.LatLng(52.5167, 13.3833),
		unitSystem : google.maps.UnitSystem.METRIC,
		draggable: true
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	geocoder = new google.maps.Geocoder();
	
	directionsService = new google.maps.DirectionsService();
	directionsDisplay1 = new google.maps.DirectionsRenderer({
		suppressMarkers : true,
	});
	directionsDisplay1.setMap(map);
	directionsDisplay1.setPanel(document.getElementById('directions-panel1'));
	directionsDisplay2 = new google.maps.DirectionsRenderer({
		suppressMarkers : true,
	});
	directionsDisplay2.setMap(map);
	directionsDisplay2.setPanel(document.getElementById('directions-panel2'));

}

function getLocationData(addresses, callback) {
	var coords = [];
	for (var i = 0; i < addresses.length; i++) {
		currAddress = addresses[i];
		var geocoder = new google.maps.Geocoder();
		if (geocoder) {
			geocoder.geocode({
				'address' : currAddress
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					coords.push(results[0].geometry.location);
					if (coords.length == addresses.length) {
						if (callback && typeof (callback) === "function") {
							callback(coords);
						}
					}
				} else {
					throw ('No results found: ' + status);
				}
			});
		}
	}
}

function calcRoute() {
	//alert("calculate route");
	var addresses = new Array();
	addresses[0] = "Mollwitzstraße 3, Berlin";
	addresses[1] = "Goltzstraße 20, Berlin";
	addresses[2] = "Goltzstraße 150, Berlin";

	getLocationData(addresses, function(coords) {

		drawMarker(coords[0], map, addresses[0], '1');
		drawMarker(coords[1], map, addresses[1], '2');
		drawMarker(coords[2], map, addresses[2], '3');

		var request1 = {
			origin : addresses[0],
			destination : addresses[1],
			travelMode : google.maps.TravelMode.TRANSIT,
			transitOptions : {
				arrivalTime : new Date(1337675679473)
			},
			unitSystem : google.maps.UnitSystem.METRIC
		};
		directionsService.route(request1, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				$('#directions-info1').html(
						"<b>Start Location to Cinema</b>");
				directionsDisplay1.setDirections(response);
			}
		});

		var request2 = {
			origin : addresses[1],
			destination : addresses[2],
			travelMode : google.maps.TravelMode.WALKING,
			unitSystem : google.maps.UnitSystem.METRIC
		};
		directionsService.route(request2, function(response, status) {
			if (status == google.maps.DirectionsStatus.OK) {
				$('#directions-info2').html(
						'<b>Cinema to Restaurant</b>');
				directionsDisplay2.setDirections(response);
			}
		});

		 var bounds = new google.maps.LatLngBounds();

		    for (var i = 0; i < coords.length; i++) {
		      //  And increase the bounds to take this point
		      bounds.extend (coords[i]);
		    }
		    //  Fit these bounds to the map
		    map.fitBounds (bounds);
	});

}
