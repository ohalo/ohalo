<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>我的收藏夹</title>
		<link rel="stylesheet" type="text/css" href="http://ohalo.cn:8080/halo/static/styles/ohalo.favorite.css" />
		<script type="text/javascript" src="http://ohalo.cn:8080/halo/static/scripts/jstree/jquery.js"></script>
		<script type="text/javascript" src="http://ohalo.cn:8080/halo/static/scripts/jstree/jquery.cookie.js"></script>
		<script type="text/javascript" src="http://ohalo.cn:8080/halo/static/scripts/jstree/jquery.hotkeys.js"></script>
		<script type="text/javascript" src="http://ohalo.cn:8080/halo/static/scripts/jstree/jquery.jstree.js"></script>
		<link type="text/css" rel="stylesheet" href="http://ohalo.cn:8080/halo/static/scripts/jstree/jstree/themes/default/style.css"/>
		<style>
			#win {
				/*边框*/
				border: 1px red solid;
				/*窗口的高度和宽度*/
				width: 400px;
				height: 300px;
				/*窗口的位置*/
				position: absolute;
				top: 100px;
				left: 500px;
				/*开始时窗口不可见*/
				display: none;
			}
			/*控制背景色的样式*/
			#title {
				background-color: blue;
				color: red;
				/*控制标题栏的左内边距*/
				padding-left: 3px;
			}
			#cotent {
				padding-left: 3px;
				padding-top: 5px;
			}
			/*控制关闭按钮的位置*/
			#close {
				margin-left: 290px;
				/*当鼠标移动到X上时，出现小手的效果*/
				cursor: pointer;
			}
		</style>

	</head>
	<body>

		<div id="container">
			<div id="header">
				<h1>我的收藏夹</h1>
			</div>
			<div id="menu">
				<div id="ohaloFavorite"></div>
			</div>
			<div id="content">
				Content goes here
			</div>
			<div id="footer">
				ohalo.cn|我的收藏夹
			</div>
		</div>

		<div id="win">
			<div id="title">
				标签信息！<span id="close" onclick="hide()">X</span>
			</div>
			<div id="content">
				标签编码：
				<input type="text" name="tagCode" value="" id="tagCode"/>
				<br />
				标签名称：
				<input type="text" name="tagName" value="" id="tagName"/>
				<br />
				父标编码：
				<input type="text" name="tagParentCode" value="" id="tagParentCode"/>
				<br />
				父标名称：
				<input type="text" name="tagParentName" value="" id="tagParentName"/>
				<br />
				标签描述信息： 				<textarea name="tagDesc" rows="8" cols="40"></textarea>
				<br />
				<input type="button" name="tijiao" value="提交" id="tijiao" onclick="submitTag();"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="reset" name="chongzhi" value="重置" id="chongzhi"/>
			</div>
		</div>
	</body>
</html>

<script type="text/javascript" class="source">
    $(function() {
        $("#ohaloFavorite").bind("before.jstree", function(e, data) {
            $("#alog").append(data.func + "<br />");
        }).jstree({
            "plugins" : ["themes", "json_data", "ui", "crrm", "cookies", "dnd", "search", "types", "hotkeys", "contextmenu"],
            "json_data" : {
                "ajax" : {
                    "url" : "queryJsonTreeTags.action",
                    "data" : function(n) {
                        return {
                            "tagParentCode" : n.attr ? n.attr("id") : 1
                        };
                    }
                }
            },
            "search" : {
                "ajax" : {
                    "url" : "./server.php",
                    "data" : function(str) {
                        return {
                            "operation" : "search",
                            "search_str" : str
                        };
                    }
                }
            },
            "types" : {
                "max_depth" : -2,
                "max_children" : -2,
                "valid_children" : ["drive"],
                "types" : {
                    "default" : {
                        "valid_children" : "none",
                        "icon" : {
                            "image" : "http://ohalo.cn:8080/halo/static/scripts/jstree/file.png"
                        }
                    },
                    "folder" : {
                        "valid_children" : ["default", "folder"],
                        "icon" : {
                            "image" : "http://ohalo.cn:8080/halo/static/scripts/jstree/folder.png"
                        }
                    },
                    "drive" : {
                        "valid_children" : ["default", "folder"],
                        "icon" : {
                            "image" : "http://ohalo.cn:8080/halo/static/scripts/jstree/root.png"
                        },
                        "start_drag" : false,
                        "move_node" : false,
                        "delete_node" : false,
                        "remove" : false
                    }
                }
            },
            "ui" : {
                "initially_select" : ["node_4"]
            },
            "core" : {
                "initially_open" : ["node_2", "node_3"]
            },
            "contextmenu" : {
                "items" : {
                    // Some key
                    "create" : {
                        // The item label
                        "label" : "新增标签",
                        // The function to execute upon a click
                        "action" : function(obj) {
                            showWin();
                        },
                        // All below are optional
                        "_disabled" : false, // clicking the item won't do a thing
                        "_class" : "class", // class is applied to the item LI node
                        "separator_before" : false, // Insert a separator before the item
                        "separator_after" : true, // Insert a separator after the item
                        // false or string - if does not contain `/` - used as classname
                        "icon" : false
                    },
                }
            }
        }).bind("create.jstree", function(e, data) {
            $.post("addTag.action", {
                "id" : data.rslt.parent.attr("id").replace("node_", ""),
                "position" : data.rslt.position,
                "title" : data.rslt.name,
                "type" : data.rslt.obj.attr("rel")
            }, function(r) {
                if (r.status) {
                    $(data.rslt.obj).attr("id", "node_" + r.id);
                } else {
                    $.jstree.rollback(data.rlbk);
                }
            });
        }).bind("remove.jstree", function(e, data) {
            data.rslt.obj.each(function() {
                $.ajax({
                    async : false,
                    type : 'POST',
                    url : "./server.php",
                    data : {
                        "operation" : "remove_node",
                        "id" : this.id.replace("node_", "")
                    },
                    success : function(r) {
                        if (!r.status) {
                            data.inst.refresh();
                        }
                    }
                });
            });
        }).bind("rename.jstree", function(e, data) {
            $.post("./server.php", {
                "operation" : "rename_node",
                "id" : data.rslt.obj.attr("id").replace("node_", ""),
                "title" : data.rslt.new_name
            }, function(r) {
                if (!r.status) {
                    $.jstree.rollback(data.rlbk);
                }
            });
        }).bind("move_node.jstree", function(e, data) {
            data.rslt.o.each(function(i) {
                $.ajax({
                    async : false,
                    type : 'POST',
                    url : "./server.php",
                    data : {
                        "operation" : "move_node",
                        "id" : $(this).attr("id").replace("node_", ""),
                        "ref" : data.rslt.cr === -1 ? 1 : data.rslt.np.attr("id").replace("node_", ""),
                        "position" : data.rslt.cp + i,
                        "title" : data.rslt.name,
                        "copy" : data.rslt.cy ? 1 : 0
                    },
                    success : function(r) {
                        if (!r.status) {
                            $.jstree.rollback(data.rlbk);
                        } else {
                            $(data.rslt.oc).attr("id", "node_" + r.id);
                            if (data.rslt.cy && $(data.rslt.oc).children("UL").length) {
                                data.inst.refresh(data.inst._get_parent(data.rslt.oc));
                            }
                        }
                        $("#analyze").click();
                    }
                });
            });
        });

    }); 
</script>
<script type="text/javascript" class="source below">
    // Code for the menu buttons
    $(function() {
        $("#mmenu input").click(function() {
            switch(this.id) {
                case "add_default":
                case "add_folder":
                    $("#demo").jstree("create", null, "last", {
                        "attr" : {
                            "rel" : this.id.toString().replace("add_", "")
                        }
                    });
                    break;
                case "search":
                    $("#demo").jstree("search", document.getElementById("text").value);
                    break;
                case "text":
                    break;
                default:
                    $("#demo").jstree(this.id);
                    break;
            }
        });
    });

    function showWin() {
        /*找到div节点并返回*/
        var winNode = $("#win");
        //方法一：利用js修改css的值，实现显示效果
        // winNode.css("display", "block");
        //方法二：利用jquery的show方法，实现显示效果
        // winNode.show("slow");
        //方法三：利用jquery的fadeIn方法实现淡入
        winNode.fadeIn("slow");
    }

    function submitTag() {
		var tagCode = $("#tagCode");
		alert(tagCode);
    }

    function hide() {
        var winNode = $("#win");
        //方法一：修改css的值
        //winNode.css("display", "none");
        //方法二：jquery的fadeOut方式
        winNode.fadeOut("slow");
        //方法三：jquery的hide方法
        winNode.hide("slow");
    }
</script>