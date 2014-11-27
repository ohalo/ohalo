<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>股票信息推荐</title>

<script type="text/javascript">
	function domain() {
		document.getElementById("myForm").submit()
	}
	setInterval("domain()", 30000);
</script>
</head>
<body>
	购买股票推荐

	<form id="myForm" action="stocks" method="post">
		<input text="text" 
			name="oprType" value="query" style="display:none;"><br> <input type="submit"
			value="提交" style="display:none;" />
	</form>

	<table>
		<tr>
			<td>序列号</td>
			<td align="center">股票代码</td>
			<td align="center">买入价格</td>
			<td align="center">购买时间</td>
			<td align="center">操盘盈利</td>
			<td align="center">操作类型</td>
			<!-- 自定义标签 -->
		</tr>
		<c:forEach items="${stockInfos}" var="stock" varStatus="vs">
			<tr>
				<td><s:property value="#vs.index+1" /></td>
				<td align="center">${stock.stockCode}</td>
				<td align="center">${stock.currentprice}</td>
				<td align="center">${stock.currentDate} </td>
				<td align="center">${stock.profitStockPrice}  </td>
				<td align="center">${stock.type}</td>
				<!-- 自定义标签 -->
			</tr>
		</c:forEach>
	</table>
</body>
</html>