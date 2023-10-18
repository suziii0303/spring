<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
	//class가 form-control-userl인 요소들을 readonly처리해보자
	$(".form-control-user").attr("readonly","readonly");
	//비활성
	$("#category").attr("disabled","disabled");
	
	//상태도 읽기전용 처리
	//$("input[name='condition']").attr("disabled","disabled");
	$("input[type='radio']").attr("onclick","return(false);");
	
	//ckeditor 상세내역 읽기전용 처리
	//CKEDITOR.instances['description'].setReadOnly(true);
	$("#description").attr("readOnly",true);
	//파일 등록 버튼 가리기
	$("#bookImage").parent().parent().css("display","none");

//$("input[name='condition']").prop("disabled", true);
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
	   		success:function(result){
	   			console.log("result:"+result);
	   			//도서코드 텍스트박스에 값 넣기
	   			$("#bookId").val(result);
	   			
	   		}
	   }) ;
   });
})
</script>
<div class="row" id="row">
<!--  data.attachVOList[0] -->

  <div class="col-lg-5 d-none d-lg-block bg-register-image2" 
      style="background-image:url('/resources/images${data.attachVOList[0].filename}');">
   </div>
 
   <div class="col-lg-7">
      <div class="p-5">
         <div class="text-center">
            <h1 class="h4 text-gray-900 mb-4">도서등록</h1>
         </div>
         <!-- 
			요청 URL : /bookInfo/updateBookPost
			요청파라미터 : {bookId=ISBN1234,name=....}
			요청방식 : post
          -->
         <form class="bookInfoFrm" action="/bookInfo/updateBookPost" method="post"
         	enctype="multipart/form-data">
         <!-- 폼데이터 -->
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="text" class="form-control form-control-user"
                     id="bookId" name="bookId" value="${data.bookId}" placeholder="Id" readonly/>
                  <a href="#" id="aBookId" class="btn btn-info btn-icon-split">
                   	<span class="icon text-white-50">
                     	<i class="fas fa-info-circle"></i>
                    </span>
                    <span class="text">도서코드생성</span>
                  </a>
               </div>
               <div class="col-sm-6">
               <!-- data : BookInfoVO bookInfoVO -->
                  <input type="text" class="form-control form-control-user"
                     id="name" name="name" value="${data.name}"placeholder="제목"/>
               </div>
            </div>
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="number" class="form-control form-control-user"
                     id="unitPrice" name="unitPrice" value="${data.unitPrice}"placeholder="가격"/>
               </div>
               <div class="col-sm-6">
                  <input type="text" class="form-control form-control-user"
                     id="author" name="author" value="${data.author}"placeholder="저자"/>
               </div>
            </div>
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="text" class="form-control form-control-user"
                     id="publisher" name="publisher" value="${data.publisher}"placeholder="출판사"/>
               </div>
               <div class="col-sm-6">
                  <input type="date" class="form-control form-control-user"
                     id="releaseDate" name="releaseDate" value="${data.releaseDate}"placeholder="출판일"/>
               </div>
            </div>
            <div class="form-group row">
               <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="number" class="form-control form-control-user"
                     id="totalPages" name="totalPages" value="${data.totalPages}"placeholder="총페이지"/>
               </div>
               <div class="col-sm-6">
                  <button class="btn btn-primary dropdown-toggle" type="button" 
                  id="category" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  ${data.category}
                  </button>
                  <div class="dropdown-menu animated--fade-in" aria-labelledby="dropdownMenuButton" style="">
                            <a class="dropdown-item clsCategory" href="#">Hello Coding</a>
                            <a class="dropdown-item clsCategory" href="#">IT모바일</a>
                            <a class="dropdown-item clsCategory" href="#">소설</a>
                        </div>
                        <input type="text" name="category" value="${data.category}"/>
               </div>
            </div>
            <div class="form-group">
               <textarea class="form-control form-control-user"
                  id="description" name="description" 
                  placeholder="상세정보" >${data.description}</textarea>
            </div>
            <div class="form-group row">
               <div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" id="condition1" name="condition" 
				  value="신규도서"
				  <c:if test="${data.condition =='신규도서'}">checked</c:if>
				   />
				  <label class="form-check-label" for="condition1">신규도서</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" id="condition2" name="condition" 
				  value="중고도서"
				  <c:if test="${data.condition =='중고도서'}">checked</c:if>
				  />
				  <label class="form-check-label" for="condition2">중고도서</label>
				</div>
				<div class="form-check form-check-inline">
				  <input class="form-check-input" type="radio" id="condition3" name="condition"
				  value="E-Book"
				  <c:if test="${data.condition =='E-Book'}">checked</c:if>
				  >
				  <label class="form-check-label" for="condition3">E-Book</label>
				</div>
				<div class="input-group mb-3">
				 		<input type="file" class="form-control" id="bookImage"
				 			name="bookImage" /></div></div>
				<!-- 일반모드 -->
				<div id="div1">
					<p>
						<button type="button" id="edit"
							class="btn btn-primary btn-user btn-block"
							style="width:50%;float:left;">수정</button>
					</p>
					<p>
						<button type="button" id="delete"
								class="btn btn-primary btn-user btn-block"
								style="width:50%;">삭제</button>
					</p>
					<p>
						<a href="/bookInfo/listBook"
							class="btn btn-success btn-user btn-block">목록</a>
					</p>
				</div>
				<!-- 수정모드 -->
				<div id="div2" style="display:none;">
					<button type="submit" class="btn btn-primary btn-user btn-block">확인</button>
					<a href="/bookInfo/detailBook?bookId=${param.bookId}"
						class="btn btn-success btn-user btn-block">취소</a>
				</div>
         </form>
         </div>
      </div>
   </div>

<script type="text/javascript">
	CKEDITOR.replace('description');
	
	$(function(){
		//수정버튼 클릭 -> 수정모드로 전환
		$("#edit").on("click",function(){
			$("#div1").css("display","none");
			$("#div2").css("display","block");
			
			//class가 form-control-userl인 요소들을 readonly처리해보자
			$(".form-control-user").removeAttr("readonly");
			//비활성 -> 활성
			$("#category").removeAttr("disabled");
			
			//상태도 읽기전용 처리 ->수정 가능
			//$("input[name='condition']").attr("disabled","disabled");
			$("input[type='radio']").attr("onclick","return(true);");
			
			
			//파일 등록 버튼 가리기
			$("#bookImage").parent().parent().css("display","block");

			//ckeditor 상세내역 읽기전용 처리
			CKEDITOR.instances['description'].setReadOnly(false);
			//$("#description").attr("readOnly",true);
			
			//도서아이디는 계속 비활성화
			$("input[name='bookId']").attr("redOnly",true);
			
		});
		
		//삭제버튼 클릭 <button type="button" id="delete"
		$("#delete").on("click",function(){
			//<form class="bookInfoFrm" 의 action속성의 값을 /bookInfo/deleteBookPost로 변경
			$(".bookInfoFrm").attr("action","/bookInfo/deleteBookPost");
			//BookInfpController에서 해당 URI를 받아서 
			
			//log.info("bookId : "+bookInfoVO.getBookId());
			//도서 목록으로 redirect
			
			//삭제하시겠습니까? comfirm 처리
			let result= confirm("삭제하시겠습니까?");
			console.log("result : " +result);
			
			if(result>0){ // 확인
				//form 요소를 선택하여  submit처리
				$(".bookInfoFrm").submit();
			}else{
				//삭제가 취소되었습니다. 경고창 처리
				alert("삭제가 취소되었습니다")
			}
		});
		
		
	});
</script>




