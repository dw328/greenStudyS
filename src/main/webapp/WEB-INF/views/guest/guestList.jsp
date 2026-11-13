<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>GuestList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <link rel ="stylesheet" type = "text/css" href = "${ctp}/css/linkOrange.css" />
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
  </style>
  <script>
  	'use strict';
  	
  	if ('${res}' != "") alert("자료가 등록되었습니다.");
  	
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
	<div class = "text-center">
		<h2>게 시 물 목 록</h2>
	</div>
	<div class = "text-end">
		<c:if test="${pageVO.pag > 1}"> 
		  <a href="guestList?pag=1&pageSize=${pageVO.pageSize}" title="첫페이지">◁◁</a>
      <a href="guestList?pag=${pageVO.pag-1}&pageSize=${pageVO.pageSize}" title="이전페이지">←</a>
    </c:if>
      ${pageVO.pag}/${pageVO.totPage}
		<c:if test="${pageVO.pag < pageVO.totPage}"> 
      <a href="guestList?pag=${pageVO.pag+1}&pageSize=${pageVO.pageSize}" title="다음페이지">→</a>
      <a href="guestList?pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}" title="마지막페이지">▷▷</a>
    </c:if>
	</div>
	<hr/>
  <table width="100%">
		<c:set var="curScrStartNo" value = "${pageVO.curScrStartNo}"/>			<!-- 아 처름의 시작이 필요하지.. -->
		  <c:forEach var="vo" items="${vos}" varStatus="st">							<!-- 위의 set을 가지 않고 안에서 -1하면서 번호가 적히네 -->
		    <c:if test="${st.index % 2 == 0}"><tr></c:if>
		    <td width="50%" valign="top">
		      <table class="card-box" width="95%">
			    <div> 번호 : ${curScrStartNo} </div>
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
		          </td>
		        </tr>
		      </table>
		      <br/>
		    </td>
		    <c:if test="${st.index % 2 == 1 || st.last}"></c:if>
		    <c:set var="curScrStartNo" value = "${curScrStartNo -1}"/>			<!-- 아 위의 값을 가지고 와서 -1한거구나.. -->
	  </c:forEach>
	</table>
	<br/>
	<%-- <!-- 페이징처리(블록페이지 시작) -->
  <div class="text-center">
	  <c:if test="${pag > 1}">[<a href="guestList?pag=1&pageSize=${pageSize}">첫페이지</a>]</c:if>
	  <c:if test="${curBlock > 0}">[<a href="guestList?pag=${(curBlock-1)*blockSize + 1}&pageSize=${pageSize}">이전블록</a>]</c:if>
	  <c:forEach var="i" begin="${(curBlock*blockSize) + 1}" end="${(curBlock*blockSize) + blockSize}" varStatus="st">
	   	<c:if test="${i <= totPage && pag != i}">[<a href="guestList?pag=${i}&pageSize=${pageSize}">${i}</a>]</c:if>
	    <c:if test="${i <= totPage && pag == i}">[<a href="guestList?pag=${i}&pageSize=${pageSize}" class="text-danger"><b>${i}</b></a>]</c:if>
	  </c:forEach>
	  <c:if test="${curBlock < lastBlock}">[<a href="guestList?pag=${(curBlock+1)*blockSize + 1}&pageSize=${pageSize}">다음블록</a>]</c:if>
	  <c:if test="${pag < totPage}">[<a href="guestList?pag=${totPage}&pageSize=${pageSize}">마지막페이지</a>]</c:if>
  </div>
  <!-- 페이징처리(블록페이지 끝) --> --%>
	<!-- 페이징처리(블록페이지 시작) -->
  <div class="text-center">
    <ul class="pagination justify-content-center">
      <c:if test="${pageVO.pag > 1}"><li class="page-item"><a href="guestList?pag=1&pageSize=${pageVO.pageSize}" class="page-link text-secondary">첫페이지</a></li></c:if>
      <c:if test="${pageVO.curBlock > 0}"><li class="page-item"><a href="guestList?pag=${(pageVO.curBlock-1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">이전블록</a></li></c:if>
      <c:forEach var="i" begin="${(pageVO.curBlock*pageVO.blockSize) + 1}" end="${(pageVO.curBlock*pageVO.blockSize) + pageVO.blockSize}" varStatus="st">
        <c:if test="${i <= pageVO.totPage && pageVO.pag != i}"><li class="page-item"><a href="guestList?pag=${i}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">${i}</a></li></c:if>
        <c:if test="${i <= pageVO.totPage && pageVO.pag == i}"><li class="page-item"><a href="guestList?pag=${i}&pageSize=${pageVO.pageSize}" class="page-link active bg-secondary border-secondary"><b>${i}</b></a></li></c:if>
      </c:forEach>
	    <c:if test="${pageVO.curBlock < pageVO.lastBlock}"><li class="page-item"><a href="guestList?pag=${(pageVO.curBlock+1)*pageVO.blockSize + 1}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">다음블록</a></li></c:if>
      <c:if test="${pageVO.pag < pageVO.totPage}"><li class="page-item"><a href="guestList?pag=${pageVO.totPage}&pageSize=${pageVO.pageSize}" class="page-link text-secondary">마지막페이지</a></li></c:if>
    </ul>
  </div>
  <!-- 페이징처리(블록페이지 끝) -->
  <br/>
  <hr/>
  <div class ="text-center">
  	<input type = "button" value = "돌아가기" onclick="location.href='guestMain'" class = "btn btn-secondary">
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>