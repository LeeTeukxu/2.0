<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploaderCostReduction.js"></script>
    <script type="text/javascript" src="/js/work/costReductionFormManager.js"></script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>专利费用减缓办理管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="CostReductionEdit_Save" onclick="CostReductionEdit_Save()">保存</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="专利费用减缓办理" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="CostReductionForm">
                        <table style="width: 100%; height: 100%">
                            <tr>
                                <td class="showlabel">客户名称</td>
                                <td>
                                    <input name="customerId" id="customer" style="width: 100%;" textname="khName" class="mini-buttonedit" onbuttonclick="onCustomDialog" allowInput="false" required="true"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">费减请求人名称</td>
                                <td>
                                    <textarea class="mini-textbox" id="reductionRequest" name="reductionRequest" style="width: 100%"></textarea>
                                    <input class="mini-hidden" id="costReductionId" name="costReductionId">
                                    <input class="mini-hidden" id="uUId" name="uUId" />
                                    <input class="mini-hidden" id="userId" name="userId"/>
                                    <input class="mini-hidden" id="addTime" name="addTime"/>
                                    <input class="mini-hidden" id="internalPeople" name="internalPeople"/>
                                    <input class="mini-hidden" id="internalResult" name="internalResult"/>
                                    <input class="mini-hidden" id="iternalDate" name="internalDate"/>
                                    <input class="mini-hidden" id="transactor" name="transactor"/>
                                    <input class="mini-hidden" id="dealTime" name="dealTime"/>
                                    <input class="mini-hidden" id="gzjZt" name="gzjZt"/>
                                    <input class="mini-hidden" id="theSuccess" name="theSuccess"/>
                                    <input class="mini-hidden" id="reductionTheYear" name="reductionTheYear"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">费减请求人证件号</td>
                                <td>
                                    <input class="mini-textbox" style="width: 100%;" name="reductionRequestNumber" id="reductionRequestNumber">
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="附件信息"
                     width="auto" showcollapsebutton="false"
                     showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div id="attGrid" style="width:100%;height: 300px;">
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
        var loadObj =${Load};
        var attIds =${AttID};
        var att = null;
        var manager = null;

        $(function () {
            mini.parse();
            initDocument(mode);
            var internalResult = mini.get('internalResult').getValue();
            if (internalResult.toString()=="2"){
                $('#CostReductionEdit_Save').css('display', "none");
            }else {
                $('#CostReductionEdit_Save').css('display', "block");
            }
        });

        function initDocument(mode) {
            manager = new costReductionFormManager();
            manager.bindEvent('beforeSave',function (result,doValid) {
                var rows=att.getPostFile();
                var res=[];
                for (var i=0;i<rows.length;i++){
                    var row=rows[i];
                    res.push(row.ATTID);
                }
                if (res.length>0)result.Att = res;
            });
            manager.bindEvent('afterLoad',function (obj) {
                att.loadFiles({IDS: attIds.join(',')});
                var CostReductionID = obj.costReductionId;
                if (CostReductionID){
                    var customerIdCon = mini.getbyName('customerId');
                    if (customerIdCon){
                        customerIdCon.setText(obj["clientIdName"]);
                    }
                }
            })
            if (mode == 'Add' || mode == "Edit") {
                att = $('#attGrid').fileUploaderCostReduction({mode: 'Add'});
            } else att = $('#attGrid').fileUploaderCostReduction({mode: 'Browse'});
            <#if Mode=="Edit" || Mode=="Browse">
                loadObj.addTime = new Date(loadObj.addTime);
                loadObj.iternalDate = new Date(loadObj.iternalDate);
                loadObj.dealTime = new Date(loadObj.dealTime);
                manager.setValue(loadObj);
            </#if>
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

        function CostReductionEdit_Save() {
            var msgId = mini.loading('正在保存数据.........');
            try {
                var dd = manager.getValue(true);
                dd.Action = "Save";
                var url = '/CostReduction/saveAll';

                $.post(url, {Data: mini.encode(dd)}, function (result) {
                    if (result.success) {
                        function g() {
                            var obj = result.data;
                            if (obj) {
                                manager.setValue(obj);
                                if (window["CloseOwnerWindow"]) window.CloseOwnerWindow();
                            }
                        }
                        mini.alert('专利费用减缓信息保存成功!', '系统提示', function () {
                            g();
                        });
                    } else mini.alert(result.message || "保存失败，请稍候重试!");
                    form.unmask();
                });

            } catch (e) {
                mini.alert(e);
            }
            finally {
                mini.hideMessageBox(msgId);
            }
        }


    </script>
</@layout>