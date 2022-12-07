function ywDetail(gridId,addId,removeId,xMode){
    var grid=null;
    var cmdAdd=null;
    var cmdRemove=null;
    var casesId=null;
    var g=this ;
    window.yw=g;
    var yCon=null;
    var mode=xMode;
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

            if(field=='techFiles'){
                var now=record[field];
                if(mode=="Add" || mode=="Edit"){
                    if(now) uText="管理"; else uText="上传";
                } else {
                    if(now)uText="下载"; else uText="";
                }
                if(uText){
                    var type="Tech";
                    e.cellHtml='<a href="#" style="color:blue;text-decoration:underline"' +
                        ' onclick="yw.uploadRow('+"'"+record._id+"','"+type+"'"+')">&nbsp;'+uText+'&nbsp;</a>';
                } else e.cellHtml="";
            } else if(field=="total"){
                var num=parseInt(record["num"]);
                var guanMoney=parseFloat(record["guan"] || 0);
                var daiMoney=parseFloat(record["dai"] || 0);
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
                    var daiCon=mini.getbyName('dai');
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
        var addYwCon=mini.get('#AddYw');
        if(addYwCon){
            addYwCon.on('click',addYw);
        }
        if (mode == "Browse"){
            grid.setAllowCellEdit(false);
        }
        var cmdYwSave=mini.get('#saveYw');
        if(cmdYwSave){
            cmdYwSave.on('click',save);
        }
        var cmdChange=mini.get('#changeTotal');
        if(cmdChange){
            cmdChange.on('click',function(){
                var rows=grid.getData();
                var realMoney=countTotalMoney(rows);
                mini.prompt('输入优惠总价','系统提示',function(action,value){
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
                        var url='/work/tradeCases/changeAllMoney';
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
        function addYw(){
            var form=new mini.Form('#ywForm');
            form.validate();
            if(form.isValid()){
                var casesIdCon=mini.get('#casesid');
                var casesId=casesIdCon.getValue();
                function g(){
                    var ddx=mini.loading('正在生成明细数据......');
                    var data=form.getData();
                    data.casesId=casesId;
                    var url='/work/tradeCases/createYwRecord';
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
        grid.load();
    }
    function save(){
        var rows=grid.getChanges('modified');
        function g(){
            var url='/work/tradeCases/saveYws';
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
        var row=grid.getRowByUID (id);
        if(row){
            var subId=row["subId"];
            var casesId=row["casesId"];
            var url='/work/tradeCases/getSubFiles';
            $.getJSON(url,{SubID:subId,Type:type},function(result){
                if(result.success){
                    var att=result.data || [];
                    doUpload(casesId,subId,att,type,row);
                }
            });
        }
    }
    // this.render=function(){
    //     if(mode=='Add' || mode=="Edit"){
    //         cmdAdd.enable();
    //         cmdRemove.enable();
    //     } else {
    //         cmdAdd.disable();
    //         cmdRemove.disable();
    //     }
    // }
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
            var money=parseFloat(row["total"]);
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
    function calcAllMoney(row){
        var num=parseFloat(row.num || 0);
        var guan=parseFloat(row.guan || 0);
        var dai=parseFloat(row.dai || 0);
        var price=guan+dai;
        var total=price*num;
        return {total:total,price:price};
    }
    this.getData=function(){
        grid.validate();
        if(grid.isValid()){
            var rows=mini.clone(grid.getData());
            return rows ;
        } else throw "数据录入不完整!";
    }
    this.getCount=function(){
        return (grid.getData() || []).length;
    }
    this.loadData=function(cId){
        casesId=cId;
        if(grid.isChanged()==false)
        {
            grid.load({casesId:casesId});
        }
    }
    function add(){
        grid.validate();
        var id=mini.get('#id').getValue();
        if(!id){
            mini.alert('请先保存交单信息!');
            return ;
        }
        var rows=grid.getChanges('modified');
        if(rows.length==0){
            var form=new mini.Form('#ywForm');
            var win=mini.get('#addYwWindow');
            form.reset();
            win.show();
        }else{
            mini.alert('存在未保存的修改，请选进行保存!');
        }
        var form=new mini.Form('#ywForm');
        var win=mini.get('#addYwWindow');
        form.reset();
        win.show();
    }
    function remove(){
        var rows=grid.getSelecteds();
        if(rows.length>0){
            function g(){
                var cs=[];
                for(var i=0;i<rows.length;i++) {
                    var row=rows[i];
                    var subId=row["subId"];
                    cs.push(subId);
                }
                var url='/work/tradeCases/removeYwDetail?YWID='+cs.join(',');
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
            url:'/attachment/addFile?IDS='+ids+'&Mode='+mode,
            width:800,
            height:400,
            title:'案件附件',
            onload:function(){
                var iframe = this.getIFrameEl();
                iframe.contentWindow.addEvent('eachFileUploaded',function(data){
                    var attId=data.AttID;
                    var url='/work/tradeCases/saveSubFiles';
                    var arg={CasesID:casesId,SubID:subId,AttID:attId,Type:type};
                    $.post(url,arg,function(result){
                        if(result.success){
                            var obj={};
                            var field="techFiles";
                            if(type=="Zl")field="zlFiles";
                            obj[field]=attId;
                            grid.updateRow(row,obj);
                        }
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
    init();
}