[#ftl strip_whitespace=true]
<!DOCTYPE html>
<html>
    <head>
        [#include "${ctx}/common/head.ftl"/]
    </head>
    <body class="no-skin">
        <div class="main-container" id="main-container">
            <div class="main-content">
                <div class="main-content-inner">
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div style="padding-top: 13px;"></div>
                                <table id="table_report" class="table table-striped table-bordered table-hover">
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">编号:</td>
                                        <td style="padding-top: 13px;">${role.id}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">角色标识:</td>
                                        <td style="padding-top: 13px;">${role.identifier}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">角色名:</td>
                                        <td style="padding-top: 13px;">${role.name}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">描述:</td>
                                        <td style="padding-top: 13px;">${role.description!''}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">是否默认角色:</td>
                                        <td style="padding-top: 13px;">${role.isDefault.getName()}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">创建时间:</td>
                                        <td style="padding-top: 13px;">${role.createdTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">修改时间:</td>
                                        <td style="padding-top: 13px;">${role.updatedTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">当前状态:</td>
                                        <td style="padding-top: 13px;">${role.valid.getName()}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    [#include "${ctx}/common/footer.ftl"/]
</html>