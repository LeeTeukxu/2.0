<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js?v=${version}"></script>
    <script type="text/javascript" src="/js/common/complexExcelExport.js?v=${version}"></script>
<script type="text/javascript">
    var states = [
        {id: 1, text: '待提交'},
        {id: 2, text: '待接单'},
        {id: 3, text: '已驳回'},
        {id: 4, text: '已接单'},
        {id:5,text:'未引用'},
        {id: 6, text: '已引用'},
        {id: 7, text: '待结算'},
        {id: 8, text: '已结算'},
        {id: 9, text: '全部'}
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
                            <a class="mini-button" iconcls="icon-add" id="TechSupport_AddCases"
                               onclick="addCases">技术支持申请</a>
                            <a class="mini-button" iconcls="icon-edit" id="TechSupport_EditCases" onclick="editCases"
                               visible="false">编辑</a>
                            <a class="mini-button" iconcls="icon-ok" id="TechSupport_AuditCases"
                               onclick="auditCases" visible="false">技术接单</a>
                            <a class="mini-button" iconcls="icon-ok" id="TechSupport_Upload"
                               onclick="uploadTechFile()" visible="false">上传交底文件</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconCls="icon-remove" id="TechSupport_RemoveCases"
                               onclick="deleteCases" visible="false">删除</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="TechSupport_BrowseCases"
                               onclick="browseCases">查看交单</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox TechSupport_Query" style="width:250px"
                                   emptyText="备注/流水号/客户/商务人员"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success TechSupport_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
                            <a class="mini-button mini-button-danger TechSupport_Reset"  id="a2"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info TechSupport_HighQuery" onclick="expand"  iconCls="icon-expand">高级查询</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;;display:none">
                <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                    <tr>
                        <td style="width:80px;text-align: center;">商务人员</td>
                        <td><input name="CreateMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                   valueFromSelect="true" style="width:100%" data-oper="EQ"/></td>

                        <td style="width:80px;text-align: center;">申请时间</td>
                        <td><input class="mini-datepicker" name="CreateTime" style="width:100%" data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="CreateTime" style="width:100%" data-oper="LE"
                                   dateFormat="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">客户名称</td>
                        <td><input class="mini-textbox" name="ClientName" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">开始时间</td>
                        <td><input class="mini-datepicker" name="BeginTime" style="width:100%" data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">结束时间</td>
                        <td><input class="mini-datepicker" name="EndTime" style="width:100%" data-oper="LE"
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
                        <td style="width:80px;text-align: center;">到访地址</td>
                        <td><input class="mini-textbox" name="Address" style="width:100%" data-oper="LIKE"/></td>
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
                     sortorder="desc" pagesize="20" onload="afterload" url="/techSupport/getData?State=${State}"
                     autoload="true"  onshowrowdetail="onShowRowDetail" idField="DocSN" showLoading="true">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div type="expandcolumn" field="expand"></div>
                        <div type="comboboxcolumn" field="State" width="80" headerAlign="center" align="center"
                             allowSort="true">业务状态
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="Memo" width="150"  align="center"  headeralign="center"  allowSort="true">备注</div>
                        <div field="DocSN" width="150" align="center"  headeralign="center"
                             allowSort="true">专利挖掘流水号</div>
                        <div field="TechType" width="100" align="center" headeralign="center" type="comboboxcolumn" allowSort="true">技术领域
                            <input  property="editor"  class="mini-combobox" url="/systems/dict/getByDtId?dtId=25" valueFromSelect="true"/>
                        </div>
                        <div field="CreateMan" width="100"  align="center"  headeralign="center" type="treeselectcolumn"   allowSort="true">商务人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="ClientName" align="center" width="200" headeralign="center"
                             allowSort="true">客户名称
                        </div>
                        <div field="Address" align="center" width="200" headeralign="center">
                            到访地址
                        </div>
                        <div field="BeginTime" align="center" width="120" headeralign="center" dataType="date"
                             allowSort="true"
                             dateFormat="yyyy-MM-dd HH:mm" allowsort="true">开始时间
                        </div>
                        <div field="EndTime" align="center" width="120" headeralign="center" dataType="date"
                             allowSort="true"
                             dateFormat="yyyy-MM-dd HH:mm" allowsort="true">结束时间
                        </div>
                        <div field="TechMan"width="80" align="center"  headeralign="center" type="treeselectcolumn"
                             allowSort="true">技术支持
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditMan"width="80" align="center"  headeralign="center" type="treeselectcolumn"
                             allowSort="true">接单人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" align="center" width="120" headeralign="center" dataType="date"   allowSort="true"
                             dateFormat="yyyy-MM-dd HH:mm" allowsort="true">接单时间
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="detailForm" style="display:none">
    <div class="mini-datagrid" id="datagrid2" style="width:100%;height:400px" onDrawCell="onDraw"
         showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick"
         sortfield="UploadTime" sortorder="desc" pagesize="20" onload="afterload" url="/techSupport/getFiles?State=${State}"
         autoload="true">
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
            <div field="CreateMan" width="100" align="center" headeralign="center"
                 type="treeselectcolumn" allowSort="true">商务人员
                <input property="editor" class="mini-treeselect"
                       url="/systems/dep/getAllLoginUsersByDep"
                       textField="Name" valueField="FID" parentField="PID"/>
            </div>
            <div field="ClientID" align="center" width="200" headeralign="center"
                 type="treeselectcolumn" allowSort="true">
                客户名称
                <input property="editor" class="mini-treeselect"
                       url="/systems/client/getAllClientTree"/>
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
            <div field="SubState" width="120" align="center" headeralign="center"
                 type="comboboxcolumn">业务状态
                <input property="editor" class="mini-combobox" data="subStates" />
            </div>
            <div field="YName" width="120" align="center" headeralign="center">业务类型</div>
            <div field="TechManName" width="80" align="center" headeralign="center">案件代理师</div>
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
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var results=[{id:'同意接单',text:'同意接单'},{id:'拒绝接单',text:'拒绝接单'}];
        var roleName = '${RoleName}';
        var userId = '${UserID}';
        var grid = mini.get('datagrid1');
        var grid2=mini.get('datagrid2');
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        var cmdAdd=mini.get('#TechSupport_AddCases');
        var cmdEdit=mini.get('#TechSupport_EditCases');
        var cmdBrowse=mini.get('#TechSupport_BrowseCases');
        var cmdAudit=mini.get('#TechSupport_AuditCases');
        var cmdDelete=mini.get('#TechSupport_RemoveCases');
        var cmdUpload=mini.get('#TechSupport_Upload');
        var cmdA1=mini.get('a1');
        var cmdA2=mini.get('a2');
        var cmdA3=mini.get('a3');
        var txtQuery=mini.get('queryText');
        var detailForm=null;
        $(function () {
            $('#p1').hide();
            cmdAdd.show();
            cmdEdit.hide();
            cmdAudit.hide();
            cmdBrowse.show();
            cmdUpload.hide();
            detailForm=document.getElementById("detailForm");
        })
        function addCases() {
            mini.open({
                url: '/techSupport/add',
                width: '100%',
                height: '100%',
                showModal: true,
                title:'新增专利挖掘申请',
                ondestroy: function () {
                    grid.reload();
                }
            });
        }
        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.open({
                    url: '/techSupport/edit?Mode=Browse&ID=' + id,
                    width: '100%',
                    height: '100%',
                    title:'查看专利挖掘申请',
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
                    url: '/techSupport/edit?Mode=Edit&ID=' + id,
                    width: '100%',
                    height: '100%',
                    title:'编辑专利挖掘申请',
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
                    html: ' <input labelField="true" label="接单结果" class="mini-combobox"  id="Result"  ' +
                        'style="width:100%" labelStyle="width:60px" value="同意接单" data="results" ' +
                        'onvaluechanged="resultChanged"/>' +
                        '<textarea labelField="true" label="意见" class="mini-textarea"  id="ResultText"  ' +
                        'style="width:100%;height:40px" ' +
                        'labelStyle="width:60px" value="同意接单" required="true"></textarea>',
                    buttons: ['yes', 'no'],
                    title: '处理结果',
                    width: 300,
                    height: 180,
                    callback: function (action) {
                        if (action == 'yes') {
                            var url='/techSupport/commit';
                            var result=mini.get('#Result').getValue();
                            var resultText=mini.get('#ResultText').getValue();
                            if(result && resultText)
                            {
                                mini.mask({
                                    html:'正在提交数据......'
                                });
                                $.post(url,{ID:id,Result:result,ResultText:resultText},function(result){
                                    if(result.success){
                                        mini.unmask();
                                        mini.alert('接单处理成功！','系统提示',function(){
                                            grid.reload();
                                        });
                                    } else mini.alert(result.message || '接单处理失败，请稍候重试！');
                                });
                            } else {
                                mini.alert('请填写意见后再进行处理操作。');
                            }
                        }
                    }
                });
                mini.parse();
            } else mini.alert('选择要接单的记录!');
        }
        function resultChanged(e){
            var value=e.value;
            var con=mini.get('#ResultText');
            if(con)con.setValue(value);
        }
        function deleteCases(){
            var record=grid.getSelected();
            if(!record) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            if(state!=1 && state!=3  && state!=2){
                mini.alert('只有未提交和被驳回的记录才可以被删除!');
                return ;
            }
            if(createMan!=parseInt(userId))
            {
               if(roleName!='系统管理员'  && roleName!="财务人员" && roleName.indexOf("流程")==0)  {
                   mini.alert('只有该业务的创建人才可以删除记录!');
               }
            }

            var casesid=record["CasesID"] || "";
            if(!casesid) return;
            function g(){
                var url='/techSupport/deleteSupport';
                $.post(url,{CasesID:casesid},function(result){
                    if(result.success){
                        mini.alert('选择的专利挖掘记录删除成功!');
                        grid.reload();
                    } else {
                        mini.alert(result.message || "删除失败，请稍候重试!");
                    }
                })
            }
            mini.confirm('确认要删除选择的专利挖掘记录？','删除提示',function(result){
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
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setIconCls("icon-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
            grid.setHeight('100%');
            grid.doLayout();
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
        function onRowClick(e) {
            var record=e.record ;
            var state=parseInt(record.State || 1);
            var createMan=record["CreateMan"] || "";
            var auditMan=parseInt(record["AuditMan"] || 0);
            cmdAdd.show();
            cmdEdit.hide();
            cmdDelete.hide();
            cmdAudit.hide();
            cmdUpload.hide();
            cmdBrowse.show();
            var userIdValue=parseInt(userId);
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
                cmdDelete.show();
            }
            else if(state==4){
                var techMans=$.trim(record["TechMan"] ||"").split(",");
                var mans=[createMan];
                if(roleName=="系统管理员")mans.push(userIdValue);
                for(var i=0;i<techMans.length;i++){
                    var techMan=parseInt(techMans[i] ||0);
                    mans.push(techMan);
                }
                if(mans.indexOf(userIdValue)>-1) cmdUpload.show(); else cmdUpload.hide();
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
            if(field=='TechFiles' || field=="ZLFiles" ){
                if(now){
                    if(field=="ZLFiles") type="Zl";
                    isOk=true;
                }
            } else if(field=="AcceptTechFiles"){
                type="Accept";
                isOk=true;
            }
            if(isOk){
                var x='<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                        ' onclick="uploadRow('+"'"+record._id+"','"+type+"',"+"'"+mode+"')"+'">'+
                        '&nbsp;' + uText+'&nbsp;</a>';
                e.cellHtml=x;
            }
            if(field=="expand"){
                var attNum=parseInt(record["AttNum"]);
                if(attNum==0) e.cellHtml="";

            }
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
                grid2.setUrl('/techSupport/getFiles');
                grid2.load({CasesID:row.CasesID});
            }
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
                var fields=['Memo',"DocSN","ClientName","CreateManName","Address"];
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
        function uploadTechFile(){
            var row=grid.getSelected();
            if(row){
                uploadRow(row._id,'Tech','Add');
            }
        }
        function uploadRow(id,type,mode){
            var row=grid.getRowByUID (id);
            if(row){
                var casesId=row["CasesID"];
                var url='/techSupport/getTechFiles';
                $.getJSON(url,{CasesID:casesId},function(result){
                    if(result.success){
                        var att=result.data || [];
                        doUpload(casesId,att,row,mode);
                    }
                });
            }
        }
        function doUpload(casesId,ids,row,mode){
            var title='每个技术交底书对应上传一个文件，同一个交底书多个文件请做成压缩包上传';
            mini.open({
                url:'/attachment/addFile?IDS='+ids+'&Mode='+mode,
                width:900,
                height:500,
                title:title,
                onload:function(){
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                        var attId = data.AttID;
                        var url = '/techSupport/saveTechFile';
                        var arg = {CasesID: casesId,AttID: attId};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                doReload(grid);
                            } else mini.alert('保存技术交底文件失败，请联系管理员解决问题。');
                        })
                    });
                    iframe.contentWindow.addEvent('eachFileRemoved', function (data) {
                        var casesID = row["CasesID"];
                        if (casesID) {
                            var url = '/techSupport/deleteTechFile';
                            $.post(url, {CasesID: casesID, AttID: data.ATTID,Check:0}, function (result) {
                                if (result.success == false) {
                                    mini.alert('删除文件信息失败，请联系管理员解决问题。');
                                } else {
                                    doReload(grid);
                                }
                            })
                        }
                    });
                },
                ondestroy:function(){
                    grid.reload();
                }
            });
        }
        function updateNumber() {
            var url = "/techSupport/getStateNumber";
            $.getJSON(url, {}, function (result) {
                var rows = result.data || {};
                for (var state in rows) {
                    var num = parseInt(rows[state]);
                    var con = $('.' + state);
                    if (con.length > 0) {
                        con.text(num);
                    }
                }
            })
        }
    </script>
</@js>