<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>知服帮</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="/css/ionicons.css" rel="stylesheet" type="text/css">
    <link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/simple-line-icons.css"/>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <script src="http://apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
<#--    <script src="http://apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>-->
    <script type="text/javascript" src="/js/boot.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js?v=${version}"></script>

    <style type="text/css">
        .KJul {
            width: 160%;
            text-align: left
        }

        .KJul li {
            list-style: none;
            float: left;
            padding-left: 2%;
            padding-right: 3%;
            padding-top: 30px;
            padding-bottom: 30px;
            text-align: left;
        }

        .KJul li a {
            color: #7a7575;
        }

        .KJul li span {
            position: relative;
            top: 8px;
        }

        .KJul li img {
            width: 80px;
        }
    </style>
    <script type="text/javascript">
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
    </script>
</head>
<body style="overflow-y: auto;overflow-x: hidden;">
<div style="width:100%;height:10px"></div>
<div class="bottommain bottomain2" style="margin-left: -30px;margin-top: -10px; height: 100%">
    <div class="b1"  style="height:360px">
        <div class="b1L" style="width:98%;height:360px">
            <h3 class="box-title">&nbsp;专利信息更新
                <a href="#" id="pantentNow" class="btn btn-info  btn-sm" style="margin-left:100px" onclick="return  pantentNow();">今天更新</a>
                <a href="#" id="pantentPre" class="btn btn-secondary btn-sm" style="margin-left:20px" onclick="return pantentPre();">昨天更新</a>
                <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_Export"
                   onclick="doExport('grid0','专利信息更新记录.xls')">导出Excel</a>
                <div style="display: inline;float:right">
                    <div class="mini-combobox Query_Field Browse_Query" id="comFieldZero" style="width:100px" data="[{id:'All',
                            text:'全部属性'},
                            {id:'SHENQINGRXM',text:'专利申请人'},{id:'SHENQINGH',text:'专利申请号'},
                            {id:'SHENQINGLX',text:'专利类型'},{id:'FAMINGMC',text:'专利名称'},{id:'ANJIANYWZT',text:'专利状态'},{id:'FAMINGRXM',text:'发明人'},{id:'DAILIJGMC',text:'代理机构'}]" value="All" id="Field"></div>
                    <input class="mini-textbox Query_Field Browse_Query" style="width:150px" id="QueryTextZero"/>
                    <a class="mini-button mini-button-success" onclick="doQuery('grid0','QueryTextZero','comFieldZero');"
                       id="ClientBrowse_Query">模糊搜索</a>
                    <a class="mini-button mini-button-danger"  id="Browse_Reset"
                       onclick="reset('QueryTextZero','comFieldZero')">重置条件</a>
                </div>
            </h3>
            <div class="mini-datagrid" style="margin:2px;width:99%;height:295px" id="grid0"
                 autoload="false"  url="/workNumber/getPantent" pageSize="8" sortField="LASTUPDATETIME"
                 sortOrder="Desc">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="SHENQINGRXM" width="200" headerAlign="center" allowsort="true">专利申请人</div>
                    <div field="SHENQINGH" width="120" headerAlign="center" allowsort="true">专利申请号</div>
                    <div field="SHENQINGLX" width="80" align="center" headerAlign="center" type="comboboxcolumn"
                         allowsort="true">
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
                    <div field="DAILIJGMC" width="200" headerAlign="center" allowsort="true">代理机构</div>
                    <div field="LASTUPDATETIME" width="140" headerAlign="center" Align="center" dataType="date"
                         dateFormat="yyyy-MM-dd HH:mm:ss" allowsort="true">更新时间
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="b1"  style="height:360px">
        <div class="b1L" style="width:98%;height:360px">
            <h3 class="box-title">&nbsp;通知书更新
                <a href="#" id="tzsNow" class="btn btn-info  btn-sm" style="margin-left:100px" onclick="return tzsNow();">今天上传</a>
                <a href="#" id="tzsPre" class="btn btn-secondary  btn-sm" style="margin-left:20px" onclick="return tzsPre();
">昨天上传</a>
                <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_Export"
                   onclick="doExport('grid1','通知书更新记录.xls')">导出Excel</a>
                <div style="display: inline;float:right">
                    <div class="mini-combobox Query_Field Browse_Query" id="comFieldOne" style="width:100px" data="[{id:'All',
                            text:'全部属性'},
                            {id:'TONGZHISBH',text:'通知书编号'},{id:'TZSMC',text:'通知书名称'},
                            {id:'SHENQINGH',text:'专利号'},{id:'ZHUANLIMC',text:'专利名称'},{id:'SHENQINGLX',text:'专利类型'},{id:'SQR',text:'申请人'}]" value="All" id="Field"></div>
                    <input class="mini-textbox Query_Field Browse_Query" style="width:150px" id="QueryTextOne"/>
                    <a class="mini-button mini-button-success" onclick="doQuery('grid1','QueryTextOne','comFieldOne');"
                       id="ClientBrowse_Query">模糊搜索</a>
                    <a class="mini-button mini-button-danger"  id="Browse_Reset"
                       onclick="reset('QueryTextOne','comFieldOne')">重置条件</a>
                </div>
            </h3>
            <div class="mini-datagrid" style="margin:2px;width:99%;height:295px" id="grid1"
                 autoload="false"  url="/workNumber/getTZS" pageSize="8" sortField="TUploadTime" sortOrder="Desc">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="TONGZHISBH" align="center" headerAlign="center" width="120">通知书编号</div>
                    <div field="TZSMC" align="center" headerAlign="center" width="150">通知书名称</div>
                    <div field="SHENQINGH" align="center" headerAlign="center" >专利号</div>
                    <div field="ZHUANLIMC" align="center" headerAlign="center" width="200">专利名称</div>
                    <div field="SHENQINGLX" align="center" headerAlign="center" type="comboboxcolumn" width="80">专利类型
                        <input property="editor" class="mini-combobox" data="types" />
                    </div>
                    <div field="SQR" align="center" headerAlign="center" width="200">申请人</div>
                    <div field="FAWENRQ" align="center" headerAlign="center"  width="120"  dataType="date"
                         dateformat="yyyy-MM-dd">发文日期</div>
                    <div field="TUploadTime" align="center" headerAlign="center" width="150" dataType="date"
                         dateformat="yyyy-MM-dd HH:mm:ss">上传时间</div>
                </div>
            </div>
        </div>
    </div>
    <div class="b1"  style="height:360px">
        <div class="b1L" style="width:98%;height:360px">
            <h3 class="box-title">官费监控更新
                <a href="#" id="addFeeNow" class="btn btn-info btn-sm" style="margin-left:100px" onclick="return addFeeNow()">今天添加</a>
                <a href="#" id="addFeePre" class="btn btn-secondary btn-sm" style="margin-left:20px" onclick="return addFeePre()">昨天添加</a>
                <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_Export"
                   onclick="doExport('grid3','官费监控更新记录.xls')">导出Excel</a>
                <div style="display: inline;float:right">
                    <div class="mini-combobox Query_Field Browse_Query" id="comFieldThree" style="width:100px" data="[{id:'All',
                            text:'全部属性'},
                            {id:'SHENQINGH',text:'专利号'},{id:'FAMINGMC',text:'发明名称'},
                            {id:'SHENQINGLX',text:'专利类型'},{id:'FEENAME',text:'费用名称'},{id:'FEENAME',text:'费用名称'}]" value="All" id="Field"></div>
                    <input class="mini-textbox Query_Field Browse_Query" style="width:150px" id="QueryTextThree"/>
                    <a class="mini-button mini-button-success" onclick="doQuery('grid3','QueryTextThree','comFieldThree');"
                       id="ClientBrowse_Query">模糊搜索</a>
                    <a class="mini-button mini-button-danger"  id="Browse_Reset"
                       onclick="reset('QueryTextThree','comFieldThree')">重置条件</a>
                </div>
            </h3>
            <div class="mini-datagrid" style="margin:2px;width:99%;height:295px" id="grid3"
                 autoload="false"url="/workNumber/getAddFee" pageSize="8" sortField="CREATETIME" sortOrder="desc">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="SHENQINGH" align="center" headerAlign="center" width="120">专利号</div>
                    <div field="FAMINGMC" align="center" headerAlign="center" width="200">发明名称</div>
                    <div field="SHENQINGLX" align="center" headerAlign="center" type="comboboxcolumn" width="80">专利类型
                        <input property="editor" class="mini-combobox" data="types" />
                    </div>
                    <div field="FEENAME" align="center" headerAlign="center" width="200">费用名称</div>
                    <div field="MONEY" align="center" headerAlign="center" width="150">金额</div>
                    <div field="JIAOFEIR" align="center" headerAlign="center"  width="120"  dataType="date"
                         dateformat="yyyy-MM-dd">缴费截止日</div>
                    <div field="CREATETIME" align="center" headerAlign="center" width="150" dataType="date"
                         dateformat="yyyy-MM-dd HH:mm:ss">添加时间</div>
                </div>
            </div>
        </div>
    </div>
    <div class="b1"  style="height:360px">
        <div class="b1L" style="width:98%;height:360px">
            <h3 class="box-title">CPC案卷包更新
                <a href="#" id="cpcNow" class="btn btn-info  btn-sm" style="margin-left:100px" onclick="return cpcNow();">今天上传</a>
                <a href="#" id="cpcPre" class="btn btn-secondary  btn-sm" style="margin-left:20px" onclick="return cpcPre();">昨天上传</a>
                <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_Export"
                   onclick="doExport('grid2','CPC案卷包更新记录.xls')">导出Excel</a>
                <div style="display: inline;float:right">
                    <div class="mini-combobox Query_Field Browse_Query" id="comFieldFour" style="width:100px" data="[{id:'All',
                            text:'全部属性'},
                            {id:'SHENQINGH',text:'专利号'},{id:'FAMINGMC',text:'发明名称'},
                            {id:'SHENQINGLX',text:'专利类型'},{id:'NEIBUBH',text:'内部编号'},{id:'FAMINGRXM',text:'发明人'}]" value="All" id="Field"></div>
                    <input class="mini-textbox Query_Field Browse_Query" style="width:150px" id="QueryTextPatentFour"/>
                    <a class="mini-button mini-button-success" onclick="doQuery('grid2','QueryTextPatentFour','comFieldFour');"
                       id="ClientBrowse_Query">模糊搜索</a>
                    <a class="mini-button mini-button-danger"  id="Browse_Reset"
                       onclick="reset('QueryTextPatentFour','comFieldFour')">重置条件</a>
                </div>
            </h3>
            <div class="mini-datagrid" style="margin:2px;width:99%;height:295px" id="grid2"
                 autoload="false"url="/workNumber/getCPC" pageSize="8" sortField="UploadTime" sortOrder="desc">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div field="SHENQINGH" align="center" headerAlign="center" width="120">专利号</div>
                    <div field="FAMINGMC" align="center" headerAlign="center" width="200">发明名称</div>
                    <div field="SHENQINGLX" align="center" headerAlign="center" type="comboboxcolumn" width="80">专利类型
                        <input property="editor" class="mini-combobox" data="types" />
                    </div>
                    <div field="NEIBUBH" align="center" headerAlign="center" width="200">内部编号</div>
                    <div field="FAMINGRXM" align="center" headerAlign="center" width="150">发明人</div>
                    <div field="SHENQINGR" align="center" headerAlign="center"  width="120"  dataType="date"
                         dateformat="yyyy-MM-dd">申请日期</div>
                    <div field="UploadTime" align="center" headerAlign="center" width="150" dataType="date"
                         dateformat="yyyy-MM-dd HH:mm:ss">上传时间</div>
                </div>
            </div>
        </div>
    </div>
</div><!--container-fluid_END-->
</body>
<script type="text/javascript">
    mini.parse();
    var types = [{id:0,text:'发明专利'}, {id:1,text:'实用新型'}, {id:2,text:'外观设计'}];
    var grid0=mini.get('grid0');
    var grid1=mini.get('grid1');
    var grid2=mini.get('grid2');
    var grid3=mini.get('grid3');
    var param = {};

    function pantentNow(){
        grid0.load({'Date':'Now'});
        param = {};
        param['Date'] = 'Now';
        $('#pantentNow').addClass('btn-info').removeClass('btn-secondary');
        $('#pantentPre').addClass('btn-secondary').removeClass('btn-info');
        return false;
    }
    function pantentPre(){
        grid0.load({'Date':'Pre'});
        param = {};
        param['Date'] = 'Pre';
        $('#pantentPre').addClass('btn-info').removeClass('btn-secondary');
        $('#pantentNow').addClass('btn-secondary').removeClass('btn-info');
        return false;
    }
    function tzsNow(){
        grid1.load({'Date':'Now'});
        param = {};
        param['Date'] = 'Now';
        $('#tzsNow').addClass('btn-info').removeClass('btn-secondary');
        $('#tzsPre').addClass('btn-secondary').removeClass('btn-info');
        return false;
    }
    function tzsPre(){
        grid1.load({'Date':'Pre'});
        param = {};
        param['Date'] = 'Pre';
        $('#tzsPre').addClass('btn-info').removeClass('btn-secondary');
        $('#tzsNow').addClass('btn-secondary').removeClass('btn-info');
        return false;
    }
    function cpcNow(){
        grid2.load({'Date':'Now'});
        param = {};
        param['Date'] = 'Now';
        $('#cpcNow').addClass('btn-info').removeClass('btn-secondary');
        $('#cpcPre').addClass('btn-secondary').removeClass('btn-info');
        return false;
    }
    function cpcPre(){
        grid2.load({'Date':'Pre'});
        param = {};
        param['Date'] = 'Pre';
        $('#cpcPre').addClass('btn-info').removeClass('btn-secondary');
        $('#cpcNow').addClass('btn-secondary').removeClass('btn-info');
        return false;
    }
    function addFeeNow(){
        grid3.load({'Date':'Now'});
        param = {};
        param['Date'] = 'Now';
        $('#addFeeNow').addClass('btn-info').removeClass('btn-secondary');
        $('#addFeePre').addClass('btn-secondary').removeClass('btn-info');
        return false;

    }
    function addFeePre(){
        grid3.load({'Date':'Pre'});
        param = {};
        param['Date'] = 'Pre';
        $('#addFeePre').addClass('btn-info').removeClass('btn-secondary');
        $('#addFeeNow').addClass('btn-secondary').removeClass('btn-info');
        return false;
    }
    addFeeNow();
    tzsNow();
    cpcNow();
    pantentNow();
    function doQuery(grid,QueryText,comField,type) {
        var grids = mini.get(grid);
        var arg = {};
        var bs = [];
        var cs = [];
        var txtQuery = mini.get('#' + QueryText);
        var comField = mini.get('#' + comField);
        var word = txtQuery.getValue();
        var field = comField.getValue();
        if (word) {
            if (field == "All") {
                var datas = comField.getData();
                for (var i = 0; i < datas.length; i++) {
                    var d = datas[i];
                    var f = d.id;
                    if (f == "All") continue;
                    var kWork = f + '=' + word;
                    if (cs.indexOf(kWork) == -1) {
                        var op = {field: f, oper: 'LIKE', value: word};
                        cs.push(op);
                    }
                }
            } else {
                var op = {field: field, oper: 'LIKE', value: word};
                cs.push(op);
            }
        }
        if (cs.length > 0) arg["Query"] = mini.encode(cs);
        arg["Date"] = param['Date'];
        grids.load(arg);
    }
    function doExport(grids, fileName) {
        var grid = mini.get(grids);
        var excel = new excelData(grid);
        excel.export(fileName);
    }
    function reset(QueryText,comField){
        var txtQuery = mini.get('#' + QueryText);
        var comField = mini.get('#' + comField);
        txtQuery.setValue(null);
        comField.setValue('All');
    }
</script>

</html>
