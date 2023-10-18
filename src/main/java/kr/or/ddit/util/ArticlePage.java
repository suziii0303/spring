package kr.or.ddit.util;

import java.util.List;

//페이징 관련 정보 + 게시글 데이터
public class ArticlePage<T> {
	// 전체글 수
	public int total; // 164 // 178
	// 현재 페이지 번호
	private int currentPage;
	// 전체 페이지 수
	private int totalPages;
	// 시작 페이지 번호
	private int startPage;
	// 한 화면에 보여질 행의 수
	private int size;//기본은 10 행 
	// 종료 페이지 번호
	private int endPage;
	// 데이터
	private List<T> content;

	// 생성자 (Constructor) : 페이징 정보를 생성
	public ArticlePage(int total, int currentPage, int size, List<T> content) {
		// size : 한 화면에 보여질 목록의 행 수
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		this.size = size;

		// 전체글 수가 0이면?
		if (total == 0) {
			totalPages = 0; // [1][2][3]..[14]
			startPage = 0;
			endPage = 0;
		} else {// 글이 있다면
				// 전체글 수 /한 화면에 보여질 목록의 행 수(size) => 전체 페이지 수
			totalPages = this.total / size;

			// 전체글 수(total) % 한 화면에 보여질 목록의 행 수(size)
			// =>0이 아니면(137행). 나머지가 잇다면, 페이지를 1증가
			if (total % size > 0) {
					this.totalPages++;
			}
			//시작페이지를 구하는 공식!
			//시작페이지 = 현재페이지  / 페이지크기 * 페이지크기 +1
			//			6	= 7 /
			//			[6][7][8][9][10]
			startPage = currentPage / 5 * 5 +1;
			
			//현재페이지 % 페이지크기 => 0일 때 보정
			if(currentPage % 5 ==0) {
				//페이지 크기를 빼줌
				startPage -= 5;
			}
			// 종료페이지번호 = 시작페이지번호 + (페이지크기 -1)
			//10
			endPage = startPage +(5-1);
			
			//종료페이지 번호 > 전체페이지수보다 클 때 
			if(endPage > totalPages) {
				endPage = totalPages;
			}
			
		}// end if
	}// end constructor

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
	
	//데이터가 없으면 ? 전체 글의 수는 0 => true리턴
	public boolean hasNoArticles() {
		return total == 0;
	}
	//데이터가 있으면 ? true를 리턴
	public boolean hasArticles() {
		return total > 0;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
