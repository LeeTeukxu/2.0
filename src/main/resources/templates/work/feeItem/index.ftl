<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript">
        var feeItems=${feeItems};
        var zlItems=${zlItems};
    </script>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="north" height="32" showheader="false" showsplit="false" bodystyle="padding-top:5px"
             splitsize="1px">
        <span>
            <span style="margin-left:20px;border-radius: 50%;height:15px;width:15px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">已过缴费期限</span>
        </span>
            <span>
            <span style="border-radius: 50%;height:15px;width:15px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">
                距离缴费期限0-30天
            </span>
        </span>
            <span>
            <span style="border-radius: 50%;height:15px;width:15px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">
                距离缴费期限31-90天
            </span>
        </span>
            <span>
            <span style="border-radius: 50%;height:15px;width:15px;background-color:yellow">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">距离缴费期限超过91天</span>
        </span>
        </div>
        <div region="center" style="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button mini-button-success" id="FeeItem_NoPayMark"
                               onclick="NoPayMark">标记不缴费</a>
                            <a class="mini-button mini-button-danger" id="FeeItem_PayMark" onclick="PayMark">取消标记不缴费</a>
                            <a class="mini-button mini-button-info" id="FeeItem_AddToFeeList" onclick="AddToFeeList">添加到缴费清单</a>
                            <a class="mini-button mini-button-info" id="FeeItem_Export" onclick="doExport">导出Excel</a>
                            <a class="mini-button mini-button-info" onclick="doExportYear">导出代缴费清单</a>
                            <a class="mini-button mini-button-info" onclick="Jiaodui">校对金额</a>
                            <span class="separator FeeItem_Config"></span>
                            <button class="mini-button FeeItem_Config" iconCls="icon-reload"
                                    onclick="doFixedQuery('yingjiaof_jiaofeijzr', 'LT', '${now}')"
                                    plain="true">已过期</button>
                            <button class="mini-button FeeItem_Config" iconCls="icon-reload"
                                    onclick="doFixedQuery('yingjiaof_jiaofeijzr', 'GT', '${next}')" plain="true">未过期</button>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field"  id="comField" style="width:100px" data="[{id:'All',text:'全部属性'},
                            {id:'jfqd',text:'缴费清单'},{id:'zhuanlimc',text:'专利名称'},{id:'shenqingh',text:'专利申请号'},
                            {id:'shenqingrxm',text:'专利申请人'},{id:'famingrxm',text:'发明人'},{id:'KH',text:'所属客户'},{id:'XS',text:'销售人员'},{id:'DL',text:'代理责任人'},{id:'LC',text:'流程人员'}]" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field" style="width:150px" id="QueryText" />
                            <a class="mini-button mini-button-success" onclick="doQuery();"
                               id="PatentInfoBrowse_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger" id="FeeItem_HighQuery"
                               onclick="expand">高级搜索</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:140px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">缴纳期限：</td>
                            <td style="width:15%;"><input class="mini-datepicker" name="yingjiaof_jiaofeijzr" data-oper="GE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;"><input class="mini-datepicker" name="yingjiaof_jiaofeijzr" data-oper="LE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">费用项目：</td>
                            <td style="width:15%;"><input name="yingjiaofydm" style="width:100%"
                                                          class="mini-combobox" data="feeItems"
                                                          allowInput="true" valueFromSelect="true" data-oper="EQ"/></td>
                            <td style="width:6%;padding-left:10px;">专利类型：</td>
                            <td style="width:15%;"><input name="zhuanlilx"  data-oper="EQ" style="width:100%" class="mini-combobox" data="zlItems"
                                                          allowInput="true" valueFromSelect="true"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">是否缴费：</td>
                            <td style="width:15%;"><input name="pstate" id="ipstate" data-oper="EQ"
                                                          style="width:100%" data="[{ id: '', text: '全部' }, { id:
                                                          '1', text: '需缴费' }, { id: '0', text: '不需缴费' }]"/></td>
                            <td style="width:6%;padding-left:10px;">缴费清单：</td>
                            <td style="width:15%;"><input name="jfqd" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">专利名称：</td>
                            <td style="width:15%;"><input name="zhuanlimc" class="mini-textbox" filtertype="LIKE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">专利申请号：</td>
                            <td style="width:15%;"><input name="shenqingh" class="mini-textbox" filtertype="LIKE"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">专利申请人：</td>
                            <td style="width:15%;"><input name="shenqingrxm" class="mini-textbox" filtertype="LIKE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">专利发明人：</td>
                            <td style="width:15%;"><input name="famingrxm" class="mini-textbox" filtertype="LIKE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">法律状态：</td>
                            <td style="width:15%;"><input name="anjianywzt" class="mini-combobox" valueFromSelect="true"
                                                          data-oper="EQ"
                                                          style="width:100%"  url="/systems/dict/getAllByDtId?dtId=12"/></td>
                            <td style="width:6%;padding-left:25px;position: relative;">内部编号：</td>
                            <td style="width:15%;"><input name="NEIBUBH" class="mini-textbox" emptyText="归属客户/销售维护人/代理责任人/流程责任人" data-oper="LIKE"
                                                          style="width:100%"/></td>
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
                <div id="FeeItem_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                     frozenstartcolumn="0" frozenendcolumn="8" sortorder="asc" sortfield="yingjiaof_jiaofeijzr"
                     ondrawcell="onDraw" allowresize="true" url="/work/feeItem/getData" multiselect="true" pagesize="20"
                     sizelist="[5,10,20,50,100]" autoload="true" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div width="40" field="status" align="center"></div>
                        <div field="Action" width="60" headerAlign="center">备注信息</div>
                        <div field="yingjiaof_jiaofeijzr" name="yingjiaof_jiaofeijzr" width="100" allowsort="true"
                             headeralign="center" align="center" renderer="onZhanlihaoGonggaobj">
                            缴纳期限
                        </div>
                        <div field="yingjiaofydm" name="yingjiaofydm" width="180" allowsort="true" headeralign="center">
                            费用项目
                        </div>
                        <div field="yingjiaof_shijiyjje" name="yingjiaof_shijiyjje" width="60" allowsort="true"
                             headeralign="center" align="right">
                            金额
                        </div>
                        <div field="rengong_jdje" name="rengong_jdje" width="90" allowsort="true" headeralign="center"
                             align="right">
                            人工校对金额
                        </div>
                        <div field="pstateText" width="80" allowsort="true" headeralign="center" align="center">
                            是否需缴费
                        </div>
                        <div field="jfqd" width="80" allowsort="true" headeralign="center" align="center"
                             renderer="onJfqdRenderer">
                            缴费清单
                        </div>

                        <div field="shenqingrxm" width="200" allowsort="true" headeralign="center">
                            专利申请人
                        </div>
                        <div field="shenqingh" name="shenqingh" width="120" allowsort="true" headeralign="center"
                             renderer="onZhanlihao">
                            专利申请号
                        </div>
                        <div field="shenqingr" name="shenqingr" width="80" allowsort="true" headeralign="center">
                            申请日
                        </div>
                        <div field="zhuanlimc" name="zhuanlimc" width="200" allowsort="true" headeralign="center">
                            专利名称
                        </div>
                        <div field="zhuanlilxText" name="zhuanlilxText" width="90" headeralign="center">
                            专利类型
                        </div>
                        <div field="anjianywzt" width="210" allowsort="true" headeralign="center">
                            专利法律状态
                        </div>
                        <div field="famingrxm" width="130" allowsort="true" headeralign="center">
                            专利发明人
                        </div>
                        <div field="error_message" width="120" allowsort="true" headeralign="center" align="center">
                            错误信息
                        </div>
                        <div field="updatetime1" width="120" allowsort="true" headeralign="center" align="center"
                             datatype="date" dateFormat="yyyy-MM-dd HH:mm:ss">
                            更新时间
                        </div>
                        <div width="240" allowsort="true" headeralign="center" field="NEIBUBH">内部编号</div>
                        <div width="120" headeralign="center" field="KH">归属客户</div>
                        <div width="100" headeralign="center" field="YW">销售维护人</div>
                        <div width="100" headeralign="center" field="DL">代理责任人</div>
                        <div width="100" headeralign="center" field="LC">流程责任人</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form action="/Fee/FeeItem/ExportYear" method="post" style="display:none" id="ExportYearForm">
        <input type="hidden" name="KKRows" id="KKRows"/>
    </form>
    <form id="ExportForm" style="display:none" method="post" action="/Common/ExportExcelService/Export">
        <input type="hidden" id="Data" name="Data"/>
    </form>
    <div id="selectExportColumn" class="mini-window" title="选择导出字段" style="width:420px;height:250px;"
         showshadow="true" showfooter="true"
         footerstyle="text-align: right;margin-right: 25px;" allowresize="true" allowdrag="true">
        <div property="footer">
            <a class="mini-button mini-button-success"
               href="javascript:reqExoprt('View_t_3','费用监控','费用监控.xls', 0);">确定</a>
            <a class="mini-button mini-button-danger" href="javascript:cancelSelectExportColumn();">取消</a>
            <a class="mini-button mini-button-info" href="javascript:opsSelectExportColumn();">反选</a>
        </div>
    </div>
    <script type="text/javascript" src="/js/work/feeItem/FeeCommon.js"></script>
    <script type="text/javascript" src="/js/work/feeItem/FeeItem.js"></script>
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
    function doFixedQuery(field,oper,val){
        var arg = {};
        var cs=[{field:field,oper:oper,value:val}];
        arg["Query"]=mini.encode(cs);
        grid.load(arg);
    }
</script>
</@layout>