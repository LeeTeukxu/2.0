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
        <div id="grid1" class="mini-treegrid" style="width:100%;height:100%;" onbeforenodeselect="beforeSelect"
             idfield="FID" parentField="PID"  allowresize="true" sortorder="desc" sortfield="SN"
             url="/work/clientInfo/getAllLinksClient" treeColumn="Code" resultAsTree="false"
             borderstyle="border-left:0;border-right:0;" onrowdblclick="onRowDblClick" expandonload="true">
            <div property="columns">
                <div field="Code" width="120" headeralign="center" name="Code">
                    编号
                </div>
                <div field="Name" width="200" headeralign="center">
                    客户名称
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        mini.parse();
        var grid = mini.get("grid1");
        grid.load();
        function GetData() {
           var row=grid.getSelectedNode();
           return row;
        }
        function search() {
            var txt=mini.get('key').getValue();
            if(txt){
                grid.filter(function(node){
                    var dText= node.Code;
                    var exist=dText.indexOf(txt)>-1;
                    if(exist==false ){
                        var isLeaf=grid.isLeaf(node);
                        if(isLeaf){
                            var pNode=grid.getParentNode(node);
                            if(pNode) {
                                var pText=pNode.Name
                                exist=pNode.Name.indexOf(txt)>-1;
                            }
                        }
                    }
                    return exist;
                });
            }else grid.clearFilter();
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
            var row=grid.getSelectedNode();
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
    </script>
</@layout>