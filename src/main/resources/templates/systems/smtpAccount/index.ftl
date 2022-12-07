<#include "/shared/dialog.ftl">
<@layout>
    <script type="text/javascript" src="/js/emailAddress/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/emailAddress/autoMail.js"></script>
    <style type="text/css">

        /* mailBox */
        #mailBox{background:#fff;border:1px solid #ddd;padding:3px 5px 5px;position:absolute;z-index:9999;display:none;-webkit-box-shadow:0px 2px 7px rgba(0, 0, 0, 0.35);-moz-box-shadow:0px 2px 7px rgba(0, 0, 0, 0.35);}
        #mailBox p{width:100%;margin:0;padding:0;height:20px;line-height:20px;clear:both;font-size:12px;color:#ccc;cursor:default;}
        #mailBox ul{padding:0;margin:0;}
        #mailBox li{font-size:12px;height:22px;line-height:22px;color:#939393;font-family:'Tahoma';list-style:none;cursor:pointer;overflow:hidden;}
        #mailBox .cmail{color:#000;background:#e8f4fc;}

        #userName{width:99.4%;height: 25px;border-radius: 5px;border: 1px solid #ddd;}
        .mini-icon{
            min-height:15px
        }
    </style>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>邮箱信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 0px; float: right; font-size: 16px">
                <a class="mini-button mini-button-primary" id="employeBindEmail_Save" onclick="employeBindEmail_Save()">保存</a>
            </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width: 99%; padding-left: 10px">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="邮箱绑定设置" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <div id="editform">
                        <table style="width: 100%; height: 100%">
                            <tr>
                                <td class="showlabel">邮箱</td>
                                <td>
                                    <input type="text" id="userName" name="userName" onkeyup="emailRelated(this.value)" onblur="emailRelated(this.value)" />
                                    <div class="demo">
                                    </div>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">邮箱密码</td>
                                <td >
                                    <input class="mini-textbox" id="password" name="password"  style="width:60%" />
                                    <span class="mini-button-icon mini-iconfont icon-tip" style="position: relative;top:6px;"></span>
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a target="_blank" href="javascript:void(0)" onclick="clickhref(this)">如何设置密码？</a>
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">邮箱昵称</td>
                                <td >
                                    <input class="mini-textbox" id="nickName" name="nickName" style="width:100%" />
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">smtp服务器</td>
                                <td >
                                    <input class="mini-textbox" id="server" name="server" style="width:100%" />
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">ssl加密
                                </td>
                                <td>
                                    <div class="mini-checkbox" id="ssl" name="ssl" style="width:100%;"
                                         inputStyle="min-height:12px"></div>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel">端口</td>
                                <td>
                                    <input class="mini-textbox" id="port" name="port" style="width:100%" />
                                    <input class="mini-hidden" id="id" name="id">
                                    <input class="mini-hidden" id="canUse" name="canUse">
                                    <input class="mini-hidden" id="userId" name="userId">
                                </td>
                            </tr>
                            <tr>
                                <td class="showlabel">公司默认帐户
                                </td>
                                <td>
                                    <div class="mini-checkbox" id="ssl" name="companyDefault" style="width:100%;"
                                         inputStyle="min-height:12px"></div>
                                </td>
                                <td class="blank"><span style="color: red">&nbsp;</span></td>
                                <td class="showlabel"></td>
                                <td>

                                </td>
                            </tr>
<#--                            <tr>-->
<#--                                <td>-->
<#--                                    <span class="mini-button-icon mini-iconfont icon-tip" style="position: relative;top:6px;"></span>-->
<#--                                </td>-->
<#--                                <td colspan="3">-->
<#--                                    邮箱服务器示例:smtp.qq.com; QQ邮箱请设置ssl加密方式，端口为587-->
<#--                                </td>-->
<#--                                <td class="blank"><span style="color: red">&nbsp;</span></td>-->
<#--                            </tr>-->
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var formData=${LoadData};
         var form = new mini.Form('#editform');
         form.setData(formData);
        //由于使用了邮件补全功能，文本框的设置有点变化导致数据无法加载。暂时采用手动填充数据的方式实现。
        if (formData.userName!=null) {
            document.getElementById("userName").value = formData.userName;
        }
        function employeBindEmail_Save() {
            var form = new mini.Form('#editform');
            var Data = form.getData();
            form.validate();
            if (form.isValid()){
                form.loading("保存中...");
                var arg={
                    Data:mini.encode(Data),
                    UserName:$("#userName").val()
                };
                var url="/systems/smtpAccount/save";
                $.post(url,arg,
                    function (text) {
                        var res=mini.decode(text);
                        if (res.success){
                            var data=res.data || {};
                            mini.alert('邮箱信息设置成功!','系统提示',function () {
                                form.setData(data);
                            });
                        }else {
                            mini.alert(res.message);
                        }
                        form.unmask();
                    }
                )
            }
        }

        $(document).ready(function(){
            $('#userName').autoMail({
                emails:['zhiqin.onaliyun.com','sina.com','qq.com','tom.com','163.com','139.com','gmail.cn']
            });
        });
        
        function emailRelated(val) {
            var port={
                "zhiqin.onaliyun.com":25,
                "sina.com":25,
                "qq.com":587,
                "tom.com":25,
                "163.com":25,
                "139.com":25,
                "gmail.com":465
            };

            var emailreg=/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+(\.[a-zA-Z0-9_-]+)+$/;
            if (emailreg.test(val)==true){
                //是一个完整并正确的E-MAIL地址
                var result=val.substring(val.indexOf('@')+1,val.length);
                mini.get("server").setValue("smtp."+result);
                for (var keys in port){
                    if(result==keys){
                        mini.get("port").setValue(port[keys]);
                    }
                    if (result=="qq.com"){
                        mini.get('ssl').setChecked(true);
                    }else{
                        mini.get('ssl').setChecked(false);
                    }
                }
            }
            else {
                mini.get("server").setValue("");
                mini.get("port").setValue("");
                mini.get('ssl').setChecked(false);
            }
        }

        function clickhref(obj) {
            obj.href="https://service.mail.qq.com/cgi-bin/help?subtype=1&&id=28&&no=1001256";
        }
    </script>
</@layout>
