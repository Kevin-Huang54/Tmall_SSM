<!DOCTYPE html>
<%@page language="java" isELIgnored="false" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/jsp/include/admin/adminHeader.jsp"%>
<%@include file="/WEB-INF/jsp/include/admin/adminNavigator.jsp"%>
<script>
    $(function () {
        $("#editForm").submit(function () {
            if (!checkEmpty("name", "属性名称")) {
                return false;
            }
        });
    });
</script>

<title>属性编辑</title>

<div class="workingArea">
    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑属性</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_property_update">
                <table class="editTable">
                    <tr>
                        <td>属性名称</td>
                        <td><input type="text" value="${p.name}" id="name" name="name" class="form-control"></td>
                        <td><input type="hidden" name="id" value="${p.id}"></td>
                        <td><input type="hidden" name="cid" value="${p.cid}"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="4" align="center"><button type="submit" class="btn btn-success">提 交</button></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>