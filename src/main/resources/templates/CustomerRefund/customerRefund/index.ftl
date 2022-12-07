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
        var types = [
            { id: 1, text: '现金' },
            { id: 2, text: '转账' },
            { id: 3, text: '支付宝' },
            { id: 4, text: '微信' }
        ];
        var refundtype = [
            { id: 1, text: '代理费' },
            { id: 2, text: '官费' },
            { id: 3, text: '代理费+官费' }
        ];
        var SPYJ = [
            { id: 1, text: '拒绝' },
            { id: 2, text: '同意' }
        ];
    </script>
    <style type="text/css">
        .Zlyw *{display:inline-block;vertical-align:middle;}
        .Zlyw{padding-top: -30px;}
        .Zlyw img{width: 20px;}
        .Zlyw h5{color: #ffffff;font-size: 15px}
        .Zlywtop {display: inline-block;width:500px;float: left;margin-top: -58px;height: 32px;}
        .Zlywtop ul{margin-top: 55px;margin-right:30px ;text-align: center;}
        .Zlywtop ul li{float: left;margin-left: 120px;height: 45px;padding-top: 11px;width: 90px;border-radius: 5px;margin-top: -54px;list-style: none;}
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
            margin-left: 15%;
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
        .info2bottom ul li{float: left;margin-left: 13%;width: 90px;height: 45px;margin-top: -6px;padding-top: 12px;border-radius: 5px;}
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
                            <div class="mini-col-2" >
                                <div  id="info0" style="height:80px;background-color: rgb(63,87,131);overflow: hidden;border-radius:3px;
                            -moz-box-shadow: 2px 2px 10px rgb(63,87,131);-webkit-box-shadow: 2px 2px 10px rgb(63,87,131);-shadow:2px 2px 10px rgb(63,87,131);">
                                    <div class="file-list" >
                                        <div class="Zlyw" style="margin-left: 18px;margin-top: 5px;">
                                            <img src="/appImages/zongheicon.png"  alt="财务退款汇总">
                                            <h5>财务退款汇总</h5>
                                        </div>
                                        <div class="Zlywtop">
                                            <ul >
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

                            <div class="mini-col-5" >
                                <div  id="info1"  style="height:80px;background:rgb(226,250,252);border:1px solid rgb(190,226,240);border-radius: 5px;">
                                    <div class="info1top" style="width:100%;height: 30px;border-bottom: 1px solid rgb(214,239,243);text-align: center;">
                                        <h2 style="color: rgb(3,154,209);margin-left: 5px;margin-top: 4px;">经理审批</h2>
                                    </div>
                                    <div class="info1bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli CustomerRefund" id="J3">
                                                <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApproverResult',0,this);">
                                                    <span id="J3span">待经理审批</span>
                                                    <h4 id="J3h4" class="x6">4</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli CustomerRefund" id="J1">
                                                <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApproverResult',2,this)">
                                                    <span id="J1span">经理同意</span>
                                                    <h4 class="x2">2</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli CustomerRefund" id="J2" >
                                                <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApproverResult',1,this)">
                                                    <span id="J2span">经理驳回</span>
                                                    <h4 id="J2h4" class="x3">3</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="mini-col-5" >
                                <div id="info2"  style="height:80px;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                                    <div class="info2top" style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                                        <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">财务审批</h2>
                                    </div>
                                    <div class="info2bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli CustomerRefund" id="J3">
                                                <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('AuditResult',0,this);">
                                                    <span id="J3span">待财务审批</span>
                                                    <h4 id="J3h4" class="x7">4</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli CustomerRefund" id="Js1">
                                                <a  style="text-decoration:none" target="_self" href="#" onclick="changeQuery('AuditResult',2,this)">
                                                    <span id="Js1span">财务同意</span>
                                                    <h4 id="Js1h4" class="x4">5</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli CustomerRefund" id="Js2">
                                                <a  style="text-decoration:none" target="_self" href="#" class="active_a" onclick="changeQuery('AuditResult',1,this)">
                                                    <span id="Js2span">财务驳回</span>
                                                    <h4 id="Js2h4" class="x5">6</h4>
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
                            <a class="mini-button" iconcls="icon-add" id="CustomerRefundRequestBrowse_Add" onclick="addCustomerRefund">增加</a>
                            <a class="mini-button" iconcls="icon-edit" id="CustomerRefundRequestBrowse_Edit" onclick="editCustomerRefund">编辑</a>
                            <a class="mini-button" iconcls="icon-remove" id="CustomerRefundRequestBrowse_Remove" onclick="remove">删除</a>
                            <a class="mini-button" iconcls="icon-ok" id="CustomerRefundRequestBrowse_Jlsp" onclick="Jlsp">经理审批</a>
                            <a class="mini-button" iconcls="icon-ok" id="CustomerRefundRequestBrowse_Cwsp" onclick="Cwsp">财务审批</a>
                            <a class="mini-button" iconcls="icon-add" id="CustomerRefundRequestBrowse_Again" onclick="AgainAddCustomerRefund">重新申请</a>
                            <a class="mini-button mini-button-info" id="CustomerRefundRequestBrowse_Export" onclick="doExport">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <a class="mini-button mini-button-danger" id="CustomerRefundRequestBrowse_HighQuery" onclick="expand">高级搜索</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">申请人：</td>
                            <td style="width:13%;">
                                <input class="mini-textbox" data-oper="LIKE" name="SQR" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">代理费金额：</td>
                            <td style="width:15%;">
                                <input class="mini-textbox" data-oper="LIKE" name="AgencyFeeAmount" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">官费金额：</td>
                            <td style="width:15%;">
                                <input class="mini-textbox" data-oper="LIKE" name="OfficalFeeAmount" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">开户行：</td>
                            <td style="width:15%;">
                                <input class="mini-textbox" data-oper="LIKE" name="Bank" style="width:100%" />
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">账号：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="AccountNumber" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">户名：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="AccountName" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">退款方式：</td>
                            <td style="width:15%;"><input class="mini-combobox" data="types"  name="PaymentMethod" data-oper="EQ"
                                                          style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">经理审批意见：</td>
                            <td style="width:15%;"><input class="mini-combobox" data="SPYJ"  name="PaymentMethod" data-oper="EQ"
                                                          style="width:100%" /></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">财务审批意见：</td>
                            <td style="width:15%;"><input class="mini-combobox" data="SPYJ"  name="PaymentMethod" data-oper="EQ"
                                                          style="width:100%" /></td>
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
                     allowresize="true" url="/CustomerRefund/customerRefund/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="AddTime" sortorder="desc"
                     autoload="true" onDrawCell="onDraw" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="RefundRequestNumber" width="140" headerAlign="center" >退款申请编号</div>
                        <div field="Applicant" width="80" align="center" headeralign="center" type="treeselectcolumn"
                             allowsort="true">
                            申请人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="Applicant" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="RefundType"  width="100" headerAlign="center" type="treeselectcolumn"
                             allowsort="true" align="center">退款类型
                            <div property="editor" class="mini-combobox" data="refundtype"></div>
                        </div>
                        <div field="KHName" width="120" headeralign="center" >客户名称</div>
                        <div field="AgencyFeeAmount" width="120" headeralign="center" >代理费金额</div>
                        <div field="OfficalFeeAmount" width="120" headeralign="center" >官费金额</div>
                        <div field="Bank" width="120" headeralign="center" >开户行</div>
                        <div field="AccountNumber" width="120" headeralign="center" >账号</div>
                        <div field="AccountName" width="120" headeralign="center" >户名</div>
                        <div field="RefundMethod"  width="60" headerAlign="center" type="treeselectcolumn" allowsort="true">退款方式
                            <div property="editor" class="mini-combobox" data="types"></div>
                        </div>
                        <div field="ReasonForReturn" width="120" headeralign="center" >退款原因</div>
                        <div field="ApproverResult"  width="60" headerAlign="center" type="treeselectcolumn" allowsort="true">经理审批意见
                            <div property="editor" class="mini-combobox" data="SPYJ"></div>
                        </div>
                        <div field="ApproverDescription" visible="false">审批描述</div>
                        <div field="AuditResult"  width="60" headerAlign="center" type="treeselectcolumn" allowsort="true">财务审批意见
                            <div property="editor" class="mini-combobox" data="SPYJ"></div>
                        </div>
                        <div field="ReviewerDescription" visible="false">审批描述</div>
                        <div field="DocumentNumber" visible="false">销售回款单据编号</div>
                    </div>
                </div>
                <input class="mini-hidden" name="LoginUserID" id="LoginUserID" value="${UserID}" />
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid=mini.get('#datagrid1');
        var win1=mini.get('editform1');
        var win2=mini.get("editform2");

        var OCForm;
        var OCData;

        var ORForm;
        var ORData;

        $(function () {
            var forma1 = new mini.Form('#editforma1');
            var forma2 = new mini.Form('#editforma2');
            var formc2 = new mini.Form('#editformc2');

            forma1.setEnabled(false);
            forma2.setEnabled(false);
            formc2.setEnabled(false);
        })

        function addCustomerRefund(){
            mini.open({
                url:'/CustomerRefund/customerRefund/add',
                width:'70%',
                height:700,
                title:'新增客户退款',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            });
        }

        function expand(e) {
            e=e||{};
            var btn = e.sender;
            if(!btn){
                btn=mini.get('#CustomerRefundBrowse_HighQuery');
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

        function editCustomerRefund() {
            var row=grid.getSelected();
            if(!row) {
                mini.alert('请选择要编辑的退款信息!');
                return ;
            }
            var ID=row['CustomerRefundRequestID'];
            mini.open({
                url:'/CustomerRefund/customerRefund/edit?CustomerRefundRequestID='+ID,
                width:'70%',
                height:700,
                title:'修改财务退款',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            })
        }

        function AgainAddCustomerRefund() {
            var row=grid.getSelected();
            if (!row){
                mini.alert('请选择要重新申请的退款信息！');
                return;
            }
            if ((row["ApproverResult"]==2 && row["AuditResult"]==2)||(row["ApproverResult"]==0 && row["AuditResult"]==0)){
                mini.alert("该财务退款数据需被上级经理或财务驳回，才能重新申请");
                return;
            }
            var ID=row["CustomerRefundRequestID"];
            var DocumentNumber=row["DocumentNumber"];
            mini.open({
                url:'/CustomerRefund/customerRefund/again?CustomerRefundRequestID='+ID+'&DocumentNumber='+DocumentNumber,
                width:'70%',
                height:700,
                title:'修改财务退款',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            })
        }

        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            var isexists = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["CustomerRefundRequestID"]);
                if (rows[i]["ApproverResult"]!="0") {
                    isexists.push("y");
                }else {
                    isexists.push("n");
                }
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            if (isexists.length > 0){
                if (isexists.indexOf('y')!=-1){
                    mini.alert('您选择的数据存在已经开始审核的数据，无法完成删除操作！');
                    return;
                }
            }
            mini.confirm('确认要删除的财务退款数据?', '删除提示', function (act) {
                if (act === 'ok') {
                    var url="/CustomerRefund/customerRefund/remove";
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

        function Jlsp() {
            var row = grid.getSelected();
            if (row == "") {
                mini.alert("请选择要填写的销售退款信息!");
                return;
            }
            // var form = new mini.Form("#editform1");
            //
            // var forma1 = new mini.Form("#editforma1");
            // var winForm1=new mini.Form("#editform1");
            // if (row.AuditResult=="1"){
            //     winForm1.setEnabled(false);
            // }
            // else{
            //     winForm1.setEnabled(true);
            // }
            //
            // var editWindow = mini.get("editWindow1");
            //
            // OCForm = new mini.Form('#editform1');
            // OCData = OCForm.getData();
            //
            // form.setData(row);
            // forma1.setData(row);
            // editWindow.show();

            var ID=row['CustomerRefundRequestID'];
            mini.open({
                url:'/CustomerRefund/customerRefund/saveJinLi?CustomerRefundRequestID='+ID,
                width:'70%',
                height:580,
                title:'经理审批',
                showModal:true,
                onDestroy:function(){
                    grid.reload();
                }
            });
        }

        function Cwsp() {
            var row = grid.getSelected();
            if (row == "") {
                mini.alert("请选择要填写的销售退款信息!");
                return;
            }

            // var form1=new mini.Form("#editform1");
            // var form2 = new mini.Form("#editform2");
            // if (row.ApproverResult=="0"){
            //     form1.setEnabled(false);
            // }else {
            //     form1.setEnabled(true);
            // }
            //
            // ORForm = new mini.Form('#editform2');
            // ORData = ORForm.getData();
            //
            // var forma2 = new mini.Form("#editforma2");
            // var formc2 = new mini.Form("#editformc2");
            // var editWindow = mini.get("editWindow2");
            // form2.setData(row);
            // forma2.setData(row);
            // formc2.setData(row);
            // editWindow.show();

            var ID=row['CustomerRefundRequestID'];
            mini.open({
                url:'/CustomerRefund/customerRefund/saveCw?CustomerRefundRequestID='+ID,
                width:'70%',
                height:580,
                title:'经理审批',
                showModal:true,
                onDestroy:function(){
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
        function updateNumber() {
            var url="/CustomerRefund/customerRefund/refreshTotal";
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
            if (field =="ApproverResult") {
                var dd = record['ApproverResult'];
                if (dd == '2') e.cellHtml = '<SPAN style="color:red">同意</span>';
            }
            if (field == "AuditResult") {
                var dd = record['AuditResult'];
                if (dd == '2') e.cellHtml = '<SPAN style="color:red">同意</span>';
            }
            if (field == "AgencyFeeAmount"){
                var dd = record['AgencyFeeAmount'];
                if (dd!="") {
                    e.cellHtml = dd + "(" + smalltoBIG(dd) + ")";
                }
            }
            if (field == "OfficalFeeAmount"){
                var dd = record['OfficalFeeAmount'];
                if (dd!="") {
                    e.cellHtml = dd + "(" + smalltoBIG(dd) + ")";
                }
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
    </script>
</@layout>
<@layout>
    <div id="editWindow1" class="mini-window" title="上级经理填写" showmodal="true" allowresize="true" allowdrag="true" style="width:1100px;">
        <div id="editforma1" class="form" >
            <table style="width:100%;">
                <tr>
                    <td style="width: 100px">退款原因：</td>
                    <td colspan="3">
                        <textarea class="mini-textarea" id="ReasonForReturn" name="ReasonForReturn" style="width: 100%" ></textarea>
                        <input class="mini-hidden" id="CustomerRefundRequestID" name="CustomerRefundRequestID">
                        <input class="mini-hidden" id="RefundRequestNumber" name="RefundRequestNumber" />
                        <input class="mini-hidden" id="DocumentNumber" name="DocumentNumber" />
                        <input class="mini-hidden" id="Applicant" name="Applicant"/>
                        <input class="mini-hidden" id="AddTime" name="AddTime"/>
                        <input class="mini-hidden" id="UserID" name="UserID"/>
                    </td>
                </tr>
                <tr>
                    <td style="border-right: none; color: blue;">业务人员填写: </td>
                    <td colspan="3" style="border-left: none; background-color: rgb(238, 238, 238);"></td>
                </tr>
                <tr>
                    <td>退款类型:</td>
                    <td>
                        <input class="mini-combobox" name="RefundType" id="RefundType"  data="refundtype" style="width: 100%" />
                    </td>
                    <td style="width: 100px">代理费金额: </td>
                    <td>
                        <input class="mini-textbox" required="true" style="width: 100%;" name="AgencyFeeAmount" id="AgencyFeeAmount"  />
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px">官费金额:
                    </td>
                    <td>
                        <input class="mini-textbox" required="true" style="width: 100%;" name="OfficalFeeAmount" id="OfficalFeeAmount"  />
                    </td>
                    <td>销售回款编号:</td>
                    <td>
                        <input name="documentNumber" id="DocumentNumber" style="width: 100%;" textname="DocumentNumber" class="mini-buttonedit" allowInput="false" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>退款方式:
                    </td>
                    <td>
                        <input class="mini-combobox" name="RefundMethod" id="RefundMethod"  data="types" style="width: 100%"  />
                    </td>
                    <td>开户行：</td>
                    <td>
                        <input class="mini-textbox" style="width: 100%;" name="Bank" id="Bank" />
                    </td>
                </tr>
                <tr>
                    <td>账号:
                    </td>
                    <td>
                        <input class="mini-textbox" style="width: 100%;" name="AccountNumber" id="AccountNumber" />
                    </td>
                    <td>户名：</td>
                    <td>
                        <input class="mini-textbox" style="width: 100%;" name="AccountName" id="AccountName" />
                    </td>
                </tr>
            </table>
        </div>
        <p></p>
        <div id="editform1" class="form">
            <input class="mini-hidden" name="CustomerRefundRequestID" />
            <table style="width:100%;">
                <tr>
                    <td style="width: 100px;">审批结果:</td>
                    <td >
                        <input class="mini-combobox" name="ApproverResult" id="ApproverResult"  data="SPYJ" style="width: 100%" />
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px;">审批描述:</td>
                    <td >
                        <input class="mini-textbox" name="ApproverDescription" id="ApproverDescription" style="width: 100%;" />
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="text-align:center">
                        <button class="mini-button" style="width:80px" id="SaveForm1" onclick="SaveForm1">确定</button>
                        <button class="mini-button" style="width:80px;" id="CancelForm1" onclick="CancelForm1">取消</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="editWindow2" class="mini-window" title="财务人员填写" showmodal="true" allowresize="true" allowdrag="true" style="width:1100px;">
        <div id="editforma2" class="form" enabled="false">
            <table style="width:100%;">
                <tr>
                    <td style="width: 100px">退款原因：</td>
                    <td colspan="3">
                        <textarea class="mini-textarea" id="ReasonForReturn" name="ReasonForReturn" style="width: 100%" ></textarea>
                        <input class="mini-hidden" id="CustomerRefundRequestID" name="CustomerRefundRequestID">
                        <input class="mini-hidden" id="RefundRequestNumber" name="RefundRequestNumber" />
                        <input class="mini-hidden" id="DocumentNumber" name="DocumentNumber" />
                        <input class="mini-hidden" id="Applicant" name="Applicant"/>
                        <input class="mini-hidden" id="AddTime" name="AddTime"/>
                        <input class="mini-hidden" id="UserID" name="UserID"/>
                    </td>
                </tr>
                <tr>
                    <td style="border-right: none; color: blue;">业务人员填写: </td>
                    <td colspan="3" style="border-left: none; background-color: rgb(238, 238, 238);"></td>
                </tr>
                <tr>
                    <td>退款类型:</td>
                    <td>
                        <input class="mini-combobox" name="RefundType" id="RefundType"  data="refundtype" style="width: 100%" />
                    </td>
                    <td style="width: 100px">代理费金额: </td>
                    <td>
                        <input class="mini-textbox" required="true" style="width: 100%;" name="AgencyFeeAmount" id="AgencyFeeAmount"  />
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px">官费金额:
                    </td>
                    <td>
                        <input class="mini-textbox" required="true" style="width: 100%;" name="OfficalFeeAmount" id="OfficalFeeAmount"  />
                    </td>
                    <td>销售回款编号:</td>
                    <td>
                        <input name="documentNumber" id="DocumentNumber" style="width: 100%;" textname="DocumentNumber" class="mini-buttonedit" allowInput="false" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>退款方式:
                    </td>
                    <td>
                        <input class="mini-combobox" name="RefundMethod" id="RefundMethod"  data="types" style="width: 100%"  />
                    </td>
                    <td>开户行：</td>
                    <td>
                        <input class="mini-textbox" style="width: 100%;" name="Bank" id="Bank" />
                    </td>
                </tr>
                <tr>
                    <td>账号:
                    </td>
                    <td>
                        <input class="mini-textbox" style="width: 100%;" name="AccountNumber" id="AccountNumber" />
                    </td>
                    <td>户名：</td>
                    <td>
                        <input class="mini-textbox" style="width: 100%;" name="AccountName" id="AccountName" />
                    </td>
                </tr>
            </table>
        </div>
        <p></p>
        <div id="editformc2" class="form">
            <input class="mini-hidden" name="CustomerRefundRequestID" />
            <input class="mini-hidden" name="Approver" />
            <input class="mini-hidden" name="ApproverDate" />
            <table style="width:100%;">
                <tr>
                    <td style="width: 100px;">审批结果:</td>
                    <td >
                        <input class="mini-combobox" name="ApproverResult" id="ApproverResult"  data="SPYJ" style="width: 100%" />
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px;">审批描述:</td>
                    <td >
                        <input class="mini-textbox" name="ApproverDescription" id="ApproverDescription" style="width: 100%;" />
                    </td>
                </tr>
            </table>
        </div>
        <p></p>
        <div id="editform2" class="form">
            <table style="width:100%;">
                <input class="mini-hidden" name="CustomerRefundRequestID" />
                <tr>
                    <td style="width: 100px;">审批结果:</td>
                    <td >
                        <input class="mini-combobox" name="AuditResult" id="AuditResult"  data="SPYJ" style="width: 100%" />
                    </td>
                </tr>
                <tr>
                    <td style="width: 100px;">审批描述:</td>
                    <td >
                        <input class="mini-textbox" name="ReviewerDescription" id="ReviewerDescription" style="width: 100%;" />
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="text-align:center">
                        <button class="mini-button" style="width:80px" id="SaveForm2" onclick="SaveForm2">确定</button>
                        <button class="mini-button" style="width:80px;" id="CancelForm2" onclick="CancelForm2">取消</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <form method="post" action="/CustomerRefund/customerRefund/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <script>
        mini.parse();
        var grid=mini.get('#datagrid1');

        function SaveForm1() {
            var row = grid.getSelected();
            if (!row) {
                return;
            }
            var editForm = new mini.Form("#editWindow1");
            editForm.validate();
            if (editForm.isValid() ){
                editForm.loading("保存中......");
                var arg={
                    Data: mini.encode(editForm.getData()),
                };
                $.post("/CustomerRefund/customerRefund/jlsp", arg,
                    function (result) {
                        var res = mini.decode(result);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('数据保存成功','系统提示',function(){
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
            if (editForm.isValid() ){
                editForm.loading("保存中......");
                var arg={
                    Data: mini.encode(editForm.getData()),
                };
                $.post("/CustomerRefund/customerRefund/cwsp", arg,
                    function (result) {
                        var res = mini.decode(result);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('数据保存成功','系统提示',function(){
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

        function doExport(){
            var excel=new excelData(grid);
            excel.addEvent('beforeGetData',function (grid,rows) {
                return grid.getSelecteds();
            })
            excel.export("财务退款记录.xls");
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
        function doQuery(code, state) {
            var arg = {};
            var cs = [];
            if (code && state) {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }else if (code=="ApproverResult" && state.toString()=="0"){
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }else if (code=="AuditResult" && state.toString()=="0"){
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }
            if(cs.length>0)arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function doQuerys(code, state) {
            var arg = {};
            var cs = [];
            if (code && state) {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }else if (code=="ApproverResult" && state.toString()=="0") {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }else if(code=="AuditResult" && state.toString()=="0"){
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
                var ops = {field: 'ApproverResult', oper: 'EQ', value: 2};
                cs.push(ops);
            }
            if(cs.length>0)arg["High"] = mini.encode(cs);
            grid.load(arg);
        }
    </script>
</@layout>
