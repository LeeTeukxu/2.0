<#include "/shared/layout.ftl">
<@layout>
 <style type="text/css">
     .panel-expand {
         background-image: url(/js/miniui/themes/icons/collapse.gif);
     }

     .panel-collapse {
         background-image: url(/js/miniui/themes/icons/collapse.gif);
     }
 </style>
 <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
<div class="mini-fit" style="overflow:hidden">
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" bodyStyle="overflow:hidden">
            <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                <table style="width:100%;" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="width:100%;" id="toolbarContainer">
                            <a class="mini-button" iconcls="icon-upload" id="TradeCases_Audit"
                               visible="false" onclick="auditFile">提交国知局</a>
                            <a class="mini-button" iconCls="icon-cancel" id="TradeCases_Cancel"
                               visible="false" onclick="cancelAudit">取消申报</a>
                            <a class="mini-button" iconcls="icon-download" id="TradeCases_Download"
                               onclick="download">下载文件</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="TradeCases_NBBH"
                               onclick="NBBH">内部编号快捷键</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%"
                     showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick" sortfield="State"
                     sortorder="desc" pagesize="20" url="/work/tradeCasesCommit/getData?State=${State}"
                     autoload="true" onload="afterload">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div field="DocSN" headerAlign="center" align="center" width="120">业务流水号</div>
                        <div field="Nums" width="200" headeralign="center" align="center">业务类型及数量</div>
                        <div field="ContractNo" width="120" headeralign="center"  align="center" >合同编号</div>
                        <div field="ClientID" align="center" width="200" headeralign="center" type="treeselectcolumn">
                            客户名称
                            <input property="editor" class="mini-treeselect" url="/systems/client/getAllClientTree"/>
                        </div>
                        <div field="Type" headerAlign="center" align="center">专利类型</div>
                        <div field="CreateMan" headerAlign="center" align="center" type="treeselectcolumn">商务人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>

                        <div field="FileName" headerAlign="center" align="center" width="180">技术文件名称</div>
                        <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn">撰写人
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="UploadTime" headerAlign="center" align="center" width="120"
                             dataType="date" dateFormat="yyyy-MM-dd">文件上传时间
                        </div>
                        <div field="Memo" headerAlign="center" align="center" width="150">技术文件说明</div>
                        <div field="AuditText" headerAlign="center" align="center">核稿意见</div>
                        <div field="AuditMan" headerAlign="center" align="center" type="treeselectcolumn">文件核稿人
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" headerAlign="center" align="center" width="120" dataType="date"
                             dateFormat="yyyy-MM-dd">核稿时间
                        </div>

                        <div field="ShenBaoText" headerAlign="center" align="center">申报意见</div>
                        <div field="ShenBaoMan" headerAlign="center" align="center" type="treeselectcolumn">申报审核人
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="ShenBaoTime" headerAlign="center" align="center" width="120" dataType="date"
                             dateFormat="yyyy-MM-dd">申报审核时间
                        </div>
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
    var roleName = '${RoleName}';
    var userId = '${UserID}';
    var depId =${DepID};
    var grid = mini.get('datagrid1');
    var cmdAudit = mini.get('#TradeCases_Audit');
    var cmdCancel=mini.get('#TradeCases_Cancel');
    var results = [{id: '同意申报', text: '同意申报'}, {id: '不同意申报', text: '不同意申报'}];
    var results1 = [ {id: '不同意申报', text: '不同意申报'}];
    function onRowClick(e) {
        var record = e.record;
        var techManager = (record["TechManager"] || "").split(',');
        var lcMans = (record["LCManager"] ||"").split(",");
        lcMans.push(record["LCMan"].toString());
        var state = parseInt(record.State || 1);
        cmdCancel.hide();
        cmdAudit.hide();
        if (state == 4) {
            if (lcMans.indexOf(userId) > -1) {
                cmdAudit.hide();
                cmdCancel.show();
            }
        }
        else if (state == 3) {
            if (lcMans.indexOf(userId) > -1) {
                cmdAudit.show();
                cmdCancel.hide();
            }
        }
    }

    function auditFile() {
        var row = grid.getSelected();
        if (row) {
            var id = row.ID;
            mini.showMessageBox({
                html: ' <input labelField="true" label="是否申报" class="mini-combobox"  id="Result"  ' +
                'style="width:100%" labelStyle="width:60px" value="同意申报" data="results"/>' +
                '<textarea labelField="true" label="审核意见" class="mini-textarea"  id="ResultText"  ' +
                'style="width:100%;height:40px" ' +
                'labelStyle="width:60px" value="同意申报" required="true"></textarea>',
                buttons: ['yes', 'no'],
                title: '是否申报专利',
                width: 300,
                height: 160,
                showModal: true,
                callback: function (action) {
                    if (action == 'yes') {
                        var url = '/work/tradeCasesConfirm/commit';
                        var result = mini.get('#Result').getValue();
                        var resultText = mini.get('#ResultText').getValue();
                        if (result && resultText) {
                            var arg = {ID: id, Result: encodeURI(result), ResultText: encodeURI(resultText)};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('交单业务审核成功！', '系统提示', function () {
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || '审核失败，请稍候重试！');
                            });
                        } else {
                            mini.alert('请填写审核意见后再进行提交操作。');
                        }
                    }
                }
            });
            mini.parse();
            var resultCon=mini.get('#Result');
            if(resultCon){
                resultCon.on('valuechanged',function(){
                    var text=resultCon.getText();
                    var textCon= mini.get("#ResultText");
                    textCon.setText(text);
                    textCon.setValue(text);
                });
            }
        } else mini.alert('选择要审核的记录!');
    }
    function cancelAudit(){
        var row = grid.getSelected();
        if (row) {
            var id = row.ID;
            mini.showMessageBox({
                html: ' <input labelField="true" label="是否申报" class="mini-combobox"  id="Result"  ' +
                'style="width:100%" labelStyle="width:60px" value="不同意申报" data="results"/>' +
                '<textarea labelField="true" label="审核意见" class="mini-textarea"  id="ResultText"  ' +
                'style="width:100%;height:40px" ' +
                'labelStyle="width:60px" value="不同意申报" required="true"></textarea>',
                buttons: ['yes', 'no'],
                title: '取消技术文件审稿结果',
                width: 300,
                height: 160,
                showModal: true,
                callback: function (action) {
                    if (action == 'yes') {
                        var url = '/work/tradeCasesConfirm/commit';
                        var result = mini.get('#Result').getValue();
                        var resultText = mini.get('#ResultText').getValue();
                        if (result && resultText) {
                            var arg = {ID: id, Result: encodeURI(result), ResultText: encodeURI(resultText)};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('交单业务审核成功！', '系统提示', function () {
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || '审核失败，请稍候重试！');
                            });
                        } else {
                            mini.alert('请填写审核意见后再进行提交操作。');
                        }
                    }
                }
            });
            mini.parse();
            var resultCon=mini.get('#Result');
            if(resultCon){
                resultCon.on('valuechanged',function(){
                    var text=resultCon.getText();
                    var textCon= mini.get("#ResultText");
                    textCon.setText(text);
                    textCon.setValue(text);
                });
            }
        } else mini.alert('选择要审核的记录!');
    }
    function afterload(){
        var parent= window.parent;
        if(parent)parent.updateStateNumbers();
    }
    function NBBH() {
        var row=grid.getSelected();
        if(!row) {
            mini.alert('请选择要显示的内部编号的专利协作信息!');
            return ;
        }
        var ID=row['ID'];
        mini.open({
            url:'/systems/setNBBH/edit?ID='+ID,
            width:'70%',
            height:'580',
            showModal:true,
            ondestory:function () {

            }
        })
    }
</script>
</@js>