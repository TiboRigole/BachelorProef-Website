var map;
var smallBlueIcon;
var smallRedIcon;
var measure;


function initMap(){
	
	// Map options
	var options = {
			zoom:7,
			center:{lat:50.50, lng: 4.00},
			draggableCursor: "crosshair"
	}
	// new map
	map= new google.maps.Map(document.getElementById('map'), options);
	
	google.maps.event.addListener(map, "click", function(evt) {
        // When the map is clicked, pass the LatLng obect to the measureAdd function
        measureAdd(evt.latLng);
    });
	
	var blueIcon={
			url:'https://retailsonar.com/themes/retailsonar/assets/images/icons/location-blue.svg',
			scaledSize: new google.maps.Size(30,30)
	}
	smallBlueIcon={
			url:'https://retailsonar.com/themes/retailsonar/assets/images/icons/location-blue.svg',
			scaledSize: new google.maps.Size(15,15)
	}
	var redIcon={
			url:'https://retailsonar.com/themes/retailsonar/assets/images/icons/location-red.svg',
			scaledSize: new google.maps.Size(30,30)
	}
	smallRedIcon={
			url:'https://retailsonar.com/themes/retailsonar/assets/images/icons/location-red.svg',
			scaledSize: new google.maps.Size(15,15)
	}
	
	function addMarker(latitude, longitude) {
		var marker= new google.maps.Marker({
			position:{lat:latitude,lng:longitude},
			map:map,
			label:{text:"x", color: "red",fontWeight: "bold"},
			icon:redIcon})
			marker.addListener('click', function() {
	        map.setZoom(16);
	        map.setCenter(marker.getPosition());
	      });
		
	}
	
	
	var adresDelen=document.getElementsByClassName("adres");
	console.log(adresDelen);
	var adres="";
	for(var a=0; a<adresDelen.length; a++){
		adres+=adresDelen[a].value;
		adres+=" ";
	}
	console.log(adres);
	
	// call geocode
	geocode(adres);
	
	
	
	function geocode(adres){
		
			
		axios.get('https://maps.googleapis.com/maps/api/geocode/json',{
			params:{
				address:adres,
				key:'AIzaSyBRZNaT7fSLOt4IaEhRg8J3-4JlmQx7x0M'
			}
		})
		.then(function(response){
			// log full response
			console.log(response);
					
			// Geometry
	        var lat = response.data.results[0].geometry.location.lat;
	        var lng = response.data.results[0].geometry.location.lng;
	        
	        
	        console.log(lat);
	        console.log(lng);
	        addMarker(lat,lng);
	       
		})
		.catch(function(error){
			console.log(error);
		})
		
	}
	
	// oppervlakte en afstand meten
	measure = {
		    mvcLine: new google.maps.MVCArray(),
		    mvcPolygon: new google.maps.MVCArray(),
		    mvcMarkers: new google.maps.MVCArray(),
		    line: null,
		    polygon: null
		};

	function measureAdd(latLng) {

	    // Add a draggable marker to the map where the user clicked
	    var marker = new google.maps.Marker({
	        map: map,
	        position: latLng,
	        draggable: true,
	        raiseOnDrag: false,
	        title: "Drag me to change shape",
	        icon: smallBlueIcon
	    });

	    // Add this LatLng to our line and polygon MVCArrays
	    // Objects added to these MVCArrays automatically update the line and polygon shapes on the map
	    measure.mvcLine.push(latLng);
	    measure.mvcPolygon.push(latLng);

	    // Push this marker to an MVCArray
	    // This way later we can loop through the array and remove them when measuring is done
	    measure.mvcMarkers.push(marker);

	    // Get the index position of the LatLng we just pushed into the MVCArray
	    // We'll need this later to update the MVCArray if the user moves the measure vertexes
	    var latLngIndex = measure.mvcLine.getLength() - 1;

	    // When the user mouses over the measure vertex markers, change shape and color to make it obvious they can be moved
	    google.maps.event.addListener(marker, "mouseover", function() {
	        marker.setIcon(smallRedIcon);
	    });

	    // Change back to the default marker when the user mouses out
	    google.maps.event.addListener(marker, "mouseout", function() {
	        marker.setIcon(smallBlueIcon);
	    });

	    // When the measure vertex markers are dragged, update the geometry of the line and polygon by resetting the
	    //     LatLng at this position
	    google.maps.event.addListener(marker, "drag", function(evt) {
	        measure.mvcLine.setAt(latLngIndex, evt.latLng);
	        measure.mvcPolygon.setAt(latLngIndex, evt.latLng);
	    });

	    // When dragging has ended and there is more than one vertex, measure length, area.
	    google.maps.event.addListener(marker, "dragend", function() {
	        if (measure.mvcLine.getLength() > 1) {
	            measureCalc();
	        }
	    });
	    

	    // If there is more than one vertex on the line
	    if (measure.mvcLine.getLength() > 1) {

	        // If the line hasn't been created yet
	        if (!measure.line) {

	            // Create the line (google.maps.Polyline)
	            measure.line = new google.maps.Polyline({
	                map: map,
	                clickable: false,
	                strokeColor: "#FF0000",
	                strokeOpacity: 1,
	                strokeWeight: 3,
	                path:measure. mvcLine
	            });

	        }

	        // If there is more than two vertexes for a polygon
	        if (measure.mvcPolygon.getLength() > 2) {

	            // If the polygon hasn't been created yet
	            if (!measure.polygon) {

	                // Create the polygon (google.maps.Polygon)
	                measure.polygon = new google.maps.Polygon({
	                    clickable: false,
	                    map: map,
	                    fillOpacity: 0.25,
	                    strokeOpacity: 0,
	                    paths: measure.mvcPolygon
	                });

	            }

	        }

	    }

	    // If there's more than one vertex, measure length, area.
	    if (measure.mvcLine.getLength() > 1) {
	        measureCalc();
	    }

	}

	function measureCalc() {

	    // Use the Google Maps geometry library to measure the length of the line
	    var length = google.maps.geometry.spherical.computeLength(measure.line.getPath());   
	    document.getElementById("span-length").innerHTML = length.toFixed(2);

	    // If we have a polygon (>2 vertexes in the mvcPolygon MVCArray)
	    if (measure.mvcPolygon.getLength() > 2) {
	        // Use the Google Maps geometry library to measure the area of the polygon
	        var area = google.maps.geometry.spherical.computeArea(measure.polygon.getPath());
	        document.getElementById("span-area").innerHTML = area.toFixed(2);
	    }

	}

	
	
}
function measureReset() {

    // If we have a polygon or a line, remove them from the map and set null
    if (measure.polygon) {
        measure.polygon.setMap(null);
        measure.polygon = null;
    }
    if (measure.line) {
        measure.line.setMap(null);
        measure.line = null
    }

    // Empty the mvcLine and mvcPolygon MVCArrays
    measure.mvcLine.clear();
    measure.mvcPolygon.clear();

    // Loop through the markers MVCArray and remove each from the map, then empty it
    measure.mvcMarkers.forEach(function(elem, index) {
        elem.setMap(null);
    });
    measure.mvcMarkers.clear();

    document.getElementById("span-area").innerHTML = "";
    document.getElementById("span-length").innerHTML = "";

}


