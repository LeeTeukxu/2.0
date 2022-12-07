<#include "/shared/layout.ftl">
<@layout>
    <style>
        .mini-htmlfile .mini-buttonedit-button {
            width: 400px
        }

    </style>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <link href="/js/miniui/themes/default/large-mode.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/js/miniui/ajaxfileUpload.js"></script>
    <script type="text/javascript" src="/js/common/jquery.fileDownload.js"></script>
    <div class="mini-tabs" tabPosition="bottom" bodyStyle="overflow:hidden"
         style="height:100%;width:100%;margin-left:-5px;margin-top:2px;overflow: hidden">
        <div title="公司基本信息" bodyStyle="overflow:hidden">
            <a class="mini-button mini-button-primary" id="CompanyConfig_Save" onclick="save()">保存信息</a>
            <div id="editform" class="mini-fit" style="overflow: hidden">
                <table style="width: 100%; height: 100%;overflow: hidden" class="layui-table">
                    <tr>
                        <td class="showlabel">公司名称</td>
                        <td colspan="4">
                            <input name="name" class="mini-textbox" style="width:100%"
                                   required="true"/>
                            <input class="mini-hidden" name="id"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">联系电话</td>
                        <td>
                            <input name="phone" class="mini-textbox" style="width:100%"
                                   required="true"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                        <td class="showlabel">公司地址</td>
                        <td>
                            <input name="address" class="mini-textbox" style="width:100%"
                                   required="true"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">开户行帐号</td>
                        <td>
                            <input name="bankNo" class="mini-textbox" style="width:100%"
                                   required="true"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                        <td class="showlabel">收款人</td>
                        <td>
                            <input name="bankMan" class="mini-textbox" style="width:100%"
                                   required="true"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">开户行名称</td>
                        <td colspan="4">
                            <input name="bankName" class="mini-textbox" style="width:100%"
                                   required="true"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">公司简介</td>
                        <td colspan="4">
                   <textarea name="memo" class="mini-textarea" style="width:100%;
                   height:80px" required="true"></textarea>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">经营范围
                        </td>
                        <td colspan="4">
                    <textarea name="range" class="mini-textarea" style="width:100%;
                    height:80px" required="true"></textarea>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">公司公章</td>
                        <td>
                            <img id="sealImg" style="width:220px;height:200px" name="imageFile"/>
                        </td>
                        <td colspan="3">
                            <input class="mini-hidden" name="image"/>
                            <input class="mini-htmlfile" name="imageFile" id="file1"
                                   style="width:400px"
                                   buttonText="上传底色透明的JPG文件,最大不超过500K"
                                   limitType="*.jpg;*.png" buttonWidth="400px"
                                   onfileselect="onupload()"/>
                        </td>
                        <td class="blank"><span style="color: red"></span></td>
                    </tr>
                </table>
            </div>
        </div>
        <div title="Excel模版">
            <div class="mini-layout" style="width:100%;height:100%;margin:0px;padding:0px">
                <div region="west" title="模版列表" width="240px" showTitle="true">
                    <ul class="mini-tree" id="tree1" style="width:100%;height:100%" onnodeclick="onNodeClick"></ul>
                </div>
                <div region="center">
                    <div class="mini-toolbar">
                        <a class="mini-button mini-button-primary" iconCls="icon-download" onclick="download()">下载模版</a>
                        <a class="mini-button mini-button-info" iconCls="icon-upload" onclick="doUpload()">更新模版</a>

                        <input type="file" id="file2" name="excelFile"
                               style="width:1px;display: none"
                               accept=".xls" buttonWidth="1px"
                               onchange="onExcelChange()"/>
                    </div>
                    <div class="mini-fit" id="Fit2">
                        <div class="mini-datagrid" id="grid1" style="width:100%;height:100%"
                             url="/companyConfig/getExcelImage" autoload="false">
                            <div property="columns">
                                <div type="indexcolumn"></div>
                                <div field="name" width="200" headerAlign="center" align="center">名称</div>
                                <div field="templatePath" width="400" headerAlign="center" align="center">模版文件</div>
                                <div field="createTime" width="150" headerAlign="center" align="center" dataType="date"
                                     dateFormat="yyyy-MM-dd">创建时间
                                </div>
                                <div field="updateTime" width="150" headerAlign="center" align="center" dataType="date"
                                     dateFormat="yyyy-MM-dd">更新时间
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div title="邮件基本信息">
        <a class="mini-button mini-button-primary"  onclick="SaveMailConfig()">保存信息</a>
            <div id="mailform" class="mini-fit" style="overflow: hidden">
                <table style="width: 100%; height: 300px;overflow: hidden" class="layui-table">
                    <tr>
                        <td class="showlabel">联系电话</td>
                        <td>
                            <input name="phone" class="mini-textbox" style="width:100%"
                                   required="true"/>
                            <input class="mini-hidden" name="id"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                        <td class="showlabel">公司地址</td>
                        <td>
                            <input name="address" class="mini-textbox" style="width:100%"
                                   required="true"/>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">公司简介</td>
                        <td colspan="4">
                   <textarea name="memo" class="mini-textarea" style="width:100%;
                   height:80px" required="true"></textarea>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                    <tr>
                        <td class="showlabel">经营范围
                        </td>
                        <td colspan="4">
                    <textarea name="range" class="mini-textarea" style="width:100%;
                    height:80px" required="true"></textarea>
                        </td>
                        <td class="blank"><span style="color: red">&nbsp;</span></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var formData = ${LoadData};
        var mailformdata = ${MailLoadData};
        var tree = mini.get('#tree1');
        var form = new mini.Form("#editform");
        var mailform = new mini.Form("#mailform");
        var grid = mini.get('#grid1');
        form.setData(formData);
        mailform.setData(mailformdata);
        $('#sealImg').attr('src', formData.image);
        var fit2 = mini.get('#Fit2');

        function save() {
            form.validate();
            if (form.isValid() == false) {
                mini.alert('数据录入不完整!');
                return;
            }
            var data = form.getData();
            var url = '/companyConfig/save';
            $.post(url, {Data: mini.encode(data)}, function (result) {
                if (result.success) {
                    mini.alert('公司信息保存成功！', '系统提示', function () {
                        form.setData(result.data);
                        $('#sealImg').attr('src', result.data.image);
                    });
                }
            });
        }
        function SaveMailConfig() {
            mailform.validate();
            if (mailform.isValid() == false) {
                mini.alert('数据录入不完整!');
                return;
            }
            var data = mailform.getData();
            var url = '/companyConfig/saveMailConfig';
            $.post(url, {Data: mini.encode(data)}, function (result) {
                if (result.success) {
                    mini.alert('邮件基本信息保存成功！', '系统提示', function () {
                        mailform.setData(result.data);
                    });
                }
            });
        }

        function onupload() {
            var htmlFile = mini.get('#file1');
            htmlFile.validate();
            if (htmlFile.isValid()) {
                var gg = mini.loading('正在上传公章图片请稍候.......');
                var inputFile = $("#file1>input:file")[0];
                $.ajaxFileUpload({
                    url: '/companyConfig/uploadImage',   //用于文件上传的服务器端请求地址
                    fileElementId: inputFile,            //文件上传域的ID
                    dataType: 'text',                   //返回值类型 一般设置为json
                    success: function (data, status)    //服务器成功响应处理函数
                    {
                        mini.hideMessageBox(gg);
                        var dx = null;
                        if (data.toString().indexOf("<pre") == -1) dx = mini.decode(data);
                        else {
                            var ddom = $(data);
                            dx = mini.decode(ddom.text());
                        }
                        if (dx.success) {
                            $('#sealImg').attr('src', dx.data);
                            mini.getbyName('image').setValue(dx.data);
                        } else {
                            mini.alert(dx.message);
                            htmlFile.clear();
                        }
                    },
                    error: function (data, status, e)   //服务器响应失败处理函数
                    {
                        alert(e);
                        htmlFile.clear();
                    },
                    complete: function () {
                        var jq = $("#file1 > input:file");
                        jq.before(inputFile);
                        jq.remove();
                        htmlFile.clear();
                    }
                });
            } else {
                mini.alert('请上传正确格式的公章图片!');
                htmlFile.clear();
            }
        }

        function onNodeClick(e) {
            var node = e.node;
            if (node) {
                var code = node.name;
                grid.load({Code: code});
            }
        }

        function download() {
            var rows = grid.getData();
            if (rows.length > 0) {
                var row = rows[0];
                $.fileDownload('/companyConfig/download?Code=' + row["code"], {
                    httpMethod: 'POST',
                    prepareCallback: function (url) {

                    },
                    failCallback: function (html, url) {
                        mini.alert('下载错误:' + html, '系统提示');
                    }
                });
            } else mini.alert('请先选择模版!');
        }

        function doUpload() {
            var file2 = document.getElementById('file2');
            if (file2) file2.click();
        }

        function onExcelChange() {
            var rows = grid.getData();
            if (rows.length > 0) {
                var row = rows[0];
                var gg = mini.loading('正在上传文件请稍候.......');

                var inputFile = document.getElementById('file2')
                $.ajaxFileUpload({
                    url: '/companyConfig/uploadExcel?Code=' + row["code"],   //用于文件上传的服务器端请求地址
                    fileElementId: 'file2',            //文件上传域的ID
                    dataType: 'text',                   //返回值类型 一般设置为json
                    success: function (data, status)    //服务器成功响应处理函数
                    {
                        mini.hideMessageBox(gg);
                        var dx = null;
                        if (data.toString().indexOf("<pre") == -1) dx = mini.decode(data);
                        else {
                            var ddom = $(data);
                            dx = mini.decode(ddom.text());
                        }
                        if (dx.success) {
                            mini.alert('模版文件更新成功!');
                            grid.reload();
                        } else {
                            mini.alert(dx.message);
                        }
                    },
                    error: function (data, status, e)   //服务器响应失败处理函数
                    {
                        alert(e);
                    },
                    complete: function () {
                        var jq = $("#file2");
                        jq.before(inputFile);
                        jq.remove();
                    }
                });
            }
        }

        $(function () {
            var nodes = [
                {id: '1', pid: '0', text: '缴费通知书模版', name: 'OneYear'},
                {id: '2', pid: '0', text: '专利业务交单对帐单', name: 'CasesBill'},
                {id: '3', pid: '0', text: '项目业务交单对帐单', name: 'HighBill'},
                {id: '4', pid: '0', text: '商标业务交单对帐单', name: 'OtherBill'},
                {id: '5', pid: '0', text: '待缴费用导出清单', name: 'WaitFee'}
            ];
            $('.mini-layout-region-title').css('color', 'black').show();
            tree.loadList(nodes);
        })
    </script>
</@layout>
