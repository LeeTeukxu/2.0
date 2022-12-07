<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-toolbar" style="padding:5px;">
        <a class="mini-button" iconCls="icon-ok" onclick="save">确认申报</a>
    </div>
    <div class="mini-fit">
        <div class="mini-datagrid" id="jdGrid" style="width:100%;height: 100%;"
             url="/work/shenBaoDetail/getData?CasesID=${CasesID}"
             autoload="true"
             showPager="true" allowCellEdit="true" allowCellSelect="true" allowCellValid="true"
             oncellendedit="endEdit" oncellbeginedit="beginEdit">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div name="yname" displayField="yname" field="yname" headerAlign="center" width="200"
                     vtype="required">业务名称
                </div>
                <div field="ytype" headerAlign="center" align="center">类型</div>
                <div field="num" headerAlign="center" vtype="required" align="center">数量</div>
                <div field="techMan" type="treeselectcolumn" headerAlign="center" align="center">
                    专利撰写人
                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                           textField="Name" valueField="FID" parentField="PID"/>
                </div>
                <div field="finishTime" headerAlign="center" dataType="date" dateFormat="yyyy-MM-dd" vtype="required">预计完成日期
                </div>
                <div field="shenBao" type="comboboxcolumn" headerAlign="center" align="center">
                    申报专利
                    <input property="editor" class="mini-combobox" data="sfsb" />
                </div>

                <div field="shenBaoMan" type="treeselectcolumn" headerAlign="center" align="center">
                    专利申报人
                    <input property="editor" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                           textField="Name" valueField="FID" parentField="PID"/>
                </div>
                <div field="shenBaoTime" headerAlign="center" dataType="date" dateFormat="yyyy-MM-dd">申报日期
                </div>
                <div field="yid" visible="false"></div>
            </div>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var grid = mini.get('#jdGrid');
        var grid1 = mini.get('#datagrid1');
        var sfsb=[{id:'同意申报',text:'同意申报'}];
        function onShowPopup() {
            grid1.reload();
        }

        function endEdit(e) {

        }

        function beginEdit(e) {
            var field = e.field;
            var editor = e.editor;
            var record = e.record;
            if (field == "num") {
                editor.setMaxValue(parseInt(record.AllowNum));
                editor.setMinValue(1);
            }
            else if(field=="yname" || field=="num"){
                var id=parseInt(record["id"] || 0);
                e.cancel=!(id==0);
            }
        }
        function save(){
            grid.validate();
            if (grid.isValid()) {
                var rows = grid.getChanges();
                if (rows.length == 0) return;

                function g(rows){
                    var url = '/work/shenBaoDetail/saveAll';
                    $.post(url,{Data:mini.encode(rows)},function(result){
                        if(result.success){
                            mini.alert('专利申报成功!','操作提示',function(){
                                CloseOwnerWindow('ok');
                            });
                        } else mini.alert(result.message || "接单失败，请稍候重试!");
                    });
                }
                var xRow=mini.clone(rows);
                mini.confirm('确认要进行专利申报？','操作提示',function(action){
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
            } else {

            }
        }
    </script>
</@js>
