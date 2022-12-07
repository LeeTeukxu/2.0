<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript" src="/js/common/complexExcelExportExpress.js"></script>
    <script type="text/javascript" src="/js/layui/layui.js"></script>
    <script type="text/javascript" src="/js/miniui/pagertree.js"></script>

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
    <script type="text/javascript">
        var states = [
            {id:0,text:' 待分类'},{id:1,text:'待自取'}, {id:2,text:'已自取'}
        ];
        var types = [
            {id:0,text:'发明专利'}, {id:1,text:'新型专利'}, {id:2,text:'外观专利'}
        ];
        var yjzt=[ { id: '0', text: '待分类' }, { id: '1', text: '待自取' }, { id: '2', text: '已自取' }];
    </script>
    <div class="mini-layout" style="width:100%;height:100%;overflow:hidden">
        <div region="center">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-12">
                                <div id="info1" style="height:55px;background:rgb(226,250,252);border-radius: 5px;border:1px solid rgb(190,226,240);">
                                    <div class="info1top" style="padding-left: 120px;">
                                        <h3 class="sqf" style="color: rgb(3,154,209);margin-left:-90px;margin-top:17px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jk.png">&nbsp;自取管理监控
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli pickup" id="J1" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',0,this,'no')">
                                                    <span id="J1span">全部清单</span>
                                                    <h4 class="x0">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli pickup" id="J3" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',1,this,'yes')">
                                                    <span id="J3span">待自取清单</span>
                                                    <h4 class="x2">1</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli pickup" id="J4" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('ostateText',2,this,'yes')">
                                                    <span id="J4span">已自取清单</span>
                                                    <h4 class="x3">2</h4>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td style="width:100%">
                            <a class="mini-button mini-button-success" id="PickUp_PickUpWindow" onclick="PickUpWindow">标记为已自取</a>
                            <a class="mini-button mini-button-success" id="PickUp_PickNoUpWindow" onclick="PickUp">标记为待自取</a>
                            <a class="mini-button mini-button-success" id="PickUp_Remove" onclick="NoPickUpWindow">删除取件</a>
<#--                            <a class="mini-button mini-button-info" onclick="doExportChecklist">导出核对清单</a>-->
                            <a class="mini-button mini-button-info" onclick="doExportQshz">导出文件签收回执</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field" id="comField" style="width:100px" data="[{id:'All',text:'全部属性'},
                            {id:'DrawNo',text:'自取编号'},{id:'PickUp',text:'取件人'},{id:'otypeText',text:'原件类型'},{id:'dnum',text:'发文序号/收据号/证书号'},
                            {id:'TONGZHISMC',text:'通知书名称'},{id:'ostateText',text:'原件状态'},{id:'PickUpApplicant',text:'取件申请人'},{id:'SHENQINGH', text:'专利申请号'},
                            {id:'FAMINGMC',text:'专利名称'},{id:'SHENQINGLX',text:'专利类型'},{id:'ANJIANYWZT',text:'专利法律状态'},{id:'SHENQINGRXM',text:'专利申请人'},
                            {id:'FAMINGRXM',text:'专利发明人'},{id:'KH',text:'归属客户'},{id:'XS',text:'销售维护人'},{id:'DL',text:'代理责任人'},{id:'LC',text:'流程责任人'}]" value="All"></div>
                            <lable class="Query_Field">模糊查询:</lable>
                            <input type="text" id="txtQuery" style="width:150px" class="mini-textbox" />
                            <button class="mini-button" iconCls="icon-find" onclick="doQuery" id="PickUp_Query">查询</button>
                        </td>
                    </tr>
                </table>
            </div>
<#--            <div class="mini-fit">-->
<#--                <div class="mini-pagertree"  id="grid1" style="width:100%;height:100%" checkRecursive="true"-->
<#--                idField="FID" parentField="PID" resultAsTree="false" treeColumn="Name"-->
<#--                url="/express/pickup/getPickUpData" pageSize="10" showTreeIcon="true"-->
<#--                multiSelect="true" autoload="true" >-->
<#--                <div property="columns">-->
<#--                    <div field="DrawNo" name="Name" width="200" headeralign="center" allowsort="true">-->
<#--                        自取编号-->
<#--                    </div>-->
<#--                    <div field="PickUp" width="80" headeralign="center" >-->
<#--                        取件人-->
<#--                    </div>-->
<#--                    <div field="PickUpTime" width="150" headeralign="center" datatype="date" dateformat="yyyy-MM-dd HH:mm:ss" allowsort="true">-->
<#--                        取件时间-->
<#--                    </div>-->
<#--                    <div field="otypeText" width="100" headeralign="center" align="center" >-->
<#--                        原件类型-->
<#--                    </div>-->
<#--                    <div field="dnum" width="160" headeralign="center" align="right">-->
<#--                        发文序号/收据号/证书号-->
<#--                    </div>-->
<#--                    <div field="TONGZHISMC" width="130" headeralign="center" >-->
<#--                        通知书名称-->
<#--                    </div>-->
<#--                    <div field="ostateText"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">-->
<#--                        原件状态-->
<#--                        <input property="editor" class="mini-combobox" data="states" />-->
<#--                    </div>-->
<#--                    <div field="PickUpApplicationTime" width="150" headeralign="center" datatype="date" dateformat="yyyy-MM-dd HH:mm:ss" allowsort="true">-->
<#--                        取件申请时间-->
<#--                    </div>-->
<#--                    <div field="PickUpApplicant" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">-->
<#--                        取件申请人-->
<#--                        <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"-->
<#--                               textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="PickUpApplicant" style="width:-->
<#--                               98%;" required="true" resultAsTree="false"/>-->
<#--                    </div>-->
<#--                    <div field="SHENQINGH" name="shenqingh" width="120" headeralign="center" renderer="onZhanlihao">-->
<#--                        专利申请号-->
<#--                    </div>-->
<#--                    <div field="FAMINGMC" name="zhuanlimc" width="200" headeralign="center">-->
<#--                        专利名称-->
<#--                    </div>-->
<#--                    <div field="SHENQINGLX"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">-->
<#--                        专利类型-->
<#--                        <input property="editor" class="mini-combobox" data="types" />-->
<#--                    </div>-->
<#--                    <div field="ANJIANYWZT" width="150" headeralign="center">-->
<#--                        专利法律状态-->
<#--                    </div>-->
<#--                    <div field="SHENQINGRXM" width="250" headeralign="center">-->
<#--                        专利申请人-->
<#--                    </div>-->
<#--                    <div field="FAMINGRXM" width="130" headeralign="center">-->
<#--                        专利发明人-->
<#--                    </div>-->
<#--                    <div width="240" headeralign="center" field="NEIBUBH">内部编号</div>-->
<#--                    <div width="120" headeralign="center" field="KH">归属客户</div>-->
<#--                    <div width="100" headeralign="center" field="YW">销售维护人</div>-->
<#--                    <div width="100" headeralign="center" field="DL">代理责任人</div>-->
<#--                    <div width="100" headeralign="center" field="LC">流程责任人</div>-->
<#--                </div>-->
<#--            </div>-->
            <div class="mini-fit" id="fitt">
                <div id="PickUp_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" onshowrowdetail="onShowPickDetail"
                     allowresize="true" url="/express/pickup/getData" pagesize="20" sizelist="[5,10,20,50,100,150,
                     200]" sortfield="PickUpApplicationTime" sortorder="desc" multiselect="true" autoload="true" onDrawCell="onDraw" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
<#--                        <div type="checkcolumn"></div>-->
                        <div type="expandcolumn" width="40">#</div>
                        <div field="DrawNo" name="Name" width="200" headeralign="center" allowsort="true">
                            自取编号
                        </div>
                        <div field="ostateText"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">
                            原件状态
                            <input property="editor" class="mini-combobox" data="states" />
                        </div>
                        <div field="PickUpApplicant" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                            取件申请人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="PickUpApplicant" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="PickUpApplicationTime" width="150" headeralign="center" datatype="date" dateformat="yyyy-MM-dd HH:mm:ss" allowsort="true">
                            取件申请时间
                        </div>
                        <div field="PickUp" width="80" headeralign="center" >
                            取件人
                        </div>
                        <div field="PickUpTime" width="150" headeralign="center" datatype="date" dateformat="yyyy-MM-dd HH:mm:ss" allowsort="true">
                            取件时间
                        </div>
                    </div>
                </div>
                <div id="detailGrid_Form" style="display:none;">
                    <div id="PickUpDetail_datagrid" class="mini-datagrid" style="width:100%;"
                         url="/express/pickup/getDetail" multiselect="true" onload="selectAll()">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div type="checkcolumn"></div>
                            <div field="otypeText" width="100" headeralign="center" align="center" >
                                原件类型
                            </div>
                            <div field="dnum" width="160" headeralign="center" align="right">
                                发文序号/收据号/证书号
                            </div>
                            <div field="TONGZHISMC" width="130" headeralign="center" >
                                通知书名称
                            </div>
                            <div field="SHENQINGH" name="shenqingh" width="120" headeralign="center" renderer="onZhanlihao">
                                专利申请号
                            </div>
                            <div field="FAMINGMC" name="zhuanlimc" width="200" headeralign="center">
                                专利名称
                            </div>
                            <div field="SHENQINGLX"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">
                                专利类型
                                <input property="editor" class="mini-combobox" data="types" />
                            </div>
                            <div field="ANJIANYWZT" width="150" headeralign="center">
                                专利法律状态
                            </div>
                            <div field="SHENQINGRXM" width="250" headeralign="center">
                                专利申请人
                            </div>
                            <div field="FAMINGRXM" width="130" headeralign="center">
                                专利发明人
                            </div>
                            <div width="240" headeralign="center" field="NEIBUBH">内部编号</div>
                            <div width="120" headeralign="center" field="KH">归属客户</div>
                            <div width="100" headeralign="center" field="YW">销售维护人</div>
                            <div width="100" headeralign="center" field="DL">代理责任人</div>
                            <div width="100" headeralign="center" field="LC">流程责任人</div>
                        </div>
                    </div>
                </div>
                <div id="editWindow1" class="mini-window" title="原件已寄送" showmodal="true"
                     allowresize="true" allowdrag="true" style="width:800px;">
                    <div id="editform1" class="form">
                        <table style="width:100%;">
                            <tr>
                                <td style="width:80px;">寄送时间：</td>
                                <td style="width:150px;"><input id="DrawTime1" name="DeliveryTime" class="mini-datepicker" showtime="true" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td style="width:80px;">寄送人：</td>
                                <td style="width:150px;"><input id="DrawEmp1" name="Render" class="mini-textbox" value="${DrawEmp}" /></td>
                            </tr>
                            <tr>
                                <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="2">
                                    <a class="Update_Button" href="javascript:ExpressAlready();">确定</a>
                                    <a class="Cancel_Button" href="javascript:cancelRow2();">取消</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <form method="post" action="/express/orig/exportExcel" style="display:none" id="exportExcelForm">
                <input type="hidden" name="Data" id="exportExcelData"/>
            </form>
            <div id="editWindow" class="mini-window" title="原件已自取" showmodal="true"
                     allowresize="true" allowdrag="true" style="width:800px;">
                    <div id="editform" class="form">
                        <input name="id" class="mini-hidden" />
                        <table style="width:100%;">
                            <tr>
                                <td style="width:80px;">自取时间：</td>
                                <td style="width:150px;"><input id="DrawTime1" name="DrawTime" class="mini-datepicker" showtime="true" format="yyyy-MM-dd HH:mm:ss" /></td>
                                <td style="width:80px;">自取人：</td>
                                <td style="width:150px;"><input id="DrawEmp1" name="DrawEmp" class="mini-textbox" value="${DrawEmp}" /></td>
                                <td style="width:80px;">自取编号：</td>
                                <td style="width:150px;"><input id="DrawNo2" class="mini-textbox" enabled="false"/></td>
                            </tr>
                            <tr>
                                <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="4">
                                    <span style="padding-right:20px;color:red;">此自取编号所有原件将已自取</span>
                                    <a class="mini-button mini-button-success" href="javascript:AlreadyPickUp();">确定</a>
                                    <a class="mini-button mini-button-danger" href="javascript:cancelRow();">取消</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        // var grid = mini.get('#grid1');

        var grid = mini.get("PickUp_datagrid");
        var PrePickRow="";

        grid.on("select",function(e){

            PrePickRow=grid.getSelected();
            grid.showRowDetail(PrePickRow);
        });
        grid.on("deselect",function (e) {
            var grids=mini.get("PickUpDetail_datagrid");
            grids.deselectAll(true);

            grid.hideRowDetail(PrePickRow);
        })
        function selectAll() {
            var PickUpDetail_datagrid = mini.get("PickUpDetail_datagrid");
            PickUpDetail_datagrid.selectAll(false);
        }
        function onShowPickDetail(e) {
            var OriginalKd_datagrid = mini.get("PickUpDetail_datagrid");
            var detailGrid_Form =OriginalKd_datagrid.el.parentNode;

            var gridCon = e.sender;
            var row = e.record;
            var td = gridCon.getRowDetailCellEl(row);
            if(detailGrid_Form) {
                td.appendChild(detailGrid_Form);
                detailGrid_Form.style.display = "block";
            }

            var ids=row["DrawNo"];
            if(ids)
            {
                OriginalKd_datagrid.load({ IDS:ids});
            } else {
                mini.alert('没有发现可展示的数据。');
            }
        }

        function PickUpWindow() {
            var date = new Date();
            var nowDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " +date.getHours()+ ":" + date.getMinutes()+":"+date.getSeconds();
            mini.get('DrawTime1').setValue(nowDate);
            var row = grid.getSelected();
            if (row == null) {
                mini.alert('请选择需要自取的原件!');
                return;
            }
            if (row.ostateText=="2"){
                mini.alert("该原件已自取，请勿再次自取！");
                return;
            }
            mini.get("DrawNo2").setValue(row.DrawNo);
            var editWindow = mini.get("editWindow");
            editWindow.show();
        }

        function PickUp() {
            var row = grid.getSelected();
            if (row==null){
                mini.alert("请选择要设置的原件信息！");
                return;
            }

            mini.confirm('确定待自取?', '系统提示', function (act) {
                if (act == 'ok') {
                    var url = '/express/pickup/dzq';
                    var arg={
                        DrawNo:row.DrawNo,
                    };
                    $.post(url, arg, function (r) {
                        if (r['success']) {
                            //mini.alert('标记成功!');
                            grid.reload();
                        }
                        else mini.alert(r['Message'] || "标记失败!");
                    });
                }
            });
        }
        
        function NoPickUpWindow() {
            var detailgrid = mini.get('#PickUpDetail_datagrid');
            var row = detailgrid.getSelecteds();
            var ids=[];
            for (var i = 0; i < row.length; i++) {
                if (row[i]["ostateText"]=="2") {
                    mini.alert("已自取的数据无法删除取件！");
                    return;
                }
                ids.push(row[i]["dnum"]);
            }
            if (ids.length==0){
                mini.alert("请选择要设置的原件信息！");
                return;
            }
            var iss = ids.join(',');
            mini.confirm('确定取消取件?', '系统提示', function (act) {
                if (act == 'ok') {
                    var url = '/express/pickup/pickupno';
                    var arg={
                        Dnum:iss
                    };
                    $.post(url, arg, function (r) {
                        if (r['success']) {
                            //mini.alert('标记成功!');
                            grid.reload();
                            detailgrid.reload();
                        }
                        else mini.alert(r['Message'] || "标记失败!");
                    });
                }
            });
        }

        function AlreadyPickUp() {
            var grid = mini.get('#PickUp_datagrid');
            var row = grid.getSelected();
            if (row == null) {
                mini.alert('请选择需要自取的原件!');
                return;
            }
            var url = '/express/pickup/alreadyPickUp';
            $.post(url, { 'PickUpNumber': row.DrawNo,  'PickUpTime':mini.get('DrawTime1').getText(),
                'PickUp':mini.get('DrawEmp1').getValue()}, function (r) {
                if (r['success']) {
                    mini.get("editWindow").hide();
                    grid.reload();
                }
                else mini.alert(r['Message'] || "标记失败!");
            });
        }

        function cancelRow() {
            var editWindow = mini.get("editWindow");
            editWindow.hide();
        }
        kendo.culture("zh-CN");

        function doExportChecklist(){
            var excel=new excelData(grid);
            excel.export("自取核对清单.xls");
        }

        function doExportQshz(){
            var DetailGrid = mini.get('PickUpDetail_datagrid');
            var excel=new complexExcelDatas(DetailGrid);
            excel.export('kdqs','自取文件签收回执.xls');
        }

        function doQuery(code, state,yn){

            var grid = mini.get('PickUp_datagrid');
            var txtQuery = mini.get('#txtQuery');
            var comField = mini.get('#comField');
            var arg = {};
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
                        if (f == "KH" || f == "LC" || f == "XS" || f == "DL" || f=="JS") f = "NEIBUBH";
                        var kWork=f+'='+word;
                        if(cs.indexOf(kWork)==-1){
                            var op={field:f,oper:'LIKE',value:encodeURI(word)};
                            cs.push(op);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                    var op={field:field,oper:'LIKE',value:encodeURI(word)};
                    cs.push(op);
                }
            }
            if (code && yn=="yes") {
                var op = {field: code, oper: 'EQ', value: state};
                cs.push(op);
            }
            if(cs.length>0)arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field=="ostateText"){
                var state=record["ostateText"];
                if (state=="1") e.cellHtml = '<SPAN style="color:blue">待自取</span>';
                if (state=="2") e.cellHtml = '<SPAN style="color:red">已自取</span>';
            }
        }

        function afterload(e) {
            updateNumber();
        }
        function updateNumber() {
            var url="/express/pickup/refreshTotal";
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
        function changeQuery(code, state,obj,yn) {
            var con = $(event.srcElement || e.targetElement);
            var cons=$('.Jdlcli');
            for(var i=0;i<cons.length;i++){
                var cx=cons[i];
                if(cx.className=="Jdlcli pickup" || cx.className=="Jdlcli pickup clicked" ){
                    cx.children[0].children[0].style.cssText="color:rgb(0, 159, 205);";
                    cx.children[0].children[1].style.cssText="color:rgb(0, 159, 205);";
                }
                cx.classList.remove('clicked');
            }
            $(con).parents('.Jdlcli').addClass('clicked');
            obj.children[0].style.cssText="color:#fff";
            obj.children[1].style.cssText="color:#fff";
            doQuery(code, state,yn);
        }
    </script>
</@layout>