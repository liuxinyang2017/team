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
                                <div style="height: 35px;">
                                    <form action="${ctx}/role/list" method="post" name="roleForm" id="roleForm">
                                        <table style="float:left;margin-top:5px;">
                                            <tr>
                                                <td>
                                                    <div class="nav-search">
                                                        <span class="input-icon">
                                                            <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="content" value="${roleSearchable.content!''}" placeholder="这里输入关键词" />
                                                            <i class="ace-icon fa fa-search nav-search-icon"></i>
                                                        </span>
                                                    </div>
                                                </td>
                                                <td style="padding-left:2px;">[@macro.dateRange fromName="beginCreatedTime" toName="endCreatedTime" fromValue=roleSearchable.beginCreatedTime!'' toValue=roleSearchable.endCreatedTime!'' placeholder="创建日期"/]</td>
                                                <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                            </tr>
                                        </table>
                                    </form>

                                    <div style="float:right;margin-top:5px;">
                                        <a class="btn btn-xs btn-success" onclick="diag('角色添加', '${ctx}/role/create', '${page.number}');">新增</a>
                                    </div>
                                </div>


                                <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                    <thead>
                                    <tr>
                                        <th class="center">ID</th>
                                        <th class="center">角色标识</th>
                                        <th class="center">角色名</th>
                                        <th class="center">描述</th>
                                        <th class="center">是否默认角色</th>
                                        <th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
                                        <th class="center">当前状态</th>
                                        <th class="center" colspan="3">操作</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    [#if page?? && page.content?has_content]
                                        [#list page.content as role]
                                        <tr>
                                            <td class="center">${role.id}</td>
                                            <td class="center">${role.identifier}</td>
                                            <td class="center"><a onclick="diag('角色查看', '${ctx}/role/view/${role.id}')" style="cursor:pointer;">${role.name}</a></td>
                                            <td class="center">${role.description!''}</td>
                                            <td class="center">${role.isDefault.getName()}</td>
                                            <td class="center">${role.createdTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                            <td class="center">
                                                [#if role.valid == disableStatus]
                                                    ${role.valid.getName()}
                                                [#else]
                                                    <div class="hidden-sm hidden-xs btn-group">
                                                        <span class="red">${role.valid.getName()}</span>
                                                    </div>
                                                [/#if]
                                            </td>
                                            <td class="center">
                                                <div class="hidden-sm hidden-xs btn-group">
                                                    <a class="green" onclick="diag('角色修改', '${ctx}/role/update/${role.id}', '${page.number}');" style="cursor: pointer;text-decoration:none;">
                                                        <i class="ace-icon fa fa-pencil bigger-130" title="编辑"></i>
                                                    </a>
                                                </div>
                                            </td>
                                            <td class="center">
                                                <div class="hidden-sm hidden-xs btn-group">
                                                    <a class="purple" onclick="diag('分配权限', '${ctx}/role/resource/allot/${role.id}', '${page.number}');" style="cursor: pointer;text-decoration:none;">
                                                        <i class="ace-icon fa fa-sitemap bigger-130" title="分配权限"></i>
                                                    </a>
                                                </div>
                                            </td>
                                            <td class="center">
                                                <div class="hidden-sm hidden-xs btn-group">
                                                    [#if role.valid == disableStatus]
                                                        <a class="blue" href="${ctx}/role/enable/${role.id}" onclick="return confirm('确定要启用[${role.name}]角色吗?');">
                                                            <i class="ace-icon fa fa-unlock bigger-130" title="启用"></i>
                                                        </a>
                                                    [#else]
                                                        <a class="red" href="${ctx}/role/disable/${role.id}" onclick="return confirm('确定要禁用[${role.name}]角色吗?');">
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
                                                <div style="float: right;padding-top: 0px;margin-top: 0px;">[@macro.pagination page=page/]</div>
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
    [#include "${ctx}/common/list.ftl"/]
</html>