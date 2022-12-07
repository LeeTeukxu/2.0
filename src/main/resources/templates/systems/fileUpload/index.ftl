<#include "/shared/layout.ftl">
<@layout>
    <style>
        .mini-layout-region-title {
            color: black;
        }
    </style>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploader.js"></script>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="west" width="240" title="文件分类">
            <ul id="leftTree" style="width:100%;height:100%" onnodeclick="onTypeChanged" expandonload="true"
                onload="afterload" class="mini-tree" idField="id" ondrawnode="drawNode"
                parentfield="pid" textField="text" url="/systems/fileType/getAllCanUse?ShowNum=1" resultAsTree="false">
            </ul>
        </div>
        <div region="center">
            <div class="mini-toolbar" id="tool1" enable="false">
                <table style="width:100%;">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" id="tbFileUpload_Add" iconcls="icon-upload" onclick="add"
                               plain="true">新增</a>
                            <a class="mini-button" id="tbFileUpload_Edit" iconcls="icon-edit" onclick="edit"
                               plain="true">编辑</a>
                            <span class="separator"></span>
                            <a class="mini-button" id="tbFileUpload_Remove" iconcls="icon-remove" onclick="remove"
                               plain="true">删除</a>
                            <a class="mini-button" id="tbFileUpload_Download" iconcls="icon-download" onclick="download"
                               plain="true">下载</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconCls="icon-print" onclick="showConfigWindow"  plain="true"
                               id="tbFileUpload_Config">文件分类设置</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox tbFileUpload_Query" id="GridFilter" emptytext="文件名称或上传人姓名"
                                   onvaluechanged="searchFunction" onenter="searchFunction" style="width:400px;"/>
                            <button class="mini-button" iconCls="icon-search">查询</button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit">
                <div id="tbFileUpload_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                     allowCellSelect="true"
                     url="/systems/fileUpload/getData" onload="afterload" multiselect="true"
                     pagesize="20" sizelist="[5,10,20]" sortfield="uploadTime" sortorder="desc" autoload="false">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div field="TypeID" width="80" type="treeselectcolumn" headeralign="center" align="center">文件类型
                            <input property="editor" class="mini-treeselect" popupWidth="300"
                                   popupHeight="300" idField="id" expandOnload="true"
                                   onbeforenodeselect="beforeSelectNode"
                                   parentfield="pid" textField="text" url="/systems/fileType/getAllCanUse"
                                   resultAsTree="false"/>
                        </div>
                        <div field="Name" width="150" headeralign="center" allowsort="true">文件名称

                        </div>
                        <div field="Required" width="150" headeralign="center" allowsort="true">文件要求

                        </div>
                        <div field="AttNames" width="200" headeralign="center" allowsort="true">文件列表

                        </div>
                        <div field="Memo1" width="150" headeralign="center" allowsort="true">备注1

                        </div>
                        <div field="Memo2" width="150" headeralign="center" allowsort="true">备注2

                        </div>
                        <div field="UploadTime" width="100" headeralign="center" align="center"
                             allowsort="true" dateformat="yyyy-MM-dd HH:mm:ss" dataType="date">
                            创建日期
                        </div>
                        <div field="UploadManName" width="80" headeralign="center" align="center" allowsort="true">创建人
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" style="width:900px;height:600px" title="标准文件资料" id="AddFileWindow"
         onbuttonclick="onWinClose">
        <table style="width:100%;height:100%" id="FileForm">
            <tr>
                <td>文件分类</td>
                <td>
                    <input property="editor" class="mini-treeselect" popupWidth="300"
                           popupHeight="300" idField="id" expandOnload="true"
                           onbeforenodeselect="beforeSelectNode"
                           parentfield="pid" textField="text" url="/systems/fileType/getAllCanUse"
                           resultAsTree="false" style="width:100%" required="true" name="TypeID"/>
                </td>
                <td>文件名称</td>
                <td>
                    <input class="mini-hidden" name="ID"/>
                    <input class="mini-textbox" property="editor" style="width:100%" required="true" name="Name"/>
                </td>
            </tr>
            <tr>
                <td>文件要求</td>
                <td colspan="3">
                    <input class="mini-textarea" property="editor" style="width:100%;height:70px" name="Required"/>
                </td>
            </tr>
            <tr>
                <td>备注1</td>
                <td colspan="3"> <input class="mini-textarea" property="editor" style="width:100%;height:70px"
                                        name="Memo1"/></td>
            </tr>
            <tr>
                <td>备注2</td>
                <td colspan="3"> <input class="mini-textarea" property="editor" style="width:100%;height:70px"
                                        name="Memo2"/></td>
            </tr>
            <tr>
                <td colspan="4" style="height:180px">
                    <div id="attGrid" style="width:100%;height:180px;" ></div>
                </td>
            </tr>
            <tr>
                <td>创建人</td>
                <td> <input class="mini-textbox" property="editor" style="width:100%" enabled="false"
                            name="UploadManName"/></td>
                <td>创建日期</td>
                <td> <input class="mini-datepicker" property="editor" style="width:100%" enabled="false"
                            name="UploadTime" dateFormat="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td colspan="4">
                    <hr/>
                </td>
            </tr>
            <tr>
                <td colspan="2"  style="text-align: center">
                    <button class="mini-button mini-button-primary" style="width:100px" onclick="save">保存</button>
                </td>
                <td colspan="2"  style="text-align: center">
                    <button class="mini-button mini-button-danger" style="width:100px" onclick="onClose">退出</button>
                </td>
            </tr>
        </table>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var tree = mini.get('leftTree');
        var grid = mini.get('tbFileUpload_datagrid');
        var tool1 = mini.get('#tool1');
        var curTypeNode = null;
        var cmdAdd = mini.get('#tbFileUpload_Add');

        function beforeSelectNode(e) {
            e.cancel = !e.isLeaf;
        }
        function reloadNode(){
            tree.load('/systems/fileType/getAllCanUse?ShowNum=1');
        }
        function afterload(e) {
            updateNumber();
            if (curTypeNode) {
                grid.enable();
                tool1.enable();
            }
        }

        function drawNode(e) {
            var node = e.node;
            e.nodeHtml = node.text + "(" + node.name + ")";
        }

        function updateNumber() {
            var tree = mini.get('leftTree');
            var nodes = tree.getAllChildNodes(null);
            var sortNodes = nodes.sort(function (a, b) {
                if (a == null) return -1;
                if (b == null) return 1;
                if (a == null && b == null) return 0;
                var aLevel = tree.getLevel(a) || 0;
                var bLevel = tree.getLevel(b) || 0;
                if (aLevel > bLevel) return 1;
                if (aLevel == bLevel) return 0;
                if (aLevel < bLevel) return -1;
            }).reverse();
            for (var i = 0; i < sortNodes.length; i++) {
                var node = sortNodes[i];
                if (tree.isLeaf(node)) continue;
                if (node.name == "" || node.name == "0") {
                    var childNodes = tree.getChildNodes(node);
                    var total = 0;
                    for (var n = 0; n < childNodes.length; n++) {
                        var childNode = childNodes[n];
                        var num = parseInt(childNode.name || 0);
                        total += num;
                    }
                    node.name=total;
                    tree.updateNode(node,{name:total});
                }
            }
        }

        function onTypeChanged(e) {
            var node = e.node;
            if (!node) return;
            curTypeNode = node;
            var childNodes = tree.getAllChildNodes(node) || [];
            childNodes.push(node);
            var cc = [];
            for (var i = 0; i < childNodes.length; i++) {
                var node = childNodes[i];
                var id = node.id;
                if (cc.indexOf(id) == -1) {
                    cc.push(id);
                }
            }
            grid.load({Type: cc.join(',')});
        }
        var addWin=mini.get('AddFileWindow'),fileForm=new mini.Form('#FileForm'),att=null;
        var cnf={
            mode:'Add',
            uploadComplete:function(grid){
                var d=fileForm.getData();
                if(d.ID)saveFileInfo();
            },
            removeFile:function(){
                var d=fileForm.getData();
                if(d.ID)saveFileInfo();
            }
        };
        function add(){
            if(att==null){
                att = $('#attGrid').fileUploader(cnf);
            } else {
                att.clear();
                fileForm.reset();
            }
            if(curTypeNode){
                fileForm.setData({TypeID:curTypeNode.id});
            }
            addWin.show();
        }
        function edit(){
            var row=grid.getSelected();
            if(row){
                if(att==null){
                    att = $('#attGrid').fileUploader(cnf);
                }
                fileForm.reset();
                fileForm.setData(row);
                addWin.show();
                var attIds=(row["AttID"] || "");
                att.loadFiles({IDS:attIds});
            }
        }
        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                ids.push(row["ID"])
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('确认要删除选择的文件？', '删除提示', function (btn) {
                if (btn == 'ok') {
                    var url = "/systems/fileUpload/remove";
                    $.post(url, {IDS: ids.join(',')}, function (result) {
                        if (result.success) {
                            mini.alert('选择的文件记录删除成功!');
                            grid.reload();
                            reloadNode();
                        } else mini.alert(result.messasge || "删除文件记录失败，请稍候重试!");
                    })
                }
            })
        }

        function searchFunction() {
            var text = mini.get('GridFilter').getValue();
            var arg = {};
            var fields =['Name','Required'];
            var result = [];
            if(text){
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {
                        field: field,
                        value: text,
                        oper: 'LIKE'
                    };
                    result.push(obj);
                }
            }
            if(result.length>0)arg["Query"] = mini.encode(result);
            grid.load(arg);
        }
        function download() {
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["AttID"];
                    name = row["Name"]+'.zip';
                }
            } else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["AttID"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个文件打包下载.zip';
            }
            mini.open({
                url:'/fileDownload/index?AttIDS='+code+'&ZipFileName='+name,
                width:800,
                height:400,
                title:'下载文件',
                showModal:true
            });
            return false;
        }
        function save(){
            fileForm.validate();
            if(fileForm.isValid()==true){
                function g(){
                    var data=fileForm.getData();
                    var files=att.getPostFile();
                    var cs=[];
                    for(var i=0;i<files.length;i++){
                        var file=files[i];
                        var attId=file["ATTID"];
                        if(attId)cs.push(attId);
                    }
                    if(cs.length>0)data.AttID=cs.join(',');
                    var url='/systems/fileUpload/save';
                    var idx=mini.loading('正在保存信息........');
                    $.post(url,{Data:mini.encode(data)},function(result){
                        mini.hideMessageBox(idx);
                        if(result.success){
                            mini.alert('保存成功!','系统提示');
                            onWinClose({name:'close'});
                        } else mini.alert(result.message || "保存失败，请稍候重试!");
                    })
                }
                mini.confirm('确认要保存信息吗?','保存提示',function(act){
                    if(act=='ok') g();
                });
            } else mini.alert('数据录入不完整，无法进行保存！');
        }
        function onWinClose(e){
            if(e.name=="close"){
                grid.reload();
                fileForm.reset();
                addWin.hide();
                reloadNode();
            }
        }
        function onClose(){
            addWin.hide();
            onWinClose({name:'close'});
        }
        function saveFileInfo(){
            function g(){
                var data=fileForm.getData();
                var files=att.getPostFile();
                var cs=[];
                for(var i=0;i<files.length;i++){
                    var file=files[i];
                    var attId=file["ATTID"];
                    if(attId)cs.push(attId);
                }
                if(cs.length>0)data.AttID=cs.join(',');
                var url='/systems/fileUpload/save';
                $.post(url,{Data:mini.encode(data)},function(result){
                    console.log("保存文件结果:"+result);
                })
            }
            g();
        }
        function showConfigWindow(){
            mini.open({
                url:'/systems/fileType/index',
                width:'60%',
                height:'70%',
                title:'文件分类维护',
                showModal:true,
                onDestroy:function(){
                    if (typeId > 0) grid.reload();
                    reloadNode();
                }
            });
        }
    </script>
</@js>