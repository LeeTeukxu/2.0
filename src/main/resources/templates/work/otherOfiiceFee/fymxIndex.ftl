<#include "/shared/layout.ftl">
<@layout>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }
        .ui-tooltip {
            max-width: 850px;
        }
    </style>
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderstyle="border:0;">
        <label>费用项目：</label>
        <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter" />
        <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
        <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
             allowresize="true" url="/work/otherOfficeFee/getFymxDataWindow?Type=${Type}" multiselect="true"
             pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="ExpenseItem" sortorder="desc"
             autoload="true" onrowdblclick="onRowDblClick">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="ExpenseItem" name="ExpenseItem" width="200" headeralign="center" allowsort="true">
                     费用项目
                </div>
                <div field="Amount" name="Amount" width="140" headeralign="center" allowsort="true">
                    金额
                </div>
                <div field="OfficialExplanation" name="OfficialExplanation" width="140" headeralign="center" allowsort="true">
                    官方解释
                </div>
            </div>
        </div>
    </div>
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderstyle="border:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    <script>
        mini.parse();
        var Type=${Type};
        var grid = mini.get("datagrid1");

        grid.load();

        function GetData() {
            var row = grid.getSelecteds();
            return row;
        }
        function search() {
            var key = mini.get("key").getValue();
            if(!key)  return ;
            grid.load({key:encodeURI(key)});
        }
        function onKeyEnter(e) {
            search();
        }
        function onRowDblClick(e) {
            onOk();
        }
        function CloseWindow(action) {
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        }

        function onOk() {
            CloseWindow("ok");
        }
        function onCancel() {
            CloseWindow("cancel");
        }
    </script>
</@layout>
