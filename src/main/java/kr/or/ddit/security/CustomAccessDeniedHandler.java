package kr.or.ddit.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
   /*
    공지사항 등록 화면(/notice/register)은
    일반 회원(member/java)이 접근할 수 없는 URI이고
    관리가(admin/java)만 접근 가능함.
    사용자 접근 거부 처리자(CustomAccessDeniedHandler)에서
    접근 거부 처리 페이지(/security/accessError)로 리다이렉트 시킴
    */
   
   @Override
   public void handle(HttpServletRequest request
         , HttpServletResponse response,
         AccessDeniedException accessDeniedException) 
               throws IOException, ServletException {
      log.info("handle");
      
      response.sendRedirect("/security/accessError");
   }
   
}
