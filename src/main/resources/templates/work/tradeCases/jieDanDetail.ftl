<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar" style="padding:5px;">
        <a class="mini-button" iconCls="icon-add" onclick="add">新增</a>
        <a class="mini-button" iconCls="icon-remove" onclick="remove">删除</a>
        <a class="mini-button" iconCls="icon-save" onclick="save">确认接单</a>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="jdGrid" style="width:100%;height: 100%;"
             url="/work/jieDanDetail/getData?CasesID=${CasesID}&UserID=${UserID}"
             autoload="true"
             showPager="true" allowCellEdit="true" allowCellSelect="true" allowCellValid="true"
             oncellendedit="endEdit" oncellbeginedit="beginEdit">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div name="yname" displayField="yname" field="yname" headerAlign="center" width="200"
                     vtype="required">业务名称
                    <input property="editor" class="mini-lookup" style="width:200px;" textField="YName"
                           valueField="YName"
                           popupWidth="auto" popup="#gridPanel" grid="#datagrid1" onshowpopup="onShowPopup"/>
                </div>
                <div field="ytype" headerAlign="center" align="center">类型</div>
                <div field="num" headerAlign="center" vtype="required">数量
                    <input property="editor" class="mini-spinner" maxValue="10000000" minValue="1"/>
                </div>
                <div field="progress" type="comboboxcolumn" headerAlign="center" align="center" visible="false">
                    撰写进度
                    <input property="editor" class="mini-combobox" data="zxjds" />
                </div>
                <div field="techMan" type="treeselectcolumn" headerAlign="center" align="center">
                    专利撰写人
                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                           textField="Name" valueField="FID" parentField="PID"/>
                </div>
                <div field="finishTime" headerAlign="center" dataType="date"
                     dateFormat="yyyy-MM-dd" vtype="required">预计完成日期
                    <input property="editor" class="mini-datepicker"/>
                </div>
                <div field="AllowNum" visible="false"></div>
                <div field="yid" visible="false"></div>
            </div>
        </div>
    </div>

<div id="gridPanel" class="mini-panel" title="header" iconCls="icon-add" style="width:480px;height:150px;display:none"
     showToolbar="true" showCloseButton="true" showHeader="false" bodyStyle="padding:0" borderStyle="border:0">
    <div id="datagrid1" class="mini-datagrid" style="width:100%;height:100%;"
         borderStyle="border:0" showPageSize="false" showPageIndex="false" autoload="true"
         multiSelect="false" url="/work/casesTech/getCasesFreeNums?CasesID=${CasesID}">
        <div property="columns">
            <div type="checkcolumn">#</div>
            <div field="YName" width="240" headerAlign="center">业务名称</div>
            <div field="YType" width="120" headerAlign="center">分类</div>
            <div field="Num" width="80" headerAlign="center">可领取数量</div>
        </div>
    </div>
</div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#jdGrid');
        var grid1 = mini.get('#datagrid1');
        var userId=${UserID};
        var zxjds = [{ id: '预备处理', text: '预备处理' }, { id: '正在处理', text: '正在处理' }, { id: '完成初稿', text: '完成初稿' }, { id:
                    '等待定稿', text: '等待定稿' }, { id: '定稿', text: '定稿' }, { id: '提交申报', text: '提交申报' }];
        function add() {
            grid.validate();
            if (grid.isValid()) {
                    var row = {techMan:${UserID},casesid:'${CasesID}'};
                    grid.addRow(row);
                    grid.validateRow(row);
            } else {
                mini.alert('完善上一条记录以后，才能进行新增操作。');
            }
        }

        function onShowPopup() {
            grid1.reload();
        }

        function endEdit(e) {
            var field = e.field;
            var record = e.record;
            var sourceRecord = grid1.getSelected();
            if (field == 'yname' && sourceRecord) {
                var g = {};
                g["ytype"] = sourceRecord.YType;
                g["yid"] = sourceRecord.YID;
                g["AllowNum"] = sourceRecord.Num;
                grid.updateRow(record, g);
            }
        }

        function beginEdit(e) {
            var field = e.field;
            var editor = e.editor;
            var record = e.record;
            var techMan=parseInt(record["techMan"] || 0);
            if(techMan>0)
            {
                if(techMan!=userId){
                    e.cancel=true;
                }
                else
                {
                    var id=parseInt(record["id"] || 0);
                    if(id==0)
                    {
                        e.cancel=false;
                    }
                    else {
                        if(field=='yname'){
                            e.cancel=true;
                        }
                    }
                }
            }
            if (field == "num") {
                editor.setMaxValue(parseInt(record.AllowNum));
                editor.setMinValue(1);
            }
        }
        function save(){
            grid.validate();
            if (grid.isValid()) {
                var rows = grid.getChanges();
                if (rows.length == 0) return;

                function g(rows){
                    var url = '/work/jieDanDetail/saveAll';
                    $.post(url,{Data:mini.encode(rows)},function(result){
                        if(result.success){
                            mini.alert('接单成功!');
                            grid.reload();
                        } else mini.alert(result.message || "接单失败，请稍候重试!");
                    });
                }
                var xRow=mini.clone(rows);
                mini.confirm('确认要接单，相关数据将不允许修改，是否继续?','接单提示',function(action){
                    if(action=='ok') g(xRow);
                });
            }else mini.alert('请完善认领记录后再进行保存操作!');

        }
        function remove(){
            var row=grid.getSelected();
            if(row){
                function g()
                {
                    var id=parseInt(row["id"] || 0);
                    if(id==0){
                        grid.removeRow(row);
                        grid.acceptRecord(row);
                    } else {
                        var url='/work/jieDanDetail/remove';
                        $.post(url,{ID:id},function(res){
                            if(res.success){
                                mini.alert('选择记录删除成功!','系统提示',function(){
                                    grid.removeRow(row);
                                    grid.acceptRecord(row);
                                });
                            }else {
                                mini.alert(res.message || "删除失败，请稍候重试!");
                            }
                        });
                    }
                }
                mini.confirm('确认删除选择的记录?','删除提示',function(action){
                    if(action=='ok'){
                        g(row);
                    }
                });
            }
        }
    </script>
</@js>
