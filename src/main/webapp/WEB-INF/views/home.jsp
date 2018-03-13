<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<%
System.out.println("IN JSP :: IsLogin ::"+session.getAttribute("IsLogin"));
System.out.println("IN JSP :: Username ::"+session.getAttribute("UserName"));
//if ("true".equals(session.getAttribute("IsLogin").toString())) {
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
		<div id="divPgFtr">
		<%@ include file="footer.jsp" %>
		</div>
	</div>
<%
//} else {
	%>
	<h1 align="center">Navigating back is restricted for security Reasons</h1>
	<%
//}
%>
</html>