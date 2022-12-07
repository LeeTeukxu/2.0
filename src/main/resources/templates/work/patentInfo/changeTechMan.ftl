<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar">
        <a class="mini-button" iconCls="icon-save" onclick="save">确认转移</a>
        <span class="separator"></span>
        <a class="mini-button" iconCls="icon-user" onclick="setAll">全部设置为选中人员</a>
        <a class="mini-button" iconCls="icon-remove" onclick="clearAll">清空</a>
    </div>
<div class="mini-fit">
    <div id="datagrid1" showPager="false" class="mini-datagrid" style="width:100%;height:100%;" autoload="false"
         allowCellEdit="true" allowCellSelect="true" oncellbeginedit="onbeforeEdit">
        <div property="columns">
            <div type="indexcolumn"></div>
            <div field="SHENQINGH" width="120" headerAlign="center" align="center">专利申请号</div>
            <div field="FAMINGMC" width="300" headerAlign="center"  align="center">专利名称</div>
            <div field="OLDXS" width="120" headerAlign="center" type="treeselectcolumn" align="center">原代理师
                <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID" textField="Name"
                       resultAsTree="false" style="width:100%" multiSelect="false" expandonload="true" allowInput="false"
                       url="/systems/dep/getAllLoginUsersByDep" />
            </div>
            <div field="NOWXS" width="120" headerAlign="center"  type="treeselectcolumn" vtype="required" align="center">转移至
                <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID" textField="Name"
                       resultAsTree="false" style="width:100%" multiSelect="false" popupWidth="400" popupHeight="300"
                       expandonload="true" allowInput="true" valueFromSelect="true"
                       allowInput="false" url="/systems/dep/getAllLoginUsersByDep"/>
            </div>
        </div>
    </div>
</div>
</@layout>
<@js>
    <script type="text/javascript" >
        mini.parse();
        var grid = mini.get('datagrid1');
        var allUsers = null;
        function setData(rows, allowUsers,someUsers,jklx) {
            allUsers = someUsers;
            rows = rows || [];
            var dx = [];
            var TZSLX="";
            var SHENQINGH="";
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                if (jklx=="otherTZS"){
                    TZSLX=row["ZHUANLIMC"];
                    SHENQINGH=row["SHENQINGH"];
                }else if (jklx=="allTZS"){
                    TZSLX=row["FAMINGMC"]
                    SHENQINGH=row["shenqingh"];
                } else {
                    TZSLX=row["FAMINGMC"];
                    SHENQINGH=row["SHENQINGH"];
                }
                var xs = row["JS"];
                var xsId = allowUsers[xs];
                var data = { SHENQINGH: SHENQINGH, FAMINGMC: TZSLX, OLDXS: xsId,NEIBUBH:row["NEIBUBH"] };
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
            var datas = grid.getData();
            for (var i = 0; i < datas.length; i++) {
                var data = datas[i];
                var nowId = data["NOWXS"];
                var nowName = allUsers[nowId];
                if (!nowName) {
                    mini.alert('选择的转移人员非法。');
                    return;
                }
                // var nb = data["NEIBUBH"]
                // if(nb) {
                //     if (nb.indexOf(nowName) > -1) {
                //         mini.alert(nowName + '在内部编号中已被指定，不能重复。');
                //         return;
                //     }
                // }
            }

            function g() {
                var rows = mini.clone(grid.getData());
                var url = '/work/changeTechMan/changeTechMan';
                $.post(url, {Data: mini.encode(rows), User: mini.encode(allUsers)}, function (r) {
                    if (r.success) {
                        mini.alert('技术人员转移成功。', '系统提示', function () {
                            if (window["CloseOwnerWindow"]) window.CloseOwnerWindow();
                        });
                    } else {
                        var msg = r.message || "技术人员转移失败，请稍候重试。";
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
            if(!row)row=grid.getData()[0];
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

</@js>