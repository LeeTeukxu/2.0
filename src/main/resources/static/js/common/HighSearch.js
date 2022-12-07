
kendo.culture("zh-CN");
function showHighSearchForm() {
    var searchForm = document.getElementById("searchForm");
    searchForm.style.display = "";
}
function cancelHighSearchForm() {
    var searchForm = document.getElementById("searchForm");
    searchForm.style.display = "none";
}
//高级搜索
function doHightSearch(barName) {
    var loadField=[];
    //var word = txtWords.getValue();
    //var field = comField.getValue();
    $("#" + barName + " input").each(function (i, n) {
        if ("" != $.trim($(n).val()) && $(n).attr("filterType")!=null) {
            var i = loadField.length;
            if (loadField[i] == null) {
                loadField[i] = {}
                loadField[i].name = $(n).attr("name");
                loadField[i].filterType = $(n).attr("filterType");
            }
            /*($(n).attr("valueIndex") == 2) ?*/ loadField[i].value1 = $.trim($(n).val()); //: loadField[i].value1 = $.trim($(n).val());
        }
    });  
    grid.load({ 'loadField': mini.encode(loadField), 'sortField': grid.getSortField(), 'sortOrder': grid.getSortOrder() });
}
//关闭导出窗口
function cancelSelectExportColumn() {
    mini.get('selectExportColumn').hide(); 
}
//反选
function opsSelectExportColumn() {
    $("#selectExportColumn input[type='checkbox']").each(function (i, n) {
        if ($(n)[0].checked == true) $(n)[0].checked = false;
        else $(n)[0].checked = true;
    });
}

//导出
function doExport(e) {
    var rows = mini.clone(grid.getData());
    if (rows.length == 0) {
        mini.alert('没有发现可以导出的记录!');
        return;
    }
    var window = mini.get('selectExportColumn');
    if (window.getBodyEl().textContent.trim() == '') {
        var Element = '<div class="Js_default">'
        var cs = grid.getColumns();
        for (var i = 0; i < cs.length; i++) {
            var column = cs[i];
            var header = typeof(column['header'])=="function"?'':$.trim(column['header']);
            var field = $.trim(column['field']);
            var dType = column['dataType'] || "string";
            if (header && field) {
                Element = Element + '<label style="width: 24%;display: inline-block;line-height: 35px;"><input type="checkbox" checked="checked" header="' + header + '"'
                    + 'dType="' + dType + '" width="' + column['width'].replace('px', '') + '" field="' + field + '">' + header + '</label>'
            }
        }
        //设置导出字段
        Element = Element + '</div>';
        window.setBody(Element);
    }
    window.show();
    //win.showAtEl(document.getElementById("doExport"), {xAlign: xAlign, yAlign: yAlign});
    return false;
}
//导出请求 ytype:1两种导出 0:导出选择 没有专利号的只做选择导出
function reqExoprt(vt, title, fileName, ytype) {
	ytype = 0;
    var columns = [];
    $("#selectExportColumn input[type='checkbox']").each(function (i, n) {
        if ($(n)[0].checked == true) {
            var header = $(n)[0].getAttribute('header')
            var dType = $(n)[0].getAttribute('dType')
            var field = $(n)[0].getAttribute('field')
            var width = $(n)[0].getAttribute('width')
            var p = { header: header, type: dType, field: field, width: width };
            columns.push(p);
        }
    });
    if (columns.length == 0) {
        mini.alert('请选择导出的列!');
        return;
    }
    var f = $('#ExportForm');
    var rows = mini.clone(grid.getSelecteds());
    var arg = {
        Columns: columns,
        Rows: rows,
        Title: title,
        FileName: fileName,
        TableName: '',
        sortField: '',
        sortOrder: '',
        loadField: '',
        SubTitle:[]
    }
	if (rows.length == 0 && ytype == 0) {
		mini.alert('请选择导出的行!');
		return;
	}
    if (ytype == 0 || rows.length > 0) {
        var ds = encodeURIComponent(mini.encode(arg));
        var f = $('#ExportForm');
        $('#Data').val(ds);
        f[0].submit();
        return false;
    } else {
        var loadField = [];
        $("#searchForm input").each(function (i, n) {
            if ("" != $.trim($(n).val()) && $(n).attr("filterType") != null) {
                var i = loadField.length;
                if (loadField[i] == null) {
                    loadField[i] = {}
                    loadField[i].name = $(n).attr("name");
                    loadField[i].filterType = $(n).attr("filterType");
                }
                /*($(n).attr("valueIndex") == 2) ?*/ loadField[i].value1 = $.trim($(n).val()); //: loadField[i].value1 = $.trim($(n).val());
            }
        });
        arg.loadField = loadField;
        arg.sortOrder = grid.getSortOrder();
        arg.sortField = grid.getSortField();
        arg.TableName = vt;
        f.attr('action', "/Common/ExportExcelService/ReqExport")
    }/*
    var ds = encodeURIComponent(mini.encode(arg));
    mini.mask({ el: document.body, cls: 'mini-mask-loading', html: '正在导出中...' });
    $('#Data').val(ds);
    $.ajaxSetup({ async: false });
    $('#ExportForm').form('submit', {
        onSubmit: function (e) {
            $.post('/Common/ExportExcelService/ReqExport', { Data: mini.encode(arg) });
            mini.unmask();
        }
    });*/
    return false;    
}