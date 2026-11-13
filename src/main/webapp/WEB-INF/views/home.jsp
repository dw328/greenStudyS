<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
<title>greenStudyS</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" sizes="180x180" href="${ctp}/images/favicon.ico">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/6/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lato">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body {font-family: "Lato", sans-serif}
.mySlides {display: none}
</style>
</head>
<body>

<!-- Navbar -->
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<!-- Page content -->
<div class="w3-content" style="max-width:2000px;margin-top:46px">
	<!-- Automatic Slideshow Images -->
	<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
	<!-- End Page Content -->
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>



</body>
</html>

