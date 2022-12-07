function payCasesMoney(moneyGridId, detailGridId) {
    var cmdAdd, cmdSave, cmdRemove;
    var curMode;
    var moneyGrid;//就是领用费用的Grid
    var detailGrid;//就是交单明细的Grid
    var win, grid1, cmdConfirm, cmdClose,cmdQuery,queryText;
    var curRow;
    this.init = function (mode) {
        curMode = mode;
        defineCompont();
        bindEvent(mode);
        moneyGrid.load({CasesID:getCasesId()});
    }

    function defineCompont() {
        cmdAdd = mini.get('#addMoney');
        cmdRemove = mini.get('#removeMoney');
        moneyGrid = mini.get('#' + moneyGridId);
        detailGrid = mini.get('#' + detailGridId);
        win = mini.get('#PayBackWindow');
        grid1 = mini.get('datagrid1');
        cmdConfirm = mini.get('#cmdConfirm');
        cmdClose = mini.get('#cmdClose');
        cmdQuery=mini.get('#CmdQuery');
        queryText=mini.get('#QueryText');
    }

    function bindEvent() {
        if (mode == "Add" || mode == "Edit") {
            cmdAdd.on('click', addMoney);
            cmdRemove.on('click', removeMoney);
            detailGrid.on('load', detailLoad);
            grid1.on('rowclick', onRowClick);

            grid1.on('cellbeginedit', function (e) {
                var field = e.field;
                if (field == "FreeGuan" || field == "FreeDai") {
                    if (e.editor) e.editor.on('enter', onEnter);
                }
            });
            cmdConfirm.on('click', onConfirm);
            cmdRemove.hide();

            moneyGrid.on('rowclick',function(){
                var row =moneyGrid.getSelected();
                if(row){
                    var state=parseInt(row["State"] || 0);
                    if(state>0)cmdRemove.hide();
                    if(state==0)cmdRemove.show();
                }
            });
            moneyGrid.on('deselect',function(){
                cmdRemove.hide();
            });
            cmdQuery.on('click',doQuery);
        }
    }
    function doQuery(){
        var arg={};
        var txt=queryText.getValue() || "";
        var cs=createBaseCondition();
        var fields=['DocumentNumber','Payer','PaymentAccount','ReturnBank'];
        if(txt){
            for(var i=0;i<fields.length;i++){
                var field=fields[i];
                var obj={oper:'LIKE'};
                obj.value=txt;
                obj.field=field;
                cs.push(obj);
            }
        }
        arg["Query"]=mini.encode(cs);
        grid1.load(arg);
    }
    function createBaseCondition(){
        var cs=[];
        // var o1 = {field: 'ClaimStatus', oper: 'EQ', value:1};
        // var o2={field: 'ClaimStatus', oper: 'EQ', value:4};
        // cs.push(o1,o2);
        return cs;
    }
    function addMoney() {
        var pForm=new mini.Form('#Table1');
        pForm.validate();
        if(pForm.isValid()==false){
            mini.alert('业务交单数据不完整，操作被中止!');
            return ;
        }
        win.show();
        var arg={};
        var cs=createBaseCondition();
        arg["Query"]=mini.encode(cs);
        grid1.load(arg);

        var freeMoney=getJiaoDanMoney()-getUsedMoney();
        $('#MoneyText').text("可领用金额:"+freeMoney.toFixed(2));
    }

    function removeMoney() {
        var row=moneyGrid.getSelected();
        if(row){
            var state=parseInt(row["State"] || 0);
            if(state!=0){
                mini.alert('财务已审核的领用记录无法删除!');
                return ;
            }
            function g(){
                var id=parseInt(row["ID"] || 0);
                var casesId=row["CasesID"];
                var url='/arrivalUse/removeOne';
                $.post(url,{ID:id},function(result){
                    if(result.success){
                        mini.alert('选择的记录删除成功','系统提示',function(){
                            moneyGrid.load({CasesID:getCasesId()});
                        });
                    } else mini.alert("删除失败，请稍候重试!"||result.message);
                });
            }
            mini.confirm('确认要删除选择的记录吗？','系统提示',function(res){
                if(res=='ok'){
                   g();
                }
            });
        }
    }

    function detailLoad() {
        var rows = detailGrid.getData();
        if (rows.length == 0 || !getCasesId()) {
            cmdAdd.hide();
            cmdRemove.hide();
        } else{
            cmdAdd.show();
            cmdRemove.show();
        }
    }
    function getCasesId() {
        var con = mini.get('#casesId');
        return con.getValue();
    }

    ///实际交单金额
    function getJiaoDanMoney() {
        var con = mini.getByName('ChangeMoney');
        var money= parseFloat(con.getValue() || 0);
        if(money==0){
            var rows=detailGrid.getData();
            var total=0;
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                var dai=parseFloat(row["daiMoney"] || 0);
                var guan=parseFloat(row["guanMoney"] || 0);
                var t=dai+guan;
                total+=t;
            }
            return total;
        } else return money;
    }

    ///已领用多少金额
    function getUsedMoney() {
        var res = 0;
        var rows = moneyGrid.getData();
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            var state=parseInt(row["State"] || 0);
            if(state==1)continue;
            var total = parseFloat(row["Total"] || 0);
            res += total;
        }
        return res;
    }

    function onRowClick(e) {
        var row = e.row;
        if (row) {
            if (curRow) {
                if (curRow._id != row._id) {
                    grid1.commitEdit();
                    grid1.beginEditRow(row);
                    var column=grid1.getColumn('FreeGuan');
                    var editor=grid1.getCellEditor(column,row);
                    if(editor)
                    {
                        editor.setValue(null);
                        editor.focus();
                    }
                    var column1=grid1.getColumn('FreeDai');
                    var editor1=grid1.getCellEditor(column1,row);
                    if(editor1)editor1.setValue(null);
                    curRow = row;
                }
            } else {
                grid1.beginEditRow(row);
                curRow = row;
            }
        }
    }

    function onConfirm() {
        var row = grid1.getSelected();
        if(grid1.isEditingRow(row)){
            onEnter();
        }
        if (row != null) {
            try {
                checkSelectResult(row);
                var postRow = packagePostRow(row);
                postSelectInfo(postRow, function () {
                    win.hide();
                    moneyGrid.load({CasesID:getCasesId()});
                });
            } catch (e) {
                mini.alert(e);
                return;
            }
        } else {
            mini.alert('请选择回款记录!');
            return;
        }
    }
    function postSelectInfo(obj, succFun, failFun) {
        var url = '/caseHigh/saveSelectMoney';
        var iid = mini.loading('正在保存数据.........');
        $.post(url, {Data: mini.encode(obj)}, function (result) {
            mini.hideMessageBox(iid);
            if (result.success) {
                mini.alert('回款记录选择并保存成功,请注意：回款记录在财务审核后将无法删除', '系统提示', function () {
                    if (succFun) succFun();
                });
            } else {
                mini.alert('选择回款记录保存失败，请稍候重试!', '系统提示', function () {
                    if (failFun) failFun();
                });
            }
        });
    }

    function onClose() {
        win.close();
    }

    function onEnter() {
        var row = grid1.getSelected();
        if (row) {
            grid1.commitEdit();
            curRow = null;
            var total = getSelectMoney(row);
            grid1.updateRow(row, {FreeTotal: total.toFixed(2)});
        }
    }

    //对领用金额进行校验。
    function checkSelectResult(row) {
        var freeMoney = parseFloat(row["FreeMoney"] || 0);
        var selectMoney = getSelectMoney(row);
        if (selectMoney == 0) throw "领用的代理费与官费合计必须要大于0";
        if (selectMoney > freeMoney) throw "领用的官费和代理费不能超过剩余可领用金额!";
        if (selectMoney > getJiaoDanMoney()) throw "领用的官费和代理费不能超过业务交单金额!";
        if (selectMoney + getUsedMoney() > getJiaoDanMoney()) throw "累计领用金额超过了业务交单金额!";
    }

    function packagePostRow(row) {
        var res = {ArrID: row["ArrivalRegistrationID"]};
        res.CasesID = getCasesId();
        //res.DocSN=row["DocumentNumber"];
        res.Dai = parseFloat(row["FreeDai"] || 0);
        res.Guan = parseFloat(row["FreeGuan"] || 0);
        res.Total = getSelectMoney(row);
        var Info = getClientInfo();
        res.ClientID = Info.id;
        res.ClientName = Info.text;
        return res;
    }

    function getSelectMoney(row) {
        var total = parseFloat(row["FreeDai"] || 0) + parseFloat(row["FreeGuan"] || 0);
        return total;
    }

    function getClientInfo() {
        var clientIdCon = mini.getbyName('ClientID');
        return {id: clientIdCon.getValue(), text: clientIdCon.getText()};
    }
}