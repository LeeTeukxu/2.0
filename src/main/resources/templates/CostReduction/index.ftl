<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/excelExportCostReduction.js"></script>
    <script type="text/javascript" src="/js/jquery.fileDownload.js"></script>
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

        var fit = mini.get('fitt');

        $(function () {
            $('#p1').hide();
        })

        var InternalResult = [
            { id: 1, text: '内审驳回' },
            { id: 2, text: '内审通过' }
        ];
        var TheSuccess = [
            { id: 1, text: '驳回'},
            { id: 2, text: '通过'}
        ]
    </script>
    <style type="text/css">
        .Zlyw *{display:inline-block;vertical-align:middle;}
        .Zlyw{padding-top: -30px;}
        .Zlyw img{width: 20px;}
        .Zlyw h5{color: #ffffff;font-size: 15px}
        .Zlywtop {display: inline-block;width:500px;float: left;margin-top: -58px;height: 32px;}
        .Zlywtop ul{margin-top: 55px;margin-right:30px ;text-align: center;}
        .Zlywtop ul li{float: left;margin-left: 135px;height: 45px;padding-top: 11px;width: 90px;border-radius: 5px;margin-top: -54px;list-style: none;}
        .Zlywtop ul li:hover{background-color: rgb(74,106,157);}
        .Zlywtop ul li a{color: white;}
        .Zlywtop ul li a span{font-size: 15px;}
        .Zlywtop ul li a h4{display: inline}

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
        .clicked{
            background-color: rgba(241, 112, 46, 0.84);
        }

        .unclick{
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
            margin-left: 8%;
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
        .info2bottom ul{margin-top: -8px;list-style: none;margin-left: 20px;text-align: center;}
        .info2bottom ul li{float: left;margin-left:9%;width: 90px;height: 45px;margin-top: -6px;padding-top: 12px;border-radius: 5px;}
        @media screen and (max-width:1593px){  .info2bottom ul li {margin-left:11%;}  }
        @media screen and (max-width:1480px){  .info2bottom ul li {margin-left:4%;}  }
        @media screen and (max-width:1184px){  .info2bottom ul li {margin-left:3%;}  }

        .info2bottom ul li:hover{background-color: rgb(216,228,250)}
        .info2bottom ul li a span{color: rgb(53,102,231);font-size: 15px}
        .info2bottom ul li a h4{display: inline;color: rgb(51,97,232)}

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
        .unclick1{
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
            margin-top: 12px;
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
    <table style="width:100%">
        <tr>
            <div class="mini-clearfix ">
                <div class="mini-col-2" >
                    <div  id="info0" style="height:80px;background-color: rgb(63,87,131);overflow: hidden;border-radius:3px;
                            -moz-box-shadow: 2px 2px 10px rgb(63,87,131);-webkit-box-shadow: 2px 2px 10px rgb(63,87,131);-shadow:2px 2px 10px rgb(63,87,131);">
                        <div class="file-list" >
                            <div class="Zlyw" style="margin-left: 18px;margin-top: 5px;">
                                <!--<img src="/appImages/zongheicon.png"  alt="专利费用减缓办理汇总">-->
                                <h5>专利费用减缓办理汇总</h5>
                            </div>
                            <div class="Zlywtop">
                                <ul>
                                    <li class="Jdlcli" id="Z1">
                                        <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('',0,this)">
                                            <span id="J1span">全部</span>&nbsp;
                                            <h4 class="x1">1</h4>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="mini-col-3" >
                    <div  id="info1"  style="height:80px;background:rgb(226,250,252);border:1px solid rgb(190,226,240);border-radius: 5px;">
                        <div class="info1top" style="width:100%;height: 30px;border-bottom: 1px solid rgb(214,239,243);text-align: center;">
                            <h2 style="color: rgb(3,154,209);margin-left: 5px;margin-top: 4px;">费减材料内审结果</h2>
                        </div>
                        <div class="info1bottom" style="width: 100%;height: 55px">
                            <ul>
                                <li class="Jdlcli CustomerRefund" id="J3">
                                    <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('InternalResult',0,this);">
                                        <span id="J3span">待内审</span>
                                        <h4 id="J3h4" class="x2">2</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli CustomerRefund" id="J1">
                                    <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('InternalResult',2,this)">
                                        <span id="J1span">内审通过</span>
                                        <h4 class="x3">3</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli CustomerRefund" id="J2" >
                                    <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('InternalResult',1,this)">
                                        <span id="J2span">内审驳回</span>
                                        <h4 id="J2h4" class="x4">4</h4>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="mini-col-3" >
                    <div id="info3"  style="height:80px;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                        <div class="info3top" style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                            <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">提交国知局</h2>
                        </div>
                        <div class="info3bottom" style="width: 100%;height: 55px">
                            <ul>
                                <li class="Jdlcli CustomerRefund" id="J3">
                                    <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('GzjZt',0,this);">
                                        <span id="J3span">待提交国知局</span>
                                        <h4 id="J3h4" class="x8">8</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli CustomerRefund" id="Js1">
                                    <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('GzjZt',1,this)">
                                        <span id="Js1span">已提交国知局</span>
                                        <h4 id="Js1h4" class="x9">9</h4>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="mini-col-4" >
                    <div id="info2"  style="height:80px;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                        <div class="info2top" style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                            <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">费减结果</h2>
                        </div>
                        <div class="info2bottom" style="width: 100%;height: 55px">
                            <ul>
                                <li class="Jdlcli CustomerRefund" id="J3">
                                    <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('TheSuccess',0,this);">
                                        <span id="J3span">待费减</span>
                                        <h4 id="J3h4" class="x5">5</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli CustomerRefund" id="Js1">
                                    <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('TheSuccess',2,this)">
                                        <span id="Js1span">费减通过</span>
                                        <h4 id="Js1h4" class="x6">6</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli CustomerRefund" id="Js2">
                                    <a  style="text-decoration:none" target="_self" href="#" class="active_a" onclick="changeQuery('TheSuccess',1,this)">
                                        <span id="Js2span">费减驳回</span>
                                        <h4 id="Js2h4" class="x7">6</h4>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

            </div>
        </tr>
    </table>
<div class="mini-layout" style="width:100%;height:100%">
    <div region="center" showheader="false">
        <div class="mini-toolbar">
            <table style="width:100%">
                <tr>
                    <td style="width:95%">
                        <a class="mini-button" iconcls="icon-add" id="CostReduction_Add" onclick="addCostReduction">增加</a>
                        <a class="mini-button" iconcls="icon-edit" id="CostReduction_Edit" onclick="editCostReduction">编辑</a>
                        <a class="mini-button" iconcls="icon-remove" id="CostReduction_Remove"  onclick="remove">删除</a>
                        <a class="mini-button" iconcls="icon-ok" id="CostReduction_NeiShen" onclick="addNeiShen">费减材料内审</a>
                        <a class="mini-button" iconcls="icon-ok" id="CostReduction_GuoZhiJu" onclick="addGuoZhiJu">提交国知局</a>
                        <a class="mini-button" iconcls="icon-ok" id="CostReduction_UnGuoZhiJu" onclick="UnGuoZhiJu">取消提交国知局</a>
                        <a class="mini-button" iconcls="icon-ok" id="CostReduction_FeiJianJieGuo" onclick="addFeiJianJieGuo">费减结果审核</a>
                        <a class="mini-button mini-button-info" id="CostReduction_Export" onclick="doExport">导出Excel</a>
                        <a class="mini-button" iconCls="icon-download" id="CasesBrowse_Download"
                           onclick="downloadFile()">下载文件</a>
                    </td>
                    <td style="white-space:nowrap;">
                        <a class="mini-button mini-button-danger" id="CostReudction_HighQuery" onclick="expand">高级搜索</a>
                    </td>
                </tr>
            </table>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;display:none;margin-bottom: 25px;border: none">
                <table style="width:100%;" id="highQueryForm">
                    <tr>
                        <td style="width:6%;padding-left:10px;">填写人：</td>
                        <td style="width:13%;">
                            <input class="mini-textbox" data-oper="LIKE" name="UserIDName" style="width:100%" />
                        <td style="width:80px;text-align: center;">填写时间</td>
                        <td><input class="mini-datepicker" name="AddTime" style="width:100%" data-oper="GE"
                                   dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="AddTime" style="width:100%" data-oper="LE"
                                   dateFormat="yyyy-MM-dd HH:mm:ss"/></td>
                        <td style="width:6%;padding-left:10px;">客户名称：</td>
                        <td style="width:15%;">
                            <input class="mini-textbox" data-oper="LIKE" name="CustomerName" style="width:100%" />
                        </td>
                    </tr>
                    <tr>
                        <td style="width:6%;padding-left:10px;">费减请求人名称：</td>
                        <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="ReductionRequest" style="width:100%" /></td>
                        <td style="width:6%;padding-left:10px;">费减请求人证件：</td>
                        <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="ReductionRequestNumber" style="width:100%" /></td>
                        <td style="width:6%;padding-left:10px;">费减材料内审人：</td>
                        <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="NSR" style="width:100%" /></td>
                        <td style="width:6%;padding-left:10px;">内审材料结果：</td>
                        <td style="width:15%;"><input class="mini-combobox" data="InternalResult"  name="InternalResult" data-oper="EQ"
                                                      style="width:100%" /></td>
                    </tr>
                    <tr>
                        <td style="width:6%;padding-left:10px;">费减内审材料时间：</td>
                        <td style="width:13%;">
                            <input name="InternalDate" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                   data-oper="GE" style="width:100%" />
                        </td>
                        <td style="width:6%;padding-left:10px;">到：</td>
                        <td style="width:15%;">
                            <input name="InternalDate" data-oper="LE" class="mini-datepicker" datatype="date"
                                   dateformat="yyyy-MM-dd"  style="width:100%" />
                        </td>
                        <td style="width:6%;padding-left:10px;">费减办理时间：</td>
                        <td style="width:13%;">
                            <input name="DealTime" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                   data-oper="GE" style="width:100%" />
                        </td>
                        <td style="width:6%;padding-left:10px;">到：</td>
                        <td style="width:15%;">
                            <input name="DealTime" data-oper="LE" class="mini-datepicker" datatype="date"
                                   dateformat="yyyy-MM-dd"  style="width:100%" />
                        </td>
                    </tr>
                    <tr>
                        <td style="width:6%;padding-left:10px;">费减办理人：</td>
                        <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="FJR" style="width:100%" /></td>
                        <td style="width:6%;padding-left:10px;">费减是否成功：</td>
                        <td style="width:15%;"><input class="mini-combobox" data="TheSuccess"  name="TheSuccess" data-oper="EQ"
                                                      style="width:100%" /></td>
                        <td style="width:6%;padding-left:10px;">费减截止年度：</td>
                        <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="ReductionTheYear" style="width:100%" /></td>
                    </tr>
                    <tr>
                        <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="8">
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
                     onrowdblclick="editClient"
                     allowresize="true" url="/CostReduction/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="AddTime" sortorder="desc"
                     autoload="true" onDrawCell="onDraw" onload="afterload">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div type="checkcolumn"></div>
                    <div field="UserID" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                        填写人
                        <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                               textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="UserID" style="width:
                               98%;" required="true" resultAsTree="false"/>
                    </div>
                    <div field="AddTime" width="80" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">填写时间</div>
                    <div field="KHName" width="120" headeralign="center">客户名称</div>
                    <div field="ReductionRequest" name="ReductionRequest" headeralign="center">费减请求人名称</div>
                    <div field="ReductionRequestNumber" name="ReductionRequestNumber" headeralign="center">费减请求人证件号</div>
                    <div field="FileName" name="FileName" headeralign="center">费减请求人附件</div>
                    <div field="InternalPeople" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                        费减材料内审人
                        <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                               textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="InternalPeople" style="width:
                               98%;" required="true" resultAsTree="false"/>
                    </div>
                    <div field="InternalResult"  width="90" headerAlign="center" type="treeselectcolumn" allowsort="true">费减内审材料结果
                        <div property="editor" class="mini-combobox" data="InternalResult"></div>
                    </div>
                    <div field="InternalDate" width="90" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">费减材料内审时间</div>
                    <div field="Transactor" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                        费减办理人
                        <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                               textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="Transactor" style="width:
                               98%;" required="true" resultAsTree="false"/>
                    </div>
                    <div field="DealTime" width="80" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">费减办理时间</div>
                    <div field="TheSuccess"  width="70" headerAlign="center" type="treeselectcolumn" allowsort="true">费减是否成功
                        <div property="editor" class="mini-combobox" data="TheSuccess"></div>
                    </div>
                    <div field="ReductionTheYear" name="ReductionTheYear" headeralign="center">费减截止年度</div>
                    <div field="UUID" name="UUID" Visible="false">UUID</div>
                    <div field="UserIDName" name="UserIDName" Visible="false">UserIDName</div>
                    <div field="FileName" name="FileName" Visible="false">FileName</div>
                    <div field="NSR" name="NSR" Visible="NSR">NSR</div>
                </div>
            </div>
        </div>
    </div>
</div>
    <form method="post" action="/CostReduction/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <script type="text/javascript">
        mini.parse();
        var grid=mini.get('#datagrid1');

        function afterload() {
            updateNumber();
        }
        function updateNumber() {
            var url="/CostReduction/refreshTotal";
            $.getJSON(url,{},function (result) {
                var rows=result.data || [];
                for (var i=0;i<rows.length;i++){
                    var row=rows[i];
                    var cName=row["name"];
                    var num = parseInt(row["num"] || 0);
                    var con=$('.'+cName);
                    if (con.length > 0){
                        con.text(num);
                    }
                }
            })
        }
        function changeQuery(code, state,obj) {
            var con = $(event.srcElement || e.targetElement);
            var cons=$('.Jdlcli');
            for(var i=0;i<cons.length;i++){
                var cx=cons[i];
                if(cx.className=="Jdlcli CustomerRefund" || cx.className=="Jdlcli CustomerRefund clicked" ){
                    cx.children[0].children[0].style.cssText="color:rgb(0, 159, 205);";
                    cx.children[0].children[1].style.cssText="color:rgb(0, 159, 205);";
                }
                cx.classList.remove('clicked');
            }
            $(con).parents('.Jdlcli').addClass('clicked');
            obj.children[0].style.cssText="color:#fff";
            obj.children[1].style.cssText="color:#fff";
            doQuerys(code, state);
        }

        function doQuerys(code, state) {
            var arg = {};
            var cs = [];
            if (code && state) {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }else if (code=="InternalResult" && state.toString()=="0") {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }else if (code=="GzjZt" && state.toString()=="0") {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
                var ops = {field: 'InternalResult', oper: 'EQ', value: 2};
                cs.push(ops);
            }else if (code=="TheSuccess" && state.toString()=="0") {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
                var ops = {field: 'GzjZt', oper: 'EQ', value: 1};
                cs.push(ops);
            }
            if(cs.length>0)arg["High"] = mini.encode(cs);
            grid.load(arg);
        }
        function addCostReduction(){
            mini.open({
                url:'/CostReduction/add',
                width:'70%',
                height:580,
                title:'新增专利费用减缓',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            });
        }


        function editCostReduction(){
            var row = grid.getSelected();
            if (row["InternalResult"].toString()=="2"){
                mini.alert("您选择的费减数据已内审通过，无法修改！");
                return;
            }
            if (row) {
                var id = row.CostReductionID;
                mini.open({
                    url: '/CostReduction/edit?Mode=Edit&ID=' + id,
                    width: '70%',
                    height: 580,
                    title:'编辑专利费用减缓',
                    showModal: true,
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function expand(e) {
            e=e||{};
            var btn = e.sender;
            if(!btn){
                btn=mini.get('#CostReduction_showHighSearchForm');
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

        function editFinancialInitial() {
            var row=grid.getSelected();
            if(!row) {
                mini.alert('请选择要编辑的财务初始设置的信息!');
                return ;
            }
                var ID=row['FinancialInitialID'];
                mini.open({
                    url:'/FinancialInitial/edit?FinancialInitialID='+ID+"&Type=Edit",
                    width:'70%',
                    height:580,
                    title:'修改财务初始值设置',
                    showModal:true,
                    ondestroy:function(){
                        grid.reload();
                    }
                })
        }


        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            var delInternalResult = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["UUID"]);
                if (rows[i]["InternalResult"].toString()=="2"){
                    delInternalResult.push("y");
                }else {
                    delInternalResult.push("n");
                }
            }
            if (delInternalResult.indexOf("y")>-1){
                mini.alert("您选择的数据存在已内审通过的记录，无法删除！");
                return;
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('确认要删除的专利费用减缓办理的数据?', '删除提示', function (act) {
                if (act === 'ok') {
                    var url="/CostReduction/remove";
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
        function refreshData(grid) {
            var pa = grid.getLoadParams();
            var pageIndex = grid.getPageIndex() || 0;
            var pageSize = grid.getPageSize() || 20;
            pa = pa || { pageIndex: pageIndex, pageSize: pageSize };
            grid.load(pa);
        }
        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "ClaimStatus") {
                var dd = record['ClaimStatus'];
                if (dd == '2') e.cellHtml = '<SPAN style="color:red">已认领</span>';
                if (dd == '3') e.cellHtml = '<SPAN style="color:#ff6a00">已拒绝,重认领</span>';
            }
            if (field == "ReviewerStatus") {
                var dd = record['ReviewerStatus'];
                if (dd == '2') {
                    e.cellHtml = '<SPAN style="color:red">同意</span>';
                }
                else{
                    e.cellHtml='<SPAN></span>'
                }
            }
            if (field=="PaymentAmount"){
                var dd = record['PaymentAmount'];
                if(dd!="") {
                    e.cellHtml = dd + "(" + smalltoBIG(dd) + ")";
                }
            }
            if(field=="FileName"){
                var DisplayName = "";
                var dd = record['UUID'];
                var Name = getAttachmentName(dd);
                var AttachmentName = Name.split(',');
                var ForAttachmentName="";
                var ForName="";
                for (var i=0;i<AttachmentName.length;i++){
                    ForName = AttachmentName[i];
                    ForAttachmentName = ForName.split(';');
                    DisplayName += '<a href="#" style="color:blue;text-decoration:underline"' +
                        ' onclick="ViewDocument(' + "'" + ForAttachmentName[1] + "','" + ForAttachmentName[2] + "')" + '">' +
                        '&nbsp;' + ForAttachmentName[0] + '&nbsp;</a>' + ",";
                }
                if (Name!="") {
                    if (DisplayName != "") {
                        DisplayName = DisplayName.substring(0, DisplayName.length - 1);
                        e.cellHtml = "<SPAN>" + DisplayName + "</SPAN>";
                    }
                }
            }
        }

        function ViewDocument(Path,GUID) {
            if (GUID){
                mini.mask('正在获取文件数据......');
                var arg = {'Path':Path,'GUID':GUID};
                var url='/CostReduction/getAllImages';
                $.getJSON(url,arg, function (result) {
                    mini.unmask('body');
                    var isOK = parseInt(result.status);
                    if (isOK == 1){
                        window.parent.showImages(mini.encode(result));
                    }else {
                        var msg = result.message || "无法加载通知书附件。";
                        layer.alert(msg);
                    }
                })
            }
        }

        function getAttachmentName(UUID){
            var AttachmentName="";
            if (UUID){
                $.ajax({
                    type : "post",
                    url : "/CostReduction/getAttachmentName?UUID="+UUID,
                    data:UUID,
                    async : false,
                    success : function(r){
                        if (r['success']) {
                            AttachmentName = r['data'];
                        }
                    }
                });
            }
            return AttachmentName;
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

        function addNeiShen(){
            var row = grid.getSelected();
            if (row == "") {
                mini.alert("请选择要审核的费减材料!");
                return;
            }
            var ID=row['CostReductionID'];
            if (row['InternalResult'].toString()=="2"){
                mini.alert('该数据已通过内审，无法再次内审！');
                return;
            }
            mini.open({
                url:'/CostReduction/NeiShen?CostReductionID='+ID,
                width:'70%',
                height:700,
                title:'费减材料内审',
                showModal:true,
                onDestroy:function(){
                    grid.reload();
                }
            });
        }

        function addGuoZhiJu() {
            var rows = grid.getSelecteds();
            var ids = [];
            var chnInternalResult = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["CostReductionID"]);
                if (rows[i]["InternalResult"].toString()=="1" || rows[i]["InternalResult"].toString()=="0"){
                    chnInternalResult.push("y");
                }else {
                    chnInternalResult.push("n");
                }
            }
            if (chnInternalResult.length>0){
                if (chnInternalResult.indexOf("y")>-1){
                    mini.alert('选择的数据存在费减材料内审驳回或尚未内审的记录，请重新选择！');
                    return;
                }
            }
            if (ids.length == 0) {
                mini.alert('请选择要提交国知局的记录!');
                return;
            }
            mini.confirm('确认要提交到国知局?', '系统提示', function (act) {
                if (act === 'ok') {
                    var url="/CostReduction/GuoZhiJu";
                    $.ajax({
                        contentType:'application/json',
                        method:'post',
                        url:url,
                        data:mini.encode(ids),
                        success:function (r) {
                            if (r['success']) {
                                mini.alert("提交国知局成功！",'系统提示',function () {
                                    grid.reload();
                                });

                            } else mini.alert("提交失败！");
                        },
                        failure:function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function UnGuoZhiJu() {
            var rows = grid.getSelecteds();
            var ids = [];
            var chnInternalResult = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["CostReductionID"]);
                if (rows[i]["InternalResult"].toString()=="1" || rows[i]["InternalResult"].toString()=="0" || rows[i]["TheSuccess"].toString()!="0"){
                    chnInternalResult.push("y");
                }else {
                    chnInternalResult.push("n");
                }
            }
            if (chnInternalResult.length>0){
                if (chnInternalResult.indexOf("y")>-1){
                    mini.alert('选择的数据存在费减材料内审驳回或尚未内审或费减存在审核的记录，请重新选择！');
                    return;
                }
            }
            if (ids.length == 0) {
                mini.alert('请选择要取消提交国知局的记录!');
                return;
            }
            mini.confirm('确认要取消提交到国知局?', '系统提示', function (act) {
                if (act === 'ok') {
                    var url="/CostReduction/UnGuoZhiJu";
                    $.ajax({
                        contentType:'application/json',
                        method:'post',
                        url:url,
                        data:mini.encode(ids),
                        success:function (r) {
                            if (r['success']) {
                                mini.alert("取消提交国知局成功！",'系统提示',function () {
                                    grid.reload();
                                });

                            } else mini.alert("取消提交失败！");
                        },
                        failure:function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function addFeiJianJieGuo(){
            var row = grid.getSelected();
            if (row == "") {
                mini.alert("请选择要审核的费减结果!");
                return;
            }
            var ID=row['CostReductionID'];
            if (row['Transactor']==undefined || row['Transactor']==null || row["Transactor"].toString()=="0"){
                mini.alert('费减未提交国知局办理之前不能费减审核！');
                return;
            }
            if (row['TheSuccess'].toString()!="0"){
                mini.alert("费减结果已存在审核记录，无法再次审核！");
                return;
            }
            mini.open({
                url:'/CostReduction/FeiJianJieGuo?CostReductionID='+ID,
                width:'70%',
                height:800,
                title:'费减结果审核',
                showModal:true,
                onDestroy:function(){
                    grid.reload();
                }
            });
        }

        function doExport(){
            var excel=new excelData(grid);
            excel.addEvent('beforeGetData',function (grid,rows) {
                return grid.getSelecteds();
            })
            excel.export("专利费用减缓办理记录.xls");
        }

        function downloadFile() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var uuId = row["UUID"];
                    if (uuId) cs.push(uuId);
                }

            }
            $.fileDownload('/CostReduction/download?UUIDS=' + cs.join(","), {
                httpMethod: 'POST',
                failCallback: function (html, url) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
        }
    </script>
</@layout>
