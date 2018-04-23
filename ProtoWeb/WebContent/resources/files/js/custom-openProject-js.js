var retailMarkers=[];
var map;
var x=0;
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
	
	function addMarker(latitude, longitude,waarde) {
		var marker= new google.maps.Marker({
			position:{lat:latitude,lng:longitude},
			map:map,
			label:{text: String(waarde), color: "red",fontWeight: "bold"},
			icon:redIcon})
		marker.addListener('click', function() {
	        map.setZoom(16);
	        map.setCenter(marker.getPosition());
	      });
		
		// dit id is dynamisch dus bij verandering van form kan id veranderen en deze listener niet meer werken !!!
		document.getElementById("my-form:dataTable:"+x).addEventListener('click', function(){
			map.setZoom(16);
			map.setCenter(marker.getPosition());
		});
		x++;
		retailMarkers.push(marker);
	}
	
	
	
	
	var adressen=document.getElementsByClassName("completeAdres");
	console.log(adressen[0].childNodes[0].value);
	
	//call geocode
	for (var a = 0; a<adressen.length; a++){
	
	geocode(a);
	}
	
	
	function geocode(i){
		

			
		axios.get('https://maps.googleapis.com/maps/api/geocode/json',{
			params:{
				address:adressen[i].childNodes[0].value,
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
	        addMarker(lat,lng,(i+1));
	       
		})
		.catch(function(error){
			console.log(error);
		})
		
	}
}


function zoekPand(){
	var zoekBalk= document.getElementById("zoekTekst");
	var panden= document.getElementsByClassName("panden");
	var adres=document.getElementsByClassName("adres");
	var provincie=document.getElementsByClassName("provincie");
	var stad=document.getElementsByClassName("stad");
	var land=document.getElementsByClassName("land");
	var postcode=document.getElementsByClassName("postcode");
	var zoekOpties= [adres, provincie, stad, land, postcode];
	for(var i = 0; panden.length > i; i++){
		
		
		document.getElementById("checkAdres").disabled=false;
		document.getElementById("checkProvincie").disabled=false;
		document.getElementById("checkStad").disabled=false;
		document.getElementById("checkLand").disabled=false;
		document.getElementById("checkPostcode").disabled=false;
				
				if((!document.getElementById("checkAdres").checked) && (!document.getElementById("checkPostcode").checked) && (!document.getElementById("checkStad").checked) && (!document.getElementById("checkProvincie").checked) && (!document.getElementById("checkLand").checked) ){
					
					zoekBalk.disabled=true;
					zoekBalk.value="";
					zoekBalk.placeholder="Search...";
				}
				else{
					
					zoekBalk.disabled=false;
				}
				
				//zoek op adres
				if(document.getElementById("checkAdres").checked ){
					zoekBalk.placeholder="Adres...";
					document.getElementById("checkProvincie").disabled=true;
					document.getElementById("checkStad").disabled=true;
					document.getElementById("checkLand").disabled=true;
					document.getElementById("checkPostcode").disabled=true;
					
					if(!(adres[i].innerHTML.toLowerCase().includes(zoekBalk.value.toLowerCase()))){
						panden[i].style.display="none";
						
					}
					else{
						panden[i].style.display="";
						
					}
				}
				
				//zoek op postcode
				if(document.getElementById("checkPostcode").checked ){
					zoekBalk.placeholder="Postcode...";
					document.getElementById("checkAdres").disabled=true;
					document.getElementById("checkProvincie").disabled=true;
					document.getElementById("checkStad").disabled=true;
					document.getElementById("checkLand").disabled=true;
					
					if(!(postcode[i].innerHTML.toLowerCase().includes(zoekBalk.value.toLowerCase()))){
						panden[i].style.display="none";
					}
					else{
						panden[i].style.display="";
					}
				}
				
				//zoek op stad
				if(document.getElementById("checkStad").checked ){
					zoekBalk.placeholder="Stad...";
					document.getElementById("checkAdres").disabled=true;
					document.getElementById("checkProvincie").disabled=true;
					document.getElementById("checkLand").disabled=true;
					document.getElementById("checkPostcode").disabled=true;
			
					if(!(stad[i].innerHTML.toLowerCase().includes(zoekBalk.value.toLowerCase()))){
						panden[i].style.display="none";
					}
					else{
						panden[i].style.display="";
					}
				}
				
				//zoek op provincie
				if(document.getElementById("checkProvincie").checked ){
					zoekBalk.placeholder="Provincie...";
					document.getElementById("checkAdres").disabled=true;
					document.getElementById("checkStad").disabled=true;
					document.getElementById("checkLand").disabled=true;
					document.getElementById("checkPostcode").disabled=true;
			
					if(!(provincie[i].innerHTML.toLowerCase().includes(zoekBalk.value.toLowerCase()))){
						panden[i].style.display="none";
					}
					else{
						panden[i].style.display="";
					}
				}
				
				//zoek op land
				if(document.getElementById("checkLand").checked ){
					zoekBalk.placeholder="Land...";
					document.getElementById("checkAdres").disabled=true;
					document.getElementById("checkProvincie").disabled=true;
					document.getElementById("checkStad").disabled=true;
					document.getElementById("checkPostcode").disabled=true;
			
					if(!(land[i].innerHTML.toLowerCase().includes(zoekBalk.value.toLowerCase()))){
						panden[i].style.display="none";
					}
					else{
						panden[i].style.display="";
					}
				}
				
				
				
				
				
				
				
				
				
			
	}
	
}
