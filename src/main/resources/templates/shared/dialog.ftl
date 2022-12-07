<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-store, must-revalidate" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title></title>
    <script src="/js/boot.js" type="text/javascript"></script>
    <link href="/js/miniui/themes/default/large-mode.css" rel="stylesheet" type="text/css" />
    <link href="/js/miniui/themes/bootstrap/skin.css" rel="stylesheet" type="text/css" />
    <style>
        .baseinfo h1 {
            width: 98%;
            margin: 0 auto;
            font-size: 18px;
            font-family: 黑体;
            line-height: 30px;
            font-weight: 700;
            border-bottom: 1px solid #ddd;
        }

        .showlabel {
            width: 120px;
            text-align: center;
            font-family: 黑体;
            font-size: 14px;
            height: 35px;
            margin: 5px;
        }

        .blank {
            width: 80px;
            text-align: center;
        }
    </style>
     <#macro js>
         <#nested>
     </#macro>
</head>
<body style="width:100%;height:100%;overflow-x: hidden">
    <script type="text/javascript">
    mini.parse();
    var pageName = "${PageName?default('')}";
    var allFuns =${AllFuns?default('[]')};
    var hasFuns= ${HasFuns?default('[]')};
    $(function () {
        if (pageName) {
            for(var i=0;i<allFuns.length;i++){
                var fun=allFuns[i];
                if(hasFuns.indexOf(fun)==-1){
                    var conId=pageName+"_"+fun;
                    var con=mini.get(conId);
                    if(con){
                        con.hide();
                        con.show=function(){}
                        con.click=function(){
                            mini.alert('没有执行操作的权限!');
                        }
                    }
                    var cons=$('.'+conId);
                    for(var n=0;n<cons.length;n++){
                        var cn=cons[n];
                        if(!cn) continue;
                        var cbb=mini.get(cn);
                        if(cbb){
                            cbb.hide();
                            cbb.show=function(){}
                        } else {
                            $(cn).hide();
                            cn.show=function(){}
                        }
                    }
                }
            }
        }
    });
</script>
<#macro layout>
    <#nested>
    </body>
</html>
</#macro>