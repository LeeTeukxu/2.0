function complexExcelDatas(grid){
    function init(){
        var rows=grid.getSelecteds();
        if(rows.length==0){
            mini.alert('请选择要导出的记录!');
            return null;
        }
        var obj=ZLObject.parse(rows);
        return obj;
    }
    this.export=function(code,fileName,postResult){
        if(postResult==null) postResult=init();
        if(postResult){
            $.fileDownload('/excel/download1', {
                httpMethod : 'POST',
                data : 'data='+mini.encode(postResult)+'&filename='+encodeURI(fileName)+'&code='+code,
                prepareCallback : function(url) {

                },
                failCallback : function(html, url) {
                    mini.alert('下载错误:'+html,'系统提示');
                }
            });
        } else mini.alert('未发现可导出的数据。');
    }
}
function singleItem(){
    this.SHENQINGRXM="";
    this.FAMINGMC="";
    this.ZHUANLIMC="";
    this.SHENQINGH="";
    this.SHENQINGLX="";
    this.FAMINGRXM="";
    this.otypeText="";
    this.TONGZHISMC="";
}
function ZLObject(){
    this.Rows=[];
}
ZLObject.parse=function(rows){
    var g=new ZLObject();
    var types = {0:'发明专利',1:'新型专利',2:'外观专利'};
    function parseSingle(row,index){
        var item=new singleItem();
        item.SHENQINGRXM=row.SHENQINGRXM || "";
        item.FAMINGMC=row.FAMINGMC;
        item.SHENQINGH=row.SHENQINGH;
        item.SHENQINGLX=types[parseInt(row.SHENQINGLX)];
        item.FAMINGRXM=row.FAMINGRXM || "";
        item.otypeText=row.otypeText;
        item.TONGZHISMC=row.TONGZHISMC || "";
        return item;
    }
    for(var i=0;i<rows.length;i++){
        var row=rows[i];
        var r=parseSingle(row,i+1);
        g.Rows.push(r);
    }
    return g;
}