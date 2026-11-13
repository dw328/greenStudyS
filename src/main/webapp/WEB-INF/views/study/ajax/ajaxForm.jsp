<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ajaxForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script>
  	'use strict';
  	
  	function ajaxTest1(idx) {
			$.ajax({
				url  : "ajaxTest1",
				type : 'post',
				data : {idx : idx},
				success : (res) => {
					$("#demo1").html(res);
				},
				error : () => alert("전송오류")
			});
		}
  	
  	function ajaxTest2(str) {
			$.ajax({
				url  : "ajaxTest2",
				type : 'post',
				data : {str : str},
				success : (res) => {
					$("#demo2").html(res);
				},
				error : () => alert("전송오류")
			});
		}
  	
  	function fCheck1() {
			let mid = $('#mid').val();
			if(mid.trim() == "") {
				alert("아이디를 입력하시오.");
				return false;
			}
			$.ajax({
				url : 'midExactCheck',
				type: 'post',
				data : {mid : mid},
				success : (vo) => {
					let str = '';
	  			str += "고유번호: " + vo.idx + "<br/>";
	  			str += "아이디: " + vo.mid + "<br/>";
	  			str += "성명: " + vo.name + "<br/>";
	  			str += "나이: " + vo.age + "<br/>";
	  			str += "성별: " + vo.gender + "<br/>";
	  			str += "주소: " + vo.address + "<br/>";
					$("#demo3").html(str);
					if(vo.mid == null) {
						let str = "검색한 이름은 없는 아이디입니다.";
						$("#demo3").html(str);
					}
				},
				error : () => alert("전송오류")
			});
		}
  	
  	function fCheck2() {
			let mid = $('#mid').val();
			
			if(mid.trim() == "") {
				alert("아이디를 입력하시오.");
				return false;
			}
			$.ajax({
				url : 'midPartCheck',
				type: 'post',
				data : {mid : mid},
				success : (vos) => {
					let str = '';
	  			str += '<table class = "table table-hover mt-2">';
	  			str += '<tr class = "table-secondary">';
	  			str += '<th>번호</th><th>아이디</th><th>성명</th><th>나이</th><th>성별</th><th>주소</th>';
	  			str += '</tr>';
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
				error : () => alert("전송오류")
			});
			
		}
  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <h2>ajax 연습</h2>
  <hr/>
  <div class = "mb-3"> 기본1 :
  	<a href = "javascript:ajaxTest1(10)" class ="btn btn-success me-2">값전달1</a>
  	: <span id= "demo1"></span>
  </div>
  <div class = "mb-3"> 기본2 :
  	<a href = "javascript:ajaxTest2('안녕')" class ="btn btn-primary me-2">값전달2</a>
  	: <span id= "demo2"></span>
  </div>
  <div class = "mb-3">응용(배열) - 시(도)/구(시,군,동) 출력 : 
  	<a href = "ajaxTest3" class ="btn btn-secondary me-2">배열</a>
  	<a href = "ajaxTest4" class ="btn btn-primary me-2">ArrayList</a>
  	<a href = "ajaxTest5" class ="btn btn-info me-2">map</a>
  	<a href = "ajaxTest6" class ="btn btn-warning me-2">DB자료검색</a>
  	<hr/>
  	<h4>AJax를 이용한 검색: 아이디로 검색하여 완전일치자료와 부분일치자료를 출력하시오.</h4>
	  <div class="input-group">
	    <div class="input-group-text">아이디</div> 
	    <input type="text" name="mid" id="mid" class="form-control me-2" autofocus />
	    <input type="button" value="완전일치" onclick="fCheck1()" class="btn btn-info me-2"/>
	    <input type="button" value="부분일치" onclick="fCheck2()" class="btn btn-secondary"/>
	  </div>
  <div id="demo3"></div>
  </div>
</div>
<p><br/></p>
  <jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>