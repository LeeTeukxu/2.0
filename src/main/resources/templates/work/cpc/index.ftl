<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="application/javascript">
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}];
        var modes=[{id:1,text:'1、选择并导入专利业务交单信息填充案卷包。<br/>'},{id:2,text:'2、手工录入案卷包信息。'}];
        var querFields = [
            {id: 'All', text: '全部属性'},
            {id: 'Famingmc', text: '专利名称'},
            {id: 'FirstInventor', text: '第一发明人'},
            {id: 'Agents', text: '案件代理师'},
            {id: 'NBBH', text: '内部编号'}
        ];
    </script>
    <div class="mini-toolbar">
        <table style="width:100%">
            <tr>
                <td style="width:95%">
                    <a class="mini-button" iconCls="icon-add" id="CPCPackage_Add" onclick="addOne()">新增案卷包</a>
                    <a class="mini-button" iconCls="icon-edit" id="CPCPackage_Edit" onclick="editOne();">编辑</a>
                    <a class="mini-button" iconCls="icon-remove" id="CPCPackage_Remove" onclick="removeOne()">删除</a>
                    <a class="mini-button" iconCls="icon-reload" id="CPCPackage_Browse" onclick="browseOne()">查看</a>
                    <span class="separator"></span>
                    <a class="mini-button" onclick="editAgents" iconCls="icon-user">代理机构信息维护</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconCls="icon-ok" id="CPCPackage_Commit" onclick="buildCPCPackage()">生成案卷包</a>
                    <a class="mini-button" iconCls="icon-download" id="CPCPackage_Download" onclick="dowloadBatch()">批量下载</a>
                </td>
                <td style="white-space:nowrap;">
                    <div class="mini-combobox Query_Field CPCPackage_Query" id="comField"
                         style="width:100px" data="querFields" value="All" id="Field"></div>
                    <input class="mini-textbox Query_Field CPCPackage_Query" style="width:150px"
                           id="QueryText"/>
                    <a class="mini-button mini-button-success" onclick="doQuery();" id="CPCPackage_Query">模糊搜索</a>
                    <a class="mini-button mini-button-danger Query_Field" id="CPCPackage_Reset"
                       onclick="reset()">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%" url="/cpc/getData" autoload="true"
             pageSize="20" multiSelect="true" ondrawcell="onDraw" onselectionchanged="onSelectionChanged">
            <div property="columns">
                <div type="indexcolumn" headerAlign="center">#</div>
                <div type="checkcolumn"></div>
                <div field="Action" width="120" align="center" headerAlign="center">案卷包文件</div>
                <div field="DocSN" width="120" align="center" headerAlign="center">案卷编号</div>
                <div field="Famingmc" width="200" align="center" headerAlign="center">专利名称</div>
                <div field="Shenqinglx" width="80" align="center" headerAlign="center" type="comboboxcolumn">专利类型
                    <input property="editor" class="mini-combobox" data="types"/>
                </div>
                <div field="NBBH" width="360" align="center" headerAlign="center">内部编号</div>
                <div field="FirstInventor" width="120" align="center" headerAlign="center">第一发明人</div>
                <div field="Agents" widt="200" width="120" align="center" headerAlign="center">案件代理师</div>
                <div field="AgentName" width="150" align="center" headerAlign="center">代理机构名称</div>
                <div field="PackageCreateTime" width="150" align="center" headerAlign="center"
                     dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss">案卷生成时间
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" id="AgentWindow" title="代理机构、代理人信息维护" width="1000" height="400">
        <div class="mini-toolbar">
            <a class="mini-button" iconCls="icon-add" onclick="addAgent()">新增</a>
            <a class="mini-button" iconCls="icon-remove" onclick="deleteAgent()">删除</a>
            <a class="mini-button" iconCls="icon-save" onclick="saveAgent()">保存</a>
        </div>
        <div class="mini-fit">
            <div class="mini-tabs" style="width:100%;height:100%" id="tab1">
                <div title="代理机构" name="company">
                    <div class="mini-datagrid" style="width:100%;height:100%" id="grid2" onload="onCompanyload"
                         autoload="true" url="/cpc/getCompanyInfo" allowCellSelect="true" allowCellEdit="true"
                         allowCellValid="true">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div vtype="required" field="OrgCode" width="120" align="center" headerAlign="center">机构代码
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                            <div vtype="required" field="CompanyName" width="240" align="center"
                                 headerAlign="center">公司名称
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                            <div vtype="required" field="Phone" width="120" align="center" headerAlign="center">联系电话
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                            <div vtype="required" field="PostCode" width="120" align="center" headerAlign="center">邮政编码
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div title="代理人" name="agent">
                    <div class="mini-datagrid" style="width:100%;height:100%" id="grid3" url="/cpc/getAgentInfo"
                         autoload="true" allowCellSelect="true" allowCellEdit="true" allowCellValid="true">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div vtype="required" field="CompanyName" width="180" align="center" headerAlign="center">
                                公司名称
                                <input property="editor" class="mini-combobox" url="/cpc/getCompany"
                                       valueFromSelec="true" allowInput="true"/>
                            </div>
                            <div vtype="required" field="Name" width="150" align="center" headerAlign="center">从员姓名
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                            <div vtype="required" field="Code" width="120" align="center" headerAlign="center">从业证号
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                            <div vtype="required;maxLength:18" field="Phone" width="120" align="center"
                                 headerAlign="center">手机号码
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" id="CopyWindow" title="复制案卷包" width="500" height="180" style="display: none">
        <table style="width:100%;height:100%" id="CopyForm">
            <tr>
                <td style="width:80px">案件名称</td>
                <td>
                    <input name="Famingmc" class="mini-textbox" style="width:100%" required="true"/>
                    <input name="MainID" class="mini-hidden"/>
                </td>
            </tr>
            <tr>
                <td style="width:80px">案件类型</td>
                <td>
                    <input name="Shenqinglx" class="mini-combobox" style="width:100%" data="types" required="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <hr/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td  style="text-align: center">
                    <button class="mini-button mini-button-primary" width="80px" onclick="postCopy()">确认</button>
                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    <button class="mini-button mini-button-danger" width="80px">取消</button>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        mini.parse();
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var grid = mini.get('#grid1');
        var grid2 = mini.get('#grid2');
        var grid3 = mini.get('#grid3');
        var tab = mini.get('#tab1');
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
        var querFields = [
            {id: 'All', text: '全部属性'},
            {id: 'Famingmc', text: '专利名称'},
            {id: 'FirstInventor', text: '第一发明人'},
            {id: 'Agents', text: '案件代理师'},
            {id: 'NBBH', text: '内部编号'}
        ];
        function editAgents() {
            var win = mini.get('#AgentWindow');
            win.show();
            grid2.reload();
            grid3.reload();

            var rows = grid2.getData();
            if (rows.length > 0) {
                tab.updateTab(tabAgent, {
                    enabled: true
                });
                tab.activeTab(tabAgent);
            } else {
                tab.updateTab(tabAgent, {enabled: false});
                tab.activeTab(tabCompany);
            }
        }

        var tabAgent = tab.getTab('agent');
        var tabCompany = tab.getTab('company');

        function onCompanyload() {
            var rows = grid2.getData();
            if (rows.length > 0) {
                tab.updateTab(tabAgent, {
                    enabled: true
                });
            } else {
                tab.updateTab(tabAgent, {enabled: false});
            }
        }

        function onDraw(e) {
            var field = e.field;
            var row = e.record;
            if (field == "Action") {
                var id = row["PID"];
                var filePath = row["PackageFilePath"];
                if (filePath) {
                    e.cellHtml = '<a style="text-decoration: underline" href="javascript:download(\'' + id + '\')"">下载</a>';
                }
                var dirPath = row["PackageDirPath"];
                if (dirPath) {
                    e.cellHtml += '&nbsp;&nbsp;&nbsp;<a  style="text-decoration: underline"  href="javascript:viewDocument(\'' + id + '\')">查看</a>';
                    e.cellHtml += '&nbsp;&nbsp;&nbsp;<a  style="text-decoration: underline"  href="javascript:copyDocument(\'' + id + '\')">复制</a>';
                }
            }
        }

        function download(id) {
            var url = '/cpc/downloadCPC?ID=' + id;
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {

                },
                failCallback: function (html, xurl) {
                    mini.alert('下载文件出现错误:' + '<br/>' + html + '<br/>', '系统提示');
                }
            });
            return false;
        }
        function dowloadBatch(){
            var rows=grid.getSelecteds();
            var cs=[];
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                var id=row["PID"];
                var filePath = row["PackageFilePath"];
                if(filePath){
                    cs.push(id);
                }
            }
            if(cs.length==0){
                mini.alert('请选择生成案卷包以后再进行批量下载!');
                return ;
            }
            var url = '/cpc/batchDownload?IDS=' +cs.join(',');
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {

                },
                failCallback: function (html, xurl) {
                    mini.alert('下载文件出现错误:' + '<br/>' + html + '<br/>', '系统提示');
                }
            });
            return false;
        }

        function viewDocument(id) {
            mini.open({
                url: '/cpc/viewPackage?MainID=' + id,
                width: '100%',
                height: '100%',
                showModal: true,
                title: '查看案卷包文件'
            });
        }
        var copyForm=new mini.Form('#CopyForm');
        var copyWin=mini.get('#CopyWindow');
        function copyDocument(id){
            var url='/cpc/copyDocument';
            var rows=grid.findRows(function(row){
                var pId=row["PID"];
                if(pId==id) return true;
            });
            if(rows.length>0){
                var row=rows[0];
                row.MainID=id;
                copyForm.setData(row);
                copyWin.show();
            }
        }
        function postCopy(){
            copyForm.validate();
            if(copyForm.isValid()){
                var data=copyForm.getData();
                var mainId=data.MainID;
                var rows=grid.findRows(function(row){
                    var pId=row["PID"];
                    if(pId==mainId) return true;
                });
                var row=rows[0];
                var Famingmc=row["Famingmc"];
                if(Famingmc==data.Famingmc){
                    mini.alert('复制案卷包时，案卷包名称不能与源包名称相同。');
                    return ;
                }
                var url='/cpc/copyDocument';
                $.post(url,data,function(result){
                    if(result.success){
                        mini.alert('案卷包复制成功！');
                        copyWin.hide();
                        grid.reload();
                    } else mini.alert(result.message || "复制案卷包出错，请稍候重试!");
                })
            } else mini.alert('数据录入不完整，操作被中止!');
        }
        function addOne() {
            var url = '/cpc/getCompanyCount';
            $.getJSON(url, {}, function (result) {
                var num = parseInt(result || 0);
                if (num > 0) {
                    mini.showMessageBox({
                        html:'<div class="mini-radiobuttonlist" repeatItems="1" repeatLayout="flow" id="CreateMode" ' +
                        'repeatDirection="vertical" textField="text" valueField="id" value="1" data="modes" ' +
                            '></div><hr/>',
                        title:'新增案卷包方式',
                        buttons:['ok','cancel'],
                        width:350,
                        height:150,
                        iconCls: "mini-messagebox-question",
                        callback:function(action){
                            if(action=='ok'){
                                var mode=mini.get('#CreateMode').getValue();
                                mini.open({
                                    url: '/cpc/add?CreateType='+mode,
                                    width: '100%',
                                    height: '100%',
                                    title: 'CPC案卷包信息',
                                    onDestroy: function () {
                                        grid.reload();
                                    }
                                });
                            }
                        }
                    });
                    mini.parse();
                } else {
                    mini.alert('没有代理公司信息，无法新建案卷包', '系统提示', function () {
                        editAgents();
                    });
                }
            })
        }

        function editOne() {
            var row = grid.getSelected();
            if (row) {
                var pid = row["PID"];
                mini.open({
                    url: '/cpc/edit?PID=' + pid,
                    width: '100%',
                    height: '100%',
                    title: 'CPC案卷包信息',
                    onDestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function browseOne() {
            var row = grid.getSelected();
            if (row) {
                var pid = row["PID"];
                mini.open({
                    url: '/cpc/browse?PID=' + pid,
                    width: '100%',
                    height: '100%',
                    title: 'CPC案卷包信息',
                    onDestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function buildCPCPackage() {
            var row = grid.getSelected();
            if (row) {
                var mainId = row["PID"];
                var name = row["Famingmc"];

                function g() {
                    var iid = mini.loading('正在打包案卷数据,请稍候.........');
                    var url = '/cpc/buildPackage';
                    $.post(url, {MainID: mainId}, function (result) {
                        mini.hideMessageBox(iid);
                        if (result.success) {
                            mini.alert("【" + name + '】的案券包生成成功!', function () {
                                grid.reload();
                            });
                        } else {
                            mini.alert(result.message || name + '的案券包生成失败:');
                        }
                    })
                }

                var url = '/cpc/checkAll';
                $.post(url, {MainID: mainId}, function (res) {
                    if (res.success) {
                        mini.confirm('正在准备生成【' + name + '】的案卷包，如有已存在的案卷包将会被删除，是否继续？', '删除提示', function (btn) {
                            if (btn == 'ok') g();
                        });
                    } else mini.alert(res.message || "案卷包校验失败，不允许生成案卷包。");
                })
            }
        }

        function removeOne() {
            var row = grid.getSelected();
            if (row) {
                var pid = row["PID"];
                mini.confirm('确认要删除选择的记录吗？', '系统提示', function (act) {
                    if (act == 'ok') {
                        $.post('/cpc/removeOne', {PID: pid}, function (result) {
                            if (result.success) {
                                mini.alert('选择记录删除成功!', '系统提示', function () {
                                    grid.reload();
                                });
                            } else {
                                mini.alert(result.message || "删除失败，请稍候重试!");
                            }
                        })
                    }
                });
            } else {
                mini.alert('请选择要删除的记录!');
            }
        }

        function addAgent() {
            var name = getActiveName();
            if (name == "company") {
                grid2.addRow({});
            } else {
                grid3.addRow({});
            }
        }

        function getActiveName() {
            var actTab = tab.getActiveTab();
            return actTab.name;
        }

        function saveAgent() {
            var name = getActiveName();
            var rows = null;
            var url = '';
            var curGrid = null;
            if (name == "company") {
                rows = grid2.getData();
                url = '/cpc/saveCompanyInfo';
                curGrid = grid2;
            } else {
                rows = grid3.getData();
                url = '/cpc/saveAgentInfo';
                curGrid = grid3;
            }
            curGrid.validate();
            if(curGrid.isValid()){
                $.post(url, {Data: mini.encode(rows)}, function (result) {
                    if (result.success) {
                        mini.alert('保存成功!');
                        curGrid.reload();
                    } else mini.alert(result.message || "保存失败，请稍候重试!");
                })
            } else {
                mini.alert('数据录入不完整，不允许保存!');
            }
        }

        function deleteAgent() {
            var name = getActiveName();
            var row = null;
            var url = '';
            var curGrid = null;
            if (name == "company") {
                row = grid2.getSelected();
                url = '/cpc/removeCompanyInfo';
                curGrid = grid2;
            } else {
                row = grid3.getSelected();
                url = '/cpc/removeAgentInfo';
                curGrid = grid3;
            }

            function g() {
                var id = parseInt(row["ID"] || 0);
                if (id > 0) {
                    $.post(url, {ID: id}, function (result) {
                        if (result.success) {
                            mini.alert('选择的记录删除成功!');
                            curGrid.removeRow(row);
                        } else mini.alert(result.message || "保存失败，请稍候重试!");
                    })
                } else {
                    curGrid.removeRow(row);
                }
            }

            mini.confirm('确认要删除选择的记录吗？', '删除提示', function (act) {
                if (act == 'ok') {
                    g();
                }
            });
        }
        function doQuery() {
            var arg = {};
            var cs = [];
            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        var op = {field: f, oper: 'LIKE', value: word};
                        cs.push(op);
                    }
                } else {
                    var op = {field: field, oper: 'LIKE', value: word};
                    cs.push(op);
                }
            }
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function reset() {
            txtQuery.setValue(null);
            comField.setValue('All');
            doQuery();
        }
        var cmdBatch=mini.get('#CPCPackage_Download');
        function onSelectionChanged(){
            var rows=grid.getSelecteds();
            if(rows.length>=1){
                cmdBatch.show();
            } else cmdBatch.show();
        }
    </script>
</@layout>