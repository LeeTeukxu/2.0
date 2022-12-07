package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.NBBHCreator;
import com.zhide.dtsystem.services.define.IAllUserListService;
import com.zhide.dtsystem.services.define.IClientInfoService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientInfoService implements IClientInfoService {

    @Autowired
    ClientInfoMapper clientMapper;

    @Autowired
    tbClientRepository clientRepository;

    @Autowired
    v_tbEmployeeRepository employeeRepository;

    @Autowired
    tbClientLinkersRepository clientLinkersRepository;

    @Autowired
    followRecordRepository followRecordRepository;

    @Autowired
    tbLinkersUpdateRecordRepository linkersUpdateRecordRep;

    @Autowired
    followRecordRepository followRep;
    @Autowired
    tbClientLinkersRepository linkerRep;
    @Autowired
    tbLoginUserRepository loginUserRep;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    NBBHCreator nbCreator;
    @Autowired
    patentInfoPermissionRepository patentPerRep;
    @Autowired
    casesMainRepository mainRep;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    casesUserRepository mainUserRep;
    @Autowired
    casesSubUserRepository subUserRep;

    @Autowired
    caseHighMainRepository highMainRep;
    @Autowired
    caseHighSubRepository highSubRep;
    @Autowired
    caseHighSubUserRepository highSubUserRep;
    @Autowired
    caseHighUserRepository highUserRep;

    @Autowired
    ClientInfoMapper clientInfoMapper;

    Logger logger = LoggerFactory.getLogger(ClientInfoService.class);
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    tbImageMemoRepository memoRep;
    @Autowired
    pantentInfoRepository pRep;
    @Autowired
    tbDefaultMailRepository defaultMailRepository;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    IAllUserListService allMapper;

    @Override
    @Cacheable(value = "getAllClientByNameAndID", keyGenerator = "CompanyKeyGenerator")
    public Map<String, String> getAllByNameAndID() throws Exception {
        Map<String, String> result = new HashMap<>();
        List<Map<String, Object>> rows = clientMapper.getAllByNameAndID();
        rows.stream().forEach(f -> {
            Object OX = f.get("Name");
            if (ObjectUtils.isEmpty(OX) == false) {
                String Name = OX.toString();
                String ID = f.get("ID").toString();
                if (result.containsKey(Name) == false) {
                    result.put(Name, ID);
                }
            }
        });
        return result;

    }

    @Override
    @Cacheable(value = "getAllClientByIDAndName", keyGenerator = "CompanyKeyGenerator")
    public Map<String, String> getAllByIDAndName() throws Exception {
        Map<String, String> result = new HashMap<>();
        List<Map<String, Object>> rows = clientMapper.getAllByNameAndID();
        rows.stream().forEach(f -> {
            String ID = f.get("ID").toString();
            if (result.containsKey(ID) == false) {
                String Name = f.get("Name").toString();
                result.put(ID, Name);
            }
        });
        return result;
    }

    @Override
    public pageObject getPageData(HttpServletRequest request) throws Exception {
        pageObject result = new pageObject();
        Map<String, Object> params = getParameters(request);
        List<Map<String, Object>> rows = clientMapper.getPageData(params);
        int total = 0;
        if (rows.size() > 0) {
            total = Integer.parseInt(rows.get(0).get("_TotalNum").toString());
            rows.forEach(f -> {
                if (f.containsKey("_TotalNum")) f.remove("_TotalNum");
            });
        }
        result.setData(rows);
        result.setTotal(total);
        return result;
    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject result = new pageObject();
        Map<String, Object> params = getParameters(request);
        List<Map<String, Object>> rows = clientMapper.getData(params);
        int total = 0;
        if (rows.size() > 0) {
            total = Integer.parseInt(rows.get(0).get("_TotalNum").toString());
            rows.forEach(f -> {
                if (f.containsKey("_TotalNum")) f.remove("_TotalNum");
            });
        }
        result.setData(rows);
        result.setTotal(total);
        return result;
    }

    public List<Map<String, Object>> getAllClientInfoExistLinkMan(HttpServletRequest request) throws Exception {
        Map<String, Object> params = getParameters(request);
        List<Map<String, Object>> rows = clientMapper.getAllClientInfoExistLinkMan(params);
        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveImageFollowRecord(followRecord record) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        String ImageData = record.getImageData();
        if (ImageData.length() > 200) {
            UploadUtils uploadUtils = UploadUtils.getInstance(Info.getCompanyId());
            byte[] Bs = Base64Utils.decodeFromString(ImageData.substring(ImageData.indexOf("base64,") + 7));
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Bs);
            uploadFileResult result = uploadUtils.uploadAttachment(UUID.randomUUID().toString() + ".jpg", inputStream);
            if (result.isSuccess()) {
                tbAttachment newOne = new tbAttachment();
                newOne.setName(Long.toString(System.currentTimeMillis()) + ".jpg");
                newOne.setType(1);
                newOne.setGuid(UUID.randomUUID().toString());
                newOne.setPath(result.getFullPath());
                newOne.setUploadMan(Info.getUserIdValue());
                newOne.setUploadManName(Info.getUserName());
                newOne.setUploadTime(new Date());
                newOne.setSize(ImageData.length());
                attRep.save(newOne);

                String XMemo = record.getRecord();
                if (StringUtils.isEmpty(record.getFid())) {
                    record.setFid(UUID.randomUUID().toString());
                    record.setImageData("1");
                    record.setRoleId(Integer.parseInt(Info.getRoleId()));
                    record.setRoleName(Info.getRoleName());
                    record.setFollowUserName(Info.getUserName());
                    record.setDepId(Info.getDepIdValue());
                    record.setCreateTime(new Date());
                    record.setUserId(Info.getUserIdValue());
                    record.setUserName(Info.getUserName());
                } else {
                    Optional<followRecord> finds = followRecordRepository.findFirstByFid(record.getFid());
                    if (finds.isPresent()) {
                        followRecord re = finds.get();
                        re.setUpdateTime(new Date());
                        record = re;
                    } else throw new Exception("数据发生了改变，请刷新后重试!");
                }
                followRecordRepository.save(record);

                tbImageMemo memo = new tbImageMemo();
                memo.setMemo(XMemo);
                memo.setPid(record.getFid());
                memo.setCreateManName(Info.getUserName());
                memo.setAttId(newOne.getGuid());
                memo.setCreateTime(new Date());
                memoRep.save(memo);


                List<tbImageMemo> ims = memoRep.findAllByPid(record.getFid());
                List<String> XIDS = new ArrayList<>();
                Integer Z = 1;
                for (int n = 0; n < ims.size(); n++) {
                    tbImageMemo m = ims.get(n);
                    String X = m.getMemo();
                    if (StringUtils.isEmpty(X)) continue;
                    X = Integer.toString(Z) + ":" + X;
                    XIDS.add(X);
                    Z++;
                }
                String Y = String.join("<Br/>", XIDS);
                record.setRecord(Y);
                followRecordRepository.save(record);
            }
        }
    }

    @Override
    @Cacheable(value = "getClientTree", keyGenerator = "CompanyKeyGenerator")
    public List<TreeListItem> getClientTree(Integer UserID, Integer DepID, String RoleName) {
        Map<String, Object> params = new HashMap<>();
        params.put("DepID", DepID);
        params.put("RoleName", RoleName);
        params.put("UserID", UserID);
        List<tbClient> clients = clientInfoMapper.getClientTree(params);
        List<TreeListItem> result = new ArrayList<>();
        for (int i = 0; i < clients.size(); i++) {
            tbClient one = clients.get(i);
            int CooType = one.getCootype();
            Optional<TreeListItem> find = result.stream()
                    .filter(f -> f.getId().replace("Type", "").equals(Integer.toString(CooType)))
                    .findFirst();
            if (find.isPresent() == false) {
                TreeListItem oo = new TreeListItem();
                oo.setId("Type" + Integer.toString(CooType));
                oo.setText(CooType == 1 ? "合作客户" : "意向客户");
                oo.setPid("0");
                result.add(0, oo);
            }
            TreeListItem item = new TreeListItem();
            item.setPid("Type" + Integer.toString(CooType));
            item.setId(Integer.toString(one.getClientID()));
            item.setText(one.getName());
            result.add(item);
        }
        return result;
    }

    @Override
    @Cacheable(value = "getClientTree", keyGenerator = "CompanyKeyGenerator")
    public List<TreeListItem> getAllClientTree(Integer UserID, Integer DepID, String RoleName) {
        Map<String, Object> params = new HashMap<>();
        params.put("DepID", DepID);
        params.put("RoleName", RoleName);
        params.put("UserID", UserID);
        List<tbClient> clients = clientInfoMapper.getClientTree(params);
        List<TreeListItem> result = new ArrayList<>();
        for (int i = 0; i < clients.size(); i++) {
            tbClient one = clients.get(i);
            int CooType = one.getCootype();
            Optional<TreeListItem> find = result.stream()
                    .filter(f -> f.getId().equals(Integer.toString(CooType)))
                    .findFirst();
            if (find.isPresent() == false) {
                TreeListItem oo = new TreeListItem();
                oo.setId("Type" + Integer.toString(CooType));
                oo.setText(CooType == 1 ? "合作客户" : "意向客户");
                oo.setPid("0");
                result.add(0, oo);
            }
            TreeListItem item = new TreeListItem();
            item.setPid("Type" + Integer.toString(CooType));
            item.setId(Integer.toString(one.getClientID()));
            item.setText(one.getName());
            result.add(item);
        }
        return result;
    }

    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
//        int ClientID=Integer.parseInt(request.getParameter("ClientID"));
//        int SignMan=Integer.parseInt(request.getParameter("SignMan"));
        if (sortField.isEmpty()) sortField = "SN";
        String key = request.getParameter("key");
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        if (Strings.isEmpty(key) == false) {
            key = URLDecoder.decode(key, "utf-8");
            params.put("key", "%" + key + "%");

        }
        String Type = request.getParameter("Type");
        if (Strings.isEmpty(Type) == false) params.put("Type", Type);

        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            queryText = URLDecoder.decode(queryText, "utf-8");
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            highText = URLDecoder.decode(highText);
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
//        if (ClientID!=0){
//            params.put("ClientID",ClientID);
//        }
//        if (SignMan!=0){
//            params.put("SignMan",SignMan);
//        }
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("UserID", Info.getUserId());
            params.put("RoleName", Info.getRoleName());
            params.put("DepID", Info.getDepId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }

    @Cacheable(value = "getClientJSR", keyGenerator = "CompanyKeyGenerator")
    public List<Map<String, Object>> getJSR(int ClientID, int UserID, int DepID, String RoleName) {
        Map<String, Object> params = new HashMap<>();
        params.put("UserID", UserID);
        params.put("RoleName", RoleName);
        params.put("DepID", DepID);
        params.put("ID", ClientID);
        return clientMapper.getJSR(params);
    }

    @Override
    public ClientAndClientLinkers Save(tbClient client, tbClientLinkers clientLinkers, String mode) throws Exception {
        ClientAndClientLinkers result = new ClientAndClientLinkers();
        if (client == null) throw new Exception("提交的客户数据为空!");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失效，请重新登录!");
        String ClientName = client.getName();
        if (StringUtils.isEmpty(ClientName)) throw new Exception("客户名称不能为空!");
        ClientName = ClientName.trim();
        if (client.getClientID() != null) {
            Optional<tbClient> fClient = clientRepository.findById(client.getClientID());
            if (fClient.isPresent()) {
                EntityHelper.copyObject(client, fClient.get());
            }
        } else {
            Optional<tbClient> nameClients = clientRepository.findFirstByName(ClientName);
            if (nameClients.isPresent()) {
                throw new Exception("【" + ClientName + "】已经建立了客户档案，如有需要请联系系统管理员进行客户信息转移!");
            }
            client.setCreateTime(new Date());
            client.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
        }
        if (StringUtils.isEmpty(client.getPassword())) {
            //  client.setPassword(MD5Util.EnCode("123456"));
        }
        client.setName(ClientName);
        tbClient lastClient = clientRepository.save(client);
        if (mode.indexOf("Add") != -1) {
            clientLinkers.setClientID(lastClient.getClientID());
            SaveLinkers(clientLinkers);
        }
        result.setClient(lastClient);
        result.setClientLinkers(clientLinkers);
        redisUtils.clearAll("Client");
        return result;
    }

    @Override
    public followRecord SaveFollowRecord(followRecord followRecord) throws Exception {
        followRecord result = null;
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失效，请重新登录！");
        if (followRecord.getId() == null) {
            followRecord.setCreateTime(new Date());
            followRecord.setFid(UUID.randomUUID().toString());
            followRecord.setDepId(Integer.parseInt(info.getDepId()));
            followRecord.setFollowUserName(info.getUserName());
            followRecord.setRoleId(Integer.parseInt(info.getRoleId()));
            followRecord.setRoleName(info.getRoleName());
            result = followRecord;
        } else {
            Optional<followRecord> find = followRep.findById(followRecord.getId());
            if (find.isPresent()) {
                followRecord fx = find.get();
                fx.setRecord(followRecord.getRecord());
                fx.setUpdateTime(new Date());
                result = fx;
            } else throw new Exception("数据异常:" + Integer.toString(followRecord.getId()) + "无法获取到数据!");
        }
        return followRep.save(result);
    }

    @Override
    public Page<followRecord> getFollowRecords(int ClientID, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createTime").descending());
        Page<followRecord> recordPage = followRep.findAllByClientId(ClientID, pageable);
        return recordPage;
    }

    @Override
    public tbClientLinkers SaveLinkers(tbClientLinkers linkers) throws Exception {
        tbClientLinkers obj = null;
        LoginUserInfo loginInfo = CompanyContext.get();
        tbLinkersUpdateRecord updateRecord = new tbLinkersUpdateRecord();
        updateRecord.setUpID(UUID.randomUUID().toString());
        updateRecord.setActionTime(new Date());
        updateRecord.setActionMan(Integer.parseInt(loginInfo.getUserId()));
        updateRecord.setClientID(linkers.getClientID());
        if (linkers.getLinkID() == null) {
            linkers.setCreateTime(new Date());
            updateRecord.setActionType("增加");
            updateRecord.setAObj("{}");
            updateRecord.setBObj(JSON.toJSONString(linkers));
            obj = linkers;
        } else {
            Optional<tbClientLinkers> finds = linkerRep.findById(linkers.getLinkID());
            if (finds.isPresent()) {
                obj = linkers;
                updateRecord.setActionType("更新");
                updateRecord.setAObj(JSON.toJSONString(finds.get()));
                updateRecord.setBObj(JSON.toJSONString(linkers));
            } else throw new Exception(linkers.getLinkMan() + "的数据已不存在。操作被中止。");
        }
        linkersUpdateRecordRep.save(updateRecord);
        return linkerRep.save(obj);
    }

    @Override
    public Page<tbClientLinkers> getLinkers(int ClientID, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createTime").descending());
        Page<tbClientLinkers> recordPage = linkerRep.findAllByClientIDOrderByPosition(ClientID, pageable);
        return recordPage;
    }

    @Override
    public Page<tbLinkersUpdateRecord> getLinkersUpdateRecord(int ClientID, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("ActionTime").descending());
        Page<tbLinkersUpdateRecord> recordPage = linkersUpdateRecordRep.findAllByClientID(ClientID, pageable);
        return recordPage;
    }

    @Override
    public pageObject getDLF(int ClientID, int SignMan, HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParameters(request);
        params.put("ClientID", ClientID);
        params.put("SignMan", SignMan);
        List<Map<String, Object>> datas = clientMapper.getDLF(params);
        object.setData(datas);
        return object;
    }

    @Override
    public pageObject GetLoginClientReords(int ClientID, HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParameters(request);
        params.put("ClientID", ClientID);
        List<Map<String, Object>> datas = clientMapper.getLoginClientReords(params);
        object.setData(datas);
        return object;
    }

    @Override
    public pageObject getGF(int ClientID, int SignMan, HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParameters(request);
        params.put("ClientID", ClientID);
        params.put("SignMan", SignMan);
        List<Map<String, Object>> datas = clientMapper.getGF(params);
        object.setData(datas);
        return object;
    }

    @Override
    public pageObject getInvoice(int ClientID, HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParameters(request);
        params.put("ClientID", ClientID);
        List<Map<String, Object>> datas = clientMapper.getInvoice(params);
        object.setData(datas);
        return object;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveChangeKH(HttpServletRequest request) throws Exception {
        String Data = request.getParameter("Data");
        JSONArray result = JSON.parseArray(Data);
        for (int i = 0; i < result.size(); i++) {
            JSONObject object = result.getJSONObject(i);
            String Name = object.getString("Name");
            int ClientID = object.getInteger("ClientID");
            int OLDXS = object.getInteger("OLDXS");
            int NOWXS = object.getInteger("NOWXS");

            if (ClientID != 0) {
                List<Integer> list = new ArrayList<>();
                list.add(OLDXS);
                list.add(NOWXS);
                List<tbLoginUser> loginUsers = loginUserRep.findAllById(list);
                list.clear();
                for (int x = 0; x < loginUsers.size(); x++) {
                    tbLoginUser log = loginUsers.get(x);
                    list.add(log.getEmpId());
                }
                List<v_tbEmployee> employees = employeeRepository.findAllById(list);

                SaveChangeKH(ClientID, OLDXS, NOWXS);

                AddFollowRecord(ClientID, OLDXS, NOWXS, Name, employees);
                //更新内部编号
                UpdateNBBH(ClientID, OLDXS, NOWXS);
                //clientRepository.updatePatentInfoPermission(NOWXS, ClientID);

//                String NEIBUBH = clientMapper.getNEIBUBH(ClientID);
//                if (NEIBUBH != null) {
//                    ReplaceNEIBUBH(NEIBUBH, employees.get(1).getEmpName(), employees.get(0).getEmpName());
//                }

                //clientRepository.updateCASESPERMISSION(NOWXS, ClientID);
                //将新的业务员加到专利交单。
                UpdateCaseMainPermission(ClientID, OLDXS, NOWXS);
                //将新的业务员添加到项目交单。
                UpdateCaseHighPermission(ClientID, OLDXS, NOWXS);
            }
        }
    }

    public int SaveChangeKH(int ClientID, int OLDXS, int NOWXS) {
        int result = clientRepository.SaveChangeKH(NOWXS, OLDXS, ClientID);
        return result;
    }

    public followRecord AddFollowRecord(int ClientID, int OLDXS, int NOWXS, String ClientName, List<v_tbEmployee>
            employees) {
        followRecord record = new followRecord();
        if (ClientID != 0) {
            LoginUserInfo info = CompanyContext.get();
            Date nowTime = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String Text = "";
            if (employees.size() > 1) {
                Text = String.format(ClientName + "的跟进人员在" + dt.format(nowTime) + "由" + employees.get(1)
                        .getEmpName() + "变更为" + employees.get(0).getEmpName());
            } else {
                Text = String.format(ClientName + "的跟进人员在" + dt.format(nowTime) + "添加为" + employees.get(0).getEmpName());
            }
            record.setRecord(Text);
            record.setFollowUserName(info.getUserName());
            record.setCreateTime(nowTime);
            record.setClientId(ClientID);
            record = followRep.save(record);
        }
        return record;
    }

    public String ReplaceNEIBUBH(String NB, String OLDName, String NewName) {
        if (NB.lastIndexOf(OLDName) > -1) {
            return NB.replace(OLDName, NewName);
        } else {
            String[] Fs = NB.split(";");
            for (int i = 0; i < Fs.length; i++) {
                if (Fs[i].indexOf("XS") > -1) {
                    Fs[i] = "XS:" + NewName;
                }
            }
            return String.join(";", Fs);
        }
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * <p>
     * 将带有特定客户所有的专利的业务人进行更新。
     *
     * @return
     */
    private void UpdateNBBH(int clientId, int oldXs, int nowXs) throws Exception {
        List<patentInfoPermission> PS = patentPerRep.findAllByUsertypeAndUserid("KH", clientId);
        List<String> AllShenqingh = PS.stream().map(f -> f.getShenqingh()).distinct().collect(Collectors.toList());
        for (String shenqingh : AllShenqingh) {
            Optional<pantentInfo> ps = pRep.findByShenqingh(shenqingh);
            if (ps.isPresent()) {
                pantentInfo pFirst = ps.get();
                String nbbh = pFirst.getNeibubh();
                String newBH = nbCreator.replace(nbbh, "XS", nowXs);
                pFirst.setNeibubh(newBH);
                pRep.save(pFirst);

                patentPerRep.deleteAllByShenqinghAndUsertypeAndUserid(shenqingh, "XS", oldXs);

                patentInfoPermission pNew = new patentInfoPermission();
                pNew.setShenqingh(shenqingh);
                pNew.setUsertype("XS");
                pNew.setUserid(nowXs);
                patentPerRep.save(pNew);
            }
        }
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * <p>
     * 针对特定客户的交单，如果发起人是老的业务人员。则把新的业务人员也加到该笔交单业务可查看的人员列表中。
     *
     * @return
     */
    private void UpdateCaseMainPermission(int clientId, int oldXs, int nowXs) {
        List<casesMain> findMains = mainRep.findAllByClientId(clientId);
        for (casesMain main : findMains) {
            String casesId = main.getCasesId();
            if (main.getCreateMan() == oldXs) {
                List<casesUser> findMainUsers = mainUserRep.findAllByCasesIdAndUserId(casesId, nowXs);
                if (findMainUsers.size() == 0) {
                    casesUser mainUser = new casesUser();
                    mainUser.setCasesId(casesId);
                    mainUser.setUserId(nowXs);
                    mainUser.setCreateTime(new Date());
                    mainUserRep.save(mainUser);
                }
                List<casesSubUser> findSubUsers = subUserRep.findAllByCasesIdAndUserId(casesId, nowXs);
                if (findSubUsers.size() == 0) {
                    List<casesSub> subs = subRep.findAllByCasesId(casesId);
                    for (casesSub sub : subs) {
                        casesSubUser subUser = new casesSubUser();
                        subUser.setSubId(sub.getSubId());
                        subUser.setCasesId(casesId);
                        subUser.setUserId(nowXs);
                        subUser.setCreateTime(new Date());
                        subUserRep.save(subUser);
                    }
                }
            }
        }

    }

    private void UpdateCaseHighPermission(int clientId, int oldXs, int nowXs) {
        List<caseHighMain> findMains = highMainRep.findAllByClientId(clientId);
        for (caseHighMain main : findMains) {
            String casesId = main.getCasesId();
            if (main.getCreateMan() == oldXs) {
                List<caseHighUser> findMainUsers = highUserRep.findAllByCasesIdAndUserId(casesId, nowXs);
                if (findMainUsers.size() == 0) {
                    caseHighUser mainUser = new caseHighUser();
                    mainUser.setCasesId(casesId);
                    mainUser.setUserId(nowXs);
                    mainUser.setCreateTime(new Date());
                    highUserRep.save(mainUser);
                }
                List<caseHighSubUser> findSubUsers = highSubUserRep.findAllByCasesIdAndUserId(casesId, nowXs);
                if (findSubUsers.size() == 0) {
                    List<caseHighSub> subs = highSubRep.findAllByCasesId(casesId);
                    for (caseHighSub sub : subs) {
                        caseHighSubUser subUser = new caseHighSubUser();
                        subUser.setSubId(sub.getSubId());
                        subUser.setCasesId(casesId);
                        subUser.setUserId(nowXs);
                        subUser.setCreateTime(new Date());
                        highSubUserRep.save(subUser);
                    }
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @MethodWatch(name = "删除客户信息")
    public boolean remove(List<String> ids) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        if(Info.getRoleName().equals("系统管理员")==false) throw  new Exception("只有系统管理员才能执行删除操作!");
        for (int i = 0; i < ids.size(); i++) {
            Integer id = Integer.parseInt(ids.get(i));
            Optional<tbClient> findOnes = clientRepository.findById(id);
            if (findOnes.isPresent()) {
               tbClient cc=findOnes.get();
                List<patentInfoPermission> VS=  patentPerRep.findAllByUsertypeAndUserid("KH",id);
                if(VS.size()>0){
                    throw new Exception(cc.getName()+"还有引用专利信息，无法删除!");
                }

                List<casesMain> Ms=mainRep.findAllByClientId(id);
                if(Ms.size()>0) throw new Exception(cc.getName()+"还被专利交单引用，无法删除!");
                logger.info(Info.getUserName() + "试图删除客户:" + findOnes.get().getName());

                clientMapper.delClient(id);
                clientMapper.delClientLinkers(id);
                clientMapper.delFollowRecord(id);
            } else throw new Exception("准备删除的客户信息不存在，请刷新后再重试!");
        }
        return true;
    }

    private int getTypeInteger(String TypeChn) {
        int TypeInteger = 0;
        switch (TypeChn) {
            case "工矿企业":
                TypeInteger = 1;
                break;
            case "事业单位":
                TypeInteger = 3;
                break;
            case "大专院校":
                TypeInteger = 4;
                break;
            case "个人":
                TypeInteger = 2;
                break;
            default:
                break;
        }
        return TypeInteger;
    }

    @Override
    public tbClient findNameByName(String Name) {
        return clientMapper.findNameByName(Name);
    }
    @Override
    public Map<String, Object> SaveAll(String Type, JSONArray jsonArray) throws Exception {
        Map<String,Object> result=new HashMap();
        List<String> Errors=new ArrayList<>();
        Integer Total=0;
        for (Object obj : jsonArray.toArray()) {
            JSONObject OO = (JSONObject) obj;
            try {
               int Num= SaveSingle(Type, OO);
               Total+=Num;
            } catch (Exception ax) {
                String Error=OO.getString("Name")+"保存失败,错误:"+ax.getMessage();
                logger.info(Error);
                Errors.add(Error);
            }
        }
        result.put("Num",Total);
        result.put("Errors",Errors);
        return result;
    }

    @Transactional
    public int  SaveSingle(String Type, JSONObject jo) throws Exception {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        String Result="正常导入";
        if(jo.containsKey("IsErrorResult")) Result=jo.getString("IsErrorResult");
        if (Result.equals("正常导入")) {
            tbClient client = new tbClient();

            String name = jo.getString("Name");
            if (StringUtils.isEmpty(name)) throw new Exception("客户名称不能为空!");
            Optional<tbClient> findClients = clientRepository.findFirstByName(name);
            if (findClients.isPresent()) {
                client = findClients.get();
                client.setType(getTypeInteger(jo.getString("Type")));
                client.setSignMan(Integer.parseInt(loginUserInfo.getUserId()));
                client.setOrgCode(jo.getString("Email"));
                client.setCootype(Integer.parseInt(Type));
            } else {
                client.setName(jo.getString("Name"));
                client.setType(getTypeInteger(jo.getString("Type")));
                client.setSignMan(Integer.parseInt(loginUserInfo.getUserId()));
                client.setSignDate(new Date());
                client.setOrgCode(jo.getString("Email"));
                client.setCootype(Integer.parseInt(Type));
                client.setCreateTime(new Date());
                client.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
                client.setCanUse(true);
            }
            clientRepository.save(client);
            String Phone=jo.getString("Mobile");
            if(StringUtils.isEmpty(Phone)==false) {
                Optional<tbClientLinkers> findLinkers =
                        clientLinkersRepository.findFirstByClientIDAndMobile(client.getClientID(),Phone);
                if(findLinkers.isPresent()==false) {
                    tbClientLinkers clientLinkers = new tbClientLinkers();
                    clientLinkers.setClientID(client.getClientID());
                    clientLinkers.setCType(jo.getString("CType"));
                    clientLinkers.setLinkMan(jo.getString("LinkMan"));
                    clientLinkers.setMobile(jo.getString("Mobile"));
                    clientLinkers.setAddress(jo.getString("Address"));
                    clientLinkers.setLinkPhone(jo.getString("LinkPhone"));
                    clientLinkers.setMemo(jo.getString("Memo"));
                    clientLinkers.setEmail(jo.getString("Email"));
                    clientLinkers.setCreateTime(new Date());
                    clientLinkersRepository.save(clientLinkers);
                }
            }
            return  1;
        } else return 0;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    @MethodWatch(name = "设置默认邮箱")
    public void EditDefaultMaile(List<String> ids, String ClientID) {
        LoginUserInfo Info = CompanyContext.get();
        defaultMailRepository.deleteAllByClientId(Integer.parseInt(ClientID));
        List<tbDefaultMail> listMail = new ArrayList<>();
        tbDefaultMail mail;
        for (int i=0;i<ids.size();i++) {
            mail = new tbDefaultMail();
            Integer id = Integer.parseInt(ids.get(i));
            mail.setClientId(Integer.parseInt(ClientID));
            mail.setLinkersId(id);
            mail.setUserId(Info.getUserIdValue());
            mail.setAddTime(new Date());
            listMail.add(mail);
        }
        defaultMailRepository.saveAll(listMail);
    }

    @Transactional(rollbackFor =  Exception.class)

    @Override
    public void CannelDefaultMail(List<String> ids) {
        List<Integer> listLinkersId = new ArrayList<>();
        for (int i=0;i<ids.size();i++) {
            Integer id = Integer.parseInt(ids.get(i));
            listLinkersId.add(id);
        }
        defaultMailRepository.deleteAllByLinkersIdIn(listLinkersId);
    }

    @Override
    public void ChangeSignMan(Integer Transfer, Integer Resgination) throws Exception {
        List<tbClient> listClient = new ArrayList<>();
        clientRepository.findAll().stream().forEach(f -> {
            if (f.getSignMan() == Resgination) {
                tbClient client = new tbClient();
                client = f;
                client.setSignMan(Transfer);
                listClient.add(client);
            }
        });
        clientRepository.saveAll(listClient);
    }

    @Override
    public void FollowRecordUserIDChange(Integer Transfer, Integer Resgination) throws Exception {
        List<followRecord> listFollowRecod = new ArrayList<>();
        followRecordRepository.findAll().stream().forEach(f -> {
            if (f.getUserId() == Resgination) {
                followRecord followRecord = new followRecord();
                followRecord = f ;
                followRecord.setUserId(Transfer);
            }
        });
        followRecordRepository.saveAll(listFollowRecod);
    }

    @Override
    public void FollowRecordAddInChange(Integer Transfer, Integer Resgination) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        String LoginCode = loginUserMapper.getLoginCodeById(Transfer);
        LoginUserInfo login = allMapper.findUserInfoByAccountAndCompanyID("Client_" + Info.getCompanyId(),LoginCode);
        String ResginationName = loginUserMapper.getLoginUserNameById(Resgination);
        List<tbClient> listResgination = clientRepository.findAll().stream().filter(f -> f.getSignMan() == Resgination).collect(Collectors.toList());
        List<followRecord> listFollowRecord = new ArrayList<>();
        for (tbClient client : listResgination) {
            followRecord record = new followRecord();
            record.setRecord("登记人由：" + ResginationName + "变更为：" + login.getUserName());
            record.setFollowUserName(login.getUserName());
            record.setCreateTime(new Date());
            record.setClientId(client.getClientID());
            record.setUserName(login.getUserName());
            record.setUserId(Transfer);
            record.setRoleId(Integer.parseInt(login.getRoleId()));
            record.setRoleName(login.getRoleName());
            record.setDepId(login.getDepIdValue());
            listFollowRecord.add(record);
        }
        followRep.saveAll(listFollowRecord);
    }
}