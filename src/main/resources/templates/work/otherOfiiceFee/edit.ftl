<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript">
        var types = [
            { id: 0, text: '发明专利' },
            { id: 1, text: '实用新型' },
            { id: 2, text: '外观设计' }
        ];
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>其它官费设置及监控信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
            </span>
            </h1>
        </div>
    </div>

            <div class="mini-col-6">
                <div id="Info1" headerStyle="text-align:right" class="mini-panel mini-panel-info"
                     title="1.专利清单"
                     width="99%" showcollapsebutton="false" showclosebutton="false" >
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="ywGrid1" style="width:100%;height: 240px;" autoload="false" showPager="false"
                                     showPager="true" allowCellEdit="true" allowCellSelect="true" allowCellValid="true" onDrawCell="onDraw">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div field="SHENQINGH" headerAlign="center" align="right">专利号</div>
                                        <div field="SHENQINGLX" headerAlign="center" align="right">专利类型</div>
                                        <div field="FAMINGMC" headerAlign="center" align="right">专利名称</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="mini-col-6">
                <div id="Info2" headerStyle="text-align:right" class="mini-panel mini-panel-info"
                     title="2.费用清单"
                     width="99%"  showcollapsebutton="false" showclosebutton="false" style="margin-left: 5px">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="ywGrid2" style="width:100%;height: 212px;" autoload="false" showPager="false"
                                     showPager="true" allowCellEdit="true" allowCellSelect="true" allowCellValid="true">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div field="JIAOFEIR" headerAlign="center" dataType="date"
                                             dateFormat="yyyy-MM-dd" vtype="required">截止日期
                                            <input property="editor" class="mini-datepicker"/>
                                        </div>
                                        <div field="SHENQINGH" headerAlign="center" align="right">专利号</div>
                                        <div field="ExpenseItem" headerAlign="center" align="right">费用名称</div>
                                        <div field="Amount" headerAlign="center" vtype="required" align="right" vtype="required">金额
                                            <input property="editor" class="mini-spinner" maxValue="10000000"
                                                   minValue="1"/>
                                        </div>
                                        <div field="OfficialExplanation" headerAlign="center" align="right">官方解释</div>
                                        <div field="ExpenseItemID" visible="false"></div>
                                    </div>
                                </div>
                            </td>
                        </tr>

                    </table>
                    <div id="generate" style="width: 100%;text-align: center;">
                        <a class='mini-button' style='width:80px;font-weight:bold;' onclick="onHUIZONG">生成</a>
                    </div>
                </div>
            </div>

            <div class="mini-col-12" >
                <div id="Info3" headerStyle="text-align:right" class="mini-panel mini-panel-info"
                     title="3.清单汇总"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 70%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="ywGrid3" style="width:100%;height: 400px;" autoload="false" showPager="false"
                                     showPager="true" allowCellEdit="true" allowCellSelect="true" allowCellValid="true" onDrawCell="onDraw">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div field="JIAOFEIR" align="center" width="120" headeralign="center" dataType="date"
                                             dateFormat="yyyy-MM-dd" allowsort="true">截止日期
                                        </div>
                                        <div field="ExpenseItem" headerAlign="center" align="right">费用名称</div>
                                        <div field="Amount" headerAlign="center" align="right">金额</div>
                                        <div field="SHENQINGH" headerAlign="center" align="right">专利号</div>
                                        <div field="FAMINGMC" headerAlign="center" align="right">专利名称</div>
                                        <div field="SHENQINGLX" headerAlign="center" align="right">专利类型</div>
                                        <div field="STATE" visible="false"></div>
                                        <div field="SHOW" visible="false"></div>
                                        <div field="YEAR" visible="false"></div>
                                        <div field="AddPayFor" visible="false"></div>
                                        <div field="NeedPayFor" visible="false"></div>
                                        <div field="ExpenseItemID" visible="false"></div>
                                        <div field="Type" visible="false"></div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <div id="Aggregatehold" style="width: 100%;text-align: center;">
                        <a class='mini-button' style='width:80px;font-weight:bold;' onclick="save()">保存</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var mode="${Mode}";
        var formData=${LoadData};
        var SHENQINGH="${SHENQINGH}";
        var ExpenseItem="${ExpenseItem}";
        var Type="${Type}";
        var ID="${ID}";
        var PreShenqingh="";
        if (mode=="Edit"){
            var ywGrid1=mini.get('ywGrid1');
            ywGrid1.setUrl("/work/otherOfficeFee/getData?SHENQINGH="+SHENQINGH+"&ExpenseItem="+ExpenseItem+"&ID="+ID);
            ywGrid1.load({ 'sortField': ywGrid1.getSortField(), 'sortOrder': ywGrid1.getSortOrder() });

            var ywGrid2=mini.get('ywGrid2');
            ywGrid2.setUrl("/work/otherOfficeFee/getData?SHENQINGH="+SHENQINGH+"&ExpenseItem="+ExpenseItem+"&ID="+ID);
            ywGrid2.load({ 'sortField': ywGrid2.getSortField(), 'sortOrder': ywGrid2.getSortOrder() });

            var ywGrid3=mini.get('ywGrid3');
            ywGrid3.setUrl("/work/otherOfficeFee/getData?SHENQINGH="+SHENQINGH+"&ExpenseItem="+ExpenseItem+"&ID="+ID);
            ywGrid3.load({ 'sortField': ywGrid3.getSortField(), 'sortOrder': ywGrid3.getSortOrder() });
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "SHENQINGLX") {
                var dd = record['SHENQINGLX'];
                if (dd == '0') e.cellHtml = '<SPAN>发明专利</span>';
                if (dd == '1') e.cellHtml = '<SPAN>实用新型</span>';
                if (dd == '2') e.cellHtml = '<SPAN>外观设计</span>';
            }
        }

        $(function () {
            var panel1 = mini.get('#Info1');
            if (panel1) {
                panel1.setButtons([
                    {html: "<a class='mini-button' style='width:80px;margin-left:20px;top:-4px;' onclick='onSHENQINGHDialog'>添加</a>"},
                    {html: "<a class='mini-button' style='width:80px;margin-left:20px;top:-4px;' onclick='onDeleteP1'>删除</a>"},
                ]);
            }
            var panel2 = mini.get('#Info2');
            if (panel2) {
                panel2.setButtons([
                    {html: "<a class='mini-button' style='width:80px;margin-left:20px;top:-4px;' onclick='onFEIYONGQINGDDialog'>添加</a>"},
                    {html: "<a class='mini-button' style='width:80px;margin-left:20px;top:-4px;' onclick='onDeleteP2'>删除</a>"}
                ]);
            }
            var panel3 = mini.get('#Info3');
            if (panel3) {
                panel3.setButtons([
                    {html: "<a class='mini-button' style='width:80px;margin-left:20px;top:-4px;' onclick='onDeleteP3'>删除</a>"}
                ]);
            }
        });

        function onSHENQINGHDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/work/otherOfficeFee/zlzhIndex",
                title: "选择专利综合信息",
                width: 1625,
                height: 480,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            var ywGrid1=mini.get('ywGrid1');
                            ywGrid1.validate();
                            var _shenqingh = "";
                            var _shenqinglx = "";
                            var _shenqinglxValue = "";
                            var _famingmc="";
                            var strIsExists=[];
                            for (var i = 0; i < data.length; i++) {
                                if (ywGrid1.isValid()) {
                                    var row=ywGrid1.getData();
                                    if (row){
                                        for (var j=0;j<row.length;j++){
                                            strIsExists.push(row[j].SHENQINGH);
                                        }
                                    }
                                    //如果选择重复的专利清单，只添加一条
                                    if (strIsExists.indexOf(data[i].SHENQINGH)==-1) {
                                        _shenqingh = data[i].SHENQINGH;
                                        _shenqinglx = data[i].SHENQINGLX;
                                        if (_shenqinglx == "0") {
                                            _shenqinglx = "发明专利";
                                        } else if (_shenqinglx == "1") {
                                            _shenqinglx = "实用新型";
                                        } else {
                                            _shenqinglx = "外观设计";
                                        }
                                        _shenqinglxValue = data[i].SHENQINGLX;
                                        _famingmc = data[i].FAMINGMC;
                                        var row = {SHENQINGH: _shenqingh, SHENQINGLX: _shenqinglx, FAMINGMC: _famingmc};
                                        ywGrid1.addRow(row);
                                        ywGrid1.setSelected(row);
                                        ywGrid1.validateRow(row);
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
        function onFEIYONGQINGDDialog(e) {
            var ywGrids=mini.get('ywGrid1').getSelected();
            var shenqingh="";

            if (ywGrids) {
                var t = ywGrids["SHENQINGLX"];
                shenqingh=ywGrids["SHENQINGH"];
                var Type = 0;
                switch (t) {
                    case '发明专利':
                        Type = 0;
                        break;
                    case '实用新型':
                        Type = 1;
                        break;
                    case '外观设计':
                        Type = 2;
                        break;
                    default:
                        break;
                }
            }else{
                mini.alert("请先选择专利信息！");
                return;
            }
            var btnEdit = this;
            mini.open({
                url: "/work/otherOfficeFee/fymxIndex?Type="+Type,
                title: "选择费用清单",
                width: 1625,
                height: 480,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            var ywGrid2=mini.get('ywGrid2');
                            ywGrid2.validate();
                            var _expenseItem = "";
                            var _amount = "";
                            var _officialExplanation = "";
                            var strSHENQINGH=[];
                            var strExpenseItem=[];
                            for (var i = 0; i < data.length; i++) {
                                    var row=ywGrid2.getData();
                                    if (row.length>0) {
                                        for (var j = 0; j < row.length; j++) {
                                            strSHENQINGH.push(row[j].SHENQINGH);
                                            strExpenseItem.push(row[j].ExpenseItem);
                                        }
                                    }
                                    //费用清单没有数据时，新增一行
                                    if (row.length==0) {
                                        _expenseItem = data[i].ExpenseItem;
                                        _amount = data[i].Amount;
                                        _officialExplanation = data[i].OfficialExplanation;
                                        var row = {
                                            SHENQINGH: shenqingh,
                                            ExpenseItem: _expenseItem,
                                            Amount: _amount,
                                            OfficialExplanation: _officialExplanation,
                                            ExpenseItemID:data[i].id
                                        };
                                        ywGrid2.addRow(row);
                                        ywGrid2.validateRow(row);
                                        continue;
                                    }
                                    //申请号存在，费用名称不存在相同的数据。添加一行
                                    else if (strSHENQINGH.indexOf(shenqingh)!=-1&&strExpenseItem.indexOf(data[i].ExpenseItem)==-1){
                                        _expenseItem = data[i].ExpenseItem;
                                        _amount = data[i].Amount;
                                        _officialExplanation = data[i].OfficialExplanation;
                                        var row = {
                                            SHENQINGH: shenqingh,
                                            ExpenseItem: _expenseItem,
                                            Amount: _amount,
                                            OfficialExplanation: _officialExplanation,
                                            ExpenseItemID:data[i].id
                                        };
                                        ywGrid2.addRow(row);
                                        ywGrid2.validateRow(row);
                                        PreShenqingh=shenqingh;
                                        continue;
                                    }
                                    //如果选在的申请专利不存在，添加一条
                                    else if (strSHENQINGH.indexOf(shenqingh)==-1){
                                        _expenseItem = data[i].ExpenseItem;
                                        _amount = data[i].Amount;
                                        _officialExplanation = data[i].OfficialExplanation;
                                        var row = {
                                            SHENQINGH: shenqingh,
                                            ExpenseItem: _expenseItem,
                                            Amount: _amount,
                                            OfficialExplanation: _officialExplanation,
                                            ExpenseItemID:data[i].id
                                        };
                                        ywGrid2.addRow(row);
                                        ywGrid2.validateRow(row);
                                        continue;
                                    }
                                    //如果新选择的专利和上一个选择的专利不同并且申请号存在并且费用项目不存在，添加一条
                                    else if (PreShenqingh!=shenqingh&&strSHENQINGH.indexOf(shenqingh)!=-1&&strExpenseItem.indexOf(data[i].ExpenseItem)!=-1){
                                        _expenseItem = data[i].ExpenseItem;
                                        _amount = data[i].Amount;
                                        _officialExplanation = data[i].OfficialExplanation;
                                        var row = {
                                            SHENQINGH: shenqingh,
                                            ExpenseItem: _expenseItem,
                                            Amount: _amount,
                                            OfficialExplanation: _officialExplanation,
                                            ExpenseItemID:data[i].id
                                        };
                                        ywGrid2.addRow(row);
                                        ywGrid2.validateRow(row);
                                        PreShenqingh=shenqingh;
                                        continue;
                                    }
                            }
                        }
                    }
                }
            });
        }

        function onHUIZONG() {
            var ywGrid2=mini.get('ywGrid2');
            ywGrid2.validate();
            var grid1=mini.get('ywGrid1').getData();
            var grid2=mini.get('ywGrid2').getData();
            var ywGrid3=mini.get('ywGrid3');
            if (ywGrid2.isValid()) {
                ywGrid3.clearRows();
                if (grid1.length > 0) {
                    for (var i = 0; i < grid1.length; i++) {
                        var shenqingh1 = grid1[i].SHENQINGH;
                        for (var j = 0; j < grid2.length; j++) {
                            var shenqingh2 = grid2[j].SHENQINGH;
                            if (shenqingh1 == shenqingh2) {
                                var row = {
                                    JIAOFEIR: grid2[j].JIAOFEIR,
                                    ExpenseItem: grid2[j].ExpenseItem,
                                    Amount: grid2[j].Amount,
                                    SHENQINGH: grid1[i].SHENQINGH,
                                    FAMINGMC: grid1[i].FAMINGMC,
                                    SHENQINGLX: grid1[i].SHENQINGLX,
                                    STATE:1,
                                    SHOW:1,
                                    YEAR:1,
                                    AddPayFor:0,
                                    NeedPayFor:1,
                                    ExpenseItemID:grid2[j].ExpenseItemID,
                                    Type:getTypeID(grid1[i].SHENQINGLX)
                                };
                                ywGrid3.addRow(row);
                                ywGrid3.validateRow(row);
                            }
                        }
                    }
                }
            }else {
                mini.alert("数据录入不完整!");
            }
        }

        function getTypeID(Type) {
            var TypeID="0";
            switch (Type) {
                case "发明专利":
                    TypeID="0";
                    break;
                case "实用新型":
                    TypeID="1";
                    break;
                case "外观设计":
                    TypeID="2";
                    break;
                default:
                    break;
            }
            return TypeID;
        }

        function onDeleteP1() {
            var ywGrid1=mini.get('ywGrid1');
            var row1=ywGrid1.getSelected();
            if (row1){
                ywGrid1.removeRow(row1);
                var ywGrid2=mini.get('ywGrid2');
                var row2=ywGrid2.getData();
                if (row2) {
                    for (var i = 0; i < row2.length; i++) {
                        if (row1['SHENQINGH'] == row2[i].SHENQINGH) {
                            ywGrid2.removeRow(row2[i]);
                        }
                    }
                }
                var ywGrid3=mini.get('ywGrid3');
                var row3=ywGrid3.getData();
                if (row3){
                    for (var j=0;j<row3.length;j++){
                        if (row1['SHENQINGH']==row3[j].SHENQINGH){
                            ywGrid3.removeRow(row3[j]);
                        }
                    }
                }
            }
        }

        function onDeleteP2() {
            var ywGrid1=mini.get('ywGrid1');
            var row1=ywGrid1.getData();

            var ywGrid2=mini.get('ywGrid2');
            var row2=ywGrid2.getSelected();

            var ywGrid3=mini.get('ywGrid3');
            var row3=ywGrid3.getData();

            var strSHENQINGH=[];
            if (row2){
                ywGrid2.removeRow(row2);
                //获取费用清单表所有的专利号
                var row2s=ywGrid2.getData();
                for (var i=0;i<row2s.length;i++){
                    strSHENQINGH.push(row2s[i].SHENQINGH);
                }
                //如果费用清单表中专利号全部删除 则删除专利清单对应的专利号数据
                if (strSHENQINGH.indexOf(row2["SHENQINGH"])==-1){
                    for (var j=0;j<row1.length;j++){
                        if (row2["SHENQINGH"]==row1[j].SHENQINGH){
                            ywGrid1.removeRow(row1[j]);
                        }
                    }
                }
                for (var x=0;x<row3.length;x++){
                    if (row2["SHENQINGH"]==row3[x].SHENQINGH&&row2["ExpenseItem"]==row3[x].ExpenseItem){
                        ywGrid3.removeRow(row3[x]);
                    }
                }
            }else {
                mini.alert("请选择需要删除的专利费用信息")
            }
        }

        function onDeleteP3(){
            var ywGrid3=mini.get('ywGrid3');
            var rows=ywGrid3.getSelecteds();
            if(rows.length>0){
                function g(){
                    var cs=[];
                    for(var i=0;i<rows.length;i++) {
                        var row=rows[i];
                        var ID=row["id"];
                        cs.push(ID);
                    }
                    var url='/work/otherOfficeFee/removeGridLast?ID='+cs.join(',');
                    $.post(url,{},function(result){
                        if(result.success){
                            mini.alert('选择的记录删除成功!','删除提示',function(){
                                for(var i=0;i<rows.length;i++){
                                    var row=rows[i];
                                    ywGrid3.removeRow(row);
                                    ywGrid3.acceptRecord(row);
                                }
                            });
                        }else mini.alert(result.message || "删除失败，请稍候重试!");
                    });
                }
                mini.confirm('确认要删除选择的记录?','系统提示',function(action){
                    if(action=='ok'){
                        g();
                    }
                });
            } else  mini.alert('请选择要删除的记录!');
        }
        function save() {
            var ywGrid3=mini.get('ywGrid3');
            var row=ywGrid3.getData();
            var arg={Data:mini.encode(row)};
                $.post('/work/otherOfficeFee/save',arg,function (text) {
                    var res = mini.decode(text);
                    if (res.success){
                        mini.alert('其它官费设置信息保存成功','系统提示',function(){

                        });
                    }else {
                        mini.alert(res.Message);
                    }
                })
        }
    </script>
</@layout>