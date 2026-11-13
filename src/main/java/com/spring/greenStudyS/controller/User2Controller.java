package com.spring.greenStudyS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.greenStudyS.service.User2Service;
import com.spring.greenStudyS.vo.User2VO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user2")			/* 컨트롤러에서는 앞에 / <- O , jsp에서의 앞에 / <- X */
public class User2Controller {

	@Autowired
	User2Service user2Service;  
	
	@GetMapping("/user2Main")
	public String user2MainGet() {
		return "user2/user2Main";
	}
	
	@GetMapping("/user2List")
	public String user2ListGet(Model model) {		// 값을 담아 보낸다
		List<User2VO> vos = user2Service.getUser2List();
		model.addAttribute("vos", vos);
		return "user2/user2List";
	}
	
	@PostMapping("/user2List")
	public String user2ListPost(Model model, User2VO vo) {	
		System.out.println("vo.mid: " + vo.getMid());
		user2Service.setUser2Join(vo);
		
		List<User2VO> vos = user2Service.getUser2List();
		model.addAttribute("vos", vos);
		return "user2/user2List";
	}
	
	@GetMapping("/userInput")
	public String userInputGet() {
		return "user2/userInput";
	}
	
	@PostMapping("/userInput")
	public String userInputPost(Model model , User2VO vo) {
		int res = user2Service.setUserInput(vo);
		model.addAttribute("res", res);
		return "user2/user2Main";
	}
}
