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
	
	
	
});

</script>

</head>
<body>



<table border="1">
	<tbody>
		<tr><td><h2>DETAILS</h2></td></tr>
		<c:forEach items="${pbd}" var="PBD">
		<tr >
			<td> 		
			<h3>Prevision: ${PBD.prevision}</h3>
			</td>
			<td> 		
			<h3>Total: ${PBD.suma}</h3>
			</td>
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