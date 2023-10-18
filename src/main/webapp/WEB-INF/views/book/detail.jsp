<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
//data : BookVO bookVO
// mav.addObject("data", bookVO);
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<title>책 상세</title>
<script type="text/javascript">
//documtnt 
$(function(){
	//1) /detail?bookId=1
	let bookId = ${param.bookId};	//1
	//2) json object 
	let data = {"bookId":bookId};
	console.log("data :"+ JSON.stringify(data));
	
	//3) 아작나써유..피씨다타써
	$.ajax({
		url : "/resp/goHome030105",
		contentType: "application/json;charset:utf-8",
		data :JSON.stringify(data),
		type:"post",
		success:function(result){
			//result : {"bookId":"1","title":"개똥이의 여행",...}
			console.log("result:"+JSON.stringify(result));
			$("input[name='bookId']").val(result.bookId);
			$("input[name='title']").val(result.title);
			$("input[name='category']").val(result.category);
			$("input[name='price']").val(result.price);
			$("textarea[name='content']").val(result.content.replace(/<\/?p>/g, ''));
		}
	});
})
</script>
</head>
<body>
<h1>책 상세</h1>
<!-- 
JSTL(JSP Standard Tag Library) : 개발자들이 자주 사용하는 패턴을 모아놓은 집합
=> BookVontroller에서 보내준 데이터를 뷰(jsp)에 표현하도록 도와줌

method
1) GET : 데이터를 변경하지 않을 때. 목록/상세보기
2) POST : 데이터를 변경할 때 . 입력/ 수정/ 삭제

업데이트 쎄대여
UPDATE BOOK
SET		TITLE='개똥이의 모험', CATEGORY='소설', PRICE=12000, CONTENT='재미있다'
WHERE 	BOOK_ID = 1

틍푸른생선 주세요
DELETE FROM BOOK
WHERE	BOOK_ID = 1

WHERE
1) 단일행 : =, <, >, <=, >=, !=, <>
2) 다중행 : IN(교집합), ANSY(OR), ALL(AND), EXISTS(교집합
 -->
<form id="frm" name="frm" action="/updatePost" method="post">

	<!-- 폼 데이터  -->
<!-- 	data : BookVO bookVO -->
	<input type="hidden" name="bookId"value="">
	<p>제목 : <input type="text" name="title" class="formdata" value="" readonly/></p>
	<p>카테고리 : <input type="text" name="category" class="formdata" value=""readonly/></p>
	<p>가격 : <input type="text" name="price"  class="formdata" maxlength="10" value='<fmt:formatNumber type="number" value="" pattern="#,###"/>' readonly/></p>
	
	<p>설명 : <textarea rows="5" cols="30" class="formdata" name="content" readonly></textarea></p>
	<!-- 일반모드 시작 -->
	<p id="p1">
		<input type="button" id="edit" value="수정"/>
		<input type="button" id="delete" value="삭제"/>
		<input type="button" id="list" value="목록"
			onclick="javaacript:location.href='/list';"/>
	</p>
	<!-- 일반모드 끝 -->
	<!-- 수정 모드 시작 -->
	<p id="p2" style="display:none;">
		<input type="submit" id="confirm" value="확인"/>
		<input type="button" id="cancel" value="취소"/>
	</p>
	<!-- 수정 모드 끝 -->
	
</form>
<script type="text/javascript">
//document 내의 모든 요소들이 로딩이 완료된 후에 실행
$(function(){
	console.log("개똥이");
	//수정 버튼 클릭 -> 수정모드로 전환
	$("#edit").on("click",function(){
		$("#p1").css("display","none");
		$("#p2").css("display","block");
		$(".formdata").removeAttr("readonly");
		
		//가격요소를 선택 후 쉼표 제거 후 type을 number로 바꾸자
		let objPrice = $("input[name='price']");
		let price = objPrice.val();
		price = price.replaceAll(",","");
		objPrice.val(price);
		objPrice.attr("type","number");
		
		CKEDITOR.replace("content");
		
		$("form").attr("action","/updatePost");
	});
	//취소버튼클릭
	$("#cancel").on("click",function(){
		//주소 표시줄 : detail?bookId=1
		//param : bookId=1
		//param.bookId : 1
		location.href="/detail?bookId=${param.bookId}";		
	});
	//삭제버튼 클릭
// 	DELETE FROM BOOK
// 	WHERE	BOOK_ID = 1
	$("#delete").on("click",function(){
		$("form").attr("action","/deletePost");
		
		let result = confirm("삭제하시겠습니까?");
		
		console.log("result :"+result);
		
		if(result>0){
			let objPrice = $("input[name='price']");
			let price = objPrice.val();
			price = price.replaceAll(",","");
			objPrice.val(price);
			objPrice.attr("type","number");
			$("form").submit();
		}else{
			alert("삭제가 취소되었습니다");
		}
		
	});
	$("#list").on("click",function(){
		location.href="/list";		
	});
});
</script>

</body>
</html>