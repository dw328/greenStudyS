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
		
		return "include/message";
	}
	
	
}
