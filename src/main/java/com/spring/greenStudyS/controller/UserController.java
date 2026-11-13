package com.spring.greenStudyS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.greenStudyS.service.UserService;
import com.spring.greenStudyS.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {


	@Autowired		//연결하기(controller -> service)
	UserService userService;
	
	@GetMapping("/userList/{orderList}")
	public String userListGet(Model model, @PathVariable String orderList, UserVO vo) {
		String[] temp = orderList.split("Order");
		vo.setFieldName(temp[0]);
		vo.setOrderName(temp[1]);
		
		List<UserVO> vos = userService.getUserList(vo);
		
		model.addAttribute("vos",vos);
		model.addAttribute("orderList",orderList);
		
		return "user/userList";
	}
	
	@GetMapping("/userSearch")		// 값을 넘기는 mid가 @PathVariable로 받는 줄알고 위에서 받아서 에러가 난다. 그래서 절대 경로로 준다.
	public String userSearchGet(Model model, String mid) {
		List<UserVO> vos = userService.getUserSearch(mid);
		
		model.addAttribute("vos",vos);
		model.addAttribute("orderList","idxOrderDesc");
		
		return "user/userList";
	}
	
	@ResponseBody		
	@PostMapping("/userDelete")		
	public String userDeleteGet(int idx) {
		return userService.setUserDelete(idx) + "";		// String으로 넣어서 넘김
	}
	
	
	/*
	 * @GetMapping("/userList") public String userJoinGet(Model model, @PathVariable
	 * String orderList, UserVO vo) { vo.setMid("mid"); vo.setName("name");
	 * vo.setAge(Integer.parseInt("age")); vo.setAddress("address");
	 * userService.setUserJoin(vo);
	 * 
	 * return "user/userList";
	 * 
	 * }
	 */
	
	@PostMapping("/userList/{orderList}")		// 같은 url로 넘어서 받음(홈페이지에 확인하면 보임)(내가 있었던 회면의 url로 방아서 action이 필요 없다.)
	public String userListPost(Model model, @PathVariable String orderList, UserVO vo) {
		userService.setUserJoin(vo);		// 회원가입
		
		vo.setFieldName("idx");
		vo.setOrderName("desc");

		List<UserVO> vos = userService.getUserList(vo);
		model.addAttribute("vos", vos);
		model.addAttribute("orderList", "idxOrderDesc");
		return "user/userList";
	}
	
	
}
