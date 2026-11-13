package com.spring.greenStudyS.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.security.enterprise.credential.Password;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.greenStudyS.common.PageNation;
import com.spring.greenStudyS.common.PageVO;
import com.spring.greenStudyS.common.ProjectProvide;
import com.spring.greenStudyS.service.MemberService;
import com.spring.greenStudyS.vo.MailVO;
import com.spring.greenStudyS.vo.MemberVO;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

//@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	ProjectProvide projectProvide;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	PageNation pagenation;
	
	@GetMapping("/memberLogin")
	public String memberLoginGet(HttpServletRequest request) {
		Cookie[] cookies =request.getCookies();
		
		if(cookies != null) {
			for(int i = 0; i< cookies.length; i ++) {
				cookies[i].setPath("/");
				if(cookies[i].getName().equals("cMid")) {
					request.setAttribute("mid", cookies[i].getValue());
					break;
				}
			}
		}
		
		return "member/memberLogin";
	}
	
	@PostMapping("/memberLogin")
	public String memberLoginPost(HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(name="mid", defaultValue = "atom1111" ,required = false)String mid,
			@RequestParam(name="pwd", defaultValue = "1234" ,required = false)String pwd,
			@RequestParam(name="idSave", defaultValue = "" ,required = false)String idSave
			) {
		MemberVO vo = memberService.memberIdCheck(mid);
		if(vo != null && vo.getUserDel().equals("NO") && passwordEncoder.matches(pwd, vo.getPwd())) {
			// 세션처리
			String strLevel ="";
			if(vo.getLevel() ==0 ) strLevel = "관리자";
			else if (vo.getLevel()==1) strLevel = "우수회원";
			else if (vo.getLevel()==2) strLevel = "정회원";
			else if (vo.getLevel()==3) strLevel = "준회원";
			
			session.setAttribute("sMid", mid);
			session.setAttribute("sNickName", vo.getNickName());
			session.setAttribute("sLevel", vo.getLevel());
			session.setAttribute("strLevel", strLevel);
			
			// 쿠키 저장/ 처리
			// 페이지, 리퀘스트, 어플리에키션, 세션, 모듈 (서버에서 사용) 서버 과부화 -> 너네집 기록에서 사용 쿠키
			if(idSave.equals("on")) {
				Cookie cookieMid = new Cookie("cMid", mid);
				cookieMid.setPath("/");
				cookieMid.setMaxAge(60*60*24*7);		// 쿠키의 만료시간을 7일로 지정
				response.addCookie(cookieMid);
			}
			else {
				Cookie[] cookies = request.getCookies();
				if(cookies != null) {
					for(int i=0; i<cookies.length; i++) {
						if(cookies[i].getName().equals("cMid")) {
							cookies[i].setPath("/");
							cookies[i].setMaxAge(0);
							response.addCookie(cookies[i]);
							break;
						}
					}
				}
			}
			
			// 기타처리(방문카운트 증가, 포인트 증가,방문 포인트 하루동안 5회까지 방문시 각 방문마다 10포인트 지급한다.
			if(!LocalDateTime.now().toString().substring(0,10).equals(vo.getLastDate().substring(0,10))) {
				vo.setTodayCnt(0);
			}
//			vo.setTodayCnt(vo.getTodayCnt()+1);
			
			// 방문 포인트 는 1일 ->5회가능 (1회방문시 10point 부여)
			vo.setPoint(vo.getTodayCnt() <= 5 ? 10 : 0);
			
			
			// 오늘로부터 2일 그냥 처음 방문과 마지막 방문이 다름
			/******************************************************************************/
			if((vo.getStartDate() != vo.getLastDate()) & vo.getLevel()== 3 && vo.getVisitCnt() >= 5) {
				vo.setLevel(vo.getLevel()-1);
			}
			/******************************************************************************/
			
			// 조건에 대한 처리가 완료되면 변경된 모든 내용들을 업데이트 처리한다.
			memberService.setmemberinforUpdate(vo);
			return "redirect:/message/memberLoginOk?mid="+mid;
		}
		else return "redirect:/message/memberLoginNo";
		
	}
	
	@GetMapping("/memberJoin")
	public String memberJoinGet() {
		return "member/memberJoin";
	}
	
	@ResponseBody
	@GetMapping("/memberIdCheck")
	public String memberIdCheckGet(String mid, MemberVO vo) {
		vo = memberService.memberIdCheck(mid);
		int res = 0;
		
		if(vo != null) {
	    res = 1;
		}
		return  res + "";		// redirect을 이용해서 message controller 이용하기
	}
	
	@ResponseBody
	@PostMapping("/memberNickCheck")
	public String memberNickCheckPost(String nickName, MemberVO vo) {
		vo = memberService.memberNickCheck(nickName);
		int res = 0;
		
		if(vo != null) {
	    res = 1;
		}
		return  res + "";
	}
	
	// ****************************************************회원 가입 사진 업로드***************************************************//
	@PostMapping("/memberJoin")
	public String memberJoinPost(MultipartFile fName, MemberVO vo) {
		String con = "";
		// 비밀번호 암호화
		vo.setPwd(passwordEncoder.encode(vo.getPwd()));
		
		System.out.println("fNmae: " + fName);
		// 사진 업로드
		if(!fName.getOriginalFilename().equals(""))  vo.setPhoto(projectProvide.uploadFile(fName,"member",""));
		else vo.setPhoto("noimage.jpg");
		
		System.out.println("vo: " + vo);
//		String oFileName = fName.getOriginalFilename().toString();
//		vo.setPhoto(oFileName);
//		projectProvide.fileUpload(fName, "member",(String)vo.getName());
		
		int res = memberService.setMemberJoin(vo);

		if (res != 0) con = "redirect:/message/memberJoin";
		else con = "redirect:/message/memberJoinNo";
		return con;
	}
	// ****************************************************회원 가입 사진 업로드***************************************************//
	
	
	// ****************************************************이메일 인증 ***************************************************//
	@ResponseBody
	@PostMapping("/emailCheckOk")
	public String emailCheckOkPost(String email,HttpServletRequest request) {
		/* projectProvide.mailSend("dwg5057@naver.com", "비밀번호 변경 확인", "1234") {*/
		MailVO vo = new MailVO();
		vo.setToMail(email);
		vo.setTitle("인증이메일");
		String uid = UUID.randomUUID().toString();
		
		HttpSession session = request.getSession();
		session.setAttribute("pwd", uid);
		session.getAttribute("pwd");
		
		int res = 0;
		try {
			projectProvide.mailSend(vo.getToMail(), vo.getTitle(), uid);
			res = 1;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		if(res !=0) return  res + "";
		else return res + "";
		
	}
	// ****************************************************이메일 인증 ***************************************************//
	
	
	// ****************************************************이메일 인증 확인 ***************************************************//
	@ResponseBody
	@PostMapping("/certificationCheck")
	public String certificationCheckPost(String certificationId, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String pwd = (String) session.getAttribute("pwd");
				
		int res =0;
		if(certificationId.equals(pwd)) {
			res = 1;
		}

		
		return res + "";
		
	}
//****************************************************이메일 인증 확인 ***************************************************//
	@GetMapping("/memberLogout")
	public String memberLogoutGet(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		session.invalidate();
		return "redirect:/message/memberLogout?mid="+mid;
	}
	
	@GetMapping("/memberMain")
	public String memberMainGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.memberIdCheck(mid);
		model.addAttribute("vo", vo);
		return "member/memberMain";
	}
	
	@GetMapping("/memberList")
	public String memberListGet(Model model, PageVO pageVO,
			@RequestParam(name = "pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name = "pageSize", defaultValue = "3", required = false) int pageSize
		) {
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setSection("member");
		pageVO = pagenation.getTotRecCnt(pageVO);
		
		List<MemberVO> vos = memberService.getMemberList(pageVO.getStartIndexNo(), pageSize);
		model.addAttribute("vos" ,vos);
		model.addAttribute("pageVO",pageVO);
		return "member/memberList";
	}
	
	@GetMapping("/memberContent")
	public String memberListGet(Model model, String mid) {
		MemberVO vo = memberService.memberIdCheck(mid);
		model.addAttribute("vo" ,vo);
		
		return "member/memberContent";
	}
	
	@GetMapping("/memberPwdCheck/{pwdFlag}")		// p : 비밀번호 변경, u: 회원정보수정
	public String memberPwdCheckGet(Model model, @PathVariable String pwdFlag) {
		model.addAttribute("pwdFlag" ,pwdFlag);
		
		return "member/memberPwdCheck";
	}
	
	@ResponseBody
	@PostMapping("/memberPwdCheckOk")
	public String memberPwdCheckPost(String pwd, String mid) {
		MemberVO vo = memberService.memberIdCheck(mid);
		if (vo != null && passwordEncoder.matches(pwd, vo.getPwd())) return "1";
		return "0";
	}
	
	@ResponseBody
	@PostMapping("/memberPwdChangeOk")
	public int memberPwdChangePost(String pwd, String mid) {
		return memberService.setMemberPwdChangeOk(mid, passwordEncoder.encode(pwd));
	}
	
	@GetMapping("/memberUpdate")
	public String memberUpdateGet(Model model, HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		MemberVO vo = memberService.memberIdCheck(mid);
		model.addAttribute("vo" ,vo);
		
		return "member/memberUpdate";
	}
	
	// **mapper에 userInfor 빠짐 (채우기)**
	@PostMapping("/memberUpdate")
	public String memberUpdatePost(MemberVO vo,MultipartFile fName, HttpSession session) {
		// 수정은 백엔드랑 프론트 엔드 둘 다 확인해야한다.
		// 닉네임 변경유무...(닉네임 중복 체크)
		String nickName = (String) session.getAttribute("sNickName");
		if(memberService.memberNickCheck(vo.getNickName()) != null && !nickName.equals(vo.getNickName())) {		
			// 아 그러니깐 내가 넣은 nickName이랑 session에 넣은거랑 비교해서 같지 않으면..이면 같은것도 못들어가는거 아니간?
			return "redirect:/message/nickNameError";
		}

		if(fName.getOriginalFilename() != null && !fName.getOriginalFilename().equals("")) { 
			vo.setPhoto(projectProvide.uploadFile(fName, "member", null));
		}
		
		// 처리된 결과를 DB에 저장(Ok)
		int res = memberService.setMemberUpdateOk(vo);
		
		if(res != 0) {
			session.setAttribute("sNickName", nickName);
			return "redirect:/message/memberUpdateOk";
		}
		else return "redirect:/message/memberUpdateNo";
	}

	
	@ResponseBody
	@PostMapping("/userDel")
	public String userDelPost(HttpSession session) {
		String mid = (String) session.getAttribute("sMid");
		int res = memberService.setUserDel(mid);
		
		if(res != 0) {
			session.invalidate();
			return "1";
		}
		else return "0";
	}
	
}
