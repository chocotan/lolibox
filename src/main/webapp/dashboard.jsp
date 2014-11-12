<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Dashboard</title>


<link
	href='http://fonts.useso.com/css?family=Open+Sans:300,400,600&subset=latin,latin-ext'
	rel='stylesheet'>
<link rel="stylesheet"
	href="<%=basePath%>/assets/stylesheets/font-awesome.min.css">
<link href="http://cdn.bootcss.com/normalize/3.0.1/normalize.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=basePath%>/assets/stylesheets/dashboard.css">

</head>
<body class="theme-frost">

	<div id="main-wrapper">
		
		
		<div id="main-navbar" class="navbar navbar-inverse">
		
			<div class="navbar-inner">
				<div class="navbar-header">
					<a href="/" class="navbar-brand">DASHBOARD</a>
				</div>
			
				<div id="main-navbar-collapse"  class="collapse navbar-collapse main-navbar-collapse">
			
				</div>
			</div>
		</div>
		
		
		
	    <div id="main-menu-bg"></div>
		
	</div>

</body>
</html>