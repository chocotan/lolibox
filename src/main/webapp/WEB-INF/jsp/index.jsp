<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
  import="io.loli.box.startup.LoliBoxConfig"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Lolibox-一个方便、快捷的图床程序</title>
<c:import url="${pageContext.request.contextPath}/meta" charEncoding="UTF-8"></c:import>
</head>
<body>
  <c:import url="${pageContext.request.contextPath}/top" charEncoding="UTF-8"></c:import>
  <c:import url="${pageContext.request.contextPath}/uploader" charEncoding="UTF-8"></c:import>
  <c:import url="${pageContext.request.contextPath}/footer" charEncoding="UTF-8"></c:import>
</body>
</html>