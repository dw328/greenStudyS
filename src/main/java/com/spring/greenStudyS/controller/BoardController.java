package com.spring.greenStudyS.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.greenStudyS.common.PageNation;
import com.spring.greenStudyS.common.PageVO;
import com.spring.greenStudyS.common.ProjectProvide;
import com.spring.greenStudyS.service.BoardService;
import com.spring.greenStudyS.vo.BoardReplyVO;
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

//  @Autowired
//  ProjectProvide projectProvide;
	
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
	
	@PostMapping("/boardInput")
	public String boardInputPost(BoardVO vo) {
		// 1. 글을 올리기 전에 글 안에 이미지가 포함된어 있는지를 판별한다.
		// 이미지가 있다면, 저장된 이미지만 골라서(ckeditor 폴더) board폴더를 복사처리한다. ('/ckImage'폴더에서 '/board'폴더로 파일들을 처리한다.)
		if(vo.getContent().indexOf("src=\"/") != -1)	boardService.imageCheck(vo.getContent()); 
		
		// 2. 이미지 복사 작업을 완료했다면, DB안의 ckImage폴더 경로를 board폴더로 바꿔야한다.
		vo.setContent(vo.getContent().replace("/ckImage", "/board"));
		
		// 3. 2번작업을 마치면 작업처리된 내용들을 모두 vo에 담아서 DB에 저장처리된다. 
		int res = boardService.setBoardInput(vo);
		if(res != 0) return "redirect:/message/boardInputOk";
		else return "redirect:/message/boardInputNo";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/boardContent")
	public String boardCintentGet(Model model,
			HttpSession session,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
			int idx
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
		return "board/boardContent";
	}
	
	@GetMapping("/boardDelete")
	public String boardDeleteGet(int idx) {
		// 1. 내용물의 사진이 있을 경우에만 사진을 삭제처리한다.
		BoardVO vo = boardService.getBoardContent(idx);
		if(vo.getContent().indexOf("src=\"/") != -1)	boardService.imageDelete(vo.getContent());
		
		// 2. 사진작업이 끝나면 DB에 저장된 실제 정보를 삭제 시킨다.
		int res = boardService.setBoardDelete(idx);
		
		if(res != 0) return "redirect:/message/boardDeleteOk";
		else return "redirect:/message/boardDeleteNo?idx="+idx;
	}
	
	@GetMapping("/boardUpdate")
	public String boardUpdateGet(int idx, Model model, HttpSession session) {
		BoardVO vo = boardService.getBoardContent(idx);
		// 1. 수정 전의 내용을 세션에 담음
		session.setAttribute("sContent", vo.getContent());
		
		model.addAttribute("vo",vo);
		return "board/boardUpdate";
	}
	
	@PostMapping("/boardUpdate")
	public String boardUpdateOkPost(int idx, BoardVO vo, HttpSession session) {
		// 담았던 세션을 불음ㅁ
		String sContent = (String) session.getAttribute("sContent");
		// 일단 내용 바꿈
		vo.setContent(vo.getContent().replace("/ckImage", "/board"));   
		int res = boardService.setBoardUpdate(idx, vo);
		if(res != 0) {
			boardService.imageUpdateCoty(vo.getContent(), sContent); 
			session.removeAttribute("sContent");
			return "redirect:/message/boardUpdateOk?idx="+idx;
		}
		else return "redirect:/message/boardUpdateNo?idx="+idx;
	}
	
	@ResponseBody
	@PostMapping("/boardGoodCheck")
	public void boardGoodCheckOkPost(int idx, int sw) {
		boardService.setBoardGoodCheck(idx, sw);
	}
	
	@ResponseBody
	@PostMapping("/boardReplyInput")
	public String boardReplyInputPost(BoardReplyVO replyVO) {
	BoardReplyVO replyParentVO =	boardService.getBoardParentReplyCheck(replyVO.getBoardIdx());
	if(replyParentVO == null) {
		replyVO.setRe_order(1);
	}
	else {
		replyVO.setRe_order(replyParentVO.getRe_order() +1);
	}
	replyVO.setRe_step(0);
	int res = boardService.setBoardReplyInput(replyVO);
	
	return res + "";
	}
	
	@ResponseBody
	@PostMapping("/boardReplyInputRe")
	public String boardReplyInputRePost(BoardReplyVO replyVO) {
		// 부모 스텝(Re_step) + 1;
		replyVO.setRe_step(replyVO.getRe_step()+1);
		boardService.setReplyOrderUpdate(replyVO.getBoardIdx(), replyVO.getRe_order()); // 부모 보다 큰 Re_order 자료들은 모루 +1 시킨다.
		// 부모의 re_order +1
		replyVO.setRe_order(replyVO.getRe_order()+1);
		
		int res = boardService.setBoardReplyInput(replyVO);
		
		return res + "";
	}
	
	
	
}
