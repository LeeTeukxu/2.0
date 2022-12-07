<#include "/shared/layout.ftl">
<@layout>
    <style>
        .mini-layout-region-title {
            color: black;
        }
    </style>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="west" width="200" title="数据列表">
            <ul id="leftTree" style="width:100%;height:100%" onnodeclick="onDepChange" expandonload="true"
                class="mini-tree" idField="fid"
                parentfield="pid" textField="name" url="/systems/dict/getParent" resultAsTree="false">
            </ul>
        </div>
        <div region="center">
            <div class="mini-toolbar">
                <table style="width:100%;">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" id="tbDictData_Add" iconcls="icon-add" onclick="add"
                               plain="true">新增</a>
                            <a class="mini-button" id="tbDictData_Edit" iconcls="icon-save" onclick="save" plain="true">保存</a>
                            <a class="mini-button" id="tbDictData_Remove" iconcls="icon-remove" onclick="remove"
                               plain="true">删除</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox tbDictData_Query" id="GridFilter" emptytext="按回车对人员名称或手机号码模糊查询"
                                   onvaluechanged="searchFunction" onenter="searchFunction"
                                   style="width:400px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit">
                <div id="tbDictData_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" autoload="false"
                     allowresize="true" url="/systems/dict/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50]" sortfield="sn" sortorder="asc" allowCellSelect="true"
                     allowCellEdit="true">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div field="sn" name="sn" width="80" headeralign="center" allowsort="true">编号
                            <input property="editor" class="mini-textbox" style="width:100%"/>
                        </div>
                        <div field="name" name="name" width="100" headeralign="center" allowsort="true">名称
                            <input property="editor" class="mini-textbox" style="width:100%"/>
                        </div>
                        <div field="canUse" width="80" headeralign="center" allowsort="true"
                             type="checkboxcolumn">是否显示
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var tree = mini.get('leftTree');
        var grid = mini.get('tbDictData_datagrid');
        var curDepNode = null;

        function onDepChange(e) {
            var node = e.node;
            if (!node) return;
            curDepNode = node;
            var dtId = node.fid;
            grid.load({DtID: dtId});
        }

        function add() {
            if (!curDepNode) {
                mini.alert('请选择数据字段目录后再进行新增操作!');
                return;
            }
            var obj = {dtId: curDepNode.fid, pid: 0,canUse:true};
            grid.addRow(obj);
        }

        function save() {
            var rows = mini.clone(grid.getChanges() || []);
            if (rows.length > 0) {

                function g() {
                    var url = '/systems/dict/saveAll';
                    var data = mini.encode(rows);
                    $.post(url, {Data: data}, function (result) {
                        if (result.success) {
                            mini.alert('数据保存成功!');
                            grid.reload();
                        } else {
                            mini.alert(result.message || "保存失败，请稍候重试！");
                        }
                    })
                }
                mini.confirm('确认要保存数据吗？','系统提示',function(act){
                    if(act=='ok') g();

                });
            } else mini.alert("数据未发生改变!");
        }

        function remove() {
            var row = grid.getSelecteds();
            var userId = [];
            for (var i = 0; i < row.length; i++) {
                userId.push(row[i]["userId"])
            }
            if (userId.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('删除人员信息会连同该人员的帐号信息一同删除，是否继续执删除操作？', '删除提示', function (btn) {
                if (btn == 'ok') {
                    var url = "/systems/employee/removeAll";
                    $.ajax({
                        contentType: 'application/json',
                        method: 'post',
                        url: url,
                        data: mini.encode(userId),
                        success: function (r) {
                            if (r['success']) {
                                mini.alert("选择的用户删除成功！", '删除提示', function () {
                                    grid.reload();
                                });

                            } else mini.alert(r.message || "选择的用户删除失败！");
                        },
                        failure: function (error) {
                            mini.alert(error);
                        }
                    });
                }
            })
        }

        function searchFunction() {
            var depId = 0;
            var text = mini.get('GridFilter').getValue();
            if (curDepNode) {
                depId = curDepNode.depId;
            } else {
                var root = tree.getRootNode();
                if (root) {
                    var nodes = root.children || [];
                    if (nodes.length > 0) {
                        curDepNode = nodes[0];
                    }
                    depId = curDepNode.depId;
                }
            }
            grid.load({depId: depId, empName: text});

        }
    </script>
</@js>