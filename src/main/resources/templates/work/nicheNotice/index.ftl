<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/layui/layui.js"></script>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExportOther.js"></script>
    <script type="text/javascript">
        mini.parse();
        var zhuangtais = [
            {id: 0, text: '未查看'}, {id: 1, text: '已查看'}, {id: 2, text: '已处理'}, {id: 3, text: '已过期'}
        ];
        var types = [
            {id: 0, text: '发明专利'}, {id: 1, text: '新型专利'}, {id: 2, text: '外观专利'}
        ];
        <#--var documentType = "${Type}";-->
        <#--var encodeType = encodeURI("${Type}");-->
        var tzsStatus =${TZSStatus};
        var readTypes = [{id: '', text: '----'}, {id: 0, text: '未读'}, {id: 1, text: '已读'}];
        var writeReply = [{id: '', text: '----'}, {id: 0, text: '未撰写'}, {id: 1, text: '已撰写'}];
        var commitReply = [{id: '', text: '----'}, {id: 0, text: '未提交'}, {id: 1, text: '已提交'}];
        var hasEnd = [{id: '', text: '----'}, {id: 0, text: '未结案'}, {id: 1, text: '已结案'}];
        var layer = null;
        layui.use(['layer'], function () {
            layer = layui.layer;
        });
    </script>
    <style type="text/css">
        #popupMenu .mini-button .mini-menuitem-text {
            cursor: pointer !important;
        }
    </style>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }

        .ui-tooltip {
            max-width: 850px;
        }
    </style>
    <div class="mini-layout" style="width:100%;height:100%">
            <div region="north" height="33" showHeader="false" showSplit="false" bodyStyle="padding-top:4px;
            overflow:hidden" splitSize="1px">
            <span>
                <span style="margin-left:20px;  border-radius: 50%;height:15px;width:15px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span style="margin-right:30px">已过缴费期</span>
            </span>
                <span>
                <span style="border-radius: 50%;height:15px;width:15px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span style="margin-right:30px">距离缴费日0-15天</span>
            </span>
                <span>
                <span style="border-radius: 50%;height:15px;width:15px;background-color:green">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span style="margin-right:30px">距离缴费日16-30天</span>
            </span>
                <span>
                <span style="border-radius: 50%;height:15px;width:15px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span style="margin-right:30px">距离缴费日31-60天</span>
            </span>
                <span>
                <span style="border-radius: 50%;height:15px;width:15px;background-color:blue">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span style="margin-right:30px">距离缴费日大于60天</span>
            </span>
                <span>
                <span style="width:30px;height:15px;background-image :url(/js/miniui/themes/icons/ok.png);background-repeat: no-repeat;
">&nbsp;&nbsp;&nbsp;&nbsp;
                </span>
                <span>已完成缴费</span>
            </span>
            </div>
                <div region="north" height="33" showheader="false" showsplit="false" bodystyle="padding-top:4px;
                overflow:hidden" splitsize="1px">
                    <span>
                        <span style="margin-left:20px;  border-radius: 50%;height:15px;width:15px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span style="margin-right:30px">已过答复期限</span>
                    </span>
                    <span>
                        <span style="border-radius: 50%;height:15px;width:15px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span style="margin-right:30px">距离答复0-15天</span>
                    </span>
                    <span>
                        <span style="border-radius: 50%;height:15px;width:15px;background-color:green">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span style="margin-right:30px">距离答复16-30天</span>
                    </span>
                    <span>
                        <span style="border-radius: 50%;height:15px;width:15px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span style="margin-right:30px">距离答复31-60天</span>
                    </span>
                    <span>
                        <span style="border-radius: 50%;height:15px;width:15px;background-color:blue">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span style="margin-right:30px">距离答复大于60天</span>
                    </span>
                    <span>
                        <span style="width:30px;height:15px;background-image :url(/js/miniui/themes/icons/ok.png)
">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <span style="margin-right:20px">已提交答复文件</span>
                    </span>
                    <span class="separator"></span>
                    <button class="mini-button NiCheNotice_Config" iconCls="icon-reload"
                            onclick="doFixedQuery('ISREAD','EQ','0')" plain="true">未读
                    </button>
                    <button class="mini-button NiCheNotice_Config" iconCls="icon-reload"
                            onclick="doFixedQuery('ISREAD','EQ','1')" plain="true">已读
                    </button>
                    <button class="mini-button NiCheNotice_Config" iconCls="icon-reload"
                            onclick="doFixedQuery('ISCOMMIT','EQ','1')" plain="true">已处理
                    </button>
                    <button class="mini-button NiCheNotice_Config" iconCls="icon-reload"
                            onclick="doFixedQuery('ISCOMMIT','EQ','0')" plain="true">未处理
                    </button>
                    <button class="mini-button NiCheNotice_Config" iconCls="icon-reload"
                            onclick="doFixedQuery('DAYS','LE','0')" plain="true">已过期
                    </button>
                    <button class="mini-button NiCheNotice_Config" iconCls="icon-reload"
                            onclick="doFixedQuery('DAYS','GT','0')" plain="true">未过期
                    </button>
                </div>
        <div region="center" bodyStyle="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:100%">
                            <a class="mini-button" iconCls="icon-user" onclick="jkConfig"
                               id="NicheNotice_jkConfig">监控设置</a>
                            <a class="mini-button" id="NiCheNotice_setRead" iconCls="icon-ok" plain="true"
                               onclick="setRead(true)">设置已读</a>
                            <a class="mini-button" id="NiCheNotice_setNotRead" iconCls="icon-cancel"
                               plain="true" onclick="setRead(false)">取消已读</a>
                            <a class="mini-button" iconCls="icon-user" onclick="sendEmail"
                               id="NiCheNotice_Email">发送客户</a>
                            <a class="mini-button" iconCls="icon-download" plain="true"
                               id="NiCheNotice_Download"  onclick="download">下载通知书 </a>
                            <a class="mini-button" iconCls="icon-download" plain="true"
                               id="NiCheNotice_DownloadSource" onclick="downloadSource">下载源文件</a>

                            <a class="mini-button" iconCls="icon-zoomin" id="NiCheNotice_Image"
                               onclick="viewDocument">在线查看</a>
                            <a class="mini-button" iconCls="icon-xls" plain="true" onclick="doExport"
                               id="NiCheNotice_Export">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <a class="mini-menubutton" id="NiCheNotice_setpopupMenu" menu="#popupMenu"
                               style="color:blue;" plain="true">通知书状态设置</a>
                            <ul class="mini-menu" id="popupMenu" vertical="true"
                                style="display:none;white-space:nowrap;">
                                <li class="mini-button" style="display:block;" id="NiCheNotice_doJiaJi"
                                    onclick="setJIAJI(true)">设置加急
                                </li>
                                <li class="mini-button" style="display:block;" id="NiCheNotice_unJiaJi"
                                    onclick="setJIAJI(false)">取消加急
                                </li>

                                <li class="mini-button" style="display:block;" id="NiCheNotice_NoticeReply"
                                    onclick="setReply(true)">设置已撰写答复
                                </li>
                                <li class="mini-button" style="display:block;"
                                    id="NiCheNotice_NoticeNotReply" onclick="setReply(false)">设置未撰写答复
                                </li>
                                <li class="separator"></li>
                                <li class="mini-button" style="display:block;"
                                    id="NiCheNotice_NoticeCommit" onclick="setCommit(true)">设置已提交答复
                                </li>
                                <li class="mini-button" style="display:block;"
                                    id="NiCheNotice_NoticeNotCommit" onclick="setCommit(false)">设置未提交答复
                                </li>

                                <li class="mini-button" style="display:block;" id="NiCheNotice_JieAn"
                                    onclick="setJieAn(true)">设置已结案
                                </li>
                                <li class="mini-button" style="display:block;" id="NiCheNotice_NotJieAn"
                                    onclick="setJieAn(false)">设置未结案
                                </li>
                            </ul>
                            <span class="separator  NiCheNotice_Query"></span>
                            <div class="mini-combobox Query_Field NiCheNotice_Query" id="comField" style="width:120px" popupWidth="200"
                                 data="[{id:'All',text:'全部属性'},{id:'SHENQINGH',text:'专利申请号'}, {id:'TZSMC',
                                 text:'通知书名称'}, {id:'ZHUANLIMC',text:'发明名称'},{id:'ZSTATUS',text:'专利状态'}, {id:'SQR',
                                 text:'申请人'},{id:'KH',text:'所属客户'},
                            {id:'XS',text:'销售人员'},{id:'DL',text:'代理责任人'},{id:'LC',text:'流程人员'}]" value="All"></div>
                            <input type="text" class="mini-textbox Query_Field NiCheNoticee_Query"
                                   style="width:180px" id="QueryText"/>
                            <a class="mini-button" iconCls="icon-find" onclick="doQuery()"
                               id="NiCheNotice_Query">模糊搜索</a>
                            <a class="mini-button" id="NiCheNotice_HighQuery"
                               onclick="expand">展开</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:140px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">答复期限：</td>
                            <td style="width:13%;">
                                <input data-oper="GE" class="mini-datepicker" dateformat="yyyy-MM-dd" name="QX"
                                       style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到为止：</td>
                            <td style="width:13%;">
                                <input name="QX" data-oper="LE" dateFormat="yyyy-MM-dd" style="width:100%"
                                       class="mini-datepicker"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">专利名称：</td>
                            <td style="width:13%;">
                                <input class="mini-textbox" data-oper="LIKE" name="ZHUANLIMC" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">专利号：</td>
                            <td style="width:13%;">
                                <input class="mini-textbox" name="SHENQINGH" data-oper="LIKE" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">专利类型：</td>
                            <td style="width:13%;"><input class="mini-combobox" data="types" name="SHENQINGLX"
                                                          data-oper="EQ" style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">专利状态：</td>
                            <td style="width:13%;"><input class="mini-combobox" data="tzsStatus" name="ZSTATUS"
                                                          data-oper="EQ" style="width:100%"
                            /></td>
                            <td style="width:6%;padding-left:10px;">专利申请人：</td>
                            <td style="width:13%;">
                                <input name="SQR" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;" title="归属客户/销售维护人/代理责任人/流程责任人">
                                内部编号
                            </td>
                            <td style="width:13%;">
                                <input name="NEIBUBH" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">是否已读：</td>
                            <td style="width:13%;">
                                <input class="mini-combobox" data="readTypes" name="ISREAD" data-oper="EQ"
                                       style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">撰写答复：</td>
                            <td style="width:13%;">
                                <input class="mini-combobox" data="writeReply" name="REPLY" data-oper="EQ"
                                       style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">提交答复：</td>
                            <td style="width:13%;">
                                <input class="mini-combobox" data="commitReply" name="ISCOMMIT" data-oper="EQ"
                                       style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">是否结案：</td>
                            <td style="width:13%;">
                                <input class="mini-combobox" data="hasEnd" name="JA" data-oper="EQ" style="width:100%"/>
                            </td>
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
            </div>
            <div class="mini-fit" id="fitt">
                <div class="mini-datagrid" id="grid1" style="width:100%;height:100%" frozenStartColumn="0" sizelist="[5,10,20,50,100,
                150,200,500,800,1000,1500]"
                     frozenEndColumn="5" url="/work/nicheNotice/getData" autoload="true"
                     ondrawcell="onDraw" pageSize="20" sortField="QX" sortOrder="desc" multiSelect="true"
                     oncellbeginedit="beforeEdit" allowCellSelect="true" allowCellEdit="true" oncellendedit="EndEdit"
                     onload="afterload" onrowclick="onRowClick">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div width="40" field="STATUS" align="center" allowSort="true"></div>
                            <div width="100" headeralign="center" field="QX" allowsort="true" dataType="date"
                                 dateFormat="yyyy-MM-dd">答复期限
                            </div>
                            <div width="100" headerAlign="center" field="QX" allowSort="true" dataType="date"
                                 dateFormat="yyyy-MM-dd">缴费期限
                            </div>
                        <div field="SENDMAIL" name="SENDMAIL" width="80" headerAlign="center" align="center" >邮件通知</div>
                        <div field="Action" width="80" headerAlign="center" align="center" >备注信息</div>
                        <div width="150" headerAlign="center" field="TZSMC" allowSort="true" align="center" >通知书名称</div>
                            <div width="120" headerAlign="center" field="JS" align="center" align="center"> 技术负责人</div>
                            <div width="200" headerAlign="center" field="KH" align="center" align="center" >归属客户</div>
                        <div width="160" headeralign="center" field="ZHUANLIMC" allowsort="true" align="center"
                             renderer="onZhanlihaoZhuangtai">专利名称
                        </div>
                        <div width="160" headerAlign="center" field="SQR" align="center" allowsort="true" align="center" >申请人</div>
                        <div width="100" headerAlign="center" field="SHENQINGH" align="center" allowsort="true" align="center" >专利号
                        </div>
                        <div width="100" headerAlign="center" field="SHENQINGLX" type="comboboxcolumn" align="center"
                             allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div width="100" headerAlign="center" field="ZSTATUS" allowSort="true" align="center"
                             allowsort="true">专利状态
                        </div>
                            <div width="80" headerAlign="center" field="JIAJI" allowSort="true" align="center">已加急</div>
                            <div width="80" headerAlign="center" field="ISREAD" align="center" allowSort="true">已读</div>
                                <div field="MIDDLEFILE" name="MIDDLEFILE" width="80" headerAlign="center"
                                     align="center">中间文件</div>
                            <div width="80" headerAlign="center" field="REPLY" align="center" allowsort="true">已撰写答复
                            </div>
                            <div width="100" headerAlign="center" field="REPLYDATE" align="center" dataType="date"
                                 dateFormat="yyyy-MM-dd HH:mm:ss" allowsort="true">
                                答复撰写日期
                                <input property="editor" class="mini-datepicker" showTime="true"/>
                            </div>
                            <div width="80" headerAlign="center" field="ISCOMMIT" align="center" allowsort="true">
                                已提交答复
                            </div>
                            <div width="100" headerAlign="center" field="ISCOMMITDATE" align="center" dataType="date"
                                 dateFormat="yyyy-MM-dd HH:mm:ss" allowsort="true">
                                答复提交日期
                                <input property="editor" class="mini-datepicker" showTime="true"/>
                            </div>
                            <div width="80" headerAlign="center" field="JA" align="center">已结案</div>
                        <div width="200" headerAlign="center" field="NEIBUBH" align="center" allowsort="true">案件内部号
                        </div>
                        <div width="150" headerAlign="center" field="FAWENXLH" align="center" allowsort="true">发文序列号
                        </div>
                        <div width="100" headerAlign="center" field="FAWENRQ" align="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">发文日期
                        </div>
                            <div width="200" headerAlign="center" field="KH" align="center">归属客户</div>
                        <div width="120" headerAlign="center" field="YW" align="center">销售维护人</div>
                            <div width="120" headerAlign="center" field="JS" align="center">技术负责人</div>
                        <div width="120" headerAlign="center" field="LC" align="center">流程责任人</div>
                        <div width="120" headerAlign="center" field="MEMO" visible="false">
                            备注
                            <input property="editor" class="mini-textbox"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
    mini.parse();
    var grid = mini.get('#grid1');
    var tip = new mini.ToolTip();
    var txtQuery = mini.get('#QueryText');
    var comField = mini.get('#comField');

    var cmdDownload=mini.get('NiCheNotice_Download');
    var cmdView=mini.get('NiCheNotice_Image');
    var cmdEmail=mini.get('NiCheNotice_Email');
    var cmdDownloadSource=mini.get('NiCheNotice_DownloadSource');

    var integer = {};
    integer.parse = function (o) {
        if (o == null || o == undefined) return 0;
        if (o == "true" || o == "True" || o == "TRUE") return 1;
        if (o == "false" || o == "False" || o == "FALSE") return 0;
        if (o == true) return 1;
        if (o == false) return 0;

        var dx = parseInt(o.toString());
        return dx > 0;
    }

    function jkConfig() {
        mini.open({
            url:'/work/nicheNoticeConfig/Config',
            width:1100,
            height:500,
            title:'监控设置',
            showModal:true,
            ondestroy:function(action){
                if (action=='ok') {
                    grid.reload();
                }
            }
        })
    }

    function onDraw(e) {
        var field = e.field;
        var record = e.record;
        if (field == "STATUS") {

            var days = record['DAYS'];
            if (days <= 0) {
                e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            else if (days > 60) {
                e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:blue">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            else if (days >= 0 && days <= 15) {
                e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            else if (days >= 16 && days <= 30) {
                e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:green">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            else if (days >= 31 && days <= 60) {
                e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            var read = integer.parse(record['ISCOMMIT'] || 0);
            if (read) {
                e.cellHtml = ' <span style="width:30px;height:15px;background-image :url(/appImages/ok.png) ">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
        }
        else if (field == "ISREAD") {
            var read = integer.parse(record['ISREAD'] || 0);
            if (!read) e.cellHtml = '<SPAN style="color:black">未读</span>'; else e.cellHtml = '<SPAN style="color:red">已读</span>';
        }
        else if (field == "REPLY") {
            var read = integer.parse(record['REPLY'] || 0);
            if (!read) e.cellHtml = '<SPAN style="color:black">未回复</span>'; else e.cellHtml = '<SPAN style="color:red">已回复</span>';
        }
        else if (field == "ISCOMMIT") {
            var read = integer.parse(record['ISCOMMIT'] || 0);
            if (!read) e.cellHtml = '<SPAN style="color:black">未提交</span>'; else e.cellHtml = '<SPAN style="color:red">已提交</span>';
        }
        else if (field == "END") {
            var read = integer.parse(record['END'] || 0);
            if (!read) e.cellHtml = '<SPAN style="color:black">未结案</span>'; else e.cellHtml = '<SPAN style="color:red">未结案</span>';
        }
        else if (field == "ISCOMMIT") {
            var read = integer.parse(record['ISCOMMIT'] || 0);
            if (!read) e.cellHtml = '<SPAN style="color:black">未提交'; else e.cellHtml = '<SPAN style="color:red">已提交</span>';
        }
        else if (field == "JIAJI") {
            var read = integer.parse(record['JIAJI'] || 0);
            if (!read) e.cellHtml = '<SPAN style="color:black">未加急'; else e.cellHtml = '<SPAN style="color:red">已加急</span>';
        }
        else if (field == "JA") {
            var read = integer.parse(record['JA'] || 0);
            if (!read) e.cellHtml = '<SPAN style="color:black">未结案'; else e.cellHtml = '<SPAN style="color:red">已结案</span>';
        }
        else if (field == "SENDMAIL") {
            var issend = record["SENDMAIL"];
            var dx = integer.parse(issend);
            var fid = record['TONGZHISBH'];
            if (dx == 1) e.cellHtml = '<a href="#" data-placement="bottomleft" class="showCellTooltip"    code=' + "'" + fid + "'" + '>已发送</a>'; else e.cellHtml = "未发送";
        }
        else if (field == "Action") {
            var record = e.record;
            var memo = record["MEMO"];
            var editMemo = parseInt(record["EDITMEMO"] || 0);
            var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
            if (editMemo == 0) {
                if (memo) text = "<span style='color:gay'>查看</span>";
            }
            e.cellHtml = '<a href="#"  data-placement="bottomleft"  hCode="' + record["SHENQINGH"] + '" class="showCellTooltip" onclick="ShowMemo(' + "'" + record["SHENQINGH"] + "'," + "'" + record["TZSMC"] + "'" + ')">' + text + '</a>';
        }
        else if (field == "SHENQINGLX") {
            var val = parseInt(e.value);
            var textVal = "";
            for (var i = 0; i < types.length; i++) {
                var tt = types[i];
                if (tt.id == val) {
                    textVal = tt.text;
                    break;
                }
            }
            switch (val) {
                case 0: {
                    e.cellHtml = "<span style='color:red'>" + textVal + "</style>";
                    break;
                }
                case 1: {
                    e.cellHtml = "<span style='color:black'>" + textVal + "</style>";
                    break;
                }
                case 2: {
                    e.cellHtml = "<span style='color:blue'>" + textVal + "</style>";
                    break;
                }
            }
        }
        else if (field == "MIDDLEFILE") {

                var files = parseInt(record[field] || 0);
                var shenqingh = record["TONGZHISBH"];
                if (files == 0) e.cellHtml = '<a href="#" id="browse" onclick="onFileupload(' + "'" + shenqingh + "'" + ')">上传</a>'; else
                    e.cellHtml = '<a href="#" onclick="onFileupload(' + "'" + shenqingh + "'" + ')">管理</a>';

        }
        else if (field == "KH") {
            var val = e.value;
            if (val) {
                var clientId = e.record["KHID"];
                if (clientId) {
                    e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            }
        }
    }

    function onRowClick(e) {
        var row = e.record;
        var original = row["Original"];
        var tzsPath = row["TZSPATH"];
        var sqr=row["SQR"]
       if(tzsPath){
           cmdDownloadSource.show();
           cmdDownload.show();
           cmdView.show();
           if(sqr)cmdEmail.show(); else cmdEmail.hide();
       } else {
            cmdEmail.hide();
           cmdDownloadSource.hide();
           cmdDownload.hide();
           cmdView.hide();
       }
    }

    function setRead(readValue) {
        var action = (readValue == true ? '设置为已读状态' : '取消已读状态');
        var rs = mini.clone(grid.getSelecteds());
        if (rs.length > 0) {
            mini.confirm('确认要将选择项目' + action + '？', '系统提示', function (yesorno) {
                if (yesorno == "ok") {
                    p();
                }
            });
        } else mini.alert('请选择要设置记录。');

        function p() {
            var gs = [];
            for (var i = 0; i < rs.length; i++) {
                var row = rs[i];
                var TZSBH = row["TONGZHISBH"];
                gs.push(TZSBH);
            }
            var url = '/work/nicheNotice/setRead';
            var arg={ID: gs.join(","), value:readValue};
            $.post(url,arg , function (r) {
                if (r.success) {
                    mini.alert(action + '成功。', '系统提示', function () {
                        doReload(grid);
                    });
                } else {
                    var msg = r.message || "设置通知书状态失败，请稍候重试。";
                    mini.alert(msg);
                }
            });
        }
    }

    function setJIAJI(jaValue) {
        var action = (jaValue == true ? '设置' : '取消');
        var rs = mini.clone(grid.getSelecteds());
        if (rs.length > 0) {
            mini.confirm('确认将选择的项目' + action + '加急状态吗？', '系统提示', function (yesorno) {
                if (yesorno == "ok") {
                    p();
                }
            });
        } else mini.alert('请选择要设置的记录。');

        function p() {
            var gs = [];
            var tzsbh=[];
            for (var i = 0; i < rs.length; i++) {
                var row = rs[i];
                var SHENQINGH = row["SHENQINGH"];
                var TONGZHISBH=row["TONGZHISBH"];
                gs.push(SHENQINGH);
                tzsbh.push(TONGZHISBH);
            }
            var url = '/work/nicheNotice/setJiaJi';
            var arg={ID: gs.join(','),TZSBH:tzsbh.join(','), value: jaValue};
            $.post(url,arg, function (r) {
                if (r.success) {
                    mini.alert(action + '单据的加急状态成功。', '系统提示', function () {
                        doReload(grid);
                    });
                } else {
                    var msg = r.message || "设置单据状态失败，请稍候重试。";
                    mini.alert(msg);
                }
            });
        }
    }

    function setJieAn(readValue) {
        var action = (readValue == true ? '设置' : '取消');
        var rs = mini.clone(grid.getSelecteds());
        if (rs.length > 0) {
            mini.confirm('确认将选择的项目' + action + '结案态吗？', '系统提示', function (yesorno) {
                if (yesorno == "ok") {
                    p();
                }
            });
        } else mini.alert('请选择要设置记录。');

        function p() {
            var gs = [];
            for (var i = 0; i < rs.length; i++) {
                var row = rs[i];
                var TZSBH = row["TONGZHISBH"];
                gs.push(TZSBH);
            }
            var url = '/work/nicheNotice/setJieAn';
            var arg={ID: gs.join(','), value: readValue};
            $.post(url,arg, function (r) {
                if (r.success) {
                    mini.alert(action + '单据是否结案状态成功。', '系统提示', function () {
                        doReload(grid);
                    });
                } else {
                    var msg = r.message || "设置单据状态失败，请稍候重试。";
                    mini.alert(msg);
                }
            });
        }
    }

    function setReply(replyValue) {
        var action = (replyValue == true ? '设置' : '取消');
        var rs = mini.clone(grid.getSelecteds());
        if (rs.length > 0) {
            mini.confirm('确认将选择的项目' + action + '已撰写交答复吗？', '系统提示', function (yesorno) {
                if (yesorno == "ok") {
                    p();
                }
            });
        } else mini.alert('请选择要设置记录。');

        function p() {
            var gs = [];
            for (var i = 0; i < rs.length; i++) {
                var row = rs[i];
                var TZSBH = row["TONGZHISBH"];
                gs.push(TZSBH);
            }
            var url = '/work/nicheNotice/setReply';
            var arg={ID: gs.join(','), value: replyValue};
            $.post(url,arg,function (r) {
                if (r.success) {
                    mini.alert(action + '单据是否撰写答复状态成功。', '系统提示', function () {
                        doReload(grid);
                    });
                } else {
                    var msg = r.message || "设置单据状态失败，请稍候重试。";
                    mini.alert(msg);
                }
            });
        }
    }

    function doReload(grid) {
        if (grid) {
            var pa = grid.getLoadParams();
            var pageIndex = grid.getPageIndex() || 0;
            var pageSize = grid.getPageSize() || 20;
            pa = pa || {pageIndex: pageIndex, pageSize: pageSize};
            grid.load(pa);
        }
    }

    function EndEdit(e) {
        var field = e.field;
        var val = e.value;
        ;
        var row = e.record;
        if (field == "ISCOMMITDATE" || field == "REPLYDATE") {
            val = mini.formatDate(e.value, 'yyyy-MM-dd HH:mm:ss');
        }
        var TZSBH = row["TONGZHISBH"];
        var url = '/work/nicheNotice/changeValue';
        $.post(url, {Field: field, ID: TZSBH, Value: val}, function (r) {
            if (r.success) {
                doReload(grid);
            } else {
                var msg = r.message || "更新属性值失败，请稍候重试。";
                mini.alert(msg);
            }
        });
    }

    function doQuery(status) {
        var grid = mini.get('grid1');
        var arg = {};
        var bs = [];
        if (status) {
            arg["ZHUANGTAI"] = encodeURI(status);
        }
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
                    if (f == "KH" || f == "LC" || f == "XS" || f == "DL") f = "NEIBUBH";
                    var kWork = f + '=' + word;
                    if (cs.indexOf(kWork) == -1) {
                        var op = {field: f, oper: 'LIKE', value: word};
                        cs.push(op);
                    }
                }
            } else {
                if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                //cs.push(field+"=" + word);
                var op = {field: field, oper: 'LIKE', value: word};
                cs.push(op);
            }
        }
        if (cs.length > 0) arg["Query"] = mini.encode(cs);
        grid.load(arg);
    }

    function beforeEdit(e) {
        var field = e.field;
        var record = e.record;
        if (field == "SHENQINGLX") {
            e.cancel = true;
        } else if (field == "REPLYDATE") {
            var reply =integer.parse(record["REPLY"] || 0);
            e.cancel = !reply;
        }
        else if (field == "ISCOMMITDATE") {
            var commit =integer.parse(record["ISCOMMIT"] || 0);
            e.cancel = !commit;
        }
    }

    function setCommit(commitValue) {
        var action = (commitValue == true ? '设置' : '取消');
        var rs = mini.clone(grid.getSelecteds());
        if (rs.length > 0) {
            mini.confirm('确认将选择的项目' + action + '已提交答复吗？', '系统提示', function (yesorno) {
                if (yesorno == "ok") {
                    p();
                }
            });
        } else mini.alert('请选择要设置记录。');

        function p() {
            var gs = [];
            for (var i = 0; i < rs.length; i++) {
                var row = rs[i];
                var TZSBH = row["TONGZHISBH"];
                gs.push(TZSBH);
            }
            var url = '/work/nicheNotice/setCommit';
            var arg={ID: gs.join(','), value: commitValue};
            $.post(url,arg, function (r) {
                if (r.success) {
                    mini.alert(action + '单据是否提交答复状态成功。', '系统提示', function () {
                        // grid.reload();
                        doReload(grid);
                    });
                } else {
                    var msg = r.message || "设置单据状态失败，请稍候重试。";
                    mini.alert(msg);
                }
            });
        }
    }

    function sendEmail() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            var vs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var r = {
                    Index: i + 1, SHENQINGRXM: row["SQR"], PID: row["SHENQINGH"], FAMINGMC: row["ZHUANLIMC"],
                    TONGZHISMC: row["TZSMC"]
                };
                vs.push(r);
            }
            var documentType=rows[0]["TZSMC"];
            mini.open({
                url: '/common/email/index?Code=TZS',
                width: 1000,
                height: 580,
                title: '发送邮件',
                showModal: true,
                allowResize: false,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.getContent(documentType,mini.encode(vs));
                    if (rows.length == 1) {
                        var row = rows[0];
                        var ppath = row["TZSPATH"];
                        var filename = row["TZSMC"];
                        iframe.contentWindow.addAttachment([{id: ppath, text: filename}]);
                        var title = row["ZHUANLIMC"] + "-" + filename;
                        iframe.contentWindow.setSubject(title);
                    }
                    else if (rows.length > 1) {
                        var codes = [];
                        for (var i = 0; i < rows.length; i++) {
                            var row = rows[i];
                            codes.push(row["TONGZHISBH"]);
                        }
                        var code = codes.join(",");
                        var url = '/common/email/getAllByCodes';
                        $.getJSON(url, {Code: code}, function (r) {
                            if (r.success) {
                                var ds = r.data || [];
                                iframe.contentWindow.addAttachment(ds);
                                iframe.contentWindow.setSubject(ds.length + "个通知书附件.zip");
                            }
                        })
                    }
                    iframe.contentWindow.addEvent('complete',function(obj){
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
                            var obj={TONGZHISBH:row["TONGZHISBH"],SHENQINGH:row["SHENQINGH"]};
                            obj.Client=clients.join(',');
                            obj.Email=receiver.join(',');
                            os.push(obj);
                        }
                        var url='/work/notice/addEmailRecord';
                        $.post(url,{Data:mini.encode(os)},function(result){
                            if(result.success==false){
                                mini.alert('邮件发送记录保存失败:'+result.message+',请联系系统管理员解决!否则会导致数据记录不完整','系统提赤');
                            } else {
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
                    var hCode = $(el).attr('hCode');
                    if (hCode) {
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["SHENQINGH"] == hCode) return true;
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

    function ShowMemo(id, title) {
        mini.open({
            url:'/work/addMemo/index?ID='+id,
            showModal:true,
            width:1000,
            height:500,
            title:"【"+title+"】的备注信息",
            onDestroy:function(){
                doReload(grid);
            }
        });
        window.parent.doResize();
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
    function onFileupload(tongzhsbh){
        var row=grid.getSelected();
        if(row){
            function p(ids){
                mini.open({
                    url:'/attachment/addFile?IDS='+ids,
                    width:800,
                    height:400,
                    title:'通知书中间文件',
                    onload:function(){
                        var iframe = this.getIFrameEl();
                        iframe.contentWindow.addEvent('eachFileUploaded',function(data){
                            var arg={Type:row["TZSMC"],ATTID:data.AttID,SHENQINGH:row["SHENQINGH"],
                                TZSBH:row["TONGZHISBH"]};
                            var url='/work/notice/saveMiddleFile';
                            $.post(url,{Data:mini.encode(arg)},function(result){
                                if(result.success==false){
                                    mini.alert('保存中间文件信息失败，请联系管理员解决问题。');
                                } else {
                                    doReload(grid);
                                }
                            })
                        });
                        iframe.contentWindow.addEvent('eachFileRemoved',function(data){
                            var url='/work/notice/removeMiddleFile';
                            $.post(url,{AttID:data.ATTID},function(result){
                                if(result.success==false){
                                    mini.alert('删除中间文件信息失败，请联系管理员解决问题。');
                                } else {
                                    doReload(grid);
                                }
                            })
                        });
                    }
                });
            }
            var url='/work/notice/getMiddleFile';
            $.getJSON(url,{TZSBH:tongzhsbh},function(result){
                var data=result.data ||[];
                p(data.join(','));
            });
        }
    }
    var fit = mini.get('fitt');
    $(function () {
        $('#p1').hide();
        fit.setHeight('100%');
        fit.doLayout();
    });

    function expand(e) {
        e = e || {};
        var btn = e.sender;
        if (!btn) {
            btn = mini.get('#NiCheNotice_HighQuery');
        }
        var display = $('#p1').css('display');
        if (display == "none") {
            btn.setIconCls("panel-collapse");
            btn.setText("折叠");
            $('#p1').css('display', "block");

        } else {
            btn.setIconCls("panel-expand");
            btn.setText("展开");
            $('#p1').css('display', "none");
        }
        fit.setHeight('100%');
        fit.doLayout();
    }

    function doHightSearch() {
        var grid = mini.get('grid1');
        var arg = {};
        var form = new mini.Form('#highQueryForm');
        var fields = form.getFields();
        var result = [];
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];
            var val = field.getValue();
            if (val != null && val != undefined) {
                if (val != '') {
                    var obj = {field: field.getName(), value: field.getValue(), oper: field.attributes["data-oper"]};
                    result.push(obj);
                }
            }
        }
        arg["High"] = mini.encode(result);
        grid.load(arg);
    }
    function download() {
        var rows = grid.getSelecteds();
        var code = null;
        var name = null;
        if (rows.length == 1) {
            var row = rows[0];
            if (row) {
                code = row["TONGZHISBH"];
                name = row["ZHUANLIMC"] + '-' + row["TZSMC"] + '.zip';
            }
        }
        else {
            var codes = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var code = row["TONGZHISBH"];
                codes.push(code);
            }
            code = codes.join(',');
            name = rows.length + '个通知书打包下载.zip';
        }
        var url = '/work/nicheNotice/download?Code=' + code + '&FileName=' + encodeURI(name);
        var boxId = mini.open({
            url: url
        });
        boxId.hide();
        event.preventDefault();
        event.stopPropagation();
        return false;
    }

    function doFixedQuery(field, oper, val) {
        var grid = mini.get('grid1');
        var arg = {};
        var cs = [{field: field, oper: oper, value: val}];
        arg["Query"] = mini.encode(cs);
        grid.load(arg);
    }



    function downloadSource() {
        var rows = grid.getSelecteds();
        var code = null;
        var name = null;
        if (rows.length == 1) {
            var row = rows[0];
            if (row) {
                code = row["TONGZHISBH"];
                name = row["ZHUANLIMC"] + '-' + row["TZSMC"] + '(原件).zip';
            }
        } else {
            var codes = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var code = row["TONGZHISBH"];
                codes.push(code);
            }
            code = codes.join(',');
            name = rows.length + '个通知书打包下载(原件).zip';
        }
        var boxId = mini.open({
            url: '/work/nicheNotice/downloadSource?Code=' + code + '&FileName=' + encodeURI(name)
        });
        boxId.hide();
        event.preventDefault();
        event.stopPropagation();
        return false;
    }

    function viewDocument() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            mini.mask('正在获取文件数据......');
            var codes = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var code = row["TONGZHISBH"];
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
    function doExport(){
        var excel=new excelData(grid);
        excel.addEvent('beforeGetData',function (grid,rows) {
            return grid.getSelecteds();
        })
        excel.export("小众通知书明细记录.xls");
    }
</script>
</@layout>