<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar">
        <a class="mini-button" iconCls="icon-save" onclick="save">确认转移</a>
        <span class="separator"></span>
        <a class="mini-button" iconCls="icon-user" onclick="setAll">全部设置为选中人员</a>
        <a class="mini-button" iconCls="icon-remove" onclick="clearAll">清空</a>
    </div>
    <div class="mini-fit">
        <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;" autoload="false" allowCellEdit="true" allowCellSelect="true" oncellbeginedit="onbeforeEdit">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="Name" width="120" headerAlign="center">客户名称</div>
                <div field="OLDXS" width="120" headerAlign="center" allowSort="true" type="treeselectcolumn"  align="center" >
                    原销售人员
                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID" textField="Name" resultAsTree="false" style="width:100%" multiSelect="false" expandonload="true" allowInput="false" url="/systems/dep/getAllLoginUsersByDep" />
                </div>
                <div field="NOWXS" width="120" headerAlign="center" align="center" allowSort="true"
                     type="treeselectcolumn" vtype="required">
                    转移至<input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                           textField="Name" resultAsTree="false" style="width:100%" multiSelect="false"
                           expandonload="true" allowInput="true" valueFromSelect="true" popupWidth="500"
                           url="/systems/dep/getAllLoginUsersByDep" />
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('datagrid1');
        var allUsers = null;
        function setData(rows, allowUsers,someUsers) {
            allUsers = someUsers;
            rows = rows || [];
            var dx = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var xs = row["SignMan"];
                var data = { ClientID: row["ClientID"], Name: row["Name"], OLDXS: xs};
                dx.push(data);
            }
            grid.setData(dx);
        }
        function onbeforeEdit(e) {
            var field = e.field;
            if (field == "OLDXS") {
                e.cancel = true;
            }
        }
        function save() {
            grid.accept();

            grid.validate();
            if (grid.isValid() == false) {
                mini.alert('所有转移的专利记录都要选待转移的人员。');
                return;
            }
            function g() {
                var rows = mini.clone(grid.getData());
                var url = '/work/clientInfo/SaveChangeKH';
                $.post(url, { Data: mini.encode(rows)}, function (r) {
                    if (r.success) {
                        mini.alert('销售人员转移成功。', '系统提示', function () {
                            if (window["CloseOwnerWindow"]) window.CloseOwnerWindow();
                        });
                    }
                    else {
                        var msg = r.message || "客户转移失败，请稍候重试。";
                        mini.alert(msg);
                    }
                });
            }
            mini.confirm('确要将选择的专利转移到指定的人员下？', '转移通知', function (r) {
                if (r == "ok") g();
            });
        }
        function setAll() {
            var row = grid.getSelected();
            if (row) {
                var oldSource = row["NOWXS"];
                if (oldSource) {
                    var rows = grid.getData();
                    for (var i = 0; i < rows.length; i++) {
                        var r = rows[i];
                        var oldId = r["NOWXS"];
                        if (!oldId)
                        {
                            grid.updateRow(r, { NOWXS: oldSource });
                        }
                    }
                }
            }
        }
        function clearAll() {
            var rows = grid.getData();
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                grid.updateRow(row, { NOWXS: null });
            }
        }
    </script>
</@layout>