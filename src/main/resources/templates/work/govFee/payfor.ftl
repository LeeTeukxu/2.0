<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript">
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
        var YearWatch = [
            { id: 0, text: '监控中' },
            { id: 3, text: '放弃监控' },
            { id: 4, text: '待缴费' },
            { id: 5, text: '完成缴费' }
        ];
    </script>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="center" style="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button mini-button-info" id="GovPay_Export"
                               onclick="doExport">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field GovPay_Query" id="comField" style="width:100px"
                                 data="[{id:'All',text:'全部属性'},{id:'FEENAME',text:'费用名称'},{id:'ShenQingh',text:'专利号'},{id:'FeeName',text:'缴费清单'},
                                 {id:'FAMINGMC',text:'发明名称'},{id:'SHENQINGRXM',text:'申请人姓名'},{id:'SHENQINGLX',text:'专利类型'}]" value="All"
                                 id="Field"></div>
                            <input class="mini-textbox Query_Field GovPay_Query" style="width:150px"
                                   id="QueryText"/>
                            <a class="mini-button mini-button-success" onclick="doQuery();" id="GovPay_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger" id="GovPay_Reset" onclick="reset">重置</a>
                            <a class="mini-button" id="GovPay_HighQuery" iconCls="icon-expand" onclick="expand">高级查询
                            </a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:110px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">缴纳期限：</td>
                            <td style="width:15%;"><input name="JIAOFEIR" class="mini-datepicker" data-oper="GE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;"><input name="JIAOFEIR" class="mini-datepicker" data-oper="LE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">清单名称</td>
                            <td style="width:15%;"><input name="FEENAME" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                                /></td>
                            <td style="width:6%;padding-left:10px;">金额：</td>
                            <td style="width:15%;"><input name="MONEY" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">专利编号：</td>
                            <td style="width:15%;"><input name="SHENQINGH" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                                /></td>
                            <td style="width:6%;padding-left:10px;">发明名称：</td>
                            <td style="width:15%;"><input name="FAMINGMC" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                                /></td>
                            <td style="width:6%;padding-left:10px;">专利类型：</td>
                            <td style="width:15%;"><input name="SHENQINGLX" class="mini-combobox" data="types"
                                                          data-oper="EQ" style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">专利申请人：</td>
                            <td style="width:15%;"><input name="SHENQINGRXM" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" onclick="doHightSearch" style="width:120px">搜索</a>
                                <a class="mini-button mini-button-danger" onclick="expand" style="margin-left:30px;width:120px">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="GovPay_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" onrowclick="onRowClick"
                     frozenstartcolumn="0" frozenendcolumn="6" sortorder="asc" sortfield="shenqingh"
                     ondrawcell="onDraw" allowresize="true" url="/work/govFee/getData?PayState=1" multiselect="true"
                     pagesize="20"
                     sizelist="[5,10,20,50,100]" autoload="true" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="JIAOFEIR" name="JIAOFEIR" width="100" allowsort="true" headeralign="center" align="center" dataType="date" dateFormat="yyyy-MM-dd">
                            缴费日期
                        </div>
                        <div field="FEENAME" name="FEENAME" width="140" allowsort="true" headeralign="center">
                            费用项目
                        </div>
                        <div field="MONEY" name="MONEY" width="60" allowsort="true" headeralign="center" align="right" dataType="float">
                            金额
                        </div>
                        <div field="SHENQINGH" name="shenqingh" width="120" allowsort="true" headeralign="center"
                             renderer="onZhanlihao">
                            专利申请号
                        </div>
                        <div field="FAMINGMC" name="FAMINGMC" width="200" allowsort="true" headeralign="center">
                            专利名称
                        </div>
                        <div field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="SHENQINGR" name="shenqingr" width="80" allowsort="true" headeralign="center"
                             datatype="date" dateformat="yyyy-MM-dd">
                            申请日
                        </div>

                        <div field="ANJIANYWZT" width="80" allowsort="true" headeralign="center">
                            专利法律状态
                        </div>
                        <div field="SHENQINGRXM" width="200" allowsort="true" headeralign="center">
                            专利申请人
                        </div>
                        <div field="FAMINGRXM" width="130" allowsort="true" headeralign="center">
                            专利发明人
                        </div>
                        <#if RoleName!='客户'  && RoleName!="外协代理师">
                        <div width="240" allowsort="true" headeralign="center" field="NEIBUBH">内部编号</div>
                        <div width="120" headeralign="center" field="KH">归属客户</div>
                        <div width="100" headeralign="center" field="YW">销售维护人</div>
                        <div width="100" headeralign="center" field="DL">代理责任人</div>
                        <div width="100" headeralign="center" field="LC">流程责任人</div>
                        <div field="JkStatus" visible="false"></div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        var grid = mini.get('GovPay_datagrid');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var cmdQuery=mini.get('#GovPay_Query');
        $(function () {
            $('#p1').hide();
            fit.setHeight('100%');
            fit.doLayout();
        });

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("icon-collapse");
                btn.setText("隐藏");
                $('#p1').css('display', "block");
                cmdQuery.hide();
                txtQuery.hide();
                comField.hide();
            } else {
                btn.setIconCls("icon-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
                cmdQuery.show();
                txtQuery.show();
                comField.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
        }

        function doHightSearch() {
            var arg = {};
            var form = new mini.Form('#highQueryForm');
            var fields = form.getFields();
            var result = [];
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var val = field.getValue();
                if (val != null && val != undefined) {
                    if (val != '') {
                        var obj = {field: field.getName(), value: field.getValue(), oper: field.attributes["data-oper"]};
                        result.push(obj);
                    }
                }
            }
            arg["High"] = mini.encode(result);
            grid.load(arg);
        }

        function doQuery(code, state) {
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
                        var kWork = f + '=' + word;
                        if (cs.indexOf(kWork) == -1) {
                            var op = {field: f, oper: 'LIKE', value: word};
                            cs.push(op);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                    var op = {field: field, oper: 'LIKE', value: word};
                    cs.push(op);
                }
            }
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }

        function doFixedQuery(field, oper, val) {
            var arg = {};
            var cs = [{field: field, oper: oper, value: val}];
            arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }

        function doExport() {
            var excel = new excelData(grid);
            excel.export("已缴费费用项目列表.xls");
        }


        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "KH") {
                var clientId = record["KHID"];
                var val = e.value;
                if (clientId) {
                    e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            }
            else if (field == "SHENQINGLX") {
                var val = parseInt(e.value);
                var textVal = "";
                for (var i = 0; i < types.length; i++) {
                    var tt = types[i];
                    if (tt.id == val) {
                        textVal = tt.text;
                        break;
                    }
                }
                switch (val) {
                    case 0: {
                        e.cellHtml = "<span style='color:red'>" + textVal + "</style>";
                        break;
                    }
                    case 1: {
                        e.cellHtml = "<span style='color:black'>" + textVal + "</style>";
                        break;
                    }
                    case 2: {
                        e.cellHtml = "<span style='color:blue'>" + textVal + "</style>";
                        break;
                    }
                }
            }
        }
        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            mini.get('#QueryText').setValue(null);
            doQuery();
        }
        function afterload(e) {
            tip.set({
                target: document,
                selector: '.showCellTooltip',
                onbeforeopen: function (e) {
                    e.cancel = false;
                },
                onopen: function (e) {
                    var el = e.element;
                    if (el) {
                        var hCode = $(el).attr('hCode');
                        if (hCode) {
                            if(!grid)grid=mini.get('FeeItem_datagrid');
                            var rows = grid.getData();
                            var row = grid.findRow(function (row) {
                                if (row["id"] == hCode) return true;
                            });
                            if (row) {
                                var memo = row["MEMO"];
                                if (memo) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + row["MEMO"] + '</td></tr></table>');
                                }
                            }
                        }
                    }
                }
            });
        }

        function ShowMemo(id, title) {
            mini.open({
                url: '/watch/feeWatch/addMemo?Type=Year&ID=' + id,
                showModal: true,
                width: 800,
                height: 400,
                title: "【" + title + "】的备注信息",
                onDestroy: function () {
                    grid.reload();
                }
            });
            window.parent.doResize();
        }
        function showClient(clientId) {
            mini.open({
                url:'/work/clientInfo/browse?Type=1&ClientID='+clientId,
                width:'100%',
                height:'100%',
                title:'浏览客户资料',
                showModal:true,
                ondestroy:function(){

                }
            });
            window.parent.doResize();
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

        function onRowClick() {
            onClickDisable();
        }
    </script>
</@layout>