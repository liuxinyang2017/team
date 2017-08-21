[#ftl strip_whitespace=true]
<!DOCTYPE html>
<html>
    <head>
        [#include "${ctx}/common/head.ftl"/]
        <link href="${ctx}/plugins/zTree/3.5/zTreeStyle.css" rel="stylesheet">
    </head>
    <body class="no-skin">
        <div class="main-container" id="main-container">
            <div class="main-content">
                <div class="main-content-inner">
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <form action="${ctx}/role/resource/allot" name="roleForm" id="roleForm" method="post">
                                    <input type="hidden" name="role.id" value="${roleForm.role.id}"/>

                                    <h3 class="header smaller lighter blue">
                                        请为[<span class="red">${roleForm.role.name}</span>]分配权限
                                    </h3>

                                    <div class="row">
                                        <div class="col-xs-12 col-sm-5">
                                            <ul id="resourceTree" class="ztree"></ul>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <hr/>
                        <div class="row">
                            <div class="col-xs-12 col-sm-5" style="text-align: center;">
                                <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    [#include "${ctx}/common/footer.ftl"/]
    <script src="${ctx}/plugins/zTree/3.5/jquery.ztree.all-3.5.min.js"></script>

    <script>
        function save(){
            $("#roleForm").submit();
        }

        var setting = {
            view: {
                fontCss: {'font-family': 'Helvetica, Arial'}
            },
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        var data = ${resources};
        $(function(){
            $.fn.zTree.init($("#resourceTree"), setting, data);

            var treeObj = $.fn.zTree.getZTreeObj("resourceTree");

            $("#roleForm").submit(function(){
                var nodes = treeObj.getCheckedNodes(true);
                for(var i = 0;i < nodes.length;i ++){
                    $(this).append('<input type="hidden" name="resourceIdList[' + i + ']" value="' + nodes[i].id +'"/>');
                }
            });
        });
    </script>
</html>