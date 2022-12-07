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
                <a class="mini-button mini-button-success" id="CmdPass" onclick="auditPass()">审核通过</a>
                <a class="mini-button mini-button-danger" id="CmdNotPass" onclick="auditRoll()">退回重编</a>
                <#elseif Mode=="Set">
                <a class="mini-button mini-button-success" id="CmdPass" onclick="setPass()">审批通过</a>
                <a class="mini-button mini-button-danger" id="CmdNotPass" onclick="setRoll()">退回重编</a>
            </#if>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="费用报销单"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="Table1" style="height:500px">
                        <table class="layui-table" style="width:100%;height:100%" id="mainTable">
                            <tr>
                                <td>业务编号</td>
                                <td>
                                    <input class="mini-textbox" style="width:100%" name="docSn" enabled="false"/>
                                </td>
                                <td><span>报销日期</span></td>
                                <td>
                                    <input class="mini-datepicker" style="width:100%" name="createTime"
                                           format="yyyy-MM-dd" enabled="false"/>
                                </td>
                            </tr>
                            <tr>
                                <td>所属部门</td>
                                <td>
                                    <input class="mini-treeselect" name="depId" style="width: 100%" required="true"
                                           url="/systems/dep/getAllCanUse" valueField="depId" parentField="pid"
                                           textField="name" resultAsTree="false"/>
                                </td>
                                <td>报销人</td>
                                <td>
                                    <ul class="mini-treeselect" name="createMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        resultAsTree="false" multiSelect="false"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        enabled="false"
                                        style="width:100%"></ul>
                                </td>
                            </tr>
                            <tr>
                                <td>报销事由</td>
                                <td colspan="3">
                                    <textarea style="width:100%;height:80px" name="memo" class="mini-textarea"
                                              required="true"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>所属项目</td>
                                <td>
                                    <input class="mini-treeselect" style="width:100%" name="projectId" textField="name"
                                           parentField="pid" valueField="fid" url="/expense/getProject"
                                           expandonload="true"/>
                                </td>
                                <td>指定审核人</td>
                                <td>
                                    <ul class="mini-treeselect" name="allocAuditMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        required="true" resultAsTree="false" multiSelect="false"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        style="width:100%"></ul>
                                </td>
                            </tr>
                            <tr>
                                <td>审核意见</td>
                                <td colspan="3">
                                    <textarea class="mini-textarea" style="width:100%;height:40px"
                                              name="auditText" required="true"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>实际审核人</td>
                                <td>
                                    <ul class="mini-treeselect" name="auditMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        required="true" resultAsTree="false" multiSelect="false"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        style="width:100%"></ul>
                                </td>
                                <td><span>审核时间</td>
                                <td>
                                    <input class="mini-datepicker" style="width:100%" name="auditTime"
                                           dateFormat="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <tr>
                                <td>审批意见</td>
                                <td colspan="3">
                                    <textarea class="mini-textarea" style="width:100%;height:40px"
                                              name="setText" required="true"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>审批人</td>
                                <td>
                                    <ul class="mini-treeselect" name="setMan"
                                        url="/systems/dep/getAllLoginUsersByDep"
                                        required="true" resultAsTree="false" multiSelect="false"
                                        textField="Name" valueField="FID" parentField="PID" expandOnload="true"
                                        style="width:100%"></ul>
                                </td>
                                <td>审批时间</td>
                                <td>
                                    <input class="mini-datepicker" style="width:100%" name="setTime"
                                           dateFormat="yyyy-MM-dd"/>
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
                <div id="Info2" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="2、报销明细"
                     width="auto" showcollapsebutton="false"
                     showclosebutton="false">
                    <table style="width: 100%; height: 300px" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-toolbar">
                                    <a class="mini-button" iconCls="icon-add" onclick="addSub">新增</a>
                                    <a class="mini-button" iconCls="icon-remove" onclick="removeSub">删除</a>
                                </div>
                                <div class="mini-datagrid" style="width:100%;height:100%" allowCellEdit="true"
                                     allowCellValid="true" allowCellSelect="true" url="/expense/getSubs"
                                     autoload="false" id="subGrid">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div align="center" headerAlign="center" width="100"
                                             field="beginTime" dataType="date" dateFormat="yyyy-MM-dd">发生日期起
                                            <input property="editor" class="mini-datepicker"/>
                                        </div>
                                        <div align="center" headerAlign="center" width="100"
                                             field="endTime" dataType="date" dateFormat="yyyy-MM-dd">止
                                            <input property="editor" class="mini-datepicker"/>
                                        </div>
                                        <div field="memo" align="center" headerAlign="center" width="200"
                                             vtype="required">摘要
                                            <input property="editor" class="mini-textbox"/>
                                        </div>
                                        <div field="itemId" align="center" headerAlign="center" width="150"
                                             type="treeselectcolumn" vtype="required">费用名称
                                            <input property="editor" class="mini-treeselect" parentField="pid"
                                                   valueField="fid" textField="name" expandonload="true"
                                                   url="/expense/getItems" popupWidth="300"/>
                                        </div>
                                        <div field="price" align="center" headerAlign="center" width="100"
                                             vtype="required">单价
                                            <input property="editor" class="mini-spinner" maxValue="9999999999"/>
                                        </div>
                                        <div field="num" align="center" headerAlign="center" width="80"
                                             vtype="required">数量
                                            <input property="editor" class="mini-spinner" maxValue="99999999"/>
                                        </div>
                                        <div field="money" align="center" headerAlign="center" width="120">金额</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="3、附件信息"
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
                <div id="Info4" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="4、修改记录"
                     width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table4">
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" id="changeGrid" style="width:100%;height: 200px;"
                                     allowCellWrap="true"
                                     url="/expense/getChangeRecord" autoload="false">
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
        var manager = null;
        var att = null;
        var attIds=${AttIDS};
        var cmdSave = null;
        var cmdCommit = null;
        var mainForm = null;
        var data = ${Data};
        var subGrid = null;
        var mode="${Mode}";
        $(function () {
            mini.parse();
            subGrid = mini.get('#subGrid');
            initDocument(mode);
            cmdSave = mini.get('CmdSave');
            cmdCommit = mini.get('CmdCommit');
        });

        function addSub() {
            subGrid.addRows([{}]);
        }

        function removeSub() {

        }

        function doSave() {
            manager.save();
        }

        function doCommit() {
            manager.commit();
        }

        function auditPass() {
            mini.confirm('确认提交审核通过吗？', '系统提示', function (act) {
                if (act == "ok") manager.audit(1);
            });
        }

        function auditRoll() {
            mini.confirm('确认提交不通过，并退回吗？', '系统提示', function (act) {
                if (act == "ok") manager.audit(0);
            });
        }
        function setPass() {
            mini.confirm('确认要提交审批通过吗？', '系统提示', function (act) {
                if (act == "ok") manager.set(1);
            });
        }

        function setRoll() {
            mini.confirm('确认提交审批不通过，并退回吗？', '系统提示', function (act) {
                if (act == "ok") manager.set(0);
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
                    subGrid.load({MainID:mainId});
                    var idCon = mini.get('#id');
                    if (idCon) {
                        idCon.setValue(obj.id);
                    }
                }
            });
            manager.bindEvent('afterInit', function () {
                if(mode=="Add"){
                    manager.setValue(data);
                    manager.render("Add");
                }
            });

            if (mode == 'Add' || mode == "Edit") {
                att = $('#attGrid').fileUploader({mode: 'Add'});
            } else att = $('#attGrid').fileUploader({mode: 'Browse'});

            <#if Mode=="Edit" || Mode=="Browse" || Mode=="Audit" || Mode=="Set">
            manager.setValue(data);
            manager.render(mode);
            </#if>
            manager.init(att);
        }

        function addCompont() {
            var mainForm = new mini.Form('#mainTable');
            var subGrid = mini.get('#subGrid');
            var g = this;
            var attGrid = null;
            var events = {
                'beforeSave': function () {
                },
                'afterLoad': function () {
                },
                'afterInit': function () {

                }
            };
            var modeHash = {
                Add: ['memo', 'allocAuditMan', 'projectId'],
                Edit: ['memo', 'allocAuditMan', 'projectId'],
                Audit: ['auditText'],
                Set: ['setText']
            };
            this.bindEvent = function (name, fun) {
                events[name] = fun;
            }
            this.validate = function () {
                mainForm.validate();
                subGrid.validate();
                return mainForm.isValid() && subGrid.isValid();
            }
            this.getValue = function (validate) {
                if (validate == true) {
                    var ok = g.validate();
                    if (ok == false) {
                        mini.alert('数据录入不完整，操作被中止!');
                        return null;
                    }
                }

                var ds = mainForm.getData();
                var dSub = subGrid.getData();
                if (dSub.length > 0) ds["Sub"] = dSub;
                var result = events.beforeSave(ds) || {};
                for (var n in result) {
                    var val = result[n];
                    if (val != null && val != undefined) {
                        ds[n] = val;
                    }
                }
                return ds;
            };
            this.save = function () {
                var msgId = mini.loading('正在保存数据.........');
                try {
                    var dd = manager.getValue(false);
                    dd.Action = "Save";
                    var url = '/expense/saveAll';
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
                        var url = '/expense/saveAll';
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
                if(manager.validate()==false){
                    mini.alert('数据录入不完整，不允许提交!');
                    return ;
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
                var url = '/expense/doAudit';
                $.post(url, arg, function (result) {
                    if (result.success) {
                        mini.alert('审核操作成功!', '系统提示', function () {
                            CloseOwnerWindow('ok');
                        });
                    } else mini.alert(result.message || "操作失败，请稍候重试!");
                })

            }
            this.set = function (yorn) {
                var obj = manager.getValue(true);
                if (obj == null) return;
                var arg = {ID: obj.id, SetResult: parseInt(yorn), Text: obj.setText};
                var url = '/expense/doSet';
                $.post(url, arg, function (result) {
                    if (result.success) {
                        mini.alert('审批操作成功!', '系统提示', function () {
                            CloseOwnerWindow('ok');
                        });
                    } else mini.alert(result.message || "操作失败，请稍候重试!");
                })

            }
            this.render = function (mode, type) {
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
                mainForm.setData(obj);
                var form = new mini.Form('#Others');
                form.setData(obj);
                events.afterLoad(obj);
            }
            this.init = function (att) {
                attGrid = att;
                events.afterInit();
            }
        }
    </script>
</@js>
