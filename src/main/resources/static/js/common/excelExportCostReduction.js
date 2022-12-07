function excelData(grid){
    var columns=[];
    var exceptHeader=[
        "备注信息","邮件通知"
    ];
    var msgId=null;
    function init(){
        var cols=grid.getColumns();
        for(var i=0;i<cols.length;i++){
            var col=cols[i];
            var field=col["field"];
            if(!field) continue;
            var visible=col.visible;
            if(visible==false) continue;
            var header=col["header"] ||"";
            if(!header) continue;
            if(exceptHeader.indexOf(header)>-1) continue;
            columns.push(col);
        }
        $.getScript('/js/common/jquery.fileDownload.js');
    }
    init();
    var downError=false;
    this.export=function(fileName,downloadError){
        downError=downloadError;
        msgId=createChoiceDialog(fileName);
    }
    function createChoiceDialog(fileName){
        var ccs=[];
        var ids=[];
        for(var i=0;i<columns.length;i++){
            var col=columns[i];
            var field=col.field;
            var header=$.trim(col.header);
            var ox={id:field,text:'<div style="width:120px;height:40px;line-height:40px">'+header+'</div>'};
            ccs.push(ox);
            ids.push(field);
        }
        window.choiceItems=ccs;
        var ttd='<div  id="cbl1" class="mini-checkboxlist" repeatItems="5" repeatLayout="flow"\n' +
            '    textField="text" valueField="id" value="'+ids.join(',')+'" data="choiceItems">\n' +
            '</div>';
        var ttx='<table style="width:100%;table-layout: auto;"><tbody><tr><td>'+ttd+'</td></tr>';
        var ttg='<input labelField="true" id="postFileName" labelStyle="width:80px" label="文件名称:"' +
            ' class="mini-textbox"' +
            ' value="'+fileName+'" style="width:80%;height:30px"' +
            ' required="true"/>';
        ttx+='<tr><td style="height:40px">'+ttg+'</td></tr>';
        ttx+='<tr><td style="height:80px;text-align:center;vertical-align:top"><button class="mini-button"' +
            ' id="downloadExcel">下载Excel</button><button' +
            ' class="mini-button" style="margin-left:100px" id="closeDialog">取消关闭</button></td></tr>';
        ttx+='</tbody></table>';
        var msgId=mini.showMessageBox({
            html:ttx,
            title:'导出Excel内容设置',
            width:800,
            height: Math.ceil(columns.length/5)*40+250,
            iconCls: "mini-messagebox-question"
        });
        mini.parse();
        mini.get('downloadExcel').on('click',downloadExcel);
        mini.get('closeDialog').on('click',closeDialog);
        return msgId;
    }
    function downloadExcel(){
        var loadingMsgId=null;
        function getData() {
            var rows=grid.getSelecteds() || [];
            rows=funs.beforeGetData(grid,rows);
            var colHash=getColumnHash();
            var fields=getFields();
            var datas=[];
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                // if (rows.indexOf("IsErrorResult")>0) {
                    if (row.IsErrorResult != undefined&&row.IsErrorResult=='正常导入') {

                    }else {
                        var singleData={};
                        for(var n =0;n<fields.length;n++){
                            var field=fields[n];
                            var col=colHash[field];
                            var val="";
                            if (field == "UserID"){
                                val = row.UserIDName;
                            }else if (field == "FileName"){
                                val = row.FileName;
                            }else if (field == "InternalPeople"){
                                val = row.NSR;
                            }else if (field == "Transactor"){
                                val = row.FJR;
                            }
                            else {
                                val = getSingleValue(field, col, row);
                            }
                            singleData[field]=val;
                        }
                        datas.push(singleData);
                    }
                // }

            }
            return datas;
        }
        function getColumns(){
            var res=[];
            for(var i=0;i<columns.length;i++){
                var col=columns[i];
                var o={field:col.field,header:$.trim(col.header),width:parseInt(col.width|| 80)};
                var type=col["dataType"] || "string";
                if(downError==true) type="string";
                o.type=type;
                res.push(o);
            }
            return res ;
        }
        function getColumnHash(){
            var res={};
            for(var i=0;i<columns.length;i++){
                var col=columns[i];
                var field=col.field;
                col.header=$.trim(col.header);
                res[field]=col;
            }
            return res;
        }
        function getFields(){
            var res=[];
            for(var i=0;i<columns.length;i++){
                var col=columns[i];
                var field=col.field;
                res.push(field);
            }
            return res;
        }
        function getSingleValue(field,col,row){
            var val=row[field];
            if(val==null || val==undefined) return '';
            if($.trim(val.toString())=='') return '';
            var type=col['type'];
            if(type=="comboboxcolumn"){
                return getTextFromCombobox(val,col._data);
            }
            if(type=="treeselectcolumn"){
                return getTextFromCombobox(val,col._data);
            }
            else{
                var dataType=col["dataType"] ||"string";
                if(dataType=="string") return val;
                else if(dataType=="date"){
                    var delValue= mini.formatDate(val,col.dateformat || "yyyy-MM-dd");
                    if(!delValue) delValue=val;
                    return delValue;
                }
                else if(dataType=="float" || dataType=="int"){
                    return val;
                }
                else return val;
            }

            function getTextFromCombobox(value,datas){
                for(var i=0;i<datas.length;i++){
                    var data=datas[i];
                    var id=data.id;
                    if(id==value) return data.text;
                }
                return '';
            }
        }
        var fileName=encodeURI(mini.get('#postFileName').getValue());
        $.fileDownload('/excel/download', {
            httpMethod : 'POST',
            data : 'data='+mini.encode(getData())+'&columns='+mini.encode(getColumns())+'&filename='+fileName,
            prepareCallback : function(url) {
                if(msgId)mini.hideMessageBox(msgId);
            },
            failCallback : function(html, url) {
                mini.alert('下载错误:'+html,'系统提示');
            }
        });
    }
    function closeDialog(){
        if(msgId)mini.hideMessageBox(msgId);
    }
    var funs={
        beforeGetData:function(grid,data){
            return data;
        }
    };
    this.addEvent=function(evt,func){
        if(funs[evt])funs[evt]=func;
    }
}