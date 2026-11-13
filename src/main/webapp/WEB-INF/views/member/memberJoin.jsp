<%@page import="java.time.LocalDate"%>
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
  <title>memberJoin.jsp</title>
  <script src="//t1.kakaocdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
  <script>
  'use strict';
  
  let idCheckSw = 0;
  let nickCheckSw = 0;
  let emailCheckSw = 0;
  
  function fCheck() {
  	// 유효성 검사(아이디, 닉네임, 성명, 이메일, 홈페이지, 전화번호, 비밀번호 등등.....)
  	
  	// 정규식을 통한 유효성 검사처리...
  	let regMid = /^[a-zA-Z0-9_]{4,20}$/; 			// 아이디는 4~20의 영문 대/소문자와 숫자와 밑줄 가능
  	//let regPwd = /{2,15}$/;										// 비밀번호는 2~15
    let regNickName = /^[가-힣0-9_]{2,20}$/;		// 닉네임은 2~20자의 한글, 숫자, 밑줄만 가능
    let regName = /^[가-힣a-zA-Z]{2,20}$/;			// 이름은 2~20자의 한글/영문 가능
    const regEmail = /^[a-zA-Z0-9._%+\-]+$/; 	// 이메일은 email형식에 맞도록 처리...
    const regUrl = /^https?:\/\/([a-z0-9-]+\.)+[a-z]{2,}(\/[^\s]*)?$/i; // 홈페이지입력시는 url형식에 맞도록 처리...
    const regTel = /^(\d{4})?$/;			 		 	// 전화번호입력시는 전화번호형식에 맞도록처리
  	
  	
  	let mid = document.myform.mid.value.trim();
  	let pwd = document.myform.pwd.value.trim();
  	let nickName = document.myform.nickName.value;
  	let name = document.myform.name.value;
  	
  	let email1 = document.myform.email1.value.trim();
  	let email2 = document.myform.email2.value;
  	let email = email1 + "@" + email2;
  	
  	let tel1 = document.myform.tel1.value;
  	let tel2 = document.myform.tel2.value.trim();
  	let tel3 = document.myform.tel3.value.trim();
  	//let tel = tel1 + "-" + tel2 + "-" + tel3;
  	
  	
  	
  	let postcode = document.myform.sample6_postcode.value;
  	let roadAddress = document.myform.sample6_address.value;
  	let detailAddress = document.myform.sample6_detailAddress.value;
  	let extraAddress = document.myform.sample6_extraAddress.value;
  	let address = postcode + " /" + roadAddress + " /" + detailAddress + " /" + extraAddress + " ";
  	
  	/* 아직은 넣지 말기 (오류 원인) */
  	/* let homePage = document.myform.homePage.value.trim(); */
  	
  	if(!regMid.test(mid)) {
  		alert("아이디는 4~40자리의 영문 '소/대문자'와 숫자와 언더바(_)만 가능합니다.");
  		document.myform.mid.focus();
  		return false;
  	}
  	else if(pwd.length < 4 || pwd.length > 20) {
      alert("비밀번호는 4~20 자리로 작성해주세요.");
      document.myform.pwd.focus();
      return false;
    }
    else if(!regNickName.test(nickName)) {
      alert("닉네임은 한글만 사용가능합니다.");
      document.myform.nickName.focus();
      return false;
    }
    else if(!regName.test(name)) {
      alert("성명은 한글과 영문대소문자만 사용가능합니다.");
      document.myform.name.focus();
      return false;
    }
    else if(!regEmail.test(email1)) {
      alert("이메일을 확인하세요.");
      document.getElementById("email1").focus();
      return false;
    }
  	
  	// 필수입력 아닌 사항들의 체크
  	/* 
    if(homePage != "" && !regUrl.test(homePage)) {
      alert("홈페이지를 확인하세요.");
      document.getElementById("homePage").focus();
      return false;
    }
  	 */
		// 이거 다시 보기
  	if(tel2 != "" && !regTel.test(tel2)) {
  		alert("전화번호(국번호)를 확인하세요.");
      document.getElementById("tel2").focus();
      return false;
		}
  	if(tel2 == "") tel2 = " "; 
  	 
  	if(tel3 != "" && !regTel.test(tel3)) {
  		alert("전화번호를 확인하세요.");
      document.getElementById("tel3").focus();
      return false;
		}
  	if(tel3 == "") tel3 = " "; 
  	
		let tel = tel1 + "-" + tel2 + "-" + tel3;
  	
  	const query = 'input[name="hobby"]:checked';
 	  const selectedEls =  document.querySelectorAll(query);
	  
 	  // 선택된 목록에서 value 찾기
 	  let hobbys = '';
 	  selectedEls.forEach((el) => {
 	  	hobbys += el.value + '/';
 	  });
		
 	 	hobbys = hobbys.substring(0,hobbys.length-1);
 	 	 
  	 
		// 전송전에 파일에 관련된 사항들을 체크해준다.
		let fName = document.getElementById("file").value;
		if(fName.trim() != "") {
			let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
			let maxSize = 1024 * 1024 * 10;
			let fileSize = document.getElementById("file").files[0].size;
				
			if(ext != 'jpg' && ext != 'gif' && ext != 'png') {
				alert("그림파일만 업로드 가능합니다.");
				return false;
			}
			else if(fileSize > maxSize) {
				alert("업로드할 파일의 최대용량은 10MByte입니다.");
				return false;
			}
		}
		//else return false;
		
	 	// 아이디/닉네임 중복버튼 눌렀는지 체크
  	if(idCheckSw == 0) {
  		alert("아이디 중복체크버튼을 눌러주세요");
  		document.getElementById("midBtn").focus();
  	}
  	else if(nickCheckSw == 0) {
  		alert("닉네임 중복체크버튼을 눌러주세요");
  		document.getElementById("nickNameBtn").focus();
  	}
  	else if(emailCheckSw == 0) {
  		alert("이메일 인증을 해주세요!");
  		document.getElementById("email1").focus();
  	}
  	else { 
  		myform.email.value = email;
  		myform.tel.value = tel;
  		myform.address.value = address;
  		myform.hobbys.value = hobbys;
  		
  		myform.submit();
  	}
	 } 
  
  // 아이디 중복체크
  function idCheck() {
  	let mid = myform.mid.value;
  	
  	if(mid.trim() == "") {
  		alert("아이디를 입력하세요!");
  		myform.mid.focus();
  		return false;
  	}
  	
  	$.ajax({
  		url  : "memberIdCheck",
  		type : "get",
  		data : {mid : mid},
  		success: (res) => {
  			if(res != "0") {
  				alert("아이디가 중복되었습니다.\n다른아이디로 검색하세요.");
  				document.myform.mid.focus();
  			}
  			else {
  				alert("사용 가능한 아이디 입니다.");
  				idCheckSw = 1;
  				nextStepCheck();
  				$("#mid").prop("readonly", true);
  				document.myform.pwd.focus();
  			}
  		},
  		error :  () => alert("전송오류!")
  	});
  }
  
  // 닉네임 중복체크
  function nickCheck() {
  	let nickName = myform.nickName.value;
  	
  	if(nickName.trim() == "") {
  		alert("닉네임을 입력하세요!");
  		myform.nickName.focus();
  	}
  	else {
  		$.ajax({
  			url  : "memberNickCheck",
  			type : "post",
  			data : {nickName : nickName},
  			success: (res) => {
  				if(res != '0') {
  					alert("이미 사용중인 닉네임 입니다. 다시 입력하세요.");
  					myform.nickName.focus();
  				}
  				else {
  					alert("사용 가능한 닉네임 입니다.");
  					nickCheckSw = 1;
  					$("#nickName").prop("readonly", true);
  					nextStepCheck();
  				}
  			},
  			error : () =>	alert("전송 오류!")
  		});
  	}
  }
  
  // 아이디/닉네임 확인처리시, 아이디/닉네임 체크확인
  $(function(){
  	$("#mid").on("blur", () => idCheckSw = 0);
  	$("#nickName").on("blur", () => nickCheckSw = 0);
  });
  
  // 선택된 사진 미리보기
  function imgCheck(e) {
  	if(e.files && e.files[0]) {
  		let reader = new FileReader();
  		reader.onload = function(e) {
  			document.getElementById("photoDemo").src = e.target.result;
  		}
  		reader.readAsDataURL(e.files[0]);
  	}
  }
  
 	function emailCheck() {
		// 아이디 ~ 이메일 전까지 체크하기(유효성)
		let email1 =  document.getElementById("email1").value;
		let email2 = document.getElementById("email2").value;
		let email = email1 + '@' + email2;
		
		console.log("email:", email);
		
		$.ajax({
			url     : "emailCheckOk",
			type    : "post",
			data    : {email :email},
			success : (res) => {
				let modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("myModal1"));
				if(res != 0) {
					alert("전송완료했습니다.");
					modal.hide();
					$("#certification").show();
					
				}
			},
			error   : () => {
				bootstrap.Modal.getOrCreateInstance(document.getElementById("myModal1")).hide();
				alert("전송오류");
			}
		});
		// AJAX(호출전에 인증번호 버튼 없어짐)
			// 이메일 인증 번호 보내기(컨드롤러 UUID 인증번호 꺼내기) -> 인증번호 : UUID
			// 인증번호 text로 인증번호 확인 인증번호 폼 show()
			// 세션을 저장하고 보낸 메일과 저장된 세션을 매칭해서 맞으면 세션을 끊기
	}
  
 	
 	function certificationCheck() {
		let certificationId = $('#certificationId').val();
		
		$.ajax({
			url 		: "certificationCheck",
			type 		: "post",
			data 		: {certificationId : certificationId},
			success : (res) => {
				if(res != 0) {
					alert("인증완료!!");
					$("#certificationId").prop("readonly", true);
					emailCheckSw = 1;
					nextStepCheck();
				} 
				else alert("인증 실패"); 
			},
			error : () => alert("전송오류")
		});
	}
 	
 	function nextStepCheck() {
 		if(idCheckSw == 1 && nickCheckSw == 1 && emailCheckSw == 1) {
 			$("#addContent").show();
 		}
 	}
 	
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide3.jsp" />
<p><br/></p>
<div class="container">
 <form name="myform" method="post"class="was-validated" enctype="multipart/form-data">
 <!--  <form name="myform" method="post" action="memberJoinOk.mem" class="was-validated"> -->
    <h2>회 원 가 입</h2>
    <br/>
    <div class="input-group mb-3">
      <div class="input-group-text"><label for="mid">아이디</label></div>
      <input type="text" class="form-control" name="mid" id="mid" placeholder="아이디를 입력하세요." required autofocus/>
      <input type="button" value="아이디 중복체크" id="midBtn" class="btn btn-secondary btn-sm" onclick="idCheck()"/>
    </div>
    <div class="input-group mb-3">
      <div class="input-group-text"><label for="pwd">비밀번호 :</label></div>
      <input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력하세요." name="pwd" required />
    </div>
    <div class="input-group mb-3">
      <div class="input-group-text"><label for="nickName">닉네임</label></div>
      <input type="text" class="form-control" id="nickName" placeholder="별명을 입력하세요." name="nickName" required />
      <input type="button" id="nickNameBtn" value="닉네임 중복체크" class="btn btn-secondary btn-sm" onclick="nickCheck()"/>
    </div>
    <div class="input-group mb-3">
      <div class="input-group-text"><label for="name">성명 :</label></div>
      <input type="text" class="form-control" id="name" placeholder="성명을 입력하세요." name="name" required />
    </div>
    <div class="input-group mb-3">
      <div class="input-group-text"><label for="email1">Email address:</label></div>
      <input type="text" class="form-control" placeholder="Email을 입력하세요." id="email1" name="email1" required />
      <div class="input-group-text">@</div>
      <select name="email2" id = "email2" class="form-select">
        <option value="naver.com"   ${email2 == 'naver.com'? 'selected' : ''}>naver.com</option>
        <option value="hanmail.net" ${email2 == 'hanmail.net'? 'selected' : ''}>hanmail.net</option>
        <option value="hotmail.com" ${email2 == 'hotmail.com'? 'selected' : ''}>hotmail.com</option>
        <option value="gmail.com" 	${email2 == 'gmail.com'? 'selected' : ''}>gmail.com</option>
        <option value="nate.com"    ${email2 == 'nate.com'? 'selected' : ''}>nate.com</option>
        <option value="yahoo.com"  ${email2 == 'yahoo.com'? 'selected' : ''}>yahoo.com</option>
      </select>
      <input type = "button" value = "인증받기" onclick="emailCheck()" data-bs-toggle="modal" data-bs-target="#myModal1" id ="emailId" class = "btn btn-success btn-sm" />
    </div>
    <div id = "certification" class = "input-group mb-3" style="display:none">
    	<div class = "input-group-text">인증번호</div>
    	<input type = "text" name = "certificationId" id ="certificationId" class = "form-control" />
      <input type = "button" value = "인증" onclick="certificationCheck()" class = "btn btn-success btn-sm" />
    </div>
    <!-- <div id = "addContent"> -->
    <div id = "addContent" style = "display:none">
	    <div class="input-group mb-3">
	      <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
	      <div class="form-check"><input type="radio" name="gender" value="남자" class="m
	      e-1" checked />남자</div>
	      <div class="form-check"><input type="radio" name="gender" value="여자" class="ms-3 ms-4 me-1" />여자</div>
	    </div>
	    <div class="input-group mb-3">
	      <div class="input-group-text"><label for="birthday">생일</label></div>
	      <input type="date" name="birthday" value ="<%=LocalDate.now()%>" class="form-control"/>
	    </div>
	    <div class="input-group">
	      <div class="input-group mb-3">
	        <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;
	        <select name="tel1" id = "tel1" class="custom-select">
	          <option value="010" selected>010</option>
	          <option value="02">서울</option>
	          <option value="031">경기</option>
	          <option value="032">인천</option>
	          <option value="041">충남</option>
	          <option value="042">대전</option>
	          <option value="043">충북</option>
	          <option value="051">부산</option>
	          <option value="052">울산</option>
	          <option value="061">전북</option>
	          <option value="062">광주</option>
	        </select>-
	        <input type="text" name="tel2"  id ="tel2" size=4 maxlength=4 class="form-control"/>-
	        <input type="text" name="tel3" size=4  id ="tel3" maxlength=4 class="form-control"/>
	      </div>
	    </div>
	    <div class="input-group mb-3">
	      <label for="address">주소</label>
	      <div class="input-group mb-1">
	        <input type="text" id="sample6_postcode" placeholder="우편번호" class ="form-control">
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class ="btn btn-success">
				</div>
				<div class ="input-group mb-1">
					<input type="text" id="sample6_address" placeholder="주소" class ="form-control">
				</div>
	      <div class="input-group mb-1">
					<input type="text" id="sample6_detailAddress" placeholder="상세주소" class ="form-control">
					<input type="text" id="sample6_extraAddress" placeholder="참고항목" class ="form-control">
	      </div>
	    </div>
	    <div class="input-group mb-3">
	      <div class="input-group-text"><label for="homePage">Homepage address:</label></div>
	      <input type="text" class="form-control" name="homepage" value="http://" placeholder="홈페이지를 입력하세요." id="homepage"/>
	    </div>
	    <div class="input-group mb-3">
	      <div class="input-group-text"><label for="job">직업</label></div>
	      <select class="form-select" id="job" name="job">
	        <option>학생</option>
	        <option>회사원</option>
	        <option>공무원</option>
	        <option>군인</option>
	        <option>의사</option>
	        <option>법조인</option>
	        <option>세무인</option>
	        <option>자영업</option>
	        <option selected>기타</option>
	      </select>
	    </div>
	    <div class="input-group mb-3">
	       <span class="input-group-text">취미</span>
	      <div class="input-check-inline">
	        <label class="input-check-label">
	          <input type="checkbox" class="input-check-input ms-3" value="등산" name="hobby"/>등산 &nbsp;
	        </label>
	      </div>
	      <div class="input-check-inline">
	        <label class="input-check-label">
	          <input type="checkbox" value="낚시" name="hobby"/>낚시 &nbsp;
	        </label>
	      </div>
	      <div class="input-check-inline">
	        <label class="input-check-label">
	          <input type="checkbox" class="input-check-input" value="수영" name="hobby"/>수영 &nbsp;
	        </label>
	      </div>
	      <div class="input-check-inline">
	        <label class="input-check-label">
	          <input type="checkbox" class="input-check-input" value="독서" name="hobby"/>독서 &nbsp;
	        </label>
	      </div>
	      <div class="input-check-inline">
	        <label class="input-check-label">
	          <input type="checkbox" class="input-check-input" value="영화감상" name="hobby"/>영화감상 &nbsp;
	        </label>
	      </div>
	      <div class="input-check-inline">
	        <label class="input-check-label">
	          <input type="checkbox" class="input-check-input" value="바둑" name="hobby"/>바둑 &nbsp;
	        </label>
	      </div>
	      <div class="input-check-inline">
	        <label class="input-check-label">
	          <input type="checkbox" class="input-check-input" value="축구" name="hobby"/>축구 &nbsp;
	        </label>
	      </div>
	      <div class="input-check-inline">
	        <label class="form-check-label">
	          <input type="checkbox" value="기타" name="hobby" checked/>기타
	        </label>
	      </div>
	    </div>
	    <div class="input-group mb-3">
	      <div class="input-group-text"><label for="content">자기소개</label></div>
	      <textarea rows="5" class="form-control" id="content" name="content" placeholder="자기소개를 입력하세요."></textarea>
	    </div>
	    <div class="input-group mb-3">
	      <span class="input-group-text">정보공개</span>  &nbsp; &nbsp;
	      <div class="form-check"><input type="radio" name="userInfor" value="공개" class="me-1" checked />공개</div>
	      <div class="form-check"><input type="radio" name="userInfor" value="비공개" class="ms-3 ms-4 me-1" />비공개</div>
	    </div>
	    <div  class="input-group mb-3">
	      <div class="input-group-text">회원 사진(파일용량:2MByte이내)</div>
	      <input type="file" name="fName" id="file" class="form-control border"/>
	    </div>
	    <button type="button" class="btn btn-secondary" onclick="fCheck()">회원가입</button> &nbsp;
	    <button type="button" onclick="location.reload()"  class="btn btn-secondary">다시작성</button> &nbsp;
	    <button type="button" class="btn btn-secondary" onclick="location.href='memberLogin'">돌아가기</button>
  	</div>
  		
  		<input type ="hidden" name ="email"/>
  		<input type ="hidden" name ="address"/>
  		<input type ="hidden" name ="tel"/>
  		<input type ="hidden" name ="hobbys"/>
  		<!-- 표준 모달(응용)) -->
			<div class ="text-center">
					<div class="modal fade" id="myModal1">
					  <div class="modal-dialog modal-dialog-centered">
					    <div class="modal-content">
					      <!-- Modal Header -->
					      <div class="modal-header d-flex justify-content-center align-items-center">
					        <h4 class="modal-title">잠시 로딩~</h4>
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
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>