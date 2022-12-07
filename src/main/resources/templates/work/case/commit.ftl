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
                            <a class="mini-button" iconcls="icon-ok" id="CasesBrowse_Add" onclick="auditFile"
                               visible="false">上级核稿</a>
                            <a class="mini-button" iconCls="icon-cancel" id="CasesBrowse_Cancel"
                               onclick="cancelAudit" visible="false">取消核稿</a>
                            <a class="mini-button" iconcls="icon-upload" id="CasesBrowse_Audit" visible="false">申报专利</a>
                            <a class="mini-button" iconcls="icon-download" id="CasesBrowse_Download"
                               onclick="download">下载文件</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox CasesBrowse_Query" style="width:250px"
                                   emptyText="备注/合同号/流水号/业备数量/客户/商务人员"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success CasesBrowse_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
                            <a class="mini-button mini-button-primary CasesBrowse_HighQuery" id="a1"
                               onclick="doHightSearch">搜索数据</a>
                            <a class="mini-button mini-button-danger CasesBrowse_Reset"  id="a2"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info"onclick="expand">展开</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%"
                     showColumnsMenu="true" onrowdblclick="BrowseCases" onrowclick="onRowClick" sortfield="State"
                     sortorder="desc" pagesize="20" url="/work/casesCommit/getData?State=${State}"
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
        var cmdAdd = mini.get('#CasesBrowse_Add');
        var cmdAudit = mini.get('#CasesBrowse_Audit');
        var cmdCancel=mini.get('#CasesBrowse_Cancel');
        var cmdA1=mini.get('a1');
        var cmdA2=mini.get('a2');
        var cmdA3=mini.get('a3');
        var txtQuery=mini.get('queryText');

        var results = [{id: '满足要求', text: '满足要求'}, {id: '修改后再审', text: '修改后再审'}, {id: '驳回后再审', text: '驳回后再审'}];
        var results1 = [{id: '修改后再审', text: '修改后再审'}, {id: '驳回后再审', text: '驳回后再审'}];
        $(function () {
            $('#p1').hide();
            cmdA1.hide();
            cmdA2.hide();
        })
        function onRowClick(e) {
            var record = e.record;
            var techManager = (record["TechManager"] || "").split(',');
            var lcMans = (record["LCManager"] ||"").split(",");
            lcMans.push(record["LCMan"].toString());
            var state = parseInt(record.State || 1);
            cmdAdd.hide();
            cmdAudit.hide();
            cmdCancel.hide();
            if (state == 1) {
                if (techManager.indexOf(userId) > -1) {
                    cmdAudit.hide();
                    cmdAdd.show();
                }
            }
            else if (state == 3) {
                if (lcMans.indexOf(userId) > -1) {
                    //cmdAudit.show();
                    cmdAdd.hide();
                }
                if(techManager.indexOf(userId)>-1){
                    cmdCancel.show();
                }
            }
        }

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                //btn.setIconCls("panel-collapse");
                btn.setText("折叠");
                $('#p1').css('display', "block");
                cmdA1.show();
                cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                //btn.setIconCls("panel-expand");
                btn.setText("展开");
                $('#p1').css('display', "none");
                cmdA1.hide();
                cmdA2.hide();
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
        }

        function auditFile() {
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.showMessageBox({
                    html: ' <input labelField="true" label="核稿结果" class="mini-combobox"  id="Result"  ' +
                    'style="width:100%" labelStyle="width:60px" value="满足要求" data="results"/>' +
                    '<textarea labelField="true" label="审核意见" class="mini-textarea"  id="ResultText"  ' +
                    'style="width:100%;height:40px" ' +
                    'labelStyle="width:60px" value="满足要求" required="true"></textarea>',
                    buttons: ['yes', 'no'],
                    title: '技术文件审稿结果',
                    width: 300,
                    height: 160,
                    showModal: true,
                    callback: function (action) {
                        if (action == 'yes') {
                            var url = '/work/commitFile/commit';
                            var result = mini.get('#Result').getValue();
                            var resultText = mini.get('#ResultText').getValue();
                            if (result && resultText) {
                                var arg = {ID: id, Result: encodeURI(result), ResultText: encodeURI(resultText)};
                                $.post(url, arg, function (result) {
                                    if (result.success) {
                                        mini.alert('技术文件审核成功！', '系统提示', function () {
                                            grid.reload();
                                        });
                                    } else mini.alert(result.message || '技术文件审核失败，请稍候重试！');
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
                        mini.get("#ResultText").setText(text);
                    });
                }
            } else mini.alert('选择要审核的记录!');
        }
        function afterload(){
            var parent= window.parent;
            if(parent)parent.updateStateNumbers();
        }
        function cancelAudit(){
            var row = grid.getSelected();
            if (row) {
                var id = row.ID;
                mini.showMessageBox({
                    html: ' <input labelField="true" label="核稿结果" class="mini-combobox"  id="Result"  ' +
                    'style="width:100%" labelStyle="width:60px" value="修改后再审" data="results1"/>' +
                    '<textarea labelField="true" label="审核意见" class="mini-textarea"  id="ResultText"  ' +
                    'style="width:100%;height:40px" ' +
                    'labelStyle="width:60px" value="修改后再审" required="true"></textarea>',
                    buttons: ['yes', 'no'],
                    title: '取消技术文件审稿结果',
                    width: 300,
                    height: 160,
                    showModal: true,
                    callback: function (action) {
                        if (action == 'yes') {
                            var url = '/work/commitFile/commit';
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
                        mini.get("#ResultText").setText(text);
                    });
                }
            } else mini.alert('选择要审核的记录!');
        }

    </script>
</@js>