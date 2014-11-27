<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>你妈叫你回家吃饭呢</title>
<script
	src="http://webapi.amap.com/maps?v=1.2&key=c6eb8f0bb9e29970f027074563e22c3c"
	type="text/javascript"></script>
</head>
<body>
	你好，该吃饭了!

	<input type="button" value="吃饭地儿">
	<input type="button" value="纠错">



	<form action="res" method="post">
		id: <input type="text" name="id" value="${id }"> <br>
		开始页：<input type="text" value="${skip }"> <br> 分页条数：<input
			type="text" value="${limit }"> <br> 操作类型（增加，删除，修改，查询）：<input
			text="text" name="oprType" value=""><br> 名字：<input
			type="text" name="name" value="" /><br> 地址：<input type="text"
			name="address" value="" /><br> 电话号码：<input type="text"
			name="phoneNum" value="" /><br> 描述:
		<textarea rows="3" cols="10" name="desc"></textarea>
		<br> <input type="submit" value="提交" />
	</form>

	<table>
		<tr>
			<td>序列号</td>
			<td align="center">编码</td>
			<td align="center">名称</td>
			<td align="center">地址</td>
			<td align="center">电话号码</td>
			<!-- 自定义标签 -->
		</tr>
		<c:forEach items="${res}" var="ress" varStatus="vs">
			<tr>
				<td><s:property value="#vs.index+1" /></td>
				<td align="center">${ress.id}</td>
				<td align="center"><a href="#">${ress.name}</a></td>
				<td align="center">${ress.address}</td>
				<td align="center">${ress.phoneNum}</td>
				<!-- 自定义标签 -->
			</tr>
		</c:forEach>
	</table>

	<a href="res?oprType=query&skip=${skip -10 }&limit=${limit }">上一页</a>
	<a href="res?oprType=query&skip=${skip + 10 }&limit=${limit }">下一页</a>
	<br>
	<iframe>

		<div id="container"></div>
		<script type="text/javascript">
			function initialize() {
				var position = new AMap.LngLat(116.404, 39.915);//创建中心点坐标
				var mapObj = new AMap.Map("container", {
					center : position
				});//创建地图实例
			}
		</script>
	</iframe>

</body>
</html>