<#include "/shared/dialog.ftl">

<@layout>
    <script type="text/javascript" xmlns="">
        var states = [
            {id: 51, text: '已确定代理师'},
            {id: 52, text: '待内审'},
            {id: 53, text: '内审驳回'},
            {id: 54, text: '内审通过'},
            {id: 55, text: '定稿驳回'},
            {id: 56, text: '客户定稿'},
            {id: 61, text: '未申报'},
            {id: 62, text: '已申报'}
        ];
        var types = [
            {id: "Tech", text: '技术人员'},
            {id: "Bus", text: '商务人员'}
        ]

    </script>
    <div class="mini-fit">
<#--        <div type="comboboxcolumn" field="Type" width="120" headerAlign="center" align="center" style="text-align: left;display: inline;float: left">人员分类-->
<#--            <input property="editor" class="mini-combobox" data="types" onvaluechanged="getState()" id="type"/>-->
<#--        </div>-->
<#--        <div type="comboboxcolumn" field="State" width="120" headerAlign="center" align="center" style="text-align: left;">业务状态-->
<#--            <input property="editor" class="mini-combobox" data="states" onvaluechanged="getState()" id="state"/>-->
<#--        </div>-->
        <div>
            <table id="DateForm">
                <tr>
                    <td style="width:80px;text-align: center;">人员分类</td>
                    <td><input property="editor" class="mini-combobox" data="types" onvaluechanged="getState()" id="type"/></td>
                    <td style="width:80px;text-align: center;">业务状态</td>
                    <td><input property="editor" class="mini-combobox" data="states" onvaluechanged="getState()" id="state"/></td>
                    <td style="width:80px;text-align: center;">交单时间</td>
                    <td><input class="mini-datepicker" name="SignTime" id="Begin" style="width:100%" data-oper="GE"
                               dateFormat="yyyy-MM-dd" onvaluechanged="getState()"/></td>
                    <td style="width:80px;text-align: center;">到</td>
                    <td><input class="mini-datepicker" name="SignTime" id="End" style="width:100%" data-oper="LE"
                               dateFormat="yyyy-MM-dd" onvaluechanged="getState()"/></td>
                </tr>
            </table>
        </div>
        <div id="tech" style="width: 4000px;height:90%;overflow: auto;overflow-y: hidden"></div>
        <#--            <div id="bus" style="width: 900px;height:250px;"></div>-->
        <#--            <div id="client" style="width: 900px;height:250px;"></div>-->
    </div>
</@layout>
<@js>
    <script type="text/javascript" src="/js/ColumnChart/lib/req/require.js" data-main="/js/ColumnChart/init"></script>
    <script type="text/javascript">
        mini.parse();
        var TechName = ${TechName};
        var TechNameOfNum = ${TechNameOfNum};
        var TechColumn = ${TechColumn};
        var TechColor = ${TechColor};
        var Total = "${Totals}";
        var Max = "${Maxs}";
        var State = ${State};
        var Type = "${Type}";
        var BeginTime = "${BeginTime}";
        var EndTime = "${EndTime}";
        var TechColumnLength = ${TechColumnLength};

        <#--var BusName = ${BusName};-->
        <#--var BusNameOfNum = ${BusNameOfNum};-->
        <#--var BusColumn = ${BusColumn};-->
        <#--var BusColor = ${BusColor};-->

        <#--var ClientName = ${ClientName};-->
        <#--var ClientNameOfNum = ${ClientNameOfNum};-->
        <#--var ClientColumn = ${ClientColumn};-->
        <#--var ClientColor = ${ClientColor};-->

        $(function () {
            mini.get('state').setValue(State);
            mini.get("type").setValue(Type);

            if (BeginTime != "") {
                mini.get('#Begin').setValue(BeginTime);
            }
            if (EndTime != "") {
                mini.get('#End').setValue(EndTime);
            }

            if (TechColumnLength > 4) {
                $("#tech").css('width', '4000px');
            }else $("#tech").css('width', '100%');
        })

        function getState() {
            var States = mini.get('state').getValue();
            var Types = mini.get('type').getValue();
            // var techurl = "/ColumnChart/getTechData";
            // var busurl = "/ColumnChart/getBusData";
            // var clienturl = "/ColumnChart/getClientData";
            //
            // $.post(techurl, {Type: "Tech", State: val}, function (text){
            //     var res = mini.decode(text);
            //     if (res.success) {
            //         debugger;
            //         var data = res.data || {};
            //         if (data) {
            //             for (var i=0;i<data.length;i++) {
            //                 TechName = data[i].TechName;
            //                 TechNameOfNum = data[i].TechNameOfNum;
            //                 TechColumn = data[i].TechColumn;
            //                 TechColor = data[i].TechColor;
            //             }
            //             // require(['config'],indexFn());
            //             window.location.href = "/ColumnChart/index?State=" + val;
            //         }
            //     }
            // })
            var Dates = getSingleTime();
            var BeginTime = mini.get('#Begin').getValue();
            var EndTime = mini.get('#End').getValue();
            var url = "/ColumnChart/index?State=" + States + "&Type=" + Types + "&Dates=" + encodeURIComponent(Dates) + "&BeginTime=" + BeginTime + "&EndTime=" + EndTime;
            window.location.replace(url);
        }

        function getSingleTime() {
            var form = new mini.Form('#DateForm');
            var fields = form.getFields();
            var result = [];
            for (var i=0;i<fields.length;i++) {
                var field = fields[i];
                var val = field.getValue();
                if (field.getName() != '') {
                    if (val != null && val != undefined) {
                        if (val != '') {
                            var obj = {
                                field: field.getName(),
                                value: field.getValue(),
                                oper: field.attributes["data-oper"]
                            };
                            result.push(obj);
                        }
                    }
                }
            }
            return mini.encode(result);
        }
    </script>
</@js>
