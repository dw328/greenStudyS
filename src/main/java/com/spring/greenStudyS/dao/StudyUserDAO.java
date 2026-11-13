package com.spring.greenStudyS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.greenStudyS.vo.MemberVO;
import com.spring.greenStudyS.vo.User2VO;

public interface StudyUserDAO {

	List<String> userMidList();

	User2VO userMidSearch(@Param("mid") String mid);

	List<User2VO> userGenderSearch();

	List<User2VO> userGenderList(@Param("gender") String gender);

	User2VO midExactCheck(@Param("mid") String mid);

	List<User2VO> midPartCheck(@Param("mid") String mid);

	List<MemberVO> getEmailList();



}
