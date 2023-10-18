package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import kr.or.ddit.vo.AddressVO;
import kr.or.ddit.vo.MemberVO_backup;
import lombok.extern.slf4j.Slf4j;

//스프링이 자바빈으로 등록하여 관리함 
@Slf4j
@RequestMapping("/member")
@Controller
public class MemberController {
	//로그인 페이지로 포워딩
	//URI : /member/loginMember
	//요청방식: get
	@GetMapping("/loginMember")
	public String loginMember() {
		//forwarding
		return "member/loginMember";
	}
	
	//모델에 폼 객체를 추가하지 않으면 오류 발생
	//뷰에 전달할 데이터를 위해 모델을 매개변수로 지정
	@GetMapping("/registerForm01")
	public String registerForm01(Model model) {
		//모델의 속성명에 memberVO를 지정하고 폼 객체를 모델에 추가함
		model.addAttribute("memberVO",new MemberVO_backup());
		
		//forwarding
		return "member/registerForm01";
	}
	
	//모델의 속성명과  스프링 폼 태그의 modelAttribute 속성값이 일치해야 함
	@GetMapping("/registerForm02")
	public String registerForm02(Model model) {
		//모델의 속성명에 memberVO를 지정하고 폼 객체를 모델에 추가함
		//model.addAttribute("gaeddongi",new MemberVO()); //(X)
		model.addAttribute("memberVO",new MemberVO_backup()); //(O)

		
		//forwarding
		return "member/registerForm02";
	}
	//컨트롤레 메서드의 매개변수로 자바빈즈 객체가 전달이 되면
	// forwarding 시 뷰(registerForm.jsp)로 memberVO를 전달함
	//컨트롤러는 자바빈즈 규칙에 맞는 객체를 뷰로 객체를 전달함
	@GetMapping("/registerForm05")
	public String registerForm05(MemberVO_backup memberVO) {
		//폼 객체의 속성명은 직접 지정하지 않으면 : 골뱅이ModelAttribute("속성명")를 생략
		//폼 객체의 클래스명의 맨 처음 문자를
		//소문자로 변화하여 처리함
		
		//forwarding
		return "member/registerForm05";
	}
	
	//골뱅이ModelAttribute 애너테이션으로 폼 객체의 속성명을 직접 지정할 수 있음
	@GetMapping("/registerForm06")
	public String registerForm06(@ModelAttribute("user") MemberVO_backup memberVO,
			Model model) {
		memberVO.setUserId("gaeddongi");
		memberVO.setUserName("개똥이");
		memberVO.setCoin(100);
		
		model.addAttribute("memberVO",memberVO);
		//forwarding
		return "member/registerForm06";
	}
	@GetMapping("/registerForm07")
	public String registerForm07(@ModelAttribute("addressVO") AddressVO addressVO,
			Model model) {
		addressVO.setZonecode("1234");
		addressVO.setAddress("대전시 중구");
		addressVO.setBuildingName("오류동");
		
		model.addAttribute("addressVO",addressVO);
		//forwarding
		return "member/registerForm07";
	}
	@GetMapping("/registerForm08")
	public String registerForm08(@ModelAttribute("memberVO") MemberVO_backup memberVO,
			Model model) {
		memberVO.setUserId("gaeddongi");
		memberVO.setUserName("개똥이");
		//값을 setting을 하여 뷰에 전달해도 패스워드 필드에 반영되지 않음
		memberVO.setPassword("java");
		memberVO.setIntroduction("안녕하세요 \n 반가워요");
		
		//취미를 미리 체크
		String[] hobbys = {"sports","movie"};
		memberVO.setHobbys(hobbys);
		
		Map<String,String> hobbyMap = new HashMap<String,String>();
		hobbyMap.put("sports","sports");
		hobbyMap.put("music","music");
		hobbyMap.put("movie","movie");
		log.info("hobbyMap :"+hobbyMap);
		
		model.addAttribute("hobbyMap",hobbyMap);
		
		memberVO.setGender("Male");
		//여러개의 라디오 버튼 요소의 value와 label을 구성
		Map<String, String> genderMap = new HashMap<String, String>();
		genderMap.put("Male","Male");
		genderMap.put("Female","Female");
		genderMap.put("Other","Other");
		log.info("genderMap : "+ genderMap);
		
		model.addAttribute("genderMap",genderMap);
		
		memberVO.setNationality("Australia");
		//국적
		Map<String, String> nationalityMap = new HashMap<String, String>();
		nationalityMap.put("Korea","한국");
		nationalityMap.put("Germany","독일");
		nationalityMap.put("Australia","오스트레일리아");
		model.addAttribute("nationalityMap",nationalityMap);
		
		
		String[] cars = {"qm5","sonata"};
		memberVO.setCars(cars);
		
		Map<String,String> carsMap = new HashMap<String,String>();
		carsMap.put("qm5","qm5");
		carsMap.put("genesis","제네시스");
		carsMap.put("sonata","소나타");
		log.info("carsMap :"+carsMap);
		
		model.addAttribute("carsMap",carsMap);
		//forwarding
		return "member/registerForm08";
	}
	//요청파라미터 : {userId=gaeddongi,userName=개똥이,password=java
	//			,email=aaa@aaaa.com
	//			,introduction=안녕하세요 \n 반가워요,hobbys=[sport,movie]
	//			,gender=Female,nationality=Korea,cars=[sonata,qm5}
	//입력값 검증을 할 도메인 클래스에 골뱅이Validated를 지정함
	/*
	입력값 검증 결과 
	입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의함
	BindingResult에는 요청 데이터의 바인딩 에러와 입력값 검증 에러 정보가 저장됨
	- hasErrors() : 오류 발생 시 true
	- hasGlobalErrors() : 객체 레벨의 오류 발생 시 true
	- hasFieldErrors() : 멤버변수 레벨의 오류 발생 시 true
	- hasFieldErrorw(String) : 인수에 지정한 멤버변수에 오류 발생 시 ture
	 */
	@PostMapping("/registerForm08Post")
	public String registerForm08Post(@Validated MemberVO_backup memberVO,
			BindingResult result,Model model) {
		log.info("memberVO : "+memberVO);
		log.info("result.hasErrors() : " +result.hasErrors());
		//유효성검증 실패
		if(result.hasErrors()) { //true(오류 발생함)
			result.getAllErrors(); // rorcp
			List<ObjectError> allErrors = result.getAllErrors(); //모든
			List<ObjectError> GlobalErrors = result.getGlobalErrors(); //객체
			List<FieldError> fieldErrors = result.getFieldErrors(); //객체
			
			log.info("allErrors.size() :"+allErrors.size());
			log.info("GlobalErrors.size() :"+GlobalErrors.size());
			log.info("fieldErrors.size() :"+fieldErrors.size());
			
			for(int i=0; i<allErrors.size();i++) {
				ObjectError objectError = allErrors.get(i);
				log.info("allErrors:"+objectError);
			}
			for(int i=0; i<GlobalErrors.size();i++) {
				ObjectError objectError = GlobalErrors.get(i);
				log.info("GlobalErrors:"+objectError);
			}
			for(int i=0; i<fieldErrors.size();i++) {
				ObjectError fieldError = fieldErrors.get(i);
				log.info("fieldErrors:"+fieldError);
				log.info("fieldError.getDefaultMessage() :"+fieldError.getDefaultMessage());
				
			}
			
			memberVO.setUserId("gaeddongi");
			memberVO.setUserName("개똥이");
			//값을 setting을 하여 뷰에 전달해도 패스워드 필드에 반영되지 않음
			memberVO.setPassword("java");
			memberVO.setIntroduction("안녕하세요 \n 반가워요");
			
			//취미를 미리 체크
			String[] hobbys = {"sports","movie"};
			memberVO.setHobbys(hobbys);
			
			Map<String,String> hobbyMap = new HashMap<String,String>();
			hobbyMap.put("sports","sports");
			hobbyMap.put("music","music");
			hobbyMap.put("movie","movie");
			log.info("hobbyMap :"+hobbyMap);
			
			model.addAttribute("hobbyMap",hobbyMap);
			
			memberVO.setGender("Male");
			//여러개의 라디오 버튼 요소의 value와 label을 구성
			Map<String, String> genderMap = new HashMap<String, String>();
			genderMap.put("Male","Male");
			genderMap.put("Female","Female");
			genderMap.put("Other","Other");
			log.info("genderMap : "+ genderMap);
			
			model.addAttribute("genderMap",genderMap);
			
			memberVO.setNationality("Australia");
			//국적
			Map<String, String> nationalityMap = new HashMap<String, String>();
			nationalityMap.put("Korea","한국");
			nationalityMap.put("Germany","독일");
			nationalityMap.put("Australia","오스트레일리아");
			model.addAttribute("nationalityMap",nationalityMap);
			
			
			String[] cars = {"qm5","sonata"};
			memberVO.setCars(cars);
			
			Map<String,String> carsMap = new HashMap<String,String>();
			carsMap.put("qm5","qm5");
			carsMap.put("genesis","제네시스");
			carsMap.put("sonata","소나타");
			log.info("carsMap :"+carsMap);
			
			model.addAttribute("carsMap",carsMap);
			
			//forwarding
			return "member/registerForm08";
		}
		return "member/result";
		//유효성검증 통과 
		//forwarding 시 memberVO객체가 뷰로 전달됨
//		return "member/result";
	}
}
