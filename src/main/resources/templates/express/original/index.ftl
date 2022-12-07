<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript" src="/js/common/complexExcelExportExpress.js"></script>
    <script type="text/javascript">
        var PackageSta = [
            {id:1,text:'待寄送'}, {id:2,text:'已寄送'}
        ];
        var types = [
            {id:0,text:'发明专利'}, {id:1,text:'新型专利'}, {id:2,text:'外观专利'}
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
    <style type="text/css">
        .New_Button, .Edit_Button, .Delete_Button, .Update_Button, .Cancel_Button,
        .AlreadyPay_Button, .ExportPay_Button, .Remove_Button, .AuditOpinion_Button {
            font-size: 11px;
            color: #1B3F91;
            font-family: Verdana;
            margin-right: 8px;
        }
    </style>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="center" style="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <div class="mini-clearfix ">
                            <div class="mini-col-12">
                                <div id="info1" style="height:55px;background:rgb(226,250,252);border-radius: 5px;border:1px solid rgb(190,226,240);">
                                    <div class="info1top" style="padding-left: 120px;">
                                        <h3 class="sqf" style="color: rgb(3,154,209);margin-left:-90px;margin-top:17px;font-weight:bold;">
                                            <img style="vertical-align: middle;" src="/appImages/jk.png">&nbsp;快递管理监控
                                        </h3>
                                        <ul>
                                            <li class="Jdlcli originalkd" id="J1" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('PackageStatus',0,this,'no')">
                                                    <span id="J1span">全部清单</span>
                                                    <h4 class="x0">0</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli originalkd" id="J5" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('PackageStatus',1,this,'yes')">
                                                    <span id="J5span">待寄送清单</span>
                                                    <h4 class="x4">3</h4>
                                                </a>
                                            </li>
                                            <li class="Jdlcli originalkd" id="J6" >
                                                <a style="text-decoration:none" target="_self" href="#" onclick="changeQuery('PackageStatus',2,this,'yes')">
                                                    <span id="J6span">已寄送清单</span>
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
                            <a class="mini-button mini-button-info" id="ExpressSend_UpdateExpressWindow" onclick="UpdateExpressWindow">添加快递信息</a>
<#--                            <a class="mini-button mini-button-success" id="ExpressSend_ExpressAlreadyWindow" onclick="ExpressAlreadyWindow">标记为已寄送</a>-->
                            <a class="mini-button mini-button-danger" id="ExpressSend_ExpressNot" onclick="ExpressNot">标记为待寄送</a>
                            <a class="mini-button mini-button-success" id="ExpressSend_Remove" onclick="NoExpressAlreadyWindow">删除寄送</a>
<#--                            <a class="mini-button mini-button-info" onclick="doExport">导出核对清单</a>-->
                            <a class="mini-button mini-button-info" onclick="doExportQshz">导出邮寄文件签收回执</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field"  id="comField" style="width:100px" data="[{id:'All',text:'全部属性'},
                            {id:'PackageContent',text:'包裹内容'},{id:'PackageStatus',text:'包裹状态'},{id:'Render',text:'寄送人'}
                            ,{id:'CourierCompany',text:'快递公司'},{id:'PostalCode',text:'快递单号'},{id:'KHName',text:'接收人'}
                            ,{id:'Address',text:'接收人地址'},{id:'Phone',text:'联系电话'},{id:'PackageStatus',text:'原件类型'}
                            ,{id:'TONGZHISMC',text:'通知书名称'},{id:'dnum',text:'发文序号/收据号/证书号'},{id:'SHENQINGH',text:'专利申请号'},{id:'FAMINGMC',text:'专利名称'}
                            ,{id:'SHENQINGLX',text:'专利类型'},{id:'ANJIANYWZT',text:'专利法律状态'},{id:'SHENQINGRXM',text:'专利申请人'},{id:'FAMINGRXM',text:'专利发明人'}]" value="All" id="Field">
                            </div>
                            <input class="mini-textbox Query_Field" style="width:150px" id="QueryText" />
                            <a class="mini-button mini-button-success" onclick="doQuery();"
                               id="OriginalKd_Query">模糊搜索</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="OriginalKd_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" onshowrowdetail="onShowOriginalKdDetail"
                     allowresize="true" url="/express/original/getData" pagesize="20" sizelist="[5,10,20,50,100,150,
                     200]" sortfield="ApplicationTime" sortorder="desc" multiselect="true" autoload="true" onDrawCell="onDraw" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
<#--                        <div type="checkcolumn">&nbsp;</div>-->
                        <div type="expandcolumn" width="40">#</div>
                        <div field="PackageNum" width="120" headeralign="center" allowsort="true">
                            包裹编号
                        </div>
                        <div field="PackageContent" name="PackageContent" width="180" headeralign="center" allowsort="true">
                            包裹内容
                        </div>
                        <div field="PackageStatus"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">
                            包裹状态
                            <input property="editor" class="mini-combobox" data="PackageSta" />
                        </div>
                        <div field="ApplicationTime" width="120" headeralign="center" datatype="date" dateformat="yyyy-MM-dd HH:mm:ss" allowsort="true" >邮寄申请时间</div>
                        <div field="MailAppicant" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                            邮寄申请人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="CreateEmp" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="DeliveryTime" width="110" headeralign="center" align="center" allowsort="true"
                             dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss">
                            寄送时间
                        </div>
                        <div field="Render" width="70" headeralign="center" align="right" allowsort="true">
                            寄送人
                        </div>
                        <div field="CourierCompany" width="70" headeralign="center" align="right" allowsort="true">
                            快递公司
                        </div>
                        <div field="PostalCode" width="70" headeralign="center" allowsort="true">
                            快递单号
                        </div>
                        <div field="KHName" width="80" headeralign="center" allowsort="true">
                            客户名称
                        </div>
                        <div field="Address" width="80" headeralign="center" allowsort="true">
                            接收人地址
                        </div>
                        <div field="ContactPerson" width="80" headeralign="center" allowsort="true">
                            接收人
                        </div>
                        <div field="Phone" width="100" headeralign="center" allowsort="true">
                            联系电话
                        </div>
                        <div field="ExpressNotes" width="120" headeralign="center" allowsort="true">
                            备注
                        </div>
                    </div>
                </div>
                <div id="detailGrid_Form" style="display:none;">
                    <div id="OriginalKdDetail_datagrid" class="mini-datagrid" style="width:100%;"
                         url="/express/original/getDetail" multiselect="true" onload="selectAll()">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div type="checkcolumn"></div>
                            <div field="otypeText" width="80" headeralign="center" align="center" allowsort="true">
                                原件类型
                            </div>
                            <div field="TONGZHISMC" width="150" headeralign="center" align="center" allowsort="true">
                                通知书名称
                            </div>
                            <div field="dnum" width="160" headeralign="center" align="right" allowsort="true" title="发文序号/收据号/证书号">
                                发文序号/收据号/证书号
                            </div>
                            <div field="SHENQINGH" name="shenqingh" width="120" headeralign="center">
                                专利申请号
                            </div>
                            <div field="FAMINGMC" name="zhuanlimc" width="200" headeralign="center">
                                专利名称
                            </div>
                            <div field="SHENQINGLX"  width="100" headerAlign="center" type="treeselectcolumn" allowsort="true">
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
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var fit=mini.get('fitt');
        var tip = new mini.ToolTip();
        // var grid=mini.get('FeeItem_datagrid');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var DetailRowsCount="";
        var OriginalKdDetail_datagrid=mini.get('OriginalKdDetail_datagrid');

        $(function(){
            $('#p1').hide();
            fit.setHeight('100%');
            fit.doLayout();
        });
        function expand(e) {
            var btn = e.sender;
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
        var grid = mini.get("OriginalKd_datagrid");
        var PrePickRow="";
        grid.on("select",function(e){

            PrePickRow=grid.getSelected();
            grid.showRowDetail(PrePickRow);
        });
        grid.on("deselect",function (e) {
            var grids=mini.get("OriginalKdDetail_datagrid");
            grids.deselectAll(true);

            grid.hideRowDetail(PrePickRow);
        })
        function selectAll() {
            var OriginalKdDetail_datagrid = mini.get("OriginalKdDetail_datagrid");
            OriginalKdDetail_datagrid.selectAll(true);
        }
        function onShowOriginalKdDetail(e) {
            var OriginalKd_datagrid = mini.get("OriginalKdDetail_datagrid");
            var detailGrid_Form =OriginalKd_datagrid.el.parentNode;

            var grid = e.sender;
            var row = e.record;
            var td = grid.getRowDetailCellEl(row);
            if(detailGrid_Form) {
                td.appendChild(detailGrid_Form);
                detailGrid_Form.style.display = "block";
            }

            var ids=row["PackageNum"];
            if(ids)
            {
                OriginalKd_datagrid.load({ IDS:ids});
            } else {
                mini.alert('没有发现可展示的数据。');
            }
        }
        function editRow(row_uid) {
            var row = grid.getSelected();
            if (!row) {
                mini.alert('请选择需要编辑的行');
                return ;
            }
            if (row) {
                var id=row["ID"];
                mini.open({
                    url:'/watch/addToPayForWait/index?IDS='+id+'&Mode=Edit&Type=Year',
                    width:800,
                    height:410,
                    showModal:true,
                    title:'添加到缴费清单',
                    ondestroy:function(action){
                        if(action=='ok')grid.reload();
                    }
                });
            }
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
        function doQuery(code, state,yn) {
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
                        var kWork=f+'='+word;
                        if(cs.indexOf(kWork)==-1){
                            var op={field:f,oper:'LIKE',value:word};
                            cs.push(op);
                        }
                    }
                } else {
                    if (field == "KH" || field == "LC" || field == "XS" || field == "DL") field = "NEIBUBH";
                    var op={field:field,oper:'LIKE',value:word};
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
        function doExport(){
                var grid = mini.get("OriginalKd_datagrid");
                var excel=new excelData(grid);
                excel.export("快递管理清单列表.xls");
        }
        function ExpressAlreadyWindow() {
            var rows = grid.getSelecteds();
            if (rows.length == 0) {
                mini.alert('请选择快递!');
                return;
            }
            for (var i=0;i<rows.length;i++){
                if (rows[i].PackageStatus=="2"){
                    mini.alert("包裹状态为已寄送，无法再次修改！");
                    return;
                }
            }
            var date = new Date();
            var nowDate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " +date.getHours()+ ":" + date.getMinutes()+":"+date.getSeconds();
            mini.get('DrawTime1').setValue(nowDate);
            var editWindow1 = mini.get("editWindow1");
            editWindow1.show();
        }
        function ExpressAlready() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["PackageNum"]);
            }
            if (ids.length == 0) {
                mini.alert('请选择快递!');
                return;
            }
            //var iss = ids.join(',');
            //mini.confirm('确定已寄送?', '系统提示', function (act) {
            //    if (act == 'ok') {
            var url = '/express/original/expressAlready';
            $.post(url, {ids: mini.encode(ids), DeliveryTime: mini.get('DrawTime1').getText(),
                Render: mini.get('DrawEmp1').getValue() }, function (r) {
                if (r['success']) {
                    mini.get("editWindow1").hide();
                    grid.reload();
                }
                else mini.alert(r['Message'] || "标记失败!");
            });
            //    }
            //});
        }

        function ExpressNot() {
            var rows = grid.getSelecteds();
            var ids = [];
            var dnums=[];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["PackageNum"]);
            }
            if (ids.length == 0) {
                mini.alert('请选择快递!');
                return;
            }
            //var iss = ids.join(',');
            mini.confirm('确定标记为未寄送?', '系统提示', function (act) {
                if (act == 'ok') {
                    var url = '/express/original/ExpressNot';
                    $.post(url, { ids: mini.encode(ids)}, function (r) {
                        if (r['success']) {
                            mini.alert('标记成功!');
                            grid.reload();
                        }
                        else mini.alert(r['Message'] || "标记失败!");
                    });
                }
            });
        }
        OriginalKdDetail_datagrid.on("load",function (e) {
            DetailRowsCount=OriginalKdDetail_datagrid.getData().length;
        })
        function NoExpressAlreadyWindow(){
            var grids=mini.get('OriginalKdDetail_datagrid');
            var rows = grids.getSelecteds();
            var ids = [];
            var detailids="";
            var isSelectAll="";
            if (DetailRowsCount==rows.length){
                isSelectAll="All";
            }
            for (var i = 0; i < rows.length; i++) {
                if (rows[i]["PackageStatus"]=="2"){
                    mini.alert("已寄送的数据不能删除寄送！");
                    return;
                }
                ids.push(rows[i]["dnum"]);
                detailids=rows[i]["PackageNum"];
            }
            if (ids.length == 0) {
                mini.alert('请选择快递!');
                return;
            }
            //var iss = ids.join(',');
            mini.confirm('确定取消寄送?', '系统提示', function (act) {
                if (act == 'ok') {
                    var url = '/express/original/orginalkdno';
                    $.post(url, { ids: mini.encode(ids),IsSelectAll:isSelectAll,DetailsID:detailids}, function (r) {
                        if (r['success']) {
                            mini.alert('标记成功!');
                            grid.reload();
                        }
                        else mini.alert(r['Message'] || "标记失败!");
                    });
                }
            });
        }

        function UpdateExpressWindow() {
            var ids=[];
            var rows = grid.getSelecteds();
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["PackageNum"]);
                if (rows[i].PackageStatus=="2"){
                    mini.alert("原件已寄送，无法再次修改包裹信息！");
                    return;
                }
            }
            if (rows.length == 0) {
                mini.alert('请选择要邮寄的原件!');
                return;
            }
            var iss = ids.join(',');
            mini.open({
                url:'/express/original/xgkd?PackageNum='+iss,
                width:'50%',
                height:460,
                title:'修改快递',
                showModal:true,
                onDestroy:function(){
                    grid.reload();
                }
            })
            // var form = new mini.Form("#editform2");
            // var editWindow = mini.get("editWindow2");
            // //$('#editform2 input[name=Contents]').val(contents);
            // mini.get(Contents2).setValue(contents);
            // editWindow.show();
        }

        function onDraw(e) {
            var field=e.field;
            var record=e.record;
            if (field=="PackageStatus"){
                if (record["PackageStatus"]=="2"){
                    record["KHName"]="";
                    record["Address"]="";
                    record["Postcode"]="";
                    record["Phone"]="";
                    record["ContactPerson"]="";
                }
            }
            if (field=="PackageStatus"){
                var state=record["PackageStatus"];
                if (state=="1") e.cellHtml = '<SPAN style="color:blue">待寄送</span>';
                if (state=="2") e.cellHtml = '<SPAN style="color:red">已寄送</span>';
            }
        }

        function doExportQshz(){
            var DetailGrid = mini.get('OriginalKdDetail_datagrid');
            var excel=new complexExcelDatas(DetailGrid);
            excel.export('kdqs','邮寄文件签收回执.xls');
        }

        function cancelRow2() {
            var editWindow = mini.get("editWindow1");
            editWindow.hide();
        }

        function afterload(e) {
            updateNumber();
        }
        function updateNumber() {
            var url="/express/original/refreshTotal";
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
                if(cx.className=="Jdlcli originalkd" || cx.className=="Jdlcli originalkd clicked" ){
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
</@js>