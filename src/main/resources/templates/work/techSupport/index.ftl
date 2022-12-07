<#include "/shared/layout.ftl">
<@layout>
    <style type="text/css">
        .panel-expand {
            background-image: url(/js/miniui/themes/icons/collapse.gif);
        }

        .panel-collapse {
            background-image: url(/js/miniui/themes/icons/collapse.gif);
        }
    </style>
    <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
    <script type="text/javascript">
        var states = [
            {id: 5, text: '未引用'},
            {id: 6, text: '已引用'},
            {id: 7, text: '待结算'},
            {id: 8, text: '已结算'},
            {id: 9, text: '已选择'}
        ];
        var subStates = [
            {id: 1, text: '业务交单-待提交'},
            {id: 2, text: '业务交单-待审核'},
            {id: 3, text: '业务交单-已驳回'},
            {id: 4, text: '业务交单-已审批'},
            {id: 7, text: '业务交单-未完成'},
            {id: 8, text: '业务交单-已完成'},
            {id: 9, text: '业务交单-全部'},
            {id: 50, text: '未确定代理师'},
            {id: 51, text: '已确定代理师'},
            {id: 52, text: '专利申报文件-待内审'},
            {id: 53, text: '专利申报文件-内审驳回'},
            {id: 54, text: '专利申报文件-内审通过'},
            {id: 56, text: '专利申报文件-客户定稿'},
            {id: 55, text: '专利申报文件-客户驳回'},
            {id: 61, text: '流程申报-未申报'},
            {id: 62, text: '流程申报-已申报'},
            {id: 63, text: '业务提成-未结算'},
            {id: 64, text: '业务提成-已结算'},
            {id: 70, text: '业务取消'}
        ];
    </script>
    <div class="mini-fit" style="overflow:hidden">
        <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
            <div region="center" bodyStyle="overflow:hidden">
                <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                    <table style="width:100%;" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="width:100%;">
                                <span class="separator"></span>
                                <a class="mini-button" iconCls="icon-save" id="TechSupport_Cwsp"
                                   onclick="doAccount" visible="false">设置为已结算</a>
                                <a class="mini-button" iconCls="icon-cancel" id="TechSupport_UnCwsp"
                                   onclick="doUnAccount" visible="false">设置为未结算</a>
                                <a class="mini-button" iconcls="icon-zoomin" id="TechSupport_BrowseCases"
                                   onclick="browseCases">查看</a>
                                <a class="mini-button" iconcls="icon-remove" id="TechSupport_RemoveCases"
                                   onclick="removeCases">删除专利挖掘</a>
                            </td>
                            <td style="white-space:nowrap;">
                                <input class="mini-textbox TechSupport_Query" style="width:250px"
                                       emptyText="备注/文件名称/流水号/客户/商务人员" id="queryText"/>
                                <a class="mini-button mini-button-success TechSupport_Query" id="a3"
                                   onclick="doQuery">模糊查询</a>
                                <a class="mini-button mini-button-danger TechSupport_Reset" id="a2"
                                   onclick="reset">重置条件</a>
                                <a class="mini-button mini-button-info TechSupport_HighQuery" onclick="expand"
                                   iconCls="">高级查询</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;;display:none">
                    <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                        <tr>
                            <td style="width:80px;text-align: center;">创建人员</td>
                            <td><input name="CreateMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                       allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                       valueFromSelect="true" style="width:100%" data-oper="EQ"/></td>

                            <td style="width:80px;text-align: center;">文件编号</td>
                            <td><input class="mini-textbox" name="FileSN" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">文件名称</td>
                            <td><input class="mini-textbox" name="Name" style="width:100%" data-oper="LIKE"/></td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">客户名称</td>
                            <td><input class="mini-textbox" name="ClientName" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">上传时间</td>
                            <td><input class="mini-datepicker" name="UploadTime" style="width:100%" data-oper="GE"
                                       dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">结束时间</td>
                            <td><input class="mini-datepicker" name="UploadTime" style="width:100%" data-oper="LE"
                                       dateFormat="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">技术领域</td>
                            <td><input class="mini-combobox" name="TechType" style="width:100%" valueFromSelect="true"
                                       url="/systems/dict/getByDtId?dtId=25" valueField="id" data-oper="EQ"/></td>
                            <td style="width:80px;text-align: center;">支持人员</td>
                            <td><input name="TechMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                       allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                       valueFromSelect="true" style="width:100%" data-oper="EQ"/></td>
                            <td style="width:80px;text-align: center;">上传人员</td>
                            <td><input name="UploadMan" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                       valueFromSelect="true" style="width:100%" data-oper="EQ"/></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" style="width:120px"
                                   href="javascript:doHightSearch();">搜索</a>
                                <a class="mini-button mini-button-danger" style="margin-left:30px;width:120px"
                                   onclick="expand">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
                    <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" onDrawCell="onDraw"
                            <#if State==7 || State==8>
                                multiSelect="true"
                            </#if>
                         showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick"
                         sortfield="UploadTime" sortorder="desc" pagesize="20" onload="afterload" url="/techSupport/getFiles?State=${State}" autoload="true">
                        <div property="columns">
                            <div type="checkcolumn"></div>
                            <div type="comboboxcolumn" field="State" width="80" headerAlign="center" align="center"
                                 allowSort="true">业务状态
                                <input property="editor" class="mini-combobox" data="states"/>
                            </div>
                            <div field="Action" width="60" headerAlign="center" align="center">备注</div>
                            <div field="DocSN" width="150" align="center" headeralign="center"
                                 allowSort="true">专利挖掘流水号
                            </div>
                            <div field="TechType" width="100" align="center" headeralign="center" type="comboboxcolumn"
                                 allowSort="true">技术领域
                                <input property="editor" class="mini-combobox" url="/systems/dict/getByDtId?dtId=25"
                                       valueFromSelect="true"/>
                            </div>
                            <div field="CreateMan" width="100" align="center" headeralign="center" type="treeselectcolumn" allowSort="true">商务人员
                                <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>


                            <div field="FileSN" width="150" align="center" headeralign="center"
                                 allowSort="true">文件编号
                            </div>
                            <div field="Name" width="150" align="center" headeralign="center" allowSort="true">文件名称
                            </div>
                            <div field="Size" width="80" align="center" headeralign="center" allowSort="true">大小</div>
                            <div field="UploadManName" width="80" align="center" headeralign="center"
                                 allowSort="true">上传人员
                            </div>
                            <div field="UploadTime" width="120" align="center" headeralign="center" dataType="date"
                                 allowSort="true" dateFormat="yyyy-MM-dd HH:mm">上传时间
                            </div>
                            <div field="SubState" width="120" align="center" headeralign="center" type="comboboxcolumn">交单业务状态
                                <input property="editor" class="mini-combobox" data="subStates" />
                            </div>
                            <div field="ClientID" align="center" width="200" headeralign="center"
                                 type="treeselectcolumn" allowSort="true">客户名称
                                <input property="editor" class="mini-treeselect" url="/systems/client/getAllClientTree"/>
                            </div>
                            <div field="YName" width="90" align="center" headeralign="center">专利业务类型</div>
                            <div field="SignTime" width="120" align="center" headeralign="center"
                                 dataType="date" allowSort="true" dateFormat="yyyy-MM-dd HH:mm">专利交单时间</div>
                            <div field="TechManName" width="80" align="center" headeralign="center">案件代理师</div>
                            <div field="AuditTechMan" width="80" align="center" headeralign="center"  type="treeselectcolumn" >交单内审人
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="AuditTechTime" width="100" align="center" headeralign="center"
                                 dataType="date" allowSort="true" dateFormat="yyyy-MM-dd HH:mm">交单内审时间</div>
                            <div field="RefMan" width="100" align="center" headeralign="center"
                                 type="treeselectcolumn" >交底引用人
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="RefTime" width="100" align="center" headeralign="center"  dataType="date"
                                 allowSort="true" dateFormat="yyyy-MM-dd HH:mm">交底引用时间</div>
                            <#if State==8>
                                <div field="FeePercent" align="center" width="120" headeralign="center">客户付款比例</div>
                                <div field="UseTechManName" align="center" width="120" headeralign="center">使用人员</div>
                                <div field="UseClientName" align="center" width="150" headeralign="center">使用客户</div>
                            </#if>
                            <div field="AccountMan" width="100" align="center" headeralign="center"
                                 type="treeselectcolumn" allowSort="true">结算人员
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="AccountTime" width="120" align="center" headeralign="center" dataType="date"
                                 allowSort="true" dateFormat="yyyy-MM-dd HH:mm">结算时间
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var tip = new mini.ToolTip();
        var results = [{id: '同意交单', text: '同意交单'}, {id: '拒绝交单', text: '拒绝交单'}];
        var roleName = '${RoleName}';
        var userId = '${UserID}';
        var grid = mini.get('datagrid1');
        var fit = mini.get('fitt');
        var cmdCwsp = mini.get('#TechSupport_Cwsp');
        var cmdUnCwsp = mini.get("#TechSupport_UnCwsp");
        var cmdBrowse = mini.get('#TechSupport_BrowseCases');
        var cmdRemove=mini.get('#TechSupport_RemoveCases');
        var cmdA1 = mini.get('a1');
        var cmdA2 = mini.get('a2');
        var cmdA3 = mini.get('a3');
        var txtQuery = mini.get('queryText');
        $(function () {
            $('#p1').hide();
            cmdA2.show();
            cmdCwsp.hide();
            cmdUnCwsp.hide();
            cmdRemove.hide();
            detailForm = document.getElementById("detailForm")
        })

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setText("隐藏");
                btn.setIconCls("icon-collapse");
                $('#p1').css('display', "block");
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setText("高级查询");
                $('#p1').css('display', "none");
                btn.setIconCls("icon-expand");
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();

            grid.setHeight('100%');
            grid.doLayout();
            cmdA2.show();
        }

        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.MainID;
                mini.open({
                    url: '/techSupport/edit?Mode=Browse&ID=' + id,
                    width: '100%',
                    height: '100%',
                    title: '查看专利挖掘申请',
                    showModal: true,
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function doAccount() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                function g() {
                    var atts = [];
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var obj = {CasesID: row["CasesID"], AttID: row["AttID"]};
                        atts.push(obj);
                    }
                    var url = '/techSupport/doAccount';
                    $.post(url, {Data: mini.encode(atts)}, function (result) {
                        if (result.success) {
                            mini.alert('设置成功!');
                            grid.reload();
                        } else mini.alert(result.message || "设置失败，请稍候重试!");
                    });
                }

                mini.confirm('确认要将记录设置为已结算吗？', '系统提示', function (act) {
                    if (act == "ok") g();
                });
            }
        }

        function doUnAccount() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                function g() {
                    var atts = [];
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var obj = {CasesID: row["CasesID"], AttID: row["AttID"]};
                        atts.push(obj);
                    }
                    var url = '/techSupport/doUnAccount';
                    $.post(url, {Data: mini.encode(atts)}, function (result) {
                        if (result.success) {
                            mini.alert('设置成功!');
                            grid.reload();
                        } else mini.alert(result.message || "设置失败，请稍候重试!");
                    });
                }

                mini.confirm('确认要将记录设置为未结算吗？', '系统提示', function (act) {
                    if (act == "ok") g();
                });
            }
        }

        function afterload() {
            var parent = window.parent;
            if (parent) parent.updateStateNumbers();

            tip.set({
                target: document,
                selector: '.showCellTooltip',
                onbeforeopen: function (e) {
                    e.cancel = false;
                },
                onopen: function (e) {
                    var el = e.element;
                    if (el) {
                        var code = $(el).attr('hCode');
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["CasesID"] == code) return true;
                        });
                        if (row) {
                            var memo = row["MEMO"];
                            if (memo) {
                                tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                    'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                    'text-top; "> <tr><td>' + row["MEMO"] + '</td></tr></table>');
                            }
                        }
                    }
                }
            });
        }

        function onRowClick(e) {
            var record = e.record;
            var state = parseInt(record.State || 1);
            var createMan = record["CreateMan"] || "";
            var uploadMan = parseInt(record["UploadMan"] || 0);
            cmdBrowse.show();
            cmdRemove.hide();
            var mans = [];
            if (roleName == "财务人员"|| roleName == "系统管理员") mans.push(userId);
            var accountMan = record["AccountMan"] || 0
            if (state == 7) {
                if (mans.indexOf(userId) > -1) cmdCwsp.show(); else cmdCwsp.hide();
            } else if (state == 8) {
                if (mans.indexOf(userId) > -1) {
                    if (accountMan == userId) cmdUnCwsp.show();
                }
            }
            else if(state==5){
                if(roleName=="系统管理员" || roleName=="财务人员" || roleName.indexOf("流程")>-1){
                    cmdRemove.show();
                }
                if(uploadMan==userId) cmdRemove.show();
            }
        }
        function removeCases(){
            var record=grid.getSelected();
            if(!record) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            if(state!=5){
                mini.alert('只有未被引用的记录才可以被删除!');
                return ;
            }
            if(createMan!=parseInt(userId))
            {
                if(roleName!='系统管理员' && roleName.indexOf('流程')==-1 && roleName.indexOf('财务')==-1){
                    mini.alert('只有上传文件的操作人员才可以删除文件!');
                    return ;
                }
            }

            var casesId=record["CasesID"] || "";
            var attId=record["AttID"] || "";
            if(!casesId || !attId) return;
            function g(){
                var url='/techSupport/deleteTechFile';
                $.post(url,{CasesID:casesId,AttID:attId,Check:1},function(result){
                    if(result.success){
                        mini.alert('选择的专利交底文件删除成功!');
                        grid.reload();
                    } else {
                        mini.alert(result.message || "删除失败，请稍候重试!");
                    }
                })
            }
            mini.confirm('确认要删除选择的专利交底文件？','删除提示',function(result){
                if(result=='ok')g();
            });
        }
        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "Action") {
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMODE"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay'>查看</span>";
                }
                e.cellHtml = '<a href="#"  data-placement="bottomleft"  hCode="' + record["CasesID"] + '" ' +
                    'class="showCellTooltip" onclick="ShowMemo(' + "'" + record["CasesID"] + "'," + "'" +
                    record["DocSN"] + "'" + ')">' + text + '</a>';
            }
        }

        function ShowMemo(id, title) {
            var rows = grid.getSelecteds();
            if (rows.length > 1) {
                var c = [];
                for (var i = 0; i < rows.length; i++) {
                    c.push(rows[i]["CasesID"]);
                }
                id = c.join(',');
                title = "多条交单批量添加";
            }
            mini.open({
                url: '/cases/addMemo?CasesID=' + id,
                showModal: true,
                width: 1000,
                height: 600,
                title: "【" + title + "】的进度信息",
                onDestroy: function () {
                    grid.deselectAll();
                    grid.reload();
                }
            });
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
                        var obj = {
                            field: field.getName(),
                            value: field.getValue(),
                            oper: field.attributes["data-oper"]
                        };
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
                var fields = ['Memo', "FileSN", "Name", "ClientName", "CreateManName","UploadManName"];
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
            txtQuery.setValue(null);
            comField.setValue('All');
            doHightSearch();
        }
    </script>
</@js>