<!DOCTYPE html>
<%@page language="java" isELIgnored="false" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/include/admin/adminHeader.jsp"%>
<%@include file="/WEB-INF/jsp/include/admin/adminNavigator.jsp"%>
<script>
    $(function () {
        $("#addForm").submit(function () {
            if (!checkEmpty("name", "产品名称")) {
                return false;
            }
            if (!checkEmpty("subtitle", "小标题")) {
                return false;
            }
            if (!checkNumber("orignalPrice", "原价格")) {
                return false;
            }
            if (!checkNumber("promotePrice", "优惠价格")) {
                return false;
            }
            if (!checkInt("stock", "库存")) {
                return false;
            }
        });
    });
</script>

<title>产品编辑</title>

<div class="workingArea">
    <div>
        <ol class="breadcrumb">
            <li><a href="admin_category_list">所有分类</a></li>
            <li><a href="admin_product_list?cid=${c.id}">${c.name}</a></li>
            <li class="active">${p.name}</li>
            <li class="active">编辑产品</li>
        </ol>
    </div>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_product_update">
                <table class="editTable">
                    <tr>
                        <td width="100px">产品名称</td>
                        <td><input id="name" name="name" value="${p.name}" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>产品小标题</td>
                        <td><input id="subtitle" name="subtitle" value="${p.subtitle}" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td><input id="orignalPrice" name="orignalPrice" value="${p.orignalPrice}" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input id="promotePrice" name="promotePrice" value="${p.promotePrice}" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>库存</td>
                        <td><input id="stock" name="stock" value="${p.stock}" type="text" class="form-control"></td>
                    </tr>

                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${c.id}">
                            <input type="hidden" name="id" value="${p.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>