<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>知朋专利申报监控系统</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta http-equiv="pragma" content="no-cache"/>
        <meta http-equiv="Cache-Control" content="no-store, must-revalidate"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <link rel="shortcut icon" href="http://www.cpquery.com/upload/images/1165242391.png" type="image/x-icon"/>
        <style type="text/css">
            html, html body {
                padding: 0;
                border: 0;
                margin: 0;
                width: 100%;
                height: 100%;
                overflow: hidden;
                font-family: Verdana;
                font-size: 12px;
                line-height: 22px;
            }

            html body .searchbox .mini-buttonedit-icon {
                background: url(/js/miniui/themes/icons/search.gif) no-repeat 50% 50%;
            }

            .k-icon.k-i-calendar {
                margin-top: 4px;
            }

            input.k-textbox {
                border-color: #c7bfbf;
            }

            .mini-layout-region-title {
                color: black;
            }

            .panel-expand {
                background-image: url(/js/miniui/themes/icons/collapse.gif);
            }

            .panel-collapse {
                background-image: url(/js/miniui/themes/icons/collapse.gif);
            }
        </style>
        <script type="text/javascript" src="/js/boot.js"></script>
<#--        <script type="text/javascript" src="/js/columnsContextMenu.js"></script>-->
    </head>
    <body style="width:100%;height:100%;overflow: hidden;">
    <script type="text/javascript">
        mini.parse();
        var pageName = "${PageName?default('')}";
        var currentRoleName = "${CurrentRoleName?default('')}";
        var currentMenuName="${CurrentMenu?default('')}";
        var allFuns =${AllFuns?default('[]')};
        var hasFuns = ${HasFuns?default('[]')};
        //var gridConfig =${GridConfig?default('{}')};
        var gridConfig={};
        function refreshData(grid) {
            if (!grid) {
                var grids = $('.mini-datagrid');
                if (grids.length > 0) {
                    grid = mini.get(grids[0]);
                }
            }
            if (grid) {
                var pa = grid.getLoadParams();
                var pageIndex = grid.getPageIndex() || 0;
                var pageSize = grid.getPageSize() || 20;
                pa = pa || {pageIndex: pageIndex, pageSize: pageSize};
                grid.load(pa);
            }
        }

        $(function () {
            mini.parse();
            var grids = $('.mini-datagrid');
            if (grids.length > 0) {
                for (var i = 0; i < grids.length; i++) {
                    var grid = mini.get(grids[i]);
                    if (grid) {

                        grid.setAllowAlternating(true);
                    }
                }
                var gdx = mini.get(grids[0]);
                if (gridConfig.url && gdx) {
                    gdx.setShowColumnsMenu(false);
                    if(grid.id){
                        //var settor = new gridConfigSetter(gdx);
                        //settor.doApply(gridConfig);
                    }
                }
                //new ColumnsContextMenu(gdx, currentRoleName);
            }
            /*一个页面只有PageName不为空才开启权限控制。*/
            if (pageName) {
                var findCons = $('.mini-button');
                var showFun = null;
                for (var i = 0; i < findCons.length; i++) {
                    var con = findCons[i];
                    var id = $(con).prop('id') || $(con).prop('class');
                    if (id.indexOf(pageName) == -1) continue;
                    var mCon = mini.get(con);
                    if (mCon) {
                        mCon.hide();
                        if (showFun == null) showFun = mCon.show;
                        mCon.show = function () {}
                    }
                }
                for (var i = 0; i < allFuns.length; i++) {
                    var fun = allFuns[i];
                    var cns = [];
                    var conId = pageName + "_" + fun;
                    var con = mini.get(conId);
                    if (con) {
                        cns.push(con);
                    } else {
                        var cons = $('.' + conId);
                        for (var n = 0; n < cons.length; n++) {
                            var cn = cons[n];
                            if (!cn) continue;
                            var cbb = mini.get(cn);
                            if (cbb) {
                                cns.push(cbb);
                            }
                        }
                    }
                    for (var a = 0; a < cns.length; a++) {
                        var con = cns[a];
                        if (hasFuns.indexOf(fun) >= 0) {
                            con.show = showFun;
                            con.show();
                        } else {
                            con.show=function(){}
                            con.hide();
                        }
                    }
                }
            }
        });
        var Integer = {};
        Integer.parse = function (obj) {
            if (obj == null || obj == undefined) return 0;
            if (obj == true) return 1;
            if (obj == false) return 0;
            if (obj == "True" || obj == "true") return 1;
            if (obj == "False" || obj == "false") return 0;
            return parseInt(obj || 0);
        }
        var Utils = {};
        Utils.isEmail = function (emailAddress) {
            var reg = /^([A-Za-z0-9_\-\.\u4e00-\u9fa5])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,8})$/;
            return reg.test(emailAddress);
        }
        Utils.isOrgCode=function(code){
            if(!code || code=="") return false;
            if(code.length!=18) return false;
            var reg=/^[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}$/;
            return reg.test(code);
        }
        Utils.isPostCode=function(code){
            var reg=/^[0-9]{6}$/;
            return reg.test(code);
        }
    </script>
    <#macro js>
    <#nested>
    </#macro>
    <#macro layout>
    <#nested>
    </body>
</html>
     </#macro>
