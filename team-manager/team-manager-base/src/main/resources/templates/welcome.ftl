<!DOCTYPE html>
<html lang="en">
<head>
    <script src="${ctx}/static/js/jquery-1.7.2.js"></script>
    <script src="${ctx}/plugins/tab/js/framework.js"></script>
    <link href="${ctx}/plugins/tab/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <link  rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/plugins/tab/" />
    <script src="${ctx}/plugins/tab/js/tab.js"></script>
</head>
<body>
<div id="tab_menu" style="height:30px;"></div>
<div style="width:100%;">
    <div id="page" style="width:100%;height:100%;"></div>
</div>
</body>
<script>
    function tabAddHandler(mid,mtitle,murl){
        tab.update({
            id :mid,
            title :mtitle,
            url :murl,
            isClosed :true
        });
        tab.add({
            id :mid,
            title :mtitle,
            url :murl,
            isClosed :true
        });

        tab.activate(mid);
    }
    var tab;
    $( function() {
        tab = new TabView( {
            containerId :'tab_menu',
            pageid :'page',
            cid :'tab1',
            position :"top"
        });
        tab.add( {
            id :'tab1_index1',
            title :"主页",
            url :"${ctx}/default",
            isClosed :false
        });
    });

    function cmainFrameT(){
        var hmainT = document.getElementById("page");
        var bheightT = document.documentElement.clientHeight;
        hmainT .style.width = '100%';
        hmainT .style.height = (bheightT  - 41) + 'px';
    }
    cmainFrameT();
    window.onresize=function(){
        cmainFrameT();
    };

</script>
</html>

