<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript">
        var types = [
            { id: 1, text: '现金' },
            { id: 2, text: '转账' },
            { id: 3, text: '支付宝' },
            { id: 4, text: '微信' }
        ];
        var rtype = [
            { id: 1, text: '代理费' },
            { id: 2, text: '官费' },
            { id: 3, text: '代理费+官费' }
        ];
        var SPYJ = [
            { id: 1, text: '拒绝' },
            { id: 2, text: '同意' }
        ];
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>财务退款信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="CaiWu_Save" onclick="CaiWu_Save()">保存</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="1、业务人员填写" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="Table1">
                        <table style="width: 100%; height: 100%">
                            <tr>
                                <td class="showlabel">退款原因</td>
                                <td colspan="4">
                                    <textarea class="mini-textarea" id="reasonForReturn" name="reasonForReturn" style="width: 100%" ></textarea>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">退款类型</td>
                                <td>
                                    <input class="mini-combobox" name="refundType" id="refundType"  data="rtype" style="width: 100%" >
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">代理费金额</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="agencyFeeAmount"  />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">官费金额
                                </td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="officalFeeAmount" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">销售回款编号</td>
                                <td>
                                    <input name="documentNumber" id="documentNumber" style="width: 100%;" textname="documentNumber" class="mini-buttonedit" allowInput="false" required="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">退款方式
                                </td>
                                <td>
                                    <input class="mini-combobox" name="refundMethod" id="refundMethod"  data="types" style="width: 100%" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">开户行</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="bank" id="bank" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">账号
                                </td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="accountNumber" id="accountNumber" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">户名</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="accountName" id="accountName" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="2、上级经理填写" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="Table2">
                        <table style="width:100%;height: 100%">
                            <tr>
                                <td class="showlabel">审批结果: </td>
                                <td>
                                    <input class="mini-combobox" name="approverResult" id="approverResult" data="SPYJ" style="width: 10%" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    审批描述:
                                </td>
                                <td>
                                    <input class="mini-textbox" name="approverDescription" id="approverDescription" style="width: 100%;" required="true"/>
                                    <input class="mini-hidden" name="customerRefundRequestId" id="customerRefundRequestId" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="3、财务人员填写" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="Table3">
                        <table style="width:100%;height: 100%">
                            <tr>
                                <td class="showlabel">审批结果: </td>
                                <td>
                                    <input class="mini-combobox" name="auditResult" id="auditResult" data="SPYJ" style="width: 10%" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    审批描述:
                                </td>
                                <td>
                                    <input class="mini-textbox" name="reviewerDescription" id="reviewerDescription" style="width: 100%;" required="true"/>
                                    <input class="mini-hidden" name="customerRefundRequestId" id="customerRefundRequestId" />
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="修改记录"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="changeGrid" style="width:100%;height: 200px;"
                                     allowCellWrap="true"
                                     url="/CustomerRefund/customerRefund/getChangeRecord" autoload="true">
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
    <script type="text/javascript">
        mini.parse();
        var mode="${Mode}";
        var formData=${LoadData};

         var form1 = new mini.Form('#Table1');
         var form2 = new mini.Form('#Table2');

         form1.setEnabled(false);
         form2.setEnabled(false);

        if (mode=="saveCw"){
            var Table1 = new mini.Form('#Table1');
            var Table2 = new mini.Form('#Table2');
            var Table3 = new mini.Form('#Table3');
            formData.ApproverDate=new Date(formData.ApproverDate);
            formData.DateOfReview=new Date(formData.DateOfReview);
            formData.AddTime=new Date(formData.AddTime);
            Table1.setData(formData);
            Table2.setData(formData);
            Table3.setData(formData);
            mini.get('#changeGrid').load({CustomerRefundRequestID:formData.customerRefundRequestID});
        }

        $(function () {
            var ApproverResult=mini.get('approverResult').getValue();
            var AuditResult=mini.get('auditResult').getValue();
            var form = new mini.Form("#Table3");
            //如果经理已审批或者经理审批结果为同意则可以填写财务审批信息
            if (ApproverResult == "0" || ApproverResult == "1")
            {
                form.setEnabled(false);
            }
            //财务审批不管是同意还是驳回都无法再次修改
            if (AuditResult!=""){
                form.setEnabled(false);
            }
        });

        function drawCell() {

        }

        function CaiWu_Save(){
            var form = new mini.Form('#Table3');
            var Data = form.getData();
            var Text=compreObj(formData,Data);
            form.validate();
            if (form.isValid()) {
                form.loading("保存中......");
                var arg={
                    Data: mini.encode(Data),
                    Text: Text,
                    CommitType: mode
                };
                var url="/CustomerRefund/customerRefund/cwsp";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('财务退款信息保存成功','系统提示',function(){
                                form.setData(data);
                            });
                        } else {
                            mini.alert(res.Message);
                        }
                        form.unmask();
                    }
                );
            }
        }

        function compreObj(obj1, obj2) {
            var text="";
            for (var x in obj2) {
                if (obj2.hasOwnProperty(x)) {
                    if ((x == "auditResult" || x == "reviewerDescription") && obj1[x] == undefined && obj2[x] != "") {
                        if (x == "auditResult") {
                            text += "新增了:" + getZDName(x) + ",值为:【" + getAuditResult(obj2[x]) + "】;";
                        }else {
                            text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】;";
                        }
                    }else if ((x == "auditResult" || x == "reviewerDescription") && obj2[x]!="" && obj1[x] != obj2[x]) {
                        if (x == "auditResult") {
                            text += getZDName(x) + " 由:【" + getAuditResult(obj1[x]) + "】,被修改为:【" + getAuditResult(obj2[x]) + "】";
                        } else {
                            text += getZDName(x) + " 由:【" + obj1[x] + "】,被修改为:【" + obj2[x] + "】";
                        }
                    }
                }
            }
            return text;
            function getAuditResult(obj){
                var AuditResult="";
                switch (obj.toString()) {
                    case "1":
                        AuditResult="拒绝";
                        break;
                    case "2":
                        AuditResult="同意";
                        break;
                }
                return AuditResult;
            }
            function getZDName(Name){
                var NameText="";
                switch (Name) {
                    case "auditResult":
                        NameText="财务人员审批结果";
                        break;
                    case "reviewerDescription":
                        NameText="财务人员审批描述";
                        break;
                }
                return NameText;
            }
        }
    </script>
</@layout>