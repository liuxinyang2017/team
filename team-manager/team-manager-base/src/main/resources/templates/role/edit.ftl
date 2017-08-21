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
                                <form action="${ctx}/role/${action}" name="roleForm" id="roleForm" method="post">
                                    <div style="padding-top: 13px;"></div>
                                    <input type="hidden" name="role.id" value="${roleForm.role.id!''}"/>
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">角色标识:</td>
                                            <td><input type="text" name="role.identifier" id="identifier" value="${roleForm.role.identifier!''}" maxlength="32" placeholder="这里输入角色标识" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">角色名:</td>
                                            <td><input type="text" name="role.name" id="name" value="${roleForm.role.name!''}" maxlength="32" placeholder="这里输入角色名" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">描述:</td>
                                            <td><input type="text" name="role.description" id="description" value="${roleForm.role.description!''}" maxlength="32" placeholder="这里输入描述" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">是否默认角色:</td>
                                            <td>
                                                [@macro.selectEnum name="role.isDefault" enumObj=roleForm.role.isDefault!yesNoStatusList[0] dataList=yesNoStatusList header=false/]
                                            </td>
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
            $("#roleForm").submit();
        }
    </script>
</html>