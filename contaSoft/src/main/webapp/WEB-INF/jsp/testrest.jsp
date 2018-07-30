<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TEST REST</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript">

$( document ).ready(function() {

	  console.log("testing")
	
	  
	
	
	//search for 1 client paybooks 
	$(document).on("click", "#libros", function(){
	  
		var clientRut = $(this).attr('title');
	  //alert (clientRut);
	  $.ajax({
		  url: "api/paybookinstance/" + clientRut
		  
		}).done(function( result ) {
		  	console.log("ajax success");
		  	alert(result[0].rut+" "+result[0].version+" "+result[0].month);
		}).fail(function() {
		    alert( "error" );
		  });
	});
	
	
	
	//search for clients  
	$( "#title" ).click( function() {
	  console.log("consoletext");
	  
	  $.ajax({
		  url: "api/clients"
		  
		}).done(function( result ) {
		  	console.log("ajax success");
		  	for(var i = 0; i < result.length; i++) 
			{
				var obj = result[i];
				$("#clients").append(
						"RUT="+obj.rut + " Nombre=" +
						obj.name + "<br/>" +
						"Direccion="+obj.address[0].name + "<br/>" +
						"Sucursal="+obj.subsidiary[0].name + "<br/>" +
						"payBookInstance="+ 
						"<input type='submit' value='libros' id='libros' title='"+obj.rut+"'/>" + 
						"<input type='submit' value='templates' id='templates' title='"+obj.id+"'/>" +
						"<hr />");
				
			 }	
	  	    
		  	
		  	
		}).fail(function() {
		    alert( "error" );
		  });
	  
	  
	  
	});
})
</script>
</head>

<body>

	
	<div id="title">
		<h1>GET CLIENTS</h1>
	</div>
	<div id="clients">
	
	</div>


</body>
</html>