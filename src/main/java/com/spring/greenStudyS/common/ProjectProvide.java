package com.spring.greenStudyS.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.greenStudyS.vo.MailVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProjectProvide {
	
	@Autowired
	JavaMailSender mailSender;
	
	// ajax에서의 파일 처리
	public int fileUpload(MultipartFile fName, String path, String name) {
		/***********************이 부분은 request를 사용하기 위한 코드(HttpServletRequest을 지역번수로 사용하지 못해서 직접 만듬)****************************/
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		/********************************************************************************************************************************/
		
		int res = 0;
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+path+"/");
		String uid = UUID.randomUUID().toString().substring(0,8);
		String oFileName = fName.getOriginalFilename();
		String file = oFileName.substring(0, oFileName.lastIndexOf(".")) + "_" + uid + oFileName.substring(oFileName.lastIndexOf("."));
		
//		System.out.println("file: " + file);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(realPath+file);  	// 저장
			if(fName.getBytes().length != -1) {						// 실패 시 -1을 반환함(!을 사용해서 -1이 아닐 경우)
				fos.write(fName.getBytes());
				res = 1;
			}
		} 
		catch (IOException e) {
			System.out.println("파일 업로드 실패: " + e.getMessage());
		} finally {
			try {
				fos.close();
			} catch (IOException e) {}
		}
		
//		System.out.println("파일업드로 사용자:" + name);
		log.info("파일 업로드 사용자: {}" , name);
		return res;
	}
	
	// 일반 파일처리
	public String uploadFile(MultipartFile fName, String path, String name) {
		/***********************이 부분은 request를 사용하기 위한 코드(HttpServletRequest을 지역번수로 사용하지 못해서 직접 만듬)****************************/
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		/********************************************************************************************************************************/
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+path+"/");
		String uid = UUID.randomUUID().toString().substring(0,8);
		String oFileName = fName.getOriginalFilename();
		String file = oFileName.substring(0, oFileName.lastIndexOf(".")) + "_" + uid + oFileName.substring(oFileName.lastIndexOf("."));
		
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(realPath+file);  	// 저장
			if(fName.getBytes().length != -1) {						// 실패 시 -1을 반환함(!을 사용해서 -1이 아닐 경우)
				fos.write(fName.getBytes());
			}
		} 
		catch (IOException e) {
			System.out.println("파일 업로드 실패: " + e.getMessage());
		} finally {
			try {
				fos.close();
			} catch (IOException e) {}
		}
		
		return file;
	}
	
	
	
	public String mailSend(String email, String title, String flag) throws MessagingException {    // controller에서 생성하면 상관 없지만 서비스에서는 쓰는건 바람직하지 않는다.
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		// 메일 전송을 위한 객체: MimeMessage(): 그림?글? 확인(진짜 메일 보냄), MimMessageHelper(): (첨부파일, 메일 내용)바구니
		String content = "";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		
		// 메세지 보관함 정리(작업)
		messageHelper.setTo(email);
		messageHelper.setSubject(title);
		messageHelper.setText(content);
		
		// (예: 발신자 비번을 요청할 때
		// 스팸이 아닌걸 알리기 위해 발신자의 필요한 정보를 추가로 포함시킨다.(출처 밝힘) messageHelper에서 꺼냄 
		content = content.replace("\n", "<br>");		// 엔터키 처리 (<br> <- /이거 뺀이유는 버전이 낮은 브라우저 때문에 안씀)
		content += "<br><hr><h3> 임시비밀번호 : "+flag+" </h3><hr><br>";
		content += "<p><img src =\"cid:main.png\" width = '500px' /></p>";					// cid 변수 <- 경로 따로 주기
		content += "<p>방문하기: <a href = 'https://49.142.157.251:9090/cjgreen'>그린아카데미</a></p>";
		content += "<hr>";

		messageHelper.setText(content, true);		// 사이 넣음 , true <- 위의 내용을 수정해줘
		
		//FileSystemResource file = new FileSystemResource("D:\\greenStudy\\springFramework\\works\\greenStudyS\\src\\main\\webapp\\resources\\images\\main.png");  // alt+ enter  (이거는 브라우저가 살아있을 때만 가능 (상대한테 안보임))
		FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/main.png")); 
		messageHelper.addInline("main.png", file);		// <- cid를 변수에 경로를 넣어준다.
		
//		// 첨부파일 보내기
//		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/11.jpg"));
//		messageHelper.addInline("11.jpg", file);			// 수정 내용 넣기(cid 필요)
//
//		file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/resources/images/12.jpg"));
//		messageHelper.addAttachment("12.jpg", file);	// 수정 내용 넣기
//		
		mailSender.send(message);	// 메세지 보내기
		
		
		return "redirect:/message/mailSendOk";
	}

	// 파일 삭제처리
	public void deleteFile(String photo, String path) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+path+"/");
		File file = new File(realPath + photo);
		if(file.exists()) file.delete();
	}

	
	public void writeFile(MultipartFile file, String sFileName, String path) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/"+path+"/");
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(realPath+sFileName);  	// 저장
			if(file.getBytes().length != -1) {						// 실패 시 -1을 반환함(!을 사용해서 -1이 아닐 경우)
				fos.write(file.getBytes());
			}
		} 
		catch (IOException e) {
			System.out.println("파일 업로드 실패: " + e.getMessage());
		} finally {
			try {
				fos.close();
			} catch (IOException e) {}
		}
		
	}

}
