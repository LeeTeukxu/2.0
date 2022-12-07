function ajDetail(gridId,addId,removeId){
    var grid=null;
    var cmdAdd=null;
    var cmdRemove=null;
    var casesId=null;
    var g=this ;
    window.aj=g;
    function init(){
        grid=mini.get(gridId);
        cmdAdd=mini.get(addId);
        cmdRemove=mini.get(removeId);
        if(cmdAdd)cmdAdd.on('click',add);
        if(cmdRemove)cmdRemove.on('click',remove);
        grid.on('load',function(){

        });
        grid.on('cellbeginedit',function(e){
            var editor=e.editor;
            var field=e.field;
            var record=e.record;
            if(field=='ajLinkMan')
            {
                editor.on('buttonclick',function(){
                    buttonClick(record._id);
                });
            }
        });
        grid.on('cellendedit',function(e){
            var row=e.record ;
            var editor=e.editor;
            var field=e.field;
            if(field=="ajLinkMan")editor.un('buttonclick');
        });
        grid.on('drawcell',function(e){
            var field=e.field;
            var record=e.record;
            if(field=='Action' ){
                return e.cellHtml='<a href="#" style="color:blue;text-decoration:underline"' +
                    ' onclick="aj.uploadRow('+"'"+record._id+"'"+')">&nbsp;案件附件&nbsp;</a>';
            }
        });
    }
    function buttonClick(id){
        var row=grid.getRowByUID(id);
        mini.open({
            url:'/work/clientInfo/queryLinkMan?multiselect=false',
            showModal:true,
            width:600,
            height:600,
            title:'选择客户资料',
            ondestroy:function(action){
                if(action=='ok'){
                    var iframe = this.getIFrameEl();
                    var data = iframe.contentWindow.GetData();
                    data = mini.clone(data);
                    var column=grid.getColumn('ajLinkMan');
                    var editor=grid.getCellEditor(column,row);
                    if(editor!=null){
                        editor.setValue(data.Code);
                        editor.setText(data.Code);
                    }
                    var clientId=data["PID"].toString().replace('X','');
                    grid.updateRow(row,{'ajLinkMan':data.Code,'clientId':clientId,'ajLinkPhone':data.Name});
                }
            }
        });
    }
    this.uploadRow=function(id){
        var row=grid.getRowByUID (id);
        if(row){
            var att=row["attIds"] ||"";
            doUpload(row,att);
        }
    }
    this.render=function(mode,roleName){
        if(mode=='Add' || mode=="Edit"){
            cmdAdd.enable();
            cmdRemove.enable();
        } else {
            cmdAdd.disable();
            cmdRemove.disable();
        }
    }
    this.getData=function(){
        grid.validate();
        if(grid.isValid()){
            var rows=mini.clone(grid.getData());
            return rows ;
        } else throw "数据录入不完整!";
    }
    this.loadData=function(casesId){
        grid.load({CasesID:casesId});
    }
    function add(){
        grid.validate();
        if(grid.isValid())
        {
            var data=grid.getData();
            var length=data.length+1;
            var pre="ZL"+mini.formatDate(new Date(),'yyyyMM');
            var x="000";
            if(length>9) x="00";
            var row={ajCode: pre+x+length.toString()};
            grid.addRow(row);
            grid.validateRow(row);
        } else mini.alert('请将数据录入完整后，再进行添加操作!');
    }
    function remove(){
        var row=grid.getSelected();
        if(row){
            function g(){
                var ajId=row["ajid"];
                if(!ajId) {
                    grid.removeRow(row);
                } else {
                    var url='/work/cases/removeAjDetail';
                    $.post(url,{AJID:ajId},function(result){
                        if(result.success){
                            mini.alert('删除成功!','删除提示',function(){
                                grid.removeRow(row);
                                grid.acceptRecord(row);
                            });
                        }
                    });
                }
            }
            mini.confirm('确认要删除选择的记录?','系统提示',function(action){
                if(action=='ok'){
                   g();
                }
            });
        } else  mini.alert('请选择要删除的记录!');
    }
    function doUpload(row,ids){
        mini.open({
            url:'/attachment/addFile?IDS='+ids,
            width:800,
            height:400,
            title:'案件附件',
            onload:function(){
                var iframe = this.getIFrameEl();
                iframe.contentWindow.addEvent('eachFileUploaded',function(data){
                    var atts=(row["attIds"]||"").split(',');
                    var attId=data.AttID;
                    if(atts.indexOf(attId)==-1){
                        atts.push(attId);
                    }
                    row["attIds"]=atts.join(',');
                });
                iframe.contentWindow.addEvent('eachFileRemoved',function(data){
                    var ajId=row["ajid"];
                    if(ajId){
                        var url='/work/cases/removeAjAttachment';
                        $.post(url,{AJID:ajId,AttID:data.ATTID},function(result){
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
    init();
}