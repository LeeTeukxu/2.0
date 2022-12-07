  <#include "/shared/layout.ftl">
<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
 <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
<div class="mini-fit" style="overflow:hidden">
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" bodyStyle="overflow:hidden">
            <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                <table style="width:100%;" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" iconcls="icon-ok" id="TradeCases_Audit"
                               onclick="auditCases" visible="false">指定技术人员</a>
                            <a class="mini-button" iconcls="icon-cancel" id="TradeCases_UnAudit"
                               onclick="unAuditCases" visible="false">取消提交商标局</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="TradeCases_Browse"
                               onclick="browseCases">查看交单信息</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox" style="width:250px" emptyText="备注/流水号/业备数量/客户/商务人员"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success" id="a3" onclick="doQuery">模糊查询</a>
                            <a class="mini-button mini-button-primary" id="a1" onclick="doHightSearch">搜索结果</a>
                            <a class="mini-button mini-button-danger"  id="a2" onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info" onclick="expand">高级查询</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:110px;padding:5px;">
                <table style="width:100%;height:100%;padding:0px;margin:0px" cellspacing="5px" cellpadding="5px" id="highQueryForm">
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
                     showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick" sortfield="CreateTime"
                     sortorder="desc" pagesize="20" onload="afterload" url="/work/tradeCasesComplete/getData?State=${State}"
                     autoload="true"  idField="DocSN">
                    <div property="columns">
                        <div type="expandcolumn"></div>
                        <div field="Action" width="60" align="center"  headeralign="center" >进度</div>
                        <div field="Memo" width="150"  align="center"  headeralign="center" >备注</div>
                        <div field="DocSN" width="150" align="center"  headeralign="center" >专利交单流水号</div>
                        <div field="CreateMan" width="100"  align="center"  headeralign="center" type="treeselectcolumn">商务人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="SignTime" width="120" datatype="date" dateFormat="yyyy-MM-dd HH:mm"  align="center"  headeralign="center">交单时间</div>
                        <div field="ContractNo" width="120" headeralign="center"  align="center" >合同编号</div>
                        <div field="ClientID" align="center" width="200" headeralign="center" type="treeselectcolumn">
                            客户名称
                            <input property="editor" class="mini-treeselect" url="/systems/client/getAllClientTree"/>
                        </div>
                        <div field="Nums" width="200" headeralign="center"  align="center" >专利类型及数量</div>
                        <div field="AuditText" align="center" width="120" headeralign="center" >是否交单</div>
                        <div field="AuditMan"width="80" align="center"  headeralign="center" type="treeselectcolumn">审核人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" align="center" width="120" headeralign="center" dataType="date"
                             dateFormat="yyyy-MM-dd HH:mm" allowsort="true">审核时间
                        </div>
                        <div field="ZXR"  align="center" headerAlign="center" >确定撰写人</div>
                        <div field="TechMan" align="center" headerAlign="center" width="80"  type="treeselectcolumn">专利撰写人
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="TechNums"  align="center" headerAlign="center" width="200">撰写数量</div>
                        <div field="TechTime"  align="center" headerAlign="center" width="200">预计完成时间</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <div class="mini-window" title="指定技术员" width="600" height="200" style="display:none" id="SetTechManWindow">
        <table style="width:100%" class="layui-table" id="SetTechManForm">
            <tr>
                <td>指定技术人员</td>
                <td>
                    <div name="AllocTechMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=AcceptTech" required="true" expandonload="true"
                         textField="Name" valueField="FID" parentField="PID" allowInput="true"
                         valueFromSelect="true"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="acceptTechMan();">确认操作</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('SetTechManWindow')
">取消操作
                    </button>
                </td>
            </tr>
        </table>
    </div>
<#--<div id="detailForm" style="display:none">-->
<#--    <div class="mini-datagrid" id="datagrid2" style="width:100%;"-->
<#--         showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick" sortfield="State"-->
<#--         sortorder="desc" pagesize="20" url="/work/tradeCasesCommit/getData?State=4"-->
<#--         autoload="true" onload="afterload" showPager="false">-->
<#--        <div property="columns">-->
<#--            <div field="Type" headerAlign="center" align="center">专利类型</div>-->
<#--            <div field="Memo" headerAlign="center" align="center" width="150">技术文件说明</div>-->
<#--            <div field="FileName" headerAlign="center" align="center" width="150">技术文件名称</div>-->
<#--            <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn">撰写人-->
<#--                <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"-->
<#--                       textField="Name" valueField="FID" parentField="PID"/>-->
<#--            </div>-->
<#--            <div field="UploadTime" headerAlign="center" align="center" width="120"-->
<#--                 dataType="date" dateFormat="yyyy-MM-dd">文件上传时间-->
<#--            </div>-->
<#--            <div field="AuditText" headerAlign="center" align="center">核稿意见</div>-->
<#--            <div field="AuditMan" headerAlign="center" align="center" type="treeselectcolumn">文件核稿人-->
<#--                <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"-->
<#--                       textField="Name" valueField="FID" parentField="PID"/>-->
<#--            </div>-->
<#--            <div field="AuditTime" headerAlign="center" align="center" width="120" dataType="date"-->
<#--                 dateFormat="yyyy-MM-dd">核稿时间-->
<#--            </div>-->
<#--            <div field="ShenBaoText" headerAlign="center" align="center">申报意见</div>-->
<#--            <div field="ShenBaoMan" headerAlign="center" align="center" type="treeselectcolumn">申报审核人-->
<#--                <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"-->
<#--                       textField="Name" valueField="FID" parentField="PID"/>-->
<#--            </div>-->
<#--            <div field="ShenBaoTime" headerAlign="center" align="center" width="120" dataType="date"-->
<#--                 dateFormat="yyyy-MM-dd">申报审核时间-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--</div>-->
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
        var cmdAudit = mini.get('#TradeCases_Audit');
        var cmdUnAudit=mini.get('#TradeCases_UnAudit');

        var cmdA1=mini.get('a1');
        var cmdA2=mini.get('a2');
        var cmdA3=mini.get('a3');
        var txtQuery=mini.get('queryText');
        var detailForm=null;
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
                btn.setText("高级查询");
                $('#p1').css('display', "block");
                cmdA1.show();
                cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                //btn.setIconCls("panel-expand");
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

        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/work/tradeCasesTech/edit?Mode=Browse&ID=' + id,
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
            cmdBrowse.show();
            // if(state==6 || state==7){
            //     cmdAudit.show();
            //     cmdUnAudit.hide();
            // }
            // else if(state==8){
            //     cmdAudit.hide();
            //     cmdUnAudit.show();
            // }
            if (state == 7 && roleName.indexOf("流程") > -1 && roleName.indexOf("技术") > -1){
                cmdAudit.show();
                cmdUnAudit.hide();
            }else if(state==4){
                cmdAudit.show();
                cmdUnAudit.hide();
            }else if(state==8){
                cmdAudit.hide();
                cmdUnAudit.show();
            }else {
                cmdAudit.hide();
                cmdUnAudit.hide();
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
                    e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            }
            else if (field == "UploadCPC") {
                var casesId = record["CASESID"];
                var upTime = record["CPCUploadTime"];
                var text = (upTime == null) ? "上传" : "上传或下载";
                e.cellHtml = '<a href="#" onclick="onFileupload(' + "'" + casesId + "'" + ')">' + text + '</a>';
            }
            else if (field == "UploadReq") {
                var casesId = record["CASESID"];
                var upTime = record["ReqUploadTime"];
                var text = (upTime == null) ? "上传" : "上传或下载";
                e.cellHtml = '<a href="#" onclick="onReqUpload(' + "'" + casesId + "'" + ')">' + text + '</a>';
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
        function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailForm);
            detailForm.style.display = "block";
            grid2.load({CasesID:row.CASESID});
        }
        function auditCases(){
            // var row=grid.getSelected();
            // if(row){
            //     var casesId=row["CASESID"];
            //     mini.confirm('确认要将选择的业务交单提交到完结？','系统提示',function(result){
            //         if(result=='ok'){
            //             var url='/work/tradeCasesComplete/commit';
            //             $.post(url,{CasesID:casesId,complete:true},function(nn){
            //                 if(nn.success)
            //                 {
            //                     mini.alert('指定业务交单已完结!','系统提示',function(){grid.reload();});
            //                 } else mini.alert(nn.message || "操作失败，请稍候重试!");
            //
            //             });
            //         }
            //     });
            // } else mini.alert('请选择要设置的交单记录!');
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#SetTechManWindow');
                win.show();
            } else mini.alert('请选择要处理的案件记录!');
        }
        function unAuditCases(){
            var row=grid.getSelected();
            if(row){
                var casesId=row["CASESID"];
                mini.confirm('确认取消选择业务的完结状态？','系统提示',function(result){
                    if(result=='ok'){
                        var url='/work/tradeCasesComplete/commit';
                        $.post(url,{CasesID:casesId,complete:false},function(nn){
                            if(nn.success)
                            {
                                mini.alert('指定业务交单已取消完结!','系统提示',function(){grid.reload();});
                            } else mini.alert(nn.message || "操作失败，请稍候重试!");

                        });
                    }
                });
            } else mini.alert('请选择要设置的交单记录!');
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
        /*
            指定技术人员。
        */
        function acceptTechMan() {
            var techManForm = new mini.Form('#SetTechManForm');
            techManForm.validate();
            if (techManForm.isValid() == false) {
                mini.alert('请选择人员以后再进行确认操作!');
                return;
            }
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["CASESID"]);
            }
            if (subs.length == 0) {
                mini.alert('请选择要指定技术人员的业务单据!');
                return;
            }
            var data = techManForm.getData();
            var techMan = data['AllocTechMan'];

            function g() {
                var iis = mini.loading('正在提交信息，请稍候.........');
                var url = '/work/tradeCasesComplete/acceptTechTask';
                var arg = {CASESIDS: subs.join(','), TechMan: techMan};
                $.post(url, arg, function (result) {
                    mini.hideMessageBox(iis);
                    if (result.success) {
                        mini.alert('案件技术人员设置成功!', '系统提示', function () {
                            var win = mini.get('#SetTechManWindow');
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || '案件技术人员设置失败，请稍候重试!');
                })
            }

            mini.confirm('确认要给选择的案件指定技术员吗？', '系统提示', function (act) {
                if (act == 'ok') g();
            });
        }
    </script>
</@js>