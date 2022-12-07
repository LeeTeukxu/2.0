<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript" src="/js/layui/layui.js"></script>
    <script type="text/javascript" src="/js/miniui/pagertree.js"></script>
    <script type="text/javascript">
        mini.parse();
        var users =${Users};
        var users2 = {};
        for (i in users) {
            users2[users[i]] = i;
        }
        var qtTypes = [{id: '风险代理', text: '风险代理'},{id:'普通代理', text: '普通代理'}];
    </script>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }

        .ui-tooltip {
            max-width: 850px;
        }
    </style>
    <div class="mini-layout" style="width:100%;height:100%;overflow:hidden">
        <div region="center">
            <div class="mini-toolbar">
                <table>
                    <tr>
                        <td style="width:100%">
                            <a class="mini-button" iconCls="icon-download" id="PatentInformationManager_Download" onclick="download()">下载附件</a>
                            <a class="mini-button" iconCls="icon-zoomin" id="PatentInformationManager_Image" onclick="viewDocument">在线查看</a>
                            <span class="separator"></span>
                            <a class="mini-button" iconCls="icon-user" id="PatentInformationManager_Email" onclick="sendEmail">发送给客户</a>
                            <a class="mini-button" iconCls="icon-xls"  id="PatentInformationManager_Export" onclick="doExport">导出Excel</a>
                            <a class="mini-button" iconCls="icon-download" plain="true" id="PatentInformationManager_DownloadSource" onclick="downloadSource">下载源文件</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field PatentInformationManager_Query" id="comField"
                                 style="width:150px" data="[{id:'All', text:'全部属性'},{id:'SHENQINGH',text:'申请编号'},
                                 {id:'SHENQINGRXM',text:'申请人'},{id:'FAMINGMC', text:'发明名称'},{id:'KH',text:'所属客户'},
                                 {id:'YW',text:'销售人员'},{id:'JS',text:'代理师'},{id:'LC',text:'流程人员'},{id:'SQTYPE',
                                 text:'代理类型'},{id:'CustomMemo',text:'手动标识'}]"
                                 value="All"></div>
                            <input type="text" id="txtQuery" style="width:200px" class="mini-textbox PatentInformationManager_Query" />
                            <button class="mini-button" iconCls="icon-find" onclick="doQuery"
                                    id="PatentInformationManager_Query">模糊查询</button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit">
                    <div class="mini-pagertree"  id="grid1" style="width:100%;height:100%" checkRecursive="true"
                         idField="FID" parentField="PID" resultAsTree="false" treeColumn="Name"
                         url="/work/allNotice/getData" pageSize="10" showTreeIcon="true"
                         multiSelect="true" autoload="true"   onbeforeselect="onBeforeSelect"  sortField="XIAZAIRQ" sortOrder="Desc"
                         ondrawcell="onShowEmail" onload="afterload" >
                <div property="columns">
                    <div type="checkcolumn"></div>
                    <div width="60" field="DE" align="center" allowSort="true" headerAlign="center"></div>
                    <div field="Action" width="70" headerAlign="center" align="center"  >备注信息</div>
                    <div field="SENDMAIL" name="SENDMAIL" width="70" headerAlign="center" align="center"  >邮件通知</div>
                    <div field="SQTYPE" width="70" align="center" headerAlign="center" type="comboboxcolumn"
                         allowsort="false">代理类型
                        <input property="editor" class="mini-combobox" data="qtTypes"/>
                    </div>
                    <div field="FAWENRQ" headerAlign="center" dataType="date" dateFormat="yyyy-MM-dd" align="center"   width="100" allowSort="true">发文日期</div>
                    <div field="Name" name="Name" width="160" headerAlign="center" allowSort="true">专利申请号</div>
                    <div field="SHENQINGRXM" width="200" headerAlign="center">专利申请人</div>
                    <div field="XIAZAIRQ" dataType="date" dateFormat="yyyy-MM-dd hh:mm:ss" width="160" headerAlign="center" align="center"    allowSort="true">下载日期</div>
                    <div field="FAMINGMC" width="240" headeralign="center" renderer="onZhanlihaoZhuangtai">专利名称</div>
                    <div field="TONGZHISMC" width="150" headerAlign="center">通知书名称</div>
                    <#if RoleName!="客户" && RoleName!="外协代理师">
                        <div field="NEIBUBH" width="250" headerAlign="center">内部编号</div>
                         <#if RoleName?index_of("技术")==-1>
                            <div field="KH" width="180" headerAlign="center" align="center">所属客户</div>
                        </#if>
                        <div field="YW" width="100" headerAlign="center" align="center">销售人员</div>
                        <div field="JS" width="100" headerAlign="center" align="center">代理师</div>
                        <div field="LC" width="100" headerAlign="center" align="center">流程责任人</div>
                    </#if>
                    <div field="FAWENXLH" width="150" headerAlign="center">发文序列号</div>
                    <div width="80" headeralign="center" field="CustomMemo">手动标识</div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#grid1');
        var tip = new mini.ToolTip();
        var cmdDownload=mini.get('PatentInformationManager_Download');
        var cmdView=mini.get('PatentInformationManager_Image');
        var cmdEmail=mini.get('PatentInformationManager_Email');
        var cmdDownloadSource=mini.get('PatentInformationManager_DownloadSource');
        var layer = null;
        layui.use(['layer'], function () {
            layer = layui.layer;
        });
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
                        var hCode = $(el).attr('hCode');
                        if (hCode) {
                            var rows = grid.getData();
                            var row = grid.findRow(function (row) {
                                if (row["FID"] == hCode) return true;
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
                var fid = record['FID'];
                if (dx == 1) e.cellHtml = '<a href="javascript:void(0)" data-placement="bottomleft" class="showCellTooltip"    code=' + "'" + fid + "'" + '>已发送</a>'; else e.cellHtml = "未发送";
            }
            else if (field == "Action") {
                var record = e.record;
                var isLeaf = record["isLeaf"];
                if (isLeaf == false)
                {
                    var memo = record["MEMO"];
                    var editMemo = parseInt(record["EDITMEMO"] || 0);
                    var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
                    if (editMemo == 0) {
                        if (memo) text = "<span style='color:gay'>查看</span>";
                    }
                    e.cellHtml = '<a href="javascript:void(0)"  data-placement="bottomleft"  hCode="' + record["FID"] + '" class="showCellTooltip" onclick="ShowMemo(' + "'" + record["FID"] + "'," + "'" + record["FAMINGMC"] + "'" + ')">' + text + '</a>';
                }
            } else if (field == "KH") {
                var val = e.value;
                if (val) {
                    var clientId = e.record["KHID"];
                    if (clientId) {
                        e.cellHtml = '<a href="javascript:void(0)" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                    } else e.cellHtml = val;
                }
            } else if (field == "Name") {
                var record = e.record;
                if (record) {
                    var tzsPath = record["TZSPATH"] || "";
                    if (tzsPath.length > 3) {
                        e.cellHtml = "<span style='width:30px;height:15px;background-image :url(/appImages/download.gif)'>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
                    }
                    var nameValue = parseInt(record["Name"] || 0);
                    if (nameValue == 0) {
                        e.cellHtml = "--";
                        e.showTreeIcon = false;
                    }
                }
            } else if (field == "FAWENRQ") {


            } else if (field == "DE") {
                var record = e.record;
                var isLeaf = record["isLeaf"];
                if (isLeaf == false) {
                    var iid = record._id;
                    e.cellHtml = '<a href="javascript:void(0)" onclick="detail(' + "'" + iid + "'" + ')">详情..</a>';
                }
            }
        }

        function doQuery() {
            var grid = mini.get('grid1');
            var txtQuery = mini.get('#txtQuery');
            var comField = mini.get('#comField');
            var arg = {};
            var cs = [];
            var ps=[];
            var word = txtQuery.getValue();
            var field = comField.getValue();
            if (word) {
                if (field == "All") {
                    var datas = comField.getData();
                    for (var i = 0; i < datas.length; i++) {
                        var d = datas[i];
                        var f = d.id;
                        if (f == "All") continue;
                        if (f == "KH" || f == "LC" ||  f == "DL" || f=="JS" || f=="YW") f = "NEIBUBH";
                        var kWork=f+'='+word;
                        if(ps.indexOf(kWork)==-1){
                            var op={field:f,oper:'LIKE',value:encodeURI(word)};
                            cs.push(op);
                            ps.push(kWork);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" ||  field == "DL" || field=="YW") field =
                        "NEIBUBH";
                    var op={field:field,oper:'LIKE',value:encodeURI(word)};
                    cs.push(op);
                }
            }
            if(cs.length>0)arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function onBeforeSelect(e) {
            var record = e.record;
            var isLeaf = record["isLeaf"];
            e.cancel = !isLeaf;
            if (isLeaf == true) {
                var tzspath = record["TZSPATH"];
                if (tzspath) e.cancel = false; else e.cancel=true;
            } else e.cancel=true;
        }
        function onSelectRow() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var onShow=true;
                var onEmail=true;
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    var tzsPath=row["TZSPATH"];
                    if(!tzsPath){
                        onShow=false;
                        break;
                    }
                    var sqr=row["SHENQINGRXM"];
                    if(!sqr){
                        onEmail=false;
                        break;
                    }
                }
                if(onShow==true)
                {
                    cmdDownload.show();
                    cmdDownloadSource.show();
                    if(onEmail)cmdEmail.show(); else cmdEmail.hide();
                    cmdView.show();
                } else {
                    cmdDownload.hide();
                    cmdEmail.hide();
                    cmdView.hide();
                    cmdDownloadSource.hide();
                }
                if (rows.length == 1) {
                    cmdDownload.setText('下载附件');
                    cmdEmail.setText('发送客户');
                    code = rows[0]["FID"];
                    isZip = false;
                    filename = rows[0]["Name"].toString();
                }
                else if (rows.length > 1) {
                    cmdDownload.setText('整体打包下载');
                    cmdEmail.setText('整体打包并发送客户');
                    isZip = true;
                    var cs = [];
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        cs.push(row["FID"]);
                    }
                    code = cs.join(',');
                    filename = mini.formatDate(new Date(), 'yyyyMMddHHmmss') + "-" + cs.length + "个通知书打包下载.zip";
                }
            } else {
                cmdDownload.hide();
                cmdEmail.hide();
                cmdView.hide();
                cmdDownloadSource.hide();
            }
        }
        function sendEmail() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var vs = [];
                var pps=[];
                var css=[];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var r = {Index:i+1,SHENQINGRXM: row["SHENQINGRXM"]||"", PID: row["PID"], FAMINGMC:
                    row["FAMINGMC"], TONGZHISMC:row["TONGZHISMC"] };
                    vs.push(r);

                    var p={SHENQINGH:row["PID"],TONGZHISBH:row["FID"]};
                    pps.push(p);

                    css.push(row["KHID"]);
                }
              var iid=  mini.open({
                    url: '/common/email/index?Code=TZS&KH='+css.join(','),
                    width: 1000,
                    height: 580,
                    title: '发送邮件',
                    showModal: true,
                    allowResize:false,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        iframe.contentWindow.getContent("",mini.encode(vs));
                        if (rows.length == 1) {
                            var row = rows[0];
                            var ppath = row["TZSPATH"];
                            var filename = row["TONGZHISMC"];
                            iframe.contentWindow.addAttachment([{ id: ppath, text: filename }]);
                            var title = row["FAMINGMC"] + "-" + filename;
                            iframe.contentWindow.setSubject(title);
                            iframe.contentWindow.setRecords(pps);
                        }
                        else if (rows.length > 1) {
                            var codes = [];
                            for (var i = 0; i < rows.length; i++) {
                                var row = rows[i];
                                codes.push(row["FID"]);
                            }
                            var code = codes.join(",");
                            var url = '/common/email/getAllByCodes';
                            $.getJSON(url, { Code: code }, function (r) {
                                if (r.success) {
                                    var ds = r.data || [];
                                    iframe.contentWindow.addAttachment(ds);
                                    iframe.contentWindow.setSubject(ds.length + "个通知书附件.zip");
                                    iframe.contentWindow.setRecords(pps);
                                }
                            })
                        }
                        iframe.contentWindow.addEvent('complete',function(obj){
                            grid.reload();
                            return ;
                            var clients=[];
                            var receiver=[];
                            var adds=obj["receAddress"] || [];
                            for(var i=0;i<adds.length;i++){
                                var add=adds[i];
                                clients.push(add.value);
                                receiver.push(add.text);
                            }
                            var os=[];
                            for(var i=0;i<rows.length;i++){
                                var row=rows[i];
                                var obj={TONGZHISBH:row["FID"],SHENQINGH:row["shenqingh"]};
                                obj.Client=clients.join(',');
                                obj.Email=receiver.join(',');
                                os.push(obj);
                            }
                            var url='/work/notice/addEmailRecord';
                            $.post(url,{Data:mini.encode(os)},function(result){
                                if(result.success==false){
                                    mini.alert('邮件发送记录保存失败:'+result.message+',请联系系统管理员解决!否则会导致数据记录不完整','系统提赤');
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
        function ShowMemo(id, title) {
            mini.open({
                url: '/work/addMemo/index?ID=' + id+'&MenuName='+currentMenuName,
                showModal: true,
                width: 1000,
                height: 600,
                title: "【" + title + "】的备注信息",
                onDestroy: function () {
                    grid.reload();
                }
            });
        }
        function showClient(clientId) {
            mini.open({
                url:'/work/clientInfo/browse?Type=1&ClientID='+clientId,
                width:'100%',
                height:'100%',
                title:'浏览客户资料',
                showModal:true,
                ondestroy:function(){

                }
            });
            window.parent.doResize();
        }
        function viewDocument() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["FID"];
                    codes.push(code);
                }
                var arg={'tongzhisbh': codes.join(",")};
                var url='/watch/addYearWatchItem/getAllImages';
                $.getJSON(url,arg, function (result) {
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
        function download() {
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["FID"];
                    name = row["FAMINGMC"] + '-' + row["TONGZHISMC"] + '.zip';
                }
            }
            else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["FID"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个通知书打包下载.zip';
            }
/*            var url = '/work/notice/download?Code=' + code + '&FileName=' + encodeURI(name);
            var boxId = mini.open({
                url: url
            });
            boxId.hide();
            event.preventDefault();
            event.stopPropagation();
            return false;*/

            var url = '/work/notice/download?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                failCallback: function (html, xurl) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }
        function downloadSource() {
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["FID"];
                    name = row["FAMINGMC"] + '-' + row["TONGZHISMC"] + '(原件).zip';
                }
            } else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["FID"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个通知书打包下载(原件).zip';
            }
/*            var boxId = mini.open({
                url: '/work/notice/downloadSource?Code=' + code + '&FileName=' + encodeURI(name)
            });
            boxId.hide();
            event.preventDefault();
            event.stopPropagation();
            return false;*/
            var  url= '/work/notice/downloadSource?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                failCallback: function (html, xurl) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }
        function doExport(){
            var excel=new excelData(grid);
            excel.addEvent('beforeGetData',function (grid,rows) {
                return grid.getSelecteds();
            })
            excel.export("通知书记录.xls");
        }
        function changeXS() {
            var jklx="allTZS";
            var rows = mini.clone(grid.getSelecteds());
            if (rows.length == 0) {
                mini.alert('请选择要转移代理师的记录。');
                return;
            }
            mini.open({
                url: '/work/changeTechMan/index',
                title: '转移代理师',
                showModal: true,
                width: 800,
                height: 400,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.setData(rows, users, users2,jklx);
                },
                onDestroy: function () {
                    if (grid) grid.reload();
                }
            });
        }
        function detail(id) {
            var row=grid.getRowByUID(id);
            var shenqingh=row["shenqingh"];
            var bh=row["BH"] ||"";
            var yw=row["YW"] ||"";
            var js=row["JS"] ||"";
            var lc=row["LC"] ||"";
            var kh=row["KH"] ||"";
            mini.open({
                url:'/work/patentInfo/detail?shenqingh='+shenqingh+'&BH='+bh+'&YW='+yw+'&JS='+js+'&LC='+lc+'&KH='+kh,
                width:'100%',
                height:'100%',
                title:'专利详细信息'
            })
        }
    </script>
</@layout>