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
    <script type="text/javascript">
        mini.parse();
        var fit = mini.get('fitt');
        $(function () {
            var forma1 = new mini.Form('#editforma1');
            var forma2 = new mini.Form('#editforma2');
            var formc2 = new mini.Form('#editformc2');

            forma1.setEnabled(false);
            forma2.setEnabled(false);
            formc2.setEnabled(false);

            $('#p1').hide();
        })
        var types = [
            {id: 1, text: '现金'},
            {id: 2, text: '转账'},
            {id: 3, text: '支付宝'},
            {id: 4, text: '微信'}
        ];
        var CATypes = [
            {id: 1, text: '待认领'},
            {id: 2, text: '已认领'},
            {id: 3, text: '驳回重领'},
            {id: 4, text: '部分认领'}
        ]
        var RTypes = [
            {id: 0, text: '待审核'},
            {id: 1, text: '驳回'},
            {id: 2, text: '同意'},
            {id: 3, text: '部分认领'},
            {id:4,text:'已退款'}
        ];
        var curUserId = parseInt("${UserID}");
        var moneyType = [{id: 1, text: '需关联业务费用'}, {id: 2, text: '无业务关联官费'}, {id: 3, text: '其他费用'},{id:4,text:'退款'}];
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
            text-align: center;
        }

        .Zlywtop ul li {
            float: left;
            margin-left: 120px;
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

        .info2bottom ul li:hover {
            background-color: rgb(216, 228, 250)
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
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center" showheader="false">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-2">
                                <div id="info0" style="height:80px;background-color: rgb(63,87,131);overflow: hidden;border-radius:3px;
                            -moz-box-shadow: 2px 2px 10px rgb(63,87,131);-webkit-box-shadow: 2px 2px 10px rgb(63,87,131);-shadow:2px 2px 10px rgb(63,87,131);">
                                    <div class="file-list">
                                        <div class="Zlyw" style="margin-left: 18px;margin-top: 5px;">
                                            <img src="/appImages/zongheicon.png" alt="销售回款汇总">
                                            <h5>销售回款汇总</h5>
                                        </div>
                                        <div class="Zlywtop">
                                            <ul>
                                                <li class="Jdlcli" id="Z1">
                                                    <a style="text-decoration:none;width:180px" target="_self" href="#"
                                                       onclick="changeQuery('ClaimStatus',0,this)">
                                                        <span id="J1span">全部</span>&nbsp;
                                                        <h4 class="x1">1</h4>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="mini-col-6">
                                <div id="info1"
                                     style="height:80px;background:rgb(226,250,252);border:1px solid rgb(190,226,240);border-radius: 5px;">
                                    <div class="info1top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(214,239,243);text-align: center;">
                                        <h2 style="color: rgb(3,154,209);margin-left: 5px;margin-top: 4px;">业务人员认领</h2>
                                    </div>
                                    <div class="info1bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival" id="J1" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('ClaimStatus',1,this);">
                                                    <span id="J1span">待认领</span>
                                                    <h4 class="x2"></h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js1" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('ClaimStatus',4,this)">
                                                    <span id="Js1span">部分认领</span>
                                                    <h4 id="Js1h4" class="x5" style="width:120px"></h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="J2" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('ClaimStatus',3,this);">
                                                    <span id="J2span">驳回重领</span>
                                                    <h4 id="J2h4" class="x4"></h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="J3" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('ClaimStatus',2,this);">
                                                    <span id="J3span">已认领</span>
                                                    <h4 id="J3h4" class="x3"></h4>
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
                                        <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">财务复核</h2>
                                    </div>
                                    <div class="info2bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival" id="Js1" style="width:150px">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('ReviewerStatus',0,this)">
                                                    <span id="Js1span">待复核</span>
                                                    <h4 id="Js1h4" class="x6" style="width:120px">5</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js2" style="width:150px">
                                                <a style="text-decoration:none;width:200px" target="_self" href="#"
                                                   class="active_a"
                                                   onclick="changeQuery('ReviewerStatus',2,this)">
                                                    <span id="Js2span">复核同意</span>
                                                    <h4 id="Js2h4" class="x7">0</h4>
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
                            <a class="mini-button" iconcls="icon-add" id="ArrivalRegistrationBrowse_Add"
                               onclick="addPayBack">增加回款</a>
                            <a class="mini-button" iconcls="icon-edit" id="ArrivalRegistrationBrowse_Edit"
                               onclick="editPayBack" visible="false">编辑回款</a>
                            <a class="mini-button" iconcls="icon-remove" id="ArrivalRegistrationBrowse_Remove"
                               onclick="remove" visible="false">删除回款</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconcls="icon-ok" id="ArrivalRegistrationBrowse_Renlin"
                               onclick="addSWRY" visible="false">认领回款</a>
                            <a class="mini-button" iconcls="icon-ok" id="ArrivalRegistrationBrowse_Caiwufuhe"
                               onclick="addCWRY" visible="false">财务复核</a>
                            <a class="mini-button" iconCls="icon-no" id="ArrivalRegistrationBrowse_Quxiaofuhe"
                               onclick="cancelAudit" visible="false">取消复核
                            </a>
                            <a class="mini-button" iconCls="icon-reload" id="ArrivalRegistrationBrowse_Browse"
                               onclick="browsePayBack"  visible="false">查看回款
                            </a>
                            <#if RoleName=="财务人员" || RoleName=="系统管理员">
                                <a class="mini-button" iconCls="icon-reload" onclick="showReport()" >统计分析</a>
                            </#if>
                            <span class="separator"></span>
                            <a class="mini-button mini-button-info" id="ArrivalRegistrationBrowse_Export"
                               onclick="doExport">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field FeeItem_Query"  id="comField" style="width:100px"
                                 data="[{id:'All',text:'全部属性'},{id:'DocumentNumber',text:'单据编号'},{id:'SignMan',text:'登记人'},
                                 {id:'PaymentAccount',text:'付款账户'},{id:'PaymentAmount',text:'回款金额'},{id:'Payer',text:'付款人'},{id:'ReturnBank',
                                 text:'回款银行'},{id:'Description',text:'款项描述'},{id:'RLR',text:'客户名称'}]" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field FeeItem_Query" style="width:200px" id="QueryText" />
                            <a class="mini-button mini-button-success" onclick="doQuery();" id="FeeItem_Query">模糊查询</a>
                            <a class="mini-button mini-button-danger" id="FeeItem_Reset" onclick="reset">重置条件</a>
                            <a class="mini-button mini-button" id="ArrivalRegistrationBrowse_HighQuery" iconCls="icon-expand"
                               onclick="expand">高级查询</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:240px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">回款日期：</td>
                            <td style="width:13%;">
                                <input name="DateOfPayment" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="DateOfPayment" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">登记日期：</td>
                            <td style="width:15%;">
                                <input name="AddTime" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="AddTime" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">登记人：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="DJR"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">回款方式：</td>
                            <td style="width:15%;"><input class="mini-combobox" data="types" name="PaymentMethod"
                                                          data-oper="EQ"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">付款账户：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="PaymentAccount"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">回款金额：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="EQ" name="PaymentAmount"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">付款人：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="Payer"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">回款银行：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="ReturnBank"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">款项描述：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="Description"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">认领状态：</td>
                            <td style="width:15%;"><input class="mini-combobox" data="CATypes" name="ClaimStatus"
                                                          data-oper="EQ" style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">认领日期：</td>
                            <td style="width:15%;">
                                <input name="ClaimDate" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="ClaimDate" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">认领人：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="RLR"
                                                          style="width:100%"/></td>

                            <td style="width:6%;padding-left:10px;">复核状态：</td>
                            <td style="width:15%;"><input class="mini-combobox" name="ReviewerStatus" data="RTypes"
                                                          data-oper="EQ" style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">审核日期：</td>
                            <td style="width:15%;">
                                <input name="ReviewerDate" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="ReviewerDate" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">客户：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="CustomerName"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">单据号：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="DocumentNumber"
                                                          style="width:100%"/></td>
                        </tr>

                        <tr>
                            <td style="width:6%;padding-left:10px;">是否退款：</td>
                            <td style="width:15%;">
                                <input class="mini-combobox" name="HasRefund" data="[{id:0,text:'无'},{id:1, text:'有'}]"
                                       data-oper="EQ" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;"></td>
                            <td style="width:15%;">
                            </td>
                            <td style="width:6%;padding-left:10px;"></td>
                            <td style="width:15%;"></td>
                            <td style="width:6%;padding-left:10px;">：</td>
                            <td style="width:15%;"></td>
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
                             allowresize="true" url="/finance/arrival/getData" multiselect="true"
                             pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="AddTime" sortorder="desc"
                             autoload="true" onDrawCell="onDraw" onload="afterload" onrowClick="rowClick">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="DateOfPayment" width="100" headeralign="center" datatype="date"
                                     dateformat="yyyy-MM-dd" allowsort="true" align="center">回款日期
                                </div>
                                <div field="AddTime" width="100" headeralign="center" datatype="date"
                                     dateformat="yyyy-MM-dd" allowsort="true" align="center">登记日期
                                </div>
                                <div field="DocumentNumber" width="140" headerAlign="center" align="center">单据编号</div>
                                <div field="Description" width="120" headeralign="center" align="center">款项描述</div>
                                <div field="PaymentMethod" width="80" headerAlign="center" type="treeselectcolumn"
                                     allowsort="true" align="center">回款方式
                                    <div property="editor" class="mini-combobox" data="types"></div>
                                </div>
                                <div field="PaymentAmount" width="140" headeralign="center" allowsort="true"
                                     align="center">回款金额
                                </div>
                                <div field="FreeMoney" width="100" headeralign="center" allowsort="true"
                                     align="center">剩余可领金额
                                </div>
                                <div field="Payer" width="140" headeralign="center" align="center">付款人</div>
                                <div field="PaymentAccount" width="180" headeralign="center" align="center">付款账户</div>
                                <div field="ReturnBank" width="140" headeralign="center" align="center">回款银行</div>
                                <div field="ClaimStatus" width="80" headerAlign="center" align="center"
                                     type="comboboxcolumn"
                                     allowsort="true">认领状态
                                    <div property="editor" class="mini-combobox" data="CATypes"></div>
                                </div>
<#--                                <div field="Claimant" width="80" headeralign="center" type="treeselectcolumn"-->
<#--                                     allowsort="true" align="center">认领人-->
<#--                                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"-->
<#--                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="Claimant"-->
<#--                                           style="width:-->
<#--                               98%;" required="true" resultAsTree="false"/>-->
<#--                                </div>-->
                                <div field="RLR" width="80" headeralign="center"allowsort="true" align="center">认领人</div>
                                <div field="ClaimDate" width="100" headeralign="center" datatype="date" align="center"
                                     dateformat="yyyy-MM-dd"
                                     allowsort="true" align="center">认领日期
                                </div>
                                <#--<div field="KHName" width="180" headeralign="center" align="center">客户名称</div>-->
                                <div field="ReviewerStatus" name="ReviewerStatus" width="80" headerAlign="center"
                                     type="comboboxcolumn" allowsort="true" align="center">复核状态
                                    <div property="editor" class="mini-combobox" data="RTypes"></div>
                                </div>
<#--                                <div field="Reviewer" width="80" headeralign="center" type="treeselectcolumn"-->
<#--                                     allowsort="true" align="center">复核人-->
<#--                                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"-->
<#--                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="Claimant"-->
<#--                                           style="width:-->
<#--                               98%;" required="true" resultAsTree="false"/>-->
<#--                                </div>-->
                                <div field="FHR" name="FHR"  allowsort="true" align="center"  width="80"
                                     headeralign="center">复核人</div>
                                <div field="ReviewerDate" width="100" headeralign="center" datatype="date"
                                     dateformat="yyyy-MM-dd" allowsort="true" align="center">复核日期
                                </div>
                                <div field="SignMan" width="80" headeralign="center" type="treeselectcolumn"
                                     allowsort="true" align="center">登记人
                                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="SignMan"
                                           style="width:
                               98%;" required="true" resultAsTree="false"/>
                                </div>
                                <div field="Reviewer" name="Reviewer" visible="false"></div>
                                <div field="OfficalFee" name="OfficalFee" visible="false">官费</div>
                                <div field="AgencyFee" name="AgencyFee" visible="false">代理费</div>
                                <div field="Note" name="Note">备注</div>
                                <div field="DJR" name="DJR" visible="false"></div>
                                <div field="RLR" name="RLR" visible="false"></div>
                                <div field="FHR" name="FHR" visible="false"></div>
                            </div>
                        </div>
                        <input class="mini-hidden" name="LoginUserID" id="LoginUserID" value="${UserID}"/>
                        <input class="mini-hidden" name="RoleName" id="RoleName" value="${RoleName}"/>
                        <input class="mini-hidden" name="RoleID" id="RoleID" value="${RoleID}"/>
                    </div>
                    <div region="south" height="220" title="领用明细">
                        <div class="mini-datagrid" id="gridDetail" style="width:100%;height:100%" id="grid1"
                             autoload="false" ondrawcell="onMainDraw" showPager="false"  pageSize="50"
                             showSummaryRow="true" sortField="moneyType" sortOrder="Asc"
                             onshowrowdetail="onShowRowDetail" url="/arrivalUse/getDetail"
                             ondrawsummarycell="drawSummary" onload="afterDetailLoad">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div width="60" field="Action" align="center" type="expandcolumn" headerAlign="center">
                                    #
                                </div>
                                <div field="state" align="center" headerAlign="center" type="comboboxcolumn">审核状态
                                    <input property="editor" data="RTypes" class="mini-combobox"/>
                                </div>
                                <div width="120" type="comboboxcolumn" field="moneyType" headerAlign="center"
                                     required="true" align="center">费用类型
                                    <input property="editor" class="mini-combobox" data="moneyType" allowInput="true"
                                           valueFromSelect="true"/>
                                </div>
                                <div width="250" field="clientId" type="treeselectcolumn" headerAlign="center"
                                     required="true" align="center">客户名称
                                    <input property="editor" class="mini-treeselect" allowInput="true"
                                           valueFromSelect="true" url="/systems/client/getClientTree"/>
                                </div>
                                <div width="120" align="center" field="guan" headerAlign="center"  vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">官费
                                </div>
                                <div width="120" align="center" field="dai" headerAlign="center"  vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">代理费
                                </div>
                                <div width="120" align="center" field="total" headerAlign="center" summaryType="sum" dataFormat="float" numberFormat="n2">合计
                                </div>
                                <div field="createMan" align="center" headerAlign="center"
                                     type="treeselectcolumn">领取人
                                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="SignMan"
                                           style="width:
                               98%;" required="true" resultAsTree="false"/>
                                </div>
                                <div field="createTime" width="150" align="center" headerAlign="center"
                                     datatype="date"
                                     dateformat="yyyy-MM-dd HH:mm:ss">领取时间
                                </div>
                                <#--                                <div width="150" field="memo" heaaderAlign="center">备注-->
                                <#--                                    <input property="editor" class="mini-textbox"/>-->
                                <#--                                </div>-->
                                <div field="auditMan" align="center" headerAlign="center"
                                     type="treeselectcolumn">审核人
                                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="SignMan"
                                           style="width:
                               98%;" required="true" resultAsTree="false"/>
                                </div>
                                <div field="auditTime" width="150" align="center" headerAlign="center" datatype="date"
                                     dateformat="yyyy-MM-dd HH:mm:ss">审核时间
                                </div>
                                <div field="auditMemo" align="center" headerAlign="center">审核意见</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#datagrid1');
        var gridD = mini.get('#gridDetail');
        var cmdEdit = mini.get('#ArrivalRegistrationBrowse_Edit');
        var cmdRemove = mini.get('#ArrivalRegistrationBrowse_Remove');
        var cmdRenLin = mini.get('#ArrivalRegistrationBrowse_Renlin');
        var cmdFuHe = mini.get('#ArrivalRegistrationBrowse_Caiwufuhe');
        var cmdBrowse=mini.get('#ArrivalRegistrationBrowse_Browse');
        var cmdCancelAudit=mini.get('#ArrivalRegistrationBrowse_Quxiaofuhe');
        var txtQuery=mini.get('#QueryText');
        var comField=mini.get('#comField');
        var cmdQuery=mini.get('#FeeItem_Query');
        var fitt1 = mini.get('#fitt1');
        function addPayBack() {
            mini.open({
                url: '/finance/arrival/add',
                width: '100%',
                height: '100%',
                title: '新增销售回款',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            });
        }
        $(function(){

            cmdEdit.hide();
            cmdRenLin.hide();
            cmdRemove.hide();
            cmdFuHe.hide();
            cmdCancelAudit.hide();
            cmdBrowse.hide();
        })
        function expand(e) {
            e = e || {};
            var btn = e.sender;
            if (!btn) {
                btn = mini.get('#ArrivalRegistrationBrowse_HighQuery');
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
            var arrId = record["ArrivalRegistrationID"];
            gridD.load({ArrID: arrId,showAll:1});
            fitt1.expandRegion('south');
            var claimStatus = parseInt(record["ClaimStatus"] || 0);
            var RevStatus = parseInt(record["ReviewerStatus"] || 0);
            var Claimant = parseInt(record["Claimant"] || 0);
            var signMan = parseInt(record["SignMan"] || 0);
            var reviewMan=parseInt(record["Reviewer"] || 0);
            var cur = parseInt(curUserId);

            cmdRenLin.hide();
            cmdFuHe.hide();
            cmdRemove.hide();
            cmdEdit.hide();
            cmdBrowse.hide();
            cmdCancelAudit.hide();
            if (claimStatus == 0 ||
                claimStatus == 1 ||
                claimStatus == 4) {
                if (Claimant > 0) {
                    if (Claimant == curUserId) cmdRenLin.show();
                } else {
                    cmdRenLin.show();
                }
            }
            if(claimStatus == 3){
                cmdRenLin.show();
                if (curUserId == signMan) cmdRemove.show();
            }
            if (claimStatus == 1 && Claimant == 0) {
                cmdEdit.show();
                if (curUserId == signMan) cmdRemove.show();
            }
            if (RevStatus == 0 || RevStatus == 3) {
                if (claimStatus == 2)
                {
                    cmdFuHe.setText('财务复核');
                    cmdFuHe.show();
                }
            }
            if(claimStatus>0){
                if(Claimant==curUserId || signMan==curUserId || reviewMan==curUserId){
                    cmdBrowse.show();
                }
            }
            if(claimStatus==2 && RevStatus==2){
                cmdCancelAudit.show();
                cmdFuHe.setText('查看');
                cmdFuHe.show();
            }
        }

        function onMainDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "Action") {
                var type = parseInt(record["moneyType"]);
                var id = parseInt(record["id"] || 0);
                if (type == 1) {

                } else {
                    e.cellHtml = "";
                }
            }else if(field=="total") {
                var guan=parseFloat(record["guan"] || 0);
                var dai=parseFloat(record["dai"] || 0);
                var total=guan+dai;
                if(type!=1){
                    if(total==0){
                        total=parseFloat(record["total"] || 0);
                    }
                }
                if(total!=0) {
                    e.value=total;
                    e.cellHtml=total.toFixed(2);
                    if(total<0)e.rowStyle="color:red";
                } else e.cellHtml="";
            }
            var state = parseInt(record["state"] || 0);
            if (state == 1) {
                e.rowStyle = "text-decoration:line-through;text-decoration-color: black;color:red";
            }
        }

        function drawSummary(e) {
            var field = e.field;
            var records = e.data || [];
            if (field == 'total') {
                var total = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["state"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["total"] || 0);
                    total += d;
                }
                e.cellHtml = total.toFixed(2);
            } else if (field == "dai") {
                var total1 = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["state"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["dai"] || 0);
                    total1 += d;
                }
                e.cellHtml = total1.toFixed(2);

            } else if (field == "guan") {
                var total2 = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["state"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["guan"] || 0);
                    total2 += d;
                }
                e.cellHtml = total2.toFixed(2);
            }
        }

        function editPayBack() {
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择要编辑的收款信息!');
                return;
            }
            var ID = row['ArrivalRegistrationID'];
            mini.open({
                url: '/finance/arrival/edit?ArrivalRegistrationID=' + ID,
                width: '100%',
                height: '100%',
                title: '修改销售回款',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            })
        }
        function browsePayBack(){
            var row=grid.getSelected();
            if(row){
                var ID = row['ArrivalRegistrationID'];
                mini.open({
                    url: '/finance/arrival/browse?ArrivalRegistrationID=' + ID,
                    width: '100%',
                    height: '100%',
                    title: '查看回款',
                    showModal: true,
                    onDestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            var ClaimStatus = "";
            var isexists = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["ArrivalRegistrationID"]);
                // if (rows[i]["ClaimStatus"] != 1) {
                //     isexists.push("y");
                // } else {
                //     isexists.push("n");
                // }
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            // if (isexists.length > 0) {
            //     if (isexists.indexOf('y') > -1) {
            //         mini.alert("您选择的数据已存在认领记录，无法完成删除操作");
            //         return;
            //     }
            // }
            var record=rows[0];
            // var Claimant = parseInt(record["Claimant"] || 0);
            // if(Claimant>0){
            //     if(Claimant!=curUserId){
            //         mini.alert('只能由申请者本人才能删除记录!');
            //         return ;
            //     }
            // } else {
            //
            // }

            var signMan=parseInt(record["SignMan"]);
            if(signMan!=curUserId){
                mini.alert('只能由登记人本人才能删除回款记录!');
                return ;
            }
            mini.confirm('确认要删除的销售回款数据?', '删除提示', function (act) {
                if (act == 'ok') {
                    var url = "/finance/arrival/remove";
                    $.ajax({
                        contentType: 'application/json',
                        method: 'post',
                        url: url,
                        data: mini.encode(ids),
                        success: function (r) {
                            if (r['success']) {
                                mini.alert("删除成功！", '删除提示', function () {
                                    grid.reload();
                                });

                            } else mini.alert("删除失败！");
                        },
                        failure: function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function addSWRY() {
            var row = grid.getSelected();
            if (row == "") {
                mini.alert("请选择要填写的回款信息!");
                return;
            }
            var ID = row['ArrivalRegistrationID'];
            mini.open({
                url: '/finance/arrival/renlin?ArrivalRegistrationID=' + ID,
                width: '100%',
                height: '100%',
                title: '认领',
                showModal: true,
                onDestroy: function () {
                    grid.reload();
                }
            });
        }

        function addCWRY() {
            var row = grid.getSelected();
            if (row == "") {
                mini.alert("请选择要填写的回款信息!");
                return;
            }

            var ID = row['ArrivalRegistrationID'];
            mini.open({
                url: '/finance/arrival/fuhe?ArrivalRegistrationID=' + ID,
                width: '100%',
                height: '100%',
                title: '复核',
                showModal: true,
                onDestroy: function () {
                    grid.reload();
                }
            });
        }

        function onCustomDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/finance/ClientWindow/index",
                title: "选择客户",
                width: 650,
                height: 380,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            var _text = "";
                            var _value = "";
                            for (var i = 0; i < data.length; i++) {
                                _text = _text + data[i].Name + ",";
                                _value = _value + data[i].ClientID + ",";
                            }
                            if (_text) _text = _text.substring(0, _text.length - 1);
                            if (_value) _value = _value.substring(0, _value.length - 1);
                            var _kh = mini.get('Customer');
                            _kh.setText(_text);
                            _kh.setValue(_value);
                        }
                    }
                }
            });
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
            var url = "/finance/arrival/refreshTotal";
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

        function onDraw(e) {
            var field = e.field;
            var record = e.record;

            if (field == "ClaimStatus") {
                var dd = record['ClaimStatus'];
                if (dd == '2') e.cellHtml = '<SPAN style="color:red">已认领</span>';
                if (dd == '3') e.cellHtml = '<SPAN style="color:#ff6a00">驳回重领</span>';
                if (dd == '4') e.cellHtml = '<SPAN style="color:#ff6a00">部分认领</span>';
            }
            if (field == "ReviewerStatus") {
                var dd = record['ReviewerStatus'];
                if (dd == "1") {
                    e.cellHtml = '<span style="color: black">驳回</span>'
                } else if (dd == '2') {
                    e.cellHtml = '<SPAN style="color:red">同意</span>';
                } else if (dd == 3) {
                    e.cellHtml = '<SPAN style="color:red">部分认领</span>';
                } else {
                    e.cellHtml = '<span></span>';
                }
            }
            if (field == "PaymentAmount") {
                var dd = record['PaymentAmount'];
                if (dd != "") {
                    e.cellHtml = dd + "(" + smalltoBIG(dd) + ")";
                }
            }
            var hasRefund=parseInt(record["HasRefund"] || 0);
            if(hasRefund>0){
                e.rowStyle="font-family:黑体;color:red";
            }
        }

        function STG(val, id) {
            var BIGAmount = val + "(" + smalltoBIG(val) + ")";
            mini.get(id).setValue(BIGAmount);
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
            if(curState && curCode){
                var p={field:curCode, oper: 'EQ', value:curState};
                result.push(p);
            }
            if(result.length>0){
                arg["High"] = mini.encode(result);
            }
            grid.load(arg);
            try {
                window.parent.doResize();
            } catch (e) {

            }
        }
        function showReport(){
            mini.open({
                url:'/finance/arrival/report',
                width: 1200,
                height: 650,
                showModal:true,
                title:'统计分析'
            });
        }
    </script>
</@layout>
<@layout>
    <div id="editWindow1" class="mini-window" title="商务人员填写" showmodal="true" allowresize="true" allowdrag="true"
         style="width:1100px;">
        <div id="editforma1" class="form">
            <input class="mini-hidden" name="ArrivalRegistrationID"/>
            <table style="width:100%;">
                <tr>
                    <td class="showlabel">款项描述：</td>
                    <td>
                        <textarea class="mini-textbox" id="Description" name="Description"
                                  style="width:100%"></textarea>
                    </td>
                    <td class="blank"><span style="color: red">&nbsp;</span></td>
                </tr>
                <tr>
                    <td class="showlabel">回款日期:</td>
                    <td>
                        <input class="mini-datepicker" style="width: 100%;" format="yyyy-MM-dd H:mm:ss"
                               timeformat="H:mm:ss" showtime="true" required="true" name="DateOfPayment"/>
                    </td>
                    <td class="blank"><span style="color: red">&nbsp;</span></td>
                    <td class="showlabel">回款方式:</td>
                    <td>
                        <input class="mini-combobox" name="PaymentMethod" id="PaymentMethod" data="types"
                               style="width: 100%"/>
                    </td>
                </tr>
                <tr>
                    <td class="showlabel">
                        付款账户:
                    </td>
                    <td>
                        <input class="mini-textbox" name="PaymentAccount" style="width: 100%;"/>
                    </td>
                    <td class="blank"><span style="color: red">&nbsp;</span></td>
                    <td class="showlabel">回款金额:</td>
                    <td>
                        <input class="mini-textbox" name="PaymentAmount" id="PaymentAmounts1" style="width: 100%;"/>
                    </td>
                </tr>
                <tr>
                    <td class="showlabel">
                        付款人:
                    </td>
                    <td>
                        <input class="mini-textbox" name="Payer" id="Payer" style="width: 100%;"/>
                    </td>
                    <td class="blank"><span style="color: red">&nbsp;</span></td>
                    <td class="showlabel">回款银行：</td>
                    <td id="colBank">
                        <input class="mini-textbox" name="ReturnBank" style="width: 100%;"/>
                    </td>
                </tr>
            </table>
        </div>
        <p></p>
        <div id="editform1" class="form">
            <input class="mini-hidden" name="ArrivalRegistrationID"/>
            <input class="mini-hidden" name="ClaimStatus"/>
            <table style="width:100%;">
                <tr>
                    <td>官费:</td>
                    <td style="width:435px;">
                        <input class="mini-textbox" name="OfficalFee" id="OfficalFees" vtype="float" value="0"
                               style="width: 100%;" required="true" onclick="setNull(this.id)"/>
                    </td>
                    <td style="width:100px;">代理费:</td>
                    <td>
                        <input class="mini-textbox" name="AgencyFee" id="AgencyFees" vtype="float" value="0"
                               style="width: 100%;" required="true" onclick="setNull(this.id)"/>
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px">
                        所属客户:
                    </td>
                    <td>
                        <input name="CustomerID" id="Customer" style="width: 100%;" textname="KHName"
                               class="mini-buttonedit" onbuttonclick="onCustomDialog" allowInput="false"
                               required="true"/>
                    </td>
                    <td>费用用途备注:</td>
                    <td>
                        <input class="mini-textbox" name="Remark" style="width: 100%;"/>
                        <input class="mini-hidden" name="DocumentNumber" id="DocumentNumber"/>
                        <input class="mini-hidden" name="SignMan" id="SignMan"/>
                        <input class="mini-hidden" name="AddTime" id="AddTime"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="text-align:center">
                        <button class="mini-button" style="width:80px" id="SaveForm1" onclick="SaveForm1">确定</button>
                        <button class="mini-button" style="width:80px;" id="CancelForm1" onclick="CancelForm1">取消
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="editWindow2" class="mini-window" title="财务人员填写" showmodal="true" allowresize="true" allowdrag="true"
         style="width:1100px;">
        <div id="editforma2" class="form" enabled="false">
            <input class="mini-hidden" name="ArrivalRegistrationID"/>
            <table style="width:100%;">
                <tr>
                    <td style="width: 100px">款项描述：</td>
                    <td>
                        <textarea class="mini-textbox" name="Description"
                                  style="width:100%"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>回款日期:</td>
                    <td>
                        <input class="mini-datepicker" style="width: 100%;" format="yyyy-MM-dd H:mm:ss"
                               timeformat="H:mm:ss" showtime="true" required="true" name="DateOfPayment"
                               id="DateOfPayment"/>
                    </td>
                    <td style="width: 100px">回款方式:</td>
                    <td>
                        <input class="mini-combobox" name="PaymentMethod" data="types"
                               style="width: 100%"/>
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px">
                        付款账户:
                    </td>
                    <td>
                        <input class="mini-textbox" name="PaymentAccount" id="PaymentAccount" style="width: 100%;"/>
                    </td>
                    <td>回款金额:</td>
                    <td>
                        <input class="mini-textbox" name="PaymentAmount" id="PaymentAmounts2" style="width: 100%;"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        付款人:
                    </td>
                    <td>
                        <input class="mini-textbox" name="Payer" style="width: 100%;"/>
                    </td>
                    <td>回款银行：</td>
                    <td id="colBank">
                        <input class="mini-textbox" id="ReturnBank" name="ReturnBank" style="width: 100%;"/>
                    </td>
                </tr>
            </table>
        </div>
        <p></p>
        <div id="editformc2" class="form">
            <input class="mini-hidden" name="ArrivalRegistrationID"/>
            <table style="width:100%;">
                <tr>
                    <td>官费:</td>
                    <td style="width:435px;">
                        <input class="mini-textbox" name="OfficalFee" id="OfficalFee" style="width: 100%;"/>
                    </td>
                    <td style="width:100px;">代理费:</td>
                    <td>
                        <input class="mini-textbox" name="AgencyFee" id="AgencyFee" style="width: 100%;"/>
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px">
                        所属客户:
                    </td>
                    <td>
                        <input class="mini-textbox" name="KHName" id="KHName" style="width: 100%;"/>
                    </td>
                    <td>费用用途备注:</td>
                    <td>
                        <input class="mini-textbox" name="Remark" id="Remark" style="width: 100%;"/>
                    </td>
                </tr>
            </table>
        </div>
        <p></p>
        <div id="editform2" class="form">
            <input class="mini-hidden" name="ArrivalRegistrationID"/>
            <table style="width:100%;">
                <tr>
                    <td style="width:100px;">
                        复核结果:
                    </td>
                    <td>
                        <input class="mini-combobox" name="ReviewerStatus" id="ReviewerStatus" data="RTypes"
                               style="width:10%"/>
                    </td>
                </tr>
                <tr>
                    <td style="width:100px;">复核备注:</td>
                    <td>
                        <input class="mini-textbox" id="Note" name="Note" style="width: 98%;" required="true"/>
                        <input class="mini-hidden" name="DocumentNumber" id="DocumentNumber"/>
                        <input class="mini-hidden" name="SignMan" id="SignMan"/>
                        <input class="mini-hidden" name="AddTime" id="AddTime"/>
                        <input class="mini-hidden" name="CustomerID" id="CustomerID"/>
                        <input class="mini-hidden" name="Claimant" id="Claimant"/>
                        <input class="mini-hidden" name="ClaimDate" id="ClaimDate"/>
                        <input class="mini-hidden" name="ClaimStatus" id="ClaimStatus"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="text-align:center">
                        <button class="mini-button" style="width:80px" id="SaveForm2" onclick="SaveForm2">确定</button>
                        <button class="mini-button" style="width:80px;" id="CancelForm2" onclick="CancelForm2">取消
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <form method="post" action="/finance/arrival/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <div id="detailGrid_Form" style="display:none;">
        <div id="subGrid" class="mini-datagrid" style="width:100%;height:150px;" showPager="false"
             url="/arrivalUse/getSub">
            <div property="columns">
                <div field="SignTime" width="100" allowSort="true" headerAlign="center"  align="center"
                     dateFormat="yyyy-MM-dd">交单日期</div>
                <div field="Type" width="100" headerAlign="center" align="center" allowSort="true">业务类型</div>
                <div field="DocSN" width="120" headerAlign="center" allowSort="true">业务编号</div>
                <div field="Nums" width="180" align="center" headerAlign="center">业务类型及数量</div>
                <div field="ContractNo" width="180" align="center" headerAlign="center">合同编号</div>
                <div field="Guan" width="100" allowSort="true" headerAlign="center" align="right">领用官费</div>
                <div field="Dai" width="100" allowSort="true" headerAlign="center" align="right">领用代理费</div>
                <div field="Total" width="100" allowSort="true" headerAlign="center" align="right">领用总额</div>
                <div field="CreateTime" width="100" allowSort="true" headerAlign="center" dateFormat="yyyy-MM-dd">领用日期
                </div>
                <div field="CreateMan" width="100" allowSort="true" headerAlign="center">领用人</div>
            </div>
        </div>
    </div>
    <script>
        mini.parse();
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

        function SaveForm1() {
            var row = grid.getSelected();
            if (!row) {
                return;
            }
            if (jisuanValue(mini.get('PaymentAmounts1').getValue(), mini.get('OfficalFees').getValue(), mini.get('AgencyFees').getValue()) == "N") {
                mini.alert("官费和代理费相加必须等于回款金额");
                return;
            }
            var editForm = new mini.Form("#editWindow1");
            editForm.validate();
            if (editForm.isValid()) {
                editForm.loading("保存中......");
                var arg = {
                    Data: mini.encode(editForm.getData()),
                };
                $.post("/finance/arrival/saveRenLin", arg,
                    function (result) {
                        var res = mini.decode(result);
                        if (res.success) {
                            var data = res.data || {};
                            mini.alert('数据保存成功', '系统提示', function () {
                                grid.reload();
                                mini.get("editWindow1").hide();
                            });
                        } else {
                            mini.alert(res.Message);
                        }
                    }
                );
            }
        }

        function SaveForm2() {
            var row = grid.getSelected();
            if (!row) {
                return;
            }
            var editForm = new mini.Form("#editWindow2");
            editForm.validate();
            if (editForm.isValid()) {
                editForm.loading("保存中......");
                var arg = {
                    Data: mini.encode(editForm.getData()),
                };
                $.post("/finance/arrival/saveFuHe", arg,
                    function (result) {
                        var res = mini.decode(result);
                        if (res.success) {
                            var data = res.data || {};
                            mini.alert('数据保存成功', '系统提示', function () {
                                grid.reload();
                                mini.get("editWindow2").hide();
                            });
                        } else {
                            mini.alert(res.Message);
                        }
                    }
                );
            }
        }

        function STG(val, id) {
            var BIGAmount = val + "(" + smalltoBIG(val) + ")";
            mini.get(id).setValue(BIGAmount);
        }

        function setNull(id) {
            mini.get(id).setValue("");
        }//金额大小写转换
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

        function jisuanValue(PaymentAmount, OfficalFee, AgencyFee) {
            var p = PaymentAmount.replace(/[^\d.]/g, '');
            var o = OfficalFee.replace(/[^\d.]/g, '');
            var a = AgencyFee.replace(/[^\d.]/g, '');
            if (Number(o) + Number(a) != p) {
                return "N";
            } else {
                return "Y";
            }
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

        function changeQuery(code, state, obj) {
            var con = $(event.srcElement || e.targetElement);
            var cons = $('.Jdlcli');
            for (var i = 0; i < cons.length; i++) {
                var cx = cons[i];
                if (cx.className == "Jdlcli Arrival" || cx.className == "Jdlcli Arrival clicked") {
                    cx.children[0].children[0].style.cssText = "color:rgb(0, 159, 205);";
                    cx.children[0].children[1].style.cssText = "color:rgb(0, 159, 205);";
                }
                cx.classList.remove('clicked');
            }
            $(con).parents('.Jdlcli').addClass('clicked');
            obj.children[0].style.cssText = "color:#fff";
            obj.children[1].style.cssText = "color:#fff";
            curCode=code;
            curState=state;
            doQuerys(code, state);
            try {
                window.parent.doResize();
            } catch (e) {

            }
        }

        var curCode='ClaimStatus',curState=0;
        function doQuery(code, state) {
            var arg = {};
            var cs = [];
            var ds=[];
            if(code==null || code==undefined){
                code=curCode;
                state=curState;
            }
            var op=null;
            if (code && state) {
                op = {field: code, oper: 'EQ', value: state};
            } else if (code == "ReviewerStatus" && state.toString() == "0") {
                op = {field: code, oper: 'EQ', value: state};
            }
            if(op)ds.push(op);

            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL" || f=="BH") f = "NEIBUBH";
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
            if (cs.length > 0)
            {
                arg["Query"] = mini.encode(cs);
                if(ds.length>0)arg["High"]=mini.encode(ds);
            } else {
                arg["Query"]=mini.encode(ds);
            }

            grid.load(arg);
            try {
                window.parent.doResize();
            } catch (e) {

            }
        }

        function doQuerys(code, state) {
            var arg = {};
            var cs = [];
            if (code && state) {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            } else if (code == "ReviewerStatus" && state.toString() == "0") {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
                var ops = {field: 'ClaimStatus', oper: 'EQ', value: 2};
                cs.push(ops);
            }
            if(code=="ReviewerStatus"){
                if(state==0){
                    grid.setSortField('ClaimDate');
                }
                else if(state==2){
                    grid.setSortField('ReviewerDate');
                }
                grid.setSortOrder('desc');
            }
            if (cs.length > 0) arg["High"] = mini.encode(cs);
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
        function afterDetailLoad(){
            //提交以后。但未审核时。不能再修改单据。
            var rows=gridD.getData();
            var num=0;
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                var state=parseInt(row["state"] || 0);
                if(state==0)num++;
            }
            if(num>0){
                var row=grid.getSelected();
                if(row){
                    var claimStatus = parseInt(row["ClaimStatus"] || 0);
                    if(claimStatus==4){
                        cmdRenLin.hide();
                    }
                }
            }
        }
        function cancelAudit(){
            var row=grid.getSelected();
            if(row){
                var claimStatus = parseInt(row["ClaimStatus"] || 0);
                var ReviewerStatus=parseInt(row["ReviewerStatus"] || 0);
                var id=parseInt(row["ArrivalRegistrationID"]);
                if(claimStatus==2 &&　ReviewerStatus==2 && id>0){
                    function g(){
                        var url='/finance/arrival/cancelAudit';
                        $.post(url,{ID:id},function(result){
                            if(result.success){
                                mini.alert('反复核领用业务成功!','系统提示',function(){
                                    grid.reload();
                                });
                            } else {
                                mini.alert('反复核业务失败，请稍候重试!');
                            }
                        })
                    }
                    mini.confirm('确认要将选择的业务退回到待复核吗？','反审提示',function(result){
                        if(result=='ok'){
                            g();
                        }
                    });
                } else {
                    mini.alert('选择的记录不能发起反复核操作!');
                }
            }
        }
    </script>
</@layout>
