package kr.or.ddit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.service.ItemService;
import kr.or.ddit.vo.Item3VO;
import kr.or.ddit.vo.ItemVO;
import lombok.extern.slf4j.Slf4j;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Slf4j
@RequestMapping("/item")
@Controller
public class ItemController {
	//DI 의존성 IOC
	@Autowired
	ItemService itemService;
	
	@GetMapping("/itemRegist")
	public String itemRegist() {
		
		//forwarding
		return "item/itemRegist";
	}
	/*
	   요청URI : /item/registPost
	   요청파라미터 : {itemName=태블릿&price=12000&description=설명&pictures=파일객체}
	   요청방식 : post
	 */
	@ResponseBody
	@PostMapping("/registPost")
	public String registPost(ItemVO itemVO) {
		/*
		 - itemVO : ItemVO(itemId=0, itemName=에어팟, price=300, description=<p>ㅇㄹ</p>
		, pictureUrl=null, pictures=org.springframework...
		*/
		log.info("itemVO : " + itemVO);
		
		int result = this.itemService.itemRegist(itemVO);
		log.info("registPost->result : "+ result); // 1행
				
		return "SUCCESS";
	}
	
	@GetMapping("/itemMultiRegist")
	public String itemMultiRegist() {
		
		//forwarding
		return "item/itemMultiRegist";
	}
	
	/*
	   요청URI : /item/registMultiPost
	   요청파라미터 : {itemName=태블릿&price=12000&description=설명&pictures=파일객체들}
	   요청방식 : post
	 */
	//다중 insert
	@ResponseBody
	@PostMapping("registMultiPost")
	public String registMultiPost(Item3VO item3VO) {
		log.info("item3VO : " + item3VO);
		
		int result = this.itemService.registMultiPost(item3VO);
		log.info("registMultiPost->result : "+result);
		
		//뷰경로가 아닌 데이터
		return "SUCCESS";
		
	}
	
}
