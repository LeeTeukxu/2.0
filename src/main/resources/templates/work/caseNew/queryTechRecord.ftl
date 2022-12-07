<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript">
        var types = [
            { id: 2, text: '个人' },
            { id: 1, text: '企业' },
            { id: 3, text: '事业单位' }
        ];
    </script>

    <div class="mini-toolbar" style="line-height:30px;" borderstyle="border:0;">
        <label>案件名称：</label>
        <input id="key" class="mini-textbox" style="width:300px;" onenter="onKeyEnter" />
        <a class="mini-button mini-button-normal" style="width:80px;" onclick="search()">查询</a>
        <span style="display:inline-block;width:50px;"></span>
        <a class="mini-button mini-button-primary" style="width:80px;" onclick="onOk()">确定选择</a>
        &nbsp;&nbsp;&nbsp;
        <a class="mini-button mini-button-danger" style="width:80px;" onclick="onCancel()">取消退出</a>
    </div>
    <div class="mini-fit">
        <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;" sortfield="CreateTime"
             sortorder="desc" pagesize="20" onload="afterload" url="/casesTech/getData?State=64" autoload="true"
             borderstyle="border-left:0;border-right:0;" onrowdblclick="onRowDblClick" multiselect="false">
            <div property="columns">
                <div type="checkcolumn"></div>
                <div field="Memo" headerAlign="center" align="center" width="200">立案说明</div>
                <div field="SubNo" headerAlign="center" align="center" width="120">立案编号</div>
                <div field="YName" headerAlign="center" align="center" vtype="required">案件名称</div>
                <div field="ShenqingName" width="250" headerAlign="center" vtype="required" align="center">专利初步名称</div>
                <div field="ClientRequiredDate" width="120" headerAlign="center" align="center"
                     dataType="date" dateFormat="yyyy-MM-dd">客户要求申报绝限</div>
                <div field="TechMan"  headerAlign="center" align="center" type="treeselectcolumn">案件代理师
                    <input property="editor" class="mini-treeselect"  url="/systems/dep/getAllLoginUsersByDep"
                           textField="Name" valueField="FID" parentField="PID" /></div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("grid1");
        grid.load();
        function GetData() {
           var row=grid.getSelected();
           return row;
        }
        function search() {
           doQuery();
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
            var row=grid.getSelected();
            if(row!=null){
                CloseWindow("ok");
            } else mini.alert('请选择客户记录!');
        }
        function onCancel() {
            CloseWindow("cancel");
        }
        function beforeSelect(e){
            if(e.isLeaf==false) e.cancel=true;
        }
        function doQuery() {
            //备注/流水号/业备数量/客户/商务人员
            var txt=mini.get('key').getValue();
            var cs = [];
            var arg = {};
            if (txt) {
                var fields = ['Memo', "SubNo", "ShenqingName", "ClientName", "YName", "Nums"];
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {field: field, oper: 'LIKE', value: txt};
                    cs.push(obj);
                }
            }
            if (cs.length > 0) {
                arg["Query"] = mini.encode(cs);
            }
            grid.load(arg);
        }
    </script>
</@layout>