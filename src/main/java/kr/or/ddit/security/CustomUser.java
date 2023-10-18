package kr.or.ddit.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.or.ddit.vo.MemberVO;

//memberVO 정보를 User(스프링 시큐리티에서 제공해주는 사용자 정보 클래스) 객체 정보에 연계하여 넣어줌
//CustomUser의 멤버변수인 memberVO는 jsp에서 principal로 사용됨
public class CustomUser extends User {

	// 멤버변수 선언
	private MemberVO memberVO;
	
	public CustomUser(String username, String password
		, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);

   }

//   return memberVO==null?null:new CustomUser(memberVO);
	//List<MemberAuthVO> -> Collection<? exetends GrantedAuthority> 필요함
	public CustomUser(MemberVO memberVO) {
		//사용자가 정의한(select한) memberVO객체를 
		//스프링 시큐리티에서 제공하고 있는 UserDetails 타입으로 변환
		//회원 정보를 --> 스프링에 보냄 이제부터 스프링이 관리 인증/인가 처리 및 관리
		
		super(memberVO.getUserNo()+"",memberVO.getUserPw(),
				memberVO.getMemberAuthVOList().stream()
				.map(auth->new SimpleGrantedAuthority(auth.getAuth()))
				.collect(Collectors.toList())
				);
		this.memberVO=memberVO;
	}
	
	public MemberVO getMemberVO() {
		return memberVO;
	}
	
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
   
}