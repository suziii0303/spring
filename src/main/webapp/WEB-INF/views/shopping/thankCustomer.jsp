<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"href="/resources/css/bootstrap.min.css">
<title>Welcome</title>
</head>
<body>
	<!-- //////////////////// header 시작 (menu.jsp) //////////////////// -->
	<!-- //////////////////// header 끝 //////////////////// -->
	
	<div class="jumbotron">
		<div class="container">
			<h1 class= "display-3">주문완료</h1>
		</div>
	</div>
	<div class="container">
	<!-- ///////////////주문 완료 내용 시작 ////////////////////// -->
	<!-- session.setAttribute("shippingDateMap",shippingDateMap);
		session.setAttribute("cartIdMap",cartIdMap); -->
		<!-- sessionScope.shippingDateMap : 
		shippingDateMap.put("shippingDate", param.get("shippingDate").toString()); -->
		<h2 class="alert alert-danger">주문해주셔서 감사합니다.</h2>
		<p>주문은 ${sessionScope.shippingDateMap.shippingDate}에 배송될 예정입니다</p>
		<p>주문 번호 : ${sessionScope.cartIdMap.cartId}</p>
	<!-- ///////////////주문 완료 내용 끝 ////////////////////// -->
	</div>
	<div class="container">
		<p><a href="/shopping/products" class="btn btn-secondary">&laquo;상품 목록</a></p>
	</div>
	<!-- //////////////////// footer 시작 (footer.jsp) //////////////////// -->
	<!-- //////////////////// footer 끝 (footer.jsp) //////////////////// -->

</body>
</html>


