package com.spring.greenStudyS.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.spring.greenStudyS.vo.MemberVO;
import com.spring.greenStudyS.vo.User2VO;

public interface StudyService {

	String[] getCityStringArray(String dodo);		// 구현객체

	List<String> getCityArrayList(String dodo);

	List<String> userMidList();

	User2VO userMidSearch(String mid);

	List<User2VO> userGenderSearch();

	List<User2VO> userGenderList(String gender);

	User2VO midExactCheck(String mid);

	List<User2VO> midPartCheck(String mid);

	int fileUpload(MultipartFile fName, String name, HttpServletRequest request);

	List<MemberVO> getEmailList();




}
