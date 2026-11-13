package com.spring.greenStudyS.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.spring.greenStudyS.dao.StudyUserDAO;
import com.spring.greenStudyS.vo.MemberVO;
import com.spring.greenStudyS.vo.User2VO;

@Service	// bean에 올라가고 + 인터페이스도 같이 가져간다.
public class StudyServiceImpl implements StudyService {

	@Autowired 
	StudyUserDAO studyUserDAO;
	
	@Override
	public String[] getCityStringArray(String dodo) {
		String[] strArray = new String[100];

		if(dodo.equals("서울")) {
			strArray[0] = "강남구";
			strArray[1] = "강북구";
			strArray[2] = "동작구";
			strArray[3] = "관악구";
			strArray[4] = "서대문구";
			strArray[5] = "종로구";
			strArray[6] = "영등포구";
			strArray[7] = "마포구";
			strArray[8] = "은평구";
			strArray[9] = "금천구";
		}
		else if(dodo.equals("경기")) {
			strArray[0] = "수원시";
			strArray[1] = "안양시";
			strArray[2] = "안성시";
			strArray[3] = "평택시";
			strArray[4] = "용인시";
			strArray[5] = "의정부시";
			strArray[6] = "양주시";
			strArray[7] = "김포시";
			strArray[8] = "부천시";
			strArray[9] = "안산시";
		}
		else if(dodo.equals("충북")) {
			strArray[0] = "청주시";
			strArray[1] = "충주시";
			strArray[2] = "제천시";
			strArray[3] = "단양군";
			strArray[4] = "음성군";
			strArray[5] = "옥천군";
			strArray[6] = "진천군";
			strArray[7] = "증평군";
			strArray[8] = "괴산군";
			strArray[9] = "영동군";
		}
		else if(dodo.equals("충남")) {
			strArray[0] = "천안시";
			strArray[1] = "아산시";
			strArray[2] = "논산시";
			strArray[3] = "계룡시";
			strArray[4] = "청양군";
			strArray[5] = "공주시";
			strArray[6] = "예산시";
			strArray[7] = "서산시";
			strArray[8] = "홍성군";
			strArray[9] = "부여시";
		}
		
		return strArray;
	}

	@Override
	public List<String> getCityArrayList(String dodo) {
		List<String> vos = new ArrayList<String>();
		
		if(dodo.equals("서울")) {
			vos.add("강남구");
			vos.add("강북구");
			vos.add("동작구");
			vos.add("관악구");
			vos.add("종로구");
			vos.add("마포구");
			vos.add("은평구");
			vos.add("금천구");
			vos.add("서대문구");
			vos.add("영등포구");
		}
		else if(dodo.equals("경기")) {
			vos.add("수원시");
			vos.add("안양시");
			vos.add("안성시");
			vos.add("평택시");
			vos.add("용인시");
			vos.add("양주시");
			vos.add("김포시");
			vos.add("부천시");
			vos.add("안산시");
			vos.add("의정부시");
		}
		else if(dodo.equals("충북")) {
			vos.add("청주시");
			vos.add("충주시");
			vos.add("제천시");
			vos.add("단양군");
			vos.add("음성군");
			vos.add("옥천군");
			vos.add("진천군");
			vos.add("증평군");
			vos.add("괴산군");
			vos.add("영동군");
		}
		else if(dodo.equals("충남")) {
			vos.add("천안시");
			vos.add("아산시");
			vos.add("논산시");
			vos.add("계룡시");
			vos.add("청양군");
			vos.add("공주시");
			vos.add("예산시");
			vos.add("서산시");
			vos.add("홍성군");
			vos.add("부여시");
		}
		return vos;
	}

	@Override
	public List<String> userMidList() {
		return studyUserDAO.userMidList();
	}

	@Override
	public User2VO userMidSearch(String mid) {
		return studyUserDAO.userMidSearch(mid);
	}

	@Override
	public List<User2VO> userGenderSearch() {
		return studyUserDAO.userGenderSearch();
	}

	@Override
	public List<User2VO> userGenderList(String gender) {
		return studyUserDAO.userGenderList(gender);
	}

	@Override
	public User2VO midExactCheck(String mid) {
		return studyUserDAO.midExactCheck(mid);
	}

	@Override
	public List<User2VO> midPartCheck(String mid) {
		return studyUserDAO.midPartCheck(mid);
	}

//	@Override
//	public int fileUpload(MultipartFile fName, String name) {
//		int res = 0;
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();  // Request 이거 쓰겠다고 에휴...
//		
//		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
//		String uid = UUID.randomUUID().toString().substring(0,8);
//		String oFileName = fName.getOriginalFilename();
//		String file = oFileName.substring(0, oFileName.lastIndexOf(".")) + "_" + uid + oFileName.substring(oFileName.lastIndexOf("."));
//		
//		System.out.println("file: " + file);
//		
//		FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream(realPath+file);  	// 저장
//			fos.write(fName.getBytes());
//			res = 1;
//		} 
//		catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				fos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return res;
//	}
//	
	
	
	@Override
	public int fileUpload(MultipartFile fName, String name, HttpServletRequest request) {
		int res = 0;
		
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/fileUpload/");
		String uid = UUID.randomUUID().toString().substring(0,8);
		String oFileName = fName.getOriginalFilename();
		String file = oFileName.substring(0, oFileName.lastIndexOf(".")) + "_" + uid + oFileName.substring(oFileName.lastIndexOf("."));
		
		System.out.println("file: " + file);
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(realPath+file);  	// 저장
			if(fName.getBytes().length != -1) {
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
		
		return res;
	}

	@Override
	public List<MemberVO> getEmailList() {
		return studyUserDAO.getEmailList();
	}



 

}
