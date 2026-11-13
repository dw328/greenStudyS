package com.spring.greenStudyS.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.greenStudyS.common.PageNation;
import com.spring.greenStudyS.common.PageVO;
import com.spring.greenStudyS.service.BoardService;
import com.spring.greenStudyS.vo.BoardVO;
import com.spring.greenStudyS.vo.GuestVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PageNation pagenation;


	@GetMapping("/boardList")
	public String boardListGet(Model model, PageVO pageVO,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize
		) {
		
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setSection("board");
		pageVO = pagenation.getTotRecCnt(pageVO);
		
		List<BoardVO> vos = boardService.getBoardList(pageVO.getStartIndexNo(), pageSize);
		
		model.addAttribute("vos",vos);
		model.addAttribute("pageVO",pageVO);
		
		return "board/boardList";
	}
	
	@GetMapping("/boardInput")
	public String boardInputGet() {
		return "board/boardInput";
	}
	
	@GetMapping("/boardContent")
	public String boardCintentGet() {
		return "board/boardContent";
	}
	/*
	 
	@GetMapping("/guestMain")
	public String guestMainGet() {
		return "guest/guestMain";
	}
	

	
	@PostMapping("/guestInput")
	public String guestInputPost(Model model, GuestVO vo, HttpServletRequest request,
			String email1, String email2,	RedirectAttributes red) {
		vo.setEmail(email1 + "@" + email2);
		vo.setHostIp(request.getRemoteAddr());
		int res = boardService.setGuestInput(vo);
		red.addFlashAttribute("res", res);
		return "redirect:/guest/boardList";
	}
	
	@GetMapping("/guestDelete")
	public String guestDeleteGet( Model model,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
			) {
		int totRecCnt = boardService.getTotRecCnt();	// 전체 레코드 건수
		int totPage = (totRecCnt % pageSize) == 0 ? (totRecCnt / pageSize) : (totRecCnt / pageSize) + 1;
		int startIndexNo = (pag -1) * pageSize;
		int curScrStarNo = totRecCnt - startIndexNo;
		
		List<GuestVO> vos = boardService.getboardList(startIndexNo, pageSize);
		model.addAttribute("vos",vos);
		model.addAttribute("pag",pag);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("totRecCnt",totRecCnt);
		model.addAttribute("totPage",totPage);
		model.addAttribute("curScrStarNo",curScrStarNo);
		
		return "guest/guestDelete";
	}
	
	@PostMapping("/guestDelete")
	public String guestDeletePost(Model model, int idx) {
		int res = boardService.setGuestDelete(idx);
		model.addAttribute("res", res);
		return "guest/guestDelete";
	}
	
	@GetMapping("/guestUpdate")
	public String guestUpdateGet(Model model, int idx, GuestVO vo) {
		
		vo = boardService.setGuestUpdate(idx);
		model.addAttribute("vo", vo);
		return "guest/guestUpdate";
	}
	
	@PostMapping("/guestUpdate")
	public String guestUpdatePost(Model model, GuestVO vo, HttpServletRequest request,
		String email1, String email2, int idx,	RedirectAttributes red) {
		vo.setEmail(email1 + "@" + email2);
		vo.setHostIp(request.getRemoteAddr());
		int res = boardService.setGuestUpdateOK(vo, idx);
		red.addFlashAttribute("res", res);
		return "redirect:/guest/boardList";
	}
*/	
	
}
