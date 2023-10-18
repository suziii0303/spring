<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<style>
.bg-register-image2{
	background-position: center;
	background-size: cover;
}
</style>
<script src="/resources/js/jquery-3.6.0.js"></script>
<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript">


//document 내 모든 요소가 로딩된 후에 실행
$(function(){
	//이미지 미리보기 시작 ////////////////////
	//<input type="file" name="bookImage"....
	$("input[name='bookImage']").on("change",handleImgFileSelect);
	
	//e : onchange 이벤트 삭제

	function handleImgFileSelect(e){
		//e.target : <<input type="file" name="bookImage"....
		let files = e.target.files;
		//이미지 오브젝트배열 
		let fileArr = Array.prototype.slice.call(files);
		//f : fileArr(이미지 오브젝트 배열)에서 이미지 오브젝트 1개
		fileArr.forEach(function(f){
			//f.type : 이미지 오브젝트의 MINE 타입
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능합니다");
				//함수 종료
				return;
			}
			//if문 통과(이미지라면..)
			//이미지를 읽어보자
			let reader = new FileReader();
			reader.onload = function(e){
				//e.target : 읽고있는 이미지 객체	
				let img_html = "<img src='" + e.target.result + "' style='width:100%;' />";
//                $("#row .bg-register-image").html(img_html);
            	$("#row .bg-register-image2").css({"background-image":"url("+e.target.result+")"});

//                $(".bg-register-image").removeClass("bg-register-image");				e.target.result
			}
			//다음 이미지 파일(f)를 위해 reader를 초기화
			reader.readAsDataURL(f);
		});//end forEach
		
		//$(".bg-register-image").removeClass("bg-register-image");
		//이미지를 담고있는 div
		$("#row").first().html();
	}


	
	//이미지 미리보기 끝 ////////////////////
   /*
   <a class="clsCategory" href="#">Hello Coding</a>
    <a class="clsCategory" href="#">IT모바일</a>
    <a class="clsCategory" href="#">소설</a>
   */
   $('.clsCategory').on('click',function(){
      let category = $(this).html();
      console.log("category :" + category)
      $('#category').html(category);
      //<button id="category" ....
      $("#category").html(category);
      //<input type="hidden" name="category" ...
      $("input[name='category']").val(category);
   });
   
   //도서코드 자동생성
   $("#aBookId").on("click",function(){
	   console.log("개똥이");
	   
	   //아작나써유..피씨다타써
	   //contentType: 보내는 타입
	   //dataType: 응답타입
	   $.ajax({
			url:"/bookInfo/getBookId",
	   		type:"post",
	   		dataType:"text",
	   		beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
	   		success:function(result){
	   			console.log("result1:"+result);
	   			//도서코드 텍스트박스에 값 넣기
	   			$("#bookId").val(result);
	   			
	   		}
	   }) ;
   });
   
   //요청URI : /board/detail/ISBN1234
   //pathVariable : bookId
   //data : bookId, name,unitPrice, author, totalPages
   //요청방식 : post
   //응답데이터 : SUCCESS
   $("#btnHeaders").on("click",function(){
	   let bookId = $("#bookId").val();
	   let name = $("#name").val();
	   let unitPrice = $("#unitPrice").val();
	   let author = $("#author").val();
	   let totalPages = $("#totalPages").val();
	   
	   let data={"bookId":bookId,"name":name
				,"unitPrice":unitPrice,"author":author
				,"totalPages":totalPages};
	   
	   console.log("data : "+JSON.stringify(data));
	   
	   $.ajax({
			url:"/board/detail/"+bookId,
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			success:function(result){
				console.log("result:"+result);
			}
		});
   });
   
});
</script>
<div class="row" id="row">
  <div class="col-lg-5 d-none d-lg-block bg-register-image2" 
      style="background-image:url('https://source.unsplash.com/Mv9hjnEUHR4/600x800');">
   </div>
   <div class="col-lg-7">
      <div class="p-5">
         <div class="text-center">
            <h1 class="h4 text-gray-900 mb-4">도서등록</h1>
         </div>
         <!-- 
			요청 URL : /bookInfo/addBookPost
			요청파라미터 : {bookId=ISBN1234,name=....}
			요청방식 : post
          -->
         <form class="bookInfoFrm" action="/bookInfo/addBookPost?${_csrf.parameterName}=${_csrf.token}" method="post"
         	enctype="multipart/form-data">
         <!-- 폼데이터 -->
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="text" class="form-control form-control-user"
                     id="bookId" name="bookId" placeholder="도서코드" readonly/>
                   <a href="#" id="aBookId" class="btn btn-info btn-icon-split">
                   		<span class="icon text=white-50">
                   			<i class="fas fa-info-circle"></i>
                   		</span>
                   		<span class="text">도서코드생성</span>
                   </a>
               </div>
               <div class="col-sm-6">
                  <input type="text" class="form-control form-control-user"
                     id="name" name="name" placeholder="도서명"/>
               </div>
            </div>
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="number" class="form-control form-control-user"
                     id="unitPrice" name="unitPrice" placeholder="가격"/>
               </div>
               <div class="col-sm-6">
                  <input type="text" class="form-control form-control-user"
                     id="author" name="author" placeholder="저자"/>
               </div>
            </div>
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="text" class="form-control form-control-user"
                     id="publisher" name="publisher" placeholder="출판사"/>
               </div>
               <div class="col-sm-6">
                  <input type="date" class="form-control form-control-user"
                     id="releaseDate" name="releaseDate" placeholder="출판일"/>
               </div>
            </div>
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="number" class="form-control form-control-user"
                     id="totalPages" name="totalPages" placeholder="총페이지수"/>
               </div>
               <div class="col-sm-6">
                  <button class="btn btn-primary dropdown-toggle" type="button" 
                  id="category" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            분류
                  </button>
                  <div class="dropdown-menu animated--fade-in" aria-labelledby="dropdownMenuButton" style="">
                            <a class="dropdown-item clsCategory" href="#">Hello Coding</a>
                            <a class="dropdown-item clsCategory" href="#">IT모바일</a>
                            <a class="dropdown-item clsCategory" href="#">소설</a>
                        </div>
                        <input type="text" name="category"/>
               </div>
            </div>
            <div class="form-group">
               <textarea class="form-control form-control-user"
                  id="description" name="description" 
                  placeholder="상세정보"></textarea>
            </div>
            <div class="form-group row">
               <div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" id="condition1" name="condition" 
				  value="신규도서" checked />
				  <label class="form-check-label" for="condition1">신규도서</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" id="condition2" name="condition" 
				  value="중고도서">
				  <label class="form-check-label" for="condition2">중고도서</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" id="condition3" name="condition"
				  value="E-Book" disabled>
				  <label class="form-check-label" for="condition3">E-Book</label>
				</div>
				<div class="form-group row">
					<div class="input-group mb-3">
				 		<input type="file" class="form-control" id="bookImage"
				 			name="bookImage" />
					</div>
				</div>
            </div>
           <button type="submit" class="btn btn-primary btn-user btn-block">도서 등록</button>
           <button type="button"id="btnHeaders"
           			class="btn btn-primary btn-user btn-block">headers매핑</button>
         	<sec:csrfInput/>
         </form>
         </div>
      </div>
   </div>

<script type="text/javascript">
	CKEDITOR.replace('description');
</script>