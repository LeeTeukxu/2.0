<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/layui/layui.js"></script>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>

    <script type="text/javascript">
        mini.parse();
        var users = ${Users};
        var users2 = {};
        for (i in users) {
            users2[users[i]] = i;
        }
        var zhuangtais = [
            {id: 0, text: '未查看'}, {id: 1, text: '已查看'}, {id: 2, text: '已处理'}, {id: 3, text: '已过期'}
        ];
        var types = [
            {id: 0, text: '发明专利'}, {id: 1, text: '新型专利'}, {id: 2, text: '外观专利'}
        ];
        var documentType =("${Type}");
        var encodeType = encodeURI("${Type}");
        var tzsStatus = ${TZSStatus};
        var readTypes = [{id: '', text: '----'}, {id: 0, text: '未读'}, {id: 1, text: '已读'}];
        var writeReply = [{id: '', text: '----'}, {id: 0, text: '未撰写'}, {id: 1, text: '已撰写'}];
        var commitReply = [{id: '', text: '----'}, {id: 0, text: '未提交'}, {id: 1, text: '已提交'}];
        var hasEnd = [{id: '', text: '----'}, {id: 0, text: '未结案'}, {id: 1, text: '已结案'}];
        var layer = null;
        layui.use(['layer'], function () {
            layer = layui.layer;
        });
        var qtTypes = [
            {id: '风险代理', text: '风险代理'},
            {id:'风险代理及预审', text: '风险代理及预审'},
            {id:'普通代理预审', text: '普通代理预审'},
            {id:'风险代理及优先审查', text: '风险代理及优先审查'},
            {id:'普通代理优先审查', text: '普通代理优先审查'},
            {id:'',text:'无'}
        ];
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
    <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
        <style type="text/css">
            .info1top ul {
                margin-top: -23px;
                list-style: none;
                margin-left: 20px;
            }

            .info1top ul li {
                float: left;
                margin-left: 8%;
                height: 41px;
                margin-top: -24px;
                padding-top: 9px;
                border-radius: 5px;
            }

            @media screen and (max-width: 1593px) {
                .info1top ul li {
                    margin-left: 8%;
                }
            }

            @media screen and (max-width: 1480px) {
                .info1top ul li {
                    margin-left: 8%;
                }
            }

            @media screen and (max-width: 1374px) {
                .info1top ul li {
                    margin-left: 8%;
                }
            }

            @media screen and (max-width: 1233px) {
                .info1top ul li {
                    margin-left: 8%;
                }
            }

            .info1top ul li:hover {
                background-color: rgb(203, 238, 242)
            }

            .clicked {
                background-color: rgba(241, 112, 46, 0.84);
            }

            .unclick {
                background-color: rgb(203, 238, 242)
            }

            .info1top ul li a {
                margin-top: 5px;
            }

            .info1top ul li a span {
                color: rgb(0, 159, 205);
                font-size: 15px
            }

            .info1top ul li a h4 {
                display: inline;
                color: rgb(1, 160, 202)
            }

            .info1bottom ul {
                margin-top: -23px;
                list-style: none;
                margin-left: 20px;
            }

            .info1bottom ul li {
                float: left;
                margin-left: 10%;
                height: 41px;
                margin-top: -9px;
                padding-top: 9px;
                border-radius: 5px;
            }

            @media screen and (max-width: 1593px) {
                .info1bottom ul li {
                    margin-left: 7%;
                }
            }

            @media screen and (max-width: 1480px) {
                .info1bottom ul li {
                    margin-left: 3.5%;
                }
            }

            @media screen and (max-width: 1374px) {
                .info1bottom ul li {
                    margin-left: 2%;
                }
            }

            @media screen and (max-width: 1233px) {
                .info1bottom ul li {
                    margin-left: 1.2%;
                }
            }

            .info1bottom ul li:hover {
                background-color: rgb(203, 238, 242)
            }

            .info1bottom ul li a {
                margin-top: 5px;
            }

            .info1bottom ul li a span {
                color: rgb(0, 159, 205);
                font-size: 15px
            }

            .info1bottom ul li a h4 {
                display: inline;
                color: rgb(1, 160, 202)
            }

            .info3top ul {
                margin-top: -23px;
                list-style: none;
                margin-left: 20px;
            }

            .info3top ul li {
                float: left;
                margin-left: 10%;
                height: 41px;
                margin-top: -9px;
                padding-top: 9px;
                border-radius: 5px;
            }

            @media screen and (max-width: 1593px) {
                .info3top ul li {
                    margin-left: 7%;
                }
            }

            @media screen and (max-width: 1480px) {
                .info3top ul li {
                    margin-left: 3.5%;
                }
            }

            @media screen and (max-width: 1374px) {
                .info3top ul li {
                    margin-left: 2%;
                }
            }

            @media screen and (max-width: 1233px) {
                .info3top ul li {
                    margin-left: 1.2%;
                }
            }

            .info3top ul li:hover {
                background-color: rgb(214, 212, 251)
            }

            .unclick1 {
                background-color: rgb(214, 212, 251)
            }

            .info3top ul li a span {
                color: rgb(53, 102, 231);
                font-size: 15px
            }

            .info3top ul li a h4 {
                display: inline;
                color: rgb(52, 101, 232)
            }

            .info3bottom ul {
                margin-top: -23px;
                list-style: none;
                margin-left: 20px;
            }

            .info3bottom ul li {
                float: left;
                margin-left: 10%;
                height: 41px;
                margin-top: -9px;
                padding-top: 9px;
                border-radius: 5px;
            }

            @media screen and (max-width: 1593px) {
                .info3bottom ul li {
                    margin-left: 7%;
                }
            }

            @media screen and (max-width: 1480px) {
                .info3bottom ul li {
                    margin-left: 3.5%;
                }
            }

            @media screen and (max-width: 1374px) {
                .info3bottom ul li {
                    margin-left: 2%;
                }
            }

            @media screen and (max-width: 1233px) {
                .info3bottom ul li {
                    margin-left: 1.2%;
                }
            }

            .info3bottom ul li:hover {
                background-color: rgb(214, 212, 251)
            }

            .info3bottom ul li a span {
                color: rgb(53, 102, 231);
                font-size: 15px
            }

            .info3bottom ul li a h4 {
                display: inline;
                color: rgb(52, 101, 232)
            }

            @media screen and (max-width: 1170px) {
                .info1bottom ul li {
                    margin-left: 2%;
                }

                .info2bottom ul li {
                    margin-left: 1%;
                }

                .info3bottom ul li {
                    margin-left: 0.5%;
                }
            }

            .sqf * {
                display: inline-block;
                vertical-align: middle;
            }
        </style>
    </#if>
    <div class="mini-layout" style="width:100%;height:100%">
        <#if Type=="受理通知书" || Type=="办理登记手续通知书">
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
        <#else>
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
                <button class="mini-button InVestigateOpinionNotice_Config" iconCls="icon-reload"
                        onclick="doFixedQuery('ISREAD','EQ','0')" plain="true">未读
                </button>
                <button class="mini-button InVestigateOpinionNotice_Config" iconCls="icon-reload"
                        onclick="doFixedQuery('ISREAD','EQ','1')" plain="true">已读
                </button>
                <button class="mini-button InVestigateOpinionNotice_Config" iconCls="icon-reload"
                        onclick="doFixedQuery('ISCOMMIT','EQ','1')" plain="true">已处理
                </button>
                <button class="mini-button InVestigateOpinionNotice_Config" iconCls="icon-reload"
                        onclick="doFixedQuery('ISCOMMIT','EQ','0')" plain="true">未处理
                </button>
                <button class="mini-button InVestigateOpinionNotice_Config" iconCls="icon-reload"
                        onclick="doFixedQuery('DAYS','LE','0')" plain="true">已过期
                </button>
                <button class="mini-button InVestigateOpinionNotice_Config" iconCls="icon-reload"
                        onclick="doFixedQuery('DAYS','GT','0')" plain="true">未过期
                </button>
            </div>
        </#if>
        <div region="center" bodyStyle="overflow:hidden">
            <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
                <div class="mini-toolbar">
                    <table style="width:100%">
                        <tr>
                            <div class="mini-clearfix ">
                                <div class="mini-col-12">
                                    <div id="info1"
                                         style="height:55px;background:rgb(226,250,252);border-radius: 5px;border:1px solid rgb(190,226,240);">
                                        <div class="info1top" style="padding-left: 100px;">
                                            <h3 class="sqf"
                                                style="color: rgb(3,154,209);margin-left:-90px;margin-top:17px;font-weight:bold;">
                                                <img style="vertical-align: middle;" src="/appImages/jk.png">&nbsp答复材料上传情况
                                            </h3>
                                            <ul>
                                                <li class="Jdlcli orig" id="J0">
                                                    <a style="text-decoration:none" target="_self" href="#"
                                                       onclick="changeQuery(0)">
                                                        <span id="J1span">全部</span>
                                                        <h4 class="x0">0</h4>
                                                    </a>
                                                </li>
                                                <li class="Jdlcli orig" id="J1">
                                                    <a style="text-decoration:none" target="_self" href="#"
                                                       onclick="changeQuery(1)">
                                                        <span id="J2span">未上传答复材料</span>
                                                        <h4 class="x1">0</h4>
                                                    </a>
                                                </li>
                                                <li class="Jdlcli orig" id="J2">
                                                    <a style="text-decoration:none" target="_self" href="#"
                                                       onclick="changeQuery(2)">
                                                        <span id="J3span">上传答复材料未提交国知局</span>
                                                        <h4 class="x2">0</h4>
                                                    </a>
                                                </li>
                                                <li class="Jdlcli orig" id="J3">
                                                    <a style="text-decoration:none" target="_self" href="#"
                                                       onclick="changeQuery(3)">
                                                        <span id="J4span">已上传国知局</span>
                                                        <h4 class="x3">0</h4>
                                                    </a>
                                                </li>
                                                <li class="Jdlcli orig" id="J4">
                                                    <a style="text-decoration:none" target="_self" href="#"
                                                       onclick="changeQuery(4)">
                                                        <span id="J5span">放弃答辩</span>
                                                        <h4 class="x4">0</h4>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </tr>
                    </table>
                </div>
            </#if>
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:100%">
                            <a class="mini-button" id="InVestigateOpinionNotice_setRead" iconCls="icon-ok" plain="true"
                               onclick="setRead(true)" visible="false">设置已读</a>
                            <a class="mini-button" id="InVestigateOpinionNotice_setNotRead" iconCls="icon-cancel"
                               plain="true" onclick="setRead(false)" visible="false">取消已读</a>
                            <a class="mini-button" iconCls="icon-user" onclick="sendEmail" plain="true"
                               id="InVestigateOpinionNotice_Email">发送客户</a>
                            <span class="separator"></span>

                            <a class="mini-button" iconCls="icon-zoomin" id="InVestigateOpinionNotice_Image"
                               plain="true"
                               onclick="viewDocument">在线查看</a>
                            <a class="mini-button" iconCls="icon-xls" plain="true" onclick="doExport"
                               id="InVestigateOpinionNotice_Export">导出Excel</a>
                            <#if Type=="补正通知书" || Type=="审查意见通知书">
                                <a class="mini-button" iconCls="icon-tip" onclick="showReport">答复统计</a>
                            </#if>
                            <span class="separator"></span>
                            <#if Type=="补正通知书" || Type=="审查意见通知书" || Type == "驳回决定">
                                <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_ChangeJS"
                                   onclick="changeXS">更换代理师</a>
                                <a class="mini-button mini-button-primary" id="Upload" onclick="doUpload">上传答辩文件</a>
                            </#if>
                            <a class="mini-menubutton"  menu="#popupMenu1" style="color:green;" plain="true">下载附件</a>
                            <ul class="mini-menu" id="popupMenu1" style="display:none">
                                <li class="mini-menu" iconCls="icon-download" plain="true"
                                    id="InVestigateOpinionNotice_Download" onclick="download">下载通知书 </li>
                                <li class="mini-menu" iconCls="icon-download" plain="true"
                                    id="InVestigateOpinionNotice_DownloadSource" onclick="downloadSource">下载源文件</li>
                                <#if Type=="补正通知书" || Type=="审查意见通知书"  || Type == "驳回决定">
                                    <a class="mini-menu InVestigateOpinionNotice_Download" iconCls="icon-download" onclick="downloadDB">下载答辩文件</a>
                                </#if>
                                <li class="mini-menu InVestigateOpinionNotice_Download" iconCls="icon-download" plain="true"
                                     onclick="downloadPdf">下载PDF</li>
                            </ul>
                        </td>
                        <td style="white-space:nowrap;">
                            <a class="mini-menubutton" id="InVestigateOpinionNotice_setpopupMenu" menu="#popupMenu"
                               style="color:green;" plain="true">设置通知书状态</a>
                            <ul class="mini-menu" id="popupMenu" style="display:none">
                                <li id="InVestigateOpinionNotice_doJiaJi"
                                    onclick="setJIAJI(true)">设置加急
                                </li>
                                <li id="InVestigateOpinionNotice_unJiaJi"
                                    onclick="setJIAJI(false)">取消加急
                                </li>
                                <li class="separator"></li>
                                <li id="InVestigateOpinionNotice_NoticeReply"
                                    onclick="setReply(true)">设置已撰写答复
                                </li>
                                <li
                                        id="InVestigateOpinionNotice_NoticeNotReply" onclick="setReply(false)">设置未撰写答复
                                </li>
                                <li class="separator"></li>
                                <li
                                        id="InVestigateOpinionNotice_NoticeCommit" onclick="setCommit(true)">设置已提交答复
                                </li>
                                <li
                                        id="InVestigateOpinionNotice_NoticeNotCommit" onclick="setCommit(false)">设置未提交答复
                                </li>
                                <li class="separator"></li>
                                <li style="display:block;" id="InVestigateOpinionNotice_JieAn"
                                    onclick="setJieAn(true)">设置已结案
                                </li>
                                <li style="display:block;" id="InVestigateOpinionNotice_NotJieAn"
                                    onclick="setJieAn(false)">设置未结案
                                </li>
                                <li class="separator"></li>
                                <li style="display:block;" id="InVestigateOpinionNotice_Abort"
                                    onclick="setAbort(true)">放弃答辩
                                </li>
                                <li style="display:block;" id="InVestigateOpinionNotice_UnAbort"
                                    onclick="setAbort(false)">恢复答辩
                                </li>
                            </ul>
                            <span class="separator  InVestigateOpinionNotice_Query"></span>
                            <div class="mini-combobox Query_Field InVestigateOpinionNotice_Query" id="comField"
                                 style="width:100px" popupWidth="200"
                                 data="[{id:'All',text:'全部属性'},{id:'SHENQINGH',text:'专利申请号'}, {id:'TZSMC',
                                 text:'通知书名称'}, {id:'ZHUANLIMC',text:'发明名称'},{id:'ZSTATUS',text:'专利状态'}, {id:'SQR',
                                 text:'申请人'},{id:'KH',text:'所属客户'},{id:'SQTYPE',text:'代理类型'},
                            {id:'XS',text:'销售人员'},{id:'DL',text:'代理责任人'},{id:'LC',text:'流程人员'},{id:'CustomMemo',
                            text:'手动标识'}]" value="All"></div>
                            <input type="text" class="mini-textbox Query_Field InVestigateOpinionNotice_Query"
                                   style="width:120px" id="QueryText"/>
                            <a class="mini-button" iconCls="icon-find" onclick="doQuery()"
                               id="InVestigateOpinionNotice_Query">模糊查询</a>
                            <a class="mini-button mini-button-danger CasesBrowse_Reset" id="a2" onclick="reset">重置</a>
                            <a class="mini-button" id="InVestigateOpinionNotice_HighQuery" onclick="expand"
                               iconCls="icon-expand">高级查询</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0; padding:5px;display:none;
                 <#if Type== "补正通知书" || Type== "审查意见通知书" || Type== "驳回决定">
                         height:160px;
                     <#else>
                         height:130px;
                 </#if> ">
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
                                                          data-oper="EQ" style="width:100%"/></td>
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
                        <#if Type== "补正通知书" || Type== "审查意见通知书" || Type== "驳回决定">
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
                                    <input class="mini-combobox" data="hasEnd" name="JA" data-oper="EQ"
                                           style="width:100%"/>
                                </td>
                            </tr>
                        </#if>
                        <tr>
                            <td style="width:6%;padding-left:10px;">邮件通知：</td>
                            <td style="width:13%;"><input class="mini-combobox" data="[{id:'',text:'全部'},{id:1,
                            text:'已发送'},{id:0, text:'未发送'}]" name="SENDMAIL" value="" data-oper="EQ"
                                                          style="width:100%"/></td>
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
                     frozenEndColumn="8" url="/work/notice/getData?Type=${CodeType}"
                        <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
                            autoload="false"
                        <#else>
                            autoload="true"
                        </#if>
                     ondrawcell="onDraw" pageSize="20" sortField="QX" sortOrder="desc" multiSelect="true"
                     oncellbeginedit="beforeEdit" allowCellSelect="true" allowCellEdit="true" oncellendedit="EndEdit"
                     onload="afterload" onrowclick="onRowClick" showLoading="true">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div width="60" field="STATUS" align="center"></div>
                        <div width="66" field="DE" align="center" headerAlign="center"></div>
                        <div field="SENDMAIL" name="SENDMAIL" width="80" headerAlign="center" align="center">邮件通知</div>
                        <div field="Action" width="60" headerAlign="center" align="center">备注</div>
                        <div width="100" headerAlign="center" field="FAWENRQ" align="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">发文日期
                        </div>
                        <#if Type!= "受理通知书" && Type!= "办理登记手续通知书">
                            <div width="90" headeralign="center" field="QX" allowsort="true" dataType="date"
                                 align="center" dateFormat="yyyy-MM-dd">答复期限
                            </div>
                        <#else>
                            <div width="100" headerAlign="center" field="QX" allowSort="true" dataType="date"
                                 align="center" dateFormat="yyyy-MM-dd">缴费期限
                            </div>
                        </#if>
                        <div field="SQTYPE" width="70" align="center" headerAlign="center" type="comboboxcolumn"
                             allowsort="true">代理类型
                            <input property="editor" class="mini-combobox" data="qtTypes"/>
                        </div>
                        <div width="160" headeralign="center" field="ZHUANLIMC" allowsort="true" align="center"
                             renderer="onZhanlihaoZhuangtai">专利名称
                        </div>
                        <div width="150" headerAlign="center" field="TZSMC" allowSort="true" align="center">通知书名称</div>
                        <#if (SpecType == true)>
                            <div width="100" headerAlign="center" field="JS" align="center" align="center">代理师</div>
                            <div width="200" headerAlign="center" field="KH" align="center" align="center">归属客户</div>
                        </#if>
                        <div width="160" headerAlign="center" field="SQR" align="center" allowsort="true"
                             align="center">申请人
                        </div>
                        <div width="140" headerAlign="center" field="SHENQINGH" align="center" allowsort="true"
                             align="center">专利号
                        </div>
                        <div width="100" headerAlign="center" field="SHENQINGLX" type="comboboxcolumn" align="center"
                             allowsort="true">专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div width="100" headerAlign="center" field="ZSTATUS" allowSort="true" align="center"
                             allowsort="true">专利状态
                        </div>
                        <div width="80" headerAlign="center" field="ISREAD" align="center" allowSort="true">已读</div>
                        <#if (Type!= "受理通知书" &&Type != "办理登记手续通知书" && Type!="缴费通知书"  && Type!= "专利权终止通知书")>
                            <div width="80" headerAlign="center" field="JIAJI" allowSort="true" align="center">已加急</div>
                            <#if (Type == "补正通知书" || Type== "审查意见通知书" || Type =="驳回决定")>
                                <div field="MIDDLEFILE" name="MIDDLEFILE" width="80" headerAlign="center" align="center">中间文件</div>
                            </#if>
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
                            <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
                                <div field="ISABORT" width="80" headerAlign="center" align="center" allowsort="true">放弃答辩</div>
                            </#if>
                            <div width="80" headerAlign="center" field="JA" align="center">已结案</div>
                        </#if>

                        <div width="150" headerAlign="center" field="FAWENXLH" align="center" allowsort="true">发文序列号
                        </div>
                        <#if RoleName!='客户'  && RoleName!="外协代理师">
                            <div width="200" headerAlign="center" field="NEIBUBH" align="center" allowsort="true">
                                案件内部号
                            </div>
                            <#if (SpecType == false)>
                                <#if RoleName?index_of("技术")==-1>
                                    <div width="200" headerAlign="center" field="KH" align="center">归属客户</div>
                                </#if>
                            </#if>
                            <div width="120" headerAlign="center" field="YW" align="center">销售维护人</div>
                            <#if (SpecType == false)>
                                <div width="120" headerAlign="center" field="JS" align="center">技术负责人</div>
                            </#if>
                            <div width="120" headerAlign="center" field="LC" align="center">流程责任人</div>
                            <div width="120" headerAlign="center" field="MEMO" visible="false">
                                备注
                                <input property="editor" class="mini-textbox"/>
                            </div>
                        </#if>
                        <div width="80" headeralign="center" field="CustomMemo">手动标识</div>
                        <#if Type=="补正通知书" || Type=="审查意见通知书">
                            <div field="DAILIJGMC" width="200" headerAlign="center" align="center"
                                 allowsort="true">代理机构名称</div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" style="display: none;width:1000px;height:500px" title="${Type}答复统计" id="ReportWindow">
        <div class="mini-toolbar">
            答复日期:<input class="mini-datepicker" name="begin" width="120" onvaluechanged="dateChanged"/>
            至<input class="mini-datepicker" name="end" width="120" onvaluechanged="dateChanged"/>
        </div>
        <div class="mini-fit">
            <div class="mini-datagrid" autoload="false" style="width:100%;height:100%" id="gridReport"
                 showSummaryRow="true"
                 url="/work/notice/getReportData?Type=${CodeType}" sortField="Reply" sortOrder="desc"
                 showPager="false">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="TechMan" width="150" align="center" headerAlign="center" allowSort="true">人员姓名</div>
                    <div field="NotReply" width="150" align="center" headerAlign="center" allowSort="true"
                         summaryType="sum">
                        未答复
                    </div>
                    <div field="Reply" width="150" align="center" headerAlign="center" allowSort="true" summaryType="sum">
                        已答复
                    </div>
                    <div field="NotCommit" width="150" align="center" headerAlign="center" allowSort="true" summaryType="sum">
                        已答复未提交
                    </div>
                    <div field="Total" width="150" align="center" headerAlign="center" allowSort="true" summaryType="sum">
                        合计
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

        var cmdDownload = mini.get('InVestigateOpinionNotice_Download');
        var cmdView = mini.get('InVestigateOpinionNotice_Image');
        var cmdEmail = mini.get('InVestigateOpinionNotice_Email');
        var cmdDownloadSource = mini.get('InVestigateOpinionNotice_DownloadSource');
        var cmdQuery = mini.get('#InVestigateOpinionNotice_Query');
        var cmdSetRead = mini.get('#InVestigateOpinionNotice_setRead');
        var cmdSetNotRead = mini.get('#InVestigateOpinionNotice_setNotRead');

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
        $(function () {

            cmdSetRead.hide();
            cmdSetNotRead.hide();
            <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
            changeQuery(0);
            </#if>
        })

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "STATUS") {
                var days = record['DAYS'];
                if (days <= 0) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                } else if (days > 60) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:blue">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                } else if (days >= 0 && days <= 15) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                } else if (days >= 16 && days <= 30) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:green">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                } else if (days >= 31 && days <= 60) {
                    e.cellHtml = ' <span style=" border-radius: 50%;height:10px;width:10px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                }
                var read = integer.parse(record['ISCOMMIT'] || 0);
                if (read) {
                    e.cellHtml = ' <span style="width:30px;height:15px;background-image :url(/appImages/ok.png) ">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
                }
                var tzsPath = record["TZSPATH"] || "";
                if (tzsPath.length > 3) {
                    e.cellHtml += "<span style='width:30px;height:15px;background-image :url(/appImages/download.gif)'>&nbsp;&nbsp;&nbsp;&nbsp;</span>";
                }
            } else if (field == "ISREAD") {
                var read = integer.parse(record['ISREAD'] || 0);
                if (!read) e.cellHtml = '<SPAN style="color:black">未读</span>'; else e.cellHtml = '<SPAN style="color:red">已读</span>';
            } else if (field == "REPLY") {
                var read = integer.parse(record['REPLY'] || 0);
                if (!read) e.cellHtml = '<SPAN style="color:black">未回复</span>'; else e.cellHtml = '<SPAN style="color:red">已回复</span>';
            } else if (field == "ISCOMMIT") {
                var read = integer.parse(record['ISCOMMIT'] || 0);
                if (!read) e.cellHtml = '<SPAN style="color:black">未提交</span>'; else e.cellHtml = '<SPAN style="color:red">已提交</span>';
            } else if (field == "END") {
                var read = integer.parse(record['END'] || 0);
                if (!read) e.cellHtml = '<SPAN style="color:black">未结案</span>'; else e.cellHtml = '<SPAN style="color:red">未结案</span>';
            } else if (field == "JIAJI") {
                var read = integer.parse(record['JIAJI'] || 0);
                if (!read) e.cellHtml = '<SPAN style="color:black">未加急'; else e.cellHtml = '<SPAN style="color:red">已加急</span>';
            } else if (field == "JA") {
                var read = integer.parse(record['JA'] || 0);
                if (!read) e.cellHtml = '<SPAN style="color:black">未结案'; else e.cellHtml = '<SPAN style="color:red">已结案</span>';
            }
                <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
            else if (field == "ISABORT") {
                var read = integer.parse(record['ISABORT'] || 0);
                if (!read) e.cellHtml = '<SPAN style="color:black">未放弃</span>'; else e.cellHtml = '<SPAN ' +
                    'style="color:red">已放弃</span>';
            }
                </#if>
            else if (field == "SENDMAIL") {
                var issend = record["SENDMAIL"];
                var dx = integer.parse(issend);
                var fid = record['TONGZHISBH'];
                if (dx == 1) e.cellHtml = '<a href="#" data-placement="bottomleft" class="showCellTooltip"    code=' + "'" + fid + "'" + '>已发送</a>'; else e.cellHtml = "未发送";
            } else if (field == "Action") {
                var record = e.record;
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMEMO"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay'>查看</span>";
                }
                e.cellHtml = '<a href="#"  data-placement="bottomleft"  hCode="' + record["SHENQINGH"] + '" class="showCellTooltip" onclick="ShowMemo(' + "'" + record["SHENQINGH"] + "'," + "'" + record["TZSMC"] + "'" + ')">' + text + '</a>';
            } else if (field == "SHENQINGLX") {
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
            } else if (field == "MIDDLEFILE") {
                if (documentType == "补正通知书" || documentType == "审查意见通知书" || documentType == "驳回决定") {
                    var files = parseInt(record[field] || 0);
                    var shenqingh = record["TONGZHISBH"];
                    var isCommit=(record["ISCOMMIT"] || "false").toString().toLowerCase();
                    if (files == 0) e.cellHtml = '<a href="#" id="browse" onclick="onFileupload(' + "'" + shenqingh + "'" + ')">上传</a>';
                    else {
                        if(isCommit=="true"){
                            e.cellHtml = '<a href="#" onclick="onFileupload(' + "'" + shenqingh + "'" + ')">查看</a>';
                        } else {
                            e.cellHtml = '<a href="#" onclick="onFileupload(' + "'" + shenqingh + "'" + ')">管理</a>';
                        }
                    }
                }
            } else if (field == "KH") {
                var val = e.value;
                if (val) {
                    var clientId = e.record["KHID"];
                    if (clientId) {
                        e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                    } else e.cellHtml = val;
                }
            } else if (field == "DE") {
                var iid = e.record._id;
                e.cellHtml = '<a href="#" onclick="detail(' + "'" + iid + "'" + ')">详情..</a>';
            }
        }

        function onRowClick(e) {
            var row = e.record;
            var original = row["Original"];
            var tzsPath = row["TZSPATH"];
            var sqr = row["SQR"]
            if (tzsPath) {
                cmdDownloadSource.show();
                cmdDownload.show();
                cmdView.show();
                cmdEmail.show();
            } else {
                cmdEmail.hide();
                cmdDownloadSource.hide();
                cmdDownload.hide();
                cmdView.hide();
            }
            var isRead = (row["ISREAD"] || false);
            if (isRead) {
                cmdSetNotRead.show();
                cmdSetRead.hide();
            } else {
                cmdSetRead.show();
                cmdSetNotRead.hide();
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
                var url = '/work/notice/setRead';
                var arg = {ID: gs.join(","), value: readValue, Type: encodeURIComponent('${Type}')};
                $.post(url, arg, function (r) {
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
                for (var i = 0; i < rs.length; i++) {
                    var row = rs[i];
                    var TZSBH = row["SHENQINGH"];
                    gs.push(TZSBH);
                }
                var url = '/work/notice/setJiaJi';
                var arg = {ID: gs.join(','), value: jaValue, Type: encodeURIComponent('${Type}')};
                $.post(url, arg, function (r) {
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
                var url = '/work/notice/setJieAn';
                var arg = {ID: gs.join(','), value: readValue, Type: encodeURIComponent('${Type}')};
                $.post(url, arg, function (r) {
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
                var url = '/work/notice/setReply';
                var arg = {ID: gs.join(','), value: replyValue, Type: encodeURIComponent('${Type}')};
                $.post(url, arg, function (r) {
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

        function setAbort(value) {
            var action = (value == true ? '设置放弃答辩' : '设置恢复答辩');
            var rs = mini.clone(grid.getSelecteds());
            if (rs.length > 0) {
                mini.confirm('确认将选择的项目' + action + '吗？', '系统提示', function (yesorno) {
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
                var url = '/work/notice/setAbort';
                var arg = {ID: gs.join(','), value: value, Type: encodeURIComponent('${Type}')};
                $.post(url, arg, function (r) {
                    if (r.success) {
                        mini.alert(action + '状态成功。', '系统提示', function () {
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
            var p = encodeURIComponent('${Type}');
            var url = '/work/notice/changeValue';
            $.post(url, {Field: field, ID: TZSBH, Value: val, Type: p}, function (r) {
                if (r.success) {
                    doReload(grid);
                } else {
                    var msg = r.message || "更新属性值失败，请稍候重试。";
                    mini.alert(msg);
                }
            });
        }

        function doQuery(state) {
            var p = encodeURI('${Type}');
            var grid = mini.get('grid1');
            var arg = {Type: p};
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
            if (state) {
                var sstt = parseInt(state || 0);
                if (sstt > 0) {
                    var p = {
                        field: 'State',
                        value: state,
                        oper: 'EQ'
                    };
                    var gg = [p];
                    arg["High"] = mini.encode(gg);
                }
            }
            grid.load(arg);
        }

        function beforeEdit(e) {
            var field = e.field;
            var record = e.record;
            var isCommit=new Boolean(record["ISCOMMIT"] || "false");
            if (field == "SHENQINGLX") {
                e.cancel = true;
            } else if (field == "REPLYDATE") {
                var reply = integer.parse(record["REPLY"] || 0);
                e.cancel = !reply;
            } else if (field == "ISCOMMITDATE") {
                var commit = integer.parse(record["ISCOMMIT"] || 0);
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
                var url = '/work/notice/setCommit';
                var arg = {ID: gs.join(','), value: commitValue, Type: encodeURIComponent('${Type}')};
                $.post(url, arg, function (r) {
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
                var pps = [];
                var css = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var r = {
                        Index: i + 1, SHENQINGRXM: row["SQR"] || "", PID: row["SHENQINGH"], FAMINGMC: row["ZHUANLIMC"],
                        TONGZHISMC: row["TZSMC"]
                    };
                    var p = {SHENQINGH: row["SHENQINGH"], TONGZHISBH: row["TONGZHISBH"]};

                    vs.push(r);
                    pps.push(p);
                    css.push(row["KHID"]);
                }
                mini.open({
                    url: '/common/email/index?Code=TZS&KH=' + css.join(','),
                    width: 1000,
                    height: 580,
                    title: '发送邮件',
                    showModal: true,
                    allowResize: false,
                    onload: function () {
                        var iframe = this.getIFrameEl();
                        iframe.contentWindow.getContent(documentType, mini.encode(vs));
                        if (rows.length == 1) {
                            var row = rows[0];
                            var ppath = row["TZSPATH"];
                            var filename = row["TZSMC"];
                            var title = row["ZHUANLIMC"] + "(" + filename + ")";
                            iframe.contentWindow.addAttachment([{id: ppath, text: title}]);
                            iframe.contentWindow.setSubject(title);
                            iframe.contentWindow.setRecords(pps);
                        } else if (rows.length > 1) {
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
                                var obj = {TONGZHISBH: row["TONGZHISBH"], SHENQINGH: row["SHENQINGH"]};
                                obj.Client = clients.join(',');
                                obj.Email = receiver.join(',');
                                os.push(obj);
                            }
                            var url = '/work/notice/addEmailRecord';
                            $.post(url, {Data: mini.encode(os)}, function (result) {
                                if (result.success == false) {
                                    mini.alert('邮件发送记录保存失败:' + result.message + ',请联系系统管理员解决!否则会导致数据记录不完整', '系统提赤');
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
            <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
            updateStateNumbers();
            </#if>
        }
        function doUpload(){
            var rows=grid.getSelecteds();
            if(rows.length>1){
                mini.alert('一个答复文件只能上一个通知书记录!');
                return ;
            }
            var record=rows[0];
            var shenqingh = record["TONGZHISBH"];
            onFileupload(shenqingh);
        }
        function ShowMemo(id, title) {
            var rows = grid.getSelecteds();
            var cs = [];
            if (rows.length > 1) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    cs.push(row["SHENQINGH"]);
                }
                id = cs.join(',');
                title = "多条专利批量添加";
            }
            mini.open({
                url: '/work/addMemo/index?ID=' + id + '&MenuName=' + currentMenuName,
                showModal: true,
                width: 1000,
                height: 600,
                title: "【" + title + "】的备注信息",
                onDestroy: function () {
                    doReload(grid);
                }
            });
            window.parent.doResize();
        }

        function showClient(clientId) {
            mini.open({
                url: '/work/clientInfo/browse?Type=1&ClientID=' + clientId,
                width: '100%',
                height: '100%',
                title: '浏览客户资料',
                showModal: true,
                ondestroy: function () {

                }
            });
            window.parent.doResize();
        }

        function onFileupload(tongzhsbh) {
            var row = grid.getSelected();
            if (row) {
                function p(ids, mode) {
                    mini.open({
                        url: '/attachment/addFile?IDS=' + ids + '&Mode=' + mode,
                        width: 800,
                        height: 400,
                        title: '通知书中间文件',
                        onload: function () {
                            var iframe = this.getIFrameEl();
                            iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                                var arg = {
                                    Type: '${Type}', ATTID: data.AttID, SHENQINGH: row["SHENQINGH"],
                                    TZSBH: row["TONGZHISBH"]
                                };
                                var url = '/work/notice/saveMiddleFile';
                                $.post(url, {Data: mini.encode(arg)}, function (result) {
                                    if (result.success == false) {
                                        mini.alert('保存中间文件信息失败，请联系管理员解决问题。');
                                    } else {
                                        doReload(grid);
                                    }
                                })
                            });
                            iframe.contentWindow.addEvent('eachFileRemoved', function (data) {
                                var url = '/work/notice/removeMiddleFile';
                                $.post(url, {AttID: data.ATTID}, function (result) {
                                    if (result.success == false) {
                                        mini.alert('删除中间文件信息失败，请联系管理员解决问题。');
                                    } else {
                                        doReload(grid);
                                    }
                                })
                            });
                        },
                        ondestroy: function () {
                            doReload(grid);
                        }
                    });
                }
                var url = '/work/notice/getMiddleFile';
                $.getJSON(url, {TZSBH: tongzhsbh}, function (result) {
                    var data = result.data || [];
                    var isCommit =(row["ISCOMMIT"] || "false").toString().toLowerCase();
                    if (isCommit=="true") {
                        p(data.join(','), 'Browse');
                    } else {
                        if (data.length > 0) p(data.join(','), 'Edit'); else p(data.join(','), 'Add');
                    }
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
                btn = mini.get('#InVestigateOpinionNotice_HighQuery');
            }
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("icon-collapse");
                btn.setText("隐藏");
                $('#p1').css('display', "block");
                txtQuery.hide();
                comField.hide();
                cmdQuery.hide();
            } else {
                btn.setIconCls("icon-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
                txtQuery.show();
                comField.show();
                cmdQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
            grid.setHeight('100%');
            grid.doLayout();
        }

        function doHightSearch() {
            var p = encodeURI('${Type}');
            var grid = mini.get('grid1');
            var arg = {Type: p};
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

        function download() {
            var rows = grid.getSelecteds();
            if(rows.length==0){
                mini.alert('请选择要下载的记录!');
                return ;
            }
            var code = null;
            var name = null;
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["TONGZHISBH"];
                    name = row["ZHUANLIMC"] + '-' + row["TZSMC"] + '.zip';
                }
            } else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["TONGZHISBH"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个通知书打包下载.zip';
            }
            var iid=mini.loading('正在生成文件数据,请稍候.........','系统提示');
            var url = '/work/notice/download?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {
                    mini.hideMessageBox(iid);
                },
                failCallback: function (html, xurl) {
                    mini.hideMessageBox(iid);
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }

        function doFixedQuery(field, oper, val) {
            var p = encodeURI('${Type}');
            var grid = mini.get('grid1');
            var arg = {Type: p};
            var cs = [{field: field, oper: oper, value: val}];
            arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function downloadPdf(){
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if(rows.length==0){
                mini.alert('请选择要下载的记录!');
                return ;
            }
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["TONGZHISBH"];
                    name = row["ZHUANLIMC"] + '-' + row["TZSMC"] + '.pdf';
                }
            } else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["TONGZHISBH"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个通知书打包下载(Pdf).zip';
            }
            var iid=mini.loading('正在生成文件数据,请稍候.........','系统提示');
            var url = '/work/notice/downloadPdf?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {
                    mini.hideMessageBox(iid);
                },
                failCallback: function (html, xurl) {
                    mini.hideMessageBox(iid);
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }

        function downloadSource() {
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if(rows.length==0){
                mini.alert('请选择要下载的记录!');
                return ;
            }
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
            var iid=mini.loading('正在生成文件数据,请稍候.........','系统提示');
            var url = '/work/notice/downloadSource?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {
                    mini.hideMessageBox(iid);
                },
                failCallback: function (html, xurl) {
                    mini.hideMessageBox(iid);
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }
        function  downloadDB(){
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if(rows.length==0){
                mini.alert('请选择要下载的记录!');
                return ;
            }
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["TONGZHISBH"];
                    name = row["ZHUANLIMC"] + '-' + row["TZSMC"] + '(答复文件).zip';
                }
            } else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["TONGZHISBH"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个通知书(答复文件)打包下载.zip';
            }
            var iid=mini.loading('正在生成文件数据,请稍候.........','系统提示');
            var url = '/work/notice/downloadDB?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {
                    mini.hideMessageBox(iid);
                },
                failCallback: function (html, xurl) {
                    mini.hideMessageBox(iid);
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
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
                var arg = {'tongzhisbh': codes.join(",")};
                var url = '/watch/addYearWatchItem/getAllImages';
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

        function doExport() {
            var excel = new excelData(grid);
            excel.export("${Type}明细记录.xls");
        }

        function changeXS() {
            var jklx = "otherTZS";
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
                    iframe.contentWindow.setData(rows, users, users2, jklx);
                },
                onDestroy: function () {
                    if (grid) grid.reload();
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

        function detail(id) {
            var row = grid.getRowByUID(id);
            var shenqingh = row["SHENQINGH"];
            var bh = row["BH"] || "";
            var yw = row["YW"] || "";
            var js = row["JS"] || "";
            var lc = row["LC"] || "";
            var kh = row["KH"] || "";
            mini.open({
                url: '/work/patentInfo/detail?shenqingh=' + shenqingh + '&BH=' + bh + '&YW=' + yw + '&JS=' + js + '&LC=' + lc + '&KH=' + kh,
                width: '100%',
                height: '100%',
                title: '专利详细信息'
            })
        }
    </script>
    <#if Type=="驳回决定" || Type=="补正通知书" || Type=="审查意见通知书">
        <script type="text/javascript">
            function updateStateNumbers() {
                var key = (new Date()).getTime();
                var url = '/work/notice/getTZSTotal?Type=${Type}&Key=' + key;
                $.getJSON(url, {}, function (result) {
                    if (result.success) {
                        var states = result.data || {};
                        for (var i = 0; i < states.length; i++) {
                            var state = states[i];
                            var con = $('.' + state.name);
                            if (con.length > 0) {
                                con.text(state.num);
                            }
                        }
                    }
                });
            }

            function changeQuery(state) {
                var con = $('.x' + state);
                var cons = $('.Jdlcli');
                for (var i = 0; i < cons.length; i++) {
                    var cx = $(cons[i]);
                    cx.removeClass('clicked');
                    $('#J' + i).css({"background-color": "rgb(226,250,252)"});
                }
                $(con).parents('.Jdlcli').addClass('clicked');
                $('#J' + state).css({"background-color": "rgba(247, 140, 24, 0.85)"});
                doQuery(state);
            }

            var conBegin = mini.getbyName('begin');
            var conEnd = mini.getbyName('end');
            var gridReport = mini.get('gridReport');

            function showReport() {
                var win = mini.get('ReportWindow');
                if (win) win.show();
                conBegin.setValue(null);
                conEnd.setValue(null);
                gridReport.load({});
            }

            function dateChanged(e) {
                var arg = {};
                if (conBegin.getValue()) {
                    arg.Begin = mini.formatDate(conBegin.getValue(), 'yyyy-MM-dd');
                }
                if (conEnd.getValue()) {
                    arg.End = mini.formatDate(conEnd.getValue(), 'yyyy-MM-dd');
                }
                gridReport.load(arg);
            }
        </script>
    </#if>
</@layout>