package com.spring.greenStudyS.vo;

import lombok.Data;

@Data
public class BoardVO {
	private int idx;
	private String mid;
	private String nickName;
	private String title;
	private String content;
	private int readNum;
	private String hostIp;
	private String openSw;
	private String wDate;
	private int good;
	private String complaint;
	
	// 위 쪽은 DB에 만들어진 필드를 만듬. , 아래는 내가 생성한 필드
	private int replyCnt;
	private int hour_diff;

}
