<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="/resources/js/jquery-3.6.0.js"></script>
<h1>Home0303</h1>
<script type="text/javascript">
$(function(){
	//요청URI :/resp/goHome030101
	//요청방식 : get
	//아작났어유.. 피씨다타써
	//dataType : 응답 타입
	$.ajax({
		url: "/resp/goHome030101",
		type: "get",
		dataType: "json",
		success:function(result){
			console.log("result : "+ JSON.stringify(result));
			
		}
	});
	
	//1) 도서정보 확인 버튼을 클릭
	$("#btnDetail").on("click",function(){
		console.log("개똥이");
		
		//2) bookId 입력 값을 받아서 JSON Objent에 넣어보자
		let data = {"bookId" :$("input[name='bookId']").val()};
		
		console.log("data : " +JSON.stringify(data));
		//3) 아작나써유..피씨다타써
		//	resp/goHome030103를 요청하여 content에 내용을 넣어보자
		$.ajax({
	        url: "/resp/goHome030103",
	        type: "post",
	        contentType:"application/json;charset:utf-8",
	        data: JSON.stringify(data),
	        dataType: "json",
	        success: function (result) {
	           console.log("result : " + JSON.stringify(result));
	            
	         
	          $("textarea[name='content']").val(result.content);
	            
	        }
		});
	});
});
</script>
<p><input type="text" name="bookId" value="1"/></p>
<p><button type="button" id="btnDetail">도서정보 확인</button></p>
<p>
	<textarea name="content" rows="5" cols="30"></textarea>
</p>