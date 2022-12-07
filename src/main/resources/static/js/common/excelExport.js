function excelData(grid){
    var columns=[];
    var exceptHeader=[
        "备注信息","邮件通知","申报资料","备注","进度说明","技术联系人信息","技术交底资料","著录信息资料"
    ];
    var msgId=null;
    var txt = '';
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
            if(exceptHeader.indexOf(header.trim())>-1) continue;
            columns.push(col);
        }
        $.getScript('/js/common/jquery.fileDownload.js');
    }
    init();
    var downError=false;
    this.export=function(fileName,downloadError){
        downError=downloadError;
       var cols= funs.updateColumnDefine(columns);
       if(cols)columns=cols;
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
            if(rows.length==0) rows=grid.getData();
            rows=funs.beforeGetData(grid,rows);
            var colHash=getColumnHash();
            var fields=getFields();
            var datas=[];
            for(var i=0;i<rows.length;i++){
                txt = "";
                var row=rows[i];
                var singleData={};
                for(var n =0;n<fields.length;n++){
                    var field=fields[n];
                    if (field=="NeedPayFor"){
                        if (row["NeedPayFor"]){
                            row.NeedPayFor='需缴费';
                        }
                        else{
                            row.NeedPayFor='';
                        }
                    }
                    if (field=="OutTech"){
                        if (row["OutTech"]){
                            row.OutTech='是'
                        }else {
                            row.OutTech='否';
                        }
                    }
                    var col=colHash[field];
                    var val = "";
                    if (field == "TechMan" || field == "CreateMan" || field == "TechMan" || field == "AuditMan" ){
                        val = func(row[field],col._data);
                    }else {
                        val = getSingleValue(field, col, row);
                    }
                    singleData[field]=val;
                }
                datas.push(singleData);
            }
            return datas;
        }
        function getColumns(){
            var res=[];
            var fields=getFields() || [];
            for(var i=0;i<columns.length;i++){
                var col=columns[i];
                var field=$.trim(col.field || "");
                if(!field) continue;
                if(fields.indexOf(field)>-1){
                    var o={field:col.field,header:$.trim(col.header),width:parseInt(col.width|| 80)};
                    var type=col["dataType"] || "string";
                    if(downError==true) type="string";
                    o.type=type;
                    res.push(o);
                }
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
            var con=mini.get('#cbl1');
            var ids=(con.getValue()||"").split(",");
            for(var i=0;i<ids.length;i++){
                var id=$.trim(ids[i]);
                res.push(id);
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
    function func (v,data) {
        for(var i =0;i<data.length;i++){
            if(data[i].children){ //判断children下是否还有数据，避免进入死循环
                var cld = data[i].children;
                for (var j=0;j<cld.length;j++){
                    if (cld[j].FID==v){
                        txt = cld[j].Name;
                    }
                }
                func(v,data[i].children)
            }
        }
        return txt;
    }
    function closeDialog(){
       if(msgId)mini.hideMessageBox(msgId);
    }
    var funs={
        beforeGetData:function(grid,data){
            return data;
        },
        updateColumnDefine:function(columns){
            return false;
        }
    };
    this.addEvent=function(evt,func){
        if(funs[evt])funs[evt]=func;
    }
}