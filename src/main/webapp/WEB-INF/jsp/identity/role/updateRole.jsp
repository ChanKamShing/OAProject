<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>办公管理系统-添加用户</title>
	<%@ include file="/WEB-INF/taglib.jsp"%>
	<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />
	<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<!-- 导入bootStrap的库 -->
	<script type="text/javascript" src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>

	<script type="text/javascript">
		$(function(){

			// 如果有提示就弹出来
			if ("${tip}") {
				 parent.showTip("${tip}");
			}

			$("#btn_submit").click(function(){
				var name = $("#name").val();
				var remark = $("#remark").val();
				var msg = "" ;
				if(!/^\S{1,30}$/.test($.trim(name))){
					msg = "请输入角色的名称";
				}else if(!/^\S{1,}$/.test($.trim(remark))){
					msg = "请输入角色备注";
				}

				if(msg!=""){
					$.messager.alert("角色提示",msg,"error");
					return ;
				}
				$("#updateRoleForm").submit();
			})
		})
	</script>
</head>
<body style="background: #F5FAFA;">
	<center>
		<form id="updateRoleForm" action="${ctx}/identity/role/updateRole"
			method="post" style="padding: 10px;">
			<input type="hidden" value="${role.id }" name="id" />
			<table class="table-condensed" width="90%" height="100%">
				<tbody>
					<tr>
						<td align="center"><label>角色名称:</label></td>
						<td><input type="text" id="name" name="name"
							value="${role.name}" class="form-control" placeholder="请输入您的角色名称"></td>
					</tr>
					<tr>
						<td align="center"><label>备注:</label></td>
						<td><textarea type="text" id="remark" name="remark"
							class="form-control" placeholder="请输入您的备注信息">${role.remark}</textarea></td>
					</tr>
			</table>
			<div align="center" style="margin-top: 20px;">
				<a id="btn_submit" class="btn btn-info"><span
					class="glyphicon glyphicon-edit"></span>&nbsp;修改</a>
				<button type="reset" class="btn btn-danger">
					<span class="glyphicon glyphicon-remove"></span>&nbsp;重置
				</button>
			</div>
		</form>

	</center>
</body>
</html>
