<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<a href="api/clients">CLIENTS API</a>
<br /><br />


<c:forEach items="${afp}" var="afp">
<span> AFP: ${afp.name} ${afp.percentaje} </span><br />
</c:forEach>

Clientes: <br /><br />

<c:forEach items="${taxpayers}" var="Client">
	
	Nombre: ${Client.name}<br />
	<c:forEach items="${Client.address}" var="add">
		Direccion: ${add.name}  ${add.number} <br />
	</c:forEach>
	<c:forEach items="${Client.subsidiary}" var="sucursal">
		Sucursal: ${sucursal.name} <br />
	</c:forEach>
	Template: ${Client.template}<br />
	<br />
	<br />

	
</c:forEach>

<form action="test" >
	<input type="submit"/>
	

</form>
<form action="importBook">
<input type="submit" value="Cargar Info Mensual" />
</form>


</body>
</html>