package kr.or.ddit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.ProductDao;
import kr.or.ddit.service.ProductService;
import kr.or.ddit.vo.ProductVO;

//스프링 -> 이클래스는 서버스 클래스 라고 알려주면 
@Service
public class ProductServiceImpl implements ProductService{
	//DI(의존성 주입), IoC(제어의 역전)
	@Autowired
	ProductDao productDao;
	
	//상품 등록
	@Override
	public int processAddProduct(ProductVO productVO) {
		return productDao.processAddProduct(productVO);
	}
	//상품 목록
	@Override
	public List<ProductVO> products() {
		return productDao.products();
	}
	//상품 상세보기
	@Override
	public ProductVO product(String productId) {
		return productDao.product(productId);
	}

}
