<style>
    .Zlyw *{display:inline-block;vertical-align:middle;}
    .Zlyw{padding-top: -30px;}
    .Zlyw img{width: 20px;}
    .Zlyw h5{color: #ffffff;font-size: 15px}
    .Zlywtop {display: inline-block;width:1000px;float: left;margin-top: -55px;margin-left: 150px;height: 32px;}
    .Zlywtop ul{margin-top: -12px;margin-right:20px }
    .Zlywtop ul li{float: left;margin-left: 20px;height: 45px;padding-top: 11px;width: 110px;border-radius: 5px;margin-top: 9.5px;}
    .Zlywtop ul li:hover{background-color: rgb(74,106,157);}
    .Zlywtop ul li a{color: white;}
    .Zlywtop ul li a span{font-size: 15px;}
    .Zlywtop ul li a h4{display: inline}


    .info1bottom ul{margin-top: 10px;}
    .info1bottom ul li{float: left;margin-left: 8.5%;height: 35px;margin-top: -6px;width: 80px;padding-top: 6px;border-radius: 5px;}
    @media screen and (max-width:1593px){  .info1bottom ul li {margin-left:7%;}  }
    @media screen and (max-width:1340px){  .info1bottom ul li {margin-left:5.5%;}  }
    @media screen and (max-width:1235px){  .info1bottom ul li {margin-left:4.5%;}  }
    .info1bottom ul li:hover{background-color: rgb(203,238,242)}
    .info1bottom ul li a{margin-top: 5px;}
    .info1bottom ul li a span{color: rgb(0,159,205);font-size: 15px}
    .info1bottom ul li a h4{display: inline;color: rgb(1,160,202)}

    .info2bottom{margin-top: 10px;}
    .info2bottom ul li{float: left;margin-left: 17%;width: 90px;height: 35px;margin-top: -6px;padding-top: 6px;border-radius: 5px;}
    @media screen and (max-width:1593px){  .info2bottom ul li {margin-left:11%;}  }
    @media screen and (max-width:1480px){  .info2bottom ul li {margin-left:4%;}  }
    @media screen and (max-width:1184px){  .info2bottom ul li {margin-left:3%;}  }

    .info2bottom ul li:hover{background-color: rgb(216,228,250)}
    .info2bottom ul li a span{color: rgb(53,102,231);font-size: 15px}
    .info2bottom ul li a h4{display: inline;color: rgb(51,97,232)}

    .info3bottom{margin-top: 10px;}
    .info3bottom ul li{float: left;margin-left:5.9%;width: 105px;height: 35px;margin-top: -6px;padding-top: 6px;border-radius: 5px;}
    @media screen and (min-width: 1480px) and (max-width:1593px){  .info3bottom ul li {margin-left:5%;}  }
    @media screen and (max-width:1480px){  .info3bottom ul li {margin-left:3.5%;}  }
    @media screen and (max-width:1374px){  .info3bottom ul li {margin-left:2%;}  }
    @media screen and (max-width:1233px){  .info3bottom ul li {margin-left:1.2%;}  }
    .info3bottom ul li:hover{background-color: rgb(214,212,251)}
    .info3bottom ul li a span{color: rgb(53,102,231);font-size: 15px}
    .info3bottom ul li a h4{display: inline;color: rgb(52,101,232)}



    @media screen and (max-width:1170px){  .info1bottom ul li {margin-left:2%;} .info2bottom ul li {margin-left:1%;} .info3bottom ul li {margin-left:0.5%;} }
</style>

<div class="container">
    <div class="mini-clearfix ">
        <div class="mini-col-6" style="">
            <div  id="info0" style="height:80px;background-color: rgb(63,87,131);overflow: hidden;border-radius:3px;
            -moz-box-shadow: 2px 2px 10px rgb(63,87,131);-webkit-box-shadow: 2px 2px 10px rgb(63,87,131);-shadow:2px 2px 10px rgb(63,87,131);">
                <div class="file-list" >
                    <div class="Zlyw" style="margin-left: 18px;margin-top: 5px;">
                        <img src="/appImages/zongheicon.png"  alt="业务汇总">
                        <h5>业务汇总</h5>
                    </div>

                    <div class="Zlywtop">
                        <ul >
                            <li class="ZLywli" id="Z1" onclick="doSomething(9,this)"
                                style="background-color: rgba(247, 140, 24, 0.85);">
                                <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                    <span>全部</span>&nbsp;
                                    <h4 class="x9">${States["9"]}</h4>
                                </a>
                            </li>
                            <li class="ZLywli" id="Z2" onclick="doSomething(1,this)">
                                <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                    <span>待提交</span>&nbsp;
                                    <h4 class="x1">${States["1"]}</h4>
                                </a>
                            </li>
                            <li class="ZLywli" id="Z3" onclick="doSomething(2,this)">
                                <a style="text-decoration:none" target="_self" href="javascript:void(0)" >
                                    <span>待审批</span>&nbsp;
                                    <h4 class="x2">${States["2"]}</h4>
                                </a>
                            </li>
                            <li class="ZLywli" id="Z4" onclick="doSomething(3,this)">
                                <a style="text-decoration:none" target="_self" href="javascript:void(0)">
                                    <span>已驳回</span>&nbsp;
                                    <h4 class="x3">${States["3"]}</h4>
                                </a>
                            </li>
                            <li class="ZLywli" id="Z5" onclick="doSomething(4,this)">
                                <a style="text-decoration:none" target="_self" href="javascript:void(0)" >
                                    <span>已审批</span>&nbsp;
                                    <h4 class="x4">${States["4"]}</h4>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="mini-col-6" style="">
            <div id="info2"
                 style="height:80px;background:rgb(230,238,251);border:1px solid rgb(211,219,238);border-radius: 5px;">
                <div class="info2top"
                     style="width:100%;height: 30px;border-bottom: 1px solid rgb(209,220,240);text-align: center;">
                    <h2 style="color: rgb(53,97,228);margin-left: 5px;margin-top: 5px;">申报情况</h2>
                </div>
                <div class="info2bottom" style="width: 100%;height: 55px">
                    <ul>
                        <li class="Jsjdli" id="jsh1" onclick="doSomething(7);" href="javascript:void(0)">
                            <a style="text-decoration:none" target="_self" onclick="doSomething(7);" href="javascript:void(0)">
                                <span id="Js1span">全部</span>
                                <h4 id="Js1h4" class="x7">${States["7"]?number}</h4>
                            </a>
                        </li>
                        <li class="Jsjdli" id="jsh2" onclick="doSomething(5);">
                            <a style="text-decoration:none" target="_self" href="javascript:void(0)" class="active_a"
                               onclick="doSomething(5);">
                                <span id="Js2span">未申报</span>
                                <h4 id="Js2h4" class="x5">${States["5"]?number}</h4>
                            </a>
                        </li>
                        <li class="Jsjdli" id="jsh3" onclick="doSomething(6);">
                            <a style="text-decoration:none" target="_self" href="javascript:void(0)" class="active_a"
                               onclick="doSomething(6);">
                                <span id="Js3span">已申报</span>
                                <h4 id="Js3h4" class="x6">${States["6"]?number}</h4>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>



