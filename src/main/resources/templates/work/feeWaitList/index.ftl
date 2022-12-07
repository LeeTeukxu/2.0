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
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js?v=${version}"></script>
    <script type="text/javascript" src="/js/common/complexExcelExport.js?v=${version}"></script>
    <script type="text/javascript" src="/js/common/excelExport.js?v=${version}"></script>
    <script type="text/javascript">
        var types = [
            {id:0,text:'发明专利'}, {id:1,text:'新型专利'}, {id:2,text:'外观专利'}
        ];
    </script>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="center" style="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-edit" id="FeeWait_Edit" href="javascript:editRow('1')">编辑</a>
                            <a class="mini-button" iconcls="icon-tip" id="FeeWait_ExportFee" href="javascript:abcd()">导出缴费清单</a>
                            <a class="mini-button" iconcls="icon-ok" id="FeeWait_AlreadyPay" onclick="confirmPayFor()">已缴费</a>
                            <a class="mini-button" iconcls="icon-user" id="FeeWait_AddAuditOpinion" onclick="rejectPayFor()">审核不通过</a>
                            <a class="mini-button mini-button-danger" id="FeeItem_Remove"
                               onclick="removeWaitItem">删除待缴项目</a>
                            <a class="mini-button mini-button-info" id="FeeWait_Export" onclick="doExport">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field FeeWait_Query"  id="comField" style="width:100px"
                                 data="[{id:'All',text:'全部属性'},{id:'ShenQingh',text:'专利号'},{id:'FeeName',text:'缴费清单'},{id:'TickHeader',text:'发票抬头'},
                            {id:'LinkMan',text:'收据接收人'},{id:'Address',text:'接收人地址'},{id:'LinkPhone',text:'接收人电话'},
                            {id:'AuditText',text:'审核意见'}]" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field FeeWait_Query" style="width:150px" id="QueryText" />
                            <a class="mini-button mini-button-success" onclick="doQuery();"
                               id="FeeWait_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger" id="FeeWait_HighQuery"
                               onclick="expand">高级搜索</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:120px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">缴纳期限：</td>
                            <td style="width:15%;"><input name="JiaoFeiDate" class="mini-datepicker" data-oper="GE"
                                                          style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;"><input name="JiaoFeiDate"  class="mini-datepicker"  data-oper="LE" style="width:100%" /></td>
                            <td style="width:6%;padding-left:10px;">清单名称</td>
                            <td style="width:15%;"><input name="FeeName" class="mini-textbox" data-oper="LIKE" style="width:100%"
                            /></td>
                            <td style="width:6%;padding-left:10px;">发票抬头：</td>
                            <td style="width:15%;"><input name="TickHeader" class="mini-textbox" data-oper="LIKE" style="width:100%" /></td>
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
                            <td style="width:15%;"><input name="AuditText" class="mini-textbox" data-oper="LIKE"style="width:100%"
                            /></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" onclick="doHightSearch"
                                   style="width:120px">搜索</a>
                                <a class="mini-button mini-button-danger"  style="margin-left:30px;width:120px"
                                   onclick="expand">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="FeeWait_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" onshowrowdetail="onShowFeeListDetail"
                     allowresize="true" url="/watch/feeWaitList/getData" pagesize="20" sizelist="[5,10,20,50,100,150,
                     200]" sortfield="JiaoFeiDate" sortorder="asc" autoload="true" ondraw="onDraw" multiSelect="true">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div type="expandcolumn" width="40">#</div>
                        <div field="FlowCode" width="120" headeralign="center" allowsort="true">
                            清单编号
                        </div>
                        <div field="FeeName" name="feeName" width="180" headeralign="center" allowsort="true">
                            清单名称
                        </div>
                        <div field="Money" width="70" headeralign="center" align="right" allowsort="true">
                            缴费金额
                        </div>
                        <div field="XMoney" width="70" headeralign="center" align="right" allowsort="true">
                            校对金额
                        </div>
                        <div field="JiaoFeiDate" width="110" headeralign="center" align="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">
                            缴纳期限
                        </div>
                        <div field="CreateTime" width="110" headeralign="center" align="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">
                            登记时间
                        </div>
                        <div field="CreateManName" width="70" headeralign="center" allowsort="true">
                            登记人
                        </div>
                        <div field="TickHeader" width="180" headeralign="center" allowsort="true">
                            发票抬头
                        </div>
                        <div field="Receiver" textname="LinkName" name="LinkName" width="180" headeralign="center"
                             allowsort="true" visible="false">
                            收据接收人
                        </div>

                        <div field="LinkMan" width="80" headeralign="center" allowsort="true">
                            收件人
                        </div>
                        <div field="LinkPhone" width="100" headeralign="center" allowsort="true">
                            联系电话
                        </div>
                        <div field="Address" width="200" headeralign="center" allowsort="true">收件地址</div>
                        <div field="Memo" width="120" headeralign="center" allowsort="true">
                            备注
                        </div>
                    </div>
                </div>
                <div id="detailGrid_Form" style="display:none;">
                    <div id="FeeListDetail_datagrid" class="mini-datagrid" style="width:100%;"
                         url="/watch/feeWaitList/getDetail" autoload="false">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div field="JIAOFEIR" name="JIAOFEIR" width="100" headeralign="center"
                                 align="center" dataType="date" dateFormat="yyyy-MM-dd">
                                缴纳期限
                            </div>
                            <div field="FEENAME" name="feeName" width="180" headeralign="center">
                                费用项目
                            </div>
                            <div field="MONEY" name="money" width="60" headeralign="center" align="right">
                                缴费金额
                            </div>
                            <div field="XMONEY" name="xmoney" width="100" headeralign="center" align="right">
                                人工校对金额
                            </div>
                            <div field="SHENQINGH" name="shenqingh" width="120" headeralign="center" renderer="onZhanlihao">
                                专利申请号
                            </div>
                            <div field="ZHUANLIMC" name="zhuanlimc" width="200" headeralign="center">
                                专利名称
                            </div>
                            <div field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                                专利类型
                                <input property="editor" class="mini-combobox" data="types" />
                            </div>
                            <div field="SHENQINGRXM" width="100" headeralign="center">
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
        var grid = mini.get("FeeWait_datagrid");
        function onShowFeeListDetail(e) {
            var FeeWait_datagrid = mini.get("FeeListDetail_datagrid");
            var detailGrid_Form =FeeWait_datagrid.el.parentNode;

            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            if(detailGrid_Form) {
                td.appendChild(detailGrid_Form);
                detailGrid_Form.style.display = "block";
            }
            FeeWait_datagrid.setData([]);
            var ids=row["FeeItemID"];
            if(ids)
            {
                FeeWait_datagrid.load({ IDS:ids,Type:row["Type"]});
            } else {
                mini.alert('没有发现可展示的数据。');
            }
        }
        function editRow(row_uid) {
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择需要编辑的行');
                return ;
            }
            if (row) {
                var id=row["ID"];
                mini.open({
                    url:'/watch/addToPayForWait/index?IDS='+id+'&Mode=Edit&Type=Year',
                    width:800,
                    height:410,
                    showModal:true,
                    title:'添加到缴费清单',
                    ondestroy:function(action){
                        if(action=='ok')grid.reload();
                    }
                });
            }
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
        function confirmPayFor(){
            var grid = mini.get("FeeWait_datagrid");
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择需要设置的的记录行');
                return ;
            }
            var id=row["ID"];
            var shenqingh=row["ShenQingh"];
            var otherofficeid=row["FeeItemID"];
            if(id){
                mini.prompt('确认缴费结果','缴费操作',function(action,value){
                    if(action=='ok'){
                        var url='/watch/feeWaitList/confirm';
                        $.post(url,{ID:id,Text:value,SHENQINGH:shenqingh,OtherOfficeID:otherofficeid},function(result){
                            if(result.success){
                                mini.alert('设置为已缴费成功!');
                                grid.reload();

                            } else  mini.alert(result.message || '设置失败，请稍候重试!');
                        });
                    }
                },true);
               var tt= $('.mini-messagebox-content').find('textarea');
               if(tt.length>0){
                   tt.val('已完成缴费。');
               }
            }
        }
        function rejectPayFor(){
            var grid = mini.get("FeeWait_datagrid");
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择需要设置为不缴费的的记录行');
                return ;
            }
            var id=row["ID"];
            if(id){
                mini.confirm('确认要将选择的项目设置为不缴费状态?','设置提示',function(result){
                    if(result=='ok'){
                        var url='/watch/feeWaitList/reject';
                        $.post(url,{ID:id},function(result){
                            if(result.success){
                                mini.alert('设置为不缴费状态成功!','系统提示',function(){
                                    grid.reload();
                                });
                            } else {
                                mini.alert(result.message || "设置失败，请稍候重试！");
                            }
                        });
                    }
                });
            }
        }
        function doExport(){

            var grid = mini.get("FeeWait_datagrid");
            var excel=new excelData(grid);
            excel.export("待缴费用清单明细.xls");
        }
        function onDraw(e){


        }
        function exportPay(){
            var FeeWait_datagrid = mini.get("FeeListDetail_datagrid");
            var row=grid.getSelected();
            if(row){
                var detailGrid_Form =FeeWait_datagrid.el.parentNode;
                if(detailGrid_Form.style.display == "block"){
                  //  abcd();
                }
                else {
                    var ids=row["FeeItemID"];
                    if(ids)
                    {
                        //FeeWait_datagrid.on('load',abcd);
                        FeeWait_datagrid.load({ IDS:ids,Type:row["Type"]});
                    }
                }
            } else mini.alert('请选择一条待缴费记录!');
        }
        function abcd(){
            function g(datas){
                var grid = mini.get("FeeWait_datagrid");
                var billObject= {Rows:datas};
                var excel = new complexExcelData(grid);
                excel.setAutoCreateNew(0);
                excel.setSheetName("国家申请号缴费");
                excel.setNumberCell("MONEY");
                excel.export('WaitFee', '待缴费用清单列表.xls',billObject);
            }
            var url='/watch/feeWaitList/getSelectedDatas';
            var rows=grid.getSelecteds();
            if(rows.length==0){
                mini.alert('请选择要导出明细的记录!');
                return ;
            }
            var ids=[];
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                ids.push(row["ID"]);
            }
            if(ids.length==0){
                mini.alert('请选择要导出明细的记录!');
                return ;
            }
            $.getJSON(url,{IDS:ids.join(',')},function(result){
                if(result.success){
                    var datas=result.data || [];
                    g(datas);
                } else g();
            })
        }
        function removeWaitItem(){
            var row=grid.getSelected();
            if(row){
                var id=row.ID;
                if(id){
                    function g(){
                        var url='/watch/feeWatch/removeOne';
                        $.post(url,{ID:id},function(result){
                            if(result.success){
                                mini.alert('选择记录删除成功!','系统提示',function(){
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "删除记录失败，请稍候重试!");
                        })
                    }
                    var flowCode=row["FlowCode"];
                    mini.confirm('确认要删除选择的清单编号为:【'+flowCode+'】的待缴费记录吗？','系统提示',function(result){
                        if(result=='ok'){
                            g();
                        }
                    });
                }
            }
        }
    </script>
</@js>