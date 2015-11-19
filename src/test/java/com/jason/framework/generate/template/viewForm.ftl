<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${functionName}管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/common/taglibs.jsp" %>
<%@include file="/common/common-header.jsp" %>

</head>
<body>
		<!-- Container Start -->
		<div id="container">
			<!-- Header Start -->
			<%@include file="/common/header.jsp" %>
			<!-- Header End -->
			<!-- Sidebar Start -->
			<%@include file="/common/left.jsp" %>
			<!-- Sidebar End-->
			
			<div id="main">
			
				<form:form method="post" modelAttribute="${className}" id="form">
				<input type="hidden" name="_method" value="${r"${_method }"}" />
				
				<div class="main-inner">
					<div class="main-title">
						<div class="main-title-l fl"><h3 class="fl">第一菜单><c:if test="${r"${_method=='PUT'}"}">修改</c:if><c:if test="${r"${_method!='PUT'}"}">添加</c:if>分类
						</h3></div>
						<!-- <div class="main-title-r fr"><a href="account.html" class="back-btn">返回</a></div> -->
					</div>
					<div class="main-cont">
						<div class="block">
							<#list columns as columns>
							<#if columns.name!="id">
							<div class="control-group">
								<span class="control-label">${columns.comment}：</span>
								<div class="controls">
	                                <form:input path="${columns.property}" cssClass="required ipt ipt-size1" />
	                            </div>
							</div>
							</#if>
							</#list>
	                        
	                        <div class="control-group op-menu">
								<input id="ok" type="submit" value="提交" class="btn" />&nbsp;
								<input id="back" type="button" value="返回" class="btn"/>
	                        </div>
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
				//选择左边菜单
				$("#${className}_manage").addClass("active open");
				$("#${className}_list").addClass("select");
		
				$("#form").validate(); 
				$("#back").click(function(){
					location.href = "${r"${ctx}"}${urlPrefix}/list/";
				});
			});
		</script>
</body>
</html>
