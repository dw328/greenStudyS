package com.spring.greenStudyS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/message")
public class MessageController {

	@RequestMapping(value = "/{msgFlag}", method = RequestMethod.GET)
	public String messageGet(Model model, 
		@PathVariable String msgFlag,
		@RequestParam(name = "idx" , defaultValue = "0", required =false) int idx,
		@RequestParam(name = "mid" , defaultValue = "", required =false) String mid
	) {
		if(msgFlag.equals("memberJoin")) {
			model.addAttribute("message", "회원가입 완료했습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberJoinNo")) {
			model.addAttribute("message", "회원가입을 실패했습니다.");
			model.addAttribute("url", "/member/memberJoin");
		}
		else if(msgFlag.equals("mailSendOk")) {
			model.addAttribute("message", "메일을 발송하였습니다.");
			model.addAttribute("url", "/study/mailForm");
		}
		else if(msgFlag.equals("mailSendNo")) {
			model.addAttribute("message", "메일 발송을 실패하였습니다.");
			model.addAttribute("url", "/study/mailForm");
		}
		else if(msgFlag.equals("memberLoginOk")) {
			model.addAttribute("message", mid+"님 로그인 되었습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("memberLoginNo")) {
			model.addAttribute("message", "로그인 실패했습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLogout")) {
			model.addAttribute("message", mid+"님 로그아웃 됐습니다.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("nickNameError")) {
			model.addAttribute("message", "닉네임이 중복됩니다..");
			model.addAttribute("url", "/member/memberUpdate");
		}
		else if(msgFlag.equals("memberUpdateOk")) {
			model.addAttribute("message", "회원 수정이 완료됩습니다.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("adminNo")) {
			model.addAttribute("message", "로그인 후 사용하세요.");
			model.addAttribute("url", "/");
		}
		else if(msgFlag.equals("memberNo")) {
			model.addAttribute("message", "로그인 후 사용하세요.");
			model.addAttribute("url", "/member/memberLogin");
		}
		else if(msgFlag.equals("memberLevelNo")) {
			model.addAttribute("message", "회원 등급을 확인하세요.");
			model.addAttribute("url", "/member/memberMain");
		}
		else if(msgFlag.equals("boardInputOk")) {
			model.addAttribute("message", "게시물에 글이 등록되었습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("boardInputNo")) {
			model.addAttribute("message", "글 등록 실패했습니다.");
			model.addAttribute("url", "/board/boardInput");
		}
		else if(msgFlag.equals("boardDeleteOk")) {
			model.addAttribute("message", "글 삭제를 완료했습니다.");
			model.addAttribute("url", "/board/boardList");
		}
		else if(msgFlag.equals("boardDeleteNo")) {
			model.addAttribute("message", "글 삭제를 실패했습니다.");
			model.addAttribute("url", "/board/boardContent?idx="+idx);
		}
		else if(msgFlag.equals("boardUpdateOk")) {
			model.addAttribute("message", "글 수정를 완료했습니다.");
			model.addAttribute("url", "/board/boardContent?idx="+idx);
		}
		else if(msgFlag.equals("boardUpdateNo")) {
			model.addAttribute("message", "글 수정를 실패했습니다.");
			model.addAttribute("url", "/board/boardUpdate?idx="+idx);
		}
		else if(msgFlag.equals("multiFileUploadOk")) {
			model.addAttribute("message", "파일이 업로드됐습니다.");
			model.addAttribute("url", "/study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("multiFileUploadNo")) {
			model.addAttribute("message", "파일이 업로드 실패");
			model.addAttribute("url", "/study/fileUpload/multiFile");
		}
		else if(msgFlag.equals("pdsInputOk")) {
			model.addAttribute("message", "자료실에 글이 입력됐습니다.");
			model.addAttribute("url", "/pds/pdsList");
		}
		else if(msgFlag.equals("pdsInputNo")) {
			model.addAttribute("message", "자료실에 입력 실패");
			model.addAttribute("url", "/pds/pdsInput");
		}
		
		return "include/message";
	}
	
	
}
