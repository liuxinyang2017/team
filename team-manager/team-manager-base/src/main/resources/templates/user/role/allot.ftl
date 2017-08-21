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
                                <form action="${ctx}/user/role/allot" name="userForm" id="userForm" method="post">
                                    <input type="hidden" name="userInfo.id" value="${userForm.userInfo.id}"/>

                                    <h3 class="header smaller lighter blue">
                                        请为[<span class="red">${userForm.userInfo.username}</span>]分配角色
                                    </h3>

                                    <div class="row">
                                        <div class="col-xs-12 col-sm-5">
                                            <div class="control-group">

                                                [#if roles?has_content]
                                                    [#list roles as role]
                                                        <div class="checkbox">
                                                            <label>
                                                                <input name="roleIdList" type="checkbox" class="ace" value="${role.id}" [#if userForm.roleIdList?seq_contains(role.id)]checked[/#if]>
                                                                <span class="lbl">${role.name}</span>
                                                            </label>
                                                        </div>
                                                    [/#list]
                                                [/#if]
                                            </div>
                                        </div>
                                    </div>

                                    <hr/>

                                    <div class="row">
                                        <div class="col-xs-12 col-sm-5" style="text-align: center;">
                                            <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                                        </div>
                                    </div>
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
            $("#userForm").submit();
        }
    </script>
</html>