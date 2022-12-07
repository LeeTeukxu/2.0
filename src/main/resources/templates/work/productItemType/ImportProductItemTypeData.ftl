<#include "/shared/layout.ftl">
<@layout>
    <style>

        #loader {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #loader {
            flex-direction: column-reverse;
            color: #8aaaff;
            font: 2.5em sans-serif;
        }
        progress[value] {
            width: 12.5em;
            height: 0.25em;
            border: none;
            border-radius: 0.125em;
            background: #e6eeff;
        }
        progress[value]::-webkit-progress-bar {
            border: none;
            border-radius: 0.125em;
            background: #e6eeff;
        }
        progress[value]::-webkit-progress-value {
            border: none;
            border-radius: inherit;
            background: linear-gradient(90deg, #8aaaff, #fa8cff);
        }
        progress[value]::-moz-progress-bar {
            border: none;
            border-radius: inherit;
            background: linear-gradient(90deg, #8aaaff, #fa8cff);
        }
        progress[value]::-ms-fill {
            border: none;
            border-radius: inherit;
            background: linear-gradient(90deg, #8aaaff, #fa8cff);
        }
        output:not(:empty) {
            padding-bottom: 1em;
        }
        output:not(:empty):after {
            content: '%';
        }
        .black_overlay{
            display: none;
            position: absolute;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color:#c1bdbd;
            opacity: 0.6;
            z-index:1001;
            -moz-opacity: 0.8;
            filter: alpha(opacity=80);
        }
        .white_content {
            display: none;
            position: absolute;
            top: 25%;
            left: 35%;
            width: 30%;
            height: 30%;
            z-index:1002;
        }
        .white_content_small {
            display: none;
            position: absolute;
            top: 20%;
            left: 30%;
            width: 40%;
            height: 50%;
            border: 16px solid lightblue;
            background-color: white;
            z-index:1002;
            overflow: auto;
        }
    </style>

    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/plupload/plupload.full.min.js"></script>
    <script type="text/javascript" src="/js/common/fileUploaderProductItemType.js"></script>
    <script type="text/javascript" src="/js/common/excelExportOther.js"></script>


    <script type="text/javascript">
        var Code="${Code}";
        var FileName="${FileName}";
        var  AllProductItemType="${AllProductItemType}";
    </script>
    <div id="ReqFile" style="width:100%;height:100%"></div>
    <!--弹出层时背景层DIV-->
    <div id="fade" class="black_overlay"></div>
    <div id="MyDiv" class="white_content">
        <div style="text-align: right; cursor: default; height: 40px;">
        </div>
        <div id="loader">
            <progress id='p' value='60' max='100'></progress>
            <output for='p'></output>
        </div>
    </div>
</@layout>
<@js>
    <script type="text/javascript">
        function init() {
            var k = $('#ReqFile').fileUploader({
                mode: 'Add',
                showConfig: false,
                uploadUrl: '/common/excelImport/selectProductItemTypeXls',
            });
            k.loadFiles({});
        }
        init();
    </script>
</@js>