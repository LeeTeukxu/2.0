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
            {id: 1, text: '未提交'},
            {id: 2, text: '待审核'},
            {id: 3, text: '转他人处理'},
            {id: 4, text: '审核驳回'},
            {id: 5, text: '待审批'},
            {id: 6, text: '审批完成'}
        ];
        var types = [{id: 1, text: '建议', pid: 0}, {id: 2, text: '投诉', pid: 0}];
    </script>
    <div class="mini-layout" style="width:100%;height:100%;overflow: hidden">
        <div region="center" showheader="false" bodyStyle="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-3">
                                <div id="info0" style="height:80px;background-color: rgb(63,87,131);overflow: hidden;border-radius:3px;
                            -moz-box-shadow: 2px 2px 10px rgb(63,87,131);-webkit-box-shadow: 2px 2px 10px rgb(63,87,131);-shadow:2px 2px 10px rgb(63,87,131);">
                                    <div class="file-list">
                                        <div class="Zlywtop">
                                            <ul>
                                                <li class="Jdlcli Arrival" id="Z1" style="width:300px;text-align:
                                                center">
                                                    <a style="text-decoration:none" target="_self"
                                                       href="javascript:void(0)"
                                                       onclick="changeQuery(0,this)">
                                                        <span id="J1span">投诉、建议&nbsp;</span>&nbsp;
                                                        <h4 class="x9">0</h4>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="mini-col-4">
                                <div id="info1"
                                     style="height:80px;background:rgb(226,250,252);border:1px solid rgb(190,226,240);border-radius: 5px;">
                                    <div class="info1top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(214,239,243);text-align: center;">
                                        <h2 style="color: rgb(3,154,209);margin-left: 5px;margin-top: 4px;">申请</h2>
                                    </div>
                                    <div class="info1bottom" style="width: 100%;height: 55px">
                                        <ul>
                                            <li class="Jdlcli Arrival" id="J1" style="width:100px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(1,this);">
                                                    <span id="J1span">未提交</span>
                                                    <h4 class="x1">0</h4>
                                                </a>
                                            </li>
                                            <li class="ho"></li>
                                            <li class="Jdlcli Arrival" id="J2" style="width:100px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(2,this);">
                                                    <span id="J2span">已提交</span>
                                                    <h4 class="x2">0</h4>
                                                </a>
                                            </li>
                                            <li class="ho"></li>
                                            <li class="Jdlcli Arrival" id="J2" style="width:100px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   onclick="changeQuery(4,this);">
                                                    <span id="J2span">已驳回</span>
                                                    <h4 class="x4">0</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="mini-col-5">
                                <div id="info2"
                                     style="height:80px;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                                    <div class="info2top"
                                         style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                                        <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 4px;">处理</h2>
                                    </div>
                                    <div class="info2bottom" style="width: 100%;height: 55px;text-align: center">
                                        <ul style="">
                                            <li class="Jdlcli Arrival" id="Js1" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self"
                                                   onclick="changeQuery(2,this)">
                                                    <span id="Js1span">待处理</span>
                                                    <h4 class="x2">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js2" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   class="active_a"
                                                   onclick="changeQuery(3,this)">
                                                    <span id="Js2span">转交他人处理</span>
                                                    <h4 class="x3">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli Arrival" id="Js3" style="width:120px;text-align: center">
                                                <a style="text-decoration:none" target="_self" href="javascript:void(0)"
                                                   class="active_a"
                                                   onclick="changeQuery(5,this)">
                                                    <span id="Js2span">已处理</span>
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
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconCls="icon-add" onclick="add()" id="Expense_Add">新增</a>
                            <a class="mini-button" iconCls="icon-edit" onclick="edit()" id="Expense_Edit">编辑</a>
                            <a class="mini-button" iconCls="icon-remove" onclick="remove()"
                               id="Expense_Remove">删除</a>
                            <a class="mini-button" iconCls="icon-print" onclick="editTypes()"
                               id="Expense_Config">分类维护</a>
                            <a class="mini-button" iconcls="icon-user" onclick="changeMan()"
                               id="Expense_ChangeMan">更换处理人</a>
                            <a class="mini-button" iconCls="icon-ok" onclick="audit()" id="Expense_Audit">审核</a>
                            <a class="mini-button" iconCls="icon-ok" onclick="set()" id="Expense_Set">审批</a>
                            <a class="mini-button" iconCls="icon-zoomin" onclick="browse()"
                               id="Expense_Browse">查看</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <input class="mini-textbox Expense_Query" style="width:250px"
                                   emptyText="业务编号/业务分类/发起人/问题描述" id="queryText"/>
                            <a class="mini-button mini-button-success Expense_Query" id="a3"
                               onclick="doQuery">模糊查询</a>
                            <a class="mini-button mini-button-danger Expense_Reset" id="a2"
                               onclick="reset">重置条件</a>
                            <a class="mini-button mini-button-info" iconCls="icon-expand" onclick="expand">高级查询</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:160px;padding:5px;display:none">
                    <table style="width:100%;height:100%;padding:0px;margin:0px" id="highQueryForm">
                        <tr>
                            <td style="width:80px;text-align: center;">业务编号</td>
                            <td><input class="mini-textbox" name="DocSN" style="width:90%" data-oper="LIKE"/></td>
                            <td style="width:80px;text-align: center;">发起日期</td>
                            <td><input class="mini-datepicker" name="CreateTime" style="width:90%"
                                       data-oper="GE" dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">到</td>
                            <td><input class="mini-datepicker" name="CreateTime" style="width:90%"
                                       data-oper="LE" dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">问题描述</td>
                            <td><input class="mini-textbox" name="Memo" style="width:90%" data-oper="LIKE"/></td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">事项类型</td>
                            <td><input class="mini-combobox" data="types" style="width:90%" name="Type"
                                       data-oper="EQ"/>
                            </td>
                            <td style="width:80px;text-align: center;">业务分类</td>
                            <td><input class="mini-combobox" style="width:90%" name="Title"
                                       url="/suggest/getByType?Type=1" data-oper="EQ"/>
                            </td>
                            <td style="width:80px;text-align: center;">投诉人</td>
                            <td><input name="SuggestMan" style="width:90%" data-oper="EQ"
                                       class="mini-treeselect" style="width:90%" data-oper="EQ"
                                       url="/systems/dep/getAllLoginUsersByDep" expandOnload="true"
                                       textField="Name" valueField="FID" parentField="PID" allowInput="true"
                                       valueFromSelect="true" popupWidth="300"/></td>
                            <td style="width:80px;text-align: center;">转交人</td>
                            <td><input class="mini-treeselect" name="ChangeMan" style="width:90%" data-oper="EQ"
                                       url="/systems/dep/getAllLoginUsersByDep" expandOnload="true"
                                       textField="Name" valueField="FID" parentField="PID" allowInput="true"
                                       valueFromSelect="true" popupWidth="300"/></td>
                        </tr>
                        <tr>
                            <td style="width:80px;text-align: center;">发起人</td>
                            <td><input class="mini-treeselect" name="CreateMan" style="width:90%" data-oper="EQ"
                                       url="/systems/dep/getAllLoginUsersByDep" expandOnload="true"
                                       textField="Name" valueField="FID" parentField="PID" allowInput="true"
                                       valueFromSelect="true" popupWidth="300"/>
                            </td>
                            <td style="width:80px;text-align: center;">审核人</td>
                            <td><input class="mini-treeselect" name="AuditMan" style="width:90%" data-oper="EQ"
                                       url="/systems/dep/getAllLoginUsersByDep" expandOnload="true"
                                       textField="Name" valueField="FID" parentField="PID" allowInput="true"
                                       valueFromSelect="true" popupWidth="300"/></td>
                            <td style="width:80px;text-align: center;">审核日期</td>
                            <td><input class="mini-datepicker" name="AuditTime" style="width:90%"
                                       data-oper="GE" dateFormat="yyyy-MM-dd"/></td>
                            <td style="width:80px;text-align: center;">到</td>
                            <td><input class="mini-datepicker" name="AuditTime" style="width:90%"
                                       data-oper="LE" dateFormat="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:5px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" style="width:120px"
                                   href="javascript:doHightSearch();">搜索</a>
                                <a class="mini-button mini-button-danger" style="margin-left:30px;width:120px"
                                   onclick="expand">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;overflow: hidden"
                     onrowdblclick="editClient" onselectionchanged="onRowChange"
                     allowresize="true" url="/expense/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50]" sortfield="CreateTime" sortorder="desc"
                     autoload="true" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div type="comboboxcolumn" field="State" width="80" headerAlign="center" align="center"
                             allowSort="true">业务状态
                            <input property="editor" class="mini-combobox" data="states"/>
                        </div>
                        <div field="DocSN" headerAlign="center" align="center" width="120" allowSort="true">业务编号</div>
                        <div field="Memo" headerAlign="center" width="200" align="center">报销事由</div>
                        <div field="DepID" headerAlign="center" width="120" align="center" type="treeselectcolumn">所属部门
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllCanUse"
                                   valueField="depId" parentField="pid" textField="name"/>
                        </div>
                        <div field="CreateTime" headerAlign="center" width="120" align="center" dataType="date"
                             dateFormat="yyyy-MM-dd">报销时间
                        </div>
                        <div field="CreateMan" headerAlign="center" width="120" align="center" type="treeselectcolumn">
                            报销人
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditText" headerAlign="center" width="200" align="center">审核意见</div>
                        <div field="AuditMan" headerAlign="center" width="120" align="center"
                             type="treeselectcolumn">审核人
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="AuditTime" headerAlign="center" width="120" align="center" dataType="date"
                             dateFormat="yyyy-MM-dd">处理时间
                        </div>
                        <div field="SetText" headerAlign="center" width="200" align="center">审批意见</div>
                        <div field="SetMan" headerAlign="center" width="120" align="center" type="treeselectcolumn">审批人
                            <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID"/>
                        </div>
                        <div field="SetTime" headerAlign="center" width="120" align="center" dataType="date"
                             dateFormat="yyyy-MM-dd">审批时间
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" title="更换处理人" width="600" height="240" style="display:none" id="ChangeManWindow">
        <table style="width:100%" class="layui-table" id="ChangeManForm">
            <tr>
                <td>原处理人:</td>
                <td>
                    <div name="OldMan" class="mini-treeselect" name="OldMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true"
                         expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false" enabled="false"></div>
                    <input class="mini-hidden" name="ID"/>
                </td>
            </tr>
            <tr>
                <td>更换为:</td>
                <td>
                    <div name="NewMan" class="mini-treeselect" name="NewMan" style="width:100%"
                         url="/systems/dep/getAllLoginUsersByDep" multiSelect="false" required="true"
                         expandOnload="true"
                         textField="Name" valueField="FID" parentField="PID" resultAsTree="false"
                         allowInput="true"></div>
                </td>
            </tr>
            <tr>
                <td>说明</td>
                <td>
                    <textarea class="mini-textarea" style="width:100%;height:70px" name="Text"
                              required="true"></textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center">
                    <br/>
                    <button class="mini-button mini-button-success" name="CmdChangeTechMan" onclick="changeManAction()">
                        确认操作
                    </button>
                    <button class="mini-button mini-button-danger" style="margin-left:100px"
                            onclick="closeWindow('ChangeManWindow')">取消关闭
                    </button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" title="报销项目维护" style="width:800px;height:500px" id="typeWindow">
        <div class="mini-toolbar">
            <a id="cmdAddType" class="mini-button" iconCls="icon-add" onclick="addType()">新增分类</a>
            <a id="cmdAddItem" class="mini-button" iconCls="icon-addnew" onclick="addItem()">新增项目</a>
            <span class="separator"></span>
            <a id="cmdSaveType" class="mini-button" iconCls="icon-save" onclick="saveType()">保存</a>
            <a id="cmdRemoveType" class="mini-button" iconCls="icon-remove" onclick="removeType()">删除</a>
        </div>
        <div class="mini-fit">
            <div class="mini-treegrid" style="width:100%;height:100%" id="itemGrid" url="/expense/getItems"
                 expandonload="true" resultAsTree="false"
                 treeColumn="sn" parentField="pid" idField="fid" allowCellEdit="true" allowCellSelect="true">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="sn" name="sn" align="left" headerAlign="left" width="120">编号
                        <input property="editor" class="mini-spinner" maxValue="9999999999"/>
                    </div>
                    <div field="name" align="center" headerAlign="center" width="400">费用名称
                        <input property="editor" class="mini-textbox"/>
                    </div>
                    <div field="money" align="center" headerAlign="center">年度预算
                        <input property="editor" class="mini-spinner" maxValue="9999999999"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <input class="mini-hidden" name="LoginUserID" id="LoginUserID" value="${UserID}" visible="false"/>
    <input class="mini-hidden" name="RoleName" id="RoleName" value="${RoleName}" visible="false"/>
    <input class="mini-hidden" name="RoleID" id="RoleID" value="${RoleID}" visible="false"/>
    <script type="text/javascript">
        mini.parse();
        var tip = new mini.ToolTip();
        var grid = mini.get('#datagrid1');
        var typeGrid = mini.get('#itemGrid');
        var leftTree = mini.get('#leftTree');
        var cmdAdd = mini.get('#Expense_Add');
        var cmdEdit = mini.get('#Expense_Edit');
        var cmdRemove = mini.get('#Expense_Remove');
        var cmdConfig = mini.get('#Expense_Config')
        var cmdChange = mini.get("#Expense_ChangeMan");
        var cmdAudit = mini.get('#Expense_Audit');
        var cmdSet=mini.get('#Expense_Set');
        var cmdAddType = mini.get('#cmdAddType');
        var cmdSaveType = mini.get('#cmdSaveType');
        var cmdRemoveType = mini.get('#cmdRemoveType');
        var userId = parseInt(${UserID} || 0);

        var cmdA1 = mini.get('a1');
        var cmdA2 = mini.get('a2');
        var cmdA3 = mini.get('a3');
        var txtQuery = mini.get('queryText');
        var fit = mini.get('#fitt');
        var curType = null;

        function add() {
            mini.open({
                url: '/expense/add?mode=Add',
                width: '60%',
                height: '100%',
                title: '新增费用报销',
                ondestroy: function (act) {
                    grid.reload();
                }
            });
        }

        function edit() {
            var row = grid.getSelected();
            if (row) {
                var id = parseInt(row.ID);
                mini.open({
                    url: '/expense/edit?mode=Edit&ID=' + id,
                    width: '60%',
                    height: '100%',
                    title: '编辑费用报销',
                    ondestroy: function (act) {
                        grid.reload();
                    }
                });
            }
        }

        function browse() {
            var row = grid.getSelected();
            if (row) {
                var id = parseInt(row.ID);
                mini.open({
                    url: '/expense/browse?ID=' + id,
                    width: '60%',
                    height: '100%',
                    title: '查看费用报销',
                    ondestroy: function (act) {
                        grid.reload();
                    }
                });
            }
        }

        function audit() {
            var row = grid.getSelected();
            if (row) {
                var id = parseInt(row.ID);
                mini.open({
                    url: '/expense/audit?ID=' + id,
                    width: '60%',
                    height: '100%',
                    title: '审核费用报销',
                    ondestroy: function (act) {
                        grid.reload();
                    }
                });
            }
        }

        function set() {
            var row = grid.getSelected();
            if (row) {
                var id = parseInt(row.ID);
                mini.open({
                    url: '/expense/set?ID=' + id,
                    width: '60%',
                    height: '100%',
                    title: '审批费用报销',
                    ondestroy: function (act) {
                        grid.reload();
                    }
                });
            }
        }

        function changeMan() {
            var win = mini.get('ChangeManWindow');
            win.show();
            var form = new mini.Form('#ChangeManForm');
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                var auditMan = parseInt(rows[0].AuditMan);
                var ids = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var id = row.ID;
                    ids.push(id);
                }
                var obj = {
                    ID: ids.join(','),
                    OldMan: auditMan,
                    NewMan: null
                };
                form.setData(obj);
            }
        }

        function changeManAction() {
            var form = new mini.Form('#ChangeManForm');
            form.validate();
            if (form.isValid()) {
                var win = mini.get('ChangeManWindow');
                var data = form.getData();

                function g() {
                    var url = '/suggest/changeMan';
                    $.post(url, data, function (result) {
                        if (result.success) {
                            mini.alert('操作成功!', '系统提示', function () {
                                win.hide();
                                grid.reload();
                            });
                        } else mini.alert(result.message || "操作失败，请稍候重试!");
                    })
                }

                mini.confirm('确认要转交其他人处理吗？', '系统提示', function (act) {
                    if (act == 'ok') g();
                });
            } else mini.alert('数据录入不完整，无法进行操作!');
        }

        function addType() {
            typeGrid.addNode({pid: 0});
        }

        function addItem() {
            var changes = typeGrid.getChanges();
            if (changes.length == 0) {
                var parent = typeGrid.getSelectedNode();
                if (parent) {
                    typeGrid.addNodes([{pid: parent.fid}], parent);
                }
            }
        }

        function editTypes() {
            var win = mini.get('typeWindow');
            win.show();
            typeGrid.reload();
        }

        function saveType() {
            var data = typeGrid.getChanges();
            var url = '/expense/saveItems';
            $.post(url, {Data: mini.encode(data)}, function (result) {
                if (result.success) {
                    mini.alert('保存成功!', '系统提示', function () {
                        typeGrid.reload();
                    });
                } else mini.alert('保存成功!' || result.message);
            })
        }

        function expand(e) {
            var btn = e.sender;
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setText("隐藏");
                btn.setIconCls("icon-collapse");
                $('#p1').css('display', "block");
                cmdA3.hide();
                txtQuery.hide();
            } else {
                btn.setText("高级查询");
                btn.setIconCls("icon-expand");
                $('#p1').css('display', "none");
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
            var url = "/expense/getStateNumber";
            $.getJSON(url, {}, function (result) {
                var rows = result.data || {};
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var state = row.name;
                    var num = parseInt(row.num || 0);
                    var con = $('.' + state);
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
                var o = {field: 'State', oper: 'EQ', value: curState};
                result.push(o);
            }
            arg["High"] = mini.encode(result);
            grid.load(arg);
        }

        function showMemo(ID) {
            window.parent.doResize();
            var record = grid.getRowByUID(ID);
            var subId = record["OutID"];
            var rows = grid.getSelecteds();
            if (rows.length > 1) {
                var c = [];
                for (var i = 0; i < rows.length; i++) {
                    c.push(rows[i]["Out"]);
                }
                subId = c.join(',');
            }
            mini.open({
                url: '/addSingleMemo/index?ID=' + subId,
                width: 1000,
                height: 600,
                showModal: true,
                title: '添加进度信息',
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

        var curState = null;

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
            curState = parseInt(state || 0);
            if (curState == 0) curState = null;
            grid.load({State: state});
        }

        function initButtons() {
            cmdEdit.hide();
            cmdAudit.hide();
            cmdChange.hide();
            cmdRemove.hide();
            cmdSet.hide();
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
            var row = rows[0];
            var mans = [];
            var createMan = parseInt(row["CreateMan"]);
            mans.push(createMan);
            var auditMans = (row["AllocAuditMan"] || "0").split(",");
            var ads = [];
            for (var i = 0; i < auditMans.length; i++) {
                var m = parseInt(auditMans[i]);
                if (m > 0) {
                    mans.push(m);
                    ads.push(m);
                }
            }
            var changeMan = parseInt(row["ChangeMan"] || "0");
            if (changeMan > 0) mans.push();
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
            //待提交
            if (state == 1) {
                if (userId == createMan) {
                    cmdRemove.show();
                    cmdEdit.show();
                }
            } else if (state == 2) {
                if (ads.indexOf(userId) > -1) {
                    cmdAudit.show();
                    cmdChange.show();
                }
            } else if (state == 3) {
                if (userId == changeMan) cmdAudit.show();
            } else if (state == 4) {
                if (userId == createMan) {
                    cmdEdit.show();
                    cmdRemove.show();
                }
            }
            else if(state==5){
                cmdSet.show();
            }
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
            excel.export("外协接单记录.xls");
        }

        function remove() {
            var rows = grid.getSelecteds();
            if (rows.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }

            function g() {
                var ids = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var id = row.ID;
                    ids.push(id);
                }
                var url = '/suggest/removeAll';
                $.post(url, {IDS: ids.join(",")}, function (result) {
                    if (result.success) {
                        mini.alert('选择的记录删除成功!', '删除提示', function () {
                            grid.reload();
                        });
                    } else mini.alert(result.message || "删除失败，请稍候重试!");
                })
            }

            mini.confirm('确认要删除选择的记录吗？', '删除提示', function (result) {
                if (result == 'ok') g();
            });
        }

        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            doHightSearch();
        }

        function doQuery() {
            //备注/流水号/业备数量/客户/商务人员
            var txt = txtQuery.getValue();
            var cs = [];
            var arg = {};
            if (txt) {
                var fields = ['Memo', "DocSN", "TitleName", "TypeName", "CreateManName"];
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {field: field, oper: 'LIKE', value: txt};
                    cs.push(obj);
                }
            }
            if (cs.length > 0) {
                if (curState) {
                    var o = {field: 'State', oper: 'EQ', value: curState};
                    var bs = [o];
                    arg["High"] = mini.encode(bs);
                }
                arg["Query"] = mini.encode(cs);
            }
            grid.load(arg);
        }

        function closeWindow(name) {
            var win = mini.get(name);
            if (win) win.hide();
        }
    </script>
</@layout>
