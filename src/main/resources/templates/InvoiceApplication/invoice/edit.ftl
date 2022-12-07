<#include "/shared/dialog.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript">
        var types = [
            { id: 1, text: '未到账' },
            { id: 2, text: '已到账' },
            { id: 3, text: '部分到账' }
        ];
        var InvoiceForm= [
            { id: 1, text: '电子发票' },
            { id: 2, text: '纸质发票' }
        ];
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>发票申请信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                    <table >
                        <tr style="width:100%">
                            <td style="width:90%"></td>
                                <td style="white-space:nowrap">
                                    <a class="mini-button mini-button-success" id="CmdSave"
                                       onclick="invoiceApplicationEdit_Save()" style="width:80px">保&nbsp;&nbsp;存</a>
                                    &nbsp;&nbsp;&nbsp;
                                </td>
                                <td style="white-space:nowrap;">
                                    <a class="mini-button mini-button-primary" id="CmdCommit" onclick="commitToAudit()">提交审核</a>
                                </td>
                        </tr>
                    </table>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="财务人员填写" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="invoiceApplicationForm">
                        <table style="width: 100%; height: 100%" class="layui-table">
                            <tr>
                                <td class="showlabel">备注</td>
                                <td colspan="3">
                                    <textarea class="mini-textarea" id="remarks" name="remarks" style="width: 100%" ></textarea>
                                    <input class="mini-hidden" id="invoiceApplicationId" name="invoiceApplicationId">
                                    <input class="mini-hidden" id="dateOfApplication" name="dateOfApplication" />
                                    <input class="mini-hidden" id="applicant" name="applicant"/>
                                    <input class="mini-hidden" id="addTime" name="addTime"/>
                                    <input class="mini-hidden" id="userId" name="userId"/>
<#--                                    <input class="mini-hidden" id="clientId" name="clientId" />-->
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">客户名称</td>
                                <td>
                                    <input name="clientId" id="customer" style="width: 100%;" class="mini-buttonedit" onbuttonclick="onCustomDialog" allowInput="false" required="true" />
                                </td>
                                <td class="showlabel">选择合同</td>
                                <td>
                                    <input name="contractNo" id="contractNo" style="width: 100%;"
                                           class="mini-buttonedit" onbuttonclick="onCustomerIdDialog"
                                           allowInput="false" required="false"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">款项到款情况:</td>
                                <td>
                                    <input class="mini-combobox" name="paymentToPayment" id="paymentToPayment"  data="types" style="width: 100%"  onvaluechanged="Enable(this.value)"/>
                                </td>
                                <td class="showlabel">到账银行
                                </td>
                                <td>
                                    <input class="mini-combobox" id="bankOfArrival" name="bankOfArrival" style="width: 100%"  required="true" url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=2"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">发票领用时间
                                </td>
                                <td>
                                    <input class="mini-datepicker" id="receiveTime" name="receiveTime" style="width:
                                    100%"  enabled="false" />
                                </td>
                                <td class="showlabel">到帐单位:</td>
                                <td>
                                    <input class="mini-textbox" name="companyOfArrival" id="companyOfArrival" enabled="false"  style="width: 100%"  onvaluechanged="Enable(this.value)"/>
                                </td>

                            </tr>
                            <tr>
                                <td class="showlabel">开票单位</td>
                                <td>
                                    <input class="mini-combobox" id="tickCompany"
                                           url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=4"
                                           name="tickCompany" required="true" style="width: 100%" required="true"/>
                                </td>
                                <td class="showlabel">发票形式:</td>
                                <td>
                                    <input class="mini-combobox" name="invoiceForm" id="invoiceForm"  data="InvoiceForm" style="width: 100%" required="true" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">发票类型</td>
                                <td>
                                    <input class="mini-combobox" id="invoiceType" url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=1" name="invoiceType" required="true" style="width: 100%" required="true"/>
                                </td>
                                <td class="showlabel">发票抬头
                                </td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="invoiceTt" id="invoiceTt" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">纳税人识别号</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="taxpayerIdentificationNumber" id="taxpayerIdentificationNumber" />
                                </td>
                                <td class="showlabel">项目名称:
                                </td>
                                <td>
                                    <input class="mini-combobox" id="projectName" url="/InvoiceApplication/invoiceparameter/getAllByDtId?dtId=3" name="projectName" style="width: 100%" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">购买方电话</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="purchaserPhoneNumber" id="purchaserPhoneNumber" />
                                </td>
                                <td class="showlabel">购买方地址</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="purchaserAddress" id="purchaserAddress" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">购买方开户行
                                </td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="purchaserBank" id="purchaserBank" />
                                </td>
                                <td class="showlabel">购买方账号
                                </td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="purchaserAccount" id="purchaserAccount" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">金额-元</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="amount" id="amount"  />
                                </td>
                                <td class="showlabel">联系人信息</td>
                                <td>
                                    <input name="emailAddress" id="emailAddress" style="width: 100%;" textname="emailAddress" class="mini-buttonedit" onbuttonclick="onEmaillDialog" required="true" />
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
                                     url="/InvoiceApplication/invoice/getChangeRecord" autoload="true">
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
        var ClientName="${ClientName}";
        var ContractName = "${ContractName}";
        var form = new mini.Form('#invoiceApplicationForm');
        formData.addTime=new Date(formData.addTime);
        formData.dateOfPayment=new Date(formData.dateOfPayment);
        formData.receiveTime=new Date(formData.receiveTime);
        debugger;
        form.setData(formData);

        mini.get('contractNo').setText(ContractName);
        mini.get('customer').setText(ClientName);
        mini.get('customer').getValue(formData.clientId);
        mini.get('#changeGrid').load({InvoiceApplicationID:formData.invoiceApplicationId});
        if(mode=="Audit" || mode==""){
            form.setEnabled(false);

        }

        function RefundTypeEnable(val){
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
            if (val == "2" || val == "3") {
                mini.get('bankOfArrival').setEnabled(true);
                mini.get('companyOfArrival').setEnabled(true);
            }else {
                mini.get('bankOfArrival').setEnabled(false);
                mini.get('companyOfArrival').setEnabled(false);
            }
        }

        $(function () {
            var obj = mini.get('bankOfArrival');
            obj.disable();
        });
        
        function invoiceApplicationEdit_Save() {
            var form = new mini.Form('#invoiceApplicationForm');
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
                var url="";
                if (mode=="Add"){
                    url="/InvoiceApplication/invoice/save"
                }
                else if (mode=="Edit"){
                    url="/InvoiceApplication/invoice/update"
                }
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('开票申请信息保存成功','系统提示',function(){
                                form.setData(data);
                                CloseOwnerWindow('ok');
                            });
                        } else {
                            mini.alert(res.message || "保存失败，请稍候重试!");
                        }
                        form.unmask();
                    }
                );
            } else mini.alert("数据录入不完整，无法保存数据!");
        }
        
        function onCustomerIdDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/InvoiceApplication/ContractWindow/index?customer="+mini.get('customer').getValue(),
                title: "选择合同编号",
                width:1000,
                height: 380,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        debugger;
                        data = mini.clone(data);    //必须
                        if (data.length != 1) {
                            mini.alert("一个开票申请只能选择一个编号");
                        } else {
                            if (data) {
                                var _text = "";
                                var _value = "";
                                var _ClientID="";
                                for (var i = 0; i < data.length; i++) {
                                    _text = _text + (data[i].ContractName ||data[i].ContractNo) + ",";
                                    _value = _value + data[i].ContractNo + ",";
                                    _ClientID=data[i].ClientID;
                                }
                                if (_text) _text = _text.substring(0, _text.length - 1);
                                if (_value) _value = _value.substring(0, _value.length - 1);
                                var _ContractNo = mini.get('#contractNo');
                                var ClientID=mini.get('clientID');
                                _ContractNo.setText(_text);
                                _ContractNo.setValue(_value);
                                ClientID.setValue(_ClientID);
                            }
                        }
                    }
                }
            });
        }

        function onCustomDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/finance/ClientWindow/index",
                title: "选择客户",
                width: 1000,
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

        function onEmaillDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/InvoiceApplication/ClientLinkWindow/index?customer="+mini.get('customer').getValue(),
                title: "选择联系人信息",
                width: 650,
                height: 380,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data.length != 1) {
                            mini.alert("一个开票申请只能选择一个编号");
                        } else {
                            if (data) {
                                var _text = "";
                                var _value = "";
                                for (var i = 0; i < data.length; i++) {
                                    _text = _text + "邮寄地址:" + data[i].Address + ",收件人:" + data[i].LinkMan + ",联系电话:" + data[i].Mobile;
                                    _value = _value + "邮寄地址:" + data[i].Address + ",收件人:" + data[i].LinkMan + ",联系电话:" + data[i].Mobile;
                                }
                                if (_text) _text = _text.substring(0, _text.length - 1);
                                if (_value) _value = _value.substring(0, _value.length - 1);
                                var _EmailAddress = mini.get('emailAddress');
                                _EmailAddress.setText(_text);
                                _EmailAddress.setValue(_value);
                            }
                        }
                    }
                }
            });
        }

        function compreObj(obj1, obj2) {
            var text="";
            for (var x in obj2){
                if (obj2.hasOwnProperty(x)){
                    if (x!="addtime" && x!="applicant" && x!="invoiceApplicationId" && x!="userId" && obj1[x]==undefined && obj2[x]!=""){
                        if (x=="clientID"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getClientName(obj2[x]) + "】;";
                        }else if (x=="paymentToPayment"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getPaymentToPaymentName(obj2[x]) + "】;";
                        }else if (x=="bankOfArrival" || x=="invoiceType" || x=="projectName"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getInvoiceParameterName(obj2[x]) + "】;";
                        }else if (x=="invoiceForm"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getInvoiceForm(obj2[x]) + "】;";
                        }else{
                            text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】;";
                        }
                    }else if(x!="addtime" && x!="applicant" && x!="invoiceApplicationId" && x!="userId" && obj2[x]!="" && obj1[x] != obj2[x]){
                        if (x=="clientID"){
                            text += getZDName(x) + " 由:【" + getClientName(obj1[x]) + "】,被修改为:【" + getClientName(obj2[x]) + "】;";
                        }else if (x=="paymentToPayment"){
                            text += getZDName(x) + " 由:【" + getPaymentToPaymentName(obj1[x]) + "】,被修改为:【" + getPaymentToPaymentName(obj2[x]) + "】;";
                        }else if (x=="bankOfArrival" || x=="invoiceType" || x=="projectName"){
                            text += getZDName(x) + " 由:【" + getInvoiceParameterName(obj1[x]) + "】,被修改为:【" + getInvoiceParameterName(obj2[x]) + "】;";
                        }else if (x=="invoiceForm"){
                            text += getZDName(x) + " 由:【" + getInvoiceForm(obj1[x]) + "】,被修改为:【" + getInvoiceForm(obj2[x]) + "】;";
                        }else{
                            text += getZDName(x) + " 由:【" + obj1[x] + "】,被修改为:【" + obj2[x] + "】;";
                        }
                    }
                }
            }
            return text;

            function getZDName(Name) {
                var NameText="";
                switch (Name.toString()) {
                    case "remarks":
                        NameText="备注";
                        break;
                    case "clientId":
                        NameText="客户名称";
                        break;
                    case "contractNo":
                        NameText="合同名称";
                        break;
                    case "paymentToPayment":
                        NameText="款项到款情况";
                        break;
                    case "bankOfArrival":
                        NameText="到账银行";
                        break;
                    case "invoiceType":
                        NameText="发票类型";
                        break;
                    case "invoiceTt":
                        NameText="发票抬头";
                        break;
                    case "axpayerIdentificationNumber":
                        NameText="纳税人识别号";
                        break;
                    case "projectName":
                        NameText="项目名称";
                        break;
                    case "purchaserAddress":
                        NameText="购买方地址";
                        break;
                    case "purchaserPhoneNumber":
                        NameText="购买方电话";
                        break;
                    case "purchaserBank":
                        NameText="购买方开户行";
                        break;
                    case "purchaserAccount":
                        NameText="购买方账号";
                        break;
                    case "invoiceForm":
                        NameText="发票形式";
                        break;
                    case "tickCompany":
                        NameText="开票单位";
                        break;
                    case "emailAddress":
                        NameText="联系人信息";
                        break
                }
                return NameText;
            }
            function getClientName(ClientID) {
                var ClientName="";
                var url="/InvoiceApplication/invoice/getClientNameByClientID?ClientID="+ClientID;
                $.ajax({
                    url: url,
                    async: false,//改为同步方式
                    type: "POST",
                    success: function (result) {
                        ClientName = result.name || "";
                    }
                });
                return ClientName;
            }

            function getPaymentToPaymentName(paymentToPayment) {
                var PaymentToPayment="";
                switch (paymentToPayment.toString()) {
                    case "1":
                        PaymentToPayment="未到账";
                        break;
                    case "2":
                        PaymentToPayment="已到账";
                        break;
                    case "3":
                        PaymentToPayment="部分到账";
                        break;
                }
                return PaymentToPayment;
            }

            function getInvoiceParameterName(Id) {
                var ArrivalName="";
                var url="/InvoiceApplication/invoiceparameter/getNameByFIdAndId?FId="+Id;
                $.ajax({
                    url: url,
                    async: false,//改为同步方式
                    type: "POST",
                    success: function (result) {
                        var data=result.data||{};
                        ArrivalName = data.name || "";
                    }
                });
                return ArrivalName;
            }

            function getInvoiceForm(invoiceForm) {
                var InvoiceForm="";
                switch (invoiceForm.toString()) {
                    case "1":
                        InvoiceForm="电子发票";
                        break;
                    case "2":
                        InvoiceForm="纸质发票";
                        break;
                }
                return InvoiceForm;
            }
        }
        function commitToAudit(){
            var form = new mini.Form('#invoiceApplicationForm');
            var Data = form.getData();
            var Text=compreObj(formData,Data);
            form.validate();
            if (form.isValid()) {
                function g(){
                    form.loading("正在提交数据......");
                    var arg={
                        Data: mini.encode(Data),
                        Text: Text
                    };
                    var url="/InvoiceApplication/invoice/commit";
                    $.post(url, arg,
                        function (text) {
                            var res = mini.decode(text);
                            if (res.success) {
                                var data=res.data || {};
                                mini.alert('开票申请信息保存成功','系统提示',function(){
                                    form.setEnabled(false);
                                    mini.get('#CmdSave').destroy();
                                    mini.get('#CmdCommit').destroy();
                                    CloseOwnerWindow('ok');
                                });

                            } else {
                                mini.alert(res.Message);
                            }
                            form.unmask();
                        }
                    );
                }
                mini.confirm('即将提交发票申请，所有内容将无法修改，是否继续?','系统提示',function(act){
                    if(act=='ok'){
                        g();
                    }
                });
            } else mini.alert("数据录入不完整，无法提交申请!");
        }
    </script>
</@layout>