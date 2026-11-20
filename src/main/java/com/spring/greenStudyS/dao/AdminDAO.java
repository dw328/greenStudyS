package com.spring.greenStudyS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.greenStudyS.vo.BoardVO;
import com.spring.greenStudyS.vo.MemberVO;

public interface AdminDAO {

	void setMemberLevelSelectCheck(@Param("idx") int idx,@Param("levelItem") int levelItem);

	int levelChangeUpdate(@Param("idx")int idx,@Param("level") int level);

	List<MemberVO> searchLevel(@Param("level")int level);

	List<MemberVO> getMemberList(@Param("startIndexNo")int startIndexNo, @Param("pageSize")int pageSize);

	int setMemerDeleteOk(@Param("idx") int idx);

	List<MemberVO> getuserCreateList();

	List<MemberVO> getuserDelList();

	int setBoardUpdateDelList(@Param("idx") int idx);

	BoardVO getBoardContent(@Param("idx") int idx);

}
