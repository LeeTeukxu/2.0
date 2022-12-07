<#include "/shared/layout.ftl">
<@layout>
 <style type="text/css">
     .ddd{
         background-color: #EEF3FF
     }
 </style>
 <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
    <#include "/shared/tradeCasesHeader.ftl">
<div class="mini-fit" style="overflow:hidden">
    <iframe id="mainFrame1" name="mainFrame1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"
            width="100%"
            height="99%"
            style="background: white;" src="/work/tradeCasesAll/index?State=9&pageName=TradeCases&MenuID=151"  onload="closeLoading();"></iframe>
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
        function doSomething(state) {
            var frame = $('#mainFrame1');
            mini.mask({
                el: document.body,
                cls: 'mini-mask-loading',
                html: '正在加载数据，请稍候...'
            });
            var tt="&pageName=CasesBrowse&MenuID=25";
            if (state==1) {
                frame.attr('src', '/work/tradeCases/index?State='+state+tt);
                change(0)
                colorhuanyuan();
                var J1=document.getElementById("Z2");
               J1.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            } else if (state == 2) {
                frame.attr('src','/work/tradeCases/index?State='+state+tt);
                change(0);
                colorhuanyuan();
                var J2=document.getElementById("Z3");
                J2.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }else if (state == 3) {
                frame.attr('src','/work/tradeCases/index?State='+state+tt);
                change(0);
                colorhuanyuan();
                var J3=document.getElementById("Z4");
                J3.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }else if (state == 4) {
                frame.attr('src','/work/tradeCases/index?State='+state+tt);
                change(0);
                colorhuanyuan();
                var J4=document.getElementById("Z5");
                J4.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }
            else if (state == 5) {
                colorhuanyuan();
                frame.attr('src','/work/tradeCasesTech/index?State='+state+tt);
                changeSomeEx(2)
            }
            else if (state == 6) {
                colorhuanyuan();
                frame.attr('src','/work/tradeCasesTech/index?State='+state+tt);
                changeSomeEx(3)
            }
            else if(state==64){
                frame.attr('src','/work/tradeCasesConfirm/index?State='+(state-60)+tt);
                change(3);
            }
            else if(state==7){
                colorhuanyuan();
                frame.attr('src','/work/tradeCasesTech/index?State='+state+tt);
                changeSomeEx(1)
            } else if(state==8){
                frame.attr('src','/work/tradeCasesComplete/index?State='+state+tt);
                change(0);
                colorhuanyuan();
                var Z3=document.getElementById("Z3");
                Z3.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }
            else if(state==9){
                frame.attr('src','/work/tradeCasesAll/index?State='+state+tt);
                change(0);
                colorhuanyuan();
                var z1=document.getElementById("Z1");
                z1.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }
        }
        function toDoSomething(state){
            var tt="&pageName=CasesBrowse&MenuID=25";
            var frame = $('#mainFrame1');
            if(state==4){
                frame.attr('src','/work/tradeCasesTech/index?State=45'+tt);
                change(1);
            }
            // else if(state==63){
            //     frame.attr('src','/work/casesConfirm/index?State='+(state-60)+tt);
            //     change(3);
            // }
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
        function changeOne(index){
            var j1=document.getElementById("J"+index);
            var j1span=document.getElementById("J"+index+"span");
            var j1h4=document.getElementById("J"+index+"h4");
            j1.style.cssText="background-color:  rgba(241, 112, 46, 0.84)";
            j1span.style.cssText="color:#fff";
            j1h4.style.cssText="color:#fff";
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
            var url='/work/tradeCases/getStateNumbers?Key='+key;
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
    </script>
</@js>
<script>
    function colorhuanyuan() {
        var c = $(".ZLywli");
        for (var i = 0; i < c.length; i++) {
            c[i].style.cssText = "background-color:rgb(63,87,131)";
        }
        ;
        var a=$(".Jsjdli");
        for(var i=0;i<a.length;i++){
            a[i].style.cssText="background-color:rgb(230,238,251)";
        };
    }
</script>