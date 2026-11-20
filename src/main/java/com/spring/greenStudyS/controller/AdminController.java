package com.spring.greenStudyS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.spring.greenStudyS.service.BoardService;
import com.spring.greenStudyS.service.GuestService;
import com.spring.greenStudyS.service.MemberService;
import com.spring.greenStudyS.vo.BoardReplyVO;
import com.spring.greenStudyS.vo.BoardVO;
import com.spring.greenStudyS.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberService;

	@Autowired
	BoardService boardService;
	
	@Autowired
	GuestService guestService;
	
	@Autowired
	PageNation pageNation;
	
	@Autowired
	ProjectProvide projectProvide;
	
	

	@GetMapping("/adminMain")
	public String adminMainGet() {
		return "admin/adminMain";
	}
	
	@GetMapping("/adminRight")
	public String adminRightGet(Model model, MemberVO vo) {
		List<MemberVO> userCreateVos = adminService.getuserCreateList();
		List<MemberVO> userDelVos = adminService.getuserDelList();

		model.addAttribute("userCreateVos", userCreateVos);
		model.addAttribute("userDelVos", userDelVos);
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

	@GetMapping("/boardList")
	public String boardListGet(Model model, PageVO pageVO,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
		) {
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setSection("board");
		pageVO = pageNation.getTotRecCnt(pageVO);
		
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageSize);
		model.addAttribute("vos",vos);
		model.addAttribute("pageVO", pageVO);
		return "admin/board/boardList";
	}
	
	// board게시물 삭제
	@ResponseBody
	@PostMapping("/boardDeleteList")
	public String boardDeleteListPost(String boardDeleteidxArray) {
		String[] boardDeleteidxArrays = boardDeleteidxArray.split("/");
		int res = 0;

		for(int i =0; i < boardDeleteidxArrays.length; i++) {
			int idx = Integer.parseInt(boardDeleteidxArrays[i]);
			// 내용을 업데이트 전에 이미지를 먼저 삭제애햐아함...
			BoardVO vo = adminService.getBoardContent(idx);
			if(vo.getContent().indexOf("src=\"/") != -1)	boardService.imageDelete(vo.getContent());
			// 댓글 때문에 함부로 지울 수 없음.그래서 그냥 내용이랑 개인정보만 삭제하고 안에 있는 내용을 삭제한 내용이라고 교체하기
			res = adminService.setBoardUpdateDelList(idx);

			// 파일 안에 이미지가 있으면 폴더에 이미지 삭제도 해야함..
		}
		return  res + "" ;
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("boardContent")
	public String boardContentGet(int idx, Model model, PageVO pageVO, HttpSession session,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
		) {
	// 조회수 증가처리(중복불허)
			List<String> contentReadNum = (List<String>) session.getAttribute("sContentIdx");
			if(contentReadNum == null) contentReadNum = new ArrayList<String>();
			String imsiContentReadNum = "board" + idx;
			if(!contentReadNum.contains(imsiContentReadNum)) {
				boardService.setReadNumPlus(idx);
				contentReadNum.add(imsiContentReadNum);
			}
			session.setAttribute("sContentIdx", contentReadNum);
			
			// 이전글 / 다음글 처리
			BoardVO preVO = boardService.getPreNextSearch(idx,"preVO");
			BoardVO nextVO = boardService.getPreNextSearch(idx,"nextVO");
			model.addAttribute("preVO",preVO);
			model.addAttribute("nextVO",nextVO);
			
			BoardVO vo = boardService.getBoardContent(idx);
			model.addAttribute("vo",vo);
			model.addAttribute("pag",pag);
			model.addAttribute("pageSize",pageSize);
			
			// 댓글(대댓글) 추가 입력처리
			List<BoardReplyVO> replyVos = boardService.getBoardReply(idx);
			model.addAttribute("replyVos", replyVos);
		
			return "admin/board/boardContent";
	}

	@GetMapping("/guestList")
	public String guestList (Model model) {
		return "admin/guest/guestDelete";		
	} 
	
}
