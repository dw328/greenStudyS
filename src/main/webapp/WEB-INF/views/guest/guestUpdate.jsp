<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guestUpdate.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <form method = "post">
	  	<h2 class="text-center">게 스 트 수 정</h2>
	  	<hr/>
	  		<table class = "table table-boardered">
		  			<tr>
			  			<th>성명</th>
			  			<td><input type = "text" name = "name" value ="${vo.name}" class = "form-control"></td>
		  			</tr>
		  			<tr>
			  			<th>내용</th>
			  			<td>
			  				<textarea name="content" rows="5" class="form-control">${vo.content}</textarea>
			  			</td>
		  			</tr>
		  			<tr>
			  			<th>이메일</th>
			  			<td class= "col d-flex">
			  				<input type = "text" name = "email1" class = "form-control"  value ="${vo.email.substring(0,vo.email.indexOf('@'))}" >
			  				@
			  				<select name = "email2" class = "form-select">
			  					<option value ="naver.com" ${vo.email.substring(vo.email.indexOf('@')+1) == 'naver.com' ? 'selected' : ''}>naver.com</option>
			  					<option value ="gmail.com" ${vo.email.substring(vo.email.indexOf('@')+1) == 'gmail.com' ? 'selected' : ''}>gmail.com</option>
			  					<option value ="daum.com"  ${vo.email.substring(vo.email.indexOf('@')+1) == 'daum.com' ? 'selected' : ''}>daum.net</option>
			  				</select>
			  			</td>
		  			</tr>
		  			<tr>
			  			<th>홈페이지</th>
			  			<td><input type = "text" name = "homePage" value = "${vo.homePage}" class = "form-control"></td>
		  			</tr>
		  			<tr>
		  				<td colspan= "2" class= "text-center">
			  				<input type = "submit" value = "수정하기" class = "btn btn-primary me-2">
			  				<input type = "reset" value = "다시입력" class = "btn btn-warning me-x" >
			  				<input type = "button" value = "돌아가기" onclick="location.href='guestMain'" class = "btn btn-danger">
			  			</td>
		  			</tr>
	  		</table>
	  		<input type = "hidden" name = "idx" value ="${vo.idx}" />
  	</form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>