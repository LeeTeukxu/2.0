<#include "/shared/layout.ftl">
<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
 <link href="/js/miniui/themes/default/large-mode.css" rel="stylesheet" type="text/css" />
<div class="mini-toolbar">
    <a class="mini-button" iconCls="icon-ok" onclick="confirm()">确认选择</a>
    <a class="mini-button" iconCls="icon-no">取消关闭</a>
</div>
<div class="mini-fit" style="width:100%;height:100%">
    <table style="width:100%;height:100%" class="layui-table">
    <#assign  x=1>
    <#list images as image>
    <#if  x==1>
    <tr>
    </#if>
       <td style="text-align:center">
           <#if (image?length>1)>
           <input type="radio" name="image" value="${image}" style="width:20px;height:20px"/>
           <img src="/appImages/${image}" style="width:100%;" onclick="doClick()"/>
           </#if>
           <#assign x=x+1>
       </td>
    <#if  x==5>
    </tr>
        <#assign x=1>
    </#if>
    </#list>
    </table>
</div>
<script type="text/javascript">
    function doClick(){
        $('input:radio').removeAttr('checked');
        var ele=event.srcElement || event.target;
        if(ele){
            var radio=$(ele).siblings();
            radio.click();
        }
    }
    function confirm(){
       var url= $('input:radio[name=image]:checked').val();
       if(url){
           CloseOwnerWindow('ok');
       } else mini.alert('请选择图标!');
    }
    function getData(){
        return   '/appImages/'+$('input:radio[name=image]:checked').val();
    }
    function setData(val){
        var cons=$('input:radio[name=image]');
        for(var i=0;i<cons.length;i++){
            var con=$(cons[i]);
            if(con.attr('value')==val){
                con.attr('checked',true);
            }
        }
    }
</script>
</@layout>