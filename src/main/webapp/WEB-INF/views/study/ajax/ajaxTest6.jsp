<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ajaxTest6.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	'use strict';
  	
  	function midCheck() {				/* 문자열 처리 */
			let mid = $('#mid').val();		
	  	/* $('#demo').html(mid); */
	  	if(mid == "") {
	  		alert("아이디를 선택해주세요");
	  		return false;
	  	}
	  	
	  	$.ajax({
	  		url  : "ajaxTest6",
	  		type : "post",
	  		data : {mid : mid},
	  		success : (str) => {
	  			$('#demo').html(str);
	  		},
	  		error : () => alert("전송오류!")
	  	});
		}
  	
  	
  	function midCheck2() {			/* vo 처리 */
			let mid = $('#mid2').val();		
	  	/* $('#demo').html(mid); */
	  	if(mid == "") {
	  		alert("아이디를 선택해주세요");
	  		return false;
	  	}
	  	
	  	$.ajax({
	  		url  : "ajaxTest6_2",
	  		type : "post",
	  		data : {mid : mid},
	  		success : (vo) => {
	  			/* console.log("str", vo); */
	  			let str = '';
	  			str += "고유번호: " + vo.idx + "<br/>";
	  			str += "아이디: " + vo.mid + "<br/>";
	  			str += "성명: " + vo.name + "<br/>";
	  			str += "나이: " + vo.age + "<br/>";
	  			str += "성별: " + vo.gender + "<br/>";
	  			str += "주소: " + vo.address + "<br/>";
	  			$('#demo2').html(str);
	  		},
	  		error : () => alert("전송오류!")
	  	});
		}
  	function genderCheck() {			/* vos 처리 */
			let gender = $('#gender').val();		
	  	/* $('#demo').html(mid); */
	  	if(gender == "") {
	  		alert("성별을 선택해주세요");
	  		return false;
	  	}
	  	
	  	$.ajax({
	  		url  : "ajaxTest6_3",
	  		type : "post",
	  		data : {gender : gender},
	  		success : (vos) => {
	  			console.log("str", vos);
	  			let str = '';
	  			str += '<table class = "table table-hover">';
	  			str += '<tr class = "table-secondary">';
	  			str += '<th>번호</th><th>아이디</th><th>성명</th><th>나이</th><th>성별</th><th>주소</th>';
	  			str += '</tr>';
	  				console.log("vos: " ,vos.length)
	  			for (let i =0; i<vos.length; i++) {
		  			str += '<tr>';
			  		str += '<td>'+vos[i].idx+'</td>';
			  		str += '<td>'+vos[i].mid+'</td>';
			  		str += '<td>'+vos[i].name+'</td>';
			  		str += '<td>'+vos[i].age+'</td>';
			  		str += '<td>'+vos[i].gender+'</td>';
			  		str += '<td>'+vos[i].address+'</td>';
		  			str += '</tr>';
	  			}
	  			str += '</table>';
	  			$('#demo3').html(str);
	  		},
	  		error : () => alert("전송오류!")
	  	});
		}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <h2>ajaxTest6(DB자료 검색)</h2>
  <hr/>
  <form>
  	<h4>1. 아이디를 선택하세요(String 받기)</h4>
  	<table class = "table">
  		<tr>
	  		<td>
	  			<select name = "mid" id ="mid" onchange="midCheck()">
	  				<option value ="">아이디선택</option>
	  				<c:forEach var = "mid" items="${midVos}" varStatus ="st">
	  					<option>${mid}</option>
	  				</c:forEach>
	  			</select>
	  		</td>
  		</tr>
	  	<td><div id = "demo"></div></td>
  	</table>
  	<h4>2. 아이디를 선택하세요(vo 받기)</h4>
  	<table class = "table">
  		<tr>
	  		<td>
	  			<select name = "mid2" id ="mid2" onchange="midCheck2()">
	  				<option value ="">아이디선택</option>
	  				<c:forEach var = "mid" items="${midVos}" varStatus ="st">
	  					<option>${mid}</option>
	  				</c:forEach>
	  			</select>
	  		</td>
  		</tr>
	  	<td><div id = "demo2"></div></td>
  	</table>
  	<hr/>
  	<h4>3. 성별로 선택하세요(vos 받기)</h4>
  	<table class = "table">
  		<tr>
	  		<td>
	  			<select name = "gender" id ="gender" onchange="genderCheck()">
	  				<option value ="">성별선택</option>
	  				<c:forEach var = "vo" items="${genderVos}" varStatus ="st">
	  					<option>${vo.gender}</option>
	  				</c:forEach>
	  			</select>
	  		</td>
  		</tr>
	  	<td><div id = "demo3"></div></td>
  	</table>
  </form>
  <hr/>
  <a href = "${ctp}/study/ajaxForm" class ="btn btn-primary">뒤로가기</a>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>