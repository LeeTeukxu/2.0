<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>知服帮管理系统</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="/css/ionicons.css" rel="stylesheet" type="text/css">
    <link href="/css/jquery.mCustomScrollbar.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="/res/css/global.css"/>
    <link rel="stylesheet" href="/res/css/index.css"/>
    <script type="text/javascript" src="/js/boot.js"></script>
    <script type="text/javascript" src="/js/layui/layui.js"></script>
    <script type="text/javascript" src="/js/common/notification.js"></script>
    <link href="/css/index.css" rel="stylesheet" type="text/css"/>
    <style>
        .menuul {
            position: fixed;
            top: 26px;
            width: 60%;
            margin-left: 160px;
        }

        .Headmenu .menuul li {
            list-style: none;
            font-size: 0.9vw;
            display: inline;
            padding-top: 1px;
            padding: 2%;
        }

        .Headmenu .menuul li a {
            color: #605d5d;
            text-align: center;
        }

        .Headmenu .menuul li:hover {
            background: rgba(235, 225, 31, 0.58);
        }

        .right_jt {
            float: right;
            margin-top: -31px;
            width: 13px;
        }

        .side_tit {
            text-align: center;
            border-bottom: 1px solid rgba(255, 255, 255, 0.23);
            height: 40px;
            padding-top: 10px
        }

        .side_tit span {
            color: #fff;
            font-size: 18px;
            margin-top: 15px;
        }

        .side_tit img {
            float: right;
            margin-top: 5px;
            margin-right: 3px;
            width: 18px;
        }

        .mini-layout-region-title {
            padding-top: 5px;
            font-size: 16px;
        }

        .mini-layout-region-west .mini-layout-region-header .mini-tools-collapse {
            background: url(/appImages/delsc.png) no-repeat 50% 50%;
            margin-top: 5px
        }

        .rightcaidan {
            margin-top: 12px
        }

        .rightcaidan .rgitem li {
            float: left;
            padding: 10px;
        }

        .rightcaidan .rgitem li:hover {
            transform: scale(1.2, 1.2);
            cursor: pointer;
        }

        /*box-shadow: 0 0 10px #0000ff;*/
        .rightcaidan .rgitem li img {
            width: 28px;
        }

        .mini-layout-region-title {
            text-align: center;
            font-weight: bold;
        }

        .mini-layout-region-title {
            color: #fff;
        }
    </style>
</head>

<body style="width:100%;height:100%;overflow:hidden">
<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div region="north" height="70px" bodystyle="border:none;overflow:hidden" splitSize="1" showHeader="false">
        <!--header   头部开始   -->
        <div class="top">
            <div class="logo">
                <img src="/appImages/logo.png">
            </div>
            <div class="logoR">
                <div class="navv">
                    <#if roots??>
                        <#list roots  as root>
                            <#if root?index==0>
                                <a href="javascript:;" menuId="${root.fid}"
                                   class=" navmenu nava">${root.title}</a>
                            <#else>
                                <a href="javascript:;" menuId="${root.fid}" class="nava">${root.title}</a>
                            </#if>
                        </#list>
                    </#if>
                </div>
                <div class="navr">
                    <span><a href="https://www.kancloud.cn/rockyecool/zfbip/1731132" target="_blank"
                             style="color: #666;margin-top:-22px;">帮助文档</a></span>
                    <span id="cmdChangeCompany"><a title="切换公司"><img src="/appImages/hezuo.png" onclick="changeCompany()
"/></a></span>
                    <span><a title="修改密码"><img src="/appImages/pasword.png" onclick="changePassword();"></a></span>
                    <span><a title="退出"><img src="/appImages/out.png" onclick="exitSystem();"></a></span>
                </div>
            </div>
        </div>
        <!-- header_End 头部结束-->
    </div>


    <div style="margin-top: -2px;" visible="false" region="west" width="190" bodyStyle="background-color:#3e5883;"
         headerStyle="background-color:#3e5883;height:40px;font-size:18px;"
         title="基础资料管理" showheader="false" showspliticon="true" showCloseButton="false" showCollapseButton="false"
         showCollapseButton="false" oncollapse="onhide" showSplit="true" showProxy="false">
        <!--左边子菜单-->
        <ul id="dc_accordion" class="sidebar-menu tree">
            <#if firsts??>
                <#list firsts as first>
                    <li url="${first.url}" style="cursor:pointer" class="menudetail">
                        <a href="javascript:;">${first.title}</a>
                        <span class="right_jt"><img src="/appImages/rgjt.png"></span>
                    </li>
                </#list>
            </#if>
        </ul>
    </div>
    <div region="center">
        <div class="mini-tabs" activeIndex="0" id="tab1" style="width:100%;height:98%;background-color: #EAF2FF;
        padding:0px" ontabload="closeLoading();" ontabdestroy="onDestroy" bodyStyle="padding:0px">
            <div title="我的工作台" url="/workBench/index"></div>
            <div title="专利数据更新" url="/workNumber/index"></div>
            <#if LoginCode=="15074835232">
                <div title="管理员工具" url="/admin/index"></div>
            </#if>
        </div>
    </div>
</div>
<div class="mini-window" title="修改登录密码" id="changePasswordWindow" style="width:600px;height:250px">
    <table class="layui-table" style="width:96%;height:100%" cellpadding="5px" id="changeForm">
        <tr>
            <td style="width:150px;text-align: center">原登录密码:</td>
            <td>
                <input class="mini-password" style="width:100%" name="oldPassword" required="true"/>
            </td>
        </tr>
        <tr>
            <td style="width:150px;text-align: center">新登录密码:</td>
            <td>
                <input class="mini-password" style="width:100%" name="newPassword" required="true"/>
            </td>
        </tr>
        <tr>
            <td style="width:150px;text-align: center">确认密码:</td>
            <td>
                <input class="mini-password" style="width:100%" name="confirmPassword" required="true"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <button class="mini-button mini-button-primary" onclick="confirmChange();">确认修改</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button class="mini-button mini-button-danger" onclick="disposeChange();">关闭退出</button>
            </td>
        </tr>
    </table>
</div>
<div id="111111" style="display:none;text-align: center">
    <br/>
    <div bodyStyle="margin-left:10px" id="radioClient" class="xxxx-radiobuttonlist" data="companys"
         valueField="name"
         textField="name"
         value="${Account}"></div>
    <br/>
</div>
<script type="text/javascript">
    mini.parse();
    var notify = new iNotify({
        effect: 'flash',
        interval: 500,
        message: "",
        silent: true,
        notification: {
            title: "通知！",
            body: '您来了一条新消息'
        }
    });

    function showMessage(title, message) {
        notify.setFavicon(15).setTitle('请查看消息').notify({
            title: title,
            body: message
        });
    }

    function createInstance(url, onmessage) {
        if (window.WebSocket == undefined) return false;
        var ws = new WebSocket(url);
        ws.onopen = function (evn) {
            var obj = {Method:"Login",Company:"${loginUser.companyId}",Account:"${loginUser.account}",
                Role:"${loginUser.roleName}"};
            ws.send(mini.encode(obj));
        }
        ws.onmessage = function (evt) {
            if (onmessage) onmessage(evt);
        }
    }

    //var url = 'ws://120.79.170.134:5000/webSocket/loginSuccess';
    var url = 'ws://localhost:8082/dtsystem/message';
    createInstance(url, function (evt) {
        var loginMessage = mini.decode(evt.data);
        showMessage('登录信息', loginMessage.company + '的' + loginMessage.userName + '上线了!');
        showMessage('消息通知',evt.data);
    })
</script>
<script type="text/javascript">
    var companys = ${Companys};
    var currentClient = "${Account}";

    var layer = null;
    layui.use(['layer'], function () {
        layer = layui.layer;
    });
    var childMenus = [];
    <#if childMenus??>
    childMenus = ${childMenus};
    </#if>
    var menuHash = {};
    for (var i = 0; i < childMenus.length; i++) {
        var menu = childMenus[i];
        var pId = +menu['pid'];
        var css = menuHash[pId] || [];
        css.push(menu);
        menuHash[pId] = css;
    }

    function closeLoading() {
        mini.unmask(document.body);
    }

    function addParent(url, title) {
        if (url && title) {
            addChildTab(title, url);
        }
    }

    $(function () {
        if (companys.length < 2) {
            $('#cmdChangeCompany').remove();
        }
    })
</script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/popper.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="/js/custom.js"></script>
<script type="text/javascript">
    mini.parse();
    var tab1 = mini.get('tab1');
    var layout1 = mini.get('layout1');
    $(function () {
        $(".nava").click(function () {
            var obj = $(this);
            var mainTitle = $.trim(obj.text());
            var menuId = obj.attr('menuId');
            $('.sidebar-menu').empty();
            $("#west .mini-layout-region-header .mini-layout-region-title").html(mainTitle);
            var fss = menuHash[menuId] || [];
            var text = $(this).text();
            layout1.expandRegion("west");
            layout1.showRegion('west');
            for (var i = 0; i < fss.length; i++) {
                var F = fss[i];
                var Title = F["title"];
                var Url = F["url"] || "";
                var MenuID = F["fid"];
                if (Url && Url.indexOf("?") == -1) {
                    Url += "?MenuID=" + MenuID;
                } else {
                    Url += "&MenuID=" + MenuID;
                }
                var obj = '<li  url="' + Url + '" style="cursor:pointer" class="menudetail"> ' +
                    '<a href="javascript:;"  style="font-color:black">' + Title + '</a>' +
                    '<span class="right_jt"><img src="/appImages/rgjt.png"</span></li>';
                $('.sidebar-menu').append(obj);

            }
            $('.menudetail').on('click', menuDetailClick);
            $('.content_wrapper').css({'margin-left': '270px'});
        });
        $('.menudetail').on('click', menuDetailClick);
    });

    function menuDetailClick() {
        var url = $(this).attr('url');
        var title = $(this).text();
        addChildTab(title, url);
    }

    function changeCompany() {
        var xxxx = $('#111111').html();
        xxxx = xxxx.replace('xxxx', 'mini');
        mini.showMessageBox({
            width: 300,
            height: 200,
            title: "切换公司用户",
            buttons: ["ok", "cancel"],
            message: "登录为另一个公司用户",
            html: xxxx,
            showModal: true,
            callback: function (action) {
                if (action == 'ok') {
                    var con = mini.get('#radioClient');
                    var curClient = con.getValue();
                    if (curClient != currentClient) {
                        $.post('/relogin', {userName: curClient}, function (result) {
                            if (result.success) {
                                window.location.href = '/index';
                            } else {
                                window.location.href = '/login';
                            }
                        })
                    }
                }
            }
        });
        mini.parse();
        return false;
    }

    var tabHash = {};
    var titleHash = {};

    function addChildTab(title, url) {
        var pageName = getPageName(url);
        if (!pageName) pageName = title;
        var childTab = tabHash[pageName];
        if (!childTab) {
            childTab = {
                url: url,
                title: title,
                showCloseButton: true,
                onload: function () {
                    closeLoading();
                },
                bodyStyle: "height:99%;padding-top:0px"
            };
            childTab = tab1.addTab(childTab);
            tabHash[pageName] = childTab;
            titleHash[title] = pageName;
        } else {
            closeLoading();
            var bodyEl = tab1.getTabIFrameEl(childTab);
            if (bodyEl) {
                var content = bodyEl.contentWindow;
                if (content) {
                    var fun = content.refreshData;
                    if (fun) {
                        try {
                            fun();
                        } catch (e) {

                        }
                    }
                }
            }
        }
        tab1.activeTab(childTab);
    }

    function doResize() {
        if (myBrowser() == "Chrome") {
            $('#tab1').css({"padding-top": "10px"});
            setTimeout(function () {
                $('#tab1').css({"padding-top": "10px"});
            }, 500);
            setTimeout(function () {
                $('#tab1').css({"padding-top": "10px"});
            }, 500);
        }
        tab1.doLayout();
    }

    function getPageName(url) {
        var us = url.split('?');
        var vs = us[1].split('&');
        for (var i = 0; i < vs.length; i++) {
            var v = vs[i];
            if (v.toString().indexOf("MenuID") > -1) {
                return v.split('=')[1];
            }
        }
        return "";
    }

    function onDestroy(e) {
        var tab = e.tab;
        var title = tab.title;
        var pageName = titleHash[title];
        if (pageName) {
            var findTab = tabHash[pageName];
            if (findTab) {
                delete tabHash[pageName];
                delete titleHash[title];
            }
        }
    }

    function showImages(result) {
        debugger;
        var gg = JSON.parse(result);
        layer.photos({
            photos: gg,
            area: '600px',
            shade: [1, '#000'],
            anim: 5,
            offset: 'auto',
            imageChange: function () {
                changeImage(0);
            },
            success: function () {
                changeImage(0);
            }
        });
    }

    function changeImage(delta) {
        var imagep = $(".layui-layer-phimg").parent().parent();
        var image = $(".layui-layer-phimg").parent();
        var h = image.height();
        var w = image.width();
        if (delta > 0) {
            if (h < (window.innerHeight + 500)) {
                h = h * 1.1;
                w = w * 1.1;
            }
        } else if (delta < 0) {
            if (h > 100) {
                h = h * 0.95;
                w = w * 0.95;
            }
        }
        else if(delta==0){
            if(h<window.innerHeight){
                h = h * 0.95;
                w = w * 0.95;
            }
        }
        imagep.css('margin-top', 0);
        imagep.css("top", (window.innerHeight - h) / 2);
        imagep.css("top", (window.innerHeight - h) / 2);
        image.height(h);
        image.width(w);
        imagep.height(h);
        imagep.width(w);
    }

    $(document).on("mousewheel DOMMouseScroll", ".layui-layer-phimg img", function (e) {
        var delta = (e.originalEvent.wheelDelta && (e.originalEvent.wheelDelta > 0 ? 1 : -1)) || // chrome & ie
            (e.originalEvent.detail && (e.originalEvent.detail > 0 ? -1 : 1)); // firefox
        changeImage(delta);
    });

    function exitSystem() {
        layer.confirm('您即将退出本系统，所有未保存的数据将会丢失，是否继续进行退出操作？', {icon: 3, title: '退出系统'}, function (act) {
            $.post('/loginOut', {}, function (result) {
                window.location = '/login';
            });
        });
    }

    var changeForm = null;
    var win1 = mini.get('#changePasswordWindow');

    function changePassword() {
        win1.show();
        changeForm = new mini.Form('#changeForm');
        changeForm.reset();
    }

    function confirmChange() {
        changeForm.validate();
        if (changeForm.isValid()) {
            var data = changeForm.getData();
            if (data.newPassword != data.confirmPassword) {
                mini.alert('新的密码两次输入不一致!');
                return;
            }
            var url = '/changePassword';
            $.post(url, {data: mini.encode(data)}, function (result) {
                if (result.success) {
                    mini.alert('密码修改成功，下次登录必须使用新的密码!');
                    win1.hide();
                } else {
                    mini.alert(result.message || "修改密码失败，请稍候重试!");
                    return;
                }
            });
        } else {
            mini.alert('数据录入不完整。');
        }
    }

    $('.navv a').click(
        function () {
            $(this).addClass('active').siblings().removeClass('active')
        }
    )
    $('.pagetab  a').click(
        function () {
            $(this).addClass('active').siblings().removeClass('active')
        }
    )
    $('.menu').click(function () {

        $('.showmenu').hide();
        $('.bottomL').addClass('bottomL2')
        $('.pagetab').addClass('pagetab2')
        $('.bottommain').addClass('bottomain2')
        $('.hidemenu').show()
    })
    $('.hidemenu').click(function () {
        $(this).hide()
        $('.showmenu').show();
        $('.bottomL').removeClass('bottomL2')
        $('.pagetab').removeClass('pagetab2')
        $('.bottommain').removeClass('bottomain2')
    })
    $('.navmenu').click(function () {
        $('.hidemenu').hide()
        $('.showmenu').show();
        $('.bottomL').removeClass('bottomL2')
        $('.pagetab').removeClass('pagetab2')
        $('.bottommain').removeClass('bottomain2')
    })

    function myBrowser() {
        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
        var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
        var isIE = userAgent.indexOf("compatible") > -1
            && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
        var isEdge = userAgent.indexOf("Edge") > -1; //判断是否IE的Edge浏览器
        var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器
        var isSafari = userAgent.indexOf("Safari") > -1
            && userAgent.indexOf("Chrome") == -1; //判断是否Safari浏览器
        var isChrome = userAgent.indexOf("Chrome") > -1
            && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器

        if (isIE) {
            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            if (fIEVersion == 7) {
                return "IE7";
            } else if (fIEVersion == 8) {
                return "IE8";
            } else if (fIEVersion == 9) {
                return "IE9";
            } else if (fIEVersion == 10) {
                return "IE10";
            } else if (fIEVersion == 11) {
                return "IE11";
            } else {
                return "0";
            }//IE版本过低
            return "IE";
        }
        if (isOpera) {
            return "Opera";
        }
        if (isEdge) {
            return "Edge";
        }
        if (isFF) {
            return "FF";
        }
        if (isSafari) {
            return "Safari";
        }
        if (isChrome) {
            return "Chrome";
        }
    }
</script>
</body>
</html>