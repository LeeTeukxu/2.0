mini.parse();
var tip = new mini.ToolTip();
var grid = null, curNode = null;
//原件状态 1:待分类 2:自取 3:邮寄 4:归档 5:已领取
function onOstateRenderer(e) {
    var value = e.value;
    if (value == '1') return '待分类';
    if (value == '2') return '待自取';
    if (value == '3') return '邮寄';
    if (value == '4') return '归档';
    if (value == '5') return '已自取';
    return "";
}
//原件类型 1:证书 2:通知书 3:票据
function onOtypeRenderer(e) {
    var value = e.value;
    if (value == '1') return '证书';
    if (value == '2') return '通知书';
    if (value == '3') return '票据';
    return ''
}

function cancelRow(row_uid) {
    var editWindow = mini.get("editWindow");
    editWindow.hide();
}
var otype = 0;
//登记证书/通知书窗口
function Register(row_uid) {
    var form = new mini.Form("#editform");
    var editWindow = mini.get("editWindow");
    editWindow.setTitle("通知书证书登记");
    editWindow.show();
    otype = 1;
    $('input[name=barcode]').focus();
}
//登记收据窗口
function RegisterShouju(row_uid) {
    var form = new mini.Form("#editform");
    var editWindow = mini.get("editWindow");
    editWindow.setTitle("票据登记");
    editWindow.show();
    otype = 2;
    $('input[name=barcode]').focus();
}
//添加快递窗口
function AddExpressWindow() {
    grid=mini.get('#datagrid')
    var ids=[];
    var rows = grid.getSelecteds();
    var otype1 = 0;
    var otype2 = 0;
    var zlmc="";
    var zqxx=""
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].PackageNum!=undefined){
            zlmc=zlmc+rows[i].FAMINGMC+",";
        }
        if (rows[i].ostateText == '2') {
            zqxx=zqxx+rows[i].FAMINGMC+",";
        }
		if (rows[i].ostate == '2' || rows[i].ostate == '5' ) {
            mini.alert('自取状态需先修改为待分类, 才能邮寄!');
            return;
        }
        ids.push(rows[i]["dnum"]);
        if (rows[i].dnum.length == 13) otype1++;
        if (rows[i].dnum.length == 16) otype2++;
    }
    if (zlmc!=""){
        mini.alert("专利名称为'"+zlmc.substring(0,zlmc.length-1)+"'的原件已添加到快递！");
        return;
    }
    if (zqxx!=""){
        mini.alert("专利名称为'"+zqxx.substring(0,zqxx.length-1)+"'的原件已自取，不能邮寄！");
        return;
    }
    //原件类型 1:证书 2:通知书 3:票据
    var contents = '';
    if (otype1 > 0) contents += "证书"+otype1+"份, ";
    if (otype2 > 0) contents += "通知书" + otype2 + "份, ";
    contents = contents.substring(0, contents.length - 2)
    if (rows.length == 0) {
        mini.alert('请选择要邮寄的原件!');
        return;
    }
    var iss = ids.join(',');
    mini.open({
        url:'/express/orig/addExpress?id='+iss+'&contents='+encodeURI(contents),
        width:'50%',
        height:460,
        title:'快递信息',
        showModal:true,
        onDestroy:function(){
            grid.reload();
        }
    })
    // var form = new mini.Form("#editform2");
    // var editWindow = mini.get("editWindow2");
    // //$('#editform2 input[name=Contents]').val(contents);
    // mini.get(Contents2).setValue(contents);
    // editWindow.show();
}
function PickUp() {
    grid=mini.get('#datagrid');
    var rows = grid.getSelecteds();
    var ids = [];
    var packagenum=[];
    for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i]["dnum"]);
        packagenum.push(rows[i]["PackageNum"])
        if (rows[i].ostateText=="4"){
            mini.alert("该原件已邮寄，不能自取！");
            return;
        }
        if (rows[i].ostateText=="2"){
            mini.alert("该原件已自取，不能再次设置为待自取！");
            return;
        }
    }
    if (ids.length==0){
        mini.alert("请选择要设置的原件信息！");
        return;
    }

    var iss = ids.join(',');
    mini.confirm('确定待自取?', '系统提示', function (act) {
        if (act == 'ok') {
            var url = '/express/orig/yjzq';
            var arg={
                Dnum:iss,
                PackageNum:mini.encode(packagenum)
            };
            $.post(url, arg, function (r) {
                if (r['success']) {
                    //mini.alert('标记成功!');
                    grid.reload();
                }
                else mini.alert(r['Message'] || "标记失败!");
            });
        }
    });
}
function AddExpressByExpressNo() {
    var rows = grid.getSelecteds();
    var editform2 = new mini.Form("#editform2");
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].ostate == '3') {
            mini.alert('选择的原件已添加到快递!');
            return;
        }
		if (rows[i].ostate == '2' || rows[i].ostate == '5' ) {
            mini.alert('自取状态需先修改为待分类, 才能邮寄!');
            return;
        }
		if (rows[i].ostate == 2 || rows[i].ostate == 5 ) {
            mini.alert('自取状态需先修改为待分类, 才能邮寄!');
            return;
        }
        ids.push(rows[i]["id"]);
    }
    if (ids.length == 0) {
        mini.alert('请选择要邮寄的原件!');
        return;
    }
    var iss = ids.join(',');
	url = '/Original/ExpressSend/AddExpressByExpressNo';
	mini.prompt("请输入包裹编号：", "请输入",
		function (action, value) {
			if (action == "ok") {
				if(!value) return ;
				$.post(url, { ids: iss, ExpressNo: value}, function (r) {
					if (r['success']) {
						grid.reload();
					}
					else mini.alert(r['Message'] || "添加失败!");
				});
			}
		}
	);
}
var form = new mini.Form("#editform3");
//修改原件状态窗口
function EditOriginal() {
    var rows = grid.getSelecteds();
    if(rows.length == 0){
        mini.alert('请选择需要修改的原件!');
        return;
    }
    if (rows.length > 1) {
        mini.alert('请只选择一个修改的原件!');
        return;
    }
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].ostate == 3) {
            mini.alert('邮寄状态需先删除快递, 才能修改状态!');
            return;
        }
    }
    var editWindow = mini.get("editWindow3");
    var obj = rows[0];
    if(obj.otype == 1) obj.otypeText = "证书";
    if(obj.otype == 2) obj.otypeText = "通知书";
    if (obj.otype == 3) obj.otypeText = "票据";
    mini.get("ostate3").setValue(obj.ostate);
    form.setData(obj);
    editWindow.show();
}
//原件批量归档
function PlaceFile() {
    var rows = grid.getSelecteds();
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        if (rows[i].ostate == 3) {
            mini.alert('邮寄状态需先删除快递, 才能修改状态!');
            return;
        }
        ids.push(rows[i]["id"]);
    }
    if (ids.length == 0) {
        mini.alert('请选择需要归档的原件!');
        return;
    }
    var iss = ids.join(',');
    mini.prompt('请输入归档号:', '系统提示', function (act, value) {
        if (act == 'ok') {
            var url = '/Original/Original/PlaceFile';
            $.post(url, { ids: iss, archivno: value}, function (r) {
                if (r['success']) {
                    //mini.alert('归档成功!');
                    grid.reload();
                }
                else mini.alert(r['Message'] || "归档失败!");
            });
        }
    });
}

//修改原件状态
function SaveOriginal() {
    if (mini.get("ostate3").getValue() == 3) {
        mini.alert('不能修改为邮寄状态,请添加快递！');
        return;
    }
    var editWindow = mini.get("editWindow3");
    form.validate();
    if (form.isValid()) {
        var data = form.getData();
        var url = '/Original/Original/SaveOriginal';
        $.post(url, { Data: mini.encode(data) }, function (r) {
            if (r.success) {
                grid.reload();
                //mini.alert('修改成功');
                editWindow.hide();
            } else mini.alert(r['Message'] || "添加失败!");
        });
    }
}

function cancelRow3() {
    var editWindow = mini.get("editWindow3");
    editWindow.hide();
}
function cancelRow2() {
    var editWindow = mini.get("editWindow2");
    editWindow.hide();
}
function onDraw(e) {
    var field = e.field;
    var record = e.record;
    if (field=="ostateText"){
        var state=record["ostateText"];
        if (state=="1") e.cellHtml = '<SPAN style="color:blue">待自取</span>';
        if (state=="2") e.cellHtml = '<SPAN style="color:red">已自取</span>';
        if (state=="3") e.cellHtml = '<SPAN style="color: blue">待寄送</span>';
        if (state=="4") e.cellHtml = '<SPAN style="color: red">已寄送</span>';
    }
    if (field == "KH") {
        var clientId = record["KHID"];
        var val = e.value;
        if (clientId) {
            e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
        } else e.cellHtml = val;
    }
}
function afterload(e) {
    tip = new mini.ToolTip();
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
                    var rows = grid.getData();
                    var row = grid.findRow(function (row) {
                        if (row["SHENQINGH"] == hCode) return true;
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
        url:'/work/addMemo/index?ID='+id,
        showModal:true,
        width:1000,
        height:500,
        title:"【"+title+"】的备注信息",
        onDestroy:function(){
            doReload(grid);
        }
    });
}
function onCustomLinkerDialog(e) {
    var btnEdit = this;
    mini.open({
        url: "/finance/ClientWindow/index",
        title: "选择客户",
        width: 650,
        height: 380,
        ondestroy: function (action) {
            if (action == "ok") {
                var iframe = this.getIFrameEl();
                var data = iframe.contentWindow.GetData();
                data = mini.clone(data);    //必须
                if (data) {
                    var _text = "";
                    var _value = "";
                    for (var i = 0; i < data.length; i++) {
                        _text = _text + data[i].Name + ",";
                        _value = _value + data[i].ClientID + ",";
                    }
                    if (_text) _text = _text.substring(0, _text.length - 1);
                    if (_value) _value = _value.substring(0, _value.length - 1);
                    var _kh = mini.get('customer');
                    _kh.setText(_text);
                    _kh.setValue(_value);
                }
            }
        }
    });
}
$(function () {
    mini.parse();
    grid=mini.get('#datagrid');


    $("input[name=barcode]").keypress(function (e) {
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13) {
            value = $("input[name=barcode]").val()
            url = ''
            var Code=mini.get('Coding').getValue()+",";
            var Coding=[];
            Coding=Code.split(',');
            for (var i=0;i<Coding.length;i++) {
                if (value == Coding[i]) {
                    mini.alert("该二维编码已经在原件管理中存在，请勿重复添加！")
                    return;
                }
            }
            if (otype == 1) url = '/express/orig/register';
            if (otype == 2) url = '/express/orig/registerShouju';
            $.post(url, { barcode: value }, function (r) {
                if (r['success']) {

                    //BEGIN 将新添加成功的二维编码追加到隐藏域中，方便下次检查登记的二维编码是否重复
                    var addCoding=mini.get('Coding').getValue();
                    addCoding=addCoding+","+value;
                    mini.get('Coding').setValue(addCoding);
                    //END

                    grid.reload();
                    $("input[name=barcode]").val('');
                }
                else mini.alert(r['Message'] || "添加失败!");
            });
        }
    });
	$("#CreateTimeA").kendoDatePicker({ format: "yyyy-MM-dd", animation: false });
    $("#CreateTimeB").kendoDatePicker({ format: "yyyy-MM-dd", animation: false });
    //原件状态
    $("#ipOstate").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "id",
        animation: false,
        dataSource: [{ id: '', text: '全部' }, { id: '1', text: '待分类' }, { id: '2', text: '待自取' },
		{ id: '3', text: '邮寄' }, { id: '4', text: '归档' }, { id: '5', text: '已自取' }]
    }).data("kendoDropDownList");
	//案件状态
    $("#ipAnjianywzt").kendoDropDownList({
        dataTextField: "text",
        dataValueField: "text",
        animation: false,
        dataSource: {
            type: "json",
            serverFiltering: true,
            transport: {
                read: {
                    url: "/systems/dict/getAllByDtId?dtId=12",
                }
            }
        },
        optionLabel: {
            text: "--请选择--",
            id: ""
        }
    }).data("kendoComboBox");
});

// function expand(e) {
//     e=e||{};
//     var btn = e.sender;
//     if(!btn){
//         btn=mini.get('#Original_showHighSearchForm');
//     }
//     var display = $('#p1').css('display');
//     if (display == "none") {
//         btn.setIconCls("panel-collapse");
//         btn.setText("高级查询");
//         $('#p1').css('display', "block");
//
//     } else {
//         btn.setIconCls("panel-expand");
//         btn.setText("高级查询");
//         $('#p1').css('display', "none");
//     }
//     fit.setHeight('100%');
//     fit.doLayout();
// }


function doExport(){
    try
    {
        grid=mini.get('#datagrid');
        // var rows=mini.clone(grid.getData());
        var rows=mini.clone(grid.getSelecteds());
        $('#exportExcelData').val(mini.encode(rows));
        var formObject=document.getElementById("exportExcelForm");
        if(formObject)formObject.submit();
        event.stopPropagation();
        return false;
    }
    catch(e)
    {
        alert(e.toString());
    }
    finally
    {
        return false;
    }
}

function afterload(e) {
    updateNumber();
}
function updateNumber() {
    var url="/express/orig/refreshTotal";
    $.getJSON(url,{},function (result) {
        var rows=result.data || [];
        for (var i=0;i<rows.length;i++){
            var row=rows[i];
            var cName=row["name"];
            var num = parseInt(row["num"] || 0);
            var con=$('.'+cName);
            if (con.length > 0){
                con.text(num);
            }
        }
    })
}
function changeQuery(code, state,obj,yn) {
    var con = $(event.srcElement || e.targetElement);
    var cons=$('.Jdlcli');
    for(var i=0;i<cons.length;i++){
        var cx=cons[i];
        if(cx.className=="Jdlcli orig" || cx.className=="Jdlcli orig clicked" ){
            cx.children[0].children[0].style.cssText="color:rgb(0, 159, 205);";
            cx.children[0].children[1].style.cssText="color:rgb(0, 159, 205);";
        }
        cx.classList.remove('clicked');
    }
    $(con).parents('.Jdlcli').addClass('clicked');
    obj.children[0].style.cssText="color:#fff";
    obj.children[1].style.cssText="color:#fff";
    doQuery(code, state,yn);
}

function doQuery(code, state,yn) {
    grid=mini.get('datagrid');
    var arg = {};
    var cs = [];
    if (code && yn=="yes") {
        var op = {field: code, oper: 'EQ', value: state};
        cs.push(op);
    }
    if(cs.length>0)arg["Query"] = mini.encode(cs);
    grid.load(arg);
}

function onDraw(e) {
    var field = e.field;
    var record = e.record;
    if (field=="ostateText"){
        var dd = record["ostateText"];
        if(dd=="0"){
            e.cellHtml='<span style="color: black">待分类</span>';
        }
        if (dd=="1"){
            e.cellHtml='<span style="color: red">待自取</span>';
        }
        if (dd=="2"){
            e.cellHtml='<span style="color: orange">已自取</span>';
        }
        if (dd=="3"){
            e.cellHtml='<span style="color: blue">待寄送</span>';
        }
        if (dd=="4"){
            e.cellHtml='<span style="color: green">已寄送</span>';
        }
    }
}