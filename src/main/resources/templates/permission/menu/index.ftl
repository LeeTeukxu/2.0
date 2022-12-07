<#include "/shared/layout.ftl">
<@layout>
    <div id="tbMenuList_Toolbar" class="mini-toolbar">
        <a class="mini-button" id="tbMenuList_AddRoot" iconcls="icon-collapse" onclick="addRoot" plain="true">新增根节点</a>
        <a class="mini-button" id="tbMenuList_AddChild" iconcls="icon-expand" onclick="addChild" plain="true">新增子节点</a>
        <a class="mini-button" id="tbMenuList_Save" iconcls="icon-save" onclick="save" plain="true">保存</a>
        <a class="mini-button" id="tbMenuList_Remove" iconcls="icon-remove" plain="true" onclick="remove">删除</a> <span
                class="separator"> </span>
        <a class="mini-button" id="tbMenuList_Refresh" iconcls="icon-reload" plain="true" onclick="reload">刷新 </a>
    </div>
    <div class="mini-fit">
        <div id="treegrid1" class="mini-treegrid" style="width: 100%; height: 100%;" allowdrag="true"
             allowdrop="true" url="/permission/menu/getAll" showtreeicon="true " ondrawcell="renderCheck"
             treecolumn="sn" idfield="fid" parentfield="pid" resultastree="false" allowleafdropin="true"
             allowcelledit="true" allowcellselect="true" sortfield="sn" autoload="true" multiSelect="true"
             renderer="renderCheck" oncellbeginedit="beginEdit" allowCellValid="true">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div name="sn" field="sn" width="100" headeralign="center" vType="required">
                    菜单编号
                    <input property="editor" class="mini-spinner" minValue="1" maxValue="99999"/>
                </div>
                <div name="title" field="title" width="100" headeralign="center" vType="required">
                    菜单名称
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div name="url" field="url" width="200" headeralign="center">
                    菜单地址
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div name="pageName" field="pageName" width="100" headeralign="center">
                    页面名称
                    <input property="editor" class="mini-textbox"/>
                </div>
                <div name="canUse" field="canUse" width="60" headeralign="center" align="center">
                    是否可用
                    <input property="editor" class="mini-checkbox"/>
                </div>
                <div name="shortCut" field="shortCut" width="60" headeralign="center" align="center">
                    是否快捷菜单
                    <input property="editor" class="mini-checkbox"/>
                </div>
                <div name="icon" field="icon" width="150" headeralign="center" displayField="icon">
                    图标地址
                    <input property="editor" class="mini-buttonedit" onbuttonclick="showIcons()"/>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var treeGrid = mini.get('treegrid1');

        function renderCheck(a, b, c) {
            if (a.field == "canUse") {
                var val = a.value;
                if (val == true || val == "true") {
                    a.cellHtml = '<div style="color:green;text-align:center">可用</div>';
                } else {
                    a.cellHtml = '<div style="color:red;text-align:center">不可用</div>';
                }
            } else if (a.field == "shortCut") {
                var val = a.value;
                if (val == true || val == "true") {
                    a.cellHtml = '<div style="color:green;text-align:center">是</div>';
                } else {
                    a.cellHtml = '<div style="color:red;text-align:center">不是</div>';
                }
            }
        }

        function showIcons() {
            var row = treeGrid.getSelected();
            if (row) {
                var icon = row["icon"] || "";
                mini.open({
                    url: '/permission/menu/choice',
                    width: '80%',
                    height: '60%',
                    showModal: true,
                    title: '选择' + row["title"] + "快捷菜单图标",
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        if (icon) {
                            var ics = icon.split('/');
                            iframe.contentWindow.setData(ics[ics.length - 1]);
                        }
                    },
                    ondestroy: function (action) {
                        if (action == 'ok') {
                            treeGrid.cancelEdit();
                            var iframe = this.getIFrameEl();
                            var url = iframe.contentWindow.getData();
                            treeGrid.updateNode(row, {icon: url});
                        }
                    }
                });
            }
        }

        function addRoot() {
            treeGrid.addNode({pid: 0, canUse: true});
        }

        function beginEdit(e) {
            var field = e.field;
            var record = e.record;
            if (treeGrid.isLeaf(record) == false) {
                if (field == "sn" || field == "title") e.cancel = false; else e.cancel = true;
            }

        }

        function addChild() {
            var node = treeGrid.getSelectedNode();
            if (!node) {
                mini.alert("请选择父节点!");
                return;
            }
            var pId = node['fid'];
            if (!pId) {
                mini.alert('父级节点没有保存，无法新建子节点。');
                return;
            }
            treeGrid.addNode({pid: pId, canUse: true}, 'add', node);
        }

        function save() {
            function g(){
                var rows = treeGrid.getChanges();
                var url = '/permission/menu/saveAll';
                $.ajax({
                    contentType: 'application/json',
                    method: 'post',
                    url: url,
                    data: mini.encode(rows),
                    success: function (r) {
                        if (r.success) {
                            mini.alert('数据保存成功', '系统提示', function () {
                                refresh();
                            });
                        } else {
                            mini.alert('保存失败！' + r.message);
                        }
                    },
                    failure: function (error) {
                        alert(error);
                    }
                });
            }
            treeGrid.validate();
            if(treeGrid.isValid()){
                g();
            }else mini.alert('数据录入不完整，无法进行保存!');
        }

        function remove() {
            mini.confirm("你确认要删除选中项吗？", "系统提示", function (action) {
                if (action == "ok") {
                    var nodes = treeGrid.getSelecteds();
                    if (nodes.length <= 0) {
                        mini.alert('请选择要删除的数据！');
                        return;
                    }
                    var ids = [];
                    for (var i = 0; i < nodes.length; i++) {
                        var node = nodes[i];
                        var fid = node["fid"];
                        if (treeGrid.isLeaf(node) == false) {
                            var childs = treeGrid.getChildNodes(node);
                            for (var n = 0; n < childs.length; n++) {
                                var child = childs[n];
                                var ffid = child["fid"];
                                if (ffid) ids.push(ffid);
                            }
                        }
                        ids.push(fid);
                    }
                    $.ajax({
                        contentType: 'application/json',
                        method: 'post',
                        url: '/permission/menu/removeAll',
                        data: mini.encode(ids),
                        success: function (r) {
                            if (r['success']) {
                                mini.alert("菜单项删除成功！", '删除提示', function () {
                                    refresh();
                                });
                            } else mini.alert(r['message'] || "删除失败！");
                        },
                        failure: function (error) {
                            alert(error);
                        }
                    });
                }
            });
        }

        function refresh() {
            treeGrid.load({});
        }
    </script>
</@js>
