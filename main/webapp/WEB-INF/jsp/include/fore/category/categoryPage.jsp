<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<title>模仿天猫官网-${c.name}</title>	
<div id="category">
	<div class="categoryPageDiv">
		<img src="/img/category/${c.id}.jpg">
		<%@include file="/WEB-INF/jsp/include/fore/category/sortBar.jsp"%>
		<%@include file="/WEB-INF/jsp/include/fore/category/productsByCategory.jsp"%>
	</div>

</div>