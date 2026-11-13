<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>userMain.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
   <script>
  	'use strict';
  	
  	if ('${res}' != "") alert("자료를 등록되었습니다.");
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <h2 class = "text-cender"> 회 원 가 입 2</h2>
  <hr>
  <div>전체 회원 수 : ${userCnt}</div>
  <hr/>
  <div class = "row text-center">
  	<div class ="col"><input type = "button" value = "회원등록" onclick="location.href='userInput'"  class= "btn btn-success" /></div>
  	<div class ="col"><input type = "button" value = "회원개별조회" onclick="location.href='userSearch'"  class= "btn btn-info" /></div>
  	<div class ="col"><input type = "button" value = "회원전체조회" onclick="location.href='user2List'"  class= "btn btn-secondary" /></div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>