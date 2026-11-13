package com.spring.greenStudyS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.greenStudyS.vo.GuestVO;


public interface GuestDAO {

	// List<GuestVO> getGuestList();
	List<GuestVO> getGuestList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	int guestInput(@Param("vo") GuestVO vo);

	int setGuestDelete(int idx);

	GuestVO setGuestUpdate(int idx);

	int setGuestUpdateOK(@Param("vo") GuestVO vo, @Param("idx") int idx);

	int getTotRecCnt();


}
