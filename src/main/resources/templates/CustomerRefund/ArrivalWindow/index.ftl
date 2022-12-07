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
    <script type="text/javascript">
        var types = [
            {id: 1, text: '现金'},
            {id: 2, text: '转账'},
            {id: 3, text: '支付宝'},
            {id: 3, text: '微信'}
        ];
    </script>
    <div class="mini-toolbar" style="text-align:left;line-height:30px;" borderstyle="border:0;">
        <label>客户名称：</label>
        <input id="key" class="mini-textbox" style="width:300px;" onenter="onKeyEnter"/>
        <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
        <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
             allowresize="true" url="/CustomerRefund/ArrivalWindow/getDataWindow" multiselect="true"
             pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="DateOfPayment" sortorder="desc"
             autoload="true">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="DocumentNumber" width="150" headeralign="center">
                    单据编号
                </div>
                <div field="CustomerName" width="200" headerAlign="center">客户名称
                </div>
                <div field="DateOfPayment" width="140" headeralign="center" datatype="date" dateformat="yyyy-MM-dd"
                     allowsort="true">回款日期
                </div>
                <div field="PaymentMethod" width="60" headeralign="center" type="comboboxcolumn">
                    回款方式
                    <div property="editor" class="mini-combobox" data="types"></div>
                </div>
                <div field="PaymentAmount" width="140" headeralign="center" align="center">
                        回款金额
                    </div>
                    <div field="AgencyFee" width="140" headeralign="center" align="center">
                        回款代理费金额
                    </div>
                    <div field="OfficalFee" width="140" headeralign="center" align="center">
                        回款官费金额
                    </div>
                    <div field="Description" width="140" headeralign="center">
                        款项描述
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

        var grid = mini.get("datagrid1");

        grid.load();

        function GetData() {
            var row = grid.getSelecteds();
            return row;
        }

        function search() {
            var key = mini.get("key").getValue();
            if (!key) return;
            var cs = [];
            var fields = ['DocumentNumber', 'CustomerName'];
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var op = {field: field, oper: 'LIKE', value: key};
                cs.push(op);
            }
            var arg = {};
            arg["Query"] = mini.encode(cs);
            grid.load(arg);
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
