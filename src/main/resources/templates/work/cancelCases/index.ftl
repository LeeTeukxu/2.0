<#include "/shared/layout.ftl">
<@layout>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }

        .ui-tooltip {
            max-width: 850px;
        }
    </style>
    <script type="text/javascript">
        mini.parse();
        var fit = mini.get('fitt');
        $(function () {
            $('#p1').hide();
        })
        var states = [
            {id: 1, text: '待审核'},
            {id: 2, text: '待复核'},
            {id: 3, text: '审核驳回'},
            {id: 4, text: '复核驳回'},
            {id: 5, text: '业务完成'},
        ];
        var curUserId = "${UserID}";
    </script>
    <style type="text/css">
        .Zlyw * {
            display: inline-block;
            vertical-align: middle;
        }

        .Zlyw {
            padding-top: -30px;
        }

        .Zlyw img {
            width: 20px;
        }

        .Zlyw h5 {
            color: #ffffff;
            font-size: 15px
        }

        .Zlywtop {
            display: inline-block;
            width: 500px;
            float: left;
            margin-top: -58px;
            height: 32px;
        }

        .Zlywtop ul {
            margin-top: 55px;
            margin-right: 30px;
            text-align: left;
        }

        .Zlywtop ul li {
            float: left;
            margin-left: 30px;
            height: 45px;
            padding-top: 11px;
            width: 90px;
            border-radius: 5px;
            margin-top: -54px;
            list-style: none;
        }

        .Zlywtop ul li:hover {
            background-color: rgb(74, 106, 157);
        }

        .Zlywtop ul li a {
            color: white;
        }

        .Zlywtop ul li a span {
            font-size: 15px;
        }

        .Zlywtop ul li a h4 {
            display: inline
        }

        @media screen and (max-width: 1551px) {
            .Zlywtop ul li {
                margin-left: 100px;
            }
        }


        .info1top ul {
            margin-top: -23px;
            list-style: none;
            margin-left: 20px;
        }

        .info1top ul li {
            float: left;
            margin-left: 10%;
            height: 41px;
            margin-top: -24px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info1top ul li {
                margin-left: 10%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info1top ul li {
                margin-left: 10%;
            }
        }

        @media screen and (max-width: 1374px) {
            .info1top ul li {
                margin-left: 10%;
            }
        }

        @media screen and (max-width: 1233px) {
            .info1top ul li {
                margin-left: 10%;
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
            margin-top: -8px;
            list-style: none;
            margin-left: 20px;
        }

        .info1bottom ul li {
            float: left;
            margin-left: 6%;
            height: 45px;
            margin-top: -6px;
            padding-top: 12px;
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

        /*.info2bottom{margin-top: 10px;}*/
        .info2bottom ul {
            margin-top: -8px;
            list-style: none;
            margin-left: 20px;
            text-align: center;
        }

        .info2bottom ul li {
            float: left;
            margin-left: 13%;
            width: 90px;
            height: 45px;
            margin-top: -6px;
            padding-top: 12px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info2bottom ul li {
                margin-left: 11%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info2bottom ul li {
                margin-left: 4%;
            }
        }

        @media screen and (max-width: 1184px) {
            .info2bottom ul li {
                margin-left: 3%;
            }
        }

        .info2bottom ul li a span {
            color: rgb(53, 102, 231);
            font-size: 15px
        }

        .info2bottom ul li a h4 {
            display: inline;
            color: rgb(51, 97, 232)
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
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center" showheader="false">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-4">
                                <div id="info1"
                                     style="height:80px;background:rgb(226,250,252);border:1px solid rgb(190,226,240);border-radius: 5px;">
                                    <div class="info1top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(214,239,243);text-align: center;">
                                        <h2 style="color: rgb(3,154,209);margin-left: 5px;margin-top: 4px;">申请记录</h2>
                                    </div>
                                    <div class="info1bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival clicked" id="J1" style="width:120px;text-align:
                                            center">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(0);">
                                                    <span id="J1span">全部</span>
                                                    <h4 class="x0">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="J1" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(5);">
                                                    <span id="J1span">已完成</span>
                                                    <h4 class="x5">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="mini-col-4">
                                <div id="info2"
                                     style="height:80px;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                                    <div class="info2top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                                        <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">初审</h2>
                                    </div>
                                    <div class="info2bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival" id="Js1" style="width:100px">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(1)">
                                                    <span id="Js1span">待审核</span>
                                                    <h4 id="Js1h4" class="x1" style="width:100px">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js2" style="width:100px">
                                                <a style="text-decoration:none;width:200px" target="_self" href="#"
                                                   class="active_a"
                                                   onclick="changeQuery(3)">
                                                    <span id="Js2span">审核驳回</span>
                                                    <h4 id="Js2h4" class="x3">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js3" style="width:100px">
                                                <a style="text-decoration:none;width:200px" target="_self" href="#"
                                                   class="active_a"
                                                   onclick="changeQuery(2)">
                                                    <span id="Js2span">审核通过</span>
                                                    <h4 id="Js2h4" class="x2">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="mini-col-4">
                                <div id="info2"
                                     style="height:80px;background:rgb(230,238,253);border:1px solid rgb(211,219,236);border-radius: 5px;">
                                    <div class="info2top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                                        <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">终审</h2>
                                    </div>
                                    <div class="info2bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival" id="Js1" style="width:100px">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery(2)">
                                                    <span id="Js1span">待复核</span>
                                                    <h4 id="Js1h4" class="x2" style="width:100px">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js2" style="width:100px">
                                                <a style="text-decoration:none;width:200px" target="_self" href="#"
                                                   class="active_a"
                                                   onclick="changeQuery(4)">
                                                    <span id="Js2span">复核驳回</span>
                                                    <h4 id="Js2h4" class="x4">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js3" style="width:100px">
                                                <a style="text-decoration:none;width:200px" target="_self" href="#"
                                                   class="active_a"
                                                   onclick="changeQuery(5)">
                                                    <span id="Js2span">复核通过</span>
                                                    <h4 id="Js2h4" class="x5">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                </table>
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-add" id="CancelCases_Add" onclick="addCancel">申请中止</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconcls="icon-ok" id="CancelCases_Audit" onclick="auditOne"
                               visible="false">主管审核</a>
                            <a class="mini-button" iconcls="icon-ok" id="CancelCases_Set" onclick="setOne"
                               visible="false">流程复核</a>
                            <a class="mini-button" iconCls="icon-reload" id="CancelCases_Browse"
                               onclick="browseCancel">查看</a>
                            <span class="separator"></span>
                            <a class="mini-button" id="CancelCases_Remove" iconCls="icon-remove"
                               onclick="remove()" visible="false">删除申请</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field FeeItem_Query" id="comField" style="width:100px"
                                 data="[{id:'All',text:'全部属性'},{id:'DocSN',text:'单据编号'},{id:'ContractNo',
                                 text:'合同编号'},{id:'ClientName',text:'客户名称'}]" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field FeeItem_Query" style="width:200px" id="QueryText"/>
                            <a class="mini-button mini-button-success" onclick="doQuery();" id="FeeItem_Query">模糊查询</a>
                            <a class="mini-button mini-button-danger" id="FeeItem_Reset" onclick="reset">重置条件</a>
                            <a class="mini-button mini-button" id="CancelCases_HighQuery" iconCls="icon-expand"
                               onclick="expand">高级查询</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:160px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">申请日期：</td>
                            <td style="width:13%;">
                                <input name="CreateTime" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="CreateTime" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">复核日期：</td>
                            <td style="width:15%;">
                                <input name="SetTime" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="SetTime" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">项目类型：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="Type"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">交单编号：</td>
                            <td style="width:15%;"><input class="mini-textbox"  data-oper="LIKE" name="Type"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">合同编号：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="ContractNo"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">业务数量：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="Nums"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">中止原因：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="Memo"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">客户名称：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="ClientName"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">银行名称：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="BankName"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">银行帐号：</td>
                            <td style="width:15%;"><input class="mini-textbox" name="AccountNumber" data-oper="LIKE"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">退款金额：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="TotalMoney"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">申请人：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="CreateManName"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">审核人：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="AuditManName"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">复核人：</td>
                            <td style="width:15%;"><input class="mini-textbox" name="SetManName" data-oper="LIKE"
                                                          style="width:100%"/></td>
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
                <div class="mini-layout" style="width:100%;height:100%" id="fitt1">
                    <div region="center">
                        <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                             onrowdblclick="editClient" frozenStartColumn="0" frozenEndColumn="6"
                             allowresize="true" url="/work/cancelCases/getMain" multiselect="false"
                             pagesize="20"  sortfield="CreateTime" sortorder="desc"
                             autoload="true" onDrawCell="onDraw" onload="afterload" onrowClick="rowClick">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="State" width="80" headerAlign="center" align="center"
                                     type="comboboxcolumn" allowsort="true">状态
                                    <div property="editor" class="mini-combobox" data="states"></div>
                                </div>
                                <div field="Type" width="100" headerAlign="center" align="center">业务类型</div>
                                <div field="DocSN" width="140" headerAlign="center" align="center">交单业务编号</div>
                                <div field="ContractNo" width="150" headerAlign="center" align="center">合同编号</div>
                                <div field="ClientName" align="center" width="200" headeralign="center" allowSort="true">客户名称</div>
                                <div field="Nums" width="180" headeralign="center" allowsort="true"
                                     align="center">业务数量
                                </div>
                                <div field="TotalMoney" width="100" headeralign="center" allowsort="true"
                                     align="center">退款金额
                                </div>
                                <div field="Memo" width="200" headeralign="center" align="center">中止原因</div>
                                <div field="CreateMan" width="80" headeralign="center" allowsort="true"
                                     align="center" type="treeselectcolumn">业务人员
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep"
                                           textField="Name" valueField="FID" parentField="PID"/>
                                </div>
                                <div field="CreateTime" width="100" headeralign="center" datatype="date" align="center"
                                     dateformat="yyyy-MM-dd"
                                     allowsort="true" align="center">申请日期
                                </div>
                                <div field="AuditMan" allowsort="true" align="center" width="80"
                                     headeralign="center" type="treeselectcolumn">审核人
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep"
                                           textField="Name" valueField="FID" parentField="PID"/>
                                </div>
                                <div field="AuditText" width="100" headerAlign="center" align="center">审核意见</div>
                                <div field="AuditTime" width="100" headeralign="center" datatype="date"
                                     dateformat="yyyy-MM-dd" allowsort="true" align="center">审核日期
                                </div>
                                <div field="SetMan" width="80" headeralign="center" type="treeselectcolumn"
                                     allowsort="true" align="center" type="treeselectcolumn">终审人
                                    <input property="editor" class="mini-treeselect"
                                           url="/systems/dep/getAllLoginUsersByDep"
                                           textField="Name" valueField="FID" parentField="PID"/>
                                </div>
                                <div field="SetText" width="100" headerAlign="center" align="center">复核意见</div>
                                <div field="SetTime" width="100" headeralign="center" datatype="date" align="center"
                                     dateformat="yyyy-MM-dd"
                                     allowsort="true" align="center">终审日期
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" id="SelectWindow" title="选择要中止的业务交单" width="1200" height="500" style="display:none"
         onbuttonclick="onClick">
        <div class="mini-toolbar">
            <table style="width:100%;">
                <tr>
                    <td style="width:120px">查询关键字:</td>
                    <td style="width:50%">
                        <input class="mini-textbox" id="ClientQueryText" style="width:200px">
                        <button class="mini-button mini-button-normal" onclick="doClientQuery">&nbsp;模糊搜索&nbsp;</button>
                    </td>
                    <td style="text-align: left;width:10%">
                        <span style="font-size: 20px;font-family: 黑体;color:red" id="MoneyText"></span>
                    </td>
                    <td>
                        <button class="mini-button mini-button-success" onclick="confirmDetail()">确认选择</button>
                        &nbsp;&nbsp;
                        <button class="mini-button mini-button-danger" onclick="closeMe('SelectWindow')">关闭窗口</button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="mini-fit">
            <div id="datagrid2" class="mini-datagrid" style="width:100%;height:100%" frozenStartColumn="0"
                 frozenEndColumn="4" allowHeaderWrap="true" url="/work/cancelCases/getCasesMain" autoload="false">
                <div property="columns">
                    <div type="indexcolumn">#</div>
                    <div type="checkcolumn" width="40"></div>
                    <div field="Type" width="100" headerAlign="center" align="center">业务类型</div>
                    <div field="SignTime" width="80" headerAlign="center" align="center" dateFormat="yyyy-MM-dd"
                         allowSort="true">交单时间
                    </div>
                    <div field="ContractNo" width="120" headerAlign="center" allowSort="true">合同编号</div>
                    <div field="ClientName" width="160" headerAlign="center" allowSort="true">客户名称</div>
                    <div field="DocSN" width="120" headerAlign="center" allowSort="true">业务编号</div>
                    <div field="Nums" width="120" headerAlign="center" allowSort="true">专利类型及数量</div>
                    <div field="TotalGuan" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">官费金额
                    </div>
                    <div field="UsedGuan" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">认领官费
                    </div>
                    <div field="TotalDai" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">代理费金额
                    </div>
                    <div field="UsedDai" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">已认领<br/>代理费
                    </div>
                    <div field="FreeGuan" width="70" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">应收官费<br/>结余
                    </div>
                    <div field="AcceptGuan" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">认领官费
                        <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999"
                               onenter="onEnter" value="0"/>
                    </div>
                    <div field="FreeDai" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">应收代理费<br/>结余
                    </div>
                    <div field="AcceptDai" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">认领<br/>代理费
                        <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999" value="0"
                               onenter="onEnter"/>
                    </div>
                    <div field="AcceptMoney" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">领用合计
                    </div>
                    <div field="FreeMoney" width="100" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">结余总额
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var RTypes = [];
        var grid = mini.get('#datagrid1');
        var gridD = mini.get('#gridDetail');
        var cmdRemove = mini.get('#CancelCases_Remove');
        var cmdAudit = mini.get('#CancelCases_Audit');
        var cmdSet = mini.get('#CancelCases_Set');
        var cmdBrowse = mini.get('#CancelCases_Browse');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var cmdQuery = mini.get('#FeeItem_Query');
        var fitt1 = mini.get('#fitt1');
        var gridMain = mini.get('#datagrid2');
        var win = mini.get('#SelectWindow');
        var addWin = null;
        var addIFrame=null;
        function addCancel() {
            addWin = mini.open({
                url: '/work/cancelCases/add',
                width: '900',
                height: '520',
                title: '专利交单业务中止',
                showModal: true,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    addIFrame=iframe;
                },
                ondestroy: function () {
                    grid.reload();
                }
            });
        }
        function browseCancel() {
            var row = grid.getSelected();
            if (row) {
                var ID = row['ID'];
                addWin= mini.open({
                    url: '/work/cancelCases/browse?ID=' + ID,
                    width: '900',
                    height: '560',
                    title: '专利交单业务中止',
                    showModal: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var kk = mini.clone(row);
                        kk.TypeName = row.Type;
                        kk.Nums = row.DocSN + '(' + row.Nums.replace(/ /g, '') + ")";
                        iframe.contentWindow.SetData(kk);
                    },
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function auditOne() {
            var row = grid.getSelected();
            if (row) {
                var ID = row['ID'];
                addWin= mini.open({
                    url: '/work/cancelCases/audit?ID=' + ID,
                    width: '900',
                    height: '560',
                    title: '专利交单业务中止',
                    showModal: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var kk = mini.clone(row);
                        kk.TypeName = row.Type;
                        kk.Nums = row.DocSN + '(' + row.Nums.replace(/ /g, '') + ")";
                        iframe.contentWindow.SetData(kk);
                    },
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function setOne() {
            var row = grid.getSelected();
            if (row) {
                var ID = row['ID'];
                addWin= mini.open({
                    url: '/work/cancelCases/set?ID=' + ID,
                    width: '900',
                    height: '560',
                    title: '专利交单业务中止',
                    showModal: true,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        var kk = mini.clone(row);
                        kk.TypeName = row.Type;
                        kk.Nums = row.DocSN + '(' + row.Nums.replace(/ /g, '') + ")";
                        iframe.contentWindow.SetData(kk);
                    },
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function expand(e) {
            e = e || {};
            var btn = e.sender;
            if (!btn) {
                btn = mini.get('#CancelCases_HighQuery');
            }
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("icon-collapse");
                btn.setText("隐藏");
                $('#p1').css('display', "block");
                txtQuery.hide();
                comField.hide();
                cmdQuery.hide();

            } else {
                btn.setIconCls("icon-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
                txtQuery.show();
                comField.show();
                cmdQuery.show();
            }
            fitt1.setHeight('100%');
            fitt1.doLayout();
        }

        function rowClick(e) {
            var record = e.record;
            var state = parseInt(record["State"] || 0);
            var createMan = parseInt(record["CreateMan"]);
            var auditMan = parseInt(record["AuditMan"] || 0);
            var setMan = parseInt(record["SetMan"] || 0);

            var cur = parseInt(curUserId);

            cmdAudit.hide();
            cmdSet.hide();
            cmdRemove.hide();
            if (state == 1) {//待审核
                cmdAudit.show();
            } else if (state == 2) {//待复核
                cmdSet.show();
            } else if (state == 3) {//审核失败
                if (cur == createMan) cmdRemove.show();
            } else if (state == 4) {//复核失败
                if (cur == createMan) cmdRemove.show();
            }
        }

        function remove() {
            var row = grid.getSelected();
            if (row) {
                mini.confirm('确认要删除选择的业务中止申请吗?', '删除提示', function (act) {
                    if (act == 'ok') {
                        var url = "/work/cancelCases/remove";
                        $.post(url, {ID: row["ID"]}, function (result) {
                            if (result.success) {
                                mini.alert('选择记录删除成功!');
                                grid.reload();
                            } else mini.alert(result.message || "删除失败，请稍候重试!");
                        })
                    }
                });
            } else mini.alert('选择要删除的记录!');
        }


        function afterload() {
            updateNumber();
        }

        function refreshData(grid) {
            var pa = grid.getLoadParams();
            var pageIndex = grid.getPageIndex() || 0;
            var pageSize = grid.getPageSize() || 20;
            pa = pa || {pageIndex: pageIndex, pageSize: pageSize};
            grid.load(pa);
        }

        function updateNumber() {
            var url = "/work/cancelCases/refreshTotal";
            $.getJSON(url, {}, function (result) {
                var rows = result.data || [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var cName = row["name"];
                    var num = parseInt(row["num"] || 0);
                    var con = $('.' + cName);
                    if (con.length > 0) {
                        con.text(num);
                    }
                }
            })
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
            if (curState && curCode) {
                var p = {field: curCode, oper: 'EQ', value: curState};
                result.push(p);
            }
            if (result.length > 0) {
                arg["High"] = mini.encode(result);
            }
            grid.load(arg);
            try {
                window.parent.doResize();
            } catch (e) {

            }
        }

        var grid = mini.get('#datagrid1');

        var detailGrid_Form = document.getElementById("detailGrid_Form");
        var subGrid = mini.get('#subGrid');

        function onShowRowDetail(e) {
            subGrid.setData([]);
            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            subGrid.load({CasesID: row["casesId"], ArrID: row["arrId"], Total: row["total"]})
        }

        function doExport() {
            var excel = new excelData(grid);
            excel.addEvent('beforeGetData', function (grid, rows) {
                return grid.getSelecteds();
            })
            excel.export("销售回款记录.xls");
        }

        function CancelForm1() {
            var editWindow = mini.get("editWindow1");
            editWindow.hide();
        }

        function CancelForm2() {
            var editWindow = mini.get("editWindow2");
            editWindow.hide();
        }

        function changeQuery(state) {
            var con = $(event.srcElement || e.targetElement);
            var cons = $('.Jdlcli');
            for (var i = 0; i < cons.length; i++) {
                var cx = cons[i];
                cx.classList.remove('clicked');
            }
            $(con).parents('.Jdlcli').addClass('clicked');
            curState = state;
            doQuery(state);
            try {
                window.parent.doResize();
            } catch (e) {

            }
        }

        var curState = 0;
        var code = 'State';

        function doQuery(state) {
            var arg = {};
            var cs = [];
            var ds = [];
            if (state == null || state == undefined) {
                state = curState;
            }
            var op = null;
            if (code && state) {
                op = {field: code, oper: 'EQ', value: state};
            }
            if (op) ds.push(op);

            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL" || f == "BH") f = "NEIBUBH";
                        var kWork = f + '=' + word;
                        if (cs.indexOf(kWork) == -1) {
                            var op = {field: f, oper: 'LIKE', value: word};
                            cs.push(op);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                    var op = {field: field, oper: 'LIKE', value: word};
                    cs.push(op);
                }
            }
            if (cs.length > 0) {
                arg["Query"] = mini.encode(cs);
                if (ds.length > 0) arg["High"] = mini.encode(ds);
            } else {
                arg["Query"] = mini.encode(ds);
            }

            grid.load(arg);
            try {
                window.parent.doResize();
            } catch (e) {

            }
        }

        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            mini.get('#QueryText').setValue(null);
            doQuery();
        }

        function afterDetailLoad() {
            //提交以后。但未审核时。不能再修改单据。
            var rows = gridD.getData();
            var num = 0;
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var state = parseInt(row["state"] || 0);
                if (state == 0) num++;
            }
            if (num > 0) {
                var row = grid.getSelected();
                if (row) {
                    var claimStatus = parseInt(row["ClaimStatus"] || 0);
                    if (claimStatus == 4) {
                        cmdRenLin.hide();
                    }
                }
            }
        }

        function onDraw(e) {

        }

        function onCasesClick() {
            gridMain.reload();
            win.show();
            addWin.hide();
        }

        function confirmDetail() {
            var row = gridMain.getSelected();
            if (row && addIFrame) {
                addIFrame.contentWindow.confirmDetail(row);
                addWin.show();
                win.hide();
            } else mini.alert('请选择业务交单记录!');
        }
        function changeWindowHeight(height){
            addWin.setHeight(height);
            addWin.doLayout();
            addWin.render();
        }
        function onClick(e){
           if(e.name=="close"){
               if(win){
                   if(win.getVisible()==true){
                       if(addWin){
                           if(addWin.getVisible()==false) addWin.show();
                       }
                   }
               }
           }
        }
        function doClientQuery(){
            var arg={};
            var txt=mini.get('ClientQueryText').getValue();
            if(txt){
                var cs=[];
                var fields=['Type','DocSN','Nums','ContractNo','ClientName','AllMoney'];
                for(var i=0;i<fields.length;i++){
                    var field=fields[i];
                    var obj={field:field,oper:'LIKE',value:txt};
                    cs.push(obj);
                }
                arg["Query"]=mini.encode(cs);
            }
            gridMain.load(arg);
        }
        function closeMe(winName){
            var  conWin=mini.get(winName);
            if(conWin)conWin.hide();
        }
    </script>
</@layout>
