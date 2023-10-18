<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- model.addAttribute("exception",e); -->
<section class="content">
	<div class="error-page">
		<h2 class="headline text-warning">${exception.getMessage()}</h2>
		<div class="error-content">
			<c:forEach var="stack" items="${exception.getStackTrace()}">
			<h3>
				<i class="fas fa-exclamation-triangle text-warning"></i>
				${stack.toString()}
			</h3>
			</c:forEach>
			<p>
				We could not find the page you were looking for. Meanwhile, you may
				<a href="/">return to dashboard</a> or try using the search form.
			</p>
		</div>
	</div>
</section>