<#include "/shared/dialog.ftl">

<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<div class="mini-fit" id="ConFit">
    <table class="layui-table" id="NBForm">
        <tr>
            <td class="showlabel">内部编号</td>
            <td colspan="4">
                <input id="ipNbbh" enabled="false" class="mini-textbox" style="width:100%" value="${NBBH}"/>
            </td>
        </tr>
        <tr>
            <td class="showlabel">销售人员</td>
            <td>
                <input class="mini-treeselect" onvaluechanged="showResult" valuefield="FID" parentfield="PID"
                       textfield="Name"
                       url="/systems/dep/getAllLoginUsersByDep" id="ipXs" name="XS" style="width: 100%;"
                       resultastree="false" allowInput="true" value="${XS}" expandonload="true"/>
            </td>
            <td class="showlabel">技术人员</td>
            <td>
                <input class="mini-treeselect" onvaluechanged="showResult" valuefield="FID" parentfield="PID"
                       textfield="Name"
                       url="/systems/dep/getAllLoginUsersByDep" id="ipJs" name="JS" style="width: 100%;"
                       resultastree="false" allowInput="true" value="${JS}" expandonload="true"/>
            </td>
        </tr>
        <tr>
            <td class="showlabel">流程人员
            </td>
            <td>
                <input class="mini-treeselect" onvaluechanged="showResult" valuefield="FID" parentfield="PID"
                       textfield="Name"
                       url="/systems/dep/getAllLoginUsersByDep" id="ipLc" name="LC" style="width: 100%;"
                       resultastree="false" allowInput="true" value="${LC}" expandonload="true"/>
            </td>
            <td class="showlabel">专利外协</td>
            <td>
                <input class="mini-treeselect" onvaluechanged="showResult();" valuefield="FID" parentfield="PID"
                       textfield="Name"
                       url=url="/permission/roleClass/getAllUserByRole?RoleID=15" id="ipZc" name="ZC" style="width: 100%;"
                       resultastree="false" allowinput="true" expandonload="true" valueFromSelect="true"
                       value="${ZC!}"/>
            </td>
        </tr>
        <tr>
            <td class="showlabel">客户
            </td>
            <td >
                <input name="KH" textname="KH" class="mini-buttonedit" style="width:100%" onbuttonclick="onCustomDialog"
                       allowinput="false" value="${KH}" text="${KH}"   onvaluechanged="showResult"/>
            </td>
            <td class="showlabel">立案编号
            </td>
            <td>
                <input name="BH" textname="BH" class="mini-textbox" style="width:100%" allowinput="false"
                       value="${BH}"    onvaluechanged="showResult"/>
            </td>
        </tr>
    </table>
</div>
<div class="mini-toolbar" id="UploadFileToolbar" style="text-align:center;height:60px;">
    <a class="mini-button mini-button-primary" style="margin-top: 10px;" onclick="doSave();">确认并保存</a>
    <a class="mini-button mini-button-danger"  style="margin-left:360px;margin-top: 10px;" onclick="closeMe()">取消并退出</a>
</div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var users1=${Users};
        var subId="${SubID}";
        var users = {};
        for (var item1 in users1) {
            users[users1[item1]] = item1;
        }
        var nbForm=new mini.Form('#NBForm');
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
                            var cnames = [];
                            for (var i = 0; i < data.length; i++) {
                                cnames.push(data[i].Name);
                            }
                            if (cnames) {
                               var con=mini.getbyName('KH');
                               if(con){
                                   con.setValue(cnames.join(','));
                                   con.setText(cnames.join(','));
                                   showResult();
                               }
                            }
                        }
                    }
                }
            });
        }
        function showResult(){
            var datas = mini.clone(nbForm.getData());
            var fields=['XS','JS','LC','ZC','KH','BH'];
            var ps = [];
            for(var i=0;i<fields.length;i++){
                var p=fields[i];
                var oData=datas[p];
                if (oData) {
                    if(oData.toString().toLowerCase()=="null") continue;
                    var num=parseInt(oData);
                    if(num==0) continue;
                    if (p == "KH" || p=="BH") {
                        var g = p + ":" + oData;
                        ps.push(g);
                    }
                    else {
                        var values =oData.split(',')
                        var texts = [];
                        for (var n = 0; n < values.length; n++) {
                            var val = values[n];
                            var text = users[val];
                            if(text)texts.push(text);
                        }
                        var g = p + ":" + texts.join(',');
                        ps.push(g);
                    }
                }
            }
            mini.get('ipNbbh').setValue(ps.join(';'));
        }
        function doSave(){
            mini.confirm('确认要更新内部编号？','系统提示',function(act){
                showResult();
                var arg={NBBH:mini.get('ipNbbh').getValue(),SubID:subId};
                var url='/addNBBH/save';
                $.post(url,arg,function(result){
                    if(result.success){
                        mini.alert('保存成功!','系统提示',function(){
                            CloseOwnerWindow('ok');
                        });

                    } else {
                         mini.alert(result.message || "保存失败，请稍候重试!");
                    }
                })

            });
        }
        function closeMe(){
            CloseOwnerWindow('no');
        }
    </script>
</@js>
