<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=0.5, maximum-scale=1">
    <title>知服帮知识产权管理系统登陆｜专利商标管理软件</title>
    <meta name="keywords" content="知识产权管理系统 , 专利管理软件, 商标管理软件" />
    <meta name="description" content="知服帮知识产权管理系统，专利商标客户拓展、专利商标流程管理、专利商标文件通知费用监控提醒、财务数据汇总统计。"/>
    <link rel="stylesheet" href="/js/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/js/layui/css/login.css" media="all"/>
    <!-- Jquery Js -->
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <!-- Layui Js -->
    <script type="text/javascript" src="/js/layui/layui.js"></script>
    <style>
        /* 覆盖原框架样式 */
        .layui-elem-quote{background-color: inherit!important;}
        .layui-input, .layui-select, .layui-textarea{background-color: inherit; padding-left: 30px;}
        .Backpage{float: right;margin-top:-60px; }
        .Backpage{text-decoration:underline;}
        .layui-fluid:hover{cursor:pointer;}
        #dl .loginhead{width: 70%;text-align: center}
        #dl .loginhead span{font-size: 20px;height: 25px;color: #fff;}
        .loginlogo{float: left;width:270px; height:70px;position: relative;color: #fff;margin-left: 5px }
        .loginlogo img{display: block;width:230px; height:58px;padding-top:13px; }
        .loginlogo h3{font-size:18px;font-weight:normal;position:absolute;left: 70px;top:20px;  }
        .loginlogo p{font-size: 16px;position: absolute;left: 70px;top: 48px;}

        .layui-layer{index:19891044;top:-400px;left:781.5px;border: 1px solid red}
        .Copyrightspan{font-size: 13px;color: #fff;}
    </style>
</head>
<body style="overflow: hidden;background:#2b88f2;height:100%">
<!-- Carousel -->
<div class="layui-row">
    <div class="loginlogo">
        <img src="/res/images/lgointest.png" >
        <h3>知服帮知识产权管理系统</h3>
        <p>zfbip.com</p>
    </div>


    <div class="layui-col-sm12 layui-col-md12">
        <div class="layui-carousel zyl_login_height"   id="zyllogin" lay-filter="zyllogin">
            <div carousel-item="">
                <div>
                    <div class="zyl_login_cont" ></div>
                </div>
            </div>
        </div>
    </div>

    <!-- LoginForm -->
    <div id="dl"  class="zyl_lofo_main" >
        <div class="loginhead">
            <span>用户登录中心</span>
        </div>
        <br><br>
        <div class="layui-row layui-col-space15">
            <div class="layui-form zyl_pad_01">
                <div class="layui-col-sm4 layui-col-md4">
                    <div class="layui-form-item">
                        <input type="text" id="username" name="username" lay-verify="required|userName" autocomplete="off"
                               placeholder="账号" class="layui-input" style="height: 34px;border: none;background-color:#fff;">
                        <i class="layui-icon layui-icon-username zyl_lofo_icon" style="margin-top:-1px;"></i>
                    </div>
                </div>

                <div class="layui-col-sm4 layui-col-md4" style="margin-left: 10px">
                    <div class="layui-form-item">
                        <input type="password" id="password" name="password"  lay-verify="required|pass" autocomplete="off"
                               placeholder="密码"  class="layui-input" style="height: 34px;border: none;background-color:#fff;">
                        <i class="layui-icon layui-icon-password zyl_lofo_icon" style="margin-top:-1px;"></i>
                    </div>
                </div>

                <div class="layui-col-sm2 layui-col-md2" style="margin-left: 10px">
                    <button class="layui-btn layui-btn-fluid" lay-submit="" id="demo1"  lay-filter="demo1"
                            onkeydown="">立即登录</button>
                </div>

                <div class="layui-col-sm12 layui-col-md12">
                    <div class="layui-row">

                        <div class="layui-col-xs2 layui-col-sm2 layui-col-md2 yzmtext" >
                            <div class="layui-form-item">
                                <input type="text" name="vercode" id="vercode" lay-verify="vercodes" autocomplete="off"
                                       placeholder="验证码" class="layui-input" maxlength="4" style="background-color: #fff;height:34px;">
                                <i class="layui-icon layui-icon-vercode zyl_lofo_icon"></i>
                            </div>
                        </div>
                        <div class="layui-col-xs2 layui-col-sm2 layui-col-md2 yzmtp" style="margin-left: 10px;">
                            <div class="zyl_lofo_vercode zylVerCode" onclick="zylVerCode()"></div>
                        </div>

                        <div class="layui-col-sm6 layui-col-md6" style="margin-left:68px;margin-top:17px;">
                    <span class="Copyrightspan">
                        <a href="https://www.kancloud.cn/rockyecool/zfbip/1731132" target="_blank" style="color: #fff;">系统帮助文档</a>&nbsp;|
                            Copyright © 湖南知名未来科技有限公司
                    </span>
                        </div>
                    </div>
                </div>



            </div>
        </div>
    </div>
    <!-- LoginForm End -->

</div>
<!-- Carousel End -->


<!-- Jqarticle Js -->
<script type="text/javascript" src="/js/jparticle.min.js"></script>
<!-- ZylVerificationCode Js-->
<script type="text/javascript" src="/js/zylVerificationCode.js"></script>
<script>
    if(window!=top){
        top.location.href=window.href;
    }


    function tiaoz(){
        location.href="index.html";
    }
    var layer= null;
    layui.use(['carousel', 'form'], function(){
        var carousel = layui.carousel,form = layui.form;
        layer=layui.layer;
        //自定义验证规则
        form.verify({
            userName: function(value){
                if(value.length <4){
                    return '登录帐号长度必须在4至30位之间!';
                }
                if(value.length>30){
                    return '登录帐号长度必须在4至30位之间!';
                }
                if(value.indexOf(' ')>-1){
                    return "登录帐号不能带空格！";
                }
            }
            ,pass: [/^[\S]{3,15}$/,'密码必须3到15位,并且不能出现空格！']
            ,vercodes: function(value){
                var zylVerCode = $(".zylVerCode").html();
                if(!value){
                    return '请填写验证码!';
                } else{
                    if(value.toString().toLowerCase()!=zylVerCode.toString().toLowerCase()){
                        return '验证码错误!';
                    }
                }
            }
        });

        //监听提交
        form.on('submit(demo1)', function(data){
            var layer = layui.layer;
            var index= layer.msg('正在向服务器请求验证，请稍候.......', {
                icon: 16
                ,shade: 0.01
                ,offset:'50%'
            });
            $.post('/doLogin',data.field,function(rs){
                layer.close(index);
                if(rs.success){
                    window.location.href='/index';
                }
                else {
                    var msg=rs.message || "登录失败，请稍候重试。";
                    layer.alert(msg);
                }
            })
            return false;
        });


        //设置轮播主体高度
        var zyl_login_height = $(window).height()/1.3;
        var zyl_car_height = $(".zyl_login_height").css("cssText","height:880px!important;");
        //$(".zyl_login_height").css("cssText","height:" + zyl_login_height + "px!important");
        //Login轮播主体
        carousel.render({
            elem: '#zyllogin'//指向容器选择器
            ,width: '100%' //设置容器宽度
            ,height:'zyl_car_height'
            ,arrow: 'always' //始终显示箭头
            ,anim: 'fade' //切换动画方式
            ,autoplay: true //是否自动切换false true
            ,arrow: 'hover' //切换箭头默认显示状态||不显示：none||悬停显示：hover||始终显示：always
            ,indicator: 'none' //指示器位置||外部：outside||内部：inside||不显示：none
            ,interval: '5000' //自动切换时间:单位：ms（毫秒）
        });

        //粒子线条
        $(".zyl_login_cont").jParticle({
            background: "rgba(0,0,0,0)",//背景颜色
            color: "#fff",//粒子和连线的颜色
            particlesNumber:100,//粒子数量
            disableLinks:true,//禁止粒子间连线
            disableMouse:true,//禁止粒子间连线(鼠标)
            particle: {
                minSize: 1,//最小粒子
                maxSize: 3,//最大粒子
                speed: 30,//粒子的动画速度
            }
        });
        $('#vercode').on('keydown',function(e){
            if(e.keyCode==13){
                $('#demo1').trigger('click');
                return false;
            }
        });
        $('#username').on('keydown',function(e){
            if(e.keyCode==13){
                $('#demo1').trigger('click');
                return false;
            }
        });
        $('#password').on('keydown',function(e){
            if(e.keyCode==13){
                $('#demo1').trigger('click');
                return false;
            }
        });
    });

</script>
</body>
</html>
