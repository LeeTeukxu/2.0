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
    <link href="/css/index.css?Key=${version}" rel="stylesheet" type="text/css"/>
    <script src="/js/jquery.min.js"></script>
<#--    <script src="/js/jquery-ui.min.js"></script>-->
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
</head>
<body style="overflow-y: auto;overflow-x: hidden;">
<div style="width:100%;height:10px"></div>
<div class="bottommain bottomain2" style="margin-left: -30px;margin-top: -10px; height: 100%">
    <div class="b1">
        <div class="b1L">
            <h3 class="box-title">常用快捷栏

                <a id="cmdP" class="mini-button" style="color:blue; font-size: 8px;font-family:楷体;
                float:right; display: none"
                   plain="true"
                   iconCls="icon-wait">待更新官费(<span style="color:black" id="pNum"></span>)
                </a>
                <a id="cmdG" class="mini-button" style="color:green; font-size: 8px;font-family:楷体;
                float:right; display: none" plain="true"
                   iconCls="icon-wait">待更新专利(<span id="gNum" style="color:black"></span>)
                </a>
            </h3>
            <div class="qustnav">
                <#list menus as menu>
                    <a class="kli" href="#" url="${menu.url}">
                        <p class="qsnimg"><img src="${menu.icon}"></p>
                        <p class="qsnname">${menu.title}</p>
                    </a>
                </#list>
            </div>
        </div>
        <div class="b1R">
            <h3 class="box-title">登录信息</h3>
            <div class="z1">
                <div class="huanjielist">
                    <div class="huanjielistL">
                        <img src="/appImages/point.png">
                    </div>
                    <div class="huanjielistR">
                        公司名称:<a  onclick="companyClick()" href="javascript:void(0)">${UserInfo.companyName!}</a>
                    </div>
                </div>
                <div class="huanjielist">
                    <div class="huanjielistL">

                        <img src="/appImages/point.png">
                    </div>
                    <div class="huanjielistR">
                        所属部门:${UserInfo.depName!}
                    </div>
                </div>
                <div class="huanjielist">
                    <div class="huanjielistL">
                        <img src="/appImages/point.png">
                    </div>
                    <div class="huanjielistR">
                        登录人员:${UserInfo.userName!}(<span style="color: green">${UserInfo.roleName!}</span>)
                    </div>
                </div>
                <div class="huanjielist">
                    <div class="huanjielistL">
                        <img src="/appImages/point.png">
                    </div>
                    <div class="huanjielistR">
                        使用期限:${Days}
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#if CaseNum gt 10>
        <div class="b3" style="height:550px">
            <div class="b3item"  style="height:550px">
                <h3 class="box-title">专利工作量汇总统计
                    <div class="mini-combobox" style="width:100px" data="[{id:2021,text:'2021年'},{text:'2022年',id:2022}]" value="${Year}"
                         onvaluechanged="yearChanged"></div>
                </h3>
                <div class="b3itembox" style="height:530px">
                    <div class="mini-datagrid" id="workGrid" style="width:100%;height:510px" autoload="false"
                         onload="afterLoad" ondrawcell="drawCell" pageSize="20"></div>
                </div>
            </div>
        </div>
    </#if>
    <div class="b3" >
        <div class="b3item">
            <h3 class="box-title">待处理专利事务</h3>
            <Div class="b3itembox">
                <div class="mini-fit">
                    <div class="mini-tabs" style="width:100%;height:420px" id="tabs" onactivechanged="tabChanged">
                        <div title="未答复的补正通知书">
                            <div class="mini-toolbar" style="border: none">
                                <table style="width: 100%">
                                    <tr>
                                        <td width="95%">
                                            <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_Export"
                                               onclick="doExport('gridOne','未答复的补正通知书记录.xls')">导出Excel</a>
                                        </td>
                                        <td style="white-space: nowrap">
                                            <div class="mini-combobox Query_Field Browse_Query" id="comFieldOne" style="width:100px" data="[{id:'All',text:'全部属性'},{id:'SHENQINGH',text:'专利编号'},{id:'ZHUANLIMC',text:'专利名称'},{id:'TZSMC',text:'通知书名称'}]" value="All" id="Field"></div>
                                            <input class="mini-textbox Query_Field Browse_Query" style="width:150px" id="QueryTextOne"/>
                                            <a class="mini-button mini-button-success" onclick="doQuery('gridOne','QueryTextOne','comFieldOne');"
                                               id="ClientBrowse_Query">模糊搜索</a>
                                            <a class="mini-button mini-button-danger"  id="Browse_Reset"
                                               onclick="reset('QueryTextOne','comFieldOne')">重置条件</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="mini-datagrid" style="width:100%;height:88%" id="gridOne"
                                 url="/workBench/getLastMonthTZS?Type=补正通知书&Days=30"
                                 autoload="true">
                                <div property="columns">
                                    <div type="indexColumn"></div>
                                    <div field="SHENQINGH" align="center" headerAlign="center">专利编号</div>
                                    <div field="ZHUANLIMC" align="center" headerAlign="center" width="300">专利名称</div>
                                    <div field="NEIBUBH" align="center" headerAlign="center" width="300">内部编号</div>
                                    <div field="TZSMC" align="center" headerAlign="center" width="120">通知书名称</div>
                                    <div field="FAWENRQ" align="center" headerAlign="center" dataType="date"
                                         dateFormat="yyyy-MM-dd" width="120">发文日期
                                    </div>
                                    <div field="JIAJI" align="center" headerAlign="center"
                                         type="checkboxcolumn">是否加急
                                    </div>
                                    <div field="QX" align="center" headerAlign="center" dataType="date"
                                         dateFormat="yyyy-MM-dd" width="120">截止日期
                                    </div>
                                    <div field="DAYS" align="center" headerAlign="center">剩余天数</div>
                                </div>
                            </div>
                        </div>
                        <div title="未答复的审查意见通知书" name="YJ">
                            <div class="mini-toolbar" style="border: none">
                                <table style="width: 100%">
                                    <tr>
                                        <td style="width: 95%">
                                            <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_Export"
                                               onclick="doExport('yjGrid','未答复的审查意见通知书记录.xls')">导出Excel</a>
                                        </td>
                                        <td style="white-space: nowrap">
                                            <div class="mini-combobox Query_Field Browse_Query" id="comFieldTwo" style="width:100px" data="[{id:'All',
                                            text:'全部属性'},
                                            {id:'SHENQINGH',text:'专利编号'},{id:'ZHUANLIMC',text:'专利名称'},{id:'TZSMC',text:'通知书名称'}]" value="All" id="Field"></div>
                                            <input class="mini-textbox Query_Field Browse_Query" style="width:150px" id="QueryTextTwo"/>
                                            <a class="mini-button mini-button-success" onclick="doQuery('yjGrid','QueryTextTwo','comFieldTwo');"
                                               id="ClientBrowse_Query">模糊搜索</a>
                                            <a class="mini-button mini-button-danger"  id="Browse_Reset"
                                               onclick="reset('QueryTextTwo','comFieldTwo')">重置条件</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="mini-datagrid" id="yjGrid" style="width:100%;height:88%"
                                 url="/workBench/getLastMonthTZS?Type=审查意见通知书&Days=30"
                            autoload="false">
                                <div property="columns">
                                    <div type="indexColumn"></div>
                                    <div field="SHENQINGH" align="center" headerAlign="center">专利编号</div>
                                    <div field="ZHUANLIMC" align="center" headerAlign="center" width="300">专利名称</div>
                                    <div field="NEIBUBH" align="center" headerAlign="center" width="300">内部编号</div>
                                    <div field="TZSMC" align="center" headerAlign="center" width="120">通知书名称</div>
                                    <div field="FAWENRQ" align="center" headerAlign="center" dataType="date"
                                         dateFormat="yyyy-MM-dd" width="120">发文日期
                                    </div>
                                    <div field="JIAJI" align="center" headerAlign="center"
                                         type="checkboxcolumn">是否加急
                                    </div>
                                    <div field="QX" align="center" headerAlign="center" dataType="date"
                                         dateFormat="yyyy-MM-dd" width="120">截止日期
                                    </div>
                                    <div field="DAYS" align="center" headerAlign="center">剩余天数</div>
                                </div>
                            </div>
                        </div>
                        <div title='近期待缴官费' name="GF">
                            <div class="mini-toolbar" style="border: none">
                                <table style="width: 100%">
                                    <tr>
                                        <td style="width: 95%">
                                            <a class="mini-button mini-button-info" id="InVestigateOpinionNotice_Export"
                                               onclick="doExport('gfGrid','近期待缴官费记录.xls')">导出Excel</a>
                                        </td>
                                        <td style="white-space: nowrap">
                                            <div class="mini-combobox Query_Field Browse_Query" id="comFieldThree" style="width:100px" data="[{id:'All',
                                text:'全部属性'},
                                {id:'AppNo',text:'专利编号'},{id:'FAMINGMC',text:'专利名称'},{id:'ClientName',text:'客户名称'},{id:'Mobile',text:'客户联系方式'},{id:'ANJIANYWZT',text:'专利状态'},{id:'CostName',text:'费用名称'}]" value="All" id="Field"></div>
                                            <input class="mini-textbox Query_Field Browse_Query" style="width:150px" id="QueryTextThree"/>
                                            <a class="mini-button mini-button-success" onclick="doQuery('gfGrid','QueryTextThree','comFieldThree');"
                                               id="ClientBrowse_Query">模糊搜索</a>
                                            <a class="mini-button mini-button-danger"  id="Browse_Reset"
                                               onclick="reset('QueryTextThree','comFieldThree')">重置条件</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="mini-datagrid" style="width:100%;height:88%" id="gfGrid" onDrawCell="onDraw"
                                 url="/workBench/getLastMonthGovFee?Days=30" autoload="false">
                                <div property="columns">
                                    <div type="indexColumn"></div>
                                    <div field="AppNo" align="center" headerAlign="center">专利编号</div>
                                    <div field="FAMINGMC" align="center" headerAlign="center" width="300">专利名称</div>
                                    <div field="ClientName" align="center" headerAlign="center" width="200">客户名称</div>
                                    <div field="Mobile" align="center" headerAlign="center">联系方式</div>
                                    <div field="ANJIANYWZT" align="center" headerAlign="center">专利状态</div>
                                    <div field="CostName" align="center" headerAlign="center" width="120">费用名称</div>
                                    <div field="Amount" align="center" headerAlign="center" width="120">缴费金额</div>
                                    <div field="LimitDate" align="center" headerAlign="center" dataType="date"
                                         dateFormat="yyyy-MM-dd" width="120">最后缴费日期
                                    </div>
                                    <div field="Days" align="center" headerAlign="center" width="80">剩余天数</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Div>
        </div>
    </div>
    <div class="b2" style="float: left;width: 100%;margin-top: -18px">
        <div class="b2item">
            <h3 class="box-title">专利类型</h3>
            <Div class="b2itembox">
                <div class="chartbox">
                    <div class="charboxL">
                        <div id="main" style="width:100%;height:280px; "></div>
                    </div>
                    <div class="charboxR">
                        <#list typeData as data>
                            <div class="charboxRitem">
                                <p class="charnamenum">${data.value}</p>
                                <p class="charname">${data.name}</p>
                            </div>
                        </#list>
                    </div>
                </div>
            </Div>
        </div>
        <div class="b2item">
            <h3 class="box-title">补正通知书</h3>
            <Div class="b2itembox">
                <div class="charboxL">
                    <div id="main2" style="width:100%;height:280px;"></div>
                </div>
                <div class="charboxR">
                    <#list buzhengData as data>
                        <div class="charboxRitem">
                            <p class="charnamenum">${data.value}</p>
                            <p class="charname">${data.name}</p>
                        </div>
                    </#list>
                </div>
            </Div>
        </div>
        <div class="b2item">
            <h3 class="box-title">审查意见通知书</h3>
            <Div class="b2itembox">
                <div class="charboxL">
                    <div id="main3" style="width:100%;height:280px;"></div>
                </div>
                <div class="charboxR">
                    <#list shenchaData as data>
                        <div class="charboxRitem">
                            <p class="charnamenum">${data.value}</p>
                            <p class="charname">${data.name}</p>
                        </div>
                    </#list>
                </div>
            </Div>
        </div>
    </div sty>

</div><!--container-fluid_END-->
</body>

<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/script" type="text/javascript" src="/js/popper.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="textjavascript" src="/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="/js/custom.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/echarts.min.js"></script>

<script type="text/javascript">
    mini.parse();
    var workGrid=mini.get('#workGrid');
    $(function () {
        $('.kli').on('click', function () {
            var obj = $(this);
            var url = obj.attr('url');
            var title = obj.find('.qsnname').text();
            window.parent.addParent(url, title);
        });
        <#if CaseNum gt 10 >
        getMonthWork(${Year});
        </#if>
    });
    function companyClick(){
        window.parent.addParent('/companyConfig/index?pageName=CompanyConfig&MenuID=158','公司信息维护');
    }
    var allColumns=[];
    function getMonthWork(year){
        allColumns=[];
        $.getJSON('/workBench/getWorkColumn?Year='+year,{},function(result){
            if(result.success){
                var columns=[{field:'WorkMonth',header:'月份',width:100,headerAlign:'center',
                    align:'center'}];
                allColumns.push(columns[0]);
                var cols=result.data ||{};
                var deps=cols["Deps"] ||[];
                delete cols.Deps;
                for(var n=0;n<deps.length;n++){
                    var name=deps[n];
                    var col={field:name,header:name,headerAlign:'center',columns:[]};
                    var childs=cols[name] || [];
                    for(var i=0;i<childs.length;i++){
                        var child=childs[i];
                        var cc={field:child,header:child,width:80,headerAlign:'center',align:'center'};
                        col.columns.push(cc);
                        allColumns.push(cc);
                    }
                    columns.push(col);
                }
                workGrid.setColumns(columns);
                workGrid.setUrl('/workBench/getData');
                workGrid.load({Year:year});
            }
        })
    }
    function yearChanged(e){
        getMonthWork(e.value);
    }
    function afterLoad(e){
        var grid=e.sender;
        var rows=grid.getData();
        var cells=[];
        for(var n=0;n<rows.length;n++){
            var row=rows[n];
            var workMonth=row["WorkMonth"];
            var columns=grid.getColumns();

            var rowIndex=grid.indexOf(row);
            if(workMonth.toString().indexOf("部门汇总")>-1) {
                for(var i=0;i<columns.length;i++){
                    var column=columns[i];
                    var cols=column.columns || [];
                    if(cols.length>1){
                        var cc=column.columns[0];
                        var x=findColumnIndex(cc);
                        var o={rowIndex:rowIndex,columnIndex:x,colSpan:cols.length};
                        cells.push(o);
                    }
                }
            }
            else if(workMonth.toString().indexOf("合计")>-1){
                for(var i=0;i<columns.length;i++){
                    var column=columns[i];
                    var cols=column.columns || [];
                    if(cols.length>0){
                        var cc=column.columns[0];
                        var x=findColumnIndex(cc);
                        var o={rowIndex:rowIndex,columnIndex:x,colSpan:allColumns.length-1};
                        cells.push(o);
                        break;
                    }
                }
            }
        }
        if(cells.length>0)
        {
            grid.mergeCells(cells);
        }

    }
    function findColumnIndex(col){
        for(var i=0;i<allColumns.length;i++){
            var all=allColumns[i];
            if(all.field==col.field) return i;
        }
        return 0;
    }
    function drawCell(e){
        var field=e.field;
        if(field!='WorkMonth'){
            var row=e.record;
            var val=parseFloat(row[field] || 0);
            if(val==0)e.cellHtml="";
            else {
                var dd=parseFloat(row[field]);
                e.cellHtml=dd.toFixed(2);
            }
        }
    }
</script>
<!--专利类型-->
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main'));
    var scale = 1;
    var echartData = ${types};
    var rich = {
        yellow: {
            color: "#ffc72b",
            fontSize: 20 * scale,
            padding: [5, 4],
            align: 'center'
        },
        total: {
            color: "#000",
            fontSize: 20 * scale,
            align: 'center'
        },
        white: {
            color: "#fff",
            align: 'center',
            fontSize: 14 * scale,
            padding: [10, 0]
        },
        blue: {
            color: '#49dff0',
            fontSize: 16 * scale,
            align: 'center'
        },
        hr: {
            borderColor: '#0b5263',
            width: '100%',
            borderWidth: 1,
            height: 0,
        }
    }
    var option = {
        title: {
            text: '全部专利',
            left: 'center',
            top: '53%',
            padding: [1, 0],
            textStyle: {
                color: '#666',
                fontSize: 20 * scale,
                align: 'center'
            }
        },
        legend: {
            selectedMode: false,
            formatter: function (name) {
                var total = 0;
                echartData.forEach(function (value, index, array) {
                    total += value.value;
                });
                return '{total|' + total + '}';
            },
            data: [echartData[0].name],
            left: 'center',
            top: 'center',
            icon: 'none',
            align: 'center',
            textStyle: {
                color: "#fff",
                fontSize: 12 * scale,
                rich: rich
            }
        },
        series: [{
            type: 'pie',
            radius: ['52%', '60%'],
            hoverAnimation: false,
            color: ['#1b9cee', '#ff8651', '#ffc35a'],
            label: {
                show: true
            },
            data: echartData
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    window.addEventListener('resize', function () {
        myChart.resize();
    });
</script>
<!--补正-->
<script type="text/javascript">
    var myChart2 = echarts.init(document.getElementById('main2'));
    var scale = 1;
    var echartData2 = ${buzhengs};
    var rich2 = {
        yellow: {
            color: "#ffc72b",
            fontSize: 12 * scale,
            padding: [5, 4],
            align: 'center'
        },
        total: {
            color: "#000",
            fontSize: 20 * scale,
            align: 'center'
        },
        white: {
            color: "#fff",
            align: 'center',
            fontSize: 14 * scale,
            padding: [10, 0]
        },
        blue: {
            color: '#49dff0',
            fontSize: 16 * scale,
            align: 'center'
        },
        hr: {
            borderColor: '#0b5263',
            width: '100%',
            borderWidth: 1,
            height: 0,
        }
    }
    var option2 = {
        title: {
            text: '全部',
            left: 'center',
            top: '53%',
            padding: [1, 0],
            textStyle: {
                color: '#666',
                fontSize: 20 * scale,
                align: 'center'
            }
        },
        legend: {
            selectedMode: false,
            formatter: function (name) {
                var total = 0; //各科正确率总和
                echartData2.forEach(function (value, index, array) {
                    total += value.value;
                });
                return '{total|' + total + '}';
            },
            data: [echartData2[0].name],
            left: 'center',
            top: 'center',
            icon: 'none',
            align: 'center',
            textStyle: {
                color: "#fff",
                fontSize: 12 * scale,
                rich: rich2
            },
        },
        series: [{
            name: '全部',
            type: 'pie',
            radius: ['52%', '60%'],
            hoverAnimation: false,
            color: ['#1b9cee', '#ff8651'],
            label: {
                normal: {
                    formatter: function (params, ticket, callback) {
                        var total = 0; //考生总数量
                        echartData2.forEach(function (value, index, array) {
                            total += value.value;
                        });
                        percent = ((params.value / total) * 100).toFixed(1);
                        return params.name;
                    },
                    rich: rich2
                },
            },
            data: echartData2
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart2.setOption(option2);
    window.addEventListener('resize', function () {
        myChart2.resize();
    });
</script>
<!--审查意见-->
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart3 = echarts.init(document.getElementById('main3'));

    // 指定图表的配置项和数据
    var scale = 1;
    var echartData3 = ${shenchas};
    var rich3 = {
        yellow: {
            color: "#ffc72b",
            fontSize: 12 * scale,
            padding: [5, 4],
            align: 'center'
        },
        total: {
            color: "#000",
            fontSize: 20 * scale,
            align: 'center'
        },
        white: {
            color: "#fff",
            align: 'center',
            fontSize: 14 * scale,
            padding: [10, 0]
        },
        blue: {
            color: '#49dff0',
            fontSize: 16 * scale,
            align: 'center'
        },
        hr: {
            borderColor: '#0b5263',
            width: '100%',
            borderWidth: 1,
            height: 0,
        }
    }
    var option3 = {
        // backgroundColor: '#031f2d',
        title: {
            text: '全部',
            left: 'center',
            top: '53%',
            padding: [1, 0],
            textStyle: {
                color: '#666',
                fontSize: 20 * scale,
                align: 'center'
            }
        },
        legend: {
            selectedMode: false,
            formatter: function (name) {
                var total = 0; //各科正确率总和
                var averagePercent; //综合正确率
                echartData3.forEach(function (value, index, array) {
                    total += value.value;
                });
                return '{total|' + total + '}';
            },
            data: [echartData3[0].name],
            left: 'center',
            top: 'center',
            icon: 'none',
            align: 'center',
            textStyle: {
                color: "#fff",
                fontSize: 12 * scale,
                rich: rich3
            },
        },
        series: [{
            name: '全部',
            type: 'pie',
            radius: ['52%', '60%'],
            hoverAnimation: false,
            color: ['#1b9cee', '#ff8651'],
            label: {
                normal: {
                    formatter: function (params, ticket, callback) {
                        var total = 0; //考生总数量
                        var percent = 0; //考生占比
                        echartData3.forEach(function (value, index, array) {
                            total += value.value;
                        });
                        percent = ((params.value / total) * 100).toFixed(1);
                        return params.name;
                    },
                    rich: rich3
                },
            },
            data: echartData3
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart3.setOption(option3);
    window.addEventListener('resize', function () {
        myChart3.resize();
    });
    function doQuery(grid,QueryText,comField) {
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
        arg["Key"] = generateTimeReqestNumber();
        grids.load(arg);
    }
    function reset(QueryText,comField){
        var txtQuery = mini.get('#' + QueryText);
        var comField = mini.get('#' + comField);
        txtQuery.setValue(null);
        comField.setValue('All');
    }
    function onDraw(e) {
        var field = e.field;
        var record = e.record;
        var val = e.value;
        if (field == "ClientName") {
            var clientId = record["ClientID"];
            var type = parseInt(record["cootype"]);
            if (val == undefined){
                e.cellHtml = "";
            }else e.cellHtml = '<a href="#" onclick="showClient(' + "'" + clientId + "'," +type+ ')">' + val + '</a>';
        }
    }
    function showClient(clientId,type) {
        mini.open({
            url:'/work/clientInfo/browse?Type=' + type + '&ClientID=' + clientId + "&Role=CW&mode=look",
            width:'100%',
            height:'100%',
            title:'浏览客户资料',
            showModal:true,
            ondestory:function () {

            }
        });
        window.parent.doResize();
    }
</script>

<script type="text/javascript">
    mini.parse();
    function tabChanged(e){
        var tab=e.tab;
        var name=tab.name || "";
        if(name=="YJ"){
            mini.get('yjGrid').load();
        }
        else if(name=="GF"){
            mini.get('gfGrid').load();
        }
    }
    $(function () {
        $(".mini-tabs-body").css("overflow","hidden");
    })

    function pad2(n) { return n < 10 ? '0' + n : n }

    function generateTimeReqestNumber() {
        var date = new Date();
        return date.getFullYear().toString() + pad2(date.getMonth() + 1) + pad2(date.getDate()) + pad2(date.getHours()) + pad2(date.getMinutes()) + pad2(date.getSeconds());
    }
    function doExport(grids, fileName) {
        var grid = mini.get(grids);
        var excel = new excelData(grid);
        excel.export(fileName);
    }

    function refreshNumber(){
        var cmdP=mini.get('#cmdP');
        var cmdG=mini.get('#cmdG');
        var txtP=$('#pNum');
        var txtG=$('#gNum');
        var url='/workBench/getNumbers';
        $.getJSON(url,{},function(result){
            if(result.success){
                var obj=result.data || {};
                var pN=parseInt(obj.P || 0);
                if(pN==0) cmdP.hide(); else {
                    cmdP.show();
                    txtP.html(pN);
                }
                var gN=parseInt(obj.G || 0);
                if(gN==0) cmdG.hide(); else {
                    cmdG.show();
                    txtG.html(gN);
                }
            } else {
                cmdP.hide();
                cmdG.hide();
            }
        })
    }
    setInterval(refreshNumber,120000);
    refreshNumber();
</script>
</html>
