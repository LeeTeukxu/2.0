<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar" id="tbRoleClass_Toolbar">
        <a class="mini-button" iconcls="icon-reload" plain="true" onclick="Reload" id="tbRoleClass_Refresh">刷新</a>
        <a class="mini-button" iconcls="icon-add" plain="true" id="tbRoleClass_AddRoot" onclick="addRoot">新建根节点</a>
        <a class="mini-button" iconcls="icon-add" plain="true" id="tbRoleClass_AddChild" onclick="add">新建子节点</a>
        <a class="mini-button" iconcls="icon-save" onclick="save" plain="true" id="tbRoleClass_Save">保存</a>
        <a class="mini-button" iconcls="icon-remove" plain="true" onclick="remove" id="tbRoleClass_Remove">删除</a>
    </div>
    <div class="mini-fit">
        <div id="tbRoleClass_treegrid" class="mini-treegrid" style="width: 100%; height: 100%;"
             allowresize="true" url="/permission/roleClass/getAll" showtreeicon="true" treecolumn="sn" idfield="roleId" parentfield="pid" resultastree="false"
             allowcelledit="true" allowcellselect="true"  autoload="true">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div name="sn" field="sn" width="20%" headeralign="center">排序编号
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div name="roleName" field="roleName" width="40%" headeralign="center">角色名称
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div name="shortName" field="shortName" width="30%" headeralign="center">角色简称
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div name="canUse" field="canUse" type="comboboxcolumn" width="120" headeralign="center">是否显示
                    <input property="editor" class="mini-combobox" data="IsUsable"/>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        var treeGrid = null;
        var IsUsable = [{ id: true, text: '可用' }, { id: false, text: '不可用'}];

        $(function () {
            mini.parse();
            treeGrid = mini.get('tbRoleClass_treegrid');
        });
        function Reload() {
            treeGrid.load();
        }
        function addRoot() {
            var rows = treeGrid.getChanges();
            if(rows.length>0){
                mini.alert('请选保存当前数据后再进行插入操作。');
                return ;
            }
            treeGrid.addNode({ pId: 0,canUse:true});
        }
        function add() {
            var node = treeGrid.getSelectedNode();
            if (node !== undefined) {
                var pId = node.roleId;
                if(!pId)
                {
                    mini.alert('请保存数据后再进行插入操作。');
                    return ;
                }
                treeGrid.addNode({pId:pId, canUse:true}, 'add', node);
            } else {
                mini.alert("请选择根节点");
            }
        }
        function renderCheck(a, b, c) {
            var val = null;
            if (a.field == "canUse") {
                val = a.value;
                if (val==true||val=="true")
                    a.cellHtml = '<div style="color:green;text-align:center">可用</div>';
                else
                    a.cellHtml = '<div style="color:red;text-align:center">不可用</div>';
            }
        }

        function save() {
            var rows =mini.clone(treeGrid.getChanges());
            var url = '/permission/roleClass/saveAll';
            $.ajax({
                contentType:'application/json',
                method:'post',
                url:url,
                data:mini.encode(rows),
                success:function (r) {
                    if (r['success']) {
                        mini.alert("数据保存成功!", "系统提示", function () {
                            Reload();
                        });
                    }
                    else mini.alert("保存失败，请稍后再试...");
                },
                failure:function (error) {
                    alert(error);
                }
            });
        }

        function remove() {
            var rows = treeGrid.getChanges();
            if(rows.length>0){
                mini.alert('当前已有数据发生了更改，请保存后再进行删除操作。');
                return ;
            }
            var nodes = treeGrid.getSelecteds();
            if (nodes.length == 0) {
                mini.alert('请选择要删除的数据！');
                return;
            }
            var ids = [];
            for (var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i]["roleId"]);
                treeGrid.cascadeChild(nodes[i], function (node) {
                    var f = node['roleId'];
                    if (f && ids.indexOf(f) == -1) ids.push(f);
                });
            }
            if(ids.length==0){
                mini.alert('请选择要删除的角色记录!');
                return ;
            }
            mini.confirm("确定删除选中的角色数据吗？", "系统提示", function (action) {
                if (action == "ok") {
                    var url='/permission/roleClass/removeAll';
                    $.ajax({
                        contentType:'application/json',
                        method:'post',
                        url:url,
                        data:mini.encode(ids),
                        success:function (r) {
                            if (r['success']) {
                                mini.alert("选择的角色删除成功！",'删除角色',function () {
                                    Reload();
                                });
                            }
                            else mini.alert("选择的角色删除失败！");
                        },
                        failure:function (error) {
                            alert(error);
                        }
                    });
                }
            });
        }
    </script>
</@js>