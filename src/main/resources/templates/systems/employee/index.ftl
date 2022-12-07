<#include "/shared/layout.ftl">
<@layout>
    <style>
        .mini-layout-region-title {
            color: black;
        }
    </style>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="west" width="400" title="部门列表">
            <ul id="leftTree" style="width:100%;height:100%" onnodeclick="onDepChange" expandonload="true"
                onload="afterload"
                class="mini-tree" idField="depId"
                parentfield="pid" textField="name" url="/systems/dep/getAllCanUse" resultAsTree="false">
            </ul>
        </div>
        <div region="center">
            <div class="mini-toolbar">
                <table style="width:100%;">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" id="tbEmployee_Add" iconcls="icon-add" onclick="add" plain="true">新增</a>
                            <a class="mini-button" id="tbEmployee_Edit" iconcls="icon-edit" onclick="edit" plain="true">修改</a>
                            <a class="mini-button" id="tbEmployee_Remove" iconcls="icon-remove" onclick="remove" plain="true">删除</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox tbEmployee_Query" id="GridFilter" emptytext="按回车对人员名称或手机号码模糊查询"
                                   onvaluechanged="searchFunction" onenter="searchFunction"
                                   style="width:400px;"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit">
                <div id="tbEmployee_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                     allowresize="true" url="/systems/employee/getPage" onload="afterload" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50]" sortfield="EmpCode" sortorder="asc">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div field="empCode" name="empCode" width="80" headeralign="center" allowsort="true">
                            人员编号
                        </div>
                        <div field="empName" name="empName" width="100" headeralign="center" allowsort="true">
                            姓名
                        </div>
                        <div field="loginCode" name="loginCode" width="80" headerAlign="center">系统帐号</div>
                        <div field="roleName" name="roleName" width="100" headeralign="center" allowsort="true">
                            角色
                        </div>
                        <div field="sexName" name="sexName" width="80" headeralign="center" align="center"
                             allowsort="true">性别
                        </div>
                        <div field="bornDate" name="bornDate" width="100" headeralign="center" align="center"
                             allowsort="true" dateformat="yyyy-MM-dd" dataType="date">
                            出生年月
                        </div>
                        <div field="nationName" name="nationName" width="80" headeralign="center" allowsort="true">民族
                        </div>
                        <div field="areaCodeName" name="areaCodeName" width="200" headeralign="center" allowsort="true">
                            籍贯
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
        var grid = mini.get('tbEmployee_datagrid');
        var curDepNode = null;

        function afterload(e) {
            updateNumber();
        }

        function updateNumber() {
            var url = '/systems/employee/getNumbers';
            var tree = mini.get('#leftTree');

            function getNodeByDepId(depId) {
                return tree.findNodes(function (node) {
                    return node["depId"].toString() == depId.toString();
                });
            }
            $.getJSON(url, {}, function (result) {
                result = result || {};
                for (var depId in result) {
                    var num = parseInt(result[depId].toString());
                    var nodes = getNodeByDepId(depId);

                    if (nodes.length > 0) {
                        var node = nodes[0];
                        var nodeName =node.oldName ||  node.name;
                        if(!node.oldName)node.oldName=node.name;
                        tree.updateNode(node, {name: nodeName + "<span style='color: blue'>(" + num + ")</span>"});
                    }
                }
            });

        }

        function onDepChange(e) {
            var node = e.node;
            if (!node) return;
            curDepNode = node;
            var depId = node.depId;
            grid.load({depId: depId});
        }

        function add() {
            var show = true;
            if (!curDepNode) {
                show = false;
                curDepNode = {};
            }

            var children = curDepNode["children"] || 0;
            if (children.length > 0) show = false;
            if (show == false) {
                mini.alert('请选择一个具体的部门后再进行新建人员操作。');
                return;
            }
            var depId = curDepNode.depId;
            mini.open({
                url: '/systems/employee/add?depId=' + depId,
                width: '70%',
                height: 580,
                title: '新增用户',
                showModal: true,
                onDestroy: function () {
                    grid.reload();
                }
            });
        }

        function edit() {
            var row = grid.getSelected();
            if (row) {
                var userId = row["userId"];
                mini.open({
                    url: '/systems/employee/edit?userId=' + userId,
                    width: '70%',
                    height: 580,
                    title: '编辑用户',
                    showModal: true,
                    onDestroy: function () {
                        grid.reload();
                    }
                });

            } else {
                mini.alert('请选择要编辑的记录。');
            }
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