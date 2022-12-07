<#include "/shared/layout.ftl">
<@layout>
    <style type="text/css">
        .ddd {
            background-color: #EEF3FF
        }
    </style>
    <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
    <#include "/shared/techSupportHeader.ftl">
    <div class="mini-fit" style="overflow:hidden;">
        <iframe id="mainFrame1" name="mainFrame1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"
                width="100%"
                height="97%"
                style="background: white;margin-top:-5px"
                src="/techSupport/all?State=9&pageName=CasesBrowse&MenuID=25" onload="closeLoading();"></iframe>
    </div>
    <div class="mini-window" title="案件级别明细" width="600" height="300" id="cLevelWindow" style="display:none">
        <#if RoleName=="系统管理员">
            <div class="mini-toolbar">
                <a class="mini-button" iconCls="icon-add" onclick="addCLevel">增加</a>
                <a class="mini-button" iconCls="icon-save" onclick="saveCLevel">保存</a>
                <a class="mini-button" iconCls="icon-remove" onclick="removeCLevel">删除</a>
            </div>
        </#if>
        <div class="mini-fit">
            <div class="mini-datagrid" id="grid1" style="width:100%;height:100%"
                 url="/cLevel/getData" sortField="sn" sortOrder="asc" autoload="true"
                    <#if RoleName=="系统管理员">
                        allowCellEdit="true" allowCellSelect="true"
                    </#if>
            >
                <div property="columns">
                    <div type="checkcolumn"></div>
                    <div field="sn" align="center" headerAlign="center">序号
                        <input property="editor" class="mini-textbox"/>
                    </div>
                    <div field="name" headerAlign="center">名称
                        <input property="editor" class="mini-textbox"/>
                    </div>
                    <div field="memo" headerAlign="center">备注
                        <input property="editor" class="mini-textbox"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" title="待处理业务数量统计" width="1300" height="500" id="WaitWindow" style="display:none">
        人员类型：
        <div class="mini-combobox" data="[{text:'技术人员',id:'Tech'},{text:'商务人员',id:'Bus'},{text:'客户',id:'Client'}]"
             valueFromSelect="true" onvaluechanged="typeChanged" value="Tech" id="conType"></div>
        <div class="mini-fit">
            <div autoload="false" class="mini-datagrid" style="width:100%;height:100%" id="WaitGrid"
                 frozenStartColumn="0" frozenEndColumn="2" allowCellWrap="true" showSummaryRow="true"
                 url="/casesTech/getWaitReport" sortField="Num" sortOrder="desc" showPager="false">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="Name" width="100" allowSort="true">人员姓名</div>
                    <div field="Num" width="60" allowSort="true">待处理业务(笔)</div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" title="客户已申报数量统计" width="600" height="400" style="display: none" id="NumberWindow">
        开始日期：
        <div class="mini-datepicker" name="BeginDate" onvaluechanged="dateChange"></div>
        &nbsp;&nbsp;&nbsp;
        结束日期:
        <div class="mini-datepicker" name="EndDate" onvaluechanged="dateChange"></div>
        <input class="mini-hidden" name="ClientNames"/>
        <div class="mini-fit">
            <div autoload="false" class="mini-datagrid" style="width:100%;height:100%" id="NumberGrid"
                 url="/casesTech/getClientReport" sortField="Num" sortOrder="desc" showPager="false">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="Name" width="300" allowSort="true" align="center" headerAlign="center">客户名称</div>
                    <div field="Num" width="80" allowSort="true" align="center" headerAlign="center">定稿数量</div>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var panel0 = "#info0";
        var panel1 = '#info1';
        var ps = [panel0, panel1];
        var grid1 = mini.get('#grid1');

        function doSomething(state, obj) {
            var frame = $('#mainFrame1');
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '正在加载数据，请稍候...'
            });
            var tt = "&pageName=CasesBrowse&MenuID=25";
            if (state == 1) {
                frame.attr('src', '/techSupport/all?State=' + state + tt);
                change(0);
                colorhuanyuan();
                var z1 = document.getElementById("Z1");
                z1.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 2) {
                frame.attr('src', '/techSupport/all?State=' + state + tt);
                change(0);
                colorhuanyuan();
                var z2 = document.getElementById("Z2");
                z2.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";

            } else if (state == 3) {
                frame.attr('src', '/techSupport/all?State=' + state + tt);
                change(3);
                colorhuanyuan();
                var z3 = document.getElementById("Z3");
                z3.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 4) {
                frame.attr('src', '/techSupport/all?State=' + state + tt);
                change(0);
                colorhuanyuan();
                var z4 = document.getElementById("Z4");
                z4.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 10) {
                frame.attr('src', '/techSupport/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(10)
            }else if (state == 5) {
                frame.attr('src', '/techSupport/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(5)
            }
            else if (state == 6) {
                frame.attr('src', '/techSupport/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(6)
            } else if (state == 7) {
                frame.attr('src', '/techSupport/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(7)
            } else if (state == 8) {
                frame.attr('src', '/techSupport/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(8)
            }   else if (state == 9) {
                frame.attr('src', '/techSupport/all?State=' + state + tt);
                change(0);
                colorhuanyuan();
                var z0 = document.getElementById("Z0");
                z0.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            }
            window.parent.doResize();
        }

        function colorhuanyuan() {
            var a = $(".Jdlcli");
            for (var i = 0; i < a.length; i++) {
                a[i].style.cssText = "background-color:rgb(226,250,252)";
            }
            ;
            var b = $(".Jsjdli");
            for (var i = 0; i < b.length; i++) {
                b[i].style.cssText = "background-color:rgb(230,238,251)";
            }
            ;
            var c = $(".ZLywli");
            for (var i = 0; i < c.length; i++) {
                c[i].style.cssText = "background-color:rgb(63,87,131)";
            }
            ;
            var d = $(".Jsshli");
            for (var i = 0; i < d.length; i++) {
                d[i].style.cssText = "background-color:rgb(232,230,252)";
            }
            var d = $(".xJsshli");
            for (var i = 0; i < d.length; i++) {
                d[i].style.cssText = "background-color:rgb(47,79,79)";
            }
            try {
                for (var i = 1; i <= 20; i++) {
                    var a1 = document.getElementById("J" + i + "span");
                    if (a1) a1.style.cssText = "color:rgb(0,159,205)";
                    var b1 = document.getElementById("J" + i + "h4");
                    if (b1) b1.style.cssText = "color:rgb(1,160,202)";

                    var a2 = document.getElementById("Js" + i + "span");
                    if (a2) a2.style.cssText = "color:rgb(53,102,231)";
                    var b2 = document.getElementById("Js" + i + "h4");
                    if (b2) b2.style.cssText = "color:rgb(51,97,232)";
                    var a3 = document.getElementById("jsh" + i + "span");
                    if (a3) a3.style.cssText = "color:rgb(53,102,231)";
                    var b3 = document.getElementById("jsh" + i + "h4");
                    if (b3) b3.style.cssText = "color:rgb(52,101,232)";
                }
            } catch (e) {

            }
        }

        function changeOne(index) {
            var j1 = document.getElementById("J" + index);
            var j1span = document.getElementById("J" + index + "span");
            var j1h4 = document.getElementById("J" + index + "h4");
            j1.style.cssText = "background-color:  rgba(241, 112, 46, 0.84)";
            j1span.style.cssText = "color:#fff";
            j1h4.style.cssText = "color:#fff";
        }

        function changeSomeDom(index) {
            var Js2 = document.getElementById("Js" + index);
            var Js2span = document.getElementById("Js" + index + "span");
            var Js2h4 = document.getElementById("Js" + index + "h4");
            Js2.style.cssText = "background-color: rgba(241, 112, 46, 0.84)";
            Js2span.style.cssText = "color:#fff";
            Js2h4.style.cssText = "color:#fff";
        }

        function changeSomeEx(index) {
            change(index);
            colorhuanyuan();
            var jsh1 = document.getElementById("jsh" + index);
            var jsh1span = document.getElementById("jsh" + index + "span");
            var jsh1h4 = document.getElementById("jsh" + index + "h4");
            jsh1.style.cssText = "background-color: rgba(241, 112, 46, 0.84)";
            jsh1span.style.cssText = "color:#fff";
            jsh1h4.style.cssText = "color:#fff";
        }

        function closeLoading() {
            setTimeout(function () {
                mini.unmask(document.body);
            }, 500)
        }

        function change(index) {
            for (var i = 0; i < ps.length; i++) {
                if (i == index) {
                    $(ps[i]).addClass('mini-panel-primary');
                } else $(ps[i]).removeClass('mini-panel-primary');
            }
        }

        $(function () {
            $('.file-box-left').click(function () {
                $('.file-box-left').each(function (index, dom) {
                    $(dom).removeClass('ddd');
                });
                $(this).addClass('ddd');
            });
        })

        function updateStateNumbers() {
            var key = (new Date()).getTime();
            var url = '/techSupport/getStateNumber?Key=' + key;
            $.getJSON(url, {}, function (result) {
                if (result.success) {
                    var states = result.data ||[];
                    for (var i=0;i<states.length;i++) {
                        var obj=states[i];
                        var name=obj.name;
                        var con = $('.' + name);
                        if (con.length > 0) {
                            con.text(obj.num);
                        }
                    }
                }
            });
        }

        function refreshData(grid) {
            try {
                var frame = document.getElementById('mainFrame1');
                if (frame) {
                    frame.contentWindow.refreshData();
                }
            } catch (e) {

            }
        }
    </script>
</@js>