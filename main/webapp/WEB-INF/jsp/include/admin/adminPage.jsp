<!DOCTYPE html>
<%@page language="java" isELIgnored="false" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>

<script>
    $(function(){
        $("ul.pagination li.disabled a").click(function(){
            return false;
        });
    });
</script>

<nav style="align-items: center">
    <ul class="pagination">
    <li <c:if test="${page.start eq 0}">class="disabled"</c:if>>
        <a href="?start=0${page.param}">首页</a>
    </li>

    <li <c:if test="${page.start eq 0}">class="disabled"</c:if>>

        <a href="?start=${page.start le page.count?0:page.start-page.count}${page.param}">上一页</a>
    </li>

    <%--遍历每一页--%>
    <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
    <li>
        <a href="?start=${status.index * page.count}${page.param}">${status.count}</a>
    </li>
    </c:forEach>

    <li <c:if test="${page.start ge page.last}">class="disabled"</c:if>>
        <a href="?start=${page.start gt page.last?page.last:page.start+page.count}${page.param}">下一页</a>
    </li>

    <li <c:if test="${page.start eq page.last}">class="disabled"</c:if>>
        <a href="?start=${page.last}${page.param}">末页</a>
    </li>
    </ul>
</nav>