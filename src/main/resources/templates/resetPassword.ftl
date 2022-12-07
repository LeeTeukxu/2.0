<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <link href="/js/layui/css/login.css" rel="stylesheet" media="all"/>


    <script src="/js/jquery.min.js?v=2.1.4"></script>
    <script src="/js/layui/layui.js" type="text/javascript"></script>
    <script src="/js/jparticle.min.js" type="text/javascript"></script>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <style type="text/css">
        .layui-elem-quote {
            background-color: inherit !important;
        }

        .layui-input, .layui-select, .layui-textarea {
            background-color: inherit;
            padding-left: 30px;
        }
    </style>
</head>
<body style="overflow: hidden">
<DIV class="layui-fluid">
    <DIV class="layui-row layui-col-space15">
        <DIV class="layui-col-sm12 layui-col-md12 zyl_mar_01">
            <br/>
            <BLOCKQUOTE style="font-family:黑体;font-size:30px">知服帮知识产权管理系统</BLOCKQUOTE>
            <br/>
        </DIV>
    </DIV>
</DIV>
<div class="layui-row">
    <div class="layui-col-sm12 layui-col-md12">
        <div class="zyl_login_cont"></div>
    </div>
</DIV>
<div class="layui-row">
    <div class="layui-col-sm12 layui-col-md12 zyl_center zyl_mar_01">
        <b>技术支持：湖南知名未来科技有限公司</b> &nbsp;&nbsp;&nbsp;电话:0731-88393139
    </div>
</div>
<div class="rzyl_lofo_main">
    <fieldset class="layui-elem-field layui-field-title zyl_mar_02">
        <legend>欢迎使用-知服帮知识产权管理系统</legend>
    </fieldset>
    <br/>
    <div class="layui-row layui-col-space15">
        <div class="layui-form zyl_pad_01">
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input type="hidden" name="Key" id="Key" value="${Key}" />
                    <input type="hidden" name="ClientID" id="ClientID" value="${ClientID}"/>
                    <input name="username" id="username" class="layui-input" type="text" placeholder="账号"
                           value="${OrgCode}" autocomplete="off" disabled="disabled"/>
                    <i class="layui-icon layui-icon-username zyl_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input name="password" id="password" class="layui-input" type="password" placeholder="新密码"
                           autocomplete="off"/>
                    <i class="layui-icon layui-icon-password zyl_lofo_icon"></i>
                </div>
            </div>
            <div class="layui-col-sm12 layui-col-md12">
                <div class="layui-form-item">
                    <input name="password" id="newPassword" class="layui-input" type="password" placeholder="重复密码" autocomplete="off"/>
                    <i class="layui-icon layui-icon-password zyl_lofo_icon"></i>
                </div>
            </div>
            <br/>
            <br/>
            <div class="layui-col-sm12 layui-col-md12"><br/><br/>
                <button class="layui-btn layui-btn-fluid" id="cmdSubmit">重置密码才能登录</button>
            </div>
<#--            <div class="layui-col-sm12 layui-col-md12"><br/><br/>-->
<#--                <button class="layui-btn layui-btn-fluid" id="cmdLogin">立即登录</button>-->
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    if (window != top) {
        top.location.href = window.href;
    }
    var error="${Error}";
    if(error){
        layui.use('layer', function () {
            var layer=layui.layer;
            layer.alert(error,function(){
                window.location.href="/login";
            });
        });
    }
    (function ($, window, document, undefined) {
        if (!window.browser) {
            var userAgent = navigator.userAgent.toLowerCase(), uaMatch;
            window.browser = {}

            /**
             * 判断是否为ie
             */
            function isIE() {
                return ("ActiveXObject" in window);
            }

            /**
             * 判断是否为谷歌浏览器
             */
            if (!uaMatch) {
                uaMatch = userAgent.match(/chrome\/([\d.]+)/);
                if (uaMatch != null) {
                    window.browser['name'] = 'chrome';
                    window.browser['version'] = uaMatch[1];
                }
            }
            /**
             * 判断是否为火狐浏览器
             */
            if (!uaMatch) {
                uaMatch = userAgent.match(/firefox\/([\d.]+)/);
                if (uaMatch != null) {
                    window.browser['name'] = 'firefox';
                    window.browser['version'] = uaMatch[1];
                }
            }
            /**
             * 判断是否为opera浏览器
             */
            if (!uaMatch) {
                uaMatch = userAgent.match(/opera.([\d.]+)/);
                if (uaMatch != null) {
                    window.browser['name'] = 'opera';
                    window.browser['version'] = uaMatch[1];
                }
            }
            /**
             * 判断是否为Safari浏览器
             */
            if (!uaMatch) {
                uaMatch = userAgent.match(/safari\/([\d.]+)/);
                if (uaMatch != null) {
                    window.browser['name'] = 'safari';
                    window.browser['version'] = uaMatch[1];
                }
            }
            /**
             * 最后判断是否为IE
             */
            if (!uaMatch) {
                if (userAgent.match(/msie ([\d.]+)/) != null) {
                    uaMatch = userAgent.match(/msie ([\d.]+)/);
                    window.browser['name'] = 'ie';
                    window.browser['version'] = uaMatch[1];
                } else {
                    /**
                     * IE10
                     */
                    if (isIE() && !!document.attachEvent && (function () {
                        "use strict";
                        return !this;
                    }())) {
                        window.browser['name'] = 'ie';
                        window.browser['version'] = '10';
                    }
                    /**
                     * IE11
                     */
                    if (isIE() && !document.attachEvent) {
                        window.browser['name'] = 'ie';
                        window.browser['version'] = '11';
                    }
                }
            }

            /**
             * 注册判断方法
             */
            if (!$.isIE) {
                $.extend({
                    isIE: function () {
                        return (window.browser.name == 'ie');
                    }
                });
            }
            if (!$.isChrome) {
                $.extend({
                    isChrome: function () {
                        return (window.browser.name == 'chrome');
                    }
                });
            }
            if (!$.isFirefox) {
                $.extend({
                    isFirefox: function () {
                        return (window.browser.name == 'firefox');
                    }
                });
            }
            if (!$.isOpera) {
                $.extend({
                    isOpera: function () {
                        return (window.browser.name == 'opera');
                    }
                });
            }
            if (!$.isSafari) {
                $.extend({
                    isSafari: function () {
                        return (window.browser.name == 'safari');
                    }
                });
            }
        }
    })(jQuery, window, document);
    $(function () {
        if ($.isIE()) {
            $('.zyl_login_cont').height(700);
        } else {
            $('.zyl_login_cont').height(600);
        }
        $(".zyl_login_cont").jParticle({
            background: "rgba(0,0,0,0)",//背景颜色
            color: "#fff",//粒子和连线的颜色
            particlesNumber: 120,//粒子数量
            particle: {
                minSize: 1,//最小粒子
                maxSize: 3,//最大粒子
                speed: 30//粒子的动画速度
            }
        });
        $('#cmdSubmit').on('click', function (e) {
            event.stopPropagation();
            event.stopImmediatePropagation();
            e.preventDefault();
            doLogin();

            return false;
        });
        $('#cmdSubmit').on('keydown', function (event) {
            if (event.keyCode == 13) {
                doLogin();
                event.stopPropagation();
                event.stopImmediatePropagation();
                event.preventDefault();
                return false;
            }
        });
        $('#cmdLogin').on('click', function (event) {
            window.location.href = '/login';
        });
        $('#password').on('keydown', function (event) {
            if (event.keyCode == 13) {
                doLogin();
                event.stopPropagation();
                event.stopImmediatePropagation();
                event.preventDefault();
                return false;
            }
        })

        function doLogin() {
            layui.use('layer', function () {
                var layer = layui.layer;
                var key=$('#Key').val();
                if(!key){
                    layer.alert('访问页面非法，请重新刷新!');
                    return ;
                }
                var username = $('#username').val();
                if (!username) {
                    layer.alert('登录用户帐号不能为空!');
                    return;
                }
                var clientId = $('#ClientID').val();

                var password = $('#password').val();
                if (!password) {
                    layer.alert('登录用户密码不能为空!');
                    return;
                }
                var newPassword = $('#newPassword').val();
                if (!newPassword) {
                    layer.alert('请再次确认新密码！');
                    return;
                }
                if(password!=newPassword){
                    layer.alert('修改的密码与确认的密码不一致，请检查后再进行重置操作!');
                    return ;
                }
                if(password.length<6 || newPassword.length<6){
                    layer.alert('设置的密码长度不能小于6位。');
                    return ;
                }
                var arg={Key:key,ClientID:clientId,OrgCode:username,password: password};
                $.post('/resetPassword/apply',arg ,function(rs) {
                    if (rs.success) {
                        window.location = '/index';
                    } else {
                        var msg = rs.message || "登录失败，请稍候重试。";
                        layer.alert(msg);
                    }
                    return false;
                })
            });
            event.preventDefault();
            event.stopPropagation();
            event.stopImmediatePropagation();

            return false;
        }
    });
</script>
</body>
</html>