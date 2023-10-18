package kr.or.ddit.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.ProductVO;

//스프링, 이 클래스는 데이터를 관린하는 클래스
// 스프링 : 자바빈으로 등록해서 관리함
@Repository
public class ProductDao {
	//SqlSessionTemplate 타입 객체를 ProductDao 객체에 주입함
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	//상품 등록
	//<insert id="processAddProduct" parameterType="productVO">
	public int processAddProduct(ProductVO productVO) {
		//sql을 실행해주는 객체 : sqlSessionTemplate
		
		return sqlSessionTemplate.insert("product.processAddProduct",productVO);
	}
	
	//상품 목록
	//<select id="products"resultType="productVO">
	public List<ProductVO> products() {
		return sqlSessionTemplate.selectList("product.products");
	}
	
	//상품 상세보기
	//<select id="product" parameterType="String" resultType="productVO">
	public ProductVO product(String productId) {
		return sqlSessionTemplate.selectOne("product.product",productId);
	}
	//장바구니 삭제
}
