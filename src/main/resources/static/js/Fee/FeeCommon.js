

function onShowFeeListDetail(e) {
	var FeeList_datagrid = mini.get("FeeListDetail_datagrid");
	var detailGrid_Form = document.getElementById("detailGrid_Form");

	var grid = e.sender;
	var row = e.record;
	var td = grid.getRowDetailCellEl(row);
	td.appendChild(detailGrid_Form);
	detailGrid_Form.style.display = "block";

	FeeList_datagrid.load({ jfqd_id: row.jfqd_id });
}

//zhuanlilx
function onShenqinghRenderer(e) {
	var value = e.value;
	if (e.record.shenqingh) value = e.record.shenqingh;
	if (!value) return '';
	if (value.length ==13 && value.substring(4, 5) == '1') return '发明专利';
	else if (value.length == 13 && value.substring(4, 5) == '8') return 'PCT发明专利';
	else if (value.length == 13 && value.substring(4, 5) == '2') return '实用新型';
	else if (value.length == 13 && value.substring(4, 5) == '9') return 'PCT实用新型';
	else if (value.length == 13 && value.substring(4, 5) == '3') return '外观设计';

	else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '1') return '发明专利';
	else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '8') return 'PCT发明专利';
	else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '2') return '实用新型';
	else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '9') return 'PCT实用新型';
	else if ((value.length == 8 || value.length == 9) && value.substring(2, 3) == '3') return '外观设计';
	return value;
}
var token = ''
function getToken(){
	if (!token){
		$.ajax({ 
			type : "post", 
			url : "/express/orig/getToken",
			async : false, 
			success : function(r){ 
				if (r['success']) {
					token = r['data'];
				}
			} 
        }); 
	}
	return token;
}
function onZhanlihao(e) {
	var value = e.value;
	if (value == null) return "";
	var str = 'http://cpquery.sipo.gov.cn/txnQueryFeeData.do?select-key:shenqingh=' + value +'&token=' + getToken()
	//+ '&select-key:gonggaobj=0' 
	return '<a target="_blank" href="' + str + '">' + value + '</a>'

}

function onZhanlihaoGonggaobj(e) {
	var value = e.value;
	if (value == null) return "";
	var str = 'http://cpquery.sipo.gov.cn/txnQueryFeeData.do?select-key:shenqingh=' + e.record.shenqingh 
		+ '&select-key:gonggaobj=0' + '&token=' + getToken()
	return '<a target="_blank" href="' + str + '">' + value + '</a>'

}

function DialogOpen(grid, config) {
    var rows = grid.getSelecteds();
    var ids = [];
    for (var i = 0; i < rows.length; i++) {
        var Id = rows[i][config.primaryKey]
        ids.push(Id);
    }
    config["ids"] = ids;
    if (config['action'] !== "add" && ids.length < 1) { mini.alert("列表为空"); return; }
    var dialogObj = null;
    mini.open({
        url: config['url'] + "?Mode=" + config["action"],
        title: config['title'], width: 900, height: 600,
        onload: function () {
            var iframe = this.getIFrameEl();
            dialogObj = new iframe.contentWindow.DialogFun(config);
            if (config['action'] !== 'add') dialogObj.load();
        },
        ondestroy: function (action) {
            if (grid) grid.reload();
        }
    });
}

function addDate(date, days) {
    var d = new Date(date);
    d.setDate(d.getDate() + days);
    var month = d.getMonth() + 1;
    var day = d.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var val = d.getFullYear() + "-" + month + "-" + day;
    return val;
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}