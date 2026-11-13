package com.spring.greenStudyS.service;

import java.util.List;

import com.spring.greenStudyS.vo.MemberVO;

public interface AdminService {

	void setMemberLevelSelectCheck(int idx, int levelItem);

	int levelChangeUpdate(int idx, int level);

	List<MemberVO> searchLevel(int level);

	List<MemberVO> getMemberList(int startIndexNo, int pageSize);

	int setMemerDeleteOk(int idx);


}
