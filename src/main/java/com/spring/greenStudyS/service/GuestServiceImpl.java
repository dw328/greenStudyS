package com.spring.greenStudyS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.greenStudyS.dao.GuestDAO;
import com.spring.greenStudyS.vo.GuestVO;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	GuestDAO guestDAO;
	
	@Override
	public List<GuestVO> getGuestList(int startIndexNo, int pageSize) {
		return guestDAO.getGuestList(startIndexNo, pageSize);
	}

	@Override
	public int setGuestInput(GuestVO vo) {
		return guestDAO.guestInput(vo);
	}

	@Override
	public int setGuestDelete(int idx) {
		return guestDAO.setGuestDelete(idx);
	}

	@Override
	public GuestVO setGuestUpdate(int idx) {
		return guestDAO.setGuestUpdate(idx);
	}

	@Override
	public int setGuestUpdateOK(GuestVO vo, int idx) {
		return guestDAO.setGuestUpdateOK( vo, idx);
	}

	@Override
	public int getTotRecCnt() {
		return guestDAO.getTotRecCnt();
	}

}
