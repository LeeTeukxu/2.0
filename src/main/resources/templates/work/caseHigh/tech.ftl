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
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<script type="text/javascript" src="/js/clipboard.min.js"></script>
<script type="text/javascript" src="/js/common/excelExport.js"></script>
<script type="text/javascript" src="/js/jquery.fileDownload.js"></script>
<script type="text/javascript">
    var states = [
        {id: 1, text: '业务交单-待提交'},
        {id: 2, text: '业务交单-待审核'},
        {id: 3, text: '业务交单-已驳回'},
        {id: 4, text: '业务交单-已审批'},
        {id: 7, text: '业务交单-未完成'},
        {id: 8, text: '业务交单-已完成'},
        {id: 9, text: '业务交单-全部'},
        {id: 50, text: '代理师未定'},
        {id: 51, text: '代理师已定'},
        {id: 52, text: '待内审'},//{id: 52, text: '待定稿'},
        {id: 54, text: '内审通过'},//{id: 54, text: '客户定稿'},
        {id: 53, text: '内审驳回'},// {id: 53, text: '客户未定稿'},
        {id: 55, text: '客户定稿'},//{id: 55, text: '未申报'},
        {id: 56, text: '已申报'},
        {id: 58, text: '已通过'},
        {id: 57, text: '未通过'}
    ];
    var repType=[
        {id:'内部出具',text:'内部出具'},
        {id:'外部出具',text:'外部出具'},
        {id:'不需要审计报告',text:'不需要审计报告'}
    ];
</script>
<div class="mini-fit" style="overflow:hidden">
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" bodyStyle="overflow:hidden">
            <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                <table style="width:100%;" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" iconCls="icon-user" id="CaseHighBrowse_SetTechMan" visible="false" onclick="setTechMan">指定技术员</a>
                            <a class="mini-button" iconcls="icon-ok" id="CaseHighBrowse_AcceptTech" visible="false"
                               onclick="auditCases">技术接单</a>
                            <a class="mini-button" iconcls="icon-no" id="CaseHighBrowse_RejectTech" visible="false"
                               onclick="rejectTechTask">取消接单</a>
                            <a class="mini-button" iconCls="icon-user" id="CaseHighBrowse_ChangeJS" visible="false"
                               onclick="showChangeTechMan">更换代理师</a>
                            <a class="mini-button" iconcls="icon-upload" id="CaseHighBrowse_UploadTechFile" visible="false"
                               onclick="uploadTechFile">上传项目申报文件</a>
                            <a class="mini-button" iconcls="icon-ok" id="CaseHighBrowse_CommitTech" visible="false"
                               onclick="commitAcceptTech">提交内审</a>
                            <a class="mini-button  客户定稿" iconcls="icon-ok" id="CaseHighBrowse_TechPass" visible="false"
                               onclick="showAuditTech(1)">内审通过</a>
                            <a class="mini-button 客户驳回" iconcls="icon-no" id="CaseHighBrowse_TechRoll" visible="false"
                               onclick="showAuditTech(0)">内审驳回</a>

                            <a class="mini-button  " iconcls="icon-ok" id="HighBrowse_KHPass" visible="false"
                               onclick="showClientSet(1)">客户定稿</a>
                            <a class="mini-button" iconcls="icon-ok" id="CaseHighBrowse_TechSBPass" visible="false"
                               onclick="showTechSB(1)">流程申报</a>
                            <a class="mini-button" iconcls="icon-no" id="CaseHighBrowse_TechSBRoll" visible="false"
                               onclick="showTechSB(0)">申报驳回</a>

                            <a class="mini-button" iconcls="icon-ok" id="CaseHighBrowse_SettlementPass" visible="false"
                               onclick="showSettle(1)">已通过</a>
                            <a class="mini-button" iconcls="icon-no" id="CaseHighBrowse_SettlementRoll" visible="false"
                               onclick="showSettle(0)">未通过</a>

                            <a class="mini-button" iconCls="icon-xls" id="CaseHighBrowse_Export"
                               onclick="doExport()">导出Excel</a>
                            <a class="mini-button" iconCls="icon-download" id="CaseHighBrowse_Download"
                               onclick="downloadFile()">下载文件</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="CaseHighBrowse_BrowseCases"
                               onclick="browseCases">查看交单信息</a>
                            <#if State==51>
                                <a class="mini-button" iconCls="icon-tip" onclick="showWait()">待处理统计</a>
                            </#if>
                            <#if State==56>
                                    <button class="xButton" style="height:1px;width:1px;background-color:transparent;
                                    color:transparent"></button>
                            </#if>

                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox CaseHighBrowse_Query" style="width:250px"
                                   emptyText="备注/合同号/流水号/业备数量/客户/商务人员"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success CaseHighBrowse_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
<#--                            <a class="mini-button mini-button-primary CaseHighBrowse_HighQuery" id="a1"-->
<#--                               onclick="doHightSearch">搜索数据</a>-->
                            <a class="mini-button mini-button-danger CaseHighBrowse_Reset" id="a2"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info CaseHighBrowse_HighQuery" onclick="expand"   iconCls="icon-expand">高级查询</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:180px;padding:5px;">
                <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                    <tr>
                        <td style="width:80px;text-align: center;">案件名称</td>
                        <td><input class="mini-textbox" name="YName" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">客户名称</td>
                        <td><input class="mini-textbox" name="ClientName" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">申报绝限</td>
                        <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%" data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%" data-oper="LE"
                                   dateFormat="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">立案编号</td>
                        <td><input class="mini-textbox" name="SubNo" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">案件代理师</td>
                        <td><input class="mini-textbox" name="TechManNames" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">商务人员</td>
                        <td><input class="mini-textbox" name="CreateManName" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">技术联系人</td>
                        <td><input class="mini-textbox" name="ClientLinkMan" style="width:100%" data-oper="LIKE"/></td>

                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">立案说明</td>
                        <td><input class="mini-textbox" name="Memo" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">内部编号</td>
                        <td><input class="mini-textbox" name="NBBH" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">业务数据汇总</td>
                        <td><input class="mini-textbox" name="Nums" style="width:100%" data-oper="LIKE"/></td>

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
                     multiSelect="true" frozenStartColumn="0" frozenEndColumn="4"
                     sortorder="desc" pagesize="20" onload="afterload" url="/caseHighTech/getData?State=${State}"
                     autoload="true" onselectionchanged="onSelectionChanged" oncelldblclick="onCellDbClick"
                    <#if  State==51 || State==52>
                        sortField="AcceptTechTime"
                    <#elseif State==54 || State==53>
                        sortField="ClientSetTime"
                    <#elseif State==55 || State==56>
                        sortField="TechSBTime"
                    <#elseif State==57 || State==58>
                        sortField="SettleTime"
                    <#else>
                        sortfield="AuditTime"
                    </#if>>
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div type="comboboxcolumn" field="State" width="120" headerAlign="center" align="center"
                             allowSort="true">业务状态
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="ProcessText" headerAlign="center" align="center" >进度说明</div>
                        <div field="Memo" headerAlign="center" align="center">立案说明</div>
                        <div field="SubNo" headerAlign="center" align="center" width="120" allowSort="true">立案编号</div>
                        <div field="ClientName" headerAlign="center" align="center" width="240" allowSort="true">客户名称
                        </div>
                        <div field="Area"  headerAlign="center" align="center" width="120">所在地区</div>
                        <div field="Nums" headerAlign="center" align="center" width="200">业务数量汇总</div>
                        <div field="YName" headerAlign="center" align="center" vtype="required" allowSort="true">案件名称
                        </div>
                        <div field="SBYear"  headerAlign="center" align="center" width="80">申报年度</div>
                        <div field="RptType"  headerAlign="center" align="center" width="100">审计报告来源
                            <input property="editor" class="mini-combobox" data="repType"/>
                        </div>

                        <div field="ContainSJF"   headerAlign="center" align="center" type="comboboxcolumn"
                             width="100">是否包含审计费
                            <input property="editor" class="mini-combobox" data="[{id:1,text:'是'},{id:0,text:'否'}]"/>
                        </div>
                        <div field="SJFMoney" headerAlign="center" align="center" width="120">审计费</div>
                        <div field="SWSName"  headerAlign="center" align="center" width="150">会计事务所名称</div>
                        <#if State==52>
                             <div field="CommitTechMemo" headerAlign="center" width="200" align="center">特别说明</div>
                        </#if>
                        <#if State==53>
                            <div field="ClientSetTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                                 dateFormat="yyyy-MM-dd">客户驳回时间</div>
                            <div field="AuditTechMemo" headerAlign="center" width="200" align="center">客户定稿驳回原因</div>
                            <div field="AuditTechFiles" headerAlign="center" align="center">客户定稿驳回说明文件</div>
                        </#if>
                        <#if State=54>
                            <div field="ClientSetTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                                 dateFormat="yyyy-MM-dd">客户定稿时间</div>
                             <div field="AuditTechMemo" headerAlign="center" width="200" align="center">审核意见</div>
                        </#if>
                        <#if State gt 51>
                             <div field="AcceptTechFiles" headerAlign="center" align="center">项目申报文件</div>
                        </#if>
                        <#if State==55>
                             <div field="TechSBMemo" headerAlign="center" width="200" align="center">未申报原因</div>
                        </#if>
                        <#if State==57>
                              <div field="SettleMemo" headerAlign="center" width="200" align="center">未通过原因</div>
                        </#if>
                        <div field="SupportMan" headerAlign="center" align="center" width="150">技术谈判人</div>
                        <div field="ClientInfo" headerAlign="center" align="center" width="150">技术联系人信息</div>
                        <div field="ClientRequiredDate" width="150" headerAlign="center" align="center" allowSort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">客户要求申报绝限
                        </div>
                        <div field="CreateMan" headerAlign="center" align="center" type="treeselectcolumn"
                             allowSort="true">商务人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>

                        <div field="SignTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                             dateFormat="yyyy-MM-dd">签单日期
                        </div>
                        <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn"
                             allowSort="true">代理师
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AcceptTechTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                             dateFormat="yyyy-MM-dd">接单日期
                        </div>
                        <div field="AuditMan" headerAlign="center" align="center" type="treeselectcolumn"
                             allowSort="true">流程审核人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                             dateFormat="yyyy-MM-dd">流程审核时间
                        </div>
                        <#if State gt 52>
                            <div field="AuditTechMan" headerAlign="center" align="center" type="treeselectcolumn">客户定稿人员
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                       parentField="PID"/>
                            </div>
                            <div field="AuditTime" headerAlign="center" align="center" dataType="date"
                                 dateFormat="yyyy-MM-dd HH:mm:ss" width="150">客户定稿时间
                            </div>
                        </#if>
                        <#if State gt 54>
                            <div field="TechSBMan" headerAlign="center" align="center" type="treeselectcolumn">项目申报员
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                       parentField="PID"/>
                            </div>
                            <div field="TechSBTime" headerAlign="center" align="center" dataType="date"
                                 dateFormat="yyyy-MM-dd">项目申报时间
                            </div>
                        </#if>
                        <#if State gt 56>
                            <div field="SettleMan" headerAlign="center" align="center" type="treeselectcolumn">项目人员
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                       parentField="PID"/>
                            </div>
                            <div field="SettleTime" headerAlign="center" align="center" dataType="date"
                                 dateFormat="yyyy-MM-dd">确定时间
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="mini-window"  bodyStyle="overflow:hidden"  title="指定技术员" width="600" height="200" style="display:none" id="SetTechManWindow">
    <table style="width:100%" class="layui-table" id="SetTechManForm">
        <tr>
            <td>指定代理师</td>
            <td>
                <div name="AllocTechMan" class="mini-treeselect" style="width:100%"
                     url="/systems/dep/getAllLoginUserByFun?FunName=AcceptTech"  required="true"   expandonload="true"
                     textField="Name" valueField="FID" parentField="PID" allowInput="true" valueFromSelect="true"></div>
            </td>
        </tr>
        <tr>
            <td>指定内审人</td>
            <td>
                <div name="AllocAuditMan" class="mini-treeselect" style="width:100%"
                     url="/systems/dep/getAllLoginUserByFun?FunName=TechPass"  required="true"  expandonload="true"
                     textField="Name" valueField="FID" parentField="PID" allowInput="true" valueFromSelect="true"></div>
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
<div class="mini-window"  bodyStyle="overflow:hidden"  title="确认接单-成为本案件代理师" width="650" height="180" style="display:none;overflow:hidden"
     id="AcceptWindow">
    <table style="width:100%;overflow: hidden" class="layui-table" id="AcceptForm">
        <tr>
            <td>指定内审人</td>
            <td>
                <div id="AllocAuditMan" class="mini-treeselect" style="width:100%"
                     url="/systems/dep/getAllLoginUserByFun?FunName=TechPass" expandOnload="true"
                     textField="Name" valueField="FID" parentField="PID" value="${Manager}"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <button class="mini-button mini-button-success" onclick="acceptTech();">确认操作</button>
                <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('AcceptWindow')
">取消接单
                </button>
            </td>
        </tr>
    </table>
</div>

<div class="mini-window"   bodyStyle="overflow:hidden" title="项目申报文件提交内审" width="600" height="240" style="display:none;overflow:hidden"
     id="CommitWindow">
    <table style="width:100%" class="layui-table" id="CommitForm">
        <tr>
            <td style="width:100px">特别说明</td>
            <td>
                <textarea class="mini-textarea" style="height:80px;width:100%" id="CommitMemo"></textarea>
            </td>
        </tr>
        <tr>
            <td>指定内审人</td>
            <td>
                <div id="ConAuditMan" class="mini-treeselect" style="width:100%"
                     url="/systems/dep/getAllLoginUsersByDep"
                     textField="Name" valueField="FID" parentField="PID" value="${Manager}" enabled="false"></div>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <button class="mini-button mini-button-success" id="CmdCommitFiles" onclick="commitFiles();
">确认操作
                </button>
                <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('CommitWindow')
">取消关闭
                </button>
            </td>
        </tr>
    </table>
</div>
<div  bodyStyle="overflow:hidden" class="mini-window" title="内审是否通过" width="650" height="300" style="display:none;
overflow:hidden" id="AuditWindow">
    <table style="width:100%" class="layui-table" id="AuditForm">
        <tr>
            <td style="width:150px">审核说明</td>
            <td>
                <textarea class="mini-textarea" style="height:80px;width:100%" name="AuditMemo"></textarea>
            </td>
        </tr>
        <tr id="TrAudFile">
            <td>内审驳回说明文件</td>
            <td style="text-align:center">
                <buttn class="mini-button mini-button-info" id="UploadAudFile" onclick="uploadAuditFile()
    " style="width:100%">上传或查看
                </buttn>
            </td>
        </tr>
        <tr>
            <td>审核结果</td>
            <td>
                <input style="width:100%" name="AuditResult" class="mini-combobox"
                       data="[{id:'1',text:'内审合格'},{id:'0', text:'内审驳回'}]" readonly="true"/>
            </td>
        </tr>

        <tr>
            <td colspan="2" style="text-align:center">
                <button class="mini-button mini-button-success" onclick="auditTechFiles();">确认操作</button>
                <button class="mini-button mini-button-danger" style="margin-left:180px" onclick="closeWindow
                ('AuditWindow')
">取消关闭
                </button>
            </td>
        </tr>
    </table>
</div>

<div   bodyStyle="overflow:hidden"  class="mini-window" title="客户是否定稿" width="600" height="280" style="display:none" id="ClientSetWindow">
    <table style="width:100%" class="layui-table" id="ClientSetForm">
        <tr>
            <td style="width:100px">审核说明</td>
            <td>
                <textarea class="mini-textarea" style="height:80px;width:100%" name="ClientSetMemo"></textarea>
            </td>
        </tr>
        <tr id="TrExpFile">
            <td>情况说明文件</td>
            <td style="text-align:center">
                <buttn class="mini-button mini-button-info" id="UploadExpFile" onclick="uploadExplainFile()
" style="width:100%">上传或查看
                </buttn>
            </td>
        </tr>
        <tr>
            <td>审核结果</td>
            <td>
                <input style="width:100%" name="ClientSetResult" class="mini-combobox"
                       data="[{id:'1',text:'客户定稿'},{id:'0', text:'客户不定稿'}]" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <button class="mini-button mini-button-success" style="margin-left:80px" onclick="clientSetFiles();">
                    确认操作
                </button>
                <button class="mini-button mini-button-danger" style="margin-left:180px" onclick="closeWindow
                ('ClientSetWindow')
">取消关闭
                </button>
            </td>
        </tr>
    </table>
</div>
<div   bodyStyle="overflow:hidden"  class="mini-window" title="是否流程申报" width="600" height="240" style="display:none" id="TechSBWindow">
    <table style="width:100%" class="layui-table" id="TechSBForm">
        <tr>
            <td style="width:100px">审核说明</td>
            <td>
                <textarea class="mini-textarea" style="height:80px;width:100%" name="SBMemo"></textarea>
            </td>
        </tr>
        <tr>
            <td>审核结果</td>
            <td>
                <input style="width:100%" name="SBResult" class="mini-combobox"
                       data="[{id:'1',text:'流程申报'},{id:'0', text:'流程不申报'}]" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <button class="mini-button mini-button-success" onclick="techSB();">确认操作</button>
                <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('TechSBWindow')
">取消关闭
                </button>
            </td>
        </tr>
    </table>
</div>

<div   bodyStyle="overflow:hidden"  class="mini-window" title="是否通过" width="600" height="240" style="display:none" id="SettleWindow">
    <table style="width:100%" class="layui-table" id="SettleForm">
        <tr>
            <td style="width:100px">审核说明</td>
            <td>
                <textarea class="mini-textarea" style="height:80px;width:100%" name="SettleMemo"></textarea>
            </td>
        </tr>
        <tr>
            <td>审核结果</td>
            <td>
                <input style="width:100%" name="SettleResult" class="mini-combobox"
                       data="[{id:1,text:'通过'},{id:0, text:'不通过'}]" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <button class="mini-button mini-button-success" onclick="auditSettle();">确认操作</button>
                <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('SettleWindow')
">取消关闭
                </button>
            </td>
        </tr>
    </table>
</div>

<div   bodyStyle="overflow:hidden"   class="mini-window" title="技术联系人信息" width="500" height="240" style="display:none" id="LinkManWindow">
    <table style="width:100%" class="layui-table" id="LinkManForm">
        <tr>
            <td style="width:100px">联系人姓名</td>
            <td>
                <input class="mini-textbox" style="width:100%" name="ClientLinkMan"/>
                <input class="mini-hidden" name="SubID"/>
            </td>
        </tr>
        <tr>
            <td style="width:100px">联系人邮箱</td>
            <td>
                <input class="mini-textbox" style="width:100%" name="ClientLinkMail"/>
            </td>
        </tr>
        <tr>
            <td style="width:100px">联系人手机</td>
            <td>
                <input class="mini-textbox" style="width:100%" name="ClientLinkPhone"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <button class="mini-button mini-button-success" onclick="saveLinkInfo();">确认保存</button>
                <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('LinkManWindow')
">取消关闭
                </button>
            </td>
        </tr>
    </table>
</div>
<div   bodyStyle="overflow:hidden"  class="mini-window" title="选择技术谈判人" width="600" height="160" style="display:none" id="SupportManWindow">
        <table style="width:100%" class="layui-table" id="SupportManForm">
            <tr>
                <td>指定技术谈判人:</td>
                <td>
                    <div id="ConSupportMan" class="mini-treeselect" name="SupportMan" style="width:100%"
                         valueFromSelect="true"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="true" required="true" expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false"    allowInput="true"></div>
                    <input class="mini-hidden" name="SubID"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" id="CmdSupportMan" onclick="changeSupportMan();">确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('SupportManWindow')">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
<div   bodyStyle="overflow:hidden"  class="mini-window" title="更换代理师" width="600" height="200" style="display:none" id="ChangeTechManWindow">
        <table style="width:100%" class="layui-table" id="ChangeTechManForm">
            <tr>
                <td>原代理师:</td>
                <td>
                    <div id="OldMan" class="mini-treeselect" name="OldMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true"
                         expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false" enabled="false"></div>
                    <input class="mini-hidden" name="SubID"/>
                </td>
            </tr>
            <tr>
                <td>更换为:</td>
                <td>
                    <div id="NewMan" class="mini-treeselect" name="NewMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true" expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false"
                         allowInput="true"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" name="CmdChangeTechMan" onclick="changeTechMan()">确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('ChangeTechManWindow')">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var roleName = '${RoleName}';
        var userId = parseInt('${UserID}', 10);
        var grid = mini.get('datagrid1');
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        var cmdBrowse = mini.get('#CaseHighBrowse_BrowseCases');
        var cmdAccept = mini.get('#CaseHighBrowse_AcceptTech');
        var cmdReject = mini.get('#CaseHighBrowse_RejectTech');
        var cmdCommit = mini.get('#CaseHighBrowse_CommitTech');
        var cmdUpload = mini.get('#CaseHighBrowse_UploadTechFile');
        var cmdTechPass = mini.get('#CaseHighBrowse_TechPass');
        var cmdTechRoll = mini.get('#CaseHighBrowse_TechRoll');
        var cmdKHPass=mini.get('#HighBrowse_KHPass');
        var cmdSBPass = mini.get('#CaseHighBrowse_TechSBPass');
        var cmdSBRoll = mini.get('#CaseHighBrowse_TechSBRoll');
        var cmdChangeTech=mini.get('CaseHighBrowse_ChangeJS');
        var cmdSettlePass = mini.get('#CaseHighBrowse_SettlementPass');
        var cmdSettleRoll = mini.get('#CaseHighBrowse_SettlementRoll');
        var cmdSetTechMan=mini.get('#CaseHighBrowse_SetTechMan');

        var cmdA1 = mini.get('a1');
        var cmdA2 = mini.get('a2');
        var cmdA3 = mini.get('a3');
        var txtQuery = mini.get('queryText');
        $(function () {
            $('#p1').hide();
            //cmdA1.hide();
            //cmdA2.hide();
            cmdAccept.hide();
            cmdCommit.hide();

            cmdAccept.hide();
            cmdReject.hide();
            cmdCommit.hide();
            cmdUpload.hide();
            cmdTechPass.hide();
            cmdTechRoll.hide();
            cmdSBPass.hide();
            cmdSBRoll.hide();
            cmdKHPass.hide();
            cmdSettlePass.hide();
            cmdSettleRoll.hide();
            cmdSetTechMan.hide();
            cmdChangeTech.hide();
        })

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setText("折叠");
                btn.setIconCls("icon-collapse");
                $('#p1').css('display', "block");
                //cmdA1.show();
                //cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setText("高级查询");
                btn.setIconCls("icon-expand");
                $('#p1').css('display', "none");
                //cmdA1.hide();
                //cmdA2.hide();
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
        }

        function closeWindow(winName) {
            var win = mini.get('#' + winName);
            if (win) win.hide();
        }

        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.MainID;
                mini.open({
                    url: '/caseHigh/edit?Mode=Browse&ID=' + id,
                    width: '100%',
                    height: '100%',
                    title: '查看业务交单',
                    showModal: true,
                    ondestroy: function () {
                        grid.reload();
                    }
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
                        var field= $(el).attr('field');
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["SubID"] == code) return true;
                        });
                        if (row) {
                            if(field=="ClientInfo"){
                                var linkMan = "<div>技术联系人:" + row["ClientLinkMan"] + "</div>";
                                var linkPhone = row["ClientLinkPhone"];
                                if (linkPhone) linkMan += "<div>联系电话:" + linkPhone + "</div>";
                                var linkEmail = row["ClientLinkMail"];
                                if (linkEmail) linkMan += "<div>联系邮箱:" + linkEmail + "</div>";
                                if (linkMan) {
                                    tip.setContent("<span style='text-align:left'>" + linkMan + '</span>');
                                }
                            }
                            else if(field=="ProcessText"){
                                var memo = row["ProcessMemo"];
                                if (memo) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                            'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                            'text-top; "> <tr><td>' +memo+ '</td></tr></table>');
                                }
                            }
                            else if(field=="Memo"){
                                var lxText=row["LXText"];
                                if(lxText){
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                        'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                        'text-top; "> <tr><td>' +lxText+ '</td></tr></table>');
                                }
                            }
                        }
                    }
                }
            });
            resetAllButton();
        }

        function resetAllButton() {
            cmdAccept.hide();
            cmdBrowse.hide();
            cmdReject.hide();
            cmdCommit.hide();
            cmdTechPass.hide();
            cmdTechRoll.hide();
            cmdSBPass.hide();
            cmdSBRoll.hide();
            cmdSettlePass.hide();
            cmdSettleRoll.hide();
            cmdUpload.hide();
            cmdSetTechMan.hide();
            cmdChangeTech.hide();
            cmdKHPass.hide();
        }

        function beforeEdit(e) {
            var field = e.field;
            if (field) {
                e.cancel = true;
                if (field == "NBBH") e.cancel = false;
            }
        }
        function showCommitOutTech(){
            var win=mini.get('OutTechWindow');
            var form=new mini.Form('#OutTechForm');
            form.reset();
            win.show();
        }

        function outSourceTech() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    if (subId) {
                        cs.push(subId);
                    }
                }
                if (cs.length > 0) {
                    function g() {
                        var con=mini.get('#ConOutTechMan');
                        var allocMan=con.getValue() || 0;
                        var url = '/casesTech/addOutSourceTech';
                        $.post(url, {IDS: cs.join(','),OutTechMan:allocMan}, function (result) {
                            if (result.success) {
                                mini.alert('设置项目外协成功!', '系统提示', function () {
                                    grid.reload();
                                    var win=mini.get('OutTechWindow');
                                    win.hide();
                                });
                            }
                        })
                    }

                    mini.confirm('确认要将选择案件设置为项目外协', '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                } else mini.alert('选择要处理的案件记录!');
            } else mini.alert('选择要处理的案件记录!');
        }




        function onSelectionChanged(e) {
            if (e.selecteds.length == 0) {
                resetAllButton();
                return;
            }
            resetAllButton();
            var rows = grid.getSelecteds();
            if (rows.length == 0) return;

            var record = rows[0];
            var state = parseInt(record.State || 1);
            var techMan = parseInt(record["TechMan"] || "0");

            var createMan = parseInt(record["CreateMan"] || 0);

            var xTechManager=record["TechManager"] || "";
            var tMs=xTechManager.split(',');
            var techManagers=[];
            for(var i=0;i<tMs.length;i++){
                var t=$.trim(tMs[i]);
                if(!t) continue;
                techManagers.push(parseInt(t));
            }
            //50未接。51已接。52待内审。
            // 53内审驳回。54内审通过。55客户定稿 56已申报 57未确定 58已确定。
            cmdBrowse.show();
            cmdUpload.setText('上传项目申报文件');
            if (state == 50) {
                cmdAccept.show();
                cmdSetTechMan.show();
            }
            else if (state == 51 || state == 53) {
                //只有接单的人才可以取消或上传项目申报文件
                if (techMan == userId) {
                    if (state == 51) cmdReject.show();
                    cmdUpload.show();
                }
                if(techManagers.indexOf(userId)>-1){
                    if(state==51)cmdReject.show();
                }
                if(state==51)  cmdChangeTech.show();
            }
            else if (state == 52) {
                cmdTechPass.show();
                cmdTechRoll.show();
            }
            else if (state == 54) {
                cmdKHPass.show();
            }
            else if(state==55){
                cmdSBPass.show();
                cmdSBRoll.show();
                //cmdUpload.show();
                //cmdUpload.setText('重传项目申报文件');
            }
            else if (state == 56 ) {
               cmdSettlePass.show();
               cmdSettleRoll.show();
            }
            else if(state==57){
                cmdUpload.show();
                cmdUpload.setText('重传项目申报文件');
            }
        }

        function setTechMan(){
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#SetTechManWindow');
                win.show();
            } else mini.alert('选择要处理的案件记录!');
        }
        /*
            指定代理师。
        */
        function acceptTechMan() {
            var techManForm=new mini.Form('#SetTechManForm');
            techManForm.validate();
            if(techManForm.isValid()==false){
                mini.alert('请选择人员以后再进行确认操作!');
                return ;
            }
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["SubID"]);
            }
            if(subs.length==0){
                mini.alert('请选择要指定代理师的业务单据!');
                return ;
            }
            var data=techManForm.getData();
            var techMan = data['AllocTechMan'];
            var auditMan=data["AllocAuditMan"]
            function g() {
                var iis=mini.loading('正在提交信息，请稍候.........');
                var url = '/caseHighTech/acceptTechTask';
                var arg = {SubIDS: subs.join(','), TechMan: techMan,AuditMan:auditMan};
                $.post(url, arg, function (result) {
                    mini.hideMessageBox(iis);
                    if (result.success) {
                        mini.alert('案件代理师设置成功!', '系统提示', function () {
                            var win = mini.get('#SetTechManWindow');
                            win.hide();
                            grid.reload();
                        });
                    }else mini.alert(result.message || '案件代理师设置失败，请稍候重试!');
                })
            }
            mini.confirm('确认要给选择的案件指定技术员吗？', '系统提示', function (act) {
                if (act == 'ok') g();
            });
        }




        function auditCases() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#AcceptWindow');
                win.show();
            } else mini.alert('选择要处理的案件记录!');
        }

        /同意接单/

        function acceptTech() {
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["SubID"]);
            }
            var auditMan = mini.get('#AllocAuditMan').getValue();
            if (auditMan) {
                function g() {
                    var url = '/caseHighTech/acceptTechTask';
                    var arg = {SubIDS: subs.join(','), AuditMan: auditMan};
                    $.post(url, arg, function (result) {
                        if (result.success) {
                            mini.alert('选择的案件认领成功!', '系统提示', function () {
                                var win = mini.get('#AcceptWindow');
                                win.hide();
                                grid.reload();
                            });
                        }
                    })
                }

                mini.confirm('确认要认领选择的案件吗？', '系统提示', function (act) {
                    if (act == 'ok') g();
                });
            } else mini.alert('请选择案件内审人!');
        }

        /取消接单/

        function rejectTechTask() {
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["SubID"]);
            }

            function g() {
                var url = '/caseHighTech/rejectTechTask';
                var arg = {SubIDS: subs.join(',')};
                $.post(url, arg, function (result) {
                    if (result.success) {
                        mini.alert('选择的案件撤消认领成功!', '系统提示', function () {
                            var win = mini.get('#AcceptWindow');
                            win.hide();
                            grid.reload();
                        });
                    }
                })
            }

            mini.confirm('确认要撤消认领选择的案件吗？', '系统提示', function (act) {
                if (act == 'ok') g();
            });
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            var mode = "Browse";
            var now = record[field];
            var uText = "查看";
            var type = "Tech";
            var state = parseInt(record["State"] || 0);
            var isOk = false;//只有特定字段才处理。
            if (field == 'TechFiles' || field == "ZLFiles") {
                if (field == "ZLFiles") type = "Zl";
                if (state < 60) {
                    if (now) {
                        uText = "管理";
                    } else {
                        uText = "上传";
                    }
                    mode = "Add";
                    isOk = true;
                }
            } else if (field == "AcceptTechFiles") {
                type = "Accept";
                var acceptTechFileName = record["AcceptTechFileName"];
                if (acceptTechFileName) uText = acceptTechFileName;
                isOk = true;
            }
            else if (field == "ExpFiles") {
                type = "Exp";
                isOk = true;
            }
            else if (field == "AuditTechFiles") {
                type = 'Aud';
                isOk = true;
            }
            if (isOk) {
                var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                        ' onclick="uploadRow(' + "'" + record._id + "','" + type + "'," + "'" + mode + "')" + '">' +
                        '&nbsp;' + uText + '&nbsp;</a>';
                e.cellHtml = x;
            } else {
                if (field == "ClientInfo") {
                    var clientLinkMan = record["ClientLinkMan"];
                    if (clientLinkMan) {
                        if (state != 56) {
                            e.cellHtml = '<a href="javascript:void(0)"  style="color:blue;text-decoration:underline" data-placement="bottomleft"  ' +
                                    'hCode="' + record["SubID"] + '" ' +
                                    'class="showCellTooltip"  field="ClientInfo">联系方式</a>';
                        } else {
                            e.cellHtml = '<a href="javascript:void(0)"  field="ClientInfo"  onclick="editTechLinkMan(' + "'" + record["_id"] + "'" + ')" ' +
                                    'style="color:blue;text-decoration:underline" data-placement="bottomleft"  ' +
                                    'hCode="' + record["SubID"] + '" ' + 'class="showCellTooltip">联系方式</a>';
                        }
                    } else {
                        e.cellHtml = '<a href="javascript:void(0)"  field="ClientInfo"  onclick="editTechLinkMan(' + "'" + record["_id"] + "'" + ')" ' +
                                'style="color:blue;text-decoration:underline">添加</a>';
                    }
                }
                if(field=="OutTech"){
                    var OX=Integer.parse(now);
                    if(OX==1) e.cellHtml="是"; else e.cellHtml="否";
                }
            }
            if(field=="ProcessText"){
                var dd=record[field] || "";
                var x='<a href="javascript:void(0)"  field="ProcessText"  data-placement="bottomleft"  style="color:blue;' +
                        'text-decoration:underline" ' +
                        'hcode="'+record["SubID"]+'"'+
                        ' class="showCellTooltip" onclick="showMemo('+"'"+record._id+"'"+')">'+(dd?"查看":"添加")+'</a>';
                e.cellHtml=x;
            }
            else if(field=="Memo"){
                if(state<60){
                    var ttt=record["Memo"] || "&nbsp;&nbsp;";
                    var x='<a href="javascript:void(0)"  field="Memo"  data-placement="bottomleft"  style="color:blue;' +
                        'text-decoration:underline" ' +
                        'hcode="'+record["SubID"]+'"'+
                        ' class="showCellTooltip" onclick="changeLXText('+"'"+record._id+"'"+')">'+ttt+'</a>';
                    e.cellHtml=x;
                }
            }
            else if (field == "SupportMan") {
                var value=record["SupportMan"] || "添加";
                if (state >= 51 && state < 54) {
                    e.cellHtml = '<a href="javascript:void(0)"  field="ClientInfo"  onclick="editSupportMan(' + "'" + record["_id"] + "'" + ')" ' +
                        'style="color:blue;text-decoration:underline">'+value+'</a>';
                }
            }
            if(state==70){
                e.rowStyle = "text-decoration:line-through;text-decoration-color: black;color:red";
            }
        }

        function editSupportMan(id) {
            var record = grid.getRowByUID(id);
            if (record) {
                var supportMan=record["SupportMan"] || "";
                var subId=record["SubID"];
                if(subId){
                    record["SupportMan"]=supportMan;
                    var win=mini.get('#SupportManWindow');
                    var form=new mini.Form('#SupportManForm');
                    form.reset();
                    var conS=mini.get('#ConSupportMan');
                    form.setData(record);
                    if(supportMan) conS.setText(supportMan);
                    win.show();
                }
            }
        }
        function changeSupportMan(){
            var win=mini.get('#SupportManWindow');
            var form=new mini.Form('#SupportManForm');
            form.validate();
            if(form.isValid()){
                var data=form.getData();
                var conS=mini.get('#ConSupportMan');
                data.SupportMan=conS.getText();
                var url = '/caseHighTech/changeSupportMan';
                $.post(url,data,function(result){
                    if(result.success){
                        mini.alert('技术谈判人员修改成功!','系统提示',function(){
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || "修改失败，请稍候重试!");
                })
            } else mini.alert('请选择技术谈判人员!');
        }
        function changeLXText(id){
            var record = grid.getRowByUID(id);
            if (record) {
                var memoText=record["Memo"] || "";
                var uid = mini.prompt("请输入立项说明：", "更改立项目说明描述", function (action,value) {
                    if(action=='ok'){
                        value=$.trim(value);
                        if(!value){
                            mini.alert('立项目说明不能为空!');
                            return ;
                        }
                        if(value==memoText){
                            mini.alert('立项目说明未发生更改，无需保存!');
                            return ;
                        }
                        function g(){
                            var url='/caseHighTech/changeLXText';
                            var arg={SubID:record["SubID"],NewLXText:value};
                            $.post(url,arg,function(result){
                                if(result.success){
                                    mini.alert('立项目说明更新成功!','系统提示',function(){
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || "更新失败，请稍候重试!");
                            })
                        }
                        g();
                    }
                }, true);
                var win = mini.getbyUID(uid);
                win.setWidth(400);
                $(win.el).find("textarea,input").width(340).val(memoText);
            }
        }
        function editTechLinkMan(id) {
            var record = grid.getRowByUID(id);
            if (record) {
                var win = mini.get('#LinkManWindow');
                var form = new mini.Form('#LinkManForm');
                form.reset();
                form.setData(record);
                win.show();
            }
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
                var fields = ['Memo', "SubNo","ClientName", "YName", "Nums"];
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
            doHightSearch();
        }

        function showMemo(ID){
            var record = grid.getRowByUID(ID);
            var subId = record["SubID"];
            var rows=grid.getSelecteds();
            if(rows.length>1){
                var c=[];
                for(var i=0;i<rows.length;i++){
                    c.push(rows[i]["SubID"]);
                }
                subId=c.join(',');
            }
            mini.open({
                url:'/addSingleMemo/index?ID='+subId,
                width:1000,
                height:600,
                showModal:true,
                title:'添加进度信息',
                onload:function(){
                    var iframe = this.getIFrameEl();
                    var saveUrl = '/casesOutSource/saveMemo?SubID=' + subId;
                    iframe.contentWindow.setConfig(saveUrl);
                    iframe.contentWindow.setSaveImageUrl('/addSingleMemo/saveImage?SubID='+subId);
                },
                onDestroy:function(){
                    grid.reload();
                }
            });
        }

        function uploadTechFile() {
            var row = grid.getSelected();
            if (row) {
                uploadRow(row._id, 'Accept', 'Add');
            }
        }

        function uploadExplainFile() {
            var row = grid.getSelected();
            if (row) {
                uploadRow(row._id, 'Exp', 'Add');
            }
        }

        function uploadAuditFile() {
            var row = grid.getSelected();
            if (row) {
                uploadRow(row._id, 'Aud', 'Add');
            }
        }

        function uploadRow(id, type, mode) {
            var row = grid.getRowByUID(id);
            if (row) {
                var subId = row["SubID"];
                var casesId = row["CasesID"];
                var url = '/caseHigh/getSubFiles';
                $.getJSON(url, {SubID: subId, Type: type}, function (result) {
                    if (result.success) {
                        var att = result.data || [];
                        doUpload(casesId, subId, att, type, row, mode);
                    }
                });
            }
        }

        function doExport() {
            var rows = grid.getData();
            if (rows.length > 0) {
                var excel = new excelData(grid);
                excel.export("接单明细.xls");
            } else mini.alert('没有可导出的内容!');
        }

        function doUpload(casesId, subId, ids, type, row, mode) {
            var title = '技术交底资料';
            if (type == "Zl") title = "著录信息资料";
            if (type == "Accept") title = "项目申报文件";
            if (type == "Exp") title = "情况说明文件";
            if (type == 'Aud') title = '内审驳回说明文件';
            var showHis = 0;
            if (type == "Accept" || type == "Exp" || type == "Aud") showHis = 1;
            mini.open({
                url: '/attachment/addFile?IDS=' + ids + '&Mode=' + mode + '&ShowHis=' + showHis,
                width: 800,
                height: 400,
                title: title,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                        var attId = data.AttID;
                        var url = '/caseHigh/saveSubFiles';
                        var arg = {CasesID: casesId, SubID: subId, AttID: attId, Type: type};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                var field = "ZLFiles";
                                if (type == "Tech") field = "TechFiles";
                                if (type == "Accept") field = "AcceptTechFiles";
                                if (type == "Aud") field = "AuditTechFiles";
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
                            var url = '/caseHigh/removeSubFiles';
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
                        iframe.contentWindow.enableCommit(grid,'/caseHighTech/commitTechFiles');
                    }
                },
                ondestroy: function () {
                    if (type != 'Exp' && type != 'Aud') grid.reload();
                }
            });
        }

        function commitFiles() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#CommitWindow');
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var acceptTechFiles = row["AcceptTechFiles"];
                    if (acceptTechFiles) {
                        cs.push(subId);
                    }
                }
                if (cs.length > 0) {
                    function g() {
                        var url = '/caseHighTech/commitTechFiles';
                        var memo = mini.get('#CommitMemo').getValue() || "";
                        var arg = {SubIDS: cs.join(','), Memo: memo};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.alert('选择的案件提交成功', '系统提示', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "提交审核失败，请稍候重试!");
                        });
                    }

                    mini.confirm('确认要将项目申报文件提交内审', '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('选择要处理的案件记录!');
        }

        function commitAcceptTech() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#CommitWindow');
                var treeCon = mini.get('#ConAuditMan');
                if (treeCon) {
                    var record = rows[0];
                    var auditMan = parseInt(record["TechAuditMan"] || 0, 10);
                    treeCon.setValue(auditMan);
                }
                win.show();
            } else mini.alert('选择要处理的案件记录!');
        }

        function onCellDbClick(e) {
            var field = e.field || "";
            if (field == "NBBH") {
                var clipboard = new Clipboard('.xButton', {
                    text: function () {
                        return e.record[field];
                    }
                });
                clipboard.on('success', function (e) {
                    mini.showTips({
                        content: '选择的内部编号复制成功。',
                        timeout: 3000,
                        x: 'center',
                        y: 'center',
                        state: 'success'
                    });
                });

                clipboard.on('error', function (e) {
                    mini.showTips({
                        content: '选择的内部编号复制失败:' + e.toString(),
                        timeout: 2000,
                        x: 'center',
                        y: 'center',
                        state: 'danger'
                    });
                });
                $('.xButton').click();
            }
        }

        function showAuditTech(result) {
            var win = mini.get('#AuditWindow');
            var form = new mini.Form('#AuditForm');
            form.reset();
            mini.getbyName('AuditResult').setValue(result);
            if (result == 1) {
                mini.get('#UploadAudFile').hide();
                $('#TrAudFile').hide();
            } else {
                mini.get('#UploadAudFile').show();
                $('#TrAudFile').show();
            }
            win.show();
        }

        function auditTechFiles() {
            var win = mini.get('#AuditWindow');
            var form = new mini.Form('#AuditForm');
            var data = form.getData();
            data.AuditMemo = mini.getbyName('AuditMemo').getValue();
            data.AuditResult = mini.getbyName('AuditResult').getValue();
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var acceptTechFiles = row["AcceptTechFiles"];
                    if (acceptTechFiles) {
                        cs.push(subId);
                    }
                }
                if (cs.length > 0) {
                    function g() {
                        var url = '/caseHighTech/auditTechFiles';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.alert('选择的案件审核成功', '系统提示', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "审核失败，请稍候重试!");
                        });
                    }

                    var ttext = "确认要将项目申报文件审核为内审通过?";
                    if (data.AuditResult == 0) ttext = "确认要将项目申报文件审核为内审驳回?";
                    mini.confirm(ttext, '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('选择要处理的案件记录!');
        }

        function showClientSet(result) {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#ClientSetWindow');
                var form = new mini.Form('#ClientSetForm');
                form.reset();
                mini.getbyName('ClientSetResult').setValue(result);
                if (result == 1) {
                    mini.get('#UploadExpFile').hide();
                    $('#TrExpFile').hide();
                } else {
                    mini.get('#UploadExpFile').show();
                    $('#TrExpFile').show();
                }
                win.show();
            }
        }

        function clientSetFiles() {
            var win = mini.get('#ClientSetWindow');
            var form = new mini.Form('#ClientSetForm');
            var data = {};
            data.Memo = mini.getbyName('ClientSetMemo').getValue();
            data.Result = mini.getbyName('ClientSetResult').getValue();

            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var acceptTechFiles = row["AcceptTechFiles"];
                    if (acceptTechFiles) {
                        cs.push(subId);
                    }
                }
                var ttext = "确认要将项目申报文件提交为客户定稿通过?";
                if (parseInt(data.Result) == 0) ttext = "确认要将项目申报文件提交为客户驳回?";
                mini.confirm(ttext, '系统提示', function (act) {
                    if (act == 'ok') g();
                });

                function g() {
                    var url = '/caseHighTech/setTechFiles';
                    data.SubIDS = cs.join(',');
                    $.post(url, data, function (result) {
                        if (result.success) {
                            mini.alert('选择的案件设置成功', '系统提示', function () {
                                win.hide();
                                grid.reload();
                            });
                        } else mini.alert(result.message || "设置失败，请稍候重试!");

                    })
                }
            } else mini.alert('请选择要操作的记录!');
        }

        function downloadFile() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    if (subId) cs.push(subId);
                }

            }
            $.fileDownload('/caseHighTech/download?SubIDS=' + cs.join(","), {
                httpMethod: 'POST',
                failCallback: function (html, url) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
        }

        function showTechSB(result) {
            var win = mini.get('#TechSBWindow');
            var form = new mini.Form('#TechSBForm');
            form.reset();
            mini.getbyName('SBResult').setValue(result);
            win.show();
        }

        function techSB() {
            var win = mini.get('#TechSBWindow');
            var form = new mini.Form('#TechSBForm');
            var data = form.getData();
            data.AuditMemo = mini.getbyName('SBMemo').getValue();
            data.AuditResult = mini.getbyName('SBResult').getValue();
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var acceptTechFiles = row["AcceptTechFiles"];
                    if (acceptTechFiles) {
                        cs.push(subId);
                    }
                }
                if (cs.length > 0) {
                    function g() {
                        var url = '/caseHighTech/sbTechFiles';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.alert('选择的流程申报成功', '系统提示', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "申报失败，请稍候重试!");
                        });
                    }

                    var ttext = "确认要进行流程申报?";
                    if (data.AuditResult == 0) ttext = "确认要将流程设置为暂不申报?";
                    mini.confirm(ttext, '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('选择要处理的案件记录!');
        }

        function showSettle(result) {
            var win = mini.get('#SettleWindow');
            var form = new mini.Form('#SettleForm');
            form.reset();
            mini.getbyName('SettleResult').setValue(result);
            win.show();
        }

        function auditSettle() {
            var win = mini.get('#SettleWindow');
            var form = new mini.Form('#SettleForm');
            var data = form.getData();
            data.AuditMemo = mini.getbyName('SettleMemo').getValue();
            data.AuditResult = mini.getbyName('SettleResult').getValue();
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var acceptTechFiles = row["AcceptTechFiles"];
                    if (acceptTechFiles) {
                        cs.push(subId);
                    }
                }
                if (cs.length > 0) {
                    function g() {
                        var url = '/caseHighTech/auditSettle';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.alert('选择的流程审核成功', '系统提示', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "审核失败，请稍候重试!");
                        });
                    }

                    var ttext = "确认要进行提成结算?";
                    if (data.AuditResult == 0) ttext = "确认暂时不进行提成结算?";
                    mini.confirm(ttext, '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('选择要处理的案件记录!');
        }

        function saveLinkInfo() {
            var win = mini.get('#LinkManWindow');
            var form = new mini.Form('#LinkManForm');
            form.validate();
            if (form.isValid()) {
                var data = form.getData();
                var url = '/caseHighTech/saveClientInfo';
                $.post(url, {Data: mini.encode(data)}, function (result) {
                    if (result.success) {
                        mini.alert('保存成功!', '系统提示', function () {
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || "保存失败，请稍候重试!");
                })
            } else mini.alert('数据录入不完整，不能进行保存!');
        }
        var changeTechWin=null;
        var changeTechForm=null;
        var curTechRow=null;
        function showChangeTechMan(){
            var row=grid.getSelected();
            if(row){
                curTechRow=row;
                changeTechWin=mini.get('#ChangeTechManWindow');
                changeTechForm=new mini.Form('#ChangeTechManForm');
                var techMan = parseInt(row["TechMan"] || "0");
                var subId=row["SubID"];
                var obb={SubID:subId,OldMan:techMan,NewMan:null};
                changeTechForm.reset();
                changeTechForm.setData(obb);
                changeTechWin.show();
            } else mini.alert('请选择要更换代理师的接单记录!');
        }
        function changeTechMan(){
            var res=changeTechForm.getData();
            if(!res.NewMan){
                mini.alert('请选择要更换的代理帅!');
                return ;
            }
            var iid=mini.loading('正在向服务器提交请求......');
            var OldMan=parseInt(curTechRow["TechMan"] || 0);
            res.OldMan=OldMan;
            res.CasesID=curTechRow["CasesID"];
            var url='/caseHighTech/changeTechMan';
            $.post(url,res,function(result){
                mini.hideMessageBox(iid);
                if(result.success){
                    mini.alert('当前交单业务的代理师已更换完成!','系统提示',function(){
                        grid.reload();
                        changeTechWin.hide();
                    });
                } else mini.alert(result.message || "更换代理师失败，请稍候重试!");
            })
        }
        function showWait(){
            window.parent.showWait();
        }
    </script>
</@js>