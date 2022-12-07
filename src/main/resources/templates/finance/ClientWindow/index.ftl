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
<script type="text/javascript">
    var types = [
        { id: 2, text: '个人' },
        { id: 1, text: '企业' },
        { id: 3, text: '事业单位' }
    ];
</script>
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderstyle="border:0;">
        <label>客户名称：</label>
        <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter" />
        <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
            <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                 allowresize="true" url="/finance/ClientWindow/getDataWindow" multiselect="true"
                 pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="SN" sortorder="desc"
                 autoload="true" onrowdblclick="onRowDblClick">>
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="Name" name="Name" width="200" headeralign="center" allowsort="true">
                    客户名称
                </div>
                <div field="Type" name="Type"  width="60" headerAlign="center" type="comboboxcolumn" allowsort="true">企业性质
                    <div property="editor" class="mini-combobox" data="types"></div>
                </div>
                <div field="SignMan" name="SignMan" width="80" headeralign="center" type="comboboxcolumn" allowsort="true">
                    签约人
                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="SignMan" style="width:
                               98%;" required="true" resultAsTree="false"/>
                </div>
                <div field="Address" name="Address" width="140" headeralign="center" allowsort="true">
                    地址
                </div>
                <div field="Mobile" name="Mobile" width="140" headeralign="center" allowsort="true">
                    电话
                </div>
                <div field="SignDate" width="140" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">签约时间</div>
                <div field="Memo" name="Memo" width="140" headeralign="center">
                    备注
                </div>
            </div>
        </div>
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
