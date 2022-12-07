<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-layout" style="width:100%;height:100%">
        <div region="west" title="文件列表" width="240">
            <ul class="mini-tree" style="width:100%;height:100%" valueField="FID" parentField="PID"
                expandonload="true" onnodeclick="onClick"
                textField="Name" resultAsTree="false" url="/cpc/getFileTree?MainID=${MainID}"></ul>
        </div>
        <div region="center">
            <iframe style="width:100%;height:100%" frameborder="0" id="docBody"
                    src=""></iframe>
        </div>
    </div>
    <script type="text/javascript">
        mini.parse();
        var mainId="${MainID}";
        function onClick(e){
            var node=e.node;
            var fileName=node.Name;
            var isLeaf=e.isLeaf;
            if(isLeaf && name){
                document.getElementById("docBody").src="/cpc/getFileContent?MainID=${MainID}&FileName="+fileName;
            }
        }
    </script>
</@layout>