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
    var ctypes = [{id:1,text:'专利类'}, {id:2,text:'商标版权类'}, {id:3,text:'高企类'}];
    var seals = [{id:1,text:'不需盖章'}, {id:2,text:'需要盖章'}, {id:3,text:'已盖章'}];
    var jiaodans = [{id:'未交单',text:'未交单'}, {id:'已交单',text:'已交单'}];
</script>
    <div class="mini-toolbar" style="text-align:center;line-height:30px;" borderstyle="border:0;">
        <label>合同名称：</label>
        <input id="key" class="mini-textbox" style="width:150px;" onenter="onKeyEnter" />
        <a class="mini-button" style="width:60px;" onclick="search()">查询</a>
    </div>
    <div class="mini-fit">
            <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                 allowresize="true" url="/InvoiceApplication/ContractWindow/getDataWindow?customer=${customer}" multiselect="true"
                 pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="DrawTime" sortorder="desc"
                 autoload="true"  onrowdblclick="onRowDblClick">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="ContractTypeText" width="100" headeralign="center" allowsort="true">合同类型</div>
                <div field="ContractName" width="140" headeralign="center" allowsort="true">合同名称</div>
                <div field="ContractNo" width="140" headeralign="center" allowsort="true">合同编号</div>
                <div field="NeadSeal" width="100" headeralign="center" allowsort="true" type="comboboxcolumn">
                    <input property="editor" class="mini-combobox" data="seals" />是否盖章
                </div>
                <div field="DrawEmp" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                    领取人
                    <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                           textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="DrawEmp" style="width:
                               98%;" required="true" resultAsTree="false"/>
                </div>
                <div field="DrawTime" width="80" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">领取日期</div>
                <div field="IsInvoice" width="140" headeralign="center" allowsort="true">是否开票</div>
                <div field="AlreadyJiao" width="100" headeralign="center" allowsort="true">是否交单</div>
                <div field="JiaoCnt" width="100" headeralign="center" allowsort="true">交单次数</div>
                <div field="Remark" width="140" headeralign="center" allowsort="true">备注</div>
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
