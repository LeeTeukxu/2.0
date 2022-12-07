<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript">
    </script>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Table1">
                    <table style="width:100%;height: 100%">
                        <tr>
                            <td class="showlabel">发票号: </td>
                            <td>
                                <input class="mini-textbox" id="receiptNumber" name="receiptNumber" style="width:100%;"
                                       required="true" />
                            </td>
                            <td class="showlabel">
                                快递公司:
                            </td>
                            <td>
                                <input name="courierCompany" id="courierCompany" class="mini-combobox"
                                       textfield="text" valuefield="text" allowinput="true" style="width:100%;" />
                            </td>
                        </tr>
                        <tr>
                            <td class="showlabel">
                                快递号:
                            </td>
                            <td >
                                <input class="mini-textbox" id="expressNumber" name="expressNumber" style="width:100%;"/>
                                <input class="mini-hidden" id="invoiceApplicationId" name="invoiceApplicationId">
                            </td>
                            <td class="showlabel">
                                发票领用日期:
                            </td>
                            <td >
                                <input class="mini-datepicker" id="receiveTime" name="receiveTime" style="width:100%;"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="showlabel">
                                审核意见:
                            </td>
                            <td colspan="3">
                                <textarea class="mini-textarea" style="width:100%;height:70px" name="auditText"
                                          value="同意">同意</textarea>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" style="text-align:center">
                                <a class="mini-button mini-button-primary" style="width:100px" id="KuaiDiAndDiZhi_Save"
                                   onclick="KuaiDiAndDiZhi_Save()">确认提交</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                <a class="mini-button mini-button-danger" style="width:100px"
                                   onclick="CloseOwnerWindow('no')">关闭退出</a>
                            </td>
                        </tr>
                    </table>
                </div>

            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        $('body').css('overflow','hidden');
        var mode="${Mode}";
        var formData=${LoadData};
        if (mode=="saveKuaiDiXinXi"){
            var Table1 = new mini.Form('#Table1');
            formData.addTime=new Date(formData.addTime);
            formData.dateOfPayment=new Date(formData.dateOfPayment);
            formData.receiveTime=new Date(formData.receiveTime);
            Table1.setData(formData);
        }

        $(function () {
            mini.get('courierCompany').load("/systems/dict/getAllByDtId?dtId=18");
        });

        function drawCell() {

        }

        function KuaiDiAndDiZhi_Save(){
            var form = new mini.Form('#Table1');
            var Data = form.getData();

            form.validate();
            if (form.isValid()) {
                form.loading("保存中......");
                var arg={
                    Data: mini.encode(Data)
                };
                var url="/InvoiceApplication/invoice/updateExpressInfo";
                $.post(url, arg,
                    function (text) {
                        var res = mini.decode(text);
                        if (res.success) {
                            var data=res.data || {};
                            mini.alert('快递及发票信息保存成功','系统提示',function(){
                                form.setData(data);
                                CloseOwnerWindow('ok')
                            });
                        } else {
                            mini.alert(res.Message);
                        }
                        form.unmask();
                    }
                );
            }
        }
    </script>
</@layout>