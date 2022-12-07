/*
 * Date Format 1.2.3
 * (c) 2007-2009 Steven Levithan <stevenlevithan.com>
 * MIT license
 *
 * Includes enhancements by Scott Trenda <scott.trenda.net>
 * and Kris Kowal <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask.
 * Returns a formatted version of the given date.
 * The date defaults to the current date/time.
 * The mask defaults to dateFormat.masks.default.
 * url:http://blog.stevenlevithan.com/archives/date-time-format
 */

var dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
	return dateFormat(this, mask, utc);
};



function dateFmt(date){
	return date.format("yyyy-mm-dd");
}


/* 表格列格式化函数  start*/

function parseBoolean(value){
	if(typeof value == 'string')
		return 'true' == value;
	if(typeof value == 'boolean')
		return value;
	return !!value;
	
	
}

/**
 * datagrid格式化数据方法
 * 格式化boolean为中文
 * @param {Object} value
 * @param {Object} row
 */
function formatterBool(value, row) {
	if(value == -1)return;
	if(parseBoolean(value))
		return '是';
	return '否';
}

function formatterY(value, row) {
	if(value == -1)return;
	if(parseBoolean(value))
		return '√';
	return 'X';
}
function formatterY2(value, row) {
	if(value == -1)return;
	if(parseBoolean(value))
		return '√';
	return '';
}

/**
 * datagrid格式化数据方法
 * 格式化性别
 * @param {Object} value
 * @param {Object} row
 */
function formatterSex(value, row) {
	if(value == -1)return;
	if(!value || value == '0')
		return '女';
	return '男';
}
/**
 * 性别下拉列表的选项
 */
var sexOptions = [{value:1,text:'男'},{value:0,text:'女'}];
/* 是否 */
var boolOptions = [{value:1,text:'是'},{value:0,text:'否'}];


function formatShortDate(v){
	if(!!v) 
		return $.format.date(v, 'yyyy-MM-dd');
	return "";
}

function formatYYYYMM(v){
	if(!!v) 
		return $.format.date(v, 'yyyy-MM');
	return "";
}
function formatDateWithmm(v){
	if(!!v) 
		return $.format.date(v, 'yyyy-MM-dd HH:mm');
	return "";
}
function formatDate(v){
	if(!!v) 
		return $.format.date(v, 'yyyy-MM-dd HH:mm:ss');
	return "";
}
function splitNum(num){
	if(!num)return num;
	num  =  num+"";  
    var  re=/(-?\d+)(\d{3})/  
    while(re.test(num)){  
    	num=num.replace(re,"$1,$2")  
    }  
    return  num;  
}
/* 表格列格式化函数  end*/

//格式化金钱  以及千分位，标示
function fmoney(s, n) {
	   if(!s||isNaN(s)) return s;
	   if(!n || n>20)n = 2;
	   s = Number(s).toFixed(n);
	   var arr = s.split('.');
	   if(arr.length !=2)
		   return splitNum(s);
	   return splitNum(arr[0]) + '.' + arr[1]; 
}

function fmoney2(v){
	if (typeof v == "number")
		return v.toFixed(2);
	v = parseFloat(v) || 0;
	return fmoney2(v) || 0;
}
function fmoney3 (v){
	if (typeof v == "number")
		return v.toFixed(2);
	else if (typeof v == "string")
		return fmoney3(parseFloat(v))
	else
		return "";
}

/**
 * 列表页单据号链接
 * @param {Object} value
 * @param {Object} row
 * @return {TypeName} 
 */
function linkFormatter(value,row){
	if(!value)return value;
	return '<a href="#" onclick="openWindow(\''+tOptions.viewURL+'\', { keyId : '+row[tOptions.keyIdField]+' })">'+value+'</a>';
}


function fnumberHideZero(v) {
	return v === 0 ? "" : v;
}

function convertDataSource(data){
	var convertData={};
	for(var v in data){
		var spliteStr = v.split("_");
		var key = "";
		key = spliteStr[0].toLowerCase();
		for(var i=1;i<spliteStr.length;i++){
			key += spliteStr[i].substring(0,1) + spliteStr[i].substring(1).toLowerCase();
		}
		convertData[key] = data[v];
	}
	return convertData;
}