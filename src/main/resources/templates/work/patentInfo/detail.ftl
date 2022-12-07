<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <style type="text/css">
        .asLabel .mini-textbox-border,
        .asLabel .mini-textbox-input,
        .asLabel .mini-buttonedit-border,
        .asLabel .mini-buttonedit-input,
        .asLabel .mini-textboxlist-border
        {
            border:0;background:none;cursor:default;
            word-wrap: break-word;

        }
        .asLabel .mini-buttonedit-button,
        .asLabel .mini-textboxlist-close
        {
            display:none;
        }
        .asLabel .mini-textboxlist-item
        {
            padding-right:8px;
        }
        .showCellTooltip {
            color: blue;text-decoration: underline;
        }
    </style>
    <script type="text/javascript">
        var types = [{id: 0, text: '发明专利'}, {id: 1, text: '实用新型'}, {id: 2, text: '外观设计'}];
        var states = [
            {id:0,text:'待分类'},{id:1,text:'待自取'}, {id:2,text:'已自取'},{id:3,text:'待寄送'}, {id:4,text:'已寄送'}
        ];
        var yjzt=[ { id: '0', text: '待分类' }, { id: '1', text: '待自取' }, { id: '2', text: '已自取' }];
    </script>
    <table style="width:100%;height:180px" class="layui-table" id="f1">
        <tr>
            <td>专利号</td>
            <td>
                <input class="mini-textbox" style="width:100%;height:100%" value="${pantent.shenqingh}"/>
            </td>
            <td>专利名称</td>
            <td>
                <input class="mini-textbox" style="width:100%;height:100%" value="${pantent.famingmc!""}"/>
            </td>
            <td>专利类型</td>
            <td>
                <input class="mini-combobox" style="width:100%;height:100%" data="types" value="${pantent
                .shenqinglx!"0"}"/>
            </td>
        </tr>
        <tr>
            <td>申请日期</td>
            <td>
                <input class="mini-datepicker" style="width:100%;height:100%" value="${(pantent.shenqingr?string("yyyy-MM-dd HH:mm:ss"))!}"/>
            </td>
            <td>专利状态</td>
            <td>
                <input class="mini-textbox" style="width:100%;height:100%" value="${pantent.anjianywzt!""}"/>
            </td>
            <td>内部编号</td>
            <td>
                <textarea class="mini-textarea" style="width:100%;height:100%" value="${pantent.neibubh!""}"></textarea>
            </td>
        </tr>
        <tr>
            <td>申请人</td>
            <td>
                <input class="mini-textbox" style="width:100%;height:100%" value="${pantent.shenqingrxm!""}"/>
            </td>
            <td>发明人</td>
            <td>
                <textarea class="mini-textarea" style="width:100%;height:100%" value="${pantent.famingrxm!""}"></textarea>
            </td>
            <td>所属客户</td>
            <td>
                <input class="mini-textbox" style="width:100%;height:100%" value="${KH!""}"/>
            </td>
        </tr>
        <tr>
            <td>销售责任人</td>
            <td>
                <input  class="mini-textbox" style="width:100%;height:100%" value="${YW!""}"/>
            </td>
            <td>代理责任人</td>
            <td>
                <input class="mini-textbox" style="width:100%;height:100%" value="${JS!""}"/>
            </td>
            <td>流程负责人</td>
            <td>
                <input class="mini-textbox" style="width:100%;height:100%" value="${LC!""}"/>
            </td>
        </tr>
    </table>
    <div class="mini-fit">
        <div class="mini-tabs" style="width:100%;height:100%" onactivechanged="tabChange">
            <div title="专利交单信息">
                <table style="width:100%;height:200px" class="layui-table" id="f2">
                    <tr>
                        <td>业务情况简介</td>
                        <td colspan="5">
                            <textarea class="mini-textarea" style="width:100%;height:60px" value="${main
                            .memo!""}"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>客户名称</td>
                        <td>
                            <input class="mini-treeselect" url="/systems/client/getAllClientTree" value="${(main
                            .clientId?c)!}"  style="width:100%;height:100%"/>
                        </td>
                        <td>合同编号</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${main.contractNo!""}"/>
                        </td>
                        <td>业务数量</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${main.nums!""}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>专利申请费</td>
                        <td>
                            <input class="mini-combobox" style="width:100%;height:100%" valueField="id" textField="text"
                                   url="/systems/dict/getByDtId?dtId=8" value="${(main.zuanLiFei?c)!}"/>
                        </td>
                        <td>费减情况</td>
                        <td>
                            <input class="mini-combobox" style="width:100%;height:100%"  valueField="id" textField="text"
                                   url="/systems/dict/getByDtId?dtId=23" value="${(main.feiJian?c)!}"/>
                        </td>
                        <td>交单日期</td>
                        <td>
                            <input class="mini-datepicker" style="width:100%;height:100%"
                                   value="${(main.signTime?string("yyyy-MM-dd HH:mm:ss"))!""}" />
                        </td>
                    </tr>
                    <tr>
                        <td>商务人员</td>
                        <td>
                            <input  style="width:100%;height:100%" class="mini-treeselect" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID" value="${main.createMan!""}"/>
                        </td>
                        <td>流程人员</td>
                        <td>
                            <input class="mini-treeselect" style="width:100%;height:100%" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID" value="${main.auditMan!""}"/>
                        </td>
                        <td>审核日期</td>
                        <td>
                            <input class="mini-datepicker" style="width:100%;height:100%"
                                   value="${(main.auditTime?string("yyyy-MM-dd HH:mm:ss"))!""}"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6">
                            <hr/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td>
                            <textarea class="mini-textarea" style="width:100%;height:100%" value="${sub.memo!}"></textarea>
                        </td>
                        <td>立案编号</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${sub.subNo!}"/>
                        </td>
                        <td>业务名称</td>
                        <td>
                            <input class="mini-textbox" style="width:100%" value="${sub.yName!}" />
                        </td>
                    </tr>
                    <tr>
                        <td>专利初步名称</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${sub.shenqingName!}"/>
                        </td>
                        <td>案件级别</td>
                        <td>
                            <input style="width:100%;height:100%"  class="mini-combobox" url="/cLevel/getItems"
                                   value="${sub.cLevel!}"/>
                        </td>
                        <td>关联案件</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${sub.relNo!}"/>
                        </td>
                    </tr>

                    <tr>
                        <td>技术交底附件</td>
                        <td>
                            <#if sub.subId??>
                                <#if sub.techFiles??>
                                    <a class="mini-button showCellTooltip" plain="true" style="width:100%"
                                       onclick="uploadRow('Tech','Browse')">查看技术交底文件</a>
                                </#if>
                            </#if>
                        </td>
                        <td>著录信息附件</td>
                        <td>
                             <#if sub.subId??>
                               <#if sub.zlFiles??>
                                    <a class="mini-button showCellTooltip" plain="true" style="width:100%"  onclick="uploadRow('Zl','Browse')">查看著录文件</a>
                               </#if>
                             </#if>
                        </td>
                        <td>专利申报稿件</td>
                        <td>
                            <#if sub.subId??>
                                <#if sub.acceptTechFiles??>
                                    <a class="mini-button showCellTooltip" plain="true" style="width:100%"
                                       onclick="uploadRow('Accept','Browse')">查看专利申报稿件</a>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                    <tr>
                        <td>专利代理师</td>
                        <td>
                            <input class="mini-treeselect" style="width:100%;height:100%" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID" value="${sub.techMan!""}"/>
                        </td>
                        <td>技审经理</td>
                        <td>
                            <input class="mini-treeselect" style="width:100%;height:100%" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID" value="${sub.techAuditMan!""}"/>
                        </td>
                        <td>专利申报人</td>
                        <td>
                            <input class="mini-treeselect" style="width:100%;height:100%" url="/systems/dep/getAllLoginUsersByDep"
                                   textField="Name" valueField="FID" parentField="PID" value="${sub.techSbMan!""}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>客户联系人</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${sub.clientLinkMan!}"/>
                        </td>
                        <td>联系电话</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${sub.clientLinkPhone!}"/>
                        </td>
                        <td>联系邮箱</td>
                        <td>
                            <input class="mini-textbox" style="width:100%;height:100%" value="${sub.clientLinkMail!}"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="通知书列表" name="f3">
                <div class="mini-datagrid" style="width:100%;height:100%" id="tzsGrid"
                     url="/work/patentInfo/getTZS?shenqingh=${Shenqingh}" autoload="false" ondrawcell="drawTZS">
                    <div property="columns">
                        <div type="checkcolumn"></div>
                        <div field="fawenxlh" width="150" headerAlign="center" align="center">发文序列号</div>
                        <div field="tongzhisbh" width="150" headerAlign="center"  align="center">通知书编号</div>
                        <div field="tongzhismc" width="150" headerAlign="center" align="center">通知书名称</div>
                        <div field="fawenrq" headerAlign="center" dataType="date" dateFormat="yyyy-MM-dd" align="center"
                             width="100" allowSort="true" >发文日期
                        </div>
                        <div field="xiazairq" dataType="date" dateFormat="yyyy-MM-dd hh:mm:ss" width="160"
                             headerAlign="center" align="center" allowSort="true">下载日期
                        </div>
                        <div field="Att"   headerAlign="center" align="center" >通知书附件</div>
                    </div>
                </div>
            </div>
            <div title="待缴官费列表" name="f4">
                <div id="GovWait_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                     frozenstartcolumn="0" frozenendcolumn="6" sortorder="asc" sortfield="shenqingh"
                     ondrawcell="onDraw" allowresize="true" url="/work/govFee/getData?PayState=0&AppNo=${Shenqingh}"
                     multiselect="true" pagesize="20" sizelist="[5,10,20,50,100]" autoload="false">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="JIAOFEIR" name="JIAOFEIR" width="100" allowsort="true" headeralign="center"
                             align="center" dataType="date" dateFormat="yyyy-MM-dd">
                            最后缴费日
                        </div>
                        <div field="FEENAME" name="FEENAME" width="140" allowsort="true" headeralign="center">
                            费用项目
                        </div>
                        <div field="MONEY" name="MONEY" width="60" allowsort="true"
                             headeralign="center" align="right" dataType="float">
                            金额
                        </div>
                        <div field="SHENQINGH" name="shenqingh" width="120" allowsort="true" headeralign="center"
                             renderer="onZhanlihao">
                            专利申请号
                        </div>
                        <div field="FAMINGMC" name="FAMINGMC" width="200" allowsort="true" headeralign="center">
                            专利名称
                        </div>
                        <div field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="SHENQINGR" name="shenqingr" width="80" allowsort="true" headeralign="center"
                             datatype="date" dateformat="yyyy-MM-dd">
                            申请日
                        </div>

                        <div field="ANJIANYWZT" width="80" allowsort="true" headeralign="center">
                            专利法律状态
                        </div>
                        <div field="SHENQINGRXM" width="200" allowsort="true" headeralign="center">
                            专利申请人
                        </div>
                        <div field="FAMINGRXM" width="130" allowsort="true" headeralign="center">
                            专利发明人
                        </div>
                    </div>
                </div>
            </div>
            <div title="已缴官费列表" name="f5">
                <div id="GovPay_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                     frozenstartcolumn="0" frozenendcolumn="6" sortorder="asc" sortfield="shenqingh"
                     ondrawcell="onDraw" allowresize="true" url="/work/govFee/getData?PayState=1&AppNo=${Shenqingh}" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50,100]" autoload="false">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div type="checkcolumn"></div>
                        <div field="JIAOFEIR" name="JIAOFEIR" width="100" allowsort="true" headeralign="center"
                             align="center" dataType="date" dateFormat="yyyy-MM-dd">
                            缴费日期
                        </div>
                        <div field="FEENAME" name="FEENAME" width="140" allowsort="true" headeralign="center">
                            费用项目
                        </div>
                        <div field="MONEY" name="MONEY" width="60" allowsort="true" headeralign="center" align="right"
                             dataType="float">
                            金额
                        </div>
                        <div field="SHENQINGH" name="shenqingh" width="120" allowsort="true" headeralign="center"
                             renderer="onZhanlihao">
                            专利申请号
                        </div>
                        <div field="FAMINGMC" name="FAMINGMC" width="200" allowsort="true" headeralign="center">
                            专利名称
                        </div>
                        <div field="SHENQINGLX" width="80" headerAlign="center" type="comboboxcolumn" allowsort="true">
                            专利类型
                            <input property="editor" class="mini-combobox" data="types"/>
                        </div>
                        <div field="SHENQINGR" name="shenqingr" width="80" allowsort="true" headeralign="center"
                             datatype="date" dateformat="yyyy-MM-dd">
                            申请日
                        </div>

                        <div field="ANJIANYWZT" width="80" allowsort="true" headeralign="center">
                            专利法律状态
                        </div>
                        <div field="SHENQINGRXM" width="200" allowsort="true" headeralign="center">
                            专利申请人
                        </div>
                        <div field="FAMINGRXM" width="130" allowsort="true" headeralign="center">
                            专利发明人
                        </div>
                    </div>
                </div>
            </div>
            <div title="原件列表" name="f6">
                <div id="Orgin_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                     sortorder="desc" sortfield="CreateTime"
                     allowresize="true" url="/express/orig/getData?Shenqingh=${Shenqingh}" multiselect="true"
                     pagesize="20" sizelist="[5,10,20,50,100]" autoload="false">
                    <div property="columns">
                        <div type="indexcolumn"></div>
                        <div field="otypeText" width="80" headeralign="center" align="center" allowsort="true">
                            原件类型
                        </div>
                        <div field="TONGZHISMC" width="300" headeralign="center" allowsort="true" align="center" >
                            通知书名称
                        </div>
                        <div field="ostateText"  width="80" headerAlign="center" type="treeselectcolumn" allowsort="true">
                            原件状态
                            <input property="editor" class="mini-combobox" data="states" />
                        </div>
                        <div field="dnum" width="160" headeralign="center" align="right" allowsort="true" title="发文序号/收据号/证书号">
                            二维编码
                        </div>
                        <div field="DrawNo" width="120" headeralign="center" allowsort="true">
                            自取编号
                        </div>
                        <div field="CreateTime" width="120" headeralign="center" datatype="date" dateformat="yyyy-MM-dd" allowsort="true">登记时间</div>
                        <div field="CreateEmp" width="80" headeralign="center" type="treeselectcolumn" allowsort="true">
                            登记人
                            <input property="editor" class="mini-treeselect" valueField="FID" parentField="PID"
                                   textField="Name" url="/systems/dep/getAllLoginUsersByDep" id="CreateEmp" style="width:
                               98%;" required="true" resultAsTree="false"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var tzsGrid=mini.get('#tzsGrid');
        var waitGrid=mini.get('#GovWait_datagrid');
        var payGrid=mini.get('#GovPay_datagrid');
        var orgGrid=mini.get('#Orgin_datagrid');
        function onDraw(e) {
            var field = e.field;
            var record = e.record;
            if (field == "KH") {
                var clientId = record["KHID"];
                var val = e.value;
                if (clientId) {
                    e.cellHtml = '<a href="javascript:void(0)" onclick="showClient(' + "'" + clientId + "'" + ')">' + val + '</a>';
                } else e.cellHtml = val;
            } else if (field == "SHENQINGLX") {
                var val = parseInt(e.value);
                var textVal = "";
                for (var i = 0; i < types.length; i++) {
                    var tt = types[i];
                    if (tt.id == val) {
                        textVal = tt.text;
                        break;
                    }
                }
                switch (val) {
                    case 0: {
                        e.cellHtml = "<span style='color:red'>" + textVal + "</style>";
                        break;
                    }
                    case 1: {
                        e.cellHtml = "<span style='color:black'>" + textVal + "</style>";
                        break;
                    }
                    case 2: {
                        e.cellHtml = "<span style='color:blue'>" + textVal + "</style>";
                        break;
                    }
                }
            }
        }
        $(function(){
            var f1=new mini.Form('#f1');
            var f2=new mini.Form('#f2');
            var fields = f1.getFields();
            var f22=f2.getFields();
            for(var i=0;i<f22.length;i++)fields.push(f22[i]);
            for (var i = 0, l = fields.length; i < l; i++) {
                var c = fields[i];
                if (c.setReadOnly) c.setReadOnly(true);     //只读
                if (c.setIsValid) c.setIsValid(true);      //去除错误提示
                if (c.addCls) c.addCls("asLabel");          //增加asLabel外观
            }
        })

        function viewDocument(id) {
            var row = tzsGrid.getRowByUID(id);
            var dx= mini.loading('正在获取文件数据......');
            var arg={'tongzhisbh': row["tongzhisbh"]};
            var url='/watch/addYearWatchItem/getAllImages';
            $.getJSON(url,arg, function (result) {
                mini.hideMessageBox(dx);
                var isOK = parseInt(result.status);
                if (isOK == 1) {
                    window.parent.showImages(mini.encode(result));
                } else {
                    var msg = result.message || "无法加载通知书附件。";
                    mini.alert(msg);
                }
            });
            return false;
        }
        function download(id) {
            var row = tzsGrid.getRowByUID(id);
            var code = row["tongzhisbh"];
            var name = row["famingmc"]+'_'+row["tongzhismc"]+'.zip';
            var url = '/work/notice/download?Code=' + code + '&FileName=' + encodeURI(name);
            $.fileDownload(url, {
                httpMethod: 'POST',
                failCallback: function (html, xurl) {
                    mini.alert('下载错误:' + html, '系统提示');
                }
            });
            return false;
        }
        function drawTZS(e){
            var field=e.field;
            var row=e.record;
            if(field=="Att"){
                var id=row._id;
                var tzspath=row["tzspath"];
                if(tzspath){
                    var tt='<a href="javascript:viewDocument(\''+ id+'\');" ' +
                        'class="showCellTooltip">查看通知书</a>';
                    tt+="&nbsp;&nbsp;&nbsp;";
                    tt+='<a href="javascript:download(\'' +id+'\');" ' +
                        'class="showCellTooltip">下载通知书</a>';
                    e.cellHtml=tt;
                }
            }
        }

        function uploadRow(type, mode) {
            var subId ="${sub.subId!}";
            var casesId ="${sub.casesId!}";

            var url = '/cases/getSubFiles';
            $.getJSON(url, {SubID: subId, Type: type}, function (result) {
                if (result.success) {
                    var att = result.data || [];
                    doUpload(casesId, subId, att, type, mode);
                }
            });
        }
        function doUpload(casesId, subId, ids, type, mode) {
            var title = '技术交底资料';
            if (type == "Zl") title = "著录信息资料";
            if (type == "Accept") title = "专利申报文件";
            if (type == "Exp") title = "情况说明文件";
            if (type == 'Aud') title = '内审驳回说明文件';
            var showHis = 0;
            if (type == "Accept" || type == "Exp" || type == "Aud") showHis = 1;
            var attId = "";
            if (ids.length > 0) attId = ids.join(",");
            mini.open({
                url: '/attachment/addFile?IDS=' + attId + '&Mode=' + mode + '&ShowHis=' + showHis,
                width: 800,
                height: 400,
                title: title
            });
        }
        function tabChange(e){
            var id=e.name;
            switch (id){
                case "f3":{
                    tzsGrid.reload();
                    break;
                }
                case　"f4":{
                    waitGrid.reload();
                    break;
                }
                case "f5":{
                    payGrid.reload();
                    break;
                }
                case "f6":{
                    orgGrid.reload({});
                    break;
                }
            }
        }
    </script>
</@layout>