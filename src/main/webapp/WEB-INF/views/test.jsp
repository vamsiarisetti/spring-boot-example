<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<head>
<title>Users</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1.0"	 />

</head>
<body>
	<h1>WelcomeMessage: ${WelcomeMsg}</h1>

	<table border="1" align="center">
		<tr>
			<th>Id</th>
			<th>name</th>
			<th>userlocation</th>
		</tr>
		<c:forEach var="user" items="${UserInfo}">
		<tr>
			<td>${user.userinfoid}</td>
			<td>${user.username}</td>
			<td>${user.userloc}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>