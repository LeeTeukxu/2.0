<#include "/shared/layout.ftl">
<@layout>
    <style type="text/css">
        .New_Button, .Edit_Button, .Delete_Button, .Update_Button, .Cancel_Button,
        .AlreadyPay_Button, .ExportPay_Button, .Remove_Button, .AuditOpinion_Button {
            font-size: 11px;
            color: #1B3F91;
            font-family: Verdana;
            margin-right: 8px;
        }
    </style>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="center" style="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-edit" id="FeeList_Edit" href="javascript:editRow('1')">编辑</a>
                            <a class="mini-button" iconcls="icon-remove" id="FeeList_Remove" href="javascript:remove('1')">删除</a>
                            <a class="mini-button" iconcls="icon-tip" id="FeeList_ExportFee" href="javascript:exportPay('1')">导出缴费清单</a>
                            <a class="mini-button" iconcls="icon-ok" id="FeeList_AlreadyPay" href="javascript:alreadyPay('1')">已缴费</a>
                            <a class="mini-button" iconcls="icon-user" id="FeeList_AddAuditOpinion" href="javascript:addAuditOpinion('1')">审核不通过</a>
                            <a class="mini-button mini-button-info" id="FeeList_Export" onclick="doExport">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field"  id="comField" style="width:100px" data="[{id:'All',text:'全部属性'},
                            {id:'jfqd',text:'缴费清单'},{id:'invoiceTitle',text:'发票抬头'},{id:'LinkMan',text:'收据接收人'},
                            {id:'Address',text:'接收人地址'},{id:'Mobile',text:'接收人电话'},{id:'auditOpinion',text:'审核意见'}]" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field" style="width:150px" id="QueryText" />
                            <a class="mini-button mini-button-success" onclick="doQuery();"
                               id="PatentInfoBrowse_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger" id="FeeList_HighQuery"
                               onclick="expand">高级搜索</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:120px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">缴纳期限：</td>
                            <td style="width:15%;"><input name="yingjiaof_jiaofeijzr" class="mini-datepicker" data-oper="GE"
                                                          style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;"><input name="yingjiaof_jiaofeijzr"  class="mini-datepicker"  data-oper="LE" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">清单名称</td>
                            <td style="width:15%;"><input name="jfqd" class="mini-textbox" data-oper="LIKE" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">发票抬头：</td>
                            <td style="width:15%;"><input name="invoiceTitle" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%" /></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">收据接收人：</td>
                            <td style="width:15%;"><input name="LinkMan"  class="mini-textbox" data-oper="LIKE" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">接收人地址：</td>
                            <td style="width:15%;"><input name="Address"class="mini-textbox" data-oper="LIKE"style="width:100%"
                            /></td>
                            <td style="width:6%;padding-left:10px;">收件人电话：</td>
                            <td style="width:15%;"><input name="Mobile" class="mini-textbox" data-oper="LIKE"style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">审核意见：</td>
                            <td style="width:15%;"><input name="auditOpinion" class="mini-textbox" data-oper="LIKE"style="width:100%" /></td>
                        </tr>
                        <tr>
                            <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" onclick="doHightSearch">搜索</a>
                                <a class="mini-button mini-button-danger" onclick="expand">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="FeeList_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" onshowrowdetail="onShowFeeListDetail"
                     allowresize="true" url="/work/feeList/getData" pagesize="20" sizelist="[5,10,20,50,100,150,200,500,
                     1000]" sortfield="CreateTime" sortorder="desc" autoload="true">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="expandcolumn" width="40">#</div>
                        <div field="jfqd_id" width="120" headeralign="center" allowsort="true">
                            清单编号
                        </div>
                        <div field="jfqd" name="jfqd" width="120" headeralign="center" allowsort="true">
                            清单名称
                        </div>
                        <div field="yingjiaof_shijiyjje" width="60" headeralign="center" align="right" allowsort="true">
                            缴费金额
                        </div>
                        <div field="rengong_jdje" width="100" headeralign="center" align="right" allowsort="true">
                            人工校对金额
                        </div>
                        <div field="yingjiaof_jiaofeijzr" width="80" headeralign="center" align="center" allowsort="true">
                            缴纳期限
                        </div>
                        <div field="CreateTime" width="120" headeralign="center" align="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">
                            登记时间
                        </div>
                        <div field="CreateEmp" width="80" headeralign="center" allowsort="true">
                            登记人
                        </div>
                        <div field="invoiceTitle" width="180" headeralign="center" allowsort="true">
                            发票抬头
                        </div>
                        <div field="LinkMan" textname="LinkName" name="LinkName" width="80" headeralign="center" allowsort="true">
                            收据接收人
                        </div>
                        <div field="Address" width="140" headeralign="center" allowsort="true">
                            收据接收人地址
                        </div>
                        <div field="PostCode" width="70" headeralign="center" allowsort="true">
                            收件人邮编
                        </div>
                        <div field="Mobile" width="100" headeralign="center" allowsort="true">
                            收件人电话
                        </div>
                        <div field="auditOpinion" width="100" headeralign="center" allowsort="true">
                            审核意见
                        </div>
                        <div field="remark" width="120" headeralign="center" allowsort="true">
                            备注
                        </div>
                    </div>
                </div>
                <div id="detailGrid_Form" style="display:none;">
                    <div id="FeeListDetail_datagrid" class="mini-datagrid" style="width:100%;"
                         url="/work/feeList/getFeeItemData">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div field="yingjiaof_jiaofeijzr" name="yingjiaof_jiaofeijzr" width="100" headeralign="center" align="center">
                                缴纳期限
                            </div>
                            <div field="yingjiaofydm" name="yingjiaofydm" width="180" headeralign="center">
                                费用项目
                            </div>
                            <div field="yingjiaof_shijiyjje" name="yingjiaof_shijiyjje" width="60" headeralign="center" align="right">
                                金额
                            </div>
                            <div field="rengong_jdje" name="rengong_jdje" width="100" headeralign="center" align="right">
                                人工校对金额
                            </div>
                            <div field="shenqingh" name="shenqingh" width="120" headeralign="center" renderer="onZhanlihao">
                                专利申请号
                            </div>
                            <div field="zhuanlimc" name="zhuanlimc" width="200" headeralign="center">
                                专利名称
                            </div>
                            <div field="zhuanlilxText" width="100" headeralign="center">
                                专利类型
                            </div>
                            <div field="shenqingrxm" width="100" headeralign="center">
                                申请人信息
                            </div>
                            <div width="240" headeralign="center" field="NEIBUBH">内部编号</div>
                            <div width="120" headeralign="center" field="KH">归属客户</div>
                            <div width="100" headeralign="center" field="YW">销售维护人</div>
                            <div width="100" headeralign="center" field="DL">代理责任人</div>
                            <div width="100" headeralign="center" field="LC">流程责任人</div>
                        </div>
                    </div>
                </div>

                <div id="editWindow" class="mini-window" title="编辑发票信息" showmodal="true"
                     allowresize="true" allowdrag="true" style="width:1000px;">
                    <div id="editform" class="form">
                        <input class="mini-hidden" name="jfqd_id" />
                        <table style="width:100%;">
                            <tr>
                                <td style="width:80px;">清单名称：</td>
                                <td ><input name="jfqd" class="mini-textbox"  style="width:100%"/></td>
                                <td style="width:80px;">缴费金额：</td>
                                <td ><input name="yingjiaof_shijiyjje" readonly="readonly" class="mini-textbox"   style="width:100%"/></td>
                                <td style="width:80px;">缴纳期限：</td>
                                <td ><input name="yingjiaof_jiaofeijzr" readonly="readonly" class="mini-textbox"   style="width:100%"/></td>
                            </tr>
                            <tr>
                                <td style="width:80px;">发票抬头：</td>
                                <td ><input name="invoiceTitle" class="mini-textbox"   style="width:100%"></td>
                                <td style="width:80px;">收据接收人：</td>
                                <td ><input name="LinkMan" textname="LinkMan" class="mini-buttonedit" onbuttonclick="onCustomLinkerDialog"   style="width:100%"/></td>
                                <td style="width:80px;">接收人地址：</td>
                                <td ><input name="Address" class="mini-textbox"   style="width:100%"/></td>
                            </tr>
                            <tr>
                                <td style="width:80px;">地址邮编：</td>
                                <td ><input name="PostCode" class="mini-textbox"   style="width:100%"/></td>
                                <td style="width:80px;">联系电话：</td>
                                <td ><input name="Mobile" class="mini-textbox"   style="width:100%"/></td>
                                <td style="width:80px;">备注：</td>
                                <td ><input name="remark" class="mini-textbox"   style="width:100%"/></td>
                            </tr>
                            <tr>
                                <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="6">
                                    <a class="Update_Button" href="javascript:updateRow();">更新</a>
                                    <a class="Cancel_Button" href="javascript:cancelRow();">取消</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var fit=mini.get('fitt');
        var tip = new mini.ToolTip();
        var grid=mini.get('FeeItem_datagrid');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        $(function(){
            $('#p1').hide();
            fit.setHeight('100%');
            fit.doLayout();
        });
        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("panel-collapse");
                btn.setText("高级查询");
                $('#p1').css('display', "block");

            } else {
                btn.setIconCls("panel-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
            }
            fit.setHeight('100%');
            fit.doLayout();
        }
        var grid = mini.get("FeeList_datagrid");
        function onShowFeeListDetail(e) {
            var FeeList_datagrid = mini.get("FeeListDetail_datagrid");
            var detailGrid_Form = document.getElementById("detailGrid_Form");

            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            td.appendChild(detailGrid_Form);
            detailGrid_Form.style.display = "block";
            FeeList_datagrid.load({ jfqd_id: row.jfqd_id });
        }
        function editRow(row_uid) {
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择需要编辑的行');
                return ;
            }
            if (row) {
                if (row) {
                    var td = grid.getRowDetailCellEl(row);
                    var form = new mini.Form("#editform");
                    var editWindow = mini.get("editWindow");
                    editWindow.show();
                    form.clear();
                    form.setData(row);
                }
            }
        }
        function updateRow(row_uid) {
            var editWindow = mini.get("editWindow");
            var form = new mini.Form("#editform");
            var o = form.getData();
            var json = mini.encode(o);
            $.post("/work/feeList/save", { Data: json }, function (r) {
                if (r['success']) {
                    mini.alert("保存成功");
                    grid.reload();
                } else {
                    mini.alert("保存失败");
                }
            });
            editWindow.hide();
        }
        function doHightSearch(){
            var arg = {};
            var form=new mini.Form('#highQueryForm');
            var fields=form.getFields();
            var result=[];
            for(var i=0;i<fields.length;i++){
                var field=fields[i];
                var val=field.getValue();
                if(val!=null && val!=undefined)
                {
                    if(val!='')
                    {
                        var obj={field:field.getName(),value:field.getValue(),oper:field.attributes["data-oper"]};
                        result.push(obj);
                    }
                }
            }
            arg["High"]=mini.encode(result);
            grid.load(arg);
        }
        function doQuery() {
            var arg = {};
            var bs = [];
            var cs = [];
            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL") f = "NEIBUBH";
                        var kWork=f+'='+word;
                        if(cs.indexOf(kWork)==-1){
                            var op={field:f,oper:'LIKE',value:word};
                            cs.push(op);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                    var op={field:field,oper:'LIKE',value:word};
                    cs.push(op);
                }
            }
            if(cs.length>0)arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function remove(row_uid) {
            var grid = mini.get("FeeList_datagrid");
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择需要编辑的行');
                return ;
            }
            var jfqd_id = row.jfqd_id
            $.post("/work/feeList/remove", { ID: jfqd_id }, function (r) {
                if (r['success']) {
                    mini.alert('删除成功!','系统提示',function(){
                        grid.reload();
                    });

                } else {
                    mini.alert("删除失败");
                }
            });
        }
        function alreadyPay(row_uid) {
            var grid = mini.get("FeeList_datagrid");
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择需要设置的的记录行');
                return ;
            }
            var jfqd_id = row.jfqd_id
            $.post("/work/feeList/alreadyPay", { ID: jfqd_id }, function (r) {
                if (r['success']) {
                    mini.alert('标记成功!','系统提示',function(){
                        grid.reload();
                    });
                } else {
                    mini.alert("标记失败");
                }
            });
        }
    </script>
</@js>