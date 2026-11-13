package com.spring.greenStudyS.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.greenStudyS.common.ARIAUtil;
import com.spring.greenStudyS.common.ProjectProvide;
import com.spring.greenStudyS.common.SecurityUtil;
import com.spring.greenStudyS.service.StudyService;
import com.spring.greenStudyS.vo.MailVO;
import com.spring.greenStudyS.vo.MemberVO;
import com.spring.greenStudyS.vo.User2VO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/study")
public class StudyController {

	
	
	@Autowired		// 이게 인터페이스에서 가지만 가지고 오는건 구현객체가 온다.
	StudyService studyService;
	
	@Autowired		
	ProjectProvide projectProvide;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
//	@Autowired
//	SecurityUtil securityUtil;
//	
	@GetMapping("/ajaxForm")
	public String ajaxFormGet() {
		return "study/ajax/ajaxForm";
	}
	
	//views를 안탄다.원래는 return만 사용해서 값을 리턴함(비동기식이라)	RestAPI(Put, get, delete, update)
	@ResponseBody
	@PostMapping("/ajaxTest1")
	public String ajaxTest1Post(int idx) {
		int res = ((int)(Math.random()*100) +1) + idx;
		return res + "";
	}
	
	@ResponseBody
	@PostMapping(value = "/ajaxTest2", produces = "application/text; charset=utf-8" ) // 한글 변화
	public String ajaxTest2Post(String str) {
		int res = ((int)(Math.random()*100) +1);
		str += "하세요." +res;
//		System.out.println("str: " + str);
		return str;
	}
	
	@GetMapping("/ajaxTest3")
	public String ajaxTest3Get() {
		return "study/ajax/ajaxTest3";
	}
	
	@ResponseBody
	@PostMapping(value = "/ajaxTest3") 
	public String[] ajaxTest3Post(String dodo) {
//		String[] strArray = new String[100];
//		strArray = studyService.getCityStringArray(dodo);		// 노예한테 일을 부탁하지 않고 주인한테 부탁하는 그런 구조인듯...
//		return strArray;
		return studyService.getCityStringArray(dodo);		// 위의 3줄를 한줄 요약
	}

	@ResponseBody
	@PostMapping(value = "/ajaxTest3Res")
	public String ajaxTest3ResPost(String dodo, String city) {
		String res = "";
		if(city.equals("도시선택")) {
			res = "지역: " + dodo + "이고, 도시는 선택하지 않았습니다.";
		}
		else if(city != null) res = "지역: " + dodo + ", 도시: " + city;
		return res;
	}
	
	@GetMapping("/ajaxTest4")
	public String ajaxTest4Get() {
		return "study/ajax/ajaxTest4";
	}
	
	@ResponseBody
	@PostMapping("/ajaxTest4")
	public List<String> ajaxTest4Post(String dodo) {
		/*
		 * List<String> vos = new ArrayList<String>(); vos =
		 * studyService.getCityArrayList(dodo); 
		 * return vos;
		 */
		return studyService.getCityArrayList(dodo);
	}
	
	
	@GetMapping("/ajaxTest5")
	public String ajaxTest5Get() {
		return "study/ajax/ajaxTest5";
	}
	
//	@ResponseBody
//	@PostMapping("/ajaxTest5")
//	public Map<Object, Object> ajaxTest5Post(String dodo) {
//		Map<Object, Object> map = new HashMap<Object, Object>();
//		List<String> vos = new ArrayList<String>();
//		vos = studyService.getCityArrayList(dodo); 
//		map.put("city", vos);		// 키(변하지 않는것을 넣는게 좋다.), 값
//		return map;
//	}
	
	/*
	 * @ResponseBody
	 * @PostMapping("/ajaxTest5") public Map<Object, Object> ajaxTest5Post(String
	 * dodo) { Map<Object, Object> map = new HashMap<Object, Object>();
	 * 
	 * List<String> vos = studyService.getCityArrayList(dodo); map.put("city", vos);
	 * // 키(변하지 않는것을 넣는게 좋다.), 값
	 * 
	 * return map; }
	 */
	
	@ResponseBody
	@PostMapping("/ajaxTest5")
	public Map<Object, Object> ajaxTest5Post(String dodo) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("city", studyService.getCityArrayList(dodo));		// 키(변하지 않는것을 넣는게 좋다.), 값
		
		return map;
	}
	
	@GetMapping("/ajaxTest6")
	public String ajaxTest6Get(Model model) {
		List<String> midVos = studyService.userMidList();
		model.addAttribute("midVos",midVos);
		
		List<User2VO> genderVos = studyService.userGenderSearch();
		model.addAttribute("genderVos",genderVos);
		return "study/ajax/ajaxTest6";
	}
	
	@ResponseBody
	@PostMapping(value = "/ajaxTest6", produces = "application/text; charset=utf-8")		// 한글이 ?? 출력이 되어서 인코딩을 넣음
	public String ajaxTest6Post(String mid) {
		User2VO vo = studyService.userMidSearch(mid);
		String str = "<h3>회원 정보</h3>";
		str += "회원 고유번호: " + vo.getIdx() + "</br>";
		str += "회원 아이디: " + vo.getMid() + "</br>";
		str += "회원 성명: " + vo.getName() + "</br>";
		str += "회원 나이: " + vo.getAge() + "</br>";
		str += "회원 성별: " + vo.getGender() + "</br>";
		str += "회원 주소: " + vo.getAddress() + "</br>";
		return  str;
	}

	@ResponseBody
	@PostMapping("/ajaxTest6_2")
	public User2VO ajaxTest6_2Post(String mid) {
		return  studyService.userMidSearch(mid);
	}

	@ResponseBody
	@PostMapping("/ajaxTest6_3")
	public List<User2VO> ajaxTest6_3Post(String gender) {
		return  studyService.userGenderList(gender);
	}
	
	@ResponseBody
	@PostMapping("/midExactCheck")
	public User2VO midExactCheckPost(String mid) {
		return  studyService.midExactCheck(mid);
	}
	
	@ResponseBody
	@PostMapping("/midPartCheck")
	public List<User2VO> midPartCheckPost(String mid) {
		return studyService.midPartCheck(mid);
	}
	
	@GetMapping("/fileUpload")
	public String fileUploadGet() {
	  return "study/fileUpload/fileUploadForm";
	}

	
// 이 메소드는 StudyController에서 파일 업로드를 구현함.(밑에 있는 fileUpload2Post는 공용으로 사용해서 common 패키지에 넣음, StudyServiceImpl에서 한번 더 구현) 3번 구현함
	@PostMapping("/fileUpload")
	public String fileUploadPost(MultipartFile fName, String name, HttpServletRequest request) throws IOException {
		// String relPath = request.getSession().getServletContext().getRealPath("");																											// request session에 있어서 생성해야함.
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");														// 파일 업로드 경로
		String uid = UUID.randomUUID().toString().substring(0,8);																																					// 중복 파일 방지 랜덤 이름 넣기
		String oFileName = fName.getOriginalFilename();																																										// 경로 이름이 길어서 여기서 변수를 선언
		String file = oFileName.substring(0, oFileName.lastIndexOf(".")) + "_" + uid + oFileName.substring(oFileName.lastIndexOf("."));		// 앞에서부터 .까지 자르고 앞에 작성한 uid를 넣음 그리고 뒤의 확장자를 붙임
																																																																			// ex) memderList.jsp  -> memderList_uid.jsp
		
//		System.out.println("file : " + file);
//		System.out.println("realPath: " + realPath);
		
		FileOutputStream fos = new FileOutputStream(realPath+file);																																				// 폴더에 사진을 넣음(in,out)out이 파일을 넣음(사실 저도 이부분 헷갈려서 확실하게 설명을 못하겠어요ㅠㅜ) (경로와 파일 이름을 붙임)			
//		System.out.println("fos: " + fos);
		
		fos.write(fName.getBytes());
		
		fos.close();
// 		"redirect: message/fileUploadOk";
		return "study/fileUpload/fileUploadForm";
	}
	
	
	
// 이 메소드는 StudyController에서 파일 업로드를 구현함
	@PostMapping("/fileUpload2")
	public String fileUpload2Post(MultipartFile fName, String name)  {
		
//		int res = studyService.fileUpload(fName, name, request);   																																			// studyService
		int res = projectProvide.fileUpload(fName,  "fileUpload", name);																																	// projectProvide 아 fileUpload 이 부분은 강제로 문제를 넣어서 경로를 만들어준거네...
																																																																			// jsp의 파일, 들어가야할 경로(path) , 사진의 주인 이름 
//		if(res != 0) return "redirect: message/fileUploadOk";
//		else return "redirect: message/fileUploadNo";
		
		return "study/fileUpload/fileUploadForm";
	}
	
	@GetMapping("/mailForm")
	public String mailFormGet(Model model) {
		List<MemberVO> vos = studyService.getEmailList();
		model.addAttribute("vos", vos);
	  return "study/mail/mailForm";
	}
	
	@PostMapping("/mailForm")		
	public String mailFormPost(HttpServletRequest request, MailVO vo) throws MessagingException {
		String toMail = vo.getToMail();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		// 메일 전송을 위한 객체: MimeMessage(): 그림?글? 확인(진짜 메일 보냄), MimMessageHelper(): (첨부파일, 메일 내용)바구니
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메세지 보관함 정리(작업)
		messageHelper.setTo(toMail);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		// 스팸이 아닌걸 알리기 위해 발신자의 필요한 정보를 추가로 포함시킨다.(출처 밝힘) messageHelper에서 꺼냄
		content = content.replace("\n", "<br>");		// 엔터키 처리 (<br> <- /이거 뺀이유는 버전이 낮은 브라우저 때문에 안씀)
		content += "<br><hr><h3>GreenStudy에서 보냅니다.</h3><hr><br>";
		content += "<p><img src =\"cid:main.png\" width = '500px' /></p>";					// cid 변수 <- 경로 따로 주기
		content += "<p>방문하기: <a href = 'https://49.142.157.251:9090/cjgreen'>그린아카데미</a></p>";
		content += "<hr>";

		messageHelper.setText(content, true);		// 사이 넣음 , true <- 위의 내용을 수정해줘
		
		//FileSystemResource file = new FileSystemResource("D:\\greenStudy\\springFramework\\works\\greenStudyS\\src\\main\\webapp\\resources\\images\\main.png");  // alt+ enter  (이거는 브라우저가 살아있을 때만 가능 (상대한테 안보임))
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.png")); 
		messageHelper.addInline("main.png", file);		// <- cid를 변수에 경로를 넣어준다.
		
		// 첨부파일 보내기
		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/11.jpg"));
		messageHelper.addInline("11.jpg", file);			// 수정 내용 넣기(cid 필요)

		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/12.jpg"));
		messageHelper.addAttachment("12.jpg", file);	// 수정 내용 넣기
		
		mailSender.send(message);	// 메세지 보내기
		
		
		return "redirect:/message/mailSendOk";
	}
	
	@PostMapping("/mailFormOk")
	public String mailFormOkPost(MailVO vo) {
		// 체크 사항 충분하게 체크한다.
		// 체크 완료 후 메일 서비스 호툴한다.
		/*projectProvide.mailSend("dwg5057@naver.com", "비밀번호 변경 확인", "1234") {*/
		// 서비스일 경우 try 사용
		
		int res = 0;
		
		try {
			projectProvide.mailSend(vo.getToMail(), vo.getTitle(), "매일연습");
			res = 1;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	  
		if(res !=0) return "redirect:/message/mailSendOk";
		else return "redirect:/message/mailSendNo";
		
	}
	
//비밀번호 암호화
	@GetMapping("/passForm")
	public String passFormGet() {
		return "study/password/passwordForm";
	}
	
	// sha-256 비밀번호 암호화
	@ResponseBody
	@PostMapping( value = "/passwordSha256", produces = "application/text; charset=utf-8")
	public String passwordSha256Post(String pwd) {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		SecurityUtil security = new SecurityUtil();
		String encPwd = security.encryptSHA256(pwd);
		
		pwd = "salt키: " + salt + "/ 암호화된 비밀번호 : " + encPwd;

		return pwd;

	}
	
	// aria 비밀번호 암호화
	@ResponseBody
	@PostMapping( value = "/passwordAria", produces = "application/text; charset=utf-8")
	public String passwordAriaPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		UUID uid = UUID.randomUUID();
		String salt = uid.toString().substring(0,8);
		
		String encPwd = ARIAUtil.ariaEncrypt(salt + pwd);
		String decPwd = ARIAUtil.ariaDecrypt(encPwd);

		
		pwd = "salt키: " + salt + "/ 암호화된 비밀번호 : " + encPwd + 
				"<br/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;" + decPwd.substring(8);
		
		
		return pwd;
		
	}
	
	// BCryptPasswordEncoder 비밀번호 암호화
	@ResponseBody
	@PostMapping( value = "/passwordEncoder", produces = "application/text; charset=utf-8")
	public String passwordEncoderPost(String pwd) throws InvalidKeyException, UnsupportedEncodingException {
		String encPwd = passwordEncoder.encode(pwd);
		
		
		pwd = " 암호화된 비밀번호 : " + encPwd;
		
		return pwd;
		
	}
	
	@ResponseBody
	@PostMapping("/emailList")
	public List<MemberVO> emailListPost(Model model, MemberVO vo) {
		List<MemberVO> vos = studyService.getEmailList();
		return vos;
	}
	

	
	
	
}
