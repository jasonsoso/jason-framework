<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${functionName}管理</title>
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/common-header.jsp" %>
<link rel="stylesheet" type="text/css" href="${r"${ctx}"}/resources/css/style.css"/>
<script src="${r"${ctx}"}/resources/js/admin.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<!-- Header Start -->
		<%@include file="/common/header.jsp" %>
		<!-- Header End -->
		<!-- Container Start -->
		<div id="container">
			<%@include file="/common/left.jsp" %>
			<div id="main-content">
			
				<form:form method="post" modelAttribute="${className}" id="form">
				<input type="hidden" name="_method" value="${r"${_method }"}" />

				<h3 class="page-title">${functionName}管理</h3>
				<div class="title-line"></div>
				<div class="databox">
					<div class="databox-title">${functionName}管理&nbsp;（所有带有<span class="red" style="color: red;" >*</span>为必填项）</div>
					<div class="databox-body">
						<div class="changepwd formbox">
							<#list columns as columns>
							<#if columns.name!="id">
							<div class="form-group">
								<span class="form-title">${columns.comment}：</span>
								<form:input path="${columns.property}" cssClass="required ipt ipt-size1" />
								<!--<span class="red" style="color: red;" >*</span>-->
							</div>
							</#if>
							</#list>
							
							<input id="ok" type="submit" value="提交" class="btn" />&nbsp;
							<input id="back" type="button" value="返回" class="btn"/>
						</div>
					</div>
				</div>
				</form:form>
				
			</div>	
		</div>
		<!-- Container End -->
		<!-- Footer Start -->
		<%@include file="/common/footer.jsp" %>
		<!-- Footer End -->
		<script src="${r"${ctx}"}/resources/js/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
		<script src="${r"${ctx}"}/resources/js/jquery-validation/localization/messages_zh.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			$(function(){
				$("#form").validate(); 
				$("#back").click(function(){
					location.href = "${r"${ctx}"}${urlPrefix}/list/";
				});
			});
		</script>
</body>
</html>
