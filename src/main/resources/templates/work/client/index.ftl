<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
 <style type="text/css">
     .panel-expand {
         background-image: url(/js/miniui/themes/icons/collapse.gif);
     }

     .panel-collapse {
         background-image: url(/js/miniui/themes/icons/collapse.gif);
     }
 </style>
    <script type="text/javascript">

        var types = [
            {id: 1, text: '工矿企业'},
            {id: 3, text: '事业单位'},
            {id: 4, text: '大专院校'},
            {id: 2, text: '个人'}
        ];
        var mode = "${Type}";
    </script>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="center" style="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" id="ClientBrowse_Add" iconcls="icon-add" onclick="addClient" plain="true">新增</a>
                            <a class="mini-button" id="ClientBrowse_Edit" iconcls="icon-edit" onclick="editClient" plain="true">修改</a>
                            <a class="mini-button" id="ClientBrowse_Remove" iconcls="icon-remove" onclick="remove" plain="true">删除</a>
                            <a class="mini-button" id="ClientBrowse_Browse" iconcls="icon-new" onclick="browseClient" plain="true">浏览</a>
                            <span class="separator"></span>
                            <a class="mini-button" plain="true" iconcls="icon-folderopen" id="ClientBrowse_Import"
                               onclick="doImport">导入客户</a>
                            <a class="mini-button" id="ClientBrowse_Export" iconcls="icon-xls" plain="true" onclick="doExport">导出Excel</a>
                            <span class="separator"></span>
                            <a class="mini-button" plain="true" iconcls="icon-unlock" onclick="changeKH" id="ClientBrowse_ChangeKH">转移客户</a>
                            <a class="mini-button" id="ClientBrowse_SendPassword" iconcls="icon-user"
                               onclick="SendPassword" plain="true" title="发密码到客户的登录邮箱">发密码给客户</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field ClientBrowse_Query" id="comField" style="width:100px" data="[{id:'All',
                            text:'全部属性'},
                            {id:'Name',text:'客户名称'},{id:'JSR',text:'介绍人'},{id:'OrgCode',text:'电子邮件'},
                            {id:'SignManName',text:'登记人'},{id:'Memo',text:'备注信息'}]" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field ClientBrowse_Query" style="width:150px" id="QueryText"/>
                            <a class="mini-button mini-button-success" onclick="doQuery();"
                               id="ClientBrowse_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger"  id="ClientBrowse_Reset"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info"  id="ClientBrowse_HighQuery"
                               onclick="expand">高级查询</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:160px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">登记时间：</td>
                            <td style="width:15%;">
                                <input name="SignDate" class="mini-datepicker" data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="SignDate" class="mini-datepicker" data-oper="LE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">企业性质：</td>
                            <td style="width:15%;">
                                <input name="Type" data-oper="EQ" style="width:100%" class="mini-combobox"
                                       data="types"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">介绍人：</td>
                            <td style="width:15%;">
                                <input name="JSR" data-oper="LIKE" class="mini-textbox" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">登录邮箱：</td>
                            <td style="width:15%;">
                                <input name="OrgCode" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">客户名称：</td>
                            <td style="width:15%;">
                                <input name="Name" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">登记人：</td>
                            <td style="width:15%;">
                                <input name="SignManName" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">备注：</td>
                            <td style="width:15%;">
                                <input name="Memo" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">客户标识：</td>
                            <td style="width:15%;">
                                <input name="FullName" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                            <#if RoleName=="系统管理员" || RoleName=="财务人员" || RoleName?index_of("流程")&gt;-1>
                                    <td style="width:6%;padding-left:10px;">所属部门</td>
                                    <td style="width:15%;">
                                        <input class="mini-treeselect"  name="DDepID" style="width: 100%" popupWidth="400px"
                                               data-oper="EQ" expandOnload="true" allowInput="true" valueFromSelect="true"
                                               url="/systems/dep/getAllCanUse" valueField="depId" parentField="pid" textField="name" resultAsTree="false"/>
                                    </td>
                                </else>
                                    <td style="width:6%;padding-left:10px;"></td>
                                    <td style="width:15%;">

                                    </td>
                            </#if>

                            <td style="width:6%;padding-left:10px;"></td>
                            <td style="width:15%;">

                            </td>
                            <td style="width:6%;padding-left:10px;"></td>
                            <td style="width:15%;">

                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <button class="mini-button mini-button-success"style="width:120px" onclick="doHightSearch()">搜索</button>
                                <button class="mini-button mini-button-danger"style="width:120px" onclick="expand()">收起</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="ClientBrowse_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                     onrowdblclick="editClient" showLoading="true"
                     allowresize="true" url="/work/clientInfo/getData?Type=${Type}" multiselect="true"
                     pagesize="30" sizelist="[5,10,20,30,50,100,150,200]" sortfield="SignDate" sortorder="desc"
                     autoload="true">
                    <div property="columns">
                        <div type="indexcolumn" ></div>
                        <div type="checkcolumn"></div>
                        <div field="cootype" name="cootype" width="80" headeralign="center" type="comboboxcolumn"
                             allowsort="true" align="center">
                            合作类型
                            <div property="editor" class="mini-combobox" data="[{id:1,text:'合作客户'},{id:2,
                        text:'意向客户'}]"></div>
                        </div>
                        <div field="Name" name="Name" width="250" headeralign="center" allowsort="true" align="center">
                            客户名称
                        </div>
                        <div field="LimitDate" width="120" headeralign="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd" align="center">最近缴费日期</div>
                        <div field="Type" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true" align="center">企业性质
                            <div property="editor" class="mini-combobox" data="types"></div>
                        </div>
                        <div field="JSR" width="100" headerAlign="center"  allowsort="true" align="center">介绍人</div>
                        <div field="OrgCode" width="120" headeralign="center" allowsort="true" align="center">登录邮箱</div>
                        <div field="SendTime" width="120" headeralign="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">上次发送密码时间</div>
                        <div field="SignMan" width="80" headeralign="center" type="treeselectcolumn" allowsort="true" align="center">
                            登记人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="SignMan" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>

                        <div field="SignDate" width="120" headeralign="center" allowsort="true" dataType="date"
                             dateFormat="yyyy-MM-dd" align="center">登记日期
                        </div>
                        <div field="FollowDate" width="100" headerAlign="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd" align="center">最新跟进时间
                        </div>
                        <div field="Memo" width="200" headeralign="center" allowsort="true" align="center">备注</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form method="post" action="/work/clientInfo/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <script type="text/javascript" src="/js/common/ImportManage.js"></script>
    <script type="text/javascript">
        mini.parse();
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var cmdQuery=mini.get('#ClientBrowse_Query');
        $(function () {
            $('#p1').hide();
        });
        var grid = mini.get('ClientBrowse_datagrid');
        var fit = mini.get('fitt')
        var RegisterPath="";

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("icon-collapse");
                btn.setText("隐藏");
                $('#p1').css('display', "block");
                cmdQuery.hide();
                comField.hide();
                txtQuery.hide();
            } else {
                btn.setIconCls("icon-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
                cmdQuery.show();
                comField.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
        }

        function doShow() {
            mini.open({
                url: "/Client/YClientReport/Index?Type=" + mode,
                width: '100%',
                height: '100%',
                showModal: true,
                title: '数据统计'
            });
        }

        function doHightSearch() {
            var arg = {};
            var form = new mini.Form('#highQueryForm');
            var fields = form.getFields();
            var result = [];
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var val = field.getValue();
                if (val != null && val != undefined) {
                    if (val != '') {
                        var obj = {
                            field: field.getName(),
                            value: field.getValue(),
                            oper: field.attributes["data-oper"]
                        };
                        result.push(obj);
                    }
                }
            }
            arg["High"] = mini.encode(result);
            grid.load(arg);
        }

        function doQuery() {
            var arg = {};
            var bs = [];
            var cs = [];
            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        var kWork = f + '=' + word;
                        if (cs.indexOf(kWork) == -1) {
                            var op = {field: f, oper: 'LIKE', value: word};
                            cs.push(op);
                        }
                    }
                } else {
                    var op = {field: field, oper: 'LIKE', value: word};
                    cs.push(op);
                }
            }
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function addClient(){
            mini.open({
                url:'/work/clientInfo/add?Type=${Type}',
                width:'100%',
                height:'100%',
                title:'新增客户资料',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            });
        }
        function editClient(){
            var row=grid.getSelected();
            if(!row){
                mini.alert('请选择要编辑的客户信息!');
                return ;
            }
            var clientId=row["ClientID"];
            mini.open({
                url:'/work/clientInfo/edit?Type=${Type}&ClientID='+clientId,
                width:'100%',
                height:'100%',
                title:'编辑客户资料',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            });
        }
        function browseClient(){
            var row=grid.getSelected();
            if(!row){
                mini.alert('请选择要浏览的客户信息!');
                return ;
            }
            var clientId=row["ClientID"];
            mini.open({
                url:'/work/clientInfo/browse?Type=${Type}&ClientID='+clientId,
                width:'100%',
                height:'100%',
                title:'编辑客户资料',
                showModal:true,
                ondestroy:function(){

                }
            });
        }
        function changeKH(){
            var rows = mini.clone(grid.getSelecteds());
            if (rows.length > 0) {
                mini.open({
                    url: '/work/clientInfo/ChangeKH',
                    width: 800,
                    height: 400,
                    showModal: true,
                    title:'客户转移',
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        iframe.contentWindow.setData(rows);
                    },
                    onDestroy: function () {
                        if (grid) grid.reload();
                    }
                });
            }
        }

        function SendPassword(){
            var rows = grid.getSelecteds();
            var mails = [];
            var vs=[];
            var ClientID="";
            var orgCode='';
            for (var i = 0; i < rows.length; i++) {
                if (!rows[i].OrgCode) {
                    mini.alert('请先问客户要邮箱, 再发帐号!');
                    return;
                }
                mails.push(rows[i]["ClientID"]);
                ClientID=rows[i]["ClientID"];
                OrgCode=rows[i]["OrgCode"];
            }
            if (mails.length === 0) {
                mini.alert('请选择需要发送密码的客户!');
                return;
            }
            if (rows.length>0){
                for (var i=0;i<rows.length;i++){
                    var row=rows[i];
                    var requestUrl="/work/clientInfo/getRegisterPath";
                    $.post(requestUrl,{ClientID:ClientID,OrgCode:OrgCode},function (r) {
                        if (r['success']){
                            var r={Url:r['message']+"&ClientID="+ClientID+'&OrgCode='+OrgCode};
                            vs.push(r);
                        }
                    });
                }
            }
            mini.confirm('确认发送?', '系统提示', function (act) {
                if (act === 'ok') {
                    mini.mask("发送中...");
                    var url = '/work/clientInfo/SendPassword?Code=xtsy&Type=发送密码给客户';
                    $.post(url,{mails:encodeURI(mini.encode(rows)),rows:encodeURI(mini.encode(vs))}, function (r) {
                        if (r['success']) {
                            mini.alert('发送成功!');
                            grid.reload();
                        }
                        else mini.alert(r['Message'] || "发送失败!");
                        mini.unmask();
                    });
                }
            });
        }

        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["ClientID"]);
                if (rows[i]["cootype"]=="1"){
                    mini.alert("无法删除合作客户，请重新选择客户");
                    return;
                }
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('确认要删除客户信息数据?', '删除提示', function (act) {
                if (act === 'ok') {
                    var url="/work/clientInfo/remove";
                    $.post(url,{IDS:mini.encode(ids)},function(r){
                        if (r['success']) {
                            mini.alert("删除成功！",'删除提示',function () {
                                grid.reload();
                            });

                        } else mini.alert("删除失败！");
                    });
                }
            });
        }

        function doExport(){
            var excel=new excelData(grid);
            excel.addEvent('beforeGetData',function (grid,rows) {
                return grid.getSelecteds();
            })
            excel.export("客户信息.xls");
        }

        function doImport() {
            var con ={};
            con['Title'] = '客户信息导入';
            con['TemplateUrl'] = '/static/template/客户导入模板.xls';
            con['openUrl'] = '/work/clientInfo/ImportClientData?Type='+mode+'&Code=client&FileName='+encodeURIComponent('客户信息导入模板.xls');
            con['dataGrid'] = grid;
            con['mode'] = 'data';
            ImportManage(con);
        }
        function reset(){
            var form=new mini.Form('#highQueryForm');
            form.reset();
            txtQuery.setValue(null);
            comField.setValue('All');
            doHightSearch();
        }
    </script>
</@layout>