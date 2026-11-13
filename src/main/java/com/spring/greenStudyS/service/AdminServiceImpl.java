package com.spring.greenStudyS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.greenStudyS.dao.AdminDAO;
import com.spring.greenStudyS.vo.MemberVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO adminDAO;

	@Override
	public void setMemberLevelSelectCheck(int idx, int levelItem) {
		adminDAO.setMemberLevelSelectCheck(idx, levelItem);
	}

	@Override
	public int levelChangeUpdate(int idx, int level) {
		return adminDAO.levelChangeUpdate(idx, level);
	}

	@Override
	public List<MemberVO> searchLevel(int level) {
		return adminDAO.searchLevel(level);
	}

	@Override
	public List<MemberVO> getMemberList(int startIndexNo, int pageSize) {
		return adminDAO.getMemberList(startIndexNo, pageSize);
	}

	@Override
	public int setMemerDeleteOk(int idx) {
		return adminDAO.setMemerDeleteOk(idx);
	}
}
