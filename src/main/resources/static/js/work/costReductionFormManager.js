function costReductionFormManager() {
    var funs={};
    this.getValue = function (doValid) {
        if(doValid==null || doValid==undefined) doValid=false;
        var result = {};
        var oo = funs["beforeSave"](result,doValid);
        if (oo) {
            for(var n in oo){
                result[n]=oo[n];
            }
        }
        var form = new mini.Form('#CostReductionForm');
        if(doValid==true){
            form.validate();
            if (form.isValid() == false) throw "数据录入不完整，操作被中止!";
        }
        var data = form.getData();
        result.Data=mini.encode(data);
        if ((data["customerId"]!=null && data["customerId"]!=undefined) || (data["reductionRequestNumber"]!=null && data["reductionRequestNumber"]!=undefined) || (data["reductionRequest"]!=null && data["reductionRequest"]!=undefined) && (data["costReductionId"]!=null || data["costReductionId"]!=undefined) || (data["uUId"]!=null && data["uUId"]!=undefined)){
            result["costReductionId"]=data["costReductionId"];
            result["uUId"]=data["uUId"];
            result["customerId"]=data["customerId"];
            result["reductionRequestNumber"]=data["reductionRequestNumber"];
            result["reductionRequest"]=data["reductionRequest"];
        }
        return result;
    }
    this.setValue=function(obj){
        var form = new mini.Form('#CostReductionForm');
        var xObj=mini.decode(obj ||"{}");
        form.setData(xObj);
        funs.afterLoad(obj);
    };
    this.bindEvent=function(name,fun){
        if(fun)
        {
            var xfun=funs[name]
            if(xfun){
                funs[name]=fun;
            }
        }
    }
    this.validate=function(){
        var form = new mini.Form('#CostReductionForm');
        form.validate();
        return form.isValid();
    }
    function init(){
        funs={
            'beforeSave':function(result){return null;},
            'afterLoad':function(obj){}
        };
    }
    init();
}