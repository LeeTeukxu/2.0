<#include "/shared/dialog.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript">
        var IsUsable = [{id: true, text: '可用'}, {id: false, text: '不可用'}];
    </script>
    <table style="width:100%;height: 100px;overflow: hidden" class="layui-table" id="Table1">
        <tr>
            <td class="showlabel">参数内容:</td>
            <td>
                <input class="mini-textbox" id="name" name="name" style="width: 100%;" required="true"/>
            </td>
        </tr>
        <tr>
            <td class="showlabel">
                是否显示:
            </td>
            <td>
                <input class="mini-combobox" name="canUse" id="canUse" data="IsUsable" style="width: 100%" value="true"
                       required="true">
                <input class="mini-hidden" id="id" name="id"/>
            </td>
        </tr>
        <tr>
            <td class="showlabel">
                备注:
            </td>
            <td>
               <textarea style="width:100%;height:50px" name="memo" class="mini-textarea"></textarea>
            </td>
        </tr>
        <tr>
            <td>

            </td>
            <td style="text-align: center">
                <a class="mini-button mini-button-primary" id="ParmeterConfig_Save" style="width:120px"
                     onclick="ParmeterConfig_Save()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a class="mini-button mini-button-danger" id="ParmeterConfig_Save" style="width:120px"
                   onclick="Close()">关闭</a>
            </td>
        </tr>
    </table>
    <script type="text/javascript">
        mini.parse();
        var WinType = "${WinType}";
        $('body').css('overflow','hidden');
        function ParmeterConfig_Save() {
            var form = new mini.Form('#Table1');
            var Data = form.getData();

            form.validate();
            if (form.isValid()) {
                form.loading("保存中......");
                var arg = {
                    Entity: mini.encode(Data),
                    Name: WinType
                };
                var url = "/InvoiceApplication/invoice/saveParameter";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data = res.data || {};
                            mini.alert('参数配置信息保存成功', '系统提示', function () {
                                form.setData(data);
                            });
                        } else {
                            mini.alert(res.Message);
                        }
                        form.unmask();
                    }
                );
            }
        }
        function Close(){
            CloseOwnerWindow('no');
        }
    </script>
</@layout>