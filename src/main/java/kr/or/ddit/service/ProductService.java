package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.ProductVO;

public interface ProductService {
	//메소드 시그니처 
	//상품등록
	public int processAddProduct(ProductVO productVO);
	
	//모든 상품 목록
	List<ProductVO> products();
	
	//상품 상세보기
	ProductVO product(String productId);
	
	
	
	
		
}
