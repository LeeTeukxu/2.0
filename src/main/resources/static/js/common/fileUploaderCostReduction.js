(function ($) {
    $.fn.fileUploaderCostReduction = function (options) {
        var fileGrid = null, addButton = null, removeButton = null, uploadButton = null, downButton = null, configButton = null, configCombobox = null, uploader = null;
        var ds = this;
        var buttons = [];
        var container = $(this), defaults = {
            mode: 'Browse',
            configName: 'Default',
            ///上传地址
            uploadUrl: '/attachment/upload',
            ///下载地址
            downloadUrl: '/attachment/download',
            ///删除文件地址
            removeUrl: '/attachment/deleteById',
            ///设置附件类型
            setAttTypeUrl: '/Common/Upload/SetAttachmentType',
            addReferenceUrl: '/Common/Upload/AddReferenceFile',
            getFileUrl: '/attachment/getAttachmentByIDS',
            getConfigUrl: '',
            browseId: 'UploadFile',
            Filters: [
                { title: '压缩文件', extensions: 'zip' }
            ],
            ///是否显示设置附件类型
            showConfig: false,
            afterAddFile: null,
            afterLoad: null,
            afterInit: null,
            eachFileUpload:null
        };
        var instance = this;
        var settings = $.extend({}, defaults, options);
        var mode = settings.mode || "Browse";
        function createToolbar() {
            var ds = [{ id: 0, text: "不能下载附件" }, { id: 1, text: "普通附件" }];
            var op = [
                '<div class="mini-toolbar" id="UploadFileToolbar">',
                '<a  class="mini-button"  iconcls="icon-add" id="UploadFile" style="margin-left:5px" plain="true">选择上传文件</a>',
                '<a class="mini-button" iconcls="icon-ok" id="BeginUpload" visible="false" style="margin-left:5px" plain="true">开始上传</a>',
                '<a class="mini-button" iconcls="icon-download" id="DownloadFile" visible="false" style="margin-left:5px" plain="true">下载文件</a>',
                '<a class="mini-button" iconcls="icon-remove" id="RemoveFile" visible="false"   style="margin-left:5px" plain="true">删除附件</a>',
                "<div class='mini-combobox' visible='false'  id='AttTypeCombobox' data='" + mini.encode(ds) + "' />",
                '<a class="mini-button" iconcls="icon-user" id="ConfigAttType"  visible="false" style="margin-left:5px" plain="true">设置附件</a>',
                '</div>'
            ];
            container.append(op.join(''));
        }
        function createGrid() {
            var op = [
                '<div class="mini-fit">',
                '<div id="AttachmentList" class="mini-datagrid" style="width: 100%; height: 100%"' +
                ' autoload="false" allowresize="false" multiselect="true" showPager="false" allowCellSelect="true"' +
                ' multiSelect="false">',
                '<div property="columns">',
                '<div type="checkcolumn"></div>',
                '<div width="80" field="STATUS" headeralign="center" >状态</div>',
                '<div field="FILENAME" width="300" headeralign="center">文件名称</div>',
                '<div field="FILESIZE" width="100" headeralign="center">文件大小</div>',
                '<div field="PROGRESS" name="PROGRESS" width="120" headeralign="center" visible="false">上传进度</div>',
                '<div field="UPLOADTIME" width="100" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" headeralign="center">上传时间</div>',
                '<div field="UPLOADMAN" width="100" headeralign="center">上传人员</div>',
                '</div>',
                '</div>',
                '</div>'
            ];
            container.append(op.join(''));
        }
        function getUploadConfig(cnfName, callback) {
            var arg = { Name: cnfName };
            var url = settings.getConfigUrl;
            $.getJSON(url, arg, function (r) {
                if (typeof (r) == "string") {
                    r = mini.decode(r);
                    if (r.success) {
                        if (callback != null) callback(r);
                    } else {
                        throw '获取设置失败，创建组件被中止。';
                    }
                }
            });
        }
        function initUploader(cnf) {
            var filters = cnf['Filters'] || "{}";
            var maxLength = cnf['MaxLength'] || "100M";
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
                    Code: settings.configName
                },
                init: {
                    Error: function (a, b) {
                        var errorCode = parseInt(b.code);
                        var file = b.file;
                        if (errorCode == -600) {
                            mini.alert(file.name + '太大，不能上传。');
                        }
                        else if (errorCode == -601) {
                            mini.alert('服务器不允许上传此文件，只能上传zip格式文件。');
                        }
                        else if (errorCode == -602) {
                            mini.alert(file.name + '已在待传列表，请勿重复选择。');
                        }
                    },
                    FilesAdded: function (up, files) {
                        for (var i = 0; i < files.length; i++) {
                            var file = files[i];
                            var k = { ATTTYPE: 1, FILENAME: file.name, FILESIZE: file.size, FILEID: file.id, PROGRESS: 0 };
                            fileGrid.addRow(k);
                        }
                        var column = fileGrid.getColumn('PROGRESS');
                        if (column) fileGrid.showColumn(column);
                        if (uploadButton) uploadButton.show();
                    },
                    UploadProgress: function (up, file) {
                        var files = fileGrid.getData();
                        for (var i = 0; i < files.length; i++) {
                            var f = files[i];
                            var fId = f['FILEID'];
                            if (fId == file.id) {
                                fileGrid.updateRow(f, { PROGRESS: file.percent });
                            }
                        }
                    },
                    UploadComplete: function (up, file) {
                        if (uploadButton) uploadButton.hide();
                        var selRow = fileGrid.getSelected();
                        if (selRow) {
                            if (configButton) configButton.hide();
                            if (configCombobox) configCombobox.hide();
                        }
                    },
                    FileUploaded: function (up, file, res) {
                        var files = fileGrid.getData();
                        var result = mini.decode(res.response);
                        var data = result.data || {};
                        var sName = data['Name'] || "";
                        if (!sName) data = result;
                        if (result['success']) {
                            var name = data['Name'] || "";
                            for (var i = 0; i < files.length; i++) {
                                var f = files[i];
                                var fName = f['FILENAME'] || "";
                                if (name.toString().indexOf(fName)>-1) {
                                    fileGrid.updateRow(f, { ATTTYPE: data['AttType'], ATTID: data['AttID'], UPLOADTIME: data['CreateTime'], UPLOADMAN: data["CreateMan"] });
                                    if (settings.eachFileUpload) {
                                        settings.eachFileUpload(fileGrid, data,f);
                                    }
                                }
                            }
                        }
                    }
                }
            });
            uploader.init();
        }
        function changeByMode(mode) {
            function changeToolbar() {
                if (mode == "Browse") {
                    uploadButton.destroy();
                    addButton.destroy();
                }
            }
            changeToolbar();
        }
        function createDownloadForm(ids, downloadUrl) {
            var text = '<form id="DownloadFileForm" style="display:none" method="POST" action="' + downloadUrl + '" >' +
                '<input type="hidden" name="AttID" id="FileIDS"/>' +
                '</form>';
            container.append(text);
            $('#FileIDS').val(ids);
            return $('#DownloadFileForm');
        }
        function bindEventByMode(mode) {
            function bindToolbar() {
                if (mode == "Add") {
                    uploadButton.on('click', function () {
                        uploader.start();
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
                            mini.confirm('确认要删除选择的文件?', '系统提示', function (btn) {
                                if (btn == 'ok') {
                                    $.post(settings.removeUrl, { GUID: attId }, function (r, c) {
                                        var res = mini.decode(r);
                                        if (res['success']) {
                                            var fileId = row['FILEID'];
                                            if (fileId) uploader.removeFile(fileId);
                                            fileGrid.removeRow(row);
                                            if (downButton) downButton.hide();
                                            if (removeButton) removeButton.hide();
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
                configButton.on('click', function () {
                    var row = fileGrid.getSelected();
                    if (row) {
                        var attId = row['ATTID'];
                        if (attId) {
                            var attType = configCombobox.getValue();
                            var url = settings.setAttTypeUrl;
                            if (url) {
                                $.post(url, { AttID: attId, AttType: attType }, function (r) {
                                    if (r.success) {
                                        mini.alert('设置成功', '系统提示', function () {
                                            fileGrid.updateRow(row, { ATTTYPE: attType });
                                        });
                                    }
                                });
                            }
                        }
                    } else mini.alert('请选择要设备的附件。');
                });
            }
            function bindGrid() {
                fileGrid.setMultiSelect(false);
                if (settings.mode == "Add" && settings.showConfig) {
                    var columns = fileGrid.getColumns();
                    var column = { field: 'ATTTYPE', header: '附件类型', align: 'center', headerAlign: 'center', type: 'comboboxcolumn', width: 100, editor: { type: 'combobox', data: [{ id: 1, text: '普通附件' }, { id: 0, text: '不下载附件' }, { id: 9, text: '审核参考' }] } };
                    columns.insert(1, column);
                    fileGrid.set({
                        columns: columns
                    });
                }
                fileGrid.on('drawcell', function (e) {
                    var field = e.field;
                    var record = e.record;
                    if (field == 'STATUS') {
                        var AttID = record['ATTID'];
                        var attType = parseInt(record['ATTTYPE']);
                        if (attType != 9) {
                            if (AttID) e.cellHtml = '已上传'; else e.cellHtml = '未上传';
                        } else if (attType == 9) e.cellHtml = '审核参考';
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
                });
                fileGrid.on('selectionchanged', rowCheckChange);
                fileGrid.on('rowclick', rowCheckChange);
                function rowCheckChange() {
                    var rows = fileGrid.getSelecteds() || [];
                    if (rows.length > 0) {
                        if (removeButton && settings.mode == "Add") removeButton.show();
                        var row = rows[0];
                        var attId = row['ATTID'];
                        var attType = parseInt(row['ATTTYPE']);
                        if (attId) {
                            if (settings.mode == "Add") {
                                if (settings.showConfig) {
                                    if (configButton) configButton.show();
                                    if (configCombobox) configCombobox.show();
                                } else {
                                    if (configButton) configButton.hide();
                                    if (configCombobox) configCombobox.hide();
                                }
                            } else {
                                if (configButton) configButton.hide();
                                if (configCombobox) configCombobox.hide();
                            }
                            if (downButton) downButton.show();
                            if (attType == 9) {
                                if (configButton) configButton.hide();
                                if (configCombobox) configCombobox.hide();
                            }
                            else {
                                configCombobox.setValue(attType);
                            }
                        }
                        else {
                            if (configButton) configButton.hide();
                            if (configCombobox) configCombobox.hide();
                            if (downButton) downButton.hide();
                        }
                    } else {
                        if (removeButton) removeButton.hide();
                        if (downButton) downButton.hide();
                        if (configButton) configButton.hide();
                        if (configCombobox) configCombobox.hide();
                    }
                }
            }
            bindToolbar();
            bindGrid();
        }
        function init() {
            createToolbar();
            createGrid();
            mini.parse();
            fileGrid = mini.get('AttachmentList'), addButton = mini.get('UploadFile'),
                removeButton = mini.get('RemoveFile'), uploadButton = mini.get('BeginUpload'),
                downButton = mini.get('DownloadFile'),
                configButton = mini.get('ConfigAttType'), configCombobox = mini.get('#AttTypeCombobox');
            buttons = [
                addButton, removeButton, uploadButton, downButton, configButton, configCombobox
            ];
            if (mode == "Add") {
                if (settings.getConfigUrl) {
                    getUploadConfig(options.configName, initUploader);
                } else initUploader(settings);
            }
            bindEventByMode(mode);
            changeByMode(mode);
            if (options.afterInit) {
                options.afterInit(ds);
            }
            if(fileGrid){
                fileGrid.doLayout();
            }
        }
        this.setOption = function (options) {
            if (uploader) uploader.setOption(options);
        }
        this.getPostFile = function () {
            var rows = mini.clone(fileGrid.getData());
            var res = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var attId = row['ATTID'];
                if (attId) {
                    res.push(row);
                } else throw "有没有上传的附件，如不需要请删除后执行操作。";
            }
            return res;
        }
        this.loadFiles = function (params, callback) {
            if (settings.getFileUrl) {
                $.getJSON(settings.getFileUrl, params, function (r) {
                    if (typeof (r) == "string") r = mini.decode(r);
                    if (r.success) {
                        var ds = r.data || [];
                        if (ds.length > 0) {
                            fileGrid.setData(ds);
                        }
                    }
                });
            }
        }
        this.disable = function () {
            for (var i = 0; i < buttons.length; i++) {
                var button = buttons[i];
                if (button) button.disable();
            }
            if (addButton) {
                uploadButton.hide();
                if (uploader) uploader.disableBrowse(true);
            }
            return instance;
        }
        this.clear = function () {
            fileGrid.setData([]);
        }
        this.getToolbar = function () {
            return mini.get('#UploadFileToolbar');
        }
        this.getGrid = function () {
            return fileGrid;
        }
        this.enable = function () {
            for (var i = 0; i < buttons.length; i++) {
                var button = buttons[i];
                if (button) {
                    button.enable();
                }
            }
            if (addButton) {
                addButton.show();
                if (uploader) uploader.disableBrowse(false);
            }
            return instance;
        }
        init();
        return this;
    }
})(jQuery);