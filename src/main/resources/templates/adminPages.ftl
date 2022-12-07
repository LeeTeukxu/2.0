<#include "/shared/layout.ftl">
<@layout>
    <div class="mini-fit">1
        <table style="width:100%;table-layout:fixed;">
            <tr>
                <td style="width:50%">
                    <div class="mini-panel" title="在线用户" style="width:100%;height:310px">
                        <div class="mini-datagrid" style="width:100%;height:100%;padding:0px;margin:0px" id="grid1"
                             url="/admin/getOnlineUsers" pageSize="7" autoload="true">
                            <div property="columns" >
                                <div type="indexcolumn"></div>
                                <div field="Name" width="100" align="center" headerAlign="center">用户名称</div>
                                <div field="Company" width="150" align="center" headerAlign="center"
                                     allowSort="true">公司名称
                                </div>
                                <div field="LoginTime" width="100" dataType="date" dateFormat="yyyy-MM-dd
                                HH:mm:ss" align="center" headerAlign="center" allowSort="true">登录时间</div>
                                <div field="Expire" width="60" align="center" headerAlign="center"
                                     allowSort="true">失效时间</div>
                            </div>
                        </div>
                    </div>
                </td>
                <td style="width:50%">
                    <div class="mini-panel" style="width:100%;height:310px" title="运行状态">
                        <div class="mini-datagrid" style="width:100%;height:100%;padding:0px;margin:0px"
                             sortField="curTime" id="grid2"
                             sortOrder="desc" url="/admin/getUsedTime" pageSize="7" autoload="false">
                            <div property="columns" >
                                <div type="indexcolumn"></div>
                                <div field="curTime" width="100" dataType="date" dateFormat="yyyy-MM-dd
                                HH:mm:ss" align="center" headerAlign="center" allowSort="true">发生时间</div>
                                <div field="url" width="150" align="center" headerAlign="center">执行服务</div>
                                <div field="usedTime" width="80" align="center" headerAlign="center" allowSort="true">用时
                                    (毫秒)
                                </div>
                                <div field="userName" width="60" align="center" headerAlign="center" allowSort="true">用户</div>
                                <div field="companyName" width="120" align="center" headerAlign="center">公司</div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="mini-panel" title="系统错误日志" style="width:100%;height:400px">
                        <div class="mini-fit">
                            <div class="mini-datagrid" id="grid1" style="width:100%;height:100%"
                                 url="/admin/getException"
                                 sortField="createTime" allowCellEdit="true" allowCellSelect="true"
                                 sortOrder="desc" autoload="false">
                                <div property="columns">
                                    <div type="indexcolumn" align="center" headerAlign="center">#</div>
                                    <div field="url" width="150" align="center" headerAlign="center">请求路径</div>
                                    <div field="parameters" width="120" align="center" headerAlign="center">附加参数</div>
                                    <div field="message" width="120" align="center" headerAlign="center">出错信息
                                        <input property="editor" class="mini-buttonedit" onbuttonclick="showMessage"/>
                                    </div>
                                    <div field="detail" width="120" align="center" headerAlign="center">所在文件及行数</div>
                                    <div field="companyName" width="150" align="center" headerAlign="center">公司名称</div>
                                    <div field="userName" width="80" align="center" headerAlign="center">操作人</div>
                                    <div field="createTime" width="80" align="center" headerAlign="center" dataType="date"
                                         dateFormat="yyyy-MM-dd HH:mm:ss">发生时间</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <script type="text/javascript">
        mini.parse();
        var grid1=mini.get('grid1');
        var grid2=mini.get('grid2');
        function showMessage(sender){
            var row=grid1.getEditorOwnerRow(sender.source);
            if(row){
                var msg=row["message"] ||"";
                if(msg){
                    mini.showMessageBox({
                        height:360,
                        width:900,
                        html:'<div class="mini-fit"><textarea class="mini-textarea" style="width:100%;height:240px" ' +
                            'id="GGG"></textarea></div>',
                        buttons:['ok','no'],
                        title:'异常详细信息'
                    });
                    mini.parse();
                    mini.get('GGG').setValue(msg);
                }
            }
        }
        function doLoad(){
            grid1.load();
            grid2.load();
        }
        doLoad();
    </script>
</@layout>