<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js?v=${version}"></script>
    <script type="text/javascript" src="/js/common/complexExcelExport.js?v=${version}"></script>
<script type="text/javascript">
    var states = [
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
        {id:70,text:'业务取消'}
    ];
</script>
<div class="mini-fit" style="overflow:hidden">
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" bodyStyle="overflow:hidden">
            <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                <table style="width:100%;" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" iconcls="icon-add" id="CasesBrowse_AddCases"
                               onclick="addCases">增加业务交单</a>
                            <a class="mini-button" iconcls="icon-edit" id="CasesBrowse_EditCases" onclick="editCases"
                               visible="false">编辑</a>
                            <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_AuditCases"
                               onclick="auditCases" visible="false">交单审批</a>
                            <a class="mini-button" iconcls="icon-no" id="CasesBrowse_ReAudit" onclick="reAudit
                            ()">取消审核</a>
                            <a class="mini-button" iconCls="icon-ok" id="CasesBrowse_Complete"
                               onclick="completeCases" visible="false">业务完成</a>
                            <a class="mini-button" iconCls="icon-no" id="CasesBrowse_UnComplete"
                               onclick="unCompleteCases" visible="false">取消业务完成</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconCls="icon-remove" id="CasesBrowse_RemoveCases"
                               onclick="deleteCases" visible="false">删除</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="CasesBrowse_BrowseCases"
                               onclick="browseCases">查看交单</a>
                            <a class="mini-button" iconcls="icon-print" id="CasesBrowse_Config"
                               onclick="configCLevel">设置案件级别</a>
                            <a class="mini-button" iconcls="icon-collapse" id="CasesBrowse_ExportBill"
                               onclick="exportBill">生成对帐单</a>
                            <#if RoleName=="系统管理员"  || RoleName=="财务人员">
                                <a class="mini-button" iconCls="icon-tip" onclick="showSell()">财务统计</a>
                            </#if>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox CasesBrowse_Query" style="width:250px"
                                   emptyText="备注/合同号/流水号/业备数量/客户/商务人员"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success CasesBrowse_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
<#--                            <a class="mini-button mini-button-primary CasesBrowse_HighQuery" id="a1"-->
<#--                               onclick="doHightSearch">搜索数据</a>-->
                            <a class="mini-button mini-button-danger CasesBrowse_Reset"  id="a2"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info CasesBrowse_HighQuery" onclick="expand"  iconCls="icon-expand">高级查询</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;;display:none">
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
                        <td><input  name="CreateMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                    allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                    valueFromSelect="true"
                                    style="width:100%"
                                    data-oper="EQ"/></td>
                        <td style="width:80px;text-align: center;">流程人员</td>
                        <td><input  name="AuditMan"  class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                   valueFromSelect="true"
                                   style="width:100%"
                                   data-oper="EQ"/></td>
                        <td style="width:80px;text-align: center;">代理师</td>
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
            <div class="mini-fit" id="fitt" style="width:100%;height:99%;overflow:hidden">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" onDrawCell="onDraw" multiSelect="true"
                     onrowdblclick="BrowseCases" onrowclick="onRowClick" sortfield="CreateTime"
                     sortorder="desc" pagesize="20" onload="afterload" url="/casesAll/getData?State=${State}"
                     autoload="true"  onshowrowdetail="onShowRowDetail" idField="DocSN" showLoading="true">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div type="expandcolumn"></div>
                        <div type="comboboxcolumn" field="State" width="120" headerAlign="center" align="center"
                             allowSort="true">业务状态
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="Action" width="60" align="center"  headeralign="center" >进度</div>
                        <div field="Memo" width="150"  align="center"  headeralign="center"    allowSort="true">备注</div>
                        <div field="DocSN" width="150" align="center"  headeralign="center"    allowSort="true">专利交单流水号</div>
                        <div field="CreateMan" width="100"  align="center"  headeralign="center" type="treeselectcolumn"   allowSort="true">商务人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="SignTime" width="120" datatype="date" dateFormat="yyyy-MM-dd HH:mm"  align="center"   allowSort="true"  headeralign="center">交单时间</div>
                        <div field="ContractNo" width="120" headeralign="center"  align="center"    allowSort="true">合同编号</div>
<#--                        <div field="ClientID" align="center" width="200" headeralign="center" type="treeselectcolumn"   allowSort="true">-->
<#--                            客户名称-->
<#--                            <input property="editor" class="mini-treeselect" url="/systems/client/getAllClientTree"/>-->
<#--                        </div>-->

                        <div field="ClientName" align="center" width="200" headeralign="center" allowSort="true">客户名称</div>
                        <div field="Nums" width="200" headeralign="center"  align="center"    allowSort="true">专利类型及数量</div>
                        <div field="FeiJian"  align="center" width="100" headeralign="center"  allowsort="true">
                            费减情况
                        </div>
                        <div field="ZuanLiFei" align="center" width="120" headeralign="center" allowsort="true"   allowSort="true">专利申请费</div>
                        <div field="TotalDai" align="center" width="120" headeralign="center" allowsort="true"
                             renderer="moneyRender" allowSort="true">应收代理费</div>
                        <div field="UsedDai" align="center" width="120" headeralign="center" allowsort="true" renderer="moneyRender"
                             allowSort="true">实收代理费</div>
                        <div field="FreeDai" align="center" width="120" headeralign="center" allowsort="true"  renderer="moneyRender"
                             allowSort="true">未收代理费余额</div>
                        <div field="TotalGuan" align="center" width="120" headeralign="center" allowsort="true"  renderer="moneyRender"
                             allowSort="true">应收官费</div>
                        <div field="UsedGuan" align="center" width="120" headeralign="center" allowsort="true"  renderer="moneyRender"
                             allowSort="true">实收官费</div>
                        <div field="FreeGuan" align="center" width="120" headeralign="center" allowsort="true"  renderer="moneyRender"
                             allowSort="true">未收官费余额</div>
                        <div field="AuditMan"width="80" align="center"  headeralign="center" type="treeselectcolumn"   allowSort="true">审核人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" align="center" width="120" headeralign="center" dataType="date"   allowSort="true"
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
         showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick" sortfield="CreateTime"
         sortorder="desc" pagesize="10"  url="/casesTech/getData?State=All" showPager="true"
         autoload="false">
        <div property="columns">
            <div type="indexcolumn"></div>
            <div type="comboboxcolumn" field="State" width="120" headerAlign="center" align="center">业务状态
                <input property="editor" class="mini-combobox" data="states"/>
            </div>
            <div field="AcceptTechFiles" headerAlign="center" align="center">技术资料</div>
            <div field="TechMan"  headerAlign="center" align="center" type="treeselectcolumn">案件代理师
                <input property="editor" class="mini-treeselect"  url="/systems/dep/getAllLoginUsersByDep"
                       textField="Name" valueField="FID" parentField="PID" /></div>
            <div field="Memo" headerAlign="center" align="center">立案说明</div>
            <div field="SubNo" headerAlign="center" align="center" width="120">立案编号</div>
            <div field="YName" headerAlign="center" align="center" vtype="required" width="150">案件名称</div>
            <div field="ShenqingName" width="150" headerAlign="center" vtype="required" align="center">专利初步名称</div>
            <div field="ClientRequiredDate" width="150" headerAlign="center" align="center"
                 dataType="date" dateFormat="yyyy-MM-dd">初稿交付时限</div>
            <div field="TechFiles" headerAlign="center" align="center" width="120">技术交底资料</div>
            <div field="ZLFiles" headerAlign="center" width="120" align="center">著录信息资料</div>
            <div field="ClientLinkMan"  name="clientLinkMan"  width="150" headerAlign="center"
                 align="center">客户联系人
            </div>
            <div field="ClientLinkPhone" width="150" headerAlign="center" align="center">联系电话</div>
            <div field="ClientLinkMail" headerAlign="center" align="center"  width="150">联系邮箱</div>
        </div>
    </div>
</div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var results=[{id:'同意交单',text:'同意交单'},{id:'拒绝交单',text:'拒绝交单'}];
        var roleName = '${RoleName}';
        var userId = '${UserID}';
        var grid = mini.get('datagrid1');
        var grid2=mini.get('datagrid2');
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        var cmdAdd=mini.get('#CasesBrowse_AddCases');
        var cmdEdit=mini.get('#CasesBrowse_EditCases');
        var cmdBrowse=mini.get('#CasesBrowse_BrowseCases');
        var cmdAudit=mini.get('#CasesBrowse_AuditCases');
        var cmdDelete=mini.get('#CasesBrowse_RemoveCases');
        var cmdComplete=mini.get('#CasesBrowse_Complete');
        var cmdUnComplete=mini.get('#CasesBrowse_UnComplete');
        var cmdReAudit=mini.get('#CasesBrowse_ReAudit');
        var cmdA1=mini.get('a1');
        var cmdA2=mini.get('a2');
        var cmdA3=mini.get('a3');
        var txtQuery=mini.get('queryText');
        var detailForm=null;
        $(function () {
            $('#p1').hide();
            //cmdA1.hide();
            //cmdA2.hide();

            cmdAdd.show();
            cmdEdit.hide();
            cmdAudit.hide();
            cmdComplete.hide();
            cmdUnComplete.hide();
            cmdBrowse.show();
            cmdReAudit.hide();
            detailForm=document.getElementById("detailForm");
        })
        function deleteCases(){
            var record=grid.getSelected();
            if(!record) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            if(state!=1)return ;
            if(createMan!=parseInt(userId))
            {
               if(roleName!='系统管理员') return ;
            }

            var casesid=record["CasesID"] || "";
            if(!casesid) return;
            function g(){
                var url='/cases/removeCases';
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
        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("icon-collapse");
                btn.setText("隐藏");
                $('#p1').css('display', "block");
                //cmdA1.show();
                //cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setIconCls("icon-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
                //cmdA1.hide();
                //cmdA2.hide();
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();

            grid.setHeight('100%');
            grid.doLayout();
        }

        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/cases/edit?Mode=Browse&ID=' + id,
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
        function editCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/cases/edit?Mode=Edit&ID=' + id,
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
                            if (row["CasesID"] == code) return true;
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
        function completeCases(){
            var row = grid.getSelected();
            if(row){
                function g(){
                    var casesId=row["CasesID"];
                    var url='/cases/completeOne';
                    $.post(url,{CasesID:casesId},function(result){
                        if(result.success){
                            mini.alert('选择交单业务设置为完成状态成功!','系统提示',function(){
                                grid.reload();
                            });
                        } else mini.alert(result.message || '设置失败，请稍候重试！'  );
                    });
                }
                mini.confirm('确认要将选择的业务设置为完成状态吗？','系统提示',function(act){
                    if(act=='ok') g();
                });
            }
        }
        function unCompleteCases(){
            var row = grid.getSelected();
            if(row){
                function g(){
                    var casesId=row["CasesID"];
                    var url='/cases/unCompleteOne';
                    $.post(url,{CasesID:casesId},function(result){
                        if(result.success){
                            mini.alert('选择交单业务设置为未完成状态成功!','系统提示',function(){
                                grid.reload();
                            });
                        } else mini.alert(result.message || '设置失败，请稍候重试！'  );
                    });
                }
                mini.confirm('确认要将选择的业务设置为未完成状态吗？','系统提示',function(act){
                    if(act=='ok') g();
                });
            }
        }
        function onRowClick(e) {
            var record=e.record ;
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            var auditMan=parseInt(record["AuditMan"] || 0);
            cmdAdd.show();
            cmdEdit.hide();
            cmdDelete.hide();
            cmdAudit.hide();
            cmdBrowse.show();
            cmdComplete.hide();
            cmdUnComplete.hide();
            cmdReAudit.hide();
            if(state==1 || state==3){
                if(createMan==userId){
                    cmdEdit.show();
                    cmdBrowse.show();
                    cmdDelete.show();
                }
                else {
                    if(roleName=="系统管理员"){
                        cmdDelete.show();
                    }
                }
            }
            else if(state==2){
                cmdBrowse.show();
                cmdAudit.show();
            }
            else if(state==4){
               if(auditMan==userId)cmdReAudit.show();
            }
            else if(state==7){
                cmdComplete.show();
            }
            else if(state==8){
                cmdUnComplete.show();
            }
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;

            var mode="Browse";
            var now=record[field];
            var uText="查看";
            var type="Tech";
            var isOk=false;//只有特定字段才处理。
            if (field == "Action") {
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMODE"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay'>查看</span>";
                }
                e.cellHtml ='<a href="javascript:void(0)"  data-placement="bottomleft"  hCode="' + record["CasesID"] + '" ' +
                        'class="showCellTooltip" onclick="ShowMemo(' + "'" + record["CasesID"] + "'," + "'" +
                        record["DocSN"] + "'" + ')">' + text + '</a>';
            }
            else if(field=='TechFiles' || field=="ZLFiles" ){
                if(now){
                    if(field=="ZLFiles") type="Zl";
                    isOk=true;
                }
            } else if(field=="AcceptTechFiles"){
                type="Accept";
                isOk=true;
            }
            else if(field=="UsedGuan"){
                var tuiGuan=parseFloat(record["TuiGuan"] || 0);
                if(tuiGuan>0){
                    e.cellHtml=e.value+"<span style='color:red'>-"+tuiGuan.toFixed(0)+"</span>"
                }
            }
            else if(field=="UsedDai"){
                var TuiDai=parseFloat(record["TuiDai"] || 0);
                if(TuiDai>0){
                    e.cellHtml=e.value+"<span style='color:red'>-"+TuiDai.toFixed(0)+"</span>"
                }
            }
            if(isOk){
                var x='<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                        ' onclick="uploadRow('+"'"+record._id+"','"+type+"',"+"'"+mode+"')"+'">'+
                        '&nbsp;' + uText+'&nbsp;</a>';
                e.cellHtml=x;
            }
            var state=parseInt(record["State"]);
            var col=e.column;
            if(col.type=='expandcolumn'){
                if(state<4){
                    e.cellHtml="";
                }
                //只有创建者和他的上级才可以看到明细。
                if(isCreator(record)==false){
                    e.cellHtml="";
                }
            }
            if(state==70){
                e.rowStyle = "text-decoration:line-through;text-decoration-color: black;color:red";
            }
        }

        function isCreator(row){
            if(roleName=="系统管理员" || roleName.indexOf("财务")>-1 || roleName.indexOf("流程")>-1) return true;
            var createMan=(row["CreateMan"] || "").toString().split(",");
            var createManager=(row["CreateManager"] || "").toString().split(",");
            var g=[];
            for(var i=0;i<createMan.length;i++)g.push(parseInt(createMan[i]));
            for(var i=0;i<createManager.length;i++)g.push(parseInt(createManager[i]));
            var userValue=parseInt(userId || 0);
            return g.indexOf(userValue)>-1;
        }
        function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;
            var state=parseInt(row["State"] || 1);
            if(state>=4)
            {
                var td = grid.getRowDetailCellEl(row);
                td.appendChild(detailForm);
                detailForm.style.display = "block";
                grid2.load({CasesID:row.CasesID});
            }
        }
        function addCases() {
            mini.open({
                url: '/cases/add',
                width: '100%',
                height: '100%',
                showModal: true,
                title:'新增业务交单',
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
            txtQuery.setValue(null);
            comField.setValue('All');
            doHightSearch();
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
                            var url='/cases/commit';
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


        function uploadTechFile(){
            var row=grid2.getSelected();
            if(row){
                uploadRow(row._id,'Accept','Add');
            }
        }
        function uploadRow(id,type,mode){
            var row=grid2.getRowByUID (id);
            if(row){
                var subId=row["SubID"];
                var casesId=row["CasesID"];
                var url='/cases/getSubFiles';
                $.getJSON(url,{SubID:subId,Type:type},function(result){
                    if(result.success){
                        var att=result.data || [];
                        doUpload(casesId,subId,att,type,row,mode);
                    }
                });
            }
        }
        function doUpload(casesId,subId,ids,type,row,mode){
            var title='技术交底资料';
            if(type=="Zl")title="著录信息资料";
            if(type=="Accept")title="技术资料";
            mini.open({
                url:'/attachment/addFile?IDS='+ids+'&Mode='+mode,
                width:800,
                height:400,
                title:title,
                ondestroy:function(){
                    grid.reload();
                }
            });
        }
        function configCLevel(){
            window.parent.configCLevel();
        }
        function exportBill(){
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var url='/casesAll/getBillObject';
                var row=rows[0];
                var casesId=row["CasesID"];
                $.post(url,{CasesID:casesId},function(result){
                    if(result.success){
                        var billObject=result.data || {};
                        var excel = new complexExcelData(grid);
                        excel.export('CasesBill', '专利申请待缴费清单.xls',billObject);
                    } else {
                        mini.alert(result.message || "获取数据失败，无法导出!");
                    }
                });
            } else mini.alert('请选择要代缴的年费清单项目!');
        }
        function reAudit(){
            var record=grid.getSelected();
            if(!record) {
                mini.alert('请选择要取消审核的记录!');
                return;
            }
            var casesid=record["CasesID"] || "";
            if(!casesid) return;
            function g(){
                var url='/cases/reAudit';
                $.post(url,{CasesID:casesid},function(result){
                    if(result.success){
                        mini.alert("操作成功,"+record["DocSN"]+"已被取消审核，目前状态是审核失败，你可以【修改后重新提交审核】或者【删除业务交单】。");
                        grid.reload();
                    } else {
                        mini.alert(result.message || "操作失败，请稍候重试!");
                    }
                })
            }
            mini.confirm('即将对【'+record["DocSN"]+'】执行取消审核操作，该笔业务将变更为【审核失败】状态，是否继续?','取消审核',function(action){
                if(action=='ok'){
                    g();
                }
            });
        }
        function  moneyRender(e){
            var val=e.value;
            var num=parseInt(val || 0);
            if(isNaN(num))num=0;
            if(num==0)return ""; else return val;
        }
        function showSell(){
            window.parent.showSell();
        }
    </script>
</@js>