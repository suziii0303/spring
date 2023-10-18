function CheckAddProduct(){
	let form =document.newProduct;
	//1) 상품 아이디 체크
	//첫글자는 P. 숫자를 조합하여 5~12자까지 입력 가능
	//P1234
	let regExpId = /^P[0-9]{4,11}$/;
	if(!regExpId.test(form.productId.value)){
		alert("[상품 코드]\nP와 숫자를 조합하여 5~12자까지 입력하세요\n첫 글자는 P로 시작하세요");
		form.productId.select();
		form.productId.focus();
		return false;
	}
	//2) 상품명 체크
	//4~12자까지 입력 가능
	// ex) 삼성갤럭시S22
	if(form.pname.value.length<4|| form.pname.value.length>12){
		alert("[상품명]\n최소 4자에서 최대 12자 까지 입력하세요");
		form.pname.select();
		form.pname.focus();
		return false;
	}
	//3)상품 가격 체크
	// 숫자만 입력 가능. 음수는 막자	// input type = "number"
	if(form.unitPrice.value.length==0||isNaN(form.unitPrice.value)){
		alert("[가격]\n숫자만 입력하세요");
		form.unitPrice.select();
		form.unitPrice.focus();
		return false;
	}
	if(form.unitPrice.value<0){
		alert("[가격]\n음수는 입력할 수 없습니다.");
		form.unitPrice.select();
		form.unitPrice.focus();
		return false;
	}
	//12343214.12 소수점 둘째자리까지만 허용 숫자 = [0-9] = \d  소수점(\.\d{1,2})?
	let regExpUnitPrice = /^\d+$/
	if(!regExpUnitPrice.test(form.unitPrice.value)){
		alert("[가격]\n가격은 소수점 둘째 자리 까지만 허용됩니다");
		form.unitPrice.select(-1);
		form.unitPrice.focus();
		return false;
	}
	
	//4) 재고 수 체크
	// 숫자만 입력 가능
	if(isNaN(form.unitsInStock.value)||form.unitsInStock.value.length==0){
		alert("[재고수량]\n숫자만 입력하세요");
		form.unitsInStock.select();
		form.unitsInStock.focus();
		return false;
	}
	if(form.unitsInStock.value<0){
		alert("[재고수량]\n음수는 입력할 수 없습니다.");
		form.unitsInStock.select();
		form.unitsInStock.focus();
		return false;
	}
	// 유효성 검사를 모두 통과햇다면
	alert("모두 통과");
	form.submit();
	
}