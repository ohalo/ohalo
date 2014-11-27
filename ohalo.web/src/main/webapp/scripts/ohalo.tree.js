var demoMsg = {
    async : "正在进行异步加载，请等一会儿再点击...",
    expandAllOver : "全部展开完毕",
    asyncAllOver : "后台异步加载完毕",
    asyncAll : "已经异步加载完毕，不再重新加载",
    expandAll : "已经异步加载完毕，使用 expandAll 方法"
};

function return16Random() {
    var a = parseInt(Math.random() * 10);
    var b = parseInt(Math.random() * 10);
    var c = parseInt(Math.random() * 10);
    var d = parseInt(Math.random() * 10);
    var e = parseInt(Math.random() * 10);
    var f = parseInt(Math.random() * 10);
    var g = parseInt(Math.random() * 10);
    var h = parseInt(Math.random() * 10);
    var i = parseInt(Math.random() * 10);
    var j = parseInt(Math.random() * 10);
    var k = parseInt(Math.random() * 10);
    var l = parseInt(Math.random() * 10);
    var m = parseInt(Math.random() * 10);
    var n = parseInt(Math.random() * 10);
    var o = parseInt(Math.random() * 10);
    var p = parseInt(Math.random() * 10);
    var q = parseInt(Math.random() * 10);
    var randomNum = a + '' + b + '' + c + '' + d + '' + e + '' + f + '' + g + '' + h + '' + i + '' + j + '' + k + '' + l + '' + m + '' + n + '' + o + '' + p + '' + q;
    return randomNum;
}

/*
 var setting = {
 async : {
 enable : true,
 url : "../asyncData/getNodes.php",
 autoParam : ["id", "name=n", "level=lv"],
 otherParam : {
 "otherParam" : "zTreeAsyncTest"
 },
 dataFilter : filter,
 type : "get"
 },
 callback : {
 beforeAsync : beforeAsync,
 onAsyncSuccess : onAsyncSuccess,
 onAsyncError : onAsyncError
 }
 };
 */

var favSetting = {
    async : {
        enable : true,
        url : "queryAllJsonTreeTags.action",
        dataFilter : filter,
        type : "post"
    },
    view : {
        addHoverDom : addHoverDom,
        removeHoverDom : removeHoverDom,
        selectedMulti : false
    },
    edit : {
        enable : true
    },
    callback : {
        beforeRemove : zTreeBeforeRemove,
        beforeRename : zTreeBeforeRename,
        onClick : zTreeOnClick
    }
};

/**
 *在这个节点修改名字之前
 */
function zTreeBeforeRename(treeId, treeNode, newName, isCancel) {
    var updateTagUrl = "updateTag.action";
    $.post(updateTagUrl, {
        "tagCode" : treeNode.id,
        "tagName" : newName,
        "tagParentCode" : treeNode.pid,
        "tagDesc" : '更新节点' + newName
    }, function(data) {
        console.log(data.resultMsg);
    }, "json");
    refushNode();
}

/**
 *在treeId 删除之前
 */
function zTreeBeforeRemove(treeId, treeNode) {
    var delTagUrl = "delTag.action";
    $.post(delTagUrl, {
        "tagCode" : treeNode.id,
    }, function(data) {
        console.log(data.resultMsg);
    }, "json");
    refushNode();
}

function filter(treeId, parentNode, childNodes) {
    if (!childNodes)
        return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
    }
    return childNodes;
}

var tagCount = 1;

function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
        return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加节点' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn)
        btn.bind("click", function() {
            var zTree = $.fn.zTree.getZTreeObj("ohaloFavorite");

            var tagId = return16Random();
            var tagPid = treeNode.id;
            var tagName = "节点 " + (tagCount++);
            fav_addtag_post(tagId, tagName, tagPid, "添加" + tagName + "节点");
            zTree.addNodes(treeNode, {
                id : tagId,
                pId : tagPid,
                name : tagName
            });
            return false;
        });
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};

/**
 *
 * 通过post的形式添加标签节点
 *
 * @param {Object} tagId
 * @param {Object} tagName
 * @param {Object} tagPid
 * @param {Object} tagDesc
 */
function fav_addtag_post(tagId, tagName, tagPid, tagDesc) {
    var addTagUrl = "addTag.action";
    $.post(addTagUrl, {
        "tagCode" : tagId,
        "tagName" : tagName,
        "tagParentCode" : tagPid,
        "tagDesc" : tagDesc
    }, function(data) {
        console.log(data.resultMsg);
    }, "json");
    refushNode();
}

function refushNode() {
    var treeObj = $.fn.zTree.getZTreeObj("ohaloFavorite");
    treeObj.reAsyncChildNodes(null, "refresh");
}

/**
 *
 * 查询内容信息
 *
 * @param {Object} contentDivId 内容id
 */
function fav_searchContent_post(contentDivId, nodeId) {

    var addTagUrl = "searchcontents.action";

    if (nodeId == '1') {
        nodeId = '';
    }

    $.post(addTagUrl, {
        "id" : '',
        "cname" : '',
        "remark" : '',
        "linkInAddress" : '',
        "tagCode" : nodeId
    }, function(data) {
        divContentDataShow(data, contentDivId);
    }, "json");
}

function divContentDataShow(data, contentDivId) {
    var divshow = $("#" + contentDivId);
    divshow.text("");
    // 清空数据
    var entitys = data.entitys;
    for (var i = 0; i < entitys.length; i++) {
        divshow.append('<span id="' + entitys[i].id + '"><a href="' + entitys[i].linkInAddress + '"> ' + entitys[i].cname + ' </a></span>|');
    };
}

function zTreeOnClick(event, treeId, treeNode) {
    fav_searchContent_post("urlInfo", treeNode.id);
}

function fav_save_urlAddress() {
    var cname = $('#cname').attr("value");
    var urlAddress = $('#urlAddress').attr("value");

    if (cname == null || cname == '') {
        alert('添加名称为空!');
        return;
    }

    var treeObj = $.fn.zTree.getZTreeObj("ohaloFavorite");
    var nodes = treeObj.getSelectedNodes();

    if (nodes.length < 1) {
        alert('请选择节点!');
        return;
    }

    var tagCode = nodes[0].id;

    var searchContentUrl = "addcontent.action";

    $.post(searchContentUrl, {
        "cname" : cname,
        "remark" : '添加链接：' + cname + ',地址：' + urlAddress,
        "linkInAddress" : urlAddress,
        "tagCode" : tagCode
    }, function(data) {
        console.log(data.resultMsg);
    }, "json");

}
