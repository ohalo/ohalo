<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>股票信息推荐</title>
</head>
<body>
	添加买入股票信息

	<input type="button" value="添加股票">
	<input type="button" value="推荐股票">



	<form action="stock" method="post">
		操作类型（增加 insert，删除 delete，修改 update，查询 query）：<input text="text"
			name="oprType" value=""><br> 股票代码：<input type="text"
			name="stockCode" value="" /><br> 买入价格：<input type="text"
			name="buyPrice" value="" /><br> 买入数量：<input type="text"
			name="buyNum" value="" /><br> 所属机构代码：<input type="text"
			name="teamOrg" value="" /><br> <br> <input type="submit"
			value="提交" />
	</form>

	<table>
		<tr>
			<td>序列号</td>
			<td align="center">股票代码</td>
			<td align="center">买入价格</td>
			<td align="center">买入数量</td>
			<td align="center">当前价格</td>
			<!-- 自定义标签 -->
		</tr>
		<c:forEach items="${rsinfos}" var="code" varStatus="vs">
			<tr>
				<td><s:property value="#vs.index+1" /></td>
				<td align="center">${code.stockCode}</td>
				<td align="center">${code.buyPrice}</td>
				<td align="center">${code.buyNum}</td>
				<!-- 自定义标签 -->
			</tr>
		</c:forEach>
	</table>
	<br>
	
	抛出股票：<br>
	股票代码：买入价格：当前价格：<br>
	<br>
	买入股票（买入股票在1w以内）：<br>
	股票代码：买入价格：<br>
</body>
</html>