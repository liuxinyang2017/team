<!DOCTYPE html>
<html>
    <head>
        <title>出票综合管理系统</title>
        <#include "${ctx}/common/head.ftl"/>
        <style type="text/css">
            html {
                overflow: hidden;
            }
        </style>
    </head>
    <body class="no-skin">
        <#include "${ctx}/common/header.ftl"/>

        <#--<div id="websocket_button"></div><!-- 少了此处，聊天窗口就无法关闭 &ndash;&gt;-->
        <!-- /section:basics/navbar.layout -->
        <div class="main-container" id="main-container">
            <script type="text/javascript">
                try{ace.settings.check('main-container' , 'fixed')}catch(e){}
            </script>

            <!-- #section:basics/sidebar -->
            <!-- 左侧菜单 -->
            <#include "${ctx}/common/left.ftl">

            <!-- /section:basics/sidebar -->
            <div class="main-content">
                <div class="main-content-inner">

                    <!-- /section:basics/content.breadcrumbs -->
                    <div class="page-content">
                        <div class="row">
                            <div>
                                <iframe name="mainFrame" id="mainFrame" frameborder="0" src="${ctx}/welcome" style="margin:0 auto;width:100%;height:100%;"></iframe>
                            </div>
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->

                </div>
            </div><!-- /.main-content -->

        </div><!-- /.main-container -->

        <#include "${ctx}/common/footer.ftl"/>
        <!-- ace scripts -->
        <script src="${ctx}/static/ace/js/ace/elements.scroller.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.colorpicker.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.fileinput.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.typeahead.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.wysiwyg.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.spinner.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.treeview.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.wizard.js"></script>
        <script src="${ctx}/static/ace/js/ace/elements.aside.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.ajax-content.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.touch-drag.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.sidebar.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.sidebar-scroll-1.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.submenu-hover.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.widget-box.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.settings.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.settings-rtl.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.settings-skin.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.widget-on-reload.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.searchbox-autocomplete.js"></script>

        <script src="${ctx}/static/ace/js/ace/elements.onpage-help.js"></script>
        <script src="${ctx}/static/ace/js/ace/ace.onpage-help.js"></script>

        <!--引入属于此页面的js -->
        <script src="${ctx}/static/js/myjs/head.js"></script>
        <!--引入属于此页面的js -->
        <script src="${ctx}/static/js/myjs/index.js"></script>

        <!--引入弹窗组件end-->
        <!--提示框-->
        <script src="${ctx}/static/js/jquery.tips.js"></script>
    </body>
</html>