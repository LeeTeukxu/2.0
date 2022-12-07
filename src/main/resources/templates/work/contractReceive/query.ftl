<#include "/shared/layout.ftl">
<@layout>
       <script type="text/javascript">
          // var ctypes = [{id: 1, text: '专利类'}, {id: 2, text: '商标版权类'}, {id: 3, text: '高企类'}];
          var ctypes=${items};
           var seals = [{id: 1, text: '不需盖章'}, {id: 2, text: '需要盖章'}, {id: 3, text: '已盖章'}];
           var jiaodans = [{id: '未交单', text: '未交单'}, {id: '已交单', text: '已交单'}];
       </script>

    <div class="mini-toolbar" style="line-height:30px;" borderstyle="border:0;">
        <label>模糊搜索：</label>
        <input id="key" class="mini-textbox" style="width:300px;" onenter="onKeyEnter" />
        <a class="mini-button mini-button-normal" style="width:80px;" onclick="search()">查询</a>
        <span style="display:inline-block;width:50px;"></span>
        <a class="mini-button mini-button-primary" style="width:80px;" onclick="onOk()">确定选择</a>
        &nbsp;&nbsp;&nbsp;
        <a class="mini-button mini-button-danger" style="width:80px;" onclick="onCancel()">取消退出</a>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%"  multiselect='${multiselect}'
             url="/work/contractReceive/getData?ClientID=${ClientID?replace(',','')}" autoload="true"
             sortfield="createTime"
             sortorder="desc"
             pagesize="10">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="ContractNo" width="140" headeralign="center" allowsort="true" align="center">合同编号</div>
                <div field="ContractName" width="200" headeralign="center" allowsort="true" align="center">合同名称</div>
                <div field="ContractType" width="100" headeralign="center" allowsort="true" align="center"
                     type="comboboxcolumn">合同类型
                    <input property="editor" class="mini-combobox" data="ctypes"/>
                </div>
                <div field="ClientName" width="140" headeralign="center" allowsort="true" align="center">客户名称</div>

                <div field="NeadSeal" width="100" headeralign="center" allowsort="true" align="center" type="comboboxcolumn">
                    <input property="editor" class="mini-combobox" data="seals"/>是否盖章
                </div>
                <div field="Remark" width="140" headeralign="center" allowsort="true" align="center">备注</div>
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
                var fields=['ContractNo',"ContractName","ClientName"];
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
            } else mini.alert('请选择合同记录!');
        }
        function onCancel() {
            CloseWindow("cancel");
        }
    </script>
</@layout>