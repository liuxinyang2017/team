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
                                <form action="${ctx}/user/password/change" name="userForm" id="userForm" method="post">
                                    <div style="padding-top: 13px;"></div>
                                    <table id="table_report" class="table table-striped table-bordered table-hover">
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">用户名:</td>
                                            <td>${userForm.userInfo.username}</td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">旧密码:</td>
                                            <td><input type="password" name="userInfo.password" id="password" maxlength="32" placeholder="这里输入旧密码" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">新密码:</td>
                                            <td><input type="password" name="newPassword" id="newPassword" maxlength="32" placeholder="这里输入新密码" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="width:79px;text-align: right;padding-top: 13px;">确认密码:</td>
                                            <td><input type="password" name="conPassword" id="conPassword" maxlength="32" placeholder="这里输入确认密码" style="width:98%;"/></td>
                                        </tr>
                                        <tr>
                                            <td style="text-align: center;" colspan="10">
                                                <a class="btn btn-mini btn-primary" onclick="save();">提交</a>
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
            $("#userForm").submit();
        }
    </script>
</html>