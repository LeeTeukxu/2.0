<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="west" title="角色列表" width="200">
            <div class="mini-tree" id="BottomTree" style="width:100%;height:100%" url='/permission/roleClass/getAllCanUse'
                 expandonload="true" onnodeclick="onRoleClick" resultAsTree="false" autoLoad="true"></div>
        </div>
        <div region="center">
            <div class="mini-toolbar">
                <a class="mini-button" id="tbMenuPermissionItem_Save" iconcls="icon-save" onclick="save"
                   plain="true">保存</a>
            </div>
            <div class="mini-fit">
                <div id="treegrid1" class="mini-treegrid" style="width:100%;height:100%;" treeColumn="title"
                     idField="fid" parentField="pid" resultAsTree="false"
                     allowResize="true" expandOnLoad="true" showTreeIcon="true" allowSelect="false"
                     allowCellSelect="false" enableHotTrack="false"
                     ondrawcell="onDrawCell" allowCellWrap="true" autoLoad="false"
                     url="/permission/rolepermission/getData">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div name="title" field="title" width="200" headeralign="center">模块名称</div>
                        <div name="action" field="action" width="120" headeralign="center">操作</div>
                        <div field="Functions" width="100%" headeralign="center">操作权限</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
   <script type="text/javascript">
       var tree = null, url = "", topTree = null, bottomTree = null, currentRole = null;
       $(function () {
           tree = mini.get('#treegrid1');
           topTree = mini.get('#TopTree');
           bottomTree = mini.get('#BottomTree');

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

       function onDrawCell(e) {
           if (!tree) tree = mini.get('treegrid1');
           var field = e.field;
           var record = e.record;
           var id = record["_id"];
           var chs = record['children'] || [];
           if (field == "action" && chs.length == 0) {
               e.cellHtml = "<button class='mini-button mini-button-success'  style='height:20px;margin-left:5px;width:45px' id='SelectAll' onclick='checkAll(" + id + ")'>全选</button>&nbsp;&nbsp;<button class='mini-button mini-button-info' style='height:20px;width:45px' id='SelectNot' onclick='checkNot(" + id + ")' >反选</button>";
           }
           else if (field == 'Functions') {
               var functions = record['Functions'] || [];
               var checkeds = record['Checkeds'] || [];
               if (functions.length > 0) {
                   var p = [];
                   var menuId = record.fId;
                    var allText="";
                   for (var i = 0; i < functions.length; i++) {
                       var fun = functions[i];
                       var funId = parseInt(fun.id);
                       var funText = fun.text;
                       var checked = checkeds.indexOf(funId) > -1;
                       var cs = checked ? 'checked' : '';
                       fun.checked = checked;
                       var g = '<label style="margin-right:10px;margin-right:10px; vertical-align:bottom;"><input ' +
                               'type="checkbox" name="' + menuId + '_' + funId + '"' + cs + ' hidefocus onclick="checkAction(' + id + ',' + funId + ',this.checked)" />' + funText + '</label>';
                       allText+=funText;
                       if(allText.length>40){
                           g=g+'<br/>';
                           allText="";
                       }
                       p.push(g);
                   }
                   if (p.length > 0) e.cellHtml = p.join(' ');
               }
           }
       }

       function checkAction(recId, funId, checked) {
           if (!tree) tree = mini.get('treegrid1');
           var record = tree.getby_id(recId);
           if (!record) return;
           var funs = record.Functions || [];
           for (var i = 0; i < funs.length; i++) {
               var fun = funs[i];
               if (fun.id == funId) {
                   fun.checked = checked;
                   break;
               }
           }
       }

       function save() {
           var res = {}, count = 0;
           var data = tree.getAllChildNodes();
           for (var i = 0; i < data.length; i++) {
               var node = data[i];
               if (node['children']) continue;
               var menuId = node['fid'];
               var funs = node['Functions'] || [];
               if (funs.length > 0) {
                   var p = [];
                   count++;
                   for (var n = 0; n < funs.length; n++) {
                       var fun = funs[n];
                       if (fun.checked) {
                           p.push(fun.id);
                       }
                   }
                   res[menuId] = p;
               }
           }
           if (count > 0) {
               mini.confirm('确认要保存权限数据吗?', '系统提示', function (r) {
                   if (r == 'ok') {
                       var url = '/permission/rolepermission/save?roleId='+currentRole;
                       $.ajax({
                           contentType:'application/json',
                           method:'post',
                           url:url,
                           data:mini.encode(res),
                           success:function (r) {
                               if (r['success']) {
                                   mini.alert('保存成功!');
                                   res = undefined;
                                   data = undefined;
                               }
                               else mini.alert(r['message'] || "保存的失败，请稍候重试。");
                           },
                           failure:function (error) {
                               alert(error);
                           }
                       });
                   }
               });
           }
       }

       function onRoleClick(e) {
           var node = e.node;
           if (!tree) tree = mini.get('treegrid1');
           var isLeaf = (node['children'] || []).length == 0;
           if (node && isLeaf) {
               currentRole = parseInt(node.id);
               tree.load({roleId: currentRole});
           }
       }

       function checkAll(id) {
           if (!tree) tree = mini.get('treegrid1');
           var record = tree.getby_id(id);
           if (!record) return;
           var funs = record['Functions'] || [];
           var checkeds = record['Checkeds'] || [];
           if (funs.length > 0) {
               for (var i = 0; i < funs.length; i++) {
                   var fun = funs[i];
                   fun.checked = true;
                   var ck = fun['checked'];
                   var iid = parseInt(fun.id);
                   if (ck && checkeds.indexOf(iid) == -1) checkeds.push(iid);
               }
               record['Functions'] = funs;
               record['Checkeds'] = checkeds;
               tree.updateRow(record);
           }
       }

       function checkNot(id) {
           if (!tree) tree = mini.get('treegrid1');
           var record = tree.getby_id(id);
           if (!record) return;
           var funs = record['Functions'] || [];
           var checkeds = record['Checkeds'] || [];
           if (funs.length > 0) {
               for (var i = 0; i < funs.length; i++) {
                   var fun = funs[i];
                   var ck = fun['checked'];
                   var iid = parseInt(fun.id);
                   if (ck == null || ck == undefined) ck = false;
                   var rk = !ck;
                   if (!rk) checkeds.remove(iid);
                   if (rk && checkeds.indexOf(iid) == -1) checkeds.push(iid);
                   fun.checked = rk;
               }
               record['Functions'] = funs;
               record['Checkeds'] = checkeds;
               tree.updateRow(record);
           }
       }
   </script>
</@js>