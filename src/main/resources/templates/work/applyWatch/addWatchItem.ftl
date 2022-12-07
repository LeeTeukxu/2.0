<#include "/shared/layout.ftl">
<@layout>
<link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
<script type="text/javascript" src="/js/layui/layui.js"></script>
    <style>
        .nCell{
            height:24px;
            line-height:24px
        }
    </style>
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
                            <a class="mini-button mini-button-success" iconCls="icon-add" onclick="confirm()">确认添加</a>
                            <span class="separator FeeItem_Config"></span>
                            <a class="mini-button mini-button-danger" iconCls="icon-edit" onclick="changeMode
                            (1)">减免85%</a>
                            <a class="mini-button mini-button-info" iconCls="icon-edit" onclick="changeMode
                            (2)">减免70%</a>
                            <a class="mini-button mini-button-info" iconCls="icon-edit"  onclick="changeMode(3)">不减免</a>
                            <span class="separator FeeItem_Config"></span>
                            <a class="mini-button mini-button-normal"  iconCls="icon-ok"  onclick="selectAll">全选</a>
                            <a class="mini-button mini-button-normal"  iconCls="icon-no"  onclick="selectNot">反选</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <div class="mini-combobox Query_Field"  id="comField" style="width:150px"
                                 data="[{id:'All',text:'全部属性'},{id:'FAMINGMC',text:'专利名称'},{id:'shenqingh',text:'专利申请号'},
                            {id:'shenqingrxm',text:'专利申请人'},{id:'famingrxm',text:'发明人'}]" value="All" id="Field"></div>
                            <input class="mini-textbox Query_Field" style="width:150px" id="QueryText" />
                            <a class="mini-button mini-button-success" onclick="doQuery();"
                               id="PatentInfoBrowse_Query">模糊搜索</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit" id="fitt">
                <div id="FeeItem_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;" sortorder="asc" sortfield="SHENQINGR" allowresize="true"
                     multiselect="true" pagesize="20" url="/watch/addApplyWatchItem/getData"
                     sizelist="[5,10,20,50,100]" autoload="true" ondrawcell="drawCell"
                     allowCellSelect="true" allowCellEdit="true">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="FeeType" width="150" type="comboboxcolumn" headerAlign="center" align="center">减免类型
                            <input property="editor" class="mini-combobox" data="feeTypes" valueFromSelect="true" />
                        </div>
                        <div field="SHENQINGH" width="150" headerAlign="center" allowsort="true">专利申请号</div>
                        <div field="SHENQINGLX" width="100" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types" />
                        </div>
                        <div field="FAMINGMC" width="300" headeralign="center" allowsort="true">专利名称</div>
                        <div field="FAMINGRXM" width="150" headerAlign="center" Align="center" allowsort="true">发明人姓名</div>
                        <div field="SHENQINGRXM" width="150" headerAlign="center" align="center" allowsort="true">申请人姓名</div>
                        <div field="SHENQINGR" width="150" headerAlign="center" Align="center" dataType="date" dateFormat="yyyy-MM-dd" allowsort="true">申请日期</div>
                        <div field="ACTION" width="300" headerAlign="center" align="center" allowsort="true"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script type="text/javascript">
    mini.parse();
    var grid=mini.get('FeeItem_datagrid');
    var txtQuery = mini.get('#QueryText');
    var comField = mini.get('#comField');
    var layer=null;
    layui.use(['layer'],function() {
        layer = layui.layer;
    });
    function doQuery() {
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
    function drawCell(e){
        var record=e.record;
        var field=e.field;
        if(field=="ACTION"){
            var pp=[];
            if(record.JL){
                pp.push('<a href="javascript:doShow(\''+record.JL+'\');">&nbsp;缴纳申请费通知书&nbsp;' +
                '</a>');
            }
            else if(record.FY){
                pp.push('<a  style="text-decoration:underline"  href="javascript:doShow(\''+record.FY+'\');">&nbsp;费用减缴审批通知书&nbsp;' +
                        '</a>');
            }
            e.cellHtml=pp.join("&nbsp;&nbsp;&nbsp;");
        }
    }
    function doShow(id){
        $.getJSON('/watch/addApplyWatchItem/getAllImages',{'tongzhisbh':id},function(result){
            var isOK=parseInt(result.status);
            if(isOK==1){
                layer.photos({
                    photos: result,
                    anim: 5,
                    success:function(){
                        for(var i=0;i<3;i++)
                        {
                            changeImage(1);
                        }
                    }
                });
            } else {
                var msg=result.message ||"无法加载通知书附件。";
                layer.alert(msg);
            }
        });
    }
    function changeImage(delta){
        var imagep = $(".layui-layer-phimg").parent().parent();
        var image = $(".layui-layer-phimg").parent();
        var h = image.height();
        var w = image.width();
        if (delta > 0) {
            if (h < (window.innerHeight+500)) {
                h = h * 1.1;
                w = w * 1.1;
            }
        } else if (delta < 0) {
            if (h > 100) {
                h = h * 0.95;
                w = w * 0.95;
            }
        }
        imagep.css("top", (window.innerHeight - h) / 2);
        imagep.css("left", (window.innerWidth - w) / 2);
        image.height(h);
        image.width(w);
        imagep.height(h);
        imagep.width(w);
    }
    $(document).on("mousewheel DOMMouseScroll", ".layui-layer-phimg img", function (e) {
        var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) || // chrome & ie
                (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1)); // firefox
        changeImage(delta);
    });
    function changeMode(mode){
        var rows=grid.getSelecteds();
        if(rows.length==0){
            mini.alert('请选择要设置的记录行。');
            return ;
        }
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            grid.updateRow(row,{FeeType:mode});
        }
    }
    function selectAll(){
        var rows=grid.getData();
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            grid.select(row);
        }
    }
    function selectNot(){
        var rows=grid.getData();
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            var isSelected=grid.isSelected(row);
            isSelected=!isSelected;
            if(isSelected==true)grid.select(row);else grid.deselect(row);
        }
    }
    function confirm(){
        var rows=grid.getSelecteds();
        var res=[];
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            var shenqingh=row["SHENQINGH"];
            var feeType=parseInt(row["FeeType"] || 0);
            if(feeType && shenqingh){
                var obj={};
                obj.FeeType=feeType;
                obj.SHENQINGH=shenqingh;
                res.push(obj);
            }
        }
        if(res.length>0){
            mini.confirm('确认要将项目添加到费用监控列表吗?','系统提示',function(btn){
                if(btn=='ok'){
                    g(res);
                }
            });
            function g(data){
                var url='/watch/applyWatch/saveAll';
                $.post(url,{Data:mini.encode(data)},function(result){
                    if(result.success){
                        mini.alert('保存成功!','系统提示',function(){
                            CloseOwnerWindow('ok');
                        });
                    } else {
                        mini.alert(result.message || "保存失败，请稍候重试。");
                    }
                });
            }
        } else {
            mini.alert('无满足条记录!');
        }
    }
</script>
</@layout>