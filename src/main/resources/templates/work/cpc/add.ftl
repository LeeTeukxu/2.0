<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <script type="text/javascript" src="/js/address.min.js"></script>
    <script type="text/javascript">
        var lxs = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
        var clientTypes = [
            {id: 1, text: '大专院校'},
            {id: 2, text: '科研单位'},
            {id: 3, text: '工矿企业'},
            {id: 4, text: '事业单位'},
            {id: 5, text: '个人'}
        ];
        var types = [
            {id: '999999', text: '申报五书稿件(系统自动拆分)'},
            {id: '100001', text: '权利要求书'},
            {id: '100002', text: '说明书'},
            {id: '100003', text: '说明书附图'},
            {id: '100004', text: '说明书摘要'},
            {id: '100005', text: '摘要附图'},
            {id: '10000701', text: '专利代理委托书扫描件'}
        ];
    </script>
    <div class="mini-layout" style="height:100%;width:100%">
        <div region="north" showHeader="false" height="<#if CreateType=1>160<#else>120</#if>">
            <div class="mini-toolbar">
                <table>
                    <tr>
                        <td width="100%"></td>
                        <td style="white-space:nowrap;">
                            <button class="mini-button mini-button-primary" id="cmdSave" iconCls="icon-save"
                                    style="width:100px" onclick="saveAll();">保存信息
                            </button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="mini-fit">
                <table style="width:100%;overflow:hidden;margin:0;padding:0" class="layui-table" id="mainForm">
                    <#if CreateType=1>
                        <tr>
                            <td style="width:8%">选择专利交单</td>
                            <td style="width:25%">
                                <input class="mini-buttonedit" style="width:100%;height:100%" name="subNo"
                                       onbuttonclick="showTechCases"/>
                                <input class="mini-hidden" name="pid"/>
                                <input class="mini-hidden" name="id"/>
                                <input class="mini-hidden" name="subId"/>
                            </td>
                            <td style="width:8%">案件名称</td>
                            <td style="width:25%">
                                <input class="mini-textbox" style="width:100%;height:100%" name="yName"/>
                            </td>
                            <td style="width:8%">内部编号</td>
                            <td style="width:25%">
                                <input class="mini-textbox" style="width:100%;height:100%" name="nbbh"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:120px">案卷包编号</td>
                            <td>
                                <input class="mini-textbox" style="width:100%;height:100%" name="docSn"
                                       required="true"/>
                            </td>
                            <td style="width:120px">专利名称</td>
                            <td>
                                <input class="mini-textbox" style="width:100%;height:100%" name="famingmc"
                                       required="true"/>
                            </td>
                            <td style="width:120px">专利类型</td>
                            <td>
                                <input class="mini-combobox" style="width:100%;height:100%" data="lxs" name="shenqinglx"
                                       required="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div id="fSame" class="mini-checkbox" text="声明本申请人对同样的发明创造在申请本发明专利的同日申请了实用新型专利"
                                     name="sameApply"></div>
                                <div id="sSame" class="mini-checkbox" text="声明本申请人对同样的发明创造在申请本实用新型专利的同日申请了发明专利"
                                     name="sameApply" visible="false"></div>
                            </td>
                            <td colspan="2">
                                权利要求项数:<input class="mini-spinner" minValue="1" maxValue="50" style="width:80px"
                                              name="itemCount"/>项。
                                &nbsp;&nbsp;
                                <div id="ffSame" class="mini-checkbox" text="生成实质审查请求书" name="addSZSC"></div>
                            </td>
                            <td style="width:120px">摘要附图</td>
                            <td>
                                <span id="f1">指定说明书附图的图&nbsp;<input class="mini-textbox" style="width:100px;height:100%"
                                                                    name="digistImage"
                                                                    required="false"/>&nbsp;为摘要附图</span>
                            </td>
                        </tr>
                    <#else>
                        <tr>
                            <td style="width:120px">内部编号</td>
                            <td>
                                <input class="mini-textbox" style="width:100%;height:100%" name="nbbh" required="true"/>
                                <input class="mini-hidden" style="width:100%;height:100%" name="docSn"/>
                                <input class="mini-hidden" name="pid"/>
                                <input class="mini-hidden" name="id"/>
                                <input class="mini-hidden" name="subId"/>
                                <input class="mini-hidden" name="createType" value="${CreateType}"/>
                            </td>
                            <td style="width:120px">专利名称</td>
                            <td>
                                <input class="mini-textbox" style="width:100%;height:100%" name="famingmc"
                                       required="true"/>
                            </td>
                            <td style="width:120px">专利类型</td>
                            <td>
                                <input class="mini-combobox" style="width:100%;height:100%" data="lxs" name="shenqinglx"
                                       required="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div id="fSame" class="mini-checkbox" text="声明本申请人对同样的发明创造在申请本发明专利的同日申请了实用新型专利"
                                     name="sameApply"></div>
                                <div id="sSame" class="mini-checkbox" text="声明本申请人对同样的发明创造在申请本实用新型专利的同日申请了发明专利"
                                     name="sameApply" visible="false"></div>
                            </td>
                            <td colspan="2">
                                权利要求项数:<input class="mini-spinner" minValue="1" maxValue="50" style="width:80px"
                                              name="itemCount"/>项。
                                &nbsp;&nbsp;
                                <div id="ffSame" class="mini-checkbox" text="生成实质审查请求书" name="addSZSC"></div>
                            </td>
                            <td style="width:120px">摘要附图</td>
                            <td>
                                <span id="f1">指定说明书附图的图&nbsp;<input class="mini-textbox" style="width:100px;height:100%"
                                                                    name="digistImage"
                                                                    required="false"/>&nbsp;为摘要附图</span>
                            </td>
                        </tr>
                    </#if>
                </table>
            </div>
        </div>
        <div region="center" bodyStyle="overflow:hidden">
            <div class="mini-fit">
                <div class="mini-splitter" style="width:100%;height:300px;margin:0;padding:0" allowResize="false">
                    <div size="50%" showCollapseButton="false">
                        <fieldset style="height:290px">
                            <legend>发明人列表</legend>
                            <div class="mini-toolbar" id="tool1">
                                <a class="mini-button subAdd" iconCls="icon-add" onclick="addInventor()">新增</a>
                                <a class="mini-button subRemove" iconCls="icon-remove" onclick="deleteInventor()">删除</a>
                                <span class="separator"></span>
                                <a class="mini-button subSave" iconCls="icon-save" onclick="saveInventor()">保存</a>
                            </div>
                            <div class="mini-fit">
                                <div class="mini-datagrid" fitColumns="true" style="width:100%; height:100%"
                                     url="/cpc/getInventors" id="grid1" allowCellSelect="true" allowCellEdit="true"
                                     autoload="false" showPager="false">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div type="checkcolumn"></div>
                                        <div field="Name" width="150" align="center" headerAlign="center"
                                             vtype="required">发明人名称
                                            <input property="editor" class="mini-textbox" style="width:100%"/>
                                        </div>
                                        <div field="NotOpen" type="checkboxcolumn" width="80" align="center"
                                             headerAlign="center">不公布姓名
                                        </div>
                                        <div field="First" type="checkboxcolumn" width="100" align="center"
                                             headerAlign="center">第一发明人
                                        </div>
                                        <div field="Country" type="comboboxcolumn" width="150" align="center"
                                             headerAlign="center">国籍
                                            <input property="editor" class="mini-combobox" url="/cpc/getCountry"
                                                   allowInput="true" valueFromSelect="true"/>
                                        </div>
                                        <div field="Code" width="160" align="center" headerAlign="center">身份证号码
                                            <input property="editor" class="mini-textbox"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div size="50%" showCollapseButton="false">
                        <fieldset style="height:290px" id="form3">
                            <legend>代理人列表</legend>
                            <div class="mini-toolbar" id="tool3">
                                代理机构: <input class="mini-combobox" style="width:300px;height:100%" name="agentName"
                                             url="/cpc/getAgentCompany" onvaluechanged="onAgentChanged"/>
                                <input class="mini-hidden" style="width:100%;height:100%" name="agentCode"
                                       required="true"/>
                                <a class="mini-button subAdd" iconCls="icon-add" onclick="addAgents()">新增</a>
                                <a class="mini-button subRemove" iconCls="icon-remove" onclick="deleteAgent()">删除</a>
                                <span class="separator"></span>
                                <a class="mini-button subSave" iconCls="icon-save" onclick="saveAgent()">保存</a>
                            </div>
                            <div class="mini-fit">
                                <div class="mini-datagrid" style="width:100%;height:100%" id="grid3" fitColumns="false"
                                     allowCellEdit="true" allowCellSelect="true" oncellbeginedit="beforeEditAgent"
                                     oncellendedit="endEditAgent" url="/cpc/getAgent" autoload="false"
                                     showPager="false">
                                    <div property="columns">
                                        <div type="indexcolumn"></div>
                                        <div type="checkcolumn"></div>
                                        <div field="Name" align="center" headerAlign="center" width="200">姓名
                                            <input property="editor" class="mini-combobox" style="width:100%"
                                                   allowInput="true" url="/cpc/getAgents"/>
                                        </div>
                                        <div field="Code" align="center" headerAlign="center" width="150">执业证号
                                        </div>
                                        <div field="Phone" align="center" headerAlign="center" width="120">电话
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <fieldset style="height:240px;width:100%">
                    <legend>申请人列表</legend>
                    <div class="mini-toolbar" id="tool2">
                        <a class="mini-button subAdd" iconCls="icon-add" onclick="addApply()">新增</a>
                        <a class="mini-button subSave" iconCls="icon-edit" onclick="editApply()">编辑</a>
                        <span class="separator"></span>
                        <a class="mini-button subRemove" iconCls="icon-remove" onclick="deleteApplyMan()">删除</a>
                        <a class="mini-button subSave" iconCls="icon-save" onclick="saveApply()">保存</a>
                    </div>
                    <div class="mini-fit">
                        <div class="mini-datagrid" style="width:100%;height:100%" id="grid2" url="/cpc/getApplyMan"
                             autoload="false" allowHeaderWrap="true" showPager="false">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="Name" align="center" headerAlign="center" width="150">申请人姓名或名称</div>
                                <#--                                <div field="Code" align="center" headerAlign="center">用户代码</div>-->
                                <div field="Type" align="center" headerAlign="center" type="comboboxcolumn">申请人类型
                                    <input property="editor" class="mini-combobox" data="clientTypes"/>
                                </div>
                                <div field="IDCode" align="center" headerAlign="center" width="150">身份证号码统一信用代码
                                </div>
                                <div field="Email" align="center" headerAlign="center" width="120">电子邮箱</div>
                                <div field="RequestFJ" align="center" headerAlign="center"
                                     width="150" type="checkboxcolumn">请求费减且已完成费减资格备案
                                </div>
                                <div field="Country" align="center" headerAlign="center" width="150"
                                     type="comboboxcolumn">国籍或注册国家(地区)
                                    <input property="editor" class="mini-combobox" url="/cpc/getCountry"/>
                                </div>
                                <div field="Province" align="center" headerAlign="center" width="150"
                                     type="treeselectcolumn">省、自治区、直辖区
                                    <input property="editor" class="mini-treeselect" url="/cpc/getXZQH"
                                           valueField="FID" parentField="PID" textField="Name" virtualScroll="true"/>
                                </div>
                                <div field="City" align="center" headerAlign="center" width="150"
                                     type="treeselectcolumn">市县
                                    <input property="editor" virtualScroll="true" class="mini-treeselect"
                                           url="/cpc/getXZQH"
                                           valueField="FID" parentField="PID" textField="Name"/>
                                </div>
                                <div field="Street" align="center" headerAlign="center" width="150">城区(乡) 、街道、门牌号
                                </div>
                                <div field="Address" align="center" headerAlign="center" width="150"
                                     type="comboboxcolumn">经常居所地、营业所所在地
                                    <input property="editor" class="mini-combobox" url="/cpc/getCountry"/>
                                </div>
                                <div field="PostCode" align="center" headerAlign="center">邮政编码</div>
                                <div field="Phone" align="center" headerAlign="center">电话</div>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset style="height:300px" id="form4">
                    <legend>案卷包附件</legend>
                    <div class="mini-toolbar" id="tool4">
                        <a class="mini-button subAdd" iconCls="icon-add" onclick="addFile()">上传附件</a>
                        <a class="mini-button subAdd" iconCls="icon-add"
                           onclick="addFile('10000701','上传委托代理书')">上传代理委托书</a>
                        <a class="mini-button subAdd" iconCls="icon-add" onclick="addFile('999999','上传五书Word稿件')">上传五书Word稿件</a>
                        <span class="separator"></span>
                        <a class="mini-button subRemove" iconCls="icon-remove" onclick="deleteFile()">删除</a>
                    </div>
                    <div class="mini-fit">
                        <div class="mini-datagrid" style="width:100%;height:100%" fitColumns="false" id="grid4"
                             ondrawCell="onDraw"
                             url="/cpc/getCPCFiles" autoload="false" showPager="false">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div type="checkcolumn"></div>
                                <div field="Code" align="center" headerAlign="center" width="150"
                                     type="comboboxcolumn">附件类型
                                    <input property="editor" class="mini-combobox" data="types"/>
                                </div>
                                <div field="Name" align="center" headerAlign="center" width="300">附件名称</div>
                                <div field="Size" align="center" headerAlign="center">大小</div>
                                <div field="Pages" align="center" headerAlign="center">页数</div>
                                <div field="CreateTime" align="center" headerAlign="center" width="150"
                                     dataType="date" dateFormat="yyyy-MM-dd" width="150">上传日期
                                </div>
                                <div field="CreateMan" align="center" headerAlign="center" width="120">上传人员</div>
                                <div field="Action" align="center" headerAlign="center" width="100">#</div>
                            </div>
                        </div>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>

    <div class="mini-window" id="TechWindow" width="1200" height="500" style="display: none" title='选择专利业务交单<span
        style="color:red;font-size:8px">(选择交单记录后即刻生成了CPC案卷包记录,不能更改选择。只能删除后重新选择)</span>'
         showToolbar="true" showCloseButton="true" bodyStyle="padding:0" borderStyle="border:0" showModal="true">
        <div property="toolbar" style="padding:5px;padding-left:8px;">
            &nbsp;关键字:&nbsp;<input id="keyText" class="mini-textbox" style="width:40%;" emptyText="输入关键字,按Enter键进行查询!"
                                   onenter="doQuery"/>
            <a class="mini-button" iconCls="icon-search" onclick="doQuery()">查询</a>
            <a class="mini-button" iconCls="icon-reload" onclick="onClear()">重置</a>
            <a class="mini-button" iconCls="icon-ok" style="margin-left:280px" onclick="doImport()">确认选择</a>
            <a class="mini-button" iconCls="icon-cancel" onclick="closeWindow('TechWindow')">放弃关闭</a>
        </div>
        <div class="mini-fit">
            <div class="mini-datagrid" id="grid5" style="width:100%;height:100%" url="/casesTech/getData?State=56"
                 autoload="true" multiSelect="false" frozenStartColumn="0" frozenEndColumn="4">
                <div property="columns">
                    <div type="indexcolumn"></div>
                    <div type="checkcolumn"></div>
                    <div field="SubNo" headerAlign="center" align="center" width="120" allowSort="true">立案编号
                    </div>
                    <div field="ClientName" headerAlign="center" align="center" width="240" allowSort="true">
                        客户名称
                    </div>
                    <div field="YName" headerAlign="center" align="center" vtype="required" allowSort="true">
                        案件名称
                    </div>
                    <div field="Nums" headerAlign="center" align="center" width="200">业务数量汇总</div>
                    <div field="CLevel" allowSort="true" headerAlign="center" align="center"
                         type="comboboxcolumn" width="80">案件级别
                        <input property="editor" class="mini-combobox" style="width:100%" url="/cLevel/getItems"/>
                    </div>
                    <div field="ShenqingName" width="150" headerAlign="center" vtype="required" align="center"
                         allowSort="true">专利初步名称
                    </div>
                    <div field="CreateMan" headerAlign="center" align="center" type="treeselectcolumn"
                         allowSort="true">商务人员
                        <input property="editor" class="mini-treeselect"
                               url="/systems/dep/getAllLoginUsersByDep"
                               textField="Name" valueField="FID" parentField="PID"/>
                    </div>
                    <div field="SignTime" headerAlign="center" align="center" dataType="date" allowSort="true"
                         dateFormat="yyyy-MM-dd">签单日期
                    </div>
                    <div field="TechMan" headerAlign="center" align="center" type="treeselectcolumn"
                         allowSort="true">案件代理师
                        <input property="editor" class="mini-treeselect"
                               url="/systems/dep/getAllLoginUsersByDep"
                               textField="Name" valueField="FID" parentField="PID"/>
                    </div>
                    <div field="AcceptTechTime" headerAlign="center" align="center" dataType="date"
                         allowSort="true" dateFormat="yyyy-MM-dd">接单日期
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mini-window" id="ApplyWindow" width="1000" height="400" bodyStyle="overflow:hidden;margin:0;padding:0"
         style="display:none;" title="申请人信息<span style='color:red'>(Ctrl+V自动从剪贴板读取名称地址信息,如包含公司名称,请用空格将公司名称分隔)</span>"
         showToolbar="false" showCloseButton="true" bodyStyle="padding:0;border:0" showModal="true">
        <table class="layui-table" id="ApplyForm">
            <tr>
                <td style="width:12%">姓名或名称</td>
                <td style="width:22%">
                    <input class="mini-textbox" style="width:100%" name="name" required="true"/>
                    <input class="mini-hidden" name="id"/>
                    <input class="mini-hidden" name="subId"/>
                    <input class="mini-hidden" name="mainId"/>
                </td>
                <td style="width:10%">申请人类型</td>
                <td style="width:24%">
                    <input class="mini-combobox" style="width:100%" name="type" required="true" data="clientTypes"/>
                </td>
            </tr>
            <tr>
                <td>费减资格备案</td>
                <td>
                    <input class="mini-checkbox" style="width:100%" name="requestFj" text="请求费减且已完成费减资格备案"
                           value="true"/>
                </td>
                <td>身份证号码统一社会信用代码</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="idCode" required="true" vtype="maxLength:18"/>
                </td>
            </tr>
            <tr>
                <td>国籍或注册国家</td>
                <td>
                    <input class="mini-combobox" style="width:100%" name="country" required="true"
                           url="/cpc/getCountry" allowInput="true" valueFromSelect="true" resultAsTree="true"/>
                </td>
                <td>省、自治区、直辖市</td>
                <td>
                    <input class="mini-treeselect" style="width:100%" name="province" required="true"
                           valueField="FID" parentField="PID" textField="Name"
                           url="/cpc/getXZQH" valueFromSelect="true" allowInput="true" resultAsTree="false"/>
                </td>
            </tr>
            <tr>
                <td>市县</td>
                <td>
                    <input class="mini-treeselect" style="width:100%" name="city" required="true" url="/cpc/getXZQH"
                           valueField="FID" parentField="PID" textField="Name"
                           valueFromSelect="true" allowInput="true" resultAsTree="false"/>
                </td>
                <td>城区(乡)、街道、门牌号</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="street" required="true"/>
                </td>
            </tr>
            <tr>
                <td>电子邮箱</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="email" required="false" vtype="email"/>
                </td>
                <td>经常居所地或营业所在地</td>
                <td>
                    <input class="mini-combobox" style="width:100%" name="address" required="true"
                           url="/cpc/getCountry" allowInput="true" valueFromSelect="true" resultAsTree="true"/>
                </td>

            </tr>
            <tr>
                <td>邮政编码</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="postCode" required="true" vtype="maxlength:6"/>
                </td>
                <td>电话</td>
                <td>
                    <input class="mini-textbox" style="width:100%" name="phone" required="false" vtype="maxLength:13"/>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: right">
                    <button class="mini-button mini-button-primary" onclick="saveApply();">确认保存</button>
                </td>

                <td colspan="2" style="text-align: left">
                    <button class="mini-button mini-button-danger" onclick="closeWindow('ApplyWindow')">放弃退出</button>
                </td>
            </tr>
        </table>
    </div>
    <div class="mini-window" id="ShowFilesWindow" style="width:500px;height:400px;display:none" showFooter="true"
         title="请选择专利交单的技术文件(分解五书文件)">
        <div class="mini-datagrid" style="width:100%;height:100%" id="fileGrid" showPager="false">
            <div property="columns">
                <div type="indexcolumn"></div>
                <div type="checkcolumn"></div>
                <div field="Name" align="center" headerAlign="center" width="420">文件名称</div>
            </div>
        </div>
        <div property="footer" style="text-align:center;height:50px;vertical-align: middle">
            <tr>
                <td style="text-align: right">
                    <button class="mini-button mini-button-primary" onclick="confirmImport();">确认保存</button>
                </td>

                <td style="text-align: left">
                    <button class="mini-button mini-button-danger" onclick="closeWindow('ShowFilesWindow')">放弃退出
                    </button>
                </td>
            </tr>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();

        function getSavedData() {
            var result = {};

            function getMain() {
                var data = mainForm.getData();
                data.agentName = mini.getbyName('agentName').getValue();
                data.agentCode = mini.getbyName('agentCode').getValue();
                return data;
            }

            function getFiles() {
                var rows = mini.clone(grid4.getData());
                return rows;
            }

            function getApply() {
                var rows = mini.clone(grid2.getData());
                return rows;
            }

            function getInventor() {
                var rows = mini.clone(grid1.getData());
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var isFirst = parseInt(row["First"] || 0);
                    if (isFirst) {
                        var country = row["Country"];
                        var code = row["Code"];
                        if (!country || !code) {
                            throw '第一发明人的国籍和身份证号码信息不能为空!';
                        }
                    }
                }
                return rows;
            }

            function getAgents() {
                var rows = mini.clone(grid3.getData());
                return rows;
            }

            result.Main = getMain();
            result.Agent = getAgents();
            result.Apply = getApply();
            result.Inventor = getInventor();
            result.Files = getFiles();
            return result;
        }

        function allComponentManager() {
            function addMode() {
                var fields = mainForm.getFields();
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var name = field.getName();
                    <#if CreateType==1>
                    if (name == "subNo") field.setEnabled(true); else field.setEnabled(false);
                    <#else>
                    if (name == "famingmc" || name == "shenqinglx" || name == "nbbh") {
                        field.setEnabled(true)
                    } else field.setEnabled(false);
                    </#if>
                }
                for (var i = 1; i <= 4; i++) {
                    var tool = mini.get('#tool' + i);
                    tool.setEnabled(false);
                    var grid = mini.get('#grid' + i);
                    grid.setEnabled(false);
                }
                changeSaveAndRemoveStatus(false);
            }

            function editMode() {
                var fields = mainForm.getFields();
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var name = field.getName();
                    if (name == "subNo" ||
                        name == "shenqinglx" ||
                        name == "yName" ||
                        name == "docSn" ||
                        name == "agentCode") {
                        field.setEnabled(false);
                    } else field.setEnabled(true);
                    if (name == "firstInventor" || name == "firstInventCountry" || name == "firstInventIdCode") {
                        field.setEnabled(false)
                    }
                }
                for (var i = 1; i <= 4; i++) {
                    var tool = mini.get('#tool' + i);
                    tool.setEnabled(true);
                    var grid = mini.get('#grid' + i);
                    grid.setEnabled(true);
                }
                changeSaveAndRemoveStatus(true);
            }

            function browseMode() {
                var fields = mainForm.getFields();
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var name = field.getName();
                    field.setEnabled(false);
                }
                for (var i = 1; i <= 4; i++) {
                    var tool = mini.get('#tool' + i);
                    tool.setEnabled(false);
                    var grid = mini.get('#grid' + i);
                    grid.setEnabled(false);
                }
                mini.get('#cmdSave').setEnabled(false);
            }

            this.doLayout = function () {
                var con = mini.getbyName('pid');
                var cmdSave = mini.get('#cmdSave');
                var pidValue = con.getValue();
                if (pidValue) {
                    if (mode == "Edit") editMode(); else browseMode();
                } else addMode();
                if (mode != "Browse") {
                    <#if CreateType=1>
                    if (pidValue) cmdSave.enable(); else cmdSave.disable();
                    <#else>
                    cmdSave.enable();
                    </#if>
                }
            }
            this.disable = function (val) {
                changeSaveAndRemoveStatus(!val);
            }

            function changeSaveAndRemoveStatus(trueOrFalse) {
                var cons = $('.subSave');
                var cons1 = $('.subRemove');
                var cons2 = $('.subAdd');
                for (var i = 0; i < cons1.length; i++) {
                    cons.push(cons1[i]);
                }
                for (var i = 0; i < cons2.length; i++) {
                    cons.push(cons2[i]);
                }
                for (var i = 0; i < cons.length; i++) {
                    var con = cons[i];
                    var mCon = mini.get(con);
                    if (mCon) {
                        if (trueOrFalse) mCon.enable(); else mCon.disable();
                    }
                }
            }
        }

        function shenqinglxProcessor() {
            //发明
            function faming() {
                var con = mini.get('sSame');
                if (con) {
                    con.hide();
                    con.enable(false);
                    var name = con.getName();
                    con.setName(name + '_1');
                }
                var cons = [mini.get('fSame'), mini.get('ffSame')];
                if (cons.length > 0) {
                    for (var i = 0; i < cons.length; i++) {
                        var con = cons[i];
                        var name = con.getName();
                        con.show();
                        con.enable(true);
                        con.setName(name.split('_')[0]);
                    }
                }
            }

            //外观
            function waiguan() {

            }

            //实用
            function shiyong() {
                var cons = [mini.get('fSame'), mini.get('ffSame')];
                if (cons.length > 0) {
                    for (var i = 0; i < cons.length; i++) {
                        var con = cons[i];
                        var name = con.getName();
                        con.hide();
                        con.enable(false);
                        con.setName(name + '_' + i);
                    }
                }
                var con1 = mini.get('sSame');
                if (con1) {
                    con1.enable(true);
                    con1.show();
                    var name = con1.getName();
                    con1.setName(name.split('_')[0]);
                }
            }

            var funHash = {0: faming, 1: shiyong, 2: waiguan};
            this.execute = function (lx) {
                var fun = funHash[lx];
                if (fun) {
                    fun();
                    <#if CreateType=2>

                    function other() {
                        mini.getbyName('itemCount').setEnabled(true);
                        mini.getbyName('digistImage').setEnabled(true);
                    }

                    other();
                    </#if>
                }
            }
        }

        function closeWindow(name) {
            var win = mini.get(name);
            if (win) win.hide();
        }
    </script>
    <script type="text/javascript">
        mini.parse();
        var allData = ${Data};
        var companyInfo = ${Company};
        //****************变量定义*********************//
        var grid1 = mini.get('#grid1');
        var grid2 = mini.get('#grid2');
        var grid3 = mini.get('#grid3');
        var grid4 = mini.get('#grid4');
        var grid5 = mini.get('#grid5');
        var techWin = mini.get('#TechWindow');
        var mainForm = new mini.Form('#mainForm');
        var allCon = new allComponentManager();
        var processor = new shenqinglxProcessor();
        var mode = "${Mode}";
        var apply = new applyManAddress();
        //*****************************************//
        $(function () {
            loadData(allData);
            grid2.setWidth(grid4.getWidth());
            allCon.doLayout();
            grid1.on('cellendedit', onfirstChange);
            apply.registerPasteEvent();
            var conlx = mini.getbyName('shenqinglx');
            if (conlx) {
                conlx.on('valuechanged', function () {
                    processor.execute(conlx.getValue());
                });
            }
        })

        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "Action") {
                var attId = record["AttID"];
                e.cellHtml = '<a style="text-decoration: underline" href="javascript:viewDocument(\'' + attId + '\')">查看</a>';
            }
        }

        function viewDocument(attId) {
            var iid = mini.loading('正在请求文件数据.........');
            var url = '/cpc/getImages';
            $.getJSON(url, {AttID: attId}, function (result) {
                mini.hideMessageBox(iid);
                var isOK = parseInt(result.status);
                if (isOK == 1) {
                    window.parent.showImages(mini.encode(result));
                } else {
                    var msg = result.message || "无法加载通知书附件。";
                    layer.alert(msg);
                }
            });
        }

        function addSBFile() {
            mini.get('#file1');
        }

        //弹出选择交单记录
        function showTechCases() {
            function resetTechWin() {
                mini.get('#keyText').setValue(null);
                grid5.reload();
            }

            techWin.show();
            resetTechWin();
        }

        //根据选择的交单记录生成CPC案券包记录。
        function doImport() {
            var row = grid5.getSelected();
            if (row) {
                var subId = row["SubID"];
                var url = '/cpc/getTargetFile';
                $.getJSON(url, {SubID: subId}, function (result) {
                    if (result.success) {
                        var files = result.data || [];
                        if (files.length == 0) {
                            mini.alert('此业务交单没有可以进行五书拆分的Word文件，导入操作被中止!');
                        } else if (files.length == 1) {
                            importAction(files[0]);
                        } else if (files.length > 1) {
                            var win = mini.get('#ShowFilesWindow');
                            win.show();
                            var grid = mini.get('fileGrid');
                            var p = [];
                            for (var i = 0; i < files.length; i++) {
                                var f = files[i];
                                p.push({Name: f});
                            }
                            grid.setData(p);
                            grid.select(p[0], false);
                        }
                    } else mini.alert(result.message || "当前选择的业务交单无法导入!");
                })
            } else mini.alert('请选择要导入的交单记录!');
        }

        function confirmImport() {
            var win = mini.get('#ShowFilesWindow');
            var grid = mini.get('fileGrid');
            var row = grid.getSelected();
            if (row) {
                var name = row["Name"];
                mini.confirm('确认进行导入？', '系统提示', function (result) {
                    if (result == 'ok') importAction(name);
                });

            } else mini.alert('请选择技术文件!');
        }

        function importAction(fileName) {
            var row = grid5.getSelected();
            var iid = mini.loading('正在生成案卷数据........');
            if (row) {
                var subId = row["SubID"];
                var url = '/cpc/importOne';
                $.post(url, {SubID: subId, FileName: fileName}, function (result) {
                    mini.hideMessageBox(iid);
                    if (result.success) {
                        mini.alert('选择交单项目数据导入成功!', '系统提示', function () {
                            techWin.hide();
                            var data = result.data || {};
                            loadData(data);
                            mode = "Edit";
                            allCon.doLayout();
                            allCon.disable(false);
                            var win = mini.get('#ShowFilesWindow');
                            win.hide();
                        });
                    } else mini.alert(result.message || "导入交单数据失败，请稍候重试!");
                })
            }
        }

        function loadData(data) {
            for (var name in data) {
                var val = data[name];
                if (val != null && val != undefined) {
                    var cons = mini.getsbyName(name);
                    if (cons.length > 0) {
                        for (var i = 0; i < cons.length; i++) {
                            var con = cons[i];
                            con.setValue(val);
                            if (name == "subNo") con.setText(val);
                        }
                    }
                }
            }
            mini.getbyName('agentName').setValue(data.agentName);
            mini.getbyName('agentCode').setValue(data.agentCode);
            grid4.load({MainID: getMainId()});
            grid1.load({MainID: getMainId()});
            grid2.load({MainID: getMainId()});
            grid3.load({MainID: getMainId()});
            processor.execute(parseInt(data.shenqinglx));
            allCon.disable(false);
        }

        function addInventor() {
            var con = mini.getByName('pid');
            var mainId = con.getValue();
            if (mainId) {
                var row = {Name: '', NotOpen: false, MainID: mainId, First: false};
                var rows = grid1.getData();
                if (rows.length == 0) row.First = true;
                grid1.addRow(row);
            } else mini.alert('数据出现异常，请刷新后再试。');
        }

        function onfirstChange(e) {
            var field = e.field;
            if (field == 'First') {
                var record = e.record;
                var targetId = record._id;
                var value = e.value;
                if (value == "true" || value == true || value == "TRUE") {
                    var rows = grid1.getData();
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        var id = row._id;
                        if (id == targetId) continue;
                        grid1.updateRow(row, {First: false});
                    }
                    grid1.accept();
                }
            }
        }

        //根据选择的第一发明人对表单上的对应值做控制。
        function doInventChange() {
            var rows = grid1.getData();
            var targetRow = null;
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var first = (row["First"]);
                if (first) {
                    targetRow = row;
                    break;
                }
            }
            var conCountry = mini.getbyName("firstInventCountry");
            var conCode = mini.getbyName("firstInventIdCode");
            var conName = mini.getbyName("firstInventor");
            if (targetRow) {
                conName.setValue(targetRow["Name"]);
                if (mode != "Browse") {
                    conCountry.setEnabled(true);
                    conCode.setEnabled(true);
                }
            } else {
                conName.setValue(null);
                conCountry.setEnabled(false);
                conCode.setEnabled(false);
                conCountry.setValue(null);
                conCode.setValue(null);
            }
        }

        function deleteInventor() {
            var row = grid1.getSelected();
            if (row) {
                function g() {
                    var id = row["ID"];
                    if (!id) {
                        grid1.removeRow(row);
                    } else {
                        var url = '/cpc/removeInventor';
                        $.post(url, {ID: id}, function (result) {
                            if (result.success) {
                                mini.alert('选择的记录删除成功!', '系统提示', function () {
                                    grid1.removeRow(row);
                                    grid1.acceptRecord(row);
                                });
                            }
                        })
                    }
                }

                mini.confirm('确认要删除选择的记录吗？', '删除提示', function (result) {
                    if (result == 'ok') g();
                });
            }
        }

        function deleteAgent() {
            var row = grid3.getSelected();
            if (row) {
                function g() {
                    var id = row["ID"];
                    if (!id) {
                        grid3.removeRow(row);
                    } else {
                        var url = '/cpc/removeAgent';
                        $.post(url, {ID: id}, function (result) {
                            if (result.success) {
                                mini.alert('选择的记录删除成功!', '系统提示', function () {
                                    grid3.removeRow(row);
                                    grid3.acceptRecord(row);
                                });
                            }
                        })
                    }
                }

                mini.confirm('确认要删除选择的记录吗？', '删除提示', function (result) {
                    if (result == 'ok') g();
                });
            }
        }

        function deleteApplyMan() {
            var row = grid2.getSelected();
            if (row) {
                function g() {
                    var id = row["ID"];
                    if (!id) {
                        grid2.removeRow(row);
                    } else {
                        var url = '/cpc/removeApplyMan';
                        $.post(url, {ID: id}, function (result) {
                            if (result.success) {
                                mini.alert('选择的记录删除成功!', '系统提示', function () {
                                    grid2.removeRow(row);
                                    grid2.acceptRecord(row);
                                });
                            }
                        })
                    }
                }

                mini.confirm('确认要删除选择的记录吗？', '删除提示', function (result) {
                    if (result == 'ok') g();
                });
            }
        }

        function saveInventor() {
            var mainId = getMainId();
            if (mainId) {
                var rows = grid1.getData();
                if (rows.length == 0) {
                    mini.alert('没有可保存的数据!');
                    return;
                }
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var isFirst = (row["First"]);
                    if (isFirst == true || isFirst == "true") {
                        var country = row["Country"];
                        var code = row["Code"];
                        if (!country || !code) {
                            mini.alert('第一发明人的国籍和身份证号码信息不能为空!');
                            return;
                        }
                    }
                }
                var url = '/cpc/saveInventor';
                $.post(url, {MainID: mainId, Data: mini.encode(rows)}, function (result) {
                    if (result.success) {
                        mini.alert('保存成功!', '保存提示', function () {
                            grid1.reload();
                        });
                    }
                })
            } else mini.alert('数据出现异常，请刷新后再试。');
        }

        function saveAgent() {
            var mainId = getMainId();
            if (mainId) {
                var rows = grid3.getData();
                if (rows.length == 0) {
                    mini.alert('没有可保存的数据!');
                    return;
                }
                var url = '/cpc/saveAgent';
                $.post(url, {MainID: mainId, Data: mini.encode(rows)}, function (result) {
                    if (result.success) {
                        mini.alert('保存成功!', '保存提示', function () {
                            grid3.reload();
                        });
                    }
                })
            } else mini.alert('数据出现异常，请刷新后再试。');
        }

        function addAgents() {
            if (getMainId()) {
                var row = {MainID: getMainId()};
                grid3.addRow(row);
            } else mini.alert('数据出现异常，请刷新后再试。');
        }

        function getMainId() {
            var con = mini.getByName('pid');
            var mainId = con.getValue();
            return mainId;
        }

        var applyWindow = mini.get('ApplyWindow');
        var applyForm = new mini.Form('#ApplyForm');

        function addApply() {
            if (getMainId()) {
                applyForm.reset();
                applyWindow.show();
            } else mini.alert('数据加载出现异常，请刷新页面后再进行操作!');
        }

        function editApply() {
            var row = grid2.getSelected();
            if (row) {
                var subId = row["SubID"];
                var url = '/cpc/getSingleApplyMan';
                $.getJSON(url, {SubID: subId}, function (result) {
                    if (result.success) {
                        var data = result.data || {};
                        applyForm.reset();
                        applyForm.setData(data);
                        applyWindow.show();
                    } else mini.alert(result.message);
                })
            }
        }

        function saveApply() {
            applyForm.validate();
            if (applyForm.isValid()) {
                var data = applyForm.getData();
                data.mainId = getMainId();
                var url = '/cpc/saveApplyMan';
                $.post(url, {Data: mini.encode(data)}, function (result) {
                    if (result.success) {
                        mini.alert('申请人信息保存成功!', '系统提示', function () {
                            applyWindow.hide();
                            grid2.load({MainID: getMainId()});
                        });
                    } else mini.alert(result.message || "保存失败，请稍候重试!");
                })
            }
        }

        function addFile(fileType, title) {
            title = title || "上传案卷包组成附件";
            if (fileType == null || fileType == undefined) fileType = '';
            mini.open({
                url: '/cpc/addCPCFile?MainID=' + getMainId() + '&FileType=' + fileType,
                width: 1000,
                height: 400,
                title: title,
                showModal: true,
                onDestroy: function () {
                    grid4.load({MainID: getMainId()});
                }
            });
        }

        function deleteFile() {
            var row = grid4.getSelected();
            if (row) {
                var subId = row["SubID"];
                var url = '/cpc/deleteCPCFile';
                mini.confirm('确认要删除选择的文件呈？', '删除提示', function (act) {
                    if (act == 'ok') {
                        $.post(url, {SubID: subId}, function (result) {
                            if (result.success) {
                                mini.alert('选择的文件已删除!', '系统提示', function () {
                                    grid4.reload();
                                });
                            } else mini.alert(result.message || "删除文件失败，请稍候重试!");
                        })
                    }
                });
            } else mini.alert('请选择要删除的文件!');
        }

        function saveAll() {
            try {
                mainForm.validate();
                if (mainForm.isValid() == false) {
                    mini.alert('数据录入不完整，保存操作被中止!');
                    return;
                }
                var data = getSavedData();
                <#if CreateType=1>
                var url = '/cpc/saveAll';
                <#else>
                var url = '/cpc/saveAllData';
                </#if>
                $.post(url, {Data: mini.encode(data)}, function (result) {
                    if (result.success) {
                        mini.alert('案卷包信息保存成功!', '系统提示', function () {
                            <#if CreateType=1>
                            var con = mini.getByName('pid');
                            con.setValue(result.data);
                            <#else>
                            mainForm.setData(result.data || {});
                            </#if>
                            mode = "Edit";
                            allCon.disable(false);
                            allCon.doLayout();
                        });
                        grid1.reload();
                        grid2.reload();
                        grid3.reload();
                        grid4.reload();
                    } else mini.alert(result.message || "保存信息失败，请稍候重试!");
                })
            } catch (e) {
                mini.alert(e);
            }
        }

        function getAgentCompanyName() {
            var con = mini.getbyName('agentName');
            return con.getValue();
        }

        //代理公司选择时发生。
        function onAgentChanged(e) {
            var con = mini.getbyName('agentCode');
            if (con) {
                con.setValue(e.selected.code);
            }
        }

        function beforeEditAgent(e) {
            var field = e.field;
            if (field == "Name") {
                var editor = e.editor;
                editor.load('/cpc/getAgents?CompanyName=' + getAgentCompanyName());
            }
        }

        function endEditAgent(e) {
            var field = e.field;
            var row = e.record;
            if (field == "Name") {
                var editor = e.editor;
                var selRow = editor.getSelected();
                if (selRow) {
                    var newRow = {Code: selRow.code, Phone: selRow.name};
                    grid3.updateRow(row, newRow);
                    grid3.accept();
                }
            }
        }

        function doQuery() {
            var txtQuery = mini.get('keyText');
            //备注/流水号/业备数量/客户/商务人员
            var txt = txtQuery.getValue();
            var cs = [];
            var arg = {};
            if (txt) {
                var fields = ['Memo', "SubNo", "ShenqingName", "ClientName", "YName", "Nums", "SignManName",
                    "AuditManName"];
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var obj = {field: field, oper: 'LIKE', value: txt};
                    cs.push(obj);
                }
            }
            if (cs.length > 0) {
                arg["Query"] = mini.encode(cs);
            }
            grid5.load(arg);
        }

        function clearQuery() {
            grid5.reload();
        }

        function beforeload() {
            alert('before');
        }

        function applyManAddress() {
            var applyForm = new mini.Form('#ApplyForm');

            function findNodeIdbyName(name, value) {
                function getFieldByName(name) {
                    var fields = applyForm.getFields();
                    for (var i = 0; i < fields.length; i++) {
                        var field = fields[i];
                        if (field.getName() == name) return field;
                    }
                    return null;
                }

                var field = getFieldByName(name);
                if (field) {
                    var nodes = field.tree.findNodes(function (node) {
                        if (node.Name.indexOf(value) > -1) return true;
                    });
                    if (nodes.length > 0) {
                        return nodes[0].FID;
                    }
                } else return null;
            }

            function isCompany(text) {
                var words = ['公司', '学院', '大学', '小学', '中学', '专营店', '直营店', '营业部', '卖部', '基地', '设计院', '陪训中心', '酒店', '宾馆'];
                for (var i = 0; i < words.length; i++) {
                    var word = words[i];
                    if (text.indexOf(word) > -1) {
                        return true;
                    }
                }
                return false;
            }

            function findByWord(findWord, result) {
                findWord = findWord.replace(/\r\n/g, ' ');
                var ssplit = findWord.split(' ');
                for (var i = 0; i < ssplit.length; i++) {
                    var s = $.trim(ssplit[i]);
                    if (s.length < 2) continue;
                    var rep = true;
                    if (Utils.isEmail(s)) {
                        result.email = s;
                    } else if (isCompany(s)) {
                        result.company = s;
                    } else if (Utils.isOrgCode(s)) {
                        result.orgCode = s;
                    } else if (Utils.isPostCode(s)) {
                        result.postCode = s;
                    } else rep = false;
                    if (rep == true) {
                        findWord = findWord.replace(s, '');
                    }
                    if (rep == false) {
                        if (isComplex(s)) result.address = s;
                    }
                }
                return findWord;
            }

            function isComplex(text) {
                if (!text || text == "") return false;
                if (text.indexOf('(') > -1 || text.indexOf('（') > -1) return true;
                if (text.indexOf('[') > -1 || text.indexOf('【') > -1) return true;
                if ((text.indexOf('-') > -1 || text.indexOf('<') > -1 || text.indexOf('《') > -1)) return true;
                return false;
            }

            function onBodyPaste(e) {
                var options = {
                    type: 0, // 哪种方式解析，0：正则，1：树查找
                    textFilter: [], // 预清洗的字段
                    nameMaxLength: 4, // 查找最大的中文名字长度
                }
                var text = window.event.clipboardData.getData('text');
                if (text) {
                    if (canParseApply()) {
                        var ttt = {};
                        text = findByWord(text, ttt);
                        if(text.indexOf('省')>-1 ||
                            text.indexOf('市')>-1 ||
                            text.indexOf('区')>-1 ||
                            text.indexOf('县') >-1){

                            var result = ZhAddressParse(text, options);
                            if(ttt.company){
                                result.name = ttt.company;
                            }
                            result.email = ttt.email;
                            result.orgCode = ttt.orgCode;
                            if (ttt.address) {
                                result.detail = ttt.address;
                            }
                            result.postCode = ttt.postCode;
                            var data = prepareData(result);
                            applyForm.setData(data);
                        }
                    } else if (canParseInventor(text)) {
                        addInventers(text);
                    }
                }
            }

            function prepareData(result) {
                var data = {country: 'CN', address: 'CN'};
                data.province = findNodeIdbyName('province', result.province);
                data.city = findNodeIdbyName('city', result.city);
                data.street = result.detail;
                if(result.area){
                    data.street=result.area+result.detail;
                    data.street=data.street.replace("市市","市");
                    data.street=data.street.replace("区市","市");
                    data.street=data.street.replace("市区","区");
                    data.street=data.street.replace("县县","县");
                    data.street=data.street.replace("区县","县");
                    data.street=data.street.replace("县区","区");
                }
                data.postCode = result.postCode;
                if (data.street.indexOf(result.province) > -1) {
                    data.street = data.street.replace(result.province, '');
                }
                if (data.street.indexOf(result.city) > -1) {
                    data.street = data.street.replace(result.city, '');
                }
                data.name = result.name;
                data.requestFj = true;

                //data.postCode = companyInfo.postCode;
                data.phone = result.phone;
                data.email = result.email;
                data.idCode = result.orgCode;
                return data;
            }

            function canParseApply() {
                var win = mini.get('#ApplyWindow');
                if (!win) return false;
                var visible = win.getVisible();
                if (visible == false) return false;
                return true;
            }

            function canParseInventor(text) {
                text = $.trim(text);
                if (!text || text == "") return false;
                text = text.replace(/\r\n/g, ',');
                text = text.replace(/，/g, '');
                var ts = text.split(',');
                var re = /^[\u4e00-\u9fa5]{2,4}$/
                for (var i = 0; i < ts.length; i++) {
                    var t = ts[i];
                    var isOk = re.test(t);
                    if (isOk == false) return false;
                }
                return true;
            }

            function addInventers(text) {
                text = $.trim(text);
                text = text.replace(/\r\n/g, ',');
                text = text.replace(/，/g, '');
                var ts = text.split(',');
                for (var i = 0; i < ts.length; i++) {
                    var t = ts[i];
                    addOne(t);
                }

                function addOne(name) {
                    var con = mini.getByName('pid');
                    var mainId = con.getValue();
                    if (mainId) {
                        var row = {Name: name, NotOpen: false, MainID: mainId, First: false, Country: 'CN'};
                        var rows = grid1.getData();
                        if (rows.length == 0) {
                            row.First = true;
                        } else row.Country = null;
                        grid1.addRow(row);
                    } else mini.alert('数据出现异常，请刷新后再试。');
                }
            }

            this.registerPasteEvent = function () {
                $('body').on('paste', onBodyPaste);
            }
        }
    </script>
</@layout>