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
            {id: 1, text: '????????????-?????????'},
            {id: 2, text: '????????????-?????????'},
            {id: 3, text: '????????????-?????????'},
            {id: 4, text: '????????????-?????????'},
            {id: 7, text: '????????????-?????????'},
            {id: 8, text: '????????????-?????????'},
            {id: 9, text: '????????????-??????'},
            {id: 50, text: '??????????????????'},
            {id: 51, text: '??????????????????'},
            {id: 52, text: '??????????????????-?????????'},
            {id: 53, text: '??????????????????-????????????'},
            {id: 54, text: '??????????????????-????????????'},
            {id: 56, text: '??????????????????-????????????'},
            {id: 55, text: '??????????????????-????????????'},
            {id: 61, text: '????????????-?????????'},
            {id: 62, text: '????????????-?????????'},
            {id: 63, text: '????????????-?????????'},
            {id: 64, text: '????????????-?????????'},
            {id: 70, text: '????????????'}
        ];
        var workStatus=[
            {id:1,text:'????????????'},
            {id:2,text:'????????????'},
            {id:3,text:'????????????'},
            {id:4,text:'?????????'}
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
                                   onclick="setTechMan">???????????????</a>
                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_AcceptTech" visible="false"
                                   onclick="auditCases">????????????</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_RejectTech" visible="false"
                                   onclick="rejectTechTask">????????????</a>
                                <a class="mini-button" iconcls="icon-goto" id="CasesBrowse_OutSourceTech"
                                   visible="false" onclick="showCommitOutTech">??????????????????</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_CancelOutTech"
                                   visible="false" onclick="cancelOutTech">??????????????????</a>
                                <a class="mini-button" iconCls="icon-user" id="CasesBrowse_ChangeJS" visible="false"
                                   onclick="showChangeTechMan">???????????????</a>

                                <a class="mini-button" iconcls="icon-upload" id="CasesBrowse_UploadTechFile"
                                   visible="false" onclick="uploadTechFile">????????????????????????</a>
                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_CommitTech" visible="false"
                                   onclick="commitAcceptTech">????????????</a>
                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_TechPass" visible="false"
                                   onclick="showAuditTech(1)">????????????</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_TechRoll" visible="false"
                                   onclick="showAuditTech(0)">????????????</a>

                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_ClientTechPass" visible="false"
                                   onclick="showClientSet(1)">????????????</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_ClientTechRoll" visible="false"
                                   onclick="showClientSet(0)">????????????</a>
                                <a class="mini-button" iconcls="icon-edit" id="CasesBrowse_EditNBBH" visible="false"
                                   onclick="showNBBH();">??????????????????</a>

                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_TechSBPass" visible="false"
                                   onclick="showTechSB(1)">????????????</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_TechSBRoll" visible="false"
                                   onclick="showTechSB(0)">????????????</a>

                                <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_SettlementPass" visible="false"
                                   onclick="showSettle(1)">????????????</a>
                                <a class="mini-button" iconcls="icon-no" id="CasesBrowse_SettlementRoll" visible="false"
                                   onclick="showSettle(0)">????????????</a>
                                <a class="mini-button" iconCls="icon-xls" id="CasesBrowse_Export"
                                   onclick="doExport()">??????Excel</a>
<#--                                <a class="mini-button" iconCls="icon-download" id="CasesBrowse_Download"-->
<#--                                   onclick="downloadFile()">????????????</a>-->

                                <a class="mini-menubutton"  menu="#popupMenu1" style="color:green;"
                                   plain="true" id="CasesBrowse_Download">????????????</a>
                                <ul class="mini-menu" id="popupMenu1" style="display:none">
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('Tech')">??????????????????
                                    </li>
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('Zl')">??????????????????</li>
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('Accept')">??????????????????</li>
                                    <li class="mini-menu" iconCls="icon-download" plain="true"
                                        onclick="downloadFile('All')">????????????</li>
                                </ul>

                                <a class="mini-button" iconcls="icon-zoomin" id="CasesBrowse_BrowseCases"
                                   onclick="browseCases">????????????</a>
<#--                                <#if State gt 51>-->
<#--                                    <a class="mini-button" iconCls="icon-tip" onclick="showWork()">???????????????</a>-->
<#--                                </#if>-->
                                <#if State==51>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(51)">???????????????</a>
                                </#if>
                                <#if State==52>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(52)">???????????????</a>
                                </#if>
                                <#if State==53>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(53)">??????????????????</a>
                                </#if>
                                <#if State==54>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(54)">??????????????????</a>
                                </#if>
                                <#if State==55>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(55)">??????????????????</a>
                                </#if>
                                <#if State==56>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(56)">??????????????????</a>
                                </#if>
                                <#if State=56>
                                    <a class="mini-button" iconCls="icon-user" onclick="showNumber()">??????????????????</a>
                                </#if>
                                <#if State==56>
                                    <button class="xButton" style="height:1px;width:1px;background-color:transparent;
                                    color:transparent"></button>
                                </#if>
                                <#if State=61>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(61)">???????????????</a>
                                </#if>
                                <#if State=62>
                                    <a class="mini-button" iconCls="icon-tip" onclick="showWait(62)">???????????????</a>
                                </#if>

                            </td>
                            <td style="white-space:nowrap;">
                                <input class="mini-textbox CasesBrowse_Query" style="width:200px"
                                       emptyText="??????/?????????/?????????/????????????/??????/????????????/???????????????"
                                       id="queryText"/>
                                <a class="mini-button mini-button-success CasesBrowse_Query" id="a3"
                                   onclick="doQuery">??????</a>
                                <a class="mini-button mini-button-danger CasesBrowse_Reset" id="a2"
                                   onclick="reset">??????</a>
                                <a class="mini-button mini-button-info CasesBrowse_HighQuery" onclick="expand"
                                   iconCls="icon-expand">????????????</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;display:none">
                    <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                        <tr>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="YName" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="ClientName" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%"
                                       data-oper="GE"
                                       dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">???</td>
                            <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%"
                                       data-oper="LE"
                                       dateFormat="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="SubNo" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">???????????????</td>
                            <td><input name="TechMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                       allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                       valueFromSelect="true"
                                       style="width:100%"
                                       data-oper="EQ"/></td>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input name="CreateMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                       allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                       style="width:100%" valueFromSelect="true"
                                       data-oper="EQ"/></td>
                            <td style="width:80px;text-align: center;">???????????????</td>
                            <td><input class="mini-textbox" name="ClientLinkMan" style="width:100%" data-oper="LIKE"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="Memo" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="NBBH" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">??????????????????</td>
                            <td><input class="mini-textbox" name="Nums" style="width:100%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">??????????????????</td>
                            <td><input class="mini-textbox" name="ShenqingName" style="width:100%" data-oper="LIKE"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" style="width:120px"
                                   href="javascript:doHightSearch();">??????</a>
                                <a class="mini-button mini-button-danger" style="margin-left:30px;width:120px"
                                   onclick="expand">??????</a>
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
                                 allowSort="true" visible="true">????????????
                                <input property="editor" class="mini-combobox" data="states"/>
                            </div>
                            <div width="90" field="CreateMan" headerAlign="center" align="center"
                                 type="treeselectcolumn" allowSort="true">????????????
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="SignTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                                 dateFormat="yyyy-MM-dd">????????????
                            </div>
                            <div field="ProcessText" headerAlign="center" align="center" width="70">??????</div>
                            <div field="ClientName" headerAlign="center" align="center" width="240" allowSort="true">
                                ????????????
                            </div>

                            <div field="YName" headerAlign="center" align="center" vtype="required" allowSort="true">
                                ????????????
                            </div>
                            <div field="CLevel" allowSort="true" headerAlign="center" align="center"
                                 type="comboboxcolumn" width="80">????????????
                                <input property="editor" class="mini-combobox" style="width:100%"
                                       url="/cLevel/getItems"/>
                            </div>
                            <div field="ShenqingName" width="150" headerAlign="center" vtype="required" align="center"
                                 allowSort="true">??????????????????
                            </div>
                            <div field="OutTech" width="80" headerAlign="center" align="center" allowSort="true">
                                ????????????
                            </div>
                            <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn"
                                 allowSort="true">???????????????
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <#if State==90 ||  State==50 || State==51>
                                <div field="Process" headerAlign="center" align="center" width="80">??????</div>
                            </#if>
                            <#if State gt 50>
                                <div field="WorkStatus" type="comboboxcolumn"  headerAlign="center" align="center"
                                     width="100">??????????????????
                                    <input property="editor" class="mini-combobox" data="workStatus"/>
                                </div>
                            </#if>
                            <#if State==51>
                                <div width="100" headerAlign="center" field="Action" align="center" dataType="date"
                                     dateFormat="yyyy-MM-dd" allowsort="true">??????????????????
                                    <input property="editor" class="mini-datepicker" showTime="false"/>
                                </div>
                            </#if>
                            <#if State==52>
                                <div field="CommitTechMemo" headerAlign="center" width="150" align="center">????????????</div>
                            </#if>
                            <#if State==53>
                                <div field="AuditTechMemo" headerAlign="center" width="200" align="center">??????????????????</div>
                                <div field="AuditTechFiles" headerAlign="center" align="center">????????????????????????</div>
                            </#if>
                            <#if State=54>
                                <div field="AuditTechMemo" headerAlign="center" width="200" align="center">????????????</div>
                            </#if>
                            <#if State gt 51>
                                <div field="AcceptTechFiles" headerAlign="center" align="center"
                                     width="150">??????????????????</div>
                                <div field="CommitTechTime" headerAlign="center" align="center" width="140"
                                     dataType="date" dateFormat="yyyy-MM-dd HH:mm" allowsort="true">??????????????????</div>
                            </#if>
                            <#if State==61>
                                <div field="TechSBMemo" headerAlign="center" width="200" align="center">???????????????</div>
                            </#if>
                            <#if State==63>
                                <div field="SettleMemo" headerAlign="center" width="200" align="center">???????????????</div>
                            </#if>
                            <#if State==55>
                                <div field="ClientSetMan"  headerAlign="center" align="center" type="treeselectcolumn">???????????????
                                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="ClientSetTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true" dateFormat="yyyy-MM-dd">??????????????????
                                </div>
                                <div field="ClientSetMemo" headerAlign="center" width="200" align="center">??????????????????</div>
                                <div field="ExpFiles" headerAlign="center" align="center">??????????????????</div>
                            </#if>
                            <#if State==56>
                                <div field="NBBH" headerAlign="center" align="center" width="200">????????????
                                    <input property="editor" class="mini-buttonedit" onbuttonclick="showNBBH"/>
                                </div>
                            </#if>
                            <#if State==56 || State==61>
                                <div field="ClientSetTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true" width="130" dateFormat="yyyy-MM-dd HH:mm:ss">??????????????????
                                </div>
                            </#if>
                            <div field="Memo" headerAlign="center" align="center" visible="false">????????????</div>
                            <div field="SupportMan" headerAlign="center" align="center" width="110">???????????????</div>
                            <div field="ClientRequiredDate" width="140" headerAlign="center" align="center"
                                 allowSort="true" dataType="date" dateFormat="yyyy-MM-dd">??????????????????
                                <input property="editor" class="mini-datepicker" showTime="false"/>
                            </div>

                            <div field="ClientInfo" headerAlign="center" align="center" width="130">?????????????????????</div>
                            <div field="HasTech" headerAlign="center" align="center" width="100"
                                 type="comboboxcolumn">??????????????????
                                <input property="editor" class="mini-combobox"  data="[{id:null,text:'?????????'},{id:1,text:'???'},{id:0, text:'???'}]"/>
                            </div>
                            <div field="TechFiles" headerAlign="center" align="center" width="110">??????????????????</div>
                            <div field="ZLFiles" headerAlign="center" width="110" align="center">??????????????????</div>
                            <div field="AcceptTechTime" headerAlign="center" align="center" dataType="date"
                                 allowSort="true" dateFormat="yyyy-MM-dd">????????????
                            </div>
                            <div field="AuditMan" headerAlign="center" align="center" type="treeselectcolumn"
                                 allowSort="true">??????????????????
                                <input property="editor" class="mini-treeselect"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="AuditTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                                 dateFormat="yyyy-MM-dd">??????????????????
                            </div>
                            <#if State gt 52>
                                <div field="AuditTechMan" headerAlign="center" align="center" type="treeselectcolumn">
                                    ??????????????????
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="AuditTechTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true"
                                     dateFormat="yyyy-MM-dd">??????????????????
                                </div>
                            </#if>
                            <#if State gt 60>
                                <div field="TechSBMan" headerAlign="center" align="center" type="treeselectcolumn">???????????????
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="TechSBTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true"
                                     dateFormat="yyyy-MM-dd">??????????????????
                                </div>
                            </#if>
                            <#if State==70>
                                <div field="CancelMan" headerAlign="center" align="center" type="treeselectcolumn">
                                    ???????????????
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                                           parentField="PID"/>
                                </div>
                                <div field="CancelTime" headerAlign="center" align="center" dataType="date"
                                     allowSort="true" dateFormat="yyyy-MM-dd">??????????????????
                                </div>
                            </#if>
                            <div field="Nums" headerAlign="center" align="center" width="200"
                                 visible="false">??????????????????</div>
                            <div field="SubNo" visible="false" headerAlign="center" align="center" width="120"
                                 allowSort="true">????????????
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" title="????????????-????????????????????????" width="600" height="150" style="display:none" id="AcceptWindow">
        <table style="width:100%" class="layui-table" id="AcceptForm">
            <tr>
                <td>???????????????</td>
                <td>
                    <div id="AllocAuditMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=AcceptTech"
                         textField="Name" valueField="FID" parentField="PID" value="${Manager}"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="acceptTech();">????????????</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('AcceptWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="???????????????" width="600" height="200" style="display:none" id="SetTechManWindow">
        <table style="width:100%" class="layui-table" id="SetTechManForm">
            <tr>
                <td>???????????????</td>
                <td>
                    <div name="AllocTechMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=AcceptTech" required="true" expandonload="true"
                         textField="Name" valueField="FID" parentField="PID" allowInput="true"
                         valueFromSelect="true"></div>
                </td>
            </tr>
            <tr>
                <td>???????????????</td>
                <td>
                    <div name="AllocAuditMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=TechPass" required="true" expandonload="true"
                         textField="Name" valueField="FID" parentField="PID" allowInput="true"
                         valueFromSelect="true"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="acceptTechMan();">????????????</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('SetTechManWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="??????????????????????????????" width="600" height="240" style="display:none" id="CommitWindow">
        <table style="width:100%" class="layui-table" id="CommitForm">
            <tr>
                <td style="width:100px">????????????</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" id="CommitMemo"></textarea>
                </td>
            </tr>
            <tr>
                <td>???????????????</td>
                <td>
                    <div id="ConAuditMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=TechPass" expandonload="true"
                         textField="Name" valueField="FID" parentField="PID" value="${Manager}" enabled="false"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" id="CmdCommitFiles" onclick="commitFiles();
">????????????
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('CommitWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="??????????????????????????????" width="650" height="280" style="display:none" id="AuditWindow">
        <table style="width:100%" class="layui-table" id="AuditForm">
            <tr>
                <td style="width:150px">????????????</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" name="AuditMemo"></textarea>
                </td>
            </tr>
            <tr id="TrAudFile">
                <td>????????????????????????</td>
                <td style="text-align:center">
                    <buttn class="mini-button mini-button-info" id="UploadAudFile" onclick="uploadAuditFile()
    " style="width:100%">???????????????
                    </buttn>
                </td>
            </tr>
            <tr>
                <td>????????????</td>
                <td>
                    <input style="width:100%" name="AuditResult" class="mini-combobox"
                           data="[{id:'1',text:'????????????????????????'},{id:'0', text:'???????????????????????????'}]" readonly="true"/>
                </td>
            </tr>

            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="auditTechFiles();">????????????</button>
                    <button class="mini-button mini-button-danger" style="margin-left:180px" onclick="closeWindow
                ('AuditWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>

    <div class="mini-window" title="??????????????????" width="600" height="280" style="display:none" id="ClientSetWindow">
        <table style="width:100%" class="layui-table" id="ClientSetForm">
            <tr>
                <td style="width:100px">????????????</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" name="ClientSetMemo"></textarea>
                </td>
            </tr>
            <tr id="TrExpFile">
                <td>??????????????????</td>
                <td style="text-align:center">
                    <buttn class="mini-button mini-button-info" id="UploadExpFile" onclick="uploadExplainFile()
" style="width:100%">???????????????
                    </buttn>
                </td>
            </tr>
            <tr>
                <td>????????????</td>
                <td>
                    <input style="width:100%" name="ClientSetResult" class="mini-combobox"
                           data="[{id:'1',text:'????????????'},{id:'0', text:'???????????????'}]" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" style="margin-left:80px"
                            onclick="clientSetFiles();">????????????
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:180px"
                            onclick="closeWindow('ClientSetWindow')">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="??????????????????" width="600" height="240" style="display:none" id="TechSBWindow">
        <table style="width:100%" class="layui-table" id="TechSBForm">
            <tr>
                <td style="width:100px">????????????</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" name="SBMemo"></textarea>
                </td>
            </tr>
            <tr>
                <td>????????????</td>
                <td>
                    <input style="width:100%" name="SBResult" class="mini-combobox"
                           data="[{id:'1',text:'????????????'},{id:'0', text:'???????????????'}]" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="techSB();">????????????</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('TechSBWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>

    <div class="mini-window" title="??????????????????" width="600" height="240" style="display:none" id="SettleWindow">
        <table style="width:100%" class="layui-table" id="SettleForm">
            <tr>
                <td style="width:100px">????????????</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" name="SettleMemo"></textarea>
                </td>
            </tr>
            <tr>
                <td>????????????</td>
                <td>
                    <input style="width:100%" name="SettleResult" class="mini-combobox"
                           data="[{id:1,text:'????????????'},{id:0, text:'????????????'}]" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="auditSettle();">????????????</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('SettleWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>

    <div class="mini-window" title="?????????????????????" width="500" height="240" style="display:none" id="LinkManWindow">
        <table style="width:100%" class="layui-table" id="LinkManForm">
            <tr>
                <td style="width:100px">???????????????</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="ClientLinkMan"/>
                    <input class="mini-hidden" name="SubID"/>
                </td>
            </tr>
            <tr>
                <td style="width:100px">???????????????</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="ClientLinkMail"/>
                </td>
            </tr>
            <tr>
                <td style="width:100px">???????????????</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="ClientLinkPhone"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="saveLinkInfo();">????????????</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('LinkManWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="??????????????????" width="600" height="160" style="display:none" id="OutTechWindow">
        <table style="width:100%" class="layui-table" id="OutTechForm">
            <tr>
                <td>?????????????????????:</td>
                <td>
                    <div id="ConOutTechMan" class="mini-treeselect" style="width:100%"
                         url="/permission/roleClass/getAllUserByRole?RoleID=15"
                         textField="Name" valueField="FID" parentField="PID"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" id="CmdCommitOutTech" onclick="outSourceTech();
">????????????
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('OutTechWindow')
">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="?????????????????????" width="640" height="180" style="display:none" id="SupportManWindow">
        <table style="width:98%;overflow:hidden" class="layui-table" id="SupportManForm">
            <tr>
                <td width="150px">?????????????????????:</td>
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
                        ????????????
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px"
                            onclick="closeWindow('SupportManWindow')">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="???????????????" width="600" height="220" style="display:none" id="ChangeTechManWindow">
        <table style="width:99%" class="layui-table" id="ChangeTechManForm">
            <tr>
                <td>????????????:</td>
                <td>
                    <div id="OldMan" class="mini-treeselect" name="OldMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true"
                         expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false" enabled="false"></div>
                    <input class="mini-hidden" name="SubID"/>
                </td>
            </tr>
            <tr>
                <td>?????????:</td>
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
                        ????????????
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px"
                            onclick="closeWindow('ChangeTechManWindow')">????????????
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
                btn.setText("??????");
                btn.setIconCls("icon-collapse");
                $('#p1').css('display', "block");
                //cmdA1.show();
                //cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setText("????????????");
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
                    title: '??????????????????',
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
                                var linkMan = "<div>???????????????:" + row["ClientLinkMan"] + "</div>";
                                var linkPhone = row["ClientLinkPhone"];
                                if (linkPhone) linkMan += "<div>????????????:" + linkPhone + "</div>";
                                var linkEmail = row["ClientLinkMail"];
                                if (linkEmail) linkMan += "<div>????????????:" + linkEmail + "</div>";
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
                                   var memo="??????"+total+"???????????????:"+free+"??????";
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
                        var iis = mini.loading('??????????????????????????????........');
                        var con = mini.get('#ConOutTechMan');
                        var allocMan = con.getValue() || 0;
                        var url = '/casesTech/addOutSourceTech';
                        $.post(url, {IDS: cs.join(','), OutTechMan: allocMan}, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('????????????????????????!', '????????????', function () {
                                    grid.reload();
                                    var win = mini.get('OutTechWindow');
                                    win.hide();
                                });
                            } else mini.alert(result.message || '??????????????????????????????????????????!');
                        })
                    }

                    mini.confirm('?????????????????????????????????????????????', '????????????', function (act) {
                        if (act == 'ok') g();
                    });
                } else mini.alert('??????????????????????????????!');
            } else mini.alert('??????????????????????????????!');
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
            cmdUpload.setText('????????????????????????');
            if (state == 50) {
                cmdAccept.show();
                cmdSetTechMan.show();
            } else if (state == 51 || state == 53) {
                //????????????????????????????????????????????????????????????
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
                    cmdUpload.setText('????????????????????????');
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
                        title: '??????????????????',
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
            } else mini.alert('??????????????????????????????!');
        }

        /????????????/


        function setTechMan() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#SetTechManWindow');
                win.show();
            } else mini.alert('??????????????????????????????!');
        }

        /*
            ??????????????????
        */
        function acceptTechMan() {
            var techManForm = new mini.Form('#SetTechManForm');
            techManForm.validate();
            if (techManForm.isValid() == false) {
                mini.alert('??????????????????????????????????????????!');
                return;
            }
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["SubID"]);
            }
            if (subs.length == 0) {
                mini.alert('??????????????????????????????????????????!');
                return;
            }
            var data = techManForm.getData();
            var techMan = data['AllocTechMan'];
            var auditMan = data["AllocAuditMan"]

            function g() {
                var iis = mini.loading('??????????????????????????????.........');
                var url = '/casesTech/acceptTechTask';
                var arg = {SubIDS: subs.join(','), TechMan: techMan, AuditMan: auditMan};
                $.post(url, arg, function (result) {
                    mini.hideMessageBox(iis);
                    if (result.success) {
                        mini.alert('???????????????????????????!', '????????????', function () {
                            var win = mini.get('#SetTechManWindow');
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || '?????????????????????????????????????????????!');
                })
            }

            mini.confirm('????????????????????????????????????????????????', '????????????', function (act) {
                if (act == 'ok') g();
            });
        }

        /*
        * ????????????????????????
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
                    var iis = mini.loading('??????????????????????????????.........');
                    var url = '/casesTech/acceptTechTask';
                    var arg = {SubIDS: subs.join(','), AuditMan: auditMan};
                    $.post(url, arg, function (result) {
                        mini.hideMessageBox(iis);
                        if (result.success) {
                            mini.alert('???????????????????????????!', '????????????', function () {
                                var win = mini.get('#AcceptWindow');
                                win.hide();
                                grid.reload();
                            });
                        } else mini.alert(result.message || '?????????????????????????????????????????????!');
                    })
                }

                mini.confirm('????????????????????????????????????', '????????????', function (act) {
                    if (act == 'ok') g();
                });
            } else mini.alert('????????????????????????!');
        }

        /????????????/

        function rejectTechTask() {
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["SubID"]);
            }

            function g() {
                var iis = mini.loading('??????????????????????????????.........');
                var url = '/casesTech/rejectTechTask';
                var arg = {SubIDS: subs.join(',')};
                $.post(url, arg, function (result) {
                    mini.hideMessageBox(iis);
                    if (result.success) {
                        mini.alert('?????????????????????????????????!', '????????????', function () {
                            var win = mini.get('#AcceptWindow');
                            win.hide();
                            grid.reload();
                        });
                    }
                })
            }

            mini.confirm('??????????????????????????????????????????', '????????????', function (act) {
                if (act == 'ok') g();
            });
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            var mode = "Browse";
            var now = record[field];
            var uText = "??????";
            var type = "Tech";
            var state = parseInt(record["State"] || 0);
            var isOk = false;//??????????????????????????????
            if(field=="TechFiles"){
                if (state < 51) {
                    if (now) {
                        uText = "??????";
                        mode = "Edit";
                    } else {
                        uText = "??????";
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
                        uText = "??????";
                        mode = "Edit";
                    } else {
                        uText = "??????";
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
                            'hCode="' + record["SubID"] + '" ' + 'class="showCellTooltip">????????????</a>';
                    } else {
                        e.cellHtml = '<a href="javascript:void(0)"  field="ClientInfo"  onclick="editTechLinkMan(' + "'" + record["_id"] + "'" + ')" ' +
                            'style="color:blue;text-decoration:underline">??????</a>';
                    }
                }
                if (field == "OutTech") {
                    var OX = Integer.parse(now);
                    if (OX == 1) e.cellHtml = "???"; else e.cellHtml = "???";
                }
            }
            if (field == "ProcessText") {
                var dd = record[field] || "";
                if (dd == "[]") dd = null;
                var x = '<a href="javascript:void(0)"  field="ProcessText"  data-placement="bottomleft"  style="color:blue;' +
                    'text-decoration:underline" ' +
                    'hcode="' + record["SubID"] + '"' +
                    ' class="showCellTooltip" onclick="showMemo(' + "'" + record._id + "'" + ')">' + (dd ? "??????" : "??????") + '</a>';
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
                var value = record["SupportMan"] || "??????";
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
                                + '<div class="progressbar-label">?????????</div>'
                                +'</div>';
                        } else {
                            e.cellHtml = '<div class="progressbar">'
                                + '<div class="progressbar-percentred" style="width:' + value + '%;"></div>'
                                + '<div class="progressbar-labelred">????????????</div>'
                                +'</div>';
                        }
                    } else {
                        e.cellHtml = '<div class="progressbar">'
                            + '<div class="progressbar-percentred" style="width:' + value + '%;"></div>'
                            + '<div class="progressbar-labelred">?????????</div>'
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
                        mini.alert('??????????????????????????????!', '????????????', function () {
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || "??????????????????????????????!");
                })
            } else mini.alert('???????????????????????????!');
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
                        var iis = mini.loading('??????????????????????????????.........');
                        $.post(url, {IDS: cs.join(',')}, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('??????????????????????????????!', '????????????', function () {
                                    grid.reload();
                                });
                            } else mini.alert(result.message || '??????????????????????????????!', '????????????');
                        });
                    }

                    mini.confirm('????????????????????????????????????????????????', '????????????', function (act) {
                        if (act == 'ok') g();
                    });
                } else mini.alert('???????????????????????????!');
            } else mini.alert('?????????????????????????????????!');
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
                var uid = mini.prompt("??????????????????????????????", "????????????????????????", function (action, value) {
                    if (action == 'ok') {
                        value = $.trim(value);
                        if (!value) {
                            mini.alert('????????????????????????!');
                            return;
                        }
                        if (value == shenqingName) {
                            mini.alert('??????????????????????????????????????????!');
                            return;
                        }

                        function g() {
                            var url = '/casesTech/changeShenqingName';
                            var arg = {SubID: record["SubID"], NewShenqingName: value};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('????????????????????????!', '????????????', function () {
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || "??????????????????????????????!");
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
                var uid = mini.prompt("????????????????????????", "???????????????????????????", function (action, value) {
                    if (action == 'ok') {
                        value = $.trim(value);
                        if (!value) {
                            mini.alert('???????????????????????????!');
                            return;
                        }
                        if (value == memoText) {
                            mini.alert('?????????????????????????????????????????????!');
                            return;
                        }

                        function g() {
                            var url = '/casesTech/changeLXText';
                            var arg = {SubID: record["SubID"], NewLXText: value};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('???????????????????????????!', '????????????', function () {
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || "??????????????????????????????!");
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
            //??????/?????????/????????????/??????/????????????
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
            var title = '????????????????????????';
            var record = grid.getRowByUID(ID);
            var subId = record["SubID"];
            var rows = grid.getSelecteds();
            if (rows.length > 1) {
                var c = [];
                for (var i = 0; i < rows.length; i++) {
                    c.push(rows[i]["SubID"]);
                }
                subId = c.join(',');
                title = '??????????????????????????????';
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
                excel.export("????????????.xls");
            } else mini.alert('????????????????????????!');
        }

        function doUpload(casesId, subId, ids, type, row, mode) {
            var title = '??????????????????';
            if (type == "Zl") title = "??????????????????";
            if (type == "Accept") title = "??????????????????";
            if (type == "Exp") title = "??????????????????";
            if (type == 'Aud') title = '????????????????????????';
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
                                    mini.alert('????????????????????????????????????????????????????????????');
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
                        var iis = mini.loading('??????????????????????????????.........');
                        var url = '/casesTech/commitTechFiles';
                        var memo = mini.get('#CommitMemo').getValue() || "";
                        var arg = {SubIDS: cs.join(','), Memo: memo};
                        $.post(url, arg, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('???????????????????????????', '????????????', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "????????????????????????????????????!");
                        });
                    }

                    mini.confirm('??????????????????????????????????????????', '????????????', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('??????????????????????????????!');
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
            } else mini.alert('??????????????????????????????!');
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
                        content: '????????????????????????????????????',
                        timeout: 3000,
                        x: 'center',
                        y: 'center',
                        state: 'success'
                    });
                });

                clipboard.on('error', function (e) {
                    mini.showTips({
                        content: '?????????????????????????????????:' + e.toString(),
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
                        var iis = mini.loading('??????????????????????????????.........');
                        var url = '/casesTech/auditTechFiles';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('???????????????????????????', '????????????', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "??????????????????????????????!");
                        });
                    }

                    var ttext = "????????????????????????????????????????????????????";
                    if (data.AuditResult == 0) ttext = "????????????????????????????????????????????????????";
                    mini.confirm(ttext, '????????????', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('??????????????????????????????!');
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
                var ttext = "??????????????????????????????????????????????????????????";
                if (parseInt(data.Result) == 0) ttext = "????????????????????????????????????????????????????";
                mini.confirm(ttext, '????????????', function (act) {
                    if (act == 'ok') g();
                });

                function g() {
                    var iis = mini.loading('??????????????????????????????.........');
                    var url = '/casesTech/setTechFiles';
                    data.SubIDS = cs.join(',');
                    $.post(url, data, function (result) {
                        mini.hideMessageBox(iis);
                        if (result.success) {
                            mini.alert('???????????????????????????', '????????????', function () {
                                win.hide();
                                grid.reload();
                            });
                        } else mini.alert(result.message || "??????????????????????????????!");

                    })
                }
            } else mini.alert('???????????????????????????!');
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
                    mini.alert('????????????:' + html, '????????????');
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
                        var iis = mini.loading('??????????????????????????????.........');
                        var url = '/casesTech/sbTechFiles';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.hideMessageBox(iis);
                                mini.alert('???????????????????????????', '????????????', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "??????????????????????????????!");
                        });
                    }

                    var ttext = "????????????????????????????";
                    if (data.AuditResult == 0) ttext = "????????????????????????????????????????";
                    mini.confirm(ttext, '????????????', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('??????????????????????????????!');
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
                        var iis = mini.loading('??????????????????????????????.........');
                        var url = '/casesTech/auditSettle';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            mini.hideMessageBox(iis);
                            if (result.success) {
                                mini.alert('???????????????????????????', '????????????', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "??????????????????????????????!");
                        });
                    }

                    var ttext = "????????????????????????????";
                    if (data.AuditResult == 0) ttext = "??????????????????????????????????";
                    mini.confirm(ttext, '????????????', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('??????????????????????????????!');
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
                        mini.alert('????????????!', '????????????', function () {
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || "??????????????????????????????!");
                })
            } else mini.alert('??????????????????????????????????????????!');
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
            } else mini.alert('??????????????????????????????????????????!');
        }

        function changeTechMan() {
            var res = changeTechForm.getData();
            if (!res.NewMan) {
                mini.alert('??????????????????????????????!');
                return;
            }
            var iid = mini.loading('??????????????????????????????......');
            var OldMan = parseInt(curTechRow["TechMan"] || 0);
            res.OldMan = OldMan;
            res.CasesID = curTechRow["CasesID"];
            var url = '/casesTech/changeTechMan';
            $.post(url, res, function (result) {
                mini.hideMessageBox(iid);
                if (result.success) {
                    mini.alert('?????????????????????????????????????????????!', '????????????', function () {
                        grid.reload();
                        changeTechWin.hide();
                    });
                } else mini.alert(result.message || "???????????????????????????????????????!");
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
                ///?????????????????????????????????????????????????????????????????????
                if(
                    userId==createMan ||
                    roleName=="???????????????" ||
                    roleName.indexOf("??????")>-1 ||
                    createManager.indexOf(userId)>-1
                ){
                    e.cancel=false;
                } else e.cancel=true;
            }
            if (field == "Action" || field=="WorkStatus") {
                ///????????????????????????????????????????????????
                var techMan = parseInt(record["TechMan"] || "0");
                var ts=record["TechManager"] ||"";
                var techManager=ts.split(",") || [];
                if(
                    userId==techMan ||
                    roleName=="???????????????" ||
                    roleName.indexOf("??????")>-1 ||
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
                        var msg = r.message || "??????????????????????????????????????????";
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
                        var msg = r.message || "??????????????????????????????????????????";
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
                        var msg = r.message || "?????????????????????????????????";
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