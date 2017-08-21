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
                                <form action="${ctx}/user/list" method="post" name="userForm" id="userForm">
                                    <table style="margin-top:5px;">
                                        <tr>
                                            <td>
                                                <div class="nav-search">
                                                    <span class="input-icon">
                                                        <input class="nav-search-input" autocomplete="off" id="nav-search-input" type="text" name="content" value="${userSearchable.content!''}" placeholder="这里输入关键词" />
                                                        <i class="ace-icon fa fa-search nav-search-icon"></i>
                                                    </span>
                                                </div>
                                            </td>
                                            <td style="padding-left:2px;">[@macro.dateRange fromName="beginCreatedTime" toName="endCreatedTime" fromValue=userSearchable.beginCreatedTime!'' toValue=userSearchable.endCreatedTime!'' placeholder="创建日期"/]</td>
                                            <td style="vertical-align:top;padding-left:2px;">
                                                [#if userSearchable.valid?? && userSearchable.valid.value??]
                                                    [#assign value = userSearchable.valid.value]
                                                [#else]
                                                    [#assign value = ""]
                                                [/#if]
                                                [@macro.select name="valid" value=value dataList=allEnableDisableStatusList style="vertical-align:top;width: 120px;"/]
                                            </td>
                                            <td style="vertical-align:top;padding-left:2px;"><button class="btn btn-light btn-xs" title="检索" type="submit"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></button></td>
                                        </tr>
                                    </table>
                                </form>

                                <table id="simple-table" class="table table-striped table-bordered table-hover"  style="margin-top:5px;">
                                    <thead>
                                    <tr>
                                        <th class="center">ID</th>
                                        <th class="center">用户名</th>
                                        <th class="center">姓名</th>
                                        <th class="center"><i class="ace-icon fa fa-envelope-o"></i>邮箱</th>
                                        <th class="center">手机号</th>
                                        <th class="center"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
                                        <th class="center">当前状态</th>
                                        <th class="center">操作</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    [#if page?? && page.content?has_content]
                                        [#list page.content as userInfo]
                                        <tr>
                                            <td class='center' style="width: 30px;">${userInfo.id}</td>
                                            <td class="center"><a onclick="diag('用户查看', '${ctx}/user/view/${userInfo.id}')" style="cursor:pointer;">${userInfo.username}</a></td>
                                            <td class="center">${userInfo.name}</td>
                                            <td class="center">${userInfo.email}</td>
                                            <td class="center">${userInfo.mobile}</td>
                                            <td class="center">${userInfo.createdTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                            <td class="center">
                                                [#if userInfo.valid == disableStatus]
                                                    ${userInfo.valid.getName()}
                                                [#else]
                                                    <div class="hidden-sm hidden-xs btn-group">
                                                        <span class="red">${userInfo.valid.getName()}</span>
                                                    </div>
                                                [/#if]
                                            </td>
                                            <td class="center">
                                                <div class="hidden-sm hidden-xs btn-group">
                                                    <a class="blue" onclick="diag('分配角色', '${ctx}/user/role/allot/${userInfo.id}', '${page.number}');" style="cursor: pointer;text-decoration:none;">
                                                        <i class="ace-icon fa fa-user bigger-130" title="分配角色"></i>
                                                    </a>
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