<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar">
        <a class="mini-button" iconCls="icon-add" id="CPCPeriodConfig_Add" onclick="AddConfig">增加</a>
        <a class="mini-button" iconCls="icon-remove" id="CPCPeriodConfig_Remove" onclick="RemoveConfig">删除</a>
        <a class="mini-button" iconCls="icon-save" id="CPCPeriodConfig_Save" onclick="SaveConfig">保存</a>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" allowCellEdit="true" allowCellSelect="true"
             allowCellValid="true" url="/work/nicheNoticeConfig/getData" autoload="true" pageSize="20"
             sortField="TypeName" sortOrder="asc">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="typeName" width="150" headerAlign="center" type="comboboxcolumn" allowsort="true" vtype="required">通知书类型
                    <input class="mini-combobox" property="editor" name="TypeName" valueField="TypeName" textField="TypeName"
                           url="/work/nicheNoticeConfig/getTZSMC" style="width: 98%;"
                           virtualScroll="true"/>
                </div>
                <div header="正常处理期限" headerAlign="center">
                    <div property="columns">
                        <div field="days" headerAlign="center" vtype="required">
                            天数
                            <input property="editor" class="mini-spinner" miniValue="0" maxValue="30"/>
                        </div>
                        <div field="months" headerAlign="center" vtype="required">
                            +月
                            <input property="editor" class="mini-spinner" miniValue="0" maxValue="12"/>
                        </div>

                    </div>
                </div>
                <div header="加急处理期限" headerAlign="center">
                    <div property="columns">
                        <div field="jdays" headerAlign="center" vtype="required">
                            天数
                            <input property="editor" class="mini-spinner" miniValue="0" maxValue="30"/>
                        </div>
                        <div field="jmonths" headerAlign="center" vtype="required">
                            +月
                            <input property="editor" class="mini-spinner" miniValue="0" maxValue="12"/>
                        </div>

                    </div>
                </div>
                <div field="memo" width="150" headerAlign="center">
                    备注
                    <input property="editor" class="mini-textbox"/>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get('datagrid1');
    function AddConfig() {
        var rows = grid.getData() || [];
        var row = { months: 2, days: 15 };
        grid.addRow(row, rows.length + 1);
        grid.validate();
    }
    function RemoveConfig() {
        var row = grid.getSelected();
        if (row) {
            var id = row["id"] || "";
            if (id) {
                mini.confirm('确认要删除选择的设置', '删除提示', function (btn) {
                    if (btn == 'ok') {
                        var url = '/work/nicheNoticeConfig/remove';
                        $.post(url, { ID: id }, function (r) {
                            if (r.success) {
                                grid.removeRow(row, false);
                                grid.acceptRecord(row);
                                mini.alert('选择的记录删除成功。');
                                onCancel();
                            } else {
                                var msg = r.message || "删除失败，请稍候重试。";
                                mini.alert(msg);
                            }
                        });
                    }
                });
            } else {
                grid.removeRow(row, false);
                grid.acceptRecord(row);
                mini.alert('选择的记录删除成功。');
            }
        } else mini.alert('请选择要删除的记录.');
    }
    function SaveConfig() {
        grid.validate();
        if (grid.isValid()) {
            mini.confirm('确认要保存设置数据吗？', '保存提示', function (action) {
                if (action == 'ok'){
                    var rs = grid.getChanges("added");
                    var rows = grid.getChanges("modified");
                    for (var i = 0; i < rs.length; i++) {
                        var row = rs[i];
                        rows.push(row);
                    }

                    var url = '/work/nicheNoticeConfig/save';
                    $.post(url, { Data: mini.encode(rows) }, function (r) {
                        if (r.success) {
                            mini.alert('保存成功', '系统提示', function () {
                                grid.reload();
                                onCancel();
                            });
                        } else {
                            var msg = r.message || "保存失败，请稍候重试。";
                            mini.alert(msg);
                        }
                    });
                }
            });
        } else {
            mini.alert('数据录入不完整，无法进行保存操作。');
        }
    }

    function onCancel() {
        CloseWindow("ok");
    }

    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
</script>
</@js>