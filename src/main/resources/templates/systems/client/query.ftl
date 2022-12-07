<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar" style="line-height:30px;" borderstyle="border:0;">
        <label>客户名称：</label>
        <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter"/>
        <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
        <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;"
             idfield="id" allowresize="true" url="/systems/client/getAll" sortorder="asc"
             sortfield="ClientName"
             borderstyle="border-left:0;border-right:0;" onrowdblclick="onRowDblClick">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="ClientName" width="150" headeralign="center">
                    客户名称
                </div>
                <div field="LinkMan" width="60" headeralign="center">
                    联系人
                </div>

                <div field="Mobile" width="70" headeralign="center">
                    电话
                </div>
                <div field="Email" width="100" headeralign="center">
                    邮箱
                </div>
                <div field="Address" width="200" headeralign="center">
                地址
            </div>
            </div>
        </div>

    </div>
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderstyle="border:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>

    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("grid1");
        grid.load();
        function GetData() {
            var row = grid.getSelected();
            return row;
        }
        function search() {
            var key = mini.get("key").getValue();
            grid.load({
                'Name':key,
                'sortField': grid.getSortField(),
                'sortOrder': grid.getSortOrder()
            });
        }

        function onKeyEnter(e) {
            search();
        }

        function onRowDblClick(e) {
            onOk();
        }

        //////////////////////////////////
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