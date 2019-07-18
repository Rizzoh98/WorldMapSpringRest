<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="Save">
		<p>
			<input type="hidden" value="${citta.id}" name="id">
		</p>
		<p>
			<input type="text" name="cityname" value="${citta.name}">
		</p>
		<p>
			<select name="selectCountry">
				<c:forEach var="nazione" items="${result}">
					<c:choose>

						<c:when test="${nazione.countrycode == param.countrycode}">
							<option value="${nazione.countrycode}" selected>${nazione.name}</option>
						</c:when>

						<c:otherwise>
							<option value="${nazione.countrycode}">${nazione.name}</option>
						</c:otherwise>

					</c:choose>
				</c:forEach>

			</select> <input type="submit" value="Salva!">
		</p>
	</form>

</body>
</html>