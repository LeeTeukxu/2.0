package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ISendEmailService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import javax.xml.soap.Text;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PantentSendMailTask {
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    TZSRepository tzsRepository;
    @Autowired
    patentInfoPermissionRepository patentInfoPermissionRepository;
    @Autowired
    ISendEmailService sendEmailService;
    @Autowired
    tbDefaultMailRepository defaultMailRepository;
    @Autowired
    tbClientLinkersRepository clientLinkersRepository;
    @Autowired
    MailConfigRepository mailConfigRepository;
    @Autowired
    tbLoginUserRepository loginUserRepository;
    @Autowired
    tbEmployeeRepository employeeRepository;
    @Autowired
    tbTZSSendResultRepository tzsSendResultRepository;
    @Autowired
    companyConfigRepository companyConfigRepository;
    @Autowired
    tzsEmailRecordRepository tzsEmailRecordRepository;
    @Autowired
    tbClientRepository clientRepository;
    @Autowired
    tzsPeriodConfigRepository tzsPeriodConfigRepository;
    @Autowired
    tbTZSSendTypeRepository tzsSendTypeRepository;

    Logger logger = LoggerFactory.getLogger(PantentSendMailTask.class);

    //每天中午12点和下午5点执行一次
//    @Scheduled(cron = "0 0 12,17 * * ?")
//    @Scheduled(cron = "0 */2 * * * ?")
    public void process() {
        try {

            FTPUtil util = new FTPUtil();
            List<String> companyIDS = userMapper.getCompanyList();
            List<Map<String, Object>> listMap = new ArrayList<>();
            if (util.connect()) {
                for (String CompanyID : companyIDS) {
//                    if (CompanyID.equals("0011")) {
                        changeContext(CompanyID);
                        Map<String, Object> map;
                        List<patentInfoPermission> listPer = patentInfoPermissionRepository.findAll();
                        List<tbDefaultMail> listDefaultMail = defaultMailRepository.findAll();
                        List<tbClientLinkers> listClientLinkers = clientLinkersRepository.findAll();
                        List<TZS> listTZS = tzsRepository.findAll();
                        List<tbLoginUser> listLoginUser = loginUserRepository.findAll();
                        List<tbEmployee> listEmployee = employeeRepository.findAll();
                        List<tbTZSSendResult> listAllTZSSendResult = tzsSendResultRepository.findAll();
                        List<TZS> listTZSPeriodConfig = tzsRepository.findTZSMCANDBH();
                        List<tbTZSSendType> listTZSSendType = tzsSendTypeRepository.findAll();
                        if (listTZS.size() > 0) {
                            for (int i = 0; i < listTZS.size(); i++) {
                                TZS tzs = listTZS.get(i);
                                //更新时间不能为空
                                if (tzs.gettUploadTime() != null) {
                                    boolean istoday = IsToday(tzs.getTUploadTime().getTime(), "yyyy-MM-dd");
                                    //已发送的记录不能重复发送
                                    Optional<tbTZSSendResult> findOne = listAllTZSSendResult.stream().filter(f -> f.getTongzhisbh() != null).filter(f -> f.getTongzhisbh().equals(tzs.getTongzhisbh())).findFirst();
                                    if (!findOne.isPresent()) {
                                        //更新时间必须为当天的数据
                                        if (istoday == true) {
                                            logger.info("CompanyID：" + CompanyID + "；通知书编号为：" + tzs.getTongzhisbh() + "的TUploadTime为：" + tzs.getTUploadTime());
                                            //设置了通知书发送类型的才能发送邮件
                                            if (tzs.getTongzhismc() != null) {
                                                Optional<TZS> findTZSPeriodConfig = listTZSPeriodConfig.stream().filter(f -> f.getTongzhismc().equals(tzs.getTongzhismc())).findFirst();
                                                if (findTZSPeriodConfig.isPresent()) {
                                                    Optional<tbTZSSendType> findTZSSendType = listTZSSendType.stream().filter(x -> x.getTzsPeriodId().equals(findTZSPeriodConfig.get().getTongzhisbh())).findFirst();
                                                    logger.info("CompanyID：" + CompanyID + "；设置的发送通知书类型编号为：" + findTZSPeriodConfig.get().getTongzhisbh());
                                                    if (findTZSSendType.isPresent()) {
                                                        map = new HashMap<>();
                                                        String uploadPath = tzs.getTzspath();
                                                        if (StringUtils.isEmpty(uploadPath)) continue;
//                            String savePath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
//                            util.download(uploadPath,savePath);

                                                        map.put("FilePath", uploadPath);
                                                        //客户ID
                                                        Optional<patentInfoPermission> findKHID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("KH")).findFirst();
                                                        //业务人员ID
                                                        Optional<patentInfoPermission> findYWID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("YW")).findFirst();
                                                        //流程人员ID
                                                        Optional<patentInfoPermission> findLCID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("LC")).findFirst();
                                                        //技术人员ID
                                                        Optional<patentInfoPermission> findJSID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("JS")).findFirst();
                                                        //如果客户存在
                                                        if (findKHID.isPresent()) {
                                                            patentInfoPermission missionkh = findKHID.get();
                                                            map.put("KHID", missionkh.getUserid());
                                                            //根据客户查找默认联系人邮箱
                                                            List<tbDefaultMail> findDefaultMail = listDefaultMail.stream().filter(f -> f.getClientId().equals(missionkh.getUserid())).collect(Collectors.toList());
                                                            logger.info("CompanyID：" + CompanyID + "；设置了默认邮箱的客户联系人为：" + missionkh.getUserid() + "。默认邮箱数量：" + findDefaultMail.size());
                                                            //如果客户的联系人设置了默认邮箱
                                                            if (findDefaultMail.size() > 0) {
                                                                for (tbDefaultMail defaultMail : findDefaultMail) {
                                                                    //根据默认邮箱的客户ID获取联系人信息
                                                                    Optional<tbClientLinkers> findClientLinkers = listClientLinkers.stream().filter(f -> f.getLinkID().equals(defaultMail.getLinkersId())).findFirst();
                                                                    //如果联系人存在
                                                                    if (findClientLinkers.isPresent()) {
                                                                        //添加联系人的邮箱到邮箱列表中
                                                                        tbClientLinkers clientLinkers = findClientLinkers.get();
                                                                        logger.info("CompanyID：" + CompanyID + "；设置的默认邮箱地址为：" + clientLinkers.getEmail());
                                                                        map.put("MailAddress", clientLinkers.getEmail());

                                                                        //如果业务人员存在获取业务人员的邮箱
                                                                        if (findYWID.isPresent()) {
                                                                            patentInfoPermission missionyw = findYWID.get();
                                                                            Optional<tbLoginUser> findLoginUser = listLoginUser.stream().filter(f -> f.getUserId().equals(missionyw.getUserid())).findFirst();
                                                                            if (findLoginUser.isPresent()) {
                                                                                tbLoginUser loginUser = findLoginUser.get();
                                                                                Optional<tbEmployee> findEmployee = listEmployee.stream().filter(f -> f.getEmpId().equals(loginUser.getEmpId())).findFirst();
                                                                                if (findEmployee.isPresent()) {
                                                                                    tbEmployee employee = findEmployee.get();
                                                                                    map.put("YWMAIL", employee.getEmail());
                                                                                }
                                                                            }
                                                                        }
                                                                        //如果流程人员存在获取流程人员的邮箱
                                                                        if (findLCID.isPresent()) {
                                                                            patentInfoPermission missionlc = findLCID.get();
                                                                            Optional<tbLoginUser> findLoginUser = listLoginUser.stream().filter(f -> f.getUserId().equals(missionlc.getUserid())).findFirst();
                                                                            if (findLoginUser.isPresent()) {
                                                                                tbLoginUser loginUser = findLoginUser.get();
                                                                                Optional<tbEmployee> findEmployee = listEmployee.stream().filter(f -> f.getEmpId().equals(loginUser.getEmpId())).findFirst();
                                                                                if (findEmployee.isPresent()) {
                                                                                    tbEmployee employee = findEmployee.get();
                                                                                    map.put("LCMAIL", employee.getEmail());
                                                                                }
                                                                            }
                                                                        }
                                                                        //如果技术人员存在获取技术人员的邮箱
                                                                        if (findJSID.isPresent()) {
                                                                            patentInfoPermission missionjs = findJSID.get();
                                                                            Optional<tbLoginUser> findLoginUser = listLoginUser.stream().filter(f -> f.getUserId().equals(missionjs.getUserid())).findFirst();
                                                                            if (findLoginUser.isPresent()) {
                                                                                tbLoginUser loginUser = findLoginUser.get();
                                                                                Optional<tbEmployee> findEmployee = listEmployee.stream().filter(f -> f.getEmpId().equals(loginUser.getEmpId())).findFirst();
                                                                                if (findEmployee.isPresent()) {
                                                                                    tbEmployee employee = findEmployee.get();
                                                                                    map.put("JSMAIL", employee.getEmail());
                                                                                }
                                                                            }
                                                                        }
                                                                    } else map.put("MailAddress", "");
                                                                }
                                                            } else map.put("MailAddress", "");
                                                        }

                                                        map.put("FAMINGMC", tzs.getFamingmc());
                                                        map.put("TONGZHISMC", tzs.getTongzhismc());
                                                        map.put("TONGZHISBH", tzs.getTongzhisbh());
                                                        map.put("SHENQINGH", tzs.getShenqingh());

                                                        //根据登陆用户的公司ID获取邮箱设置信息
                                                        String MailContent = "";
                                                        MailContent += "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tzs.getShenqingh() + "-" + tzs.getFamingmc() + "-" + tzs.getTongzhismc() + "</p>";
                                                        map.put("MailContent", MailContent);
                                                        Optional<MailConfig> findMailConfig = mailConfigRepository.findAllByCompanyCode(CompanyID);
                                                        if (findMailConfig.isPresent()) {
                                                            map.put("MailConfig", findMailConfig.get());
                                                            listMap.add(map);
                                                        } else {
                                                            Optional<companyConfig> findCompanyConfig = companyConfigRepository.findAllByCompanyCode(CompanyID);
                                                            if (findCompanyConfig.isPresent()) {
                                                                MailConfig config = new MailConfig();
                                                                config.setMemo(findCompanyConfig.get().getMemo());
                                                                config.setRange(findCompanyConfig.get().getRange());
                                                                config.setPhone(findCompanyConfig.get().getPhone());
                                                                config.setAddress(findCompanyConfig.get().getAddress());
                                                                map.put("MailConfig", config);
                                                                listMap.add(map);
                                                            }
                                                        }
                                                    } else {
                                                        logger.info("CompanyID：" + CompanyID + "；x未设置通知书发送分类");
                                                        tbTZSSendResult sendResult = new tbTZSSendResult();
                                                        sendResult.setSendTime(new Date());
                                                        sendResult.setSendResult("未设置通知书发送分类！");
                                                        tzsSendResultRepository.save(sendResult);
                                                    }
                                                } else {
                                                    logger.info("CompanyID：" + CompanyID + "；x未设置通知书发送分类");
                                                    tbTZSSendResult sendResult = new tbTZSSendResult();
                                                    sendResult.setSendTime(new Date());
                                                    sendResult.setSendResult("未设置通知书发送分类！");
                                                    tzsSendResultRepository.save(sendResult);
                                                }
                                            } else {
                                                logger.info("CompanyID：" + CompanyID + "；通知书表中没有通知书名称");
                                                tbTZSSendResult sendResult = new tbTZSSendResult();
                                                sendResult.setSendTime(new Date());
                                                sendResult.setSendResult("通知书表中没有通知书名称！");
                                                tzsSendResultRepository.save(sendResult);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (listMap.size() > 0) {
                            List<TextAndValue> listAtt;
                            //相同的客户ID分为一组
                            Map<Integer, List<Map<String, Object>>> mapList = listMap.stream().filter(x -> x.get("KHID") != null).collect(Collectors.groupingBy((Map m) -> (Integer) m.get("KHID")));
                            logger.info("CompanyID：" + CompanyID + "；客户ID数量：" + mapList.size());

                            //去除重复的客户ID
                            List<Integer> listKHID = new ArrayList<>();
                            for (Map<String, Object> maps : listMap) {
                                if (maps.get("KHID") != null) {
                                    listKHID.add(Integer.parseInt(maps.get("KHID").toString()));
                                }
                            }
                            List DistinctKHIDS = (List) listKHID.stream().distinct().collect(Collectors.toList());

                            if (DistinctKHIDS.size() > 0) {
                                for (int i = 0; i < DistinctKHIDS.size(); i++) {
                                    listAtt = new ArrayList<>();
                                    EmailContent content = new EmailContent();

                                    //循环添加附件
                                    List<Map<String, Object>> listContentGroup = mapList.get(DistinctKHIDS.get(i));
                                    String KHMail = "";
                                    List<String> listMail = new ArrayList<>();
                                    List<String> listMailContent = new ArrayList<>();
                                    List<String> listXSMAIL = new ArrayList<>();
                                    List<String> listLCMAIL = new ArrayList<>();
                                    List<String> listJSMAIL = new ArrayList<>();
                                    List<Map<String, Object>> listBHAndSQH = new ArrayList<>();
                                    MailConfig mailConfig = new MailConfig();
                                    for (Map<String, Object> mapContentGroup : listContentGroup) {
                                        TextAndValue TxtAtt = new TextAndValue();
                                        TxtAtt.setText(mapContentGroup.get("FAMINGMC").toString() + "(" + mapContentGroup.get("TONGZHISMC") + ").zip");
                                        TxtAtt.setValue(mapContentGroup.get("FilePath").toString());
                                        listAtt.add(TxtAtt);
                                        content.setAttachments(listAtt);

                                        KHMail = mapContentGroup.get("MailAddress").toString();
                                        listMailContent.add(mapContentGroup.get("MailContent").toString());
                                        mailConfig = (MailConfig) mapContentGroup.get("MailConfig");

                                        if (mapContentGroup.get("YWMAIL") != null) {
                                            listXSMAIL.add(mapContentGroup.get("YWMAIL").toString());
                                        }

                                        if (mapContentGroup.get("LCMAIL") != null) {
                                            listLCMAIL.add(mapContentGroup.get("LCMAIL").toString());
                                        }

                                        if (mapContentGroup.get("JSMAIL") != null) {
                                            listXSMAIL.add(mapContentGroup.get("JSMAIL").toString());
                                        }

                                        Map<String, Object> mapBHAndSQHS = new HashMap<>();
                                        mapBHAndSQHS.put("TONGZHISBH", mapContentGroup.get("TONGZHISBH").toString());
                                        mapBHAndSQHS.put("SHENQINGH", mapContentGroup.get("SHENQINGH").toString());
                                        mapBHAndSQHS.put("MAILS", mapContentGroup.get("MailAddress"));
                                        mapBHAndSQHS.put("YWMAILS", mapContentGroup.get("YWMAIL"));
                                        mapBHAndSQHS.put("LCMAILS", mapContentGroup.get("LCMAIL"));
                                        mapBHAndSQHS.put("JSMAILS", mapContentGroup.get("JSMAIL"));
                                        mapBHAndSQHS.put("KHID", mapContentGroup.get("KHID"));
                                        listBHAndSQH.add(mapBHAndSQHS);
                                    }


                                    List<String> DistinctXSMAIL = listXSMAIL.stream().distinct().collect(Collectors.toList());
                                    List<String> DistinctLCMAIL = listLCMAIL.stream().distinct().collect(Collectors.toList());
                                    List<String> DistinctJSMAIL = listJSMAIL.stream().distinct().collect(Collectors.toList());

                                    List<TextAndValue> listReceive = new ArrayList<>();
                                    //循环添加接收邮箱(没找到添加到接收人的方法，暂时所有的邮箱都添加到抄送人)
                                    if (!KHMail.equals("")) {
                                        TextAndValue TxtKHReceive = new TextAndValue();
                                        TxtKHReceive.setText(KHMail);
                                        TxtKHReceive.setValue(KHMail);
                                        listReceive.add(TxtKHReceive);

                                        if (DistinctXSMAIL.size() > 0) {
                                            for (String XSMAIL : DistinctXSMAIL) {
                                                TextAndValue TxtReceive = new TextAndValue();
                                                TxtReceive.setText(XSMAIL);
                                                TxtReceive.setValue(XSMAIL);
                                                listReceive.add(TxtReceive);
                                            }
                                        }

                                        if (DistinctLCMAIL.size() > 0) {
                                            for (String LCMAIL : DistinctLCMAIL) {
                                                TextAndValue TxtReceive = new TextAndValue();
                                                TxtReceive.setText(LCMAIL);
                                                TxtReceive.setValue(LCMAIL);
                                                listReceive.add(TxtReceive);
                                            }
                                        }

                                        if (DistinctJSMAIL.size() > 0) {
                                            for (String JSMAIL : DistinctJSMAIL) {
                                                TextAndValue TxtReceive = new TextAndValue();
                                                TxtReceive.setValue(JSMAIL);
                                                TxtReceive.setValue(JSMAIL);
                                                listReceive.add(TxtReceive);
                                            }
                                        }
                                    }

                                    if (listReceive.size() > 0) {
                                        TextAndValue TxtReceive = new TextAndValue();
                                        TxtReceive.setValue("454285760@qq.com");
                                        TxtReceive.setValue("454285760@qq.com");
                                        List<TextAndValue> res = new ArrayList<>();
                                        res.add(TxtReceive);
                                        content.setReceAddress(res);
                                    }

                                    StringBuilder builder = new StringBuilder();
                                    builder.append("<p>尊敬的客户，您好:</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;感谢您对我公司的信任和支持，本次给您发送的文件清单如下：</p>");
                                    //邮件内容中循环展示附件列表
                                    for (String strMailContent : listMailContent) {
                                        builder.append(strMailContent);
                                    }
                                    builder.append("</br></br></br>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + mailConfig.getMemo() + "</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经营范围：" + mailConfig.getRange() + "</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总部地址：" + mailConfig.getAddress() + "</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;联系电话：" + mailConfig.getPhone() + "</p>");
                                    content.setContent(builder.toString());
                                    content.setSubject(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-请查收你的专利通知书");


                                    try {
                                        List<tbTZSSendResult> listTZSSendResult = new ArrayList<>();
                                        List<tzsEmailRecord> listTZSEmailRecord = new ArrayList<>();
                                        logger.info("CompanyID：" + CompanyID + "；KHMail：" + KHMail);
                                        String MS = "";
                                        if (!KHMail.equals("")) {
                                            for (Map<String, Object> mapBHAndSQH : listBHAndSQH) {
                                                tbTZSSendResult sendResult = new tbTZSSendResult();
                                                sendResult.setTongzhisbh(mapBHAndSQH.get("TONGZHISBH").toString());
                                                sendResult.setShenqingh(mapBHAndSQH.get("SHENQINGH").toString());
                                                sendResult.setSendTime(new Date());
                                                sendResult.setSendResult("Success");
                                                listTZSSendResult.add(sendResult);

                                                tzsEmailRecord tzsEmailRecord = new tzsEmailRecord();
                                                tzsEmailRecord.setShenqingh(mapBHAndSQH.get("SHENQINGH").toString());
                                                tzsEmailRecord.setTongzhisbh(mapBHAndSQH.get("TONGZHISBH").toString());
                                                String Mails = mapBHAndSQH.get("MAILS").toString();
                                                if (mapBHAndSQH.get("YWMAILS") != null) {
                                                    Mails += "," + mapBHAndSQH.get("YWMAILS").toString();
                                                }
                                                if (mapBHAndSQH.get("JSMAILS") != null) {
                                                    Mails += "," + mapBHAndSQH.get("JSMAILS").toString();
                                                }
                                                if (mapBHAndSQH.get("LCMAILS") != null) {
                                                    Mails += "," + mapBHAndSQH.get("LCMAILS").toString();
                                                }
                                                String M = "sujuelfblue@sina.com";
                                                tzsEmailRecord.setClient(M);
                                                tzsEmailRecord.setEmail(M);
                                                tzsEmailRecord.setSendUserName("系统管理员");
                                                tzsEmailRecord.setSendTime(new Date());
                                                listTZSEmailRecord.add(tzsEmailRecord);
                                                MS += Mails + "；";
                                            }
                                            for (tzsEmailRecord er : listTZSEmailRecord) {
                                                logger.info("CompanyID：" + CompanyID + "；邮件地址：" + MS + "申请号：" + er.getShenqingh() + "通知书编号：" + er.getTongzhisbh());
                                            }
//                                        tzsEmailRecordRepository.saveAll(listTZSEmailRecord);
//                                        tzsSendResultRepository.saveAll(listTZSSendResult);
//
                                            sendEmailService.sendPantentEmailByContent(content);
                                        } else {
                                            StringBuilder SBTONGZHISBH = new StringBuilder();
                                            String SBSHENQINGH = "";
                                            String KHID = "";
                                            for (Map<String, Object> mapBHAndSQH : listBHAndSQH) {
                                                SBTONGZHISBH.append(mapBHAndSQH.get("TONGZHISBH").toString() + "，");
                                                SBSHENQINGH = mapBHAndSQH.get("SHENQINGH").toString();
                                                KHID = mapBHAndSQH.get("KHID").toString();
                                            }

                                            tbTZSSendResult result = new tbTZSSendResult();
//                                    result.setTongzhisbh(SBTONGZHISBH.toString());
                                            result.setShenqingh(SBSHENQINGH);
                                            result.setSendTime(new Date());
//                                        Integer ClientID = Integer.parseInt(DistinctKHIDS.get(i).toString());
//                                        Optional<tbClient> findClient = listClient.stream().filter(f -> f.getClientID().equals(ClientID)).findFirst();
                                            logger.info("公司ID为:" + CompanyID + "(客户ID为:" + KHID + "),(申请号为:" + SBSHENQINGH + "),(通知书编号为:" + SBTONGZHISBH.substring(0, SBTONGZHISBH.toString().length() - 1) + ")。未设置发送邮件的默认邮箱，无法发送邮件");
                                            result.setSendResult("公司ID为:" + CompanyID + "(客户ID为:" + KHID + "),(申请号为:" + SBSHENQINGH + "),(通知书编号为:" + SBTONGZHISBH.substring(0, SBTONGZHISBH.toString().length() - 1) + ")。未设置发送邮件的默认邮箱，无法发送邮件");
                                            tzsSendResultRepository.save(result);
                                        }
                                    } catch (Exception ex) {
                                        logger.info("CompanyID：" + CompanyID + "；发送邮件信息异常：" + ex.getMessage());
                                        List<tbTZSSendResult> listTZSSendResult = new ArrayList<>();
                                        for (Map<String, Object> mapBHAndSQH : listBHAndSQH) {
                                            tbTZSSendResult sendResult = new tbTZSSendResult();
                                            sendResult.setTongzhisbh(mapBHAndSQH.get("TONGZHISBH").toString());
                                            sendResult.setShenqingh(mapBHAndSQH.get("SHENQINGH").toString());
                                            sendResult.setSendTime(new Date());
                                            sendResult.setSendResult(ex.getMessage());
                                            listTZSSendResult.add(sendResult);
                                        }
                                        tzsSendResultRepository.saveAll(listTZSSendResult);
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                        CompanyContext.set(null);
//                    }
                }
            }
        } catch (Exception ax) {
            ax.printStackTrace();
        }
    }

    private boolean IsToday(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);
        String now = sdf.format(new Date());
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    private void changeContext(String companyId) {
        LoginUserInfo info = new LoginUserInfo();
        info.setUserName("aa");
        info.setUserId("aa");
        info.setCompanyId(companyId);
        CompanyContext.set(info);
    }
}
