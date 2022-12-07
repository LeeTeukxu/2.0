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
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript" src="/js/jquery.fileDownload.js"></script>
 <link href="/res/css/workspace.css" rel="stylesheet" media="all"/>
<script type="text/javascript">
    var states = [
        {id: 5, text: '商标版权业务协作申报情况-未申报'},
        {id: 6, text: '商标版权业务协作申报情况-已申报'}
    ];
</script>
<div class="mini-fit" style="overflow:hidden">
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" bodyStyle="overflow:hidden">
            <div id="toolbar1" class="mini-toolbar" style="padding:5px;">
                <table style="width:100%;" cellpadding="0" cellspacing="0">
                    <tr>
                        <td style="width:100%;">
                            <a class="mini-button" iconcls="icon-ok" id="TradeCases_TradeTechSBPass"
                               onclick="showTechSB(1)">流程申报</a>
                            <a class="mini-button" iconcls="icon-no" id="TradeCases_TradeTechSBRoll"
                               onclick="showTechSB(0)">申报驳回</a>
                            <a class="mini-button" iconCls="icon-xls" id="TradeCases_Export"
                               onclick="doExport()">导出Excel</a>
                            <a class="mini-button" iconCls="icon-download" id="TradeCases_Download"
                               onclick="downloadFile()">下载文件</a>
                            <a class="mini-button" iconcls="icon-zoomin" id="TradeCases_Browse"
                               onclick="browseCases">查看交单信息</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox TradeCases_Query" style="width:250px"
                                   emptyText="备注/业务状态/立案编号/类型/业务名称/客户名称/商标或版权名称/商标类别/申报人/申报说明"
                                   id="queryText"/>
                            <a class="mini-button mini-button-success TradeCases_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
                            <a class="mini-button mini-button-danger TradeCases_Reset" id="a2"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info TradeCases_HighQuery" onclick="expand"
                               iconCls="icon-expand">高级查询</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="p1" style="border:1px solid #909aa6;border-top:0;height:180px;padding:5px;">
                <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                    <tr>
                        <td style="width:6%;padding-left:10px;">业务状态：</td>
                        <td style="width:15%;"><input class="mini-combobox" data="states" name="State"
                                                      data-oper="EQ"
                                                      style="width:100%"/></td>
                        <td style="width:80px;text-align: center;">类型</td>
                        <td>
                            <input class="mini-combobox" name="YType"  style="width:100%"
                                   url="/work/productItemType/getItems" valueField="text" valueFormSelect="true"
                                   data-oper="EQ"/>
                        </td>
                        <td style="width:80px;text-align: center;">业务名称</td>
                        <td><input class="mini-textbox" name="YName" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:100px;text-align: center;">商标或版权名称</td>
                        <td><input class="mini-textbox" name="TCName" style="width:100%" data-oper="LIKE"/></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">商标类别</td>
                        <td><input class="mini-textbox" name="TCCategory" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">申报绝限</td>
                        <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%"
                                   data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:100%"
                                   data-oper="LE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">申报人</td>
                        <td><input name="TechMan" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   allowInput="true" textField="Name" valueField="FID" parentField="PID"
                                   style="width:100%"
                                   data-oper="EQ"/></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">申报时间</td>
                        <td><input class="mini-datepicker" name="FilingTime" style="width:100%"
                                   data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="FilingTime" style="width:100%"
                                   data-oper="LE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">立案编号</td>
                        <td><input class="mini-textbox" name="SubNo" style="width:100%" data-oper="LIKE"/></td>
                        <td style="width:80px;text-align: center;">客户名称</td>
                        <td><input class="mini-textbox" name="ClientName" style="width:100%" data-oper="LIKE"/></td>
                    </tr>
                    <tr>
                        <td style="width:80px;text-align: center;">签约日期</td>
                        <td><input class="mini-datepicker" name="SignDate" style="width:100%"
                                   data-oper="GE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">到</td>
                        <td><input class="mini-datepicker" name="SignDate" style="width:100%"
                                   data-oper="LE"
                                   dateFormat="yyyy-MM-dd"/></td>
                        <td style="width:80px;text-align: center;">申报说明</td>
                        <td><input class="mini-textbox" name="TechSbMemo" style="width:100%" data-oper="LIKE"/></td>
                    </tr>
                    <tr>
                        <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                            <a class="mini-button mini-button-success" style="width:120px"
                               href="javascript:doHightSearch();">搜索</a>
                            <a class="mini-button mini-button-danger" style="margin-left:30px;width:120px"
                               href="javascript:expand();">收起</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" onDrawCell="onDraw" multiSelect="true"
                     showColumnsMenu="true"  sortfield="SubNo"
                     sortorder="desc" pagesize="20" onload="afterload" url="/work/tradeCasesTech/getData?State=${State}"
                     autoload="true" onselectionchanged="onSelectionChanged">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="Memo" width="60" align="center" headerAlign="center">备注</div>
                        <div type="comboboxcolumn" field="State" width="210" headerAlign="center" align="center" allowsort="true">业务状态
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="SignDate" headerAlign="center" align="center" dataType="date" width="130"
                             dateFormat="yyyy-MM-dd" vtype="required" allowsort="true">签约日期
                            <input property="editor" class="mini-datepicker"/>
                        </div>
                        <div field="SubNo" headerAlign="center" align="center" width="150" allowsort="true">立案编号</div>
                        <div field="YType" headerAlign="center" align="center" width="80" allowsort="true">类型</div>
                        <div field="YName" headerAlign="center" align="center" vtype="required" width="180" allowsort="true">业务名称</div>
                        <div field="ClientName" headerAlign="center" align="center" vtype="required" width="180" allowsort="true">客户名称</div>
                        <div field="TCName" width="150" headerAlign="center" align="center" allowsort="true">
                            商标或版权名称
                            <input property="editor" class="mini-textbox"/>
                        </div>
                        <div field="TCCategory" width="80" headerAlign="center" align="center" allowsort="true">
                            商标类别
                            <input property="editor" class="mini-textbox"/>
                        </div>
                        <div field="ClientRequiredDate" width="130" headerAlign="center" align="center"
                             dataType="date" dateFormat="yyyy-MM-dd" allowsort="true">客户要求申报绝限
                            <input property="editor" class="mini-datepicker" dateFormat="yyyy-MM-dd"/>
                        </div>
                        <div field="TechFiles" headerAlign="center" align="center" width="100">申报资料</div>
                        <#if State gt 5>
                            <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn"
                                allowSort="true">申报人
                                <input property="editor" class="mini-treeselect"
                                url="/systems/dep/getAllLoginUsersByDep"
                                textField="Name" valueField="FID" parentField="PID"/>
                            </div>
                            <div field="FilingTime" headerAlign="center" align="center" dataType="date" width="130"
                                 dateFormat="yyyy-MM-dd" vtype="required" allowsort="true">申报时间
                                <input property="editor" class="mini-datepicker"/>
                            </div>
                            <div field="TechSbMemo" headerAlign="center" align="center" width="100" allowsort="true">申报说明
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    <div class="mini-window" title="指定技术员" width="600" height="200" style="display:none" id="SetTechManWindow">
        <table style="width:100%" class="layui-table" id="SetTechManForm">
            <tr>
                <td>指定代理师</td>
                <td>
                    <div name="AllocTechMan" class="mini-treeselect" style="width:100%"
                         url="/systems/dep/getAllLoginUserByFun?FunName=AcceptTech" required="true" expandonload="true"
                         textField="Name" valueField="FID" parentField="PID" allowInput="true"
                         valueFromSelect="true"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="acceptTechMan();">确认操作</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('SetTechManWindow')
">取消操作
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="是否流程申报" width="600" height="240" style="display:none" id="TechSBWindow">
        <table style="width:100%" class="layui-table" id="TechSBForm">
            <tr>
                <td style="width:100px">申报说明</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" name="SBMemo"></textarea>
                </td>
            </tr>
            <tr>
                <td>申报结果</td>
                <td>
                    <input style="width:100%" name="SBResult" class="mini-combobox"
                           data="[{id:'1',text:'流程申报'},{id:'0', text:'流程不申报'}]" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <button class="mini-button mini-button-success" onclick="techSB();">确认操作</button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px" onclick="closeWindow('TechSBWindow')
">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var roleName = '${RoleName}';
        var userId = '${UserID}';
        var grid = mini.get('datagrid1');
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        var cmdBrowse = mini.get('#TradeCases_Browse');
        var cmdPass = mini.get('#TradeCases_TradeTechSBPass');
        var cmdRoll = mini.get('#TradeCases_TradeTechSBRoll');
        var txtQuery = mini.get('queryText');
        var cmdA2 = mini.get('a2');
        var cmdA3 = mini.get('a3');
        $(function () {
            $('#p1').hide();
            cmdA2.hide();
        })

        function expand(e) {
            e = e || {};
            var btn = e.sender;
            if (!btn) {
                btn = mini.get('#TradeCases_HighQuery');
            }
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setText("隐藏");
                btn.setIconCls("icon-collapse");
                $('#p1').css('display', "block");
                cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setText("高级查询");
                btn.setIconCls("icon-expand");
                $('#p1').css('display', "none");
                cmdA2.hide();
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
        }

        function browseCases() {
            var row = grid.getSelected();
            if (row) {
                var id = row.MainID;
                mini.open({
                    url: '/work/tradeCases/edit?Mode=Browse&ID=' + id,
                    width: '100%',
                    height: '100%',
                    showModal: true,
                    ondestroy: function () {
                        grid.reload();
                    }
                });
            }
        }

        function afterload() {
            var parent= window.parent;
            if(parent)parent.updateStateNumbers();
            tip.set({
                target: document,
                selector: '.showCellTooltip',
                onbeforeopen: function (e) {
                    e.cancel = false;
                },
                onopen: function (e) {
                    var el = e.element;
                    if (el) {
                        var code = $(el).attr('hCode');
                        var field = $(el).attr('field');
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["SubID"] == code) return true;
                        });
                        if (row) {
                            if (field == "Memo") {
                                var memo = row["ProcessMemo"];
                                if (memo) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                        'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                        'text-top; "> <tr><td>' + memo + '</td></tr></table>');
                                }
                            } else if (field == "TCName"){
                                var sqText = row["SQText"];
                                if (sqText) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                        'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                        'text-top; "> <tr><td>' + sqText + '</td></tr></table>');
                                }
                            } else if (field == "TCCategory"){
                                var cgText = row["CGText"];
                                if (cgText) {
                                    tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                        'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                        'text-top; "> <tr><td>' + cgText + '</td></tr></table>');
                                }
                            }
                        }
                    }
                }
            });
            resetAllButton();
        }

        function resetAllButton() {
            cmdBrowse.hide();
            cmdPass.hide();
            cmdRoll.hide();
        }

        function auditCases() {
            // var row = grid.getSelected();
            // if (row) {
            //     var id = row.CASESID;
            //     mini.open({
            //         url: '/work/jieDanDetail/index?CasesID=' + id,
            //         title: '认领专利交单',
            //         width: 1000,
            //         height: 400,
            //         showModal: true,
            //         ondestroy: function (action) {
            //             grid.reload();
            //         }
            //     });
            // } else mini.alert('选择要审核的记录!');
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var win = mini.get('#SetTechManWindow');
                win.show();
            } else mini.alert('请选择要处理的案件记录!');
        }
        function auditFiles() {
            var row = grid.getSelected();
            if (row) {
                var id = row.CASESID;
                mini.open({
                    url: '/work/commitFile/index?CasesID=' + id,
                    title: '上传技术文件附件',
                    width: 1100,
                    height: 400,
                    showModal: true,
                    ondestroy: function (action) {
                        grid.reload();
                    }
                });
            } else mini.alert('选择要审核的记录!');
        }
        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            var uText = "查看";
            var type = "Tech";
            var state = parseInt(record["State"] || 0);
            var now = record[field];
            var isOk = false;//只有特定字段才处理。
            var mode = "Browse";
            if (field == "TYSB") {
                var dd = record['TYSB'];
                if (dd == '1') e.cellHtml = '<SPAN style="color:red">已申报</span>';
            }
            if (field == "ISSUBMIT") {
                var dd = record['ISSUBMIT'];
                if (dd == '1') e.cellHtml = '<SPAN style="color:red">已确定</span>';
            }
            else if (field == "KHName") {
                var clientId = record["KHID"];
                var val = e.value;
                if (clientId) {
                    e.cellHtml = '<a href="javascript:void(0)" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            }
            else if (field == "UploadCPC") {
                var casesId = record["CASESID"];
                var upTime = record["CPCUploadTime"];
                var text = (upTime == null) ? "上传" : "上传或下载";
                e.cellHtml = '<a href="javascript:void(0)" onclick="onFileupload(' + "'" + casesId + "'" + ')">' + text + '</a>';
            }
            else if (field == "UploadReq") {
                var casesId = record["CASESID"];
                var upTime = record["ReqUploadTime"];
                var text = (upTime == null) ? "上传" : "上传或下载";
                e.cellHtml = '<a href="javascript:void(0)" onclick="onReqUpload(' + "'" + casesId + "'" + ')">' + text + '</a>';
            }
            else if (field == "Memo") {
                var dd = record[field] || "";
                if (dd == "[]") dd = null;
                var x = '<a href="javascript:void(0)"  field="Memo"  data-placement="bottomleft"  style="color:blue;' +
                    'text-decoration:underline" ' +
                    'hcode="' + record["SubID"] + '"' +
                    ' class="showCellTooltip" onclick="showMemo(' + "'" + record._id + "'" + ')">' + (dd ? "查看" : "添加") + '</a>';
                e.cellHtml = x;
            }
            else if (field == "JSNames") {
                var nNames = record["JSNames"]
                var ONames = record["JSName"];
                if (typeof (nNames) == "undefined") {
                    nNames = "";
                }
                if (typeof (ONames) != "undefined") {
                    ONames = ONames + ",";
                }
                else {
                    ONames = "";
                }
                var result = ONames + nNames;
                e.cellHtml = "<span>" + result + "</span>";
            }else if (field == "TechFiles"){
                if (state < 6){
                    if (now){
                        uText = "管理";
                        mode = "Edit";
                    }else {
                        uText = "上传";
                        mode = "Add";
                    }
                }
                isOk = true;
            }else if (field == "TCName"){
                if (state < 6){
                    var ttt = record["TCName"] || "&nbsp;&nbsp;";
                    var x = '<a href="javascript:void(0)"  field="TCName"  data-placement="bottomleft"  style="color:blue;' +
                        'text-decoration:underline" ' +
                        'hcode="' + record["SubID"] + '"' +
                        ' class="showCellTooltip" onclick="changeTcName(' + "'" + record._id + "'" + ')">' + ttt + '</a>';
                    e.cellHtml = x;
                }
            }else if (field == "TCCategory"){
                if (state < 6){
                    var ttt = record["TCCategory"] || "&nbsp;&nbsp;";
                    var x = '<a href="javascript:void(0)"  field="TCCategory"  data-placement="bottomleft"  style="color:blue;' +
                        'text-decoration:underline" ' +
                        'hcode="' + record["SubID"] + '"' +
                        ' class="showCellTooltip" onclick="changeTcCategory(' + "'" + record._id + "'" + ')">' + ttt + '</a>';
                    e.cellHtml = x;
                }
            }
            if (isOk) {
                var x = '<a href="javascript:void(0)" style="color:blue;text-decoration:underline"' +
                    ' onclick="uploadRow(' + "'" + record._id + "','" + type + "'," + "'" + mode + "')" + '">' +
                    '&nbsp;' + uText + '&nbsp;</a>';
                e.cellHtml = x;
            }
        }
        function changeTcName(id) {
            var record = grid.getRowByUID(id);
            if (record) {
                var TcName = record["TCName"] || "";
                var uid = mini.prompt("请输入商标或版权名称：", "更改商标或版权名称", function (action, value) {
                    if (action == 'ok') {
                        value = $.trim(value);
                        if (!value) {
                            mini.alert('商标或版权名称不能为空!');
                            return;
                        }
                        if (value == TcName) {
                            mini.alert('商标或版权名称未发生更改，无需保存!');
                            return;
                        }

                        function g() {
                            var url = '/work/tradeCasesTech/changeTcName';
                            var arg = {SubID: record["SubID"], NewShenqingName: value};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('商标或版权名称更新成功!', '系统提示', function () {
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || "更新失败，请稍候重试!");
                            })
                        }

                        g();
                    }
                }, false)
                var win = mini.getbyUID(uid);
                win.setWidth(400);
                $(win.el).find("textarea,input").width(340).val(TcName);
            }
        }
        function changeTcCategory(id) {
            var record = grid.getRowByUID(id);
            if (record) {
                var TcName = record["TCCategory"] || "";
                var uid = mini.prompt("请输入商标类别：", "更改商标类别", function (action, value) {
                    if (action == 'ok') {
                        value = $.trim(value);
                        if (!value) {
                            mini.alert('商标类别不能为空!');
                            return;
                        }
                        if (value == TcName) {
                            mini.alert('商标类别未发生更改，无需保存!');
                            return;
                        }

                        function g() {
                            var url = '/work/tradeCasesTech/changeTcCategory';
                            var arg = {SubID: record["SubID"], NewShenqingName: value};
                            $.post(url, arg, function (result) {
                                if (result.success) {
                                    mini.alert('商标类别更新成功!', '系统提示', function () {
                                        grid.reload();
                                    });
                                } else mini.alert(result.message || "更新失败，请稍候重试!");
                            })
                        }

                        g();
                    }
                }, false)
                var win = mini.getbyUID(uid);
                win.setWidth(400);
                $(win.el).find("textarea,input").width(340).val(TcName);
            }
        }
        function uploadRow(id, type, mode) {
            var row = grid.getRowByUID(id);
            if (row) {
                var subId = row["SubID"];
                var casesId = row["CasesID"];
                var url = '/cases/getSubFiles';
                $.getJSON(url, {SubID: subId, Type: type}, function (result) {
                    if (result.success) {
                        var att = result.data || [];
                        doUpload(casesId, subId, att, type, row, mode);
                    }
                });
            }
        }
        function doUpload(casesId, subId, ids, type, row, mode) {
            var title = '商标版权交单申请资料';
            var showHis = 0;
            // if (type == "Accept" || type == "Exp" || type == "Aud") showHis = 1;
            var attId = "";
            if (ids.length > 0) attId = ids.join(",");
            mini.open({
                url: '/attachment/addFile?IDS=' + attId + '&Mode=' + mode + '&ShowHis=' + showHis,
                width: 800,
                height: 400,
                title: title,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                        var attId = data.AttID;
                        var url = '/work/tradeCases/saveSubFiles';
                        var arg = {CasesID: casesId, SubID: subId, AttID: attId, Type: type};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                var field = "ZLFiles";
                                if (type == "Tech") field = "TechFiles";
                                var obj = {};
                                obj[field] = attId;
                                var now = row[field];
                                if (!now) grid.updateRow(row, obj);
                            }
                        })
                    });
                    iframe.contentWindow.addEvent('eachFileRemoved', function (data) {
                        var casesID = row["CasesID"];
                        if (casesID) {
                            var url = '/cases/removeSubFiles';
                            $.post(url, {CasesID: casesID, AttID: data.ATTID}, function (result) {
                                if (result.success == false) {
                                    mini.alert('删除文件信息失败，请联系管理员解决问题。');
                                } else {
                                    doReload(grid);
                                }
                            })
                        }
                    });
                    if (type == "Accept" && mode != 'Browse') {
                        iframe.contentWindow.enableCommit(grid);
                    }
                },
                ondestroy: function () {
                    if (type != 'Exp' && type != 'Aud') grid.reload();
                }
            });
        }
        function onSelectionChanged(e) {
            if (e.selecteds.length == 0) {
                resetAllButton();
                return;
            }
            resetAllButton();
            var rows = grid.getSelecteds();
            if (rows.length == 0) return;

            var record = rows[0];
            var state = parseInt(record.State || 1);
            var techMan = parseInt(record["TechMan"] || "0");

            var xTechManager=record["TechManager"] || "";
            var tMs=xTechManager.split(',');
            var techManagers=[];
            for(var i=0;i<tMs.length;i++){
                var t=$.trim(tMs[i]);
                if(!t) continue;
                techManagers.push(parseInt(t));
            }

            cmdBrowse.show();
            if (state == 5) {
                cmdPass.show();
                cmdRoll.hide();
            } else if (state == 6) {
                cmdPass.hide();
                cmdRoll.show();
            }
        }
        /*
            指定代理师。
        */
        function acceptTechMan() {
            var techManForm = new mini.Form('#SetTechManForm');
            techManForm.validate();
            if (techManForm.isValid() == false) {
                mini.alert('请选择人员以后再进行确认操作!');
                return;
            }
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["CASESID"]);
            }
            if (subs.length == 0) {
                mini.alert('请选择要指定代理师的业务单据!');
                return;
            }
            var data = techManForm.getData();
            var techMan = data['AllocTechMan'];

            function g() {
                var iis = mini.loading('正在提交信息，请稍候.........');
                var url = '/work/tradeCasesComplete/acceptTechTask';
                var arg = {CASESIDS: subs.join(','), TechMan: techMan};
                $.post(url, arg, function (result) {
                    mini.hideMessageBox(iis);
                    if (result.success) {
                        mini.alert('案件代理师设置成功!', '系统提示', function () {
                            var win = mini.get('#SetTechManWindow');
                            win.hide();
                            grid.reload();
                        });
                    } else mini.alert(result.message || '案件代理师设置失败，请稍候重试!');
                })
            }

            mini.confirm('确认要给选择的案件指定技术员吗？', '系统提示', function (act) {
                if (act == 'ok') g();
            });
        }
        function showTechSB(result) {
            var win = mini.get('#TechSBWindow');
            var form = new mini.Form('#TechSBForm');
            form.reset();
            mini.getbyName('SBResult').setValue(result);
            win.show();
        }
        function techSB() {
            var win = mini.get('#TechSBWindow');
            var form = new mini.Form('#TechSBForm');
            var data = form.getData();
            data.AuditMemo = mini.getbyName('SBMemo').getValue();
            data.AuditResult = mini.getbyName('SBResult').getValue();
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var TechFiles = row["TechFiles"];
                    if (TechFiles) {
                        cs.push(subId);
                    }else {
                        mini.alert("有未上传申报资料的申报数据！");
                        return;
                    }
                }
                if (cs.length > 0) {
                    function g() {
                        var iis = mini.loading('正在提交信息，请稍候.........');
                        var url = '/work/tradeCasesTech/sbTechFiles';
                        var arg = {SubIDS: cs.join(','), Memo: data.AuditMemo, Result: data.AuditResult};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                mini.hideMessageBox(iis);
                                mini.alert('选择的流程申报成功', '系统提示', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "申报失败，请稍候重试!");
                        });
                    }

                    var ttext = "确认要进行流程申报?";
                    if (data.AuditResult == 0) ttext = "确认要将流程设置为暂不申报?";
                    mini.confirm(ttext, '系统提示', function (act) {
                        if (act == 'ok') g();
                    });
                }
            } else mini.alert('选择要处理的案件记录!');
        }
        function closeWindow(winName) {
            var win = mini.get('#' + winName);
            if (win) win.hide();
        }
        function doQuery() {
            //备注/业务状态/立案编号/类型/业务名称/客户名称/商标或版权名称/商标类别/申报人/申报说明
            var txt = txtQuery.getValue();
            var cs = [];
            var arg = {};
            if (txt) {
                var fields = ['Memo', "SBQK", "SubNo", "YType", "YName", "ClientName", "TCName", "TCCategory", "SBR", "TechSbMemo"];
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {field: field, oper: 'LIKE', value: txt};
                    cs.push(obj);
                }
            }
            if (cs.length > 0) {
                arg["Query"] = mini.encode(cs);
            }
            grid.load(arg);
        }
        function showMemo(ID) {
            var title='添加工作进度信息';
            var record = grid.getRowByUID(ID);
            var subId = record["SubID"];
            var rows=grid.getSelecteds();
            if(rows.length>1){
                var c=[];
                for(var i=0;i<rows.length;i++){
                    c.push(rows[i]["SubID"]);
                }
                subId=c.join(',');
                title='批量添加工作进度信息';
            }
            mini.open({
                url: '/addSingleMemo/index?ID=' + subId,
                width: 1000,
                height: 600,
                showModal: true,
                title: title,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var saveUrl = '/casesOutSource/saveMemo?SubID=' + subId;
                    iframe.contentWindow.setConfig(saveUrl);
                    iframe.contentWindow.setSaveImageUrl('/addSingleMemo/saveImage?SubID='+subId);
                },
                onDestroy: function () {
                    grid.reload();
                }
            });
        }
        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            txtQuery.setValue(null);
            comField.setValue('All');
            doHightSearch();
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
        function doExport() {
            var rows = grid.getData();
            if (rows.length > 0) {
                var excel = new excelData(grid);
                excel.export("申报明细.xls");
            } else mini.alert('没有可导出的内容!');
        }
        function downloadFile() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    if (subId) cs.push(subId);
                }

            }
            $.fileDownload('/work/tradeCasesTech/download?SubIDS=' + cs.join(","), {
                httpMethod: 'POST',
                failCallback: function (html, url) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
        }
    </script>
</@js>