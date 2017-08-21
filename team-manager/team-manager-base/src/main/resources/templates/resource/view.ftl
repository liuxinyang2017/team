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
                                        <td style="padding-top: 13px;">${resource.id}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">资源标识:</td>
                                        <td style="padding-top: 13px;">${resource.identifier!''}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">资源名:</td>
                                        <td style="padding-top: 13px;">${resource.name}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">资源类型:</td>
                                        <td style="padding-top: 13px;">${resource.type.getName()}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">级别:</td>
                                        <td style="padding-top: 13px;">${resource.treeLevel}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">优先级:</td>
                                        <td style="padding-top: 13px;">${resource.priority}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">创建时间:</td>
                                        <td style="padding-top: 13px;">${resource.createdTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">修改时间:</td>
                                        <td style="padding-top: 13px;">${resource.updatedTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">当前状态:</td>
                                        <td style="padding-top: 13px;">${resource.valid.getName()}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">icon-class:</td>
                                        <td style="padding-top: 13px;">${resource.resourceIcon!''}</td>
                                    </tr>
                                    <tr>
                                        <td style="width:79px;text-align: right;padding-top: 13px;">备注:</td>
                                        <td style="padding-top: 13px;">${resource.memo!''}</td>
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