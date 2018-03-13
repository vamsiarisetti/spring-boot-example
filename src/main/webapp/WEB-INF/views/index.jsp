<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/css/style.css">
<script type="text/javascript" src="/resources/js/app.js"></script>

<title>Login</title>
</head>
<body>

	<div class="loginform">
		<h1>Login Page</h1>
		<h5>${errorMsg}</h5>
		<form action="login" method="post" style="padding-top: 27px;">
			<i class="fa fa-user-o" style="font-size: 48px; color: aliceblue"></i>
			<input type="text" name="name" id="name" placeholder="Enter Username">

			<i class="fa fa-lock" style="font-size: 64px; color: aliceblue"></i>
			<input type="password" name="pwd" id="pwd" placeholder="Enter Password" />

			<input type="submit" value="Login" onclick="return validate();"></input>
			<br><br><br>
			<a href="forgetPwd" style="margin-left: 0px; margin-right: 32px;" >Forgot Password ??</a>
			<a href="newUser">New User Registration</a>
		</form>
	</div>

</body>
</html>