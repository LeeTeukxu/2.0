function formBuilder(config) {
    var instance = this;
    var buttonfield=[];
    function getMaxRow() {
        var maxRow = 0;
        for (var i = 0; i < config.length; i++) {
            var d = config[i];
            var rowIndex = parseInt(d.rowIndex || 0);
            if (rowIndex > maxRow) maxRow = rowIndex;
        }
        return maxRow;
    }

    function createMainTable(maxRow) {
        var ttx = $('<table id="Table_' + maxRow + '" style="table-layout:auto;width:100%;height:100%" class="layui-table">' +
            '<tbody class="container"></tbody>' +
            '</table>');
        for (var i = 0; i < maxRow; i++) {
            ttx.children('.container').append('<tr><td></td>><td></td></tr>');
        }
        return ttx;
    }

    function elementBuilder() {
        this.create = function (cell, cnf) {
            var type = cnf.type;
            var baseDom = null;
            switch (type) {
                case "text": {
                    baseDom = $('<input style="width:100%"/>');
                    baseDom.addClass("mini-textbox");
                    break;
                }
                case "file": {
                    baseDom = $('<input style="width:100%"/>');
                    baseDom.addClass("mini-htmlfile");
                    break;
                }
                case "textarea": {
                    baseDom = $('<textarea style="width:100%"></textarea>');
                    baseDom.addClass("mini-textarea");
                    if (cnf.height) baseDom.attr('height', cnf.height + 'px');
                    break;
                }
                case "date": {
                    baseDom = $('<input style="width:100%"/>');
                    baseDom.addClass("mini-datepicker");
                    var format=cnf.format || "yyyy-MM-dd";
                    baseDom.attr('format',format);
                    var ts=format.split(' ');
                    if(ts.length>1){
                        baseDom.attr('showTime',true);
                    }
                    break;
                }
                case "select": {
                    baseDom = $('<input style="width:100%" />');
                    baseDom.addClass("mini-combobox");
                    if(cnf.url)baseDom.attr('url',cnf.url).attr('autoload',true);
                    break;
                }
                case "radio": {
                    baseDom = $('<div  style="width:100%"></div>');
                    baseDom.addClass("mini-radiobuttonlist");
                    break;
                }
                case "checkbox": {
                    baseDom = $('<div  style="width:100%"></div>');
                    baseDom.addClass("mini-checkboxlist");
                    break;
                }
                case "tree": {
                    baseDom = $('<input style="width:100%" resultAsTree="false"/>');
                    baseDom.addClass("mini-treeselect");
                    var url=cnf.url || "";
                    if(url.toString().indexOf("useDefault")==-1)
                    {
                        baseDom.attr('valueField', 'FID')
                            .attr('parentField', 'PID')
                            .attr('textField', 'Name');
                    }
                    if(url)baseDom.attr('url',url).attr('autoload',true);
                    baseDom.attr('expandOnload',true);
                    var multiSelect=cnf.multiSelect || false;
                    baseDom.attr('multiSelect',multiSelect);
                    break;
                }
                case "buttonedit":{
                    baseDom = $('<input style="width:100%" allowInput="false" />');
                    baseDom.addClass("mini-buttonedit");
                    buttonfield.push(cnf.name);
                    break;
                }
            }
            baseDom.attr('required', cnf.required=="是")
                .prop('name', cnf.name)
                .attr('labelField', true)
                .attr('label', cnf.label);
            if(!cnf.url)
            {
                if (cnf.data) {
                    var ds = cnf.data.split(',');
                    var kk = [];
                    for (var i = 0; i < ds.length; i++) {
                        var d = ds[i];
                        var single = {id: d, text: d};
                        kk.push(single);
                    }
                    baseDom.attr('data', mini.encode(kk));
                }
            }
            if(cnf.value!=null && cnf.value!=undefined){
                baseDom.attr('value',cnf.value);
            }
            cell.append(baseDom);
        }
    }

    this.creat = function () {
        var maxRow = getMaxRow();
        var tableDom = createMainTable(maxRow);
        var rows = tableDom.find("tr");
        var bb = new elementBuilder();
        for (var rI = 0; rI < rows.length; rI++) {
            var row = $(rows[rI]);
            var rIndex = row.prop('rowIndex') + 1;
            var cells = row.find('td');
            for (var cI = 0; cI < cells.length; cI++) {
                var cell = $(cells[cI]);
                var cIndex = cell.prop('cellIndex') + 1;
                for (var i = 0; i < config.length; i++) {
                    var data = config[i];
                    var rowIndex = data.rowIndex;
                    var colIndex = data.colIndex;
                    if (rowIndex == rIndex && colIndex == cIndex) {
                        bb.create(cell, data);
                        var colspan = parseInt(data.colspan || 1);
                        if (colspan > 1) {
                            cell.prop('colspan', colspan);
                            $(cells[cIndex]).css({'display': 'none'});
                        }
                        break;
                    }
                }
            }
        }
        return tableDom;
    }
    this.getWatchFields=function(){
        var rs=[];
        for(var i=0;i<config.length;i++){
            var o=config[i];
            if(o.watchConfig=='是') rs.push(o.name);
        }
        return rs ;
    }
    this.getAloneFields=function () {
        var rs=[];
        for(var i=0;i<config.length;i++){
            var o=config[i];
            if(o.saveAlone=='是') rs.push(o.name);
        }
        return rs ;
    }
    this.getAutoCreateFields=function(){
        var rs=[];
        for(var i=0;i<config.length;i++){
            var o=config[i];
            if(o.autoCreate=='是') rs.push(o.name);
        }
        return rs ;
    }
    this.getButtonEditFields=function(){
        return buttonfield || [];
    }

}