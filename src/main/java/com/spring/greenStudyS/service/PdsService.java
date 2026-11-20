package com.spring.greenStudyS.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.greenStudyS.vo.PdsVO;

public interface PdsService {

	List<PdsVO> getPdsList(String part, int startIndexNo, int pageSize);

	int setPdsInput(MultipartHttpServletRequest mFile, PdsVO vo);

	int setPdsDownNumCheck(int idx);

	int setPdsDeleteCheck(int idx, String fSName);

	PdsVO getPdsContent(int idx);

}
