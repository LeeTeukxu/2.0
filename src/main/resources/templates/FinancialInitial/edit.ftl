<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript">
        var initialState = [
            { id: 1, text: '欠款' },
            { id: 2, text: '结余' }
        ];
    </script>
    <div class="baseinfo" style="overflow: hidden">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>财务初始值设置管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="FinancialIntitalEdit_Save" onclick="FinancialIntitalEdit_Save()">保存</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px;overflow: hidden">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="财务人员填写" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="FinancialInitialForm">
                        <table style="width: 100%; height: 100%" >
                            <tr>
                                <td class="showlabel">客户名称</td>
                                <td >
                                    <input name="customerId" id="customer" style="width: 100%;" textname="khName" value="${ClientID}" text="${ClientName}" enabled="false" class="mini-buttonedit" onbuttonclick="onCustomDialog" allowInput="false" required="true"/>
                                    <input class="mini-hidden" id="financialInitialId" name="financialInitialId">
                                    <input class="mini-hidden" id="userId" name="userId"/>
                                    <input class="mini-hidden" id="addTime" name="addTime"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">初始状态</td>
                                <td>
                                    <input class="mini-combobox" name="initialState" id="initialState"  data="initialState" style="width: 100%" required="true" onvaluechanged="SZFS(this.value)" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">官费金额</td>
                                <td>
                                    <span id="s1"></span>
                                    <input class="mini-textbox" required="true" vtype="float" style="width: 98%;" name="officalFeeAmount" id="officalFeeAmount" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">官费详情</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="officalFeeAmountDetails" id="officalFeeAmountDetails"/>
                                </td>
                            </tr>
                                <td class="showlabel">代理费金额</td>
                                <td>
                                    <span id="s2"></span>
                                    <input class="mini-textbox" required="true" vtype="float" style="width: 98%;" name="agencyFeeAmount" id="agencyFeeAmount"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">代理费详情</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="agencyFeeAmountDetails" id="agencyFeeAmountDetails"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            &nbsp;
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="修改记录"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%;" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="changeGrid" style="width:100%;height: 200px;" allowCellWrap="true"
                                     url="/FinancialInitial/getChangeRecord" autoload="true">
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
        var type="${Type}";
        var formData=${LoadData};
        var s1=document.getElementById("s1");
        var s2=document.getElementById("s2");
        if (mode=="Edit"){
            var form = new mini.Form('#FinancialInitialForm');
            formData.addTime=new Date(formData.addTime);
            if (formData.initialState=="1") {
                formData.officalFeeAmount = formData.officalFeeAmount.substr(1);
                formData.agencyFeeAmount = formData.agencyFeeAmount.substr(1);
            }
            form.setData(formData);
            if (formData.initialState=="1"){
                s1.innerHTML = "-";
                s2.innerHTML = "-";
            }else {
                s1.innerHTML = "";
                s2.innerHTML = "";
            }
            mini.get('#changeGrid').load({FinancialInitialID:formData.financialInitialId});
        }
        if (type=="look"){
            mini.get("FinancialIntitalEdit_Save").hide();
            mini.get('#changeGrid').load({FinancialInitialID:mini.get('financialInitialId').getValue()});
        }else {
            mini.get("FinancialIntitalEdit_Save").enable();
        }

        function FinancialIntitalEdit_Save(){
            var form = new mini.Form('#FinancialInitialForm');
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
                var url="/FinancialInitial/save";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('财务初始值设置信息保存成功','系统提示',function(){
                                form.setData(data);
                                if (window["CloseOwnerWindow"]) window.CloseOwnerWindow();
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

        function SZFS(value) {
            if (value=="1"){
                s1.innerHTML = "-";
                s2.innerHTML = "-";
            }else {
                s1.innerHTML = "";
                s2.innerHTML = "";
            }
        }
        function compreObj(obj1, obj2) {
            var text="";
            var initialState="";
            for (var x in obj2) {
                if (obj2.hasOwnProperty(x)) {
                    if (x != "addTime" && x != "userId" && x!="khName" && x!="financialInitialId" && obj1[x] == undefined && obj2[x]!="") {
                        if (x=="customerId"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getKHName(obj2) + "】;";
                        }
                        else if (x=="initialState"){
                            text += "新增了:" + getZDName(x) + ",值为:【"+getInitialState(obj2[x])+"】;";
                            initialState=obj2[x];
                        }
                        else if (x != "addTime" && x != "userId" && x!="khName" && x!="financialInitialId") {
                            if ((x=="officalFeeAmount" || x=="agencyFeeAmount") && initialState!="" && initialState=="1"){
                                text += "新增了:" + getZDName(x) + ",值为:【-" + obj2[x] + "】;";
                            }else {
                                text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】;";
                            }
                        }
                    } else if (x != "addTime" && x != "userId" && x!="khName" && x!="financialInitialId" && obj1[x] != obj2[x] && obj2[x]!="") {
                        if (x=="customerId"){
                            text += getZDName(x) + " 由:【"+getKHName(obj1)+"】，被修改为:【" + getKHName(obj2) + "】;";
                        }
                        else if (x=="initialState"){
                            text += getZDName(x) + " 由：【"+getInitialState(obj1[x])+"】，被修改为:【"+getInitialState(obj2[x])+"】";
                            initialState=obj2[x];
                        }
                        else {
                            if ((x=="officalFeeAmount" || x=="agencyFeeAmount") && initialState!="" && initialState=="1"){
                                text += getZDName(x) + " 由:【" + obj1[x] + "】，被修改为:【-" + obj2[x] + "】;";
                            }else {
                                text += getZDName(x) + " 由:【" + obj1[x] + "】，被修改为:【" + obj2[x] + "】;";
                            }
                        }
                    }
                }
            }
            return text;
            function getKHName(obj) {
                var khNameText="";
                for (var i in obj){
                    if (i=="khName"){
                        khNameText=obj[i];
                    }
                }
                return khNameText;
            }
            function getZDName(Name){
                var NameText="";
                switch (Name) {
                    case "customerId":
                        NameText="客户名称";
                        break;
                    case "initialState":
                        NameText="初始状态";
                        break;
                    case "officalFeeAmount":
                        NameText="官费金额";
                        break;
                    case "officalFeeAmountDetails":
                        NameText="官费详情";
                        break;
                    case "agencyFeeAmount":
                        NameText="代理费金额";
                        break;
                    case "agencyFeeAmountDetails":
                        NameText="代理费详情";
                        break;
                }
                return NameText;
            }

            function getInitialState(initialState) {
                var InitialState="";
                switch (initialState.toString()) {
                    case "1":
                        InitialState="欠款";
                        break;
                    case "2":
                        InitialState="结余";
                        break;
                }
                return InitialState;
            }
        }
    </script>
</@layout>