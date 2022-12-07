<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <div class="mini-toolbar">
        <a class="mini-button" iconCls="icon-download" onclick="download">下载导入模版</a>
        <span class="separator"></span>
        <button class="mini-button" iconCls="icon-upload" id="uploadFile">上传数据文件</button>
        <button class="mini-button" iconCls="icon-download" onclick="doExport" id="A11">下载处理结果</button>
        <span class="separator"></span>
        <a class="mini-button" iconCls="icon-ok" onclick="importData" id="A12">确认导入</a>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%">
            <div property="columns">
                <div type="indexcolumn" width="60"></div>
                <div field="ImportResult" width="200" headerAlign="center">处理结果</div>
                <#list Cols as Col>
                    <#if Col.type=='date'>
                            <div field="${Col.field}" required="${Col.required}" dataType="date" dateformat="yyyy-MM-dd"
                                 width="${Col.width}" headerAlign="center" align="center">${Col.header}</div>
                    <#elseif Col.type=="combo">
                            <div field="${Col.field}" required="${Col.required}" type="comboboxcolumn"
                                 width="${Col.width}" headerAlign="center" align="center">${Col.header}
                                <input property="editor" class="mini-combobox" data="${Col.dataSource}"/>
                            </div>
                    <#else>
                            <div field="${Col.field}" width="${Col.width}" required="${Col.required}"
                                 headerAlign="center" align="center">${Col.header}</div>
                    </#if>
                </#list>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('grid1');
        var code = "${code}";
        var fileName = "${fileName}";
        var cols = mini.decode(decodeURIComponent("${ColsText}"));
        var uploader = null;
        var processUrl = '/';
        var cmdA11 = mini.get('A11');
        var cmdA12 = mini.get('A12');
        $(function () {
            cmdA11.hide();
            cmdA12.hide();
        });

        function download() {
            var url = '/common/importData/download?code=' + code + '&fileName=' + encodeURI(fileName);
            var boxId = mini.open({
                url: url
            });
            boxId.hide();
            event.preventDefault();
            event.stopPropagation();
            return false;
        }

        var upMsgId = null;

        function initUpload(url) {
            processUrl = url;
            uploader = new plupload.Uploader({
                browse_button: 'uploadFile',
                url: '/common/importData/upload',
                flash_swf_url: '/js/plupload/Moxie.swf',
                silverlight_xap_url: '/js/plupload/Moxie.xap',
                filters: [{title: 'Office文档', extensions: 'xls'}],
                multipart: true,
                max_file_size: '5MB',
                prevent_duplicates: true,
                multipart_params: {
                    Cols: mini.encode(cols),
                    code: "${code}"
                },
                init: {
                    Error: function (a, b) {
                        var errorCode = parseInt(b.code);
                        var file = b.file;
                        if (errorCode == -600) {
                            mini.alert(file.name + '太大，不能上传。');
                        }
                        else if (errorCode == -601) {
                            mini.alert('服务器不允许上传此文件。');
                        }
                        else if (errorCode == -602) {
                            mini.alert(file.name + '已在待传列表，请勿重复选择。');
                        }
                    },
                    FilesAdded: function (up, files) {
                        cmdA11.hide();
                        cmdA12.hide();
                        mini.mask({html: '正在上传并解析数据，请稍候........'});
                        uploader.start();

                    },
                    FileUploaded: function (up, file, res) {
                        mini.unmask();
                        var result = mini.decode(res.response);
                        if (result['success']) {
                            var data = result.data || [];
                            grid.setData(data);
                            importData();
                            cmdA12.show();
                        } else {
                            var msg = result.message || "读取数据失败，请勿对导入模版进行修改。";
                            mini.alert(msg);
                        }
                        uploader.splice();
                    }
                }
            });
            uploader.init();
        }

        function importData() {
            var al = "";
            var rows = grid.getData();
            if (rows.length == 0) {
                mini.alert('无导入数据!');
                return;
            }
            var ds = [];
            var notNum = 0;
            var ns = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var obb = row["Valid"];
                if (obb == null || obb == undefined) obb = "true";
                var ok = (obb).toString().toLowerCase();
                if (ok == "true") {
                    ds.push(row);
                } else {
                    notNum++;
                    var importresult = row["ImportResult"];
                    var exnum = i + 1;
                    al += "第" + exnum + "条:"  + importresult + "</br>";
                    ns.push(row);
                }

            }
            if (notNum == rows.length) {
                mini.alert(al);
                // mini.alert('上传的所有记录全部都校验不合格，不能进行导入操作。');
                cmdA12.hide();
                cmdA11.show();
                return;
            }
            var tt = '确认导入数据?';
            if (notNum > 0) {
                tt = al + "</br>" + "有" + notNum + "条数据经服务器校验不合格，请修改校验不合格或者删除校验不合格的数据，否则将不会提交保存！";
                mini.alert(tt);
                return;
            }
            mini.confirm('确认是否导入？', '导入提示', function (act) {
                if (act == 'ok') {
                    var iiid=mini.loading("正在导入数据，请稍候................");
                    var arg = {Data: mini.encode(ds), Cols: mini.encode(cols)};
                    $.post(processUrl, arg, function (result) {
                       mini.hideMessageBox(iiid);
                        if (result.success) {
                            mini.alert('专利数据导入成功!', '系统提示', function () {
                                var ppx=result.data || [];
                                for(var i=0;i<ns.length;i++){
                                    ppx.push(ns[i]);
                                }
                                grid.setData(ppx);
                                cmdA12.hide();
                                cmdA11.show();

                                CloseOwnerWindow('ok');
                            });
                        } else {
                            mini.alert(result.message || "导入失败，请稍候重试!");
                        }
                    })
                }
            })
        }

        function doExport() {
            var excel = new excelData(grid);
            excel.export("专利数据导入处理结果.xls", true);
        }
    </script>
</@js>