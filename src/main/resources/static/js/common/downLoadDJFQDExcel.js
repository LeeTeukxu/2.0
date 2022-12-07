function downLoadDJFQDExcel(grid){
    function init(){
        var rows=grid.getSelecteds();
        if(rows.length==0){
            mini.alert('请选择要导出年费记录!');
            return null;
        }
        var obj=ZLObject.parse(rows);
        return obj;
    }
    this.export=function(code,fileName,postResult){
        var FilePath="";
        if(postResult==null) postResult=init();
        if(postResult){
            // $.fileDownload('/excel/downLoadDJFQD', {
            //     httpMethod : 'POST',
            //     data : 'data='+mini.encode(postResult)+'&filename='+encodeURI(fileName)+'&code='+code,
            //     prepareCallback : function(url) {
            //         debugger;
            //         var a=1;
            //     },
            //     failCallback : function(html, url) {
            //         debugger;
            //         mini.alert('下载错误:'+html,'系统提示');
            //     }
            // });
            var arg={
                data:mini.encode(postResult),
                fileName:encodeURI(fileName),
                code:code
            };
            var url="/excel/downLoadDJFQD";
            $.post(url, arg,
                function (text) {
                    var res = mini.decode(text);
                    if (res.success) {
                        FilePath = res.message;
                    }
                }
            );
        } else mini.alert('未发现可导出的数据。');
    }
}

function singleItem(){
    this.INDEX=0;
    this.SHENQINGH="";
    this.ZHUANLIMC="";
    this.ZHUANLILX="";
    this.SHENQINGR="";
    this.ANJIANYWZT="";
    this.SHENQINGRXM="";
    this.FAMINGRXM="";
    this.YINGJIAOFYDM="";
    this.MONEY=0;
    this.SXMONEY=0;
    this.SHOU=0;
}
function ZLObject(){
    this.Rows=[];
    this.TOTAL=0;
    this.TOTALSHOU=0;
    this.TOTALGUAN=0;
}
ZLObject.parse=function(rows){
    var g=new ZLObject();
    var types = {0:'发明专利',1:'新型专利',2:'外观专利'};
    function parseSingle(row,index){
        var item=new singleItem();
        item.INDEX=index;
        item.SHENQINGH=row.SHENQINGH;
        item.SHENQINGRXM=row.SHENQINGRXM;
        item.YINGJIAOFYDM=row.FEENAME;
        item.ZHUANLIMC=row.FAMINGMC;
        item.SHENQINGR=(mini.formatDate(row.SHENQINGR,'yyyy-MM-dd') || "").toString().split('T')[0];
        item.MONEY=parseFloat(row.XMONEY || row.MONEY) || 0;
        item.ZHUANLILX=types[parseInt(row.SHENQINGLX)];
        item.SHENQINGRXM=row.SHENQINGRXM || "";
        item.ANJIANYWZT=row.ANJIANYWZT || "";
        item.SXMONEY=parseFloat(row.SXMONEY ||0);
        return item;
    }
    for(var i=0;i<rows.length;i++){
        var row=rows[i];
        var r=parseSingle(row,i+1);
        g.Rows.push(r);
        g.TOTALGUAN+=r.MONEY;
        g.TOTALSHOU+=r.SXMONEY;
    }
    g.TOTAL=g.TOTALSHOU+g.TOTALGUAN;
    return g;
}