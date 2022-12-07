<!--高企交单-->
<style type="text/css">
    .sqf * {
        display: inline-block;
        vertical-align: middle;
    }
    /* 业务交单审批*/
    .Zlyw * {
        display: inline-block;
        vertical-align: middle;
    }

    .Zlyw {
        padding-top: -30px;
    }

    .Zlyw img {
        width: 20px;
    }

    .Zlyw h5 {
        color:rgb(53, 102, 231);
        font-size: 15px
    }

    .Zlywtop {
        display: inline-block;
        width: 100%;
        float: left;
        margin-top: -66px;
        margin-left: 100px;
        height: 32px;
    }

    .Zlywtop ul {
        margin-top: 5px;
        text-align: center;
        margin-left: -10px;
    }

    .Zlywtop ul li {
        float: left;
        margin-left: 15px;
        height: 45px;
        padding-top: 10px;
        width: 110px;
        border-radius: 5px;
        margin-top: 4px;
        list-style: none;
    }
    .Zlywtop ul li:hover {
        background-color: rgb(216, 228, 250)
    }

    .Zlywtop ul li a {
        color:rgb(53, 102, 231);
    }

    .Zlywtop ul li a span {
        font-size: 14px;
    }

    .Zlywtop ul li a h4 {
        display: inline
    }

    /*技术交单*/
    .Zlyw1 * {
        display: inline-block;
        vertical-align: middle;
    }

    .Zlyw1 {
        padding-top: -30px;
    }

    .Zlyw1 img {
        width: 20px;
    }

    .Zlyw1 h5 {
        color: rgb(52, 101, 232);
        font-size: 15px;
    }

    .Zlywtop1 {
        display: inline-block;
        width: 100%;
        float: left;
        margin-top: -66px;
        margin-left: 100px;
        height: 32px;
    }

    .Zlywtop1 ul {
        margin-top: 5px;
        text-align: center;
        margin-left: -10px;
    }

    .Zlywtop1 ul li {
        float: left;
        margin-left: 15px;
        height: 45px;
        padding-top: 10px;
        width: 110px;
        border-radius: 5px;
        margin-top: 4px;
        list-style: none;
    }

    .Zlywtop1 ul li:hover {
        background-color: rgb(214, 212, 251)
    }

    .Zlywtop1 ul li a {
        color: rgb(53, 102, 231);
    }

    .Zlywtop1 ul li a span {
        font-size: 14px;
    }

    .Zlywtop1 ul li a h4 {
        display: inline
    }



    @media screen and (max-width: 1593px) {
        .info2bottom ul li {
            margin-left: 11%;
        }
    }

    @media screen and (max-width: 1480px) {
        .info2bottom ul li {
            margin-left: 4%;
        }
    }

    @media screen and (max-width: 1184px) {
        .info2bottom ul li {
            margin-left: 3%;
        }
    }


    @media screen and (max-width: 1170px) {
        .info1bottom ul li {
            margin-left:-24px;
        }

        .info2bottom ul li {
            margin-left: 1%;
        }

        .info3bottom ul li {
            margin-left: -24px;
        }
    }


    .ho{width:55px;visibility: hidden;}
</style>
<div class="container">
        <div class="mini-clearfix ">
            <div class="mini-col-12">
                <div id="info0" style="height:55px;overflow: hidden;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                    <div class="file-list">
                        <div class="Zlyw" style="margin-left: 10px;margin-top: -10px;">
                            <h5>业务交单审批</h5>
                        </div>
                        <div class="Zlywtop">
                            <ul>
                                <li class="Jdlcli Arrival" id="J9" onclick="doSomething(9,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="J9span">全部</span>&nbsp;
                                        <h4 id="J9h4"  class="x9">0</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli Arrival" id="J1" onclick="doSomething(1,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="J1span">待提交</span>&nbsp;
                                        <h4 id="J1h4"  class="x1">0</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli Arrival" id="J2" onclick="doSomething(2,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="J2span">待审批</span>&nbsp;
                                        <h4 id="J2h4"  class="x2">0</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli Arrival" id="J3" onclick="doSomething(3,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="J3span">驳回</span>&nbsp;
                                        <h4 id="J3h4"  class="x3">0</h4>
                                    </a>
                                </li>
                                <li class="Jdlcli Arrival" id="J4" onclick="doSomething(4,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="J4span">已审批</span>&nbsp;
                                        <h4 id="J4h4"  class="x4">0</h4>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="mini-col-12">
                <div id="info1" style="height:55px;overflow: hidden;background:rgb(232,230,252);border:1px solid rgb(213,210,239);border-radius: 5px;">
                    <div class="file-list">
                        <div class="Zlyw1" style="margin-left: 10px;margin-top: -10px;">
                            <h5>技术接单</h5>
                        </div>
                        <div class="Zlywtop1">
                            <ul>
                                <li class="Jsjdli Arrival" id="Js0"  onclick="doSomething(90,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js0Span">全部</span>&nbsp;
                                        <h4 id="Js0h4" class="x90">9999</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival" id="Js1"  onclick="doSomething(50,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js1Span">技术员未定</span>&nbsp;
                                        <h4 id="Js1h4" class="x50">0</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival" id="Js2"  onclick="doSomething(51,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js2Span">技术员已定</span>&nbsp;
                                        <h4 id="Js2h4"  class="x51">0</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival 待定稿" id="Js3"  onclick="doSomething(52,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span  id="Js3Span">待内审</span>&nbsp;
                                        <h4 id="Js3h4"   class="x52">0</h4>
                                    </a>
                                </li>

                                <li class="Jsjdli Arrival 客户未定稿" id="Js5"  onclick="doSomething(53,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js5Span">内审驳回</span>&nbsp;
                                        <h4 id="Js5h4"  class="x53">0</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival" id="Js4"  onclick="doSomething(54,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js4Span">内审通过</span>&nbsp;
                                        <h4 id="Js4h4"  class="x54">0</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival" id="Js7"  onclick="doSomething(55,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js7Span">客户定稿</span>&nbsp;
                                        <h4 id="Js7h4"  class="x55">0</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival" id="Js6"  onclick="doSomething(56,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js6Span">已申报</span>&nbsp;
                                        <h4 id="Js6h4"  class="x56">0</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival" id="Js8"  onclick="doSomething(58,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js8Span">已通过</span>&nbsp;
                                        <h4 id="Js8h4"  class="x58">0</h4>
                                    </a>
                                </li>
                                <li class="Jsjdli Arrival" id="Js9"  onclick="doSomething(57,this)">
                                    <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                        <span id="Js9Span">未通过</span>&nbsp;
                                        <h4 id="Js9h4"  class="x57">0</h4>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>