package com.spring.greenStudyS.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	
	@RequestMapping("/imageUpload")
	public void imageUploadGet(MultipartFile upload,
			HttpServletRequest request,		// 여기서 이미 input을 함
			HttpServletResponse response  // 화면에 보여줌 
		) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String realPath =  request.getSession().getServletContext().getRealPath("/resources/data/ckImage/");
		String oFileName = upload.getOriginalFilename();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		oFileName = sdf.format(date) + "_" + oFileName;
		
		byte[] bytes = upload.getBytes(); 		// 올림(바이트s로 들어와서 바이트로 만듬)  
		FileOutputStream fos = new FileOutputStream(new File(realPath+oFileName)); // input 가지고(빼고) 옴 output (넣는) 함수
		fos.write(bytes);	// byte 단위로 들어감
		
		// 이거 때문에 response 쓰는듯~? 화면에 이미지 보내기 
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/ckImage/" + oFileName;
		out.println("{\"uploaded\":1,\"url\":\""+fileUrl+"\",\"originalFilename\":\""+oFileName+"\"}");
		// 업로드 작업 할것이다.(1) \ 경로 
		out.flush(); 		// 남은 byte 가져와라
		out.close();
		fos.close();
		
	}
	
	@RequestMapping(value = "/fileDownAction", method = RequestMethod.GET)
	public void fileDownActionGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = request.getParameter("path");
		String file = request.getParameter("file");
		
		if(path.equals("pds")) path += "/temp/";
		
		String realPathFile = request.getSession().getServletContext().getRealPath("/resources/data/" + path) + file;
		
		File downFile = new File(realPathFile);
		String downFileName = new String(file.getBytes("UTF-8"), "8859_1");		// 8859_1 옛날에 사용할 때 크롬보다 인터넷익스프로우에서 표준 사용안함...그래서 사용
		response.setHeader("Content-Disposition", "attachment;filename=" + downFileName);
		
		FileInputStream fis = new FileInputStream(downFile);
		ServletOutputStream sos = response.getOutputStream();
		
		byte[] bytes = new byte[2048];
		int data;
		while((data = fis.read(bytes, 0, bytes.length)) != -1) {
			sos.write(bytes, 0, data);
		}
		sos.flush();
		sos.close();
		fis.close();
		
		// downFile.delete();
	}
	
	
}
