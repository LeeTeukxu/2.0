<#include "/shared/layout.ftl">
<@layout>
    <style type="text/css">
        .ddd {
            background-color: #EEF3FF
        }
    </style>
    <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
    <#include "/shared/casesHeaderNew.ftl">
    <div class="mini-fit" style="overflow:hidden;">
        <iframe id="mainFrame1" name="mainFrame1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"
                width="100%"
                height="97%"
                style="background: white;margin-top:-5px"
                src="/casesAll/index?State=9&pageName=CasesBrowse&MenuID=25" onload="closeLoading();"></iframe>
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
    <div class="mini-window" title="待处理业务工作量统计" width="1300" height="550" id="WaitWindow" style="display:none">
        人员分类：<div class="mini-combobox" data="[{text:'技术人员',id:'Tech'},{text:'商务人员',id:'Bus'},{text:'客户',id:'Client'}]"
                  valueFromSelect="true" onvaluechanged="typeChanged" value="Tech" id="conType"
                  style="margin-bottom:6px;width:120px"></div>
        <div class="mini-fit">
            <div autoload="false" class="mini-datagrid" style="width:100%;height:100%" id="WaitGrid"
                 ondrawsummarycell="onDraw"
                 frozenStartColumn="0" frozenEndColumn="2" allowCellWrap="true" showSummaryRow="true"
                 url="/casesTech/getWaitReport" sortField="Num" sortOrder="desc" showPager="false">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="Name" width="100" allowSort="true">人员姓名</div>
                    <div field="Num" width="60" allowSort="true">待处理业务</div>
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
    <div class="mini-window" title="工作数量统计" width="1200" height="500" style="display: none" id="WorkWindow">
        开始日期：
        <div class="mini-datepicker" name="WorkBeginDate" onvaluechanged="workDateChange"></div>
        &nbsp;&nbsp;&nbsp;
        结束日期:
        <div class="mini-datepicker" name="WorkEndDate" onvaluechanged="workDateChange"></div>
        <div class="mini-fit">
            <div autoload="false" class="mini-datagrid" style="width:100%;height:100%" id="WorkGrid" ondrawcell="onWorkDraw"
                 url="/casesTech/getClientReport" sortField="Num" sortOrder="desc" showPager="false">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="TechMan" width="300" allowSort="true" align="center" headerAlign="center">专利代理人</div>
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
        var panel2 = '#info2';
        var panel3 = '#info3';
        var panel4 = "#info4";
        var ps = [panel0, panel1, panel2, panel3, panel4];
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
                frame.attr('src', '/cases/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(1)
            } else if (state == 2) {
                frame.attr('src', '/cases/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(2)
            } else if (state == 3) {
                frame.attr('src', '/cases/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(3)
            } else if (state == 4) {
                frame.attr('src', '/cases/index?State=' + state + tt);
                change(1);
                colorhuanyuan();
                changeOne(4)
            } else if (state == 50) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(2);
                colorhuanyuan();
                changeSomeDom(1);
            } else if (state == 51) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(2);
                colorhuanyuan();
                changeSomeDom(2);
            } else if (state == 52) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(2);
                colorhuanyuan();
                changeSomeDom(3)
            } else if (state == 53) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(2);
                colorhuanyuan();
                changeSomeDom(4)
            } else if (state == 54) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(2);
                colorhuanyuan();
                changeSomeDom(5)
            } else if (state == 55) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(2);
                colorhuanyuan();
                changeSomeDom(7);
            } else if (state == 56) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(2);
                colorhuanyuan();
                changeSomeDom(6);
            } else if (state == 61) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                changeSomeEx(2)
            } else if (state == 62) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                changeSomeEx(1)
            } else if (state == 63) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                changeSomeEx(4);
            } else if (state == 64) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                changeSomeEx(3);
            } else if (state == 70) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(4);
                colorhuanyuan();
                var z3 = document.getElementById("jsh55");
                z3.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 90) {
                frame.attr('src', '/casesTech/index?State=' + state + tt);
                change(4);
                colorhuanyuan();
                var z3 = document.getElementById("jsh44");
                z3.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 7) {
                frame.attr('src', '/casesAll/index?State=' + state + tt);
                change(0);
                colorhuanyuan();
                var z2 = document.getElementById("Z2");
                z2.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 8) {
                frame.attr('src', '/casesAll/index?State=' + state + tt);
                change(0);
                colorhuanyuan();
                var z3 = document.getElementById("Z3");
                z3.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 9) {
                frame.attr('src', '/casesAll/index?State=' + state + tt);
                change(0);
                colorhuanyuan();
                var z1 = document.getElementById("Z1");
                z1.style.cssText = "background-color: rgba(247, 140, 24, 0.85);";
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
                for (var i = 1; i <= 11; i++) {
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
            var url = '/cases/getStateNumbers?Key=' + key;
            $.getJSON(url, {}, function (result) {
                if (result.success) {
                    var states = result.data || {};
                    for (var state in states) {
                        var con = $('.x' + state);
                        if (con.length > 0) {
                            con.text(states[state]);
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

        function configCLevel() {
            var win = mini.get('#cLevelWindow');
            win.show();
        }

        function addCLevel() {
            var row = {};
            grid1.addRow(row);

        }

        function saveCLevel() {
            var rows = mini.clone(grid1.getData());
            if (rows.length == 0) {
                mini.alert('没有数据可供保存!');
                return;
            }
            var url = '/cLevel/save';
            $.post(url, {Data: mini.encode(rows)}, function (result) {
                if (result.success) {
                    mini.alert('保存成功!', '系统提示', function () {
                        grid1.reload();
                    });
                } else mini.alert(result.message || "保存失败，请稍候重试!");
            })
        }

        function removeCLevel() {
            var row = grid1.getSelected();
            if (row) {
                function g() {
                    var id = row["id"];
                    if (id) {
                        $.post('/cLevel/removeOne', {ID: id}, function (result) {
                            if (result.success) {
                                mini.alert('删除成功!', '系统提示', function () {
                                    grid1.removeRow(row);
                                });
                            } else mini.alert(result.message || "删除失败，请稍候重试!");
                        });
                    } else grid1.removeRow(row);
                }

                mini.confirm('确认要删除选择的记录吗？', '删除提示', function (act) {
                    if (act == 'ok') {
                        g();
                    }
                });
            } else mini.alert('请选择要删除的记录!');
        }
        var curState=null;
        function showWait(state) {
            curState=state;
            var url = '/casesTech/getDynamicColumn';
            $.getJSON(url, {Type: 'Tech',State:state}, function (result) {
                if (result.success) {
                    var cols = result.data || [];
                    if (cols.length > 0) {
                        var win = mini.get('WaitWindow');
                        var grid = mini.get('WaitGrid');
                        changeColumns('Tech', grid, mini.clone(cols));
                        win.show();
                        mini.get('conType').setValue('Tech');
                        grid.load({Type: 'Tech', Cols: mini.encode(cols),State:state});
                    }
                }
            })
        }
        function showWork(){
            var cols=[
                {type:'indexcolumn'},
                {align:'center',width:110,field:'TechMan',headerAlign:'center', header:'专利代理人'}
            ];
            var win = mini.get('#WorkWindow');
            win.show();
            var grid = mini.get('WorkGrid');
            grid.setColumns(cols);
            grid.setData([]);
        }
        function showSell(){
            mini.open({
                url:'/sellReport/index',
                width:1500,
                height:600,
                title:'费用分析',
                showModal:true
            });
        }
        function changeColumns(type, grid, cols) {
            grid.setColumns([]);
            var baseCol = [{type: 'indexcolumn'},
                {
                    field: 'Name',
                    header: '人员姓名',
                    width: (type == 'Client' ? 200 : 80),
                    allowSort: true,
                    align: 'center',
                    headerAlign: 'center'
                },
                {
                    field: 'Num',
                    header: '合计',
                    width: 90,
                    allowSort: true,
                    align: 'center',
                    summaryType: 'sum',
                    headerAlign: 'center'
                },
                {
                    header: '是否外协',
                    align: 'center',
                    headerAlign: 'center',
                    columns: [
                        {
                            field: 'OutTechTrue',
                            header: '是',
                            width: 80,
                            align: 'center',
                            summaryType: 'sum',
                            headerAlign: 'center'
                        }, {
                            field: 'OutTechFalse',
                            header: '否',
                            width: 80,
                            align: 'center',
                            summaryType: 'sum',
                            headerAlign: 'center'
                        }
                    ]
                }
            ];
            for (var i = 0; i < cols.length; i++) {
                var col = cols[i];
                col.align = 'center';
                col.width = col.header.toString().length*20;
                col.summaryType = 'sum';
                col.headerAlign = 'center';
            }
            var cc = {header: '待处理业务明细(天)', align: 'center', headerAlign: 'center', columns: cols};
            baseCol.push(cc);
            grid.setColumns(baseCol);
        }
        function typeChanged(e) {
            var val = e.value;
            var grid = mini.get('WaitGrid');
            var url = '/casesTech/getDynamicColumn';
            grid.setData([]);
            var iid=mini.loading("正在生成数据，请稍候......");
            $.getJSON(url, {Type: val,State:curState}, function (result) {
                var cols = result.data || [];
                mini.hideMessageBox(iid);
                if (cols.length > 0) {
                    changeColumns(val, grid, mini.clone(cols));
                    grid.load({Type: val, Cols: mini.encode(cols),State:curState});
                }

            });
        }
        function showClientNumber(names) {
            var win = mini.get('NumberWindow');
            var grid = mini.get('NumberGrid');
            win.show();
            mini.getbyName('BeginDate').setValue(null);
            mini.getbyName('EndDate').setValue(null);
            mini.getbyName('ClientNames').setValue(null);
            mini.getbyName('ClientNames').setValue(names);
            grid.setData([]);
        }
        function dateChange() {
            var conBegin = mini.getbyName('BeginDate');
            var conEnd = mini.getbyName('EndDate');

            var beginDate = conBegin.getValue();
            var endDate = conEnd.getValue();
            if (!beginDate && !endDate) return;
            var arg = {};
            if (beginDate) {
                arg.Begin = mini.formatDate(beginDate, 'yyyy-MM-dd');
            }
            if (endDate) {
                arg.End = mini.formatDate(endDate, 'yyyy-MM-dd');
            }
            arg.Names = mini.getbyName('ClientNames').getValue();
            var grid = mini.get('NumberGrid');
            grid.load(arg);
        }
        function onDraw(e){
            if(e.value){
                e.cellStyle="text-align:center";
            }
        }
        function workDateChange(){
            var conBegin = mini.getbyName('WorkBeginDate');
            var conEnd = mini.getbyName('WorkEndDate');

            var beginDate = conBegin.getValue();
            var endDate = conEnd.getValue();
            if (!beginDate) return;
            var arg = {};
            if (beginDate) {
                arg.Begin = mini.formatDate(beginDate, 'yyyy-MM-dd');
            }
            if (endDate) {
                arg.End = mini.formatDate(endDate, 'yyyy-MM-dd');
            }
            var grid = mini.get('WorkGrid');
            changeWorkColumn(arg,grid,function(){
                grid.setUrl('/casesTech/getWorkDays');
                grid.load(arg);
            })
        }
        function changeWorkColumn(arg,grid,callback){
            var url='/casesTech/getWorkColumns';
            $.getJSON(url,arg,function(result){
                if(result.success){
                    var columns=result.data || [];
                    var cols=[
                        {type:'indexcolumn'},
                        {align:'center',width:110,field:'TechMan',headerAlign:'center',header:'专利代理人'}
                    ];
                    for(var i=0;i<columns.length;i++){
                        var col=columns[i];
                        var cc={};
                        cc.align = 'center';
                        cc.width =90;
                        cc.field=col;
                        cc.header=col;
                        cc.headerAlign = 'center';
                        cols.push(cc);
                    }
                    grid.setColumns(cols);
                    grid.setFrozenStartColumn(0);
                    grid.setFrozenEndColumn(1);

                    grid.setData([]);
                    if(callback)callback();
                }
            })
        }
        function onWorkDraw(e){
            var field=e.field;
            if(field){
                if(field!="TechMan"){
                    var val=parseInt(e.record[field] || 0);
                    if(val==0)e.cellHtml="";
                }
            }
        }
    </script>
</@js>