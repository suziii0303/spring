package kr.or.ddit.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.opencv.core.Mat.Atable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import kr.or.ddit.service.Bookservice;
import kr.or.ddit.vo.AttachVO;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

//4장. 컨트롤러 응답
@RequestMapping("/resp")
@Slf4j
@Controller
public class ResponseController {
	//DI(의존성 주입), IoC(제어 역전)
	@Inject
	Bookservice bookservice;
	//1) void 타입
	//	호출하는 URL과 동일한 뷰 이름을 나타내기 위해 사용
	
	//요청URI : /resp/goHome0101
	@RequestMapping(value="/goHome0101",method=RequestMethod.GET)
	public void home0101() {
		log.info("home0101");
	}
	
	//요청URI : /resp/goHome0202
	@GetMapping(value="/goHome0102")
	public void home0102() {
		log.info("home0102");
	}
	
	//2. String 타입
	//	뷰 파일의 경로와 파일 이름을 나타내기 위해 사용
	
	//요청URL : /resp/goHome0201
	@GetMapping(value="/goHome0201")
	public String home0201() {
		log.info("home0201");
		
		//serblet-context.xml
		/*
		<beans:property name="prefix" value="/WEB-INF/views/" />
		<beans:property name="suffix" value=".jsp" />'
		/WEB-INF/views/+resp/goHome0201 +.jsp
		 */
		return "resp/goHome0201";
	}
	//요청URL : /resp/goHome0202
	@GetMapping(value="/goHome0202")
	public String home0202() {
		log.info("home0202");
		//forwarding
		return "resp/goHome0202";
	}
	
	//3) 반환값이"redirect:"로 시작하면 리다이렉트 방식으로 처리
	@GetMapping("/goHome0301")
	public String home0301() {
		log.info("home0301");
		
		//redirect : URL을 새롭게 요청 -> 주소표시줄이 바뀜
		return "redirect:/resp/goHome0202";
	}
	
	//반환값이 "/"로 시작하면 웹 애플리케이션의 컨텍스트 경로에 영향을 받지 않는
	//절대경로를 의미함
	//요청URI : /resp/goHome0302
	@GetMapping("/goHome0302")
	public String home0302() {
		log.info("home0302");
		//forwarding
		/*
		/WEB-INF/views/+resp/goHome0201 +.jspㅍ
		
		tiles-config.,xml의 별/별에 매핑이 안되어 타일즈 적용이 안됨
		*/
		return "/resp/goHome0302";
	}
	
	//요청URI : /resp/goHome0303
	//요청방식 : get
//	@GetMapping("/goHome0303")
//	public String home0303() {
//		log.info("home0303");
//		//forwrding
//		return "/resp/goHome0303";
//	
//	}
	
	@RequestMapping(value = "/goHome0303",method=RequestMethod.GET)
	public ModelAndView goHome0303(ModelAndView mav) {
		mav.setViewName("resp/goHome0303");
		
		return mav;
	}
	
	//3) 자바빈즈 클래스 타입
	//	VO -> JSON으로 반환해보자
	
	//골뱅이ResponseBody를 지정하지 않으면 HTTP 404(jsp없음)오류발생
	//pom.xml에 의존관계라이브러리인 jackson-databind 이 등록되어있어야 하고
	//	jackson-databind가 없으면 HTTP 406오류가 발생.
	//골뱅이 ResponseBody를 지정해줘야 함.
	@ResponseBody
	@GetMapping("/goHome030101")
	public AttachVO home030101() {
		log.info("home030301");
		
		AttachVO attachVO = new AttachVO();
		attachVO.setSeq(1);
		attachVO.setBookId("ISBN1234");
		attachVO.setFilename("개똥이.jpg");
		
		return attachVO;
	}
	
	//요청URI : /resp/goHome030102?bookId=1
	//요청방식 : get
	//BookVO{bookId=1,title=검은태양,category=드라마...}
	//골뱅이ResponseBody : VO -> JSON으로 응답
	@ResponseBody
	@GetMapping("/goHome030102")
	public BookVO home030102(@RequestParam(value="bookId",required=false,defaultValue="1") int bookId,
			BookVO bookVO) {
		log.info("bookId : "+bookId);
		
		bookVO  = this.bookservice.detail(bookVO);
		
		return bookVO;
		
	}
	//골뱅이RequestBody : json{"bookId":"1"} -> bookVOP{bookId=1,title=null...}
	//골뱅이ResponseBody : bookVOP{bookId=1,title=개똥이...}-> json{"bookId":"1","title":"개똥이"}
	@ResponseBody
	@PostMapping("/goHome030103")
	public BookVO home030103(
			@RequestBody BookVO bookVO) {
		
		bookVO  = this.bookservice.detail(bookVO);
		
		return bookVO;
		
	}
	//요청URI : /resp/goHome030105
	//json string : {"bookId":"1"}
	//요청방식 : post
	@ResponseBody
	@PostMapping("/goHome030105")
	public BookVO gohome030105(
			@RequestBody BookVO bookVO) {
		log.info("bookVO(호출전):"+bookVO);
		//호출전 : bookId만 있음
		bookVO  = this.bookservice.detail(bookVO);
		//호출후 : bookVO 멤버변수에 값들이 채워짐
		log.info("bookVO(호출후):"+bookVO);
		return bookVO;
		
	}
	//4. 컬랙션 List 타입
	//	JSON 객체 배열 타입의 데이터를 만들어서 반환
	
	//4-1) 반환값이 컬랙션 List 타입이면 JSON 객체 배열 타입으로 자동 변환됨
	// 요청URI : /resp/goHome04
	@ResponseBody
	@GetMapping("/goHome04")
	public List<BookVO> home04(BookVO bookVO) {
		log.info("home04");
		
		//도서 목록
		List<BookVO> list = new ArrayList<BookVO>();
		
		//1) 1번 도서를 목록에 추가
		bookVO.setBookId(1);
		BookVO vo1 = this.bookservice.detail(bookVO);
		list.add(vo1);
		
		//2) 2번 도서를 목록에 추가
		bookVO = new BookVO();
		bookVO.setBookId(2);
		BookVO vo2 = this.bookservice.detail(bookVO);
		list.add(vo2);
		
		log.info("list : "+ list);
		
		return list;
		
	}
	//5. 컬렉션 Map 타입
	//	Map 형태의 컬렉션 자료를JSON 객체 타입의 데이터로 만들어서 반환함
	@ResponseBody
	@GetMapping("goHome05")
	public Map<String, BookVO> home05(){
		log.info("home05");
		
		Map<String, BookVO> map = new HashMap<String, BookVO>();
		
		//1) 1번 도서를 Map에 넣기
		BookVO vo1 = new BookVO();
		vo1.setBookId(1);
		vo1= this.bookservice.detail(vo1);
		map.put(vo1.getBookId()+"", vo1);
		
		//1) 1번 도서를 Map에 넣기
		BookVO vo2 = new BookVO();
		vo2.setBookId(2);
		vo2= this.bookservice.detail(vo2);
		map.put(vo2.getBookId()+"", vo2);
		
		log.info("vo2 : " + vo2);
		
		return map;
	}
	
	//6. ResponseEntity<Void> 타입
	//	 Void : 반환할 데이터가 없음
	//		response할 때 Http 헤더 정보와 내용을 가공함
	@ResponseBody
	@GetMapping("/goHome06")
	public ResponseEntity<Void> home06(){
		log.info("home06");
		//HttpStatus.OK : HTTP 상태 (200)
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	//7. ResponseEntity<String> 타입
	//	response할 때 Http 헤더 정보와 문자열 데이터를 전달하는 용도로 사용함
	@ResponseBody
	@GetMapping("/goHome07")
	public ResponseEntity<String> home07(){
		log.info("home07");
		
		String result="";
		
		//1) 로그인 2) 로그인계정 == 작성자 계정
		//성공:
		result="SUCCESS";
		//실패: FAIL
		//result - "FAIL";
		
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	//8. ResponseEntity<자바빈즈 클래스> 타입
	// response 할 때 Http 헤더 정보와 객체 데이터를 전닿하는 용도
	@ResponseBody
	@GetMapping("/home08")
	public ResponseEntity<BookVO> home08() {
		log.info("home08");
		
		BookVO bookVO = new BookVO();
		bookVO.setBookId(1);
		bookVO = this.bookservice.detail(bookVO);
		//bookVO -> JSON
		//return bookVO;
		return new ResponseEntity<BookVO>(bookVO,HttpStatus.OK);
	}
	
	//9. ResponseEntity<List>타입
	//	 response 할 때 Http 헤더 정보와 객체 배열 데이터를 전달할 때 사용
	@ResponseBody
	@GetMapping("/goHome09")
	public List<String> home09(){
		log.info("home09");
		
		//공통 코드로 관리
		List<String> list = new ArrayList<String>();
		list.add("소설");
		list.add("IT");
		list.add("수필");
		
		log.info("home09->list : "+list);
		
		return list;
		
	}
	//10. ResponseEntity<Map> 타입 
	@ResponseBody
	@GetMapping("/goHome10")
	public ResponseEntity<Map<String,BookVO>> home10(){
		log.info("home10");
		
		Map<String, BookVO> map = new HashMap<String, BookVO>();
		
		BookVO vo1 =new BookVO();
		vo1.setBookId(1);
		vo1 = this.bookservice.detail(vo1);
		map.put(vo1.getBookId()+"", vo1);
		
		BookVO vo2 =new BookVO();
		vo2.setBookId(2);
		vo2 = this.bookservice.detail(vo2);
		map.put(vo2.getBookId()+"", vo2);
		
		return new ResponseEntity<Map<String,BookVO>>(map,HttpStatus.OK);
	}
	
	//11. ResponseEntity<byte[]> 타입 
	// response할 때 Http 헤더 정보와 바이너리 파일 데이터를 전달하는 용도
	@ResponseBody
	@GetMapping("/goHome1101")
	public ResponseEntity<byte[]> home1101() throws IOException {
		log.info("home1101");
		
		InputStream in =null;
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
		in = new FileInputStream("C:\\eGovFrame3.10.0\\workspace\\springProj\\src\\main\\webapp\\resources\\images\\36ce9f97-a924-46c4-8ef1-b8444614ffe5_스포츠.jpg");
		
		headers.setContentType(MediaType.IMAGE_JPEG);
		
		entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
				headers, HttpStatus.CREATED);
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}finally {
			in.close();
		}
		return entity;
	}
	
	//파일 다운로드에서 많이 활용
	@ResponseBody
	@GetMapping("/goHome1102")
	public ResponseEntity<byte[]> home1102() throws IOException{
		log.info("home1102");
		//Stream : 파일을 읽거나 쓸 때, 네트워크 소켓을 거쳐 통신할 때 쓰이는 추상적인 개념
		//		  데이터가 전송되는 통로
		//InputStream : 추상 클래스. 데이터가 들어오는 통로의 여고할에 관해 규정하고 있음
		//				1) 데이터를 읽어야 함 2) 남은 데이터 확인 3) 데이터 skip 가능 4) close가능(통로제거)
		//				5) 특정 시점부터 다시 읽을 수 있음
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			in = new FileInputStream(
			"C:\\eGovFrame3.10.0\\workspace\\springProj\\src\\main\\webapp\\resources\\images\\adee16df-c668-403b-b1c0-82611e0197de_ccc.jpg");
			
			headers.setContentType(MediaType.IMAGE_JPEG);
			
			//IOUtils : commons-io에서 제공.
			//HttpStatus.CREATED : HTTP상태 201
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
