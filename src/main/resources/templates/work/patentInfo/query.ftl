<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript">
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
    </script>

    <div class="mini-toolbar" style="line-height:30px;" borderstyle="border:0;">
        <label>模糊搜索：</label>
        <input id="key" class="mini-textbox" style="width:300px;" onenter="onKeyEnter"/>
        <a class="mini-button mini-button-normal" style="width:80px;" onclick="search()">查询</a>
        <span style="display:inline-block;width:50px;"></span>
        <a class="mini-button mini-button-primary" style="width:80px;" onclick="onOk()">确定选择</a>
        &nbsp;&nbsp;&nbsp;
        <a class="mini-button mini-button-danger" style="width:80px;" onclick="onCancel()">取消退出</a>
    </div>
    <div class="mini-fit">
        <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;" idfield="id" allowresize="true"
             sortField="SHENQINGR" sortOrder="desc" url="/watch/feeWatch/getData?Mode=${Mode}"
             borderstyle="border-left:0;border-right:0;" onrowdblclick="onRowDblClick">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="SHENQINGH" width="80" headeralign="center">
                    专利编号
                </div>
                <div field="FAMINGMC" width="200" headeralign="center">
                    客户名称
                </div>
                <div field="SHENQINGLX" width="80" headeralign="center" type="comboboxcolumn">
                    专利性质
                    <div property="editor" class="mini-combobox" data="types"></div>
                </div>
                <div field="NEIBUBH" width="150" headeralign="center">内部编号</div>
                <div field="SHENQINGRXM" width="150" headeralign="center">专利申请人</div>
                <div field="SHENQINGR" width="120" headeralign="center" align="center" dataType="date"
                     dateFormat="yyyy-MM-dd">
                    申请日期
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("grid1");
        var mode="${Mode}";
        function loadData(url){
            if(url)grid.setUrl(url);
            grid.load();
        }
        function GetData() {
            var row = grid.getSelected();
            return row;
        }

        function search() {
            var key = mini.get("key").getValue();
            doQuery(key);
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
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                CloseWindow("ok");
            } else mini.alert('请选择专利记录!');
        }

        function onCancel() {
            CloseWindow("cancel");
        }

        function doQuery(word) {
            var arg = {};
            var bs = [];
            var cs = [];
            if (word) {
                var fields = ['SHENQINGH', 'FAMINGMC', 'SHENQINGRXM', 'NEIBUBH'];
                for (var i = 0; i < fields.length; i++) {
                    var f = fields[i];
                    var op = {field: f, oper: 'LIKE', value: word};
                    cs.push(op);
                }
            }
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            if(mode){
                arg["Mode"]=mode;
            }
            grid.load(arg);
        }
    </script>
</@layout>