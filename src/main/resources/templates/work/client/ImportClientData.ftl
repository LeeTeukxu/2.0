<#include "/shared/layout.ftl">
<@layout>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploaderOther.js"></script>
    <script type="text/javascript" src="/js/common/excelExportOther.js"></script>
    <script type="text/javascript">
        var Type = "${Type}";
        var Codes="${Code}";
        var FileName="${FileName}";
        var  AllClient=${AllClient};
    </script>
    <div id="ReqFile" style="width:100%;height:100%"></div>
</@layout>
<@js>
    <script type="text/javascript">
        function init() {
            var k = $('#ReqFile').fileUploader({
                mode: 'Add',
                showConfig: false,
                uploadUrl: '/common/excelImport/selectXls',
            });
            k.loadFiles({});
        }
        init();
    </script>
</@js>