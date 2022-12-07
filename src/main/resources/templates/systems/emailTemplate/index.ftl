<#include "/shared/layout.ftl">
<@layout>
<style>
    .bbbb{

        font-family: 黑体;font-size:14px;
    }
</style>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="west" width="250" title="模版列表">
            <ul id="leftTree" onnodeclick="onDepChange" expandonload="true" class="mini-tree" idField="id"
                parentfield="pid" textField="text" url="/systems/emailTemplate/getTemplateTypes"
                resultAsTree="false"></ul>
        </div>
        <div region="center">
            <div class="mini-toolbar">
                <a class="mini-button" id="EmailTemplate_Add" iconcls="icon-add" onclick="add()" plain="true">新增</a>
                <a class="mini-button" id="EmailTemplate_Remove" iconcls="icon-remove" onclick="remove()"
                   plain="true">删除</a>
                <a class="mini-button" id="EmailTemplate_Save" iconcls="icon-save" onclick="save()" plain="true">保存</a>
                <span class="separator"></span>
                <a class="mini-button" id="EmailTemplate_Config" iconcls="icon-user" onclick="config()"
                   plain="true">设置</a>
                <a class="mini-button EmailTemplate_Config" id="EmailTemplate_PreView" iconcls="icon-print" onclick="preview()"
                   plain="true">预览</a>

            </div>
            <div class="mini-fit">
                <textarea id="editor" name="CONTENT"  class="mini-textarea" inputStyle="font-family:
                 黑体; font-size:16px;" style="width:100%;height:100%;" spellcheck="false" ></textarea>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var tree=mini.get('leftTree');
        var editor=mini.get('editor');
        var curTemplateId=null;
        function  onDepChange(e){
            var node=e.node;
            if(!node) return ;
            var temId=node.id;
            curTemplateId=temId;
            getAndShowTemplate(temId);
        }
        function getAndShowTemplate(id){
            var dx=mini.loading('正在加载模版数据......');
            var url='/systems/emailTemplate/getById';
            $.getJSON(url,{Id:id},function (result) {
                mini.hideMessageBox(dx);
                if(result.success){
                    var pp=result.data || "";
                    if(pp){
                        editor.setValue(pp);
                    } else editor.setValue('');
                }else editor.setValue(result.message || "加载模版数据失败，请输入模版内容后进行保存。");
            });
        }
        function add(){
            mini.showMessageBox({
                title:'新增邮件模版',
                html:'<div id="EmailTemplateForm">\n' +
                '    <input class="mini-textbox" required="true" style="width:100%" label="模版代码:" labelField="true" ' +
                'name="TemCode" ' +
                '/>\n' +
                '    <input class="mini-textbox" required="true" style="width:100%" label="模版名称:" labelField="true" ' +
                'name="TemName" ' +
                '/>\n' +
                '</div>',
                buttons:['ok','no'],
                height:150,
                width:400,
                callback:function(action){
                    if(action=="ok"){
                        var form=new mini.Form('#EmailTemplateForm');
                        form.validate();
                        if(form.isValid()){
                            var dd=form.getData();
                            var url='/systems/emailTemplate/addNew';
                            $.post(url,dd,function(result){
                                if(result.success){
                                    mini.alert('新增模版成功!','系统提示',function () {
                                        tree.load('/systems/emailTemplate/getTemplateTypes');
                                    })
                                } else {
                                    mini.alert('新增模版失败','系统提示');
                                }
                            });
                        }
                    }
                }
            });
            mini.parse();
        }
        function remove(){
            if(curTemplateId){
                mini.confirm('本操作会删作当前邮件内容模版，是否继续？','删除提示',function (btn) {
                    if(btn=='ok'){
                        $.post('/systems/emailTemplate/remove',{Id:curTemplateId},function (res) {
                           if(res.success){
                               mini.alert('选择邮件模版内容删除成功','删除提示',function () {
                                   tree.load('/systems/emailTemplate/getTemplateTypes');
                                   editor.setValue('');
                               });
                           } else {
                               mini.alert(res.messsage || "删除失败，请稍候重试。");
                           }
                        });
                    }
                })
            }
        }
        function save(){
            if(curTemplateId){
                mini.confirm('本操作会替换当前邮件内容模版，是否继续？','保存提示',function (btn) {
                    if(btn=='ok'){
                        var agg={Id:curTemplateId,Content:editor.getValue()};
                        $.post('/systems/emailTemplate/save',agg,function (res) {
                            if(res.success){
                                mini.alert('邮件模版内容保存成功','保存提示',function () {
                                    tree.load('/systems/emailTemplate/getTemplateTypes');
                                    editor.setValue('');
                                });
                            } else {
                                mini.alert(res.messsage || "保存失败，请稍候重试。");
                            }
                        });
                    }
                })
            }
        }
        function config(){

        }
        function preView(){

        }
    </script>
</@js>