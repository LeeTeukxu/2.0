<#include "/shared/layout.ftl">
<@layout>
    <style type="text/css">
        .panel-expand {
            background-image: url(/js/miniui/themes/icons/collapse.gif);
        }

        .panel-collapse {
            background-image: url(/js/miniui/themes/icons/collapse.gif);
        }

        .progressbar {
            position: relative;
            background: #bbb;
            width: 100%;
            height: 16px;
            overflow: hidden;
        }

        .progressbar-percent {
            position: absolute;
            height: 18px;
            background: blue;
            left: 0;
            top: 0px;
            overflow: hidden;
            z-index: 1;
        }
        .progressbar-percentred {
            position: absolute;
            height: 18px;
            background: darkgrey;
            left: 0;
            top: 0px;
            overflow: hidden;
            z-index: 1;
        }
        .progressbar-label {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            font-size: 13px;
            color: White;
            z-index: 10;
            text-align: center;
            height: 16px;
            line-height: 16px;
        }
        .progressbar-labelred {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            font-size: 13px;
            color: red;
            z-index: 10;
            text-align: center;
            height: 16px;
            line-height: 16px;
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
        var workStatus=[
            {id:1,text:'正在处理'},
            {id:2,text:'完成初稿'},
            {id:3,text:'等待内审'},
            {id:4,text:'已定稿'}
        ];
    </script>
    <div class="mini-fit" style="overflow:hidden">
        <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
            <div region="center" bodyStyle="overflow:hidden">
                <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                    <table style="width:100%;" cellpadding="0" cellspacing="0">
                        <tr>
                            <td style="width:100%;">
                                <a class="mini-button" iconCls="icon-user" id="CasesBrowse_SetTechMan" visible="false"
                                   onclick="setTechMan">指定代理师</a>
                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_AcceptTech" visible="false"
                                   onclick="auditCases">技术接单</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_RejectTech" visible="false"
                                   onclick="rejectTechTask">取消接单</a>
                                <a class="mini-button" iconcls="icon-goto" id="CasesBrowse_OutSourceTech"
                                   visible="false" onclick="showCommitOutTech">专利外部协作</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_CancelOutTech"
                                   visible="false" onclick="cancelOutTech">取消外部协作</a>
                                <a class="mini-button" iconCls="icon-user" id="CasesBrowse_ChangeJS" visible="false"
                                   onclick="showChangeTechMan">更换代理师</a>

                                <a class="mini-button" iconcls="icon-upload" id="CasesBrowse_UploadTechFile"
                                   visible="false" onclick="uploadTechFile">上传专利申报文件</a>
                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_CommitTech" visible="false"
                                   onclick="commitAcceptTech">提交内审</a>
                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_TechPass" visible="false"
                                   onclick="showAuditTech(1)">内审通过</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_TechRoll" visible="false"
                                   onclick="showAuditTech(0)">内审驳回</a>

                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_ClientTechPass" visible="false"
                                   onclick="showClientSet(1)">客户定稿</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_ClientTechRoll" visible="false"
                                   onclick="showClientSet(0)">客户驳回</a>
                                <a class="mini-button" iconcls="icon-edit" id="CasesBrowse_EditNBBH" visible="false"
                                   onclick="showNBBH();">编辑内部编号</a>

                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_TechSBPass" visible="false"
                                   onclick="showTechSB(1)">流程申报</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_TechSBRoll" visible="false"
                                   onclick="showTechSB(0)">申报驳回</a>

                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_SettlementPass" visible="false"
                                   onclick="showSettle(1)">提成结算</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_SettlementRoll" visible="false"
                                   onclick="showSettle(0)">暂不结算</a>
                                <a class="mini-button" iconCls="icon-xls" id="CasesBrowse_Export"
                                   onclick="doExport()">导出Excel</a>
<#--                                <a class="mini-button" iconCls="icon-download" id="CasesBrowse_Download"-->
<#--                                   onclick="downloadFile()">下载文件</a>-->

                                <a class="mini-menubutton"  menu="#popupMenu1" style="color:green;"
                                   plain="true" id="CasesBrowse_Download">下载文件</a>
                                <ul class="mini-menu" id="popupMenu1" style="display:none">
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('Tech')">下载交底资料
                                    </li>
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('Zl')">下载著录信息</li>
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('Accept')">下载技术稿件</li>
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('All')">下载全部</li>
                                </ul>

                                <a class="mini-button" iconcls="icon-zoomin" id="CasesBrowse_BrowseCases"
                                   onclick="browseCases">查看交单</a>
<#--                                <#if State gt 51>-->
<#--                                    <a class="mini-button" iconCls="icon-tip" onclick="showWork()">工作量统计</a>-->
<#--                                </#if>-->
                                <#if State==51>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(51)">待处理统计</a>
                                </#if>
                                <#if State==52>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(52)">待内审统计</a>
                                </#if>
                                <#if State==53>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(53)">内审驳回统计</a>
                                </#if>
                                <#if State==54>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(54)">内审驳回统计</a>
                                </#if>
                                <#if State==55>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(55)">定稿驳回统计</a>
                                </#if>
                                <#if State==56>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(56)">客户定稿统计</a>
                                </#if>
                                <#if State=56>
                                    <a class="mini-button" iconCls="icon-user" onclick="showNumber()">客户申报统计</a>
                                </#if>
                                <#if State==56>
                                    <button class="xButton" style="height:1px;width:1px;background-color:transparent;
                                    color:transparent"></button>
                                </#if>
                                <#if State=61>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(61)">未申报统计</a>
                                </#if>
                                <#if State=62>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(62)">已申报统计</a>
                                </#if>

                            </td>
                            <td style="white-space:nowrap;">
                                <input class="mini-textbox CasesBrowse_Query" style="width:200px"
                                       emptyText="备注/合同号/流水号/业备数量/客户/商务人员/案件代理师"
                                       id="queryText"/>
                                <a class="mini-button mini-button-success CasesBrowse_Query" id="a3"
                                   onclick="doQuery">搜索</a>
                                <a class="mini-button mini-button-danger CasesBrowse_Reset" id="a2"
                                   onclick="reset">重置</a>
                                <a class="mini-button mini-button-info CasesBrowse_HighQuery" onclick="expand"
                                   iconCls="icon-expand">高级查询</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;display:none">
                    <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                        <tr>
                            <td style="width:80px;text-align: center;">案件名称</td>
                            <td><input class="mini-textbox" name="YName" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">客户名称</td>
                            <td><input class="mini-textbox" name="ClientName" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">完稿日期</td>
                            <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%"
                                       data-oper="GE"
                                       dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">到</td>
                            <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%"
                                       data-oper="LE"
                                       dateFormat="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">立案编号</td>
                            <td><input class="mini-textbox" name="SubNo" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">案件代理师</td>
                            <td><input name="TechMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                       allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                       valueFromSelect="true"
                                       style="width:100%"
                                       data-oper="EQ"/></td>
                            <td style="width:80px;text-align: center;">商务人员</td>
                            <td><input name="CreateMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                       allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                       style="width:100%" valueFromSelect="true"
                                       data-oper="EQ"/></td>
                            <td style="width:80px;text-align: center;">技术联系人</td>
                            <td><input class="mini-textbox" name="ClientLinkMan" style="width:100%" data-oper="LIKE"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">立案说明</td>
                            <td><input class="mini-textbox" name="Memo" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">内部编号</td>
                            <td><input class="mini-textbox" name="NBBH" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">业务数据汇总</td>
                            <td><input class="mini-textbox" name="Nums" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">专利初步名称</td>
                            <td><input class="mini-textbox" name="ShenqingName" style="width:100%" data-oper="LIKE"/>
                            </td>
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
                         multiSelect="true" frozenStartColumn="0" frozenEndColumn="6"
                         sortorder="desc" pagesize="50" onload="afterload" url="/casesTech/getData?State=${State}"
                         autoload="true" onselectionchanged="onSelectionChanged" oncelldblclick="onCellDbClick"
                         allowCellEdit="true" allowCellSelect="true" oncellbeginedit="onbeforeEdit"
                         oncellendedit="EndEdit" ondrawsummarycell="drawSummary"
                            <#if State==56>
                                oncellbeginedit="beforeEdit"
                                sortfield="ClientSetTime"
                            <#elseif State==55 || State==61>
                                sortfield="ClientSetTime"
                            <#elseif  State==51>
                                sortField="AcceptTechTime"
                            <#elseif State==52>
                                sortField="CommitTechTime"
                            <#elseif State==54 || State==53>
                                sortField="AuditTechTime"
                            <#elseif State==63 || State==62>
                                sortField="TechSBTime"
                            <#elseif State==64>
                                sortField="SettleTime"
                            <#else>
                                sortfield="AuditTime"
                            </#if> >
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div type="checkcolumn"></div>
                            <div type="comboboxcolumn" field="State" width="100" headerAlign="center" align="center"
                                 allowSort="true" visible="true">业务状态
                                <input property="editor" class="mini-combobox" data="states"/>
                            </div>
                            <div width="90" field="CreateMan" headerAlign="center" align="center"
                                 type="treeselectcolumn" allowSort="true">商务人员
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="SignTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                                 dateFormat="yyyy-MM-dd">交单日期
                            </div>
                            <div field="ProcessText" headerAlign="center" align="center" width="70">备注</div>
                            <div field="ClientName" headerAlign="center" align="center" width="240" allowSort="true">
                                客户名称
                            </div>

                            <div field="YName" headerAlign="center" align="center" vtype="required" allowSort="true">
                                业务类型
                            </div>
                            <div field="CLevel" allowSort="true" headerAlign="center" align="center"
                                 type="comboboxcolumn" width="80">案件级别
                                <input property="editor" class="mini-combobox" style="width:100%"
                                       url="/cLevel/getItems"/>
                            </div>
                            <div field="ShenqingName" width="150" headerAlign="center" vtype="required" align="center"
                                 allowSort="true">专利初步名称
                            </div>
                            <div field="OutTech" width="80" headerAlign="center" align="center" allowSort="true">
                                是否外协
                            </div>
                            <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn"
                                 allowSort="true">案件代理师
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <#if State==90 ||  State==50 || State==51>
                                <div field="Process" headerAlign="center" align="center" width="80">进度</div>
                            </#if>
                            <#if State gt 50>
                                <div field="WorkStatus" type="comboboxcolumn"  headerAlign="center" align="center"
                                     width="100">案件处理状态
                                    <input property="editor" class="mini-combobox" data="workStatus"/>
                                </div>
                            </#if>
                            <#if State==51>
                                <div width="100" headerAlign="center" field="Action" align="center" dataType="date"
                                     dateFormat="yyyy-MM-dd" allowsort="true">初稿完成日期
                                    <input property="editor" class="mini-datepicker" showTime="false"/>
                                </div>
                            </#if>
                            <#if State==52>
                                <div field="CommitTechMemo" headerAlign="center" width="150" align="center">特别说明</div>
                            </#if>
                            <#if State==53>
                                <div field="AuditTechMemo" headerAlign="center" width="200" align="center">内审驳回原因</div>
                                <div field="AuditTechFiles" headerAlign="center" align="center">内审驳回说明文件</div>
                            </#if>
                            <#if State=54>
                                <div field="AuditTechMemo" headerAlign="center" width="200" align="center">审核意见</div>
                            </#if>
                            <#if State gt 51>
                                <div field="AcceptTechFiles" headerAlign="center" align="center"
                                     width="150">专利申报文件</div>
                                <div field="CommitTechTime" headerAlign="center" align="center" width="140"
                                     dataType="date" dateFormat="yyyy-MM-dd HH:mm" allowsort="true">提交内审时间</div>
                            </#if>
                            <#if State==61>
                                <div field="TechSBMemo" headerAlign="center" width="200" align="center">未申报原因</div>
                            </#if>
                            <#if State==63>
                                <div field="SettleMemo" headerAlign="center" width="200" align="center">未结算原因</div>
                            </#if>
                            <#if State==55>
                                <div field="ClientSetMan"  headerAlign="center" align="center" type="treeselectcolumn">驳回操作人
                                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="ClientSetTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true" dateFormat="yyyy-MM-dd">客户驳回时间
                                </div>
                                <div field="ClientSetMemo" headerAlign="center" width="200" align="center">客户驳回原因</div>
                                <div field="ExpFiles" headerAlign="center" align="center">情况说明文件</div>
                            </#if>
                            <#if State==56>
                                <div field="NBBH" headerAlign="center" align="center" width="200">内部编号
                                    <input property="editor" class="mini-buttonedit" onbuttonclick="showNBBH"/>
                                </div>
                            </#if>
                            <#if State==56 || State==61>
                                <div field="ClientSetTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true" width="130" dateFormat="yyyy-MM-dd HH:mm:ss">客户定稿时间
                                </div>
                            </#if>
                            <div field="Memo" headerAlign="center" align="center" visible="false">立案说明</div>
                            <div field="SupportMan" headerAlign="center" align="center" width="110">技术对接人</div>
                            <div field="ClientRequiredDate" width="140" headerAlign="center" align="center"
                                 allowSort="true" dataType="date" dateFormat="yyyy-MM-dd">初稿交付时限
                                <input property="editor" class="mini-datepicker" showTime="false"/>
                            </div>

                            <div field="ClientInfo" headerAlign="center" align="center" width="130">技术联系人信息</div>
                            <div field="HasTech" headerAlign="center" align="center" width="100"
                                 type="comboboxcolumn">是否有交底书
                                <input property="editor" class="mini-combobox"  data="[{id:null,text:'未选择'},{id:1,text:'是'},{id:0, text:'否'}]"/>
                            </div>
                            <div field="TechFiles" headerAlign="center" align="center" width="110">技术交底资料</div>
                            <div field="ZLFiles" headerAlign="center" width="110" align="center">著录信息资料</div>
                            <div field="AcceptTechTime" headerAlign="center" align="center" dataType="date"
                                 allowSort="true" dateFormat="yyyy-MM-dd">接单日期
                            </div>
                            <div field="AuditMan" headerAlign="center" align="center" type="treeselectcolumn"
                                 allowSort="true">流程审核人员
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="AuditTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                                 dateFormat="yyyy-MM-dd">流程审核时间
                            </div>
                            <#if State gt 52>
                                <div field="AuditTechMan" headerAlign="center" align="center" type="treeselectcolumn">
                                    技术审核人员
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="AuditTechTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true"
                                     dateFormat="yyyy-MM-dd">技术审核时间
                                </div>
                            </#if>
                            <#if State gt 60>
                                <div field="TechSBMan" headerAlign="center" align="center" type="treeselectcolumn">专利申报员
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="TechSBTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true"
                                     dateFormat="yyyy-MM-dd">专利申报时间
                                </div>
                            </#if>
                            <#if State==70>
                                <div field="CancelMan" headerAlign="center" align="center" type="treeselectcolumn">
                                    取消申请人
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="CancelTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true" dateFormat="yyyy-MM-dd">业务取消时间
                                </div>
                            </#if>
                            <div field="Nums" headerAlign="center" align="center" width="200"
                                 visible="false">业务数量汇总</div>
                            <div field="SubNo" visible="false" headerAlign="center" align="center" width="120"
                                 allowSort="true">立案编号
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" title="确认接单-成为本案件代理师" width="600" height="150" style="display:none" id="AcceptWindow">
        <table style="width:100%" class="layui-table" id="AcceptForm">
            <tr>
                <td>指定内审人</td>
                <td>
                    <div id="AllocAuditMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=AcceptTech"
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
    <div class="mini-window" title="指定代理师" width="600" height="200" style="display:none" id="SetTechManWindow">
        <table style="width:100%" class="layui-table" id="SetTechManForm">
            <tr>
                <td>指定代理师</td>
                <td>
                    <div name="AllocTechMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=AcceptTech" required="true" expandonload="true"
                         textField="Name" valueField="FID" parentField="PID" allowInput="true"
                         valueFromSelect="true"></div>
                </td>
            </tr>
            <tr>
                <td>指定内审人</td>
                <td>
                    <div name="AllocAuditMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=TechPass" required="true" expandonload="true"
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
    <div class="mini-window" title="专利申报文件提交内审" width="600" height="240" style="display:none" id="CommitWindow">
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
                         url="/systems/dep/getAllLoginUserByFun?FunName=TechPass" expandonload="true"
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
    <div class="mini-window" title="专利申报文件是否合格" width="650" height="280" style="display:none" id="AuditWindow">
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
                           data="[{id:'1',text:'专利申报文件合格'},{id:'0', text:'专利申报文件不合格'}]" readonly="true"/>
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

    <div class="mini-window" title="客户是否定稿" width="600" height="280" style="display:none" id="ClientSetWindow">
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
                    <button class="mini-button mini-button-success" style="margin-left:80px"
                            onclick="clientSetFiles();">确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:180px"
                            onclick="closeWindow('ClientSetWindow')">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="是否流程申报" width="600" height="240" style="display:none" id="TechSBWindow">
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

    <div class="mini-window" title="是否结算提成" width="600" height="240" style="display:none" id="SettleWindow">
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
                           data="[{id:1,text:'结算提成'},{id:0, text:'暂不提成'}]" readonly="true"/>
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

    <div class="mini-window" title="技术联系人信息" width="500" height="240" style="display:none" id="LinkManWindow">
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
    <div class="mini-window" title="发起专利外协" width="600" height="160" style="display:none" id="OutTechWindow">
        <table style="width:100%" class="layui-table" id="OutTechForm">
            <tr>
                <td>指定外协代理师:</td>
                <td>
                    <div id="ConOutTechMan" class="mini-treeselect" style="width:100%"
                         url="/permission/roleClass/getAllUserByRole?RoleID=15"
                         textField="Name" valueField="FID" parentField="PID"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" id="CmdCommitOutTech" onclick="outSourceTech();
">确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('OutTechWindow')
">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="选择技术支持人" width="640" height="180" style="display:none" id="SupportManWindow">
        <table style="width:98%;overflow:hidden" class="layui-table" id="SupportManForm">
            <tr>
                <td width="150px">指定技术谈判人:</td>
                <td>
                    <div id="ConSupportMan" class="mini-treeselect" name="SupportMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="true" required="true" expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false"
                         allowInput="true"></div>
                    <input class="mini-hidden" name="SubID"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" id="CmdSupportMan" onclick="changeSupportMan();">
                        确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px"
                            onclick="closeWindow('SupportManWindow')">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="更换代理师" width="600" height="220" style="display:none" id="ChangeTechManWindow">
        <table style="width:99%" class="layui-table" id="ChangeTechManForm">
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
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true"
                         expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false"
                         allowInput="true"></div>
                </td>
            </tr>
            <tr>
                <br/>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" name="CmdChangeTechMan" onclick="changeTechMan()">
                        确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px"
                            onclick="closeWindow('ChangeTechManWindow')">取消关闭
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
        var cmdBrowse = mini.get('#CasesBrowse_BrowseCases');
        var cmdAccept = mini.get('#CasesBrowse_AcceptTech');
        var cmdReject = mini.get('#CasesBrowse_RejectTech');
        var cmdCommit = mini.get('#CasesBrowse_CommitTech');
        var cmdUpload = mini.get('#CasesBrowse_UploadTechFile');
        var cmdTechPass = mini.get('#CasesBrowse_TechPass');
        var cmdTechRoll = mini.get('#CasesBrowse_TechRoll');
        var cmdSBPass = mini.get('#CasesBrowse_TechSBPass');
        var cmdSBRoll = mini.get('#CasesBrowse_TechSBRoll');
        var cmdOutSourceTech = mini.get('#CasesBrowse_OutSourceTech');
        var cmdCancelTech = mini.get("CasesBrowse_CancelOutTech");
        var cmdChangeTech = mini.get('CasesBrowse_ChangeJS');

        var cmdClientPass = mini.get('#CasesBrowse_ClientTechPass');
        var cmdClientRoll = mini.get('#CasesBrowse_ClientTechRoll');

        var cmdSettlePass = mini.get('#CasesBrowse_SettlementPass');
        var cmdSettleRoll = mini.get('#CasesBrowse_SettlementRoll');
        var cmdEditNBBH = mini.get('#CasesBrowse_EditNBBH');

        var cmdSetTechMan = mini.get('#CasesBrowse_SetTechMan');
        var cmdA1 = mini.get('a1');
        var cmdA2 = mini.get('a2');
        var cmdA3 = mini.get('a3');
        var txtQuery = mini.get('queryText');


        var tree = mini.get('#LoginUserTree');
        var textCon = mini.get('#keyText');
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
            cmdClientPass.hide();
            cmdClientRoll.hide();
            cmdSettlePass.hide();
            cmdSettleRoll.hide();
            cmdEditNBBH.hide();
            cmdSetTechMan.hide();
            cmdChangeTech.hide();
        })

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setText("隐藏");
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
                    url: '/cases/edit?Mode=Browse&ID=' + id,
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
                        var field = $(el).attr('field');
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["SubID"] == code) return true;
                        });
                        if (row) {
                            if (field == "ClientInfo") {
                                var linkMan = "<div>技术联系人:" + row["ClientLinkMan"] + "</div>";
                                var linkPhone = row["ClientLinkPhone"];
                                if (linkPhone) linkMan += "<div>联系电话:" + linkPhone + "</div>";
                                var linkEmail = row["ClientLinkMail"];
                                if (linkEmail) linkMan += "<div>联系邮箱:" + linkEmail + "</div>";
                                if (linkMan) {
                                    tip.setContent("<span style='text-align:left'>" + linkMan + '</span>');
                                }
                            } else if (field == "ProcessText") {
                                var memo = row["ProcessMemo"];
                                if (memo) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                        'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                        'text-top; "> <tr><td>' + memo + '</td></tr></table>');
                                }
                            } else if (field == "ShenqingName") {
                                var sqText = row["SQText"];
                                if (sqText) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                        'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                        'text-top; "> <tr><td>' + sqText + '</td></tr></table>');
                                }
                            } else if (field == "Memo") {
                                var lxText = row["LXText"];
                                if (lxText) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                        'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                        'text-top; "> <tr><td>' + lxText + '</td></tr></table>');
                                }
                            } else if (field == "JGDateMEMO") {
                                var memo = row["JGDateMEMO"];
                                if (memo) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + memo + '</td></tr></table>');
                                }
                            }
                            else if(field=="Process"){
                                var total=parseInt(row["Total"]);
                                var free=parseInt(row["Free"]);
                               if(free>0){
                                   var memo="共计"+total+"天，还剩余:"+free+"天。";
                                   tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + memo + '</td></tr></table>');
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
            cmdClientPass.hide();
            cmdClientRoll.hide();
            cmdSBPass.hide();
            cmdSBRoll.hide();
            cmdSettlePass.hide();
            cmdSettleRoll.hide();
            cmdUpload.hide();
            cmdEditNBBH.hide();
            cmdOutSourceTech.hide();
            cmdCancelTech.hide();
            cmdSetTechMan.hide();
            cmdChangeTech.hide();
        }

        function beforeEdit(e) {
            var field = e.field;
            if (field) {
                e.cancel = true;
                if (field == "NBBH") e.cancel = false;
            }
        }

        function showCommitOutTech() {
            var win = mini.get('OutTechWindow');
            var form = new mini.Form('#OutTechForm');
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
                        var iis = mini.loading('正在提交数据，请稍候........');
                        var con = mini.get('#ConOutTechMan');
                        var allocMan = con.getValue() || 0;
                        var url = '/casesTech/addOutSourceTech';
                        $.post(url, {IDS: cs.join(','), OutTechMan: allocMan}, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('设置专利外协成功!', '系统提示', function () {
                                    grid.reload();
                                    var win = mini.get('OutTechWindow');
                                    win.hide();
                                });
                            } else mini.alert(result.message || '设置专利外协失败，请稍候重试!');
                        })
                    }

                    mini.confirm('确认要将选择案件设置为专利外协', '系统提示', function (act) {
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

            var xTechManager = record["TechManager"] || "";
            var tMs = xTechManager.split(',');
            var techManagers = [];
            for (var i = 0; i < tMs.length; i++) {
                var t = $.trim(tMs[i]);
                if (!t) continue;
                techManagers.push(parseInt(t));
            }

            cmdBrowse.show();
            cmdUpload.setText('上传专利申报文件');
            if (state == 50) {
                cmdAccept.show();
                cmdSetTechMan.show();
            } else if (state == 51 || state == 53) {
                //只有接单的人才可以取消或上传专利申报文件
                if (techMan == userId) {
                    if (state == 51) cmdReject.show();
                    cmdUpload.show();
                    var acceptTechFiles = record["AcceptTechFiles"];
                    if (acceptTechFiles) {
                        // cmdCommit.show();
                    }
                    if (state == 51) {
                        var outTech = Integer.parse(record["OutTech"]);
                        if (outTech == 0) {
                            cmdOutSourceTech.show();
                            cmdCancelTech.hide();
                        } else {
                            cmdOutSourceTech.hide();
                            cmdCancelTech.show();
                        }
                    }
                }
                if (state == 51) {
                    var cs = [];
                    for (var i = 0; i < rows.length; i++) {
                        var r = rows[i];
                        var tecMan = parseInt(r["TechMan"] || "0");
                        if (cs.indexOf(tecMan) == -1) cs.push(tecMan);
                    }
                    if (cs.length == 1) {
                        cmdChangeTech.show();
                    } else cmdChangeTech.hide();

                } else cmdChangeTech.hide();
                if (techManagers.indexOf(userId) > -1) {
                    if (state == 51) cmdReject.show();
                }
            } else if (state == 52) {
                var auditMan = parseInt(record["TechAuditMan"] || 0, 10);
                if (auditMan == userId) {
                    cmdTechPass.show();
                    cmdTechRoll.show();
                }
                var audits = record["TechManager"] || "";
                var auditMans = audits.split(",");
                if (auditMans.indexOf(userId)) {
                    cmdTechPass.show();
                    cmdTechRoll.show();
                }
            } else if (state == 54 || state == 55) {
                if (state == 54) {
                    cmdClientRoll.show();
                    cmdClientPass.show();
                } else if (state == 55) {
                    //cmdCommit.show();
                    cmdUpload.show();
                    cmdUpload.setText('重传专利申报文件');
                }
            } else if (state == 56 || state == 61) {
                cmdSBPass.show();
                if (state == 56) {
                    cmdSBRoll.show();
                    cmdClientRoll.show();
                    cmdEditNBBH.show();
                }
            } else if (state == 62 || state == 63) {
                //cmdSettlePass.show();
                //if (state == 62) cmdSettleRoll.show();
            }
            if (state >= 51 && state < 60) {
                if (techMan == userId) {
                    var outTech = Integer.parse(record["OutTech"]);
                    if (outTech == 1) {
                        cmdCancelTech.show();
                    } else cmdCancelTech.hide();
                }
            }
        }

        function showNBBH() {
            var row = grid.getSelected();
            if (row) {
                var nbBH = row["NBBH"];
                if (nbBH) {
                    mini.open({
                        url: '/addNBBH/index?NBBH=' + encodeURIComponent(nbBH) + '&SubID=' + row["SubID"],
                        title: '编辑内部编号',
                        width: '60%',
                        height: '50%',
                        showModal: true,
                        ondestroy: function (action) {
                            if (action == 'ok') grid.reload();
                        }
                    });
                }
            }
        }

        function auditCases() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#AcceptWindow');
                win.show();
            } else mini.alert('选择要处理的案件记录!');
        }

        /同意接单/


        function setTechMan() {
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
                subs.push(row["SubID"]);
            }
            if (subs.length == 0) {
                mini.alert('请选择要指定代理师的业务单据!');
                return;
            }
            var data = techManForm.getData();
            var techMan = data['AllocTechMan'];
            var auditMan = data["AllocAuditMan"]

            function g() {
                var iis = mini.loading('正在提交信息，请稍候.........');
                var url = '/casesTech/acceptTechTask';
                var arg = {SubIDS: subs.join(','), TechMan: techMan, AuditMan: auditMan};
                $.post(url, arg, function (result) {
                    mini.hideMessageBox(iis);
                    if (result.success) {
                        mini.alert('案件代理师设置成功!', '系统提示', function () {
                            var win = mini.get('#SetTechManWindow');
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || '案件代理师设置失败，请稍候重试!');
                })
            }

            mini.confirm('确认要给选择的案件指定代理师吗？', '系统提示', function (act) {
                if (act == 'ok') g();
            });
        }

        /*
        * 代理师认领业务。
        */
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
                    var iis = mini.loading('正在提交信息，请稍候.........');
                    var url = '/casesTech/acceptTechTask';
                    var arg = {SubIDS: subs.join(','), AuditMan: auditMan};
                    $.post(url, arg, function (result) {
                        mini.hideMessageBox(iis);
                        if (result.success) {
                            mini.alert('选择的案件认领成功!', '系统提示', function () {
                                var win = mini.get('#AcceptWindow');
                                win.hide();
                                grid.reload();
                            });
                        } else mini.alert(result.message || '选择的案件认领失败，请稍候重试!');
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
                var iis = mini.loading('正在提交信息，请稍候.........');
                var url = '/casesTech/rejectTechTask';
                var arg = {SubIDS: subs.join(',')};
                $.post(url, arg, function (result) {
                    mini.hideMessageBox(iis);
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
            if(field=="TechFiles"){
                if (state < 51) {
                    if (now) {
                        uText = "管理";
                        mode = "Edit";
                    } else {
                        uText = "上传";
                        mode = "Add";
                    }
                    isOk = true;
                } else {
                    mode = "Browse";
                    isOk = true;
                }
            }
            else if (field == "ZLFiles") {
                if (field == "ZLFiles") type = "Zl";
                if (state < 60) {
                    if (now) {
                        uText = "管理";
                        mode = "Edit";
                    } else {
                        uText = "上传";
                        mode = "Add";
                    }
                    isOk = true;
                } else {
                    mode = "Browse";
                    isOk = true;
                }
            } else if (field == "AcceptTechFiles") {
                type = "Accept";
                var acceptTechFileName = record["AcceptTechFileName"];
                if (acceptTechFileName) uText = acceptTechFileName;
                isOk = true;
            } else if (field == "ExpFiles") {
                var ff=record[field];
                type = "Exp";
                if(ff)isOk = true; else isOk=false;
            } else if (field == "AuditTechFiles") {
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
                        e.cellHtml = '<a href="javascript:void(0)"  field="ClientInfo"  onclick="editTechLinkMan(' + "'" + record["_id"] + "'" + ')" ' +
                            'style="color:blue;text-decoration:underline" data-placement="bottomleft"  ' +
                            'hCode="' + record["SubID"] + '" ' + 'class="showCellTooltip">联系方式</a>';
                    } else {
                        e.cellHtml = '<a href="javascript:void(0)"  field="ClientInfo"  onclick="editTechLinkMan(' + "'" + record["_id"] + "'" + ')" ' +
                            'style="color:blue;text-decoration:underline">添加</a>';
                    }
                }
                if (field == "OutTech") {
                    var OX = Integer.parse(now);
                    if (OX == 1) e.cellHtml = "是"; else e.cellHtml = "否";
                }
            }
            if (field == "ProcessText") {
                var dd = record[field] || "";
                if (dd == "[]") dd = null;
                var x = '<a href="javascript:void(0)"  field="ProcessText"  data-placement="bottomleft"  style="color:blue;' +
                    'text-decoration:underline" ' +
                    'hcode="' + record["SubID"] + '"' +
                    ' class="showCellTooltip" onclick="showMemo(' + "'" + record._id + "'" + ')">' + (dd ? "查看" : "添加") + '</a>';
                e.cellHtml = x;
            } else if (field == "ShenqingName") {
                if (state < 60) {
                    var ttt = record["ShenqingName"] || "&nbsp;&nbsp;";
                    var x = '<a href="javascript:void(0)"  field="ShenqingName"  data-placement="bottomleft"  style="color:blue;' +
                        'text-decoration:underline" ' +
                        'hcode="' + record["SubID"] + '"' +
                        ' class="showCellTooltip" onclick="changeShenqingName(' + "'" + record._id + "'" + ')">' + ttt + '</a>';
                    e.cellHtml = x;
                }
            } else if (field == "Memo") {
                if (state < 60) {
                    var ttt = record["Memo"] || "&nbsp;&nbsp;";
                    var x = '<a href="javascript:void(0)"  field="Memo"  data-placement="bottomleft"  style="color:blue;' +
                        'text-decoration:underline" ' +
                        'hcode="' + record["SubID"] + '"' +
                        ' class="showCellTooltip" onclick="changeLXText(' + "'" + record._id + "'" + ')">' + ttt + '</a>';
                    e.cellHtml = x;
                }
            } else if (field == "SupportMan") {
                var value = record["SupportMan"] || "添加";
                if (state >= 50 && state < 56) {
                    e.cellHtml = '<a href="javascript:void(0)"  field="ClientInfo"  onclick="editSupportMan(' + "'" + record["_id"] + "'" + ')" ' +
                        'style="color:blue;text-decoration:underline">' + value + '</a>';
                }

            } else if (field == "Process") {
                var total=parseInt(record["Total"]);
                var free=parseInt(record["Free"]);
                var value=parseInt(((total-free)/total)*100);
                if(free>0){
                    var x = '<a href="javascript:void(0)"  field="Process"  data-placement="bottomleft"' +
                        'text-decoration:underline" style="color:white"' +
                        'hcode="' + record["SubID"] + '"' +
                        ' class="showCellTooltip">'+value+'%</a>';
                    e.cellHtml =
                        '<div class="progressbar">'
                        + '<div class="progressbar-percent"  style="width:' + value + '%;"></div>'
                        + '<div class="progressbar-label">'+x+'</div>'
                        +'</div>';
                } else {
                    value=100;
                    var dd=record["CommitTechTime"];
                    if(dd){
                        var commitTechTime=mini.parseDate(record["CommitTechTime"]).getTime();
                        var clientRequired=mini.parseDate(record["ClientRequiredDate"]).getTime();
                        if(commitTechTime<clientRequired){
                            value=100;
                            e.cellHtml = '<div class="progressbar">'
                                + '<div class="progressbar-percent" style="width:' + value + '%;"></div>'
                                + '<div class="progressbar-label">已完成</div>'
                                +'</div>';
                        } else {
                            e.cellHtml = '<div class="progressbar">'
                                + '<div class="progressbar-percentred" style="width:' + value + '%;"></div>'
                                + '<div class="progressbar-labelred">超时完成</div>'
                                +'</div>';
                        }
                    } else {
                        e.cellHtml = '<div class="progressbar">'
                            + '<div class="progressbar-percentred" style="width:' + value + '%;"></div>'
                            + '<div class="progressbar-labelred">已过期</div>'
                            +'</div>';
                    }
                }
            } else if (field == "Action") {
                if (state == 51) {
                    var ttt = mini.formatDate(record["JGDate"], 'yyyy-MM-dd') || "&nbsp;&nbsp;";
                    var x = '<a href="javascript:void(0)"  field="JGDateMEMO"  data-placement="bottomleft"  style="color:blue;' +
                        'text-decoration:underline" ' +
                        'hcode="' + record["SubID"] + '"' +
                        ' >' + ttt + '</a>';
                    e.cellHtml = x;
                }
            }
            else if(field=="ClientRequiredDate"){
                e.cellHtml="<span style='color:blue'>"+e.value+'</span>';
            }
            if (state == 70) {
                e.rowStyle = "text-decoration:line-through;text-decoration-color: black;color:red";
            }
        }

        function editSupportMan(id) {
            var record = grid.getRowByUID(id);
            if (record) {
                var supportMan = record["SupportMan"] || "";
                var subId = record["SubID"];
                if (subId) {
                    record["SupportMan"] = supportMan;
                    var win = mini.get('#SupportManWindow');
                    var form = new mini.Form('#SupportManForm');
                    form.reset();
                    var conS = mini.get('#ConSupportMan');
                    form.setData(record);
                    if (supportMan) conS.setText(supportMan);
                    win.show();
                }
            }
        }

        function changeSupportMan() {
            var win = mini.get('#SupportManWindow');
            var form = new mini.Form('#SupportManForm');
            form.validate();
            if (form.isValid()) {
                var data = form.getData();
                var conS = mini.get('#ConSupportMan');
                data.SupportMan = conS.getText();
                var url = '/casesTech/changeSupportMan';
                $.post(url, data, function (result) {
                    if (result.success) {
                        mini.alert('技术谈判人员修改成功!', '系统提示', function () {
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || "修改失败，请稍候重试!");
                })
            } else mini.alert('请选择技术谈判人员!');
        }

        function cancelOutTech() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var outTech = Integer.parse(row["OutTech"]);
                    var techMan = parseInt(row["TechMan"] || 0);
                    if (outTech == 1) {
                        if (techMan == userId) {
                            var subId = row["SubID"];
                            cs.push(subId);
                        }
                    }
                }
                if (cs.length > 0) {
                    var url = '/casesTech/cancelOutTech';

                    function g() {
                        var iis = mini.loading('正在提交信息，请稍候.........');
                        $.post(url, {IDS: cs.join(',')}, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('专利业务取消外协成功!', '系统提示', function () {
                                    grid.reload();
                                });
                            } else mini.alert(result.message || '操作失败，请稍候重试!', '系统提示');
                        });
                    }

                    mini.confirm('确认要取消选择的案件为外部协作？', '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                } else mini.alert('没有可以操作的记录!');
            } else mini.alert('请选择要进行操作的记录!');
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

        function changeShenqingName(id) {
            var record = grid.getRowByUID(id);
            if (record) {
                var shenqingName = record["ShenqingName"] || "";
                var uid = mini.prompt("请输入初步专利名称：", "更改初步专利名称", function (action, value) {
                    if (action == 'ok') {
                        value = $.trim(value);
                        if (!value) {
                            mini.alert('专利名称不能为空!');
                            return;
                        }
                        if (value == shenqingName) {
                            mini.alert('专利名称未发生更改，无需保存!');
                            return;
                        }

                        function g() {
                            var url = '/casesTech/changeShenqingName';
                            var arg = {SubID: record["SubID"], NewShenqingName: value};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('专利名称更新成功!', '系统提示', function () {
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || "更新失败，请稍候重试!");
                            })
                        }

                        g();
                    }
                }, false)
                var win = mini.getbyUID(uid);
                win.setWidth(400);
                $(win.el).find("textarea,input").width(340).val(shenqingName);
            }
        }

        function changeLXText(id) {
            var record = grid.getRowByUID(id);
            if (record) {
                var memoText = record["Memo"] || "";
                var uid = mini.prompt("请输入立项说明：", "更改立项目说明描述", function (action, value) {
                    if (action == 'ok') {
                        value = $.trim(value);
                        if (!value) {
                            mini.alert('立项目说明不能为空!');
                            return;
                        }
                        if (value == memoText) {
                            mini.alert('立项目说明未发生更改，无需保存!');
                            return;
                        }

                        function g() {
                            var url = '/casesTech/changeLXText';
                            var arg = {SubID: record["SubID"], NewLXText: value};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('立项目说明更新成功!', '系统提示', function () {
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
                var fields = ['Memo', "SubNo", "ShenqingName", "ClientName", "YName", "Nums", "SignManName",
                    "AuditManName","TechManName"];
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

        function showMemo(ID) {
            var title = '添加工作进度信息';
            var record = grid.getRowByUID(ID);
            var subId = record["SubID"];
            var rows = grid.getSelecteds();
            if (rows.length > 1) {
                var c = [];
                for (var i = 0; i < rows.length; i++) {
                    c.push(rows[i]["SubID"]);
                }
                subId = c.join(',');
                title = '批量添加工作进度信息';
            }

            mini.open({
                url: '/addSingleMemo/index?ID=' + subId,
                width: 1000,
                height: 600,
                showModal: true,
                title: title,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var saveUrl = '/casesOutSource/saveMemo?SubID=' + subId;
                    iframe.contentWindow.setConfig(saveUrl);
                    iframe.contentWindow.setSaveImageUrl('/addSingleMemo/saveImage?SubID=' + subId);
                },
                onDestroy: function () {
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
                var url = '/cases/getSubFiles';
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
            if (type == "Accept") title = "专利申报文件";
            if (type == "Exp") title = "情况说明文件";
            if (type == 'Aud') title = '内审驳回说明文件';
            var showHis = 0;
            if (type == "Accept" || type == "Exp" || type == "Aud") showHis = 1;
            var attId = "";
            if (ids.length > 0) attId = ids.join(",");
            var clientName="";
            if(type=="Tech"){
                clientName=row["ClientName"];
            }
            mini.open({
                url: '/attachment/addFile?IDS=' + attId + '&Mode=' + mode + '&ShowHis=' +
                    showHis + '&FileType=' + type + '&SubID=' + subId+'&ClientName='+clientName,
                width: 1100,
                height: type=="Tech"?640:500,
                title: title,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                        var attId = data.AttID;
                        var url = '/cases/saveSubFiles';
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
                    if (type != 'Exp' && type != 'Aud') {
                        var state = parseInt(record.State || 1);
                        if (state != 53) grid.reload();
                    }
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
                        var iis = mini.loading('正在提交信息，请稍候.........');
                        var url = '/casesTech/commitTechFiles';
                        var memo = mini.get('#CommitMemo').getValue() || "";
                        var arg = {SubIDS: cs.join(','), Memo: memo};
                        $.post(url, arg, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('选择的案件提交成功', '系统提示', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "提交审核失败，请稍候重试!");
                        });
                    }

                    mini.confirm('确认要将专利申报文件提交内审', '系统提示', function (act) {
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
                        var iis = mini.loading('正在提交信息，请稍候.........');
                        var url = '/casesTech/auditTechFiles';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('选择的案件审核成功', '系统提示', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "审核失败，请稍候重试!");
                        });
                    }

                    var ttext = "确认要将专利申报文件审核为内审通过?";
                    if (data.AuditResult == 0) ttext = "确认要将专利申报文件审核为内审驳回?";
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
                var ttext = "确认要将专利申报文件提交为客户定稿通过?";
                if (parseInt(data.Result) == 0) ttext = "确认要将专利申报文件提交为客户驳回?";
                mini.confirm(ttext, '系统提示', function (act) {
                    if (act == 'ok') g();
                });

                function g() {
                    var iis = mini.loading('正在提交信息，请稍候.........');
                    var url = '/casesTech/setTechFiles';
                    data.SubIDS = cs.join(',');
                    $.post(url, data, function (result) {
                        mini.hideMessageBox(iis);
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

        function downloadFile(type) {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    if (subId) cs.push(subId);
                }

            }
            $.fileDownload('/casesTech/download?Type='+type+'&SubIDS=' + cs.join(","), {
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
                        var iis = mini.loading('正在提交信息，请稍候.........');
                        var url = '/casesTech/sbTechFiles';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.hideMessageBox(iis);
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
                        var iis = mini.loading('正在提交信息，请稍候.........');
                        var url = '/casesTech/auditSettle';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            mini.hideMessageBox(iis);
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
                var url = '/casesTech/saveClientInfo';
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

        var changeTechWin = null;
        var changeTechForm = null;
        var curTechRow = null;

        function showChangeTechMan() {
            var rows = grid.getSelecteds() || [];
            if (rows.length > 0) {
                var row = rows[0];
                curTechRow = row;
                changeTechWin = mini.get('#ChangeTechManWindow');
                changeTechForm = new mini.Form('#ChangeTechManForm');
                var techMan = parseInt(row["TechMan"] || "0");
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var r = rows[i];
                    var ss = r["SubID"];
                    cs.push(ss);
                }
                var subId = cs.join(',');
                var obb = {SubID: subId, OldMan: techMan, NewMan: null};
                changeTechForm.reset();
                changeTechForm.setData(obb);
                changeTechWin.show();
            } else mini.alert('请选择要更换代理师的接单记录!');
        }

        function changeTechMan() {
            var res = changeTechForm.getData();
            if (!res.NewMan) {
                mini.alert('请选择要更换的代理师!');
                return;
            }
            var iid = mini.loading('正在向服务器提交请求......');
            var OldMan = parseInt(curTechRow["TechMan"] || 0);
            res.OldMan = OldMan;
            res.CasesID = curTechRow["CasesID"];
            var url = '/casesTech/changeTechMan';
            $.post(url, res, function (result) {
                mini.hideMessageBox(iid);
                if (result.success) {
                    mini.alert('当前交单业务的代理师已更换完成!', '系统提示', function () {
                        grid.reload();
                        changeTechWin.hide();
                    });
                } else mini.alert(result.message || "更换代理师失败，请稍候重试!");
            })
        }

        function showWait(state) {
            window.parent.showWait(state);
        }
        function showWork(){
            window.parent.showWork();
        }

        function showNumber() {
            var data = grid.getData();
            if (data.length == 0) return;
            var cs = [];
            for (var i = 0; i < data.length; i++) {
                var row = data[i];
                var name = row["ClientName"];
                cs.push("'" + name + "'");
            }
            window.parent.showClientNumber(cs.join(','));
        }

        function onbeforeEdit(e) {
            var field = e.field;
            e.cancel = true;
            var record=e.record;
            var createMan = parseInt(record["CreateMan"] || 0);
            var cs=record["CreateManager"] ||"";
            var createManager=cs.split(",") ||[];
            if (field == "ClientRequiredDate") {
                ///初审交稿时限，只能由发起人。。。。。才能修改。
                if(
                    userId==createMan ||
                    roleName=="系统管理员" ||
                    roleName.indexOf("流程")>-1 ||
                    createManager.indexOf(userId)>-1
                ){
                    e.cancel=false;
                } else e.cancel=true;
            }
            if (field == "Action" || field=="WorkStatus") {
                ///初稿完成日期，只能由接单技术人员
                var techMan = parseInt(record["TechMan"] || "0");
                var ts=record["TechManager"] ||"";
                var techManager=ts.split(",") || [];
                if(
                    userId==techMan ||
                    roleName=="系统管理员" ||
                    roleName.indexOf("流程")>-1 ||
                    techManager.indexOf(userId)>-1
                ){
                    e.cancel = false;
                } else  e.cancel=true;
            }
        }

        function EndEdit(e) {
            var field = e.field;
            var val = e.value;
            var row = e.record;
            var ID = row["SubID"];
            if(grid.isChanged()==false){
                return ;
            }
            if (field == "Action") {
                if(val){
                    val = mini.formatDate(e.value, 'yyyy-MM-dd HH:mm:ss');
                } else val=null;
                var url = '/casesTech/changeJGDate';
                $.post(url, {Field: field, ID: ID, Value: val,}, function (r) {
                    if (r.success) {
                        doReload(grid);
                    } else {
                        var msg = r.message || "更新属性值失败，请稍候重试。";
                        mini.alert(msg);
                    }
                });
            } else if (field == "ClientRequiredDate") {
                if (val == undefined) {
                    return;
                }
                val = mini.formatDate(e.value, 'yyyy-MM-dd HH:mm:ss');
                var url = '/casesTech/changeRequiredDate';
                $.post(url, {ID: ID, Value: val,}, function (r) {
                    if (r.success) {
                        doReload(grid);
                    } else {
                        var msg = r.message || "更新属性值失败，请稍候重试。";
                        mini.alert(msg);
                    }
                });
            }
            else if(field=="WorkStatus"){
                if (val == undefined) {
                    return;
                }
                var url = '/casesTech/updateWorkStatus';
                $.post(url, {SubID: ID, WorkStatus: val,}, function (r) {
                    if (r.success) {
                        doReload(grid);
                    } else {
                        var msg = r.message || "更新失败，请稍候重试。";
                        mini.alert(msg);
                    }
                });
            }
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

        function drawSummary(e) {

            e.cellHtml = "";
        }
    </script>
</@js>