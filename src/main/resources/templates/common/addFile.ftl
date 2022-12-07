<#include "/shared/dialog.ftl">

<@layout>
<#--    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>-->
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript">
        var states = [
            {id: 6, text: '未选择'},
            {id: 7, text: '已选择'},
            {id: 8, text: '已结算'}
        ];
    </script>
    <#if FileType!="Tech">
        <div class="mini-toolbar" id="UploadFileToolbar">
            <a class="mini-button" iconcls="icon-add" id="UploadFile" style="margin-left:5px" plain="true">选择上传文件</a>
            <a class="mini-button" iconcls="icon-ok" id="BeginUpload" visible="false" style="margin-left:5px"
               plain="true">开始上传</a>
            <span class="separator"></span>
            <a class="mini-button" iconcls="icon-download" id="DownloadFile" visible="false" style="margin-left:5px"
               plain="true">下载文件</a>
            <a class="mini-button" iconcls="icon-remove" id="RemoveFile" visible="false" style="margin-left:5px"
               plain="true">删除附件</a>
            <a class="mini-button" iconcls="icon-user" id="ViewFile" visible="false" style="margin-left:5px"
               plain="true" onclick="viewDocument">在线查看</a>
        </div>
        <div class="mini-fit" id="ConFit">
            <div id="AttachmentList" class="mini-datagrid" style="width: 100%; height:100%" autoload="false"
                 autoload="false" allowresize="false" multiselect="false" showPager="false" allowCellSelect="true"
                 multiSelect="false" ondrawCell="drawCell" allowCellEdit="true" allowCellValid="true"
                 url="/attachment/getAttachmentByIDS" onrowclick="rowCheckChange">
                <div property="columns">
                    <div type="checkcolumn"></div>
                    <div width="60" field="STATUS" headeralign="center">状态</div>
                    <div field="FILENAME" width="200" headeralign="center" align="center">文件名称</div>
                    <div field="FILESIZE" width="100" headeralign="center" align="center">文件大小</div>
                    <div field="PROGRESS" name="PROGRESS" width="120" headeralign="center" visible="false"
                         align="center">上传进度
                    </div>
                    <div align="center" field="UPLOADTIME" width="120" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss"
                         headeralign="center">上传时间
                    </div>
                    <div field="UPLOADMAN" width="100" headeralign="center" align="center">上传人员</div>
                </div>
            </div>
        </div>
    <#else>
        <div class="mini-splitter" vertical="true" style="width:100%;height:100%">
            <div size="30%" title="上传文件">
                <div class="mini-toolbar" id="UploadFileToolbar">
                    <a class="mini-button" iconcls="icon-add" id="UploadFile" style="margin-left:5px" plain="true">选择上传文件</a>
                    <a class="mini-button" iconcls="icon-ok" id="BeginUpload" visible="false" style="margin-left:5px"
                       plain="true">开始上传</a>
                    <span class="separator"></span>
                    <a class="mini-button" iconcls="icon-download" id="DownloadFile" visible="false"
                       style="margin-left:5px"
                       plain="true">下载文件</a>
                    <a class="mini-button" iconcls="icon-remove" id="RemoveFile" visible="false" style="margin-left:5px"
                       plain="true">删除附件</a>
                    <a class="mini-button" iconcls="icon-user" id="ViewFile" visible="false" style="margin-left:5px"
                       plain="true" onclick="viewDocument">在线查看</a>
                </div>
                <div class="mini-fit" id="ConFit">
                    <div id="AttachmentList" class="mini-datagrid" style="width: 100%; height:100%" autoload="false"
                         autoload="false" allowresize="false" multiselect="false" showPager="false"
                         allowCellSelect="true"
                         multiSelect="false" ondrawCell="drawCell" allowCellEdit="true" allowCellValid="true"
                         url="/attachment/getAttachmentByIDS" onrowclick="rowCheckChange">
                        <div property="columns">
                            <div type="checkcolumn"></div>
                            <div width="60" field="STATUS" headeralign="center">状态</div>
                            <div field="FILENAME" width="200" headeralign="center" align="center">文件名称</div>
                            <div field="FILESIZE" width="100" headeralign="center" align="center">文件大小</div>
                            <div field="PROGRESS" name="PROGRESS" width="120" headeralign="center" visible="false"
                                 align="center">上传进度
                            </div>
                            <div align="center" field="UPLOADTIME" width="120" dataType="date"
                                 dateFormat="yyyy-MM-dd HH:mm:ss"
                                 headeralign="center">上传时间
                            </div>
                            <div field="UPLOADMAN" width="100" headeralign="center" align="center">上传人员</div>
                        </div>
                    </div>
                </div>
            </div>
            <div title="选择专利挖掘文件">
                <div class="mini-layout" style="width:100%;height:100%">
                    <div region="north" height="180" title="本项目选择(<span style='color:red'>只能专利技术挖掘库中选择一个交底书</span>)">
                        <div class="mini-datagrid" id="datagrid2" style="width:100%;height:100%" sortfield="UploadTime"
                             onbeforeselect="beforeSelectTech" onload="afterLoad" showPager="false"
                             ondrawcell="grid2Draw" onbeforeload="bGrid2Load"
                             sortorder="desc" pagesize="10"
                             url="/techSupport/getFiles?State=10&Mode=Select&RefID=${SubID}" autoload="false">
                            <div property="columns">
                                <div field="Action" width="100" headerAlign="center" align="center" ></div>
                                <div type="comboboxcolumn" field="State" width="80" headerAlign="center" align="center"
                                     allowSort="true">业务状态
                                    <input property="editor" class="mini-combobox" data="states"/>
                                </div>
                                <div field="FileSN" width="150" align="center" headeralign="center"
                                     allowSort="true">文件编号
                                </div>
                                <div field="TechType" width="100" align="center" headeralign="center"
                                     type="comboboxcolumn" allowSort="true">技术领域
                                    <input property="editor" class="mini-combobox" url="/systems/dict/getByDtId?dtId=25"
                                           valueFromSelect="true"/>
                                </div>
                                <div field="CreateMan" width="100" align="center" headeralign="center"
                                     type="treeselectcolumn" allowSort="true">商务人员
                                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID" parentField="PID"/>
                                </div>
                                <div field="ClientName" align="center" width="200" headeralign="center" allowSort="true">客户名称
                                </div>
                                <div field="Name" width="150" align="center" headeralign="center" allowSort="true">
                                    文件名称
                                </div>
                                <div field="Size" width="80" align="center" headeralign="center" allowSort="true">大小
                                </div>
                                <div field="UploadManName" width="80" align="center" headeralign="center"
                                     allowSort="true">上传人员
                                </div>
                                <div field="UploadTime" width="120" align="center" headeralign="center" dataType="date"
                                     allowSort="true" dateFormat="yyyy-MM-dd HH:mm">上传时间
                                </div>
                            </div>
                        </div>
                    </div>
                    <div region="center" title="未选择文件" showHeader="true" showCollapse="false">
                        <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" sortfield="UploadTime"
                             onbeforeselect="beforeSelectTech" onload="afterLoad" showFilterRow="true"
                             allowResize="true" ondrawcell="grid1Draw" onbeforeload="bGrid1Load"
                             sortorder="desc" pagesize="10" url="/techSupport/getFiles?State=10&Mode=UnSelect"
                             autoload="false"  >
                            <div property="columns">
                                <div field="Action" width="100"  headerAlign="center" align="center"></div>
                                <div type="comboboxcolumn" field="State" width="80" headerAlign="center" align="center"
                                     allowSort="true">业务状态
                                    <input property="editor" class="mini-combobox" data="states"/>
                                </div>
                                <div field="FileSN" width="150" align="center" headeralign="center"
                                     allowSort="true">文件编号
                                </div>
                                <div field="TechType" width="100" align="center" headeralign="center"
                                     type="comboboxcolumn" allowSort="true">技术领域
                                    <input property="editor" class="mini-combobox" url="/systems/dict/getByDtId?dtId=25"
                                           valueFromSelect="true"/>
                                </div>
                                <div field="CreateMan" width="100" align="center" headeralign="center"
                                     type="treeselectcolumn" allowSort="true">商务人员
                                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep" textField="Name" valueField="FID" parentField="PID"/>
                                </div>
                                <div field="ClientName" align="center" width="200" headeralign="center" allowSort="true">客户名称
                                    <input id="nameFilter" property="filter" class="mini-textbox" style="width:100%;"   value="${ClientName}"
                                           onvaluechanged="onFilterChanged" emptyText="文件编号\文件名称\客户名称\上传人姓名"/>
                                </div>
                                <div field="Name" width="150" align="center" headeralign="center" allowSort="true">
                                    文件名称
                                </div>
                                <div field="Size" width="80" align="center" headeralign="center" allowSort="true">大小
                                </div>
                                <div field="UploadManName" width="80" align="center" headeralign="center"
                                     allowSort="true">上传人员
                                </div>
                                <div field="UploadTime" width="120" align="center" headeralign="center" dataType="date"
                                     allowSort="true" dateFormat="yyyy-MM-dd HH:mm">上传时间
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="mini-panel" title="<span style='color:red'>从专利技术挖掘库中选择</span>"
                     style="width:100%;height:100%;display: none">
                    <div class="mini-toolbar">
                        <table style="width:100%;">
                            <tr>
                                <td style="width:100%;">
                                    <input class="mini-textbox" id="QueryText" emptyText="文件编号\文件名称\客户名称\上传人姓名"
                                           style="width:240px"  value="${ClientName}"/>
                                    <a class="mini-button" iconCls="icon-search" onclick="doQuery()">查询</a>
                                    <span class="separator"></span>
                                    <a class="mini-button" iconCls="icon-zoomin" onclick="doQuery()"
                                       id="cmdUnSelect">未选文件</a>
                                    <a class="mini-button" iconCls="icon-zoomout" onclick="querySelect()"
                                       id="cmdSelect">已选文件</a>
                                </td>
                                <td style="white-space:nowrap;">
                                    <span class="separator"></span>
                                    <a class="mini-button" id="checkTech" iconcls="icon-save"
                                       onclick="saveTechSupport()"
                                       style="margin-left:5px"
                                       plain="true">确认选择</a>
                                    <a class="mini-button" id="unCheckTech" iconcls="icon-remove"
                                       onclick="cancelTechSupport()"
                                       style="margin-left:5px"
                                       plain="true">删除文件</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="mini-fit">

                    </div>
                </div>
            </div>
        </div>
    </#if>
    <div bodyStyle="overflow:hidden" class="mini-window" title="文件提交核稿" width="600" height="240"
         style="display:none" id="CommitWindow">
        <table style="width:100%" class="layui-table" id="CommitForm">
            <tr>
                <td style="width:100px">特别说明</td>
                <td>
                    <textarea class="mini-textarea" style="height:70px;width:100%" id="CommitMemo"></textarea>
                </td>
            </tr>
            <tr>
                <td>指定核稿人</td>
                <td>
                    <div id="ConAuditMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep"
                         textField="Name" valueField="FID" parentField="PID" enabled="false"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" id="CmdCommitFiles" onclick="commitFiles();
">确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('CommitWindow')
">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        var att = null;
        var mode = '${Mode}';
        mini.parse();
        var xGrid = null;
        var xManager = null;
        var attIds = "${IDS}";
        var showHis = ${ShowHis};
        var grid1 = mini.get('#grid1');
        var loadMode = 'UnSelect';
        var loadNum = 0;
        var fileGrid = mini.get('#AttachmentList'), addButton = mini.get('UploadFile'),
            downButton = mini.get('DownloadFile'),
            removeButton = mini.get('RemoveFile'),
            uploadButton = mini.get('BeginUpload'), viewButton = mini.get('ViewFile');
        var uploader = null;
        var eventFun = {
            eachFileUploaded: function (data) {
            },
            eachFileRemoved: function (data) {
            },
            uploadError: function (data) {
            }
        };
        var settings = {
            mode: '${Mode}',
            configName: 'Default',
            ///上传地址
            uploadUrl: '${UploadUrl}',
            ///下载地址
            downloadUrl: '/attachment/download',
            ///删除文件地址
            removeUrl: '/attachment/deleteById',
            getFileUrl: '/attachment/getAttachmentByIDS',
            browseId: 'UploadFile',
            Filters: {
                mime_type: [
                    {title: '压缩文件', extensions: 'zip,rar,7z'},
                    {title: 'Office文档', extensions: 'doc,docx,xls,xlsx'},
                    {title: 'pdf文档', extensions: 'pdf'},
                    {title: '图片', extensions: 'jpg,bmp,gif,png'}
                ]
            },
            ///是否显示设置附件类型
            showConfig: false,
            afterAddFile: null,
            afterLoad: null,
            afterInit: null,
            eachFileUpload: null
        };
        $(function () {
            $('body').css({'overflow':'hidden'});
            if (mode == "Edit" || mode == "Add") initDocument();
            bindEventByMode('${Mode}');
            if (attIds) {
                fileGrid.load({IDS: attIds});
                fileGrid.sortBy('UPLOADTIME', 'desc');
            }
            debugger;
            <#if FileType=="Tech">
            <#if HasFile==1>
            querySelect();
            <#else>
            doQuery();
            </#if>
            </#if>
        });

        function saveTechSupport() {
            if (loadNum > 0) {
                mini.alert('只允许上传一个技术挖掘文件,请将原来上传文件删除后再进行操作!');
                return;
            }
            var grid1 = mini.get('#datagrid1');
            var row = grid1.getSelected();
            if (row) {
                function g(){
                    eventFun.eachFileUploaded(row, mini, window,function(result){
                        if(result.success){
                            mini.alert('选择技术挖掘文件成功!', '系统提示', function () {
                                doQuery();
                            });
                        }  else mini.alert(result.message || "保存数据失败，请稍候重试!");
                    });
                }
                mini.confirm('确认要选择当前文件为交底文件吗？','系统提示',function(act){
                    if(act=='ok') g();
                })
            } else mini.alert('请选择文件!');
        }

        function cancelTechSupport() {
            var grid1 = mini.get('#datagrid2');
            var row = grid1.getSelected();
            if (row) {
                function g() {
                    var attId = row["AttID"];
                    var arg = {SubID: '${SubID}', AttID: attId};
                    var url = '/techSupport/unCheckFile';
                    $.post(url, arg, function (result) {
                        if (result.success) {
                            mini.alert('设置成功!', '系统提示', function () {
                                doQuery();
                            });
                        } else mini.alert(result.message || "设置失败，请稍候重试！");
                    })
                }

                mini.confirm('确认要取消选择的文件吗？', '系统提示', function (result) {
                    if (result == 'ok') g();
                });
            } else mini.alert('请选择要取消的记录!');

        }

        function beforeSelectTech(e) {
            var cmdCheck = mini.get('#checkTech');
            var cmdUnCheck = mini.get('#unCheckTech');
            var row = e.record;
            var refId = row["RefID"];
            if (refId) {
                cmdCheck.hide();
                cmdUnCheck.show();
            } else {
                cmdCheck.show();
                cmdUnCheck.hide();
            }
        }

        function querySelect() {
            var txtQuery = mini.get('QueryText');
            var grid1 = mini.get('#datagrid1');
            var grid2 = mini.get('#datagrid2');
            grid1.setAutoLoad(false);
            var fields = ['FileSN', 'ClientName', 'Name', 'UploadManName'];
            var txt = txtQuery.getValue();
            var cs = [];
            var arg = {};
            if (txt) {
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {field: field, oper: 'LIKE', value: txt};
                    cs.push(obj);
                }
            }
            if (cs.length > 0) {
                arg["Query"] = mini.encode(cs);
            }
            loadMode = 'Select';
            grid1.setUrl('/techSupport/getFiles?State=10&Mode=UnSelect');
            grid1.load(arg);

            grid2.setUrl('/techSupport/getFiles?State=10&Mode=Select&RefID=${SubID}');
            grid2.load();
        }

        function doQuery() {
            var txtQuery = mini.get('QueryText');
            var grid1 = mini.get('#datagrid1');
            var grid2 = mini.get('#datagrid2');
            grid1.setAutoLoad(false);
            var fields = ['FileSN', 'ClientName', 'Name', 'UploadManName'];
            var txt = txtQuery.getValue();
            var cs = [];
            var arg = {};
            if (txt) {
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {field: field, oper: 'LIKE', value: txt};
                    cs.push(obj);
                }
            }
            if (cs.length > 0) {
                arg["Query"] = mini.encode(cs);
            }
            loadMode = 'UnSelect';
            grid1.setUrl('/techSupport/getFiles?State=10&Mode=UnSelect');
            grid1.load(arg);

            grid2.setUrl('/techSupport/getFiles?State=10&Mode=Select&RefID=${SubID}');
            grid2.load();
        }

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
                multi_selection: ${ManyFile},
                multiple_queues: ${ManyFile},
                max_file_size: maxLength,
                prevent_duplicates: true,
                init: {
                    Error: function (a, b) {
                        var errorCode = parseInt(b.code);
                        var file = b.file;
                        if (errorCode == -600) {
                            mini.alert(file.name + '太大，不能上传。');
                        } else if (errorCode == -601) {
                            mini.alert('服务器不允许上传此文件。');
                        } else if (errorCode == -602) {
                            mini.alert(file.name + '已在待传列表，请勿重复选择。');
                        }
                    },
                    FilesAdded: function (up, files) {
                        if (showHis == 1) {
                            fileGrid.clearGroup();
                        }
                        for (var i = 0; i < files.length; i++) {
                            var file = files[i];
                            var k = {FILENAME: file.name, FILESIZE: file.size, FILEID: file.id, PROGRESS: 0, FTYPE: 1};
                            fileGrid.addRow(k);
                            fileGrid.validateRow(k);
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
                        if (showHis == 1) {
                            var rows = fileGrid.getData();
                            var cs = [];
                            for (var i = 0; i < rows.length; i++) {
                                var row = rows[i];
                                cs.push(row["ATTID"]);
                            }
                            fileGrid.load({IDS: cs.join(',')});
                        }
                    },
                    FileUploaded: function (up, file, res) {
                        var files = fileGrid.getData();
                        var result = mini.decode(res.response);
                        var data = result.data || {};
                        var sName = data["name"] || data['Name'] || "";
                        if (!sName) data = result;
                        if (result['success']) {
                            if (showHis == 1) {
                                fileGrid.clearGroup();
                            }
                            var name = data["name"] || data['Name'];
                            for (var i = 0; i < files.length; i++) {
                                var f = files[i];
                                var fName = f['FILENAME'] || "";
                                if (name.toString().indexOf(fName) > -1) {
                                    fileGrid.updateRow(f, {
                                        ATTID: data['AttID'],
                                        UPLOADTIME: data['CreateTime'],
                                        UPLOADMAN: data["CreateMan"],
                                        FTYPE: 1
                                    });
                                    if (settings.eachFileUpload) {
                                        settings.eachFileUpload(fileGrid, data, f);
                                    }
                                    eventFun.eachFileUploaded(data, mini, window);
                                }
                            }
                        } else eventFun.uploadError(result, mini, window);
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

                    var fileName = (row["FILENAME"] || "").toLowerCase();
                    var extNames = fileName.split('.');
                    if(extNames.length>1){
                        var extName=extNames[extNames.length-1];
                        if (extName == "jpg" || extName == "bmp" || extName == "png") {
                            viewButton.show();
                        } else viewButton.hide();
                    } else viewButton.hide();
                } else {
                    if (downButton) downButton.hide();
                }
            } else {
                if (removeButton) removeButton.hide();
                if (downButton) downButton.hide();
            }
            mini.get('#ConFit').doLayout();
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
            } else if (field == "PROGRESS") {
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
                if (mode == "Add" || mode == "Edit") {
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
                            var url = settings.downloadUrl + '?AttID=' + ids.join(',');
                            $.fileDownload(url, {
                                httpMethod: 'POST',
                                successCallback: function (xurl) {

                                },
                                failCallback: function (html, xurl) {
                                    mini.alert('下载错误:' + html, '系统提示');
                                }
                            });

                            // var form = createDownloadForm(ids, settings.downloadUrl);
                            // if (form.length > 0) {
                            //     form[0].submit();
                            //     form.remove();
                            // }
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
                            var fileName = row["FILENAME"];
                            mini.confirm('确认要删除选择文件【' + fileName + '】?', '系统提示', function (btn) {
                                if (btn == 'ok') {
                                    $.post(settings.removeUrl, {AttID: attId}, function (r, c) {
                                        var res = mini.decode(r);
                                        if (res['success']) {
                                            function g() {
                                                var fileId = row['FILEID'];
                                                if (fileId) uploader.removeFile(fileId);
                                                fileGrid.removeRow(row);
                                                if (downButton) downButton.hide();
                                                if (removeButton) removeButton.hide();
                                                eventFun.eachFileRemoved(mini.clone(row));
                                            }

                                            g();
                                            mini.alert('文件【' + fileName + '】已删除!', '系统提示');
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
            if (mode == "Browse") {
                uploadButton.destroy();
                removeButton.destroy();
                addButton.destroy();
            }
            if (showHis == 1) {
                fileGrid.on('load', function () {
                    var gg = [];
                    var rows = fileGrid.getData();
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var t = parseInt(row["FTYPE"] || 0);
                        if (gg.indexOf(t) == -1) gg.push(t);
                    }
                    if (gg.length > 1) {
                        fileGrid.setShowGroupSummary(false);
                        fileGrid.setShowSummaryRow(true);
                        fileGrid.groupBy('FTYPE', 'asc');
                    }
                });
                fileGrid.on('drawgroup', function (e) {
                    var value = parseInt(e.value || 1);
                    e.cellHtml = (value == 1 ? "最新文件" : "历史文件");
                });
            }
        }

        function createDownloadForm(ids, downloadUrl) {
            var text = '<form id="DownloadFileForm" style="display:none" method="POST" action="' + downloadUrl + '" >' +
                '<input type="hidden" name="AttID" id="FileIDS"/>' +
                '</form>';
            $('body').append(text);
            $('#FileIDS').val(ids);
            return $('#DownloadFileForm');
        }

        function addEvent(event, fun) {
            if (eventFun[event]) {
                eventFun[event] = fun;
            }
        }

        window.addEvent = addEvent;
        var commitUrl = '';

        function enableCommit(grid, xCommitUrl) {
            xGrid = grid;
            var cmd = new mini.Button();
            cmd.setText('提交技审');
            cmd.setIconCls('icon-ok');
            cmd.set({
                onclick: commitAcceptTech
            });
            cmd.render('UploadFileToolbar');
            commitUrl = xCommitUrl;
        }

        function enableCommitEx(buttonText, commitFun) {
            var cmd = new mini.Button();
            cmd.setText(buttonText);
            cmd.setIconCls('icon-ok');
            cmd.set({
                onclick: function () {
                    commitFun(mini, window);
                }
            });
            cmd.render('UploadFileToolbar');
        }

        function commitFiles() {
            var rows = xGrid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#CommitWindow');
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var acceptTechFiles = row["AcceptTechFiles"];
                    if (acceptTechFiles) {
                        cs.push(subId);
                    }
                }
                if (cs.length > 0) {
                    function g() {
                        var url = '/casesTech/commitTechFiles';
                        if (commitUrl) url = commitUrl;
                        var memo = mini.get('#CommitMemo').getValue() || "";
                        var arg = {SubIDS: cs.join(','), Memo: memo};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.alert('选择的案件提交成功', '系统提示', function () {
                                    win.hide();
                                    xGrid.reload();
                                    CloseOwnerWindow('ok');
                                });
                            } else mini.alert(result.message || "提交审核失败，请稍候重试!");
                        });
                    }

                    mini.confirm('确认要将专利申报文件提交核稿', '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('选择要处理的案件记录!');
        }

        function commitAcceptTech() {
            var datas = fileGrid.getData();
            if (datas.length == 0) {
                mini.alert('没有上传专利申报文件，不允许提交审核!');
                return;
            }
            for (var i = 0; i < datas.length; i++) {
                var data = datas[i];
                var attId = data["ATTID"];
                if (!attId) {
                    mini.alert('请完成文件上传以后再进行提交审核操作!');
                    return;
                }
            }
            var rows = xGrid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#CommitWindow');
                var treeCon = mini.get('#ConAuditMan');
                if (treeCon) {
                    var record = rows[0];
                    var auditMan = parseInt(record["TechAuditMan"] || 0, 10);
                    treeCon.setValue(auditMan);
                }
                win.show();
            } else mini.alert('选择要处理的案件记录!');
        }

        function closeWindow(winName) {
            var win = mini.get('#' + winName);
            if (win) win.hide();
        }

        var cmdUnSelect = mini.get('#cmdUnSelect');
        var cmdSelect = mini.get('#cmdSelect');

        function afterLoad(e) {
            var grid2 = mini.get('#datagrid2');
            loadNum = (grid2.getData() || []).length;
        }

        function viewDocument() {
            var cs=[];
            var rows = fileGrid.getSelecteds();
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var attId = row["ATTID"];
                    if (attId) {
                      cs.push(attId);
                    }
                }
                if(cs.length>0){
                    mini.mask('正在获取文件数据......');
                    var url='/attachment/getImages';
                    var arg={IDS:cs.join(',')};
                    $.getJSON(url, arg, function (result) {
                        mini.unmask('body');
                        var isOK = parseInt(result.status);
                        if (isOK == 1) {
                            window.top.showImages(mini.encode(result));
                        } else {
                            var msg = result.message || "无法加载通知书附件。";
                            layer.alert(msg);
                        }
                    });
                }
            }
        }
        function grid1Draw(e){
            var field=e.field;
            if(field=="Action"){
                var attId=e.record["AttID"];
                e.cellHtml="<a href='javascript:void(0)' onclick='saveTechSupport()'" +
                    "style='text-decoration: underline; cursor: pointer;'>设置为交底文件</a>";
            }
        }
        function grid2Draw(e){
            var field=e.field;
            if(field=="Action"){
                e.cellHtml="<a href='javascript:void(0)' onclick='cancelTechSupport()'" +
                    " style='text-decoration: underline; cursor: pointer'>取消选择</a>";
            }
        }
        function bGrid1Load(e){
            e.data["Mode"]="UnSelect";
            e.data["RefID"]="";
        }
        function bGrid2Load(e){
            e.data["Mode"]="Select";
            e.data["RefID"]="${SubID}";
        }
        function onFilterChanged(){

        }
    </script>
</@js>
