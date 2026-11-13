package com.spring.greenStudyS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.greenStudyS.dao.User2DAO;
import com.spring.greenStudyS.vo.User2VO;

@Service			// 너가 구현한다고 올림.
public class User2ServiceImpl implements User2Service {

	@Autowired
	User2DAO user2DAO;


	@Override
	public List<User2VO> getUser2List() {
		return user2DAO.getUser2List();
	}


	@Override
	public void setUser2Join(User2VO vo) {
		user2DAO.setUser2Join(vo);
		
	}


	@Override
	public int setUserInput(User2VO vo) {
		return user2DAO.setUserInput(vo);
	}


}
