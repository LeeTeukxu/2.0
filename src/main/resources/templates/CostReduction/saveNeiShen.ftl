<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploader.js"></script>
    <script type="text/javascript" src="/js/work/costReductionFormManager.js"></script>
    <script type="text/javascript">
        var fjresult = [
            { id: 1, text: '内审驳回' },
            { id: 2, text: '内审通过'}
        ]
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>专利费用减缓办理管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="CostReductionEdit_Save" onclick="NeiShen_Save()">保存</a>
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
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="费减材料内审" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="NeiShenForm">
                        <table style="width:100%;height: 100%">
                            <tr>
                                <td class="showlabel">费减材料内审结果:</td>
                                <td style="width:435px;" colspan="2">
                                    <input class="mini-combobox" name="internalResult" id="internalResult"  data="fjresult" style="width: 100%"/>
                                    <input class="mini-hidden" name="costReductionId" id="costReductionId" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
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
        var loadObj =${Load};
        var attIds =${AttID};
        var att = null;
        var manager = null;

        var form1 = new mini.Form('#CostReductionForm');
        var form2 = new mini.Form('#NeiShenForm');
        form1.setEnabled(false);
        var form4 = new mini.Form('#Table4');
        form4.setEnabled(false);

        $(function () {
            mini.parse();
            initDocument(mode);
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
                att = $('#attGrid').fileUploader({mode: 'Add'});
            } else att = $('#attGrid').fileUploader({mode: 'Browse'});
            <#if Mode=="NeiShen">
                loadObj.addTime = new Date(loadObj.addTime);
                loadObj.iternalDate = new Date(loadObj.iternalDate);
                loadObj.dealTime = new Date(loadObj.dealTime);
                manager.setValue(loadObj);
                form2.setData(loadObj);
            </#if>
        }

        function NeiShen_Save() {
            var Data = form2.getData()
            form2.validate();
            if (form2.isValid()) {
                form2.loading("保存中......");
                var arg={
                    Data: mini.encode(Data)
                };
                var url="/CostReduction/saveNeiShen";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('费减材料内审保存成功','系统提示',function(){
                                form2.setData(data);
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


    </script>
</@layout>