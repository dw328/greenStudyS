package com.spring.greenStudyS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.greenStudyS.vo.User2VO;

public interface User2DAO {

	List<User2VO> getUser2List();

	/*
	 * void setUser2Join(@Param("vo") User2VO vo); // param의 vo을 vo로 넣어서 넘김
	 */
	void setUser2Join(User2VO vo); // param의 vo을 vo로 넣어서 넘김

	int setUserInput(@Param("vo") User2VO vo);		// 내가 가져온 변수를 mapper에 할당하게 해준다.
}
