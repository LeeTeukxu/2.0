<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
		<style>
            input {
                height: 30px;
                line-height: 30px;
                width: 100%
            }

            .title {
                width: '10%';
                text-align: center;
            }

            td {
                cursor: pointer;
            }

            .clabel {
                font-size: 10px;
                font-family: 宋体;
                width: 80px
            }
        </style>
    	<link rel="stylesheet" href="http//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
		<script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
		<script src="http://apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
        <table style="width:100%;height:100%">
            <tr>
                <td style="width:18%;vertical-align: top;text-align: center;">
                    <div style="font-size:18px;font-family:'黑体', text-align:center;width:100%;background-color: #01AAED;
                    height: 40px;line-height: 40px;">业务表单模版列表
                    </div>
                    <div style="width:100%;height: 200px;border-bottom: 1px solid black;">
                        <div class="mini-datagrid" style="width:100%;height:100%" showPager="false"
                             url="/systems/formDesign/getAll" autoload="true" onrowclick="onChange" id="grid2">
                            <div property="columns">
                                <div type="checkcolumn"></div>
                                <div field="code" headerAlign="center" width="100">编号</div>
                                <div field="name" headerAlign="center" width="200">表单名称</div>
                            </div>
                        </div>
                    </div>
                    <table class="designTable" id="sourceTable" style="width:100%;table-layout: fixed;">
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                <input labelField="true" label="文本字段" class="mini-textbox" style="width:90%"/>
                            </td>
                        </tr>
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                    <textarea labelField="true" label="多行文本字段" class="mini-textarea"
                                              style="width:90%;height: 30px;"></textarea>
                            </td>
                        </tr>
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                <input labelField="true" label="日期字段" class="mini-datepicker"
                                       style="width:90%"/>
                            </td>
                        </tr>
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                <input labelField="true" label="下拉框字段" class="mini-combobox" style="width:90%"/>
                            </td>
                        </tr>
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                <input labelField="true" label="下拉树字段" class="mini-treeselect" style="width:90%"/>
                            </td>
                        </tr>
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                <input labelField="true" label="多选字段" class="mini-checkboxlist"
                                       style="width:90%" data="[{id:1,text:'是'},{id:0,text:'否'}]"/>
                            </td>
                        </tr>
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                <input labelField="true" label="单选字段" class="mini-radiobuttonlist"
                                       style="width:90%" data="[{id:1,text:'是'},{id:0,text:'否'}]"/>
                            </td>
                        </tr>
                        <tr style="height:40px;">
                            <td style="text-align: center;border-bottom:1px #000000 solid;">
                                <input labelField="true" label="上传文件" class="mini-htmlfile" style="width:90%"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td style="border:1px solid black;width:1px"></td>
                <td style="width:59%;padding:5px">
                    <button type="button" class="layui-btn layui-btn-normal formDesign_Add" id="formDesign_Add" onclick="addTemp();
">新增模版
                    </button>
                    <button type="button" class="layui-btn layui-btn-normal formDesign_Save" id="formDesign_Save" onclick="saveTemp
                    ();">保存模版
                    </button>
                    <button type="button" class="layui-btn layui-btn-danger formDesign_Remove" id="formDesign_Remove">删除模版</button>
                    <button type="button" class="layui-btn formDesign_Save" onclick="mergeRow()">合并单元</button>
                    <button type="button" class="layui-btn formDesign_Save" onclick="unMergeRow()">拆分单元</button>
                    <button type="button" class="layui-btn formDesign_Save" onclick="swap()">两列互换</button>
                    <button type="button" class="layui-btn layui-btn-warm  formDesign_Save" onclick="clearCell()">清空单元</button>
                    <table id="sort" style="width:100%;height:92%;table-layout:fixed" cellspacing="2" cellpadding="2"
                           class="layui-table designTable">
                        <thead>
                        <tr>
                            <td style="width:50px">&nbsp</td>
                            <td style="width:46%" class="index">第一列</td>
                            <td style="width:46%" class="index">第二列</td>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </td>
                <td style="width:18%;vertical-align: top;text-align: center;overflow:hidden;">
                    <div class="mini-datagrid" style="height:80%;width:100%" showPager="false" id="grid1"
                         allowcelledit="true" allowcellselect="true">
                        <div property="columns">
                            <div field="field" align="center" headerAlign="center" width="80px">字段</div>
                            <div field="value" align="center" headerAlign="center">内容
                                <input property="editor" class="mini-textbox"/>
                            </div>
                        </div>
                    </div>
                    <button type="button" id="saveConfig" class="layui-btn" style="width:98%"
                            onclick="saveConfig()">保存设置并刷新
                    </button>
                </td>
            </tr>
        </table>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#grid1');
        var grid2 = mini.get('#grid2');
        var cmdConfig = $('#saveConfig');
        var curRow = null;
        var curCell = null;
        var curTemplate = null;
        var fixHelperModified = function (e, tr) {
            var $helper = tr.clone();
            return $helper;
        }
        $("#sort tbody").sortable({
            helper: fixHelperModified,
            containment: '#sort',
            revert: true,
            connectWith: '.designTable'
        }).disableSelection();

        function initTable() {
            var dom = $('#sort tbody');
            dom.empty();
            for (var i = 0; i < 15; i++) {
                dom.append('<tr><td class="index" style="width:50px">' + (i + 1) + '</td><td></td><td></td></tr>');
            }
            $('#sort tr').click(function (e) {
                var OX = null;
                var OP = $(event.srcElement)
                while (OP != null) {
                    var tagName = OP.prop('tagName');
                    if (tagName == "TR") {
                        curRow = OP;
                        var parent = OP.parent();
                        var ttName = parent.prop('tagName');
                        if (ttName == "THEAD") break;
                        var rows = OP.parent().children('tr');
                        for (var i = 0; i < rows.length; i++) {
                            var r = $(rows[i]);
                            r.css({'background-color': 'white'});
                        }
                        curRow.css({'background-color': 'yellow'});
                        break;
                    } else OP = OP.parent();
                }
            });
            $('#sort tbody td').click(function () {
                curCell = $(this);
                var label = $(this).children('label');
                if (label.length > 0) {
                    grid.show();
                    var config = new configObject(curCell);
                    grid.setData(config.getResult());
                    cmdConfig.show();
                } else {
                    grid.clearRows();
                    cmdConfig.hide();
                }
            });
            $('#sourceTable td').sortable();
            $('#sort td').droppable({
                accept: '#sourceTable td *',
                drop: function (event, ui) {
                    var cell = $(event.target);
                    var input = $(event.toElement || ui.draggable[0]);
                    var type = input.text();
                    cell.empty();
                    switch (type) {
                        case "日期字段": {
                            cell.append('<label class="clabel">&nbsp;业务字段：</label><input type="date" ' +
                                    'style="width:80%"/>');
                            break;
                        }
                        case "文本字段": {
                            cell.append('<label class="clabel">&nbsp;业务字段：</label><input type="text" style="width:80%"/>');
                            break;
                        }
                        case "下拉框字段": {
                            cell.append('<label class="clabel">&nbsp;业务字段：</label><select style="width:80%;height:30px"><option>否</option><option>是</option></select>');
                            break;
                        }
                        case "下拉树字段": {
                            cell.append('<label class="clabel">&nbsp;业务字段：</label><input type="tree" ' +
                                    'style="width:80%;height:30px" conType="tree"></input>');
                            break;
                        }
                        case "多行文本字段": {
                            cell.append('<label class="clabel">&nbsp;业务字段：</label><textarea style="width:80%;height:30px" />');
                            break;
                        }
                        case "多选字段": {
                            var xText =
                                    '<label class="clabel">&nbsp;业务字段：</label><span class="checkboxcontainer"><input ' +
                                    'style="width:30px;' +
                                    'height:15px;line-height:15px" ' +
                                    'type="checkbox" value="是" /><label>是</label>&nbsp;&nbsp;' +
                                    '<input style="width:30px;line-height:15px;height:15px" ' +
                                    'type="checkbox" value="否" /><label>否</label></span>';
                            cell.append(xText);
                            break;
                        }
                        case "单选字段": {
                            var xText =
                                    '<label class="clabel">&nbsp;业务字段：</label><span class="radiocontainer"><input ' +
                                    'style="width:30px;' +
                                    'height:15px;line-height:15px" ' +
                                    'type="radio" value="是" /><label>是</label>&nbsp;&nbsp;' +
                                    '<input style="width:30px;line-height:15px;height:15px" ' +
                                    'type="radio" value="否" /><label>否</label></span>';
                            cell.append(xText);
                            break;
                        }
                        case "上传文件": {
                            cell.append('<label class="clabel">&nbsp;业务字段：</label><input type="file" style="width:80%;' +
                                    'height:30px" />');
                            break;
                        }
                    }
                    var row = cell.parent();
                    var rowIndex = row.prop('rowIndex');
                    var colIndex = cell.prop('cellIndex');
                    var gx = {
                        label: '业务字段',
                        width: '80%',
                        height: '0',
                        url: '',
                        value: '',
                        data: '',
                        required: true,
                        name: 'Name_' + rowIndex + '_' + colIndex,
                        watchConfig: '是',
                        saveAlone: '否',
                        autoCreate:'否'
                    };
                    $(cell).attr({'isEmpty': false, 'config': JSON.stringify(gx)});
                }
            });
        }

        $(function () {
            initTable();
            cmdConfig.hide();
        });

        function onChange(e) {
            var row = e.record;
            var text = row["allText"];
            if (text) {
                var ds = mini.decode(text);
                var f = new formFieldObject();
                f.load(ds);
            }
            curTemplate = row;
        }

        function addTemp() {
            mini.confirm('确认要新建模版?', '提示', function (action) {
                if (action == 'ok') {
                    grid2.deselect(curTemplate, false);
                    curTemplate = undefined;
                    initTable();
                }
            });
        }

        function configObject(cell) {
            var g = this;
            var configObj = [
                {field: '标签名称', name: 'label', value: ''},
                {field: '显示长度', name: 'width', value: '80%'},
                {field: '显示高度', name: 'height', value: '0'},
                {field: '默认值', name: 'value', value: ''},
                {field: '可供选择值', name: 'data', value: ''},
                {field: '是否必填', name: 'required', value: '是'},
                {field: '监视修改', name: 'watchConfig', value: '否'},
                {field: '独立保存', name: 'saveAlone', value: '否'},
                {field: '自动生成', name: 'autoCreate', value: '否'},
                {field: '字段名', name: 'name', value: ''},
                {field: 'Url', name: 'url', value: ''}
            ];
            g.obb = {};
            this.setValue = function (field, value) {
                g.obb[field] = value;
            }
            this.getResult = function () {
                var dx = mini.decode($(cell).attr('config') || "{}");
                g.obb = dx;
                var gx = mini.clone(configObj);
                for (var i = 0; i < gx.length; i++) {
                    var gv = gx[i];
                    var fieldName = gv.name;
                    if (g.obb[fieldName]) {
                        gv.value = g.obb[fieldName];
                    }
                }
                return gx;
            }
        }

        function mergeRow() {
            if (curRow) {
                var cells = curRow.children('td');
                if (cells.length == 3) {
                    $(cells[1]).prop('colspan', 2);
                    $(cells[2]).css({display: 'none'});
                }
            }
        }

        function unMergeRow() {
            if (curRow) {
                var cells = curRow.children('td');
                if (cells.length == 3) {
                    $(cells[1]).prop('colspan', 1);
                    $(cells[2]).css({display: ''});
                }
            }
        }

        function swap() {
            if (curRow) {
                var cells = curRow.children('td');
                if (cells.length == 3) {
                    var ss = cells[1].innerHTML;
                    cells[1].innerHTML = cells[2].innerHTML;
                    cells[2].innerHTML = ss;
                }
            }
        }

        function saveConfig() {
            if (curCell && curCell.length > 0) {
                var dConfig = grid.getData();
                var res = {};
                for (var i = 0; i < dConfig.length; i++) {
                    var config = dConfig[i];
                    var field = config.name;
                    var value = config.value;
                    res[field] = value;
                }
                curCell.attr('config', JSON.stringify(res));
                var form = new formFieldObject();
                form.refresh(curCell, res);
            }
        }

        function clearCell() {
            if (curCell) {
                curCell.empty();
                curCell.removeAttr('isEmpty').removeAttr('config');
            }
        }

        function saveTemp() {
            var html =
                    '<table style="width:100%;height:100%" id="xForm">\n' +
                    '                <tr>\n' +
                    '                    <td>表单编号</td>\n' +
                    '                    <td colspan="3"><input class="mini-hidden" name="id" />' +
                    '                        <input class="mini-textbox" style="width:100%;height:30px" name="code" ' +
                    'required="true"/>\n' +
                    '                    </td>\n' +
                    '                </tr>\n' +
                    '                <tr>\n' +
                    '                    <td>表单名称</td>\n' +
                    '                    <td  colspan="3">\n' +
                    '                        <input class="mini-textbox" style="width:100%;height:30px" name="name" ' +
                    'required="true"/>\n' +
                    '                    </td>\n' +
                    '                </tr>\n' +
                    '</table>\n';
            mini.showMessageBox({
                html: html,
                width: 600,
                height: 200,
                buttons: ['ok', 'no'],
                title: '表单属性',
                callback: function (action) {
                    if (action == 'ok') {
                        var form = new mini.Form('#xForm');
                        form.validate();
                        if (form.isValid()) {
                            var data = form.getData();
                            var gx = new formFieldObject();
                            var cnfs = gx.getConfig();
                            if (cnfs.length > 0) {
                                var url = '/systems/formDesign/save';
                                data["AllText"] = mini.encode(cnfs);
                                $.post(url, data, function (result) {
                                    if (result.success) {
                                        mini.alert('模版信息保存成功!', '保存提示', function () {
                                            grid2.reload();
                                            initTable();
                                            if (curTemplate) {
                                                grid2.deselect(curTemplate);
                                                curTemplate = undefined;
                                            }
                                        });
                                    }
                                });
                            }

                        } else {
                            mini.alert('数据录入不完整，不能进行保存。');
                        }
                        mini.xyz = form.getData();
                    }
                }
            });
            mini.parse();
            var form = new mini.Form('#xForm');
            if (mini.xyz) {
                form.setData(mini.xyz);
            }
            if (curTemplate) {
                var dd = {name: curTemplate.name, code: curTemplate.code, id: curTemplate.id};
                form.setData(dd);
            }
        }

        function formFieldObject() {
            var table = $('#sort');

            function textboxField(cell) {
                var targetField = null;
                var instance = this;
                instance.cell = cell;
                this.contains = function () {
                    var cs = $(cell).find('input');
                    if (cs.length == 0) return false;
                    var inputField = $(cs[0]);
                    targetField = inputField;
                    var type = targetField.prop('type');
                    if (type != "text") return false;
                    var conType = targetField.attr('conType') || "text";
                    return conType == ("text");
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'text',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        width: cc.width,
                        required: cc.required,
                        watchConfig: cc.watchConfig || '否',
                        saveAlone: cc.saveAlone || '否',
                        autoCreate:cc.autoCreate || "否",
                        colspan: cell.prop('colspan')
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    var text = '<label class="clabel">&nbsp;' + cnf.label + '：</label><input type="text" ' +
                            'style="width:' + cnf.width + '" name="' + cnf.name + '"/>';
                    instance.cell.append(text);
                }
                this.acceptType = function () {
                    return "text";
                }
                this.applyConfig = function (cnf) {
                    if (targetField) {
                        var cell = instance.cell;
                        if (cnf.value) {
                            targetField.val(cnf.value);
                        }
                        if (cnf.label) {
                            var labCon = $(cell).find('label');
                            if (labCon) {
                                labCon.text(cnf.label + '：');
                            }
                        }
                    }
                }
            }

            function dateField(cell) {
                var targetField = null;
                var instance = this;
                instance.cell = cell;
                this.contains = function () {
                    var cs = $(cell).find('input');
                    if (cs.length == 0) return false;
                    var inputField = $(cs[0]);
                    targetField = inputField;
                    return inputField.prop('type') == ("date");
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'date',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        width: cc.width,
                        required: cc.required,
                        watchConfig: cc.watchConfig || '否',
                        saveAlone: cc.saveAlone || '否',
                        autoCreate:cc.autoCreate || "否",
                        colspan: cell.prop('colspan')
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    var text = '<label class="clabel">&nbsp;' + cnf.label + '：</label><input type="date" ' +
                            'style="width:' + cnf.width + '" name="' + cnf.name + '"/>';
                    instance.cell.append(text);
                }
                this.acceptType = function () {
                    return "date";
                }
                this.applyConfig = function (cnf) {
                    if (targetField) {
                        var cell = $(instance.cell);
                        if (cnf.value) {
                            targetField.val(cnf.value);
                        }
                        if (cnf.label) {
                            var labCon = cell.find('label');
                            if (labCon) {
                                labCon.text(cnf.label + '：');
                            }
                        }
                    }
                }
            }

            function textAreaField(cell) {
                var instance = this;
                instance.cell = cell;
                var targetField = null;
                this.contains = function () {
                    var cs = $(cell).find('textarea');
                    targetField = cs;
                    return cs.length > 0;
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'textarea',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        width: cc.width,
                        height: targetField.height(),
                        required: cc.required,
                        watchConfig: cc.watchConfig || '否',
                        saveAlone: cc.saveAlone || '否',
                        autoCreate:cc.autoCreate || "否",
                        colspan: cell.prop('colspan')
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    var text = $('<label class="clabel">&nbsp;' + cnf.label + '：</label><textarea  style="width:' + cnf.width + '" name="' + cnf.name + '"></textarea>');
                    if (cnf.height) text.height(cnf.height);
                    instance.cell.append(text);
                }
                this.acceptType = function () {
                    return "textarea";
                }
                this.applyConfig = function (cnf) {
                    if (targetField) {
                        var cell = $(instance.cell);
                        if (cnf.value) {
                            targetField.val(cnf.value);
                        }
                        if (cnf.label) {
                            var labCon = cell.find('label');
                            if (labCon) {
                                labCon.text(cnf.label + '：');
                            }
                        }
                        if (cnf.height) targetField.height(cnf.height + "px");
                    }
                }
            }

            function selectField(cell) {
                var targetField = null;
                var instance = this;
                instance.cell = cell;
                this.contains = function () {
                    var cs = $(cell).find('select');
                    targetField = cs;
                    return cs.length > 0;
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'select',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        required: cc.required,
                        width: cc.width,
                        data: cc.data || "是,否",
                        colspan: cell.prop('colspan'),
                        saveAlone: cc.saveAlone || '否',
                        watchConfig: cc.watchConfig || '否',
                        autoCreate:cc.autoCreate || "否",
                        url: cc.url
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    var tDom = $('<select name="' + cnf.name + '" style="width:' + cnf.width + ';' +
                            'height:30px"></select>');
                    var data = cnf.data || "是,否";
                    var datas = data.split(',');
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        tDom.append('<option>' + d + '</option>');
                    }
                    instance.cell.append('<label class="clabel">&nbsp;' + cnf.label + '：</label>').append(tDom);
                }
                this.acceptType = function () {
                    return "select";
                }
                this.applyConfig = function (cnf) {
                    if (targetField) {
                        if (cnf.label) {
                            var labCon = instance.cell.find('label');
                            if (labCon) {
                                labCon.text(cnf.label + '：');
                            }
                        }
                        if (cnf.data) {
                            var ds = cnf.data.toString().split(',');
                            if (ds.length > 0) {
                                targetField.empty();
                                for (var i = 0; i < ds.length; i++) {
                                    var d = ds[i];
                                    targetField.append('<option value="' + d + '">' + d + '</option>');
                                }
                            }
                        }
                        if (cnf.value) {
                            targetField.val(cnf.value);
                        }
                    }
                }
            }

            function treeField(cell) {
                var targetField = null;
                var instance = this;
                instance.cell = cell;
                this.contains = function () {
                    var cs = $(cell).find('input');
                    if (cs.length > 0) {
                        var conType = cs.attr('conType');
                        targetField = cs;
                        return conType == "tree";
                    } else return false;
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'tree',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        required: cc.required,
                        width: cc.width,
                        url: cc.url,
                        saveAlone: cc.saveAlone || '否',
                        watchConfig: cc.watchConfig || '否',
                        autoCreate:cc.autoCreate || "否",
                        colspan: cell.prop('colspan')
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    var tDom = $('<input type="text" conType="tree" name="' + cnf.name + '" style="border:1px solid ' +
                            'black;width:' + cnf.width + ';height:30px" />');
                    instance.cell.append('<label class="clabel">&nbsp;' + cnf.label + '：</label>').append(tDom);
                }
                this.acceptType = function () {
                    return "tree";
                }
                this.applyConfig = function (cnf) {
                    if (targetField) {
                        if (cnf.label) {
                            var labCon = instance.cell.find('label');
                            if (labCon) {
                                labCon.text(cnf.label+'：');
                            }
                        }
                        if (cnf.value) {
                            targetField.val(cnf.value);
                        }
                        if (cnf.url) targetField.attr('url', cnf.url);
                    }
                }
            }

            function checkboxField(cell) {
                var targetField = null;
                var instance = this;
                instance.cell = cell;
                this.contains = function () {
                    var cs = $(cell).find('input');
                    if (cs.length == 0) return false;
                    var inputField = $(cs[0]);
                    targetField = inputField;
                    return inputField.prop('type') == ("checkbox");
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'checkbox',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        required: cc.required,
                        width: cc.width,
                        watchConfig: cc.watchConfig || '否',
                        data: cc.data || "是,否",
                        saveAlone: cc.saveAlone || '否',
                        autoCreate:cc.autoCreate || "否",
                        colspan: cell.prop('colspan')
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    instance.cell.append('<label class="clabel">&nbsp;' + cnf.label + '：</label>');
                    var container = $('<span class="checkboxcontainer"></div>');
                    var data = cnf.data || "是,否";
                    var datas = data.split(',');
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var t = '<input name="' + cnf.name + '" style="width:30px;height:15px;line-height:15px" ' +
                                'type="checkbox" value="' + d + '" /><label>' + d + '</label>&nbsp;&nbsp;';
                        container.append(t);
                    }
                    instance.cell.append(container);
                }
                this.acceptType = function () {
                    return "checkbox";
                }
                this.applyConfig = function (cnf) {
                    var pCell = instance.cell;
                    if (targetField && pCell) {
                        var inputContainer = pCell.find('.checkboxcontainer');
                        if (cnf.value) {
                            targetField.val(cnf.value);
                        }
                        if (cnf.label) {
                            var labCon = pCell.find('.clabel');
                            if (labCon.length > 0) {
                                labCon.text(cnf.label);
                            }
                        }
                        if (cnf.data) {
                            var ds = cnf.data.toString().split(',');
                            if (ds.length > 0) {
                                inputContainer.empty();
                                for (var i = 0; i < ds.length; i++) {
                                    var d = ds[i];
                                    var tt = '<input name="' + d.name + '" style="width:30px;height:15px;' +
                                            'line-height:15px" type="checkbox" value="' + d + '" /><label>' + d + '</label>';
                                    inputContainer.append(tt);
                                }
                            }
                        }
                    }
                }
            }

            function radioField(cell) {
                var targetField = null;
                var instance = this;
                instance.cell = cell;
                this.contains = function () {
                    var cs = $(cell).find('input');
                    if (cs.length == 0) return false;
                    var inputField = $(cs[0]);
                    targetField = inputField;
                    return inputField.prop('type') == ("radio");
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'radio',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        required: cc.required,
                        width: cc.width,
                        watchConfig: cc.watchConfig || '否',
                        data: cc.data || "是,否",
                        saveAlone: cc.saveAlone || '否',
                        autoCreate:cc.autoCreate || "否",
                        colspan: cell.prop('colspan')
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    instance.cell.append('<label class="clabel">&nbsp;' + cnf.label + '：</label>');
                    var container = $('<span class="radiocontainer"></div>');
                    var data = cnf.data || "是,否";
                    var datas = data.split(',');
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var t = '<input name="' + cnf.name + '" style="width:30px;height:15px;line-height:15px" ' +
                                'type="radio" value="' + d + '" />' + '   <label>' + d + '</label>&nbsp;&nbsp;';
                        container.append(t);
                    }
                    instance.cell.append(container);
                }
                this.acceptType = function () {
                    return "radio";
                }
                this.applyConfig = function (cnf) {
                    var pCell = instance.cell;
                    if (targetField && pCell) {
                        var inputContainer = pCell.find('.radiocontainer');
                        if (cnf.value) {
                            targetField.val(cnf.value);
                        }
                        if (cnf.label) {
                            var labCon = pCell.find('.clabel');
                            if (labCon.length > 0) {
                                labCon.text(cnf.label);
                            }
                        }
                        if (cnf.data) {
                            var ds = cnf.data.toString().split(',');
                            if (ds.length > 0) {
                                inputContainer.empty();
                                for (var i = 0; i < ds.length; i++) {
                                    var d = ds[i];
                                    var tt = '<input name="' + d.name + '" style="width:30px;height:15px;' +
                                            'line-height:15px" ' +
                                            'type="radio" ' +
                                            'value="' + d + '" /><label>' + d + '</label>';
                                    inputContainer.append(tt);
                                }
                            }
                        }
                    }
                }
            }

            function fileField(cell) {
                var instance = this;
                instance.cell = cell;
                var inputField = null;
                this.contains = function () {
                    var cs = $(cell).find('input');
                    if (cs.length == 0) return false;
                    inputField = $(cs[0]);
                    return inputField.prop('type') == ("file");
                }
                this.getValue = function () {
                    var row = cell.parent();
                    var cc = mini.decode(cell.attr('config' || "{}"));
                    var res = {
                        rowIndex: row.prop('rowIndex'),
                        colIndex: cell.prop('cellIndex'),
                        type: 'file',
                        label: cc.label,
                        value: cc.value,
                        name: cc.name,
                        required: cc.required,
                        width: cc.width,
                        watchConfig: cc.watchConfig || '否',
                        saveAlone: cc.saveAlone || '否',
                        autoCreate:cc.autoCreate || "否",
                        colspan: cell.prop('colspan')
                    };
                    return res;
                }
                this.createUI = function (cnf) {
                    instance.cell.append('<label class="clabel">&nbsp;' + cnf.label + '：</label><input type="file" ' +
                            'style="width:' + cnf.width + ';height:30px" />');
                }
                this.acceptType = function () {
                    return "file";
                }
                this.applyConfig = function (cnf) {
                    if (inputField) {
                        if (cnf.label) {
                            var labCon = instance.cell.find('label');
                            if (labCon) {
                                labCon.text(cnf.label);
                            }
                        }
                    }
                }
            }

            this.getConfig = function () {
                var result = [];
                var cells = table.find('td');
                for (var i = 0; i < cells.length; i++) {
                    var cell = $(cells[i]);
                    var finder = new newInstance(cell);
                    if (finder.containsField()) {
                        var targetField = finder.getTarget();
                        if (targetField != null) {
                            var cnf = targetField.getValue();
                            if (cnf != null) result.push(cnf);
                        }
                    }
                    finder.dispose();
                }
                return result;
            }
            this.refresh = function (cell, config) {
                var finder = new newInstance(cell);
                var targetField = finder.getTarget();
                if (targetField != null) {
                    targetField.applyConfig(config);
                }
                finder.dispose();
            }
            this.clear = function () {
                var rows = $('#sort tbody').find('tr');
                for (var i = 0; i < rows.length; i++) {
                    var row = $(rows[i]);
                    row.css({'background-color': 'white'});
                }
                var cells = $('#sort tbody').find('td');
                for (var i = 0; i < cells.length; i++) {
                    var cell = $(cells[i]);
                    if (cell.hasClass('index') == false) {
                        cell.empty();
                    }
                }
            }
            this.load = function (datas) {
                var rows = table.find("tr");
                for (var rI = 0; rI < rows.length; rI++) {
                    var row = $(rows[rI]);
                    var rIndex = row.prop('rowIndex');
                    var cells = row.find('td');
                    for (var cI = 0; cI < cells.length; cI++) {
                        var cell = $(cells[cI]);
                        var cIndex = cell.prop('cellIndex');
                        if (cell.hasClass('index') == false) {
                            cell.empty();
                        }
                        for (var i = 0; i < datas.length; i++) {
                            var data = datas[i];
                            var rowIndex = data.rowIndex;
                            var colIndex = data.colIndex;
                            if (rowIndex == rIndex && colIndex == cIndex) {
                                var instance = new newInstance(cell);
                                var colspan = parseInt(data.colspan || 1);
                                var findField = instance.getByType(data.type);
                                if (findField != null) findField.createUI(data);
                                var gx = mini.clone(data);
                                delete gx.rowIndex;
                                delete gx.colIndex;
                                cell.attr('config', mini.encode(gx)).attr('isEmpty', false);
                                if (colspan > 1) {
                                    cell.prop('colspan', colspan);
                                    var nexCell = $(cells[cI + 1]);
                                    if (nexCell.length > 0) nexCell.css({'display': 'none'});
                                }
                            }
                        }
                    }
                }
            }

            function newInstance(cell) {
                var ps = [
                    new textboxField(cell),
                    new dateField(cell),
                    new textAreaField(cell),
                    new selectField(cell),
                    new checkboxField(cell),
                    new radioField(cell),
                    new fileField(cell),
                    new treeField(cell)
                ];
                this.getTarget = function () {
                    for (var i = 0; i < ps.length; i++) {
                        var p = ps[i];
                        if (p.contains() == true) {
                            return p;
                        }
                    }
                }
                this.containsField = function () {
                    var value = cell.attr('isEmpty');
                    if (value == null || value == undefined) return false;
                    var vv = value.toString().toUpperCase();
                    return vv == ("FALSE");
                }
                this.dispose = function () {
                    ps.clear();
                    ps = undefined;
                }
                this.getByType = function (type) {
                    for (var i = 0; i < ps.length; i++) {
                        var p = ps[i];
                        if (p.acceptType() == type) {
                            return p;
                        }
                    }
                }
            }
        }
    </script>
</@js>