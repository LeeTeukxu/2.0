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
        <label>客户名称：</label>
        <input id="key" class="mini-textbox" style="width:300px;" onenter="onKeyEnter" />
        <a class="mini-button mini-button-normal" style="width:80px;" onclick="search()">查询</a>
        <span style="display:inline-block;width:50px;"></span>
        <a class="mini-button mini-button-primary" style="width:80px;" onclick="onOk()">确定选择</a>
        &nbsp;&nbsp;&nbsp;
        <a class="mini-button mini-button-danger" style="width:80px;" onclick="onCancel()">取消退出</a>
    </div>
    <div class="mini-fit">
        <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;" multiselect='${multiselect}'
             idfield="id" allowresize="true" sortorder="desc" sortfield="SN" url="/work/clientInfo/getPageData"
             borderstyle="border-left:0;border-right:0;" onrowdblclick="onRowDblClick">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="SN" width="80" headeralign="center">编号</div>
                <div field="Name" width="200" headeralign="center">客户名称</div>
                <div field="Type" width="80" headeralign="center" type="comboboxcolumn">
                    企业性质<div property="editor" class="mini-combobox" data="types"></div>
                </div>
                <div field="SignMan" width="100" headeralign="center" type="treeselectcolumn">
                    签约人
                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                           textField="Name" valueField="FID" parentField="PID"/>
                </div>
                <div field="SignDate" width="120" headeralign="center" align="center" dataType="date"
                     dateFormat="yyyy-MM-dd">
                    签约时间
                </div>
                <div field="Memo" width="120" headeralign="center">
                    备注
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("grid1");
        grid.load();
        function GetData() {
            <#if multiselect=="true">
                var row = grid.getSelecteds();
                return row;
            <#else>
                var row=grid.getSelected();
                return row;
            </#if>
        }
        function search() {
            var txt=mini.get('key').getValue();
            var cs=[];
            var arg={};
            if(txt){
                var fields=['Name',"SType","SSignMan"];
                for(var i=0;i<fields.length;i++){
                    var field=fields[i];
                    var obj={field:field,oper:'LIKE',value:txt};
                    cs.push(obj);
                }
            }
            if(cs.length>0){
                arg["Query"]=mini.encode(cs);
            }
            grid.load(arg);
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
            var rows=grid.getSelecteds();
            if(rows.length>0){
                CloseWindow("ok");
            } else mini.alert('请选择客户记录!');
        }
        function onCancel() {
            CloseWindow("cancel");
        }
    </script>
</@layout>