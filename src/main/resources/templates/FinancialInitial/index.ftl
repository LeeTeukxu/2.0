<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/excelExportOther.js"></script>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }

        .ui-tooltip {
            max-width: 850px;
        }
    </style>
    <script type="text/javascript">
        var types = [
            {id: 1, text: '工矿企业'},
            {id: 3, text: '事业单位'},
            {id: 4, text: '大专院校'},
            {id: 2, text: '个人'}
        ];
        var fit = mini.get('fitt');

        $(function () {
            $('#p1').hide();
        })

        var initialState = [
            {id: 1, text: '欠款'},
            {id: 2, text: '结余'}
        ];
    </script>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center" showheader="false">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-add" id="FinancialInitialBrowse_Add"
                               onclick="addFinancialInitial">增加</a>
                            <a class="mini-button" iconcls="icon-edit" id="FinancialInitialBrowse_Edit"
                               onclick="editFinancialInitial">编辑</a>
                            <a class="mini-button" iconcls="icon-remove" id="FinancialInitialBrowse_Remove"
                               onclick="remove">删除</a>
                            <a class="mini-button" iconcls="icon-ok" id="FinancialInitialBrowse_Browse" onclick="look">查看</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field FinancialInitialBrowse_Query" id="comField"
                                 style="width:150px" data="[{id:'All', text:'全部属性'},{id:'KHName',text:'客户名称'},{id:'Type',text:'企业类型'},{id:'SignManName', text:'客户登录人'}]" value="All"></div>
                            <input type="text" id="txtQuery" style="width:200px" class="mini-textbox FinancialInitialBrowse_Query" />
                            <button class="mini-button" iconCls="icon-find" onclick="doQuery"
                                    id="FinancialInitialBrowse_Query">模糊查询</button>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:200px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">回款日期：</td>
                            <td style="width:13%;">
                                <input name="DateOfPayment" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="DateOfPayment" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit">
                <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                     onrowdblclick="editClient" allowresize="true" url="/FinancialInitial/getData" multiselect="false"
                     pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="AddTime" sortorder="desc"
                     autoload="true" onDrawCell="onDraw" onrowclick="onRowClick">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="cootype" name="cootype" width="80" headeralign="center" type="comboboxcolumn"
                             align="center"
                             allowsort="true">
                            合作类型
                            <div property="editor" class="mini-combobox" data="[{id:1,text:'合作客户'},{id:2,
                        text:'意向客户'}]"></div>
                        </div>
                        <div field="KHName" width="240" headeralign="center" align="center">客户名称</div>
                        <div field="Type" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true"
                             align="center">企业性质
                            <div property="editor" class="mini-combobox" data="types"></div>
                        </div>
                        <div field="DLF" name="DLF" width="100" headeralign="center" allowsort="true" align="center">
                            代理费对账
                        </div>
                        <div field="GF" name="GF" width="100" headeralign="center" allowsort="true" align="center">
                            官费对账
                        </div>

                        <div field="SignManName" width="80" headeralign="center" allowsort="true" align="center">登记人
                        </div>
                        <div field="SignDate" width="120" headeralign="center" allowsort="true" dataType="date"
                             align="center"
                             dateFormat="yyyy-MM-dd">登记日期
                        </div>
                        <div field="InitialState" width="80" headerAlign="center" type="treeselectcolumn"
                             allowsort="true" align="center">初始状态
                            <div property="editor" class="mini-combobox" data="initialState"></div>
                        </div>
                        <div field="OfficalFeeAmount" name="OfficalFeeAmount" align="center">官费金额</div>
                        <div field="OfficalFeeAmountDetails" name="OfficalFeeAmountDetails" align="center">官费详情</div>
                        <div field="AgencyFeeAmount" name="AgencyFeeAmount">代理费金额</div>
                        <div field="AgencyFeeAmountDetails" name="AgencyFeeAmountDetails">代理费详情</div>
                        <div field="UserID" width="80" headeralign="center" type="treeselectcolumn" allowsort="true"
                             align="center">
                            录入人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="UserID" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="AddTime" width="80" headeralign="center" datatype="date" dateformat="yyyy-MM-dd"
                             allowsort="true" align="center">录入日期
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#datagrid1');
        var cmdAdd = mini.get('#FinancialInitialBrowse_Add');
        var cmdEdit = mini.get('#FinancialInitialBrowse_Edit');
        var cmdRemove = mini.get('#FinancialInitialBrowse_Remove');
        var cmdBrowse = mini.get('#FinancialInitialBrowse_Browse');

        function addFinancialInitial() {
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择客户后再进行操作!');
                return;
            }
            var clientId = row["ClientID"];
            mini.open({
                url: '/FinancialInitial/add?ClientID=' + clientId,
                width: '70%',
                height: 580,
                title: '新增财务初始值',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            });
        }

        function expand(e) {
            e = e || {};
            var btn = e.sender;
            if (!btn) {
                btn = mini.get('#ArrivalRegistrationBrowse_HighQuery');
            }
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
        function doQuery(){
            var txtQuery = mini.get('#txtQuery');
            var comField = mini.get('#comField');
            var arg = {};
            var cs = [];
            var ps=[];
            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL" || f=="JS") f = "NEIBUBH";
                        var kWork=f+'='+word;
                        if(ps.indexOf(kWork)==-1){
                            var op={field:f,oper:'LIKE',value:encodeURI(word)};
                            cs.push(op);
                            ps.push(kWork);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                    var op={field:field,oper:'LIKE',value:encodeURI(word)};
                    cs.push(op);
                }
            }
            if(cs.length>0)arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }

        function editFinancialInitial() {
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择要编辑的财务初始设置的信息!');
                return;
            }
            var ID = row['FinancialInitialID'];
            mini.open({
                url: '/FinancialInitial/edit?FinancialInitialID=' + ID + "&Type=Edit",
                width: '70%',
                height: 580,
                title: '修改财务初始值设置',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            })
        }
        function  onRowClick(e){
            var row=e.record;
            if(!row) return ;
            var ID = row['FinancialInitialID'];
            if(ID){
                cmdBrowse.show();
                cmdEdit.show();
                cmdRemove.show();
                cmdAdd.hide();
            } else {
                cmdBrowse.hide();
                cmdEdit.hide();
                cmdRemove.hide();
                cmdAdd.show();
            }
        }

        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            var ClaimStatus = "";
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["FinancialInitialID"]);
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('确认要删除的财务初始值设置的数据?', '删除提示', function (act) {
                if (act === 'ok') {
                    var url = "/FinancialInitial/remove";
                    $.ajax({
                        contentType: 'application/json',
                        method: 'post',
                        url: url,
                        data: mini.encode(ids),
                        success: function (r) {
                            if (r['success']) {
                                mini.alert("删除成功！", '删除提示', function () {
                                    grid.reload();
                                });

                            } else mini.alert("删除失败！");
                        },
                        failure: function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function refreshData(grid) {
            var pa = grid.getLoadParams();
            var pageIndex = grid.getPageIndex() || 0;
            var pageSize = grid.getPageSize() || 20;
            pa = pa || {pageIndex: pageIndex, pageSize: pageSize};
            grid.load(pa);
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            var val=e.value;
            if (field == "ClaimStatus") {
                var dd = record['ClaimStatus'];
                if (dd == '2') e.cellHtml = '<SPAN style="color:red">已认领</span>';
                if (dd == '3') e.cellHtml = '<SPAN style="color:#ff6a00">已拒绝,重认领</span>';
            }
            if (field == "ReviewerStatus") {
                var dd = record['ReviewerStatus'];
                if (dd == '2') {
                    e.cellHtml = '<SPAN style="color:red">同意</span>';
                } else {
                    e.cellHtml = '<SPAN></span>'
                }
            }
            if (field == "PaymentAmount") {
                var dd = record['PaymentAmount'];
                if (dd != "") {
                    e.cellHtml = dd + "(" + smalltoBIG(dd) + ")";
                }
            }
            else if(field=="KHName"){
                var clientId = record["ClientID"];
                var type=parseInt(record["CooType"]);
                e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'," +type+ ')">' + val + '</a>';
            }
        }
        function showClient(clientId,type) {
            mini.open({
                url:'/work/clientInfo/browse?Type='+type+'&ClientID='+clientId+"&Role=CW&mode=look",
                width:'100%',
                height:'100%',
                title:'浏览客户资料',
                showModal:true,
                ondestroy:function(){

                }
            });
            window.parent.doResize();
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
                        var obj = {
                            field: field.getName(),
                            value: field.getValue(),
                            oper: field.attributes["data-oper"]
                        };
                        result.push(obj);
                    }
                }
            }
            arg["High"] = mini.encode(result);
            grid.load(arg);
        }

        function look() {
            var row = grid.getSelected();
            if (row == "") {
                mini.alert("请选择要查看的财务初始值设置信息!");
                return;
            }
            var ID = row['FinancialInitialID'];
            mini.open({
                url: '/FinancialInitial/edit?FinancialInitialID=' + ID + "&Type=look",
                width: '70%',
                height: 580,
                title: '修改财务初始值设置',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            })
        }
    </script>
</@layout>
