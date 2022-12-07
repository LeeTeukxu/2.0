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
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>财务退款信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="customerRefundRequestEdit_Save" onclick="customerRefundEdit_Save()">保存</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="销售回款信息" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="arrivalForm">
                        <table style="width: 100%; height: 100%">
                            <tr>
                                <td class="showlabel">销售回款编号</td>
                                <td>
                                    <input name="documentNumber" id="documentNumber" style="width: 100%;" textname="documentNumber" class="mini-buttonedit" onbuttonclick="onCustomDialog" allowInput="false" required="true"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">客户名称
                                </td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="khName" id="khName" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">回款代理费金额</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="agencyFee" id="agencyFee" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款官费金额
                                </td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="officalFee" id="officalFee" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="业务人员填写" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="customerRefundForm">
                        <table style="width: 100%; height: 100%">
                            <tr>
                                <td class="showlabel">退款类型</td>
                                <td>
                                    <input class="mini-combobox" name="refundType" id="refundType"  data="rtype" style="width: 100%"  onvaluechanged="RefundTypeEnable(this.value)" required="true"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">退款原因</td>
                                <td>
                                    <textarea class="mini-textbox" id="reasonForReturn" name="reasonForReturn" style="width: 100%"></textarea>
                                                                        <input class="mini-hidden" id="customerRefundRequestId" name="customerRefundRequestId">
                                                                        <input class="mini-hidden" id="refundRequestNumber" name="refundRequestNumber" />
                                                                        <input class="mini-hidden" id="applicant" name="applicant"/>
                                                                        <input class="mini-hidden" id="addTime" name="addTime"/>
                                                                        <input class="mini-hidden" id="userId" name="userId"/>
                                    <#--                                    <input class="mini-hidden" id="approverResult" name="approverResult"/>-->
                                    <#--                                    <input class="mini-hidden" id="auditResult" name="auditResult"/>-->
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">退款代理费金额</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="agencyFeeAmount" id="agencyFeeAmount" vtype="float" value="0" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">退款官费金额</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="officalFeeAmount" id="officalFeeAmount" vtype="float" value="0" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">退款方式
                                </td>
                                <td>
                                    <input class="mini-combobox" name="refundMethod" id="refundMethod"  data="types" style="width: 100%"  onvaluechanged="Enable(this.value)" required="true"/>
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
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var mode="${Mode}";
        var formData=${LoadData};
        var arrivalData=${arrivalData};
        var AF="";
        var OF="";
        if (mode=="Edit"){
            var form = new mini.Form('#customerRefundForm');
            var arrivalform = new mini.Form('#arrivalForm');
            formData.addTime=new Date(formData.addTime);
            formData.dateOfPayment=new Date(formData.dateOfPayment);
            form.setData(formData);
            arrivalform.setData(arrivalData);
            AF=formData.agencyFeeAmount;
            OF=formData.officalFeeAmount;
        }

        $(function () {
            mini.get('#bank').setEnabled(false);
            mini.get('#accountNumber').setEnabled(false);
            mini.get('#accountName').setEnabled(false);
            mini.get('#khName').setEnabled(false);
            mini.get('#agencyFee').setEnabled(false);
            mini.get('#officalFee').setEnabled(false);
            if (mini.get('refundMethod').getValue() == "2") {
                mini.get('#bank').setEnabled(true);
                mini.get('#accountNumber').setEnabled(true);
                mini.get('#accountName').setEnabled(true);
            }
            else {
                mini.get('#bank').setEnabled(false);
                mini.get('#accountNumber').setEnabled(false);
                mini.get('#accountName').setEnabled(false);
            }
            if (mini.get('refundType').getValue() == "1") {
                mini.get('#agencyFeeAmount').setEnabled(true);
                mini.get('#officalFeeAmount').setEnabled(false);
            }
            else if (mini.get('refundType').getValue() == "2") {
                mini.get('#agencyFeeAmount').setEnabled(false);
                mini.get('#officalFeeAmount').setEnabled(true);
            }
            else if (mini.get('refundType').getValue() == "3") {
                mini.get('#officalFeeAmount').setEnabled(true);
                mini.get('#agencyFeeAmount').setEnabled(true);
            }
            if (mode != "Edit") {
                mini.get('#agencyFeeAmount').setEnabled(false);
                mini.get('#officalFeeAmount').setEnabled(false);
                mini.get('#bank').setEnabled(false);
                mini.get('#accountNumber').setEnabled(false);
                mini.get('#accountName').setEnabled(false);
            }
        })

        function RefundTypeEnable(val)
        {
            if (val == "1") {
                mini.get('#agencyFeeAmount').setEnabled(true);
                mini.get('#officalFeeAmount').setEnabled(false);
            }
            else if (val == "2") {
                mini.get('#agencyFeeAmount').setEnabled(false);
                mini.get('#officalFeeAmount').setEnabled(true);
            }
            else if (val=="3") {
                mini.get('#agencyFeeAmount').setEnabled(true);
                mini.get('#officalFeeAmount').setEnabled(true);
            }
        }

        function setNull(id) {
            mini.get(id).setValue("");
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

        function Enable(val) {
            if (val == "2") {
                mini.get('bank').setEnabled(true);
                mini.get('#accountNumber').setEnabled(true);
                mini.get('#accountName').setEnabled(true);
            } else {
                mini.get('#bank').setEnabled(false);
                mini.get('#accountNumber').setEnabled(false);
                mini.get('#accountName').setEnabled(false);
            }
        }

        function customerRefundEdit_Save(){
            var arrivalAgencyFee=mini.get('agencyFee').getValue();
            var arrivalOfficalFee=mini.get('officalFee').getValue();
            var customerRefundAgencyFeeAmount=mini.get('agencyFeeAmount').getValue();
            var customerRefundOfficalFeeAmount=mini.get('officalFeeAmount').getValue();
            var customerRefundRequestId=mini.get('customerRefundRequestId').getValue();
            var documentNumber=mini.get('documentNumber').getValue();
            if (parseFloat(customerRefundAgencyFeeAmount)>parseFloat(arrivalAgencyFee)){
                mini.alert('退款代理费金额不能大于回款代理费金额');
                return;
            }
            if (parseFloat(customerRefundOfficalFeeAmount)>parseFloat(arrivalOfficalFee)){
                mini.alert('退款官费金额不能大于回款官费金额');
                return;
            }
            //销售回款的官费和代理费总和
            var arrivalTotal=parseFloat(arrivalAgencyFee)+parseFloat(arrivalOfficalFee);
            //销售回款中所有编号相同的官费和代理费总和
            var TotalFeeByDocumentNumber=getTotalFeeByDocumentNumber(documentNumber);
            //获取再次修改前的代理费和官费总和
            var jianTotalFee=parseFloat(AF)+parseFloat(OF);
            //用销售回款的官费和代理费总和减去需要退款的代理费和官费总和(这里由于是再次申请，需要减去修改前的代理费和官费总和)
            var jianTotal=arrivalTotal-(TotalFeeByDocumentNumber-jianTotalFee);
            if((parseFloat(customerRefundAgencyFeeAmount)+parseFloat(customerRefundOfficalFeeAmount))>jianTotal){
                mini.alert("此回款剩余可退金额不足(已退款金额："+TotalFeeByDocumentNumber+"，可退款金额："+(arrivalTotal-TotalFeeByDocumentNumber)+"),请核对退款金额");
                return;
            }

            var custoerRequestForm = new mini.Form('#customerRefundForm');
            var arrivalForm = new mini.Form('#arrivalForm');
            var Data = custoerRequestForm.getData();
            var arrivalData = arrivalForm.getData();
            var Text=compreObj(formData,Data);
            custoerRequestForm.validate();
            if (custoerRequestForm.isValid()) {
                custoerRequestForm.loading("保存中......");
                var arg={
                    Data: mini.encode(Data),
                    ArrivalData: mini.encode(arrivalData),
                    Text: Text,
                    CommitType: mode
                };
                var url="/CustomerRefund/customerRefund/save";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            var DocumentNumber=data.documentNumber;
                            mini.alert('财务退款信息保存成功','系统提示',function(){
                                custoerRequestForm.setData(data);
                                mini.get('documentNumber').setText(DocumentNumber);
                                if (window["CloseOwnerWindow"]) window.CloseOwnerWindow();
                            });
                        } else {
                            mini.alert(res.Message);
                        }
                        custoerRequestForm.unmask();
                    }
                );
            }
        }

        function onCustomDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/CustomerRefund/ArrivalWindow/index",
                title: "选择客户",
                width: 1350,
                height: 380,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data.length != 1) {
                            mini.alert("一个销售退款只能选择一个销售回款");
                        }
                        else {
                            if (data) {
                                var documentNumber_text = "";
                                var documentNumber_value = "";

                                var khName_text = "";
                                var khName_value = "";

                                var agencyFee_text = "";
                                var agencyFee_value = "";

                                var officalFee_text = "";
                                var officalFee_value = "";
                                for (var i = 0; i < data.length; i++) {
                                    documentNumber_text = documentNumber_text + data[i].DocumentNumber + ",";
                                    documentNumber_value = documentNumber_value + data[i].DocumentNumber + ",";

                                    khName_text = khName_text + data[i].CustomerName + ",";
                                    khName_value = khName_value + data[i].CustomerName + ",";

                                    agencyFee_text = agencyFee_text + data[i].AgencyFee;
                                    agencyFee_value = agencyFee_value + data[i].AgencyFee;

                                    officalFee_text = officalFee_text + data[i].OfficalFee;
                                    officalFee_value = officalFee_value + data[i].OfficalFee
                                }
                                if (documentNumber_text) documentNumber_text = documentNumber_text.substring(0, documentNumber_text.length - 1);
                                if (documentNumber_value) documentNumber_value = documentNumber_value.substring(0, documentNumber_value.length - 1);

                                if (khName_text) khName_text = khName_text.substring(0, khName_text.length - 1);
                                if (khName_value) khName_value= khName_value.substring(0, khName_value.length - 1);

                                if (agencyFee_text) agencyFee_text = agencyFee_text.substring(0, agencyFee_text.length - 1);
                                if (agencyFee_value) agencyFee_value = agencyFee_value.substring(0, agencyFee_value.length - 1);

                                if (officalFee_text) officalFee_text = officalFee_text.substring(0, officalFee_text.length - 1);
                                if (officalFee_value) officalFee_value = officalFee_value.substring(0, officalFee_value.length - 1);

                                var customerRefundTotal=getTotalFeeByDocumentNumber(documentNumber_text);
                                var arrivalTotal=parseFloat(agencyFee_value)+parseFloat(officalFee_value);
                                if (customerRefundTotal>=arrivalTotal){
                                    mini.alert("此回款的代理费和官费总额已退款完成,请勿重复退款");
                                    var form = new mini.Form('#customerRefundForm');
                                    form.setEnabled(false);
                                    return;
                                }

                                var _DocumentNumber = mini.get('documentNumber');
                                var _KHName = mini.get('khName');
                                var _AgencyFee = mini.get('agencyFee');
                                var _OffialFee = mini.get('officalFee');

                                _DocumentNumber.setText(documentNumber_text);
                                _DocumentNumber.setValue(documentNumber_value);

                                _KHName.setText(khName_text);
                                _KHName.setValue(khName_value);

                                _AgencyFee.setText(agencyFee_text);
                                _AgencyFee.setValue(agencyFee_value);

                                _OffialFee.setText(officalFee_text);
                                _OffialFee.setValue(officalFee_value);
                            }
                        }
                    }
                }
            });
        }

        function compreObj(obj1, obj2) {
            var text="";
            for (var x in obj2) {
                if (obj2.hasOwnProperty(x)) {
                    if ((x == "reasonForReturn" || x == "refundType" || x=="agencyFeeAmount" || x=="officalFeeAmount" || x=="documentNumber" || x=="refundMethod" || x=="bank" || x=="accountNumber" || x=="accountName") && obj1[x] == undefined && obj2[x]!="") {
                        if (x=="refundType"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getRefundType(obj2[x]) + "】;";
                        }
                        else if (x=="refundMethod"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getRefundMethod(obj2[x]) + "】;";
                        }
                        else if (x=="documentNumber"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getClientName(obj2[x]) + "】;";
                        }else {
                            text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】;";
                        }
                    } else if ((x == "reasonForReturn" || x == "refundType" || x=="agencyFeeAmount" || x=="officalFeeAmount" || x=="documentNumber" || x=="refundMethod" || x=="bank" || x=="accountNumber" || x=="accountName") && obj1[x] != obj2[x] && obj2[x]!="") {
                        if (x=="refundType"){
                            text += getZDName(x) + " 由:【" + getRefundType(obj1[x])+"】，被修改为:【" + getRefundType(obj2[x]) + "】;";
                        }
                        else if (x=="refundMethod"){
                            text += getZDName(x) + " 由：【" + getRefundMethod(obj1[x])+"】，被修改为:【" + getRefundMethod(obj2[x])+"】";
                        }else if (x=="documentNumber"){
                            text += getZDName(x) + " 由:【" + getClientName(obj1[x]) + "】，被修改为:【" + getClientName(obj2[x]) + "】;";
                        }else {
                            text += getZDName(x) + " 由:【" + obj1[x] + "】，被修改为:【" + obj2[x] + "】;";
                        }
                    }
                }
            }
            return text;
            function getZDName(Name){
                var NameText="";
                switch (Name) {
                    case "reasonForReturn":
                        NameText="退款原因";
                        break;
                    case "refundType":
                        NameText="退款类型";
                        break;
                    case "agencyFeeAmount":
                        NameText="代理费金额";
                        break;
                    case "officalFeeAmount":
                        NameText="官费金额";
                        break;
                    case "documentNumber":
                        NameText="销售回款编号(客户名称:)";
                        break;
                    case "refundMethod":
                        NameText="退款方式";
                        break;
                    case "bank":
                        NameText="开户行";
                        break;
                    case "accountNumber":
                        NameText="账号";
                        break;
                    case "accountName":
                        NameText="户名";
                        break;
                }
                return NameText;
            }

            function getRefundType(refundType) {
                var RefundType="";
                switch (refundType.toString()) {
                    case "1":
                        RefundType="代理费";
                        break;
                    case "2":
                        RefundType="官费";
                        break;
                    case "3":
                        RefundType="代理费+官费";
                        break;
                }
                return RefundType;
            }

            function getRefundMethod(refundMethod) {
                var RefundMethod="";
                switch (refundMethod.toString()) {
                    case "1":
                        RefundMethod="现金";
                        break;
                    case "2":
                        RefundMethod="转账";
                        break;
                    case "3":
                        RefundMethod="支付宝";
                        break;
                    case "4":
                        RefundMethod="微信";
                        break;
                }
                return RefundMethod;
            }
            function getClientName(DocumentNumber) {
                var ClientName="";
                var url="/finance/arrival/getClientNameByDocumentNumber?DocumentNumber="+DocumentNumber;
                $.ajax({
                    url: url,
                    async: false,//改为同步方式
                    type: "POST",
                    success: function (result) {
                        ClientName = result.data || "";
                    }
                });
                return ClientName;
            }
        }
        function getTotalFeeByDocumentNumber(DocumentNumber) {
            var data="";
            var Total="";
            var AgencyFeeAmount="";
            var OfficalFeeAmount="";
            var url="/CustomerRefund/customerRefund/getTotalFeeByDocumentNumber?DocumentNumber="+DocumentNumber;
            $.ajax({
                url: url,
                async: false,//改为同步方式
                type: "POST",
                success: function (result) {
                    data = result.data || "";
                    AgencyFeeAmount=data.agencyFeeAmount;
                    OfficalFeeAmount=data.officalFeeAmount;
                    if (AgencyFeeAmount && OfficalFeeAmount){
                        Total=parseFloat(AgencyFeeAmount)+parseFloat(OfficalFeeAmount);
                    }
                }
            });
            return Total;
        }
        function getTotalFeeByCustomerRequestID(ID){
            var data="";
            var Total="";
            var AgencyFeeAmount="";
            var OfficalFeeAmount="";
            var url="/CustomerRefund/customerRefund/getTotalFeeByCustomerRequestID?CustomerRefundRequestID="+ID;
            $.ajax({
                url: url,
                async: false,//改为同步方式
                type: "POST",
                success: function (result) {
                    data = result.data || "";
                    AgencyFeeAmount=data.agencyFeeAmount;
                    OfficalFeeAmount=data.officalFeeAmount;
                    if (AgencyFeeAmount && OfficalFeeAmount){
                        Total=parseFloat(AgencyFeeAmount)+parseFloat(OfficalFeeAmount);
                    }
                }
            });
            return Total;
        }
    </script>
</@layout>