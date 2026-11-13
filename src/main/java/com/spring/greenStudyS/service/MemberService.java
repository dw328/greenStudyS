package com.spring.greenStudyS.service;

import java.util.List;

import com.spring.greenStudyS.vo.MemberVO;

public interface MemberService {

	int setMemberJoin(MemberVO vo);

	MemberVO memberIdCheck(String mid);

	MemberVO memberNickCheck(String nickName);

	void setmemberinforUpdate(MemberVO vo);

	List<MemberVO> getMemberList(int startIndexNo, int pageSize);

	int setMemberPwdChangeOk(String mid, String pwd);

	int setMemberUpdateOk(MemberVO vo);

	int setUserDel(String mid);


}
