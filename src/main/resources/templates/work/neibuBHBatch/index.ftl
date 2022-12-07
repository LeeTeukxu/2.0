<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/common/excelExport.js"></script>
    <script type="text/javascript">
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
    </script>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="center" style="overflow:hidden">
            <div class="mini-tabs" style="width:100%;height:100%">
                <div title="批量操作">
                    <div class="mini-toolbar">
                        <table style="width:100%">
                            <tr>
                                <td style="width:95%">
                                    <a class="mini-button" iconCls="icon-save" id="NBBatch_Save" onclick="doSave">更换人员</a>
                                </td>
                                <td style="white-space:nowrap;">
                                    <div class="mini-combobox Query_Field NBBHError_Query" id="comField" style="width:100px"
                                         data="[{id:'All',text:'全部属性'},{id:'ShenQingh',text:'专利号'},
{id:'FAMINGMC',text:'发明名称'},{id:'SHENQINGRXM',text:'申请人姓名'},{id:'SHENQINGLX',text:'专利类型'},{id:'NEIBUBH',text:'内部编号'}]" value="All"
                                         id="Field"></div>
                                    <input class="mini-textbox Query_Field NBBHError_Query" style="width:150px"
                                           id="QueryText"/>
                                    <a class="mini-button mini-button-success" onclick="doQuery();" id="NBBHError_Query">模糊搜索</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="mini-fit" id="fitt">
                        <div id="NBBatch_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" onrowclick="onRowClick"
                             frozenstartcolumn="0" sortorder="asc" sortfield="shenqingh"
                             ondrawcell="onDraw" allowresize="true" url="/work/patentInfo/getData?LongTime=1" multiselect="true"
                             pagesize="20"
                             sizelist="[5,10,20,50,100]" autoload="true" >
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div width="300" allowsort="true" headeralign="center" field="NEIBUBH" align="center" >内部编号</div>
                                <div align="center"  field="SHENQINGH" name="shenqingh" width="120" allowsort="true" headeralign="center"
                                     renderer="onZhanlihao">
                                    专利申请号
                                </div>
                                <div align="center"  field="FAMINGMC" name="FAMINGMC" width="200" allowsort="true" headeralign="center">
                                    专利名称
                                </div>
                                <div  align="center" field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                                    专利类型
                                    <input property="editor" class="mini-combobox" data="types"/>
                                </div>
                                <div field="SHENQINGR" name="shenqingr" width="80" allowsort="true" headeralign="center"
                                     datatype="date" dateformat="yyyy-MM-dd" align="center">
                                    申请日
                                </div>

                                <div align="center"  field="ANJIANYWZT" width="100" allowsort="true" headeralign="center">
                                    专利法律状态
                                </div>
                                <div align="center"  field="SHENQINGRXM" width="200" allowsort="true" headeralign="center">
                                    专利申请人
                                </div>
                                <div align="center"  field="FAMINGRXM" width="200" allowsort="true" headeralign="center">
                                    专利发明人
                                </div>
                                <div field="KH" width="200" headerAlign="center" align="center" allowsort="true">所属客户</div>
                                <div field="YW" width="100" headeralign="center" renderer="onRendererXS" align="center"
                                     allowsort="true">销售责任人
                                </div>
                                <div field="BH" width="120"  headerAlign="center" align="center" allowsort="true">立案编号</div>
                                <div field="JS" width="100" headerAlign="center" align="center" allowsort="true">代理责任人</div>
                                <div field="LC" width="100" headerAlign="center" align="center" allowsort="true">流程责任人</div>
                                <div field="DAILIJGMC" width="250" headerAlign="center" allowsort="true">代理机构</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div title="操作日志">
                    <div class="mini-datagrid" style="width:100%;height:100%" autoload="true">
                        <div property="columns">
                            <div type="indexcolumn"></div>
                            <div field="Source" align="center" headerAlign="center">原人员姓名</div>
                            <div field="Target" align="center" headerAlign="center">替换为</div>
                            <div field="CreateMan"  align="center" headerAlign="center">操作人员</div>
                            <div field="CreateTime"  align="center" headerAlign="center">操作日期</div>
                            <div field="Nums"  align="center" headerAlign="center">影响数量</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" title="更新专利权限归属人员" width="600" height="320" style="display:none" id="ReplaceWin">
        <span style="color:red;font-family: 黑体;font-size: 20px">本操作会对专利权限重新进行分配。请谨慎操作，
            由于该操作会对所有专利的内部编号进行遍历，需要较长时间，建议你在非高峰时段使用此功能。</span>
        <hr />
        <div class="mini-tabs">
            <div title="替换操作人员">
                <table class="layui-table" style="width:100%">
                    <tr>
                        <td style="width:100px">原人员姓名:</td>
                        <td>
                            <input  id="OldText" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                    allowInput="true"  textField="Name" valueField="FID" parentField="PID" style="width:100%" expandOnload="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px">替换人员:</td>
                        <td>
                            <input  id="NowText" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                    allowInput="true"  textField="Name" valueField="FID" parentField="PID" style="width:100%" expandOnload="true" valueFromSelect="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <button class="mini-button mini-button-info" onclick="postSave()">确认替换</button>
                            &nbsp;&nbsp;
                            <button class="mini-button mini-button-danger" onclick="doClose()">放弃关闭</button>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="替换客户">

            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var fit = mini.get('fitt');
        var tip = new mini.ToolTip();
        var grid = mini.get('NBBatch_datagrid');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
        var win=mini.get('#ReplaceWin');
        $(function () {
            $('#p1').hide();
            fit.setHeight('100%');
            fit.doLayout();
        });

        function expand(e) {
            var btn = e.sender;
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
            arg["High"] = mini.encode(result);
            grid.load(arg);
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
            if (cs.length > 0) arg["Query"] = mini.encode(cs);
            grid.load(arg);
        }
        function doExport() {
            var excel = new excelData(grid);
            excel.export("内部编号解析错误列表.xls");
        }
        function onDraw(e) {
            var field = e.field;
            var record = e.record;

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
        }
        function reset() {
            var form = new mini.Form('#highQueryForm');
            form.reset();
            mini.get('#QueryText').setValue(null);
            doQuery();
        }
        function doClose(){
            win.hide();
        }
        function ShowMemo(id, title) {
            mini.open({
                url: '/watch/feeWatch/addMemo?Type=Year&ID=' + id,
                showModal: true,
                width: 800,
                height: 400,
                title: "【" + title + "】的备注信息",
                onDestroy: function () {
                    grid.reload();
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
        function doSave(){
            mini.get('#OldText').setValue(null);
            mini.get('#NowText').setValue(null);
            win.show();
        }
        function postSave(){
            var oldText=mini.get('#OldText').getText();
            var nowText=mini.get('#NowText').getText();
            var arg={Old:oldText,Now:nowText};
            var url='/nbBatch/saveAll';
            mini.confirm('确认要将内部编号中的:【'+oldText+'】更新为:'+nowText+"吗？",'更新提示',function(act){
                if(act=='ok'){
                    $.post(url,arg,function(result){
                        if(result.success){
                            var data=result.data || {};
                            var replaceNum=parseInt(data.ReplaceNum || 0);
                            var okNum=parseInt(data.OKNum || 0);
                            if(okNum==0){
                                mini.alert('本次替换了:'+replceNum+'专利的内部编号记录!','系统提示',function(){
                                    win.hide();
                                    grid.reload();
                                });
                            } else {
                                mini.alert('本次共更新了'+okNum+"条专利权限记录!",'系统提示',function(){
                                    win.hide();
                                    grid.reload();
                                });
                            }
                        } else mini.alert(result.message || "更新失败，请稍候重试!");
                    })
                }
            });
        }
    </script>
</@layout>