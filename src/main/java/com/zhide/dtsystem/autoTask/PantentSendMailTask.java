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

    //????????????12????????????5???????????????
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
                                //????????????????????????
                                if (tzs.gettUploadTime() != null) {
                                    boolean istoday = IsToday(tzs.getTUploadTime().getTime(), "yyyy-MM-dd");
                                    //????????????????????????????????????
                                    Optional<tbTZSSendResult> findOne = listAllTZSSendResult.stream().filter(f -> f.getTongzhisbh() != null).filter(f -> f.getTongzhisbh().equals(tzs.getTongzhisbh())).findFirst();
                                    if (!findOne.isPresent()) {
                                        //????????????????????????????????????
                                        if (istoday == true) {
                                            logger.info("CompanyID???" + CompanyID + "????????????????????????" + tzs.getTongzhisbh() + "???TUploadTime??????" + tzs.getTUploadTime());
                                            //???????????????????????????????????????????????????
                                            if (tzs.getTongzhismc() != null) {
                                                Optional<TZS> findTZSPeriodConfig = listTZSPeriodConfig.stream().filter(f -> f.getTongzhismc().equals(tzs.getTongzhismc())).findFirst();
                                                if (findTZSPeriodConfig.isPresent()) {
                                                    Optional<tbTZSSendType> findTZSSendType = listTZSSendType.stream().filter(x -> x.getTzsPeriodId().equals(findTZSPeriodConfig.get().getTongzhisbh())).findFirst();
                                                    logger.info("CompanyID???" + CompanyID + "?????????????????????????????????????????????" + findTZSPeriodConfig.get().getTongzhisbh());
                                                    if (findTZSSendType.isPresent()) {
                                                        map = new HashMap<>();
                                                        String uploadPath = tzs.getTzspath();
                                                        if (StringUtils.isEmpty(uploadPath)) continue;
//                            String savePath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
//                            util.download(uploadPath,savePath);

                                                        map.put("FilePath", uploadPath);
                                                        //??????ID
                                                        Optional<patentInfoPermission> findKHID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("KH")).findFirst();
                                                        //????????????ID
                                                        Optional<patentInfoPermission> findYWID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("YW")).findFirst();
                                                        //????????????ID
                                                        Optional<patentInfoPermission> findLCID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("LC")).findFirst();
                                                        //????????????ID
                                                        Optional<patentInfoPermission> findJSID = listPer.stream().filter(f -> f.getShenqingh().equals(tzs.getShenqingh())).filter(f -> f.getUsertype().equals("JS")).findFirst();
                                                        //??????????????????
                                                        if (findKHID.isPresent()) {
                                                            patentInfoPermission missionkh = findKHID.get();
                                                            map.put("KHID", missionkh.getUserid());
                                                            //???????????????????????????????????????
                                                            List<tbDefaultMail> findDefaultMail = listDefaultMail.stream().filter(f -> f.getClientId().equals(missionkh.getUserid())).collect(Collectors.toList());
                                                            logger.info("CompanyID???" + CompanyID + "????????????????????????????????????????????????" + missionkh.getUserid() + "????????????????????????" + findDefaultMail.size());
                                                            //?????????????????????????????????????????????
                                                            if (findDefaultMail.size() > 0) {
                                                                for (tbDefaultMail defaultMail : findDefaultMail) {
                                                                    //???????????????????????????ID?????????????????????
                                                                    Optional<tbClientLinkers> findClientLinkers = listClientLinkers.stream().filter(f -> f.getLinkID().equals(defaultMail.getLinkersId())).findFirst();
                                                                    //?????????????????????
                                                                    if (findClientLinkers.isPresent()) {
                                                                        //??????????????????????????????????????????
                                                                        tbClientLinkers clientLinkers = findClientLinkers.get();
                                                                        logger.info("CompanyID???" + CompanyID + "????????????????????????????????????" + clientLinkers.getEmail());
                                                                        map.put("MailAddress", clientLinkers.getEmail());

                                                                        //???????????????????????????????????????????????????
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
                                                                        //???????????????????????????????????????????????????
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
                                                                        //???????????????????????????????????????????????????
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

                                                        //???????????????????????????ID????????????????????????
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
                                                        logger.info("CompanyID???" + CompanyID + "???x??????????????????????????????");
                                                        tbTZSSendResult sendResult = new tbTZSSendResult();
                                                        sendResult.setSendTime(new Date());
                                                        sendResult.setSendResult("?????????????????????????????????");
                                                        tzsSendResultRepository.save(sendResult);
                                                    }
                                                } else {
                                                    logger.info("CompanyID???" + CompanyID + "???x??????????????????????????????");
                                                    tbTZSSendResult sendResult = new tbTZSSendResult();
                                                    sendResult.setSendTime(new Date());
                                                    sendResult.setSendResult("?????????????????????????????????");
                                                    tzsSendResultRepository.save(sendResult);
                                                }
                                            } else {
                                                logger.info("CompanyID???" + CompanyID + "???????????????????????????????????????");
                                                tbTZSSendResult sendResult = new tbTZSSendResult();
                                                sendResult.setSendTime(new Date());
                                                sendResult.setSendResult("???????????????????????????????????????");
                                                tzsSendResultRepository.save(sendResult);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (listMap.size() > 0) {
                            List<TextAndValue> listAtt;
                            //???????????????ID????????????
                            Map<Integer, List<Map<String, Object>>> mapList = listMap.stream().filter(x -> x.get("KHID") != null).collect(Collectors.groupingBy((Map m) -> (Integer) m.get("KHID")));
                            logger.info("CompanyID???" + CompanyID + "?????????ID?????????" + mapList.size());

                            //?????????????????????ID
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

                                    //??????????????????
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
                                    //????????????????????????(?????????????????????????????????????????????????????????????????????????????????)
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
                                    builder.append("<p>????????????????????????:</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;????????????????????????????????????????????????????????????????????????????????????</p>");
                                    //???????????????????????????????????????
                                    for (String strMailContent : listMailContent) {
                                        builder.append(strMailContent);
                                    }
                                    builder.append("</br></br></br>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + mailConfig.getMemo() + "</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;???????????????" + mailConfig.getRange() + "</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;???????????????" + mailConfig.getAddress() + "</p>");
                                    builder.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;???????????????" + mailConfig.getPhone() + "</p>");
                                    content.setContent(builder.toString());
                                    content.setSubject(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-??????????????????????????????");


                                    try {
                                        List<tbTZSSendResult> listTZSSendResult = new ArrayList<>();
                                        List<tzsEmailRecord> listTZSEmailRecord = new ArrayList<>();
                                        logger.info("CompanyID???" + CompanyID + "???KHMail???" + KHMail);
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
                                                tzsEmailRecord.setSendUserName("???????????????");
                                                tzsEmailRecord.setSendTime(new Date());
                                                listTZSEmailRecord.add(tzsEmailRecord);
                                                MS += Mails + "???";
                                            }
                                            for (tzsEmailRecord er : listTZSEmailRecord) {
                                                logger.info("CompanyID???" + CompanyID + "??????????????????" + MS + "????????????" + er.getShenqingh() + "??????????????????" + er.getTongzhisbh());
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
                                                SBTONGZHISBH.append(mapBHAndSQH.get("TONGZHISBH").toString() + "???");
                                                SBSHENQINGH = mapBHAndSQH.get("SHENQINGH").toString();
                                                KHID = mapBHAndSQH.get("KHID").toString();
                                            }

                                            tbTZSSendResult result = new tbTZSSendResult();
//                                    result.setTongzhisbh(SBTONGZHISBH.toString());
                                            result.setShenqingh(SBSHENQINGH);
                                            result.setSendTime(new Date());
//                                        Integer ClientID = Integer.parseInt(DistinctKHIDS.get(i).toString());
//                                        Optional<tbClient> findClient = listClient.stream().filter(f -> f.getClientID().equals(ClientID)).findFirst();
                                            logger.info("??????ID???:" + CompanyID + "(??????ID???:" + KHID + "),(????????????:" + SBSHENQINGH + "),(??????????????????:" + SBTONGZHISBH.substring(0, SBTONGZHISBH.toString().length() - 1) + ")????????????????????????????????????????????????????????????");
                                            result.setSendResult("??????ID???:" + CompanyID + "(??????ID???:" + KHID + "),(????????????:" + SBSHENQINGH + "),(??????????????????:" + SBTONGZHISBH.substring(0, SBTONGZHISBH.toString().length() - 1) + ")????????????????????????????????????????????????????????????");
                                            tzsSendResultRepository.save(result);
                                        }
                                    } catch (Exception ex) {
                                        logger.info("CompanyID???" + CompanyID + "??????????????????????????????" + ex.getMessage());
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
