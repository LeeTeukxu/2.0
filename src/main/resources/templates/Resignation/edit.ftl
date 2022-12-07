<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <div class="mini-toolbar" id="mainToolbar" style="text-align:right;padding-right:25px;">
        <a class="mini-button" iconcls="icon-save" id="ResignationEdit_Save">保存</a>
    </div>
    <form id="ResignationForm" action="/" method="post">
        <table style="width: 100%; height: 100%" class="layui-table">
            <tr>
                <td>离职人员：</td>
                <td>
                    <input class="mini-treeselect" valuefield="FID" parentfield="PID" textfield="Name"
                           url="/systems/dep/getAllLoginUsersByDep" id="resignation" name="resignation" style="width: 100%;"
                           resultastree="false" required="true" onbeforenodeselect="onbeforenodeselectresignation"/>
                    <input class="mini-hidden" id="resignationRecordId" name="resignationRecordId"/>
                    <input class="mini-hidden" id="createMan" name="createMan"/>
                    <input class="mini-hidden" id="createTime" name="createTime"/>
                </td>
                <td>交接人员：</td>
                <td>
                    <input class="mini-treeselect" valuefield="FID" parentfield="PID" textfield="Name"
                           url="" id="transfer" name="transfer" style="width: 100%;"
                           resultastree="false" required="true" onbeforenodeselect="onbeforenodeselecttransfer"/>
                </td>
            </tr>
            <tr>
                <td>离职日期：</td>
                <td>
                    <input class="mini-datepicker" id="resignationTime" name="resignationTime" style="width: 100%;"
                           format="yyyy-MM-dd H:mm:ss" timeformat="H:mm:ss" showtime="true" required="true"/>
                </td>
                <td>离职原因：</td>
                <td colspan="5">
                    <textarea class="mini-textarea" style="width:100%;height:50px" id="resignationLeaving" name="resignationLeaving"></textarea>
                </td>
            </tr>
        </table>
    </form>
    <script type="text/javascript">

        mini.parse();
        var mode = "${Mode}";
        var formData = ${LoadData};

        $(function () {
            $("#ResignationEdit_Save").click(function () {
                var form = new mini.Form('#ResignationForm');
                var Data = form.getData();
                form.validate();
                if (form.isValid()) {
                    var arg = {
                        Data: mini.encode(Data),
                    };
                    $.post("/Resignation/save", arg,
                        function (result) {
                            var res = mini.decode(result);
                            if (res['success']) {
                                mini.alert('离职人员信息保存成功', '系统提示', function () {
                                    var returnData = result.data || {};
                                    form.setData(returnData);
                                });
                            } else {
                                mini.alert(res.message || "保存失败，请稍候重试!");
                            }
                            form.unmask();
                        }
                    );
                }
            });

            if (mode == "Edit") {
                var ResignationForm = new mini.Form('#ResignationForm');
                formData.createTime=new Date(formData.createTime);
                formData.resignationTime=new Date(formData.resignationTime)
                var tree = mini.get('transfer');
                tree.setUrl('/systems/dep/getAllLoginUsersByDep');
                ResignationForm.setData(formData);
            }
        });

        function ResignationEdit_Save() {
            var form = new mini.Form('#ResignationForm');
            var Data = form.getData();
            form.validate();
            if (form.isValid()) {
                var arg = {
                    Data: mini.encode(Data),
                };
                $.post("/Resignation/save", arg,
                    function (result) {
                        var res = mini.decode(result);
                        if (res['success']) {
                            mini.alert('离职人员信息保存成功', '系统提示', function () {
                                var returnData = result.data || {};
                                form.setData(returnData);
                            });
                        } else {
                            mini.alert(res.message || "保存失败，请稍候重试!");
                        }
                        form.unmask();
                    }
                );
            }
        }

        function onbeforenodeselectresignation(e){
            //禁止选中父节点
            if(e.isLeaf==false) {
                e.cancel=true;
            } else {
                debugger;
                e.cancel=false;
                var UserID = e.selected.FID;
                var tree = mini.get('transfer');
                // tree.load({'UserID':UserID});
                tree.setUrl('/systems/dep/getAllLoginUsersByDepNotSelf?UserID=' + UserID);
            }
        }

        function onbeforenodeselecttransfer(e){
            //禁止选中父节点
            if(e.isLeaf==false) {
                e.cancel=true;
            }
        }
    </script>
</@layout>