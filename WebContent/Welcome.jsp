<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/util.css">
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Pendrive Portal</title>
</head>
<body>
	<%
		response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Expires","0");    //Proxies
	%>
	<%
		if(session.getAttribute("username")==null)
		{	
			response.sendRedirect("login.html");	
			
		} else {%>
	 <br />
	<br />
	<center>
		<h2>Welcome to Pendrive Check-in / Check-out Portal</h2>
	</center>
	<br />
	<br />
	<form action="servlet2" method="post">
		<%
			String fileUserName = (String) request.getAttribute("name");
		%>
		<%
			String loginUserName = (String) request.getAttribute("UserName");
		%>
		<%
			int flag = (int) request.getAttribute("flag");
		%>
		<%
			int fileEmpty = (int) request.getAttribute("fileEmpty");
		%>
		<%
			String filePath = (String) request.getAttribute("filePath");
		%>
		<%
			if (fileEmpty == 1) {
		%>
		<br /> <br />
		<center>
			<b>Click here to use the Pendrive</b> &emsp;&emsp;
			<div class="container-login100-form-checkout">
				<input class="login100-form-checkout" type="submit"
					value="Check-Out" name="checkout" />
			</div>
		</center>
		<br /> <br />
		<center>
			<b>Click here to return the Pendrive</b> &emsp;
			<div class="container-login100-form-checkin">
				<input class="login100-form-checkin disabled" type="submit"
					value="Check-In" name="checkin" disabled="disabled" />
			</div>
		</center>
		<%
			} else {
		%>
		<%
			if (flag == 1) {
		%>
		<font color="red">
			<center>
				<p>
				<h2>
					<i>Pendrive is checked out by <strong><%=request.getAttribute("name")%></strong>
						on <strong><%=request.getAttribute("checkedOutDate")%></strong> <strong><%=request.getAttribute("checkedOutTime")%></strong>
					</i>
				</h2>
				</p>
			</center>
		</font> <br /> <br />
		<%
			if (fileUserName.equals(loginUserName)) {
		%>
		<center>
			<b>Click here to use the Pendrive</b> &emsp;&emsp;
			<div class="container-login100-form-checkout">
				<input class="login100-form-checkout disabled" type="submit"
					value="Check-Out" disabled="disabled" />
			</div>
		</center>
		<br /> <br />
		<center>
			<b>Click here to return the Pendrive</b> &emsp;
			<div class="container-login100-form-checkin">
				<input class="login100-form-checkin" type="submit" value="Check-In"
					name="checkin" />
			</div>
		</center>
		<%
			} else {
		%>
		<center>
			<b>Click here to use the Pendrive</b> &emsp;&emsp;
			<div class="container-login100-form-checkout">
				<input class="login100-form-checkout disabled" type="submit"
					value="Check-Out" disabled="disabled" />
			</div>
		</center>
		<br /> <br />
		<center>
			<b>Click here to return the Pendrive</b> &emsp;
			<div class="container-login100-form-checkin">
				<input class="login100-form-checkin disabled" type="submit"
					value="Check-In" disabled="disabled" />
			</div>
		</center>
		<%
			}
		%>
		<%
			} else {
		%>
		<font color="blue">
			<center>
				<p>
				<h2>
					<i>Pendrive is ready for <strong>Check-out</strong></i>
				</h2>
				</p>
			</center>
		</font> <br /> <br />
		<center>
			<b>Click here to use the Pendrive</b> &emsp;&emsp;
			<div class="container-login100-form-checkout">
				<input class="login100-form-checkout" type="submit"
					value="Check-Out" name="checkout" />
			</div>
		</center>
		<br /> <br />
		<center>
			<b>Click here to return the Pendrive</b> &emsp;
			<div class="container-login100-form-checkin">
				<input class="login100-form-checkin disabled" type="submit"
					value="Check-In" name="checkin" disabled="disabled" />
			</div>
		</center>
		<%
			}
		%>
		<%
			}
		%>
		<%
			if (loginUserName.equals("Admin")) {
		%>
		<br /> <br /> <br /> <br />
		<center>
			<div class="container-login100-form-logout">
				<input class="login100-form-logout" type="submit" value="DeleteFile"
					name="deleteFile" />
			</div>
		</center>
		<%
			}
		%>
		<br /> <br /> <br /> <br />
		<center>
			<div class="container-login100-form-logout">
				<input class="login100-form-logout" type="submit" value="Logout"
					name="logout" />
			</div>
		</center>
	</form>
 <% } %>
</body>
</html>