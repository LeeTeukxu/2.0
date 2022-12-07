package com.zhide.dtsystem.controllers.cpc;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.AttachmentMainMapper;
import com.zhide.dtsystem.mapper.PantentInfoMapper;
import com.zhide.dtsystem.mapper.UploadUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.LoginUserErrorCounter;
import com.zhide.dtsystem.services.define.IAllUserListService;
import com.zhide.dtsystem.services.define.ICompanyConfigService;
import com.zhide.dtsystem.services.define.IWebAPIService;
import com.zhide.dtsystem.services.instance.govfee.feeProcessorFactory;
import com.zhide.dtsystem.viewModel.versionUpdateResult;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequestMapping("/WebAPI")
@Controller
public class CPCWebAPIController {

    @Autowired
    tbDepListRepository depListRepository;

    @Autowired
    ErrorInfoRepository errorInfoRepository;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    TZSRepository tzsRepository;

    @Autowired
    PantentInfoMapper pantentInfoMapper;

    @Autowired
    notExistCpcRepository notExistCPCRep;

    @Autowired
    notExistTzsRepository notExistTZSRep;

    @Autowired
    IAllUserListService allUserListService;

    @Autowired
    LoginUserErrorCounter errorCounter;
    @Autowired
    versionUpdateRepository versionRep;

    @Autowired
    uploadUserRepository uploadUserRep;
    @Autowired
    ICompanyConfigService companyConfigService;
    @Autowired
    UploadUserMapper uploadMapper;
    @Autowired
    AttachmentMainMapper attMainMapper;
    @Autowired
    feeProcessorFactory feeFactory;

    @Autowired
    pantentInfoRepository ptInfoRep;

    @Autowired
    StringRedisTemplate redisUtils;
    @Autowired
    IWebAPIService webAPIService;

    Logger logger = LoggerFactory.getLogger(CPCWebAPIController.class);
    @Autowired
    pantentInfoRepository pRep;

    @RequestMapping("/Login")
    @ResponseBody
    public successResult login(String username, String password, String token) {
        successResult Res = new successResult(false);
        try {
            username = username.trim();
            password = password.trim();

            errorCounter.setAccount(username);
            errorCounter.setMaxTimes(10);
            if (errorCounter.isLock() == true) {
                throw new Exception("当前用户还处理登录锁定期，无法登录系统！");
            }
            if (errorCounter.getTimes() >= 10) {
                errorCounter.lockUser();
                throw new Exception(username + "登录错误超过十次，已被锁定30分钟。");
            }


            HashMap<String, Object> O = new HashMap<>();
            String companyId = allUserListService.findCompanyIdByAccount(username);
            if (StringUtil.isNullOrEmpty(companyId)) {
                errorCounter.addOne();
                throw new Exception("登录帐号:" + username + "在系统中不存在。");
            }
            if (companyConfigService.canLogin(companyId) == false) {
                errorCounter.addOne();
                throw new Exception("本公司已被管理员设置为不可登录状态!");
            }
            if (companyConfigService.isExpired(companyId)) {
                errorCounter.addOne();
                throw new Exception("已超出了服务期限，无法登录系统，请联系管理员解决!");
            }
            if (uploadMapper.IsCPCUser(companyId, username) == false) {
                errorCounter.addOne();
                throw new Exception(username + "不是指定的CPC信息上传专用帐号，无法进行登录操作,联系管理员进行设置!");
            }

            LoginUserInfo loginUser = allUserListService.findUserInfoByAccountAndCompanyID("Client_" + companyId,
                    username);
            if (loginUser == null) {
                errorCounter.addOne();
                throw new Exception(username + "设置不完整，请检查该用户的部门或权限设置是否正确！");
            }
            if (loginUser.isCanLogin() == false) {
                errorCounter.addOne();
                throw new Exception(username + "已被限制登录系统。");
            }
            /*if (StringUtil.isNullOrEmpty(token) == true) {
                if (CompanyTokenUtils.existToken(companyId, username)) {
                    errorCounter.addOne();
                    throw new Exception(username + "已登录系统,不可重复登录!");
                }
            }*/

            String vPassword = MD5Util.EnCode(password);
            if (loginUser.getPassword().equals(vPassword) == false) {
                errorCounter.addOne();
                throw new Exception(username + "登录时密码不正确!");
            }

            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String VX = CompanyTokenUtils.getOrCreateToken(companyId, username);
            O.put("CompanyID", companyId);
            O.put("Token", VX);
            O.put("EndTime", simple.format(DateUtils.addDays(new Date(), 30)));

            Res.setData(O);
            logger.info(username + "登录成功!");
        } catch (Exception ax) {
            Res.raiseException(ax);
        }
        return Res;
    }

    @ResponseBody
    @RequestMapping("/HeartBeat")
    public successResult heartBeat(String webToken) {
        successResult res = new successResult(false);
        try {
            Integer x = CompanyTokenUtils.refreshToken(webToken);
            if (x == 0) {
                res.setSuccess(false);
            } else {
                Date tEnd = DateUtils.addSeconds(new Date(), x - 10);
                SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                res.setData(simple.format(tEnd));
            }
        } catch (Exception ax) {
            res.setSuccess(false);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/addClientError")
    public successResult addClientError(String Data, String webToken) {
        successResult result = new successResult(false);
        try {
            errorInfo info = JSON.parseObject(Data, errorInfo.class);
            if (CompanyTokenUtils.existToken(webToken)) {
                logger.info(info.getError());
                errorInfoRepository.save(info);
            } else {
                throw new RuntimeException("Token已失效，请重新登录客户端 ");
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/GetNotUploadTZS")
    @ResponseBody
    public successResult getNotUploadTZS() {
        successResult result = new successResult(false);
        try {

            List<String> ts = webAPIService.GetNotUploadTZS();
            result.setData(ts);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/GetNotUploadCPC")
    public successResult getNotUploadCPC() {
        successResult result = new successResult(false);
        try {
            result.setData(webAPIService.getNotUploadCPC());
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addNotExistCPC")
    public successResult addNotExistCPC(@RequestParam(value = "IDS") List<String> IDS) {
        successResult result = new successResult(false);
        try {
            for (int i = 0; i < IDS.size(); i++) {
                String ID = IDS.get(i);
                notExistCpc notCpc = new notExistCpc();
                notCpc.setCpcid(ID);
                notCpc.setCreateTime(new Date());
                notExistCPCRep.save(notCpc);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addNotExistTZS")
    public successResult addNotExistTZS(@RequestParam List<String> IDS) {
        successResult result = new successResult(false);
        try {
            for (int i = 0; i < IDS.size(); i++) {
                String ID = IDS.get(i);
                notExistTzs notTzs = new notExistTzs();
                notTzs.setTzsid(ID);
                notTzs.setCreateTime(new Date());
                notExistTZSRep.save(notTzs);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("getSqlVersionUpdate")
    @ResponseBody
    public successResult getSqlVersionUpdate(String account, Integer maxVersion) {
        successResult result = new successResult(false);
        try {
            List<versionUpdateResult> os = webAPIService.getSqlVersionUpdate(account, maxVersion);
            result.setData(os);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getSoftVersion")
    @ResponseBody
    public successResult getSoftVersion(int curVer) {
        successResult result = new successResult(false);
        try {
            attachmentMain main = webAPIService.getSoftVersion(curVer);
            if (main != null) {
                result.setData(main);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/downloadUpdate")
    public void DownloadUpdate(String AttID, HttpServletResponse response) {
        try {
            FTPUtil ftpUtil = new FTPUtil();
            if (ftpUtil.connect() == true) {
                String DownloadPath = CompanyPathUtils.getFullPath("Temp", AttID + ".zip");
                ftpUtil.download("/Update/" + AttID + ".zip", DownloadPath);
                WebFileUtils.download(AttID + ".zip", new File(DownloadPath), response);
            }
        } catch (Exception ax) {
            ax.printStackTrace();
        }
    }

    @RequestMapping("/finishDownload")
    @ResponseBody
    public successResult finishDownload(String AttID) {
        successResult result = new successResult(false);
        try {
            attachmentMain main = attMainMapper.getbyAttID(AttID);
            LoginUserInfo info = CompanyContext.get();

            attachmentDetail detail = new attachmentDetail();
            detail.setMainId(main.getId());
            detail.setCompanyId(info.getCompanyId());
            detail.setDownloadTime(new Date());
            attMainMapper.addDetail(detail);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/finishUpdate")
    @ResponseBody
    public successResult finishUpdate(String AttID, String Result) {
        successResult result = new successResult(false);
        try {
            LoginUserInfo info = CompanyContext.get();
            attachmentMain main = attMainMapper.getbyAttID(AttID);
            attachmentDetail detail = attMainMapper.getbyMainIDAndCompanyID(main.getId(), info.getCompanyId());
            detail.setUpdateResult(Result);
            detail.setUpdateTime(new Date());
            attMainMapper.updateDetail(detail);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changeSqlVersionStatus")
    @ResponseBody
    public successResult changeSqlVersionStatus(String account, Integer verId, String updateTime, String result) {
        successResult rr = new successResult(false);
        try {
            Optional<versionUpdate> finds = versionRep.findFirstByAccountAndVerId(account, verId);
            if (finds.isPresent()) {
                SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dx = simple.parse(updateTime);
                versionUpdate One = finds.get();
                One.setHasProcess(true);
                One.setProcessTime(dx);
                One.setResult(result);
                versionRep.save(One);
            } else throw new Exception(verId + "指向数据不存在!");
        } catch (Exception ax) {
            rr.raiseException(ax);
        }
        return rr;
    }

    @ResponseBody
    @RequestMapping("/loginOut")
    public successResult loginOut(String webToken) {
        successResult result = new successResult(false);
        try {
            if (CompanyTokenUtils.existToken(webToken)) {
                CompanyTokenUtils.deleteByToken(webToken);
                errorCounter.clear();
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getUploadProcess")
    @ResponseBody
    public successResult getUploadProcess() {
        successResult result = new successResult(false);
        try {
            uploadProcessInfo info = webAPIService.getUploadProcess();
            result.setData(info);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/IsLegalLogin")
    public successResult IsLegalLogin(String webToken) {
        successResult result = new successResult(false);
        try {
            boolean ok = CompanyTokenUtils.existToken(webToken);
            result.setSuccess(ok);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/acceptGovFee")
    public successResult AcceptGovFee(String Data) {
        successResult result = new successResult(false);
        try {
            if (StringUtils.isEmpty(Data) == true) throw new IllegalArgumentException("提交的官费更新数据不能为空!");
            List<patentGovFee> items = JSON.parseArray(Data, patentGovFee.class);
            if (items == null) items = new ArrayList<>();
            if (items.size() > 0) {
                LoginUserInfo Info = CompanyContext.get();
                redisUtils.opsForList().rightPush("AcceptGovFee::" + Info.getCompanyId(), Data);
            } else throw new IllegalArgumentException("提交的数据中不包含官费更新数据!");
        } catch (Exception ax) {
            result.raiseException(ax);
            logger.info("AcceptGovFee提交数据异常:" + ax.getMessage());
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/acceptPatentInfo")
    public successResult AcceptGovPatentInfo(String Data) {
        successResult result = new successResult(false);
        try {
            if (StringUtils.isEmpty(Data)) throw new IllegalArgumentException("更新专利信息内容不能为空!");
            GovPatentInfo PInfo = JSON.parseObject(Data, GovPatentInfo.class);
            String AppNo = PInfo.getAppNo();
            if (StringUtils.isEmpty(AppNo) == true) throw new IllegalArgumentException("提交的专利数据中专利号不能为空!");
            LoginUserInfo Info = CompanyContext.get();
            redisUtils.opsForList().rightPush("AcceptPatentInfo::" + Info.getCompanyId(), Data);
        } catch (Exception ax) {
            result.raiseException(ax);
            logger.info("AcceptGovPatentInfo提交数据异常:" + ax.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllPantentCode")
    public successResult getAllPantentCode() {
        successResult result = new successResult(false);
        try {
            List<String> res = new ArrayList<>();
            LoginUserInfo Info = CompanyContext.get();
            String overflowKey = Info.getCompanyId() + "::AllCodesOverflow";
            if (redisTemplate.hasKey(overflowKey)) {
                result.setData(res);
                return result;
            }
            int pageIndex = 0;
            String sKey = Info.getCompanyId() + "::AllCodePageIndex";
            if (redisTemplate.hasKey(sKey)) {
                pageIndex = Integer.parseInt(redisTemplate.opsForValue().get(sKey));
            }
            res = pRep.getAllCodes(pageIndex);
            if (res.size() > 0) {
                pageIndex = pageIndex + 1;
                redisTemplate.opsForValue().set(sKey, Integer.toString(pageIndex), getFreeNum(), TimeUnit.MILLISECONDS);
                logger.info("getAllPantentCode执行到分页查询，当前是第:" + Integer.toString(pageIndex) + "页。,共:" + Integer.toString(res.size()) + "条记录!");
            } else {
                redisTemplate.delete(sKey);
                redisTemplate.opsForValue().set(overflowKey, LocalDateTime.now().toString(),1, TimeUnit.HOURS);
                res = new ArrayList<>();
            }
            result.setData(res);
        } catch (Exception ax) {
            result.raiseException(ax);
            logger.info("getAllPantentCode查询数据异常:" + ax.getMessage());
        }
        return result;
    }

    private Long getFreeNum() {
        Date now = new Date();
        Long Begin = now.getTime();
        now.setHours(23);
        now.setMinutes(59);
        now.setSeconds(59);
        Long End = now.getTime();
        return End - Begin;
    }
}
