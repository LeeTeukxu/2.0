<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-layout" style="width:100%;height:100%">
            <div region="west" title="菜单列表" width="300">
                <ul id="leftTree" url="/permission/menu/getAllCanUse" class="mini-tree" parentField="pid" idField="fid"
                    textField="title" resultAsTree="false" onnodeclick="nodeclick" expandonload="true"></ul>
            </div>
            <div region="center">
                <div class="mini-toolbar">
                    <a class="mini-button tbMenuPermissionItem_Save" iconCls="icon-ok" plain="true"
                       onclick="copyConfig()">从其它菜单复制</a>
                    <input id="sourceTree" url="/permission/menu/getAllCanUse"
                           class="mini-treeselect tbMenuPermissionItem_Save" parentField="pid"
                           valueField="fid" textField="title" resultAsTree="false" style="width:300px"
                           expandonload="true"/>

                    <span class="separator"></span>
                    <a id="tbMenuPermissionItem_Save" class="mini-button" iconcls="icon-save" onclick="save"
                       plain="true">保存</a>
                    <a id="tbMenuPermissionItem_AllSave" class="mini-button" iconcls="icon-edit" onclick="allsave"
                       plain="true">全选</a>

                </div>
                <div class="mini-fit">
                    <div id="tbMenuPermissionItem_cbl" style="width:99%;height:100%;margin-top:10px;margin-left:10px;
                    overflow:auto" class="mini-checkboxlist" repeatItems="5"   repeatLayout="table"></div>
                </div>
            </div>
        </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var sourceTree = mini.get('#sourceTree');
        var leftTree = mini.get('leftTree');
        var allFunctionItems =${FunctionItems};
        var changeHash = {};
        var checkBoxList = mini.get('tbMenuPermissionItem_cbl');
        var currentNode = null;
        var curMenuId = null;
        $(function () {
            $('.mini-checkboxlist-table').attr('cellspacing', '10px');
        });
        function allsave()
        {
            checkBoxList.selectAll();
        }
        function nodeclick(e) {
            var node = e.node;
            if (node['children']) {
                if (checkBoxList) checkBoxList.hide();
                return;
            } else {
                if (!checkBoxList)
                    checkBoxList = mini.get('tbMenuPermissionItem_cbl');
                checkBoxList.show();
            }

            if (currentNode) {
                var menuID = currentNode.fid;
                var val = checkBoxList.getValue();
                changeHash[menuID] = val;

            }
            var node = e.node;
            if (node) {
                var menuId = node.fid;
                curMenuId = menuID;
                var r = changeHash[menuId];
                if (r) {
                    checkBoxList.setValue(r);
                    $('.mini-checkboxlist-table').attr('cellspacing', '10px');
                }
                else Server(menuId);
                currentNode = node;
            }
        }

        function Server(id) {
            var url = '/permission/menupermission/getItemByMenuId';
            $.getJSON(url, {menuId: id}, function (r, c) {
                var data = r || [];
                if (!checkBoxList)
                    checkBoxList = mini.get('tbMenuPermissionItem_cbl');
                if (checkBoxList) {
                    checkBoxList.loadData(allFunctionItems);
                    if (data.length > 0) {
                        checkBoxList.setValue(data.join(','));
                    }
                    $('.mini-checkboxlist-table').attr('cellspacing', '10px');
                }
            });
        }

        function copyConfig() {
            var sourceMenuId = sourceTree.getValue();
            if (!sourceMenuId) {
                mini.alert('请选择要复制的菜单项目源。');
                return;
            }
            var node = leftTree.getSelectedNode();
            if (!node) {
                mini.alert('请选择要复制的目标菜单项目。');
                return;
            }
            mini.confirm('确认要复制选择的菜单权限设置?', '系统提示产', function (act) {
                if (act == 'ok') {
                    var url = '/permission/menupermission/copyConfig';
                    var arg = {Target: node.fid, Source: sourceMenuId};
                    $.post(url, arg, function (res) {
                        if (res.success) {
                            mini.alert('设置复制成功!', '系统提示', function () {
                                Server(curMenuId);
                            });
                        } else {
                            mini.alert(res.message || "复制设置失败，请稍候重试!");
                        }
                    });
                }
            })
        }

        function save() {
            mini.confirm("确定要保存修改吗？", "系统提示", function (action) {
                if (action == "ok") {
                    var ids = checkBoxList.getValue();
                    changeHash[currentNode.fid] = ids.toString().split(',');
                    var hh = {};
                    for (var n in changeHash) {
                        var dd = changeHash[n];
                        if (dd == "") dd = null;
                        if (dd != null && dd != undefined) {
                            if (typeof(dd) == "string") {
                                hh[n] = dd.toString().split(',');
                            } else hh[n] = dd;
                        } else {
                            hh[n] = [];
                        }
                    }
                    var url = '/permission/menupermission/saveAll';
                    $.post(url, {Data: mini.encode(hh)}, function (r) {
                        if (r['success']) {
                            mini.alert('保存成功', '提示', function () {
                                changeHash = {};
                            });
                        }
                        else {
                            mini.alert(r['Message'], '提示');
                        }
                    })
                }
            });
        }
    </script>
</@js>
