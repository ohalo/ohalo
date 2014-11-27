<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<SCRIPT language="javascript">
	function attachmentDelete(id) {
		var sure = confirm("提示：确定要删除");
		if (sure) {
			var attachGuid = document.getElementById("relUid").value;
			var attachFileName = document.getElementById("name").value;
			var currentUser = document.getElementById("currentUser").value;
			window.location.href="AttachmentDeleteServletUrl.do?currentUser="+currentUser+"&attachGuid="+attachGuid+"&id="+id;
		}

	}
	function check()
	{
		//判断当前工号是否为空
		var currentUser = document.getElementById("currentUser").value;
		if(currentUser=="")
			{
			alert("当前创建人为空，不能上传");
			return false;
			}
		//判断附件个数是否大于5个
		var oFileList = document.getElementById("i_attachmentUp");
		var pchildren = oFileList.childNodes;
		var length = pchildren.length;
    	if(length>=5)
		{
			alert("附件个数不能超过5个！");
			return false;
		}
		//判断是否选择
		var attachFileName = document.getElementById("attachFileName").value;
		if (attachFileName == "") {
			alert("未选择任何上传附件！！");
			return false;
		}
		//判断文件格式
		var attachFileName = document.getElementById("attachFileName").value;
		var attachStr=attachFileName.substring(attachFileName.lastIndexOf(".")+1);
		if(attachStr=="exe" ||attachStr=="bat"|| attachStr=="shell"||attachStr=="bin")
		{
			alert("文件后缀不能为exe,bat,shell,bin");
			return false;
		}
		return true;
	}
</script>
<style type="text/css">

.form_table {
	border-collapse: collapse;
	border: 1px solid #AEC2CD;
	background: #F7FDFD;
	padding-left: 5px;
}

.form_table td {
	height: 25px;
	border: 1px solid #AEC2CD;
}

.form_label {
	background: #EBF3F5;
}

a:link {
	text-decoration: underline;
	color: #00414E;
}

a:hover {
	text-decoration: underline;
	color: FE6C00;
}

a:visited {
	color: #588993;
	text-decoration: underline;
}

body {
	scrollbar-highlight-color: white;
	scrollbar-3dlight-color: #F7FCFD;
	scrollbar-face-color: #E7E7E7;
	scrollbar-arrow-color: #222222;
	scrollbar-shadow-color: #C0D2DB;
	scrollbar-darkshadow-color: #716F64;
	scrollbar-track-color: #F8F7F9;
}

.button {
	height: 20px;
	background: #DCE1E7;
	border-top: 1px #fff solid;
	border-right: 1px #7F9DB9 solid;
	border-bottom: 1px #7F9DB9 solid;
	border-left: 1px #fff solid;
	padding: 2 5;
}

.EOS_table {
	background: #F7FDFD;
	border-collapse: collapse;
	border: 1px solid #AEC2CD;
}

.EOS_table tr td,.EOS_table tr th
	/*  the common style of Result table's td and th */ {
	border: 1px solid #AEC2CD;
	height: 20px;
	padding: 0px 3px;
}

.EOS_table tr th {
	height: 25px;
	vertical-align: middle;
	padding-top: 3px;
	background: #F4F5F9;
	filter: progid:DXImageTransform.Microsoft.gradient(enabled='enabled',
		startColorstr=#D3DAE0, endColorstr=#F4F5F9 );
	text-align: center;
}

.EOS_table_row
	/* the different background color between rows in result table */ {
	background: #EBF3F5;
}

.EOS_table_selectRow
	/* the selected row's background color in result table */ {
	background: #CDDEE6;
}
</style>
</head>
<body>
		<h3 align="center">添加附件</h3>
		
 <form onsubmit="return check()" METHOD="POST" ACTION="/attach/uploadFile.action" ENCTYPE="multipart/form-data">
		<table class="form_table" border="0" width="100%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right" class="form_label" style="font:13px">当前用户：
				<input type="hidden" id="currentUser"  name="currentUser" value=${currentUser}> </td>
				<td style="font:13px;color:red">${currentUser}</td>
			</tr>
			<tr>
				<td align="right" class="form_label" style="font:13px">当前数据唯一标志：
				<input type="hidden" id="relUid"  name="relUid"  value=${attachGuid}></td>
				<td style="font:13px;color:green">${attachGuid}</td>
			</tr>
			<tr>
				<td class="form_label" align="right" style="font:13px">请选择上传文件：</td>
				<td>
						<input type="file" id="files" name="files"
							size="35">&nbsp;<input type="submit" class="button"
							value="上 传"
							style="cursor: pointer" />
					
			</tr>
		</table>
		
			
		<p>
		<table border="1" width="100%" border="1" cellpadding="0"
			cellspacing="0" class="EOS_table">
			<tr style="height:26px">
				<td align="center" style="font:13px">序号</td>
				<td align="center" style="font:13px">附件名称</td>
				<td align="center" style="font:13px">附件大小</td>
				<td align="center" style="font:13px">附件操作</td>
			</tr>
			<tbody id="i_attachmentUp" class="EOS_table">
				<c:forEach var="s" items="${resourceList}">
					<tr height="26px">
						<td align="center" class="form_label" style="font:12px">${s.extra1}</td>
						<td align="center" class="form_label" style="font:12px"><a
							href="AttachmentDownServletUrl.do?path=${s.attachpath}&fileName=${s.attachname}">${s.attachname}</a></td>
						<td align="center" class="form_label" style="font:12px">${s.attachsize}</td>
						<td align="center" class="form_label" style="font:12px">
						<a href="javascript:attachmentDelete('${s.id}')">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
	<c:if test="${message!=''}">
		<script type="text/javascript">
           alert('<c:out value="${requestScope.message}"/>'); 
       </script>
		</c:if>
</body>
</html>
