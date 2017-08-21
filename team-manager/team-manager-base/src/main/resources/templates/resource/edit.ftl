[#ftl strip_whitespace=true]
[#import "${ctx}/common/macroes.ftl" as macro]
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css"  href="${ctx}/static/ace/css/chosen.css" />
        [#include "${ctx}/common/head.ftl"/]
    </head>
    <body class="no-skin">
        <div class="main-container" id="main-container">
            <div class="main-content">
                <div class="main-content-inner">
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <form action="${ctx}/resource/${action}" name="resourceForm" id="resourceForm" method="post">
                                    <div style="padding-top: 13px;"></div>
                                    <input type="hidden" name="resource.id" value="${resourceForm.resource.id!''}"/>
                                    [#if resourceForm.resource.parent?? && resourceForm.resource.parent.id??]
                                        <input type="hidden" name="resource.parent.id" value="${resourceForm.resource.parent.id!''}"/>
                                    [/#if]
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">资源标识:</td>
                                            <td><input type="text" name="resource.identifier" id="identifier" value="${resourceForm.resource.identifier!''}" maxlength="50" placeholder="这里输入资源标识" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">资源名:</td>
                                            <td><input type="text" name="resource.name" id="name" value="${resourceForm.resource.name!''}" maxlength="64" placeholder="这里输入资源名" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">资源url:</td>
                                            <td><input type="text" name="resource.url" id="url" value="${resourceForm.resource.url!''}" maxlength="64" placeholder="这里输入资源url" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">优先级:</td>
                                            <td><input type="number" name="resource.priority" id="priority" value="${resourceForm.resource.priority!''}" maxlength="32" placeholder="这里输入优先级" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">资源类型:</td>
                                            <td>
                                                [@macro.selectEnum name="resource.type" enumObj=resourceForm.resource.type!resourceTypeList[0] dataList=resourceTypeList /]
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">icon-class:</td>
                                            <td><input type="text" name="resource.resourceIcon" id="resourceIcon" value="${resourceForm.resource.resourceIcon!''}" maxlength="64" placeholder="这里输入icon-class" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
                                            <td><input type="text" name="resource.memo" id="memo" value="${resourceForm.resource.memo!''}" maxlength="32" placeholder="这里输入备注" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="text-align: center;" colspan="10">
                                                <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                                <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    [#include "${ctx}/common/footer.ftl"/]

    <script>
        function save(){
            $("#resourceForm").submit();
        }
    </script>
</html>