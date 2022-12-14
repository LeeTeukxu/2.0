<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <style>
        .showCellTooltip {
            color: blue;
            text-decoration: underline;
        }

        .ui-tooltip {
            max-width: 850px;
        }
    </style>
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
            margin-top: -24px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info1top ul li {
                margin-left: 10%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info1top ul li {
                margin-left: 10%;
            }
        }

        @media screen and (max-width: 1374px) {
            .info1top ul li {
                margin-left: 10%;
            }
        }

        @media screen and (max-width: 1233px) {
            .info1top ul li {
                margin-left: 10%;
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
            margin-top: -8px;
            list-style: none;
            margin-left: 20px;
        }

        .info1bottom ul li {
            float: left;
            margin-left: -2px;
            height: 45px;
            margin-top: -6px;
            padding-top: 12px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info1bottom ul li {
                margin-left: -10px;
            }
        }

        @media screen and (max-width: 1480px) {
            .info1bottom ul li {
                margin-left: -10px;
            }
        }

        @media screen and (max-width: 1374px) {
            .info1bottom ul li {
                margin-left: -15px;
            }
        }

        @media screen and (max-width: 1233px) {
            .info1bottom ul li {
                margin-left: -22px;
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
            margin-left: -2px;
            height: 41px;
            margin-top: 11px;
            padding-top: 9px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info3bottom ul li {
                margin-left: -10px;
            }
        }

        @media screen and (max-width: 1480px) {
            .info3bottom ul li {
                margin-left: -8px;
            }
        }

        @media screen and (max-width: 1374px) {
            .info3bottom ul li {
                margin-left: -15px;
            }
        }

        @media screen and (max-width: 1233px) {
            .info3bottom ul li {
                margin-left: -22px;
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
                margin-left: -24px;
            }

            .info2bottom ul li {
                margin-left: 1%;
            }

            .info3bottom ul li {
                margin-left: -24px;
            }
        }

        .sqf * {
            display: inline-block;
            vertical-align: middle;
        }

        .Zlyw * {
            display: inline-block;
            vertical-align: middle;
        }

        .Zlyw {
            padding-top: -30px;
        }

        .Zlyw img {
            width: 20px;
        }

        .Zlyw h5 {
            color: #ffffff;
            font-size: 15px
        }

        .Zlywtop {
            display: inline-block;
            width: 100%;
            float: left;
            margin-top: 8px;
            height: 32px;
        }

        .Zlywtop ul {
            margin-top: 5px;
            text-align: center;
            margin-left: -10px;
        }

        .Zlywtop ul li {
            float: left;
            margin-left: 0px;
            height: 45px;
            padding-top: 10px;
            width: 90px;
            border-radius: 5px;
            margin-top: 4px;
            list-style: none;
        }

        .Zlywtop ul li:hover {
            background-color: rgb(74, 106, 157);
        }

        .Zlywtop ul li a {
            color: white;
        }

        .Zlywtop ul li a span {
            font-size: 15px;
        }

        .Zlywtop ul li a h4 {
            display: inline
        }

        .info2bottom ul {
            margin-top: -8px;
            list-style: none;
            margin-left: 20px;
            text-align: center;
        }

        .info2bottom ul li {
            float: left;
            margin-left: 10%;
            height: 45px;
            margin-top: -6px;
            padding-top: 12px;
            border-radius: 5px;
        }

        @media screen and (max-width: 1593px) {
            .info2bottom ul li {
                margin-left: 11%;
            }
        }

        @media screen and (max-width: 1480px) {
            .info2bottom ul li {
                margin-left: 4%;
            }
        }

        @media screen and (max-width: 1184px) {
            .info2bottom ul li {
                margin-left: 3%;
            }
        }

        .info2bottom ul li:hover {
            background-color: rgb(216, 228, 250)
        }

        .info2bottom ul li a span {
            color: rgb(53, 102, 231);
            font-size: 15px
        }

        .info2bottom ul li a h4 {
            display: inline;
            color: rgb(51, 97, 232)
        }

        .ho {
            width: 55px;
            visibility: hidden;
        }
    </style>
    <script type="text/javascript">
        var states = [
            {id: 1, text: '?????????'},
            {id: 2, text: '?????????'},
            {id: 3, text: '?????????'},
            {id: 4, text: '????????????'},
            {id: 5, text: '????????????'},
            {id: 6, text: '????????????'},
            {id: 7, text: '????????????'},
            {id: 8, text: '?????????'},
            {id: 9, text: '?????????'}
        ];
    </script>
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" showheader="false" bodyStyle="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">

                            <div class="mini-col-1">
                                <div id="info0" style="height:80px;background-color: rgb(63,87,131);overflow: hidden;border-radius:3px;
                            -moz-box-shadow: 2px 2px 10px rgb(63,87,131);-webkit-box-shadow: 2px 2px 10px rgb(63,87,131);-shadow:2px 2px 10px rgb(63,87,131);">
                                    <div class="file-list">
                                        <div class="Zlywtop">
                                            <ul>
                                                <li class="Jdlcli Arrival" id="Z1">
                                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                       onclick="changeQuery(0,this)">
                                                        <span id="J1span">??????</span>&nbsp;
                                                        <h4 class="x0">0</h4>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mini-col-2">
                                <div id="info1"
                                     style="height:80px;background:rgb(226,250,252);border:1px solid rgb(190,226,240);border-radius: 5px;">
                                    <div class="info1top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(214,239,243);text-align: center;">
                                        <h2 style="color: rgb(3,154,209);margin-left: 5px;margin-top: 4px;">????????????</h2>
                                    </div>
                                    <div class="info1bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival" id="J1">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(1,this);">
                                                    <span id="J1span">?????????</span>
                                                    <h4 class="x1">0</h4>
                                                </a>
                                            </li>
                                            <li class="ho"></li>
                                            <li class="Jdlcli Arrival" id="J2">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(2,this);">
                                                    <span id="J2span">?????????</span>
                                                    <h4 id="J2h4" class="x2">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="mini-col-7">
                                <div id="info2"
                                     style="height:80px;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                                    <div class="info2top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                                        <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">????????????</h2>
                                    </div>
                                    <div class="info2bottom" style="width: 100%;height: 55px;text-align: center">
                                        <ul style="">
                                            <li class="Jdlcli Arrival" id="Js1">
                                                <a style="text-decoration:none" target="_self"
                                                   onclick="changeQuery(3,this)">
                                                    <span id="Js1span">?????????</span>
                                                    <h4 id="Js1h4" class="x3">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js2">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)" class="active_a"
                                                   onclick="changeQuery(4,this)">
                                                    <span id="Js2span">????????????</span>
                                                    <h4 id="Js2h4" class="x4">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js3">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)" class="active_a"
                                                   onclick="changeQuery(5,this)">
                                                    <span id="Js2span">????????????</span>
                                                    <h4 id="Js2h4" class="x5">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js4">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)" class="active_a"
                                                   onclick="changeQuery(6,this)">
                                                    <span id="Js2span">????????????</span>
                                                    <h4 id="Js2h4" class="x6">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js5">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)" class="active_a"
                                                   onclick="changeQuery(7,this)">
                                                    <span id="Js2span">????????????</span>
                                                    <h4 id="Js2h4" class="x7">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="mini-col-2">
                                <div id="info3"
                                     style="height:80px;background:rgb(232,230,252);border:1px solid rgb(213,210,239);border-radius: 5px;">
                                    <div class="info3top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(219,217,239);text-align: center;">
                                        <h2 style="color: rgb(91,35,220);margin-left: 5px;margin-top: 5px;">??????????????????</h2>
                                    </div>
                                    <div class="info3bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival" id="jsh2">
                                                <a style="text-decoration:none" target="_self"
                                                   onclick="changeQuery(8,this);" href="javascript:void(0)">
                                                    <span id="jsh2span">?????????</span>
                                                    <h4 id="jsh2h4" class="x8">0</h4>
                                                </a>
                                            </li>
                                            <li class="ho"></li>
                                            <li class="Jdlcli Arrival" id="jsh1">
                                                <a style="text-decoration:none" target="_self"
                                                   onclick="changeQuery(9,this);" href="javascript:void(0)">
                                                    <span id="jsh1span">?????????</span>
                                                    <h4 id="jsh1h4" class="x9">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                </table>
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-xls" id="CasesOutSource_Export"
                               onclick="doExport">??????Excel</a>
                            <a class="mini-button" iconcls="icon-add" id="CasesOutSource_AcceptOutTech"
                               onclick="acceptTech">????????????</a>
                            <a class="mini-button" iconcls="icon-no" id="CasesOutSource_RejectOutTech"
                               onclick="rejectTech">????????????</a>
                            <a class="mini-button" iconcls="icon-upload" id="CasesOutSource_UploadOutTechFile"
                               onclick="uploadTechFiles">??????????????????</a>
                            <a class="mini-button" iconcls="icon-ok" id="CasesOutSource_PassOutTech"
                               onclick="showAudit(1)">????????????</a>
                            <a class="mini-button" iconcls="icon-no" id="CasesOutSource_RollbackOutTech"
                               onclick="showAudit(0)">????????????</a>
                            <a class="mini-button" iconcls="icon-user" id="CasesOutSource_ChangeJS"
                               onclick="changeJS()" visible="false">??????????????????</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox CasesOutSource_Query" style="width:250px"
                                   emptyText="/???????????????/????????????/????????????/????????????/????????????/" id="queryText"/>
                            <a class="mini-button mini-button-success CasesOutSource_Query" id="a3"
                               onclick="doQuery">????????????</a>
                            <#--                            <a class="mini-button mini-button-primary CasesOutSource_HighQuery" id="a1"-->
                            <#--                               onclick="doHightSearch">????????????</a>-->
                            <a class="mini-button mini-button-danger CasesOutSource_Reset" id="a2"
                               onclick="reset">????????????</a>
                            <a class="mini-button mini-button-info" iconCls="icon-expand" onclick="expand">????????????</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:160px;padding:5px;display:none">
                    <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                        <tr>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="SubNo" style="width:90%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:90%" data-oper="GE" dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">???</td>
                            <td><input class="mini-datepicker" name="ClientRequiredDate" style="width:90%" data-oper="LE" dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">??????</td>
                            <td><input class="mini-textbox" name="Memo" style="width:90%" data-oper="LIKE"/></td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="YName" style="width:90%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">??????????????????</td>
                            <td><input class="mini-textbox" name="ShenqingName" style="width:90%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="RelNo" style="width:90%" data-oper="LIKE"/></td>

                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">????????????</td>
                            <td><input class="mini-textbox" name="ClientName" style="width:90%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">?????????</td>
                            <td><input class="mini-treeselect" name="MasterTechMan" style="width:90%" data-oper="EQ" url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID" allowInput="true"
                                       valueFromSelect="true" popupWidth="300"/>
                            </td>
                            <td style="width:80px;text-align: center;">?????????</td>
                            <td><input  name="AuditMan" style="width:90%" data-oper="EQ"
                                       class="mini-treeselect"  style="width:90%" data-oper="EQ" url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID" allowInput="true"
                                       valueFromSelect="true" popupWidth="300"/></td>
                            <td style="width:80px;text-align: center;">???????????????</td>
                            <td><input class="mini-treeselect" name="TechMan" style="width:90%" data-oper="EQ"
                                       url="/systems/dep/getAllLoginUsersByDep"
                                       textField="Name" valueField="FID" parentField="PID" allowInput="true"
                                       valueFromSelect="true" popupWidth="300"/></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" style="width:120px"
                                   href="javascript:doHightSearch();">??????</a>
                                <a class="mini-button mini-button-danger" style="margin-left:30px;width:120px"
                                   onclick="expand">??????</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;overflow: hidden"
                     onrowdblclick="editClient" onselectionchanged="onRowChange"
                     allowresize="true" url="/casesOutSource/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50]" sortfield="CreateTime" sortorder="desc"
                     autoload="true" onDrawCell="onDraw" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div type="comboboxcolumn" field="State" width="80" headerAlign="center" align="center"
                             allowSort="true">????????????
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="ProcessText" width="80" headerAlign="center" align="center">????????????</div>
                        <div field="SubNo" headerAlign="center" align="center" width="120" allowSort="true">????????????</div>
                        <div field="Memo" headerAlign="center" align="center">??????</div>
                        <div field="ClientName" headerAlign="center" width="200" align="center" allowSort="true">????????????
                        </div>
                        <div field="YName" headerAlign="center" align="center" width="120">????????????</div>
                        <div field="RelNo" headerAlign="center" align="center">????????????</div>
                        <div field="ShenqingName" width="200" headerAlign="center" align="center" allowSort="true">
                            ??????????????????
                        </div>
                        <div field="ClientRequiredDate" width="150" headerAlign="center" align="center" allowSort="true"
                             dataType="date" dateFormat="yyyy-MM-dd">????????????????????????
                        </div>
                        <div field="AuditTechFiles" headerAlign="center" width="120" align="center">??????????????????</div>
                        <div field="TechFiles" headerAlign="center" align="center" width="120">??????????????????</div>
                        <div field="OutTechFiles" headerAlign="center" width="120" align="center">????????????</div>
                        <div field="OutTechFileUploadTime" headerAlign="center" width="120" align="center"
                             dataType="date" dateFormat="yyyy-MM-dd">??????????????????
                        </div>
                        <div field="TechMan" headerAlign="center" width="120" align="center" type="treeselectcolumn">
                            ???????????????
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="TechTime" headerAlign="center" width="120" align="center" dataType="date"
                             dateformat="yyyy-MM-dd">????????????
                        </div>
                        <div field="AuditText" headerAlign="center" width="120" align="center">????????????</div>
                        <div field="MasterTechMan" headerAlign="center" width="120" align="center"
                             type="treeselectcolumn">?????????
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="OutTechTime" headerAlign="center" width="120" align="center" dataType="date"
                             dateFormat="yyyy-MM-dd">????????????
                        </div>
                        <div field="AuditMan" headerAlign="center" width="120" align="center" type="treeselectcolumn">
                            ?????????
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" headerAlign="center" width="120" align="center" dataType="date"
                             dateFormat="yyyy-MM-dd">????????????
                        </div>
                    </div>
                </div>
                <input class="mini-hidden" name="LoginUserID" id="LoginUserID" value="${UserID}"/>
                <input class="mini-hidden" name="RoleName" id="RoleName" value="${RoleName}"/>
                <input class="mini-hidden" name="RoleID" id="RoleID" value="${RoleID}"/>
            </div>
        </div>
    </div>
    <div class="mini-window" title="?????????????????????????????????" width="600" height="220" style="display:none" id="AuditWindow">
        <table style="width:100%" class="layui-table" id="AuditForm">
            <tr>
                <td style="width:150px">????????????</td>
                <td>
                    <textarea class="mini-textarea" style="height:80px;width:100%" name="AuditMemo"></textarea>
                    <input class="mini-hidden" id="CSID"/>
                </td>
            </tr>
            <tr>
                <td>????????????</td>
                <td>
                    <input style="width:100%" name="AuditResult" class="mini-combobox"
                           data="[{id:'1',text:'??????????????????'},{id:'0', text:'?????????????????????'}]" readonly="true"/>
                </td>
            </tr>
            <tr id="TrAudFile">
                <td>????????????????????????</td>
                <td style="text-align:center">
                    <buttn class="mini-button mini-button-info" id="UploadAudFile" onclick="uploadAuditFile()
    " style="width:100%">???????????????
                    </buttn>
                </td>
            </tr>
            <tr>
                <td style="width:150px">&nbsp;</td>
                <td style="text-align:left">
                    <button class="mini-button mini-button-success" style=";margin-top:10px" onclick="auditOutTechFiles();
">????????????
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:180px;margin-top:10px"
                            onclick="closeWindow('AuditWindow')">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="???????????????" width="600" height="160" style="display:none" id="ChangeTechManWindow">
        <table style="width:100%" class="layui-table" id="ChangeTechManForm">
            <tr>
                <td>????????????:</td>
                <td>
                    <div id="OldMan" class="mini-treeselect" name="OldMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true"
                         expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false" enabled="false"></div>
                    <input class="mini-hidden" name="SubID"/>
                </td>
            </tr>
            <tr>
                <td>?????????:</td>
                <td>
                    <div id="NewMan" class="mini-treeselect" name="NewMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true"
                         expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false"
                         allowInput="true"></div>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <br/>
                    <button class="mini-button mini-button-success" name="CmdChangeTechMan" onclick="changeTechMan()">
                        ????????????
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px"
                            onclick="closeWindow('ChangeTechManWindow')">????????????
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        mini.parse();
        var tip = new mini.ToolTip();
        var grid = mini.get('#datagrid1');
        var cmdAcceptTech = mini.get('#CasesOutSource_AcceptOutTech');
        var cmdRejectAccept = mini.get('#CasesOutSource_RejectOutTech');
        var cmdUploadFile = mini.get('#CasesOutSource_UploadOutTechFile');
        var cmdPass = mini.get("#CasesOutSource_PassOutTech");
        var cmdRollback = mini.get("#CasesOutSource_RollbackOutTech");
        var cmdChange = mini.get("#CasesOutSource_ChangeJS");
        var userId = ${UserID};

        var cmdA1 = mini.get('a1');
        var cmdA2 = mini.get('a2');
        var cmdA3 = mini.get('a3');
        var txtQuery = mini.get('queryText');
        var fit = mini.get('#fitt');

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setText("??????");
                btn.setIconCls("icon-collapse");
                $('#p1').css('display', "block");
                //cmdA1.show();
                // cmdA2.show();
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setText("????????????");
                btn.setIconCls("icon-expand");
                $('#p1').css('display', "none");
                // cmdA1.hide();
                // cmdA2.hide();
                cmdA3.show();
                txtQuery.show();
            }
            fit.setHeight('100%');
            fit.doLayout();
            grid.setHeight('100%');
            grid.doLayout();
        }

        function afterload() {
            initButtons();
            updateNumber();

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
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["SubID"] == code) return true;
                        });
                        if (row) {
                            var memo = row["ProcessMemo"];
                            if (memo) {
                                tip.setContent('<table style="width:400px;height:auto;table-layout:fixed;' +
                                    'word-wrap:break-word;word-break:break-all;text-align:left;vertical-align:  ' +
                                    'text-top; "> <tr><td>' + memo + '</td></tr></table>');
                            }
                        }
                    }
                }
            });
        }

        function refreshData(grid) {
            if (!grid) grid = mini.get('#datagrid1');
            if (grid) {
                var pa = grid.getLoadParams();
                var pageIndex = grid.getPageIndex() || 0;
                var pageSize = grid.getPageSize() || 20;
                pa = pa || {pageIndex: pageIndex, pageSize: pageSize};
                grid.load(pa);
            }
        }

        function updateNumber() {
            var url = "/casesOutSource/getStateNumber";
            $.getJSON(url, {}, function (result) {
                var rows = result.data || {};
                for (var state in rows) {
                    var num = parseInt(rows[state]);
                    var con = $('.x' + state);
                    if (con.length > 0) {
                        con.text(num);
                    }
                }
            })
        }

        function uploadAuditFile() {
            var row = grid.getSelected();
            if (row) {
                uploadRow(row._id, 'Aud', 'Add');
            }
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            var mode = "Browse";
            var now = record[field];
            var uText = "??????";
            var type = "Tech";
            var state = parseInt(record["State"] || 0);
            var isOk = false;

            if (field == 'TechFiles') {
                isOk = true;
                var techFileName = record["TechFileName"];
                if (techFileName) uText = techFileName;
            } else if (field == "OutTechFiles") {
                type = "Out";
                var outTechFileName = record["OutTechFileName"];
                if (outTechFileName) uText = outTechFileName;
                isOk = true;
            } else if (field == "AuditTechFiles") {
                type = "Aud";
                var audTechFileName = record["AuditTechFileName"];
                if (audTechFileName) uText = audTechFileName;
                else {
                    if (state == 4) {
                        uText = "??????";
                        now = "1";
                    }

                }
                if (state == 4) mode = "Add";
                isOk = true;
            }
            if (isOk == true) {
                if (!now) isOk = false;
            }
            if (isOk) {
                var x = '<a href="javascript:void(0)"  style="color:blue;text-decoration:underline" hCode="' + record["SubID"] + '"' +
                    ' onclick="uploadRow1(' + "'" + record._id + "','" + type + "'," + "'" + mode + "')" + '">' +
                    '&nbsp;' + uText + '&nbsp;</a>';
                e.cellHtml = x;
            }
            if (field == "ProcessText") {
                var dd = record[field] || "";
                var x = '<a href="javascript:void(0)"  data-placement="bottomleft"  style="color:blue;text-decoration:underline" ' +
                    'hcode="' + record["SubID"] + '"' +
                    ' class="showCellTooltip" onclick="showMemo(' + "'" + record._id + "'" + ')">' + (dd ? "??????" : "??????") + '</a>';
                e.cellHtml = x;
            }
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
            if(curState){
                var o={field:'State',oper:'EQ',value:curState};
                result.push(o);
            }
            arg["High"] = mini.encode(result);
            grid.load(arg);
        }

        function showMemo(ID) {
            window.parent.doResize();
            var record = grid.getRowByUID(ID);
            var subId = record["OutID"];
            var rows=grid.getSelecteds();
            if(rows.length>1){
                var c=[];
                for(var i=0;i<rows.length;i++){
                    c.push(rows[i]["Out"]);
                }
                subId=c.join(',');
            }
            mini.open({
                url: '/addSingleMemo/index?ID=' + subId,
                width: 1000,
                height: 600,
                showModal: true,
                title: '??????????????????',
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var saveUrl = '/casesOutSource/saveMemo?SubID=' + subId;
                    iframe.contentWindow.setConfig(saveUrl);
                    iframe.contentWindow.setSaveImageUrl('/addSingleMemo/saveImage');
                },
                onDestroy: function () {
                    grid.reload();
                }
            });
        }

        function doUpload(outId, subId, ids, type, row, mode) {
            var title = '??????????????????';
            if (type == "Out") title = "??????????????????";
            if (type == 'Aud') title = '??????????????????????????????';
            var showHis = 0;
            if (type == "Tech" || type == "Out" || type == 'Aud') showHis = 1;
            mini.open({
                url: '/attachment/addFile?IDS=' + ids + '&Mode=' + mode + '&ShowHis=' + showHis,
                width: 800,
                height: 400,
                title: title,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.addEvent('eachFileUploaded', function (data) {
                        var attId = data.AttID;
                        var url = '/casesOutSource/saveSubFiles';
                        var arg = {OutID: outId, SubID: subId, AttID: attId, Type: type};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                var field = "ZLFiles";
                                if (type == "Tech") field = "TechFiles";
                                if (type == "Accept") field = "AcceptTechFiles";
                                if (type == "Aud") field = "AuditTechFiles";
                                var obj = {};
                                obj[field] = attId;
                                var now = row[field];
                                if (!now) grid.updateRow(row, obj);
                            } else {
                                mini.alert('??????????????????????????????????????????????????????360???????????????????????????');
                            }
                        })
                    });
                    iframe.contentWindow.addEvent('eachFileRemoved', function (row) {
                        var attId = row["ATTID"];
                        if (attId) {
                            var url = '/casesOutSource/removeSubFiles';
                            $.post(url, {AttID: attId}, function (result) {
                                if (result.success == false) {
                                    mini.alert('????????????????????????????????????????????????????????????');
                                } else {
                                    grid.reload();
                                }
                            })
                        }
                    });
                    if (type == "Out" && mode != 'Browse') {
                        iframe.contentWindow.enableCommitEx('????????????', function (xmini, xwindow) {
                            xmini.confirm('??????????????????????????????????????????', '????????????', function (action) {
                                if (action == 'ok') {
                                    var url = '/casesOutSource/commitTech';
                                    $.post(url, {SubID: subId}, function (result) {
                                        if (result.success) {
                                            xmini.alert('??????????????????????????????!', '????????????', function () {
                                                xwindow.CloseOwnerWindow();
                                                grid.reload();
                                            });
                                        } else {
                                            xmini.alert('??????????????????????????????????????????!' || result.message);
                                        }
                                    })
                                }
                            });
                        });
                    }
                },
                ondestroy: function () {
                    var win = mini.get('#AuditWindow');
                    var visible=win.getVisible();
                    if(visible==false)grid.reload();
                }
            });
        }
        var curState=null;
        function changeQuery(state, obj) {
            var con = $(event.srcElement || e.targetElement);
            var cons = $('.Jdlcli');
            for (var i = 0; i < cons.length; i++) {
                var cx = cons[i];
                if (cx.className == "Jdlcli Arrival" || cx.className == "Jdlcli Arrival clicked") {
                    cx.children[0].children[0].style.cssText = "color:rgb(0, 159, 205);";
                    cx.children[0].children[1].style.cssText = "color:rgb(0, 159, 205);";
                }
                cx.classList.remove('clicked');
            }
            $(con).parents('.Jdlcli').addClass('clicked');
            obj.children[0].style.cssText = "color:#fff";
            obj.children[1].style.cssText = "color:#fff";
            window.parent.doResize();
            curState=parseInt(state || 0);
            if(curState==0)curState=null;
            grid.load({State: state});
        }

        function initButtons() {
            cmdAcceptTech.hide();
            cmdRejectAccept.hide();
            cmdUploadFile.hide();
            cmdPass.hide();
            cmdRollback.hide();
            cmdChange.hide();
            //cmdA1.hide();
            //cmdA2.hide();
        }

        function getPublicValue(rows, field) {
            var firstValue = null;
            if (rows.length > 0) {
                firstValue = rows[0][field];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var val = row[field];
                    if (val != firstValue) {
                        return null;
                    }
                }
            }
            if (firstValue == undefined) firstValue = null;
            return firstValue;
        }

        function onRowChange() {
            var rows = grid.getSelecteds();
            if (rows.length == 0) {
                initButtons();
                return;
            }
            var state = parseInt(rows[0]["State"]);
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var xState = parseInt(row["State"] || 0);
                if (xState != state) {
                    initButtons();
                    return;
                }
            }
            initButtons();
            //?????????
            if (state == 1) {
                var techMan = getPublicValue(rows, 'TechMan');
                if (techMan == null) cmdAcceptTech.show();
                else {
                    techMan = parseInt(techMan || 0);
                    if (techMan != userId) {
                        cmdAcceptTech.hide();
                    } else {
                        cmdAcceptTech.show();
                    }
                }
            } else if (state == 2) {
                //????????????
                cmdRejectAccept.show();
                cmdUploadFile.show();
            } else if (state == 3) {
                //?????????
                cmdPass.show();
                cmdRollback.show();
                cmdChange.show();
            } else if (state == 4) {
                cmdUploadFile.show();
            } else if (state == 6) {
                cmdUploadFile.show();
            }
        }

        function acceptTech() {
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["SubID"]);
            }

            function g() {
                var url = '/casesOutSource/acceptTech';
                var arg = {SubIDS: subs.join(',')};
                $.post(url, arg, function (result) {
                    if (result.success) {
                        mini.alert('???????????????????????????!', '????????????', function () {
                            grid.reload();
                        });
                    }
                })
            }

            if (subs.length > 0) {
                mini.confirm('????????????????????????????????????', '????????????', function (act) {
                    if (act == 'ok') g();
                });
            } else {
                mini.alert('?????????????????????????????????!');
            }
        }

        function rejectTech() {
            var rows = grid.getSelecteds();
            var subs = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                subs.push(row["SubID"]);
            }

            function g() {
                var url = '/casesOutSource/rejectTech';
                var arg = {SubIDS: subs.join(',')};
                $.post(url, arg, function (result) {
                    if (result.success) {
                        mini.alert('???????????????????????????!', '????????????', function () {
                            grid.reload();
                        });
                    }
                })
            }

            if (subs.length > 0) {
                mini.confirm('????????????????????????????????????', '????????????', function (act) {
                    if (act == 'ok') g();
                });
            } else {
                mini.alert('?????????????????????????????????!');
            }
        }

        function uploadTechFiles() {
            var row = grid.getSelected();
            if (row) {
                uploadRow(row._id, 'Out', 'Add');
            } else mini.alert('???????????????????????????????????????!');
        }

        function uploadRow(id, type, mode) {
            var row = grid.getRowByUID(id);
            if (row) {
                var subId = row["SubID"];
                var outId = row["OutID"];
                var url = '/casesOutSource/getSubFiles';
                $.getJSON(url, {SubID: subId, Type: type}, function (result) {
                    if (result.success) {
                        var att = result.data || [];
                        doUpload(outId, subId, att, type, row, mode);
                    }
                });
            }
        }

        function uploadRow1(id, type, mode) {
            window.parent.doResize();
            var row = grid.getRowByUID(id);
            if (row) {
                var subId = row["SubID"];
                var outId = row["OutID"];
                var url = '/casesOutSource/getSubFiles';
                $.getJSON(url, {SubID: subId, Type: type}, function (result) {
                    if (result.success) {
                        var att = result.data || [];
                        doUpload(outId, subId, att, type, row, mode);
                    }
                });
            }
        }

        function showAudit(result) {
            var win = mini.get('#AuditWindow');
            var form = new mini.Form('#AuditForm');
            form.reset();
            mini.getbyName('AuditResult').setValue(result);
            mini.getbyName('AuditResult').setValue(result);
            if (result == 1) {
                mini.get('#UploadAudFile').hide();
                $('#TrAudFile').hide();
                win.setHeight(220);
            } else {
                mini.get('#UploadAudFile').show();
                $('#TrAudFile').show();
                win.setHeight(250);
            }
            var rows = grid.getSelecteds();
            var cs=[];
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                var id=row._id;
                cs.push(id);
            }
            mini.get('#CSID').setValue(cs.join(','));
            win.show();
        }

        function auditOutTechFiles() {
            var win = mini.get('#AuditWindow');
            var form = new mini.Form('#AuditForm');
            var data = form.getData();

            var rows = grid.getSelecteds();
            var result = parseInt(data.AuditResult || 0);
            if (rows.length > 0) {
                var cs = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var subId = row["SubID"];
                    var outTechFiles = row["OutTechFiles"];
                    if (result == 1) {
                        if (outTechFiles) {
                            cs.push(subId);
                        }
                    } else cs.push(subId);
                }
                if (cs.length > 0) {
                    function g() {
                        var url = '/casesOutSource/auditTech';
                        var arg = {SubIDS: cs.join(','), Text: data.AuditMemo, Result: result};
                        $.post(url, arg, function (result) {
                            if (result.success) {
                                var tt = '???????????????????????????';
                                if (result == 1) tt += ",??????????????????????????????????????????????????????????????????";
                                mini.alert(tt, '????????????', function () {
                                    win.hide();
                                    grid.reload();
                                });
                            } else mini.alert(result.message || "??????????????????????????????!");
                        });
                    }

                    var ttext = "?????????????????????????????????????";
                    if (data.AuditResult == 0) ttext = "??????????????????????????????????";
                    mini.confirm(ttext, '????????????', function (act) {
                        if (act == 'ok') g();
                    });
                } else mini.alert('??????????????????????????????????????????!');
            } else mini.alert('??????????????????????????????!');
        }

        function doExport() {
            var excel = new excelData(grid);
            excel.addEvent('updateConfig', function (columns) {
                var findIndex = null;
                for (var i = 0; i < columns.length; i++) {
                    var column = columns[i];
                    var field = column["field"];
                    if (!field) continue;

                    if (field == 'TechFiles') {
                        column.field = "TechFileName";
                    } else if (field == "OutTechFiles") {
                        column.field = "OutTechFileName";
                    } else if (field == "AuditTechFiles") {
                        column.field = "AuditTechFileName";
                    } else if (field == "ProcessText") {
                        findIndex = i;
                    }
                }
                if (findIndex != null) columns.removeAt(findIndex);
            });
            excel.export("??????????????????.xls");
        }

        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            doHightSearch();
        }

        function doQuery() {
            //??????/?????????/????????????/??????/????????????
            var txt = txtQuery.getValue();
            var cs = [];
            var arg = {};
            if (txt) {
                var fields = ['Memo', "SubNo", "ShenqingName", "ClientName", "YName", "TechManName", "AuditManName", "MasterTechManName"];
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {field: field, oper: 'LIKE', value: txt};
                    cs.push(obj);
                }
            }
            if (cs.length > 0) {
                if(curState){
                    var o={field:'State',oper:'EQ',value:curState};
                    var bs=[o];
                    arg["High"]=mini.encode(bs);
                }
                arg["Query"] = mini.encode(cs);
            }
            grid.load(arg);
        }

        function closeWindow(name) {
            var win = mini.get(name);
            if (win) win.hide();
        }

        var changeForm = new mini.Form('#ChangeTechManForm');
        var changeWin = mini.get('#ChangeTechManWindow');

        function changeJS() {
            var rows = grid.getSelecteds();
            if (rows.length>0) {
                var cs=[];
                for(var i=0;i<rows.length;i++){
                    var row=rows[i];
                    var subId = row["SubID"];
                    cs.push(subId);
                }
                var masterTechMan = parseInt(rows[0]["MasterTechMan"] || 0);
                changeWin.show();
                changeForm.reset();
                var data = {OldMan: masterTechMan, SubID: cs.join(','), NewMan: null};
                changeForm.setData(data);
            } else mini.alert('???????????????????????????!');
        }

        function changeTechMan() {
            changeForm.validate();
            if (changeForm.isValid()) {
                function g() {
                    var data = changeForm.getData();
                    var url = '/casesOutSource/changeJS';
                    $.post(url, data, function (result) {
                        if (result.success) {
                            mini.alert('????????????????????????????????????!', '????????????', function () {
                                changeWin.hide();
                                grid.reload();
                            });
                        } else mini.alert(result.message || "??????????????????????????????!");
                    })
                }

                mini.confirm('??????????????????????????????', '????????????', function (act) {
                    if (act == 'ok') g();
                });
            } else mini.alert('???????????????????????????!');
        }
    </script>
</@layout>
