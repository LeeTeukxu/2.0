<#include "/shared/layout.ftl">
<@layout>
    <style>
        .mydt {
            padding: 5px;
            margin: 5px;
            text-align: right;
            width: 120px
        }
    </style>
    <script type="text/javascript">
        var types = [
            { id: 1, text: '现金' },
            { id: 2, text: '转账' },
            { id: 3, text: '支付宝' },
            { id: 4, text: '微信' }
        ];
    </script>
    <div class="mini-toolbar">
        <table style="width:100%;height:100%">
            <tr>
                <td style="width:100%">&nbsp;</td>
                <td style="white-space:nowrap;">
                    <button id="cmdCommit" class="mini-button mini-button-success" onclick="commit">提交审核</button>
                    <button id="cmdAudit" class="mini-button mini-button-success" onclick="auditOne">审核业务</button>
                    <button id="cmdSet" class="mini-button mini-button-success" onclick="setOne">复核业务</button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-fit">
        <table style="width:100%;table-layout: auto" id="mainForm">
            <tr style="height:32px">
                <td class="mydt">交单业务：</td>
                <td>
                    <input class="mini-hidden" name="CasesID"/>
                    <input class="mini-hidden" name="ID"/>
                    <input class="mini-buttonedit" style="width:100%" name="Nums" required="true"
                           onbuttonclick="onCasesClick" textName="Nums"/>
                </td>
                <td class="mydt">中止原因：</td>
                <td>
                    <input  class="mini-textbox" style="width:100%;" name="Memo" required="true"/>
                </td>
            </tr>
            <tr style="height:32px">
                <td class="mydt">合同编号：</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="ContractNo" enabled="false"/>
                </td>
                <td class="mydt">客户名称：</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="ClientName" enabled="false"/>
                    <input class="mini-hidden" name="ClientID"/>
                </td>
            </tr>
            <tr style="height:32px">
                <td class="mydt">业务类型：</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="TypeName" enabled="false"/>
                    <input class="mini-hidden" name="Type"/>
                </td>
                <td class="mydt">交单金额：</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="AllMoney" enabled="false"/>
                </td>
            </tr>
            <tr style="height:32px">
                <td class="mydt">商务人员：</td>
                <td>
                    <input style="width:100%" name="SignMan" enabled="false" class="mini-treeselect"
                           url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID"
                           parentField="PID"/>
                </td>
                <td class="mydt">交单日期：</td>
                <td>
                    <input class="mini-datepicker" style="width:100%" name="SignTime" enabled="false"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" height="200px">
                    <div class="mini-datagrid" id="grid2" style="width:100%;height:100%" allowCellSelect="true"
                         showSummaryRow="true" showPager="false" multiSelect="true"
                         allowCellEdit="true" ondrawcell="onDraw" url="/work/cancelCases/getSub" autoload="false"
                         oncellendedit="onEndSubEdit">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div type="checkcolumn"></div>
                            <div field="SubNo" width="120" headerAlign="center" align="center">业务编号</div>
                            <div field="YName" width="180" headerAlign="center" align="center">案件名称</div>
                            <div field="GuanMoney" width="80" headerAlign="center" align="right"   summaryType="sum">官费</div>
                            <div field="DaiMoney" width="100" headerAlign="center" align="right"   summaryType="sum">代理费</div>
                            <div field="KouTotal" width="100" headerAlign="center" align="center"
                                 summaryType="sum">可退款金额</div>
                            <div field="Memo" width="100" headerAlign="center" align="center">备注
                                <input property="editor" class="mini-textbox" style="width:100%"/>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr style="height:32px">
                <td class="mydt">是否退款：</td>
                <td>
                    <div name="RefundMoney" class="mini-radiobuttonlist" repeatDirection="vertical" style="width:100%"
                         textField="text" valueField="id" value="0" data="[{id:0,text:'不退款'},{id:1,text:'退款'}]"
                         enabled="false" onvaluechanged="onMoneyTypeChanged"></div>
                </td>
                <td class="mydt">退款金额：</td>
                <td>
                    <input class="mini-spinner" maxValue="9999999" minValue="0" name="TotalMoney" style="width:100%"
                           enabled="false" />
                </td>
            </tr>
            <tr style="height:32px">
                <td class="mydt">官费退款：</td>
                <td>
                    <input class="mini-spinner" maxValue="9999999" minValue="0" name="GuanMoney" style="width:100%"
                           enabled="false" onvaluechanged="onMoneyChanged"/>
                </td>
                <td class="mydt">代理费退款：</td>
                <td>
                    <input class="mini-spinner" maxValue="9999999" minValue="0" name="DaiMoney" style="width:100%"
                           enabled="false"  onvaluechanged="onMoneyChanged"/>
                </td>
            </tr>
            <tr class="refund">
                <td class="mydt">退款方式
                </td>
                <td>
                    <input class="mini-combobox" name="RefundMethod" data="types" style="width: 100%" required="true"
                           disabled="true" visible="false"/>
                </td>
                <td class="mydt">开户行</td>
                <td>
                    <input class="mini-textbox" style="width: 100%;" name="BankName" disabled="true" required="true"  visible="false"/>
                </td>
            </tr>
            <tr class="refund">
                <td class="mydt">银行账号</td>
                <td>
                    <input class="mini-textbox" style="width: 100%;" name="AccountNumber" disabled="true" required="true"  visible="false"/>
                </td>
                <td class="mydt">帐户名称</td>
                <td>
                    <input class="mini-textbox" style="width: 100%;" name="AccountName" disabled="true"
                           required="true"  visible="false"/>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="审核中止申请" width="600" height="240" style="display:none" id="CancelWindow">
        <table style="width:100%" class="layui-table" id="TechSBForm">
            <tr>
                <td style="width:100px">审核说明</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" name="AuditMemo"
                              value="审核通过"></textarea>
                </td>
            </tr>
            <tr>
                <td>审核结果</td>
                <td>
                    <input style="width:100%" name="AuditResult" class="mini-combobox"
                           data="[{id:1,text:'审核通过'},{id:0, text:'审核驳回'}]" value="1"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" id="CmdOK">确认操作</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('TechSBWindow')
">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        mini.parse();
        var gridSub = mini.get('#grid2');
        var auditWin = mini.get('#CancelWindow');
        var form = new mini.Form('#mainForm');
        var mode = "${Mode}";
        var cmdCommit, cmdAudit, cmdSet;
        var refundMoneType=mini.getbyName('RefundMoney');
        $(function () {
            cmdCommit = mini.get('#cmdCommit');
            cmdAudit = mini.get('#cmdAudit');
            cmdSet = mini.get('#cmdSet');

            cmdAudit.hide();
            //cmdCommit.hide();
            cmdSet.hide();
            onMoneyTypeChanged();
        })

        function SetData(data) {
            form.setData(data);
            onMoneyTypeChanged();
            form.setEnabled(false);
            var idCon = mini.getbyName('ID');
            if (idCon) {
                gridSub.load({MainID: idCon.getValue()});
                gridSub.setAllowCellEdit(false);
            }
            var column = gridSub.getColumn(1);
            gridSub.hideColumn(column);
            if (mode == "Browse") {
                cmdCommit.destroy();
                cmdAudit.destroy();
                cmdSet.destory();
            }
            if (mode == "Audit") {
                cmdCommit.destroy();
                cmdSet.destroy();
                cmdAudit.show();
            } else if (mode == "Set") {
                cmdCommit.destroy();
                cmdAudit.destroy();
                cmdSet.show();
            }
            if (mode == "Add") {
                gridSub.showColumn(column);
            }
        }
        var targetWindow = null;
        function findTargetWindow(){
            if (targetWindow == null) {
                var frames = window.parent.frames;
                for (var i = 0; i < frames.length; i++) {
                    var frame = frames[i];
                    if(frame.onCasesClick){
                        targetWindow = frame;
                        break;
                    }
                }
            }
            return targetWindow;
        }
        function onCasesClick() {
            targetWindow=findTargetWindow();
            if (targetWindow) targetWindow.onCasesClick();
        }
        function confirmDetail(row) {
            if (row) {
                setOne(row);
                var casesId = row["CasesID"];
                var url = '/work/cancelCases/getSubsByCasesId';
                $.getJSON(url, {CasesID: casesId}, function (result) {
                    if (result.success) {
                        var datas = result.data || [];
                        gridSub.setData(datas);
                    }
                });
            } else mini.alert('请选择业务交单记录!');
            function setOne(row) {
                var des = {
                    CasesID: row.CasesID,
                    ClientID: row.ClientID,
                    ClientName: row.ClientName,
                    Type: row.Type,
                    TypeName: row.Type,
                    AllMoney: row.AllMoney,
                    ContractNo: row.ContractNo,
                    Nums: row.DocSN + '(' + row.Nums + ')',
                    SignMan: row.SignMan,
                    SignTime: row.SignTime,
                    RefundMoney:0,
                    TotalMoney:0,
                    GuanMoney:0,
                    DaiMoney:0
                };
                form.setData(des);
                refundMoneType.setEnabled(true);
                form.validate();
            }
        }

        function commit() {
            form.validate();
            if (form.isValid()) {
                function g() {
                    var mainData = form.getData();
                    var total=parseFloat(conDaiMoney.getValue())+parseFloat(conGuanMoney.getValue());
                    mainData.TotalMoney=total;
                    var subData = mini.clone(gridSub.getSelecteds());
                    if (subData.length == 0) {
                        mini.alert('至少要选择一条交单记录!');
                        return;
                    }
                    var url = '/work/cancelCases/saveAll';
                    $.post(url, {Main: mini.encode(mainData), Sub: mini.encode(subData)}, function (result) {
                        if (result.success) {
                            mini.alert('交单业务中止申请提交审核成功!', '系统提示', function () {
                                CloseOwnerWindow('ok');
                            });
                        } else mini.alert(result.message || "提交审核失败，请稍候重试!");
                    })
                }
                var rows=gridSub.getSelecteds();
                if(rows.length>0){
                    mini.confirm('确认要提交业务交单中止申请吗？', '系统提示', function (result) {
                        if (result == 'ok') {
                            g();
                        }
                    });
                } else mini.alert('请选择要中止的交单记录!');
            } else mini.alert('数据录入不完整，无法提交!');
        }

        function auditOne() {
            postResult('/work/cancelCases/auditOne');
        }

        function setOne() {
            postResult('/work/cancelCases/setOne');
        }

        function postResult(url) {
            auditWin.show();
            var memoCon = mini.getbyName('AuditMemo');
            var comAudit = mini.getbyName('AuditResult');
            var idCon = mini.getByName('ID');
            var cmd = mini.get('#CmdOK');

            function click() {
                function g() {
                    var id = idCon.getValue();
                    var result = comAudit.getValue();
                    var memo = memoCon.getValue();
                    var arg = {ID: id, Result: result, Memo: memo};
                    $.post(url, arg, function (result) {
                        if (result.success) {
                            mini.alert('审核成功!', '系统提示', function () {
                                CloseOwnerWindow('ok')
                            });
                        } else mini.alert(result.message || '审核失败，请稍候重试!');
                    })
                }

                mini.confirm('确认要提交吗？', '系统提示', function (result) {
                    if (result == 'ok') g();
                });

            }

            cmd.un('click', click);
            cmd.on('click', click);
            comAudit.on('valuechanged', function (e) {
                var rec = comAudit.getSelected();
                memoCon.setValue(rec.text);
            });
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            var id = record._uid;
            if (field == "KouTotal") {
                var row = e.record;
                var kouGuan = parseFloat(row["KouGuan"] || 0);
                var kouDai = parseFloat(row["KouDai"] || 0);
                var total = kouDai + kouGuan;
                e.value = total;
                e.cellHtml = total;
            } else if (field == "Action") {
                if (mode == "Add") {
                    e.cellHtml = '<a style="text-decoration:underline" href=javascript:removeRow(\'' + id + '\')>删除记录</a>';
                }
            }
        }

        function removeRow(uid) {
            var row = gridSub.getRowByUID(uid);
            if (row) {
                mini.confirm('确认要删除选择的记录？', '系统提示', function (result) {
                    if (result == 'ok') {
                        gridSub.removeRow(row);
                        mini.alert('选择记录删除成功!', '系统提示', function () {
                            gridSub.acceptRecord(row);
                        });
                    }
                });
            }
        }

        function onEndSubEdit(e) {
            var row = e.record;
            var field = e.field;
            if (field != "Memo") {
                var kouGuan = parseFloat(row["KouGuan"] || 0);
                var kouDai = parseFloat(row["KouDai"] || 0);
                var total = kouDai + kouGuan;
                gridSub.updateRow(row, {KouTotal: total});
                gridSub.acceptRecord(row);
            }
        }
        var conMoney=mini.getbyName('TotalMoney');
        var conDaiMoney=mini.getbyName('DaiMoney');
        var conGuanMoney=mini.getbyName('GuanMoney');
        var conRefundMethod=mini.getbyName('RefundMethod');
        var conBankName=mini.getByName('BankName');
        var conAccountName=mini.getbyName('AccountName');
        var conAccountNumber=mini.getbyName('AccountNumber');
        function onMoneyTypeChanged(){
            var value=parseInt(refundMoneType.getValue());
            if(isNaN(value)){
                if(refundMoneType.getValue()==true){
                    value=1;

                }else value=0;
                refundMoneType.setValue(value);
            }
            if(value==1){
                var rows=gridSub.getData();
                var totalGuan=0;
                var totalDai=0;
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    var num=parseFloat(row["DaiMoney"] || 0);
                    var num1=parseFloat(row["GuanMoney"] || 0);
                    totalDai+=num;
                    totalGuan+=num1;
                }
                conDaiMoney.setEnabled(true);
                conDaiMoney.setRequired(true);
                conDaiMoney.setMaxValue(totalDai);
                conDaiMoney.setMinValue(0);


                conGuanMoney.setEnabled(true);
                conGuanMoney.setRequired(true);
                conGuanMoney.setMaxValue(totalGuan);
                conGuanMoney.setMinValue(0);


                conRefundMethod.setRequired(true);
                conRefundMethod.setVisible(true);
                conRefundMethod.setEnabled(true);

                conBankName.setRequired(true);
                conBankName.setVisible(true);
                conBankName.setEnabled(true);

                conAccountName.setRequired(true);
                conAccountName.setVisible(true);
                conAccountName.setEnabled(true);

                conAccountNumber.setRequired(true);
                conAccountNumber.setVisible(true);
                conAccountNumber.setEnabled(true);
                $('.refund').show();
                if(!targetWindow)targetWindow=findTargetWindow();
                if(targetWindow)targetWindow.changeWindowHeight(560);
            } else {
                conDaiMoney.setEnabled(false);
                conDaiMoney.setRequired(false);
                conDaiMoney.setMinValue(0);


                conGuanMoney.setEnabled(false);
                conGuanMoney.setRequired(false);
                conGuanMoney.setMinValue(0);


                conRefundMethod.setRequired(false);
                conRefundMethod.setVisible(false);
                conRefundMethod.setEnabled(false);

                conBankName.setRequired(false);
                conBankName.setVisible(false);
                conBankName.setEnabled(false);

                conAccountName.setRequired(false);
                conAccountName.setVisible(false);
                conAccountName.setEnabled(false);

                conAccountNumber.setRequired(false);
                conAccountNumber.setVisible(false);
                conAccountNumber.setEnabled(false);

                $('.refund').hide();
                if(!targetWindow)targetWindow=findTargetWindow();
                if(targetWindow)targetWindow.changeWindowHeight(510);
            }
            form.validate();
        }
       function  onMoneyChanged(){
            var total=parseFloat(conDaiMoney.getValue())+parseFloat(conGuanMoney.getValue());
            conMoney.setValue(total);
       }
    </script>
</@layout>

