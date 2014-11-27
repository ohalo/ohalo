<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>我的收藏夹</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles/ohalo.favorite.css" />
<link rel="stylesheet" href="scripts/ztree/css/demo.css" type="text/css">
<link rel="stylesheet"
	href="scripts/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="scripts/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="scripts/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="scripts/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="scripts/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="scripts/ohalo.tree.js"></script>

<style type="text/css">
</style>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$.fn.zTree.init($("#ohaloFavorite"), favSetting);
		zTree = $.fn.zTree.getZTreeObj("ohaloFavorite");

		$("#menu").css({
			width : "230px",
			height : "1024px"
		});

		$("#content").css({
			width : "1024px",
			height : "1024px"
		});

	});

	/*$(document).ready(function() {
	 $.fn.zTree.init($("#ohaloFavorite"), setting, childNodes);
	 zTree = $.fn.zTree.getZTreeObj("ohaloFavorite");
	 rMenu = $("#rMenu");
	 });*/

	/*
	 $(document).ready(function() {
	 $.fn.zTree.init($("#treeDemo"), setting);
	 $("#expandAllBtn").bind("click", expandAll);
	 $("#asyncAllBtn").bind("click", asyncAll);
	 $("#resetBtn").bind("click", reset);
	 });
	 */
</script>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>我的收藏夹</h1>
		</div>
		<div id="menu" class="c_left">
			<div id="ohaloFavorite" class="ztree"></div>
		</div>
		<div id="content" class="c_right">
			<div id="addUrlInfo">
				名称： <input class="cite-name-input" id="cname" type="text"
					maxlength="128" label="请输入网站名称"> 网址： <input
					class="cite-url-input" type="text" maxlength="1024" id="urlAddress"
					label="请输入网址"> <input id="urlSubmit" type="button"
					onclick="fav_save_urlAddress();" value="保存">
			</div>
			<div id="urlInfo"></div>
		</div>
		<div id="footer">ohalo.cn|我的收藏夹</div>
	</div>

	<div id="demoMsg"></div>
</body>
</html>
