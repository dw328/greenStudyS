<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>mailForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
  	#myModal2 {
		  position: fixed;
		  top: 0;
		  left: 0;
		  width: 100%;
		  height: 100%;
		  background: rgba(0, 0, 0, 0.5);
		  display: none;
		  justify-content: center;
		  align-items: center;
		  z-index: 2000;
		}
		#myModal2 .modal-content {
		  position: relative;
		  width: 500px;
		  max-width: 90%;
		}
  </style>
  <script>
  	'use strict';
  	
  	function mailCheck() {
			$("#spinner").show();
			
			//기본 체크 모두 할것
			myform.submit();
		}
  	
  	// 선생님이 말씀하신 방법 메일에 오기 전에 이미 vos을 담고 주소록을 누르면 mocdal을 이용해서 화면에 짠하고 나오는 방법
  	function jusoroCheck() {
			$.ajax({
				url  : "emailList",
				type : "post",
				dataType : "json",
				success : (vos) => {
		  		$("#myModal2").show();
					$("#myModal2").css("display", "flex");
					
					let str = '<tr class ="table table-secondary">';					
					str += '<th>아이디</th><th>닉네임</th><th>성명</th><th>이메일</th><th>선택</th>';					
					str += '</tr>';					
					for(let i =0; i < vos.length; i++){
						str += '<tr>';	
						str += '<td>'+vos[i].mid+'</td>';	
						str += '<td>'+vos[i].nickName+'</td>';	
						str += '<td>'+vos[i].name+'</td>';	
						str += '<td>'+vos[i].email+'</td>';	
						if(i == 0) str += '<td><input type="radio" name="emailCheckRadio" value="'+vos[i].email+'" checked /></td>';
						else str += '<td><input type="radio" name="emailCheckRadio" value="'+vos[i].email+'"  /></td>';
						str += '</tr>';	
					}
					$("#jusoroTable").html(str);
				},
				error : () => alert("전송 오류")
			});
  		
		}
  	
  	function closeCheck() {
  		document.getElementById("myModal2").style.display = "none";
  		
		}
  	
  	function emailCheck() {
			let email = $("input[name='emailCheckRadio']:checked").val();
			$("#toMail").val(email);
			closeCheck();
			
		}
  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
  <h2 class= "text-center">메 일 보 내 기</h2>
  <div class ="text-end">(받는 사람의 메일주소를 정확히 이력하셔야 합니다.(진짜 메일 보내짐))</div>
 <!--  <form name ="myform" method = "post"> -->
  <form name ="myform" method = "post" action="mailFormOk">
	  <table class= "table table-boardered">
	  	<tr>
	  		<th>받는사람</th>
	  		<td>
	  			<div class = "input-group">
	  				<input type = "text" name = "toMail" id = "toMail" placeholder = "받는사람 메일주소를 입려하세요." required class ="form-control" />
	  				<input type = "button" value = "주소록" onclick="jusoroCheck()" class ="btn btn-info" /><!-- modal -->
	  			</div>
	  		</td>
	  	</tr>
	  	<tr>
	  		<th>메일제목</th>
	  		<td><input type = "text" name = "title" id = "title" placeholder="메일 제목 입력하세요." required class ="form-control" /></td>
	  	</tr>
	  	<tr>
	  		<th>메일 내용</th>
	  		<td><textarea rows ="7" name = "content" id ="content" placeholder="메일 내용을 입력하세요" required class= "form-control"></textarea></td>
	  	</tr>
	  	<tr>
	  		<td colspan = "2" class = "text-center">
	  			<input type = "button" value = "메일보내기" data-bs-toggle="modal" data-bs-target="#myModal1"  onclick="mailCheck()" class = "btn btn-success me-2" />
	  			<input type = "reset" value = "다시쓰기" class = "btn btn-warning" />
	  		</td>
	  	</tr>
	  </table>
		<!-- 표준 모달(응용)) -->
		<div class ="text-center">
				<div class="modal fade" id="myModal1">
				  <div class="modal-dialog modal-dialog-centered">
				    <div class="modal-content">
				      <!-- Modal Header -->
				      <div class="modal-header d-flex justify-content-center align-items-center">
				        <h4 class="modal-title">Modal spinner</h4>
				        <!-- <button type="button" class="btn-close" data-bs-dismiss="modal"></button> -->
				      </div>
				      <!-- Modal body -->
				      <div class="modal-body d-flex justify-content-center align-items-center">
				         <div style="font-size:100px"><div class="spinner-border text-warning" style="width:100px; height:100px;" /></div>
				      </div>
				     </div>
				  </div>
				</div>
	  </div>
	  </div>
	  <!-- The Modal -->
		<div id="myModal2" class="modal">
		
		  <!-- Modal content -->
		  <div class="modal-content">
		    <div class="modal-header d-flex justify-content-between align-items-center">
					<h2>이메일 보낼 사람</h2>
				  <input type="button" class="btn btn-danger" id="close" value="×" onclick="closeCheck()">
				</div>
		    <div class="modal-body">
					<table class = "table table-hover" id = "jusoroTable">
					</table>
		    </div>
		    <div class="modal-footer">
		      <input type = "button" value ="선택하기" onclick="emailCheck()" class ="btn btn-success text-center"/>
		    </div>
		  </div>
		
		</div>

  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>