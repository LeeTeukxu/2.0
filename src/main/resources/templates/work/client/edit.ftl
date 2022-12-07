<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript" src="/js/common/complexExcelExport.js"></script>
    <script type="text/javascript" src="/js/common/downLoadDJFQDExcel.js"></script>
    <script type="text/javascript">
        var cooTypes = [{id: 1, text: '合作客户'}, {id: 2, text: '意向客户'}];
        var types = [
            {id: 1, text: '工矿企业'},
            {id: 3, text: '事业单位'},
            {id: 4, text: '大专院校'},
            {id: 2, text: '个人'}
        ];
        var IsSend = [
            {id: 0, text: '未寄出'},
            {id: 1, text: '已寄出'}
        ];
        var positions=[{id:1,text:'在职'},{id:2,text:'离职'}];
        var role = "${Role}";
    </script>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="north" showHeader="false" height="260"   showSplit="false" >
            <div class="mini-toolbar" id="mainToolbar" style="text-align:right;padding-right:25px;">
                <a class="mini-button" iconcls="icon-save" id="ClientEdit_Save">保存</a>
            </div>
            <form id="clientForm" action="/" method="post" style="display: none">
                <table style="width: 100%; height: 100%" class="layui-table">
                    <tr>
                        <td style="padding-left:25px;position: relative;" title="保存后不能更改！"><span
                                    class="mini-button-icon mini-iconfont icon-help "></span>客户名称：
                        </td>
                        <td>
                            <input class="mini-textbox" id="name" name="name" required="true" style="width: 100%;"/>
                            <input class="mini-hidden" id="clientID" name="clientID" value="${ClientID}"/>
                            <input class="mini-hidden" id="DocMax" name="DocMax"/>
                            <input class="mini-hidden" name="canUse"/>
                            <input class="mini-hidden" name="canLogin"/>
                            <input class="mini-hidden" id="createMan" name="createMan"/>
                            <input class="mini-hidden" id="createTime" name="createTime"/>
                        </td>
                        <td>客户性质：</td>
                        <td>
                            <input class="mini-combobox" id="type" name="type" style="width: 100%;" value="1" data="types"
                                   required="true"/>
                        </td>
                        <td>登录邮箱：</td>
                        <td>
                            <input class="mini-textbox" id="orgCode" name="orgCode" style="width: 100%;" vtype="email"/>
                        </td>
                    </tr>
                    <tr>
                        <td>登记人员：</td>
                        <td>
                            <input class="mini-treeselect" valuefield="FID" parentfield="PID" textfield="Name"
                                   url="/systems/dep/getAllLoginUsersByDep" id="signMan" name="signMan" style="width: 100%;"
                                   resultastree="false" required="true"/>
                        </td>
                        <td>登记日期：</td>
                        <td>
                            <input class="mini-datepicker" id="signDate" name="signDate" style="width: 100%;"
                                   format="yyyy-MM-dd H:mm:ss" timeformat="H:mm:ss" showtime="true" required="true"/>
                        </td>
                        <td>客户标识：</td>
                        <td>
                            <input class="mini-textbox" name="fullName" style="width: 100%;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>合作类型：</td>
                        <td>
                            <input class="mini-combobox" id="cootype" name="cootype" valuefromselect="true" value="${Type}"
                                   style="width: 100%;" required="true" data="cooTypes" enabled="false"/>
                        </td>
                        <td>介绍人</td>
                        <td>
                            <input id="jsr" name="jsr" class="mini-textbox" style="width:100%"/>
                            <#--                    <input class="mini-combobox" name="jsr" valueField="ID" textField="Name"-->
                            <#--                           url="/work/clientInfo/getJSR?ClientID=${ClientID}" style="width: 100%;"-->
                            <#--                           virtualScroll="true" valuefromselect="true" autoload="false" allowInput="true" />-->
                        </td>
                        <td>信用代码:</td>
                        <td>
                            <input class="mini-textbox" style="width:100%" name="creditCode"/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td colspan="7">
                            <textarea class="mini-textarea" style="width:100%;height:50px" name="memo"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8">

                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div region="center"  showSplit="false" >
            <div class="mini-fit">
                <div id="tabs1" class="mini-tabs" style="width:100%;height:100%;display: none"
                     bodystyle="padding:0;border:0;border-bottom: 1px solid rgb(210, 210, 210);" activeindex="0">
                    <div name="FollowTab" title="跟进记录">
                        <div class="mini-toolbar" id="mainToolbar2" name="followRecordTool" style="text-align:right;padding-right:20px; border: none; background: none;">
                            <a class="mini-button" iconcls="icon-add" onclick="AddFollowRecordWindow">新增跟进记录</a>
                            <a class="mini-button" iconcls="icon-add" onclick="addImage()">新增跟进记录(带图片)</a>
                            <a class="mini-button" iconcls="icon-edit" onclick="UpdateFollowRecordWindow">修改</a>
                        </div>
                        <div class="mini-fit">
                            <div class="mini-datagrid" id="grid1" style="width:100%;height:100%;" pagesize="15"
                                 ondrawcell="onFollowDraw"
                                 sizelist="[5,10,20,50,100,150,200]" url="" autoload="false" onrowdblclick="onRowDblClick">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div type="checkcolumn"></div>
                                    <div field="Image" headerAlign="center" align="center">图片</div>
                                    <div headeralign="center" field="record" width="480">内容</div>
                                    <div field="followUserName" width="100" headeralign="center" align="center">跟进人</div>
                                    <div field="createTime" width="120" headeralign="center" align="center"
                                         dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss">跟进时间
                                    </div>
                                    <div field="updateTime" width="120" headeralign="center" align="center" dataType="date"
                                         dateFormat="yyyy-MM-dd HH:mm:ss">修改时间
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div name="LinkerTab" title="联系人信息">
                        <div class="mini-layout" style="width:100%;height:98%">
                            <div region="north" height="240" showHeader="false">
                                <div class="mini-toolbar" id="mainToolbar1" name="linkManTool"
                                     style="text-align:right;padding-right:20px; border: none; background: none;">
                                    <a class="mini-button" iconcls="icon-edit" onclick="EditDefaultMail">设为默认邮箱</a>
                                    <a class="mini-button" iconcls="icon-edit" onclick="CannelDefaultMail">取消默认邮箱</a>
                                    <a class="mini-button" iconcls="icon-add" onclick="AddClinetLinkWindow">新增联系人</a>
                                    <a class="mini-button" iconcls="icon-edit" onclick="UpdateClinetLinkWindow">修改</a>
                                </div>
                                <div class="mini-fit">
                                    <div class="mini-datagrid" id="grid2" style="width:100%;height:100%;"
                                         url="" autoload="false" onrowdblclick="onRowDblClick" ondrawcell="linkerDraw" multiselect="true">
                                        <div property="columns">
                                            <div type="indexcolumn"></div>
                                            <div type="checkcolumn"></div>
                                            <div headeralign="center" field="ctype" width="80" align="center">联系人类型</div>
                                            <div field="linkMan" headeralign="center" align="center">联系人</div>
                                            <div field="position"  headeralign="center" align="center"
                                                 type="comboboxcolumn">在职状态
                                                <input property="editor" class="mini-combobox"  data="positions" />
                                            </div>
                                            <div field="mobile" headeralign="center" align="center">手机号码</div>
                                            <div field="linkPhone" headeralign="center" align="center">联系电话</div>
                                            <div field="email" headeralign="center" align="center">邮箱</div>
                                            <div field="qq" headeralign="center" align="center">QQ</div>
                                            <div field="address" width="200" headeralign="center">地址</div>
                                            <div field="memo" width="200" headeralign="center">备注</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div region="center" title="联系人更新记录">
                                <div class="mini-datagrid" id="grid9" style="width:100%;height:100%;" pagesize="15"
                                     url="/work/clientInfo/getClientLinkersUpdateRecord?ClientID=${ClientID}"
                                     autoload="false" ondrawcell="drawCell">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div headeralign="center" align="center" field="actionType"
                                             width="100">操作类型
                                        </div>
                                        <div headeralign="center" field="actionTime" width="100" dataType="date"
                                             dateFormat="yyyy-MM-dd HH:m:ss">操作时间
                                        </div>
                                        <div headeralign="center" align="center" field="actionMan"
                                             width="100" type="treeselectcolumn">操作人员
                                            <input property="editor" class="mini-treeselect"
                                                   url="/systems/dep/getAllLoginUsersByDep" valueField="FID"
                                                   parentField="PID" textField="Name" resultAsTree="false"/>
                                        </div>
                                        <div headeralign="center" align="left" field="UAction" width="700">更新内容</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div name="PantentTab" title="专利清单">
                        <div class="mini-toolbar" id="mainToolbar1"
                             style="text-align:right;padding-right:20px; border: none; background: none;">
                            <a class="mini-button mini-button-info" onclick="doExport3">导出专利清单</a>
                            <a class="mini-button mini-button-primary" onclick="sendZhuanliEmail">发送专利清单</a>
                        </div>
                        <div class="mini-fit">
                            <div class="mini-datagrid" id="grid3" style="width:100%;height:100%;" autoload="false"
                                 multiselect="true" frozenstartcolumn="0" frozenendcolumn="7" pagesize="100"
                                 sizelist="[5,10,20,50,100,150,200]" sortfield="SHENQINGH" sortorder="desc">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div type="checkcolumn"></div>
                                    <div field="JIAJI" width="60" headeralign="center" type="comboboxcolumn">是否加急
                                        <input property="editor" class="mini-combobox" data="[{id:0,text:'否'},{id:1,text:'是'}]"/>
                                    </div>
                                    <div field="JIEAN" width="60" headeralign="center" type="comboboxcolumn">是否结案
                                        <input property="editor" class="mini-combobox" data="[{id:0,text:'否'},{id:1,text:'是'}]"/>
                                    </div>
                                    <div field="SHENQINGRXM" width="200" headeralign="center">专利申请人</div>
                                    <div field="SHENQINGH" width="100" headeralign="center">专利申请号</div>
                                    <div field="SHENQINGLX" width="60" headeralign="center" type="comboboxcolumn">
                                        专利类型
                                        <input property="editor" class="mini-combobox" data="zhuanlxs"/>
                                    </div>
                                    <div field="FAMINGMC" width="200" headeralign="center" renderer="onZhanlihaoZhuangtai">专利名称
                                    </div>
                                    <div field="ANJIANYWZT" width="100" headeralign="center">专利状态</div>
                                    <div field="FAMINGRXM" width="200" headeralign="center">发明人</div>
                                    <div field="SHENQINGR" width="120" headeralign="center" datatype="date" dateformat="yyyy-MM-dd">申请日期</div>
                                    <div field="NEIBUBH" width="200" headeralign="center">内部编号</div>
                                    <div field="KH" width="150" headeralign="center">所属客户</div>
                                    <div field="XS" width="150" headeralign="center">销售人员</div>
                                    <div field="DL" width="150" headeralign="center">代理责任人</div>
                                    <div field="LC" width="150" headeralign="center">流程责任人</div>
                                    <div field="DAILIJGMC" width="250" headeralign="center">代理机构</div>
                                    <div field="MEMO" width="100" headeralign="center">备注</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div name="FeeTab" title="费用监控">
                        <div class="mini-toolbar" id="mainToolbar1"
                             style="text-align:right;padding-right:20px; border: none; background: none;">
                            <a class="mini-button mini-button-info" onclick="doExport4">导出费用清单</a>
                            <a class="mini-button mini-button-danger" onclick="doExportYear">导出代缴费清单</a>
                            <a class="mini-button mini-button-primary" onclick="doSendYear">发送代缴费清单</a>
                        </div>
                        <div class="mini-fit" id="fitt">
                            <div id="grid4" class="mini-datagrid" style="width: 100%; height: 100%;"
                                 frozenstartcolumn="0" frozenendcolumn="8" sortorder="asc" sortfield="shenqingh"
                                 ondrawcell="onDraw" allowresize="true" url="" multiselect="true"
                                 pagesize="20"
                                 sizelist="[5,10,20,50,100]" autoload="false">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div type="checkcolumn"></div>
                                    <div field="JIAOFEIR" name="jiaofeir" width="100" allowsort="true"
                                         headeralign="center" align="center" dataType="date" dateFormat="yyyy-MM-dd">
                                        缴纳期限
                                    </div>
                                    <div field="FEENAME" name="feeName" width="180" allowsort="true" headeralign="center">
                                        费用项目
                                    </div>
                                    <div field="MONEY" name="money" width="90" allowsort="true" headeralign="center"
                                         align="right">
                                        金额
                                    </div>
                                    <div field="XMONEY" name="XMONEY" width="90" allowsort="true" headeralign="center"
                                         align="right">
                                        人工校对金额
                                    </div>
                                    <div field="SXMONEY" name="SXMONEY" width="90" allowsort="true" headeralign="center"
                                         align="right" dataType="float">
                                        手续费
                                    </div>
                                    <div field="NeedPayFor" width="80" allowsort="true" headeralign="center" align="center"
                                         renderer="onStateRenderer">
                                        是否需缴费
                                    </div>
                                    <div field="SHENQINGRXM" width="200" allowsort="true" headeralign="center">
                                        专利申请人
                                    </div>
                                    <div field="SHENQINGH" name="shenqingh" width="120" allowsort="true" headeralign="center">
                                        专利申请号
                                    </div>
                                    <div field="SHENQINGR" name="shenqingr" width="80" allowsort="true" headeralign="center"
                                         datatype="date" dateformat="yyyy-MM-dd">
                                        申请日
                                    </div>
                                    <div field="FAMINGMC" name="FAMINGMC" width="200" allowsort="true" headeralign="center">
                                        专利名称
                                    </div>
                                    <div field="SHENQINGLX" name="SHENQINGLX" width="90" headeralign="center"
                                         type="comboboxcolumn">
                                        专利类型
                                        <input class="mini-combobox" property="editor" data="types"/>
                                    </div>
                                    <div field="ANJIANYWZT" width="210" allowsort="true" headeralign="center">
                                        专利法律状态
                                    </div>
                                    <div field="FAMINGRXM" width="130" allowsort="true" headeralign="center">
                                        专利发明人
                                    </div>
                                    <div width="240" allowsort="true" headeralign="center" field="NEIBUBH">内部编号</div>
                                    <div width="120" headeralign="center" field="KH">归属客户</div>
                                    <div width="100" headeralign="center" field="YW">销售维护人</div>
                                    <div width="100" headeralign="center" field="DL">代理责任人</div>
                                    <div width="100" headeralign="center" field="LC">流程责任人</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div name="LoginTab" title="登录记录">
                        <div class="mini-fit">
                            <div class="mini-datagrid" id="grid0" style="width:100%;height:100%;" pagesize="15"
                                 sizelist="[5,10,20,50,100,150,200]"
                                 url="/work/clientInfo/GetLoginClientReords?ClientID=${ClientID}"
                                 autoload="false">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div headeralign="center" align="center" field="LoginTime" width="140">登录时间</div>
                                    <div headeralign="center" field="IPAddress" width="120">登录ip</div>
                                    <div field="CityAddrees" width="480" headeralign="center">登录地址</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div name="DLFTab" title="代理费对账">
                        <div class="mini-fit">
                            <div class="mini-datagrid" id="grid5" style="width:100%;height:100%;" pagesize="15"
                                 sizelist="[5,10,20,50,100]" url="" autoload="false" showSummaryRow="true"
                                 ondrawsummarycell="onDrawSummaryCellDLF" onload="onDaiGridLoad">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div header="交单信息" headerAlign="center">
                                        <div property="columns">
                                            <div field="SignTime" headeralign="center" width="120" datatype="date"
                                                 dateformat="yyyy-MM-dd" align="center">交单日期
                                            </div>
                                            <div field="Nums" headeralign="center" width="200" align="center">业务类型</div>
                                            <div field="DaiMoney" headeralign="center" align="center">业务金额</div>
                                        </div>
                                    </div>
                                    <div header="销售回款信息" headerAlign="center">
                                        <div property="columns">
                                            <div field="InMoneyText" headeralign="center" width="120" align="center">款项描述</div>
                                            <div field="InMoneyTime" headeralign="center" datatype="date"
                                                 dateformat="yyyy-MM-dd" align="center">到账日期
                                            </div>
                                            <div field="InMoney" headeralign="center">到账金额</div>
                                        </div>
                                    </div>
                                    <div header="财务退款信息" headerAlign="center">
                                        <div property="columns">
                                            <div field="OutMoneyText" headeralign="center" width="120" align="center">退款原因</div>
                                            <div field="OutMoneyTime" headeralign="center" datatype="date"
                                                 dateformat="yyyy-MM-dd" align="center">退款日期
                                            </div>
                                            <div field="OutMoney" headeralign="center">退款金额</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div name="GFTab" title="官费对账">
                        <div class="mini-fit">
                            <div class="mini-datagrid" id="grid6" style="width:100%;height:100%;" pagesize="15"
                                 sizelist="[5,10,20,50,100,150,200]" url="" autoload="false" showSummaryRow="true"
                                 ondrawsummarycell="onDrawSummaryCellGF" onload="onDaiGridLoad">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div header="交单信息" headerAlign="center">
                                        <div property="columns">
                                            <div field="SignTime" headeralign="center" width="120" datatype="date"
                                                 dateformat="yyyy-MM-dd" align="center">交单日期
                                            </div>
                                            <div field="Nums" headeralign="center" width="200" align="center">业务类型</div>
                                            <div field="GuanMoney" headeralign="center" align="center">业务金额</div>
                                        </div>
                                    </div>
                                    <div header="销售回款信息" headerAlign="center">
                                        <div property="columns">
                                            <div field="InMoneyText" headeralign="center" width="120" align="center">款项描述</div>
                                            <div field="InMoneyTime" headeralign="center" datatype="date"
                                                 dateformat="yyyy-MM-dd" align="center">到账日期
                                            </div>
                                            <div field="InMoney" headeralign="center">到账金额</div>
                                        </div>
                                    </div>
                                    <div header="财务退款信息" headerAlign="center">
                                        <div property="columns">
                                            <div field="OutMoneyText" headeralign="center" width="120" align="center">退款原因</div>
                                            <div field="OutMoneyTime" headeralign="center" datatype="date"
                                                 dateformat="yyyy-MM-dd" align="center">退款日期
                                            </div>
                                            <div field="OutMoney" headeralign="center">退款金额</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div name="TickTab" title="开票信息">
                        <div class="mini-fit">
                            <div class="mini-datagrid" id="grid7" style="width:100%;height:100%;" pagesize="15"
                                 sizelist="[5,10,20,50,100,150,200]" url="" autoload="false" showSummaryRow="true">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div field="ReceiptNumber" headeralign="center">发票号</div>
                                    <div field="Amount" headeralign="center">金额</div>
                                    <div field="Applicant" width="80" headeralign="center" type="treeselectcolumn"
                                         allowsort="true">申请人
                                        <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                               textField="Name" url="/systems/dep/getAllLoginUsersByDep"
                                               id="Applicant" style="width: 98%;" required="true" resultAsTree="false"/>
                                    </div>
                                    <div headeralign="center" field="AddTime" width="80" datatype="date"
                                         dateformat="yyyy-MM-dd">申请日期
                                    </div>
                                    <div field="InvoiceType" headerAlign="center" align="center" type="comboboxcolumn">
                                        发票类型
                                        <input property="editor" class="mini-combobox" url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=1"
                                               valueFromSelect="true" multiSelect="false" valueField="id" textField="text"
                                               autoload="true" required="true" style="width:100%"/>
                                    </div>
                                    <div field="ProjectName" headerAlign="center" align="center" type="comboboxcolumn">
                                        项目名称
                                        <input property="editor" class="mini-combobox" url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=3"
                                               valueFromSelect="true" multiSelect="false" valueField="id" textField="text"
                                               autoload="true" required="true" style="width:100%"/>
                                    </div>
                                    <div field="TickCompany" headerAlign="center" align="center" type="comboboxcolumn">
                                        项目名称
                                        <input property="editor" class="mini-combobox"  url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=4"
                                               valueFromSelect="true" multiSelect="false" valueField="id" textField="text"
                                               autoload="true" required="true" style="width:100%"/>
                                    </div>

                                    <div field="IsSend" width="60" headerAlign="center" type="comboboxcolumn" allowsort="true">
                                        是否寄出
                                        <div property="editor" class="mini-combobox" data="IsSend"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="selectExportColumn3" class="mini-window" title="选择导出字段" style="width:420px;height:250px;display: none"
         showshadow="true" showfooter="true"
         footerstyle="text-align: right;margin-right: 25px;" allowresize="true" allowdrag="true">
        <div property="footer">
            <a class="mini-button mini-button-success" href="javascript:doExportZhuanliXls();">确定</a>
            <a class="mini-button mini-button-danger" href="javascript:cancelSelectExportColumn3();">取消</a>
            <a class="mini-button mini-button-info" href="javascript:opsSelectExportColumn3();">反选</a>
        </div>
    </div>
    <div id="selectExportColumn4" class="mini-window" title="选择导出字段" style="width:420px;height:250px;display: none"
         showshadow="true" showfooter="true"
         footerstyle="text-align: right;margin-right: 25px;" allowresize="true" allowdrag="true">
        <div property="footer">
            <a class="mini-button mini-button-success" href="javascript:reqExoprt('4','费用清单','费用清单.xls');">确定</a>
            <a class="mini-button mini-button-danger" href="javascript:cancelSelectExportColumn4();">取消</a>
            <a class="mini-button mini-button-info" href="javascript:opsSelectExportColumn4();">反选</a>
        </div>
    </div>
    <form id="ExportForm" style="display:none" method="post" action="/work/patentInfo/exportExcel">
        <input class="mini-hidden" id="Data" name="Data"/>
    </form>

    <div id="editWindow1" class="mini-window" title="跟进记录" showmodal="true"
         allowresize="true" allowdrag="true" style="width:800px;display: none">
        <div id="editform1" class="form">
            <table style="width:100%;">
                <tr>
                    <td>内容</td>
                    <td colspan="7">
                        <textarea class="mini-textarea" style="width:98%;height:50px" name="record"
                                  required="true"></textarea>
                        <input class="mini-hidden" name="createTime"/>
                        <input class="mini-hidden" name="id"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="text-align:center">
                        <button class="mini-button" style="width:80px" id="SaveForm1" onclick="SaveRecord">保存</button>
                        <button class="mini-button" style="width:80px;" id="CancelForm1" onclick="cancelRow1">取消
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="editWindow2" class="mini-window" title="联系人信息" showmodal="true" allowresize="true" allowdrag="true"
         style="width:1100px;display:none">
        <div id="editform2" class="form">
            <table style="width:100%;" class="layui-table">
                <tr>
                    <td>联系人类型：</td>
                    <td>
                        <input class="mini-combobox" id="ctype" name="ctype" style="width: 98%;" required="true"
                               valuefield="text" url="/systems/dict/getByDtId?dtId=24"/>
                        <input class="mini-hidden" id="linkID" name="linkID"/>
                    </td>
                    <td>联系人姓名：</td>
                    <td>
                        <input class="mini-textbox" id="linkMan" name="linkMan" style="width: 98%;" required="true"/>
                    </td>
                    <td>手机号码：</td>
                    <td>
                        <input class="mini-textbox" id="mobile" name="mobile" style="width: 98%;" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>邮箱</td>
                    <td>
                        <input class="mini-textbox" id="email" name="email" style="width: 98%;"/>
                    </td>
                    <td>QQ：</td>
                    <td>
                        <input class="mini-textbox" id="qq" name="qq" style="width: 98%;"/>
                    </td>
                    <td>微信：</td>
                    <td>
                        <input class="mini-textbox" id="wx" name="wx" style="width: 98%;"/>
                    </td>
                </tr>
                <tr>
                    <td>座机号码：</td>
                    <td>
                        <input class="mini-textbox" id="linkPhone" name="linkPhone" style="width: 98%;" required="true"/>
                    </td>
                    <td>传真号码：</td>
                    <td>
                        <input class="mini-textbox" id="fax" name="fax" style="width: 98%;"/>
                    </td>
                    <td>在职状态</td>
                    <td>
                        <input class="mini-combobox" id="position" name="position" style="width: 98%;" data="[{id:1,
                        text:'在职'},{id:2,text:'离职'}]" required="true" value="1"/>
                    </td>
                </tr>
                <tr>
                    <td>联系地址</td>
                    <td colspan="3">
                        <input class="mini-textbox" id="address" name="address" style="width: 100%;" required="true"/>
                    </td>
                    <td>邮编：</td>
                    <td>
                        <input class="mini-textbox" id="postCode" name="postCode" style="width: 98%;"/>
                    </td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="6">
                        <textarea class="mini-textarea" style="width:100%;height:60px" name="memo" id="Memo"></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="6" style="text-align:center">
                        <button class="mini-button mini-button-primary" style="width:120px" id="SaveForm2" onclick="SaveLink">确定</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button class="mini-button mini-button-danger" style="width:120px;" id="CancelForm2"
                                onclick="cancelRow2">取消
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div style="display: none" class="mini-window" width="600" height="480" id="imgWindow" showFooter="true"
         showToolbar="true" title="粘贴至剪贴板图片">
        <div property="toolbar" style="padding:5px;">
            跟进内容: <input class="mini-textbox" style="width:90%" name="FMemo"/>
            <input class="mini-hidden" name="imageData"/>
            <input class="mini-hidden" name="FFID"/>
        </div>
        <div class="mini-fit">
            <img src="" id="myPicture" style="width:100%;height:100%" alt="复制图片并用Ctrl+V进行粘剪"/>
        </div>
        <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
            <button class="mini-button mini-button-success" onclick="saveImage()">确认保存</button>
            &nbsp;&nbsp;&nbsp;
            <button class="mini-button mini-button-danger">消取关闭</button>
        </div>
    </div>
    <script type="text/javascript" src="/js/work/feeItem/FeeCommon.js"></script>
    <script type="text/javascript">
        var DLFPrice = 0;
        var AgencyFee = 0;
        var AgencyFeeAmount = 0;

        var GFPrice = 0;
        var OfficalFee = 0;
        var OfficalFeeAmount = 0;

        var data = "";
        var Name = '';

        mini.parse();
        var types = [
            {id: 1, text: '工矿企业'},
            {id: 3, text: '事业单位'},
            {id: 4, text: '大专院校'},
            {id: 2, text: '个人'}
        ];
        var mode = "${Mode}";
        var AllClientInfo = ${AllClientInfo};
        var zhuanlxs = [
            {id: 0, text: '发明专利'}, {id: 1, text: '新型专利'}, {id: 2, text: '外观专利'}
        ];

        var formData = ${LoadData};
        var toolbar = mini.get('#mainToolbar');
        //联系人信息
        var grid = mini.get('grid2');
        var currentTabTitle = '跟进记录';

        $(function () {
            var tabs = mini.get("tabs1");
            var form = new mini.Form('#clientForm');
            var clientLinkersform = new mini.Form('#editWindow2');
            $("#ClientEdit_Save").click(function () {
                var clientId = mini.get('#clientID').getValue() || 0;
                var Data = form.getData();
                var grid2 = mini.get('grid2');
                form.validate();
                if (form.isValid()) {
                    if (mode == "Add") {
                        var ClientName = mini.get('name').getValue() || '';
                        for (var p in AllClientInfo) {
                            if (ClientName == AllClientInfo[p].Name) {
                                mini.alert('客户名称已存在，清选择编辑客户或者重新输入客户名称！');
                                return;
                            }
                        }
                    }
                    var md = mode;
                    var linkDatas = grid2.getChanges();
                    if (!clientId && linkDatas.length == 0) { //保存新客户时必须有联系人
                        mini.alert('保存客户时必须有联系人');
                        return;
                    }
                    form.loading("保存中......");
                    var linkData = clientLinkersform.getData();
                    linkData.ClientID = clientId;
                    var arg = {
                        client: mini.encode(Data),
                        clientLinkers: mini.encode(linkData),
                        mode: mini.encode(md)
                    };

                    $.post("/work/clientInfo/save", arg,
                        function (result) {
                            var res = mini.decode(result);
                            if (res['success']) {
                                mini.alert('客户信息保存成功', '系统提示', function () {
                                    var returnData = result.data || {};
                                    form.setData(returnData.client);
                                    clientLinkersform.setData(returnData.clientLinkers);
                                });
                            } else {
                                mini.alert(res.message || "保存失败，请稍候重试!");
                            }
                            form.unmask();
                        }
                    );
                }
            });

            var SMan = mini.get('signMan').getValue();
            tabs.on("activechanged", function (e) {
                var clientId = mini.get('#clientID').getValue();
                var clientName = mini.get('name').value;
                currentTabTitle = e.tab.title;
                if (e.tab.title == "跟进记录") {
                    var grid2 = mini.get('grid1');
                    if (grid2.getUrl() == '' && clientId) {
                        grid2.setUrl('/work/clientInfo/getFollowRecords');
                        grid2.load({ClientID: clientId});
                    }
                    if (mode == "look") {
                        var con = $("[name='followRecordTool']");
                        if (con) con.show();
                    }
                }
                if (e.tab.title == "联系人信息") {
                    var grid2 = mini.get('grid2');
                    if (grid2.getUrl() == '' && clientId) {
                        grid2.setUrl('/work/clientInfo/getClientLinkers');
                        grid2.load({ClientID: clientId});

                        var grid9 = mini.get('#grid9');
                        grid9.load({"ClientID": clientId});
                    }
                    if (mode == "look") {
                        var con1 = $("[name='linkManTool']");
                        if (con1) con1.show();
                    }
                }
                if (e.tab.title == "专利清单") {
                    var grid3 = mini.get('grid3');
                    if (clientId) {
                        grid3.setUrl('/work/patentInfo/getData');
                        var kc = [{"field": "NEIBUBH", "value": clientName, "oper": "LIKE"}];
                        kc.push({"field": "NEIBUBH", "value": clientName.toString().toUpperCase(), "oper": "LIKE"})
                        grid3.load({
                            'Query': mini.encode(kc), 'sortField': grid3.getSortField(), 'sortOrder': grid3
                                .getSortOrder()
                        });
                    }
                }
                if (e.tab.title == "费用监控") {
                    var grid4 = mini.get('grid4');
                    if (grid4.getUrl() == '' && clientId) {
                        var kc = [{"field": "NEIBUBH", "value": clientName, "oper": "LIKE"}];
                        kc.push({"field": "NEIBUBH", "value": clientName.toString().toUpperCase(), "oper": "LIKE"})
                        //grid4.setUrl('/work/feeItem/getData');
                        grid4.setUrl('/watch/applyList/clientFYJK')
                        grid4.load({
                            'Query': mini.encode(kc),
                            'sortField': grid4.getSortField(),
                            'sortOrder': grid4.getSortOrder()
                        });
                    }
                }
                if (e.tab.title == "登录记录") {
                    var grid0 = mini.get('grid0');
                    grid0.reload();
                }
                if (e.tab.title == "代理费对账") {
                    var grid5 = mini.get('grid5');
                    if (grid5.getUrl() == '' && clientId) {
                        grid5.setUrl('/work/clientInfo/GetDLF');
                        grid5.load({ClientID: clientId, SignMan: SMan});
                    }
                }
                if (e.tab.title == "官费对账") {
                    var grid5 = mini.get('grid6');
                    if (grid5.getUrl() == '' && clientId) {
                        grid5.setUrl('/work/clientInfo/GetGF');
                        grid5.load({ClientID: clientId, SignMan: SMan});
                    }
                }
                if (e.tab.title == "开票信息") {
                    var grid7 = mini.get('grid7');
                    if (grid7.getUrl() == '' && clientId) {
                        grid7.setUrl('/work/clientInfo/GetInvoice');
                        grid7.load({
                            ClientID: clientId,
                            'sortField': grid7.getSortField(),
                            'sortOrder': grid7.getSortOrder()
                        });
                    }
                }
                if (e.tab.title == "联系人更新记录") {

                }
            });

            if (mode == "Add") {
                var SignMan = "${SignMan}";
                var SignManID = "${SignManID}";
                var SignDate = "${SignDate}";
                mini.get('signMan').setText(SignMan);
                mini.get('signMan').setValue(SignManID);
                mini.get('signDate').setValue(SignDate);
            }
            tabs.show();
           $('#clientForm').show();
        });
        function linkerDraw(e){
            var row=e.record;
            if(row){
                var position=parseInt(row["position"] || 1);
                if(position==2){
                    e.rowStyle="color:red";
                }
            }
        }
        function getReClientName(Data) {
            var arg = {
                Data: mini.encode(Data)
            };
            var url = '/work/clientInfo/findNameByName';
            $.post(url, arg,
                function (text) {
                    var res = mini.decode(text);
                    if (res.success) {
                        data = res.data || {};
                    } else {
                        mini.alert(res.Message);
                    }
                }
            );
            return data;
        }

        if (mode == "look") {
            $("#mainToolbar").remove();
            $("#mainToolbar1").hide();
            $("#mainToolbar2").hide();
            var mainForm = new mini.Form('#clientForm');
            mainForm.setEnabled(false);
            if (role == "CW") {
                var mainTab = mini.get("tabs1");
                var tabs = mainTab.getTabs();
                for (var i = 0; i < tabs.length; i++) {
                    var tab = tabs[i];
                    var name = tab.name;
                    if (name == "DLFTab" || name == "GFTab") {
                        mainTab.updateTab(tab, {visible: true})
                    } else mainTab.updateTab(tab, {visible: false});
                }
            }

        }
        if (mode == "Edit") {
            mini.get("name").disable();
            function bb(){
                var form = new mini.Form('#clientForm');
                formData.signDate = new Date(formData.signDate);
                formData.createTime = new Date(formData.createTime);
                form.setData(formData);
            }
            function gg(){
                var grid1 = mini.get('grid1');
                if (grid1.getUrl() == '') {
                    grid1.setUrl('/work/clientInfo/getFollowRecords');
                    grid1.load({ClientID: formData.clientID});
                }
            }
            setTimeout(bb(),50);
            setTimeout(gg(),100);
        } else {
            if (formData != null && formData != undefined) {
                var form = new mini.Form('#clientForm');
                formData.Type = 1;
                formData.cootype = mini.get('#cootype').getValue();
                form.setData(formData);
            }
        }
        function UpdateFollowRecordWindow() {
            var data = mini.get("grid1").getSelected();
            if (!data) {
                mini.alert('请选择要修改记录');
                return;
            }
            if ((new Date() - new Date(data.CreateTime)) / 1000 / 60 / 60 / 24 > 2) {
                mini.alert('只能修改两天以内的跟进记录!');
                return;
            }
            data.UpdateTime = new Date();
            var form = new mini.Form("#editform1");
            var editWindow = mini.get("editWindow1");
            form.setData(data);
            editWindow.show();
        }

        function AddFollowRecordWindow() {
            var clientId = mini.get('clientID').getValue();
            if (!clientId) {
                mini.alert('请先保存客户资料!');
                return;
            }
            var form = new mini.Form("#editform1");
            var editWindow = mini.get("editWindow1");
            form.setData({'createTime': new Date()});
            editWindow.show();
        }

        function UpdateClinetLinkWindow() {
            var data = mini.get("grid2").getSelected();
            if (!data) {
                mini.alert('请选择要修改记录');
                return;
            }
            var form = new mini.Form("#editform2");
            var editWindow = mini.get("editWindow2");
            form.setData(data);
            editWindow.show();
        }

        function AddClinetLinkWindow() {
            var clientId = mini.get('clientID').getValue();
            if (clientId) {
                mini.get('SaveForm2').setText('保存')
            } else {
                mini.get('SaveForm2').setText('添加')
            }
            var form = new mini.Form("#editform2");
            var editWindow = mini.get("editWindow2");
            form.setData({});
            editWindow.show();
        }

        function SaveRecord() {
            var clientId = mini.get('clientID').getValue();
            var form = new mini.Form('#editWindow1');
            var editWindow = mini.get("editWindow1");
            form.validate();
            if (form.isValid()) {
                var data = form.getData();
                data.ClientID = clientId;
                var grid = mini.get('grid1');
                if (clientId) {
                    form.loading("保存中");
                    $.post(
                        "/work/clientInfo/saveFollowRecord",
                        {Data: mini.encode(data)},
                        function (res) {
                            if (res.success) {
                                grid.reload();
                            } else {
                                mini.alert(res.Message);
                            }
                            form.unmask();
                            editWindow.hide();
                        }
                    );
                } else {
                    var row = {};
                    if (data.id) {
                        row = grid.getSelected();
                    } else {
                        grid.addRow(row, 0);
                    }
                    grid.updateRow(row, data);
                    editWindow.hide();
                }
            }
        }

        function SaveLink() {
            var clientId = mini.get('#clientID').getValue();
            var form = new mini.Form('#editWindow2');
            var editWindow = mini.get("editWindow2");
            form.validate();
            if (form.isValid()) {
                var data = form.getData();
                data.ClientID = clientId;
                var grid = mini.get('grid2');
                if (!clientId) {
                    var row = {};
                    if (data.LinkID) {
                        row = grid.getSelected();
                    } else {
                        grid.addRow(row, 0);
                    }
                    grid.updateRow(row, data);
                    editWindow.hide();
                } else {
                    form.loading("保存中........");
                    var url = "/work/clientInfo/saveClientLinkers";
                    $.post(url, {Data: mini.encode(data)}, function (res) {
                        form.unmask();
                        if (res.success) {
                            grid.reload();
                            var gg = mini.get('grid9');
                            if (gg) gg.reload();
                            editWindow.hide();
                        } else {
                            mini.alert(res.message || "保存失败，请稍候重试。");
                        }
                    });
                }
            }
        }

        function doExport3(e) {
            var grid3 = mini.get('grid3');
            var excel = new excelData(grid3);
            excel.export("专利清单列表.xls");
        }

        //关闭导出窗口
        function cancelSelectExportColumn3() {
            mini.get('selectExportColumn3').hide();
        }

        //反选
        function opsSelectExportColumn3() {
            $("#selectExportColumn3 input[type='checkbox']").each(function (i, n) {
                if ($(n)[0].checked == true) $(n)[0].checked = false;
                else $(n)[0].checked = true;
            });
        }

        function doExportYear() {
            var grid4 = mini.get('grid4');
            var rows = grid4.getSelecteds();
            if (rows.length > 0) {
                var excel = new complexExcelData(grid4);
                excel.export('OneYear', '专利申请代缴费清单.xls');
            } else mini.alert('请选择要代缴的年费清单项目!');
        }

        function doSendYear() {
            function p() {
                var FilePath = "";
                var postResult;
                var grid4 = mini.get('grid4');
                if (postResult == null) postResult = inits(grid4);
                if (postResult) {
                    var arg = {
                        data: encodeURIComponent(mini.encode(postResult)),
                        // fileName:encodeURI('专利申请代缴费清单.xls'),
                        code: 'OneYear'
                    };
                    var url = "/work/clientInfo/downLoadDJFQD";
                    $.post(url, arg,
                        function (text) {
                            var res = mini.decode(text);
                            if (res.success) {
                                FilePath = res.message;
                                if (FilePath != "") {
                                    var rows = grid4.getSelecteds();
                                    if (rows.length == 0) {
                                        alert('至少选择一条要发送邮件的记录。');
                                        return;
                                    }

                                    var rows = grid4.getSelecteds();
                                    if (rows.length > 0) {
                                        mini.open({
                                            //url: '/common/email/index?Code=djf&Type=代缴费清单',
                                            url: '/common/email/index?Code=djf',
                                            width: 1000,
                                            height: 580,
                                            title: '发送邮件',
                                            showModal: true,
                                            allowResize: false,
                                            onload: function () {
                                                var iframe = this.getIFrameEl();
                                                var documentType = "代缴费清单";
                                                iframe.contentWindow.getContent(documentType);
                                                var data = [{id: FilePath, text: "代缴费清单"}];
                                                iframe.contentWindow.addAttachment(data);
                                                iframe.contentWindow.setSubject('年度缴费清单,详见附件');
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    );
                }
            }

            var grid4 = mini.get('grid4');
            var rows = grid4.getSelecteds();
            if (rows.length > 0) {
                p();
            } else mini.alert('请选择要代缴的年费清单项目!');
        }

        function getTypeByShenqingh(value) {
            if (value.length == 13 && value.substring(4, 5) == '1') return '发明专利';
            else if (value.length == 13 && value.substring(4, 5) == '8') return 'PCT发明专利';
            else if (value.length == 13 && value.substring(4, 5) == '2') return '实用新型';
            else if (value.length == 13 && value.substring(4, 5) == '9') return 'PCT实用新型';
            else if (value.length == 13 && value.substring(4, 5) == '3') return '外观设计';
            else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '1') return '发明专利';
            else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '8') return 'PCT发明专利';
            else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '2') return '实用新型';
            else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '9') return 'PCT实用新型';
            else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '3') return '外观设计';
            return "";
        }

        function doExport4(e) {
            var grid4 = mini.get('grid4');
            var excel = new excelData(grid4);
            excel.export("费用监控列表.xls");
        }

        //关闭导出窗口
        function cancelSelectExportColumn4() {
            mini.get('selectExportColumn4').hide();
        }

        //反选
        function opsSelectExportColumn4() {
            $("#selectExportColumn4 input[type='checkbox']").each(function (i, n) {
                if ($(n)[0].checked == true) $(n)[0].checked = false;
                else $(n)[0].checked = true;
            });
        }

        function sendZhuanliEmail() {
            var grid3 = mini.get('grid3');
            var rows = grid3.getSelecteds();
            if (rows.length == 0) {
                alert('至少选择一条要发送邮件的记录。');
                return;
            }
            var data = mini.clone(rows);
            for (var i = 0; i < data.length; i++) {
                var d = data[i];
                var shenqingh = d["SHENQINGH"];
                var lx = getTypeByShenqingh(shenqingh);
                d["SHENQINGLX"] = lx;
                delete d.MEMO;

                delete d.NEIBUBH;
                delete d.DAILIJGMC;
                delete d.JIEAN;
                delete d.JS;
                delete d.EDITMEMO;
                delete d.DIYIDLRXM;
                delete d.RowNum;
                delete d.SHENQINGBH;
                delete d.UploadPath;
                delete d.JIAJI;
                delete d.LC;
                delete d.KHID;
                delete d.KH;
                delete d.YW;
            }
            var fields = ['专利申请人', '专利申请号', '专利类型', '专利名称', '专利状态', '发明人', '申请日期'];
            var cols = [];
            var columns = grid3.getColumns();
            for (var i = 0; i < columns.length; i++) {
                var column = columns[i];
                var field = column.field;
                var header = column.header;
                if (header) {
                    //获取列名称的时候有空格，暂时还没找到原因
                    header = $.trim(header);
                    if (fields.indexOf(header) > -1) {
                        var width = parseInt(column.width || 80);
                        var p = {header: header, type: 'string', field: field, width: width};
                        cols.push(p);
                    }
                }
            }
            var arg = {
                Columns: cols,
                Rows: data,
                Title: '专利清单',
                FileName: '专利清单.xls',
                TableName: '',
                sortField: '',
                sortOrder: '',
                loadField: '',
                SubTitle: []
            }
            var GridExcelURL = "/work/clientInfo/getGridDataExcel";
            var ExcelPath = "";
            $.post(GridExcelURL, {
                data: mini.encode(data),
                columns: mini.encode(cols),
                filename: "专利清单.xlsx"
            }, function (res) {
                if (res.success) {
                    ExcelPath = res.message;

                    if (rows.length > 0) {
                        var vs = [];
                        for (var i = 0; i < rows.length; i++) {
                            var row = rows[i];
                            var r = {
                                Index: i + 1,
                                SHENQINGRXM: row["SHENQINGRXM"],
                                SHENQINGH: row["SHENQINGH"],
                                FAMINGMC: row["FAMINGMC"],
                                SHENQINGLX: getZLLXMC(row["SHENQINGLX"])
                            };
                            vs.push(r);
                        }
                        mini.open({
                            // url: '/common/email/index?Code=zlqd&Type=专利清单&rows='+encodeURI(mini.encode(vs)),
                            url: '/common/email/index?Code=zlqd',
                            width: 1000,
                            height: 580,
                            title: '发送邮件',
                            showModal: true,
                            allowResize: false,
                            onload: function () {
                                var iframe = this.getIFrameEl();
                                var documentType = "专利清单";
                                iframe.contentWindow.getContent(documentType, mini.encode(vs));
                                if (rows.length > 0) {
                                    var row = rows[0];
                                    var ppath = row["UploadPath"];
                                    var filename = getZLLXMC(row["SHENQINGLX"]);
                                    iframe.contentWindow.addAttachment([{id: ExcelPath, text: '专利清单.xlsx'}]);
                                    var title = row["FAMINGMC"] + "-" + filename;
                                    iframe.contentWindow.setSubject("专利清单资料,详见附件");
                                }
                                // else if (rows.length > 1) {
                                //     var codes = [];
                                //     for (var i = 0; i < rows.length; i++) {
                                //         var row = rows[i];
                                //         codes.push(row["SHENQINGBH"]);
                                //     }
                                //     var code = codes.join(",");
                                //     var url = '/common/email/getAllByCodes';
                                //     $.getJSON(url, { Code: code }, function (r) {
                                //         if (r.success) {
                                //             var ds = r.data || [];
                                //             iframe.contentWindow.addAttachment(ds);
                                //             iframe.contentWindow.setSubject(ds.length + "个通知书附件.zip");
                                //         }
                                //     })
                                // }
                            }
                        });
                    }
                }
            });

        }

        function getZLLXMC(SHENQINGLX) {
            var MC = "";
            switch (SHENQINGLX) {
                case 0:
                    MC = "发明专利";
                    break;
                case 1:
                    MC = "新型专利";
                    break;
                case 2:
                    MC = "外观专利";
                    break;
                default:
                    MC = "";
            }
            return MC;
        }

        function doExportZhuanliXls() {
            var grid3 = mini.get('grid3');
            var rows = grid3.getSelecteds();
            if (rows.length == 0) {
                alert('至少选择一条要导出的记录。');
                return;
            }
            var data = mini.clone(rows);
            for (var i = 0; i < data.length; i++) {
                var d = data[i];
                var shenqingh = d["SHENQINGH"];
                var lx = getTypeByShenqingh(shenqingh);
                d["SHENQINGLX"] = lx;
                delete d.MEMO;
            }
            var columns = [];
            $("#selectExportColumn3 input[type='checkbox']").each(function (i, n) {
                if ($(n)[0].checked == true) {
                    var header = $(n)[0].getAttribute('header')
                    var dType = $(n)[0].getAttribute('dType')
                    var field = $(n)[0].getAttribute('field')
                    var width = $(n)[0].getAttribute('width')
                    var p = {header: header, type: dType, field: field, width: width};
                    columns.push(p);
                }
            });
            var f = $('#ExportForm');
            var arg = {
                Columns: columns,
                Rows: data,
                Title: '专利清单',
                FileName: '专利清单.xls',
                TableName: '',
                sortField: '',
                sortOrder: '',
                loadField: '',
                SubTitle: []
            }
            if (f) {
                var ds = mini.encode(arg);
                $('#Data').val(ds);
                f[0].submit();
            }
            return false;
        }

        function reqExoprt(ColumnId, title, fileName) {
            var columns = [];
            $("#selectExportColumn" + ColumnId + " input[type='checkbox']").each(function (i, n) {
                if ($(n)[0].checked == true) {
                    var header = $(n)[0].getAttribute('header')
                    var dType = $(n)[0].getAttribute('dType')
                    var field = $(n)[0].getAttribute('field')
                    var width = $(n)[0].getAttribute('width')
                    var p = {header: header, type: dType, field: field, width: width};
                    columns.push(p);
                }
            });
            if (columns.length == 0) {
                mini.alert('请选择导出的列!');
                return;
            }
            var f = $('#ExportForm');
            var pgrid = mini.get('grid' + ColumnId);
            var rows = mini.clone(pgrid.getData());
            if (rows.length == 0) {
                mini.alert('没有发现可以导出的记录!');
                return;
            }
            var arg = {
                Columns: columns,
                Rows: rows,
                Title: title,
                FileName: fileName,
                TableName: '',
                sortField: '',
                sortOrder: '',
                loadField: '',
                SubTitle: []
            }
            var ds = encodeURIComponent(mini.encode(arg));
            var f = $('#ExportForm');
            $('#Data').val(ds);
            f[0].submit();
            return false;
        }

        function CreateTimeRenderer(e) {
            //if(e.value) return formatDate(e.value);
            return "";
        }

        function UpdateTimeRenderer(e) {
            //if(e.value && e.value.getFullYear()>1900) return formatDate(e.value);
            return "";
        }

        function onRowDblClick(e) {
            var row = e.row;
            var uId = row._uid;
            if (uId) {
                var button = $("." + uId + "_row");
                var status = button.attr('status') || "show";
                //if (status == "show") editRow(uId); else hideAllRowDetail();
            }
        }

        function hideAllRowDetail() {
            var rows = grid.getData();
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var uId = row._uid;
                var button = $("." + uId + "_row");
                button.attr('status', 'show');
                button.text('显示');
            }
            grid.hideAllRowDetail();
        }

        function cancelRow1() {
            var editWindow = mini.get("editWindow1");
            editWindow.hide();
        }

        function cancelRow2() {
            var editWindow = mini.get("editWindow2");
            editWindow.hide();
        }

        function drawCell(e) {
            var field = e.field;
            if (field == "UAction") {
                var upId = e.record["upID"];
                e.cellHtml = showChange(upId);
            }
        }

        function getRecordById(id) {
            var grid = mini.get('#grid9');
            var rows = grid.getData();
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var upId = row["upID"];
                if (upId == id) return mini.clone(row);
            }
            return null;
        }

        function showChange(id) {
            var record = getRecordById(id);
            if (!record) return;
            var fields = {
                "cType": "联系人类型",
                "linkMan": "联系人姓名",
                "mobile": "手机号码",
                "linkPhone": "座机号码",
                "qq": "QQ",
                wx: "微信",
                fax: "传真号码",
                "address": "联系地址",
                "postCode": "邮编"
            };
            var aObj = mini.decode(record["bobj"]);
            var bObj = mini.decode(record["aobj"]);
            var rows = [];
            var changeRows = [];
            for (var field in fields) {
                var title = fields[field];
                var aValue = aObj[field];
                var bValue = bObj[field];
                if ($.trim(aValue) == "" && $.trim(bValue) == "") continue;
                if (aValue == null || aValue == undefined) aValue = "空";
                if (bValue == null || bValue == undefined) bValue = "空";
                if (aValue != bValue) {
                    var tt = "<span style='color:blue'>" + title + "</span>" + "由:[<span style='color:green'>" + bValue + "</span>]更改为:[<span style='color:red'>" + aValue + "</span>]";
                    changeRows.push(tt);
                }
            }
            return changeRows.join("&nbsp;&nbsp;")
        }

        function onDaiGridLoad(e) {
            var grid = e.sender;
            var rows = grid.getData();
            if (rows.length > 0) {
                var firstRow = rows[0];
                var signTime = firstRow["SignTime"] || "";
                if (signTime.toString().indexOf("期初余额") > -1) {
                    var info = [
                        {rowIndex: 0, columnIndex: 1, rowSpan: 1, colSpan: 9}
                    ];
                    grid.mergeCells(info);
                }
            }
        }

        function onDrawSummaryCellDLF(e) {
            var result = e.result;
            var grid = e.sender;
            var rows = e.data;
            if (e.field == "Nums") {
                var jiaoMoney = 0;
                var inMoney = 0;
                var outMoney = 0;

                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var jiao = parseFloat(row["DaiMoney"] || 0);
                    var inM = parseFloat(row["InMoney"] || 0);
                    var outM = parseFloat(row["OutMoney"] || 0);
                    jiaoMoney += jiao;
                    inMoney += inM;
                    outMoney += outM;
                }
                var totalMoney = inMoney - jiaoMoney - outMoney;
                if (totalMoney > 0) e.cellHtml = "<div style='padding-top:10px;height:30px;color:green;font-size: 18px;" +
                    "'>" + "费用汇总: " + totalMoney + "元</div>";
                else e.cellHtml = "<div style='padding-top:10px;height:30px;color:red;font-size: 18px;'>" + "费用汇总: "
                    + totalMoney + "元</div>";
            }
        }

        function onDrawSummaryCellGF(e) {
            var result = e.result;
            var grid = e.sender;
            var rows = e.data;
            if (e.field == "Nums") {
                var jiaoMoney = 0;
                var inMoney = 0;
                var outMoney = 0;

                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var jiao = parseFloat(row["GuanMoney"] || 0);
                    var inM = parseFloat(row["InMoney"] || 0);
                    var outM = parseFloat(row["OutMoney"] || 0);
                    jiaoMoney += jiao;
                    inMoney += inM;
                    outMoney += outM;
                }
                var totalMoney = inMoney - jiaoMoney - outMoney;
                if (totalMoney > 0) e.cellHtml = "<div style='padding-top:10px;height:30px;color:green;font-size: 18px;" +
                    "'>" + "费用汇总: " + totalMoney + "元</div>";
                else e.cellHtml = "<div style='padding-top:10px;height:30px;color:red;font-size: 18px;'>" + "费用汇总: "
                    + totalMoney + "元</div>";
            }
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "status") {
                var now = (new Date()).Format("yyyy-MM-dd");
                var value = record['JIAOFEIR'];
                if (value < now) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                } else if (addDate(value, -30) <= now) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                } else if (addDate(value, -90) <= now) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                } else {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:yellow">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                }
            } else if (field == "Action") {
                var record = e.record;
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMEMO"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay'>查看</span>";
                }
                e.cellHtml = '<a href="#"  data-placement="bottomleft"  hCode="' + record["shenqingh"] + '" class="showCellTooltip" onclick="ShowMemo(' + "'" + record["shenqingh"] + "'," + "'" + record["zhuanlimc"] + "'" + ')">' + text + '</a>';
            } else if (field == "KH") {
                var clientId = record["KHID"];
                var val = e.value;
                if (clientId) {
                    e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            }
        }

        function onStateRenderer(e) {
            var value = e.value;
            if (value == '1') return '需缴费';
            if (value == '0') return '不需缴费';
            return "";
        }

        function inits(grid) {
            var rows = grid.getSelecteds();
            if (rows.length == 0) {
                mini.alert('请选择要导出年费记录!');
                return null;
            }
            var obj = ZLObject.parse(rows);
            return obj;
        }

        function singleItem() {
            this.INDEX = 0;
            this.SHENQINGH = "";
            this.ZHUANLIMC = "";
            this.ZHUANLILX = "";
            this.SHENQINGR = "";
            this.ANJIANYWZT = "";
            this.SHENQINGRXM = "";
            this.FAMINGRXM = "";
            this.YINGJIAOFYDM = "";
            this.MONEY = 0;
            this.SXMONEY = 0;
            this.SHOU = 0;
        }

        function ZLObject() {
            this.Rows = [];
            this.TOTAL = 0;
            this.TOTALSHOU = 0;
            this.TOTALGUAN = 0;
        }

        ZLObject.parse = function (rows) {
            var g = new ZLObject();
            var types = {0: '发明专利', 1: '新型专利', 2: '外观专利'};

            function parseSingle(row, index) {
                var item = new singleItem();
                item.INDEX = index;
                item.SHENQINGH = row.SHENQINGH;
                item.SHENQINGRXM = row.SHENQINGRXM;
                item.YINGJIAOFYDM = row.FEENAME;
                item.ZHUANLIMC = row.FAMINGMC;
                item.SHENQINGR = (mini.formatDate(row.SHENQINGR, 'yyyy-MM-dd') || "").toString().split('T')[0];
                item.MONEY = parseFloat(row.XMONEY || row.MONEY) || 0;
                item.ZHUANLILX = types[parseInt(row.SHENQINGLX)];
                item.SHENQINGRXM = row.SHENQINGRXM || "";
                item.ANJIANYWZT = row.ANJIANYWZT || "";
                item.SXMONEY = parseFloat(row.SXMONEY || 0);
                return item;
            }

            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var r = parseSingle(row, i + 1);
                g.Rows.push(r);
                g.TOTALGUAN += r.MONEY;
                g.TOTALSHOU += r.SXMONEY;
            }
            g.TOTAL = g.TOTALSHOU + g.TOTALGUAN;
            return g;
        }

        function showImageWindow(){
            var win=mini.get('imgWindow');
            $('#myPicture').attr('src',"");
            mini.getbyName('FMemo').setValue(null);
            if(win.getVisible()==false)win.show();
        }
        $(function(){
            $(document.body).on('paste',function(e){
                if(checkWeb()){
                    var clip=(e.clipboardData || window.clipboardData || window.event.clipboardData);
                    if(clip){
                        var items=clip.items;
                        var targetItem=null;
                        for(var i=0;i<items.length;i++){
                            var item=items[i];
                            var kind=item.kind;
                            var type=item.type;
                            if(kind=="file"){
                                if(type.startsWith("image")){
                                    targetItem=item;
                                    break;
                                }
                            }
                        }
                        if(targetItem==null){
                            return;
                        }
                        var data=targetItem.getAsFile().slice();
                        var isImg=(data && 1) ||-1;
                        if(isImg){
                            var fileReader=new FileReader();
                            fileReader.onload=function(event){
                                var bStr=event.target.result;
                                if(bStr){
                                    showImageWindow();
                                    $('#myPicture').attr('src',bStr);
                                    mini.getbyName('imageData').setValue(bStr);
                                }
                            }
                            fileReader.readAsDataURL(data);
                        }
                    }
                } else mini.showTips({content:'当前浏览器由于版本太低，不支持【Html5】特性，无法使用粘贴图片功能。'});
            });
        })
        function checkWeb(){
            var res=false;
            var pp=window.FileReader;
            if(pp) res=true; else return false;
            return res;
        }
        function saveImage(){
            var grid1=mini.get('#grid1')
            var obj={};
            obj.imageData=mini.getbyName('imageData').getValue() || "";
            obj.record=mini.getbyName('FMemo').getValue();
            obj.fid=mini.getbyName("FFID").getValue();
            obj.clientId="${ClientID}";
            if(!obj.imageData || obj.imageData.length<100){
                mini.alert('图片数据为空，不能进行保存!');
                return;
            }
            var iid=mini.loading('正在保存.......','保存图片');
            var url='/work/clientInfo/saveImageFollow';
            $.post(url,{Data:mini.encode(obj)},function(result){
                mini.hideMessageBox(iid);
                if(result.success){
                    mini.alert('保存成功!','系统提示',function(){
                        var win=mini.get('imgWindow');
                        if(win)win.hide();
                        grid1.reload();
                    });
                }
            })
        }
        function addImage(mId){
            if(checkWeb()){
                showImageWindow();
                if(mId){
                    if(typeof(mId)=="string"){
                        mini.getbyName('FFID').setValue(mId);
                    }
                }
            }else mini.alert('当前浏览器由于版本太低，不支持【Html5】特性，无法使用粘贴图片功能。');
        }
        function onFollowDraw(e){
            var field=e.field;
            var row=e.record;
            if(field=="Image"){
                var isImage=parseInt(row["imageData"] || 0);
                if(isImage==1){
                    var pId=row["fid"];
                    e.cellHtml += '<a  style="text-decoration: underline"  ' +
                        'href="javascript:viewImage(\'' + pId + '\')">查看</a>';
                    e.cellHtml += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a  style="text-decoration: underline"  ' +
                        'href="javascript:addImage(\'' + pId + '\')">添加</a>';
                }
            }
        }
        function viewImage(mId){
            var iid = mini.loading('正在加载图片数据.........');
            var url = '/work/addMemo/getImages';
            $.getJSON(url, {MID: mId}, function (result) {
                mini.hideMessageBox(iid);
                var isOK = parseInt(result.status);
                if (isOK == 1) {
                    window.parent.showImages(mini.encode(result));
                } else {
                    var msg = result.message || "无法加载通知书附件。";
                    layer.alert(msg);
                }
            });
        }
        function EditDefaultMail() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i=0;i<rows.length;i++) {
                ids.push(rows[i]["linkID"]);
            }
            if (ids.length == 0) {
                mini.alert("请选择要设置的默认邮箱！");
                return;
            }
            mini.confirm('确认设置为默认邮箱?', '系统提示', function(act) {
               if (act == 'ok') {
                   var url = '/work/clientInfo/editDefaultMail';
                   $.post(url, {IDS:mini.encode(ids),ClientID:${ClientID}},function (r) {
                       if (r['success']) {
                           mini.alert("设置默认邮箱成功！",'删除提示',function () {
                               grid.reload();
                           });

                       } else mini.alert("设置默认邮箱失败！");
                   })
               }
            });
        }

        function CannelDefaultMail() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i=0;i<rows.length;i++) {
                ids.push(rows[i]["linkID"]);
            }
            if (ids.length == 0) {
                mini.alert("请选择要取消的默认邮箱");
                return;
            }
            mini.confirm('确认取消默认邮箱?', '系统提示', function (act) {
                if (act == 'ok') {
                    var url = '/work/clientInfo/cannelDefaultMail';
                    $.post(url, {IDS:mini.encode(ids)}, function (r) {
                        if (r['success']) {
                            mini.alert("取消默认邮箱成功！",'系统提示',function () {
                                grid.reload();
                            });
                        }else mini.alert("取消默认邮箱失败！");
                    });
                }
            });
        }
    </script>
</@layout>