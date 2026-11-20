package com.spring.greenStudyS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.greenStudyS.vo.PdsVO;

public interface PdsDAO {

	int getTotRecCnt(@Param("part") String part);

	List<PdsVO> getPdsList(@Param("part")String part, @Param("startIndexNo")int startIndexNo,@Param("pageSize") int pageSize);

	int setPdsInput(@Param("vo") PdsVO vo);

	int setPdsDownNumCheck(@Param("idx")int idx);

	int setPdsDeleteCheck(@Param("idx")int idx);

	PdsVO getPdsContent(@Param("idx") int idx);

	

}
