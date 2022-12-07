<#include "/shared/layout.ftl">
<@layout>
    <link rel="stylesheet" href="/js/kindeditor/themes/default/default.css"/>
    <script type="text/javascript" src="/js/kindeditor/kindeditor-all-min.js"></script>
    <div class="mini-fit" style="width:100%;height:100%;overflow:hidden">
        <div class="mini-toolbar">
            <a class="mini-button" iconCls="icon-ok" onclick="send">发送邮件</a>
        </div>
        <div class="mini-fit">
            <div id="EmailForm" style="width:100%;height:100%">
                <table>
                    <tr>
                        <td style="width:100px;padding-left:15px">收件人:</td>
                        <td>
                            <input id="EmailAddress" style="width:95%" class="mini-textboxlist" remote="false"
                                  required="true" requirederrortext="接收人邮箱不能为空" readOnly="true"  value="${ClientEmail}" text="${ClientName}"/>
                            <button class="mini-button" plain="true" onclick="onCustomLinkerDialog">....</button>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;padding-left:15px">主题</td>
                        <td>
                            <input class="mini-textbox" style="width:100%" id="Subject" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:100px;padding-left:15px">附件</td>
                        <td>
                            <input class="mini-textboxlist" style="width:100%" remote="false" id="Attachment"/>
                        </td>
                    </tr>
                </table>
                <table style="width:100%">
                    <tr>
                        <td colspan="3">
                            <div name="editor" id="editor" rows="10" cols="80" style="width:100%;height:400px"></div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var editor = null;
        var records=null;
        var postRecords=[];
        var khIds="${KH}";
        $(function () {
            editor = KindEditor.create('#editor');
        });

        function setSubject(title) {
            mini.get('#Subject').setValue(title);
        }
        function  setRecords(objs){
            records=objs;
        }

        function send() {
            var form = new mini.Form('#EmailForm');
            form.validate();
            if (form.isValid()) {
                try {
                    var obj = getValue();
                    var msgId = mini.loading("正在通过服务器发送邮件，请等待.........", "提示信息");
                    var url = '/common/email/sendEmail';
                    $.post(url, {data: mini.encode(obj),Record:mini.encode(postRecords)}, function (r) {
                        mini.hideMessageBox(msgId);
                        if (r.success) {
                            eventfun.complete(obj);
                            mini.alert('邮件发送成功!', '系统提示', function () {
                                if (CloseOwnerWindow) CloseOwnerWindow('ok');
                            });
                        } else {
                            var msg = r.message || "发送邮件失败，请稍候重试。";
                            mini.alert(msg);
                        }
                    });
                } catch (e) {
                    mini.alert(e);
                }
            } else {
                mini.alert('邮件内容不完整，不能发送。');
            }

            function getValue() {
                var obj = {};
                var addValue = mini.get('#EmailAddress').getValue();
                var addText = mini.get('#EmailAddress').getText();
                var values = addValue.split(',');
                var texts = addText.split(',');
                var ass = [];
                for (var i = 0; i < values.length; i++) {
                    var address = {};
                    var emailAddress = values[i];
                    if (Utils.isEmail(emailAddress)) {
                        address["value"] = values[i];
                        address["text"] = texts[i];
                        ass.push(address);
                    } else {
                        throw emailAddress + "不是一个合法的邮件地址!";
                    }
                }
                if(records){
                    for(var n=0;n<records.length;n++){
                        var re=records[n];
                        re.Client=addText;
                        re.Email=addValue;
                        postRecords.push(re);
                    }
                }
                obj["receAddress"] = ass;
                var subject = mini.get('#Subject').getValue();
                obj.subject = subject;
                var con = mini.get('#Attachment');
                var attPath = con.getValue();
                var attName = con.getText();
                var add = [];
                var atts = attPath.split(',');
                var attn = attName.split(',');
                for (var i = 0; i < atts.length; i++) {
                    var id = atts[i];
                    var text = attn[i];
                    var ox = {value: id, text: $.trim(text)};
                    add.push(ox);
                }
                obj.attachments = add;
                var content = editor.html();
                if (content) {
                    obj.content = encodeURIComponent(content);
                }
                return obj;
            }
        }

        function onCustomLinkerDialog(e) {
            var btnEdit = this;
            var conEmail=mini.get('#EmailAddress');
            mini.open({
                url: "/systems/client/queryAll?KHID="+khIds,
                title: "选择联系人邮箱(自行录入的邮箱需按回车键确认)",
                width: 1000,
                height: 450,
                ondestroy: function (action) {
                    if (action == "ok") {
                        var iframe = this.getIFrameEl();
                        var data = iframe.contentWindow.GetData();
                        data = mini.clone(data);
                        if (data.length>0) {
                            conEmail.setValue(data.value);
                            conEmail.setText(data.text);
                        }
                    }
                },
                onload:function(){
                    var obj={text:conEmail.getText(),value:conEmail.getValue()};
                    var iframe = this.getIFrameEl();
                    iframe.contentWindow.SetData(obj);
                }
            });
        }

        function addAttachment(atts) {
            var con = mini.get('#Attachment');
            if (con) {
                con.setData(atts);
                if (atts.length == 1) {
                    var id = atts[atts.length - 1].id;
                    var text = atts[atts.length - 1].text;
                    con.setValue(id);
                    var fileName = text;
                    if (fileName.toString().indexOf('.') == -1) {
                        fileName += '.zip';
                    }
                    con.setText(fileName);
                } else {
                    var ids = [];
                    var texts = [];
                    for (var i = 0; i < atts.length; i++) {
                        var att = atts[i];
                        ids.push(att.id);
                        if (att.text.indexOf('.') == -1) att.text = att.text + ".zip";
                        texts.push(att.text);
                    }
                    con.setValue(ids.join(','));
                    con.setText(texts.join(','));
                }
            }
        }

        function getContent(type, rows) {
            var url = '/common/email/getMailContent?Code=${Code}';
            $.post(url, {rows: encodeURIComponent(rows), Type: type}, function (result) {
                if (result.success) {
                    editor.html(unescape(result.data));
                } else {
                    mini.alert(result.message || "获取邮件数据失败，请稍候重试!");
                }
            })
        }

        var eventfun = {
            complete: function () {

            }
        };

        function addEvent(event, fun) {
            if (eventfun[event]) {
                eventfun[event] = fun;
            }
        }
    </script>
</@layout>