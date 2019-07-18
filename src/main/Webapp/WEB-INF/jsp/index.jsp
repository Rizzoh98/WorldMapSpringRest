<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script>
	function myFunction() {
		document.getElementById("demo").innerHTML = "2";
	}
</script>

</head>
<body>
	<h1>Da 1 a 2</h1>
	<p id="demo">1</p>
	<button type="button" onclick="myFunction()">Cambia</button>
</body>
</html>
