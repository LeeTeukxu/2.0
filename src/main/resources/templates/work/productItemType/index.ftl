<#include "/shared/layout.ftl">
<@js>
    <script type="text/javascript" src="/js/common/excelExportOther.js"></script>
</@js>
<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<div class="mini-layout" style="width:100%;height:100%">
    <div region="center" showheader="false">
        <div class="mini-toolbar">
            <table style="width:100%">
                <tr>
                    <td style="width:95%">
                        <a class="mini-button" iconcls="icon-add" id="ProductItemTypeBrowse_Add"
                           onclick="addNew">增加</a>
                        <a class="mini-button" iconcls="icon-edit" id="ProductItemTypeBrowse_Edit"
                           onclick="editOne">编辑</a>
                        <a class="mini-button" iconcls="icon-save" id="ProductItemTypeBrowse_Save"
                           onclick="saveAll">保存</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconcls="icon-zoomin" id="ProductItemTypeBrowse_Browse"
                           onclick="browseOne">浏览</a>
                        <a class="mini-button" iconcls="icon-remove" id="ProductItemTypeBrowse_Remove"
                           onclick="deleteOne">删除</a>
                        <span class="separator"></span>
                        <a class="mini-button" plain="true" iconcls="icon-folderopen" id="ProductItemTypeBrowse_Import"
                           onclick="doImport">导产品类型及成本配置</a>
                        <span class="separator"></span>
                        <a class="mini-menubutton"  menu="#popupMenu1" style="color:green;" plain="true">下载附件</a>
                        <ul class="mini-menu" id="popupMenu1" style="display:none">
                            <li class="mini-menu" iconCls="icon-download" plain="true"
                                id="ProductItemTypeBrowse_Download" onclick="download('blank')">下载空白文档 </li>
                            <li class="mini-menu" iconCls="icon-download" plain="true"
                                id="ProductItemTypeBrowse_Download" onclick="download('standard')">下载标准文档</li>
                            <a class="mini-menu ProductItemTypeBrowse_Download" iconCls="icon-download"
                               onclick="download('normal')">下载学习文档</a>
                        </ul>
                    </td>
                    <td style="white-space:nowrap;">
                        <input class="mini-textbox" style="width:250px" emptyText="产品名称" id="queryText"/>
                        <a class="mini-button mini-button-success"  onclick="doQuery">模糊查询</a>
                    </td>
                </tr>
            </table>
        </div>
        <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
            <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%"
                 url="/work/productItemType/getData" autoload="true" sortfield="sn" sortorder="asc"
                 pagesize="20" allowCellEdit="true" allowCellSelect="true" ondrawcell="onDraw">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div type="checkcolumn"></div>
                    <div field="sn" width="80" headeralign="center" allowsort="true" align="center">产品编号</div>
                    <div field="name" width="200" headeralign="center" allowsort="true" align="center">产品名称
                        <input property="editor" class="mini-textbox"/>
                    </div>
                    <div field="type" width="80" headeralign="center" allowsort="true" align="center">产品分类
                        <input property="editor" class="mini-textbox"/>  
                    </div>
                    <div field="cost" width="100" headeralign="center" allowsort="true" align="center">产品成本
                        <input property="editor" class="mini-spinner" maxValue="99999999999" minValue="0"/>
                    </div>
                    <div field="price" width="100" headeralign="center" allowsort="true" align="center">产品报价
                        <input property="editor" class="mini-spinner" maxValue="99999999999" minValue="0"/>
                    </div>
                    <div field="maxDays" width="100" headeralign="center" allowsort="true" align="center">产品处理周期(天)
                        <input property="editor" class="mini-spinner" maxValue="99999999999" minValue="0"/>
                    </div>
                    <div field="workDays" width="80" headeralign="center" allowsort="true" align="center">工作量
                        <input property="editor" class="mini-spinner" maxValue="99999999999" minValue="0"/>
                    </div>
                    <div field="blankFile" width="100" headeralign="center" align="center">空白文档</div>
                    <div field="standardFile" width="100" headeralign="center" align="center">标准文档</div>
                    <div field="normalFile" width="100" headeralign="center" align="center">学习文档</div>
                    <div field="required" width="150" headeralign="center" allowsort="true" align="center">业务交单要求
                        <input property="editor" class="mini-textbox"/>
                    </div>
                    <div field="memo" width="140" headeralign="center" allowsort="true" align="center">备注
                        <input property="editor" class="mini-textarea"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="editWindow1" class="mini-window" title="产品类型及成本配置" showmodal="true"
     allowresize="true" allowdrag="true" style="width:900px;">
    <div id="editform1" class="form">
        <table style="width:100%; height:100%" class="layui-table">
            <tr>
                <td>产品名称</td>
                <td>
                    <input class="mini-textbox" name="name"  required="true"  style="width:100%"/>
                    <input class="mini-hidden" name="id" />
                    <input class="mini-hidden" name="fid" />
                </td>
                <td>业务交单要求：</td>
                <td colspan="3">
                    <textarea class="mini-textbox" name="required" required="true" style="width: 100%"></textarea>
                </td>
            </tr>
            <tr>
                <td>产品编号</td>
                <td>
                    <input class="mini-spinner" name="sn" style="width:100%" required="true"/>
                </td>
                <td>产品分类</td>
                <td>
                    <input class="mini-textbox" name="type" style="width:100%" required="true"/>
                </td>
            </tr>
            <tr>
                <td>产品成本</td>
                <td>
                    <input class="mini-spinner" name="cost"  minValue="-999999" maxValue="10000000" value="0" required="true"
                           style="width:100%"/>
                </td>
                <td>产品报价</td>
                <td>
                    <input class="mini-spinner" name="price"   minValue="-999999" maxValue="10000000"  value="0" style="width:100%"
                           required="true"/>
                </td>
            </tr>
            <tr>
                <td>业务处理期限</td>
                <td>
                    <input class="mini-spinner" name="maxDays"   minValue="1" maxValue="100"  value="0" style="width:100%"
                           required="true"/>
                </td>
                <td>工作量</td>
                <td>
                    <input class="mini-spinner" name="workDays"   minValue="1" maxValue="100"  value="0"
                           style="width:100%" required="true"/>
                </td>
            </tr>
            <tr>
                <td>备注：</td>
                <td colspan="3">
                    <textarea class="mini-textarea" name="memo" style="width: 100%;height:60px"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="8" style="text-align:center">
                    <button class="mini-button" style="width:120px" id="Cmd_Save" iconcls="icon-save"
                            onclick="saveContract">&nbsp;保存信息
                    </button>

                    <button class="mini-button" style="width:120px;margin-left:180px" id="CancelForm1"
                            iconCls="icon-no"
                            onclick="closeMe">关闭退出
                    </button>
                </td>
            </tr>
        </table>
    </div>
</div>
    <form method="post" action="/work/clientInfo/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <script type="text/javascript" src="/js/common/ImportManage.js"></script>
<script type="text/javascript">
    mini.parse();
    var txtQuery = mini.get('queryText');
    var detailForm = null;
    var form = new mini.Form("#editform1");
    var editWindow = mini.get("editWindow1");
    var cmdAdd = mini.get('#ProductItemTypeBrowse_Add');
    var grid = mini.get('datagrid1');
    var grid2 = mini.get('grid2');
    $(function () {
        detailForm = document.getElementById("detailForm");
    })
    function deleteOne() {
        var row = grid.getSelected();
        if (row) {
            var rows=grid.getChanges();
            if(rows.length>0){
                mini.alert('先保存记录后再进行删除操作!');
                return ;
            }
            var id = parseInt(row["id"] || 0);
            if (id > 0) {
                var url = '/work/productItemType/remove';
                mini.confirm('确认要删除选择的记录?', '删除提示', function (action) {
                    if (action == 'ok') {
                        var msgId = mini.loading('正在删除........');
                        $.post(url, {id: id}, function (result) {
                            mini.hideMessageBox(msgId);
                            if (result.success) {
                                mini.alert('选择的信息已删除!');
                                grid.reload();
                            } else {
                                mini.alert(result.message || "删除失败，请稍候重试!");
                            }
                        });
                    }
                });
            }
        } else mini.alert('请选择要删除的记录!');
    }
    function doQuery() {
        //备注/流水号/业备数量/客户/商务人员
        var txt = txtQuery.getValue();
        var cs = [];
        var arg = {};
        if (txt) {
            arg.Name=txt;
        }
        grid.load(arg);
    }
    function addNew() {
        form.reset();
        form.setEnabled(true);
        mini.get("Cmd_Save").show();
        editWindow.show();
    }
    function editOne() {
        var row = grid.getSelected();
        if (row != null) {
            form.reset();
            row.name=$.trim(row.name);
            form.setData(row);
            mini.get('Cmd_Save').show();
            editWindow.show();
        } else mini.alert('请选择要编辑的记录!');
    }
    function browseOne() {
        var row = grid.getSelected();
        if (row != null) {
            form.reset();
            form.setEnabled(false);
            form.setData(row);
            var clientCon = mini.get('ClientID');
            mini.get('Cmd_Save').hide();
            if (clientCon) clientCon.setText(row["ClientName"]);
            editWindow.show();
        } else mini.alert('请选择要浏览的记录!');
    }
    function saveContract() {
        form.validate();
        if (form.isValid() == false) {
            mini.alert('数据录入不完整，保存操作被中止!');
            return;
        }
        mini.confirm('确认要保存信息', '保存提示', function (action) {
            if (action == 'ok') {
                var data = form.getData();
                var url = '/work/productItemType/save';
                $.post(url, {Data: mini.encode(data)}, function (result) {
                    if (result.success) {
                        mini.alert('信息保存成功!', '系统提示', function () {
                            grid.reload();
                            closeMe();
                        });
                    } else {
                        mini.alert(result.message || "保存失败，请稍候重试!");
                    }
                });
            }
        });
    }

    function closeMe() {
        editWindow.hide();
    }

    function doImport() {
        var con ={};
        con['Title'] = '产品类型及成本配置导入';
        con['TemplateUrl'] = '/static/template/产品类型及成本配置导入模板.xls';
        con['openUrl'] = '/work/productItemType/ImportProductItemType?Code=productItemType&FileName='+encodeURIComponent('产品类型及成本配置导入模板.xls');
        con['dataGrid'] = grid;
        con['mode'] = 'data';
        ImportManage(con);
    }
    function saveAll(){
        var rows=grid.getChanges();
        if(rows.length>0){
            var url='/work/productItemType/saveAll';
            $.post(url,{Data:mini.encode(rows)},function(result){
                if(result.success){
                    mini.alert('保存成功!');
                    grid.reload();
                } else mini.alert(result.message || "保存失败，请稍候重试!");
            })
        }
    }
    function onDraw(e){
        var mode='Add';
        if(hasFuns.indexOf("Save")==-1){
            mode="Browse";
        }
        var record=e.record;
        var field=e.field || "";
        if(field=="sn"){
            var val=e.value ||"";
            if(val==0 || val=="0") e.cellHtml="";
        }
        else if(field=="blankFile"){
            var blankFile=record["blankFile"];
            var uText="上传";
            if(blankFile){
                uText=record["blankFileName"];
            }
            var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                ' onclick="uploadRow(' + "'" + record._id + "','"+'blank'+ "'," + "'" + mode + "')" + '">' +
                '&nbsp;' + uText + '&nbsp;</a>';
            e.cellHtml = x;
        }
        else if(field=="standardFile"){
            var standardFile=record["standardFile"];
            var uText="上传";
            if(standardFile){
                uText=record["standardFileName"];
            }
            var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                ' onclick="uploadRow(' + "'" + record._id + "','"+'standard'+ "'," + "'" + mode + "')" + '">' +
                '&nbsp;' + uText + '&nbsp;</a>';
            e.cellHtml = x;
        }
        else if(field=="normalFile"){
            var normalFile=record["normalFile"];
            var uText="上传";
            if(normalFile){
                uText=record["normalFileName"];
            }
            var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                ' onclick="uploadRow(' + "'" + record._id + "','"+'normal'+ "'," + "'" + mode + "')" + '">' +
                '&nbsp;' + uText + '&nbsp;</a>';
            e.cellHtml = x;
        }
    }
    function uploadRow(id, type, mode) {
        var row = grid.getRowByUID(id);
        if (row) {
            var subId = row["fid"];
            var url = '/work/productItemType/getSubFiles';
            $.getJSON(url, {FID: subId, Type: type}, function (result) {
                if (result.success) {
                    var att = result.data || "";
                    var attId=att.split(",") || [];
                    doUpload(subId,attId,type,row,mode);
                } else mini.alert(result.message || "加载数据失败，请稍候重试!");
            });
        }
    }

    function doUpload(subId, ids, type, row, mode) {
        var title = '';
        if (type == "blank") title = "空白文档";
        if (type == "standard") title = "标准文档";
        if (type == "normal") title = "学习资料";
        var showHis = 0;
        var attId = "";
        if (ids.length > 0) attId = ids.join(",");
        mini.open({
            url: '/attachment/addFile?IDS=' + attId + '&Mode=' + mode + '&ShowHis=' +
                showHis + '&FileType=' + type + '&SubID=' + subId,
            width: 900,
            height: 500,
            title: title,
            onload: function () {
                var iframe = this.getIFrameEl();
                iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                    var attId = data.AttID;
                    var url = '/work/productItemType/saveSubFiles';
                    var arg = {FID: subId, AttID: attId, Type: type};
                    $.post(url, arg, function (result) {
                        if (result.success) {
                            var field = "ZLFiles";
                            if (type == "Tech") field = "TechFiles";
                            if (type == "Accept") field = "AcceptTechFiles";
                            if (type == "Aud") field = "AuditTechFiles";
                            var obj = {};
                            obj[field] = attId;
                            var now = row[field];
                            if (!now) grid.updateRow(row, obj);
                        }
                    })
                });
                iframe.contentWindow.addEvent('eachFileRemoved', function (data) {
                    var casesID = row["fid"];
                    if (casesID) {
                        var url = '/work/productItemType/removeSubFiles';
                        $.post(url, {AttID: data.ATTID}, function (result) {
                            if (result.success == false) {
                                mini.alert('删除文件信息失败，请联系管理员解决问题。');
                            } else {
                                doReload(grid);
                            }
                        })
                    }
                });
            },
            ondestroy: function () {
                grid.reload();
            }
        });
    }

    function download(type) {
        var rows = grid.getSelecteds();
        var code = null;
        var name = null;
        if (rows.length == 1) {
            var row = rows[0];
            if (row) {
                var oo=getCodes(type,row);
                code = oo.id;
                if(code.indexOf(",")>-1){
                    name="文件打包下载.zip";
                } else name = oo.code[0]+'.zip';
            }
        } else {
            var codes = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var code = getCodes(type,row).id.split(",");
                for(var n=0;n<code.length;n++) {
                    codes.push(code[n]);
                }
            }
            code = codes.join(',');
            name = codes.length + '个文件打包下载.zip';
        }
        function getCodes(type,row){
            var ids=[];
            var names=[];
            if(type=="blank"){
                ids=(row["blankFile"] ||"");
                names=(row["blankFileName"] || "");
            }
            else if(type=="standard"){
                ids=(row["standardFile"] ||"");
                names=(row["standardFileName"] || "");
            }
            else if(type=="normal"){
                ids=(row["normalFile"] ||"");
                names=(row["normalFileName"] || "");
            }
            return {id:ids,code:names};
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
</script>
</@layout>
