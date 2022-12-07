function formManager(formBuilder) {
    var tableDom = null;
    var funs={};
    this.render = function (div,mode) {
        tableDom = formBuilder.creat();
        $(div).append(tableDom);
        mini.parse();

        funs["afterInit"](tableDom);
        var form = new mini.Form(tableDom.prop('id'));
        if(mode=='Add' || mode=="Edit")  form.setEnabled(true);else form.setEnabled(false);
        afterInit();


        var fs=formBuilder.getButtonEditFields();
        for(var i=0;i<fs.length;i++){
            var name=fs[i];
            var con=mini.getbyName(name);
            if(con){
                con.on('buttonclick',buttonClick);
            }
        }
        return tableDom;
    }
    this.getValue = function (doValid) {
        if(doValid==null || doValid==undefined) doValid=false;
        var result = {};
        var watchFields = formBuilder.getWatchFields() || [];
        var saveAloneFields = formBuilder.getAloneFields();
        var oo = funs["beforeSave"](result,doValid);
        if (oo) {
            for(var n in oo){
                result[n]=oo[n];
            }
        }
        var form = new mini.Form(tableDom.prop('id'));
        if(doValid==true){
            form.validate();
            if (form.isValid() == false) throw "数据录入不完整，操作被中止!";
        }
        var data = form.getData();
        result.formText = mini.encode(data);
        result.watchFields = watchFields;
        result.saveAloneFields = saveAloneFields;
        for(var i=0;i<saveAloneFields.length;i++){
            var field=saveAloneFields[i];
            var val=data[field];
            if(val!=null && val!=undefined){
                result[field]=val;
            }
        }
        return result;
    }
    this.validate=function(){
        var form = new mini.Form(tableDom.prop('id'));
        form.validate();
        return form.isValid();
    }
    this.setValue=function(obj){
        var form = new mini.Form(tableDom.prop('id'));
        var xObj=mini.decode(obj.formText ||"{}");
        for(var n in xObj){
            var oo=xObj[n];
            if(oo==null || oo==undefined) delete xObj.n;
        }
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
    function init(){
        funs={
            'beforeSave':function(result){return null;},
            'afterInit':function(tableDom){},
            'afterLoad':function(obj){},
            'buttonEditClick':function(field,con){}
        };
    }
    function afterInit(){
        var fields=formBuilder.getAutoCreateFields() ||[];
        for(var i=0;i<fields.length;i++){
            var field=fields[i];
            var con=mini.getByName(field);
            if(con)con.disable();
        }
    }
    function buttonClick(e){
        funs.buttonEditClick(e.source.name,e.source);
    }
    init();
}