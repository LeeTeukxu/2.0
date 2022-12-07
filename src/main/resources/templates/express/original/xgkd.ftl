<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript" src="/js/Original/Original.js"></script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>快递信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="AddExpress" onclick="AddExpress">保存</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="添加快递信息" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="Table1">
                        <table style="width:100%;height: 100%">
                            <tr>
                                <td class="showlabel">包裹内容: </td>
                                <td>
                                    <input name="packageContent" id="Contents2" class="mini-textbox"  readonly="readonly" style="width: 100%" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">
                                    客户名称:
                                </td>
                                <td>
                                    <input name="receiver" id="customer" style="width: 100%" textname="kHName"  class="mini-buttonedit" onbuttonclick="onCustomLinkerDialog()" allowInput="false" required="true" requirederrortext="接收人不能为空"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    客户联系人:
                                </td>
                                <td>
                                    <input name="contactPerson" id="contactPerson" class="mini-textbox" style="width:100%" required="true" requirederrortext="客户联系人不能为空" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">
                                    接收人地址:
                                </td>
                                <td>
                                    <input name="address" id="address" class="mini-textbox" style="width:100%" required="true" requirederrortext="接收人地址不能为空" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">联系电话: </td>
                                <td>
                                    <input name="phone" id="phone" style="width: 100%" class="mini-textbox" required="true" requirederrortext="联系电话不能为空" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">快递公司: </td>
                                <td>
                                    <input name="courierCompany" class="mini-textbox" style="width: 100%" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    快递编号:
                                </td>
                                <td>
                                    <input name="postalCode" class="mini-textbox" style="width:100%" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">
                                    寄送时间
                                </td>
                                <td>
                                    <input class="mini-datepicker" style="width: 100%;" format="yyyy-MM-dd H:mm:ss" timeformat="H:mm:ss" showtime="true" name="deliveryTime" id="deliveryTime" required="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    寄送人:
                                </td>
                                <td>
                                    <input name="render" class="mini-textbox" style="width:100%" required="true"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">
                                    备注:
                                </td>
                                <td>
                                    <input name="expressNotes" class="mini-textbox" style="width:100%" />
                                    <input class="mini-hidden" name="packageNum" id="packageNum" value="${PackageNum}" />
                                    <input class="mini-hidden" id="originalKdId" name="originalKdId" />
                                    <input class="mini-hidden" id="packageStatus" name="packageStatus" />
                                    <input class="mini-hidden" id="applicationTime" name="applicationTime" />
                                    <input class="mini-hidden" id="mailAppicant" name="mailAppicant" />
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
        if (mode=="Edit"){
            var form = new mini.Form('#Table1');
            if(formData.deliveryTime!=undefined) {
                formData.deliveryTime = new Date(formData.deliveryTime);
            }
            form.setData(formData);
        }
        $(function () {
            var form = new mini.Form('#Table1');
            $("#AddExpress").click(function () {
                var Data = form.getData();
                form.validate();
                if (form.isValid()) {
                    form.loading("保存中......");
                    var arg={
                        Data: mini.encode(Data),
                    };
                    var url="/express/original/saveExpress";
                    $.post(url, arg,
                        function (text) {
                            var res = mini.decode(text);
                            if (res.success) {
                                var data=res.data || {};
                                mini.alert('快递信息保存成功！','系统提示',function(){
                                    form.setData(data);
                                });
                            } else {
                                mini.alert(res.Message);
                            }
                            form.unmask();
                            onCancel();
                        }
                    );
                }
            });
        });

        function onCustomLinkerDialog(e) {
            mini.open({
                url: "/InvoiceApplication/ClientLinkWindow/index",
                title: "选择客户",
                width: 800,
                height: 300,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            var _text = "";
                            var _value = "";

                            var _jsrtext="";
                            var _jsrvalue="";

                            var _mobiletext="";
                            var _mobilevalue="";

                            var _lxrtext="";
                            var _lxrvalue="";

                            for (var i = 0; i < data.length; i++) {
                                _text = _text + data[i].ClientName + ",";
                                _value = _value + data[i].ClientID + ",";

                                _jsrtext=_jsrtext+data[i].Address+ ",";
                                _jsrvalue=_jsrvalue+data[i].Address+ ",";

                                _mobiletext=_mobiletext+data[i].Mobile+ ",";
                                _mobilevalue=_mobilevalue+data[i].Mobile+ ",";

                                _lxrtext=_lxrtext+data[i].LinkMan+",";
                                _lxrvalue=_lxrvalue+data[i].LinkMan+",";
                            }
                            if (_text) _text = _text.substring(0, _text.length - 1);
                            if (_value) _value = _value.substring(0, _value.length - 1);

                            if (_jsrtext) _jsrtext = _jsrtext.substring(0, _jsrtext.length - 1);
                            if (_jsrvalue) _jsrvalue = _jsrvalue.substring(0, _jsrvalue.length - 1);

                            if (_mobiletext) _mobiletext = _mobiletext.substring(0, _mobiletext.length - 1);
                            if (_mobilevalue) _mobilevalue = _mobilevalue.substring(0, _mobilevalue.length - 1);

                            if (_lxrtext) _lxrtext = _lxrtext.substring(0, _lxrtext.length - 1);
                            if (_lxrvalue) _lxrvalue = _lxrvalue.substring(0, _lxrvalue.length - 1);

                            var _kh = mini.get('customer');
                            var _jsr=mini.get('address');
                            var _mobile=mini.get('phone');
                            var _lxr=mini.get('contactPerson');

                            _kh.setText(_text);
                            _kh.setValue(_value);

                            _jsr.setText(_jsrtext);
                            _jsr.setValue(_jsrvalue);

                            _mobile.setText(_mobiletext);
                            _mobile.setValue(_mobilevalue);

                            _lxr.setText(_lxrtext);
                            _lxr.setValue(_lxrvalue);
                        }
                    }
                }
            });
        }

        function onCancel(e) {
            CloseWindow("cancel");
        }
        function CloseWindow(action) {
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        }
    </script>
</@layout>