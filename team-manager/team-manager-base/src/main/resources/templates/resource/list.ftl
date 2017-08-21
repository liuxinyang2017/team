[#ftl strip_whitespace=true]
[#import "${ctx}/common/macroes.ftl" as macro]
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
                                <table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
                                    <thead>
                                        <tr>
                                            <th class="center">ID</th>
                                            <th class="center">资源标识</th>
                                            <th class="center">资源名</th>
                                            <th class="center">资源url</th>
                                            <th class="center">资源类型</th>
                                            <th class="center">优先级</th>
                                            <th class="center">父级ID</th>
                                            <th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
                                            <th class="center">当前状态</th>
                                            <th class="center" colspan="4">操作</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        [#if resourceList?has_content]
                                            [#list resourceList as resource]
                                                <tr>
                                                    <td class="center">${resource.id}</td>
                                                    <td class="center">${resource.identifier!''}</td>
                                                    <td class="center"><a onclick="diag('资源查看', '${ctx}/resource/view/${resource.id}')" style="cursor:pointer;">${resource.name}</a></td>
                                                    <td class="center">${resource.url!''}</td>
                                                    <td class="center">${resource.type.getName()}</td>
                                                    <td class="center">${resource.priority}</td>
                                                    <td class="center">${resource.parentId!''}</td>
                                                    <td class="center">${resource.createdTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                                    <td class="center">
                                                        [#if resource.valid == disableStatus]
                                                            ${resource.valid.getName()}
                                                        [#else]
                                                            <div class="hidden-sm hidden-xs btn-group">
                                                                <span class="red">${resource.valid.getName()}</span>
                                                            </div>
                                                        [/#if]
                                                    </td>
                                                    <td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            <a class="green" onclick="diag('资源修改', '${ctx}/resource/update/${resource.id}');" style="cursor: pointer;text-decoration:none;">
                                                                <i class="ace-icon fa fa-pencil bigger-130" title="编辑"></i>
                                                            </a>
                                                        </div>
                                                    </td>
                                                    <td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            [#if !resource.end]
                                                                <a class="green" onclick="diag('资源添加', '${ctx}/resource/create?resource.parent.id=${resource.id}');" style="cursor: pointer;">
                                                                    <i class="ace-icon fa fa-plus bigger-120" title="添加子资源"></i>
                                                                </a>
                                                            [/#if]
                                                        </div>
                                                    </td>
                                                    <td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            [#if !resource.end]
                                                                <a class="purple" href="${ctx}/resource/list?resource.parent.id=${resource.id}">
                                                                    <i class="ace-icon fa fa-sitemap bigger-120" title="查看子资源"></i>
                                                                </a>
                                                            [/#if]
                                                        </div>
                                                    </td>
                                                    <td class="center">
                                                        <div class="hidden-sm hidden-xs btn-group">
                                                            [#if resource.valid == disableStatus]
                                                                <a class="blue" href="${ctx}/resource/enable/${resource.id}" onclick="return confirm('确定要启用[${resource.name}]资源吗?');">
                                                                    <i class="ace-icon fa fa-unlock bigger-130" title="启用"></i>
                                                                </a>
                                                            [#else]
                                                                <a class="red" href="${ctx}/resource/disable/${resource.id}" onclick="return confirm('确定要禁用[${resource.name}]资源吗?');">
                                                                    <i class="ace-icon fa fa-lock bigger-120" title="禁用"></i>
                                                                </a>
                                                            [/#if]
                                                        </div>
                                                    </td>
                                                </tr>
                                            [/#list]
                                        [/#if]
                                    </tbody>
                                </table>

                                <div class="page-header position-relative">
                                    <table style="width:100%;">
                                        <tr>
                                            <td style="vertical-align:top;">
                                                [#if parentResource??]
                                                    <a class="btn btn-mini btn-success" onclick="diag('资源添加', '${ctx}/resource/create?resource.parent.id=${parentResource.id}');">新增[${parentResource.name}]子资源</a>
                                                [#else]
                                                    <a class="btn btn-mini btn-success" onclick="diag('资源添加', '${ctx}/resource/create');">新增一级资源</a>
                                                [/#if]
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    [#include "${ctx}/common/footer.ftl"/]
</html>