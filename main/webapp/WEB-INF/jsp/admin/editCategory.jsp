<!DOCTYPE html>
<%@page language="java" isELIgnored="false" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/include/admin/adminHeader.jsp"%>
<%@include file="/WEB-INF/jsp/include/admin/adminNavigator.jsp"%>
<script>
    $(function () {
        $("#editForm").submit(function () {
            if (!checkEmpty("name", "分类名称")) {
                return false;
            }
        });
    });
</script>

<title>分类编辑</title>

<div class="workingArea">
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑分类</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_category_update" enctype="multipart/form-data">
                <table class="editTable">
                    <tr>
                        <td>分类名称</td>
                        <td><input type="text" value="${c.name}" id="name" name="name" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>分类图片</td>
                        <td><input type="file" id="categoryPic" accept="image/*" name="image">
                        </td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="id" value="${c.id}">
                        </td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center"><button type="submit" class="btn btn-success">提 交</button> </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>