<#include "/shared/dialog.ftl">
<@layout>
    <div class="baseinfo">
        <div style="width: 100%; overflow: hidden; margin-top: 10px">
            <h1>企业人员信息管理<span style="color: red; font-size: 14px; font-family: 仿宋"></span>
                <span style="margin-right: 5px; float: right; font-size: 16px">
                    <a class="mini-button mini-button-primary" id="CmdSave" onclick="doSave()">保存信息</a>
                </span>
            </h1>
        </div>
    </div>
    <div class="container" style="width:99%;overflow: hidden;">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="1、人员基本信息" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table id="Table1" style="width: 100%; height: 100%" >
                        <tr>
                            <td class="showlabel">人员编号</td>
                            <td>
                                <input class="mini-textbox" name="empCode" id="empCode" style="width:100%" required="true" />
                                <input class="mini-hidden"  name="empId" id="empId"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">性别</td>
                            <td>
                                <input class="mini-combobox" id="sex" name="sex" style="width: 100%"  required="true" url="/systems/dict/getAllByDtId?dtId=5"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">人员姓名</td>
                            <td>
                                <input class="mini-textbox" id="empName"  name="empName" style="width: 100%" required="true" / >
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">出生年月</td>
                            <td>
                                <input class="mini-datepicker" id="bornDate" required="true"  format="yyyy-MM-dd" name="bornDate" style="width:100%"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">民族</td>
                            <td>
                                <input class="mini-combobox" id="nation" url="/systems/dict/getAllByDtId?dtId=1" name="nation" required="true" style="width: 100%" required="true"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">学历</td>
                            <td>
                                <input class="mini-combobox" id="education"  url="/systems/dict/getAllByDtId?dtId=6" name="education" style="width: 100%" required="true" />
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">所属部门</td>
                            <td>
                                <input class="mini-treeselect" value="${depId}"  name="depId" style="width: 100%"  required="true" url="/systems/dep/getAllCanUse" valueField="depId" parentField="pid" textField="name" resultAsTree="false"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">行政区域</td>
                            <td>
                                <input class="mini-treeselect" required="true" id="areaCode" name="areaCode" style="width: 100%" valuefromSelect="true" url="/systems/dict/getAllByDtId?dtId=2" allowInput="true"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">联系电话</td>
                            <td>
                                <input class="mini-textbox"  vtype="maxLength:13" name="mobile" style="width: 100%"
                                       required="true" id="mobile"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">电子邮箱</td>
                            <td>
                                <input class="mini-textbox" name="email" style="width: 100%" vtype="email;maxLength:50"
                                       required="true" id="email"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">家庭住址</td>
                            <td colspan="4">
                                <input class="mini-textbox" name="homeAddress" style="width: 100%" id="homeAddress"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="2、登录帐户信息" width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table2">
                        <tr>
                            <td class="showlabel">登录帐号</td>
                            <td>
                                <input class="mini-textbox" name="loginCode" style="width: 100%" required="true"
                                       vtype="int;rangeLength:8,11" emptyText="帐号长度为8至11位，建议设置为手机号码!"/>
                                <input class="mini-hidden"  name="userId"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">登录密码</td>
                            <td>
                                <input class="mini-textbox" name="password" style="width: 100%" required="true"
                                       vtype="minLength:3" emptyText="密码长度必须大于3位!"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">人员角色</td>
                            <td>
                                <input class="mini-treeselect" expandonload="true" name="roleId" style="width: 100%"
                                       url="/permission/roleClass/getAllCanUse" required="true"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">登录状态</td>
                            <td>
                                <input class="mini-combobox" style="width: 100%" value="true" name="canLogin" data="[{id:true,text:'允许登录'},{id:false,text:'禁止登录'}]" required="true" />
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">创建时间</td>
                            <td>
                                <input class="mini-datepicker" name="createTime" style="width: 100%"  enabled="false"
                                       showTime="true"
                                       format="yyyy-MM-dd HH:mm:ss"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">最后登录时间</td>
                            <td>
                                <input class="mini-datepicker" style="width: 100%"  enabled="false"
                                       format="yyyy-MM-dd HH:mm:ss" name="lastLoginTime" showTime="true"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="Info1" class="mini-panel mini-panel-info" title="3、人员操作日志  " width="auto" showcollapsebutton="false" showclosebutton="false">
                    <table style="width: 100%; height: 100%" id="Table3">
                        <tr>
                            <td class="showlabel">开始时间</td>
                            <td>
                                <input class="mini-textbox" name="PARAGRAPHCODE" style="width: 100%"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">结束时间</td>
                            <td>
                                <input class="mini-combobox" name="OBJECTTYPE" style="width: 100%" />
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td class="showlabel">关键字</td>
                            <td>
                                <input class="mini-textbox" name="PARAGRAPHCODE" style="width: 100%"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                            <td class="showlabel">日志类型</td>
                            <td>
                                <input class="mini-combobox" name="OBJECTTYPE" style="width: 100%"/>
                            </td>
                            <td class="blank"><span style="color: red">&nbsp;</span></td>
                        </tr>
                        <tr>
                            <td colspan="6">
                                <div class="mini-datagrid" style="width:100%;height: 300px;">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div field="" headerAlign="center">发生时间</div>
                                        <div field="" headerAlign="center">模块名称</div>
                                        <div field="" headerAlign="center">业务描述</div>
                                        <div field="" headerAlign="center">提交参数</div>
                                        <div field="" headerAlign="center">返回结果</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@layout>
<@js>
<script type="text/javascript">
    mini.parse();
    var mode="${mode}";
    var result=${result};
    <#--var pwd="${pwd}";-->
    function doSave(){
        var empForm=new mini.Form('#Table1');
        var userForm=new mini.Form('#Table2');
        empForm.validate();
        userForm.validate();
        if(empForm.isValid()==false){
            mini.alert('人员基本信息录入不完整或不正确，不能进行保存。');
            return ;
        }
        if(userForm.isValid()==false){
            mini.alert('登录用户录入信息录入不完整或不正确，不能进行保存。');
            return ;
        }
        var employee=empForm.getData();
        employee["bornDate"]=mini.formatDate(employee["bornDate"],'yyyy-MM-dd HH:mm:ss');
        var user=userForm.getData();
        user["createTime"]=mini.formatDate(user["createTime"],'yyyy-MM-dd HH:mm:ss');
        user["lastLoginTime"]=mini.formatDate(user["lastLoginTime"],'yyyy-MM-dd HH:mm:ss');
        var url = '/systems/employee/saveAll';
        var postData={employee:employee,loginUser:user}
        $.post(url,{Data:mini.encode(postData)},function(result){
            if (result['success']) {
                mini.alert('数据保存成功', '提示', function (r) {
                    var returnData=result.data;
                    empForm.setData(returnData.employee);
                    userForm.setData(returnData.loginUser);
                });
            }
            else
            {
                mini.alert(result['message'] || "保存失败，请稍候重试。", '提示');
            }
        });
    }
    $(function () {
        var empForm=new mini.Form('#Table1');
        var userForm=new mini.Form('#Table2');
        if(mode!="add"){
            result=mini.decode(result);
            result.employee.bornDate=new Date(result.employee.bornDate);
            empForm.setData(result.employee);
            result.loginUser.createTime=new Date(result.loginUser.createTime);
            result.loginUser.lastLoginTime=new Date(result.loginUser.lastLoginTime);
            userForm.setData(result.loginUser);
             // mini.get('pwd').setValue(pwd);
        }
    })
</script>
</@js>