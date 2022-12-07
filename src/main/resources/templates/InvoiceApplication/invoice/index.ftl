<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/excelExportOther.js"></script>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }

        .ui-tooltip {
            max-width: 850px;
        }
    </style>
    <style type="text/css">
        .info1top ul {
            margin-top: -23px;
            list-style: none;
            margin-left: 20px;
        }

        .info1top ul li {
            float: left;
            margin-left: 8%;
            height: 41px;
            margin-top: -24px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info1top ul li {
                margin-left: 8%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info1top ul li {
                margin-left: 8%;
            }
        }

        @media screen and (max-width: 1374px) {
            .info1top ul li {
                margin-left: 8%;
            }
        }

        @media screen and (max-width: 1233px) {
            .info1top ul li {
                margin-left: 8%;
            }
        }

        .info1top ul li:hover {
            background-color: rgb(203, 238, 242)
        }

        .clicked {
            background-color: rgba(241, 112, 46, 0.84);
        }

        .unclick {
            background-color: rgb(203, 238, 242)
        }

        .info1top ul li a {
            margin-top: 5px;
        }

        .info1top ul li a span {
            color: rgb(0, 159, 205);
            font-size: 15px
        }

        .info1top ul li a h4 {
            display: inline;
            color: rgb(1, 160, 202)
        }

        .info1bottom ul {
            margin-top: -23px;
            list-style: none;
            margin-left: 20px;
        }

        .info1bottom ul li {
            float: left;
            margin-left: 10%;
            height: 41px;
            margin-top: -9px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info1bottom ul li {
                margin-left: 7%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info1bottom ul li {
                margin-left: 3.5%;
            }
        }

        @media screen and (max-width: 1374px) {
            .info1bottom ul li {
                margin-left: 2%;
            }
        }

        @media screen and (max-width: 1233px) {
            .info1bottom ul li {
                margin-left: 1.2%;
            }
        }

        .info1bottom ul li:hover {
            background-color: rgb(203, 238, 242)
        }

        .info1bottom ul li a {
            margin-top: 5px;
        }

        .info1bottom ul li a span {
            color: rgb(0, 159, 205);
            font-size: 15px
        }

        .info1bottom ul li a h4 {
            display: inline;
            color: rgb(1, 160, 202)
        }

        .info3top ul {
            margin-top: -23px;
            list-style: none;
            margin-left: 20px;
        }

        .info3top ul li {
            float: left;
            margin-left: 10%;
            height: 41px;
            margin-top: -9px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info3top ul li {
                margin-left: 7%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info3top ul li {
                margin-left: 3.5%;
            }
        }

        @media screen and (max-width: 1374px) {
            .info3top ul li {
                margin-left: 2%;
            }
        }

        @media screen and (max-width: 1233px) {
            .info3top ul li {
                margin-left: 1.2%;
            }
        }

        .info3top ul li:hover {
            background-color: rgb(214, 212, 251)
        }

        .unclick1 {
            background-color: rgb(214, 212, 251)
        }

        .info3top ul li a span {
            color: rgb(53, 102, 231);
            font-size: 15px
        }

        .info3top ul li a h4 {
            display: inline;
            color: rgb(52, 101, 232)
        }

        .info3bottom ul {
            margin-top: -23px;
            list-style: none;
            margin-left: 20px;
        }

        .info3bottom ul li {
            float: left;
            margin-left: 10%;
            height: 41px;
            margin-top: -9px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info3bottom ul li {
                margin-left: 7%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info3bottom ul li {
                margin-left: 3.5%;
            }
        }

        @media screen and (max-width: 1374px) {
            .info3bottom ul li {
                margin-left: 2%;
            }
        }

        @media screen and (max-width: 1233px) {
            .info3bottom ul li {
                margin-left: 1.2%;
            }
        }

        .info3bottom ul li:hover {
            background-color: rgb(214, 212, 251)
        }

        .info3bottom ul li a span {
            color: rgb(53, 102, 231);
            font-size: 15px
        }

        .info3bottom ul li a h4 {
            display: inline;
            color: rgb(52, 101, 232)
        }

        @media screen and (max-width: 1170px) {
            .info1bottom ul li {
                margin-left: 2%;
            }

            .info2bottom ul li {
                margin-left: 1%;
            }

            .info3bottom ul li {
                margin-left: 0.5%;
            }
        }

        .sqf * {
            display: inline-block;
            vertical-align: middle;
        }
    </style>
    <script type="text/javascript">

        var fit = mini.get('fitt');

        var IsUsable = [{ id: true, text: '可用' }, { id: false, text: '不可用' }];
        var types = [
            { id: 1, text: '未到账' },
            { id: 2, text: '已到账' },
            { id: 3, text: '部分到账' }
        ];
        var IsSend = [
            { id: 0, text: '未寄出' },
            { id: 1, text: '已寄出' }
        ];
        var InvoiceForm= [
            { id: 1, text: '电子发票' },
            { id: 2, text: '纸质发票' }
        ]
        var states=[
            {id:1,text:'待提交'},
            {id:2,text:'待审核'},
            {id:3,text:'审核驳回'},
            {id:4,text:'审核通过'}
        ];
    </script>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center" showheader="false">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-12">
                                <div id="info1"
                                     style="height:55px;background:rgb(226,250,252);border-radius: 5px;border:1px solid rgb(190,226,240);">
                                    <div class="info1top" style="padding-left: 100px;">
                                        <h3 class="sqf"
                                            style="color: rgb(3,154,209);margin-left:-90px;margin-top:17px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jk.png">&nbsp;发票申请
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli orig" id="J0">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(0)">
                                                    <span id="J1span">全部</span>
                                                    <h4 class="x0">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J1">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(1)">
                                                    <span id="J2span">待提交</span>
                                                    <h4 class="x1">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J2">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(2)">
                                                    <span id="J3span">提审核</span>
                                                    <h4 class="x2">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J3">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(3)">
                                                    <span id="J4span">审核驳回</span>
                                                    <h4 class="x3">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J4">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(4)">
                                                    <span id="J5span">审核通过</span>
                                                    <h4 class="x4">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                </table>
            </div>
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-add" id="InvoiceApplication_Add" onclick="addInvoiceApplication">增加</a>

                            <a class="mini-button" iconcls="icon-edit" id="InvoiceApplication_Edit" onclick="editInvoiceApplication">编辑</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="InvoiceApplication_Browse" onclick="browse()">浏览</a>
                            <a class="mini-button" iconcls="icon-remove" id="InvoiceApplication_Remove" onclick="remove">删除</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconcls="icon-ok" id="InvoiceApplication_KDXX" onclick="KDXX">审核通过</a>
                            <a class="mini-button" iconcls="icon-edit InvoiceApplication_KDXX" id="CmdNotPass" onclick="notPass()">拒绝驳回</a>

                            <span class="separator"></span>
                            <a class="mini-button" iconcls="icon-cut InvoiceApplication_Add" id="CmdCopyDocument" onclick="copyInvoice">复制</a>
                            <a class="mini-button" iconcls="icon-ok" id="InvoiceApplication_Config" onclick="CSPZ">参数配置</a>

                            <span class="separator"></span>
                            <a class="mini-button mini-button-info" id="InvoiceApplication_Export" onclick="doExport">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field InvoiceApplication_Query" id="comField"
                                 style="width:100px" popupWidth="200"
                                 data="[{id:'All',text:'全部属性'},{id:'InvoiceTT',text:'发票抬头'}, {id:'ReceiptNumber',
                                 text:'发票号'}, {id:'TaxpayerIdentificationNumber',text:'纳税人识别号'},{id:'EmailAddress',
                                 text:'联系人信息'}]" value="All"></div>
                            <input type="text" class="mini-textbox Query_Field InvoiceApplication_Query" style="width:120px" id="QueryText"/>
                            <a class="mini-button" iconCls="icon-find" onclick="doQuery()" id="InvoiceApplication_Query">模糊查询</a>
                            <a class="mini-button mini-button-danger InvoiceApplication_Reset" id="a2" onclick="reset">重置</a>
                            <a class="mini-button mini-button-primary" id="InvoiceApplication_HighQuery" onclick="expand">高级搜索</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:220px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">申请人：</td>
                            <td style="width:13%;">
                                <input class="mini-textbox" data-oper="LIKE" name="SQR" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">申请日期：</td>
                            <td style="width:13%;">
                                <input name="DateOfPayment" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="DateOfPayment" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd"  style="width:100%" />
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">到账银行：</td>
                            <td style="width:15%;"><input class="mini-combobox" id="BankOfArrival"
                                                          name="BankOfArrival" style="width: 100%"   url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=2"/></td>
                            <td style="width:6%;padding-left:10px;">发票类型：</td>
                            <td style="width:15%;"><input class="mini-combobox" id="InvoiceType" name="InvoiceType"
                                                          style="width: 100%"   url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=1"/></td>
                            <td style="width:6%;padding-left:10px;">发票抬头：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="InvoiceTT" style="width:100%" /></td>

                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">项目名称：</td>
                            <td style="width:15%;"><input class="mini-combobox" id="ProjectName" name="ProjectName" style="width: 100%"  url="/systems/dict/getAllByDtId?dtId=29"/></td>
                            <td style="width:6%;padding-left:10px;">金额：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="Amount" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">款项到款情况：</td>
                            <td style="width:15%;"><input class="mini-combobox" data="types"  name="PaymentToPayment" data-oper="EQ" style="width:100%" /></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">户名：</td>
                            <td style="width:15%;">
                                <input class="mini-textbox" data-oper="LIKE" name="AccountName" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">纳税人识别号：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="TaxpayerIdentificationNumber" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">联系人信息：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="EmailAddress" style="width:100%" /></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">开票单位：</td>
                            <td style="width:15%;"><input class="mini-combobox"  name="TickCompany"
                                                          data-oper="EQ" style="width:100%"
                                                          url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=4" /></td>
                            <td style="width:6%;padding-left:10px;">到帐银行：</td>
                            <td style="width:15%;"><input class="mini-combobox"  name="BankOfArrival"
                                                          data-oper="EQ" style="width:100%"
                                                          url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=3" /></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" style="width:120px"
                                   href="javascript:doHightSearch();">搜索</a>
                                <a class="mini-button mini-button-danger" style="margin-left:30px;width:120px"
                                   href="javascript:expand();">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit">
                <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                     allowresize="true" url="/InvoiceApplication/invoice/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="AddTime" sortorder="desc"
                     autoload="false" onDrawCell="onDraw" onload="afterload" onrowclick="onRowChange">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="State" type="comboboxcolumn"  headerAlign="center"  align="center" width="80">状态
                            <input property="editor" class="mini-combobox" data="states" style="width:100%"/>
                        </div>
                        <div field="InvoiceTT" width="150" headeralign="center" >发票抬头</div>
                        <div field="TaxpayerIdentificationNumber" width="120" headeralign="center" >纳税人识别号</div>
                        <div field="Amount" width="120" headeralign="center" >金额</div>
                        <div field="PaymentToPayment"  width="80" headerAlign="center" type="treeselectcolumn"
                             allowsort="true" align="center">到帐情况
                            <div property="editor" class="mini-combobox" data="types"></div>
                        </div>
                        <div field="InvoiceForm"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true" align="center">发票类型
                            <div property="editor" class="mini-combobox" data="InvoiceForm"></div>
                        </div>
                        <div field="DZFPMIDDLEFILE" name="DZFPMIDDLEFILE" width="80" headeralign="center"
                             align="center">电子发票</div>
                        <div field="EmailAddress" width="200" headeralign="center" >联系人信息</div>
                        <div field="ReceiptNumber" width="100" headeralign="center" >发票号</div>
                        <div field="IsSend"  width="60" headerAlign="center" type="treeselectcolumn" allowsort="true">是否寄出
                            <div property="editor" class="mini-combobox" data="IsSend"></div>
                        </div>
                        <div field="CourierCompany" width="120" headeralign="center" >快递公司</div>
                        <div field="ExpressNumber" width="120" headeralign="center" >快递号</div>
                        <div field="MIDDLEFILE" name="MIDDLEFILE" width="80" headeralign="center" align="center">回执上传</div>
                        <div field="UploadTime" width="100" headeralign="center" datatype="date"
                             dateformat="yyyy-MM-dd" allowsort="true">上传时间</div>
                        <div field="Applicant" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">申请人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="Applicant" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="AddTime" width="80" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">申请日期</div>
                        <div field="Remarks" visible="false">备注</div>
                    </div>
                </div>
                <input class="mini-hidden" name="LoginUserID" id="LoginUserID" value="${UserID}" />
            </div>
        </div>
    </div>
    <div id="CSPZWindow" class="mini-window" showmodal="true" allowresize="false" allowdrag="true" style="width:900px;height:400px;" title="参数设置">
        <div class="mini-fit">
            <div id="tabs1" class="mini-tabs" style="width:100%;height:100%" bodystyle="padding:0;border:0;border-bottom: 1px solid rgb(210, 210, 210);" activeindex="0">
                <div title="开票单位">
                    <div class="mini-toolbar" id="mainToolbar1" style="text-align:right;padding-right:20px; border:none;background: none;">
                        <a class="mini-button" iconcls="icon-add" onclick="add()">新增</a>
                        <a class="mini-button" iconcls="icon-remove" onclick="del()">删除</a>
                    </div>
                    <div class="mini-fit">
                        <div class="mini-datagrid" id="grid4" style="width:100%;height:100%;" pagesize="15"
                             sizelist="[5,10,20]" url="" autoload="false" ondrawcell="renderCheck">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div headeralign="center" field="name" width="480">公司名称</div>
                                <div field="canUse" width="100" headeralign="center">
                                    是否显示
                                    <input property="editor" class="mini-combobox" data="IsUsable"  />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div title="发票类型">
                    <div class="mini-toolbar" id="mainToolbar1" style="text-align:right;padding-right:20px; border: none; background: none;">
                        <a class="mini-button" iconcls="icon-add" onclick="add()">新增发票类型</a>
                        <a class="mini-button" iconcls="icon-add" onclick="del()">删除</a>
                    </div>
                    <div class="mini-fit">
                        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%;" pagesize="15" sizelist="[5,10,20,50,100,150,200]" url="" autoload="false" ondrawcell="renderCheck">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div headeralign="center" field="name" width="480">参数内容</div>
                                <div field="canUse" width="100" headeralign="center">
                                    是否显示
                                    <input property="editor" class="mini-combobox" data="IsUsable"  />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div title="到账银行">
                    <div class="mini-toolbar" id="mainToolbar1" style="text-align:right;padding-right:20px; border: none; background: none;">
                        <a class="mini-button" iconcls="icon-add" onclick="add()">新增到账银行</a>
                        <a class="mini-button" iconcls="icon-add" onclick="del()">删除</a>
                    </div>
                    <div class="mini-fit">
                        <div class="mini-datagrid" id="grid2" style="width:100%;height:100%;" pagesize="15" sizelist="[5,10,20,50,100,150,200,500,1000]" autoload="false" ondrawcell="renderCheck">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div headeralign="center" field="name" width="480">参数内容</div>
                                <div field="canUse" width="100" headeralign="center">
                                    是否显示
                                    <input property="editor" class="mini-combobox" data="IsUsable" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div title="项目名称">
                    <div class="mini-toolbar" id="mainToolbar1" style="text-align:right;padding-right:20px; border: none; background: none;">
                        <a class="mini-button" iconcls="icon-add" onclick="add()">新增项目名称</a>
                        <a class="mini-button" iconcls="icon-add" onclick="del()">删除</a>
                    </div>
                    <div class="mini-fit">
                        <div class="mini-datagrid" id="grid3"  style="width:100%;height:100%;" pagesize="15" sizelist="[5,10,20,50,100,150,200,500,1000]" autoload="false" ondrawcell="renderCheck">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div headeralign="center" field="name" width="480">参数内容</div>
                                <div field="canUse" width="100" headeralign="center">
                                    是否显示
                                    <input property="editor" class="mini-combobox" data="IsUsable" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="editWindow1" class="mini-window" title="参数设置" showmodal="true" allowresize="true" allowdrag="true" style="width:1100px;">
        <div id="editform1" class="form">
            <table style="width:100%;">
                <tr>
                    <td>参数内容：</td>
                    <td>
                        <input class="mini-textbox" id="Name" name="Name" style="width: 98%;" required="true" />
                    </td>
                    <td>是否显示：</td>
                    <td>
                        <input class="mini-combobox" name="CanUse" id="CanUse" data="[{id:true,text:'可用'},{id:false,text:'不可用'}]" style="width: 100%" value="true" required="true">
                        <input class="mini-hidden" id="id" name="id"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="text-align:center">
                        <button class="mini-button" style="width:80px" id="SaveForm2" onclick="WinMehod">确定</button>
                        <button class="mini-button" style="width:80px;" id="CancelForm2" onclick="cancelRow1">取消</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="editWindow2" class="mini-window" title="快递信息" showmodal="true" allowresize="true" allowdrag="true" style="width:1100px;">
        <div id="editform2" class="form">
            <input class="mini-hidden" name="InvoiceApplicationID" />
            <table style="width:100%;">
                <tr>
                    <td>发票号：</td>
                    <td>
                        <input class="mini-textbox" id="ReceiptNumber" name="ReceiptNumber" style="width: 98%;" required="true" />
                    </td>
                    <td>快递公司：</td>
                    <td>
                        <input name="CourierCompany" id="CourierCompany" class="mini-combobox" textfield="text" valuefield="text" allowinput="true" style="width: 98%;" />
                    </td>
                </tr>
                <tr>
                    <td>快递号：</td>
                    <td colspan="3">
                        <input class="mini-textbox" id="ExpressNumber" name="ExpressNumber" style="width: 98%;" />
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="text-align:center">
                        <button class="mini-button" style="width:80px" id="SaveForm2" onclick="SaveExpressInfo">确定</button>
                        <button class="mini-button" style="width:80px;" id="CancelForm2" onclick="cancelRow2">取消</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <form method="post" action="/InvoiceApplication/invoice/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <script type="text/javascript">
        mini.parse();
        var grid=mini.get('#datagrid1');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var WinType="";
        var cmdAdd=mini.get('#InvoiceApplication_Add');
        var cmdEdit=mini.get('#InvoiceApplication_Edit');
        var cmdAudit=mini.get('#InvoiceApplication_KDXX');
        var cmdNotPass=mini.get('#CmdNotPass');
        var cmdCopy=mini.get('#CmdCopyDocument');
        var cmdRemove=mini.get('#InvoiceApplication_Remove');

        $(function () {
            var tabs = mini.get("tabs1");
            tabs.on("activechanged", function (e) {
                if (e.tab.title == "发票类型") {
                    WinType="InvoiceType";
                    var grid1 = mini.get('grid1');
                    if (grid1.getUrl() == '') {
                        grid1.setUrl('/InvoiceApplication/invoice/getParameter');
                        grid1.load({dtId: 1});
                    }
                }
                if (e.tab.title == "到账银行") {
                    WinType="Bank";
                    var grid2 = mini.get('grid2');
                    if (grid2.getUrl() == '') {
                        grid2.setUrl('/InvoiceApplication/invoice/getParameter');
                        grid2.load({dtId: 2});
                    }
                }
                if (e.tab.title == "项目名称") {
                    WinType="ProjectName";
                    var grid3 = mini.get('grid3');
                    if (grid3.getUrl() == '') {
                        grid3.setUrl('/InvoiceApplication/invoice/getParameter');
                        grid3.load({dtId: 3});
                    }
                }
                if (e.tab.title == "开票单位") {
                    WinType="TickCompany";
                    var grid4 = mini.get('grid4');
                    if (grid4.getUrl() == '') {
                        grid4.setUrl('/InvoiceApplication/invoice/getParameter');
                        grid4.load({dtId: 4});
                    }
                }
            });
        });
        var grid1 = mini.get('grid1');
        WinType="InvoiceType";
        if (grid1.getUrl() == '') {
            grid1.setUrl('/InvoiceApplication/invoice/getParameter');
            grid1.load({dtId: 1});
        }
        function WinMehod() {
            var form = new mini.Form('#editWindow1');
            var editWindow = mini.get("editWindow1");
            form.validate();
            if (form.isValid()) {
                var data = form.getData();
                form.loading("保存中");
                if (WinType == "InvoiceType") {
                    var grid = mini.get('grid1');
                    $.post(
                        "/InvoiceApplication/invoice/saveParameter",
                        { Entity: mini.encode(data), Name: "发票类型" },
                        function (text) {
                            var res=mini.decode(text);
                            if (res.success) {
                                grid.reload();
                            } else {
                                mini.alert(res.Message);
                            }
                            form.unmask();
                            editWindow.hide();
                        }
                    );
                }
                else if (WinType == "Bank"){
                    var grid = mini.get('grid2');
                    $.post(
                        "/InvoiceApplication/invoice/saveParameter",
                        { Entity: mini.encode(data), Name: "到账银行" },
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
                }
                else if (WinType == "ProjectName") {
                    var grid = mini.get('grid3');
                    $.post(
                        "/InvoiceApplication/invoice/saveParameter",
                        { Entity: mini.encode(data), Name: "项目名称" },
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
                }else if(WinType=="TickCompany"){
                    var grid = mini.get('grid4');
                    $.post(
                        "/InvoiceApplication/invoice/saveParameter",
                        { Entity: mini.encode(data), Name: "开标单位" },
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

                }
            }
        }

        function del() {
            var form = new mini.Form('#editWindow1');
            if (WinType == "InvoiceType") {
                var rows = mini.get("grid1").getSelected();
            }
            else if (WinType == "Bank") {
                var rows = mini.get("grid2").getSelected();
            }
            else if (WinType == "ProjectName") {
                var rows = mini.get("grid3").getSelected();
            }
            else if (WinType == "TickCompany") {
                var rows = mini.get("grid4").getSelected();
            }
            var ids = [];
            if (rows == undefined || rows == "undefined") {
                mini.alert('请选择要删除的记录!');
                return;
            }
            else {
                ids.push(rows.id);
            }
            mini.confirm('确定要删除配置数据？', '删除提示', function (act) {
                if (act == "ok")
                {
                    var grid = "";
                    if (WinType == "InvoiceType") {
                        grid = mini.get('grid1');
                    }
                    else if (WinType == "Bank")
                    {
                        grid = mini.get('grid2');
                    }
                    else if (WinType == "ProjectName") {
                        grid = mini.get('grid3');
                    }
                    else if (WinType == "TickCompany") {
                        grid = mini.get('grid4');
                    }
                    var url="/InvoiceApplication/invoice/removeParameter";
                    $.ajax({
                        contentType:'application/json',
                        method:'post',
                        url:url,
                        data:mini.encode(ids),
                        success:function (r) {
                            if (r['success']) {
                                mini.alert("删除成功！",'删除提示',function () {
                                    grid.reload();
                                });

                            } else mini.alert("删除失败！");
                            form.unmask();
                        },
                        failure:function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function SaveExpressInfo() {
            var row = grid.getSelected();
            if (!row) {
                return;
            }
            var editForm = new mini.Form("#editWindow2");
            editForm.validate();
            if (editForm.isValid() ){
                editForm.loading("保存中......");
                var arg={
                    Data: mini.encode(editForm.getData()),
                };
                $.post("updateExpressInfo", arg,
                    function (result) {
                        var res = mini.decode(result);
                        if (res.success) {
                            mini.alert('数据保存成功','系统提示',function(){
                                grid.reload();
                                mini.get("editWindow2").hide();
                            });
                        }else {
                            mini.alert(res.Message);
                        }
                    }
                );
            }
        }

        function addInvoiceApplication(){
            mini.open({
                url:'/InvoiceApplication/invoice/add',
                width:'100%',
                height:'100%',
                title:'新增开票申请',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            });
        }

        function editInvoiceApplication() {
            var row=grid.getSelected();
            if(!row) {
                mini.alert('请选择要编辑的退款信息!');
                return ;
            }
            var ID=row['InvoiceApplicationID'];
            mini.open({
                url:'/InvoiceApplication/invoice/edit?Mode=Edit&InvoiceApplicationID='+ID,
                width:'100%',
                height:'100%',
                title:'修改开票信息',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            })
        }

        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["InvoiceApplicationID"]);
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('确认要删除的开票信息数据?', '删除提示', function (act) {
                if (act === 'ok') {
                    var url="/InvoiceApplication/invoice/remove";
                    $.ajax({
                        contentType:'application/json',
                        method:'post',
                        url:url,
                        data:mini.encode(ids),
                        success:function (r) {
                            if (r['success']) {
                                mini.alert("删除成功！",'删除提示',function () {
                                    grid.reload();
                                });

                            } else mini.alert("删除失败！");
                        },
                        failure:function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function expand(e) {
            e=e||{};
            var btn = e.sender;
            if(!btn){
                btn=mini.get('#InvoiceApplication_HighQuery');
            }
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("panel-collapse");
                btn.setText("高级查询");
                $('#p1').css('display', "block");

            } else {
                btn.setIconCls("panel-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
            }
            fit.setHeight('100%');
            fit.doLayout();
        }
        function afterload() {
            updateStateNumbers();
        }
        function refreshData(grid) {
            var pa = grid.getLoadParams();
            var pageIndex = grid.getPageIndex() || 0;
            var pageSize = grid.getPageSize() || 20;
            pa = pa || { pageIndex: pageIndex, pageSize: pageSize };
            grid.load(pa);
        }

        function doHightSearch(){
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

        function CSPZ() {
            var win = mini.get("CSPZWindow");
            if (win) {
                win.show();
            }
        }

        function KDXX() {
            var row = grid.getSelected();
            if (row == null) {
                mini.alert("请选择要配置的开票信息!");
                return;
            }
            var InvoiceApplicationID=row['InvoiceApplicationID']
            mini.open({
                url:'/InvoiceApplication/invoice/saveKuaiDiXinXi?InvoiceApplicationID='+InvoiceApplicationID,
                width:'50%',
                height:400,
                title:'发票及快递信息',
                showModal:true,
                onDestroy:function(){
                    grid.reload();
                }
            });
        }

        function renderCheck(a, b, c) {
            var val = a.value;
            var field = a.field;
            if (a.field == "canUse") {
                if (val == true || val == "true")
                    a.cellHtml = '<div style="color:green;text-align:center">可用</div>';
                else
                    a.cellHtml = '<div style="color:red;text-align:center">不可用</div>';
            }
        }

        function add() {
            mini.open({
                url:'/InvoiceApplication/invoice/saveParameterConfigs?WinType='+WinType,
                width:'40%',
                height:245,
                title:'参数设置',
                showModal:true,
                onDestroy:function(){
                    if (WinType=="InvoiceType"){
                        var grid1 = mini.get('grid1');
                        grid1.reload();
                    }else if (WinType=="Bank"){
                        var grid2 = mini.get('grid2');
                        grid2.reload();
                    }if (WinType=="ProjectName"){
                        var grid3 = mini.get('grid3');
                        grid3.reload();
                    }if (WinType=="TickCompany"){
                        var grid4 = mini.get('grid4');
                        grid4.reload();
                    }
                }
            });
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
        
        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "MIDDLEFILE") {
                var files = parseInt(record[field] || 0);
                var InvoiceApplicationID = record["InvoiceApplicationID"];
                if (files == 0) e.cellHtml = '<a href="#" id="browse" onclick="onFileupload(' + "'" + InvoiceApplicationID + "','发票申请'" + ')">上传</a>'; else
                    e.cellHtml = '<a href="#" onclick="onFileupload(' + "'" + InvoiceApplicationID + "','发票申请'" + ')">管理</a>';
            }
            if (field=="DZFPMIDDLEFILE"){
                var files = parseInt(record[field] || 0);
                var InvoiceApplicationID=record["InvoiceApplicationID"];
                if (files == 0) e.cellHtml = '<a href="#" id="browse" onclick="onFileupload(' + "'" + InvoiceApplicationID + "','电子发票'" + ')">上传</a>'; else
                    e.cellHtml = '<a href="#" onclick="onFileupload(' + "'" + InvoiceApplicationID + "','电子发票'" + ')">管理</a>';
            }
            if (field=="Amount"){
                var dd = record["Amount"];
                e.cellHtml = dd + "(" + smalltoBIG(dd) + ")";
            }
        }

        //金额大小写转换
        function smalltoBIG(n) {
            var fraction = ['角', '分'];
            var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
            var unit = [['元', '万', '亿'], ['', '拾', '佰', '仟']];
            var head = n < 0 ? '欠' : '';
            n = Math.abs(n);

            var s = '';

            for (var i = 0; i < fraction.length; i++) {
                s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
            }
            s = s || '整';
            n = Math.floor(n);

            for (var i = 0; i < unit[0].length && n > 0; i++) {
                var p = '';
                for (var j = 0; j < unit[1].length && n > 0; j++) {
                    p = digit[n % 10] + unit[1][j] + p;
                    n = Math.floor(n / 10);
                }
                s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
            }
            return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
        }

        function doExport(){
                var excel=new excelData(grid);
                excel.addEvent('beforeGetData',function (grid,rows) {
                    return grid.getSelecteds();
                })
                excel.export("发票申请记录.xls");
        }

        function onFileupload(InvoiceApplicationID,Type){
            var row=grid.getSelected();
            if(row){
                function p(ids){
                    mini.open({
                        url:'/attachment/addFile?IDS='+ids,
                        width:800,
                        height:400,
                        title:Type,
                        onload:function(){
                            var iframe = this.getIFrameEl();
                            iframe.contentWindow.addEvent('eachFileUploaded',function(data){
                                var arg={Type:Type,ATTID:data.AttID,ID:row["InvoiceApplicationID"]};
                                var url='/InvoiceApplication/invoice/saveMiddleFile';
                                $.post(url,{Data:mini.encode(arg)},function(result){
                                    if(result.success==false){
                                        mini.alert('保存中间文件信息失败，请联系管理员解决问题。');
                                    } else {
                                        doReload(grid);
                                    }
                                })
                            });
                            iframe.contentWindow.addEvent('eachFileRemoved',function(data){
                                var url='/InvoiceApplication/invoice/removeMiddleFile';
                                $.post(url,{AttID:data.ATTID},function(result){
                                    if(result.success==false){
                                        mini.alert('删除中间文件信息失败，请联系管理员解决问题。');
                                    } else {
                                        doReload(grid);
                                    }
                                })
                            });
                        }
                    });
                }
                var url='/InvoiceApplication/invoice/getMiddleFile';
                $.getJSON(url,{InvoiceApplicationID:InvoiceApplicationID,Type:Type},function(result){
                    var data=result.data ||[];
                    p(data.join(','));
                });
            }
        }
        function copyInvoice(){
            var row=grid.getSelected();
            if(row){
                var id=row["InvoiceApplicationID"];
                var url='/InvoiceApplication/invoice/copyDocument';
                mini.confirm('确认要复制选择的记录并生成发票申请吗？','复制发票申请',function(act){
                    if(act=='ok') {
                        $.post(url,{ID:id},function(result){
                            if(result.success){
                                mini.alert('发票申请信息复制成功!','系统提示',function(){
                                    grid.reload();
                                });
                            } else {
                                mini.alert(result.message || "复制操作失败，请稍候重试!");
                            }
                        })
                    }
                });

            } else mini.alert('请选择要复制的发票记录!');
        }

        function changeQuery(state) {
            var con = $('.x' + state);
            var cons = $('.Jdlcli');
            for (var i = 0; i < cons.length; i++) {
                var cx = $(cons[i]);
                cx.removeClass('clicked');
                $('#J' + i).css({"background-color": "rgb(226,250,252)"});
            }
            $(con).parents('.Jdlcli').addClass('clicked');
            $('#J' + state).css({"background-color": "rgba(247, 140, 24, 0.85)"});
            doQuery(state);
        }
        function doQuery(state){
            var arg = {};
            var cs = [];
            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL") f = "NEIBUBH";
                        var kWork = f + '=' + word;
                        if (cs.indexOf(kWork) == -1) {
                            var op = {field: f, oper: 'LIKE', value: word};
                            cs.push(op);
                        }
                    }
                } else {
                    var op = {field: field, oper: 'LIKE', value: word};
                    cs.push(op);
                }
            }
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            if (state) {
                var sstt = parseInt(state || 0);
                if (sstt > 0) {
                    var p = {
                        field: 'State',
                        value: state,
                        oper: 'EQ'
                    };
                    var gg = [p];
                    arg["High"] = mini.encode(gg);
                }
            }
            grid.load(arg);
        }
        function updateStateNumbers() {
            var key = (new Date()).getTime();
            var url = '/InvoiceApplication/invoice/getInvoiceTotal';
            $.getJSON(url, {}, function (result) {
                if (result.success) {
                    var states = result.data || {};
                    for (var i = 0; i < states.length; i++) {
                        var state = states[i];
                        var con = $('.' + state.name);
                        if (con.length > 0) {
                            con.text(state.num);
                        }
                    }
                }
            });
        }
        $(function(){
            changeQuery(0);
            updateStateNumbers();
        })

        function hideAll(){
            cmdEdit.hide();
            cmdRemove.hide();
            cmdAudit.hide();
            cmdNotPass.hide();
            cmdCopy.hide();
        }
        var curMan=parseInt(mini.get('#LoginUserID').getValue());
        function onRowChange(e){
            var rows=grid.getSelecteds();
            if(rows.length>1) {
                hideAll();
            } else if(rows.length==1){
                var row=rows[0];
                var state=parseInt(row["State"] || 0);
                var createMan=parseInt(row["Applicant"] || 0);
                hideAll();
                if(state==1 || state==3){
                    if(curMan==createMan){
                        cmdEdit.show();
                        cmdRemove.show();
                    }
                }
                if(state==2){
                    cmdAudit.show();
                    cmdNotPass.show();
                    cmdAudit.setText('审核通过');
                }
                if(state>1)cmdCopy.show();
                if(state==4){
                    cmdAudit.show();
                    cmdAudit.setText('更改发票快递信息');
                }
            }
            else hideAll();
        }
        function browse(){
            var row=grid.getSelected();
            if(!row) {
                mini.alert('请选择要查看的发票申请信息!');
                return ;
            }
            var ID=row['InvoiceApplicationID'];
            mini.open({
                url:'/InvoiceApplication/invoice/edit?Mode=Browse&InvoiceApplicationID='+ID,
                width:'100%',
                height:'100%',
                title:'查看开票申请信息',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            })
        }
        function notPass(){
            var row=grid.getSelected();
            if(!row) {
                mini.alert('请选择要审核的发票申请信息!');
                return ;
            }
            var ID=row['InvoiceApplicationID'];
            mini.prompt("请输入审核不通过的原因：", "请输入",
                function (action, value) {
                    if (action == "ok") {
                        value=value || "审核不通过";
                        g(value);
                    }
                },
                true
            );
            function g(result){
                var url='/InvoiceApplication/invoice/notPass?ID='+ID;
                $.post(url,{auditText:result},function(result){
                        if(result.success){
                            mini.alert('操作成功!','系统提示',function(){grid.reload();});
                        } else mini.alert(result.message || "操作失败，请稍候重试!",'系统提示');
                })
            }
        }
    </script>
</@layout>
