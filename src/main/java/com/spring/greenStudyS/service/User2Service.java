package com.spring.greenStudyS.service;

import java.util.List;

import com.spring.greenStudyS.vo.User2VO;

public interface User2Service {


	List<User2VO> getUser2List();

	void setUser2Join(User2VO vo);

	int setUserInput(User2VO vo);



}
