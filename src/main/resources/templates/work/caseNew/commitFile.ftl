<#include "/shared/dialog.ftl">

<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="/js/common/fileUploader.js"></script>
<div class="baseinfo">
    <div style="width: 100%; overflow: hidden; margin-top: 10px">
        <h1><span style="color: red; font-size: 14px; font-family: 仿宋"></span>
            <span style="margin-right: 0px; float: right; font-size: 16px;vertical-align: middle">
                    <#if Mode=="Add" || Mode=="Edit">
                            <a class="mini-button mini-button-primary" id="CmdSave" onclick="doSave()">保存信息</a>
                            <a class="mini-button mini-button-success" id="CmdCommit" onclick="doCommit()">提交审核</a>
                        <#else >
                            <#if Mode=="Audit">
                            <a class="mini-button mini-button-primary" id="CmdCommit" onclick="commit()">审核通过</a>
                            <a class="mini-button mini-button-success" id="CmdRollback" onclick="rollback()">退回重编</a>
                            </#if>
                    </#if>
            </span>
        </h1>
    </div>
</div>
<div class="container" style="width: 99%; padding-left: 10px">
    <div class="mini-clearfix ">
        <div class="mini-col-12">
            <div id="Info1" class="mini-panel mini-panel-info" title="1、技术文件提交信息" width="auto"
                 showcollapsebutton="false" showclosebutton="false">
                <table style="width:100%;height:100%" class="layui-table" id="mainTable">
                    <tbody>
                    <tr>
                        <td>文件说明</td>
                        <td colspan="3">
                            <textarea class="mini-textarea" style="width:100%;height:60px" name="memo" value="${LoadObj
                            .memo!}"></textarea>
                            <input class="mini-hidden" name="casesid" value="${LoadObj.casesId!}"/>
                            <input class="mini-hidden" name="id" value="${LoadObj.id!}"/>
                            <input class="mini-hidden" name="atts" value="${LoadObj.atts!}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>审核意见</td>
                        <td colspan="3">
                            <textarea  class="mini-textarea"  style="width:100%;height:60px" name="auditText" value="${LoadObj
                            .auditText!}"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>提交人</td>
                        <td>
                            <input  class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                    textField="Name" valueField="FID" parentField="PID" name="createMan"
                                    value="${LoadObj.createMan!}" style="width:100%" />
                        </td>
                        <td>提交日期</td>
                        <td><input class="mini-datepicker" style="width:100%" name="createTime"  value="${(LoadObj
                        .createTime?string('yyyy-MM-dd'))!}"/> </td>
                    </tr>
                    <tr>
                        <td>审核人</td>
                        <td>
                            <input  class="mini-treeselect"  url="/systems/dep/getAllLoginUsersByDep"
                                    textField="Name" valueField="FID" parentField="PID" name="auditMan"
                                    value="${LoadObj.auditMan!}" style="width:100%" />
                        </td>
                        <td>审核日期</td>
                        <td><input class="mini-datepicker" style="width:100%" name="auditTime"  value="${(LoadObj
                        .auditTime?string
                        ('yyyy-MM-dd'))!}"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="mini-col-12">
            <div id="Info3" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="2、技术文件附件"
                 width="auto" showcollapsebutton="false"
                 showclosebutton="false">
                <table style="width: 100%; height: 100%" id="Table4">
                    <tr>
                        <td colspan="6">
                            <div id="attGrid" style="width:100%;height: 300px;">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</@layout>
<@js>
    <script type="text/javascript">
        var manager = null;
        var att = null;
        var ywD = null;
        var cmdSave = null;
        var cmdCommit = null;
        var mode='${Mode}';
        mini.parse();
        var configs={
            Add: {
                memo: true,
                createMan: false,
                createTime: false,
                auditMan: false,
                auditTime:false,
                auditText: false
            },
            Edit:{
                memo: true,
                createMan: false,
                createTime: false,
                auditMan: false,
                auditTime: false,
                auditText: false
            },
            Audit:{
                memo: false,
                createMan: false,
                createTime: false,
                auditMan: false,
                auditTime: false,
                auditText: true
            },
            Browse:{
                memo: false,
                createMan: false,
                createTime: false,
                auditMan: false,
                auditTime: false,
                auditText: false
            }
        };
        $(function () {
            mini.parse();
            initDocument(mode);
            cmdSave = mini.get('CmdSave');
            cmdCommit = mini.get('CmdCommit');
        });

        function doSave() {
            var form=new mini.Form('#mainTable');
            form.validate();
            if(form.isValid()==false){
                mini.alert('数据录入不完整，请稍候重试!');
                return ;
            }
            var dd=form.getData();
            var files=att.getPostFile();
            if(files.length>0){
                var g=[];
                for(var i=0;i<files.length;i++){
                    var file=files[i];
                    g.push(file["ATTID"]);
                }
                dd["atts"]=g.join(',');
            }
            var msgId = mini.loading('正在保存数据.........');
            try {
                var url = '/work/commitFile/saveAll';
                $.post(url, {Data: mini.encode(dd),Action:'Save'}, function (result) {
                    if (result.success) {
                        function g() {
                            var obj = result.data;
                            if (obj) {
                                form.setData(obj);
                            }
                        }
                        mini.alert('保存成功!', '系统提示', function () {
                            g();
                        });
                    } else mini.alert(result.message || "保存失败，请稍候重试!");
                });

            } catch (e) {
                mini.alert(e);
            }
            finally {
                mini.hideMessageBox(msgId);
            }
        }

        function doCommit() {
            var form=new mini.Form('#mainTable');
            form.validate();
            if(form.isValid()==false){
                mini.alert('数据录入不完整，请稍候重试!');
                return ;
            }
            var files=att.getPostFile();
            if(files.length==0){
                mini.alert('必须要上传技术文档后才能提交!');
                return ;
            }
            function g() {
                var msgId = mini.loading('正在提交审核.........');
                try {
                    var dd=form.getData();
                    if(files.length>0){
                        var g=[];
                        for(var i=0;i<files.length;i++){
                            var file=files[i];
                            g.push(file["ATTID"]);
                        }
                        dd["atts"]=g.join(',');
                    }
                    var url = '/work/commitFile/saveAll';
                    $.post(url, {Data: mini.encode(dd),Action:'Commit'}, function (result) {
                        if (result.success) {
                            function g() {
                                var obj = result.data;
                                if (obj) {
                                    CloseOwnerWindow('ok');
                                }
                            }
                            mini.alert('提交审核成功!', '系统提示', function () {
                                g();
                            });
                        } else mini.alert(result.message || "提交审核失败，请稍候重试!");
                    });

                } catch (e) {
                    mini.alert(e);
                }
                finally {
                    mini.hideMessageBox(msgId);
                }
            }

            mini.confirm('确认审核，信息将不能修改，是否继续？', '审核提示', function (action) {
                if (action == 'ok') g();
            });
        }

        function initDocument(mode) {
            if (mode == 'Add' || mode == "Edit") {
                att = $('#attGrid').fileUploader({mode: 'Add'});
            } else att = $('#attGrid').fileUploader({mode: 'Browse'});
            var config=configs[mode];
            if(config){
                for(var name in config){
                   var con= mini.getbyName(name);
                   if(con){
                       var dis=config[name];
                       if(dis)
                       {
                           con.setRequired(true);
                           con.enable();
                       } else con.disable();
                   }
                }
            }
            if(mode!='Add'){
                var attId=mini.getbyName('atts').getValue();
                if(attId){
                    att.loadFiles({IDS: attId});
                }
            }
        }
        function commit() {
            var form = new mini.Form('#mainTable');
            form.validate();
            if (form.isValid() == false) {
                mini.alert('数据录入不完整，请稍候重试!');
                return;
            }
            function g() {
                var msgId = mini.loading('正在提交审核结果.........');
                try {
                    var dd = form.getData();
                    var url = '/work/commitFile/commit';
                    $.post(url, {Data: mini.encode(dd)}, function (result) {
                        if (result.success) {
                            function g() {
                                var obj = result.data;
                                if (obj) {
                                    CloseOwnerWindow('ok');
                                }
                            }

                            mini.alert('审核成功!', '系统提示', function () {
                                g();
                            });
                        } else mini.alert(result.message || "审核失败，请稍候重试!");
                    });

                } catch (e) {
                    mini.alert(e);
                }
                finally {
                    mini.hideMessageBox(msgId);
                }
            }
            mini.confirm('确认审核结果，提交后信息将不能修改，是否继续？', '审核提示', function (action) {
                if (action == 'ok') g();
            });
        }
        function rollback() {
            var form = new mini.Form('#mainTable');
            form.validate();
            if (form.isValid() == false) {
                mini.alert('数据录入不完整，请稍候重试!');
                return;
            }
            function g() {
                var msgId = mini.loading('正在提交审核结果.........');
                try {
                    var dd = form.getData();
                    var url = '/work/commitFile/rollback';
                    $.post(url, {Data: mini.encode(dd)}, function (result) {
                        if (result.success) {
                            function g() {
                                var obj = result.data;
                                if (obj) {
                                    CloseOwnerWindow('ok');
                                }
                            }

                            mini.alert('审核成功!', '系统提示', function () {
                                g();
                            });
                        } else mini.alert(result.message || "审核失败，请稍候重试!");
                    });

                } catch (e) {
                    mini.alert(e);
                }
                finally {
                    mini.hideMessageBox(msgId);
                }
            }
            mini.confirm('确认审核结果，提交后信息将不能修改，是否继续？', '审核提示', function (action) {
                if (action == 'ok') g();
            });
        }
    </script>
</@js>
