<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <div class="mini-toolbar">
        <a class="mini-button" iconcls="icon-download" plain="true" onclick="download()">下载文件</a>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%" multiSelect="true"
             url="/fileDownload/getData?AttIDS=${AttIDS}" autoload="true">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="name" width="200" headerAlign="center">文件名称</div>
                <div field="size" width="100" headerAlign="center">文件大小</div>
                <div field="uploadMan" width="120"   headerAlign="center" align="center" type="treeselectcolumn">上传人

                    <input property="editor" class="mini-treeselect"
                           url="/systems/dep/getAllLoginUsersByDep"
                           textField="Name" valueField="FID" parentField="PID"/>
                </div>
                <div field="uploadTime" width="150" headerAlign="center"  align="center" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" >上传时间</div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var grid=mini.get('#grid1');
        var zipFileName="${ZipFileName}";
        function download(){
            debugger;
            var rows=grid.getSelecteds();
            if(rows.length>0){

                var rows = grid.getSelecteds();
                var code = null;
                var name = null;
                if (rows.length == 1) {
                    var row = rows[0];
                    if (row) {
                        code = row["guid"];
                        name = row["name"];
                    }
                } else {
                    var codes = [];
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var code = row["guid"];
                        codes.push(code);
                    }
                    code = codes.join(',');
                    name = zipFileName;
                }
                var url = '/systems/fileUpload/download?Code=' + code + '&FileName=' + encodeURI(name);
                $.fileDownload(url, {
                    httpMethod: 'POST',
                    failCallback: function (html, xurl) {
                        mini.alert('下载错误:' + html, '系统提示');
                    }
                });
            } else mini.alert('请选择要下载的文件!');
        }
    </script>
</@js>