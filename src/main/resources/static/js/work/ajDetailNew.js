function ajDetail(gridId,addId,removeId,xMode){
    var grid=null;
    var cmdAdd=null;
    var cmdRemove=null;
    var casesId=null;
    var moneyGrid=null;
    var g=this ;
    window.aj=g;
    var yCon=null;
    var mode=xMode;
    var clientName="";
    function init(){
        grid=mini.get(gridId);
        if(!grid){
            grid={
                on:function(){},
                load:function(){},
                getData:function(){},
                setAllowCellEdit:function(){},
                isChanged:function(){}
            };
        }
        cmdAdd=mini.get(addId);
        cmdRemove=mini.get(removeId);
        if(cmdAdd)cmdAdd.on('click',add);
        if(cmdRemove)cmdRemove.on('click',remove);
        yCon=mini.get('#yId');
        grid.on('load',function(){
           refreshCount();
        });
        grid.on('drawcell',function(e){
            var field=e.field;
            var record=e.record;
            var uText="下载";

            if(field=='techFiles' || field=="zlFiles" ){
                var now=record[field];
                if(mode=="Add" || mode=="Edit"){
                    if(now) uText="管理"; else uText="上传";
                } else {
                    if(now)uText="下载"; else uText="";
                }
                if(uText){
                    var type="Tech";
                    if(field=="zlFiles") type="Zl";
                    e.cellHtml='<a href="#" style="color:blue;text-decoration:underline"' +
                        ' onclick="aj.uploadRow('+"'"+record._id+"','"+type+"'"+')">&nbsp;'+uText+'&nbsp;</a>';
                } else e.cellHtml="";
            }
            else if(field=="totalMoney"){
                var num=parseInt(record["num"]);
                var guanMoney=parseFloat(record["guanMoney"] || 0);
                var daiMoney=parseFloat(record["daiMoney"] || 0);
                var total=num*daiMoney+guanMoney;
                e.value=total;
                e.record.totalMoney=total;
                e.cellHtml=total.toFixed(2);
            }
        });
        if(yCon){
            yCon.on('valuechanged',function(e){
                var tree=e.source;
                if(tree){
                    var record=tree.getSelectedNode();
                    //var costCon=mini.getbyName('guanMoney');
                    //if(costCon)costCon.setValue(record.cost);
                    var daiCon=mini.getbyName('daiMoney');
                    if(daiCon)daiCon.setValue(record.price);
                    var dateCon=mini.getbyName('clientRequiredDate');
                    if(dateCon){
                        var now=new Date();
                        var maxDay=addDays(now,parseInt(record.maxDays));
                        dateCon.setValue(maxDay);
                    }
                }
            });
        }
        var addAJCon=mini.get('#AddAJ');
        if(addAJCon){
            addAJCon.on('click',addAJ);
        }
        if(xMode=="Add" || xMode=="Edit"){
            grid.on('cellbeginedit',function(e){
                var editor=e.editor;
                var field=e.field;
                var record=e.record;
                if(field=='clientLinkManA')
                {
                    editor.on('buttonclick',function(){
                        buttonClick(record._id);
                    });
                }
                else if(field=="relNo"){
                    editor.on('buttonclick',function(){
                        buttonClickEx(record._id);
                    });
                }
            });
            grid.on('cellendedit',function(e){
                var row=e.record ;
                var editor=e.editor;
                var field=e.field;
                if(field=="clientLinkManA")editor.un('buttonclick');
                else if(field=="RelNo")editor.un('buttonclickEx');
            });
        } else {
            grid.setAllowCellEdit(false);
        }
        var cmdAJSave=mini.get('#saveAj');
        if(cmdAJSave){
            cmdAJSave.on('click',save);
        }
        var cmdChange=mini.get('#changeTotal');
        if(cmdChange){
            cmdChange.on('click',function(){
                var rows=grid.getData();
                var realMoney=countTotalMoney(rows);
                mini.prompt('输入优惠后总价','系统提示',function(action,value){
                    var money=parseFloat(value);
                    if(isNaN(money)){
                        mini.alert('输入金额不正确!');
                        return ;
                    }
                    if(money>realMoney){
                        mini.alert('优惠总价不能大于实际金额!');
                        return ;
                    }
                    if(money>0){
                        var url='/cases/changeAllMoney';
                        $.post(url,{allMoney:money,CasesID:casesId},function(result){
                            if(result.success)
                            {
                                mini.alert('折扣总价更新成功!','系统提示',function(){
                                   var con= mini.getbyName('AllMoney');
                                   if(con)con.setValue(value);

                                   var dMoney=realMoney-money;
                                   mini.getbyName('DMoney').setValue(dMoney);
                                });
                            } else mini.alert(result.message || "更新折扣总价失败，请稍候重试!");
                        });
                    }
                });
            });
        }
        grid.load();

        moneyGrid=mini.get('#gridDetail');
    }
    function refreshCount(){
        var rows=grid.getData();
        var txt="";
        if(rows.length>0){
            txt=countAJNum(rows);
        }
        var con=mini.getbyName("Nums");
        if(con)con.setValue(txt);
    }
    function countTotalMoney(rows){
        var total=0;
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            var money=parseFloat(row["totalMoney"]);
            total+=money;
        }
        return total;
    }
    this.validate=function(doValidate){
        if(doValidate==true) {
            var rows=grid.getData();
            if(rows.length==0) return false;
        }
        grid.validate();
        return grid.isValid();
    }
    this.setClientName=function(cName){
        clientName=cName;
    }
    function addAJ(){
        var form=new mini.Form('#ajForm');
        form.validate();
        if(form.isValid()){
            var casesIdCon=mini.get('#casesId');
            var casesId=casesIdCon.getValue();
            function g(){
                var ddx=mini.loading('正在生成明细数据......');
                var data=form.getData();
                data.casesId=casesId;
                var url='/cases/createAJRecord';
                $.post(url,{Data:mini.encode(data)},function(result){
                    mini.hideMessageBox(ddx);
                    if(result.success){
                        mini.alert('业务清单生成成功!','系统提示',function(){
                            var win=mini.get('#addYwWindow');
                            if(win)win.hide();
                            grid.load({casesId:casesId});
                        });
                    } else{
                        mini.alert(result.message || "数据生成失败,请稍候重试!");
                    }
                });
            }
            mini.confirm('确认要生成业务交单明细吗？','系统提示',function(act){
                if(act=='ok'){
                    g();
                }
            });
        } else {
            mini.alert('数据录入不完整，操作被中止!');
        }
    }
    function save(){
        var rows=grid.getChanges('modified');
        function g(){
            var url='/cases/saveSubs';
            $.post(url,{Data:mini.encode(rows)},function(result){
                if(result.success){
                    mini.alert('数据保存成功!','系统提示',function(){
                        grid.accept();
                        grid.reload();
                    });
                }
            });
        }
        if(rows.length>0){
            var ok=false;
            for(var i=0;i<rows.length;i++){
                var row=rows[i];
                grid.validateRow(row);
                var errors=grid.getCellErrors();
                if(errors.length>0){
                    mini.alert('数据录入不完整，保存操作被中止!','系统提示',function(){
                        grid.beginEditRow(row,errors[0].column);
                    });
                    return ;
                }
            }
            mini.confirm('确认要保存信息','系统提示',function(act){
                if(act=='ok'){
                    g();
                }
            });
        } else mini.alert('没有发现可保存内容!');
    }
    this.uploadRow=function(id,type){
        var rows=grid.getChanges('modified');
        if(rows.length==0){
            var row=grid.getRowByUID (id);
            if(row){
                var subId=row["subId"];
                var casesId=row["casesId"];
                var url='/cases/getSubFiles';
                $.getJSON(url,{SubID:subId,Type:type},function(result){
                    if(result.success){
                        var att=result.data || [];
                        doUpload(casesId,subId,att,type,row);
                    } else mini.alert('获取文件设置失败:'+result.message);
                });
            }
        }else{
            mini.alert('存在未保存的修改，请先保存数据后再进行上传操作！');
        }
    }
    this.render=function(){
        if(mode=='Add' || mode=="Edit"){
            cmdAdd.enable();
            cmdRemove.enable();
        } else {
            cmdAdd.disable();
            cmdRemove.disable();
        }

    }
    this.getData=function(doValidate){
        if(doValidate){
            grid.validate();
            if(grid.isValid()){
                var rows=mini.clone(grid.getData());
                return rows ;
            } else throw "数据录入不完整!";
        } else {
            var rows = mini.clone(grid.getData());
            return rows;
        }
    }
    this.loadData=function(cId){
        casesId=cId;
        if(grid.isChanged()==false)
        {
            grid.load({casesId:casesId});
        }
    }
    function add(){
        var id=mini.get('#id').getValue();
        if(!id){
            mini.alert('请先保存交单信息!');
            return ;
        }
        var pForm=new mini.Form('#Table1');
        pForm.validate();
        if(pForm.isValid()==false){
            mini.alert('业务交单数据不完整，操作被中止!');
            return ;
        }

        var rows=grid.getChanges('modified');
        if(rows.length==0){
            var form=new mini.Form('#ajForm');
            var win=mini.get('#addYwWindow');
            form.reset();
            win.show();
        }else{
            mini.alert('存在未保存的修改，请选进行保存!');
        }
    }
    function addDays(dateObj,d){
        var dayNumber = new Date(dateObj.getYear(), dateObj.getMonth() + 1, 0).getDate(); //当月天数
        var Year = dateObj.getFullYear();  //当前年
        var Month = dateObj.getMonth() + 1; //当前月
        var Day = dateObj.getDate(); //当前日

        for (var i = 0; i < d; i++) {
            Day++;
            if (Day > dayNumber) {
                Day = 1;
                Month++;
                if (Month > 12) {
                    Month = 1;
                    Year++;
                }
            }
        }
        return new Date(Year, Month - 1, Day);
    }
    function remove(){
        var rows=grid.getSelecteds();
        if(rows.length>0){
            if(getAllMoney()-getSelectedMoney()<getUsedMoney()){
                mini.alert('删除选择的交单记录会造成交单金额小于领用金额。删除操作被中止!');
                return ;
            }
            function g(){
                var cs=[];
                for(var i=0;i<rows.length;i++) {
                    var row=rows[i];
                    var subId=row["subId"];
                    cs.push(subId);
                }
                var url='/cases/removeAjDetail?AJID='+cs.join(',');
                $.post(url,{},function(result){
                    if(result.success){
                        mini.alert('选择的记录删除成功!','删除提示',function(){
                            for(var i=0;i<rows.length;i++){
                                var row=rows[i];
                                grid.removeRow(row);
                                grid.acceptRecord(row);
                            }
                            refreshCount();
                        });
                    }else mini.alert(result.message || "删除失败，请稍候重试!");
                });
            }
            mini.confirm('确认要删除选择的记录?','系统提示',function(action){
                if(action=='ok'){
                   g();
                }
            });
        } else  mini.alert('请选择要删除的记录!');
    }
    function doUpload(casesId,subId,ids,type,row){
        mini.open({
            url:'/attachment/addFile?IDS='+ids+'&Mode='+mode+'&FileType='+type+'&ClientName='+clientName+'&SubID='+subId,
            width:'85%',
            height:'92%',
            title:'案件附件',
            onload:function(){
                var iframe = this.getIFrameEl();
                iframe.contentWindow.addEvent('eachFileUploaded',function(data,min,window,call){
                    debugger;
                    var attId=data.AttID;
                    var url='/cases/saveSubFiles';
                    var arg={CasesID:casesId,SubID:subId,AttID:attId,Type:type};
                    $.post(url,arg,function(result){
                        if(result.success){
                            var obj={};
                            var field="techFiles";
                            if(type=="Zl")field="zlFiles";
                            obj[field]=attId;
                            grid.updateRow(row,obj);
                        }
                        if(call)call(result);
                    })
                });
                iframe.contentWindow.addEvent('eachFileRemoved',function(data){
                    var casesID=row["CasesID"];
                    if(casesID){
                        var url='/cases/removeSubFiles';
                        $.post(url,{CasesID:casesID,AttID:data.ATTID},function(result){
                            if(result.success==false){
                                mini.alert('删除文件信息失败，请联系管理员解决问题。');
                            } else {
                                doReload(grid);
                            }
                        })
                    }
                });
            },
            ondestroy:function (){
               grid.reload();
            }
        });
    }
    function buttonClick(id){
        var row=grid.getRowByUID(id);
        mini.open({
            url:'/work/clientInfo/queryLinkMan?multiselect=false',
            showModal:true,
            width:800,
            height:600,
            title:'选择客户资料',
            ondestroy:function(action){
                if(action=='ok'){
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = mini.clone(data);
                    var column=grid.getColumn('clientLinkMan');
                    var editor=grid.getCellEditor(column,row);
                    if(editor!=null){
                        ///editor.setValue(data.Code);
                        //editor.setText(data.Code);
                    }
                    grid.updateRow(row,{'clientLinkMan':data.Code,'clientLinkPhone':data.Name,'clientLinkMail':data.Email});
                }
            }
        });
    }
    function buttonClickEx(id){
        var row=grid.getRowByUID(id);
        mini.open({
            url:'/cases/queryTechRecord',
            showModal:true,
            width:1000,
            height:300,
            title:'选择历史交单明细记录',
            ondestroy:function(action){
                if(action=='ok'){
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = mini.clone(data);
                    var column=grid.getColumn('relNo');
                    var editor=grid.getCellEditor(column,row);
                    if(editor!=null){
                        editor.setValue(data.SubNo);
                        editor.setText(data.SubNo);
                    }
                    grid.updateRow(row,{'relNo':data.SubNo,'relId':data.SubID});
                }
            }
        });
    }
    function countAJNum(rows){
        var nums={};
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            var yname=$.trim(row["yname"]);
            var num=parseInt(row["num"]);
            if(!nums[yname])nums[yname]=1;
            else {
                var nn=parseInt(nums[yname],10);
                nn+=num;
                nums[yname]=nn;
            }
        }
        var cc=[];
        for(var name in nums){
            cc.push(nums[name].toString()+name);
        }
        return cc.join();
    }
    ///已领用多少金额
    function getUsedMoney() {
        var res = 0;
        var rows = moneyGrid.getData();
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            var total = parseFloat(row["total"] || 0);
            var state=parseInt(row["State"] || 0);
            if(state!=1)res += total;
        }
        return res;
    }
    //所有交单金额。
    function getAllMoney(){
        var res=0;
        var rows=grid.getData();
        for(var i=0;i<rows.length;i++){
            var row=rows[i];
            var totalMoney=parseFloat(row["totalMoney"] || 0);
            res+=totalMoney;
        }
        return res;
    }
    function getSelectedMoney(){
        var row=grid.getSelected();
        if(!row) return 0;
        return parseFloat(row["totalMoney"] || 0);
    }
    init();
}