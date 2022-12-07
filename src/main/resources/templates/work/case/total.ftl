<#include "/shared/layout.ftl">
<@layout>
 <style type="text/css">
     .ddd{
         background-color: #EEF3FF
     }
 </style>
 <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
    <#include "/shared/casesHeaderNew.ftl">
<div class="mini-fit" style="overflow:hidden;">
    <iframe id="mainFrame1" name="mainFrame1" frameborder="0" scrolling="no" marginheight="0" marginwidth="0"
            width="100%"
            height="98%"
            style="background: white;" src="/work/casesAll/index?pageName=CasesBrowse&MenuID=25"  onload="closeLoading();"></iframe>
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
            var tt="&pageName=CasesBrowse&MenuID=25";
            if (state==1) {
                frame.attr('src', '/work/cases/index?State='+state+tt);
                change(1);
                colorhuanyuan();
                var j1=document.getElementById("J1");
                var j1span=document.getElementById("J1span");
                var j1h4=document.getElementById("J1h4");
                j1.style.cssText="background-color:  rgba(241, 112, 46, 0.84)";
                j1span.style.cssText="color:#fff";
                j1h4.style.cssText="color:#fff";
            }else if(state==2){
                frame.attr('src', '/work/cases/index?State='+state+tt);
                change(1);
                colorhuanyuan();
                var j2=document.getElementById("J2");
                var j2span=document.getElementById("J2span");
                var j2h4=document.getElementById("J2h4");
                j2.style.cssText="background-color:  rgba(241, 112, 46, 0.84)";
                j2span.style.cssText="color:#fff";
                j2h4.style.cssText="color:#fff";
            }else if(state==3){
                frame.attr('src', '/work/cases/index?State='+state+tt);
                change(1);
                colorhuanyuan();
                var j3=document.getElementById("J3");
                var j3span=document.getElementById("J3span");
                var j3h4=document.getElementById("J3h4");
                j3.style.cssText="background-color:  rgba(241, 112, 46, 0.84)";
                j3span.style.cssText="color:#fff";
                j3h4.style.cssText="color:#fff";
            }
            else if(state==4){
                frame.attr('src', '/work/cases/index?State='+state+tt);
                change(1);
                colorhuanyuan();
                var j4=document.getElementById("J4");
                var j4span=document.getElementById("J4span");
                var j4h4=document.getElementById("J4h4");
                j4.style.cssText="background-color:  rgba(241, 112, 46, 0.84)";
                j4span.style.cssText="color:#fff";
                j4h4.style.cssText="color:#fff";
            }
            else if (state == 5 ) {
                frame.attr('src','/work/casesTech/index?State='+state+tt);
                change(2);
            }else if(state == 6){
                frame.attr('src','/work/casesTech/index?State='+state+tt);
                change(2);
                colorhuanyuan();
                var Js2=document.getElementById("Js2");
                var Js2span=document.getElementById("Js2span");
                var Js2h4=document.getElementById("Js2h4");
                Js2.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
                Js2span.style.cssText="color:#fff";
                Js2h4.style.cssText="color:#fff";
            }
            else if(state==61 ){
                frame.attr('src','/work/casesCommit/index?State='+(state-60)+tt);
                change(3);
                colorhuanyuan();
                var jsh1=document.getElementById("jsh1");
                var jsh1span=document.getElementById("jsh1span");
                var jsh1h4=document.getElementById("jsh1h4");
                jsh1.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
                jsh1span.style.cssText="color:#fff";
                jsh1h4.style.cssText="color:#fff";
            }else if(state==62){
                frame.attr('src','/work/casesCommit/index?State='+(state-60)+tt);
                change(3);
                colorhuanyuan();
                var jsh2=document.getElementById("jsh2");
                var jsh2span=document.getElementById("jsh2span");
                var jsh2h4=document.getElementById("jsh2h4");
                jsh2.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
                jsh2span.style.cssText="color:#fff";
                jsh2h4.style.cssText="color:#fff";
            }
            else if(state==63){
                frame.attr('src','/work/casesCommit/index?State='+(state-60)+tt);
                change(3);
                colorhuanyuan();
                var jsh3=document.getElementById("jsh3");
                var jsh3span=document.getElementById("jsh3span");
                var jsh3h4=document.getElementById("jsh3h4");
                jsh3.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
                jsh3span.style.cssText="color:#fff";
                jsh3h4.style.cssText="color:#fff";
            }
            else if(state==64){
                frame.attr('src','/work/casesConfirm/index?State='+(state-60)+tt);
                change(3);
                colorhuanyuan();
                var jsh5=document.getElementById("jsh5");
                var jsh5span=document.getElementById("jsh5span");
                var jsh5h4=document.getElementById("jsh5h4");
                jsh5.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
                jsh5span.style.cssText="color:#fff";
                jsh5h4.style.cssText="color:#fff";
            }
            else if(state==7){
                frame.attr('src','/work/casesComplete/index?State='+state+tt);
                change(0);
                colorhuanyuan();
                var z2=document.getElementById("Z2");
                z2.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }else if(state==8){
                frame.attr('src','/work/casesComplete/index?State='+state+tt);
                change(0);
                colorhuanyuan();
                var z3=document.getElementById("Z3");
                z3.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }
            else if(state==9){
                frame.attr('src','/work/casesAll/index?pageName=CasesBrowse&MenuID=25');
                 change(0);
                colorhuanyuan();
                var z1=document.getElementById("Z1");
                z1.style.cssText="background-color: rgba(247, 140, 24, 0.85);";
            }
            window.parent.doResize();
        }

        function colorhuanyuan() {
            var a=$(".Jdlcli");
            for(var i=0;i<a.length;i++){
                a[i].style.cssText="background-color:rgb(226,250,252)";
            };
            var b=$(".Jsjdli");
            for(var i=0;i<b.length;i++){
                b[i].style.cssText="background-color:rgb(230,238,251)";
            };
            var c=$(".ZLywli");
            for(var i=0;i<c.length;i++){
                c[i].style.cssText="background-color:rgb(63,87,131)";
            };
            var d=$(".Jsshli");
            for(var i=0;i<d.length;i++){
                d[i].style.cssText="background-color:rgb(232,230,252)";
            };
            document.getElementById("J1span").style.cssText="color:rgb(0,159,205)";
            document.getElementById("J1h4").style.cssText="color:rgb(1,160,202)";
            document.getElementById("J2span").style.cssText="color:rgb(0,159,205)";
            document.getElementById("J2h4").style.cssText="color:rgb(1,160,202)";
            document.getElementById("J3span").style.cssText="color:rgb(0,159,205)";
            document.getElementById("J3h4").style.cssText="color:rgb(1,160,202)";
            document.getElementById("J4span").style.cssText="color:rgb(0,159,205)";
            document.getElementById("J4h4").style.cssText="color:rgb(1,160,202)";
            document.getElementById("Js1span").style.cssText="color:rgb(53,102,231)";
            document.getElementById("Js1h4").style.cssText="color:rgb(51,97,232)";
            document.getElementById("Js2span").style.cssText="color:rgb(53,102,231)";
            document.getElementById("Js2h4").style.cssText="color:rgb(51,97,232)";
            document.getElementById("jsh1span").style.cssText="color:rgb(53,102,231)";
            document.getElementById("jsh1h4").style.cssText="color:rgb(52,101,232)";
            document.getElementById("jsh2span").style.cssText="color:rgb(53,102,231)";
            document.getElementById("jsh2h4").style.cssText="color:rgb(52,101,232)";
            document.getElementById("jsh3span").style.cssText="color:rgb(53,102,231)";
            document.getElementById("jsh3h4").style.cssText="color:rgb(52,101,232)";
            document.getElementById("jsh4span").style.cssText="color:rgb(53,102,231)";
            document.getElementById("jsh4h4").style.cssText="color:rgb(52,101,232)";
            document.getElementById("jsh5span").style.cssText="color:rgb(53,102,231)";
            document.getElementById("jsh5h4").style.cssText="color:rgb(52,101,232)";

        }

        function toDoSomething(state){
            var tt="&pageName=CasesBrowse&MenuID=25";
            var frame = $('#mainFrame1');
            if(state==4){
                frame.attr('src','/work/casesTech/index?State=45'+tt);
                change(1);
                colorhuanyuan();
                var Js1=document.getElementById("Js1");
                var Js1span=document.getElementById("Js1span");
                var Js1h4=document.getElementById("Js1h4");
                Js1.style.cssText="background-color:  rgba(241, 112, 46, 0.84)";
                Js1span.style.cssText="color:#fff";
                Js1h4.style.cssText="color:#fff";
            }
            else if(state==63){
                frame.attr('src','/work/casesConfirm/index?State='+(state-60)+tt);
                change(3);
                colorhuanyuan();
                var jsh4=document.getElementById("jsh4");
                var jsh4span=document.getElementById("jsh4span");
                var jsh4h4=document.getElementById("jsh4h4");
                jsh4.style.cssText="background-color: rgba(241, 112, 46, 0.84)";
                jsh4span.style.cssText="color:#fff";
                jsh4h4.style.cssText="color:#fff";
            }
            window.parent.doResize();
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
            var url='/work/cases/getStateNumbers?Key='+key;
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
    </script>
</@js>