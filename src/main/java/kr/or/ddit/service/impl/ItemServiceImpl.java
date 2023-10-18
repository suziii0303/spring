package kr.or.ddit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.mapper.ItemMapper;
import kr.or.ddit.service.ItemService;
import kr.or.ddit.util.FileUploadUtils;
import kr.or.ddit.vo.Item3VO;
import kr.or.ddit.vo.ItemAttachVO;
import kr.or.ddit.vo.ItemVO;
import lombok.Builder.ObtainVia;

@Service
public class ItemServiceImpl implements ItemService {

	// DI, IoC
	@Autowired
	ItemMapper itemMapper;

	// 아이템 등록
	@Override
	public int itemRegist(ItemVO itemVO) {
		/*
		 * itemVO : ItemVO(itemId=0, itemName=태블릿, price=12000, description=<p>내용글</p> ,
		 * pictureUrl=null, pictures=org.springframework....
		 */
		// MultipartFile : 스프링 파일객체 인터페이스
		MultipartFile picture = itemVO.getPictures();

		// 파일업로드
		String pictureUrl = FileUploadUtils.singleUpload(picture);

		// 파일객체를 던지면 연월일+파일명 리턴
		itemVO.setPictureUrl(pictureUrl);

		// 아이템 insert
		/*
		 * itemVO : ItemVO(itemId=0, itemName=태블릿, price=12000, description=<p>내용글</p> ,
		 * pictureUrl=/2023/08/10/asdfafsd_개똥이.jpg, pictures=org.springframework....
		 */
		int result = this.itemMapper.itemRegist(itemVO);

		return result;
	}
	//아이템 등록 + 다중 파일 등록
	//요청파라미터 : {itemName=태블릿&price=12000&description=설명
	//				&pictures=파일객체들}
	//Transactional : 클래스나 메서드의 스프링의 트랜잭션 처리를 적용
	@Transactional
	@Override
	public int registMultiPost(Item3VO item3VO) {
		//1) item3VO -> ITEM3 테이블에 insert
		//처음item3VO[itemId=0,itemName=태블릿&price=12000&description=설명&pictures=파일객체들]
		int result = this.itemMapper.registMultiPost(item3VO);
		//selectKey후item3VO[itemId=1,itemName=태블릿&price=12000&description=설명&pictures=파일객체들]
		
		MultipartFile[] pictures = item3VO.getPictures();
		
		//파일업로드
		/*
		itemAttachVOList[
			itemAttachVO[fullname=2023/08/10/asdfjl_개똥이.jpg,itemId=0,regdate=null]
			itemAttachVO[fullname=2023/08/10/erwqeq_박명수.jpg,itemId=0,regdate=null]
			itemAttachVO[fullname=2023/08/10/kjyuig_홍길동.jpg,itemId=0,regdate=null]
		]
		*/
		List<ItemAttachVO> itemAttachVOList 
			= FileUploadUtils.multiUpload(pictures);
		
		//ITEM_ATTACH테이블을 위한itemId=0을 ITEM3테이블에 inset된 itemId값으로 보정
		for(ItemAttachVO vo : itemAttachVOList) {
			vo.setItemId(item3VO.getItemId());
		}
		
		item3VO.setItemAttachVOList(itemAttachVOList);
		
		//2) ItemAttach(fullname, item_id, regdate) -> ITEM_ATTACH 테이블에 insert
		//public int registMultiAttach(List<ItemAttachVO> itemAttachVOList);
		result += this.itemMapper.registMultiAttach(itemAttachVOList);
		
		return result;
	}

}
