<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <table class="layui-table" style="width:100%;height:100%;padding:0px;margin:0px" cellpadding="0px"
           cellspacing="0px" id="mainForm">
        <tbody>
        <tr>
            <td>清单名称</td>
            <td colspan="3">
                <input class="mini-textbox" style="width:100%;height:28px;line-height:28px" required="true"
                       id="feeName" name="feeName"/>
                <input class="mini-hidden" name="feeItemId"  />
                <input class="mini-hidden" name="shenQingh"  />
                <input class="mini-hidden" name="id" />
            </td>
        </tr>

        <tr>
            <td>缴费金额</td>
            <td>
                <input class="mini-spinner" maxValue="999999"  required="true"  name="money" style="width:100%;height:28px;line-height:28px"  />
            </td>
            <td>缴费期限</td>
            <td>
                <input class="mini-datepicker" dateformat="yyyy-MM-dd" required="true"  style="width:100%;height:28px;line-height:28px"  name="jiaoFeiDate"/>
            </td>
        </tr>
        <tr>
            <td>缴费人发票抬头</td>
            <td >
                <input class="mini-textbox" style="width:100%;height:28px;line-height:28px" required="true"  name="tickHeader"/>
            </td>
            <td>缴费人信用代码<br/>或身份证号码</td>
            <td >
                <input class="mini-textbox" style="width:100%;height:28px;line-height:28px"  name="creditCode"/>
            </td>
        </tr>
        <tr>
            <td>发票接收单位</td>
            <td colspan="3">
                <input class="mini-treeselect" style="width:100%;height:28px;line-height:28px" required="true" expandOnload="true"
                       onbeforenodeselect="beforeSelect" id="tickReceiver" allowInput="true" valueFromSelect="true"
                       name="tickReceiver" allowEdit="true" valueFromSelect="true" url="/systems/client/getClientTree" onvaluechanged="SearchLinkMan"/>
            </td>
        </tr>
        <tr>
            <td>发票接收人</td>
            <td>
<#--                <input class="mini-textbox" required="true"   style="width:100%;height:28px;line-height:28px"  name="linkMan"/>-->
                <input class="mini-combobox" name="linkMan" id="linkMan" valueField="ID" textField="Name"
                        style="width: 98%;"
                       allowInput="true" virtualScroll="true" onvaluechanged="SearchLinkManInfo"/>
            </td>
            <td>联系电话</td>
            <td>
                <input class="mini-textbox" required="true"  style="width:100%;height:28px;line-height:28px"  name="linkPhone" id="linkPhone"/>
            </td>
        </tr>
        <tr>
            <td>联系地址</td>
            <td colspan="3">
                <input class="mini-textbox" style="width:100%;height:28px;line-height:28px" required="true"  name="address" id="address"/>
            </td>
        </tr>
        <tr>
            <td>备注信息</td>
            <td colspan="3">
                <input class="mini-textbox" style="width:100%;height:28px;line-height:28px"   name="memo"/>
            </td>
        </tr>
        <tr>
            <td colspan="4" style="text-align: center">
                <button class="layui-btn layui-btn-normal" style="margin-right: 200px;" onclick="doSave();">保存信息</button>
                <button class="layui-btn layui-btn-danger" onclick="doClose()">关闭退出</button>
            </td>
        </tr>
        <tr>
            <td colspan="4"></td>
        </tr>
        </tbody>
    </table>
</@layout>
<@js>
    <script type="text/javascript">
        mini.parse();
        var obj=${obj};
        var mode='${mode}';0
        obj.createTime=new Date(obj.createTime);
        obj.jiaoFeiDate=new Date(obj.jiaoFeiDate);
        $(function(){
            var form=new mini.Form('#mainForm');
            form.setData(obj);
            var linkMan=mini.get('linkMan');
            var ClientID=mini.get('tickReceiver').getValue();
            if (ClientID!="") {
                linkMan.setUrl("/work/otherOfficeFee/getLinkMan?ClientID=" + mini.get('tickReceiver').getValue());
            }
        });
        function doSave(){
            var form=new mini.Form('#mainForm');
            form.validate();
            if(form.isValid())
            {
                var data=form.getData();
                var url='/watch/addToPayForWait/save';
                $.post(url,{Data:mini.encode(data),Type:'${type}'},function(result){
                    if(result.success){
                        if(mode=='Add')
                        {
                            mini.alert('添加到缴费清单成功!','系统提示',function(){
                                CloseOwnerWindow('ok');
                            });
                        } else {
                            mini.alert('清单缴费信息保存成功!','系统提示',function(){
                                CloseOwnerWindow('ok');
                            });
                        }
                    } else {
                        mini.alert(result.message || "添加到缴费清单失败，请稍候重试。");
                    }
                });
            } else {
                mini.alert('数据录入不完整，保存操作被中止!');
            }
        }
        function doClose(){
            CloseOwnerWindow('no');
        }
        function beforeSelect(e){
            e.cancel=!e.isLeaf;
        }
        function SearchLinkMan() {
            var linkMan=mini.get('linkMan');
            var ClientID=mini.get('tickReceiver').getValue();
            if (ClientID!="") {
                linkMan.setUrl("/work/otherOfficeFee/getLinkMan?ClientID=" + mini.get('tickReceiver').getValue());
            }
        }
        function SearchLinkManInfo() {
            var url='/work/otherOfficeFee/getLinkManInfo';
            $.post(url,{LinkID:mini.get('linkMan').getValue()},function (text) {
                var res=mini.decode(text);
                if (res.success){
                    var data=res.data || {};
                    if (data.length>0){
                        for (var i=0;i<data.length;i++){
                            mini.get('address').setText(data[i].Address);
                            mini.get('address').setValue(data[i].Address);
                            mini.get('linkPhone').setText(data[i].LinkPhone);
                            mini.get('linkPhone').setValue(data[i].LinkPhone);
                        }
                    }
                }
            })
        }
    </script>
</@js>