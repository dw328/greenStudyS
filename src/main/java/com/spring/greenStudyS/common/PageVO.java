package com.spring.greenStudyS.common;

import lombok.Data;

@Data
public class PageVO {
	 private int pag;
	 private int pageSize;
	 private int totRecCnt;
	 private int totPage;
	 private int startIndexNo;
	 private int curScrStartNo;
	 private int blockSize;
	 private int curBlock;
	 private int lastBlock;
	 
	 private String search;  				// 검색필드명
	 private String searchString;		// 검색어
	 private String searchStr;			// java -> 뷰로 보넬 때(ex. title -> '타이틀')
	 
	 private String part; 					// PDS 자료 폴더 나누는 필드
	 private String section;				// 소속(방명록:guest, 회원:member, 게시판: board, 자료실: pds, 포토갤러리:photo, 일정관리: schedule)
}
