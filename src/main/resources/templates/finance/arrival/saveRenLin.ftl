<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploader.js"></script>
    <script type="text/javascript">
        var types = [
            {id: 1, text: '现金'},
            {id: 2, text: '转账'},
            {id: 3, text: '支付宝'},
            {id: 4, text: '微信'}
        ];
        var moneyType = [{id: 1, text: '需关联业务费用'}, {id: 2, text: '无业务关联官费'}, {id: 3, text: '其他费用'}];
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>销售回款信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-info" style="width:100px" id="RenLin_Save"
                   onclick="RenLin_Save()" visible="false">保存</a>
                    &nbsp;&nbsp;&nbsp;
                    <a class="mini-button mini-button-primary" style="width:120px;"
                       id="RenLin_Commit"
                       onclick="RenLin_Commit()">提交审核</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="1、财务人员填写" width="auto"
                     showcollapsebutton="false" showclosebutton="false">
                    <div id="Table1">
                        <table style="width:100%;height: 100%">
                            <tr>
                                <td class="showlabel">款项描述：</td>
                                <td>
                                    <textarea class="mini-textbox" id="description" name="description"
                                              style="width:100%"></textarea>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">回款日期:</td>
                                <td>
                                    <input class="mini-datepicker" style="width: 100%;" format="yyyy-MM-dd H:mm:ss"
                                           timeformat="H:mm:ss" showtime="true" required="true" name="dateOfPayment"
                                           id="dateOfPayment"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款方式:</td>
                                <td>
                                    <input class="mini-combobox" name="paymentMethod" id="paymentMethod" data="types"
                                           style="width: 100%"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    付款账户:
                                </td>
                                <td>
                                    <input class="mini-textbox" name="paymentAccount" id="paymentAccount"
                                           style="width: 100%;"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款金额:</td>
                                <td>
                                    <input class="mini-textbox" name="paymentAmount" id="paymentAmounts1"
                                           style="width: 100%;"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">
                                    付款人:
                                </td>
                                <td>
                                    <input class="mini-textbox" name="payer" id="ayer" style="width: 100%;"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">回款银行：</td>
                                <td id="colBank">
                                    <input class="mini-textbox" id="returnBank" name="returnBank" style="width: 100%;"/>
                                </td>
                            </tr>
                        </table>

                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="2、商务人员填写" width="auto"
                     showcollapsebutton="false" showclosebutton="false">
                    <div id="Table2">
                        <div class="mini-toolbar" id="myToolbar">
                            <a class="mini-button" iconCls="icon-add" onclick="addNewRow()" plain="true">增加</a>
                            <a class="mini-button" iconCls="icon-save" plain="true" onclick="saveRow()"
                               id="cmdSaveArrival">保存</a>
                            <a class="mini-button" iconCls="icon-remove" plain="true" onclick="removeOne">删除</a>

                            <input class="mini-hidden" name="officalFee" id="officalFees"/>
                            <input class="mini-hidden" name="agencyFee" id="agencyFees"/>

                            <input class="mini-hidden" name="remark" id="remark" style="width: 100%;"/>
                            <input class="mini-hidden" name="arrivalRegistrationId" id="arrivalRegistrationId"/>
                            <input class="mini-hidden" name="claimStatus" id="claimStatus"/>
                            <input class="mini-hidden" name="reviewerStatus" id="reviewerStatus"/>
                        </div>
                        <div class="mini-datagrid" id="gridDetail" style="width:100%;height:300px" id="grid1" allowCellValid="true"
                             ondrawcell="onMainDraw" autoload="true" sortField="moneyType" sortOrder="Asc"
                             showSummaryRow="true" allowCellSelect="true" allowCellEdit="true" sortField="moneyType"
                             oncellbeginedit="onBeforeEditMain" onshowrowdetail="onShowRowDetail" onload="afterload"
                             url="/arrivalUse/getDetail?ArrID=${ArrivalRegistrationID}" autoload="true" pageSize="50"
                             showPager="false" ondrawsummarycell="drawSummary">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div width="60" field="Action" align="center" type="expandcolumn" headerAlign="center">#</div>
                                <div width="120" type="comboboxcolumn" field="moneyType" headerAlign="center"
                                     vType="required" align="center">费用类型
                                    <input property="editor" class="mini-combobox" data="moneyType" allowInput="true"
                                           valueFromSelect="true"/>
                                </div>
                                <div width="80" field="Action1" align="center" headerAlign="center">操作</div>
                                <div width="180" field="clientId" type="treeselectcolumn" headerAlign="center"
                                     vType="required" align="center">客户名称
                                    <input property="editor" expandonload="true" class="mini-treeselect"
                                           allowInput="true"
                                           onbeforenodeselect="beforeSelectClient"
                                           valueFromSelect="true" url="/systems/client/getClientTree" popupWidth="400"/>
                                </div>
                                <div field="files" width="100" align="center" headerAlign="center">相关附件</div>
                                <div width="120" align="center" field="guan" headerAlign="center" vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">官费
                                    <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999"/>
                                </div>
                                <div width="120" align="center" field="dai" headerAlign="center" vType="required"
                                     summaryType="sum" dataFormat="float" numberFormat="n2">代理费
                                    <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999"/>
                                </div>
                                <div width="120" align="center" field="total" headerAlign="center" summaryType="sum"
                                     dataFormat="float" numberFormat="n2">合计
                                </div>
                                <div field="createMan" width="100" type="treeselectcolumn" align="center"
                                     headerAlign="center">领用人员
                                    <input property="editor" class="mini-treeselect" valueField="FID"
                                           parentField="PID" onbeforenodeselect="beforeSelectClient"
                                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" style="width:
                               98%;" required="true" resultAsTree="false"/>
                                </div>
                                <div field="createTime" width="120" dataType="date" dateFormat="yyyy-MM-dd"
                                     align="center" headerAlign="center">领用时间
                                </div>
                                <div width="150" field="memo" headerAlign="center" align="center">备注
                                    <input property="editor" class="mini-textbox"/>
                                </div>
                            </div>
                        </div>
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
    <div class="mini-window" title="选择业务协作明细<span style='font-size:10px;color:red'>(更改认领官费或认领代理费后按Enter键确认更改)</span>"
         style="width:90%; height:400px" id="AddWindow">
        <div class="mini-toolbar">
            <table style="width:100%">
                <tr>
                    <td style="width:120px">查询关键字:</td>
                    <td style="width:50%">
                        <input class="mini-textbox" id="ClientQueryText" style="width:400px">
                        <button class="mini-button mini-button-normal" onclick="doClientQuery">&nbsp;模糊搜索&nbsp;</button>
                    </td>
                    <td style="text-align: left;width:18%">
                        <span style="font-size: 20px;font-family: 黑体;color:red" id="MoneyText"></span>
                    </td>
                    <td>
                        <button class="mini-button mini-button-success" onclick="confirmDetail()">确认选择</button>
                        &nbsp;&nbsp;
                        <button class="mini-button mini-button-danger" onclick="closeMe()">关闭窗口</button>
                    </td>
                </tr>
            </table>
        </div>
        <div class="mini-fit">
            <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%" frozenStartColumn="0"
                 onrowclick="onRowClick"
                 frozenEndColumn="4"  allowCellSelect="true" allowHeaderWrap="true"
                 url="/arrivalUse/getMain" autoload="true" ondrawcell="drawMainGrid">
                <div property="columns">
                    <div type="indexcolumn">#</div>
                    <div type="checkcolumn" width="40"></div>
                    <div field="Type" width="100" headerAlign="center" align="center">业务类型</div>
                    <div field="SignTime" width="100" headerAlign="center" align="center" dateFormat="yyyy-MM-dd"
                         allowSort="true">交单时间
                    </div>
                    <div field="ContractNo" width="120" headerAlign="center" allowSort="true">合同编号</div>
                    <div field="ClientName" width="200" headerAlign="center" allowSort="true">客户名称</div>
                    <div field="DocSN" width="120" headerAlign="center" allowSort="true">业务编号</div>
                    <div field="Nums" width="160" headerAlign="center" allowSort="true">专利类型及数量</div>
                    <div field="TotalGuan" width="120" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">交单官费
                    </div>
                    <div field="TotalDai" width="110" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">交单代理费
                    </div>
                    <div field="UsedGuan" width="100" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">已认领官费
                    </div>
                    <div field="UsedDai" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">已认领<br/>代理费
                    </div>
                    <div field="FreeGuan" width="70" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">应收官费<br/>结余
                    </div>
                    <div field="AcceptGuan" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">认领官费
                        <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999"
                               onenter="onEnter" value="0"/>
                    </div>
                    <div field="FreeDai" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">应收代理费<br/>结余
                    </div>
                    <div field="AcceptDai" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">认领<br/>代理费
                        <input property="editor" class="mini-spinner" minValue="0" maxValue="999999999" value="0"
                               onenter="onEnter"/>
                    </div>
                    <div field="AcceptMoney" width="80" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2">领用合计
                    </div>
                    <div field="FreeMoney" width="120" align="right" headerAlign="center" allowSort="true"
                         dataFormat="float" numberFormat="n2" visible="false">结余总额
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="detailGrid_Form" style="display:none;">
        <div id="subGrid" class="mini-datagrid" style="width:100%;height:150px;" showPager="false"
             url="/arrivalUse/getSub">
            <div property="columns">
                <div field="Type" width="100" headerAlign="center" allowSort="true">业务类型</div>
                <div field="DocSN" width="120" headerAlign="center" allowSort="true">业务编号</div>
                <div field="Nums" width="180" align="center" headerAlign="center">业务类型及数量</div>
                <div field="ContractNo" width="180" align="center" headerAlign="center">合同编号</div>
                <div field="Guan" width="100" allowSort="true" headerAlign="center" align="right">领用官费</div>
                <div field="Dai" width="100" allowSort="true" headerAlign="center" align="right">领用代理费</div>
                <div field="Total" width="100" allowSort="true" headerAlign="center" align="right">领用总额</div>
                <div field="CreateTime" width="100" allowSort="true" headerAlign="center" dateFormat="yyyy-MM-dd">领用日期
                </div>
                <div field="CreateMan" width="100" allowSort="true" headerAlign="center">领用人</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var mode = "${Mode}";
        var xMode="Add";
        var formData = ${LoadData};
        var grid = mini.get('#grid1');
        var grid1 = mini.get('#datagrid1');
        var gridD = mini.get('#gridDetail');
        var subGrid = mini.get('#subGrid');
        var form1 = new mini.Form('#Table1');
        var form2 = new mini.Form('#Table2');

        form1.setEnabled(false);
        form2.setEnabled(false);

        if (mode == "saveRenLin") {
            var Table1 = new mini.Form('#Table1');
            var Table2 = new mini.Form('#Table2');
            formData.addTime = new Date(formData.addTime);
            formData.dateOfPayment = new Date(formData.dateOfPayment);
            Table1.setData(formData);
            Table2.setData(formData);
            mini.get('#changeGrid').load({ArrivalRegistrationID: formData.arrivalRegistrationId});
            <#if AttIDS!=''>
            var att=$('#attGrid').fileUploader({mode: 'Browse'});
            att.loadFiles({IDS:mini.get('#AttIDS').getValue()});
            </#if>
        }
        var ClaimStatus = mini.get('claimStatus').getValue();
        $(function () {
            var ReviewerStatus = mini.get('reviewerStatus').getValue();
            var form = new mini.Form("#Table2");
            //已认领过的不能再次认领
            if (ClaimStatus == "2" && ReviewerStatus == 2) {
                form.setEnabled(false);
                xMode="Browse";
            }
            //没有复核的可以修改认领信息
            else if (ReviewerStatus == "1") {
                form.setEnabled(true);
                xMode="Edit";
            }
            //如果认领状态为空或者未认领，则可以认领
            else if (ClaimStatus == "1") {
                form.setEnabled(true);
                xMode="Edit";
            }
        });

        function saveRow() {
            var allMoney = parseFloat(mini.get('#paymentAmounts1').getValue() || 0);
            if (!allMoney) {
                mini.alert('领用金额为空，数据异常!');
                return;
            }
            var rows = mini.clone(gridD.getData());
            if (rows.length == 0) {
                throw "必须要录入费用领用记录!";
            }
            var totalMoney = 0;
            var rs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var max = row["max"];
                var state = parseInt(row["state"] || 0);
                if (state == 1) continue;
                var total = parseFloat(row["total"] || 0);
                if (total > max) {
                    mini.alert("第" + (i + 1) + "行的金额("+total+")超过了允许最大录入金额("+max+")!");
                    return;
                }
                totalMoney += total;
                rs.push(row);
            }
            if (totalMoney > allMoney) {
                mini.alert('领用合计金额('+totalMoney+')超过了允许领用的最大金额('+allMoney+')!');
                return;
            }

            function g() {
                var url = '/arrivalUse/saveSub';
                $.post(url, {Data: mini.encode(rows)}, function (result) {
                    if (result.success) {
                        mini.alert('保存成功!', '系统提示', function () {
                            gridD.reload();
                        });
                    } else {
                        mini.alert(result.message || "保存失败,请稍候重试!");
                    }
                });
            }

            mini.confirm('确认要保存记录吗？', '系统提示', function (act) {
                if (act == 'ok') {
                    g();
                }

            });
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

        function drawMainGrid(e) {
            var record = e.record;
            if (e.field == "AcceptGuan" || e.field == "AcceptDai") {
                e.cellStyle = "background-color:PaleGreen;color:black";
            } else if (e.field == "AcceptMoney") {
                var guan = parseFloat(record["AcceptGuan"] || 0);
                var dai = parseFloat(record["AcceptDai"] || 0);
                e.cellHtml = parseFloat(guan + dai).toFixed(2);
                e.value = guan + dai;
            }
        }

        function afterload() {
            var rows = gridD.getData();
            var num = 0;
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var state = parseInt(row["state"] || 0);
                if (state == 0) num++;
            }
            if (num > 0) {
                if (ClaimStatus == 4) {
                    mini.get('#myToolbar').destroy();
                    mini.get('#RenLin_Commit').destroy();
                }
            }
            if (ClaimStatus == 3 || ClaimStatus == 4) {
                var con = mini.get('#cmdSaveArrival');
                if (con) con.hide();
            }
        }

        function onMainDraw(e) {
            var field = e.field;
            var record = e.record;
            var type = parseInt(record["moneyType"]);
            var id = parseInt(record["id"] || 0);
            if (field == "Action") {
                if (type != 1) e.cellHtml = "";
            } else if (field == "Action1") {
                var id = parseInt(record["id"] || 0);
                if (type == 1) {
                    var uid = record._uid;
                    if (id == 0) {
                        e.cellHtml = '<a  href="javascript:browseCases(\'' + uid + '\')" style="text-decoration: underline">&nbsp;选择交单&nbsp;</a>';
                    }
                }
                var state = parseInt(record["state"] || 0);
                if(id>0){
                    if(state==1) e.cellStyle="<span style='color:red'>审核驳回</span>"; else if(state==2) e.cellHtml="<span " +
                        "style='color:green'>审核通过</span>"; else e
                        .cellHtml="<span style='color:red'>未提交审核</span>";
                }
            }
            else if (field == "total") {
                var guan = parseFloat(record["guan"] || 0);
                var dai = parseFloat(record["dai"] || 0);
                var total = guan + dai;
                if (type != 1) {
                    if (total == 0) {
                        total = parseFloat(record["total"] || 0);
                    }
                }
                if (total > 0) {
                    e.record.total = total;
                    e.cellHtml = total.toFixed(2);
                } else e.cellHtml = "";
            }
            else if(field=="clientId"){
                var clientId=e.value;
                if(!clientId){
                    e.cellHtml="点击我,按企业名称模糊搜索。";
                }
            }
            else if(field=="files"){
                var fs=e.value;
                var uText="";

                if((fs||"").toString().length>0){
                    uText="管理附件";
                    if(xMode=="Browse") uText="查看附件";
                } else {
                    if(xMode!="Browse") uText="上传附件";
                }
                var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                    ' onclick="uploadRow(' + "'" + record._id + "','"+ xMode + "')" + '">'+'&nbsp;' + uText + '&nbsp;' +
                    '</a>';
                e.cellHtml = x;
            }
            var state = parseInt(record["state"] || 0);
            if (state == 1) {
                e.rowStyle = "text-decoration:line-through;text-decoration-color: black;color:red";
            }
        }

        function onBeforeEditMain(e) {
            var record = e.record;
            var field=e.field;
            var moneyType = parseInt(record["moneyType"] || 0);
            var clientID = parseInt(record["clientId"] || 0);
            var auditMan = parseInt(record["auditMan"] || 0);
            var id = parseInt(record["id"] || 0);
            var field = e.field;
            var state = parseInt(record["state"] || 0);
            //新增
            if(id==0){
                //未选
                if(moneyType==0){
                    if(field=="moneyType"){
                        e.cancel=false;
                    } else e.cancel=true;
                }
                else {
                    //MoneyType选择以后
                    if(moneyType==1){
                        if(field=="moneyType" || field=="clientId"){
                            e.cancel=true;
                        }else e.cancel=false;
                    } else {
                        e.cancel=false;
                    }
                }
            }
            else {
                if(state==2){
                    e.cancel=true;
                }
                else {
                    if(moneyType==1){
                        if(field=="moneyType" || field=="clientId"){
                            e.cancel=true;
                        }else e.cancel=false;
                    } else {
                        e.cancel=false;
                    }
                }
            }
        }

        //接收一条记录。
        function acceptOne(sRow, id) {
            var selRow = gridD.getSelected();
            if (selRow) {
                var arrivalId = mini.get('#arrivalRegistrationId').getValue();
                var row = {};
                row.id = id;
                row.arrID = arrivalId;
                row.clientId = sRow["ClientID"] || 0;
                row.dai=parseFloat(sRow["AcceptDai"] || 0);
                row.guan=parseFloat(sRow["AcceptGuan"] || 0);
                row.total = parseFloat(sRow["AcceptDai"] || 0) + parseFloat(sRow["AcceptGuan"] || 0);
                row.max = sRow["FreeMoney"];
                row.casesId = sRow["CasesID"];

                gridD.updateRow(selRow, row);
            }
        }

        function removeOne() {
            var row = gridD.getSelected();
            if (row) {
                var id = row["id"];
                var arrId = row["arrId"];
                var auditMan = parseInt(row["auditMan"] || 0);
                if (auditMan > 0) {
                    mini.alert('已审核记录无法删除!');
                    return;
                }
                if (id) {
                    mini.confirm('确认要删除选择的记录吗？', '删除提示', function (act) {
                        if (act == 'ok') {
                            var url = '/arrivalUse/removeOne';
                            $.post(url, {ArrID: arrId, ID: id}, function (result) {
                                if (result.success) {
                                    mini.alert('删除成功!', '系统提示', function () {
                                        gridD.removeRow(row);
                                    });
                                } else mini.alert(result.message || "删除失败!");
                            });
                        }
                    });
                } else {
                    mini.alert('删除成功!', '系统提示', function () {
                        gridD.removeRow(row);
                    });
                }
            }
        }

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

        function RenLin_Save() {
            var form = new mini.Form('#Table2');
            var rows = [];
            try {
                rows = getRen();
            } catch (e) {
                mini.alert(e);
                return;
            }

            var Data = form.getData();
            var Text = compreObj(formData, Data);

            form.validate();
            if (form.isValid()) {
                form.loading("保存中......");
                var arg = {
                    Data: mini.encode(Data),
                    Text: Text,
                    Sub: mini.encode(rows),
                    CommitType: "Arrival"
                };
                var url = "/finance/arrival/saveRenLin";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data = res.data || {};
                            mini.alert('销售回款信息保存成功', '系统提示', function () {
                                form.setData(data);
                                if (window["CloseOwnerWindow"]) window.CloseOwnerWindow();
                            });
                        } else {
                            mini.alert(res.message || "保存失败，请稍候重试!");
                        }
                        form.unmask();
                    }
                );
            }
        }

        function RenLin_Commit() {
            gridD.validate();
            if (gridD.isValid() == false) {
                var error = gridD.getCellErrors()[0];
                gridD.beginEditCell(error.record, error.column);
                mini.alert('请将【' + error.column.header + "】的内容录入完整以后再提交财务审核!");
                return;
            }
            var rows=gridD.getData();
            if(rows.length==0){
                mini.alert('领用记录为空，不允许提交审核!');
                return ;
            }
            function g() {
                var form = new mini.Form('#Table2');
                var rows = [];
                try {
                    rows = getRen();
                } catch (e) {
                    mini.alert(e);
                    return;
                }

                var Data = form.getData();
                var Text = compreObj(formData, Data);

                form.validate();
                if (form.isValid()) {
                    form.loading("正在提交数据......");
                    var arg = {
                        Data: mini.encode(Data),
                        Text: Text,
                        Sub: mini.encode(rows),
                        CommitType: "Arrival"
                    };
                    var url = "/finance/arrival/commitRenLin";
                    $.post(url, arg,
                        function (text) {
                            var res = mini.decode(text);
                            if (res.success) {
                                var data = res.data || {};
                                mini.alert('销售回款信息提交审核成功,请注意：该笔回款只有在财务审核后才能继续进行领款操作!', '系统提示', function () {
                                    form.setData(data);
                                    if (window["CloseOwnerWindow"]) window.CloseOwnerWindow();
                                });
                            } else {
                                mini.alert(res.message || "保存失败，请稍候重试!");
                            }
                            form.unmask();
                        }
                    );
                }
            }
            mini.confirm('提交审核后将不能再更改领用记录，是否继续？', '提交审核', function (act) {
                if (act == 'ok') {
                    g();
                }
            });
        }

        function getRen() {
            var allMoney = parseFloat(mini.get('#paymentAmounts1').getValue() || 0);
            var rows = mini.clone(gridD.getData());
            if (rows.length == 0) {
                mini.alert('请选择认领记录!');
                return;
            }
            var totalMoney = 0;
            var rs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var state = parseInt(row["state"] || 0);
                if (state == 1) {
                    continue;
                }

                var dai=parseFloat(row["dai"] || 0);
                var guan=parseFloat(row["guan"] || 0);
                var money = guan+dai;
                var total=parseFloat(row["total"] || 0);
                if(total<=0){
                    throw '认领金额合计必须要大于0元。';
                }
                rs.push(row);
                totalMoney += money;
            }
            totalMoney=Math.floor(totalMoney*100)/100;
            if (totalMoney > allMoney) throw "累计认领的金额("+totalMoney+")超过了到帐金额("+allMoney+")!";
            if(totalMoney==0) throw "合计认领金额必须要大于0元。";
            mini.getbyName('officalFee').setValue(0);
            mini.getByName('agencyFee').setValue(0);
            return rs;
        }

        function compreObj(obj1, obj2) {
            var text = "";
            for (var x in obj2) {
                if (obj2.hasOwnProperty(x)) {
                    if ((x == "officalFee" || x == "agencyFee" || x == "customerId" || x == "remark") && obj1[x] == undefined && obj2[x] != "") {
                        if (x == "customerId") {
                            text += "新增了:" + getZDName(x) + ",值为:【" + getKHName(obj2) + "】;";
                        } else {
                            text += "新增了:" + getZDName(x) + ",值为:【" + obj2[x] + "】;";
                        }
                    }
                }
            }
            return text;

            function getKHName(obj) {
                var khNameText = "";
                for (var i in obj) {
                    if (i == "khName") {
                        khNameText = obj[i];
                    }
                }
                return khNameText;
            }

            function getZDName(Name) {
                var NameText = "";
                switch (Name) {
                    case "officalFee":
                        NameText = "官费";
                        break;
                    case "agencyFee":
                        NameText = "代理费";
                        break;
                    case "customerId":
                        NameText = "所属客户";
                        break;
                    case "remark":
                        NameText = "费用用途备注";
                        break;
                }
                return NameText;
            }
        }

        function addNewRow() {
            var rows = gridD.getData();
            var allMoney = parseFloat(mini.get('#paymentAmounts1').getValue() || 0);
            if (rows.length > 0) {
                var p = 0;
                var num = 0;
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var state = parseInt(row["state"]);
                    var total = parseFloat(row["total"]);
                    if (state == 2)
                    {
                        p++;num += total;
                    }
                }
                if (num > allMoney) {
                    mini.alert('回款已领用完成，无法再新建领用!', '系统提示');
                    return;
                }
            }
            gridD.validate();
            if (gridD.isValid() == false) {
                var error = gridD.getCellErrors()[0];
                gridD.beginEditCell(error.record, error.column);
                mini.showTips({
                    content: "请填写【" + error.column.header + '】的内容，否则不能继续新增记录!',
                    state: 'danger',
                    x: 'center',
                    y: 'center',
                    timeout: 5000
                });
                return;
            }
            var arrivalId = mini.get('#arrivalRegistrationId').getValue();
            var row = {arrID: arrivalId, max: 9999999, canUse: 1,dai:0.00,guan:0.00,total:0.000};
            var rows = gridD.getData();
            if (rows.length > 0) {
                row.clientId = rows[0]["clientId"] || 0;
                row.clientName = rows[0]["clientName"];
            }
            var newRow = gridD.addRow(row);
            gridD.validate();
            if (gridD.isValid() == false) {
                var error = gridD.getCellErrors()[0];
                gridD.beginEditCell(error.record, error.column);
                mini.showTips({
                    content: "请选择费用类型后再进行下一步录入操作!",
                    state: 'info',
                    x: 'center',
                    y: 'center',
                    timeout: 5000
                });
            }
        }
        var win = mini.get('#AddWindow');
        function browseCases(uid) {
            var maxMoney = parseFloat(mini.getbyName('paymentAmount').getValue() || 0);
            if (maxMoney <= 0) {
                mini.alert('回款金额为0的业务无法领用!');
                return;
            }
            var win = mini.get('#AddWindow');
            win.show();

            var rows=gridD.getData();
            var totalMoney=0;
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                var state=parseInt(row["state"] || 0);
                if(state==1) continue;

                var dai=parseFloat(row["dai"] || 0);
                var guan=parseFloat(row["guan"] || 0);
                var total=dai+guan;
                totalMoney+=total;
            }
            var freeMoney=(maxMoney-totalMoney).toFixed(2);
            $('#MoneyText').text("可领用金额:"+freeMoney);
            grid1.reload();
        }

        function confirmDetail() {
            grid1.commitEdit();
            var row = grid1.getSelected();
            if (row) {
                var maxMoney = parseFloat(mini.getbyName('paymentAmount').getValue() || 0);
                if (maxMoney <= 0) {
                    return;
                }
                var ccMoney = 0;
                var rows = gridD.getData();
                for (var i = 0; i < rows.length; i++) {
                    var srow = rows[i];
                    var state = parseInt(srow["state"] || 0);
                    if (state == 1) continue;
                    ccMoney += parseFloat(srow["total"] || 0);
                }
                var acceptGuan = parseFloat(row["AcceptGuan"] || 0);
                var freeGuan = parseFloat(row["FreeGuan"] || 0);
                if (acceptGuan > freeGuan) {
                    mini.alert('领用的官费金额('+acceptGuan+')超过了应收官费结余('+freeGuan+')!');
                    return;
                }
                var acceptDai = parseFloat(row["AcceptDai"] || 0);
                var freeDai = parseFloat(row["FreeDai"] || 0);
                if (acceptDai > freeDai) {
                    mini.alert('领用的代理费金额('+acceptDai+')超过了应收代理费结余('+freeDai+')!');
                    return;
                }

                var acceptTotal = Math.floor((acceptDai + acceptGuan)*100)/100;
                var feeTotal = parseFloat(row["FreeMoney"] || 0);
                if(acceptTotal==0) {
                    mini.alert('领用的合计金额必须要大于0元!');
                    return;
                }
                if (acceptTotal > feeTotal) {
                    mini.alert('领用金额合计('+acceptTotal+')超过了应收款总额('+feeTotal+')!');
                    return;
                }
                if (acceptTotal > maxMoney) {
                    mini.alert('本次领用金额('+acceptTotal+')超过了回款总金额('+maxMoney+')!');
                    return;
                }
                if (ccMoney + acceptTotal > maxMoney) {
                    mini.alert('累计领用金额加上本次领用金额('+(ccMoney+acceptTotal)+')超过了回款总金额('+maxMoney+')!');
                    return;
                }
                var arrivalId = mini.get('#arrivalRegistrationId').getValue();
                var arg = {
                    ArrID: arrivalId,
                    ClientID: row["ClientID"],
                    ClientName: row["ClientName"],
                    Dai: row["AcceptDai"],
                    Guan: row["AcceptGuan"],
                    Total: acceptTotal,
                    CasesID: row["CasesID"],
                    MoneyType: 1
                };
                var url = '/arrivalUse/saveDetail';
                $.post(url, {Data: mini.encode([arg])}, function (result) {
                    if (result.success) {
                        mini.alert('业务交单记录选择并保存成功', '系统提示', function () {
                            acceptOne(row, result.data);
                            win.hide();
                        });
                    } else mini.alert(result.message || "保存失败，请稍候重试!");
                })
            } else {
                mini.alert('请选择一条交单记录!');
            }
        }

        var detailGrid_Form = document.getElementById("detailGrid_Form");

        function closeMe() {
            win.hide();
        }

        function onShowRowDetail(e) {
            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            subGrid.load({CasesID: row["casesId"], ArrID: row["arrId"], Total: row["total"]})
        }

        function doClientQuery() {
            var txt = mini.get('#ClientQueryText').getValue();
            var arg = {};
            if (txt) {
                var cs = [];
                var fields = ['DocSN', 'ContractNo', 'Nums', 'ClientName'];
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var op = {field: field, oper: 'LIKE', value: txt};
                    cs.push(op);
                }
                arg["Query"] = mini.encode(cs);
            }
            grid1.load(arg);
        }
        var curRow=null;
        function onRowClick(e){
            var row=e.row;
            if(row){
                if(curRow){
                    if(curRow._id!=row._id){
                        grid1.commitEdit();
                        grid1.beginEditRow(row);
                        curRow=row;
                    }
                } else {
                    grid1.beginEditRow(row);
                    curRow=row;
                }
            }
        }
        function onEnter(){
            var row=grid1.getSelected();
            if(row){
                grid1.commitEdit();
                curRow=null;
            }
        }
        function beforeSelectClient(e){
            if (e.isLeaf == false) e.cancel = true;
        }


        function uploadRow(ID,mode) {
            var row = gridD.getRowByUID(ID);
            var attId =row["files"] || "" ;
            mini.open({
                url: '/attachment/addFile?IDS=' + attId + '&Mode='+mode,
                width: 1100,
                height:500,
                title: '费用附件',
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                        var attId = data.AttID;
                        var files=(row["files"] || "").split(",") ||[];
                        files.push(attId);
                        gridD.updateRow(row,{files:files.join(',')});
                    });
                    iframe.contentWindow.addEvent('eachFileRemoved', function (data) {
                        var attId = data.AttID;
                        var files=(row["files"] || "").split(",") ||[];
                        var cc=[];
                        for(var i=0;i<files.length;i++){
                            var f=$.trim(files[i]);
                            if(!f) continue;
                            if(f!=attId){
                                cc.push(f);
                            }
                        }
                        gridD.updateRow(row,{files:cc.join(',')});
                    });
                }
            });
        }
    </script>
</@layout>