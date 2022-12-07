<#include "/shared/dialog.ftl">

<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploader.js"></script>
    <script type="text/javascript" src="/js/work/formBuilder.js"></script>
    <script type="text/javascript" src="/js/work/formManager.js"></script>
    <script type="text/javascript" src="/js/work/ajDetailHigh.js?v=${version}"></script>
    <script type="text/javascript" src="/js/work/payCaseHighMoney.js?v=${version}"></script>
    <script type="text/javascript">
        var ajTypes = [
            {id: '发明专利', text: '发明专利'},
            {id: '实用新型', text: '实用新型'},
            {id: '外观设计', text: '外观设计'},
            {id: '其他', text: '其他'}
        ];
        var repType=[
            {id:'内部出具',text:'内部出具'},
            {id:'外部出具',text:'外部出具'},
            {id:'不需要审计报告',text:'不需要审计报告'}
        ];
    </script>


    <style>
        .mini-tools span {
            width: 30px
        }

        .layui-table, .layui-table-view {
            margin: 0px
        }

        body .mini-labelfield {
            margin-bottom: 0px
        }

        .mini-button-icon {
            left: 2px
        }

        .mini-tools span {
            margin-left: 2px
        }
    </style>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden;">
            <h1>
                <span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px;vertical-align: middle">
            <#if Mode=="Add" || Mode=="Edit">
                <a class="mini-button mini-button-success" id="CmdSave" onclick="doSave()">保存信息</a>
                <a class="mini-button mini-button-primary" id="CmdCommit" onclick="doCommit()">提交审核</a>
                <a class="mini-button mini-button-primary" id="CmdCommitMan" onclick="showCommit()">指定人员审核</a>
            </#if>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="1、交单信息" width="auto"
                     showcollapsebutton="false" showclosebutton="false">
                    <div id="Table1" style="height:230px">

                    </div>
                    <div id="Others">
                        <input class="mini-hidden" name="id" id="id"/>
                        <input class="mini-hidden" name="casesId" id="casesId"/>
                        <input class="mini-hidden" name="state" id="state"/>
                        <input class="mini-hidden" name="AuditMan" id="AuditMan"/>
                    </div>
                </div>
            </div>
            <#if ViewDetail=1>
                <div class="mini-col-12">
                    <div id="Info3" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="2、精细化交单"
                         width="auto" showcollapsebutton="false" showclosebutton="false">
                        <div class="mini-datagrid" id="ajGrid" style="width:100%;height:350px;" multiSelect="true"
                             oncellbeginedit="beforeEdit"
                             url="/caseHigh/getSubRecords" autoload="false" frozenStartColumn="0" frozenEndColumn="6"
                             showPager="false" allowCellEdit="true" allowCellSelect="true" allowCellValid="true"
                             showSummaryRow="true" oncellcommitedit="afterEdit" oncellendedit="endEdit">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="memo" headerAlign="center" align="center">备注
                                    <input property="editor" class="mini-textbox"/>
                                </div>
                                <div field="subNo" headerAlign="center" align="center" width="120">立案编号</div>
                                <div field="yname" headerAlign="center" align="center" vtype="required" width="180">
                                    业务名称
                                </div>
                                <div field="sbYear" headerAlign="center" align="center" width="80">申报年度</div>
                                <div field="area" headerAlign="center" align="center" width="120">所在地区</div>
                                <div field="rptType" headerAlign="center" align="center" width="100">审计报告来源
                                    <input property="editor" class="mini-combobox" data="repType"/>
                                </div>
                                <div field="containSjf" headerAlign="center" align="center" type="comboboxcolumn"
                                     width="110">是否包含审计费
                                    <input property="editor" class="mini-combobox"
                                           data="[{id:1,text:'是'},{id:0,text:'否'}]"/>
                                </div>
                                <div field="swsName" headerAlign="center" align="center" width="200">会计事务所名称</div>
                                <div field="daiMoney" headerAlign="center" vtype="required" align="center"
                                     summaryType="sum" width="120">单价金额
                                    <input property="editor" class="mini-spinner" maxValue="99999999" minValue="0"/>
                                </div>

                                <#--                                <div field="sjfMoney" headerAlign="center" align="center" summaryType="sum"-->
                                <#--                                     width="120">审计费单价-->
                                <#--                                </div>-->
                                <div field="totalMoney" headerAlign="center" align="center" summaryType="sum"
                                     width="120">代理费小计
                                </div>

                                <div field="clientRequiredDate" width="150" headerAlign="center" align="center"
                                     dataType="date" dateFormat="yyyy-MM-dd">客户要求申报绝限
                                    <input property="editor" class="mini-datepicker" dateFormat="yyyy-MM-dd"/>
                                </div>
                                <div field="supportMan" headerAlign="center" width="200" align="center"
                                     allowInput="false">技术谈判人
                                    <input property="editor" popupWidth="500" class="mini-popupedit" name="supportMan"
                                           textField="Name" valueField="Name" popupWidth="100%"
                                           popup="#gridPanel" onhidepopup="onManHide" onshowpopup="onManShow"
                                           allowInput="false"/>
                                </div>
                                <div field="clientLinkMan" name="clientLinkMan" width="150" headerAlign="center"
                                     align="center">客户联系人
                                    <input property="editor" class="mini-buttonedit" id="cmdLinkMan"/>
                                </div>
                                <div field="clientLinkPhone" width="150" headerAlign="center" align="center">联系电话
                                    <input property="editor" class="mini-textbox"/>
                                </div>
                                <div field="clientLinkMail" headerAlign="center" align="center" width="150">联系邮箱
                                    <input property="editor" class="mini-textbox"/>
                                </div>
                            </div>
                        </div>
                        <table style="width: 100%; height:40px" class="layui-table">
                            <tr>
                                <td style="width:100px">优惠金额:</td>
                                <td colspan="2">
                                    <input class="mini-textbox" style="width:100%" name="DMoney" enabled="false"/>
                                </td>

                                <td style="width:100px">优惠后金额:</td>
                                <td colspan="2">
                                    <input class="mini-textbox" style="width:100%" name="ChangeMoney" enabled="false"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="mini-col-12">
                    <div id="Info6" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="3、回款认领"
                         width="auto" showcollapsebutton="false" showclosebutton="false">
                        <div class="mini-datagrid" id="gridDetail" style="width:100%;height:240px" allowCellValid="true"
                             autoload="true" sortField="moneyType" sortOrder="Asc" showSummaryRow="true"
                             sortField="moneyType" ondrawcell="onDetailDraw" ondrawsummarycell="drawSummary"
                             autoload="false" showPager="false" url="/cases/getSelectMoney">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div field="State" align="center" headerAlign="center" type="comboboxcolumn">业务状态
                                    <input property="editor" class="mini-combobox" data="[{id:0,text:'待审核'},{id:1,
                                    text:'审核驳回'},{id:2,text:'审核通过'}]"/>
                                </div>
                                <div field="DocSN" align="center" headerAlign="center" width="150">回款单号</div>
                                <div field="PayMoney" align="center" headerAlign="center">回款金额</div>
                                <div width="120" align="center" field="Guan" headerAlign="center" vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">领用官费
                                    <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999"/>
                                </div>
                                <div width="120" align="center" field="Dai" headerAlign="center" vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">领用代理费
                                    <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999"/>
                                </div>
                                <div width="120" align="center" field="Total" headerAlign="center" summaryType="sum"
                                     dataFormat="float" numberFormat="n2">领用合计
                                </div>
                                <div field="CreateMan" width="100" type="treeselectcolumn" align="center"
                                     headerAlign="center">领用人员
                                    <input property="editor" class="mini-treeselect" valueField="FID"
                                           parentField="PID" onbeforenodeselect="beforeSelectClient"
                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" style="width:
                               98%;" required="true" resultAsTree="false"/>
                                </div>
                                <div field="CreateTime" width="120" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss"
                                     align="center" headerAlign="center">领用时间
                                </div>
                                <div field="AuditMemo" align="center" headerAlign="center">审核意见</div>
                                <div field="AuditMan" width="100" type="treeselectcolumn" align="center"
                                     headerAlign="center">审核人员
                                    <input property="editor" class="mini-treeselect" valueField="FID"
                                           parentField="PID" onbeforenodeselect="beforeSelectClient"
                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" style="width:
                               98%;" required="true" resultAsTree="false"/>
                                </div>
                                <div field="AuditTime" width="120" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss"
                                     align="center" headerAlign="center">审核时间
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="
            <#if ViewDetail=1>4、附件信息 <#else>2、附件信息</#if>"
                     width="auto" showcollapsebutton="false"
                     showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div id="attGrid" style="width:100%;height: 300px;">
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title=
                "<#if ViewDetail=1>5、修改记录<#else>3、修改记录</#if>"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="changeGrid" style="width:100%;height: 200px;"
                                     allowCellWrap="true"
                                     url="/work/cases/getChangeRecord" autoload="false">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div field="changeText" headerAlign="center" align="left"
                                             width="600">变更内容
                                        </div>
                                        <div field="userId" align="center" headerAlign="center"
                                             type="treeselectcolumn">操作人员
                                            <input property="editor" class="mini-treeselect"
                                                   url="/systems/dep/getAllLoginUsersByDep"
                                                   textField="Name" valueField="FID" parentField="PID"/>
                                        </div>
                                        <div field="createTime" headerAlign="center" dataType="date"
                                             dateFormat="yyyy-MM-dd HH:mm:ss" align="center">操作时间
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" title="新增业务明细" id="addYwWindow" width="1000" height="380" bodyStyle="overflow:hidden">
        <table style="width:100%;height:98%" class="layui-table" id="ajForm">
            <tbody>
            <tr>
                <td>业务名称</td>
                <td>
                    <#--                <input id="yId" required="true" class="mini-treeselect" url="/cases/getProducts"-->
                    <#--                       valueFromSelect="true" allowInput="true" expandOnload="true" style="width:100%" name="yId"/>-->
                    <input class="mini-buttonedit" onclick="showAllYW()" id="yId" name="yId" style="width:100%"
                           allowInput="false" required="true"/>
                    <input class="mini-hidden" name="casesId"/>
                </td>
                <td>数量</td>
                <td>
                    <input class="mini-spinner" name="num" maxValue="9999999" minValue="1" required="true"
                           style="width:100%"/>
                </td>
            </tr>
            <tr>
                <td>申报年度</td>
                <td>
                    <input class="mini-textbox" name="sbYear" required="true" style="width:100%"/>
                </td>
                <td>所在地区</td>
                <td>
                    <input class="mini-textbox" name="area" required="true" style="width:100%"/>
                </td>
            </tr>
            <tr>
                <td>签约日期</td>
                <td>
                    <input class="mini-datepicker" style="width:100%" dateFormat="yyyy-MM-dd" required="true"
                           name="signTime" value="new Date()"/>
                </td>
                <td>客户要求申报绝限</td>
                <td>
                    <input class="mini-datepicker" name="clientRequiredDate" required="true" style="width:100%"/>
                </td>
            </tr>
            <tr>
                <td>技术谈判人</td>
                <td>
                    <input class="mini-popupedit" name="supportMan" style="width:100%;" textField="Name"
                           valueField="Name" popupWidth="500px"
                           popup="#gridPanel" onhidepopup="onManHide" onshowpopup="onManShow" allowInput="false"/>
                </td>
                <td>审计报告来源</td>
                <td>
                    <div class="mini-combobox sjCon" name="rptType" style="width:100%" data="repType"
                         valueFromSelect="true" required="true"/>
                </td>
            </tr>
            <tr>
                <#--                <td>是否包含审计费</td>-->
                <#--                <td>-->
                <#--                    <input class="mini-combobox sjCon" name="containSjf" required="true" class="mini-combobox" data="[{id:1,-->
                <#--                text:'是'},{id:0,text:'否'}]" value="" style="width:100%"/>-->
                <#--                    -->
                <#--                </td>-->
                <#--                <td>审计费单价</td>-->
                <#--                <td>-->
                <#--                    <input class="mini-spinner  sjCon" name="sjfMoney" maxValue="9999999" minValue="0" required="true"-->
                <#--                           style="width:100%" vtype="int;range:0,9999999"/>-->
                <#--                </td>-->
                <td>会计事务所名称</td>
                <td>
                    <input class="mini-textbox  sjCon" name="swsName" maxValue="9999999" minValue="0" required="true"
                           style="width:100%"/>
                    <input class="mini-hidden" name="containSjf" value="0"/>
                </td>
                <td>单价金额</td>
                <td>
                    <input class="mini-spinner" name="daiMoney" maxValue="9999999" minValue="0" required="true"
                           style="width:100%"/>
                </td>
            </tr>
            <tr>

            </tr>
            <tr>
                <td colspan="4" style="text-align: center">
                    <button class="mini-button mini-button-success" id="AddAJ">确认添加</button>
                    <button class="mini-button mini-button-danger" onclick="closeWindow('addYwWindow')"
                            style="margin-left:180px">放弃添加
                    </button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="mini-window" title="选择交单审核人" width="600" height="150" style="display:none" id="AcceptWindow">
        <table style="width:100%" class="layui-table" id="AcceptForm">
            <tr>
                <td>指定交单审核人</td>
                <td>
                    <div name="AllocAuditMan" class="mini-treeselect" style="width:100%" valueFromSelect="true"
                         url="/systems/dep/getAllLoginUserByFun?FunName=AuditCases" required="true" allowInput="true"
                         textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                         value="${Manager}"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="acceptAudit();">确认操作</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('AcceptWindow')
">取消接单
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div id="gridPanel" class="mini-panel" title="选择人员" iconCls="icon-add" style="width:100%;height:300px;"
         visible="false"
         showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0" borderStyle="border:0">
        <div property="toolbar" style="padding:5px;padding-left:8px;">
            <input id="keyText" class="mini-textbox" style="width:80%;" emptyText="录入关键字，按回车键进行查询!" onenter="doQuery"/>
            <a class="mini-button" onclick="onClear()">重置</a>
        </div>
        <ul class="mini-tree" id="LoginUserTree" url="/systems/dep/getAllLoginUsersByDep" required="true"
            showCheckBox="true" resultAsTree="false" multiSelect="true"
            textField="Name" idField="FID" parentField="PID" expandOnload="true" style="width:100%"></ul>
    </div>
    <div class="mini-window" id="YwWindow" title="选择产品" width="1000" height="600" style="display:none;overflow:hidden">
        <div class="mini-fit">
            <div class="mini-treegrid" id="treegrid1" treeColumn="text" url="/cases/getProducts" parentField="pid"
                 idField="id" showFilterRow="true"
                 resultAsTree="false" showTreeIcon="true" style="width:99%;height:100%;overflow:hidden"
                 expandonload="true" onrowclick="onYwSelect">
                <div property="columns">
                    <div type="checkcolumn"></div>
                    <div field="text" width="240" headerAlign="center" name="text">产品名称
                        <input property="filter" class="mini-textbox" onvaluechanged="onFilterChanged"
                               id="NameColumn" style="width:100%" emptyText="输入查询关键字，按Enter键进行查询。" showClose="true"/>
                    </div>
                    <div field="price" width="100" headerAlign="center" align="center">报价(元)</div>
                    <div field="maxDays" width="100" headerAlign="center" align="center">处理期限(天)</div>
                    <div field="required" width="200" headerAlign="center" align="center">交单要求</div>
                </div>
            </div>
        </div>
        <div style="text-align:center;vertical-align: middle">
            <button class="mini-button mini-button-success" id="CmdYw" style="width:120px;margin-top:10px"
                    enabled="false" onclick="selectYw">确认选择
            </button>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button class="mini-button mini-button-danger" style="width:120px;margin-top:10px"
                    onclick="closeWindow('YwWindow')">取消退出
            </button>
        </div>
    </div>
    <div class="mini-window" title="选择回款记录" id="PayBackWindow" style="width:1200px;height:500px;display: none">
        <div class="mini-toolbar">
            <table style="width:100%">
                <tr>
                    <td style="width:120px">查询关键字:</td>
                    <td style="width:50%">
                        <input class="mini-textbox" id="QueryText" style="width:400px">
                        <button class="mini-button mini-button-normal" id="CmdQuery">&nbsp;模糊搜索&nbsp;</button>
                    </td>
                    <td style="text-align: left;width:18%">
                        <span style="font-size: 20px;font-family: 黑体;color:red" id="MoneyText"></span>
                    </td>
                    <td>
                        <button class="mini-button mini-button-success" id="cmdConfirm">确认选择</button>
                        &nbsp&nbsp;
                        <button class="mini-button mini-button-danger" id="cmdClose">关闭窗口</button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="mini-fit">
            <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                 allowresize="true" url="/finance/arrival/getWorkData" multiselect="false" frozenStartColumn="0"
                 frozenEndColumn="5" allowCellSelect="true"
                 pagesize="20" sortfield="AddTime" sortorder="desc" autoload="true">
                <div property="columns">
                    <div type="checkcolumn"></div>
                    <div field="DocumentNumber" width="140" headerAlign="center" align="center">单据编号</div>
                    <div field="DateOfPayment" width="100" headeralign="center" datatype="date"
                         dateformat="yyyy-MM-dd" allowsort="true" align="center">回款日期
                    </div>
                    <div field="Payer" width="240" headeralign="center" align="center">付款人</div>
                    <div field="PaymentAmount" width="100" headeralign="center" allowsort="true"
                         align="center">回款金额
                    </div>
                    <div field="FreeMoney" width="100" headeralign="center" allowsort="true"
                         align="center">剩余可领金额
                    </div>
                    <div field="FreeGuan" name="FreeGuan" width="100" headeralign="center">领用官费
                        <input property="editor" id="ConGuanMoney" class="mini-spinner" minValue="0"
                               maxValue="999999999"/>
                    </div>
                    <div field="FreeDai" name="FreeDai" width="100" headeralign="center">领用代理费
                        <input property="editor" id="ConDaiMoney" class="mini-spinner" minValue="0"
                               maxValue="999999999"/>
                    </div>
                    <div field="FreeTotal" width="100" headeralign="center">领用合计</div>
                    <div field="Description" width="120" headeralign="center" align="center">款项描述</div>
                    <div field="PaymentAccount" width="180" headeralign="center" align="center">付款账户</div>
                    <div field="ReturnBank" width="140" headeralign="center" align="center">回款银行</div>
                    <div field="AddTime" width="100" headeralign="center" datatype="date"
                         dateformat="yyyy-MM-dd" allowsort="true" align="center">登记日期
                    </div>
                    <div field="SignMan" width="80" headeralign="center" type="treeselectcolumn"
                         allowsort="true" align="center">登记人
                        <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                               textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="SignMan"
                               style="width:
                               98%;" required="true" resultAsTree="false"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        var config = ${config};
        var loadObj = ${Load};
        var mode = '${Mode}';
        var attIds = ${AttID};
        var manager = null;
        var att = null;
        var ajD = null;
        var cmdSave = null;
        var cmdCommit = null;
        var mainObject = null;
        mini.parse();
        var tree = mini.get('#LoginUserTree');
        var textCon = mini.get('#keyText');
        $(function () {
            var panel1 = mini.get('#Info3');
            if (panel1) {
                if (mode == "Add" || mode == "Edit") {
                    panel1.setButtons([
                        {html: "<a class='mini-button' style='width:80px;margin-top:-4px' id='changeTotal' iconCls='icon-reload'>折扣</a>"},
                        {html: "<a class='mini-button' style='width:80px;margin-top:-4px' id='addAj' iconCls='icon-add'>新增</a>"},
                        {html: "<a class='mini-button' style='width:80px;margin-top:-4px' id='saveAj' iconCls='icon-save'>保存</a>"},
                        {html: "<a class='mini-button' style='width:80px;margin-top:-4px' id='removeAj' iconCls='icon-remove'>删除</a>"}
                    ]);
                }
            }
            var panel2 = mini.get('#Info6');
            if (panel2) {
                if (mode == 'Add' || mode == "Edit") {
                    panel2.setButtons([
                        {html: "<a class='mini-button' style='width:80px;margin-top:-4px' id='addMoney' iconCls='icon-add'>新增</a>"},
                        {html: "<a class='mini-button' style='width:80px;margin-top:-4px' id='removeMoney' iconCls='icon-remove'>删除</a>"}
                    ]);
                }
            }
            mini.parse();
            initDocument(mode);
            cmdSave = mini.get('CmdSave');
            cmdCommit = mini.get('CmdCommit');
        });

        function onManHide(e) {
            tree.clearFilter();
            var nodes = tree.getCheckedNodes(false);
            var cs = [];
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                cs.push(node.Name);
            }
            if (cs.length > 0) {
                e.sender.setValue(cs.join(','));
                e.sender.setText(cs.join(','));
            } else e.sender.setValue(null);
            textCon.setValue(null);
        }

        function onManShow(e) {
            var dd = (e.sender.getValue() || "");
            var cs = dd.split(',');
            tree.cascadeChild(null, function (node) {
                var name = node.Name || "";
                if (cs.indexOf(name) > -1) tree.checkNode(node); else tree.uncheckNode(node);
            });
        }

        function beforeEdit(e) {
            var field = e.field;
            if (field == "supportMan") {
                var editor = e.editor;
                var val = e.value;
                editor.setText(val);
            }
        }

        function doQuery() {
            var text = textCon.getValue();
            if (text) {
                tree.filter(function (node) {
                    return (node.Name || "").toString().indexOf(text) > -1;
                });
            } else tree.clearFilter();
        }

        function onClear() {
            textCon.setValue(null);
            tree.clearFilter();
        }

        function doSave() {
            var msgId = mini.loading('正在保存数据.........');
            try {
                var dd = manager.getValue(false);
                dd.Action = "Save";
                var url = '/caseHigh/saveAll';

                $.post(url, {Data: mini.encode(dd)}, function (result) {
                    if (result.success) {
                        function g() {
                            var obj = result.data;
                            if (obj) {
                                manager.setValue(obj);
                            }
                        }

                        mini.alert('业务交单信息保存成功!', '系统提示', function () {
                            g();
                        });
                    } else mini.alert(result.message || "保存失败，请稍候重试!");
                });

            } catch (e) {
                mini.alert(e);
            } finally {
                mini.hideMessageBox(msgId);
            }
        }

        function showCommit() {
            var win = mini.get('#AcceptWindow');
            win.show();
        }

        function acceptAudit() {
            var form = new mini.Form('#AcceptForm');
            form.validate();
            if (form.isValid()) {
                var data = form.getData();
                var dx = parseInt(data["AllocAuditMan"]);
                var con = mini.get('#AuditMan');
                if (con) con.setValue(dx);
                var ex = parseInt(con.getValue() || 0);
                if (ex == dx) {
                    doCommit();
                }
            } else {
                mini.alert('请选择交单审核人后再进行提交操作!');
            }
        }

        function doCommit() {
            var win = mini.get('#AcceptWindow');

            function g() {
                var msgId = mini.loading('正在提交审核.........');
                try {
                    var dd = manager.getValue(true);
                    dd.Action = "Commit";
                    var url = '/caseHigh/saveAll';

                    $.post(url, {Data: mini.encode(dd)}, function (result) {
                        if (result.success) {
                            function g() {
                                var obj = result.data;
                                if (obj) {
                                    manager.setValue(obj);
                                    cmdSave.destroy();
                                    cmdCommit.destroy();
                                    if (win) win.hide();
                                    CloseOwnerWindow('ok');
                                }
                            }

                            mini.alert('提交审核成功!', '系统提示', function () {
                                g();
                            });
                        } else mini.alert(result.message || "提交审核失败，请稍候重试!");
                    });

                } catch (e) {
                    mini.alert(e);
                } finally {
                    mini.hideMessageBox(msgId);
                }
            }

            var ok = manager.validate() && ajD.validate(true);
            if (ok == false) {
                mini.alert('数据录入不完整，不能提交审核!');
                return;
            }
            mini.confirm('确认审核，信息将不能修改，是否继续？', '审核提示', function (action) {
                if (action == 'ok') g();
            });
        }

        function initDocument(mode) {
            var builder = new formBuilder(config);
            var table = builder.creat();
            manager = new formManager(builder);
            ajD = new ajDetail('ajGrid', 'addAj', 'removeAj', mode);
            manager.bindEvent('beforeSave', function (result, doValid) {
                var rows = att.getPostFile();
                var res = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    res.push(row.ATTID);
                }
                if (res.length > 0) result.Att = res;
                var js = ajD.getData(doValid);
                if (js.length > 0) {
                    result.AJ = js;
                }
                var form = new mini.Form('#Others');
                var ds = form.getData();
                for (var field in ds) {
                    var dx = ds[field];
                    if (dx) result[field] = dx;
                }
                return result;
            });
            manager.bindEvent('afterLoad', function (obj) {
                var casesId = obj.casesId;
                if (casesId) {
                    ajD.loadData(obj);
                    var form = new mini.Form('#Others');
                    form.setData(obj);
                    att.loadFiles({IDS: attIds.join(',')});
                    mini.get('#changeGrid').load({CasesID: casesId});
                    var clientIdCon = mini.getbyName('ClientID');
                    if (clientIdCon) {
                        var cName = obj["clientIdName"];
                        if (cName) clientIdCon.setText(cName);
                    }
                    var contractNoCon = mini.getbyName('ContractNo');
                    if (contractNoCon) {
                        contractNoCon.setValue(obj["contractNo"]);
                        contractNoCon.setText(obj["contractNo"]);
                    }
                    var idCon = mini.get('#id');
                    if (idCon) {
                        idCon.setValue(obj.id);
                    }
                    var casesIdCon = mini.get('#casesId');
                    if (casesIdCon) {
                        casesIdCon.setValue(casesId);
                    }
                    var changeMoney = obj["allMoney"];
                    if (changeMoney) {
                        mini.getbyName('ChangeMoney').setValue(changeMoney);
                    }
                    var dMoney = obj["dMoney"];
                    if (dMoney) {
                        mini.getbyName("DMoney").setValue(dMoney);
                    }
                }
            });
            manager.bindEvent('afterInit', function () {
                var createCon = mini.getbyName('CreateMan');
                <#if Mode=="Add">
                if (createCon) {
                    createCon.setValue(${UserID});
                }
                mini.getbyName('SignTime').setValue(mini.parseDate('${Now}', 'yyyy-MM-dd HH:mm:ss'));
                </#if>
            });
            manager.bindEvent('buttonEditClick', function (name, con) {
                if (name == "ClientID") {
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
                                data = mini.clone(data);
                                con.setValue(data.ClientID);
                                con.setText(data.Name);
                            }
                        }
                    });
                } else if (name == "ContractNo") {
                    var conClient = mini.getbyName('ClientID');
                    var clientId = 0;
                    if (conClient) clientId = conClient.getValue();
                    mini.open({
                        url: '/work/contractReceive/query?multiselect=false&ClientID=' + clientId,
                        showModal: true,
                        width: 800,
                        height: 300,
                        title: '选择合同资料',
                        ondestroy: function (action) {
                            if (action == 'ok') {
                                var iframe = this.getIFrameEl();
                                var data = iframe.contentWindow.GetData();
                                data = mini.clone(data);
                                con.setValue(data.ContractNo);
                                con.setText(data.ContractNo);

                                var clientIdCon = mini.getbyName('ClientID');
                                if (clientIdCon) {
                                    clientIdCon.setValue(data["ClientID"]);
                                    clientIdCon.setText(data["ClientName"]);
                                }
                            }
                        }
                    });
                }
            });
            manager.render(document.getElementById('Table1'), mode);
            if (mode == 'Add' || mode == "Edit") {
                att = $('#attGrid').fileUploader({mode: 'Add'});
            } else att = $('#attGrid').fileUploader({mode: 'Browse'});

            <#if Mode=="Edit" || Mode=="Browse">
            manager.setValue(loadObj);
            </#if>
            var payer = new payCasesMoney('gridDetail', 'ajGrid');
            payer.init(mode);
        }

        function beforeEdit(e) {
            var field = e.field || "";
            var record = e.record;
            if (field == "sjfMoney") {
                var con = e.editor;
                var val = parseInt(record["containSjf"] || 0);
                if (val == 1) e.cancel = false; else e.cancel = true;
            }

        }

        function afterEdit(e) {
            var grid = e.sender;
            var field = e.field;
            var record = e.record;
            if (field == "containSjf") {
                var val = parseInt(e.value || 0);
                if (val == 0) {
                    grid.updateRow(record, {sjfMoney: null});
                }
            }

        }

        function endEdit(e) {
            var grid = e.sender;
            var field = e.field;
            if (field == "sjfMoney" || field == "daiMoney") {
                if (mainObject != null) {
                    var rows = grid.getData();
                    var total = countTotalMoney(rows);
                    mainObject.allMoney = total;
                    reCalcTotalMoney(mainObject);
                }
            }
        }

        function reCalcTotalMoney(mainObj) {
            var allMoney = parseFloat(mainObj["allMoney"] || 0);
            var changeMoney = parseFloat(mainObj["changeMoney"] || 0);
            if (changeMoney > 0) {
                var changeCon = mini.getByName("ChangeMoney");
                if (changeCon) changeCon.setValue(changeMoney);

                var DCon = mini.getByName("DMoney");
                if (DCon) {
                    DCon.setValue(allMoney - changeMoney);
                }
            }
            mainObject = mainObj;
        }

        function countTotalMoney(rows) {
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var money = parseFloat(row["totalMoney"]);
                total += money;
            }
            return total;
        }

        var cmdYw = mini.get('#CmdYw');
        var treegrid = mini.get('#treegrid1');
        var yCon = mini.get('#yId');
        var ywWin = mini.get('#YwWindow');

        function showAllYW() {
            ywWin.show();
            var rows = treegrid.getData();
            if (rows.length == 0) treegrid.load();
        }

        function onYwSelect(e) {
            var record = e.record;
            var level = parseInt(record["_level"]);
            if (level == 0) cmdYw.disable(); else cmdYw.enable();
        }

        function selectYw() {
            var record = treegrid.getSelected();
            if (record) {
                var id = record["id"];
                var text = record["text"];
                yCon.setValue(id);
                yCon.setText(text);


                if (text.indexOf("审计") == -1) {
                    mini.getByName("containSjf").setValue(0);
                } else mini.getByName("containSjf").setValue(1);


                var daiCon = mini.getbyName('daiMoney');
                if (daiCon) daiCon.setValue(record.price);
                var dateCon = mini.getbyName('clientRequiredDate');
                if (dateCon) {
                    var now = new Date();
                    var maxDay = addDays(now, parseInt(record.maxDays));
                    dateCon.setValue(maxDay);
                }
                var cons = ['rptType', 'swsName', 'sjfMoney'];
                if (cons.length > 0) {
                    var lock = false;
                    if (text.indexOf("审计") == -1) {
                        lock = true;
                    }
                    for (var i = 0; i < cons.length; i++) {
                        var con = mini.getByName(cons[i]);
                        if (con) {
                            if (lock == true) {
                                con.setRequired(false);
                                con.setEnabled(false);
                            } else {
                                con.setRequired(true);
                                con.setEnabled(true);
                            }
                        }
                    }
                }
                ywWin.hide();
            }
        }

        function addDays(dateObj, d) {
            var dayNumber = new Date(dateObj.getYear(), dateObj.getMonth() + 1, 0).getDate(); //当月天数
            var Year = dateObj.getFullYear();  //当前年
            var Month = dateObj.getMonth() + 1; //当前月
            var Day = dateObj.getDate(); //当前日

            for (var i = 0; i < d; i++) {
                Day++;
                if (Day > dayNumber) {
                    Day = 1;
                    Month++;
                    if (Month > 12) {
                        Month = 1;
                        Year++;
                    }
                }
            }
            return new Date(Year, Month - 1, Day);
        }

        function closeWindow(id) {
            var win = mini.get('#' + id);
            if (win) win.hide();
        }

        function onFilterChanged() {
            var con = mini.get('#NameColumn');
            var value = con.getValue();
            if (!value) treegrid.clearFilter();
            else {
                treegrid.filter(function (node) {
                    return node.text.indexOf(value) > -1;
                })
            }
        }
        function onDetailDraw(e){
            var row=e.record;
            var state=parseInt(row["State"] || 0);
            if (state == 1) {
                e.rowStyle = "text-decoration:line-through;text-decoration-color: black;color:red";
            }
        }
        function drawSummary(e) {
            var field = e.field;
            var records = e.data || [];
            if (field == 'Total') {
                var total = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["State"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["Total"] || 0);
                    total += d;
                }
                e.cellHtml = total.toFixed(2);
            } else if (field == "Dai") {
                var total1 = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["State"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["Dai"] || 0);
                    total1 += d;
                }
                e.cellHtml = total1.toFixed(2);

            } else if (field == "Guan") {
                var total2 = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["State"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["Guan"] || 0);
                    total2 += d;
                }
                e.cellHtml = total2.toFixed(2);
            }
        }
    </script>
</@js>
