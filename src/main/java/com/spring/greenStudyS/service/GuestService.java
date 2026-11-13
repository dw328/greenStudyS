package com.spring.greenStudyS.service;

import java.util.List;

import com.spring.greenStudyS.vo.GuestVO;

public interface GuestService {

//	List<GuestVO> getGuestList();
	List<GuestVO> getGuestList(int startIndexNo, int pageSize);

	int setGuestInput(GuestVO vo);

	int setGuestDelete(int idx);

	GuestVO setGuestUpdate(int idx);

	int setGuestUpdateOK(GuestVO vo, int idx);

	int getTotRecCnt();


}
