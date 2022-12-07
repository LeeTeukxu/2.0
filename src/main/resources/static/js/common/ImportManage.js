function ImportManage(config) {
    var mode=config['mode'] || 'data';
    var openUrl = config['openUrl'] || '';
    var SaveUrl = config['SaveUrl'];
    var title = decodeURIComponent(config['Title']) || '导入excel',
        width = config['Widht'] || "100%",
        height = config['Height'] || "100%",
        dataGrid = config['dataGrid'];

    if (!dataGrid) throw 'dataGrid不能为空！';
    mini.open({
        url: openUrl,
        title: title,
        width: width,
        height: height,
        onload: function () {
        },
        ondestroy: function (action) {
            //刷新表格
            dataGrid.reload();
        }
    });
}