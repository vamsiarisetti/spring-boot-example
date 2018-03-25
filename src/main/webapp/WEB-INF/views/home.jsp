<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link rel="stylesheet" href="/resources/css/bot.css">
</head>
<%
System.out.println("IN JSP :: IsLogin ::"+session.getAttribute("IsLogin"));
System.out.println("IN JSP :: Username ::"+session.getAttribute("UserName"));
if ("true".equals(session.getAttribute("IsLogin"))) {
%>
	<div id="main">
		<div id="divPgHdr">
		<%@ include file="header.jsp" %>
		</div>
		<div id="divPgBdy">
			<body>
				<h1>This is a landing page</h1>
			</body>
		</div>
		
		<div id="mySidenav" class="sidenav">
		  <a href="#" id="about">About</a>
		</div>

		<div id="divPgFtr" style="padding-top: 350px;">
		<%@ include file="footer.jsp" %>
		</div>
	</div>
<%
} else {
	%>
	<h1 align="center">Navigating back is restricted for security Reasons</h1>
	<%
}
%>
</html>