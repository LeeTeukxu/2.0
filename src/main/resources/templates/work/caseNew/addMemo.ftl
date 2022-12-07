<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar">
        <a class="mini-button" iconCls="icon-add" onclick="onAdd">增加</a>
        <a class="mini-button" iconCls="icon-add" onclick="addImage">新增图片</a>
        <span class="separator"></span>
        <a class="mini-button" iconCls="icon-save" onclick="onSave" id="CmdSave">保存</a>
        <a class="mini-button" iconCls="icon-remove" onclick="onRemove" id="CmdDelete">删除</a>
        <#if BatchMode==true>
            <span style="color:red">(请注意:当前处于批量添加模式，备注记录保存成功后，编辑窗口会马上关闭。)</span>
        </#if>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%" allowcellselect="true" allowcelledit="true"
             url="/cases/getMemoData?CasesID=${CasesID}" oncellbeginedit="beforeEdit" allowCellWrap="true"
             autoload="true" onselect="onSelectRow"   ondrawcell="onDraw">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div field="image" headerAlign="center" align="center">图片</div>
                <div field="Memo" width="300" align="center" headerAlign="center">
                    进度信息
                    <div property="editor" class="mini-textarea" style="width:100%;height:60px"></div>
                </div>
                <div field="CreateManName" width="60" align="center" headerAlign="center">创建人</div>
                <div field="CreateTime" width="100" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" align="center"
                     headerAlign="center">创建日期
                </div>
                <div field="UpdateManName" width="60" align="center" headerAlign="center">修改人</div>
                <div field="UpdateTime" width="100" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" align="center"
                     headerAlign="center">修改日期
                </div>
            </div>
        </div>
    </div>
    <div style="display: none" class="mini-window" width="600" height="480" id="imgWindow" showFooter="true"
         showToolbar="true" title="粘贴至剪贴板图片">
        <div property="toolbar" style="padding:5px;">
            图片描述: <input class="mini-textbox" style="width:90%" name="memo"/>
            <input class="mini-hidden" name="imageData"/>
            <input class="mini-hidden" name="pid" value="${CasesID}"/>
            <input class="mini-hidden" name="id"/>
        </div>
        <div class="mini-fit">
            <img src="" id="myPicture" style="width:100%;height:100%" alt="复制图片并用Ctrl+V进行粘剪"/>
        </div>
        <div property="footer" style="text-align:right;padding:5px;padding-right:15px;">
            <button class="mini-button mini-button-success" onclick="saveImage()">确认保存</button>
            &nbsp;&nbsp;&nbsp;
            <button class="mini-button mini-button-danger">消取关闭</button>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        var casesId = "${CasesID}";
        mini.parse();
        var grid = mini.get('grid1');

        function onAdd() {
            grid.addRow({CASESID: casesId});
        }

        function onSave() {
            var rows = grid.getChanges();
            var rs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = mini.clone(rows[i]);
                delete row._id;
                delete row._uid;
                delete row._state;
                rs.push(row);
            }
            var url = '/cases/saveMemo';
            $.post(url, {Data: mini.encode(rs)}, function (r) {
                if (r.success) {
                    mini.alert('保存成功。');
                    <#if BatchMode=true>
                            CloseOwnerWindow('ok');
                        <#else>
                            grid.reload();
                    </#if>
                } else {
                    var msg = r.message || "保存失败";
                    mini.alert(msg);
                }
            });
        }
        function onDraw(e) {
            var row = e.record;
            var field = e.field;
            if (field == "image") {
                var isImage = parseInt(row["imageData"] || 0);
                if (isImage == 1) {
                    var pId = row["id"];
                    e.cellHtml += '<a  style="text-decoration: underline"  ' +
                        'href="javascript:viewImage(\'' + pId + '\')">查看</a>';
                    e.cellHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<a  style="text-decoration: underline"  ' +
                        'href="javascript:addImage(\'' + pId + '\')">添加</a>';
                }
            }
        }

        function beforeEdit(e) {
            var field = e.field;
            var record = e.record;
            if (field == "Memo") {
                var memo = record["Memo"];
                if (memo) {
                    var edit = parseInt(record["Edit"] || 0);
                    e.cancel = !(edit == 1);
                }
                var isImage = parseInt(record["ImageData"]);
                if (isImage == 1) e.cancel = true;
            } else {
                e.cancel = false;
            }
        }

        function onRemove() {
            var changes = grid.getChanges();
            if (changes.length > 0) {
                alert('删除数据之前，请先保存更改。');
                return;
            }
            var row = grid.getSelected();
            if (row) {
                var fu = function () {
                    var id = row["ID"];
                    var url = '/cases/removeMemo';
                    $.post(url, {CasesID: row["CASESID"], ID: id}, function (r) {
                        if (r.success) {
                            mini.alert('选择记录删除成功!');
                            grid.reload();
                        } else {
                            var msg = r.message || "删除失败，请稍候重试。";
                            mini.alert(msg);
                        }
                    });
                };
                var edit = parseInt(row["Edit"] || 0);
                if (edit == 0) {
                    mini.alert('不允许删除。');
                    return;
                }
                mini.confirm('是否确认删除选择的记录。', '删除提示', function (r) {
                    if (r == "ok") fu();
                });
            }
        }

        function onSelectRow(e) {
            var record = e.record;
            var memo = record["Memo"];
            var cmdSave = mini.get('CmdSave');
            var cmdDelete = mini.get('CmdDelete');
            if (memo) {
                var edit = parseInt(record["Edit"] || 0);
                if (edit) {
                    cmdDelete.show();
                    cmdSave.show();
                } else {
                    cmdDelete.hide();
                    cmdSave.hide();
                }
            } else {
                cmdDelete.show();
                cmdSave.show();
            }
        }

        $(function () {
            $(document.body).on('paste', function (e) {
                if (checkWeb()) {
                    var clip = (e.clipboardData || window.clipboardData || window.event.clipboardData);
                    if (clip) {
                        var items = clip.items;
                        var targetItem = null;
                        for (var i = 0; i < items.length; i++) {
                            var item = items[i];
                            var kind = item.kind;
                            var type = item.type;
                            if (kind == "file") {
                                if (type.startsWith("image")) {
                                    targetItem = item;
                                    break;
                                }
                            }
                        }
                        if (targetItem == null) {
                            return;
                        }
                        var data = targetItem.getAsFile().slice();
                        var isImg = (data && 1) || -1;
                        if (isImg) {
                            var fileReader = new FileReader();
                            fileReader.onload = function (event) {
                                var bStr = event.target.result;
                                if (bStr) {
                                    showImageWindow();
                                    $('#myPicture').attr('src', bStr);
                                    mini.getbyName('imageData').setValue(bStr);
                                }
                            }
                            fileReader.readAsDataURL(data);
                        }
                    }
                } else mini.showTips({content: '当前浏览器由于版本太低，不支持【Html5】特性，无法使用粘贴图片功能。'});
            });
        })

        function checkWeb() {
            var pp = window.FileReader;
            if (pp) return true; else return false;
        }

        function saveImage() {
            var obj = {};
            obj.imageData = mini.getbyName('imageData').getValue() || "";
            obj.memo = mini.getbyName('memo').getValue();
            obj.pid = mini.getbyName("pid").getValue();
            obj.id=mini.getbyName('id').getValue();
            if (!obj.imageData || obj.imageData.length < 200) {
                mini.alert('图片数据为空，不能进行保存!');
                return;
            }
            var iid = mini.loading('正在保存.......', '保存图片');
            var url = '/addSingleMemo/saveImage';
            $.post(url, {Data: mini.encode(obj)}, function (result) {
                mini.hideMessageBox(iid);
                if (result.success) {
                    mini.alert('保存成功!', '系统提示', function () {
                        var win = mini.get('imgWindow');
                        if (win) win.hide();
                        <#if BatchMode==false>
                        grid.reload();
                        <#else>
                        CloseOwnerWindow('ok');
                        </#if>
                    });
                }
            })
        }

        function onDraw(e) {
            var row = e.record;
            var field = e.field;
            if (field == "image") {
                var isImage = parseInt(row["ImageData"] || 0);
                if (isImage == 1) {
                    var pId = row["SubID"];
                    e.cellHtml += '<a  style="text-decoration: underline"  ' +
                        'href="javascript:viewImage(\'' + pId + '\')">查看</a>';
                    e.cellHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<a  style="text-decoration: underline"  ' +
                        'href="javascript:addImage(\'' + pId + '\')">添加</a>';
                }
            }
        }

        function viewImage(mId) {
            var iid = mini.loading('正在加载图片数据.........');
            var url = '/work/addMemo/getImages';
            $.getJSON(url, {MID: mId}, function (result) {
                mini.hideMessageBox(iid);
                var isOK = parseInt(result.status);
                if (isOK == 1) {
                    window.parent.showImages(mini.encode(result));
                } else {
                    var msg = result.message || "无法加载通知书附件。";
                    layer.alert(msg);
                }
            });
        }
        function showImageWindow() {
            var win = mini.get('imgWindow');
            $('#myPicture').attr('src', "");
            if (win.getVisible() == false) win.show();
        }
        function addImage(mId) {
            if (checkWeb()) {
                showImageWindow();
                if (mId) {
                    if (typeof (mId) == "string") {
                        mini.getbyName('id').setValue(mId);
                    }
                }
            } else mini.alert('当前浏览器由于版本太低，不支持【Html5】特性，无法使用粘贴图片功能。');
        }
    </script>
</@js>