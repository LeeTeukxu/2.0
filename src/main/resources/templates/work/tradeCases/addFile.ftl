<#include "/shared/dialog.ftl">

<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>

<div class="mini-toolbar" id="UploadFileToolbar">
    <a class="mini-button" iconcls="icon-add" id="UploadFile" style="margin-left:5px" plain="true">选择上传文件</a>
    <a class="mini-button" iconcls="icon-ok" id="BeginUpload" visible="false" style="margin-left:5px"
       plain="true">开始上传</a>
    <a class="mini-button" iconcls="icon-download" id="DownloadFile" visible="false" style="margin-left:5px"
       plain="true">下载文件</a>
    <a class="mini-button" iconcls="icon-remove" id="RemoveFile" visible="false" style="margin-left:5px" plain="true">删除附件</a>
    <a class="mini-button" iconcls="icon-user" id="ConfigAttType" visible="false" style="margin-left:5px" plain="true">设置附件</a>
</div>
<div class="mini-fit">
    <div id="AttachmentList" class="mini-datagrid" style="width: 100%; height: 100%" autoload="false"
         autoload="false" allowresize="false" multiselect="false" showPager="false" allowCellSelect="true"
         multiSelect="false" ondrawCell="drawCell" allowCellEdit="true" allowCellValid="true"
         url="/work/commitFile/getAttachmentByIDS" onrowclick="rowCheckChange">
        <div property="columns">
            <div type="checkcolumn"></div>
            <div width="60" field="STATUS" headeralign="center">状态</div>
            <div field="MEMO" width="120"  headeralign="center" align="center">特别说明
                <input property="editor" class="mini-textbox">
            </div>
            <div field="FILENAME" width="200" headeralign="center" align="center">文件名称</div>
            <div field="ATTTYPE" width="150" type="comboboxcolumn" headeralign="center" align="center" vtype="required"
                 required="true">文件类型
                <input property="editor" class="mini-combobox" data='${items}' valueField="id" textField="text"
                       valueFromSelect="true"/>
            </div>
            <div field="LASTDATE"  headeralign="center" align="center" vtype="required" dataType="date"
                 dateFormat="yyyy-MM-dd">最后申报日期
                <input property="editor" class="mini-datepicker" dateFormat="yyyy-MM-dd"/>
            </div>
            <div field="FILESIZE" width="100" headeralign="center" align="center">文件大小</div>
            <div field="PROGRESS" name="PROGRESS" width="120" headeralign="center" visible="false" align="center">上传进度
            </div>
            <div align="center" field="UPLOADTIME" width="120" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss"
                 headeralign="center">上传时间
            </div>
            <div field="UPLOADMAN" width="100" headeralign="center" align="center">上传人员</div>
        </div>
    </div>
</div>
</@layout>
<@js>
    <script type="text/javascript">
        var att = null;
        var mode = 'Add';
        mini.parse();
        var fileGrid = mini.get('#AttachmentList'), addButton = mini.get('UploadFile'),
                downButton = mini.get('DownloadFile'),
                removeButton = mini.get('RemoveFile'), uploadButton = mini.get('BeginUpload');
        var uploader = null;
        var attIds = "${AttIDS}";
        var settings = {
            mode: 'Add',
            configName: 'Default',
            ///上传地址
            uploadUrl: '/work/commitFile/upload',
            ///下载地址
            downloadUrl: '/attachment/download',
            ///删除文件地址
            removeUrl: '/work/commitFile/remove',
            getFileUrl: '/work/commitFile/getAttachmentByIDS',
            browseId: 'UploadFile',
            Filters: [
                {title: 'Office文档', extensions: 'doc,docx,xls,xlsx'},
                {title: 'pdf文档', extensions: 'pdf'},
                {title: '图片', extensions: 'jpg,bmp,gif,png'},
                {title: '压缩文件', extensions: 'zip,rar'}
            ],
            ///是否显示设置附件类型
            showConfig: false,
            afterAddFile: null,
            afterLoad: null,
            afterInit: null,
            eachFileUpload: null
        };
        $(function () {
            initDocument();
            bindEventByMode('Add');
            if (attIds) {
                fileGrid.load({IDS: attIds});
            }
        });
        function initDocument() {
            var filters = settings['Filters'] || "{}";
            var maxLength = settings['MaxLength'] || "100M";
            if (filters) {
                if (typeof (filters) == "string") filters = mini.decode(filters);
            }
            uploader = new plupload.Uploader({
                browse_button: settings.browseId,
                url: settings.uploadUrl,
                flash_swf_url: '/js/plupload/Moxie.swf',
                silverlight_xap_url: '/js/plupload/Moxie.xap',
                filters: filters,
                multipart: true,
                max_file_size: maxLength,
                prevent_duplicates: true,
                multipart_params: {
                    CasesID: '${CasesID}'
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
                        for (var i = 0; i < files.length; i++) {
                            var file = files[i];
                            var k = {FILENAME: file.name, FILESIZE: file.size, FILEID: file.id, PROGRESS: 0};
                            fileGrid.addRow(k);
                            fileGrid.validateRow(k);
                        }
                        var column = fileGrid.getColumn('PROGRESS');
                        if (column) fileGrid.showColumn(column);
                        if (uploadButton) uploadButton.show();
                    },
                    BeforeUpload: function (up, file) {
                        var files = fileGrid.getData();
                        for (var i = 0; i < files.length; i++) {
                            var f = files[i];
                            var fId = f['FILEID'];
                            if (fId == file.id) {
                                uploader.setOption("multipart_params", {
                                    "AttType": f["ATTTYPE"],
                                    CasesID: '${CasesID}',
                                    LastDate:mini.formatDate(f["LASTDATE"],'yyyy-MM-dd'),
                                    Memo:encodeURI(f["MEMO"] || "")
                                });
                            }
                        }
                    },
                    UploadProgress: function (up, file) {
                        var files = fileGrid.getData();
                        for (var i = 0; i < files.length; i++) {
                            var f = files[i];
                            var fId = f['FILEID'];
                            if (fId == file.id) {
                                fileGrid.updateRow(f, {PROGRESS: file.percent});
                            }
                        }
                    },
                    UploadComplete: function (up, file) {
                        if (uploadButton) uploadButton.hide();
                        var selRow = fileGrid.getSelected();
                        if (selRow) {
                            if (configButton) configButton.hide();
                        }
                    },
                    FileUploaded: function (up, file, res) {
                        var files = fileGrid.getData();
                        var result = mini.decode(res.response);
                        var data = result.data || {};
                        var sName = data['Name'] || "";
                        if (!sName) data = result;
                        if (result['success']) {
                            var name = data['Name'];
                            for (var i = 0; i < files.length; i++) {
                                var f = files[i];
                                var fName = f['FILENAME'] || "";
                                if (name.toString().indexOf(fName) > -1) {
                                    fileGrid.updateRow(f, {
                                        ATTID: data['AttID'],
                                        UPLOADTIME: data['CreateTime'],
                                        UPLOADMAN: data["CreateMan"]
                                    });
                                    if (settings.eachFileUpload) {
                                        settings.eachFileUpload(fileGrid, data, f);
                                    }
                                }
                            }
                        }
                    }
                }
            });
            uploader.init();
        }

        function rowCheckChange() {
            var rows = fileGrid.getSelecteds() || [];
            if (rows.length > 0) {
                var row = rows[0];
                var attId = row['ATTID'];
                var state = parseInt(row["STATE"] || 0);
                if (attId) {
                    if (downButton) downButton.show();
                    if (removeButton) {
                        if (state <= 2) {
                            removeButton.show();
                        } else removeButton.hide();
                    }
                }
                else {
                    if (downButton) downButton.hide();
                }
            } else {
                if (removeButton) removeButton.hide();
                if (downButton) downButton.hide();
            }
        }

        function drawCell(e) {
            var field = e.field;
            var record = e.record;
            if (field == 'STATUS') {
                var AttID = record['ATTID'];
                var attType = parseInt(record['ATTTYPE']);
                if (AttID) e.cellHtml = '已上传'; else e.cellHtml = '未上传';
            } else if (field == 'FILESIZE') {
                var size = parseInt(record['FILESIZE']);
                var sizeLength = ['K', 'M', 'T'];
                var ss = [1024, 1024 * 1024, 1024 * 1024 * 1024];
                if (size < 1024) e.cellHtml = size + "字节";
                else {
                    var ddd = 0;
                    var vvv = '';
                    for (var i = 0; i < ss.length; i++) {
                        var dx = size / ss[i];
                        if (dx < 1024) {
                            ddd = dx.toFixed(2);
                            vvv = sizeLength[i];
                            break;
                        }
                    }
                    e.cellHtml = ddd + vvv;
                }
            }
            else if (field == "PROGRESS") {
                var value = e.value;
                if (record['ATTID']) value = 100;
                e.cellHtml = '<div class="progressbar">'
                        + '<div class="progressbar-percent" style="width:' + value + '%;"></div>'
                        + '<div class="progressbar-label">' + value + '%</div>'
                        + '</div>';
            }
        }

        function bindEventByMode(mode) {
            function bindToolbar() {
                if (mode == "Add") {
                    uploadButton.on('click', function () {
                        fileGrid.validate();
                        if (fileGrid.isValid() == true) {
                            uploader.start();
                        } else {
                            mini.alert('信息填写不完整，不能上传文件!');
                        }
                    });
                }
                downButton.on('click', function () {
                    var rows = fileGrid.getSelecteds();
                    if (rows.length > 0) {
                        var ids = [];
                        for (var i = 0; i < rows.length; i++) {
                            var attId = rows[i]['ATTID'];
                            if (attId) ids.push(attId);
                        }
                        if (ids.length > 0) {
                            var form = createDownloadForm(ids, settings.downloadUrl);
                            if (form.length > 0) {
                                form[0].submit();
                                form.remove();
                            }
                        } else {
                            mini.alert('导入附件还未处理，现在无法下载。提交审核后可进行操作。');
                        }
                    }
                });
                removeButton.on('click', function () {
                    var rows = fileGrid.getSelecteds();
                    if (rows.length > 0) {
                        var row = rows[0];
                        var attId = row['ATTID'];
                        if (attId) {
                            var fileName=row["FILENAME"];
                            mini.confirm('确认要删除选择文件【'+fileName+'】?', '系统提示', function (btn) {
                                if (btn == 'ok') {
                                    $.post(settings.removeUrl, {AttID: attId}, function (r, c) {
                                        var res = mini.decode(r);
                                        if (res['success']) {
                                            function g(){
                                                var fileId = row['FILEID'];
                                                if (fileId) uploader.removeFile(fileId);
                                                fileGrid.removeRow(row);
                                                if (downButton) downButton.hide();
                                                if (removeButton) removeButton.hide();
                                            }
                                            mini.alert('文件【'+fileName+'】已删除!','系统提示',function(){
                                                g();
                                            });
                                        }
                                    });
                                }
                            });
                        } else {
                            var fileId = row['FILEID'];
                            uploader.removeFile(fileId);
                            fileGrid.removeRow(row);
                        }
                    } else {
                        mini.alert('请选择要删除的文件。');
                    }
                });
            }

            bindToolbar();
        }
        function createDownloadForm(ids, downloadUrl) {
            var text = '<form id="DownloadFileForm" style="display:none" method="POST" action="' + downloadUrl + '" >' +
                    '<input type="hidden" name="AttID" id="FileIDS"/>' +
                    '</form>';
            $('body').append(text);
            $('#FileIDS').val(ids);
            return $('#DownloadFileForm');
        }
    </script>
</@js>
