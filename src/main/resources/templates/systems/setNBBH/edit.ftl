<#include "/shared/dialog.ftl">
<@layout>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>内部编号信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="NBBH_Save" onclick="NBBH_Copy()">复制内部编号</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="内部编号设置" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="editform">
                        <table style="width: 100%; height: 100%">
                            <tr>
                                <td class="showlabel">内部编号</td>
                                <td colspan="4">
                                    <input id="ipNbbh" enabled="false" class="mini-textbox" style="width:100%" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                            <tr>
                                <td class="showlabel">销售人员</td>
                                <td>
                                    <input class="mini-treeselect" onvaluechanged="showResult" valuefield="FID" parentfield="PID" textfield="Name"
                                           url="/systems/dep/getAllLoginUsersByDep" id="ipXs" name="XS" style="width: 100%;"
                                           resultastree="false" allowInput="true"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">技术人员</td>
                                <td>
                                    <input class="mini-treeselect" onvaluechanged="showResult" valuefield="FID" parentfield="PID" textfield="Name"
                                           url="/systems/dep/getAllLoginUsersByDep" id="ipJs" name="JS" style="width: 100%;"
                                           resultastree="false" allowInput="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">流程人员
                                </td>
                                <td>
                                    <input class="mini-treeselect" onvaluechanged="showResult" valuefield="FID" parentfield="PID" textfield="Name"
                                           url="/systems/dep/getAllLoginUsersByDep" id="ipLc" name="LC" style="width: 100%;"
                                           resultastree="false" allowInput="true"/>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">销售支持人</td>
                                <td>
                                    <input class="mini-treeselect" onvaluechanged="showResult" valuefield="FID" parentfield="PID" textfield="Name"
                                           url="/systems/dep/getAllLoginUsersByDep" id="ipZc" name="ZC" style="width: 100%;"
                                           resultastree="false" allowinput="true" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">客户
                                </td>
                                <td colspan="4">
                                    <input name="KH" textname="KH" class="mini-buttonedit" style="width:100%" onbuttonclick="onCustomDialog" allowinput="false" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var users1=${Users};
        var formData=${LoadData};
        var form=new mini.Form("#editform");
        form.setData(formData);
        var users2 = {};
        for (var item1 in users1) {
            users2[users1[item1]] = item1;
        }
        //提交国知局的专利协作的快捷内部编号加载
        showResult();
        function showResult() {
            var form = new mini.Form("#editform");
            var datas = mini.clone(form.getData());
            var ps = [];
            for (var p in datas) {
                if (p && datas[p]) {
                    if (p == "KH") {
                        var g = p + ":" + datas[p];
                        ps.push(g);
                    }
                    else {
                        var values = datas[p].split(',')
                        var texts = [];
                        for (var n = 0; n < values.length; n++) {
                            var val = values[n];
                            var text = users2[val];
                            texts.push(text);
                        }
                        var g = p + ":" + texts.join(',');
                        ps.push(g);
                    }
                }
            }
            mini.get('ipNbbh').setValue(ps.join(';'));
        }
        function onCustomDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/finance/ClientWindow/index",
                title: "选择客户",
                width: 650,
                height: 380,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            var form = new mini.Form("#editform");
                            var cnames = [];
                            for (var i = 0; i < data.length; i++) {
                                cnames.push(data[i].Name);
                            }
                            if (cnames) {
                                form.setData({ 'KH': cnames.join(',') }, false);
                                showResult();
                            }
                        }
                    }
                }
            });
        }
        
        function NBBH_Copy() {
            // var NBBH=document.getElementById("ipNbbh");
            // NBBH.select();
            // document.execCommand("Copy");
            // mini.alert("复制成功！已将内部编号复制到粘贴板");

            var ssrsss = mini.get("ipNbbh").getValue();//获取文本
            var flag = copyText(ssrsss); //传递文本
            if(flag) {
                mini.alert("复制成功！已将内部编号复制到粘贴板");
            }else {
                mini.alert("复制失败！");
            }
        }

        function copyText(text) {
            var textarea = document.createElement("input");//创建input对象
            var currentFocus = document.activeElement;//当前获得焦点的元素
            document.body.appendChild(textarea);//添加元素
            textarea.value = text;
            textarea.focus();
            if(textarea.setSelectionRange)
                textarea.setSelectionRange(0, textarea.value.length);//获取光标起始位置到结束位置
            else
                textarea.select();
            try {
                var flag = document.execCommand("copy");//执行复制
            } catch(eo) {
                var flag = false;
            }
            document.body.removeChild(textarea);//删除元素
            currentFocus.focus();
            return flag;
        }
    </script>
</@layout>
