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
        {id: 1, text: '业务交单-待提交'},
        {id: 2, text: '业务交单-待审核'},
        {id: 3, text: '业务交单-已驳回'},
        {id: 4, text: '业务交单-已审批'},
        {id: 5, text: '技术接单-可接单'},
        {id: 6, text: '技术接单-已确定'},
        {id: 7, text: '业务交单-未完成'},
        {id: 8, text: '业务交单-已完成'},
        {id: 9, text: '业务交单-全部'}
    ];
</script>
<div class="mini-fit" style="overflow:hidden">
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" bodyStyle="overflow:hidden">
            <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                <table style="width:100%;" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" iconcls="icon-add" id="CasesBrowse_Add"
                               onclick="addCases">增加业务交单</a>
                            <a class="mini-button" iconcls="icon-edit" id="CasesBrowse_Edit" onclick="editCases"
                               visible="false">编辑</a>
                            <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_Audit"
                               onclick="auditCases" visible="false">交单审批</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconCls="icon-remove" id="CasesBrowse_Remove"
                               onclick="deleteCases" visible="false">删除</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="CasesBrowse_Browse"
                               onclick="browseCases">查看</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox CasesBrowse_Query" style="width:250px"
                                   emptyText="备注/合同号/流水号/业备数量/客户/商务人员"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success CasesBrowse_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
                            <a class="mini-button mini-button-primary CasesBrowse_HighQuery" id="a1"
                               onclick="doHightSearch">搜索数据</a>
                            <a class="mini-button mini-button-danger CasesBrowse_Reset"  id="a2"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info"onclick="expand">展开</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:120px;padding:5px;">
                <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                    <tr>
                        <td style="width:80px;text-align: center;">客户名称</td>
                        <td><input class="mini-textbox" name="ClientName"  style="width:100%"  data-oper="LIKE" /></td>
                        <td style="width:80px;text-align: center;">交单时间</td>
                        <td><input class="mini-datepicker" name="SignTime" style="width:100%" data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="SignTime" style="width:100%" data-oper="LE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">合同编号</td>
                        <td><input class="mini-textbox" name="ContractNo" style="width:100%"  data-oper="LIKE" /></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">业务流水</td>
                        <td><input class="mini-textbox" name="DocSN" style="width:100%"  data-oper="LIKE" /></td>
                        <td style="width:80px;text-align: center;">商务人员</td>
                        <td><input class="mini-textbox" name="CreateManName" style="width:100%"  data-oper="LIKE" /></td>
                        <td style="width:80px;text-align: center;">流程人员</td>
                        <td><input class="mini-textbox" name="AuditManName"  style="width:100%"  data-oper="LIKE" /></td>
                        <td style="width:80px;text-align: center;">技术人员</td>
                        <td><input class="mini-textbox" name="TechManNames"  style="width:100%"  data-oper="LIKE" /></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">备注</td>
                        <td><input class="mini-textbox" name="Memo" style="width:100%"  data-oper="LIKE" /></td>
                        <td style="width:80px;text-align: center;">类型及数量</td>
                        <td><input class="mini-textbox" name="Nums" style="width:100%"  data-oper="LIKE" /></td>
                        <td style="width:80px;text-align: center;">费减情况</td>
                        <td><input class="mini-combobox" name="FeiJian"  style="width:100%" valueFromSelect="true"
                                   url="/systems/dict/getByDtId?dtId=23" valueField="text"  data-oper="EQ"/></td>
                        <td style="width:80px;text-align: center;">专利申请费</td>
                        <td><input class="mini-combobox" name="ZuanLiFei"  style="width:100%"
                                   url="/systems/dict/getByDtId?dtId=8" valueField="text" valueFormSelect="true"
                                   data-oper="EQ"/></td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" onDrawCell="onDraw"
                     showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick" sortfield="CreateTime"
                     sortorder="desc" pagesize="20" onload="afterload" url="/work/cases/getData?State=${State}"
                     autoload="true">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div field="Action" width="60">备注</div>
                        <div type="comboboxcolumn" field="State" width="120" headerAlign="center" align="center">业务状态
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="DocSN" width="150" align="center" headerAlign="center">业务流水号</div>
                        <div field="Nums" width="300"  headeralign="center" >业务数量</div>
                        <div field="ContractNo" width="120" headeralign="center"  align="center" >合同编号</div>
                        <div field="ClientID"  align="center" width="200" headeralign="center" type="treeselectcolumn">客户名称
                            <input property="editor" class="mini-treeselect" url="/systems/client/getClientTree"/>
                        </div>
                        <div field="FeiJian"  align="center" width="100" headeralign="center" type="comboboxcolumn" allowsort="true">
                            费减情况
                            <input property="editor" class="mini-combobox" url="/systems/dict/getByDtId?dtId=23"/>
                        </div>
                        <div field="ZuanLiFei"   align="center" width="120" headeralign="center" type="comboboxcolumn" allowsort="true">
                            专利申请费
                            <input property="editor" class="mini-combobox" url="/systems/dict/getByDtId?dtId=8"/>
                        </div>
                        <div field="CreateMan"   align="center" width="100" headeralign="center" type="treeselectcolumn"
                             align="center">商务人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="SignTime" align="center" width="120" headeralign="center" datatype="date"
                             dateformat="yyyy-MM-dd"
                             allowsort="true">交单日期
                        </div>
                        <div field="AuditMan" align="center" width="80" headeralign="center"
                             type="treeselectcolumn">审核人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" align="center" width="120" headeralign="center" dataType="date"
                             dateFormat="yyyy-MM-dd
                    HH:mm" allowsort="true">审核时间
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
        var results=[{id:'同意交单',text:'同意交单'},{id:'拒绝交单',text:'拒绝交单'}];

        var roleName='${RoleName}';
        var userId='${UserID}';
        var grid = mini.get('datagrid1');
        var fit = mini.get('fitt');
        var cmdAdd=mini.get('#CasesBrowse_Add');
        var cmdEdit=mini.get('#CasesBrowse_Edit');
        var cmdBrowse=mini.get('#CasesBrowse_Browse');
        var cmdAudit=mini.get('#CasesBrowse_Audit');
        var cmdDelete=mini.get('#CasesBrowse_Remove');
        var cmdA1=mini.get('a1');
        var cmdA2=mini.get('a2');
        var cmdA3=mini.get('a3');
        var txtQuery=mini.get('queryText');
        $(function () {
            $('#p1').hide();
            cmdA1.hide();
            cmdA2.hide();
            detailForm=document.getElementById("detailForm")
        })

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                //btn.setIconCls("panel-collapse");
                btn.setText("折叠");
                $('#p1').css('display', "block");
                cmdA1.show();
                cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                //btn.setIconCls("panel-expand");
                btn.setText("展开");
                $('#p1').css('display', "none");
                cmdA1.hide();
                cmdA2.hide();
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
        }

        function addCases() {
            mini.open({
                url: '/work/cases/add',
                width: '100%',
                height: '100%',
                title:'新增业务交单',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            });
        }

        function editCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/work/cases/edit?Mode=Edit&ID=' + id,
                    width: '100%',
                    height: '100%',
                    title:'编辑业务交单',
                    showModal: true,
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }
        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/work/cases/edit?Mode=Browse&ID=' + id,
                    width: '100%',
                    height: '100%',
                    title:'查看业务交单',
                    showModal: true,
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function afterload() {
           var parent= window.parent;
           if(parent)parent.updateStateNumbers();

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
                            if (row["CASESID"] == code) return true;
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
        function  onRowClick(e) {
            var record=e.record ;
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            cmdDelete.hide();
            if(state==1 || state==3){
                if(createMan==userId){
                    cmdEdit.show();
                    cmdAudit.hide();
                    cmdBrowse.show();
                    cmdDelete.show();
                } else {
                    cmdEdit.hide();
                    cmdBrowse.show();
                    cmdAudit.hide();
                }
            }
            else if(state==2){
                if(roleName=="流程人员"){
                    cmdEdit.hide();
                    cmdBrowse.show();
                    cmdAudit.show();
                } else {
                    cmdEdit.hide();
                    cmdBrowse.show();
                    cmdAudit.hide();
                }
            }
            else {
                cmdEdit.hide();
                cmdBrowse.show();
                cmdAudit.hide();
            }
        }
        function auditCases(){
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.showMessageBox({
                    html: ' <input labelField="true" label="审核结果" class="mini-combobox"  id="Result"  ' +
                    'style="width:100%" labelStyle="width:60px" value="同意交单" data="results"/>' +
                    '<textarea labelField="true" label="审核意见" class="mini-textarea"  id="ResultText"  ' +
                    'style="width:100%;height:40px" ' +
                    'labelStyle="width:60px" value="同意交单" required="true"></textarea>',
                    buttons: ['yes', 'no'],
                    title: '审核结果',
                    width: 300,
                    height: 180,
                    callback: function (action) {
                        if (action == 'yes') {
                            var url='/work/cases/commit';
                            var result=mini.get('#Result').getValue();
                            var resultText=mini.get('#ResultText').getValue();
                            if(result && resultText)
                            {
                                $.post(url,{ID:id,Result:result,ResultText:resultText},function(result){
                                    if(result.success){
                                        mini.alert('交单业务审核成功！','系统提示',function(){
                                            grid.reload();
                                        });
                                    } else mini.alert(result.message || '审核失败，请稍候重试！');
                                });
                            } else {
                                mini.alert('请填写审核意见后再进行提交操作。');
                            }
                        }
                    }
                });
                mini.parse();
            } else mini.alert('选择要审核的记录!');
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "TYSB") {
                var dd = record['TYSB'];
                if (dd == '1') e.cellHtml = '<SPAN style="color:red">已申报</span>';
            }
            if (field == "ISSUBMIT") {
                var dd = record['ISSUBMIT'];
                if (dd == '1') e.cellHtml = '<SPAN style="color:red">已确定</span>';
            }
            else if (field == "KHName") {
                var clientId = record["KHID"];
                var val = e.value;
                if (clientId) {
                    e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            }
            else if (field == "Action") {
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMODE"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay'>查看</span>";
                }
                e.cellHtml ='<a href="#"  data-placement="bottomleft"  hCode="' + record["CASESID"] + '" ' +
                        'class="showCellTooltip" onclick="ShowMemo(' + "'" + record["CASESID"] + "'," + "'" +
                        record["DocSN"] + "'" + ')">' + text + '</a>';
            }
            else if (field == "JSNames") {
                var nNames = record["JSNames"]
                var ONames = record["JSName"];
                if (typeof (nNames) == "undefined") {
                    nNames = "";
                }
                if (typeof (ONames) != "undefined") {
                    ONames = ONames + ",";
                }
                else {
                    ONames = "";
                }
                var result = ONames + nNames;
                e.cellHtml = "<span>" + result + "</span>";
            }
        }
        function deleteCases(){
            var record=grid.getSelected();
            if(!record) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            if(state!=1)return ;
            if(createMan!=parseInt(userId)) return ;
            var casesid=record["CASESID"] || "";
            if(!casesid) return;
            function g(){
                var url='/work/cases/removeCases';
                $.post(url,{CasesID:casesid},function(result){
                    if(result.success){
                        mini.alert('删除成功!');
                        grid.reload();
                    } else {
                        mini.alert(result.message || "删除失败，请稍候重试!");
                    }
                })
            }
            mini.confirm('确认要删除选择的交单记录？','删除提示',function(result){
                if(result=='ok')g();
            });
        }
        function ShowMemo(id, title) {
            mini.open({
                url: '/work/cases/addMemo?CasesID=' + id+'&MenuName='+currentMenuName,
                showModal: true,
                width: 900,
                height: 400,
                title: "【" + title + "】的进度信息",
                onDestroy: function () {
                    refreshData(grid);
                }
            });
        }
        function doHightSearch(){
            var arg = {};
            var form=new mini.Form('#highQueryForm');
            var fields=form.getFields();
            var result=[];
            for(var i=0;i<fields.length;i++){
                var field=fields[i];
                var val=field.getValue();
                if(val!=null && val!=undefined)
                {
                    if(val!='')
                    {
                        var obj={field:field.getName(),value:field.getValue(),oper:field.attributes["data-oper"]};
                        result.push(obj);
                    }
                }
            }
            arg["High"]=mini.encode(result);
            grid.load(arg);
        }
        function doQuery(){
            //备注/流水号/业备数量/客户/商务人员
            var txt=txtQuery.getValue();
            var cs=[];
            var arg={};
            if(txt){
                var fields=['Memo',"DocSN","Nums","ClientName","CreateManName","ContractNo"];
                for(var i=0;i<fields.length;i++){
                    var field=fields[i];
                    var obj={field:field,oper:'LIKE',value:txt};
                    cs.push(obj);
                }
            }
            if(cs.length>0){
                arg["Query"]=mini.encode(cs);
            }
            grid.load(arg);
        }
        function reset(){
            var form=new mini.Form('#highQueryForm');
            form.reset();
            doHightSearch();
        }
    </script>
</@js>