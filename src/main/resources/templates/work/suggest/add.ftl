<#include "/shared/dialog.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/js/raty/jquery.raty.css" media="all"/>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploader.js"></script>
<#--    <script type="text/javascript" src="/js/raty/jquery.raty.js"></script>-->
    <script type="text/javascript">
        var ajTypes = [
            {id: '发明专利', text: '发明专利'},
            {id: '实用新型', text: '实用新型'},
            {id: '外观设计', text: '外观设计'},
            {id: '其他', text: '其他'}
        ];
        var types = [
            {id: 1, text: '现金'},
            {id: 2, text: '转账'},
            {id: 3, text: '支付宝'},
            {id: 4, text: '微信'}
        ];
    </script>
    <style>
        .mini-tools span {
            width: 30px
        }

        .layui-table, .layui-table-view {
            margin: 0px
        }

        body .mini-labelfield {
            margin-bottom: 0px
        }

        .mini-button-icon {
            left: 2px
        }

        .mini-tools span {
            margin-left: 2px
        }
    </style>
    <script type="text/javascript">
        var types = [{id: 1, text: '建议'}, {id: 2, text: '投诉'}];
    </script>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden;">
            <h1>
                <span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px;vertical-align: middle">
            <#if Mode=="Add" || Mode=="Edit">
                <a class="mini-button mini-button-success" id="CmdSave" onclick="doSave()">保存信息</a>
                <a class="mini-button mini-button-primary" id="CmdCommit" onclick="doCommit()">提交审核</a>
                <#elseif Mode=="Audit">
                <a class="mini-button mini-button-success" id="CmdPass" onclick="doPass()">处理完成</a>
                <a class="mini-button mini-button-danger" id="CmdNotPass" onclick="doNotPass()">退回重编</a>
            </#if>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="1、<span class='typeName'>建议</span>内容"
                     width="auto"
                     showcollapsebutton="false" showclosebutton="false">
                    <div id="Table1" style="height:400px">
                        <table class="layui-table" style="width:100%;height:100%" id="mainTable">
                            <tr>
                                <td>事项类型</td>
                                <td>
                                    <input class="mini-combobox" data="types"
                                           style="width:100%" name="type" onvaluechanged="typeChanged" value="1"/>
                                </td>
                                <td><span class="hideLabel">被投诉人</span></td>
                                <td>
                                    <ul class="mini-treeselect" name="suggestMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        required="true" showCheckBox="true" resultAsTree="false" multiSelect="true"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        style="width:100%"></ul>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="typeName">建议</span>分类</td>
                                <td>
                                    <input class="mini-combobox" style="width:100%" required="true" name="title"
                                           url="/suggest/getByType?Type=1"/>
                                </td>
                                <td>指定处理人</td>
                                <td>
                                    <ul class="mini-treeselect" name="auditMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        required="true" resultAsTree="false" multiSelect="false"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        style="width:100%"></ul>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="typeName">建议</span>问题描述</td>
                                <td colspan="3">
                                    <textarea style="width:100%;height:80px" name="memo" class="mini-textarea"
                                              required="true"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>实际处理人</td>
                                <td>
                                    <ul class="mini-treeselect" name="changeMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        required="true" resultAsTree="false" multiSelect="false"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        style="width:100%"></ul>
                                </td>
                                <td>处理时间</td>
                                <td>
                                    <input class="mini-datepicker" style="width:100%" name="auditTime"/>
                                </td>
                            </tr>
                            <tr>
                                <td>处理意见</td>
                                <td colspan="3">
                                    <textarea class="mini-textarea" style="width:100%;height:80px"
                                              name="auditText" required="true"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td><span class="typeName">建议</span>人</td>
                                <td>
                                    <ul class="mini-treeselect" name="createMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        value="<#if Mode=='Add'>${UserID}</#if>"
                                        required="true" resultAsTree="false" multiSelect="false"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        style="width:100%"></ul>
                                </td>
                                <td><span class="typeName">建议</span>时间</td>
                                <td>
                                    <input class="mini-datepicker" value="<#if Mode=='Add'>${Now}</#if>"
                                           style="width:100%"
                                           name="createTime" dateFormat="yyyy-MM-dd"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id="Others">
                        <input class="mini-hidden" name="id" id="id"/>
                        <input class="mini-hidden" name="state" id="state"/>
                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="2、附件信息"
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
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="3、修改记录"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="changeGrid" style="width:100%;height: 200px;"
                                     allowCellWrap="true"
                                     url="/suggest/getChangeRecord" autoload="false">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div field="changeText" headerAlign="center" align="left"
                                             width="600">变更内容
                                        </div>
                                        <div field="userId" align="center" headerAlign="center"
                                             type="treeselectcolumn">操作人员
                                            <input property="editor" class="mini-treeselect"
                                                   url="/systems/dep/getAllLoginUsersByDep"
                                                   textField="Name" valueField="FID" parentField="PID"/>
                                        </div>
                                        <div field="createTime" headerAlign="center" dataType="date"
                                             dateFormat="yyyy-MM-dd HH:mm:ss" align="center">操作时间
                                        </div>
                                    </div>
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
        var config = {};
        var loadObj = ${Data};
        var mode = '${Mode}';
        var attIds = ${AttIDS};
        var manager = null;
        var att = null;
        var ajD = null;
        var cmdSave = null;
        var cmdCommit = null;
        $(function () {
            mini.parse();
            initDocument(mode);
            cmdSave = mini.get('CmdSave');
            cmdCommit = mini.get('CmdCommit');
        });

        function typeChanged(e) {
            var type = parseInt(e.value);
            manager.render(mode, type);
        }

        function doSave() {
            manager.save();
        }

        function doCommit() {
            manager.commit();
        }

        function doPass() {
            mini.confirm('确认该业务已处理完成，并提交吗？', '系统提示', function (act) {
                if (act == "ok") manager.audit(1);
            });
        }

        function doNotPass() {
            mini.confirm('确认不处理该业务，并退回吗？', '系统提示', function (act) {
                if (act == "ok") manager.audit(0);
            });
        }

        function initDocument(mode) {
            manager = new addCompont();
            manager.bindEvent('beforeSave', function (result, doValid) {
                var rows = att.getPostFile();
                var res = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    res.push(row.ATTID);
                }
                if (res.length > 0) result.Att = res;
                var form = new mini.Form('#Others');
                var ds = form.getData();
                for (var field in ds) {
                    var dx = ds[field];
                    if (dx) result[field] = dx;
                }
                return result;
            });
            manager.bindEvent('afterLoad', function (obj) {
                var mainId = obj.id;
                if (mainId) {
                    var form = new mini.Form('#Others');
                    form.setData(obj);
                    att.loadFiles({IDS: attIds.join(',')});
                    mini.get('#changeGrid').load({ID: mainId});
                    var idCon = mini.get('#id');
                    if (idCon) {
                        idCon.setValue(obj.id);
                    }
                }
            });
            manager.bindEvent('afterInit', function () {
                var createCon = mini.getbyName('CreateMan');
                <#if Mode=="Add">
                if (createCon) {
                    createCon.setValue(${UserID});
                }
                manager.render("Add", 1);
                </#if>
            });

            if (mode == 'Add' || mode == "Edit") {
                att = $('#attGrid').fileUploader({mode: 'Add'});
            } else att = $('#attGrid').fileUploader({mode: 'Browse'});

            <#if Mode=="Edit" || Mode=="Browse" || Mode=="Audit">
            manager.setValue(loadObj);
            manager.render(mode, parseInt(loadObj.type || 1));
            </#if>
            manager.init();
        }

        function addCompont() {
            var mainForm = new mini.Form('#mainTable');
            var events = {
                'beforeSave': function () {
                },
                'afterLoad': function () {
                },
                'afterInit':function (){

                }
            };
            var modeHash = {
                Add: ['type', 'suggestMan', 'title', 'auditMan', 'memo'],
                Edit: ['type', 'suggestMan', 'title', 'auditMan', 'memo'],
                Audit: ['auditText', 'actAuditMan']
            };
            this.bindEvent = function (name, fun) {
                events[name] = fun;
            }
            this.validate = function () {
                mainForm.validate();
                return mainForm.isValid();
            }
            this.getValue = function (validate) {
                if (validate == true) {
                    mainForm.validate();
                    if (mainForm.isValid() == false) {
                        mini.alert('数据录入不完整，操作被中止!');
                        return null;
                    }
                }
                var ds = mainForm.getData();
                var result = events.beforeSave(ds) || {};
                for (var n in result) {
                    var val = result[n];
                    if (val != null && val != undefined) {
                        ds[n] = val;
                    }
                }
                return ds;
            },
                this.save = function () {
                    var msgId = mini.loading('正在保存数据.........');
                    try {
                        var dd = manager.getValue(false);
                        dd.Action = "Save";
                        var url = '/suggest/saveAll';
                        $.post(url, {Data: mini.encode(dd)}, function (result) {
                            if (result.success) {
                                mini.alert('业务交单信息保存成功!', '系统提示', function () {
                                    CloseOwnerWindow('ok');
                                });
                            } else mini.alert(result.message || "保存失败，请稍候重试!");
                        });
                    } catch (e) {
                        mini.alert(e);
                    } finally {
                        mini.hideMessageBox(msgId);
                    }
                }
            this.commit = function () {
                function g() {
                    var msgId = mini.loading('正在提交数据.........');
                    try {
                        var dd = manager.getValue(true);
                        dd.Action = "Commit";
                        var url = '/suggest/saveAll';
                        $.post(url, {Data: mini.encode(dd)}, function (result) {
                            if (result.success) {
                                mini.alert('信息提交成功!', '系统提示', function () {
                                    CloseOwnerWindow('ok');
                                });
                            } else mini.alert(result.message || "保存失败，请稍候重试!");
                        });
                    } catch (e) {
                        mini.alert(e);
                    } finally {
                        mini.hideMessageBox(msgId);
                    }
                }

                mini.confirm('确认提交?', '系统提示', function (result) {
                    if (result == 'ok') {
                        g();
                    }
                });
            }
            this.audit = function (yorn) {
                var obj = manager.getValue(true);
                if (obj == null) return;
                var arg = {ID: obj.id, AuditResult: parseInt(yorn), Text: obj.auditText};
                var url = '/suggest/doAudit';
                $.post(url, arg, function (result) {
                    if (result.success) {
                        mini.alert('处理操作成功!', '系统提示', function () {
                            CloseOwnerWindow('ok');
                        });
                    } else mini.alert(result.message || "操作失败，请稍候重试!");
                })

            }
            this.render = function (mode, type) {
                var conSuggestMan = mini.getbyName('suggestMan');
                if (type == 1) {
                    conSuggestMan.setRequired(false);
                    conSuggestMan.hide();
                    $('.typeName').text('建议');
                    $('.hideLabel').hide();
                    mini.getbyName('title').load('/suggest/getByType?Type=1');
                } else {
                    conSuggestMan.setRequired(true);
                    conSuggestMan.show();
                    $('.typeName').text('投诉');
                    $('.hideLabel').show();
                    mini.getbyName('title').load('/suggest/getByType?Type=2');
                }
                changeByMode(mode);
            }

            function changeByMode(mode) {
                var fs = modeHash[mode] || [];
                var conFields = mainForm.getFields();
                for (var i = 0; i < conFields.length; i++) {
                    var con = conFields[i];
                    var name = con.getName();
                    if (!name) continue;
                    if (fs.indexOf(name) > -1) con.enable(); else con.disable();
                }
            }

            this.setValue = function (obj) {
                obj = obj || {};
                if (obj.createTime) {
                    var dateValue = new Date(parseInt(obj.createTime));
                    obj.createTime = dateValue;
                }
                mainForm.setData(obj);
                var form = new mini.Form('#Others');
                form.setData(obj);
                events.afterLoad(obj);
            }
            this.init=function(){
                events.afterInit();
            }
        }
    </script>
</@js>
