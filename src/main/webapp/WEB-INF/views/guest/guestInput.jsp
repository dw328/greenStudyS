<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guestInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <form method = "post">
	  	<h2 class="text-center">게 스 트 입 력</h2>
	  	<hr/>
	  		<table class = "table table-boardered">
	  			<tr>
		  			<th>성명</th>
		  			<td><input type = "text" name = "name" placeholder= "이름을 입력하시오" class = "form-control"></td>
	  			</tr>
	  			<tr>
		  			<th>내용</th>
		  			<td>
		  				<textarea name="content" rows="5" placeholder="내용을 입력하시오" class="form-control"></textarea>
		  			</td>
	  			</tr>
	  			<tr>
		  			<th>이메일</th>
		  			<td class= "col d-flex">
		  				<input type = "text" name = "email1" placeholder= "성명를 입력하시오" class = "form-control">@
		  				<select name = "email2" class = "form-select">
		  					<option value ="naver.com">naver.com</option>
		  					<option value ="gmail.com">gmail.com</option>
		  					<option value ="daum.com">daum.net</option>
		  				</select>
		  			</td>
	  			</tr>
	  			<tr>
		  			<th>홈페이지</th>
		  			<td><input type = "text" name = "homePage" placeholder= "홈페이지주소를 입력하시오" class = "form-control"></td>
	  			</tr>
	  			<tr>
	  				<td colspan= "2" class= "text-center">
		  				<input type = "submit" value = "등록하기" class = "btn btn-primary me-2">
		  				<input type = "reset" value = "다시입력" class = "btn btn-warning me-2" >
		  				<input type = "button" value = "돌아가기" onclick="location.href='guestMain'" class = "btn btn-danger">
		  			</td>
	  			</tr>
	  		</table>
  	</form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>