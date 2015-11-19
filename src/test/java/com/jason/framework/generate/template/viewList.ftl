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
			<form id="myForm" action="${r"${ctx}"}${urlPrefix}/list/" method="get">
			
				<div class="main-inner">
					<div class="main-title">
						<div class="main-title-l fl"><h3 class="fl">一级菜单>${functionName}管理</h3></div>
						<div class="main-title-r fr"><a id="add" href="javascript:void(0);" class="add-btn">添加${functionName}</a></div>
					</div>
					<div class="main-cont">
						<div class="search-bar">
							<div class="search-group fl">
								<span>名称：</span>
								<input class="ipt ipt-size1" name="params[name]" value="${r"${page.params.name }"}" type="text" />
							</div>
		                    <div class="search-group fl">
								<input class="btn" type="button" onclick="javascript:health.search();" value="搜索" />
							</div>
						</div>
						<div class="search-result">
							<table class="table result-table">
								<colgroup>
									<col/>
									<col/>
									<col/>
									<col/>
								</colgroup>
								<thead>
									<tr class="titletr">
										<th class="checktd">
											<div class="checker"><span><input name="items"  id="selectAndUnselect" type="checkbox" class="group-checkable" /></span></div>
										</th>
										<#list columns as columns>
										<#if columns.name!="id">
										<th>${columns.comment}</th>
										</#if>
										</#list>
										<th>操作</th>
									</tr>	
								</thead>
								<tbody>
									<c:choose>
									  	<c:when test="${r"${not empty page.result}"}">
										  	<c:forEach items="${r"${page.result }"}" var="${className}">
										  	<tr>
											    <td class="checktd">
											    	<div class="checker">
											    	<span><input type="checkbox" name="items" id="checkbox" value="${r"${"}${className}${r".id}"}" class="group-checkable" /></span>
											    	</div>
											    </td>
											    <#list columns as columns>
												<#if columns.name!="id">
												<td>${r"${"}${className}.${columns.property}${r"}"}</td>
												</#if>
												</#list>
											    <td  class="do-menu">
													<a href="${r"${ctx}"}${urlPrefix}/${r"${"}${className}${r".id}"}/edit/"  class="modify-btn"><span>编辑</span></a>
											    </td>
										  	</tr>
										  	</c:forEach>
									  	</c:when>
									  	<c:otherwise>
											<tr><td colspan="${columns?size+1}" align="center"><b>暂无内容</b></td></tr>
									  	</c:otherwise>
									  </c:choose>
									  
								</tbody>
							</table>
						</div>
							<!-- Page Start-->
							<jsp:include page="/common/page.jsp" />
						</div>
					</div>
				</form>
			</div>	
		</div>
		<!-- Container End -->
		<!-- Footer Start -->
		<%@include file="/common/footer.jsp" %>
		<!-- Footer End -->
<script type="text/javascript">
	$(function(){
		//选择左边菜单
		$("#${className}_manage").addClass("active open");
		$("#${className}_list").addClass("select");
		
		//提示信息：
		var type = "${r"${message.type}"}";
		var text = "${r"${message.text}"}";
		if(type && text){
			if(type == "error"){
				var d = dialog({
					title: '错误提示：',
				    content: text
				});
				d.show();
				setTimeout(function () {
				    d.close().remove();
				}, 2000);
			}else{
				var d = dialog({
					title: '温馨提示：',
				    content: text
				});
				d.show();
				setTimeout(function () {
				    d.close().remove();
				}, 2000);
				
			}
		}
		$("#del").click(function(){
			var items = health.select();
			if(items && items.length <=0){
				alert("请先选择要删除的内容");
				return false;
			}
			if(confirm("你确定要删除这些内容吗?")){
				$('input[name="_method"]').remove();
				$("#myForm").attr("action", "${r"${ctx}"}${urlPrefix}/delete/")
							.attr("method","post")
							.append('<input type="hidden" name="_method" value="DELETE" />')
							.submit();
				return false;
			}
		});
		$("#add").click(function(){
			location.href = "${r"${ctx}"}${urlPrefix}/create/";
		});
	});
</script>

</body>
</html>
