<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js?v=${version}"></script>
    <script type="text/javascript" src="/js/common/complexExcelExport.js?v=${version}"></script>
    <script type="text/javascript">
        var states = [
            {id: 1, text: '商标版权业务协作-待提交'},
            {id: 2, text: '商标版权业务协作-待审核'},
            {id: 3, text: '商标版权业务协作-已驳回'},
            {id: 4, text: '商标版权业务协作-已审批'},
            {id: 5, text: '商标版权业务协作申报情况-未申报'},
            {id: 6, text: '商标版权业务协作申报情况-已申报'}
        ];
        var results=[{id:'同意交单',text:'同意交单'},{id:'拒绝交单',text:'拒绝交单'}];
    </script>
<div class="mini-fit" style="overflow:hidden">
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" bodyStyle="overflow:hidden">
            <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                <table style="width:100%;" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" iconcls="icon-add" id="TradeCases_Add"
                               onclick="addCases">新增业务交单</a>
                            <a class="mini-button" iconcls="icon-edit" id="TradeCases_Edit" onclick="editCases"
                               visible="false">编辑</a>
                            <a class="mini-button" iconCls="icon-remove" id="TradeCases_Remove"
                               onclick="deleteCases" visible="false">删除</a>
                            <a class="mini-button" iconcls="icon-ok" id="TradeCases_TradeAudit"
                               onclick="auditCases" visible="false">交单审批</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="TradeCases_Browse"
                               onclick="browseCases" visible="false">查看</a>
                            <a class="mini-button" iconcls="icon-collapse" id="TradeCases_ExportBill"
                               onclick="exportBill">生成对帐单</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox TradeCases_Query" style="width:250px"
                                   emptyText="备注/合同号/流水号/业备数量/客户/商务人员"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success TradeCases_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
                            <a class="mini-button mini-button-primary TradeCases_HighQuery" id="a1"
                               onclick="doHightSearch">搜索数据</a>
                            <a class="mini-button mini-button-danger TradeCases_Reset"  id="a2"
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
                        <td style="width:80px;text-align: center;">类型及数量</td>
                        <td><input class="mini-textbox" name="Nums" style="width:100%"  data-oper="LIKE" /></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">备注</td>
                        <td><input class="mini-textbox" name="Memo" style="width:100%"  data-oper="LIKE" /></td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" onDrawCell="onDraw"
                      onrowdblclick="browseCases" onrowclick="onRowClick" sortfield="CreateTime"
                     sortorder="desc" pagesize="20" onload="afterload" url="/work/tradeCasesAll/getData?State=${State}"
                     autoload="true"  idField="DocSN" onshowrowdetail="onShowRowDetail">
                    <div property="columns">
                        <div type="expandcolumn"></div>
                        <div type="comboboxcolumn" field="State" width="190" headerAlign="center" align="center"
                             allowSort="true">业务状态
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="Action" width="60" align="center"  headeralign="center" >进度</div>
                        <div field="Memo" width="150"  align="center"  headeralign="center" allowsort="true">备注</div>
                        <div field="DocSN" width="150" align="center"  headeralign="center" allowsort="true">商著交单流水号</div>
                        <div field="CreateMan" width="100"  align="center"  headeralign="center" type="treeselectcolumn" allowsort="true">商务人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="SignTime" width="120" datatype="date" dateFormat="yyyy-MM-dd HH:mm"  align="center"  headeralign="center" allowsort="true">交单时间</div>
                        <div field="ContractNo" width="120" headeralign="center"  align="center" allowsort="true">合同编号</div>
                        <div field="ClientID" align="center" width="200" headeralign="center" type="treeselectcolumn" allowsort="true">
                            客户名称
                            <input property="editor" class="mini-treeselect" url="/systems/client/getAllClientTree"/>
                        </div>
                        <div field="Nums" width="200" headeralign="center"  align="center" allowsort="true">业务类型及数量</div>
                        <div field="TotalDai" align="center" width="120" headeralign="center" allowsort="true"
                             allowSort="true" renderer="moneyRender"  >应收代理费</div>
                        <div field="UsedDai" align="center" width="120" headeralign="center" allowsort="true"
                             allowSort="true" renderer="moneyRender"  >实收代理费</div>
                        <div field="FreeDai" align="center" width="120" headeralign="center" allowsort="true"
                             allowSort="true" renderer="moneyRender"  >未收代理费余额</div>
                        <div field="TotalGuan" align="center" width="120" headeralign="center" allowsort="true"
                             allowSort="true" renderer="moneyRender"  >应收官费</div>
                        <div field="UsedGuan" align="center" width="120" headeralign="center" allowsort="true"
                             allowSort="true" renderer="moneyRender"  >实收官费</div>
                        <div field="FreeGuan" align="center" width="120" headeralign="center" allowsort="true"
                             allowSort="true" renderer="moneyRender"  >未收官费余额</div>
                        <div field="AuditText" align="center" width="120" headeralign="center" allowsort="true">审核意见</div>
                        <div field="AuditMan"width="80" align="center"  headeralign="center" type="treeselectcolumn" allowsort="true">审核人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" align="center" width="120" headeralign="center" dataType="date"
                             dateFormat="yyyy-MM-dd HH:mm" allowsort="true">审核时间
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <div id="detailForm" style="display:none">
        <div class="mini-datagrid" id="datagrid2" style="width:100%;height:200px" onDrawCell="onDraw" multiSelect="true"
             showColumnsMenu="true" sortfield="CreateTime"
             sortorder="desc" pagesize="20" onload="afterload" url="/work/tradeCasesTech/getData?State=All" showPager="false"
             autoload="false">
            <div property="columns">
                <div type="checkcolumn"></div>
                <div type="comboboxcolumn" field="State" width="120" headerAlign="center" align="center">业务状态
                    <input property="editor" class="mini-combobox" data="states"/>
                </div>
                <div field="SignDate" headerAlign="center" dataType="date" width="80"
                     dateFormat="yyyy-MM-dd" align="center">签约日期
                    <input property="editor" class="mini-datepicker" dateFormat="yyyy-MM-dd"/>
                </div>
                <div field="SubNo" headerAlign="center" align="center" width="90">立案编号</div>
                <div field="YType" headerAlign="center" align="center" width="40">类型</div>
                <div field="YName" headerAlign="center" align="center" vtype="required" width="180">业务名称
                </div>
                <div field="TCName" width="80" headerAlign="center" align="center">
                    商标或版权名称
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div field="TCCategory" width="80" headerAlign="center" align="center">
                    商标类别
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div field="ClientRequiredDate" width="80" headerAlign="center" align="center"
                     dataType="date" dateFormat="yyyy-MM-dd">客户要求申报绝限
                    <input property="editor" class="mini-datepicker" dateFormat="yyyy-MM-dd"/>
                </div>
                <div field="TechFiles" headerAlign="center" align="center" width="40">申报资料
                </div>
                    <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn" width="40"
                         allowSort="true">申报人
                        <input property="editor" class="mini-treeselect"
                               url="/systems/dep/getAllLoginUsersByDep"
                               textField="Name" valueField="FID" parentField="PID"/>
                    </div>
                    <div field="FilingTime" headerAlign="center" dataType="date" width="80" align="center"
                         dateFormat="yyyy-MM-dd" vtype="required">申报时间
                        <input property="editor" class="mini-datepicker"/>
                    </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var roleName = '${RoleName}';
        var userId = '${UserID}';
        var grid = mini.get('datagrid1');
        var grid2=mini.get('datagrid2');
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        var cmdBrowse = mini.get('#TradeCases_Browse');
        var cmdA1=mini.get('a1');
        var cmdA2=mini.get('a2');
        var cmdA3=mini.get('a3');
        var txtQuery=mini.get('queryText');
        var detailForm=null;
        var cmdEdit=mini.get('#TradeCases_Edit');
        var cmdAudit=mini.get('#TradeCases_TradeAudit');
        var cmdDelete=mini.get('#TradeCases_Remove');

        $(function () {
            $('#p1').hide();
            cmdA1.hide();
            cmdA2.hide();
            cmdEdit.hide();
            cmdAudit.hide();
            cmdDelete.hide();
            cmdBrowse.hide();
            detailForm=document.getElementById("detailForm")
        });

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

        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/work/tradeCases/edit?Mode=Browse&ID=' + id,
                    width: '100%',
                    height: '100%',
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
                                tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + row["MEMO"] + '</td></tr></table>');
                            }
                        }
                    }
                }
            });
        }

        function onRowClick(e) {
            var record = e.record;
            var state = parseInt(record.State || 1);
            var techMan = record["TechMan"] || "";
            var tchMans=techMan.toString().split(",");
            var createMan=record["CreateMan"] || "";
            cmdBrowse.show();
            if (createMan==userId && state < 2){
                cmdAudit.hide();
                cmdEdit.show();
                cmdDelete.show();
            }else if (state == 2){
                cmdAudit.show();
                cmdDelete.hide();
                cmdEdit.hide();
            }else if (state == 3){
                cmdAudit.hide();
                cmdEdit.show();
                cmdDelete.show();
            }
            else {
                cmdAudit.hide();
                cmdEdit.hide();
                cmdDelete.hide();
            }
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
                    e.cellHtml = '<a href="javascript:void(0)" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            }
            else if (field == "UploadCPC") {
                var casesId = record["CASESID"];
                var upTime = record["CPCUploadTime"];
                var text = (upTime == null) ? "上传" : "上传或下载";
                e.cellHtml = '<a href="javascript:void(0)" onclick="onFileupload(' + "'" + casesId + "'" + ')">' + text + '</a>';
            }
            else if (field == "UploadReq") {
                var casesId = record["CASESID"];
                var upTime = record["ReqUploadTime"];
                var text = (upTime == null) ? "上传" : "上传或下载";
                e.cellHtml = '<a href="javascript:void(0)" onclick="onReqUpload(' + "'" + casesId + "'" + ')">' + text + '</a>';
            }
            else if (field == "Action") {
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMODE"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay'>查看</span>";
                }
                e.cellHtml ='<a href="javascript:void(0)"  data-placement="bottomleft"  hCode="' + record["CASESID"] + '" ' +
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
            else if (field == "TechFiles"){
                var uText = "查看";
                var mode = "Browse";
                var type = "Tech";
                var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                    ' onclick="uploadRow(' + "'" + record._id + "','" + type + "'," + "'" + mode + "')" + '">' +
                    '&nbsp;' + uText + '&nbsp;</a>';
                e.cellHtml = x;
            }
        }
        function uploadRow(id, type, mode) {
            var row = grid2.getRowByUID(id);
            if (row) {
                var subId = row["SubID"];
                var casesId = row["CasesID"];
                var url = '/cases/getSubFiles';
                $.getJSON(url, {SubID: subId, Type: type}, function (result) {
                    if (result.success) {
                        var att = result.data || [];
                        doUpload(casesId, subId, att, type, row, mode);
                    }
                });
            }
        }
        function doUpload(casesId, subId, ids, type, row, mode) {
            var title = '商标版权交单申请资料';
            var showHis = 0;
            // if (type == "Accept" || type == "Exp" || type == "Aud") showHis = 1;
            var attId = "";
            if (ids.length > 0) attId = ids.join(",");
            mini.open({
                url: '/attachment/addFile?IDS=' + attId + '&Mode=' + mode + '&ShowHis=' + showHis,
                width: 800,
                height: 400,
                title: title,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                        var attId = data.AttID;
                        var url = '/work/tradeCases/saveSubFiles';
                        var arg = {CasesID: casesId, SubID: subId, AttID: attId, Type: type};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                var field = "ZLFiles";
                                if (type == "Tech") field = "TechFiles";
                                var obj = {};
                                obj[field] = attId;
                                var now = row[field];
                                if (!now) grid.updateRow(row, obj);
                            }
                        })
                    });
                    iframe.contentWindow.addEvent('eachFileRemoved', function (data) {
                        var casesID = row["CasesID"];
                        if (casesID) {
                            var url = '/cases/removeSubFiles';
                            $.post(url, {CasesID: casesID, AttID: data.ATTID}, function (result) {
                                if (result.success == false) {
                                    mini.alert('删除文件信息失败，请联系管理员解决问题。');
                                } else {
                                    doReload(grid);
                                }
                            })
                        }
                    });
                    if (type == "Accept" && mode != 'Browse') {
                        iframe.contentWindow.enableCommit(grid);
                    }
                },
                ondestroy: function () {
                    if (type != 'Exp' && type != 'Aud') grid.reload();
                }
            });
        }
        function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailForm);
            detailForm.style.display = "block";
            grid2.load({CasesID:row.CASESID});
        }
        function addCases() {
            mini.open({
                url: '/work/tradeCases/add',
                width: '100%',
                height: '100%',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            });
        }
        function ShowMemo(id, title) {
            var rows=grid.getSelecteds();
            if(rows.length>1){
                var c=[];
                for(var i=0;i<rows.length;i++){
                    c.push(rows[i]["CasesID"]);
                }
                id=c.join(',');
                title="多条交单批量添加";
            }

            mini.open({
                url: '/cases/addMemo?CasesID=' + id,
                showModal: true,
                width: 1000,
                height: 600,
                title: "【" + title + "】进度信息",
                onDestroy: function () {
                    grid.deselectAll();
                    grid.reload();
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
        function editCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/work/tradeCases/edit?Mode=Edit&ID=' + id,
                    width: '100%',
                    height: '100%',
                    showModal: true,
                    ondestroy: function () {
                        grid.reload();
                    }
                });
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
                            var url='/work/tradeCases/commit';
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
        function deleteCases(){
            var record=grid.getSelected();
            if(!record) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            if(state==2 || state==4)return ;
            if(createMan!=parseInt(userId)) return ;
            var casesid=record["CASESID"] || "";
            if(!casesid) return;
            function g(){
                var url='/work/tradeCases/removeCases';
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
        function exportBill(){
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var url='/work/tradeCasesAll/getBillObject';
                var row=rows[0];
                var casesId=row["CASESID"];
                $.post(url,{CasesID:casesId},function(result){
                    if(result.success){
                        var billObject=result.data || {};
                        var excel = new complexExcelData(grid);
                        excel.export('OtherBill', '商标申请待缴费清单.xls',billObject);
                    } else {
                        mini.alert(result.message || "获取数据失败，无法导出!");
                    }
                });
            } else mini.alert('请选择要代缴的年费清单项目!');
        }
        function  moneyRender(e){
            var val=e.value;
            var num=parseInt(val || 0);
            if(isNaN(num))num=0;
            if(num==0)return ""; else return val;
        }
    </script>

</@js>