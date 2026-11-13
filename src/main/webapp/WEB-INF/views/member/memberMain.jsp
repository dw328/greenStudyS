<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var ="ctp" value ="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>memberMain.jsp</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <h2><font color = 'blue'>${vo.name}</font> 회원님 전용방입니다.${strLevel}</h2>
  <div>회원님의 마지막 방문: ${vo.lastDate}</div>
  <div>회원님의 방문 횟수: ${vo.visitCnt}</div>
  <div></div>
  <div>마지막 접속일자: ${fn:substring(vo.lastDate,0,10)}</div>
  <hr/>
  	<div><img src ="${ctp}/member/${vo.photo}" width="150px"/></div>
  <hr/>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>