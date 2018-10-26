<!DOCTYPE html>
<%@page language="java" isELIgnored="false" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/include/admin/adminHeader.jsp"%>
<%@include file="/WEB-INF/jsp/include/admin/adminNavigator.jsp"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%--<script>--%>
    <%--// $(function () {--%>
    <%--//     $("#addForm").submit(function () {--%>
    <%--//         if (!checkEmpty("name", "产品名称")) {--%>
    <%--//             return false;--%>
    <%--//         }--%>
    <%--//     });--%>
    <%--// });--%>
<%--</script>--%>

<title>产品属性编辑</title>

<div class="workingArea">
    <div>
        <ol class="breadcrumb">
            <li><a href="admin_category_list">所有分类</a></li>
            <li><a href="admin_product_list?cid=${category.id}">${category.name}</a></li>
            <li class="active">${product.name}</li>
            <li class="active">编辑属性值</li>
        </ol>
    </div>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑属性值</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_propertyValue_update?pid=${product.id}">
                <table width="300px" class="addTable">
                    <c:forEach var="i" begin="1" end="${fn:length(properties)}">
                        <tr>
                            <td width="80px" align="right">${properties[i-1].name}：</td>
                            <td><input type="text" name="${properties[i-1].name}" value="${propertyValues[i-1].value}" class="form-control"></td>
                        </tr>
                    </c:forEach>
                    <tr style="height: 50px" class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${category.id}">
                            <input type="hidden" name="pid" value="${product.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                            &nbsp&nbsp&nbsp&nbsp&nbsp
                            <a href="admin_product_list?cid=${category.id}" class="btn btn-success">返 回</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>