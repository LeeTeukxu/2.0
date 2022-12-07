<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js?v=${version}"></script>
    <script type="text/javascript" src="/js/common/complexExcelExport.js?v=${version}"></script>
    <script type="text/javascript">
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
        //var types = [{id: '发明专利', text: '发明专利'}, {id: '实用新型', text: '实用新型'}, {id: '外观设计', text: '外观设计'}];
        var YearWatch = [
            { id: 0, text: '监控中' },
            { id: 3, text: '放弃监控' },
            { id: 4, text: '待缴费' },
            { id: 5, text: '完成缴费' }
        ];
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
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="north" height="32" showheader="false" showsplit="false" bodystyle="padding-top:5px"
             splitsize="1px">
        <span>
            <span style="margin-left:20px;border-radius: 50%;height:15px;width:15px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">已过缴费期限</span>
        </span>
            <span>
            <span style="border-radius: 50%;height:15px;width:15px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">
                距离缴费期限0-30天
            </span>
        </span>
            <span>
            <span style="border-radius: 50%;height:15px;width:15px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">
                距离缴费期限31-90天
            </span>
        </span>
            <span>
            <span style="border-radius: 50%;height:15px;width:15px;background-color:yellow">&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <span style="margin-right:50px">距离缴费期限超过91天</span>
        </span>
        </div>
        <div region="center" style="overflow:hidden" bodyStyle="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-12">
                                <div id="info1"
                                     style="height:55px;background:rgb(226,250,252);border-radius: 5px;border:1px solid rgb(190,226,240);">
                                    <div class="info1top" style="padding-left: 120px;">
                                        <h3 class="sqf"
                                            style="color: rgb(3,154,209);margin-left:-90px;margin-top:17px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jk.png">年费监控
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli YearSource" id="J1">
                                                <a style="text-decoration:none" target="_self" href="#"
                                                   onclick="changeQuery('Index',0,this)">
                                                    <span id="J1span">全部</span>
                                                    <h4 class="x1"></h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli YearSource clicked" id="J2">
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('JKStatus',0,this)">
                                                    <span id="J2span">监控中</span>
                                                    <h4 class="x2"></h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli YearSource" id="J3" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('JKStatus',4,this)">
                                                    <span id="J2span">待缴费</span>
                                                    <h4 class="x3"></h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli YearSource" id="J3" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('JKStatus',5,this)">
                                                    <span id="J2span">完成缴费</span>
                                                    <h4 class="x4"></h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli AppWatchlySource" id="J4" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('JKStatus',3,this)">
                                                    <span id="J2span">放弃监控</span>
                                                    <h4 class="x5"></h4>
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
                            <a class="mini-button mini-button-danger" id="FeeItemYear_NoPayMark"
                               onclick="changePayStatus('0')">标记不缴费</a>
                            <a class="mini-button mini-button-success" id="FeeItemYear_PayMark" onclick="changePayStatus
                            ('1')">标记需缴费</a>
                            <a class="mini-button mini-button-info" id="FeeItemYear_AddToFeeList"
                               onclick="addToPayForWait">添加到缴费清单</a>
                            <a class="mini-button mini-button-info" id="FeeItemYear_Export"
                               onclick="doExport">导出Excel</a>
                            <a class="mini-button mini-button-info" id="FeeItemYear_ExportFee"
                               onclick="doExportYear">导出代缴费清单</a>
                            <a class="mini-button mini-button-primary" id="FeeItemYear_Jiaodui"
                               onclick="Jiaodui">校对金额</a>
                            <a class="mini-button mini-button-primary" id="FeeItemYear_SXF" onclick="addSXMoney">手续费</a>
                            <span class="separator FeeItemYear_Query"></span>
                            <button class="mini-button FeeItemYear_Query" iconCls="icon-reload"
                                    onclick="doFixedQuery('JIAOFEIR', 'LT', '${now}')"
                                    plain="true">已过期
                            </button>
                            <button class="mini-button FeeItemYear_Query" iconCls="icon-reload"
                                    onclick="doFixedQuery('JIAOFEIR', 'GT', '${next}')" plain="true">未过期
                            </button>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field FeeItemYear_Query" id="comField" style="width:100px"
                                 data="[{id:'All',text:'全部属性'},{id:'ShenQingh',text:'专利号'},{id:'FeeName',
                                 text:'缴费清单'},{id:'FAMINGMC',text:'发明名称'},{id:'SHENQINGRXM',text:'申请人姓名'},{id:'KH',
                                 text:'所属客户'},{id:'XS',text:'销售人员'},{id:'DL',text:'代理师'},{id:'LC',text:'流程人员'},{id:'CustomMemo',text:'手动标识'}]" value="All"
                                 id="Field"></div>
                            <input class="mini-textbox Query_Field FeeItemYear_Query" style="width:150px"
                                   id="QueryText"/>
                            <a class="mini-button mini-button-success" onclick="doQuery();" id="FeeItemYear_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger" id="FeeItemYear_Reset" onclick="reset">重置</a>
                            <a class="mini-button" id="FeeItemYear_HighQuery" iconCls="panel-expand"
                               onclick="expand">高级查询</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">缴纳期限：</td>
                            <td style="width:15%;"><input name="JiaoFeiR" class="mini-datepicker" data-oper="GE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;"><input name="JiaoFeiR" class="mini-datepicker" data-oper="LE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">费用名称</td>
                            <td style="width:15%;"><input name="FeeName" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                            /></td>
                            <td style="width:6%;padding-left:10px;">金额：</td>
                            <td style="width:15%;"><input name="Money" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">专利状态</td>
                            <td style="width:15%;"><input name="ANJIANYWZT" class="mini-textbox" data-oper="LIKE" style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">申请日：</td>
                            <td style="width:15%;"><input name="SHENQINGR" class="mini-datepicker" data-oper="GE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;"><input name="SHENQINGR" class="mini-datepicker" data-oper="LE"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">发明人：</td>
                            <td style="width:15%;"><input name="FAMINGRXM" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;">专利申请号：</td>
                            <td style="width:15%;"><input name="SHENQINGH" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                            /></td>
                            <td style="width:6%;padding-left:10px;">专利名称：</td>
                            <td style="width:15%;"><input name="FAMINGMC" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"
                            /></td>
                            <td style="width:6%;padding-left:10px;">专利类型：</td>
                            <td style="width:15%;"><input name="SHENQINGLX" class="mini-combobox" data="types"
                                                          data-oper="EQ" style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">专利申请人：</td>
                            <td style="width:15%;"><input name="SHENQINGRXM" class="mini-textbox" data-oper="LIKE"
                                                          style="width:100%"/></td>
                        </tr>
                        <tr>
                            <td style="text-align:center;padding-top:10px;padding-right:20px;" colspan="8">
                                <a class="mini-button mini-button-success" onclick="doHightSearch" style="width:120px">搜索</a>
                                <a class="mini-button mini-button-danger" onclick="expand"
                                   style="margin-left:30px;width:120px">收起</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="FeeItemYear_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" onrowclick="onRowClick"
                     frozenstartcolumn="0" frozenendcolumn="8" sortorder="asc" sortfield="JIAOFEIR"
                     ondrawcell="onDraw" allowresize="true" url="/watch/yearList/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50,100,200,500,1000,1500,2000]" autoload="false"
                     onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div width="40" field="status" align="center"></div>
                        <div width="66" field="DE" align="center" allowSort="true" headerAlign="center"></div>
                        <div field="JKStatus" name="JKStatus"  width="70" headerAlign="center" type="comboboxcolumn"
                             allowsort="true" align="center">监控状态
                            <div property="editor" class="mini-combobox" data="YearWatch"></div>
                        </div>
                        <div field="Action" width="60" headerAlign="center" align="center">备注信息</div>
                        <div field="JIAOFEIR" name="JIAOFEIR" width="120" allowsort="true"
                             headeralign="center" align="center" dataType="date" dateFormat="yyyy-MM-dd">
                            缴费截止日
                        </div>
                        <div field="FEENAME" name="FEENAME" width="160" allowsort="true" headeralign="center">
                            费用项目
                        </div>
                        <div field="MONEY" name="MONEY" width="90" allowsort="true"
                             headeralign="center" align="right" dataType="float">
                            金额
                        </div>
                        <div field="XMONEY" name="XMONEY" width="90" allowsort="true" headeralign="center"
                             align="right" dataType="float">
                            人工校对金额
                        </div>
                        <div field="SXMONEY" name="SXMONEY" width="90" allowsort="true" headeralign="center"
                             align="right" dataType="float">
                            手续费
                        </div>
                        <div field="NeedPayFor" width="80" allowsort="true" headeralign="center" align="center"
                             renderer="onStateRenderer">
                            是否需缴费
                        </div>

                        <div field="SHENQINGRXM" width="200" allowsort="true" headeralign="center">
                            专利申请人
                        </div>
                        <div field="SHENQINGH" name="shenqingh" width="120" allowsort="true" headeralign="center"
                             renderer="onZhanlihao">
                            专利申请号
                        </div>
                        <div field="SHENQINGR" name="shenqingr" width="80" allowsort="true" headeralign="center"
                             datatype="date" dateformat="yyyy-MM-dd">
                            申请日
                        </div>
                        <div field="FAMINGMC" name="FAMINGMC" width="200" allowsort="true" headeralign="center">
                            专利名称
                        </div>
                        <div field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="ANJIANYWZT" width="210" allowsort="true" headeralign="center">
                            专利法律状态
                        </div>
                        <div field="FAMINGRXM" width="130" allowsort="true" headeralign="center">
                            专利发明人
                        </div>
                        <#if RoleName!='客户'  && RoleName!="外协代理师">
                        <div width="240" allowsort="true" headeralign="center" field="NEIBUBH">内部编号</div>
                        <div field="BH" width="120"  headerAlign="center" align="center" allowsort="true">立案编号</div>
                            <#if RoleName?index_of("技术")==-1>
                                <div width="120" headeralign="center" field="KH">归属客户</div>
                            </#if>
                        <div width="100" headeralign="center" field="YW">销售维护人</div>
                        <div width="100" headeralign="center" field="DL">代理师</div>
                        <div width="100" headeralign="center" field="LC">流程责任人</div>
                        <div field="JkStatus" visible="false"></div>
                        </#if>
                        <div width="80" headeralign="center" field="CustomMemo">手动标识</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="/js/work/feeItem/FeeCommon.js"></script>
<script type="text/javascript">
    mini.parse();
    var fit = mini.get('fitt');
    var tip = new mini.ToolTip();
    var grid = mini.get('FeeItemYear_datagrid');
    var txtQuery = mini.get('#QueryText');
    var comField = mini.get('#comField');
    var NoPayMark = mini.get('FeeItemYear_NoPayMark');
    var ParyMark = mini.get('FeeItemYear_PayMark');
    var ToPayForWait = mini.get('FeeItemYear_AddToFeeList');
    $(function () {
        $('#p1').hide();
        fit.setHeight('100%');
        fit.doLayout();
        var con2=$('#J2');
        if(con2.length>0){
            doQuery('JKStatus',0,con2[0]);
        }
    });

    function expand(e) {
        var btn = e.sender;
        var display = $('#p1').css('display');
        if (display == "none") {
            btn.setIconCls("icon-collapse");
            btn.setText("隐藏");
            $('#p1').css('display', "block");
            txtQuery.hide();
            cmdQuery.hide();
            comField.hide();

        } else {
            btn.setIconCls("icon-expand");
            btn.setText("高级查询");
            $('#p1').css('display', "none");
            txtQuery.show();
            cmdQuery.show();
            comField.show();
        }
        fit.setHeight('100%');
        fit.doLayout();
    }
    var curCode=null;
    var curState=null;
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
                    var obj = {field: field.getName(), value: field.getValue(), oper: field.attributes["data-oper"]};
                    result.push(obj);
                }
            }
        }
        var ds=addPreCondition(curCode,curState);
        for(var i=0;i<ds.length;i++){
            result.push(ds[i]);
        }
        arg["High"] = mini.encode(result);
        grid.load(arg);
    }
    function doExport() {
        var excel = new excelData(grid);
        excel.export("专利年费监控列表.xls");
    }

    function doExportYear() {
        var excel = new complexExcelData(grid);
        excel.setSheetName("Sheet1");
        excel.export('OneYear', '专利年费代缴费清单.xls');
    }

    function addToPayForWait() {
        var rows = grid.getSelecteds();
        if (rows.length == 0) {
            mini.alert('请选择要添加到缴费清单位的记录。');
            return;
        }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            var id = parseInt(row["id"]);
            if (id) ids.push(id);
        }
        mini.open({
            url: '/watch/addToPayForWait/index?IDS=' + ids.join(',') + '&Mode=Add&Type=Year',
            width: 800,
            height: 410,
            showModal: true,
            title: '添加到缴费清单',
            ondestroy: function (action) {
                if (action == 'ok') grid.reload();
            }
        });
    }

    function onDraw(e) {
        var field = e.field;
        var record = e.record;
        if (field == "status") {
            var now = mini.parseDate('${now}');
            var value = record['JIAOFEIR'];
            if(typeof(value)=="string")value=mini.parseDate(value);
            var totalDays=parseInt((value.getTime()-now.getTime())/(1000*60*60*24));
            if (totalDays<0) {
                e.cellHtml = '<span style="border-radius: 50%;height:10px;width:10px;background-color:black">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            else if (totalDays>=0 && totalDays<=30) {
                e.cellHtml = '<span style="border-radius: 50%;height:10px;width:10px;background-color:red">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            else if (totalDays>30 && totalDays<90) {
                e.cellHtml = '<span style="border-radius: 50%;height:10px;width:10px;background-color:orange">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
            else {
                e.cellHtml = '<span style="border-radius: 50%;height:10px;width:10px;background-color:yellow">&nbsp;&nbsp;&nbsp;&nbsp;</span>';
            }
        } else if (field == "Action") {
            var record = e.record;
            var memo = record["MEMO"];
            var editMemo = parseInt(record["EDITMEMO"] || 0);
            var text = ((memo == null || memo == "") ? "<span style='color:green'>添加</span>" : "<span style='color:blue'>修改</span>");
            if (editMemo == 0) {
                if (memo) text = "<span style='color:gay'>查看</span>";
            }
            e.cellHtml = '<a href="#"  data-placement="bottomleft"  hCode="' + record["id"] + '" ' +
                    'class="showCellTooltip" onclick="ShowMemo(' + "'" + record["id"] + "'," + "'"
                    +record["FAMINGMC"]+"-"+
                    record["FEENAME"] + "'" + ')">' + text + '</a>';
        }
        else if (field == "KH") {
            var clientId = record["KHID"];
            var val = e.value;
            if (clientId) {
                e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
            } else e.cellHtml = val;
        }
        else if (field == "JKStatus"){
            var record = e.record;
            //var YearWatch = record["YearWatch"];
            var JkStatus = record["JkStatus"];
            var NeedPayFor = record["NeedPayFor"];
                if (JkStatus == "4") {
                    e.cellHtml = '<span style="color: orange">待缴费</span>';
                } else if (JkStatus == "5") {
                    e.cellHtml = '<span style="color: blue">完成缴费</span>';
                }
            else if (NeedPayFor =="0") {
                e.cellHtml='<span style="color: black">放弃监控</span>';
            }
            else if (JkStatus == "0"){
                e.cellHtml='<span style="color: red">监控中</span>';
            }
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
        else if(field=="DE"){
            var iid=e.record._id;
            e.cellHtml = '<a href="#" onclick="detail(' + "'" +iid+ "'" + ')">详情..</a>';
        }
    }

    function checkNumber(theObj) {
        var reg = /^[0-9]+.?[0-9]*$/;
        if (reg.test(theObj)) {
            return true;
        }
        return false;
    }

    function Jiaodui() {
        var rows = grid.getSelecteds();
        if (rows.length == 0) {
            mini.alert('请选择修改的费用项目!');
            return;
        }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            ids.push(row.id);
        }

        mini.prompt("人工校对金额：", "请输入",
                function (action, value) {
                    if (action == "ok") {
                        if (!checkNumber(value)) {
                            alert("请输入正确格式的金额!");
                            return;
                        }
                        var url = '/watch/yearList/changeJiaoDuiMoney';
                        $.post(url, {IDS: mini.encode(ids), Money: value}, function (r) {
                            if (r['success']) {
                                mini.alert('人工校对金额修改成功!', '提示', function () {
                                    grid.reload();
                                });
                            }
                            else mini.alert(r['message'] || "修改失败!");
                        });
                    }
                }
        );
    }

    function addSXMoney() {
        var rows = grid.getSelecteds();
        if (rows.length == 0) {
            mini.alert('请选择修改的费用项目!');
            return;
        }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            ids.push(row.id);
        }

        mini.prompt("代缴手续费金额:", "请输入",
                function (action, value) {
                    if (action == "ok") {
                        if (!checkNumber(value)) {
                            alert("请输入正确格式的金额!");
                            return;
                        }
                        var url = '/watch/yearList/changeShouXiuMoney';
                        $.post(url, {IDS: mini.encode(ids), Money: value}, function (r) {
                            if (r['success']) {
                                mini.alert('代缴手续费金额修改成功!', '提示', function () {
                                    grid.reload();
                                });
                            }
                            else mini.alert(r['message'] || "修改失败!");
                        });
                    }
                }
        );
    }

    function changePayStatus(rr) {
        var rows = grid.getSelecteds();
        if (rows.length == 0) {
            mini.alert('请选择修改的费用项目!');
            return;
        }
        var ids = [];
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            ids.push(row.id);
        }
        var msg="确认要将选择的<"+rows.length+"条>记录"+(rr=='0'?'标记为【不缴费】？':'标记为【缴费】?');
        mini.confirm(msg, '设置提示', function (action) {
            if (action == 'ok') {
                var url = '/watch/yearList/changePayStatus';
                $.post(url, {IDS: mini.encode(ids), Status: rr}, function (r) {
                    if (r['success']) {
                        mini.alert('缴费状态修改成功!', '提示', function () {
                            grid.reload();
                        });
                    }
                    else mini.alert(r['message'] || "修改失败!");
                });
            }
        });
    }

    function onStateRenderer(e) {
        var value = e.value;
        if (value == '1') return '需缴费';
        if (value == '0') return '不需缴费';
        return "";
    }

    function exportZL() {
        var rows = grid.getSelecteds();
        var obj = ZLObject.parse(rows);

    }

    function reset() {
        var form = new mini.Form('#highQueryForm');
        form.reset();
        mini.get('#QueryText').setValue(null);
        comField.setValue('All');
        doQuery();
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
                    var hCode = $(el).attr('hCode');
                    if (hCode) {
                        if(!grid)grid=mini.get('FeeItem_datagrid');
                        var rows = grid.getData();
                        var row = grid.findRow(function (row) {
                            if (row["id"] == hCode) return true;
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
        updateNumber();
        onClickDisable();
    }
    function updateNumber() {
        var url="/watch/yearList/refreshTotal";
        $.getJSON(url,{},function (result) {
            var rows=result.data || [];
            for (var i=0;i<rows.length;i++){
                var row=rows[i];
                var cName=row["name"];
                var num = parseInt(row["num"] || 0);
                var con=$('.'+cName);
                if (con.length > 0){
                    con.text(num);
                }
            }
        })
    }
    function ShowMemo(id, title) {
        var cs=[];
        var rows=grid.getSelecteds();
        if(rows.length>1){
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                var feeId=row["id"];
                cs.push(feeId);
            }
            id=cs.join(',');
            title="多个缴费项目批量";
        }
        mini.open({
            url: '/watch/feeWatch/addMemo?Type=Year&ID=' + id,
            showModal: true,
            width: 1000,
            height: 600,
            title: "【" + title + "】的备注信息",
            onDestroy: function () {
                grid.reload();
                grid.deselectAll(false);
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
    function doHightSearch(){
        var arg = {};
        var form=new mini.Form('#highQueryForm');
        var fields=form.getFields();
        var result=[];
        for(var i=0;i<fields.length;i++){
            var field=fields[i];
            var val=field.getValue();
            if(val!=null && val!=undefined)
            {
                if(val!='')
                {
                    var obj={field:field.getName(),value:field.getValue(),oper:field.attributes["data-oper"]};
                    result.push(obj);
                }
            }
        }
        arg["High"]=mini.encode(result);
        grid.load(arg);
    }

    function onRowClick() {
        onClickDisable();
    }
    function onClickDisable() {
        var rows=grid.getSelecteds();
        var off=[];
        var offs=[];
        for (var i=0;i<rows.length;i++){
            var row=rows[i];
            var JkStatus=parseInt(row["JkStatus"]);
            var NeedPayFor=row["NeedPayFor"];
            off.push(NeedPayFor);
            offs.push(JkStatus);
        }
        if (offs.length>0) {
            if (offs.indexOf(4) == -1 && offs.indexOf(5) == -1) {
                if (off.indexOf(false) > -1) {
                    ToPayForWait.disable();
                } else {
                    ToPayForWait.enable();
                }
            } else {
                if (offs.indexOf(4) > -1 || offs.indexOf(5) > -1) {
                    NoPayMark.disable();
                    ParyMark.disable();
                    ToPayForWait.disable();
                } else {
                    NoPayMark.enable();
                    ParyMark.enable();
                    ToPayForWait.enable();
                }
            }
        } else {
            NoPayMark.enable();
            ParyMark.enable();
            ToPayForWait.enable();
        }
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

    function changeQuery(code, state, obj) {
        var con = $(event.srcElement || e.targetElement);
        var cons = $('.Jdlcli');
        for (var i = 0; i < cons.length; i++) {
            var cx = cons[i];
            if (cx.className == "Jdlcli YearSource" || cx.className == "Jdlcli YearSource clicked") {
                cx.children[0].children[0].style.cssText = "color:rgb(0, 159, 205);";
                cx.children[0].children[1].style.cssText = "color:rgb(0, 159, 205);";
            }
            cx.classList.remove('clicked');
        }
        $(con).parents('.Jdlcli').addClass('clicked');
        obj.children[0].style.cssText = "color:#fff";
        obj.children[1].style.cssText = "color:#fff";
        doQuery(code, state);
        window.parent.doResize();
    }

    function doQuery(code, state) {
        var arg = {};
        var bs = [];
        var cs = [];
        var css = [];
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
        if (code) {
            curCode = code;
            curState = state;
            if (code != "" && code != "Index") {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
                if (code == "JKStatus" && state != 3) {
                    var ops = {field: 'NeedPayFor', oper: 'EQ', value: 1};
                    css.push(ops)
                }
            }
        }

        var ds = addPreCondition(curCode, curState);
        if (cs.length > 0) arg["Query"] = mini.encode(cs);
        arg["Querys"] = mini.encode(css);
        if (ds.length > 0) arg["High"] = mini.encode(ds);
        grid.load(arg);
    }

    function addPreCondition(code, state) {
        var ds = [];
        if (code) {
            if (code != "" && code != "Index") {
                var op = {field: code, oper: 'EQ', value: state};
                ds.push(op);
                if (code == "JKStatus" && state != 3) {
                    var ops = {field: 'NeedPayFor', oper: 'EQ', value: 1};
                    ds.push(ops)
                }
            }
        }
        return ds;
    }

    function doFixedQuery(field, oper, val) {
        var arg = {};
        var cs = [{field: field, oper: oper, value: val}];
        arg["Query"] = mini.encode(cs);
        grid.load(arg);
    }
</script>
</@layout>