
var ColumnsContextMenu = function (grid, roleName) {

    var me = this;
    this.grid = grid;
    this.menu = this.createMenu(roleName);
    this.currentColumn = null;

    this.menu.on("beforeopen", this.onBeforeOpen, this);
    grid.setHeaderContextMenu(this.menu);

}

ColumnsContextMenu.prototype = {
    createMenu: function (roleName) {
        var grid = this.grid;
        //创建菜单对象
        var menu = mini.create({ type: "menu", hideOnClick: false });
        //创建自定义菜单列
        var items = [
            {text:'修改标题',name:'changeTitle'},
            '-'
        ];
        //创建隐藏菜单列
        var columns = grid.getBottomColumns();
        var columnMenuItems = { text: "隐藏列", name: "showcolumn" };
        columnMenuItems.children = [];
        for (var i = 0, l = columns.length; i < l; i++) {
            var column = columns[i];
            if (column.hideable) continue;
            var item = {};
            item.checked = column.visible;
            item.checkOnClick = true;
            item.text = column.header;
            if (item.text == "&nbsp;") {
                if (column.type == "indexcolumn") item.text = "序号";
                if (column.type == "checkcolumn") item.text = "选择";
            }
            item.enabled = column.enabled;
            //console.log(item);
            item._column = column;
            columnMenuItems.children.push(item);
        }
        items.push(columnMenuItems);
        items.push('-');
        items.push({ text: "保存配置", name: "saveConfig" });

        // if(roleName){
        //     if(roleName=="系统管理员"){
        //         items.push({ text: "共享配置", name: "saveConfigShare" });
        //     }
        // }
        menu.setItems(items);
        menu.on("itemclick", this.onMenuItemClick, this);
        return menu;
    },
    onBeforeOpen: function (e) {
        var grid = this.grid;
        var column = grid.getColumnByEvent(e.htmlEvent);
        this.currentColumn = column;
    },
    onMenuItemClick: function (e) {
        function decodeUrl(url){
            var urls=url.split('/');
            var length=urls.length;
            var cs=[];
            for(var i=0;i<urls.length;i++){
                var surl=$.trim(urls[i]);
                if(surl.toString().toLowerCase().indexOf('http')>-1) continue;
                if(surl.toString().indexOf(":")>-1) continue;
                if(surl=="") continue;
                if(surl.indexOf('.')>-1) continue;
                if(surl.indexOf('?')>-1) surl=surl.split('?')[0];
                cs.push(surl);
            }
            return '/'+cs.join('/');
        }
        var grid = this.grid;
        var menu = e.sender;
        var columns = grid.getBottomColumns();
        var items = menu.getItems();
        var item = e.item;
        var targetColumn = item._column;
        var currentColumn = this.currentColumn;
        if (item.name == "frozencolumn") {
            var frozenEndColumn = grid.getFrozenEndColumn()
            if (!currentColumn.visible) return
            if (frozenEndColumn != -1) {
                grid.unFrozenColumns();

            } else {
                grid.frozenColumns(0, (currentColumn._index - 1));
            }
            return
        }
        //显示/隐藏列
        if (targetColumn) {
            //确保起码有一列是显示的
            var checkedCount = 0;
            var columnsItem = mini.getbyName("showcolumn", menu);
            var childMenuItems = columnsItem.menu.items;
            for (var i = 0, l = childMenuItems.length; i < l; i++) {
                var it = childMenuItems[i];
                if (it.getChecked()) checkedCount++;
            }
            if (checkedCount < 1) {
                item.setChecked(true);
            }
            if (item.getChecked()) grid.showColumn(targetColumn);
            else grid.hideColumn(targetColumn);
        }
        if(item.name=="saveConfig"){
            function g(){
                var getter=new gridInformationGetter(grid);
                var result=getter.getAll();
                result.url=decodeUrl(window.location.href);
                $.post('/gridConfig/save',{Data:mini.encode(result),Share:0},function(r){
                    if(r.success){
                        mini.alert('当前表格显示设置保存成功，刷新后可以查看最新设置结果!','系统提示');
                    } else mini.alert(r.message || "保存失败，请稍候重试！");
                });
            }
            mini.confirm('确认要保存当前表格的显示设置?','系统提示',function(r){
                if(r=='ok'){
                    g();
                }
            });
        }
        if(item.name=="saveConfigShare"){
            function g(){
                var getter=new gridInformationGetter(grid);
                var result=getter.getAll();
                result.url=decodeUrl(window.location.href);
                $.post('/gridConfig/save',{Data:mini.encode(result),Share:0},function(r){
                    if(r.success){
                        mini.alert('当前表格显示设置保存成功，刷新后可以查看最新设置结果!','系统提示');
                    } else mini.alert(r.message || "保存失败，请稍候重试！");
                });
            }
            mini.confirm('确认要保存当前表格的显示设置?','系统提示',function(r){
                if(r=='ok'){
                    g();
                }
            });
        }
        if(item.name=="changeTitle"){
            if(currentColumn){
                var text="当前列的标题为:"+currentColumn.header;
                mini.prompt(text,"更改标题",function(action,value){
                    if(action=='ok'){
                        grid.updateColumn(currentColumn,{header:value});
                    }
                });

            }
        }
    }
};
function gridInformationGetter(grid){
    this.getAll=function(){
        var res={};
        res.columnIndex=getColumnIndex();
        res.columnWidth=getColumnWidth();
        res.columnTitle=getColumnTitle();
        res.columnVisible=getColumnVisible();
        return res;
    }
    function getColumnIndex(){
        var columns=grid.getColumns();
        var hash={};
        for(var i=0;i<columns.length;i++){
            var column=columns[i];
            var field=column["field"];
            if(field){
                hash[field]=i;
            }
        }
        return hash;
    }
    function getColumnVisible(){
        var columns=grid.getColumns();
        var hash={};
        for(var i=0;i<columns.length;i++){
            var column=columns[i];
            var field=column["field"];
            if(field){
                hash[field]=column.visible;
            }
        }
        return hash;
    }
    function getColumnWidth(){
        var columns=grid.getColumns();
        var hash={};
        for(var i=0;i<columns.length;i++){
            var column=columns[i];
            var field=column["field"];
            if(field){
                hash[field]=parseInt(column.width.toString().replace("px",""));
            }
        }
        return hash;
    }
    function getColumnTitle(){
        var columns=grid.getColumns();
        var hash={};
        for(var i=0;i<columns.length;i++){
            var column=columns[i];
            var field=column["field"];
            if(field){
                hash[field]=$.trim(column.header);
            }
        }
        return hash;
    }
}
function gridConfigSetter(grid){
    grid.getColumnByField=function(field){
        var columns=grid.getColumns();
        for(var i=0;i<columns.length;i++){
            var column=columns[i];
            var fx=column["field"];
            if(!fx) continue;
            if(fx==field) return column;
        }
        return null;
    }
    this.doApply=function(config){
        var visibleObj=mini.decode(config.columnVisible || "{}");
        if(visibleObj){
            var indexObj=mini.decode(config.columnIndex || "{}");
            var addressObj={};
            for(var name  in indexObj){
                var index=parseInt(indexObj[name]);
                addressObj[index]=name;
            }
            var titleObj=mini.decode(config.columnTitle || "{}");
            var widthObj=mini.decode(config.columnWidth || "{}");

            for(var field in visibleObj){
                var xObj={};
                var column=grid.getColumnByField(field);
                if(column==null) continue;
                var visible=visibleObj[field];
                if(visible)grid.showColumn(column); else grid.hideColumn(column);
                xObj["visible"]=visible;
                var width=widthObj[field];
                if(width){
                    grid.setFitColumns(false);
                    grid.setColumnWidth(column,width);
                }
                var header=titleObj[field];
                if(header)xObj["header"]=header;
                grid.updateColumn(column,xObj);
            }
            var columns=grid.getColumns();
            var cs=[];
            for(var i=0;i<columns.length;i++){
                var column=columns[i];
                var field=column["field"];
                if(field){
                    var name=addressObj[i];
                    if(name){
                        var findColumn=grid.getColumnByField(name);
                        if(findColumn!=null){
                            cs.push(findColumn);
                        }
                    } else cs.push(column);
                } else cs.push(column);
            }
            grid.setColumns(cs);
            grid.doLayout();

        }
    }
}


