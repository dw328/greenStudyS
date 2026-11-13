package com.spring.greenStudyS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.greenStudyS.dao.UserDAO;
import com.spring.greenStudyS.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;

	@Override
	public List<UserVO> getUserList(UserVO vo) {
		return userDAO.getUserList(vo);
		// root-context에서의 sqlsession을 이용한 것이다(하지만 지금은 잘 안씀.)
	}

	@Override
	public List<UserVO> getUserSearch(String mid) {
		return userDAO.getUserSearch(mid);
	}

	@Override
	public int setUserDelete(int idx) {
		return userDAO.setUserDelete(idx);
	}

	@Override
	public void setUserJoin(UserVO vo) {
		userDAO.setUserJoin(vo);
	}
	
	


	
	
	
}
