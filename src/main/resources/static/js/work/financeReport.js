mini.parse();
debugger;
var beginCon=mini.get('#Begin');
var endCon=mini.get('#End');
var grid=mini.get('#grid1');
var queryField="";
var queryValue="";
var groupField="";

function onQueryMethodChanged(e){
    var btn=e.sender ;
    var checked=btn.getChecked();
    if(checked){
        groupField=btn.getValue();
        doQuery();
    }
}
function onQueryTimeChanged(e)  {
    var btn=e.sender ;
    var checked=btn.getChecked();
    if(checked){
        debugger;
        var fieldName=btn.getName();
        var fieldValue=btn.getValue();
        queryField=fieldName;
        queryValue=fieldValue;

        beginCon=mini.get('#Begin');
        endCon=mini.get('#End');
        if(beginCon){
            beginCon.setValue(null);
            endCon.setValue(null);
        }
        doQuery();
    }
}
function dateChange(){
    var keys=['Day','Week','Month'];
    var begin=beginCon.getValue();
    var end=endCon.getValue();
    if(!begin && !end){
        return ;
    } else {
        if(!begin)begin=new Date();
        if(!end)end=new Date();
        queryField="";
        queryValue ="";
        for(var i=0;i<keys.length;i++){
            var key=keys[i];
            var cons=mini.getsByName(key);
            for(var n=0;n<cons.length;n++){
                var con=cons[n];
                con.setChecked(false);
            }
        }
        doQuery()
    }
}
function doQuery(){
    var grid=mini.get('#grid1');
    var result={};
    var Cons=[];
    var names=['Payer','AddUser','Begin','End'];
    for(var i=0;i<names.length;i++){
        var name=names[i];
        var con=mini.get(name);
        if(con){
            var val=con.getValue();
            if(val){
                var obj={field:name,oper:'Like',value:val};
                Cons.push(obj);
            }
        }
    }
    if(Cons.length>0){
        result["cons"]=Cons;
    }
    result.timeField=queryField;
    result.timeValue=queryValue;
    result.groupField=groupField;
    grid.load({Query:mini.encode(result)});
}