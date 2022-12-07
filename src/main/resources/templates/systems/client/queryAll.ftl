<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-fit">
        <div class="mini-toolbar" style="text-align:center;padding-top:8px;padding-bottom:8px;" borderstyle="border:0;">
            选择或录入邮箱：<input id="EmailAddress" class="mini-textboxlist" emptyText="自行录入邮箱地址，按Enter确认" inputmode="true"
                           remote="false" style="width:70%;height:40px">&nbsp;&nbsp;
            <a class="mini-button" iconCls="icon-ok" style="width:80px;" onclick="onOk()">确定</a>
            <span style="display:inline-block;width:10px;"></span>
            <a class="mini-button" iconCls="icon-no" style="width:80px;" onclick="onCancel()">取消</a>
        </div>
        <div class="mini-fit">
            <div class="mini-tabs" activeIndex="0" style="width:100%;height:100%" tabPosition="top">
                <div title="客户列表" bodyStyle="overflow:hidden">
                    <div class="mini-toolbar" style="line-height:25px;" borderstyle="border:0;">
                        <label>客户名称：</label>
                        <input id="key" class="mini-textbox" style="width:350px;" onenter="onKeyEnter"/>
                        <a class="mini-button" iconCls="icon-search" style="width:90px;" onclick="search()">查询</a>
                        &nbsp;&nbsp;
                        <a class="mini-button" iconCls="icon-reload" style="width:120px" onclick="clear()">查询全部</a>
                    </div>
                    <div class="mini-fit">
                        <div id="grid1" class="mini-datagrid" style="width:100%;height:100%;"
                             idfield="id" allowresize="true" url="/systems/client/getAll?KHID=${KHID}" sortorder="asc"
                             sortfield="position" multiSelect="true" onselect="onSelect" ondeselect="onDeselect"
                             borderstyle="border-left:0;border-right:0;" onload="afterload"  onlyCheckSelection="true">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="ClientName" width="150" headeralign="center" align="center">
                                    客户名称
                                </div>
                                <div field="LinkMan" width="60" headeralign="center" align="center">
                                    联系人
                                </div>
                                <div field="Position" type="comboboxcolumn" width="80" headeralign="center"
                                     align="center">在职状态
                                    <input property="editor" class="mini-combobox"  data="positions" />
                                </div>
                                <div field="Mobile" width="70" headeralign="center" align="center">
                                    电话
                                </div>
                                <div field="Email" width="100" headeralign="center" align="center">
                                    邮箱
                                </div>
                                <div field="Address" width="200" headeralign="center" align="center">
                                    地址
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div title="人员列表" bodyStyle="overflow:hidden">
                    <div class="mini-toolbar" style="line-height:25px;" borderstyle="border:0;">
                        <label>人员姓名：</label>
                        <input id="key1" class="mini-textbox" style="width:350px;" onenter="onKeyEnter1"/>
                        <a class="mini-button" style="width:80px;" onclick="search1()">查询</a>
                    </div>
                    <div class="mini-fit">
                        <div id="grid2" class="mini-datagrid" style="width:100%;height:98%;"
                             idfield="id" allowresize="true" url="/systems/client/getAllEmailUser" sortorder="asc"
                             sortfield="EmpName" autoload="true" multiSelect="true" onselect="onSelect"
                             ondeselect="onDeselect"  onload="afterload"  onlyCheckSelection="true"
                             borderstyle="border-left:0;border-right:0;" onbeforeselect="beforeSelect">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="UserName" width="60" headeralign="center">
                                    人员姓名
                                </div>
                                <div field="DepName" width="120" headerAlign="center">
                                    所属部门
                                </div>
                                <div field="RoleName" width="70" headeralign="center">
                                    角色名称
                                </div>
                                <div field="Email" width="100" headeralign="center">
                                    邮箱
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div title="最近使用">
                    <div class="mini-fit">
                        <div class="mini-datagrid" style="width:100%;height:100%"  onload="afterload" onlyCheckSelection="true">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="UserName" width="120" headeralign="center" align="center">人员姓名</div>
                                <div field="Email" width="180" headeralign="center" align="center">邮箱</div>
                                <div field="CreateTime" width="120" headerAlign="center" align="center">上次发送时间</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var positions=[{id:1,text:'在职'},{id:2,text:'离职'}];
        var grid1 = mini.get("grid1");
        var grid2 = mini.get("grid2");
        var emailAddress = mini.get('#EmailAddress');
        grid1.load();
        var utilObj = new emailAddressManager();

        function GetData() {
            return utilObj.getValue();
        }
        function SetData(obj){
            utilObj.setValue(obj);
        }

        function search() {
            var key = mini.get("key").getValue();
            grid1.load({
                'Name': key,
                'sortField': grid1.getSortField(),
                'sortOrder': grid1.getSortOrder()
            });
        }
        function clear(){
           grid1.setUrl('/systems/client/getAll');
           grid1.reload();
        }
        function search1() {
            var key = mini.get("key1").getValue();
            grid2.load({
                'Name': key,
                'sortField': grid2.getSortField(),
                'sortOrder': grid2.getSortOrder()
            });
        }

        function onKeyEnter(e) {
            search();
        }

        function onKeyEnter1(e) {
            search1();
        }

        function CloseWindow(action) {
            if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
            else window.close();
        }

        function onOk() {
            CloseWindow("ok");
        }

        function onCancel() {
            CloseWindow("cancel");
        }
        function afterload(e){
            var grid=e.source;
            var rows=grid.getData();
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                var email=row["Email"];
                if(utilObj.contains(email)){
                    grid.select(row,false);
                }
            }
        }
        function onSelect(e) {
            var record = e.record;
            var email = record["Email"];
            if (Utils.isEmail(email) == true) {
                var pre = record["ClientName"] || record["DepName"];
                if (!pre) pre = "未知公司";
                utilObj.addOne({
                    value: record.Email,
                    text: pre + '-' + (record["LinkMan"] ? record["LinkMan"] : record["UserName"])
                });
            }
        }

        function onDeselect(e) {
            var record = e.record;
            var email = record["Email"];
            if (Utils.isEmail(email) == true) {
                utilObj.removeOne(email);
            }
        }

        function beforeSelect(e) {
            var record = e.record;
            var email = record["Email"];
            if (email) {
                e.cancel = !Utils.isEmail(email);
            } else e.cancel = true;
        }

        function emailAddressManager() {
            var g=this ;
            this. contains=function(emailAddress) {
                var items = getNow();
                for (var i = 0; i < items.length; i++) {
                    var item = items[i];
                    if (item.value == emailAddress) {
                        return true;
                    }
                }
                return false;
            }

            this.addOne = function (item) {
                if (g.contains(item.value) == false) {
                    var items = getNow();
                    items.push(item);
                    var texts = [];
                    var values = [];
                    for (var i = 0; i < items.length; i++) {
                        var obj = items[i];
                        texts.push(obj.text);
                        values.push(obj.value);
                    }
                    emailAddress.setValue(values.join(','));
                    emailAddress.setText(texts.join(','));
                    emailAddress.doLayout();
                    emailAddress.render();
                }
            }
            this.removeOne = function (email) {
                if (g.contains(email) == true) {
                    var items = getNow();
                    var texts = [];
                    var values = [];
                    for (var i = 0; i < items.length; i++) {
                        var obj = items[i];
                        if(obj.value!=email){
                            texts.push(obj.text);
                            values.push(obj.value);
                        }
                    }
                    emailAddress.setValue(values.join(','));
                    emailAddress.setText(texts.join(','));
                    emailAddress.doLayout();
                    emailAddress.render();
                }
            }
            this.getValue=function(){
                var items = getNow();
                var texts = [];
                var values = [];
                for (var i = 0; i < items.length; i++) {
                    var obj = items[i];
                    if(Utils.isEmail(obj.value)){
                        texts.push(obj.text);
                        values.push(obj.value);
                    }
                }
                return {text:texts.join(','),value:values.join(','),length:texts.length};
            }
            this.setValue=function(obj){
                emailAddress.setValue(obj.value);
                emailAddress.setText(obj.text);
                grid1.reload();
                grid2.reload();
            }

            function getNow() {
                var text = emailAddress.getText();
                var value = emailAddress.getValue();
                var items = [];
                var texts = (text || "").split(",");
                var values = (value || "").split(",");
                for (var i = 0; i < texts.length; i++) {
                    var t = texts[i];
                    var v = values[i];
                    if (t && v) {
                        var item = {text: t, value: v};
                        items.push(item);
                    }
                }
                return items;
            }
        }
    </script>
</@layout>