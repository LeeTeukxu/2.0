<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploader.js"></script>
    <script type="text/javascript">
        var types = [
            { id: 1, text: '现金' },
            { id: 2, text: '转账' },
            { id: 3, text: '支付宝' },
            { id: 4, text: '微信' }
        ];
        var FX = [
            { id: 0, text: '拒绝' },
            { id: 1, text: '同意' }
        ];
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>销售回款信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="PayBackEdit_Save" onclick="PayBackEdit_Save()">保存</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="财务人员填写" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="arrivalForm">
                        <table style="width: 100%; height: 100%" >
                            <tr>
                                <td class="showlabel">款项描述</td>
                                <td colspan="4" >
                                    <textarea class="mini-textarea" id="description" name="description" style="width: 100%" ></textarea>
                                    <input class="mini-hidden" id="arrivalRegistrationId" name="arrivalRegistrationId">
                                    <input class="mini-hidden" id="documentNumber" name="documentNumber"/>
                                    <input class="mini-hidden" id="signMan" name="signMan"/>
                                    <input class="mini-hidden" id="addTime" name="addTime"/>
                                    <input class="mini-hidden" id="userID" name="userId"/>
                                    <input class="mini-hidden" id="claimStatus" name="claimStatus"/>
                                    <input class="mini-hidden" id="attIDS" name="attIDS"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">回款日期</td>
                                <td>
                                    <input class="mini-datepicker" style="width: 100%;" format="yyyy-MM-dd H:mm:ss" timeformat="H:mm:ss" showtime="true" required="true" name="dateOfPayment" id="dateOfPayment" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款方式</td>
                                <td>
                                    <input class="mini-combobox" name="paymentMethod" id="paymentMethod"  required="true"  data="types" style="width: 100%"   onvaluechanged="Enable(this.value)"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">付款账户</td>
                                <td>
                                    <input class="mini-textbox" required="true" style="width: 100%;" name="paymentAccount" id="paymentAccount" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款金额</td>
                                <td>
                                    <input class="mini-textbox" required="true" vtype="float" value="0" style="width: 100%;" name="paymentAmount" id="paymentAmount" onclick="setNull(this.id)"  />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">付款人</td>
                                <td>
                                    <input class="mini-textbox" required="true" style="width: 100%;" name="payer" id="payer" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款银行</td>
                                <td id="colBank">
                                    <input class="mini-textbox" style="width: 100%;" name="returnBank" id="returnBank" />
                                </td>
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
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="修改记录"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="changeGrid" style="width:100%;height: 200px;"
                                     allowCellWrap="true"
                                     url="/finance/arrival/getChangeRecord" autoload="true">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div field="changeText" headerAlign="center" align="left"
                                             width="500">变更内容
                                        </div>
                                        <div field="userId" align="center" headerAlign="center"
                                             type="treeselectcolumn">操作人员
                                            <input property="editor" class="mini-treeselect"
                                                   url="/systems/dep/getAllLoginUsersByDep"
                                                   textField="Name" valueField="FID" parentField="PID"/>
                                        </div>
                                        <div field="createTime" headerAlign="center" dataType="date"
                                             dateFormat="yyyy-MM-dd HH:mm:ss" align="center" width="150">操作时间
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
        if (mode=="Edit"){
            var form = new mini.Form('#arrivalForm');
            formData.addTime=new Date(formData.addTime);
            formData.dateOfPayment=new Date(formData.dateOfPayment);
            form.setData(formData);
            mini.get('#changeGrid').load({ArrivalRegistrationID:formData.arrivalRegistrationId});
        }
        var att=null;
        if (mode == 'Add' || mode == "Edit") {
            att = $('#attGrid').fileUploader({
                mode: 'Add',
                eachFileUpload:function(fileGrid,data){
                    var attId=data.AttID;
                    var ids=((mini.get('#attIDS').getValue() || "").split(",") || []);
                    ids.push(attId);
                    mini.get('#attIDS').setValue(ids.join(','));
                },
                uploadComplete:function(){
                    mini.alert('文件上传成功，需要对录入表单进行保存，否则附件数据会丢失!');
                }
            });
            att.loadFiles({IDS:formData["attIDS"]});
        } else att = $('#attGrid').fileUploader({mode: 'Browse'});
        $(function () {
            var form=new mini.Form('#arrivalForm');
            var claimStatus=parseInt(mini.get('claimStatus').getValue() || 0);
            //如果已认领并且认领状态为已认领和已拒绝，则不能修改
            if (claimStatus==2){
                form.setEnabled(true);
                //如果未认领则可以修改
            }else{
                form.setEnabled(true);
            }

        });
        var conTechMan = mini.get('#keyText');
        mini.get('returnBank').disable;
        if (mini.get('paymentMethod').getValue() == "2") {
            mini.get('#returnBank').setEnabled(true);
        }

        function drawCell() {

        }
        function onValueChange(e) {
            var data = e.selected;
            if (data) {
                var row = grid.getEditorOwnerRow(e.source);
                if (row) {
                    var num = parseFloat(row["Num"] || 0);
                    var price = data["Price"];
                    var total = price * num;
                    grid.updateRow(row, { Price: data["Price"], Type: data["Type"], Total: (total > 0) ? total.toFixed(2) : "" });
                }
            }
        }
        function onDraw(e) {
            var row = e.record;
            var field = e.field;
            if (field == "Num" || field == "Price") {
                var price = parseFloat(row["Price"] || 0);
                var num = parseFloat(row["Num"] || 0);
                var total = price * num;
                grid.updateRow(row, { Total: (total > 0) ? total.toFixed(2) : "" });
            }
        }
        //function onSearchClick() {
        //    var txt = conTechMan.getValue();
        //    grid1.load({ TechMan: txt });
        //}

        function STG(val, id) {
            var BIGAmount = val + "(" + smalltoBIG(val) + ")";
            mini.get(id).setValue(BIGAmount);
        }

        function setNull(id) {
            mini.get(id).setValue("");
        }

        function Enable(val) {
            if (val == "2") {
                mini.get('#returnBank').setEnabled(true);
            } else {
                mini.get('#returnBank').setEnabled(false);
            }
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

        function PayBackEdit_Save(){
            var form = new mini.Form('#arrivalForm');
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
                    url="/finance/arrival/save"
                }
                else if (mode=="Edit"){
                    url="/finance/arrival/update"
                }
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('销售回款信息保存成功','系统提示',function(){
                                form.setData(data);
                            });
                        } else {
                            mini.alert(res.message || "保存失败，请稍候重试!");
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
                    if ((x == "description" || x == "dateOfPayment" || x=="paymentMethod" || x=="paymentAccount" || x=="paymentAmount" || x=="payer" || x=="returnBank") && obj1[x] == undefined && obj2[x]!="") {
                        if (x=="paymentMethod"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + getPaymentMethod(obj2[x]) + "】;";
                        }
                        else if(x=="dateOfPayment"){
                            text += "新增了:" + getZDName(x) + ",值为:【" + formatDate(obj2[x]) + "】;";
                        }
                        else {
                            text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】;";
                        }
                    } else if ((x == "description" || x == "dateOfPayment" || x=="paymentMethod" || x=="paymentAccount" || x=="paymentAmount" || x=="payer" || x=="returnBank") && obj2[x]!="" && obj1[x] != obj2[x]) {
                        if (x=="paymentMethod"){
                            text += getZDName(x) + " 由:【"+getPaymentMethod(obj1[x])+"】，被修改为:【" + getPaymentMethod(obj2[x]) + "】;";
                        }
                        else if (x=="dateOfPayment" && obj1[x].getTime()!=obj2[x].getTime()){
                            text += getZDName(x) + " 由:【"+formatDate(obj1[x])+"】，被修改为:【" + formatDate(obj2[x]) + "】;";
                        }
                        else if (x!="dateOfPayment"){
                            text += getZDName(x) + " 由:【" + obj1[x] + "】，被修改为:【" + obj2[x] + "】;";
                        }
                    }
                }
            }
            return text;
            function getZDName(Name){
                var NameText="";
                switch (Name) {
                    case "description":
                        NameText="款项描述";
                        break;
                    case "dateOfPayment":
                        NameText="回款日期";
                        break;
                    case "paymentMethod":
                        NameText="回款方式";
                        break;
                    case "paymentAccount":
                        NameText="付款账户";
                        break;
                    case "paymentAmount":
                        NameText="回款金额";
                        break;
                    case "payer":
                        NameText="付款人";
                        break;
                    case "returnBank":
                        NameText="回款银行";
                        break;
                }
                return NameText;
            }

            function getPaymentMethod(paymentMethod) {
                var PaymentMethod="";
                switch (paymentMethod.toString()) {
                    case "1":
                        PaymentMethod="现金";
                        break;
                    case "2":
                        PaymentMethod="转账";
                        break;
                    case "3":
                        PaymentMethod="支付宝";
                        break;
                    case "4":
                        PaymentMethod="微信";
                        break;
                }
                return PaymentMethod;
            }

            function formatDate(date) {
                var d = new Date(date);
                return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
            }
        }
    </script>
</@layout>