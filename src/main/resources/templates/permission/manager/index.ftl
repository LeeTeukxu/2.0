<#include "/shared/layout.ftl">
<@layout>
<style>
    .mini-layout-region-title {
        color: black
    }
</style>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="west" title="部门经理列表" width="300">
            <div class="mini-tree" id="LeftTree" style="width:100%;height:100%"
                 url='/permission/manager/getTree'
                 idField="FID" parentField="PID" textField="Name"
                 expandonload="true" onnodeclick="onManagerClick" resultAsTree="false" autoLoad="true"></div>
        </div>
        <div region="center">
            <div class="mini-toolbar">
                <a class="mini-button" id="ManagerPermission_Save" iconcls="icon-save" onclick="SaveAll"
                   plain="true">保存</a>
            </div>
            <div class="mini-fit">
                <div class="mini-layout" style="width:100%;height:100%">
                    <div region="west" title="部门列表" width="600" showCollapseButton="false">
                        <ul id="DepTree" style="width:100%;height:100%" expandonload="true" class="mini-tree"
                            idField="depId" showCheckBox="true"
                            parentfield="pid" textField="name" url="/systems/dep/getAllCanUse" resultAsTree="false">
                        </ul>
                    </div>
                    <div region="center" title="人员列表" showHeader="true" showCollapseButton="false">
                        <ul id="ManTree" class="mini-tree" url="/permission/manager/getAllUser" showCheckBox="true"
                            textField="Name" valueField="FID" parentField="PID" resultAsTree="false"
                            expandonload="true"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</@layout>
<@js>
   <script type="text/javascript">
       mini.parse();
       var depTree = null;
       var manTree = null;
       var leftTree = null;
       var currentManager=null;
       $(function () {
           depTree = mini.get('DepTree');
           manTree = mini.get('ManTree');
           leftTree = mini.get('LeftTree');
           depTree.hide();
           manTree.hide();
           var qText = mini.get('#QueryText');
           if (qText) {
               qText.on('enter', function () {
                   var val = qText.getValue();
                   if (val) {
                       bottomTree.filter(function (node) {
                           return node['text'].indexOf(val) > -1;
                       });
                   } else bottomTree.clearFilter();
               });
           }
       });

       function onManagerClick(e) {
           var node=leftTree.getSelectedNode();
           if(node){
               manTree.show();
               depTree.show();
               var userId=node.FID;
               currentManager=userId;
               var url='/permission/manager/getSavedConfig';
               $.getJSON(url,{UserID:userId},function(result){
                   if(result.success){
                       var datas=result.data ||[];
                       if(datas.length>0){
                           var dep=[];
                           var man=[];
                           for(var i=0;i<datas.length;i++){
                               var data=datas[i];
                               var ID=data["value"];
                               var type=data["type"];
                               if(type=="Dep")dep.push(ID);else man.push(ID);
                           }
                           depTree.setValue(dep.join(','));
                           manTree.setValue(man.join(','));

                       } else {
                           depTree.setValue(null);
                           manTree.setValue(null);
                       }
                   }
               })
           }
       }
       function SaveAll(){
           function g(){
               var depNodes=depTree.getCheckedNodes(false);
               var manNodes=manTree.getCheckedNodes(false);
               var mans=[];
               var deps=[];
               for(var i=0;i<depNodes.length;i++){
                   var depNode=depNodes[i];
                   var depId=parseInt(depNode["depId"]);
                   deps.push(depId);
               }
               for(var i=0;i<manNodes.length;i++){
                   var manNode=manNodes[i];
                   var manId=parseInt(manNode["FID"]);
                   mans.push(manId);
               }
               var arg={"Manager":currentManager,"Dep":deps.join(','),"Man":mans.join(',')};
               var url='/permission/manager/saveAll';
               $.post(url,{Data:mini.encode(arg)},function(result){
                    if(result.success){
                        mini.alert('权限设置保存成功!');
                    } else mini.alert(result.message || "权限设置保存失败!");
               })
           }
           mini.confirm('确认要保存信息？','系统提示',function (action) {
                if(action=='ok')g();
           });
       }
   </script>
</@js>