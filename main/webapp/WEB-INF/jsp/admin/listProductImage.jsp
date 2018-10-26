<!DOCTYPE html>
<%@page language="java" isELIgnored="false" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%--<link rel="stylesheet" type="text/css" href="css/back/style.css" />--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/include/admin/adminHeader.jsp"%>
<%@include file="/WEB-INF/jsp/include/admin/adminNavigator.jsp"%>


<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_category_list">所有分类</a></li>
        <li><a href="admin_product_list?cid=${category.id}">${category.name}</a></li>
        <li class="active">${product.name}</li>
        <li class="active">产品图片管理</li>
    </ol>


    <table align="center">
        <tr>
            <td>
                <%--新增展示图片--%>
                <div class="panel panel-warning addPictureDiv" >
                    <div class="panel-heading">新增展示图片</div>
                    <div class="panel-body">
                        <form method="post" id="addFormSingle" action="admin_productImage_add" enctype="multipart/form-data">
                            <table class="addTable">
                                <tr>
                                    <td>上传展示图片</td>
                                    <td><input type="file" id="productSinglePic" accept="image/*" name="image">
                                    </td>
                                </tr>
                                <tr class="submitTR">
                                    <td colspan="2" align="center">
                                        <input type="hidden" name="pid" value="${product.id}">
                                        <input type="hidden" name="type" value="type_single">
                                        <button type="submit" class="btn btn-success">提 交</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </td>
            <td width="10%"></td>
            <td>
                <%--新增详情图片--%>
                <div class="panel panel-warning addPictureDiv" >
                    <div class="panel-heading">新增详情图片</div>
                    <div class="panel-body">
                        <form method="post" id="addFormDetail" action="admin_productImage_add" enctype="multipart/form-data">
                            <table class="addTable">
                                <tr>
                                    <td>上传详情图片</td>
                                    <td><input type="file" id="productDetailPic" accept="image/*" name="image">
                                    </td>
                                </tr>
                                <tr class="submitTR">
                                    <td colspan="2" align="center">
                                        <input type="hidden" name="pid" value="${product.id}">
                                        <input type="hidden" name="type" value="type_detail">
                                        <button type="submit" class="btn btn-success">提 交</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td valign="top">
                <div class="listProductImageDiv">
                    <table class="table table-striped table-bordered table-hover  table-condensed">
                        <thread>
                            <tr class="success">
                                <th>ID</th>
                                <th>产品展示图片</th>
                                <th>删除</th>
                            </tr>
                        </thread>
                        <tbody>
                        <c:forEach items="${pisSingle}" var="pisSingle">
                            <tr>
                                <td>${pisSingle.id}</td>
                                <td><img height="40px" src="/img/product/type_single/${pisSingle.id}.jpg"></td>
                                <td><a deletelink="true" href="admin_productImage_delete?id=${pisSingle.id}"><span class="glyphicon glyphicon-trash"></span></a> </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </td>
            <td width="10%"></td>
            <td>
                <div class="listProductImageDiv">
                    <table class="table table-striped table-bordered table-hover  table-condensed">
                        <thread>
                            <tr class="success">
                                <th>ID</th>
                                <th>产品详情图片</th>
                                <th>删除</th>
                            </tr>
                        </thread>
                        <tbody>
                        <c:forEach items="${pisDetail}" var="pisDetail">
                            <tr>
                                <td>${pisDetail.id}</td>
                                <td><img height="80px" src="/img/product/type_detail/${pisDetail.id}.jpg"></td>
                                <td><a deletelink="true" href="admin_productImage_delete?id=${pisDetail.id}"><span class="glyphicon glyphicon-trash"></span></a> </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
    </table>











</div>