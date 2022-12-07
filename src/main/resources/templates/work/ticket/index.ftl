<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript" src="/js/layui/layui.js"></script>
    <script type="text/javascript">
        var querFields = [
            {id: 'All', text: '全部属性'},
            {id: 'SHENQINGRXM', text: '专利申请人'},
            {id: 'SHENQINGH', text: '专利申请号'},
            {id: 'FAMINGMC', text: '发明名称'},
            {id: 'PayCompany', text: '缴款单位'},
            {id: 'TicketCode', text: '发票号码'},
            {id: 'KH', text: '所属客户'},
            {id: 'XS', text: '销售人员'},
            {id: 'DL', text: '代理责任人'},
            {id: 'LC', text: '流程人员'}
        ];
    </script>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }
        .showCellTooltip1 {
            color: blue;
            text-decoration: underline;
        }
        .ui-tooltip {
            max-width: 850px;
        }
    </style>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center" showheader="false">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-upload" id="Ticket_Upload" onclick="addFile()">上传票据</a>
                            <a class="mini-button" iconcls="icon-remove" id="Ticket_Remove" onclick="deleteFile()
">删除</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconCls="icon-download" id="Ticket_Download" onclick="download()">下载附件</a>
                            <a class="mini-button" iconCls="icon-zoomin" id="Ticket_Image"
                               onclick="viewDocument">在线查看</a>
                            <a class="mini-button" iconCls="icon-user" id="Ticket_Email" onclick="sendEmail">发送给客户</a>
                            <a class="mini-button" iconCls="icon-xls" id="Ticket_Export" onclick="exportExcel">导出</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field PatentInfoBrowse_Query" id="comField"
                                 style="width:100px" data="querFields" value="All" id="Field"></div>
                            <input class="mini-textbox" style="width:250px" emptyText="专利名称/专利号" id="queryText"/>
                            <a class="mini-button mini-button-success" onclick="doQuery">模糊查询</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt" style="width:100%;height:100%;overflow:hidden">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%" multiSelect="true"
                     url="/work/ticket/getData" autoload="true" sortfield="payDate" sortorder="desc"
                     pagesize="20" ondrawcell="onShowEmail" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="SENDMAIL" width="70" headeralign="center" align="center">邮件发送</div>
                        <div field="Action" width="75" headerAlign="center" align="center">备注信息</div>
                        <div field="Shenqingh" width="150" headeralign="center" allowsort="true" align="center">专利号
                        </div>
                        <div field="FAMINGMC" width="300" headeralign="center" allowsort="true" align="center">专利名称
                        </div>
                        <div field="SHENQINGRXM" width="200" headeralign="center" allowsort="true" align="center">申请人姓名
                        </div>
                        <div field="NEIBUBH" headeralign="center" allowsort="true" width="300" align="center">内部编号
                        </div>
                        <div field="PayCompany" width="200" headerAlign="center" width="200" align="center"
                             allowSort="true">缴款单位
                        </div>
                        <div field="TicketType" width="120" headeralign="center" allowsort="true" align="center">票据编码
                        </div>
                        <div field="TicketCode" width="120" headeralign="center" allowsort="true" align="center">票据号码
                        </div>
                        <div field="TicketName" width="120" headeralign="center" allowsort="true" align="center">票据名称
                        </div>
                        <div field="Money" width="100" headeralign="center" allowsort="true" align="center">缴费金额</div>
                        <div field="PayDate" dataType="date" dateFormat="yyyy-MM-dd" width="150" headeralign="center"
                             allowsort="true" align="center">缴费日期
                        </div>
                        <div field="CreateMan" headerAlign="center" align="center" type="treeselectcolumn"
                             allowSort="true">上传人员
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="CreateTime" dataType="date" dateFormat="yyyy-MM-dd" width="150" headeralign="center"
                             allowsort="true" align="center">上传日期
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var tip = new mini.ToolTip();
        var layer = null;
        layui.use(['layer'], function () {
            layer = layui.layer;
        });
        var grid = mini.get('#datagrid1');
        var txtQuery = mini.get('#queryText');
        var comField = mini.get('#comField');
        function addFile() {
            var idx = mini.open({
                url: '/attachment/addFile?Mode=Add&UploadUrl=/work/ticket/upload&ManyFile=false',
                width: 800,
                height: 400,
                title: "上传票据文件",
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data, xmini, xwindow) {
                        xmini.alert('票据文件上传并解析成功', '系统提示', function () {
                            xwindow.CloseOwnerWindow('yes');
                        });
                    });
                    iframe.contentWindow.addEvent('uploadError', function (result, xmini, xwindow) {
                        xmini.alert(packageLine(result.message), '文件上传失败', function () {
                            xwindow.CloseOwnerWindow('ok');
                        });
                    });
                },
                ondestroy: function () {
                    grid.reload();
                }
            });
        }
        function  packageLine(msg){
            if(!msg) return "";
            var all=[];
            var single=[];
            var ms=msg.split(',');
            if(ms<=6) return msg;
            for(var i=0;i<ms.length;i++){
                var m=ms[i];
                single.push(m);
                if(single.length==3){
                    all.push(single.join(','));
                    single=[];
                }
            }
            if(single.length>0){
                all.push(single.join(','));
            }
            return all.join('<br/>');
        }
        function deleteFile() {
            var rows = grid.getSelecteds() ||[];
            if (rows.length>0) {

                function g(){
                    var ids=[];
                    for(var i=0;i<rows.length;i++) {
                        var row=rows[i];
                        var id=row["ID"];
                        ids.push(id);
                    }
                    var url='/work/ticket/delete';
                    $.post(url,{IDS:ids.join(',')},function(result){
                        if(result.success){
                            mini.alert('选择的票据记录已删除成功!','系统提示',function(){
                                grid.reload();
                            });
                        } else mini.alert(result.message || "记录删除失败，请稍候重试!");
                    });
                }
                mini.confirm('确认要删除选择的票据记录？','系统提示',function(result){
                    if(result=='ok')g();
                });
            } else {
                mini.alert('请选择要删除的记录!');
            }
        }

        function viewDocument() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["ID"];
                    codes.push(code);
                }
                var arg = {'ID': codes.join(",")};
                var url = '/work/ticket/getImages';
                $.getJSON(url, arg, function (result) {
                    mini.unmask('body');
                    var isOK = parseInt(result.status);
                    if (isOK == 1) {
                        window.parent.showImages(mini.encode(result));
                    } else {
                        var msg = result.message || "无法加载通知书附件。";
                        layer.alert(msg);
                    }
                });
            }
        }

        function sendEmail() {
            var rows = grid.getSelecteds();
            var KHID=null;
            if (rows.length > 0) {
                var vs = [];
                var pps=[];
                KHID=rows[0]["ClientID"];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var r = {
                        index: i + 1, SHENQINGH: row["Shenqingh"] || "", FAMINGMC: row["FAMINGMC"],
                        TICKETCODE: row["TicketCode"], MONEY: row["Money"], TICKETNAME: row["TicketName"]
                    };
                    vs.push(r);

                    var obj = {TONGZHISBH: row["TicketCode"], SHENQINGH: row["Shenqingh"]};
                    pps.push(obj);
                }
                var iid = mini.open({
                    url: '/common/email/index?Code=Ticket&KH='+KHID,
                    width: 1000,
                    height: 580,
                    title: '发送邮件',
                    showModal: true,
                    allowResize: false,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        iframe.contentWindow.getContent("", mini.encode(vs));
                        if (rows.length > 0) {
                            var codes = [];
                            for (var i = 0; i < rows.length; i++) {
                                var row = rows[i];
                                codes.push(row["ID"]);
                            }
                            var code = codes.join(",");
                            var url = '/work/ticket/getFilesByID';
                            $.getJSON(url, {IDS: code}, function (r) {
                                if (r.success) {
                                    var ds = r.data || [];
                                    iframe.contentWindow.addAttachment(ds);
                                    iframe.contentWindow.setSubject(ds.length + "个票据附件.zip");
                                    iframe.contentWindow.setRecords(pps);
                                }
                            })
                        }
                        iframe.contentWindow.addEvent('complete', function (obj) {
                            grid.reload();
                            return;
                            var clients = [];
                            var receiver = [];
                            var adds = obj["receAddress"] || [];
                            for (var i = 0; i < adds.length; i++) {
                                var add = adds[i];
                                clients.push(add.value);
                                receiver.push(add.text);
                            }
                            var os = [];
                            for (var i = 0; i < rows.length; i++) {
                                var row = rows[i];
                                var obj = {TONGZHISBH: row["TicketCode"], SHENQINGH: row["Shenqingh"]};
                                obj.Client = clients.join(',');
                                obj.Email = receiver.join(',');
                                os.push(obj);
                            }
                            var url = '/work/notice/addEmailRecord';
                            $.post(url, {Data: mini.encode(os)}, function (result) {
                                if (result.success == false) {
                                    mini.alert('邮件发送记录保存失败:' + result.message + ',请联系系统管理员解决!否则会导致数据记录不完整', '系统提赤');
                                } else {
                                    mini.hideMessageBox(iid);
                                    doReload(grid);
                                }
                            });
                        });
                    }
                });
            }
        }

        function afterload(e) {
            tip.set({
                target: document,
                selector: '.showCellTooltip',
                onbeforeopen: function (e) {
                    e.cancel = false;
                },
                onopen: function (e) {
                    var el = e.element;
                    if (el) {
                        var code = $(el).attr('code');
                        if (code) {
                            var url = '/work/notice/getEmailRecord?ID=' + code;
                            $.getJSON(url, {}, function (r) {
                                if (r.success) {
                                    var ds = r.data || [];
                                    if (ds.length > 0) {
                                        var Memo = ds.join('<br/><br/>');
                                        if (Memo) {
                                            tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + Memo + '</td></tr></table>');
                                        } else tip.hide();
                                    } else tip.hide();
                                } else tip.hide();
                            });
                        }
                        else {
                            var xCode=$(el).attr('xcode');
                            if(xCode){
                                var rows = grid.getData();
                                var row = grid.findRow(function (row) {
                                    if (row["Shenqingh"] == xCode) return true;
                                });
                                if (row) {
                                    var memo = row["MEMO"];
                                    if (memo) {
                                        tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + row["MEMO"] + '</td></tr></table>');
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }

        function onShowEmail(e) {
            var field = e.field;
            if (!field) return;
            if (field == "SENDMAIL") {
                var record = e.record;
                var issend = record["SENDMAIL"];
                if (issend == null || issend == undefined) return;
                var dx = parseInt(issend);
                var fid = record['TicketCode'];
                if (dx == 1) e.cellHtml = '<a href="#" data-placement="bottomleft" class="showCellTooltip" code=' + "'" + fid + "'" + '>已发送</a>'; else e.cellHtml = "未发送";
            }
            else if (field == "Action") {
                var record = e.record;
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMEMO"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green;text-align:center'>添加</span>" : "<span " +
                    "style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay;text-align:center'>查看</span>";
                }
                e.cellHtml = '<a href="#"  data-placement="bottomleft"  xcode="' + record["Shenqingh"] + '" ' +
                    'class="showCellTooltip" onclick="ShowMemo(' + "'" + record["Shenqingh"] + "'," + "'" + record["FAMINGMC"] + "'" + ')">' + text + '</a>';
            }
        }

        function download() {
            debugger;
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["ID"];
                    name = row["FAMINGMC"] + '-' + row["TicketCode"] + '.zip';
                }
            } else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["ID"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个缴费发票打包下载.zip';
            }
            var url = '/work/ticket/download?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback:function(xurl){

                },
                failCallback: function (html, xurl) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
        }
        function ShowMemo(id, title) {
            mini.open({
                url: '/work/addMemo/index?ID=' + id,
                showModal: true,
                width: 1000,
                height: 500,
                title: "【" + title + "】的电子票据备注信息",
                onDestroy: function () {
                    grid.reload();
                }
            });
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
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL" || f=="BH") f = "NEIBUBH";
                        var kWork = f + '=' + word;
                        if (cs.indexOf(kWork) == -1) {
                            var op = {field: f, oper: 'LIKE', value: word};
                            cs.push(op);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                    var op = {field: field, oper: 'LIKE', value: word};
                    cs.push(op);
                }
            }
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function exportExcel(){
            var excel=new excelData(grid);
            excel.export("发票明细记录.xls");
        }
    </script>
</@layout>
