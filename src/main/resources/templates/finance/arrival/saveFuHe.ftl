<#include "/shared/dialog.ftl">
<@layout>

    <script type="text/javascript" src="/js/common/fileUploader.js"></script>
    <script type="text/javascript">
        var types = [
            { id: 1, text: '现金' },
            { id: 2, text: '转账' },
            { id: 3, text: '支付宝' },
            { id: 4, text: '微信' }
        ];
        var RTypes = [
            { id: 1, text: '拒绝' },
            { id: 2, text: '同意' }
        ];
        var moneyType = [{id: 1, text: '需关联业务费用'}, {id: 2, text: '无业务关联官费'}, {id: 3, text: '其他费用'}];
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>销售回款信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
<#--                <a class="mini-button mini-button-primary" id="FuHe_Save" onclick="FuHe_Save()">保存</a>-->
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="1、销售回款信息" width="auto"
                     showcollapsebutton="false" showclosebutton="false">
                    <div id="Table1">
                        <table style="width:100%;height: 100%">
                            <tr>
                                <td class="showlabel">款项描述：</td>
                                <td >
                                    <textarea class="mini-textbox" id="description" name="description" style="width:100%" ></textarea>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">回款日期:</td>
                                <td>
                                    <input class="mini-datepicker" style="width: 100%;" format="yyyy-MM-dd H:mm:ss" timeformat="H:mm:ss" showtime="true" required="true" name="dateOfPayment" id="dateOfPayment" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款方式: </td>
                                <td>
                                    <input class="mini-combobox" name="paymentMethod" id="paymentMethod" data="types" style="width: 100%" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    付款账户:
                                </td>
                                <td>
                                    <input class="mini-textbox" name="paymentAccount" id="paymentAccount" style="width: 100%;" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款金额:</td>
                                <td>
                                    <input class="mini-textbox" name="paymentAmount" id="paymentAmounts1" style="width: 100%;" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    付款人:
                                </td>
                                <td>
                                    <input class="mini-textbox" name="payer" id="ayer" style="width: 100%;" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款银行：</td>
                                <td id="colBank">
                                    <input class="mini-textbox" id="returnBank" name="returnBank" style="width: 100%;" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="2、财务人员审核费用" width="auto"
                     showcollapsebutton="false" showclosebutton="false">
                    <div id="Table2">
                        <div class="mini-datagrid" id="gridDetail" style="width:100%;height:300px" id="grid1"
                             autoload="true"  ondrawcell="onMainDraw"
                             showSummaryRow="true"  sortField="moneyType" sortOrder="Asc"
                             oncellbeginedit="onBeforeEditMain" ondrawsummarycell="drawSummary"
                             onshowrowdetail="onShowRowDetail"
                             url="/arrivalUse/getDetail?ArrID=${ArrivalRegistrationID}">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div width="80" field="Action" align="center" type="expandcolumn"  headerAlign="center">#</div>
                                <div field="Audit"  align="center"  headerAlign="center" width="150">审核费用</div>
                                <div width="120" type="comboboxcolumn" field="moneyType" headerAlign="center" required="true" align="center">费用类型
                                    <input property="editor" class="mini-combobox" data="moneyType" allowInput="true" valueFromSelect="true"/>
                                </div>
                                <div width="250" field="clientId" type="treeselectcolumn" headerAlign="center"
                                     required="true" align="center">客户名称
                                    <input property="editor" class="mini-treeselect" allowInput="true" valueFromSelect="true" url="/systems/client/getClientTree"/>
                                </div>

                                <div width="120" align="center" field="guan" headerAlign="center" vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">官费
                                </div>
                                <div width="120" align="center" field="dai" headerAlign="center" vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">代理费
                                </div>
                                <div width="120" align="center" field="total" headerAlign="center" summaryType="sum"
                                     dataFormat="float" numberFormat="n2">合计
                                </div>
                                <div field="files" width="100" align="center" headerAlign="center">相关附件</div>
                                <div field="createMan" width="100"  type="treeselectcolumn" align="center" headerAlign="center">领用人员
                                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" style="width:
                               98%;" required="true" resultAsTree="false"/>
                                </div>
                                <div field="createTime" width="120" dataType="date" dateFormat="yyyy-MM-dd"  align="center" headerAlign="center">领用时间</div>
                                <div width="150" field="memo" headerAlign="center" align="center">备注</div>
                            </div>
                        </div>
                        <input class="mini-hidden" id="officalFees" vtype="float" name="officalFee" id="officalFees"
                               style="width: 100%;" required="true"/>
                        <input class="mini-hidden" id="officalFees" vtype="float" name="agencyFee" id="agencyFees" style="width: 100%;" required="true"/>
                        <input class="mini-hidden" name="khName" id="khName" style="width: 100%;" />
                        <input class="mini-hidden" name="remark" id="remark" style="width: 100%;" />

                        <input class="mini-hidden" name="reviewerStatus" id="reviewerStatus" />
                        <input class="mini-hidden" name="arrivalRegistrationId" id="arrivalRegistrationId" />
                        <input class="mini-hidden" name="officalFee" id="officalFee" />
                    </div>
                </div>
            </div>
            <#if AttIDS!=''>
                <div class="mini-col-12">
                    <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="附件信息"
                         width="auto" showcollapsebutton="false"
                         showclosebutton="false">
                        <table style="width: 100%; height: 100%" id="Table5">
                            <tr>
                                <td colspan="6">
                                    <input class="mini-hidden" id="AttIDS" value="${AttIDS}" />
                                    <div id="attGrid" style="width:100%;height: 300px;"></div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </#if>
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
    <input class="mini-hidden" name="RoleName" id="RoleName" value="${RoleName}" />
    <div id="detailGrid_Form" style="display:none;">
        <div id="subGrid" class="mini-datagrid" style="width:100%;height:150px;" showPager="false"
             url="/arrivalUse/getSub" ondrawcell="onSubDraw">
            <div property="columns">
                <div field="SignTime" width="100" allowSort="true" headerAlign="center"  align="center"
                                          dateFormat="yyyy-MM-dd">交单日期</div>
                <div field="Type" width="100"  headerAlign="center" allowSort="true">业务类型</div>
                <div field="DocSN" width="120" headerAlign="center" allowSort="true">业务编号</div>
                <div field="Nums" width="180" align="center" headerAlign="center">业务类型及数量</div>
                <div field="ContractNo" width="180" align="center" headerAlign="center">合同编号</div>
                <div field="Guan" width="100" allowSort="true" headerAlign="center" align="right">领用官费</div>
                <div field="Dai" width="100" allowSort="true" headerAlign="center"  align="right">领用代理费</div>
                <div field="Total" width="100" allowSort="true" headerAlign="center"  align="right">领用总额</div>

                <div field="CreateTime" width="100" allowSort="true" headerAlign="center"  align="center"  dateFormat="yyyy-MM-dd">领用日期</div>
                <div field="CreateMan" width="100" allowSort="true" headerAlign="center"  align="center" >领用人</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var mode="${Mode}";
        var formData=${LoadData};
        var subGrid=mini.get('#subGrid');
        var gridD=mini.get('#gridDetail');
        var form1 = new mini.Form('#Table1');
        var form2 = new mini.Form('#Table2');
        var xMode="Browse";
        //var form3 = new mini.Form('#Table3');

        form1.setEnabled(false);
        form2.setEnabled(false);
        //form3.setEnabled(false);

        if (mode=="saveFuHe"){
            var Table1 = new mini.Form('#Table1');
            var Table2 = new mini.Form('#Table2');
            //var Table3 = new mini.Form('#Table3');
            formData.addTime=new Date(formData.addTime);
            formData.dateOfPayment=new Date(formData.dateOfPayment);
            Table1.setData(formData);
            Table2.setData(formData);
            //Table3.setData(formData);
            mini.get('#changeGrid').load({ArrivalRegistrationID:formData.arrivalRegistrationId});
            <#if AttIDS!=''>
                var att=$('#attGrid').fileUploader({mode: 'Browse'});
                att.loadFiles({IDS:mini.get('#AttIDS').getValue()});
            </#if>
        }

        $(function () {
            var RoleName = mini.get('#RoleName').getValue();
            var ReviewerStatus = mini.get('reviewerStatus').getValue();
            var OfficalFee = mini.get('officalFee').getValue();
            // var form = new mini.Form("#Table3");
            // //没有认领不能审核
            // if (ReviewerStatus==undefined){
            //     form.setEnabled(false);
            // }
            // //如果当前登录人不是财务则不能复核
            // if (RoleName!=undefined && RoleName.indexOf("财务") == -1)
            // {
            //     form.setEnabled(false);
            // }
            // //如果当前登录人为财务人员并且已经商务人员未认领
            // else if (RoleName!=undefined && RoleName.indexOf("财务") == 0 && OfficalFee == undefined)
            // {
            //     form.setEnabled(false);
            // }
            // else if (RoleName!=undefined && RoleName.indexOf("财务") == 0 && OfficalFee != "") {
            //     form.setEnabled(true);
            // }
            // else if (RoleName!=undefined && RoleName.indexOf("财务") == 0 && ReviewerStatus != "") {
            //     form.setEnabled(false);
            // }
        });
        function onMainDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "Action") {
                var type = parseInt(record["moneyType"]);
                var id=parseInt(record["id"] || 0);
                if (type == 1) {

                } else {
                    e.cellHtml="";
                }
            }
            if(field=="Audit"){
                var id=record["id"];
                var state=parseInt(record["state"] || 0);
                if(state==0){
                    e.cellHtml = '<a  href="javascript:auditOne(2,\'' + id + '\')" style="text-decoration: ' +
                        'underline">&nbsp;同意&nbsp;</a>'+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
                        '<a  href="javascript:auditOne(1,\''+id + '\')" ' +
                        'style="text-decoration:underline">&nbsp;驳回&nbsp;</a>';
                } else if(state==1){
                    e.cellHtml="拒绝驳回";
                }
                else if(state==2){
                    e.cellHtml="审核通过";
                }
            }
            else if(field=="files"){
                var fs=e.value;
                var uText="";
                if((fs||"").toString().length>0){
                    uText="查看附件";
                    var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                        ' onclick="uploadRow(' + "'" + record._id + "','"+ xMode + "')" + '">'+'&nbsp;' + uText + '&nbsp;' +
                        '</a>';
                    e.cellHtml = x;
                }
            }
        }
        function onSubDraw(e){
            var record=e.record;
            var state=parseInt(record["State"] || 0);
            if(state==1){
                e.rowStyle="text-decoration-color:black;text-decoration: line-through;color:red";
            }
        }

        function auditOne(result,id){
           var uid=mini.prompt('请输入审核意见','审核提示',function(action,value){
                if(action=='ok'){
                  var url='/arrivalUse/auditOne';
                  $.post(url,{ID:id,Result:result,ResultText:value},function(result){
                      if(result.success){
                          mini.alert('审核成功!','系统提示',function(){
                              gridD.reload();
                          });
                      }
                  });
                }

           },true);
            var win = mini.getbyUID(uid);
            win.setWidth(400);
           if(result==2){
               $(win.el).find("textarea,input").width(340).val("审核通过");
           } else  $(win.el).find("textarea,input").width(340).val("拒绝驳回");
        }
        function FuHe_Save(){
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
                var url="/finance/arrival/saveFuHe";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('销售回款信息保存成功','系统提示',function(){
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

        function compreObj(obj1, obj2) {
            var text="";
            for (var x in obj2) {
                if (obj2.hasOwnProperty(x)) {
                    if ((x == "reviewerStatus" || x == "note") && obj2[x]!="") {
                        if (x=="reviewerStatus"){
                            if (obj1[x].toString()=="0") {
                                text += "新增了:" + getZDName(x) + ",值为:【" + getReviewerStatus(obj2[x]) + "】;";
                            }else {
                                text+= getZDName(x) + " 由:【" + getReviewerStatus(obj1[x]) + "】，被修改为:【" +getReviewerStatus(obj2[x]) +"】;"
                            }
                        }
                        else {
                            text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】;";
                        }
                    }
                }
            }
            return text;
            function getReviewerStatus(obj) {
                var ReviewerStatus="";
                switch (obj.toString()) {
                    case "1":
                        ReviewerStatus="拒绝";
                        break;
                    case "2":
                        ReviewerStatus="同意";
                        break;
                }
                return ReviewerStatus;
            }
            function getZDName(Name){
                var NameText="";
                switch (Name) {
                    case "reviewerStatus":
                        NameText="复核结果";
                        break;
                    case "note":
                        NameText="复核备注";
                        break;
                }
                return NameText;
            }
        }
        var detailGrid_Form = document.getElementById("detailGrid_Form");
        function onShowRowDetail(e){
            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            subGrid.load({ CasesID: row["casesId"] })
        }

        function drawSummary(e) {
            var field = e.field;
            var records = e.data || [];
            if (field == 'total') {
                var total = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["state"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["total"] || 0);
                    total += d;
                }
                e.cellHtml = total.toFixed(2);
            } else if (field == "dai") {
                var total1 = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["state"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["dai"] || 0);
                    total1 += d;
                }
                e.cellHtml = total1.toFixed(2);

            } else if (field == "guan") {
                var total2 = 0;
                for (var i = 0; i < records.length; i++) {
                    var record = records[i];
                    var state = parseInt(record["state"] || 0);
                    if (state == 1) continue;
                    var d = parseFloat(record["guan"] || 0);
                    total2 += d;
                }
                e.cellHtml = total2.toFixed(2);
            }
        }
        function uploadRow(ID,mode) {
            var row = gridD.getRowByUID(ID);
            var attId =row["files"] || "" ;
            mini.open({
                url: '/attachment/addFile?IDS=' + attId + '&Mode='+mode,
                width: 1100,
                height:500,
                title: '费用附件'
            });
        }
    </script>
</@layout>