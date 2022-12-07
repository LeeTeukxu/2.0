<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar">
        <a class="mini-button" iconCls="icon-add" onclick="onAdd">增加</a>
        <a class="mini-button" iconCls="icon-add" onclick="addImage">新增图片</a>
        <span class="separator"></span>
        <a class="mini-button" iconCls="icon-save" onclick="onSave" id="CmdSave">保存</a>
        <a class="mini-button" iconCls="icon-remove" onclick="onRemove" id="CmdDelete">删除</a>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%" allowcellselect="true" allowcelledit="true"
             oncellbeginedit="beforeEdit" allowCellWrap="true" multiselect="true"
             autoload="true" onselect="onSelectRow"  ondrawcell="onDraw" url="/addSingleMemo/getData?SubID=${ID}">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="image" headerAlign="center" align="center">图片</div>
                <div field="memo" width="300" align="center" headerAlign="center">
                    进度信息
                    <div property="editor" class="mini-textarea" style="width:100%;height:60px"></div>
                </div>
                <div field="createManName" width="60" align="center" headerAlign="center">创建人</div>
                <div field="createTime" width="120" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" align="center"
                     headerAlign="center">创建日期
                </div>
                <div field="updateManName" width="60" align="center" headerAlign="center">修改人</div>
                <div field="updateTime" width="120" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" align="center"
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
            <input class="mini-hidden" name="pid" value="${ID}"/>
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
        mini.parse();
        var grid = mini.get('grid1');
        var saveUrl = '';
        var imageSaveUrl='';
        var removeUrl = '/casesOutSource/removeMemo';
        var now = mini.parseDate('${Now}', 'yyyy-MM-dd 23:59:59');

        function setConfig(sUrl) {
            saveUrl = sUrl;
        }
        function setSaveImageUrl(sUrl){
            imageSaveUrl=sUrl;
        }
        function showImageWindow() {
            var win = mini.get('imgWindow');
            $('#myPicture').attr('src', "");
            if (win.getVisible() == false) win.show();
        }
        function setData(rows) {
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                delete row.edit;
                var createTime = mini.parseDate(row["createTime"].toString(), 'yyyy-MM-dd');
                var free = ((now.getTime() - createTime.getTime()) / (1000 * 60 * 60 * 24));
                if (free > 3) row.Edit = 1; else row.Edit = 0;
            }
            grid.setData(rows);
        }

        function onAdd() {
            grid.addRow({
                id: guid(), pid: '${ID}', edit: 0, createMan: '${UserID}', createManName: '${UserName}', createTime: new
                Date()
            });
        }

        function onSave() {
            var rows = grid.getData();
            var rs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = mini.clone(rows[i]);
                delete row._id;
                rs.push(row);
            }
            $.post(saveUrl, {Data: mini.encode(rs)}, function (r) {
                if (r.success) {
                    mini.alert('保存成功。', '系统提示', function () {
                        grid.accept();
                        CloseOwnerWindow('ok');
                    });
                } else {
                    var msg = r.message || "保存失败";
                    mini.alert(msg);
                }
            });
        }

        function beforeEdit(e) {
            var field = e.field;
            var record = e.record;
            if (field == "memo") {
                var memo = record["memo"];
                if (memo) {
                    var edit = parseInt(record["Edit"] || 0);
                    e.cancel = !(edit == 0);
                }
                var isImage = parseInt(record["imageData"]);
                if (isImage == 1) e.cancel = true;
            } else {
                e.cancel = false;
            }
        }

        function onRemove() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var fu = function () {
                    var c=[];
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        c.push(row["id"]);
                    }
                    $.post(removeUrl, {SubID:'${ID}',IDS:c.join(',')}, function (r) {
                        if (r.success) {
                            mini.alert('选择的记录删除成功!');
                            grid.reload();
                        } else {
                            var msg = r.message || "删除记录失败";
                            mini.alert(msg);
                        }
                    });
                };
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];

                    var createTime = mini.parseDate(row["createTime"].toString(), 'yyyy-MM-dd');
                    var free = ((now.getTime() - createTime.getTime()) / (1000 * 60 * 60 * 24));
                    if (free > 3) row.Edit = 1; else row.Edit = 0;
                    var edit = parseInt(row["Edit"] || 0);
                    if (edit == 1) {
                        mini.alert('不允许删除。');
                        return;
                    }
                }
                mini.confirm('是否确认删除选择的记录。', '删除提示', function (r) {
                    if (r == "ok") fu();
                });
            } else mini.alert('请选择要删除的记录!');
        }

        function onSelectRow(e) {
            var record = e.record;
            var memo = record["memo"];
            var cmdSave = mini.get('CmdSave');
            var cmdDelete = mini.get('CmdDelete');
            if (memo) {
                var createTime = record["createTime"];
                var edit = parseInt(record["Edit"] || 0);
                if (createTime) {
                    createTime = mini.parseDate(createTime.toString(), 'yyyy-MM-dd');
                    var free = (now.getTime() - createTime.getTime()) / (1000 * 60 * 60 * 24);
                    if (free > 3) edit = 1; else edit = 0;
                }
                if (edit == 0) {
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

        function guid() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
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
            if(!obj.id) obj.id=guid();
            if (!obj.imageData || obj.imageData.length < 200) {
                mini.alert('图片数据为空，不能进行保存!');
                return;
            }
            var iid = mini.loading('正在保存.......', '保存图片');
            //var url = '/work/addMemo/saveImage';
            var url=imageSaveUrl;
            $.post(url, {Data: mini.encode(obj)}, function (result) {
                mini.hideMessageBox(iid);
                if (result.success) {
                    mini.alert('保存成功!', '系统提示', function () {
                        var win = mini.get('imgWindow');
                        if (win) win.hide();
                        CloseOwnerWindow('ok');
                    });
                }
            })
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