<#include "/shared/layout.ftl">
<@layout>
   <style>
       .showCellTooltip {
           color: blue;
           text-decoration: underline;
       }

       .ui-tooltip {
           max-width: 850px;
       }
   </style>
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderstyle="border:0;">
        <label>客户名称：</label>
        <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter" />
        <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
            <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                 allowresize="true" url="/InvoiceApplication/ClientLinkWindow/getDataWindow?customer=${customer}" multiselect="true"
                 pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="ClientName" sortorder="desc"
                 autoload="true"  onrowdblclick="onRowDblClick">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="ClientID" width="70" headeralign="center">
                        ID
                    </div>
                    <div field="ClientName" width="70" headeralign="center">
                        客户名称
                    </div>
                    <div field="LinkMan" width="60" headeralign="center">
                        联系人
                    </div>
                    <div field="Address" width="100" headeralign="center">
                        地址
                    </div>
                    <div field="PostCode" width="70" headeralign="center">
                        邮编
                    </div>
                    <div field="Mobile" width="70" headeralign="center">
                        电话
                    </div>
                    <div field="Email" width="70" headeralign="center">
                        邮箱
                    </div>
                </div>
        </div>
        <input class="mini-hidden" name="UserID" id="UserID" value="${UserID}" />
        <input class="mini-hidden" name="UserName" id="UserName" value="${UserName}" />
        <input class="mini-hidden" name="RoleID" id="RoleID" value="${RoleID}" />
        <input class="mini-hidden" name="RoleName" id="RoleName" value="${RoleName}" />
        <input class="mini-hidden" name="DepID" id="DepID" value="${DepID}" />
    </div>
    <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderstyle="border:0;">
        <a class="mini-button" style="width:60px;" onclick="onOk()">确定</a>
        <span style="display:inline-block;width:25px;"></span>
        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    <script>
        mini.parse();

        var grid = mini.get("datagrid1");

        grid.load();

        function GetData() {
            var row = grid.getSelecteds();
            return row;
        }
        function search() {
            var key = mini.get("key").getValue();
            if(!key)  return ;
            grid.load({key:encodeURI(key)});
        }
        function onKeyEnter(e) {
            search();
        }
        function onRowDblClick(e) {
            onOk();
        }
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
