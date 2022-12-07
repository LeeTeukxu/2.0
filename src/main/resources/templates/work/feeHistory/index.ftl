<#include "/shared/layout.ftl">
<@layout>
    <style type="text/css">
        .New_Button, .Edit_Button, .Delete_Button, .Update_Button, .Cancel_Button {
            font-size: 11px;
            color: #1B3F91;
            font-family: Verdana;
            margin-right: 5px;
        }
    </style>
   <div class="mini-layout" style="width: 100%; height: 100%;overflow:hidden">
       <div region="center" style="overflow:hidden">
           <div class="mini-toolbar">
               <table style="width:100%">
                   <tr>
                       <td style="width:95%">

                       </td>
                       <td style="white-space:nowrap;">
                           <a class="mini-button mini-button-info" id="FeeListHistory_Export"
                              onclick="doExport">导出Excel</a>
                           <a class="mini-button mini-button-danger" id="FeeListHistory_HighQuery"
                              onclick="showHighSearchForm">高级搜索</a>
                       </td>
                   </tr>
               </table>
               <div id="searchForm" style="display:none;padding:5px;">
                   <table style="width:100%;">
                       <tr>
                           <td style="width:6%;padding-left:10px;">缴纳期限：</td>
                           <td style="width:15%;"><input name="yingjiaof_jiaofeijzr" id="yingjiaof_jiaofeijzrA"
                                                         valueindex="1" filtertype="GT" style="width:100%"/></td>
                           <td style="width:6%;padding-left:10px;">到：</td>
                           <td style="width:15%;"><input name="yingjiaof_jiaofeijzr" id="yingjiaof_jiaofeijzrB"
                                                         valueindex="2" filtertype="LT" style="width:100%"/></td>
                           <td style="width:6%;padding-left:10px;">清单名称</td>
                           <td style="width:15%;"><input name="jfqd" class="k-textbox" filtertype="LIKE"
                                                         style="width:100%"/></td>
                           <td style="width:6%;padding-left:10px;">发票抬头：</td>
                           <td style="width:15%;"><input name="invoiceTitle" class="k-textbox" filtertype="LIKE"
                                                         style="width:100%"/></td>
                       </tr>
                       <tr>
                           <td style="width:6%;padding-left:10px;">收据接收人：</td>
                           <td style="width:15%;"><input name="LinkMan" class="k-textbox" filtertype="LIKE"
                                                         style="width:100%"/></td>
                           <td style="width:6%;padding-left:10px;">接收人地址：</td>
                           <td style="width:15%;"><input name="Address" class="k-textbox" filtertype="LIKE"
                                                         style="width:100%"/></td>
                           <td style="width:6%;padding-left:10px;">收件人电话：</td>
                           <td style="width:15%;"><input name="Mobile" class="k-textbox" filtertype="LIKE"
                                                         style="width:100%"/></td>
                           <td style="width:6%;padding-left:10px;">审核意见：</td>
                           <td style="width:15%;"><input name="auditOpinion" class="k-textbox" filtertype="LIKE"
                                                         style="width:100%"/></td>
                       </tr>
                       <tr>
                           <td style="text-align:right;padding-top:5px;padding-right:20px;" colspan="8">
                               <a class="mini-button mini-button-success"
                                  href="javascript:doHightSearch('searchForm');">搜索</a>
                               <a class="mini-button mini-button-danger"
                                  href="javascript:cancelHighSearchForm();">收起</a>
                           </td>
                       </tr>
                   </table>
               </div>
           </div>
           <form id="ExportForm" style="display:none" method="post" action="/Common/ExportExcelService/Export">
               <input type="hidden" id="Data" name="Data"/>
           </form>
           <div id="selectExportColumn" class="mini-window" title="选择导出字段" style="width:420px;height:250px;"
                showshadow="true" showfooter="true"
                footerstyle="text-align: right;margin-right: 25px;" allowresize="true" allowdrag="true">
               <div property="footer">
                   <a class="mini-button mini-button-success"
                      href="javascript:reqExoprt('','已缴费清单列表','已缴费清单列表.xls', 0);">确定</a>
                   <a class="mini-button mini-button-danger" href="javascript:cancelSelectExportColumn();">取消</a>
                   <a class="mini-button mini-button-info" href="javascript:opsSelectExportColumn();">反选</a>
               </div>
           </div>
           <div class="mini-fit">
               <div id="FeeList_datagrid" class="mini-datagrid" style="width: 100%; height: 100%;"
                    onshowrowdetail="onShowFeeListDetail"
                    allowresize="true" url="/Fee/FeeItemHistory/GetData" pagesize="20"
                    sizelist="[5,10,20,50,100,150,200,500,1000]" sortfield="DrawTime" sortorder="desc" autoload="true">
                   <div property="columns">
                       <div type="indexcolumn"></div>
                       <div type="expandcolumn" width="40">#</div>
                       <div field="jfqd_id" width="120" headeralign="center" allowsort="true">
                           清单编号
                       </div>
                       <div field="jfqd" name="jfqd" width="120" headeralign="center" allowsort="true">
                           清单名称
                       </div>
                       <div field="yingjiaof_shijiyjje" width="60" headeralign="center" align="right" allowsort="true">
                           缴费金额
                       </div>
                       <div field="rengong_jdje" width="100" headeralign="center" align="right" allowsort="true">
                           人工校对金额
                       </div>
                       <div field="yingjiaof_jiaofeijzr" width="80" headeralign="center" align="center"
                            allowsort="true">
                           缴纳期限
                       </div>

                       <div field="invoiceTitle" width="180" headeralign="center" allowsort="true">
                           发票抬头
                       </div>
                       <div field="LinkMan" textname="LinkName" name="LinkName" width="120" headeralign="center"
                            allowsort="true">
                           收据接收人
                       </div>
                       <div field="Address" width="200" headeralign="center" allowsort="true">
                           接收人地址
                       </div>
                       <div field="PostCode" width="70" headeralign="center" allowsort="true">
                           收件人邮编
                       </div>
                       <div field="DrawEmp" width="100" headeralign="center" allowsort="true">
                           财务操作
                       </div>
                       <div field="DrawTime" width="120" headeralign="center" allowsort="true">
                           操作日期
                       </div>
                       <div field="remark" width="100" headeralign="center" allowsort="true">
                           备注
                       </div>
                   </div>
               </div>
               <div id="detailGrid_Form" style="display:none;">
                   <div id="FeeListDetail_datagrid" class="mini-datagrid" style="width:100%;"
                        url="/Fee/FeeList/FeeItemList">
                       <div property="columns">
                           <div type="indexcolumn"></div>
                           <div field="yingjiaof_jiaofeijzr" name="yingjiaof_jiaofeijzr" width="100"
                                headeralign="center" align="center">
                               缴纳期限
                           </div>
                           <div field="yingjiaofydm" name="yingjiaofydm" width="180" headeralign="center">
                               费用项目
                           </div>
                           <div field="yingjiaof_shijiyjje" name="yingjiaof_shijiyjje" width="60" headeralign="center"
                                align="right">
                               金额
                           </div>
                           <div field="rengong_jdje" name="rengong_jdje" width="100" headeralign="center" align="right">
                               人工校对金额
                           </div>
                           <div field="shenqingh" name="shenqingh" width="120" headeralign="center"
                                renderer="onZhanlihao">
                               专利申请号
                           </div>
                           <div field="zhuanlimc" name="zhuanlimc" width="200" headeralign="center">
                               专利名称
                           </div>
                           <div field="zhuanlilxText" width="100" headeralign="center">
                               专利类型
                           </div>
                           <div field="shenqingrxm" width="100" headeralign="center">
                               申请人信息
                           </div>
                           <div width="240" headeralign="center" field="NEIBUBH">内部编号</div>
                           <div width="120" headeralign="center" field="KH">归属客户</div>
                           <div width="100" headeralign="center" field="YW">销售维护人</div>
                           <div width="100" headeralign="center" field="DL">代理责任人</div>
                           <div width="100" headeralign="center" field="LC">流程责任人</div>
                       </div>
                   </div>
               </div>
           </div>
       </div>
   </div>
</@layout>