package com.spring.greenStudyS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Member;
import com.spring.greenStudyS.common.PageNation;
import com.spring.greenStudyS.common.PageVO;
import com.spring.greenStudyS.common.ProjectProvide;
import com.spring.greenStudyS.service.AdminService;
import com.spring.greenStudyS.service.MemberService;
import com.spring.greenStudyS.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;

	@Autowired
	PageNation pageNation;
	
	@Autowired
	ProjectProvide projectProvide;
	
	@GetMapping("/adminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@GetMapping("/adminRight")
	public String adminRightGet() {
		return "admin/adminRight";
	}
	
	@GetMapping("/adminLeft")
	public String adminLeftGet() {
		return "admin/adminLeft";
	}
	
	@GetMapping("/memberList")
	public String memberListGet(Model model, PageVO pageVO,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
		) {
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setSection("member");
		pageVO = pageNation.getTotRecCnt(pageVO);
		
		List<MemberVO> vos = adminService.getMemberList(pageVO.getStartIndexNo(), pageSize);
		model.addAttribute("vos", vos);
		return "admin/member/memberList";
	}
	
	@GetMapping("/memberContent")
	public String memberContentGet(Model model, String mid) {
		MemberVO vo = memberService.memberIdCheck(mid);
		model.addAttribute("vo", vo);
		return "admin/member/memberContent";
	}

	// 선택된 회원등급을 일관 변경
	@ResponseBody
	@PostMapping("/memberLevelSelectCheck")
	public String memberLevelSelectCheckPost(String idxSelectArray, int levelItem) {
		String[] idxSelectArrays = idxSelectArray.split("/");
		for(int i =0; i<idxSelectArrays.length; i++) {
			int idx = Integer.parseInt(idxSelectArrays[i]);
			adminService.setMemberLevelSelectCheck(idx, levelItem);
		}
		return "1";
	}
	
	@ResponseBody
	@PostMapping("/levelChange")
	public int levelChangePost(int idx, int level) {
		return adminService.levelChangeUpdate(idx, level);
	}
	
	@ResponseBody
	@PostMapping("/searchLevel")
	public List<MemberVO> searchLevelPost(int level, Model model) {
		List<MemberVO> vos = adminService.searchLevel(level); 
		 return vos;
	}
	// 탈퇴 신청된 회원을 영구 삭제
	@ResponseBody
	@PostMapping("/memberDeleteOk")
	public String memberDeleteOkPost(int idx, String photo) {
		if(!photo.equals("noimage.jpg")) projectProvide.deleteFile(photo, "member");
		return adminService.setMemerDeleteOk(idx) + "";
	}

}
