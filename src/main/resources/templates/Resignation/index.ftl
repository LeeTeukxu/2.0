<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="center" showheader="false">
            <div class="mini-toolbar">
                <table style="width:100%">
                    <tr>
                        <td style="width:95%">
                            <a class="mini-button" iconcls="icon-add" id="Resignation_Add" onclick="addResignation">增加</a>
                            <a class="mini-button" iconcls="icon-edit" id="Resignation_Edit" onclick="editResignation">编辑</a>
                            <a class="mini-button" iconcls="icon-remove" id="Resignation_Remove" onclick="remove">删除</a>
                        </td>
                        <td style="white-space:nowrap;">
                            <a class="mini-button mini-button-danger" id="Resignation_HighQuery" onclick="expand">高级搜索</a>
                        </td>
                    </tr>
                </table>
                <div id="p1" style="border:1px solid #909aa6;border-top:0;height:150px;padding:5px;display:none">
                    <table style="width:100%;" id="highQueryForm">
                        <tr>
                            <td style="width:6%;padding-left:10px;">离职人：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="LZR"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">交接人：</td>
                            <td style="width:15%;"><input class="mini-textbox" data-oper="LIKE" name="JJR"
                                                          style="width:100%"/></td>
                            <td style="width:6%;padding-left:10px;">离职时间：</td>
                            <td style="width:15%;">
                                <input name="ResignationTime" class="mini-datepicker" datatype="date" dateformat="yyyy-MM-dd"
                                       data-oper="GE" style="width:100%"/>
                            </td>
                            <td style="width:6%;padding-left:10px;">到：</td>
                            <td style="width:15%;">
                                <input name="ResignationTime" data-oper="LE" class="mini-datepicker" datatype="date"
                                       dateformat="yyyy-MM-dd" style="width:100%"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:6%;padding-left:10px;" colspan="8">离职原因：</td>
                            <td style="width:15%;">
                                <input name="ResignationLeaving" class="mini-textbox" data-oper="LIKE" style="width:100%"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-fit">
                <div id="datagrid1" class="mini-datagrid" style="width: 100%; height: 100%;"
                     allowresize="true" url="/Resignation/getData" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50,100,150,200]" sortfield="CreateTime" sortorder="desc"
                     autoload="true" onDrawCell="onDraw" onload="afterload">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="Resignation" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                            离职人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="Resignation" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="Transfer" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                            交接人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="Transfer" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="ResignationLeaving" width="120" headeralign="center" >离职原因</div>
                        <div field="ResignationTime" width="80" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">离职时间</div>
                        <div field="CreateMan" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                            创建人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="CreateMan" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                        <div field="CreateTime" width="80" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">创建时间</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid=mini.get('#datagrid1');

        $(function () {

        });

        function addResignation(){
            mini.open({
                url:'/Resignation/add',
                width:'100%',
                height:'100%',
                title:'新增人员离职',
                showModal:true,
                ondestroy:function(){
                    grid.reload();
                }
            });
        }

        function editResignation() {
            var row=grid.getSelected();
            if(!row) {
                mini.alert('请选择要编辑的退款信息!');
                return ;
            }
            var ID=row['ResignationRecordID'];
            mini.open({
                url:'/Resignation/edit?ResignationRecordID='+ID,
                width:'100%',
                height:'100%',
                title:'修改人员离职信息',
                showModal:true,
                ondestroy:function(){

                }
            })
        }

        function remove() {
            var rows = grid.getSelecteds();
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i]["InvoiceApplicationID"]);
            }
            if (ids.length == 0) {
                mini.alert('请选择要删除的记录!');
                return;
            }
            mini.confirm('确认要删除的开票信息数据?', '删除提示', function (act) {
                if (act === 'ok') {
                    var url="/InvoiceApplication/invoice/remove";
                    $.ajax({
                        contentType:'application/json',
                        method:'post',
                        url:url,
                        data:mini.encode(ids),
                        success:function (r) {
                            if (r['success']) {
                                mini.alert("删除成功！",'删除提示',function () {
                                    grid.reload();
                                });

                            } else mini.alert("删除失败！");
                        },
                        failure:function (error) {
                            mini.alert(error);
                        }
                    });
                }
            });
        }

        function expand(e) {
            e=e||{};
            var btn = e.sender;
            if(!btn){
                btn=mini.get('#Resignation_HighQuery');
            }
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
        function afterload() {
        }
        function refreshData(grid) {
            var pa = grid.getLoadParams();
            var pageIndex = grid.getPageIndex() || 0;
            var pageSize = grid.getPageSize() || 20;
            pa = pa || { pageIndex: pageIndex, pageSize: pageSize };
            grid.load(pa);
        }

        function doHightSearch(){
            var arg = {};
            var form = new mini.Form('#highQueryForm');
            var fields = form.getFields();
            var result = [];
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var val = field.getValue();
                if (val != null && val != undefined) {
                    if (val != '') {
                        var obj = {
                            field: field.getName(),
                            value: field.getValue(),
                            oper: field.attributes["data-oper"]
                        };
                        result.push(obj);
                    }
                }
            }
            arg["High"] = mini.encode(result);
            grid.load(arg);
        }
        
        function onDraw(e) {
            var field = e.field;
            var record = e.record;

        }
    </script>
</@layout>
