var grid = null, curNode = null;
mini.parse();
var tip = new mini.ToolTip();
grid=mini.get('FeeItem_datagrid');
var config = {
    url: '/Fee/FeeItem/Get',
    saveUrl: '/Fee/FeeItem/Save',
    loadUrl: '/Fee/FeeItem/Load',
    primaryKey: "id"
};
function DialogOpen(config) {
    var rows = grid.getSelecteds();
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        var Id=rows[i][config.primaryKey]
        ids.push(Id);
    }
    config["ids"] = ids;
    if (config['action'] != "add" && ids.length < 1) { mini.alert("列表为空"); return; }
    var dialogObj = null;
    mini.open({
        url: config['url'],
        title: config['title'], width: 900, height: 600,
        onload: function () {
            var iframe = this.getIFrameEl();
            dialogObj = new iframe.contentWindow.DialogFun(config);
            if (config['action'] != 'add') dialogObj.load();
        },
        ondestroy: function (action) {
            if (grid) grid.reload();
        }
    });
}
function addFee() {
    config['action'] = 'add';
    config['title'] = '添加费用监控';
    config['afterInit'] = function (window, mini) {
        var grid = mini.get('grid1');
        var datas = grid.getData();
        if (datas.length == 0)
        {
            grid.addRow({});

        }
    },
    config["afterLoad"] = function () {

    }
    DialogOpen(config);
}
function editFee() {
    config['action'] = 'edit';
    config['title'] = '编辑年费资料';
    DialogOpen(config);
}
function browseFee() {
    config['action'] = 'browse';
    config['title'] = '浏览年费资料';
    DialogOpen(config);
}
function remove() {
    var rows = grid.getSelecteds();
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i]["FID"]);
    }
    if (ids.length == 0) {
        mini.alert('请选择要删除的记录!');
        return;
    }
    mini.confirm('确认要删除选择的年费资料?', '删除提示', function (act) {
        if (act == 'ok') {
            var url = '/Fee/FeeItem/Remove';
            $.post(url, { IDS: mini.encode(ids) }, function (r) {
                if (r['success']) {
                    mini.alert('删除成功!');
                    grid.reload();
                }
                else mini.alert(r['Message'] || "删除失败!");
            });
        }
    });
}
function NoPayMark() {
    var rows = grid.getSelecteds();
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].jfqd != '未加入') {
            mini.alert('选择的缴费项目已加入清单，不能标记为不需缴费!');
            return;
        }
        ids.push(rows[i]["id"]);
    }
    if (ids.length == 0) {
        mini.alert('请选择不需缴费的费用项目!');
        return;
    }
    var iss = ids.join(',');
    mini.confirm('确定不需缴费?', '系统提示', function (act) {
        if (act == 'ok') {
            var url = '/work/feeItem/changePayMark';
            $.post(url,  {IDS:mini.encode(ids),PayState:0}, function (r) {
                if (r['success']) {
                    mini.alert('标记成功!','提示',function(){
                        grid.reload();
                    });
                }
                else mini.alert(r['Message'] || "标记失败!");
            });
        }
    });
}
function checkNumber(theObj) {
    var reg = /^[0-9]+.?[0-9]*$/;
    if (reg.test(theObj)) {
        return true;
    }
    return false;
}
function doExportYear() {
    var rows = grid.getSelecteds();
    if (rows.length == 0) {
        alert('至少选择一条要导出的记录。');
        return;
    }
    var data = mini.clone(rows);
    for (var i = 0; i < data.length; i++) {
        var d = data[i];
        var shenqingh = d["shenqingh"];
        var lx = getTypeByShenqingh(shenqingh);
        d["zhuanlilx"] = lx;
        delete d.MEMO;
    }
    var form =document.getElementById('ExportYearForm');
    if (form) {
        $('#KKRows').val(mini.encode(data));
        form.submit();
    }
    event.preventDefault();
    event.stopPropagation();
    return false;
}
function PayMark() {
    var rows = grid.getSelecteds();
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i]["id"]);
    }
    if (ids.length == 0) {
        mini.alert('请选择需缴费的费用项目!');
        return;
    }
    mini.confirm('确定取消标记不缴费?', '系统提示', function (act) {
        if (act == 'ok') {
            var url = '/work/feeItem/changePayMark';
            $.post(url, {IDS:mini.encode(ids),PayState:1 }, function (r) {
                if (r['success']) {
                    mini.alert('保存成功!','提示',function(){
                        grid.reload();
                    });

                }
                else mini.alert(r['Message'] || "保存失败!");
            });
        }
    });
}

function AddToFeeList() {
    var rows = grid.getSelecteds();
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].jfqd != '未加入') {
            mini.alert('选择的缴费项目已加入清单，不能再加入缴费清单!');
            return;
        }
        if (rows[i].pstate==0) {
            mini.alert('选择的缴费项目已标记为不需缴费，不能加入缴费清单!');
            return;
        }
        ids.push(rows[i]["id"]);
    }
    if (ids.length == 0) {
        mini.alert('请选择需缴费的费用项目!');
        return;
    }
    var iss = ids.join(',');
    mini.prompt("请输入费用清单名称：", "请输入",
        function (action, value) {
            if (action == "ok") {
                var url = '/work/feeItem/addToFeeList';
                $.post(url, { IDS: mini.encode(ids),JFQD: value}, function (r) {
                    if (r['success']) {
                        mini.alert('添加费用清单成功!','提示',function(){
                            grid.reload();
                        });
                    }
                    else mini.alert(r['Message'] || "添加失败!");
                });
            } 
        }
    );
}

function Jiaodui() {
    var rows = grid.getSelecteds();
    if (rows.length == 0) {
        mini.alert('请选择修改的费用项目!');
        return;
    }
    if (rows.length > 1) {
        mini.alert('请仅选择一个费用项目!');
        return;
    }
    var item = rows[0];
    mini.prompt("人工校对金额：", "请输入",
        function (action, value) {
            if (action == "ok") {
                if (!checkNumber(value)) {
                    alert("请输入正确格式的金额!");
                    return;
                }
                var url = '/work/feeItem/changeJiaoDuiMoney';
                $.post(url, {IDS:mini.encode([item.id]),Money:value }, function (r) {
                    if (r['success']) {
                        mini.alert('人工校对金额修改成功!','提示',function(){
                            grid.reload();
                        });
                    }
                    else mini.alert(r['Message'] || "修改失败!");
                });
            }
        }
    );
}

function getTypeByShenqingh(value) {
    if (value.length == 13 && value.substring(4, 5) == '1') return '发明专利';
    else if (value.length == 13 && value.substring(4, 5) == '8') return 'PCT发明专利';
    else if (value.length == 13 && value.substring(4, 5) == '2') return '实用新型';
    else if (value.length == 13 && value.substring(4, 5) == '9') return 'PCT实用新型';
    else if (value.length == 13 && value.substring(4, 5) == '3') return '外观设计';
    else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '1') return '发明专利';
    else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '8') return 'PCT发明专利';
    else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '2') return '实用新型';
    else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '9') return 'PCT实用新型';
    else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '3') return '外观设计';
    return "";
}

function onStateRenderer(e) {
    var value = e.value;
    if (value == '1') return '需缴费';
    if (value == '0') return '不需缴费';
    return "";
}

function onJfqdRenderer(e) {
    var value = e.value;
    if (value) return value;
    return '未加入'
}
//缴费截止日状态
/*
已过缴费期限
距离缴费期限0-30天
距离缴费期限31-90天
距离缴费期限超过91天
*/
function onDraw(e) {
    var field = e.field;
    var record = e.record;
    if (field == "status") {
        var now = (new Date()).Format("yyyy-MM-dd");
        var value = record['JIAOFEIR'];
        if (value < now) {
            e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        }
        else if (addDate(value, -30) <= now) {
            e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        }
        else if (addDate(value, -90) <= now) {
            e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        }
        else {
            e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:yellow">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
        }
    } else if (field == "Action") {
        var record = e.record;
        var memo = record["MEMO"];
        var editMemo = parseInt(record["EDITMEMO"] || 0);
        var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
        if (editMemo == 0) {
            if (memo) text = "<span style='color:gay'>查看</span>";
        }
        e.cellHtml = '<a href="#"  data-placement="bottomleft"  hCode="' + record["shenqingh"] + '" class="showCellTooltip" onclick="ShowMemo(' + "'" + record["shenqingh"] + "'," + "'" + record["zhuanlimc"] + "'" + ')">' + text + '</a>';
    }
    else if (field == "KH") {
        var clientId = record["KHID"];
        var val = e.value;
        if (clientId) {
            e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
        } else e.cellHtml = val;
    }
}
function showClient(clientID) {
    var config = {
        url: '/Client/ClientEdit/Index',
        saveUrl: '/Client/ClientEdit/Save',
        loadUrl: '/Client/ClientBrowse/Load',
        formID: 'clientForm',
        pageName: "ClientEdit",
        primaryKey: "ClientID"
    };
    config['action'] = 'browse';
    config['title'] = '浏览客户资料';
    var ClientID = clientID; 0
    window.open(config['url'] + "?Mode=" + config["action"] + "&ClientID=" + ClientID, config['title'], 'fullscreen=no,directories=no,left=1px,top=1px,status=no,toolbar=no,scroll=no,help=no,width=' + (window.screen.width - 50) + 'px,height=' + (window.screen.height - 150) + 'px');
}
function afterload(e) {
    tip.set({
        target: document,
        selector: '.showCellTooltip',
        onbeforeopen: function (e) {
            e.cancel = false;
        },
        onopen: function (e) {
            var el = e.element;
            if (el) {
                var hCode = $(el).attr('hCode');
                if (hCode) {
                    if(!grid)grid=mini.get('FeeItem_datagrid');
                    var rows = grid.getData();
                    var row = grid.findRow(function (row) {
                        if (row["shenqingh"] == hCode) return true;
                    });
                    if (row) {
                        var memo = row["MEMO"];
                        if (memo) {
                            tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + row["MEMO"] + '</td></tr></table>');
                        }
                    }
                }
            }
        }
    });
}
function ShowMemo(id, title) {
    mini.open({
        url: '/work/addMemo/index?ID=' + id,
        showModal: true,
        width: 800,
        height: 400,
        title: "【" + title + "】的备注信息",
        onDestroy: function () {
            grid.reload();
        }
    });
}
