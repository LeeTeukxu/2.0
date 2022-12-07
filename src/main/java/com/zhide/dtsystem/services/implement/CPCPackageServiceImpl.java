package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.CPCPackageMapper;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICPCFileService;
import com.zhide.dtsystem.services.define.ICPCPackageService;
import com.zhide.dtsystem.services.instance.cpcPackage.cpcPackage;
import com.zhide.dtsystem.services.sqlParameterCreator;
import com.zhide.dtsystem.viewModel.testAcceptFileResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: CPCPackageServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月10日 17:36
 **/
@Service
public class CPCPackageServiceImpl implements ICPCPackageService {
    @Autowired
    casesSubRepository subRep;
    @Autowired
    cpcPackageMainRepository cpcMainRep;
    @Autowired
    cpcInventorRepository cpcInventorRep;
    @Autowired
    cpcFilesRepository cpcFilesRep;
    @Autowired
    cpcApplyManRepository cpcApplyRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    cpcAgentRepository cpcAgentRep;
    @Autowired
    CPCPackageMapper cpcMapper;
    @Autowired
    cpcFilesRepository cpcAttRep;
    @Autowired
    tbCompanyRepository companyRep;
    @Autowired
    tbAgentsRepository agentsRep;
    @Autowired
    cpcPackage cpcPackage;


    Logger logger= LoggerFactory.getLogger(CPCPackageServiceImpl.class);

    private List<CPCFileType> getDefaultTypes(int type) {
        List<CPCFileType> types = new ArrayList<>();
        CPCFileType A100001 = new CPCFileType("100001", "权利要求书");
        CPCFileType A100002 = new CPCFileType("100002", "说明书");
        CPCFileType A100003 = new CPCFileType("100003", "说明书附图");
        CPCFileType A100004 = new CPCFileType("100004", "说明书摘要");
        CPCFileType A100005 = new CPCFileType("100005", "摘要附图");
        types.add(A100001);
        types.add(A100003);
        types.add(A100004);
        if(type==1) types.add(A100005);
        types.add(A100002);

        return types;
    }
    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失败，请重新登录!");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public cpcPackageMain createOne(String SubID, String TargetFile) throws Exception {
        Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesSub sub = findSubs.get();
            cpcPackageMain main = createMain(sub);

            String acceptFiles = sub.getAcceptTechFiles();
            if (StringUtils.isEmpty(acceptFiles) == false) {
                List<cpcFiles> files=createFiles(main,acceptFiles,TargetFile);
                if(files.size()>0) {
                    cpcFilesRep.saveAll(files);
                }
            }
            cpcMainRep.save(main);
            return main;
        } else throw new Exception(SubID + "的交单业务不存在!");
    }
    public List<String> testAcceptFile(String SubID) throws Exception{
        List<String> result=new ArrayList<>();
        Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesSub sub = findSubs.get();
            String acceptFiles = sub.getAcceptTechFiles();
            int Num=0;
            if (StringUtils.isEmpty(acceptFiles) == false) {
                List<String> AttIDS= Arrays.stream(acceptFiles.split(",")).collect(Collectors.toList());
                for(String AttID:AttIDS){
                    Optional<tbAttachment> findAtts=attRep.findAllByGuid(AttID);
                    if(findAtts.isPresent()){
                        tbAttachment tb=findAtts.get();
                        if(tb.getType()==2) continue;

                        String filePath=tb.getPath();
                        String targetDir=unZipFile(filePath);
                        List<File> wordFiles=
                                FileUtils.listFiles(new File(targetDir),new String[]{"doc","DOC","docx", "DOCX","Doc", "Docx"}, true).stream().collect(Collectors.toList());
                        for(File file:wordFiles){
                            String name=file.getName();
                            if(result.contains(name)==false) result.add(name);
                        }
                        FileUtils.deleteQuietly(new File(targetDir));
                        Num++;
                    }
                }
            } else throw new Exception("该业务交单没有专利交单文件！");
            if(Num==0)throw new Exception("该业务交单没有专利交单文件！");
        } else throw new Exception(SubID + "的交单业务不存在!");
        return result;
    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = cpcMapper.getData(params);
        int Total = 0;
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;

    }

    @Override
    public pageObject getCPCFiles(String MainID) {
        List<Map<String,Object>> Datas= cpcMapper.getCPCFiles(MainID);
        pageObject obj=new pageObject();
        obj.setData(Datas);
        obj.setTotal(Datas.size());
        return obj;
    }

    @Override
    public pageObject getInventors(String MainID) {
        List<Map<String,Object>> Datas= cpcMapper.getInventors(MainID);
        pageObject obj=new pageObject();
        obj.setData(Datas);
        obj.setTotal(Datas.size());
        return obj;
    }

    @Override
    public pageObject getApplyMan(String MainID) {
        List<Map<String,Object>> Datas= cpcMapper.getApplyMan(MainID);
        pageObject obj=new pageObject();
        obj.setData(Datas);
        obj.setTotal(Datas.size());
        return obj;
    }

    @Override
    public pageObject getAgent(String MainID) {
        List<Map<String,Object>> Datas= cpcMapper.getAgents(MainID);
        pageObject obj=new pageObject();
        obj.setData(Datas);
        obj.setTotal(Datas.size());
        return obj;
    }

    @Override
    public void SaveApply(cpcApplyMan app) {
        LoginUserInfo Info=CompanyContext.get();
        String SubID=app.getSubId();
        if(StringUtils.isEmpty(SubID)){
            app.setSubId(UUID.randomUUID().toString());
            app.setCreateMan(Info.getUserIdValue());
            app.setCreateTime(new Date());
        }
        cpcApplyRep.save(app);
    }

    @MethodWatch(name="删除CPC案卷包记录")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOne(String MainID) throws Exception {
        Optional<cpcPackageMain> findMains=cpcMainRep.findFirstByPid(MainID);
        if(findMains.isPresent()){
            LoginUserInfo Info=CompanyContext.get();
            cpcPackageMain main=findMains.get();
            String Code=main.getDocSn();
            String  YearMonth=Code.substring(3,9);
            Integer Num=Integer.parseInt(Code.substring(Code.length()-4));
            feeItemMapper.deleteFlowCode("AJB",Num,YearMonth);
            cpcInventorRep.deleteAllByMainId(MainID);
            cpcApplyRep.deleteAllByMainId(MainID);
            cpcFilesRep.deleteAllByMainId(MainID);
            cpcAgentRep.deleteAllByMainId(MainID);
            cpcMainRep.delete(main);
            DirectoryUtils.deleteAll(cpcPackage.getRootPath()+Info.getCompanyId()+"\\"+MainID);
            return true;
        } else throw new Exception("待删除的对象已不存在!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeInventor(Integer ID) throws Exception {
         cpcInventorRep.deleteById(ID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeApplyMan(Integer ID) throws Exception {
         cpcApplyRep.deleteById(ID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAgent(Integer ID) throws Exception {
       cpcAgentRep.deleteById(ID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String SaveAll(Map<String, Object> Datas) throws Exception {
       if(Datas.containsKey("Main")){
           String MT=Datas.get("Main").toString();
           cpcPackageMain main=JSON.parseObject(MT,cpcPackageMain.class);
           cpcPackageMain targetMain= SaveMain(main);
           if(Datas.containsKey("Apply")){
               String AT=Datas.get("Apply").toString();
               List<cpcApplyMan> mans=JSON.parseArray(AT,cpcApplyMan.class);
               SaveApplyMan(main.getPid(),mans);
           }
           if(Datas.containsKey("Inventor")){
               String IT=Datas.get("Inventor").toString();
               List<cpcInventor> mans=JSON.parseArray(IT,cpcInventor.class);
               SaveInventor(main.getPid(),mans);
           }
           if(Datas.containsKey("Agent")){
               String AT=Datas.get("Agent").toString();
               List<cpcAgent> mans=JSON.parseArray(AT,cpcAgent.class);
               SaveAgents(main.getPid(),mans);

               String XName=mans.stream().map(f->f.getName()).collect(Collectors.joining(","));
               targetMain.setAgents(XName);
               cpcMainRep.save(targetMain);
           }
           if(Datas.containsKey("File")){
               String FT=Datas.get("File").toString();
               List<cpcFiles> mans=JSON.parseArray(FT,cpcFiles.class);
               SaveFiles(main.getPid(),mans);
           }
           return main.getPid();
       } else throw new Exception("提交数据异常，缺少Main键。");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public cpcPackageMain SaveAllData(Map<String, Object> Datas) throws Exception {
        if(Datas.containsKey("Main")){
            String MT=Datas.get("Main").toString();
            cpcPackageMain main=JSON.parseObject(MT,cpcPackageMain.class);
            cpcPackageMain targetMain= saveMainData(main);
            if(Datas.containsKey("Apply")){
                String AT=Datas.get("Apply").toString();
                List<cpcApplyMan> mans=JSON.parseArray(AT,cpcApplyMan.class);
                SaveApplyMan(main.getPid(),mans);
            }
            if(Datas.containsKey("Inventor")){
                String IT=Datas.get("Inventor").toString();
                List<cpcInventor> mans=JSON.parseArray(IT,cpcInventor.class);
                SaveInventor(main.getPid(),mans);
            }
            if(Datas.containsKey("Agent")){
                String AT=Datas.get("Agent").toString();
                List<cpcAgent> mans=JSON.parseArray(AT,cpcAgent.class);
                SaveAgents(main.getPid(),mans);

                String XName=mans.stream().map(f->f.getName()).collect(Collectors.joining(","));
                targetMain.setAgents(XName);
                cpcMainRep.save(targetMain);
            }
            if(Datas.containsKey("File")){
                String FT=Datas.get("File").toString();
                List<cpcFiles> mans=JSON.parseArray(FT,cpcFiles.class);
                SaveFiles(main.getPid(),mans);
            }
            return main;
        } else throw new Exception("提交数据异常，缺少Main键。");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void DeleteCPCFile(String SubID) throws Exception {
        Optional<cpcFiles> findFiles=cpcAttRep.findFirstBySubId(SubID);
        if(findFiles.isPresent()){
            cpcFiles file=findFiles.get();
            String AttID=file.getAttId();
            attRep.deleteAllByGuid(AttID);
            cpcAttRep.delete(file);
        } else throw  new Exception("要删除的文件已不存在!");
    }

    @Override
    public boolean CheckAll(String MainID) throws Exception {
        Optional<cpcPackageMain> findMains=cpcMainRep.findFirstByPid(MainID);
        if(findMains.isPresent()) {
            cpcPackageMain main =findMains.get();
            if(main.getShenqinglx()==null) throw new Exception("请选择专利类型!");
            if(StringUtils.isEmpty(main.getNbbh())) throw new Exception("必须填写专利的内部编号!");
            if(StringUtils.isEmpty(main.getFamingmc())) throw new Exception("必须填写专利名称!");

            Integer shenqinglx=main.getShenqinglx();
            List<cpcFiles> files=cpcFilesRep.findAllByMainIdOrderByCode(MainID);
            List<cpcAgent> agents=cpcAgentRep.findAllByMainId(MainID);
            CheckFiles(main,agents,files);
            CheckAgents(agents);
            List<cpcApplyMan> mans=cpcApplyRep.findAllByMainId(MainID);
            CheckApplyMan(mans);
            List<cpcInventor> invs=cpcInventorRep.findAllByMainId(MainID);
            CheckInventor(invs);
        } else throw new Exception("操作的案卷包对象已不存在!");
        return false;
    }

    @Override
    public boolean saveAgentInfo(List<tbAgents> rows) throws Exception {
        agentsRep.saveAll(rows);
        return true;
    }

    @Override
    public boolean saveCompanyInfo(List<tbCompany> rows) throws Exception {
        companyRep.saveAll(rows);
        return true;
    }

    @Override
    public void removeAgentInfo(Integer ID) {
        agentsRep.deleteById(ID);
    }

    @Override
    public void removeCompanyInfo(Integer ID) {
        companyRep.deleteById(ID);
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     校验上传文件。
     * @return
     */
    private void CheckFiles(cpcPackageMain main,List<cpcAgent> agents,List<cpcFiles> files) throws Exception{
        Integer shenqinglx=main.getShenqinglx();
        Map<String,String> CodeHash=new HashMap<String,String>(){{
            put("100001","权利要求书");
            put("100002","说明书");
            put("100003","说明书附图");
            put("100004","说明书摘要");
            put("100005","摘要附图");
            put("10000701","专利代理委托书扫描件");
        }};
        List<String>needCodes= Lists.newArrayList("100001","100002","100004");
        //if(shenqinglx==1) needCodes=Lists.newArrayList("100001","100002","100004","100003","100005");//发明也可以没有附图。
        if(shenqinglx==1) needCodes=Lists.newArrayList("100001","100002","100004");
        if(agents.size()>0){
            needCodes.add("10000701");
        }
        for(String code:needCodes){
            Optional<cpcFiles> findFile= files.stream().filter(f-> f.getCode().equals(code)).findFirst();
            if(findFile.isPresent()==false) {
                String Name=CodeHash.get(code);
                throw new Exception(Name + "类型的文件没有上传!");
            }
        }
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     校验代理师信息
     * @return
     */
    private void CheckAgents(List<cpcAgent> agents) throws Exception{
        for(cpcAgent agent:agents){
            String code=agent.getCode();
            String phone=agent.getPhone();
            String name=agent.getName();
            if(StringUtils.isEmpty(code)) throw new Exception("代理师:"+name+"执业证号录入不完整。");
            if(StringUtils.isEmpty(phone)) throw new Exception("代理师:"+name+"手机号码录入不完整。");
            if(RegexUtils.IsMobile(phone)==false) {
                if(RegexUtils.IsPhone(phone)==false) {
                    throw new Exception("代理师:" + name + "的手机号码无效!");
                }
            }
        }
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     校验申请人信息。
     * @return
     */
    private  void CheckApplyMan(List<cpcApplyMan> mans) throws Exception{
        if(mans.size()==0) throw new Exception("至少需要一个申请人!");
        for(cpcApplyMan man:mans){
            String code=man.getCode();
            String name=man.getName();
            String idCode=man.getIdCode();
            int type= Integer.parseInt(man.getType());
            String email=man.getEmail();
            if(type==5) {
                if (RegexUtils.IsIDCard(idCode)==false) throw new Exception("申请人:"+name+"身份证号码无效!");
            }else {
                if (RegexUtils.IsCompanyCard(idCode)==false) throw new Exception("申请公司"+name+"的社会信用代码无效!");
            }
            if(RegexUtils.IsEmail(email)==false)throw new Exception("申请人:"+name+"的电子邮箱无效!");
        }
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     发明人信息校验。
     * @return
     */
    private  void CheckInventor(List<cpcInventor> inventors) throws Exception{
        if(inventors.size()==0) throw new Exception("至少需要录入一个专利发明人!");
        Integer firstCount=0;
        for(cpcInventor man:inventors){
            String name=man.getName();
            boolean first=man.getFirst();
            if(first==true) {
                String country =man.getCountry();
                String code=man.getCode();
                if(StringUtils.isEmpty(country)) throw new Exception("第一发明人:"+name+"的国籍不能为空!");
                if (RegexUtils.IsIDCard(code)==false) throw new Exception("第一发明人:"+name+"的身份证号码无效!");
                firstCount++;
            }
        }
        if(firstCount==0) throw new Exception("必须要指定一个第一发明人!");
        if(firstCount>1) throw new Exception("有且只能有一个第一发明人!");
    }
    private cpcPackageMain SaveMain(cpcPackageMain main) throws Exception {
        String PID=main.getPid();
        Optional<cpcPackageMain> findMains=cpcMainRep.findFirstByPid(PID);
        if(findMains.isPresent()){
            cpcPackageMain mmain=findMains.get();
            mmain.setAgentCode(main.getAgentCode());
            mmain.setAgentName(main.getAgentName());
            mmain.setFamingmc(main.getFamingmc());
            mmain.setNbbh(main.getNbbh());
            mmain.setFirstInventor(main.getFirstInventor());
            mmain.setFirstInventCountry(main.getFirstInventCountry());
            mmain.setFirstInventIdCode(main.getFirstInventIdCode());
            mmain.setDigistImage(main.getDigistImage());
            mmain.setSameApply(main.getSameApply());
            mmain.setItemCount(main.getItemCount());
            mmain.setAddSZSC(main.getAddSZSC());
            mmain.setSameApply(main.getSameApply());
           return  cpcMainRep.save(mmain);
        } else  return null;
    }
    private cpcPackageMain saveMainData(cpcPackageMain main) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        if(StringUtils.isEmpty(main.getPid())){
            main.setDocSn(feeItemMapper.getFlowCode("AJB"));
            main.setAbandonChangeRights(false);
            main.setAdvanceApply(true);
            main.setRealMessage(true);
            main.setPid(UUID.randomUUID().toString());
            main.setCreateMan(Info.getUserIdValue());
            main.setCreateTime(new Date());
           return  cpcMainRep.save(main);
        } else {
            return SaveMain(main);
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveApplyMan(String MainID,List<cpcApplyMan> mans) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        for(cpcApplyMan apply:mans){
            String SubID=apply.getSubId();
            if(StringUtils.isEmpty(SubID)){
                apply.setSubId(UUID.randomUUID().toString());
                apply.setMainId(MainID);
                apply.setCreateMan(Info.getUserIdValue());
                apply.setCreateTime(new Date());
            }
        }
        cpcApplyRep.saveAll(mans);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveInventor(String MainID,List<cpcInventor> mans) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        cpcPackageMain main=cpcMainRep.findFirstByPid(MainID).get();
        long count=mans.stream().filter(f->f.getFirst()==true).count();
        if(count>1) throw new Exception("有且只能有一个第一发明人!");
        for(cpcInventor inv:mans){
            String SubID=inv.getSubId();
            if(StringUtils.isEmpty(SubID)){
                inv.setSubId(UUID.randomUUID().toString());
                inv.setMainId(MainID);
                inv.setCreateMan(Info.getUserIdValue());
                inv.setCreateTime(new Date());
            }
            if(inv.getFirst()==true){
                main.setFirstInventor(inv.getName());
                main.setFirstInventIdCode(inv.getCode());
                main.setFirstInventCountry(inv.getCountry());
                cpcMainRep.save(main);
            }
        }
        cpcInventorRep.saveAll(mans);
    }
    private  void SaveFiles(String MainID,List<cpcFiles> files) throws  Exception{
        LoginUserInfo Info=CompanyContext.get();
        for(cpcFiles file:files){
            String SubID=file.getSubId();
            if(StringUtils.isEmpty(SubID)){
                file.setSubId(UUID.randomUUID().toString());
                file.setMainId(MainID);
            }
        }
        cpcFilesRep.saveAll(files);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public  void SaveAgents(String MainID,List<cpcAgent> mans) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        for(cpcAgent inv:mans){
            String SubID=inv.getSubId();
            if(StringUtils.isEmpty(SubID)){
                inv.setSubId(UUID.randomUUID().toString());
                inv.setMainId(MainID);
                inv.setCreateMan(Info.getUserIdValue());
                inv.setCreateTime(new Date());
            }
        }
        cpcAgentRep.saveAll(mans);
    }
    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");

        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String StateText = request.getParameter("State");
        if (org.apache.commons.lang.StringUtils.isEmpty(StateText) == false) {
            params.put("State", StateText);
        }
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");

        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        return params;
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 根据交单明细生成CPC案卷包信息。

     * @return
     */
    private cpcPackageMain createMain(casesSub sub) {
        String acceptFiles=sub.getAcceptTechFiles();
        LoginUserInfo Info=CompanyContext.get();
        cpcPackageMain main = new cpcPackageMain();
        main.setSubId(sub.getSubId());
        main.setSubNo(sub.getSubNo());
        main.setNbbh(sub.getNbbh());
        if(StringUtils.isEmpty(acceptFiles)){
            main.setFamingmc(sub.getShenqingName());
        }else {
            List<String>IDS= Arrays.asList(acceptFiles.split(",").clone());
            List<tbAttachment> Atts=attRep.findAllByGuidIn(IDS);
            if(Atts.size()>0){
                tbAttachment first=Atts.get(0);
                String Name=first.getName();
                String XT=FilenameUtils.getBaseName(Name);
                main.setFamingmc(XT);
            } else  main.setFamingmc(sub.getShenqingName());
        }
        Integer lx = null;
        if (sub.getyName().indexOf("发明专利") > -1) {
            lx = 0;
        } else if (sub.getyName().indexOf("实用新型") > -1) {
            lx = 1;
        } else if (sub.getyName().indexOf("外观设计") > -1) {
            lx = 2;
        }
        main.setShenqinglx(lx);
        main.setDocSn(feeItemMapper.getFlowCode("AJB"));
        main.setAbandonChangeRights(false);
        main.setAdvanceApply(true);
        main.setItemCount(0);
        main.setRealMessage(true);
        main.setAddSZSC(false);
        main.setDigistImage("");
        main.setPid(UUID.randomUUID().toString());
        main.setSameApply(false);
        main.setYName(sub.getyName().trim());
        main.setCreateMan(Info.getUserIdValue());
        main.setCreateTime(new Date());
        return main;
    }
    @Autowired
    ICPCFileService cpcFileService;
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 下载申报文件。解压以后。找到5书文件。生成CPC包附件信息。

     * @return
     */
    private List<cpcFiles> createFiles(cpcPackageMain main, String acceptFiles,String targetFile) throws Exception {
        List<cpcFiles> files = new ArrayList<>();
        String MainID=main.getPid();
        String[] IDS = acceptFiles.split(",").clone();
        for (String ID : IDS) {
            Optional<tbAttachment> findAtt = attRep.findAllByGuid(ID);
            if (findAtt.isPresent()) {
                tbAttachment attInfo = findAtt.get();
                String Name = attInfo.getName();
                if (FileNameUtils.isZipFile(Name)) {
                    String saveDir = unZipFile(attInfo.getPath());
                    List<File>Fs=null;
                    if (containFileFiles(main.getShenqinglx(), saveDir)) {
                        Fs=getFiveFiles(saveDir);
                    } else {
                        List<File> docFiles=FileUtils.listFiles(new File(saveDir),new String[]{"DOC","DOCX","doc", "docx", "Doc","Docx"},true).stream().collect(Collectors.toList());
                        if(docFiles.size()>0) {
                            Optional<File> findFiles=
                                    docFiles.stream().filter(f->f.getName().equals(targetFile)).findFirst();
                            if(findFiles.isPresent()) {
                                File docFile=findFiles.get();
                                Fs = cpcFileService.getFiveFiles(main.getShenqinglx(), docFile.getPath(), saveDir);
                            } else throw  new Exception("技术文件:"+targetFile+"不存在!");
                        }
                    }
                    if(Fs.size()==0)throw new Exception("上传的技术文档中未发现可分解为五书的Word文件!");
                    for(File file:Fs){
                        try {
                            cpcFiles f=uploadAndCreate(main,file);
                            f.setMainId(MainID);
                            f.setSubId(UUID.randomUUID().toString());
                            files.add(f);
                        }
                        catch(Exception ax){
                            logger.info("uploadAndCreate:"+file.getName()+"发生错误:"+ax.getMessage());
                            throw ax;
                        }
                    }
                    if(files.size()>0) break;
                }
            }
        }
        return files;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 下载并解压一个文件。返回文件夹路径。
     *
     * @return
     */
    private String unZipFile(String filePath) throws Exception {
        FTPUtil util = new FTPUtil();
        if (util.connect() == true) {
            String downloadPath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
            util.download(filePath, downloadPath);
            String saveDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
            ZipFileUtils.unZip(downloadPath, saveDir);
            hasZipFiles=new ArrayList<>();
            unZipAllFiles(new File(saveDir),saveDir);
            FileUtils.deleteQuietly(new File(downloadPath));
            return saveDir;
        } else throw new Exception("下载" + filePath + "失败。");
    }
    List<String> hasZipFiles=new ArrayList<>();
    private void unZipAllFiles(File zipFile,String saveDir) throws  Exception{
        List<File> zipFiles=
                FileUtils.listFiles(zipFile,new String[]{"zip","ZIP","rar", "RAR"},true).stream().collect(Collectors.toList());
        if(zipFiles.size()>0){
            for(File file:zipFiles){
                String X=file.getName();
                if(hasZipFiles.contains(X)==false) {
                    ZipFileUtils.unZip(file.getPath(), saveDir);
                    hasZipFiles.add(X);
                    unZipAllFiles(new File(saveDir), saveDir);
                }
            }
        }
    }

    private List<File> getFiveFiles(String targetDir){
        File f = new File(targetDir);
        List<File> fs=new ArrayList<>();
        if (f.isDirectory()) {
           fs= FileUtils.listFiles(new File(targetDir),new String[]{"pdf","Pdf","PDF"},true).stream().collect(Collectors.toList());
        }
        return fs;
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 是否包含五书文件。有才开始生成记录。否则跳过。
     *
     * @return
     */
    private Boolean containFileFiles(int shenqinglx,String targetDir) {
        List<File> Fs=getFiveFiles(targetDir);
        if (Fs.size() == 0) return false;
        else {
            List<CPCFileType> types = getDefaultTypes(shenqinglx);
            for (File file : Fs) {
                String Name =FilenameUtils.getBaseName(file.getName());

                Optional<CPCFileType> findTypes =
                        types.stream().filter(x -> Name.equals(x.getName())).findFirst();
                Boolean X= findTypes.isPresent();
                if(X==true) return X;
            }
            return false;
        }
    }
    private cpcFiles uploadAndCreate(cpcPackageMain main,File file) throws  Exception {
        LoginUserInfo Info=CompanyContext.get();
        List<CPCFileType> types = getDefaultTypes(main.getShenqinglx());
        String fileName =FilenameUtils.getBaseName(file.getName());
        String ExtName="."+FilenameUtils.getExtension(file.getPath()).toUpperCase();
        Optional<CPCFileType> findTypes =
                types.stream().filter(x -> fileName.equals(x.getName())).findFirst();
        if(findTypes.isPresent()){
            CPCFileType fileType=findTypes.get();
            UploadUtils uploadUtils=getUtils();
            String X=UUID.randomUUID().toString();
            String uuName=X+"."+ExtName;
            uploadFileResult result=  uploadUtils.uploadAttachment(uuName,new FileInputStream(file));
            if(result.isSuccess()){
                tbAttachment newOne=new tbAttachment();
                newOne.setGuid(X);
                newOne.setName(fileType.getName());
                newOne.setUploadMan(Info.getUserIdValue());
                newOne.setUploadTime(new Date());
                newOne.setPath(result.getFullPath());
                newOne.setType(1);
                newOne.setSize(Math.toIntExact(file.length()));
                attRep.save(newOne);


                cpcFiles  f=new cpcFiles();
                f.setCode(fileType.getCode());
                f.setType(fileType.getName());
                f.setExtName(ExtName);
                f.setPages(PageSizeUtils.get(file.getPath()));
                f.setAttId(X);
                if(fileType.getCode().equals("100001")){
                    if(ExtName.equals(".PDF")) {
                        main.setItemCount(PageSizeUtils.getSectionCount(file.getPath()));
                        cpcMainRep.save(main);
                    }
                }
                return f;
            } else throw new Exception("上传"+file.getName()+"时发生了错误!");
        }else  throw new Exception("未知文件类型:"+fileName);
    }

    class CPCFileType {
        private String code;
        private String name;
        public CPCFileType(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

