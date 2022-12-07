<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript">
        mini.parse();
        var types = [
            {id: 1, text: '现金'},
            {id: 2, text: '转账'},
            {id: 3, text: '支付宝'},
            {id: 4, text: '微信'}
        ];
        var moneyType = [{id: 1, text: '需关联业务费用'}, {id: 2, text: '无业务关联官费'}, {id: 3, text: '其他费用'}];
    </script>
    <div class="mini-toolbar">
        <table style="width:100%;height:110px" class="layui-table">
            <tr>
                <td style="width:120px">客户名称</td>
                <td style="width:450px">
                    <input class="mini-textbox" style="width:99%" id="Payer" data-oper="LIKE"  onvaluechanged="doQuery"/>
                </td>
                <td colspan="2" width="90px"></td>
                <td>登记人</td>
                <td>
                    <input class="mini-textbox" style="width:99%" id="AddUser" data-oper="LIKE"  onvaluechanged="doQuery"/>
                </td>
            </tr>
            <tr>
                <td style="width:120px">收支类型</td>
                <td style="width:450px">
                    <input class="mini-combobox" style="width:99%" id="Type" data-oper="EQ" data="[{id:'',text:'全部'},
                    {id:'收款',text:'收款'},
                    {id:'退款',text:'退款'}]"   onvaluechanged="doQuery"/>
                </td>
                <td colspan="2" width="90px"></td>
                <td>领用人</td>
                <td>
                    <input class="mini-textbox" style="width:99%" id="LYR" data-oper="LIKE"  onvaluechanged="doQuery"/>
                </td>
            </tr>
            <tr>
                <td>登记日期</td>
                <td>
                    <a checkOnClick="true" groupName="dayName" class="mini-button"
                       oncheckedchanged="onQueryTimeChanged" name="" value=""  checked="true" >全部</a>
                    <a checkOnClick="true" groupName="dayName" class="mini-button"
                       oncheckedchanged="onQueryTimeChanged" name="Week" value="1">上周</a>
                    <a checkOnClick="true" groupName="dayName" class="mini-button"
                       oncheckedchanged="onQueryTimeChanged" name="Week" value="0">本周</a>
                    <a checkOnClick="true" groupName="dayName" class="mini-button"
                       oncheckedchanged="onQueryTimeChanged" name="Month" value="1">上月</a>
                    <a checkOnClick="true" groupName="dayName" class="mini-button"
                       oncheckedchanged="onQueryTimeChanged" name="Month" value="0">本月</a>
                    <a checkOnClick="true" groupName="dayName" class="mini-button"
                       oncheckedchanged="onQueryTimeChanged" name="Year" value="0">今年</a>
                    <a checkOnClick="true" groupName="dayName" class="mini-button"
                       oncheckedchanged="onQueryTimeChanged" name="Year" value="1">去年</a>
                </td>
                <td colspan="2" width="120px"></td>
                <td style="width:120px">自定义</td>
                <td style="width:380px">
                    <input class="mini-datepicker" style="width:47%" id="Begin" onvaluechanged="dateChange"
                           data-oper="GE"/>~
                    <input class="mini-datepicker" style="width:47%" id="End" onvaluechanged="dateChange"
                           data-oper="LE"/>
                </td>
            </tr>
            <tr>
                <td colspan="6" style="text-align: center">
                    <a id="btn_Yes" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" checked="true" value="Day">明细查询</a>
                    <a id="btn_Yes" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" value="Week">按周汇总</a>
                    <a id="btn_Week" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" value="Month">按月汇总</a>

                    <a id="btn_KH" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" value="Payer">按客户汇总</a>
                    <a id="btn_DJ" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" value="AddUser">按登记人汇总</a>
                    <a id="btn_LQ" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" value="LYR">按领用人汇总</a>

                </td>
            </tr>
        </table>
    </div>
    <div class="mini-fit" style="overflow: hidden">
        <div class="mini-datagrid" style="width:100%;height:98%" url="/finance/report/getData" id="grid1"
             autoload="false" sortField="AddTime" sortOrder="Desc" showSummaryRow="true"
             ondrawsummarycell="drawSumCell">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="AddTime" width="120" headerAlign="center" align="center" dataType="date"
                     dateFormat="yyyy-MM-dd" allowSort="true">发生时间</div>
                <div field="Type" width="80" headerAlign="center" align="center">类型</div>
                <div field="Doc" width="150" headerAlign="center"  align="center">业务流水号</div>
                <div field="Amount" width="120" headerAlign="center" align="center" dataType="float"
                     summaryType="sum" allowSort="true">金额</div>
                <div field="Payer" width="200" headerAlign="center" align="center" allowSort="true">客户名称</div>
                <div field="AddUser" width="120" headerAlign="center" align="center" allowSort="true">登记人</div>
                <div field="LYR" width="120" headerAlign="center" align="center" allowSort="true">领用人</div>
            </div>
        </div>
        <div class="mini-datagrid" style="width:100%;height:100%" visible="false" url="/finance/report/getData"
             id="grid2" autoload="false"  showSummaryRow="true"   ondrawsummarycell="drawSumCell">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="ItemName" name="ItemName" width="120" headerAlign="center" align="center" >发生时间</div>
                <div field="Money" width="150" headerAlign="center" align="center"  dataType="float"
                     summaryType="sum">金额</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        $('body').css('overflow','hidden');
        var beginCon = mini.get('#Begin');
        var endCon = mini.get('#End');
        var grid1 = mini.get('#grid1');
        var grid2=mini.get('#grid2');
        var queryField = "";
        var queryValue = "";
        var groupField = "Day";
        $(function () {
            beginCon = mini.get('#Begin');
            endCon = mini.get('#End');
            grid1 = mini.get('#grid1');
            grid2=mini.get('#grid2');
        })

        function onQueryMethodChanged(e) {
            var btn = e.sender;
            mini.parse();
            grid1 = mini.get('#grid1');
            grid2=mini.get('#grid2');
            var checked = btn.getChecked();
            if (checked) {
                groupField = btn.getValue();

                if(groupField=="Day"){
                    grid1.show();
                    grid2.hide();
                } else {
                    grid1.hide();
                    grid2.show();
                    var dColumn=grid2.getColumn('ItemName');
                    if(groupField=="Week" || groupField=="Month"){
                        grid2.updateColumn(dColumn,{header:'发生时间'});
                    } else if(groupField=="Payer"){
                        grid2.updateColumn(dColumn,{header:'客户名称'});
                    }
                    else if(groupField=="AddUser"){
                        grid2.updateColumn(dColumn,{header:'登记人名称'});
                    }
                    else if(groupField=="LYR"){
                        grid2.updateColumn(dColumn,{header:'领用人名称'});
                    }
                }
                doQuery();
            }
        }

        function onQueryTimeChanged(e) {
            var btn = e.sender;
            var checked = btn.getChecked();
            if (checked) {
                mini.parse();
                var fieldName = btn.getName();
                var fieldValue = btn.getValue();
                queryField = fieldName;
                queryValue = fieldValue;

                beginCon = mini.get('#Begin');
                endCon = mini.get('#End');
                if (beginCon) {
                    beginCon.setValue(null);
                    endCon.setValue(null);
                }
                doQuery();
            }
        }

        function dateChange() {
            var keys = ['Day', 'Week', 'Month'];
            var begin = beginCon.getValue();
            var end = endCon.getValue();
            if (!begin && !end) {
                return;
            } else {
                if (!begin) begin = new Date();
                if (!end) end = new Date();
                queryField = "";
                queryValue = "";
                if(groupField=="") groupField="Day";
                for (var i = 0; i < keys.length; i++) {
                    var key = keys[i];
                    var cons = mini.getsByName(key);
                    for (var n = 0; n < cons.length; n++) {
                        var con = cons[n];
                        con.setChecked(false);
                    }
                }
                doQuery()
            }
        }

        function doQuery() {
            mini.parse();
            var grid = mini.get('#grid1');
            var result = {};
            var Cons = [];
            var names = ['Payer', 'AddUser', 'Begin', 'End','Type','LYR'];
            for (var i = 0; i < names.length; i++) {
                var name = names[i];
                var con = mini.get(name);
                if (con) {
                    var val = con.getValue();
                    if (val) {
                        var obj = {field: name, oper: con.attributes["data-oper"], value: val};
                        if (name == "Begin") {
                            obj.oper = "GE";
                            obj.field = "AddTime";
                            obj.value=mini.formatDate(val,'yyyy-MM-dd');
                        }
                        if (name == "End") {
                            obj.oper = "LE";
                            obj.field = "AddTime";
                            obj.value=mini.formatDate(val,'yyyy-MM-dd');
                        }
                        Cons.push(obj);
                    }
                }
            }
            if (Cons.length > 0) {
                result["cons"] = Cons;
            }
            result.timeField = queryField;
            result.timeValue = queryValue;
            result.groupField = groupField;
            if(groupField=="Day"){
                grid1.load({Query: mini.encode(result)});
            } else grid2.load({Query: mini.encode(result)});
        }
        function drawSumCell(e){
            var field=e.field;
            var record=e.result;
            if(field=="Money" || field=="Amount"){
                e.cellHtml="合计："+parseFloat(record.sum || 0).toFixed(2)+"元";
            }
        }
    </script>
</@layout>