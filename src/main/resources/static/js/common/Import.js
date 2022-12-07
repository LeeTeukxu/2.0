var dataGrid = null, config = null, dictList = {}, defaultList = null, isCreateSelect = false;;
var dataFormat = {}, formatObj = null, formatConfig = null, mode = null;
$(function () {
    mini.parse();
    dataGrid = mini.get('datagrid1');
});
function setConfig(con) {
    mode = con['mode'];
    config = con;
}