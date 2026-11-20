<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>guestDeleteAndUpdate.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <style>
  	.card-box {
		  border-collapse: collapse;
		  border: 0.5px solid #999;\
		}
		.content-box {
		  border-collapse: collapse;
		}
		.content-box,
		.content-box td {
		 	border: 1px solid #CCCCCC !important;
		}
		.hr {
			border: 0;
	 		border-top: 2px solid #CCCCCC !important;
	 		opacity: 1;
		}
  </style>
  <script>
  	'use strict'
  	function userDeleteCheck(idx) {
			let ans = confirm("현재 유저를 삭제하시겠습니까?");
			if(! ans) return false;
			
			$.ajax({
				url  : "guestDelete",
				type : "post",
				data : {idx:idx},
				success : (res) => {
					if(res != 0) {
						alert("삭제 완료했습니다.");
						location.reload();
					}
					else alert("삭제 실패!");
				},
				error : () => alert("전송오류")
			});
		}
  	
  	function userUpdateCheck(idx) {
			let ans = confirm("현재 게시물를 수정하시겠습니까?");
			if(! ans) return false;
			
			location.href = "${ctp}/guest/guestUpdate?idx=" + idx;
		}
  	
  	
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
 <div class = "text-center">
		<h2>게 시 물 목 록</h2>
	</div>
	<div class = "text-end">
		<c:if test="${pag > 1}"> 
		  <a href="guestList?pag=1&pageSize=${pageSize}" title="첫페이지">◁◁</a>
      <a href="guestList?pag=${pag-1}&pageSize=${pageSize}" title="이전페이지">←</a>
    </c:if>
      ${pag}/${totPage}
		<c:if test="${pag < totPage}"> 
      <a href="guestList?pag=${pag+1}&pageSize=${pageSize}" title="다음페이지">→</a>
      <a href="guestList?pag=${totPage}&pageSize=${pageSize}" title="마지막페이지">▷▷</a>
    </c:if>
	</div>
	<hr/>
  <table width="100%">
	  <c:forEach var="vo" items="${vos}" varStatus="st">
	    <c:if test="${st.index % 2 == 0}"><tr></c:if>
	    <td width="50%" valign="top">
		   <table class="card-box" width="95%">
	        <tr>
	          <td>
	            <h2 class ="text-center">🔒 ${vo.name}</h2>
	            <br/>
	            <table class="content-box" width="100%" height="120">
	              <tr>
	                <td class ="text-start ps-2">
	                  <b>${vo.content}</b>
	                </td>
	              </tr>
	            </table>
	            <br/>
	            이메일: ${vo.email}<br/>
	            홈페이지:<a href="${vo.homePage}">${vo.homePage}</a><br/>
	            아이피: ${vo.hostIp}<br/>
	            작성날짜: ${vo.visitDate.substring(0,10)}
	            <hr class = "hr"/>
	            <div class = "text-center">
	            	<a href="javascript:userUpdateCheck(${vo.idx})" class="badge bg-info">수정</a>
	            	<a href="javascript:userDeleteCheck(${vo.idx})" class="badge bg-danger">삭제</a>
	            </div>
	            <br/>
	          </td>
	        </tr>
	      </table>
	      <br/>
	    </td>
	    <c:if test="${st.index % 2 == 1 || st.last}"><tr></tr></c:if>
	  </c:forEach>
	</table>
  <br/>
</div>
<p><br/></p>
</body>
</html>