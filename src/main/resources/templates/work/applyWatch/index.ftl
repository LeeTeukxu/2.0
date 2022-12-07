<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript">
        var types = [
            {id:0,text:'发明专利'}, {id:1,text:'新型专利'}, {id:2,text:'外观专利'}
        ];
        var feeTypes=[
            {id:1,text:'减免85%'},{id:2,text:'减免70%'},{id:3,text:'不减免'}
        ];
    </script>
    <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
        <div region="center" style="overflow:hidden">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button mini-button-success" iconCls="icon-add" id="FeeItemApplyConfig_Add"
                               onclick="addWatchItem">添加监控项目</a>
                            <a class="mini-button mini-button-success" iconCls="icon-remove"
                               id="FeeItemApplyConfig_Remove"
                               onclick="removeItem">删除监控项目</a>
                            <span class="separator FeeItemApplyConfig_Query"></span>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field  FeeItemApplyConfig_Query"  id="comField" style="width:100px" data="[{id:'All',
                            text:'全部属性'},
                            {id:'FAMINGMC',text:'专利名称'},{id:'shenqingh',text:'专利申请号'},{id:'ANJIANYWZT',text:'专利状态'}]"
                                 value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field  FeeItemApplyConfig_Query" style="width:150px" id="QueryText" />
                            <a class="mini-button mini-button-success" onclick="doQuery();"
                               id="FeeItemApplyConfig_Query">模糊搜索</a>
                            <a class="mini-button mini-button-danger" onclick="reset()"
                               id="FeeItemApplyConfig_Reset">重置</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="FeeItem_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" sortorder="asc" sortfield="SHENQINGR"
                     ondrawcell="onDraw" allowresize="true" url="/watch/applyWatch/getData" multiselect="true"
                     pagesize="20"
                     sizelist="[5,10,20,50,100]" autoload="true" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="SHENQINGH" width="120" headerAlign="center" allowsort="true">专利申请号</div>
                        <div field="SHENQINGLX" width="100" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types" />
                        </div>
                        <div field="ANJIANYWZT" width="100" headerAlign="center" allowsort="true">专利状态</div>
                        <div field="FAMINGMC" width="300" headeralign="center" allowsort="true">专利名称</div>
                        <div field="SHENQINGR" width="120" headerAlign="center" Align="center" dataType="date"
                             dateFormat="yyyy-MM-dd" allowsort="true">申请日期</div>
                        <div field="FeeType" width="100" headerAlign="center" align="center"  type="comboboxcolumn"
                        >费减类型
                            <input property="editor" class="mini-combobox" data="feeTypes" />
                        </div>
                        <div field="LASTDATE" width="150" headerAlign="center" Align="center" dataType="date"
                             dateFormat="yyyy-MM-dd" allowsort="true">缴费截止时间</div>
                        <div field="CreateTime" width="120" headerAlign="center" align="center"  dataType="date"
                             dateFormat="yyyy-MM-dd" allowsort="true">创建日期</div>
                        <div field="CREATEMAN" width="120" headerAlign="center" align="center"
                             allowsort="true">创建人员</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <form action="/Fee/FeeItem/ExportYear" method="post" style="display:none" id="ExportYearForm">
        <input type="hidden" name="KKRows" id="KKRows"/>
    </form>
    <form id="ExportForm" style="display:none" method="post" action="/Common/ExportExcelService/Export">
        <input type="hidden" id="Data" name="Data"/>
    </form>
    <div id="selectExportColumn" class="mini-window" title="选择导出字段" style="width:420px;height:250px;"
         showshadow="true" showfooter="true"
         footerstyle="text-align: right;margin-right: 25px;" allowresize="true" allowdrag="true">
        <div property="footer">
            <a class="mini-button mini-button-success"
               href="javascript:reqExoprt('View_t_3','费用监控','费用监控.xls', 0);">确定</a>
            <a class="mini-button mini-button-danger" href="javascript:cancelSelectExportColumn();">取消</a>
            <a class="mini-button mini-button-info" href="javascript:opsSelectExportColumn();">反选</a>
        </div>
    </div>
    <script type="text/javascript" src="/js/work/feeItem/FeeCommon.js"></script>
    <script type="text/javascript" src="/js/work/feeItem/FeeItem.js"></script>
<script type="text/javascript">
    mini.parse();
    var tip = new mini.ToolTip();
    var grid=null;

    $(function(){
        grid=mini.get('FeeItem_datagrid');
    });
    function expand(e) {
        var btn = e.sender;
        var display = $('#p1').css('display');
        if (display == "none") {
            btn.setIconCls("panel-collapse");
            btn.setText("展开");
            $('#p1').css('display', "block");

        } else {
            btn.setIconCls("panel-expand");
            btn.setText("折叠");
            $('#p1').css('display', "none");
        }
        fit.setHeight('100%');
        fit.doLayout();
    }
    function doHightSearch(){
        var arg = {};
        grid=mini.get('FeeItem_datagrid');
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
    function doQuery() {
        grid=mini.get('FeeItem_datagrid');
        var txtQuery = mini.get('#QueryText');
        var comField = mini.get('#comField');
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
        if(cs.length>0)arg["Query"] = mini.encode(cs);
        grid.load(arg);
    }
    function doFixedQuery(field,oper,val){
        var arg = {};
        grid=mini.get('FeeItem_datagrid');
        var cs=[{field:field,oper:oper,value:val}];
        arg["Query"]=mini.encode(cs);
        grid.load(arg);
    }
    function reset(){
        var txtQuery = mini.get('#QueryText');
        if(txtQuery)
        {
            txtQuery.setValue('');
            doQuery();
        }
    }
    function addWatchItem(){
        mini.open({
            url:'/watch/addApplyWatchItem/index',
            width:'100%',
            height:'100%',
            title:'选择并设置监控项目',
            showModal:true,
            ondestroy:function(){
                grid=mini.get('FeeItem_datagrid');
                grid.reload();
            }
        });
    }
    function removeItem(){
        grid=mini.get('FeeItem_datagrid');
        var rows=grid.getSelecteds();
        if(rows.length==0){
            mini.alert('请选择要删除的监控项目!');
            return ;
        }
        var ids=[];
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            var shenqingh=row["SHENQINGH"];
            if(shenqingh) ids.push(shenqingh);
        }
        if(ids.length>0){
            mini.confirm('删除选择的监控项目,也会删除已生成的监控记录，是否继续？','删除提示',function(btn){
                if(btn=='ok')g();
            });

        } else {
            mini.alert('请选择要删除的监控项目!');
            return ;
        }
        function g(){
            var url='/watch/applyWatch/removeAll';
            $.post(url,{IDS:mini.encode(ids)},function(result){
                if(result.success){
                    mini.alert('选择的记录删除成功!','系统提示',function(){
                        grid.reload();
                    });
                } else {
                    mini.alert(result.message || "删除失败，请稍候重试！");
                }
            });
        }
    }
</script>
</@layout>