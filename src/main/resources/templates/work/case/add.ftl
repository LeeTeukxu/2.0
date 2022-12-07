<#include "/shared/dialog.ftl">

<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
<script type="text/javascript" src="/js/common/fileUploader.js"></script>
<script type="text/javascript" src="/js/work/formBuilder.js"></script>
<script type="text/javascript" src="/js/work/formManager.js"></script>
<#--<script type="text/javascript" src="/js/work/ywDetail.js"></script>-->
<script type="text/javascript" src="/js/work/ajDetail.js"></script>
<script type="text/javascript">
    var ajTypes=[{id:'发明专利',text:'发明专利'},{id:'实用新型',text:'实用新型'},{id:'外观设计',text:'外观设计'},{id:'其他',text:'其他'}];

</script>
<style>
    .mini-tools span {
        width: 30px
    }
    .layui-table, .layui-table-view{
        margin:0px
    }
    body .mini-labelfield{
        margin-bottom:0px
    }
</style>
<div class="baseinfo">
    <div style="width: 100%; overflow: hidden;">
        <h1>
            <span style="color: red; font-size: 14px; font-family: 仿宋"></span>
            <span style="margin-right: 0px; float: right; font-size: 16px;vertical-align: middle">
            <#if Mode=="Add" || Mode=="Edit">
                <a class="mini-button mini-button-primary" id="CmdSave" onclick="doSave()">保存信息</a>
                <a class="mini-button mini-button-success" id="CmdCommit" onclick="doCommit()">提交审核</a>
            </#if>
            </span>
        </h1>
    </div>
</div>
<div class="container" style="width: 99%; padding-left: 10px">
    <div class="mini-clearfix ">
        <div class="mini-col-12">
            <div id="Info1" class="mini-panel mini-panel-info" title="1、交单信息" width="auto"
                 showcollapsebutton="false" showclosebutton="false">
                <div id="Table1" style="height:250px">

                </div>
                <div id="Others">
                    <input class="mini-hidden" name="id" id="id"/>
                    <input class="mini-hidden" name="casesid" id="casesid"/>
                    <input class="mini-hidden" name="state" id="state"/>
                </div>
            </div>
        </div>
        <#--<div class="mini-col-12">-->
            <#--<div id="Info2" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="2、业务清单"-->
                 <#--width="auto" showcollapsebutton="false" showclosebutton="false">-->
                <#--<table style="width: 100%; height: 100%" id="Table4">-->
                    <#--<tr>-->
                        <#--<td colspan="6">-->
                            <#--<div class="mini-datagrid" id="ywGrid" style="width:100%;height:120px;"-->
                                 <#--url="/work/cases/getAllByCasesId" autoload="false" showPager="false"-->
                                 <#--showPager="true" allowCellEdit="true" allowCellSelect="true" allowCellValid="true">-->
                                <#--<div property="columns">-->
                                    <#--<div type="indexcolumn"></div>-->
                                    <#--<div field="signDate" headerAlign="center" dataType="date"-->
                                         <#--dateFormat="yyyy-MM-dd" vtype="required">签约日期-->
                                        <#--<input property="editor" class="mini-datepicker"/>-->
                                    <#--</div>-->
                                    <#--<div name="yname" field="yname" headerAlign="center" width="300"-->
                                         <#--type="treeselectcolumn" vtype="required">业务名称-->
                                        <#--<input property="editor" class="mini-treeselect"-->
                                               <#--url="/work/cases/getProducts" valueFromSelect="true"-->
                                               <#--allowInput="true" expandOnload="true"/>-->
                                    <#--</div>-->
                                    <#--<div field="ytype" headerAlign="center" align="center">类型</div>-->
                                    <#--<div field="dai" headerAlign="center" vtype="required" align="right">代理费-->
                                        <#--<input property="editor" class="mini-spinner" maxValue="10000000"-->
                                               <#--minValue="1"/>-->
                                    <#--</div>-->
                                    <#--<div field="guan" headerAlign="center" vtype="required" align="right">官费-->
                                        <#--<input property="editor" class="mini-spinner" maxValue="10000000"-->
                                               <#--minValue="0"/>-->
                                    <#--</div>-->
                                    <#--<div field="num" headerAlign="center" vtype="required">数量-->
                                        <#--<input property="editor" class="mini-spinner" maxValue="10000000"-->
                                               <#--minValue="1"/>-->
                                    <#--</div>-->
                                    <#--<div field="price" headerAlign="center" align="right">单价</div>-->
                                    <#--<div field="total" headerAlign="center" width="150" align="right"-->
                                         <#--summaryType="sum">合计-->
                                    <#--</div>-->
                                <#--</div>-->
                            <#--</div>-->
                        <#--</td>-->
                    <#--</tr>-->
                <#--</table>-->
            <#--</div>-->
        <#--</div>-->
        <div class="mini-col-12">
            <div id="Info3" headerStyle="text-align:right" class="mini-panel mini-panel-info" title="2、精细化交单"
                 width="auto" showcollapsebutton="false" showclosebutton="false">
                <table style="width: 100%; height: 100%" id="Table4">
                    <tr>
                        <td colspan="6">
                            <div class="mini-datagrid" id="ajGrid" style="width:100%;height: 320px;"
                                 url="/work/cases/getAjbyCasesId" autoload="false" showPager="false"
                                 showPager="true" allowCellEdit="true" allowCellSelect="true" allowCellValid="true">
                                <div property="columns">
                                    <div type="indexcolumn"></div>
                                    <div name="ajMemo" field="ajMemo"  headerAlign="center" align="center">备注
                                        <input property="editor" class="mini-textbox" />
                                    </div>
                                    <div field="ajCode"  headerAlign="center" align="center">案件编号</div>
                                    <div field="ajName" headerAlign="center" align="center"  vtype="required" >案件名称
                                        <input property="editor" class="mini-textbox" />
                                    </div>
                                    <div field="ajType" headerAlign="center" align="center"  vtype="required" >案件类型
                                            <input property="editor" class="mini-combobox" data="ajTypes"
                                                   valueFromSelect="true"/>
                                    </div>
                                    <div name="ajLinkMan" field="ajLinkMan" headerAlign="center" vtype="required"
                                         align="center">联系人
                                        <input property="editor" class="mini-buttonedit" id="cmdLinkMan"/>
                                    </div>
                                    <div field="ajLinkPhone" headerAlign="center" vtype="required" align="center">联系电话
                                        <input property="editor" class="mini-textbox" />
                                    </div>
                                    <div field="ajWriteMan" headerAlign="center" vtype="required"
                                         type="treeselectcolumn">专利撰写人
                                        <input property="editor" valueField="FID" parentField="PID" textField="Name"
                                               checkRecursive="true" popupWidth="300"
                                               class="mini-treeselect" multiSelect="true" showFolderCheckBox="true"
                                               url="/systems/dep/getAllLoginUsersByDep" />
                                    </div>
                                    <div field="Action" headerAlign="center" align="center"></div>
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
                                 url="/work/cases/getChangeRecord" autoload="false">
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
        var config =${config};
        var loadObj =${Load};
        var mode = '${Mode}';
        var attIds =${AttID};
        var manager = null;
        var att = null;
        var ywD = null;
        var ajD=null;
        var cmdSave = null;
        var cmdCommit = null;
        mini.parse();
        $(function () {
            var panel = mini.get('#Info2');
            if (panel) {
                panel.setButtons([
                    {html: "<a class='mini-button' style='width:80px;top:-4px;' id='addYw'>新增</a>"},
                    {html: "<a class='mini-button' style='width:80px;margin-left:20px;top:-4px;' id='removeYw'>删除</a>"}
                ]);
            }

            var panel1=mini.get('#Info3');
            if(panel1){
                panel1.setButtons([
                    {html: "<a class='mini-button' style='width:80px;top:-4px;' id='addAj'>新增</a>"},
                    {html: "<a class='mini-button' style='width:80px;margin-left:20px;top:-4px;' id='removeAj'>删除</a>"}
                ]);
            }
            mini.parse();
            initDocument(mode);
            cmdSave = mini.get('CmdSave');
            cmdCommit = mini.get('CmdCommit');

        });

        function doSave() {
            var msgId = mini.loading('正在保存数据.........');
            try {
                var dd = manager.getValue();
                dd.Action = "Save";
                var url = '/work/cases/saveAll';

                $.post(url, {Data: mini.encode(dd)}, function (result) {
                    if (result.success) {
                        function g() {
                            var obj = result.data;
                            if (obj) {
                                manager.setValue(obj);
                                ywD.loadData(obj.casesid);
                            }
                        }

                        mini.alert('业务交单信息保存成功!', '系统提示', function () {
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
            function g() {
                var msgId = mini.loading('正在提交审核.........');
                try {
                    var dd = manager.getValue();
                    dd.Action = "Commit";
                    var url = '/work/cases/saveAll';

                    $.post(url, {Data: mini.encode(dd)}, function (result) {
                        if (result.success) {
                            function g() {
                                var obj = result.data;
                                if (obj) {
                                    manager.setValue(obj);
                                    ywD.loadData(obj.casesid);
                                    cmdSave.destroy();
                                    cmdCommit.destroy();
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
            var builder = new formBuilder(config);
            var table = builder.creat();
            manager = new formManager(builder);
            <#--ywD = new ywDetail('ywGrid', 'addYw', 'removeYw', new Date(), '${RoleName}', function (rs) {-->
                <#--if (rs.length > 0) {-->
                    <#--var con = mini.getbyName('Nums');-->
                    <#--if (con) con.setValue(rs.join(';'));-->
                <#--}-->
            <#--});-->
            ajD=new ajDetail('ajGrid','addAj','removeAj');
            manager.bindEvent('beforeSave', function (result) {
                var rows = att.getPostFile();
                var res = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    res.push(row.ATTID);
                }
                if (res.length > 0) result.Att = res;
                var rs = ywD.getData();
                if (rs.length > 0) result.Yw = rs;
                var js=ajD.getData();
                if(js.length>0){
                    result.AJ=js;
                }
                var form = new mini.Form('#Others');
                var ds = form.getData();
                for (var field in ds) {
                    var dx = ds[field];
                    if (dx) result[field] = dx;
                }
                return result;
            });
            manager.bindEvent('afterLoad', function (obj) {
                var casesId = obj.casesid;
                if (casesId) {
                    ywD.loadData(casesId);
                    ajD.loadData(casesId);
                    var form = new mini.Form('#Others');
                    form.setData(obj);
                    att.loadFiles({IDS: attIds.join(',')});
                    mini.get('#changeGrid').load({CasesID: casesId});
                    var clientIdCon=mini.getbyName('ClientID');
                    if(clientIdCon){
                        clientIdCon.setText(obj["clientIdName"]);
                    }
                    var contractNoCon=mini.getbyName('ContractNo');
                    if(contractNoCon){
                        contractNoCon.setValue(obj["contractNo"]);
                        contractNoCon.setText(obj["contractNo"]);
                    }
                }
            });
            manager.bindEvent('afterInit', function () {
                <#if Mode=="Add">
                    mini.getbyName('CreateMan').setValue(${UserID});
                    mini.getbyName('SignTime').setValue(mini.parseDate('${Now}', 'yyyy-MM-dd HH:mm:ss'));
                </#if>
            });
            manager.bindEvent('buttonEditClick', function (name, con) {
                if(name=="ClientID"){
                    mini.open({
                        url:'/work/clientInfo/query?multiselect=false',
                        showModal:true,
                        width:800,
                        height:300,
                        title:'选择客户资料',
                        ondestroy:function(action){
                            if(action=='ok'){
                                var iframe = this.getIFrameEl();
                                var data = iframe.contentWindow.GetData();
                                data = mini.clone(data);
                                con.setValue(data.ClientID);
                                con.setText(data.Name);
                            }
                        }
                    });
                } else if(name=="ContractNo"){
                    mini.open({
                        url:'/work/contractReceive/query?multiselect=false',
                        showModal:true,
                        width:800,
                        height:300,
                        title:'选择合同资料',
                        ondestroy:function(action){
                            if(action=='ok'){
                                var iframe = this.getIFrameEl();
                                var data = iframe.contentWindow.GetData();
                                data = mini.clone(data);
                                con.setValue(data.ContractNo);
                                con.setText(data.ContractNo);

                                var clientIdCon=mini.getbyName('ClientID');
                                if(clientIdCon){
                                    clientIdCon.setValue(data["ClientID"]);
                                    clientIdCon.setText(data["ClientName"]);
                                }
                            }
                        }
                    });
                }
            });
            manager.render(document.getElementById('Table1'), mode);
            if (mode == 'Add' || mode == "Edit") {
                att = $('#attGrid').fileUploader({mode: 'Add'});
            } else att = $('#attGrid').fileUploader({mode: 'Browse'});

            ywD.render(mode);
            <#if Mode=="Edit" || Mode=="Browse">
            manager.setValue(loadObj);
            </#if>
        }
    </script>
</@js>
