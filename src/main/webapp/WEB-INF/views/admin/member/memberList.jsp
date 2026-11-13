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
  <title>memberList.jsp</title>
  <script>
  	'use strict';
  	
  	function levelChange(e) {
	  	let ans = confirm("회원등급을 변경하시겠습니까?");
	  	if(!ans) {
	  		location.reload();
	  		return false;
	  	}
	  	
	  	let query = {
	  			idx : e.dataset.idx,
	  			level : e.value
	  	}
	  	
			$.ajax({
				url  : "levelChange",
				type : "post",
				data : query,
				success : (res) => {
					if(res != 0) {
						alert("회원 등급 변경 완료.");
						location.reload();
					}
					else alert("회원 등급 변경 실패!");
				},
				error : () => alert("전송 오류")
			});	  	
			
		}
  	
		// 전체선택
    function allCheck() {
      for(let i=0; i<myform.idxFlag.length; i++) {
        myform.idxFlag[i].checked = true;
      }
    }
 
    // 전체취소
    function allReset() {
      for(let i=0; i<myform.idxFlag.length; i++) {
        myform.idxFlag[i].checked = false;
      }
    }

    // 선택반전
    function reverseCheck() {
      for(let i=0; i<myform.idxFlag.length; i++) {
        myform.idxFlag[i].checked = !myform.idxFlag[i].checked;
      }
    }
    // 선택항목 레벨 변경
    function levelSelectCheck() {
    	let levelItem = document.getElementById("levelItem").value;
    	let levelItemText = document.getElementById("levelItem").text;
    	let idxSelectArray = "";
    	
    	if(myform.idxFlag.length == 0) {
    		alert("등급을 변경할 항목을 1개 이상 선택하세요.");
    		return false;
    	}
    	
    	for(let i=0; i<myform.idxFlag.length; i++) {
    		if(myform.idxFlag[i].checked) idxSelectArray += myform.idxFlag[i].value + "/";
    	}
    	idxSelectArray = idxSelectArray.substring(0, idxSelectArray.lastIndexOf("/"));
    	
    	let query = {
    			idxSelectArray : idxSelectArray,
    			levelItem : levelItem
    	}
    	
    	$.ajax({
    		url  : "memberLevelSelectCheck",
    		type : "post",
    		data :query,
    		success : (res) => {
    			if(res != 0) {
    				alert("성택한 항목들이 "+levelItemText+"(으)로 변경되었습니다.");
    			}
    			else alert("등급 변경 실패");
    			location.reload();
    		},
    		error : () => alert("전송 오류")
    	});
    }
   
    function searchLevel(e) {
			let level = e.value;
			
			$.ajax({
				url  : "searchLevel",
				type : "post",
				data : {level : level},
				success : (vos) => {
					
					let str = '<tr class = "table-secondary">';
					str += '<th>번호</th><th>닉네임</th><th>아이디</th><th>성명</th><th>이메일</th><th>생일</th><th>회원등급</th>';
					str += '</tr>';
					for(let i =0; i<vos.length; i++) {
						str += '<tr>';
						str += '<td>'+vos[i].idx+'</td>';
						str += '<td>'+vos[i].nickName+'</td>';
						str += '<td>'+vos[i].mid+'</td>';
						str += '<td>'+vos[i].name+'</td>';
						str += '<td>'+vos[i].email+'</td>';
						str += '<td>'+vos[i].birthday+'</td>';
						str += '<td>'+vos[i].level+'</td>';
						str += '</tr>';
					}
					$("#searchRes").html(str);
					$("#searchRes").show();
				},
				error : () => alert("전송 오류")
			});
		}
    
    function pageChange() {
			let pageSize = $("#pageSize").val();
			location.href = "memberList?pageSize="+pageSize;
		}
    
    function memberDeleteOk(idx, photo) {
			let ans = confirm("선택하신 회원을 영구 삭제 하시겠습니까?");
			if(!ans) return false;
			let	query = {
					idx   : idx,
					photo : photo
			}
			
			$.ajax({
    		url  : "${ctp}/admin/memberDeleteOk",
    		type : "post",
    		data :query,
    		success : (res) => {
    			if(res != 0) {
    				alert("회원을 영구 삭제하셨습니다.");
    			}
    			else alert("회원 삭제 실패");
    			location.reload();
    		},
    		error : () => alert("전송 오류")
    	});
		}
    
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <h2 class ="text-center mb-3">회 원 리 스 트</h2>
  <div class= "row mb-2">
  	<div class = "col input-group">
  		<input type ="button" value ="전체선택" onclick="allCheck()" class ="btn btn-success btn-sm"/>
  		<input type ="button" value ="전체해제" onclick="allReset()" class ="btn btn-warning btn-sm"/>
  		<input type ="button" value ="선택반전" onclick="reverseCheck()" class ="btn btn-info btn-sm"/>
  		<select name = "levelItem" id = "levelItem" onchange="levelItemCheck()" class ="form-select w-auto">
				<option value ="1" >우수회원</option>
				<option value ="2" selected >정회원</option>
				<option value ="3" >준회원</option>
				<option value ="99" >탈퇴회원</option>
			</select>
  		<input type ="button" value ="선택항목등급변경" onclick="levelSelectCheck()" class ="btn btn-primary btn-sm"/>
  	</div>
  </div>
  <form name = "myform">
	  <table class ="table table-hover" id = "memberLsit">
	  		<tr class ="table-secondary">
	  			<th>번호</th>
	  			<th>닉네임</th>
	  			<th>아이디</th>
	  			<th>성명</th>
	  			<th>이메일</th>
	  			<th>내용</th>
	  			<th>최종접속일</th>
	  			<th>비고</th>
	  		</tr>
	  		<c:forEach var= "vo" items="${vos}" varStatus ="st">
		  		<tr>
		  			<td>
							<input type ="checkbox" name = "idxFlag" id = "idxFlag${vo.idx}" value = "${vo.idx}" />	  				
		  				${vo.idx}
		  			</td>
		  			<td>${vo.nickName}</td>
		  			<td><a href ="memberContent?mid=${vo.mid}">${vo.mid}</a></td>
		  			<td>${vo.name}</td>
		  			<td>${vo.email}</td>
		  			<td>${vo.content}</td>
		  			<td>
		  				<c:if test="${vo.userDel == 'NO'}">${fn:substring(vo.lastDate,0,10)}</c:if>
		  				<c:if test="${vo.userDel == 'OK' && vo.deleteDiff >= 30}">
		  					<a href="javascript:memberDeleteOk('${vo.idx}','${vo.photo}')"><font color="red">30일 경과</font></a>
		  				</c:if>	
		  				<c:if test="${vo.userDel == 'OK' && vo.deleteDiff < 30}">
								${vo.deleteDiff}일경과
		  				</c:if>	
		  			</td>
		  			<td>
		  				<select name = "level" id = "level" onchange="levelChange(this)" data-idx="${vo.idx}">
		  					<option value ="0" ${vo.level == 0? 'selected' : ''}>관리자</option>
		  					<option value ="1" ${vo.level == 1? 'selected' : ''}>우수회원</option>
		  					<option value ="2" ${vo.level == 2? 'selected' : ''}>정회원</option>
		  					<option value ="3" ${vo.level == 3? 'selected' : ''}>준회원</option>
		  					<option value ="99" ${vo.level == 99? 'selected' : ''}>탈퇴회원</option>
		  				</select>
		  			</td>
		  		</tr>
	  		</c:forEach>
	  </table>
	</form>
	<!-- 페이징처리(블록페이지 시작) -->
  <div class="text-center">
    <ul class="pagination justify-content-center">
      <c:if test="${pageVO.pag > 1}"><li class="page-item"><a href="memberList?pag=1&pageSize=${pageVO.pageSize}" class="page-link text-secondary">첫페이지</a></li></c:if>
      <c:if test="${pageVO.curBlock > 0}"><li class="page-item"><a href="memberList?pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">이전블록</a></li></c:if>
      <c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize) + 1}" end="${(pageVO.curBlock*pageVO.blockSize) + pageVO.blockSize}" varStatus="st">
        <c:if test="${i <= pageVO.totPage && pageVO.pag != i}"><li class="page-item"><a href="memberList?pag=${i}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">${i}</a></li></c:if>
        <c:if test="${i <= pageVO.totPage && pageVO.pag == i}"><li class="page-item"><a href="memberList?pag=${i}&pageSize=${pageVO.pageSize}" class="page-link active bg-secondary border-secondary"><b>${i}</b></a></li></c:if>
      </c:forEach>
	    <c:if test="${pageVO.curBlock < pageVO.lastBlock}"><li class="page-item"><a href="memberList?pag=${(pageVO.curBlock+1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">다음블록</a></li></c:if>
      <c:if test="${pageVO.pag < pageVO.totPage}"><li class="page-item"><a href="memberList?pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">마지막페이지</a></li></c:if>
    </ul>
    <div class ="d-flex justify-content-end">
    	<select name ="pageSize" id ="pageSize" onchange="pageChange()">
    		<option value ="5"  ${pageVO.pageSize == 5 ? 'selected': '' }>5</option>
    		<option value ="10" ${pageVO.pageSize == 10 ? 'selected': '' }>10</option>
    		<option value ="15" ${pageVO.pageSize == 15 ? 'selected': '' }>15</option>
    		<option value ="20" ${pageVO.pageSize == 20 ? 'selected': '' }>20</option>
    	</select>
    </div>
    <div class = "text-end mb-2">
  		<select name ="search" id ="search" class ="form-select w-auto" onchange="searchLevel(this)">
  			<option value ="1" >우수회원</option>
				<option value ="2" selected >정회원</option>
				<option value ="3" >준회원</option>
				<option value ="99" >탈퇴회원</option>
			</select>
    </div>
  </div>
  <!-- 페이징처리(블록페이지 끝) -->
  <table class ="table table-hover" id ="searchRes"  style ="display:none">
	</table>
</div>
<p><br/></p>
</body>
</html>