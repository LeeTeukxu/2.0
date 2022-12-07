mini.parse();
var targetGrid = mini.get('datagrid1');
var postUrl = '/common/excelImport/selectXls';
var existsValue = '', downForm = null, dataText = null, targetRecord = null;
$(function () {
    downForm = $('#DownFileForm');
    dataText = mini.get('DataText');
    var xhr;
    $('#UploadXls').fileupload({
        url: postUrl,
        autoUpload: true,
        maxNumberOfFiles:1,
        dataType:'json',
        add:function(e,data){
            var fileObj = document.getElementById("UploadXls").files[0]; // js 获取文件对象
            //var fileObj=data.files[0];
            var url = postUrl; // 接收上传文件的后台地址

            var form = new FormData(); // FormData 对象
            form.append("file", fileObj); // 文件对象

            xhr = new XMLHttpRequest(); // XMLHttpRequest 对象
            xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
            xhr.onload = uploadComplete; //请求完成
            xhr.onerror = uploadFailed; //请求失败

            //xhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
            xhr.upload.onloadstart = function(){//上传开始执行方法
                ot = new Date().getTime();  //设置上传开始时间
                oloaded = 0;//设置上传开始时，以上传的文件大小为0
            };

            xhr.send(form); //开始上传，发送form数据
        },
        done: function (e, result) {
            var fileObj = document.getElementById("UploadXls").files[0]; // js 获取文件对象
        },
        change: function (e, data) {
            var fileObj = document.getElementById("UploadXls").files[0]; // js 获取文件对象
            // var arg={};
            // var a=$("#UploadXls")[0];
            // $.post(postUrl,arg,
            //     function (text) {
            //         var res=mini.encode(text);
            //         if (res.success){
            //             var data=res.success||{};
            //         }else {
            //             mini.alert("选择文件失败！请重新选择！");
            //         }
            //     })

        }
    });
});
//上传成功响应
function uploadComplete(evt) {
    //服务断接收完文件返回的结果

    var data = JSON.parse(evt.target.responseText);
    if(data.success) {
        alert("上传成功！");
    }else{
        alert("上传失败！");
    }

}
//上传失败
function uploadFailed(evt) {
    alert("上传失败！");
}
//取消上传
function cancleUploadFile(){
    xhr.abort();
}

