<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar" id="tbFileClass_Toolbar">
        <a class="mini-button" iconcls="icon-reload" plain="true" onclick="Reload" id="tbFileClass_Refresh">刷新</a>
        <a class="mini-button" iconcls="icon-add" plain="true" id="tbFileClass_AddRoot" onclick="addRoot">新建总分类</a>
        <a class="mini-button" iconcls="icon-add" plain="true" id="tbFileClass_AddChild" onclick="add">新建子分类</a>
        <a class="mini-button" iconcls="icon-save" onclick="save" plain="true" id="tbFileClass_Save">保存</a>
        <a class="mini-button" iconcls="icon-remove" plain="true" onclick="remove" id="tbFileClass_Remove">删除</a>
    </div>
    <div class="mini-fit">
        <div id="tbFileClass_treegrid" class="mini-treegrid" style="width: 100%; height: 100%;" allowCellValid="true"
             allowresize="true" url="/systems/fileType/getAll" showtreeicon="true" treecolumn="sn" idfield="id"
             parentfield="pid" resultastree="false" expandOnload="true"
             allowcelledit="true" allowcellselect="true" autoload="true">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div name="sn" field="sn" width="20%" headeralign="center" vType="required">分类编号
                    <input property="editor" class="mini-spinner" minValue="1" maxValue="999999"/>
                </div>
                <div name="typeName" field="typeName" width="40%" headeralign="center" vType="required">分类名称
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div name="canUse" field="canUse" type="comboboxcolumn" width="120" headeralign="center"
                     align="center">是否显示
                    <input property="editor" class="mini-combobox" data="IsUsable"/>
                </div>
                <div field="memo" width="200" headerAlign="center">备注
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div field="createTime" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" visible="false"></div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        var treeGrid = null;
        var IsUsable = [{id: true, text: '可用'}, {id: false, text: '不可用'}];

        $(function () {
            mini.parse();
            treeGrid = mini.get('tbFileClass_treegrid');
        });

        function Reload() {
            treeGrid.load();
        }

        function addRoot() {
            var rows = treeGrid.getChanges();
            if (rows.length > 0) {
                mini.alert('请选保存当前数据后再进行插入操作。');
                return;
            }
            treeGrid.addNode({pid: 0, canUse: true});
        }

        function add() {
            var node = treeGrid.getSelectedNode();
            if (node !== undefined) {
                var pId = node.id;
                if (!pId) {
                    mini.alert('请保存数据后再进行插入操作。');
                    return;
                }
                var count=treeGrid.getChildNodes(node).length || 0;
                var pSn=parseInt(node.sn);
                treeGrid.addNode({pid: pId, canUse: true,sn:pSn*10+(count+1)}, 'add', node);
            } else {
                mini.alert("请选择根节点");
            }
        }

        function renderCheck(a, b, c) {
            var val = null;
            if (a.field == "canUse") {
                val = a.value;
                if (val == true || val == "true")
                    a.cellHtml = '<div style="color:green;text-align:center">可用</div>';
                else
                    a.cellHtml = '<div style="color:red;text-align:center">不可用</div>';
            }
        }

        function save() {
            function g(){
                var rows = mini.clone(treeGrid.getChanges());
                var url = '/systems/fileType/saveAll';
                $.post(url,{Data:mini.encode(rows)},function(r){
                    if (r['success']) {
                        mini.alert("数据保存成功!", "系统提示", function () {
                            Reload();
                        });
                    } else mini.alert("保存失败，请稍后再试...");
                });
            }
            treeGrid.validate();
            if(treeGrid.isValid()){
                g();
            } else {
                mini.alert('录入不完整，无法进行保存!');
            }
        }

        function remove() {
            var rows = treeGrid.getChanges();
            if (rows.length > 0) {
                mini.alert('当前已有数据发生了更改，请保存后再进行删除操作。');
                return;
            }
            var nodes = treeGrid.getSelecteds();
            if (nodes.length == 0) {
                mini.alert('请选择要删除的数据！');
                return;
            }
            var ids = [];
            for (var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i]["id"]);
                treeGrid.cascadeChild(nodes[i], function (node) {
                    var f = node['id'];
                    if (f && ids.indexOf(f) == -1) ids.push(f);
                });
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的角色记录!');
                return;
            }
            mini.confirm("确定删除选中的角色数据吗？", "系统提示", function (action) {
                if (action == "ok") {
                    var url = '/systems/fileType/removeAll';
                    $.ajax({
                        contentType: 'application/json',
                        method: 'post',
                        url: url,
                        data: mini.encode(ids),
                        success: function (r) {
                            if (r['success']) {
                                mini.alert("选择的角色删除成功！", '删除角色', function () {
                                    Reload();
                                });
                            } else mini.alert("选择的角色删除失败！");
                        },
                        failure: function (error) {
                            alert(error);
                        }
                    });
                }
            });
        }
    </script>
</@js>