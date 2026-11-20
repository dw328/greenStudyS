<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <title>adminLeft.jsp</title>
</head>
<body>
<p><br/></p>
<div class="container text-center">
  <h2><font size="12">관리자 대시보드</font></h2>
  <div class ="text-center"><img src="${ctp}/images/11.jpg" width="300px"/></div>
  <hr/>
  <h2>오늘 회원가입자의 수는 <font size ="12" color = "red">${fn:length(userCreateVos)}</font>명 입니다.</h2>
  <div class ="text-center">
  	<table class = "table table-hover">
  		<tr class ="table table-secondary">
  			<th>번호</th>
  			<th>성명</th>
  			<th>닉네임</th>
  			<th>아이디</th>
  			<th>회원 날짜</th>
  		</tr>
  		<c:if test="${empty userCreateVos}">
  			<tr>
  				<td colspan="5">오늘 등록된 회원이 없습니다.</td>
  			</tr>
  		</c:if>
  		<c:if test="${!empty userCreateVos}">
	  		<c:forEach var ="userCreateVo" items="${userCreateVos}" varStatus="st">
		  		<tr>
						<td>${st.count}</td>
						<td>${userCreateVo.name}</td>
						<td>${userCreateVo.nickName}</td>
						<td>${userCreateVo.mid}</td>
						<td>${fn:substring(userCreateVo.startDate,0,19)}</td>
		  		</tr>
	  		</c:forEach>
			</c:if>
	 	</table>
	 	<hr/>
	 	<br/>
	 	<h2>회원 탈퇴를 희망자의 수는 <font size ="12" color = "red">${fn:length(userDelVos)}</font>명입니다.</h2>
	 	<table class ="table table-hover">
	 		<tr class ="table table-secondary">
		 		<th>번호</th>
		 		<th>성명</th>
		 		<th>닉네임</th>
		 		<th>아이디</th>
		 		<th>회원탈퇴 희망여부</th>
		 		<th>회원 마지막 접속 일자</th>
	 		</tr>
	 		<c:if test="${empty userDelVos}">
  			<tr>
  				<td colspan="6">오늘 탈회 회망한 회원은 없습니다.</td>
  			</tr>
  		</c:if>
	 		<c:forEach var = "userDelVo" items ="${userDelVos}" varStatus="st">
		 		<tr>
			 		<td>${st.count}</td>
			 		<td>${userDelVo.name}</td>
			 		<td>${userDelVo.nickName}</td>
			 		<td>${userDelVo.mid}</td>
			 		<td>${userDelVo.userDel}</td>
			 		<td>${userDelVo.lastDate}</td>
			 	</tr>
		 	</c:forEach>
	 	</table>
  </div>
  <hr/>
  <br/>
  <div class ="container">
	<h2>게시물 신고 순위</h2>
	<table class ="table table-hover">
		<thead class ="table table-secondary">
			<tr>
				<th>번호</th>
				<th>순위</th>
				<th>조회순</th>
				<th>제목</th>
				<th>닉네임</th>
				<th>작성날짜</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<p><br/></p>
</body>
</html>