[#ftl strip_whitespace=true]
<!DOCTYPE html>
<html>
    <head>
    [#include "${ctx}/common/head.ftl"/]
        <script src="${ctx}/static/js/jquery-1.7.2.js"></script>
    </head>
    <body class="no-skin">
        <div class="main-container" id="main-container">
            <div class="main-content">
                <div class="main-content-inner">
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="error-container">
                                    <h1 class="grey lighter smaller">
                                        <span class="blue bigger-125">
                                            <i class="ace-icon fa fa-frown-o"></i>
                                        </span>
                                        ${errorMessage!'error'}
                                    </h1>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>