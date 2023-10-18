package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.BookVO;

//서비스 interface : 비즈니스 로직
public interface Bookservice {
	//메소드 시그니처
	//책 등록하기
	public int insert(BookVO bookVO);

	//책 상세 보기
	public BookVO detail(BookVO bookVO);
	
	//책 목록
	List<BookVO> list(String keyword);
	
	//책 수정
	int updatePost(BookVO bookVO);
	
	//삭제
	int deletePost(BookVO bookVO);

}
