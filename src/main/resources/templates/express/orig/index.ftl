<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript">
        mini.parse();
        var types = [
            {id:0,text:'发明专利'}, {id:1,text:'新型专利'}, {id:2,text:'外观专利'}
        ];
        var states = [
            {id:0,text:'待分类'},{id:1,text:'待自取'}, {id:2,text:'已自取'},{id:3,text:'待寄送'}, {id:4,text:'已寄送'}
        ];
        var yjzt=[ { id: '0', text: '待分类' }, { id: '1', text: '待自取' }, { id: '2', text: '已自取' }];
        var loadCoding="";
        $(function () {
            $.post('/express/orig/getCoding' ,{}, function (text) {
                    var res = mini.decode(text);
                    if (res.success) {
                        var data=res.data || {};
                        if (data.length>0) {
                            for (var i = 0; i < data.length; i++) {
                                loadCoding = loadCoding + data[i] + ",";
                            }
                            loadCoding = loadCoding.substring(0, loadCoding.length - 1);
                            mini.get('Coding').setValue(loadCoding);
                        }
                    } else {
                        mini.alert(res.Message);
                    }
                }
            );
        })
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
        .clicked{
            background-color: rgba(241, 112, 46, 0.84);
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
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center" showheader="false">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-12">
                                <div id="info1" style="height:55px;background:rgb(226,250,252);border-radius: 5px;border:1px solid rgb(190,226,240);">
                                    <div class="info1top" style="padding-left: 120px;">
                                        <h3 class="sqf" style="color: rgb(3,154,209);margin-left:-90px;margin-top:17px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jk.png">&nbsp;原件管理监控
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli orig" id="J1" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',0,this,'no')">
                                                    <span id="J1span">全部</span>
                                                    <h4 class="x0">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J2" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',0,this,'yes')">
                                                    <span id="J2span">待分类</span>
                                                    <h4 class="x1">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J3" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',1,this,'yes')">
                                                    <span id="J3span">待自取</span>
                                                    <h4 class="x2">1</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J4" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',2,this,'yes')">
                                                    <span id="J4span">已自取</span>
                                                    <h4 class="x3">2</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J5" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',3,this,'yes')">
                                                    <span id="J5span">待寄送</span>
                                                    <h4 class="x4">3</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli orig" id="J6" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',4,this,'yes')">
                                                    <span id="J6span">已寄送</span>
                                                    <h4 class="x5">4</h4>
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
                            <a class="mini-button mini-button-success" id="Original_Register" onclick="Register">证书通知书登记</a>
<#--                            <a class="mini-button mini-button-danger" id="Original_RegisterShouju" onclick="RegisterShouju">票据登记</a>-->
<#--                            <a class="mini-button mini-button-success" id="Original_PlaceFile" onclick="PlaceFile">原件归档</a>-->
                            <a class="mini-button mini-button-danger" id="Original_PickUp" onclick="PickUp">自取</a>
                            <a class="mini-button mini-button-info" id="Original_AddExpressWindow" onclick="AddExpressWindow">邮寄</a>
                            <a class="mini-button" iconcls="icon-remove" id="Original_Remove" onclick="remove">删除</a>
<#--                            <a class="mini-button mini-button-info" id="Original_AddExpressByExpressNo" onclick="AddExpressByExpressNo">添加到包裹编号</a>-->
<#--                            <a class="mini-button mini-button-info" id="Original_EditOriginal" onclick="EditOriginal">修改原件状态</a>-->
                            <a class="mini-button mini-button-info" id="Original_Export" onclick="doExport">导出Excel</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <a class="mini-button mini-button-danger" id="Original_HighQuery" onclick="expand">高级搜索</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">登记时间：</td>
                            <td style="width:13%;">
                                <input name="CreateTime" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="CreateTime" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd"  style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">原件状态：</td>
                            <td style="width:15%;">
                                <input class="mini-combobox" data="yjzt" name="ostateText" data-oper="EQ" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:25px;position: relative;" title="发文序号/收据号/证书号">二维编码：<span class="mini-button-icon mini-iconfont icon-help "></span></td>
                            <td style="width:15%;">
                                <input name="dnum" class="mini-textbox" data-oper="LIKE" style="width:100%" />
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">登记人：</td>
                            <td style="width:15%;">
                                <input name="CreateEmp" class="mini-textbox" data-oper="Like" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">自取编号：</td>
                            <td style="width:15%;">
                                <input name="DrawNo" class="mini-textbox" data-oper="LIKE" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">专利名称：</td>
                            <td style="width:15%;">
                                <input name="FAMINGMC" class="mini-textbox" data-oper="LIKE" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">专利申请号：</td>
                            <td style="width:15%;">
                                <input name="SHENQINGH" class="mini-textbox" data-oper="LIKE" style="width:100%" />
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">专利申请人：</td>
                            <td style="width:15%;">
                                <input name="SHENQINGRXM" class="mini-textbox" data-oper="LIKE" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">专利发明人：</td>
                            <td style="width:15%;">
                                <input name="FAMINGRXM" class="mini-textbox" data-oper="LIKE" style="width:100%" />
                            </td>
                            <td style="width:6%;padding-left:10px;">法律状态：</td>
                            <td style="width:15%;">
                                <input class="mini-combobox" id="ANJIANYWZT" name="ANJIANYWZT" style="width: 100%"  required="true" url="/systems/dict/getAllByDtId?dtId=12"/>
                            </td>
                            <td style="width:6%;padding-left:25px;position: relative;" title="归属客户/销售维护人/代理责任人/流程责任人">
                                内部编号：<span class="mini-button-icon mini-iconfont icon-help "></span>
                            </td>
                            <td style="width:15%;">
                                <input name="NEIBUBH" class="mini-textbox" data-oper="LIKE" style="width:100%" />
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="8">
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
                    <div id="datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                         frozenstartcolumn="0" frozenendcolumn="8" sortorder="desc" sortfield="CreateTime"
                         allowresize="true" url="/express/orig/getData" multiselect="true"
                         pagesize="20"
                         sizelist="[5,10,20,50,100]" autoload="true" ondrawcell="onRenderLX" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="Action" width="60" headerAlign="center">备注信息</div>
                        <div field="otypeText" width="80" headeralign="center" align="center" allowsort="true">
                            原件类型
                        </div>
                        <div field="TONGZHISMC" width="120" headeralign="center" allowsort="true">
                            通知书名称
                        </div>
                        <div field="ostateText"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">
                            原件状态
                            <input property="editor" class="mini-combobox" data="states" />
                        </div>
                        <div field="SHENQINGRXM" width="150" headeralign="center" allowsort="true">
                            专利申请人
                        </div>
                        <div field="SHENQINGH" name="SHENQINGH" width="120" headeralign="center" renderer="onZhanlihao" allowsort="true">
                            专利申请号
                        </div>
                        <div field="FAMINGMC" name="FAMINGMC" width="200" headeralign="center" allowsort="true">
                            专利名称
                        </div>
                        <div field="SHENQINGLX"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types" />
                        </div>
                        <div field="ANJIANYWZT" width="110" headeralign="center" allowsort="true">
                            专利法律状态
                        </div>
                        <div field="FAMINGRXM" width="130" headeralign="center" allowsort="true">
                            专利发明人
                        </div>
                        <div width="240" headeralign="center" field="NEIBUBH" allowsort="true">内部编号</div>
                        <div width="120" headeralign="center" field="KH">归属客户</div>
                        <div width="100" headeralign="center" field="YW">销售维护人</div>
                        <div width="100" headeralign="center" field="JS">代理责任人</div>
                        <div width="100" headeralign="center" field="LC">流程责任人</div>
                        <div field="CreateTime" width="120" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">登记时间</div>
                        <div field="CreateEmp" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                            登记人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="CreateEmp" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="dnum" width="160" headeralign="center" align="right" allowsort="true" title="发文序号/收据号/证书号">
                            二维编码
                        </div>
                        <div field="DrawNo" width="120" headeralign="center" allowsort="true">
                            自取编号
                        </div>
                    </div>
                </div>
                <div id="editWindow" class="mini-window" title="扫码登记" showmodal="true"
                     allowresize="true" allowdrag="true" style="width:720px;">
                    <div id="editform" class="form">
                        <table style="width:100%;">
                            <tr>
                                <td style="width:80px;">文件条码：</td>
                                <td style="width:550px;"><input name="barcode" class="mini-textbox" style="width:100%" /></td>
                                <td style="text-align:right;padding-right:20px;" >
                                    <a class="Cancel_Button" href="javascript:cancelRow();">关闭</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div id="editWindow2" class="mini-window" title="添加快递" showmodal="true"
                     allowresize="true" allowdrag="true" style="width:800px;">
                    <div id="editform2" class="form">
                        <input class="mini-hidden" name="ids" />
                        <table style="width:100%;">
                            <tr>
                                <td style="width:80px;">包裹内容：</td>
                                <td style="width:150px;"><input name="Contents" id="Contents2" class="mini-textbox" readonly="readonly" /></td>
                                <td style="width:80px;">包裹状态：</td>
                                <td style="width:150px;"><input name="estate" class="mini-combobox" textfield="text" valuefield="id" value="1" data='[ { "id": "1", "text": "未寄送" },    { "id": "2", "text": "已寄送" }]' required="true" requirederrortext="包裹状态不能为空"></td>
                                <td style="width:80px;">接收人：</td>
                                <td style="width:150px;"><input name="LinkMan" textname="LinkMan" class="mini-buttonedit" onbuttonclick="onCustomLinkerDialog" required="true" requirederrortext="接收人不能为空" /></td>
                            </tr>
                            <tr>
                                <td style="width:80px;">邮编：</td>
                                <td style="width:150px;"><input name="PostCode" class="mini-textbox" /></td>
                                <td style="width:80px;">接收人地址：</td>
                                <td style="" colspan="3"><input name="Address" class="mini-textbox" style="width:418px" required="true" requirederrortext="接收人地址不能为空" /></td>
                            </tr>
                            <tr>
                                <td style="width:80px;">快递公司：</td>
                                <td style="width:150px;"><input name="Company" class="mini-textbox" /></td>
                                <td style="width:80px;">快递编号：</td>
                                <td style="" colspan="3"><input name="Eno" class="mini-textbox" style="width:418px" /></td>
                            </tr>
                            <tr>
                                <td style="width:80px;">联系电话：</td>
                                <td style="width:150px;"><input name="Mobile" class="mini-textbox" required="true" requirederrortext="联系电话不能为空" /></td>
                                <td style="width:80px;">备注：</td>
                                <td style="" colspan="3"><input name="remark" class="mini-textbox" style="width:418px" /></td>
                            </tr>
                            <tr>
                                <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="6">
                                    <a class="Update_Button" href="javascript:AddExpress();">添加</a>
                                    <a class="Cancel_Button" href="javascript:cancelRow2();">取消</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div id="editWindow3" class="mini-window" title="修改状态" showmodal="true"
                     allowresize="true" allowdrag="true" style="width:800px;">
                    <div id="editform3" class="form">
                        <input class="mini-hidden" name="id" />
                        <input name="otype" class="mini-hidden" />
                        <table style="width:100%;">
                            <tr>
                                <td style="width:80px;">原件类型：</td>
                                <td style="width:150px;"><input name="otypeText" class="mini-textbox" readonly="readonly" /></td>
                                <td style="width:80px;">申请号：</td>
                                <td style="width:150px;"><input name="shenqingh" class="mini-textbox" readonly="readonly" /></td>
                                <td style="width:80px;">条码：</td>
                                <td style="width:150px;"><input name="dnum" class="mini-textbox" readonly="readonly" /></td>
                            </tr>
                            <tr>
                                <td style="width:80px;">原件状态：</td>
                                <td style="width:150px;"><input name="ostate" id="ostate3" class="mini-combobox" required="true" requirederrortext="原件状态不能为空" data='[ { "id": "1", "text": "待分类" },    { "id": "2", "text": "自取" },    { "id": "3", "text": "邮寄" },    { "id": "4", "text": "归档" },    { "id": "5", "text": "已领取" }]' /></td>
                                <td style="width:80px;">归档号：</td>
                                <td style="width:150px;"><input name="archivno" class="mini-textbox" /></td>
                                <td style="width:80px;">自取标记：</td>
                                <td style="width:150px;"><input name="DrawNo" class="mini-textbox" /></td>
                            </tr>
                            <tr>
                                <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="6">
                                    <a class="Update_Button" href="javascript:SaveOriginal();">修改</a>
                                    <a class="Cancel_Button" href="javascript:cancelRow3();">取消</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <input class="mini-hidden" id="Coding" name="Coding" />
    <form method="post" action="/express/orig/exportExcel" style="display:none" id="exportExcelForm">
        <input type="hidden" name="Data" id="exportExcelData"/>
    </form>
<#--    <script type="text/javascript" src="/js/Fee/FeeCommon.js"></script>-->
    <script type="text/javascript" src="/js/Original/Original.js"></script>
<#--    <script type="text/javascript" src="/js/common/HighSearch.js"></script>-->
    <script type="text/javascript">
        mini.parse();
        var fit=mini.get('fitt');
        var grids=mini.get('#datagrid');
        function doHightSearch(){
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
            grids.load(arg);
        }

        function expand(e) {
            e=e||{};
            var btn = e.sender;
            if(!btn){
                btn=mini.get('#Original_showHighSearchForm');
            }
            var display = $('#p1').css('display');
            if (display == "none") {
                btn.setIconCls("panel-collapse");
                btn.setText("高级查询");
                $('#p1').css('display', "block");

            } else {
                btn.setIconCls("panel-expand");
                btn.setText("高级查询");
                $('#p1').css('display', "none");
            }
            fit.setHeight('100%');
            fit.doLayout();
        }

        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                if (rows[i]["ostateText"]!="0"){
                    mini.alert("不能删除原件状态为非待分类的原件！");
                    return;
                }else {
                    ids.push(rows[i]["ID"]);
                }
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('确认要删除的原件信息数据?', '删除提示', function (act) {
                if (act === 'ok') {
                    var url='/express/orig/remove';
                    $.ajax({
                        contentType:'application/json',
                        method:'post',
                        url:url,
                        data:mini.encode(ids),
                        success:function (r) {
                            if (r['success']) {
                                mini.alert("删除成功！",'删除提示',function () {
                                    grid.reload();
                                });

                            } else mini.alert("删除失败！");
                        },
                        failure:function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function onRenderLX(e) {
            var field = e.field;
            if (field == "Action") {
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
    </script>
</@layout>
