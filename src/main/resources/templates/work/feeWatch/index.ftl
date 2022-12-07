<#include "/shared/layout.ftl">
<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript">
        var years = [{id: 1, text: 1}, {id: 2, text: 2}, {id: 3, text: 3}, {id: 4, text: 4}, {id: 5, text: 5}, {
            id: 6,
            text: 6
        },
            {id: 7, text: 7}, {id: 8, text: 8}, {id: 9, text: 9}, {id: 10, text: 10}, {id: 11, text: 11}, {
                id: 12,
                text: 12
            },
            {id: 13, text: 13}, {id: 14, text: 14}, {id: 15, text: 15}, {id: 16, text: 16}, {id: 17, text: 17}, {
                id: 18,
                text: 18
            },
            {id: 19, text: 19}, {id: 20, text: 20}];
        var tinyYear = [{id: 1, text: 1}, {id: 2, text: 2}, {id: 3, text: 3}, {id: 4, text: 4}, {
            id: 5,
            text: 5
        }, {id: 6, text: 6},
            {id: 7, text: 7}, {id: 8, text: 8}, {id: 9, text: 9}, {id: 10, text: 10}];
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
        var watchTypes = [{id: 1, text: '未监控'}, {id: 2, text: '已监控'}, {id: 3, text: '放弃监控'}];
        var watchModes = [{id: 1, text: '未监控'}, {id: 2, text: 'CPC自动'}, {id: 3, text: '手动设置'}, {id: 4, text: '数据导入'}];
    </script>
    <style type="text/css">
        .info1top ul {
            margin-top: -23px;
            list-style: none;
            margin-left: 20px;
        }

        .info1top ul li {
            float: left;
            margin-left: 10%;
            height: 41px;
            margin-top: -9px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info1top ul li {
                margin-left: 7%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info1top ul li {
                margin-left: 3.5%;
            }
        }

        @media screen and (max-width: 1374px) {
            .info1top ul li {
                margin-left: 2%;
            }
        }

        @media screen and (max-width: 1233px) {
            .info1top ul li {
                margin-left: 1.2%;
            }
        }

        .info1top ul li:hover {
            background-color: rgb(203, 238, 242)
        }
        .clicked{
           background-color:  rgba(241, 112, 46, 0.84)
        }

        .unclick{
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
        .unclick1{
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
    <div class="mini-layout" bodyStyle="overflow: hidden" style="width:100%;height:99%">
        <div region="center">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-6">
                                <div id="info1"
                                     style="height:85px;background:rgb(226,250,252);border-radius: 5px;border:1px solid rgb(190,226,240);">
                                    <div class="info1top"
                                         style="height: 33px;border-bottom: 1px solid rgb(214,239,243);padding-left: 120px;">
                                        <h3 class="sqf"
                                            style="color: rgb(3,154,209);margin-left:-90px;margin-top: 9px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jk.png">&nbsp;申请费监控
                                        </h3>

                                        <ul>
                                            <li class="Jdlcli AppWatchlySource" id="J1" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApplyWatch',1,this)">
                                                    <span id="J1span">未监控</span>
                                                    <h4 class="x1">1</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli AppWatchlySource" id="J2" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApplyWatch',2,this)">
                                                    <span id="J2span">已监控</span>
                                                    <h4 class="x2">2</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli AppWatchlySource" id="J3" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApplyWatch',3,this)">
                                                    <span id="J2span">放弃监控</span>
                                                    <h4 class="x3">3</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="info1bottom" style="width: 100%;height: 27px;padding-left: 120px;">
                                        <h3 style="color: rgb(3,154,209);margin-left:-90px;margin-top: 9px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jkfs.png">&nbsp;监控方式
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli AppWatchlySource" id="J1" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApplySource',1,this)">
                                                    <span id="J1span">未监控</span>
                                                    <h4 class="x4">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli AppWatchlySource" id="J1" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApplySource',2,this)">
                                                    <span id="J1span">cpc自动</span>
                                                    <h4 class="x5">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli AppWatchlySource" id="J2" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApplySource',3,this)">
                                                    <span id="J2span">手动设置</span>
                                                    <h4 class="x6">0</h4>
                                                </a>
                                            </li>

                                            <li class="Jdlcli AppWatchlySource" id="J4" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ApplySource',4,this)">
                                                    <span id="J2span">数据导入</span>
                                                    <h4 class="x7">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="mini-col-6">
                                <div id="info3"
                                     style=" height:85px;background:rgb(232,230,252);border-radius: 5px;border:1px solid rgb(213,210,239);">
                                    <div class="info3top"
                                         style="height: 33px;border-bottom: 1px solid rgb(219,217,239);padding-left: 120px;">
                                        <h3 style="color: rgb(91,35,220);margin-left:-90px;margin-top: 9px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jk1.png">&nbsp;年费监控
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli YearWatchlySource" id="J4" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('YearWatch',1,this)">
                                                    <span id="J4span">未监控</span>
                                                    <h4 class="x8">1</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli YearWatchlySource" id="J5" onclick="">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('YearWatch',2,this)">
                                                    <span id="J5span">已监控</span>
                                                    <h4 class="x9">2</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli YearWatchlySource" id="J6" onclick="">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('YearWatch',3,this)">
                                                    <span id="J6span">放弃监控</span>
                                                    <h4 class="x10">3</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="info3bottom" style="width: 100%;height: 27px;padding-left: 120px;">
                                        <h3 style="color:  rgb(91,35,220);margin-left:-90px;margin-top: 9px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jkfs1.png">&nbsp;监控方式
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli YearWatchlySource" id="J1" onclick=";">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('YearSource',1,this)">
                                                    <span id="J1span">未监控</span>
                                                    <h4 class="x11">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli YearWatchlySource" id="J7" onclick=";">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('YearSource',2,this)">
                                                    <span id="J7span">cpc自动</span>
                                                    <h4 class="x12">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli YearWatchlySource" id="J8" onclick="">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('YearSource',3,this)">
                                                    <span id="J8span">手动设置</span>
                                                    <h4 class="x13">0</h4>
                                                </a>
                                            </li>

                                            <li class="Jdlcli YearWatchlySource" id="J9" onclick="">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('YearSource',4,this)">
                                                    <span id="J9span">数据导入</span>
                                                    <h4 class="x14">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                    <tr>
                        <td style="width:95%;padding-top:5px">
                            <a class="mini-button mini-button-success" id="FeeWatch_AddApply"
                               onclick="addApplyItem">添加申请费监控</a>
                            <a class="mini-button mini-button-success" id="FeeWatch_AddYear"
                               onclick="addYearItem">添加年费监控</a>
                            <span class="separator"></span>
                            <a class="mini-button mini-button-info" id="FeeWatch_ImportApply"
                               onclick="doImportApply">导入申请费监控</a>
                            <a class="mini-button mini-button-info" id="FeeWatch_ImportYear"
                               onclick="doImportYear">导入年费监控</a>
                            <span class="separator"></span>
                            <a class="mini-button mini-button-warning" id="FeeWatch_AbortApply" onclick="changeValue
                            ('放弃申请费监控','ApplyWatch',3)" id="FeeWatch_Import">申请费放弃监控</a>
                            <a class="mini-button mini-button-warning" id="FeeWatch_AbortYear" onclick="changeValue('放弃年费监控',
                            'YearWatch',3)" id="FeeWatch_Export">年费放弃监控</a>
                            <span class="separator"></span>
                            <a class="mini-button mini-button-info" onclick="doExport" id="FeeWatch_Export">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox FeeWatch_Query" id="comField"
                                 style="width:100px"
                                 data="[{id:'All', text:'全部属性'},{id:'SHENQINGRXM',text:'专利申请人'},{id:'SHENQINGH',text:'专利申请号'},{id:'FAMINGMC',text:'发明名称'},{id:'ANJIANYWZT',text:'专利状态'},{id:'FAMINGRXM',text:'发明人'},{id:'DAILIJGMC',text:'代理机构'},{id:'KH',text:'所属客户'},{id:'XS',text:'销售人员'},{id:'DL',text:'代理责任人'},{id:'LC',text:'流程人员'}]"
                                 value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field FeeWatch_Query" style="width:150px"
                                   id="QueryText"/>
                            <a class="mini-button mini-button-success" onclick="doQuery();" id="FeeWatch_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger Query_Field" id="FeeWatch_Reset"
                               onclick="reset()">重置</a>
                            <a class="mini-button" id="FeeWatch_HighQuery" iconCls="panel-expand"
                               onclick="expand">展开</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:120px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">申请日期：</td>
                            <td style="width:15%;">
                                <input name="SHENQINGR" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="SHENQINGR" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">专利名称：</td>
                            <td style="width:15%;">
                                <input name="FAMINGMC" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">专利号：</td>
                            <td style="width:15%;">
                                <input name="SHENQINGH" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">专利类型：</td>
                            <td style="width:15%;"><input name="SHENQINGLX" class="mini-combobox" data-oper="EQ"
                                                          style="width:100%" data="types"/></td>
                            <td style="width:6%;padding-left:10px;">专利状态：</td>
                            <td style="width:15%;"><input name="ANJIANYWZT" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">专利申请人：</td>
                            <td style="width:15%;"><input name="SHENQINGRXM" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                            /></td>
                            <td style="width:6%;" title="归属客户/销售维护人/代理责任人/流程责任人">
                                内部编号：
                            </td>
                            <td style="width:15%;"><input name="NEIBUBH" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                            /></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" onclick="doHightSearch" style="width:120px">搜索</a>
                                <a class="mini-button mini-button-danger" onclick="expand"
                                   style="margin-left:30px;width:120px">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div class="mini-datagrid" id="datagrid1" style="width:100%;height:100%"
                     url="/watch/feeWatch/getData?LongTime=1"
                     sizelist="[5,10,20,50]" onrowclick="onRowClick"
                     autoload="true" frozenStartColumn="0" frozenEndColumn="9" multiSelect="true" pageSize="20"
                     sortField="SHENQINGR" sortOrder="desc" ondrawcell="onRenderLX" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn" style="width:80px"></div>
                        <div type="checkcolumn"></div>
                        <div field="ApplyWatch" width="90" headerAlign="center" align="center"
                             type="comboboxcolumn">申请费监控
                            <input property="editor" class="mini-combobox" data="watchTypes"/>
                        </div>
                        <div field="ApplySource" width="120" headerAlign="center" align="center" type="comboboxcolumn">
                            申请费监控方式
                            <input property="editor" class="mini-combobox" data="watchModes"/>
                        </div>
                        <div field="YearWatch" width="90" headerAlign="center" align="center" type="comboboxcolumn">年费监控
                            <input property="editor" class="mini-combobox" data="watchTypes"/>
                        </div>
                        <div field="YearSource" width="90" headerAlign="center" align="center" type="comboboxcolumn">
                            年费监控方式
                            <input property="editor" class="mini-combobox" data="watchModes"/>
                        </div>
                        <div field="SHENQINGRXM" width="200" headerAlign="center" allowsort="true">专利申请人</div>
                        <div field="SHENQINGH" width="120" headerAlign="center" allowsort="true">专利申请号</div>
                        <div field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="FAMINGMC" width="250" headeralign="center" allowsort="true"
                             renderer="onZhanlihaoZhuangtai">专利名称
                        </div>
                        <div field="SHENQINGR" width="110" headerAlign="center" Align="center" dataType="date"
                             dateFormat="yyyy-MM-dd" allowsort="true">申请日期
                        </div>
                        <div field="ANJIANYWZT" width="100" headerAlign="center" allowsort="true" align="center">专利状态
                        </div>
                        <div field="FAMINGRXM" width="200" headerAlign="center" allowsort="true" align="center">发明人
                        </div>
                        <div field="NEIBUBH" width="200" headerAlign="center" allowsort="true">内部编号</div>
                        <div field="KH" width="200" headerAlign="center" align="center" allowsort="true">所属客户</div>
                        <div field="YW" width="100" headeralign="center" renderer="onRendererXS" align="center"
                             allowsort="true">销售责任人
                        </div>
                        <div field="JS" width="100" headerAlign="center" align="center" allowsort="true">代理责任人</div>
                        <div field="LC" width="100" headerAlign="center" align="center" allowsort="true">流程责任人</div>
                        <div field="DAILIJGMC" width="250" headerAlign="center" allowsort="true">代理机构</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="editWindow" class="mini-window" title="新增年费监控项目" showmodal="true"
         allowresize="true" allowdrag="true" style="width:1200px;">
        <div class="mini-toolbar" style="margin:0px;padding:0px">
            <a class="mini-button" iconCls="icon-ok" onclick="importManyYear();">确认添加</a>
            <a class="mini-button" iconCls="icon-cancel" onclick="cancelRow();">取消关闭</a>
        </div>
        <table style="width:100%;" id="editform">
            <tbody>
            <tr>
                <div class="mini-datagrid" style="width:99%;height:400px" id="addYearGrid" allowCellEdit="true"
                     allowCellSelect="true" allowCellValid="true" oncellbeginedit="beforeEditYear"
                     oncellendedit="endEditYear" ondrawcell="onDrawYear">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div field="SHENQINGH" width="80" align="center" headerAlign="center"
                             allowsort="true">专利申请号
                        </div>
                        <div field="FAMINGMC" width="200" headeralign="center" allowsort="true">专利名称</div>
                        <div field="SHENQINGLX" width="80" headerAlign="center" Align="center" type="comboboxcolumn"
                             allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="SHENQINGR" width="110" headerAlign="center" Align="center" dataType="date"
                             dateFormat="yyyy-MM-dd" allowsort="true">申请日期
                        </div>
                        <div field="FeePercent" headerAlign="center" Align="center" width="120"
                             type="comboboxcolumn" vtype="required">费减比例
                            <input property="editor" class="mini-combobox" name="FeePercent" data="[{id:'无费减',
                                text:'无费减'},{id:'85%',text:'85%'},{id:'70%',text:'70%'}]" value="无费减"/>
                        </div>
                        <div field="BeginTimes" width="110" headerAlign="center" Align="center" type="comboboxcolumn"
                             vtype="required">
                            首次缴费年度
                            <input property="editor" class="mini-combobox" data="years"/>
                        </div>
                        <div field="BeginJiaoFei" width="110" headerAlign="center" Align="center" type="comboboxcolumn"
                             vtype="required">
                             开始监控年度
                            <input property="editor" class="mini-combobox" data="years"/>
                        </div>

                    </div>
                </div>
            </tr>
            </tbody>
        </table>
    </div>
    <div id="editWindow1" class="mini-window" title="新增申请费监控项目" showmodal="true"
         allowresize="true" allowdrag="true" style="width:1200px;">
        <div class="mini-toolbar" style="margin:0px;padding:0px">
            <a class="mini-button" iconCls="icon-ok" onclick="importManyApply();">确认添加</a>
            <a class="mini-button" iconCls="icon-cancel" onclick="cancelRow();">取消关闭</a>
        </div>
        <table style="width:100%;" id="editform1">
            <tbody>
            <tr>
                <div class="mini-datagrid" style="width:99%;height:400px" id="addApplyGrid" allowCellEdit="true"
                     allowCellSelect="true" allowCellValid="true">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div field="SHENQINGH" width="80" align="center" headerAlign="center"
                             allowsort="true">专利申请号
                        </div>
                        <div field="FAMINGMC" width="200" headeralign="center" allowsort="true">专利名称</div>
                        <div field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="SHENQINGR" width="110" headerAlign="center" Align="center" dataType="date"
                             dateFormat="yyyy-MM-dd" allowsort="true">申请日期
                        </div>
                        <div field="FeePercent" headerAlign="center" Align="center" width="120"
                             type="comboboxcolumn" vtype="required">费减比例
                            <input property="editor" class="mini-combobox" name="FeePercent" data="[{id:'无费减',
                                text:'无费减'},{id:'85%',text:'85%'},{id:'70%',text:'70%'}]" value="无费减"/>
                        </div>
                    </div>
                </div>
            </tr>
            </tbody>
        </table>
    </div>
    <form method="post" action="/work/patentInfo/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#datagrid1');
        var addGrid = mini.get('#addYearGrid');
        var addGrid1 = mini.get('#addApplyGrid');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();

        var addApply = mini.get('#FeeWatch_AddApply');
        var addYear = mini.get('#FeeWatch_AddYear');
        var abortApply = mini.get('#FeeWatch_AbortApply');
        var abortYear = mini.get('#FeeWatch_AbortYear');

        $(function () {
            $('#p1').hide();
            fit.setHeight('100%');
            fit.doLayout();
        });

        function beforeEditYear(e) {
            var field = e.field;
            if (field == "SHENQINGLX") {
                e.cancel = true;
                return;
            }

            var record = e.record;
            var shenqinglx = parseInt(record["SHENQINGLX"] || 0);
            var editor = e.editor;
            if (field == "BeginJiaoFei" || field == "BeginTimes") {
                if (shenqinglx == 2) {
                    editor.setData(tinyYear);
                } else editor.setData(years);
            }
        }

        function expand(e) {
            e = e || {};
            var btn = e.sender;
            if (!btn) {
                btn = mini.get('#FeeWatch_HighQuery');
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

        function onRendererXS(e) {
            var value = e.value;
            if (value) return value;
            if (e.record.YW) return e.record.YW;
            return '';
        }

        function onRenderLX(e) {
            var field = e.field;
            if (field == "SHENQINGLX") {
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
            else if(field=="ApplySource" || field=="YearSource"){
                var val = parseInt(e.value || 1);
                var textVal = "";
                for (var i = 0; i < watchModes.length; i++) {
                    var tt = watchModes[i];
                    if (tt.id == val) {
                        textVal = tt.text;
                        break;
                    }
                }
                switch (val) {
                    case 1: {
                        e.cellHtml = "<span style='color:black'>" + textVal + "</style>";
                        break;
                    }
                    case 2: {
                        e.cellHtml = "<span style='color:blue'>" + textVal + "</style>";
                        break;
                    }
                    case 3: {
                        e.cellHtml = "<span style='color:red'>" + textVal + "</style>";
                        break;
                    }
                    case 4: {
                        e.cellHtml = "<span style='color:green'>" + textVal + "</style>";
                        break;
                    }
                }
            }
            else if (field == "SHENQINGR") {
                var val = e.value;
                if (val) {
                    if (typeof(val) == "object") {
                        e.cellHtml = mini.formatDate(val, 'yyyy-MM-dd');
                    }
                    else if (typeof(val) == "string") {
                        var ds = val.split(' ');
                        e.cellHtml = $.trim(ds[0]);
                    }
                }
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
                e.cellHtml = '<a href="#"  data-placement="bottomleft"  code="' + record["SHENQINGH"] + '" class="showCellTooltip" onclick="ShowMemo(' + "'" + record["SHENQINGH"] + "'," + "'" + record["FAMINGMC"] + "'" + ')">' + text + '</a>';
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

        function ShowMemo(id, title) {
            mini.open({
                url: '/work/addMemo/index?ID=' + id,
                showModal: true,
                width: 1000,
                height: 500,
                title: "【" + title + "】的备注信息",
                onDestroy: function () {
                    grid.reload();
                }
            });
        }

        function afterload() {
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
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["SHENQINGH"] == code) return true;
                        });
                        if (row) {
                            var memo = row["MEMO"];
                            if (memo) {
                                tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;word-wrap:break-word;word-break:break-all;text-align:left;vertical-align: text-top; "><tr><td>' + row["MEMO"] + '</td></tr></table>');
                            }
                        }
                    }
                }
            });
            updateNumber();
            addApply.disable();
            addYear.disable();
        }

        function addApplyItem() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var ck = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var dd = {
                        SHENQINGH: row.SHENQINGH, FAMINGMC: row.FAMINGMC, SHENQINGLX: row.SHENQINGLX,
                        SHENQINGR: row.SHENQINGR
                    };
                    ck.push(dd);
                }
                addGrid1.setData(ck);

                addGrid1.validate();
                var editWindow = mini.get("editWindow1");
                editWindow.show();
            }
        }

        function addYearItem() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var ck = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var dd = {
                        SHENQINGH: row.SHENQINGH, FAMINGMC: row.FAMINGMC, SHENQINGLX: row.SHENQINGLX,
                        SHENQINGR: row.SHENQINGR
                    };
                    ck.push(dd);
                }
                addGrid.setData(ck);

                addGrid.validate();
                var editWindow = mini.get("editWindow");
                editWindow.show();
            }
        }

        function doExport() {
            var excel = new excelData(grid);
            excel.export("专利综合信息.xls");
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

        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            doQuery();
        }

        function doQuery(code, state) {
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
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL") f = "NEIBUBH";
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
            if (code && state) {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }

        function onRowClick(e) {
            var rows = grid.getSelecteds();
            var ass = [];
            var yss = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var applyWatch = parseInt(row["ApplyWatch"] || 1);
                var yearWatch = parseInt(row["YearWatch"] || 1);

                ass.push(applyWatch);
                yss.push(yearWatch);
            }
            if (ass.indexOf(2) > -1) {
                addApply.disable();
            } else {
                addApply.enable();
            }
            if (yss.indexOf(2) > -1) {
                addYear.disable();
            } else {
                addYear.enable();
            }
            if (ass.indexOf(3) > -1) {
                abortApply.disable();
            } else abortApply.enable();

            if (yss.indexOf(3) > -1) {
                abortYear.disable();
            } else abortYear.enable();
        }

        function download() {
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if (rows.length == 1) {
                var row = rows[0];
                if (row) {
                    code = row["SHENQINGH"];
                    name = row["FAMINGMC"] + '.zip';
                }
            }
            else {
                var codes = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var code = row["SHENQINGH"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + '个专利申请文件打包下载.zip';
            }
            var url = '/work/patentInfo/download?Code=' + code + '&FileName=' + encodeURI(name);
            var boxId = mini.open({
                url: url
            });
            boxId.hide();
            event.preventDefault();
            event.stopPropagation();
            return false;
        }

        function doImportYear() {
            mini.open({
                url: '/common/importData/index?code=YearFeeItem&fileName=' + encodeURIComponent('专利年费数据导入模版.xls'),
                width: '100%',
                height: '100%',
                title: '导入专利年费数据',
                showModal: true,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.initUpload('/watch/feeWatch/importYearData');
                },
                ondestroy:function(){
                    grid.reload();
                }
            });
        }
        function doImportApply() {
            mini.open({
                url: '/common/importData/index?code=ApplyFeeItem&fileName=' + encodeURIComponent('专利申请费数据导入模版.xls'),
                width: '100%',
                height: '100%',
                title: '导入专利申请费数据',
                showModal: true,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.initUpload('/watch/feeWatch/importApplyData');
                },
                ondestroy:function(){
                    grid.reload();
                }
            });
        }

        function importManyYear() {
            function g() {
                var iiid = mini.loading("正在导入数据，请稍候................");
                var rows = addGrid.getData();
                var arg = {Data: mini.encode(rows),Mode:'Year'};
                $.post('/watch/feeWatch/importYearData', arg, function (result) {
                    mini.hideMessageBox(iiid);
                    if (result.success) {
                        var ds = (result.data)[0];
                        var importResult = ds["ImportResult"];
                        if (importResult == '导入成功') {
                            mini.alert('专利数据导入成功!', '系统提示', function () {
                                var editWindow = mini.get("editWindow");
                                editWindow.hide();
                                grid.reload();
                            });
                        } else {
                            mini.alert(importResult);
                        }
                    } else {
                        mini.alert(result.message || "导入失败，请稍候重试!");
                    }
                })
            }

            addGrid.validate();
            if (addGrid.isValid())
            {
                checkOne();
                mini.confirm('确认要添加选择的专利的年费监控数据吗？', '系统提示', function (act) {
                    if (act == 'ok') {
                        g();
                    }
                });
            } else mini.alert('数据录入不完整，不能进行添加监控操作!');

            function checkOne(){
                var rows=addGrid.getData();
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    var shenqingh=row["SHENQINGH"];
                    var beginJiaoFei=parseInt(row["BeginJiaoFei"] || 1);
                    var beginTimes=parseInt(row["BeginTimes"] || 1);
                    if(beginJiaoFei<beginTimes){
                        throw shenqingh+"的开始监控年度不能小于初次缴费年度!";
                    }
                }
            }
        }

        function importManyApply() {
            function g() {
                var iiid = mini.loading("正在导入数据，请稍候................");
                var rows = addGrid1.getData();
                var arg = {Data: mini.encode(rows),Mode:'Apply'};
                $.post('/watch/feeWatch/importApplyData', arg, function (result) {
                    mini.hideMessageBox(iiid);
                    if (result.success) {
                        var ds = (result.data)[0];
                        var importResult = ds["ImportResult"];
                        if (importResult == '导入成功') {
                            mini.alert('专利数据导入成功!', '系统提示', function () {
                                var editWindow = mini.get("editWindow1");
                                editWindow.hide();
                                grid.reload();
                            });
                        } else {
                            mini.alert(importResult);
                        }
                    } else {
                        mini.alert(result.message || "导入失败，请稍候重试!");
                    }
                })
            }

            addGrid.validate();
            if (addGrid.isValid()) {
                mini.confirm('确认要添加选择的专利的申请费监控数据吗？', '系统提示', function (act) {
                    if (act == 'ok') {
                        g();
                    }
                });
            } else mini.alert('数据录入不完整，不能进行添加监控操作!');
        }

        function updateNumber() {
            var url = '/watch/feeWatch/refreshTotal';
            $.getJSON(url, {}, function (result) {
                var rows = result.data || [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var cName = row["name"];
                    var num = parseInt(row["num"] || 0);
                    var con = $('.' + cName);
                    if (con.length > 0) {
                        con.text(num);
                    }
                }
            });
        }

        function changeQuery(code, state,obj) {
            var con = $(event.srcElement || e.targetElement);
            var cons=$('.Jdlcli');
            for(var i=0;i<cons.length;i++){
                var cx=cons[i];
                if(cx.className=="Jdlcli AppWatchlySource" || cx.className=="Jdlcli AppWatchlySource clicked" ){
                    cx.children[0].children[0].style.cssText="color:rgb(0, 159, 205);";
                    cx.children[0].children[1].style.cssText="color:rgb(0, 159, 205);";
                }else if(cx.className=="Jdlcli YearWatchlySource" || cx.className=="Jdlcli YearWatchlySource clicked" ){
                    cx.children[0].children[0].style.cssText="color:rgb(53, 102, 231)";
                    cx.children[0].children[1].style.cssText="color:rgb(53, 102, 231)";
                }
                cx.classList.remove('clicked');
            }
            $(con).parents('.Jdlcli').addClass('clicked');
            obj.children[0].style.cssText="color:#fff";
            obj.children[1].style.cssText="color:#fff";
            doQuery(code, state);
            window.parent.doResize();
        }

        function changeValue(Text, Code, State) {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var shenqingh = row["SHENQINGH"];
                    if (shenqingh) cs.push(shenqingh);
                }
                var args = {SHENQINGHS: cs.join(','), Code: Code, State: State};
                mini.confirm('确认要将选择的专利设置为:' + Text + '?', '系统提示', function (act) {
                    if (act == 'ok') {
                        var url = '/watch/feeWatch/changeValue';
                        $.post(url, args, function (result) {
                            if (result.success) {
                                mini.alert('设置成功!', '系统提示', function () {
                                    grid.reload();
                                });
                            } else {
                                mini.alert(result.message || "设置失败，请稍候重试!");
                            }
                        });
                    }
                });
            } else mini.alert('请选择要设置的记录!');
        }

        function endEditYear(e) {
            var field = e.field;
            if (field == "BeginTimes") {
                var val = e.value;
                var record = e.record;
                if (val) {
                    var jfNum = parseInt(record["BeginJiaoFei"]);
                    if (!jfNum) {
                        e.record["BeginJiaoFei"] = val;
                        grid.updateRow(record, {BeginJiaoFei: val});
                        grid.acceptRecord(record);
                    }
                }
            }
        }

        function onDrawYear(e) {
            var field = e.field;
            if (field == "BeginJiaoFei") {
                var val = e.value;
                if (!val) {
                    var record = e.record;
                    var beginTimes = parseInt(record["BeginTimes"]);
                    if (beginTimes) e.cellHtml = beginTimes;
                }
            }
        }
        function cancelRow(){
            var editWindow1 = mini.get("editWindow1");
            if(editWindow1)editWindow1.hide();

            var editWindow = mini.get("editWindow");
            if(editWindow)editWindow.hide();
            grid.reload();
        }
    </script>
</@layout>