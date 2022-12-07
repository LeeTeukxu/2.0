<#include "/shared/layout.ftl">
<@layout>
 <style type="text/css">
     .ddd{
         background-color: #EEF3FF
     }
 </style>
 <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
    <#include "/shared/caseHeaderHigh.ftl">
<div class="mini-fit" style="overflow:hidden;">
    <iframe id="mainFrame1" name="mainFrame1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"
            width="100%"
            height="98%"
            style="background: white;" src="/caseHighAll/index?State=9&pageName=CaseHighBrowse&MenuID=24"  onload="closeLoading();
"></iframe>
</div>
    <div class="mini-window" title="待处理业务数量统计" width="400" height="400" id="WaitWindow" style="display:none">
        人员类型：<div class="mini-combobox" data="[{text:'技术人员',id:'Tech'},{text:'商务人员',id:'Bus'}]" valueFromSelect="true"
                  onvaluechanged="typeChanged" style="width:80%;margin:0px;padding:0px" value="Tech"></div>
        <div class="mini-fit">
            <div autoload="false" class="mini-datagrid" style="width:100%;height:100%" id="WaitGrid"
                 url="/caseHighTech/getWaitReport" sortField="Num" sortOrder="desc" showPager="false">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="Name" width="150" allowSort="true">人员姓名</div>
                    <div field="Num" width="120" allowSort="true">待处理业务(笔)</div>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var panel0="#info0";
        var panel1='#info1';
        var panel2='#info2';
        var panel3='#info3';
        var ps=[panel0,panel1,panel2,panel3];
        function doSomething(state,obj) {
            var frame = $('#mainFrame1');
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '正在加载数据，请稍候...'
            });
            var tt="&pageName=CaseHighBrowse&MenuID=24";
            if (state==1) {
                frame.attr('src', '/caseHigh/index?State='+state+tt);
                colorhuanyuan();
                changeOne(1)
            }else if(state==2){
                frame.attr('src', '/caseHigh/index?State='+state+tt);
                colorhuanyuan();
                changeOne(2)
            }else if(state==3){
                frame.attr('src', '/caseHigh/index?State='+state+tt);
                colorhuanyuan();
                changeOne(3)
            }
            else if(state==4){
                frame.attr('src', '/caseHigh/index?State='+state+tt);
                change(1);
                colorhuanyuan();
                changeOne(4)
            }
            else if (state == 50 ) {
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(1);
            }else if(state == 51){
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(2);
            }
            else if(state==52){
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(3)
            }
            else if(state==53){
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(5)
            }
            else if(state==54){
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(4)
            }
            else if(state==55){
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(7);
            }
            else if(state==56){
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(6);
            }
            else if(state==57 ){
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(9);
            }
            else if (state == 58) {
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(8);
            }
            else if (state == 90) {
                frame.attr('src','/caseHighTech/index?State='+state+tt);
                colorhuanyuan();
                changeSomeDom(0);
            }
            else if(state==9){
                frame.attr('src','/caseHighAll/index?State='+state+tt);
                colorhuanyuan();
                var z1=document.getElementById("J9");
                z1.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }
            window.parent.doResize();
        }

        function colorhuanyuan() {
            var a=$(".Jdlcli");
            for(var i=0;i<a.length;i++){
                a[i].style.cssText="background-color:rgb(230,238,251)";
            };
            var b=$(".Jsjdli");
            for(var i=0;i<b.length;i++){
                b[i].style.cssText="background-color:rgb(214, 212, 251)";
            };
            try {
                for(var i=1;i<=9;i++){
                    var a1=document.getElementById("J"+i+"span");
                    if(a1)a1.style.cssText="color:rgb(0,159,205)";
                    var b1= document.getElementById("J"+i+"h4");
                    if(b1)b1.style.cssText="color:rgb(1,160,202)";

                    var a2=document.getElementById("Js"+i+"span");
                    if(a2)a2.style.cssText="color:rgb(53,102,231)";
                    var b2=document.getElementById("Js"+i+"h4");
                    if(b2)b2.style.cssText="color:rgb(51,97,232)";
                    var a3=document.getElementById("jsh"+i+"span");
                    if(a3)a3.style.cssText="color:rgb(53,102,231)";
                    var b3=document.getElementById("jsh"+i+"h4");
                    if(b3)b3.style.cssText="color:rgb(52,101,232)";
                }
            }catch(e){

            }
        }
        function changeOne(index){
            var j1=document.getElementById("J"+index);
            var j1span=document.getElementById("J"+index+"span");
            var j1h4=document.getElementById("J"+index+"h4");
            if(j1)j1.style.cssText="background-color:  rgba(241, 112, 46, 0.84)";
            if(j1span)j1span.style.cssText="color:#fff";
            if(j1h4)j1h4.style.cssText="color:#fff";
        }
        function changeSomeDom(index){
            var Js2=document.getElementById("Js"+index);
            var Js2span=document.getElementById("Js"+index+"span");
            var Js2h4=document.getElementById("Js"+index+"h4");
            if(Js2)Js2.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
            if(Js2span)Js2span.style.cssText="color:#fff";
            if(Js2h4)Js2h4.style.cssText="color:#fff";
        }
        function changeSomeEx(index){
            change(3);
            colorhuanyuan();
            var jsh1=document.getElementById("jsh"+index);
            var jsh1span=document.getElementById("jsh"+index+"span");
            var jsh1h4=document.getElementById("jsh"+index+"h4");
            jsh1.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
            jsh1span.style.cssText="color:#fff";
            jsh1h4.style.cssText="color:#fff";
        }
        function closeLoading() {
            setTimeout(function(){mini.unmask(document.body);},500)
        }
        function change(index){
            for(var i=0;i<ps.length;i++){
                if(i==index){
                    $(ps[i]).addClass('mini-panel-primary');
                } else $(ps[i]).removeClass('mini-panel-primary');
            }
        }
        $(function(){
            $('.file-box-left').click(function(){
                $('.file-box-left').each(function(index,dom){
                    $(dom).removeClass('ddd');
                });
                $(this).addClass('ddd');
            });
        })

        function updateStateNumbers(){
            var key=(new Date()).getTime();
            var url='/caseHigh/getStateNumbers?Key='+key;
            $.getJSON(url,{},function(result){
                if(result.success){
                    var states=result.data || {};
                    for(var state in states){
                        var con=$('.x'+state);
                        if(con.length>0){
                            con.text(states[state]);
                        }
                    }
                }
            });
        }
        function refreshData(grid){
            try
            {
                var frame=document.getElementById('mainFrame1');
                if(frame){
                    frame.contentWindow.refreshData();
                }
            }catch(e){

            }
        }
        function  showWait(){
            var win=mini.get('WaitWindow');
            var grid=mini.get('WaitGrid');
            win.show();
            grid.load({Type:'Tech'});
        }
        function typeChanged(e){
            var val=e.value;
            var grid=mini.get('WaitGrid');
            grid.load({Type:val});
        }
    </script>
</@js>