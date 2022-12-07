<#include "/shared/layout.ftl">
<@js>
    <script type="text/javascript">
        var ctypes = [{id: 1, text: '专利类'}, {id: 2, text: '商标类'}, {id: 3, text: '高企类'},{id: 4, text: '版权类'}];
        var seals = [{id: 1, text: '不需盖章'}, {id: 2, text: '需要盖章'}, {id: 3, text: '已盖章'}];
        var jiaodans = [{id: '未交单', text: '未交单'}, {id: '已交单', text: '已交单'}];
    </script>
</@js>
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<div class="mini-layout" style="width:100%;height:100%">
    <div region="center" showheader="false">
        <div class="mini-toolbar">
            <table style="width:100%">
                <tr>
                    <td style="width:95%">
                        <a class="mini-button" iconcls="icon-add" id="ContractReceiveBrowse_Add" onclick="addNew">增加</a>
                        <a class="mini-button" iconcls="icon-edit" id="ContractReceiveBrowse_Edit"
                           onclick="editOne">编辑</a>
                        <a class="mini-button" iconcls="icon-zoomin" id="ContractReceiveBrowse_Browse"
                           onclick="browseOne">浏览</a>
                        <a class="mini-button" iconcls="icon-remove" id="ContractReceiveBrowse_Remove"
                           onclick="deleteOne">删除</a>
                        <a class="mini-button" iconCls="icon-print" id="ContractReceiveBrowse_Config"
                           onclick="onConfig">
                            参数设置
                        </a>
                        <a class="mini-button" iconCls="icon-download" id="ContractReceiveBrowse_Download" onclick="download()">下载附件</a>
                    </td>
                    <td style="white-space:nowrap;">
                        <input class="mini-textbox" style="width:250px" emptyText="客户/商务人员/合同编号/合同名称" id="queryText"/>
                        <a class="mini-button mini-button-success" id="a3" onclick="doQuery">模糊查询</a>
                        <a class="mini-button mini-button-primary" id="a1" onclick="doHightSearch">搜索结果</a>
                        <a class="mini-button mini-button-danger" id="a2" onclick="reset">重置条件</a>
                        <a class="mini-button" onclick="expand" iconCls="icon-collapse">高级查询</a>
                    </td>
                </tr>
            </table>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:90px;padding:5px;">
                <table style="width:100%;height:100%;padding:0px;margin:0px" cellspacing="5px" cellpadding="5px"
                       id="highQueryForm">
                    <tr>
                        <td style="width:80px;text-align: center;">客户名称</td>
                        <td><input class="mini-textbox" name="ClientName" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">合同编号</td>
                        <td><input class="mini-textbox" name="ContractNo" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">领取时间</td>
                        <td><input class="mini-datepicker" name="DrawTime" style="width:100%" data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="DrawTime" style="width:100%" data-oper="LE"
                                   dateFormat="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">合同类型</td>
                        <td><input class="mini-combobox" name="ContractType" style="width:100%" data-oper="EQ"
                                   data="ctypes"/></td>
                        <td style="width:80px;text-align: center;">领取人员</td>
                        <td><input class="mini-textbox" name="DrawEmpName" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">是否交单</td>
                        <td><input class="mini-combobox" name="AlreadyJiao" style="width:100%" data-oper="EQ"
                                   data="jiaodans"/></td>
                        <td style="width:80px;text-align: center;">备注信息</td>
                        <td><input class="mini-textbox" name="Remark" style="width:100%" data-oper="LIKE"/></td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
            <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" onDrawCell="onDraw"
                 url="/work/contractReceive/getData" autoload="true" sortfield="createTime" sortorder="desc"
                 pagesize="20"  allowCellEdit="true" allowCellSelect="true" oncellbeginedit="onbeforeEdit"
                 oncellendedit="EndEdit" multiselect="true">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div type="checkcolumn"></div>
                    <div field="ContractNo" width="140" headeralign="center" allowsort="true" align="center">合同编号</div>
                    <div field="ContractName" width="200" headeralign="center" allowsort="true" align="center">合同名称
                    </div>
                    <div field="ContractType" width="100" headeralign="center" allowsort="true" align="center"
                         type="comboboxcolumn">合同类型
                        <input property="editor" class="mini-combobox" data="ctypes" id="cTypes11"/>
                    </div>
                    <div field="ClientName" width="180" headeralign="center" allowsort="true" align="center">客户名称</div>

                    <div field="NeadSeal" width="100" headeralign="center" allowsort="true" align="center"
                         type="comboboxcolumn">
                        <input property="editor" class="mini-combobox" data="seals"/>是否盖章
                    </div>
                    <div field="DrawEmpName" width="100" headeralign="center" allowsort="true" align="center">领取人
                    </div>
                    <div field="CreateTime" width="120" headeralign="center" align="center" allowsort="true"
                         align="center" dateFormat="yyyy-MM-dd">领取日期
                    </div>
                    <div field="AlreadyJiao" width="100" headeralign="center" allowsort="true" align="center"
                         renderer="hasNum">是否交单
                    </div>
                    <div field="JiaoNum" width="100" headeralign="center" allowsort="true" align="center">交单次数</div>
                    <div field="AttID" width="100" headeralign="center" allowsort="true" align="center">是否上传附件</div>
                    <div field="CreateManName" width="80" headerAlign="center" align="center">创建人</div>
                    <div field="DrawEmp" visible="false"></div>
                    <div field="Remark" width="80" headeralign="center" allowsort="true" align="center">备注</div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="editWindow1" class="mini-window" title="合同领取" showmodal="true"
     allowresize="true" allowdrag="true" style="width:900px;">
    <div id="editform1" class="form">
        <table style="width:100%; height:100%" class="layui-table">
            <tr>
                <td>合同编号</td>
                <td>
                    <input class="mini-textbox" name="ContractNo" emptyText="系统自动生成" readonly="true" style="width:100%"/>
                </td>
                <td>合同名称</td>
                <td>
                    <input class="mini-textbox" name="ContractName" style="width:100%" required="true"/>
                </td>
            </tr>
            <tr>
                <td>合同类型：</td>
                <td>
                    <input class="mini-combobox" name="ContractType" style="width:100%" required="true" data="ctypes"/>
                    <input class="mini-hidden" name="id" id="id" value="0"/>
                    <input class="mini-hidden" name="DrawEmp"/>
                </td>
                <td>是否盖章：</td>
                <td>
                    <input class="mini-combobox" name="NeadSeal" style="width:100%" required="true" data="seals"/>
                </td>
            </tr>
            <tr>
                <td>客户名称：</td>
                <td>
                    <input id="ClientID" name="ClientID" class="mini-buttonedit" onbuttonclick="onCustomDialog"
                           style="width:100%" allowInput="true" required="true"/>
                </td>
                <td>创建日期：</td>
                <td>
                    <input class="mini-datepicker" name="CreateTime" dateFormat="yyyy-MM-dd HH:mm:ss" readonly="true"
                           style="width:100%"/>
                </td>
            </tr>
            <tr>
                <td>领取人员：</td>
                <td>
                    <input class="mini-treeselect" valuefield="FID" parentfield="PID" textfield="Name"
                           url="/systems/dep/getAllLoginUsersByDep" id="DrawEmp" name="DrawEmp" style="width: 100%;"
                           resultastree="false" onbeforenodeselect="beforeSelectNode" valueFromSelect="true" required="true"/>
                </td>
                <td>备注：</td>
                <td>
                    <textarea class="mini-textarea" name="Remark" style="width: 100%;height:30px"></textarea>
                </td>
            </tr>
            <tr>
                <td>合同附件</td>
                <td colspan="3">
                    <input class="mini-hidden" name="AttID" id="AttID"/>
                    <a id="ContractReceiveBrowse_Upload" href="#" style="color:blue;text-decoration:underline" onclick="doUpload()">上传合同附件</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a id="ContractReceiveBrowse_Download" style="color:blue;text-decoration:underline"  href="#" onclick="doDownload()">下载合同附件</a>
                    <span id="AlertText" style="color:red">(合同附件上传或删除以后需要保存才会生效。)</span>
                </td>
            </tr>
            <tr>
                <td colspan="8" style="text-align:center">
                    <button class="mini-button" style="width:120px" id="ContractReceiveBrowse_Save" iconcls="icon-save"
                            onclick="saveContract">&nbsp;保存合同信息
                    </button>

                    <button class="mini-button" style="width:120px;margin-left:180px" id="CancelForm1"
                            iconCls="icon-no"
                            onclick="closeMe">关闭退出
                    </button>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="editWindow2" class="mini-window" title="参数设置" showModal="true" allowresize="true" allowdrag="true"
     style="width:600px;">
    <table style="width:100%;height:100%">
        <tr>
            <td colspan="2">
                <div class="mini-toolbar">
                    <a class="mini-button" iconCls="icon-add" onclick="addType()">新增</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="deleteType()">删除</a>
                </div>
                <div class="mini-datagrid" id="grid2" style="width:100%;height:200px" allowCellEdit="true"
                     allowCellSelect="true" showPager="false"  allowCellValid="true">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="type" width="80" vtype="required"  headeralign="center"
                             align="center">合同类型
                            <input property="editor"  class="mini-textbox" />
                        </div>
                        <div field="code" width="150" headeralign="center" align="center" vtype="required">
                            合同编号前缀<input property="editor" class="mini-textbox"/>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <td colspan="2" style="text-align:center">
            <button class="mini-button" style="width:120px;margin:10px" iconcls="icon-save"
                    onclick="saveConfig">&nbsp;保存
            </button>
            <button class="mini-button" style="width:120px;margin:10px"
                    iconCls="icon-no"
                    onclick="closeMe1">关闭
            </button>
        </td>
    </table>
</div>
<script type="text/javascript">
    mini.parse();
    var fit = mini.get('fitt');
    var cmdA1 = mini.get('a1');
    var cmdA2 = mini.get('a2');
    var cmdA3 = mini.get('a3');
    var txtQuery = mini.get('queryText');
    var detailForm = null;
    var form = new mini.Form("#editform1");
    var editWindow = mini.get("editWindow1");
    var editWindow2 = mini.get("editWindow2");
    var cmdAdd = mini.get('#ContractReceiveBrowse_Add');
    var grid = mini.get('datagrid1');
    var grid2 = mini.get('grid2');
    var codes =${Codes};

    var conUp=$('#ContractReceiveBrowse_Upload');
    var conDown=$('#ContractReceiveBrowse_Download');
    $(function () {
        $('#p1').hide();
        cmdA1.hide();
        cmdA2.hide();
        detailForm = document.getElementById("detailForm");
        if (codes.length == 0) {
            cmdAdd.hide();
            mini.alert('合同编号未设置，无法新建合同信息!','系统提示',function(){
                onConfig();
            });
        }
        codeToTypes();
    });
    function codeToTypes(){
        ctypes=[];
        for(var i=0;i<codes.length;i++){
            var code=codes[i];
            var item={id:code.id,text:code.type};
            ctypes.push(item);
        }
       var cons= mini.getsByName('ContractType');
        for(var i=0;i<cons.length;i++)
        {
            var con=cons[i];
            con.setData(ctypes);
        }
        var ccon=mini.get('cTypes11');
        if(ccon)ccon.setData(ctypes);
    }
    function expand(e) {
        var btn = e.sender;
        var display = $('#p1').css('display');
        if (display == "none") {
            btn.setIconCls("icon-expand");
            btn.setText("高级查询");
            $('#p1').css('display', "block");
            cmdA1.show();
            cmdA2.show();
            cmdA3.hide();
            txtQuery.hide();
        } else {
            btn.setIconCls("icon-collapse");
            btn.setText("高级查询");
            $('#p1').css('display', "none");
            cmdA1.hide();
            cmdA2.hide();
            cmdA3.show();
            txtQuery.show();
        }
        fit.setHeight('100%');
        fit.doLayout();
    }

    function hasNum(e) {
        var record = e.record;
        var num = parseInt(record["JiaoNum"] || 0);
        if (num > 0) return "已交单"; else return "未交单";
    }
    function onConfig() {
        grid2.setData(codes);
        editWindow2.show();
    }
    function addType(){
        var row={};
        grid2.addRow(row);
        grid2.validateRow(row);
    }
    function deleteType(){
        var row=grid2.getSelected();
        if(row){
            function g()
            {
                var id=parseInt(row["id"] || 0);
                if(id==0){
                    grid2.removeRow(row);
                    grid2.acceptRecord(row);
                } else {
                    var url='/work/contractReceive/deleteConfig';
                    $.post(url,{ID:id},function(result){
                        if(result.success){
                            mini.alert('选择的数据删除成功!','系统提示',function(){
                                grid2.removeRow(row);
                                grid2.acceptRecord(row);
                                codeToTypes();
                            });
                        } else  mini.alert(result.message || '删除失败!');
                    });
                }
            }
            mini.confirm('确认要删除选择的数据?','系统提示',function(action){
                if(action=='ok'){
                    g();
                }
            });
        } else {
            mini.alert('请选择要删除的合同类型设置!');
        }
    }
    function closeMe1() {
        editWindow2.hide();
    }

    function deleteOne() {
        var row = grid.getSelected();
        if (row) {
            var jiaoNum = parseInt(row["JiaoNum"] || 0);
            if (jiaoNum > 0) {
                mini.alert('本合同信息已被交单业务引用，无法删除!');
                return;
            }
            var id = parseInt(row["id"] || 0);
            if (id > 0) {
                var url = '/work/contractReceive/remove';
                mini.confirm('确认要删除选择的合同记录?', '删除提示', function (action) {
                    if (action == 'ok') {
                        var msgId = mini.loading('正在删除........');
                        $.post(url, {id: id}, function (result) {
                            mini.hideMessageBox(msgId);
                            if (result.success) {
                                mini.alert('选择的合同信息已删除!');
                                grid.reload();
                            } else {
                                mini.alert(result.message || "删除失败，请稍候重试!");
                            }
                        });
                    }
                });
            }
        } else mini.alert('请选择要删除的记录!');
    }

    function saveConfig() {
        var grid2 = mini.get('grid2');
        grid2.validate();
        if (grid2.isValid()) {
            var rows = grid2.getData();
            var url = '/work/contractReceive/saveConfig';
            $.post(url, {Data: mini.encode(rows)}, function (result) {
                if (result.success) {
                    mini.alert('参数设置保存成功', '系统提示', function () {
                        codes = result["data"] || [];
                        if (codes.length > 0) {
                            cmdAdd.show();
                        }
                        grid2.accept();
                        codeToTypes();
                        grid.reload();
                        editWindow2.hide();
                    });
                } else mini.alert(result.message || "保存失败，请稍候重试!");
            });
        } else mini.alert('数据录入不完整。');
    }

    function doHightSearch() {
        var arg = {};
        var form = new mini.Form('#highQueryForm');
        var fields = form.getFields();
        var result = [];
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];
            var val = field.getValue();
            if (val != null && val != undefined) {
                if (val != '') {
                    var obj = {field: field.getName(), value: field.getValue(), oper: field.attributes["data-oper"]};
                    result.push(obj);
                }
            }
        }
        arg["High"] = mini.encode(result);
        grid.load(arg);
    }

    function doQuery() {
        //备注/流水号/业备数量/客户/商务人员
        var txt = txtQuery.getValue();
        var cs = [];
        var arg = {};
        if (txt) {
            var fields = ['Remark', "ContractNo", "ClientName", "DrawEmpName", "ContractName"];
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var obj = {field: field, oper: 'LIKE', value: txt};
                cs.push(obj);
            }
        }
        if (cs.length > 0) {
            arg["Query"] = mini.encode(cs);
        }
        grid.load(arg);
    }

    function reset() {
        var form = new mini.Form('#highQueryForm');
        form.reset();
        doHightSearch();
    }

    function addNew() {
        form.reset();
        form.setData({CreateTime: new Date()});
        form.setEnabled(true);
        mini.get("ContractReceiveBrowse_Save").show();
        conDown.hide();
        editWindow.show();
        checkAtt("Add");

        var DrawEmp = "${DrawEmp}";
        var DrawEmpID = "${DrawEmpID}";
        mini.get('DrawEmp').setText(DrawEmp);
        mini.get('DrawEmp').setValue(DrawEmpID);
    }

    function editOne() {
        var mode="Edit";
        var row = grid.getSelected();
        if (row != null) {
            form.reset();
            var jiaoNum=parseInt(row["JiaoNum"] || 0);
            if(jiaoNum==0){
                form.setEnabled(true);
            } else {
                form.setEnabled(false);mode="Browse";
                editWindow.setTitle('已交单合同信息无法修改');
            }
            form.setData(row);
            var clientCon = mini.get('ClientID');
            mini.get('ContractReceiveBrowse_Save').show();
            if (clientCon) clientCon.setText(row["ClientName"]);
            editWindow.show();
            checkAtt(mode);
        } else mini.alert('请选择要编辑的记录!');
    }

    function browseOne() {
        var row = grid.getSelected();
        if (row != null) {
            form.reset();
            form.setEnabled(false);
            form.setData(row);
            var clientCon = mini.get('ClientID');
            mini.get('ContractReceiveBrowse_Save').hide();
            if (clientCon) clientCon.setText(row["ClientName"]);
            editWindow.show();
            checkAtt("Browse");
        } else mini.alert('请选择要浏览的记录!');
    }

    function onCustomDialog(e) {
        mini.open({
            url: '/work/clientInfo/query?multiselect=false',
            showModal: true,
            width: 800,
            height: 300,
            title: '选择客户资料',
            ondestroy: function (action) {
                if (action == 'ok') {
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    var ds = mini.clone(data);
                    var clientId=parseInt(ds["ClientID"]);
                    if(clientId>0) {
                        e.source.setValue(clientId);
                        e.source.setText(ds.Name);
                    } else mini.alert('选择的客户资料有误,请联系管理员解决问题:'+data.ClientID);
                }
            }
        });
    }

    function saveContract() {
        form.validate();
        if (form.isValid() == false) {
            mini.alert('数据录入不完整，保存操作被中止!');
            return;
        }
        mini.confirm('确认要保存合同信息', '保存提示', function (action) {
            if (action == 'ok') {
                var data = form.getData();
                var url = '/work/contractReceive/save';
                $.post(url, {Data: mini.encode(data)}, function (result) {
                    if (result.success) {
                        mini.alert('合同信息保存成功!', '系统提示', function () {
                            grid.reload();
                            editWindow.hide();
                        });
                    } else {
                        mini.alert(result.message || "保存失败，请稍候重试!");
                    }
                });
            }
        });
    }

    function checkAtt(mode){
        debugger;
        var cmdSave=mini.get('#ContractReceiveBrowse_Save');
        var attIds=$.trim(attCon.getValue() || "");
        if(attIds.length<10){
            if(mode=="Browse"){
                conUp.hide();
                conDown.hide();
                cmdSave.disable();
                $('#AlertText').hide();
            } else {
                conUp.show();
                conDown.hide();
                cmdSave.enable();
            }
        } else {
            if(mode=="Browse"){
                conUp.hide();
                conDown.show();
                cmdSave.disable();
                $('#AlertText').hide();
            } else {
                conUp.show();
                conDown.show();
                cmdSave.enable();
            }
        }
    }
    function closeMe() {
        editWindow.hide();
    }
    var attCon=mini.get('#AttID');
    function doUpload(){
        var attIds=attCon.getValue() || "";
        mini.open({
            url: '/attachment/addFile?IDS=' + attIds + '&Mode=Edit',
            width: 800,
            height: 400,
            title: '上传合同附件',
            onload: function () {
                var iframe = this.getIFrameEl();
                iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                    var attId = data.AttID;
                    var cs=[];
                    if(attIds)cs=attIds.split(',') || [];
                    cs.push(attId);
                    attCon.setValue(cs.join(','));
                });
                iframe.contentWindow.addEvent('eachFileRemoved', function (data) {
                    var attId = data.AttID;
                    var cs=attIds.split(',') || [];
                    cs.remove(attId);
                    attCon.setValue(cs.join(','));
                });
            }
        });
        window.parent.doResize();
    }
    function doDownload(){
        var attId=mini.get('#AttID').getValue() || "";
        mini.open({
            url: '/attachment/addFile?IDS=' + attId + '&Mode=Browse',
            width: 800,
            height: 400,
            title: '下载合同附件'
        });
        window.parent.doResize();
    }
    function onbeforeEdit(e) {
        var field = e.field;
        if (field != "CreateManName") {
            e.cancel = true;
        }
    }
    function EndEdit(e) {
        var field = e.field;
        var val = e.value;
        var row = e.record;
        var AlreadyJiao = row["JiaoNum"];
        if (AlreadyJiao > 0) {
            mini.alert("该合同已领取，无法修改领取人！");
            return;
        }
        var ID = row["id"];
        var url = '/work/contractReceive/changeDrawEmp';
        $.post(url, {ID: ID, Value: val,}, function (r) {
            if (r.success) {
                doReload(grid);
            } else {
                var msg = r.message || "更新属性值失败，请稍候重试。";
                mini.alert(msg);
            }
        });
    }

    function doReload(grid) {
        if (grid) {
            var pa = grid.getLoadParams();
            var pageIndex = grid.getPageIndex() || 0;
            var pageSize = grid.getPageSize() || 20;
            pa = pa || {pageIndex: pageIndex, pageSize: pageSize};
            grid.load(pa);
        }
    }
    function beforeSelectNode(e){
        if (e.isLeaf == false) e.cancel = true;
    }
    function onDraw(e) {
        var field = e.field;
        var record = e.record;
        if (field == "AttID") {
            var dd = record['AttID'];
            if (dd) {
                e.cellHtml = "已上传";
            }else e.cellHtml = "未上传";
        }
    }
    function download() {
        debugger;
        var rows = grid.getSelecteds();
        var code = null;
        var name = null;
        if (rows.length == 1) {
            var row = rows[0];
            if (row) {
                code = row["AttID"];
                name = row["ContractName"] + '-' + row["ContractNo"] + '.zip';
            }
        } else {
            var codes = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var code = row["AttID"];
                codes.push(code);
            }
            code = codes.join(',');
            name = rows.length + '个合同附件下载.zip';
        }
        var url = '/work/contractReceive/download?Code=' + code + '&FileName=' + encodeURI(name);
        $.fileDownload(url, {
            httpMethod: 'POST',
            successCallback:function(xurl){

            },
            failCallback: function (html, xurl) {
                mini.alert('下载错误:' + html, '系统提示');
            }
        });
    }
</script>
</@layout>
