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
                <a class="mini-button mini-button-primary" id="JinLi_Save" onclick="JinLi_Save()">保存</a>
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
                                    <input class="mini-combobox" name="approverResult" id="approverResult" data="SPYJ" style="width: 10%" required="true"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    审批描述:
                                </td>
                                <td>
                                    <input class="mini-textbox" name="approverDescription" id="approverDescription" style="width: 100%;" />
                                    <input class="mini-hidden" name="customerRefundRequestId" id="customerRefundRequestId" />
                                    <input class="mini-hidden" name="auditResult" id="auditResult" />
                                    <input class="mini-hidden" name="applicant" id="applicant" />
                                </td>
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
    <input class="mini-hidden" name="LoginUserID" id="LoginUserID" value="${LoginUserID}" />
    <input class="mini-hidden" name="ApplicantDepID" id="ApplicantDepID" value="${ApplicantDepID}" />
    <input class="mini-hidden" name="LoginUserDepID" id="LoginUserDepID" value="${LoginUserDepID}" />
    <input class="mini-hidden" name="RoleName" id="RoleName" value="${RoleName}" />
    <script type="text/javascript">
        mini.parse();
        var mode="${Mode}";
        var formData=${LoadData};

         var form1 = new mini.Form('#Table1');
         form1.setEnabled(false);

        if (mode=="saveJinLi"){
            var Table1 = new mini.Form('#Table1');
            var Table2 = new mini.Form('#Table2');
            formData.ApproverDate=new Date(formData.ApproverDate);
            formData.DateOfReview=new Date(formData.DateOfReview);
            formData.AddTime=new Date(formData.AddTime);
            Table1.setData(formData);
            Table2.setData(formData);
            mini.get('#changeGrid').load({CustomerRefundRequestID:formData.customerRefundRequestID});
        }

        $(function () {
            var Applicant=mini.get('applicant').getValue();
            var LoginUserID=mini.get('LoginUserID').getValue();
            var ApproverResult=mini.get('approverResult').getValue();
            var AuditResult=mini.get('auditResult').getValue();
            var ApplicantDepID=mini.get('ApplicantDepID').getValue();
            var LoginUserDepID=mini.get('LoginUserDepID').getValue();
            var RoleName=mini.get('RoleName').getValue();
            var t2 = new mini.Form("#Table2");
            //如果上级经理审批拒绝不可以再次修改上级经理审批结果及意见
             if (ApproverResult==1){
                 t2.setEnabled(false);
             }else {
                 t2.setEnabled(true);
             }
            //财务审批过后都不能再次修改经理审批意见
            if (AuditResult != "0")
            {
                t2.setEnabled(false);
            }
            //如果当前登录人和退款申请人为同一个部门且当前登录人为退款申请人的部门经理
            else if(ApplicantDepID==LoginUserDepID && RoleName.indexOf("经理") == -1){
                t2.setEnabled(false);
            }
        });

        function drawCell() {

        }

        function JinLi_Save(){
            var form = new mini.Form('#Table2');
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
                var url="/CustomerRefund/customerRefund/jlsp";
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
                            var _kh = mini.get('customer');
                            _kh.setText(_text);
                            _kh.setValue(_value);
                        }
                    }
                }
            });
        }

        function compreObj(obj1, obj2) {
            var text="";
            for (var x in obj2) {
                if (obj2.hasOwnProperty(x)) {
                    if ((x == "approverResult" || x == "approverDescription") && obj1[x]==undefined && obj2[x]!="") {
                        if (x=="approverResult"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getApproverResult(obj2[x]) + "】;";
                        }else {
                            text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】";
                        }
                    }else if ((x == "approverResult" || x == "approverDescription") && obj2[x]!="" && obj1[x] != obj2[x]) {
                        if (x=="approverResult") {
                            text += getZDName(x) + " 由:【" + getApproverResult(obj1[x]) +"】,被修改为:【" + getApproverResult(obj2[x]) + "】";
                        }else {
                            text += getZDName(x) + " 由:【" + obj1[x] +"】,被修改为:【" + obj2[x] + "】";
                        }
                    }
                }
            }
            return text;
            function getApproverResult(obj){
                var ApproverResult="";
                switch (obj.toString()) {
                    case "1":
                        ApproverResult="拒绝";
                        break;
                    case "2":
                        ApproverResult="同意";
                        break;
                }
                return ApproverResult;
            }
            function getZDName(Name){
                var NameText="";
                switch (Name) {
                    case "approverResult":
                        NameText="上级经理审批结果";
                        break;
                    case "approverDescription":
                        NameText="上级经理审批描述";
                        break;
                }
                return NameText;
            }
        }
    </script>
</@layout>