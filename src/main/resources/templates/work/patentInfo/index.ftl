<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript" src="/js/clipboard.min.js"></script>
    <script type="text/javascript">
        var users = ${Users};
        var users2 = {};
        for (i in users) {
            users2[users[i]] = i;
        }
        var querFields = [
            {id: 'All', text: '全部属性'},
            {id: 'Memo', text: '手动标识'},
            {id: 'SHENQINGRXM', text: '专利申请人'},
            {id: 'SHENQINGH', text: '专利申请号'},
            {id: 'FAMINGMC', text: '发明名称'},
            {id: 'ANJIANYWZT', text: '专利状态'},
            {id: 'SQTYPE',text:'代理类型'},
            {id: 'SHENQINGLXText', text: '专利类型'},
            {id: 'FAMINGRXM', text: '发明人'},
            {id: 'DAILIJGMC', text: '代理机构'},
            {id: 'KH', text: '所属客户'},
            {id: 'XS', text: '销售人员'},
            {id: 'DL', text: '代理责任人'},
            {id: 'LC', text: '流程人员'}
        ];
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
        var qtTypes = [
            {id: '风险代理', text: '风险代理'},
            {id:'风险代理及预审', text: '风险代理及预审'},
            {id:'普通代理预审', text: '普通代理预审'},
            {id:'风险代理及优先审查', text: '风险代理及优先审查'},
            {id:'普通代理优先审查', text: '普通代理优先审查'},
            {id:'',text:'无'}
        ];
        var tzsStatus = ${TZSStatus};
        var ss = "";
    </script>
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
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center">
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
                                            <img style="vertical-align: middle;" src="/appImages/jk.png">&nbsp;专利综合信息
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli orig" id="J0">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(0)">
                                                    <span id="J1span">全部</span>
                                                    <h4 class="x0">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J1">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(1)">
                                                    <span id="J2span">审中</span>
                                                    <h4 class="x1">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J2">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(2)">
                                                    <span id="J3span">有权</span>
                                                    <h4 class="x2">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J3">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(3)">
                                                    <span id="J4span">年费滞纳恢复期</span>
                                                    <h4 class="x3">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J4">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(4)">
                                                    <span id="J5span">专利失效</span>
                                                    <h4 class="x4">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J5">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(5)">
                                                    <span id="J5span">其他</span>
                                                    <h4 class="x5">0</h4>
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
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button mini-button-success" id="PatentInfoBrowse_doJiaJi" onclick="doJiaJi"
                               idClass="">设置加急</a>
                            <a class="mini-button mini-button-danger" id="PatentInfoBrowse_unJiaJi" onclick="unJiaJi"
                               idClass="PatentInfoBrowse_Config">取消加急</a>
                            <a class="mini-button mini-button-success" id="PatentInfoBrowse_doJieAn" onclick="doJieAn"
                               idClass="PatentInfoBrowse_Config">设置结案</a>
                            <a class="mini-button mini-button-danger" id="PatentInfoBrowse_unJieAn" onclick="unJieAn"
                               idClass="PatentInfoBrowse_Config">取消结案</a>
                            <a class="mini-button mini-button-success" id="PatentInfoBrowse_configNBBH"
                               onclick="configNBBH" idClass="PatentInfoBrowse_Config">设置编号</a>
                            <a class="mini-button mini-button-danger" id="PatentInfoBrowse_unNBBH" onclick="unNBBH"
                               idClass="PatentInfoBrowse_Config">取消编号</a>
                            <span class="separator"></span>
                            <a class="mini-button mini-button-info" id="PatentInfoBrowse_Download" onclick="download">申请文件下载</a>
                            <a class="mini-button mini-button-info" id="PatentInfoBrowse_ChangeJS"
                               onclick="changeXS">更换代理师</a>
                            <a class="mini-button mini-button-info" onclick="doExport" id="PatentInfoBrowse_Export">导出Excel</a>
                            <a class="mini-button mini-button-info" onclick="doImport"
                               id="PatentInfoBrowse_Import">导入专利</a>
                            <a class="mini-button mini-button-info" id="PatentInfoBrowse_AllDownload" onclick="alldownload">所有文件下载</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field PatentInfoBrowse_Query" id="comField"
                                 style="width:100px" data="querFields" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field PatentInfoBrowse_Query" style="width:150px"
                                   id="QueryText"/>
                            <a class="mini-button mini-button-success" onclick="doQuery();" id="PatentInfoBrowse_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger Query_Field" id="PatentInfoBrowse_Reset"
                               onclick="reset()">重置</a>
                            <a class="mini-button" id="PatentInfoBrowse_HighQuery" iconCls="panel-expand"
                               onclick="expand">高级查询</a>

                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:100px;padding:5px;display:none">
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
                            <td style="width:15%;">
                                <input class="mini-combobox" data="tzsStatus" name="ANJIANYWZT"
                                       data-oper="EQ" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">专利申请人：</td>
                            <td style="width:15%;"><input name="SHENQINGRXM" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                                /></td>
                            <td style="width:6%;" title="归属客户/销售维护人/代理责任人/流程责任人">
                                内部编号：
                            </td>
                            <td style="width:15%;"><input name="NEIBUBH" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
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
                     url="/work/patentInfo/getData?LongTime=1"
                     allowCellEdit="true" allowCellSelect="true" sizelist="[5,10,20,50,100,200]"
                     onrowclick="onRowClick" autoload="false" allowHeaderWrap="true"
                     oncellbeginedit="beforeEdit" oncellcommitedit="afterEdit"
                     frozenStartColumn="0" frozenEndColumn="11" multiSelect="true" pageSize="20"
                     sortField="SHENQINGR" sortOrder="desc" ondrawcell="onRenderLX" onload="afterload"
                     oncelldblclick="cellDblClick">
                    <div property="columns">
                        <div type="indexcolumn" style="width:80px"></div>
                        <div type="checkcolumn"></div>
                        <div width="50" field="DE" align="center" headerAlign="center"></div>
                        <div field="Action" width="45" headerAlign="center" align="center">备注信息</div>
                        <div field="Memo" width="45" headerAlign="center" align="center">手动标识
                            <input property="editor" class="mini-textbox" style="width:100%"/>
                        </div>
                        <div field="JIAJI" width="45" headerAlign="center" align="center" type="comboboxcolumn"
                             allowsort="true">是否加急
                            <input property="editor" class="mini-combobox" data="[{id:false,text:'否'},{id:true,
                            text:'是'}]"/>
                        </div>
                        <div field="JIEAN" width="45" headerAlign="center" align="center" type="comboboxcolumn"
                             allowsort="true">
                            是否结案
                            <input property="editor" class="mini-combobox" data="[{id:false,text:'否'},{id:true,
                            text:'是'}]"/>
                        </div>
                        <div field="SQTYPE" width="70" align="center" headerAlign="center" type="comboboxcolumn"
                             allowsort="true">代理类型
                            <input property="editor" class="mini-combobox" data="qtTypes" popupWidth="200"/>
                        </div>
                        <div field="SHENQINGRXM" width="180" headerAlign="center" allowsort="true">专利申请人</div>
                        <div field="SHENQINGH" width="108" headerAlign="center" allowsort="true">专利申请号</div>
                        <div field="SHENQINGLX" width="70" align="center" headerAlign="center" type="comboboxcolumn"
                             allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="FAMINGMC" width="200" headeralign="center" allowsort="true"
                             renderer="onZhanlihaoZhuangtai">专利名称
                        </div>
                        <div field="SHENQINGR" width="90" headerAlign="center" Align="center" dataType="date"
                             dateFormat="yyyy-MM-dd" allowsort="true">申请日期
                        </div>
                        <div field="ANJIANYWZT" width="100" headerAlign="center" allowsort="true" align="center">专利状态
                        </div>
                        <div field="FAMINGRXM" width="180" headerAlign="center" allowsort="true" align="center">发明人
                        </div>
                        <#if RoleName!="客户" && RoleName!="外协代理师">
                            <div field="NEIBUBH" width="200" headerAlign="center" allowsort="true">内部编号</div>
                            <#if RoleName?index_of("技术")==-1>
                                <div field="KH" width="200" headerAlign="center" align="center" allowsort="true">所属客户</div>
                            </#if>
                            <div field="YW" width="100" headeralign="center" renderer="onRendererXS" align="center"
                                 allowsort="false">销售责任人
                            </div>
                            <div field="BH" width="120" headerAlign="center" align="center" allowsort="false">立案编号</div>
                            <div field="JS" width="80" headerAlign="center" align="center" allowsort="false">代理师</div>
                            <div field="LC" width="100" headerAlign="center" align="center" allowsort="false">流程责任人</div>
                        </#if>
                        <div field="DAILIJGMC" width="200" headerAlign="center" allowsort="true">代理机构</div>
                        <div field="DIYIDLRXM" width="80" headerAlign="center" align="center"
                             allowsort="true">签名代理师
                        </div>
                        <div field="LASTUPDATETIME" width="140" headerAlign="center" Align="center" dataType="date"
                             dateFormat="yyyy-MM-dd HH:mm" allowsort="true">更新时间
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="editWindow" class="mini-window" title="编辑内部编号" showmodal="true"
         allowresize="true" allowdrag="true" style="width:800px;">
        <div id="editform" class="form">
            <table style="width:100%;">
                <tr>
                    <td style="width:80px;">内部编号：</td>
                    <td colspan="5"><input id="ipNbbh" enabled="false" class="mini-textbox" style="width:100%"/></td>
                </tr>
                <tr>
                    <td style="width:80px;">销售人员：</td>
                    <td style="width:300px;"><input class="mini-treeselect" id="ipXs" onvaluechanged="showResult"
                                                    valuefield="FID" parentfield="PID" textfield="Name"
                                                    url="/systems/dep/getAllLoginUsersByDep" name="XS" style="width:
                                                    100%;" resultastree="false" valuefromselect="true"
                                                    expandonload="true" allowInput="true"/></td>
                    <td style="width:80px;">代理师：</td>
                    <td style="width:300px;"><input class="mini-treeselect" id="ipJs" onvaluechanged="showResult"
                                                    valuefield="FID" parentfield="PID" textfield="Name"
                                                    url="/systems/dep/getAllLoginUsersByDep" name="JS" style="width:
                                                    100%;" resultastree="false" valuefromselect="true"
                                                    expandonload="true" allowInput="true"/></td>
                </tr>
                <tr>
                    <td style="width:80px;">流程人员：</td>
                    <td style="width:300px;">
                        <input class="mini-treeselect" id="ipLc" onvaluechanged="showResult"
                               valuefield="FID" parentfield="PID" textfield="Name"
                               url="/systems/dep/getAllLoginUsersByDep" name="LC" style="width:
                                                    100%;" resultastree="false" valuefromselect="true"
                               expandonload="true" allowInput="true"/>
                    </td>
                    <td style="width:80px;">专利外协：</td>
                    <td style="width:300px;">
                        <input class="mini-treeselect" id="ipZc" emptyText="可不填写" onvaluechanged="showResult"
                               valuefield="FID" parentfield="PID" textfield="Name"
                               url="/systems/dep/getAllLoginUsersByDep" name="ZC" style="width: 100%;"
                               resultastree="false" valuefromselect="true" expandonload="true" allowInput="true"/></td>
                </tr>
                <tr>
                    <#if RoleName?index_of("技术")==-1>
                        <td style="width:80px;">客户：</td>
                        <td colspan="3"><input name="KH" textname="KH" class="mini-buttonedit" style="width:100%"
                                               onbuttonclick="onCustomDialog" allowInput="false"/></td>
                    </#if>
                </tr>
                <tr>
                    <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="6">
                        <buttton class="mini-button" iconCls="icon-ok" class="Update_Button" onclick="saveNBBH();
">确定
                        </buttton>
                        <button class="mini-button" iconCls="icon-cancel" class="Cancel_Button" onclick="cancelRow();
">取消
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <form method="post" action="/work/patentInfo/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#datagrid1');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var cmdQuery = mini.get('#PatentInfoBrowse_Query');
        var cmdDownload = mini.get('PatentInfoBrowse_Download');
        var cmdAllDownload = mini.get('PatentInfoBrowse_AllDownload');
        var cmdChangeJS = mini.get('PatentInfoBrowse_ChangeJS');

        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        $(function () {
            $('#p1').hide();
            fit.setHeight('100%');
            fit.doLayout();

            changeQuery(0);
        });

        function expand(e) {
            e = e || {};
            var btn = e.sender;
            if (!btn) {
                btn = mini.get('#PatentInfoBrowse_HighQuery');
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
            } else if (field == "SHENQINGR") {
                var val = e.value;
                if (val) {
                    if (typeof (val) == "object") {
                        e.cellHtml = mini.formatDate(val, 'yyyy-MM-dd');
                    } else if (typeof (val) == "string") {
                        var ds = val.split(' ');
                        e.cellHtml = $.trim(ds[0]);
                    }
                }
            } else if (field == "Action") {
                var record = e.record;
                var memo = record["MEMO"];
                var editMemo = parseInt(record["EDITMEMO"] || 0);
                var text = ((memo == null || memo == "") ? "<span style='color:green;text-align:center'>添加</span>" : "<span " +
                    "style='color:blue'>修改</span>");
                if (editMemo == 0) {
                    if (memo) text = "<span style='color:gay;text-align:center'>查看</span>";
                }
                e.cellHtml = '<a href="javascript:void(0)"  data-placement="bottomleft"  code="' + record["SHENQINGH"] + '" class="showCellTooltip" onclick="ShowMemo(' + "'" + record["SHENQINGH"] + "'," + "'" + record["FAMINGMC"] + "'" + ')">' + text + '</a>';
            } else if (field == "KH") {
                var val = e.value;
                if (val) {
                    var clientId = e.record["KHID"];
                    if (clientId) {
                        e.cellHtml = '<a href="javascript:void(0)" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                    } else e.cellHtml = val;
                }
            } else if (field == "BH") {
                var val = e.value;
                if (val) {
                    var casesId = e.record["CasesID"];
                    if (casesId) {
                        e.cellHtml = '<a href="javascript:void(0)" onclick="editCases(' + "'" + casesId + "'" + ')">' + val + '</a>';
                    }
                }
            } else if (field == "DE") {
                var iid = e.record._id;
                e.cellHtml = '<a href="javascript:void(0)" onclick="detail(' + "'" + iid + "'" + ')">详情..</a>';
            }
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
                title: "【" + title + "】备注信息",
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
            try {
                updateStateNumbers();
            } catch (e) {

            }
        }

        function unJiaJi() {
            var rows = mini.clone(grid.getSelecteds());
            var ps = [];
            if (rows.length > 0) {
                function p() {
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        ps.push(row["SHENQINGH"]);
                    }
                    var url = '/work/patentInfo/changeValue';
                    $.post(url, {IDS: ps.join(","), field: "JIAJI", value: false}, function (r) {
                        if (r.success) {
                            mini.alert('选择项目设置成功.', '系统提示', function () {
                                grid.reload();
                            });
                        } else {
                            var msg = r.message || "设置失败，请稍息重试。";
                            mini.alert(msg);
                        }
                    });
                }

                mini.confirm('确认要设置选择的项目为加急状态吗？', '设置提醒', function (btn) {
                    if (btn == 'ok') {
                        p();
                    }
                });
            } else mini.alert('请选择要设置加急的项目。');
        }

        function doJieAn() {
            var rows = mini.clone(grid.getSelecteds());
            var ps = [];
            if (rows.length > 0) {
                function p() {
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        ps.push(row["SHENQINGH"]);
                    }
                    var url = '/work/patentInfo/changeValue';
                    $.post(url, {IDS: ps.join(","), field: 'JIEAN', value: true}, function (r) {
                        if (r.success) {
                            mini.alert('选择项目设置成功.', '系统提示', function () {
                                grid.reload();
                            });
                        } else {
                            var msg = r.message || "设置失败，请稍息重试。";
                            mini.alert(msg);
                        }
                    });
                }

                mini.confirm('确认要设置选择的项目为结案状态吗？', '设置提醒', function (btn) {
                    if (btn == 'ok') {
                        p();
                    }
                });
            } else mini.alert('请选择要设置结案的项目。');

        }

        function unJieAn() {
            var rows = mini.clone(grid.getSelecteds());
            var ps = [];
            if (rows.length > 0) {
                function p() {
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        ps.push(row["SHENQINGH"]);
                    }
                    var url = '/work/patentInfo/changeValue';
                    $.post(url, {IDS: ps.join(","), field: 'JIEAN', value: false}, function (r) {
                        if (r.success) {
                            mini.alert('选择项目取消结案状态成功.', '系统提示', function () {
                                grid.reload();
                            });
                        } else {
                            var msg = r.message || "设置失败，请稍息重试。";
                            mini.alert(msg);
                        }
                    });
                }

                mini.confirm('确认要取消选择的项目的结案状态吗？', '设置提醒', function (btn) {
                    if (btn == 'ok') {
                        p();
                    }
                });
            } else mini.alert('请选择要设置加急的项目。');
        }

        function doJiaJi() {
            var rows = mini.clone(grid.getSelecteds());
            var ps = [];
            if (rows.length > 0) {
                function p() {
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        ps.push(row["SHENQINGH"]);
                    }
                    var url = '/work/patentInfo/changeValue';
                    $.post(url, {IDS: ps.join(","), field: 'JIAJI', value: true}, function (r) {
                        if (r.success) {
                            mini.alert('选择项目设置成功.', '系统提示', function () {
                                grid.reload();
                            });
                        } else {
                            var msg = r.message || "设置失败，请稍息重试。";
                            mini.alert(msg);
                        }
                    });
                }

                mini.confirm('确认要设置选择的项目为加急状态吗？', '设置提醒', function (btn) {
                    if (btn == 'ok') {
                        p();
                    }
                });
            } else mini.alert('请选择要设置加急的项目。');
        }

        function configNBBH() {
            var rows = grid.getSelecteds();
            if (rows.length == 0) {
                mini.alert('请选择要设置的专利记录。');
                return;
            }
            var form = new mini.Form("#editform");
            form.reset();
            var editWindow = mini.get("editWindow");
            var code = rows[0]["NEIBUBH"];
            var ndata = {};
            if (code) {
                var rs = code.split(';');

                for (var i = 0; i < rs.length; i++) {
                    var r = rs[i];
                    var ts = r.split(':');
                    var name = ts[0];
                    var texts = ts[1];
                    if (!texts) continue;
                    if (name == "KH") {
                        ndata[name] = texts;
                    } else {
                        var ps = texts.split(',')
                        var values = [];
                        for (var n = 0; n < ps.length; n++) {
                            var p = ps[n];
                            var id = users[p];
                            values.push(id);
                        }
                        ndata[name] = values.join(',');
                    }
                }
            }
            form.setData(ndata);
            mini.get('ipNbbh').setValue(code);
            editWindow.show();
        }

        function unNBBH() {
            var rows = grid.getSelecteds();
            if (rows.length == 0) {
                mini.alert('请选择要设置的专利记录。');
                return;
            }

            function p() {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var sq = row["SHENQINGH"];
                    if (sq) cs.push(sq);
                }
                var url = '/work/patentInfo/changeValue';
                $.post(url, {IDS: cs.join(","), field: "NEIBUBH", value: ""}, function (r) {
                    if (r.success) {
                        mini.alert('取消专利内部编号成功!');
                        grid.reload();
                    } else {
                        var msg = r.message || "设置失败，请稍候重试。";
                        mini.alert(msg);
                    }
                });
            }

            mini.confirm('确认要取消当前选择的专利内部编号设置,相关人员的权限会被清空，是否继续？', '系统提示', function (b) {
                if (b == "ok") {
                    p();
                }
            });
        }

        function cancelRow() {
            var editWindow = mini.get("editWindow");
            if (editWindow) editWindow.show();
        }

        function onCustomDialog(e) {
            var btnEdit = this;
            mini.open({
                url: "/work/clientInfo/query?multiselect=1",
                title: "选择客户",
                width: 1000,
                height: 400,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);    //必须
                        if (data) {
                            var form = new mini.Form("#editform");
                            var cnames = [];
                            for (var i = 0; i < data.length; i++) {
                                cnames.push(data[i].Name);
                            }
                            if (cnames) {
                                form.setData({'KH': cnames.join(',')}, false);
                                showResult();
                            }
                        }
                    }
                }
            });
        }

        function saveNBBH() {
            var rows = grid.getSelecteds();
            var cs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var sq = row["SHENQINGH"];
                if (sq) cs.push(sq);
            }
            showResult();
            var url = '/work/patentInfo/changeValue';
            var code = mini.get('ipNbbh').getValue();
            var arg = {IDS: cs.join(","), value: code, field: "NEIBUBH"};
            $.post(url, arg, function (r) {
                if (r.success) {
                    mini.alert('选择记录的内部编号设置成功。');
                    var editWindow = mini.get("editWindow");
                    editWindow.hide();
                    grid.reload();
                } else {
                    var msg = r.message || "设置失败，请稍候重试。";
                    mini.alert(msg);
                }
            });
        }

        function showResult() {
            var form = new mini.Form("#editform");
            var datas = mini.clone(form.getData());
            var ps = [];
            for (var p in datas) {
                if (p && datas[p]) {
                    if (p == "KH") {
                        var g = p + ":" + datas[p];
                        ps.push(g);
                    } else {
                        var values = datas[p].split(',')
                        var texts = [];
                        for (var n = 0; n < values.length; n++) {
                            var val = values[n];
                            var text = users2[val];
                            texts.push(text);
                        }
                        var g = p + ":" + texts.join(',');
                        ps.push(g);
                    }
                }
            }
            mini.get('ipNbbh').setValue(ps.join(';'));
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
            if (curState) {
                var obj = {
                    field: 'State',
                    value: curState,
                    oper: 'EQ'
                };
                result.push(obj);
            }
            arg["High"] = mini.encode(result);
            grid.load(arg);
        }

        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            txtQuery.setValue(null);
            comField.setValue('All');
            doQuery();
        }

        function doQuery(state) {
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
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL" || f == "BH") f = "NEIBUBH";
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
            if (!state) {
                if (curState) state = curState;
            }
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

        function editCases(id) {
            mini.open({
                url: '/cases/editOne?Mode=Browse&ID=' + id,
                width: '100%',
                height: '100%',
                title: '查看业务交单',
                showModal: true,
                ondestroy: function () {
                    grid.reload();
                }
            });
        }

        function changeXS() {
            var rows = mini.clone(grid.getSelecteds());
            if (rows.length == 0) {
                mini.alert('请选择要转移代理师的记录。');
                return;
            }
            mini.open({
                url: '/work/changeTechMan/index',
                title: '更换代理师',
                showModal: true,
                width: 900,
                height: 500,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.setData(rows, users, users2);
                },
                onDestroy: function () {
                    if (grid) grid.reload();
                }
            });
        }

        function onRowClick(e) {
            var row = e.record;
            var tzsPath = row["UploadPath"];
            var jsr = row["JS"];
            var att = row["Att"];
            if (tzsPath) {
                cmdDownload.show();
            } else {
                cmdDownload.hide();
            }
            if (att == "0" || att == undefined) {
                cmdAllDownload.hide();
            } else cmdAllDownload.show();
            // if (jsr) {
            //     cmdChangeJS.show();
            // } else cmdChangeJS.hide();
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
            } else {
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
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {

                },
                failCallback: function (html, xurl) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }

        function alldownload() {
            var rows = grid.getSelecteds();
            var code = null;
            var name = null;
            if (rows.length == 0) {
                mini.alert("请选择需要下载的专利");
                return;
            }else if (rows.length == 1) {
                var row = rows[0];
                code = row["SubID"];
                name = row["SHENQINGH"] + "-" + row["FAMINGMC"] + "-交单附件.zip";
            }else if (rows.length > 1) {
                var codes = [];
                for (var i=0;i<rows.length;i++){
                    var row = rows[i];
                    var code = row["SubID"];
                    codes.push(code);
                }
                code = codes.join(',');
                name = rows.length + "个专利申请文件打包下载.zip";
            }
            var url = '/work/patentInfo/alldownload?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                successCallback: function (xurl) {

                },
                failCallback: function (html, xurl) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }

        function doImport() {
            mini.open({
                url: '/common/importData/index?code=PantentInfo&fileName=' + encodeURIComponent('专利数据导入模版.xls'),
                width: '100%',
                height: '100%',
                title: '导入专利数据',
                showModal: true,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.initUpload('/work/patentInfo/importData');
                }
            });
        }

        function beforeEdit(e) {
            var field = e.field;
            var record = e.record;
            var shenqingh = record["SHENQINGH"] || "";
            if (!shenqingh) {
                e.cancel = true;
                return;
            }

            if (field) {
                if (field == "Memo" || field == "SQTYPE") e.cancel = false; else e.cancel = true;
            } else e.cancel = true;
        }

        function afterEdit(e) {
            var record = e.record;
            var val = e.value;
            var field = e.field;
            var shenqingh = record["SHENQINGH"] || "";
            var msgId = mini.loading('正在保存信息.......', '保存提示');
            if (field == "Memo") {
                $.post('/work/patentInfo/updateMemo', {Memo: val, SHENQINGH: shenqingh}, function (result) {
                    mini.hideMessageBox(msgId);
                    if (result.success) {
                        grid.acceptRecord(record);
                    } else {
                        mini.showTips({
                            content: result.message || "保存失败!",
                            x: 'right',
                            y: 'buttom'
                        });
                    }
                });
            } else if (field == "SQTYPE") {
                var url = '/work/patentInfo/changeValue';
                var arg = {IDS:shenqingh, value: val, field: "SQTYPE"};
                $.post(url, arg, function (r) {
                    mini.hideMessageBox(msgId);
                    if (r.success) {
                        mini.alert('专利属性设置成功。','系统提示',function(){
                            grid.reload();
                        });
                    } else {
                        var msg = r.message || "设置失败，请稍候重试。";
                        mini.alert(msg);
                    }
                });
            }
        }

        function cellDblClick(e) {
            var row = grid.getSelected();
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

        function cancelRow() {
            var win = mini.get('editWindow');
            if (win) win.hide();

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

        function updateStateNumbers() {
            var key = (new Date()).getTime();
            var url = '/work/patentInfo/getPantentTotal?Key=' + key;
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

        var curState = null;

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
            curState = parseInt(state || 0);
            if (curState == 0) curState = 0;
            doQuery(state);
        }
    </script>
</@layout>