<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guestMain.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	'use strict'
  	
  	if('${res}' != "") alert("등록이 완료됐습니다.");
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <h2 class = "text-center">게 스 트 메 인</h2>
  <hr/>
  <br/>
  <div class = "row text-center">
  	<div class ="col"><input type = "button" value = "게시판등록" onclick="location.href='guestInput'"  class= "btn btn-success" /></div>
  	<div class ="col"><input type = "button" value = "게시판전체조회" onclick="location.href='guestList'"  class= "btn btn-warning" /></div>
  	<div class ="col"><input type = "button" value = "게시물 수정,삭제" onclick="location.href='guestDelete'"  class= "btn btn-secondary" /></div>
  </div>
  <br/>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>