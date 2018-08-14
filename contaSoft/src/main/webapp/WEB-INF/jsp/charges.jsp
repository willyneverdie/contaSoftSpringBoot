<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script type="text/javascript"> 
$( document ).ready(function() {
	
	
	function changeDisplay(id){
		$("#"+id).show();
	};

	console.log("test");
	
	$(document).on("click", "#button_details", function(){
		console.log("details");
		window.location.href = "/chargesDetails?id="+this.title; 
		
	});
	
});

</script>

</head>
<body>



<table border="1">
	<tbody>
		<tr><td><h2>CLIENTES</h2></td></tr>
		<c:forEach items="${pbi}" var="PBI">
		
		<tr >
			<td> 		
			<h3>ClientRut: ${PBI.rut}</h3>
			</td>
			<td><h3>Version: ${PBI.version}</h3>
			</td>
			<td><h3>Month: ${PBI.month}</h3>
			</td>
			<td><h3>Details: ${PBI.details}</h3>
			</td>
			<td><h3>Status: ${PBI.status}</h3>
			</td>
			<td><h3>FileName: ${PBI.fileName}</h3>
			</td>
			<td><input type="button" value="Ver Detalles" />
			</td>
			
			<td><input type="button" value="Ver Cotizaciones" id="button_details" title="${PBI.id}"/>
			</td>
			
			<form method="post" action="/process">
				<td><input type="submit" value="Procesar" /></td>
				<input type="hidden" name="id" value="${PBI.id}" />
			</form>
			
		</tr>
		
		</c:forEach>
	
	
	</tbody>
</table>

<!--				<c:forEach items="${Client.subsidiary}" var="sucursal">
						Sucursal: ${sucursal.name} <br />
					</c:forEach>
					Template: ${Client.template}<br />
					<br />
					<br />
  -->
	

<br /><br />


<form action = "importBook2" method = "post" enctype = "multipart/form-data">
         <input type = "file" name = "fileUpload" size = "50" />
         <br />
         <input type = "submit" value = "Upload File" />
</form>


</body>
</html>