package com.spring.greenStudyS.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.greenStudyS.common.ProjectProvide;
import com.spring.greenStudyS.dao.BoardDAO;
import com.spring.greenStudyS.vo.BoardReplyVO;
import com.spring.greenStudyS.vo.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	ProjectProvide projectProvide;

	@Override
	public List<BoardVO> getBoardList(int startIndexNo, int pageSize) {
		return boardDAO.getBoardList(startIndexNo, pageSize);
	}

	@Override
	public int setBoardInput(BoardVO vo) {
		return boardDAO.setBoardInput(vo);
	}

	@Override
	public BoardVO getBoardContent(int idx) {
		return boardDAO.getBoardContent(idx);
	}

	@Override
	public void imageCheck(String content) {
		//									 1				 2
		//         012345678901234567890123456
		// <p><img src="/greenStudyS/ckImage/261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>
		// <p><img src="/greenStudyS/board/261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>
		// 
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		
		int position = 26;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);		// 261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>...
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));		// 261116164347_Gemini.png
			
			String origeFilePath = realPath + "ckImage/" + imgFile;
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(origeFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
			
		}
		
	}

	// 파일 복사처리 (ckImage -> board)
	private void fileCopyCheck(String origeFilePath, String copyFilePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(origeFilePath));
			FileOutputStream fos = new  FileOutputStream(new File(copyFilePath));
			
			byte[] bytes = new byte[2048];	
			
			while(fis.read(bytes) != -1) {		// 2048 아닐때까지(꽉차지 않을 떄) 
				fos.write(bytes);
			}
			fos.flush();
			fos.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int setBoardDelete(int idx) {
		return boardDAO.setBoardDelete(idx);
	}

	@Override
	public void imageDelete(String content) {
		//									 1				 2				 3				 4
		//         01234567890123456789012345678901234567890123456
		// <p><img src="/greenStudyS/board/261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>
		// <p><img src="/greenStudyS/ckImage/261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>
		// 
				
		int position = 24;
		String nextImg = content.substring(content.indexOf("src=\"/") + position);		
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));		// 261116164347_Gemini.png
			
			projectProvide.deleteFile(imgFile, "board");
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);			
		}
	}

	
	

	@Override
	public int setBoardUpdate(int idx, BoardVO vo) {
		return boardDAO.setBoardUpdate(idx, vo);
	}
	
	@Override
	public void imageUpdateCoty(String content, String sContent) {
		//	 								 1				 2				 3
		//         0123456789012345678901234567890123456789
		// <p><img src="/greenStudyS/ckImage/261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>
		// <p><img src="/greenStudyS/board/261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>
		// 
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/");
		System.out.println("sContent: " + sContent);
		
		if(sContent == null || sContent.equals("")) return;
		
		int position = 24;
		content = content.replace("/ckImage", "/board");
		sContent = sContent.replace("/ckImage", "/board");
		
		String nextImg = content.substring(content.indexOf("src=\"/") + position);		// 261116164347_Gemini.png" style="height:1536px; width:2752px" /></p>...
		sContent = sContent.substring(sContent.indexOf("src=\"/") + position);
		boolean sw = true;
		
		while(sw) {
			String imgFile = nextImg.substring(0,nextImg.indexOf("\""));		// 261116164347_Gemini.png
			
			String origeFilePath = realPath + "ckImage/" + imgFile;
			String copyFilePath = realPath + "board/" + imgFile;
			
			fileCopyCheck(origeFilePath, copyFilePath);
			
			if(nextImg.indexOf("src=\"/") == -1) sw = false;
			else nextImg = nextImg.substring(nextImg.indexOf("src=\"/") + position);
		}
		
		sw = true;
		while(sw) {
			sw = true;
			while(sw) {
				String sImgFile = sContent.split("\"")[0];

				if(!content.contains(sImgFile)) {
					projectProvide.deleteFile(sImgFile, "board");
				}

				if(sContent.indexOf("src=\"/") == -1) sw = false;
				else sContent = sContent.substring(sContent.indexOf("src=\"/") + position);
			}
		}
		
	}

	@Override
	public void setReadNumPlus(int idx) {
		boardDAO.setReadNumPlus(idx);
	}

	@Override
	public void setBoardGoodCheck(int idx, int sw) {
		boardDAO.setBoardGoodCheck(idx, sw);
		
	}

	@Override
	public BoardVO getPreNextSearch(int idx, String str) {
		return boardDAO.getPreNextSearch(idx, str);
	}

	@Override
	public BoardReplyVO getBoardParentReplyCheck(int boardIdx) {
		return boardDAO.getBoardParentReplyCheck(boardIdx);
	}

	@Override
	public int setBoardReplyInput(BoardReplyVO replyVO) {
		return boardDAO.setBoardReplyInput(replyVO);
	}

	@Override
	public List<BoardReplyVO> getBoardReply(int idx) {
		return boardDAO.getBoardReply(idx);
	}

	@Override
	public void setReplyOrderUpdate(int boardIdx, int re_order) {
		boardDAO.setReplyOrderUpdate(boardIdx, re_order);
	}






	
	
}
