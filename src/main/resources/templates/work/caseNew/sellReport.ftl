<#include "/shared/dialog.ftl">
<@layout>
    <div class="mini-toolbar">
        <table style="width:100%;height:110px" class="layui-table">
            <tr>
                <td style="width:120px">客户名称</td>
                <td style="width:450px">
                    <input class="mini-textbox" style="width:99%" id="ClientName" data-oper="LIKE"
                           onvaluechanged="doQuery"/>
                </td>
                <td colspan="2" width="90px"></td>
                <td style="width:120px">专利类型及数量</td>
                <td style="width:450px">
                    <input class="mini-textbox" style="width:99%" id="Nums" data-oper="LIKE"
                           onvaluechanged="doQuery"/>
                </td>
            </tr>
            <tr>
                <td>商务人员</td>
                <td>
                    <input  class="mini-textbox"  style="width:99%" id="CreateManName" data-oper="LIKE"  onvaluechanged="doQuery"/>
                </td>
                <td colspan="2" width="90px"></td>
                <td>所属部门</td>
                <td>
                    <input class="mini-textbox"  id="DepName" style="width: 100%"  required="true"   data-oper="LIKE"
                           onvaluechanged="doQuery"/>
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
                       oncheckedchanged="onQueryMethodChanged" value="ClientName">按客户汇总</a>
                    <a id="btn_DJ" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" value="CreateManName">按商务人员汇总</a>
                    <a id="btn_LQ" checkOnClick="true" groupName="queryType" class="mini-button"
                       oncheckedchanged="onQueryMethodChanged" value="DepName">按部门汇总</a>

                </td>
            </tr>
        </table>
    </div>
    <div class="mini-fit" style="overflow: hidden">
        <div class="mini-datagrid" style="width:100%;height:98%" url="/sellReport/getData" id="grid1"
             autoload="false" sortField="CreateTime" sortOrder="Desc" showSummaryRow="true"
             ondrawsummarycell="drawSumCell">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="ClientName" width="200" headerAlign="center" align="center" allowSort="true">客户名称</div>
                <div field="CreateManName" width="80"  align="center"  headeralign="center" allowSort="true">商务人员</div>
                <div field="CreateTime" width="120" headerAlign="center" align="center" dataType="date"
                     dateFormat="yyyy-MM-dd" allowSort="true">交单时间</div>
                <div field="DocSN" width="130" headerAlign="center"  align="center">业务流水号</div>
                <div field="Nums" width="200"  headerAlign="center"  align="center">专利类型及数量</div>
                <div field="TotalDai" align="center" width="110" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >应收代理费</div>
                <div field="UsedDai" align="center" width="110" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >实收代理费</div>
                <div field="FreeDai" align="center" width="140" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >未收代理费余额</div>
                <div field="TotalGuan" align="center" width="110" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >应收官费</div>
                <div field="UsedGuan" align="center" width="110" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >实收官费</div>
                <div field="FreeGuan" align="center" width="120" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >未收官费余额</div>
            </div>
        </div>
        <div class="mini-datagrid" style="width:100%;height:100%" visible="false" url="/sellReport/getData"
             sortField="ItemName" sortOrder="desc"
             id="grid2" autoload="false"  showSummaryRow="true"   ondrawsummarycell="drawSumCell">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="ItemName" name="ItemName" width="120" headerAlign="center" align="center" >发生时间</div>
                <div field="TotalDai" align="center" width="120" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >应收代理费</div>
                <div field="UsedDai" align="center" width="120" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >实收代理费</div>
                <div field="FreeDai" align="center" width="140" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >未收代理费余额</div>
                <div field="TotalGuan" align="center" width="120" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >应收官费</div>
                <div field="UsedGuan" align="center" width="120" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >实收官费</div>
                <div field="FreeGuan" align="center" width="120" headeralign="center" allowsort="true"
                     allowSort="true" renderer="moneyRender"  >未收官费余额</div>
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
                        grid2.updateColumn(dColumn,{header:'交单时间'});
                    } else if(groupField=="ClientName"){
                        grid2.updateColumn(dColumn,{header:'客户名称'});
                    }
                    else if(groupField=="CreateManName"){
                        grid2.updateColumn(dColumn,{header:'商务人员姓名'});
                    }
                    else if(groupField=="DepName"){
                        grid2.updateColumn(dColumn,{header:'部门名称'});
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
            var names = ['ClientName', 'Nums', 'Begin', 'End','CreateManName','DepName'];
            for (var i = 0; i < names.length; i++) {
                var name = names[i];
                var con = mini.get(name);
                if (con) {
                    var val = con.getValue();
                    if (val) {
                        var obj = {field: name, oper: con.attributes["data-oper"], value: val};
                        if (name == "Begin") {
                            obj.oper = "GE";
                            obj.field = "CreateTime";
                            obj.value=mini.formatDate(val,'yyyy-MM-dd');
                        }
                        if (name == "End") {
                            obj.oper = "LE";
                            obj.field = "CreateTime";
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
            var sum=record.sum ||{};
            var num=parseFloat(sum[field] || 0);
            if(num>0){
                e.cellHtml=num.toFixed(2);
            }
        }
    </script>
</@layout>