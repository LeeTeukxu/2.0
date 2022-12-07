package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.AttachmentMainMapper;
import com.zhide.dtsystem.mapper.PantentInfoMapper;
import com.zhide.dtsystem.mapper.UploadProcessMapper;
import com.zhide.dtsystem.models.CacheableTtl;
import com.zhide.dtsystem.models.attachmentMain;
import com.zhide.dtsystem.models.uploadProcessInfo;
import com.zhide.dtsystem.models.versionUpdate;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.versionUpdateRepository;
import com.zhide.dtsystem.services.define.IWebAPIService;
import com.zhide.dtsystem.viewModel.versionUpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: WebAPIServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月19日 21:30
 **/
@Service
public class WebAPIServiceImpl implements IWebAPIService {
    @Autowired
    PantentInfoMapper pantentInfoMapper;
    @Autowired
    TZSRepository tzsRep;
    @Autowired
    AttachmentMainMapper attMainMapper;
    @Autowired
    versionUpdateRepository versionRep;
    @Autowired
    UploadProcessMapper processMapper;
    @Override
    @CacheableTtl(value = "getNotUploadCPC",
            keyGenerator = "CompanyKeyGenerator",
            cacheResolver="ttlCacheResolver",
            ttl =3600,unless = "#result?.size()>0")
    public List<String> getNotUploadCPC() {
        return pantentInfoMapper.getNotUploadCPC();
    }

    @Override
    @CacheableTtl(value = "getNotUploadTZS",
            keyGenerator = "CompanyKeyGenerator",
            cacheResolver="ttlCacheResolver",
            ttl =3600,unless = "#result?.size()>0")
    public List<String> GetNotUploadTZS() {
        return tzsRep.getNotuploadTZS();
    }

    @Override
    @CacheableTtl(value = "getSoftVersion",
            keyGenerator = "CompanyKeyGenerator",
            cacheResolver="ttlCacheResolver",
            ttl =3600)
    public attachmentMain getSoftVersion(int curVer) {
        List<attachmentMain> mains = attMainMapper.getList().stream()
                .filter(f -> f.getVersion() > curVer)
                .filter(f -> f.getCanUse() == true)
                .sorted(Comparator.comparingInt(attachmentMain::getVersion).reversed())
                .collect(toList());
        if (mains.size() > 0) {
            return mains.get(0);
        } else return null;
    }

    @Override
    @CacheableTtl(value = "getSqlVersionUpdate",
            keyGenerator = "CompanyKeyGenerator",
            cacheResolver="ttlCacheResolver",
            ttl =1200)
    public List<versionUpdateResult> getSqlVersionUpdate(String account, Integer maxVersion) {
        List<versionUpdate> findAll = versionRep.getAllByVerIdGreaterThanAndCanUseIsTrue(maxVersion);
        List<versionUpdateResult> os = findAll.stream().filter(f -> f.getAccount().equals(account)).map(f -> {
            versionUpdateResult rs = new versionUpdateResult();
            rs.setVerId(f.getVerId());
            rs.setProcessResult(false);
            rs.setSqlText(f.getSqlText());
            rs.setCreateTime(f.getCreateTime());
            rs.setNeedLogin(f.getNeedLogin());
            rs.setAccount(account);
            return rs;
        }).collect(toList());
        return os ;
    }

    @Override
    @CacheableTtl(value = "getSqlVersionUpdate",
            keyGenerator = "CompanyKeyGenerator",
            cacheResolver="ttlCacheResolver",
            ttl =150)
    public uploadProcessInfo getUploadProcess() {
        Map<String, Object> tzs = processMapper.getTZS();
        Map<String, Object> cpc = processMapper.getCPC();
        uploadProcessInfo info = new uploadProcessInfo();
        info.setTzsTotal(Integer.parseInt(tzs.get("AllNum").toString()));
        info.setTzsUpload(Integer.parseInt(tzs.get("UpNum").toString()));

        info.setCpcTotal(Integer.parseInt(cpc.get("AllNum").toString()));
        info.setCpcUpload(Integer.parseInt(cpc.get("UpNum").toString()));
        return info;
    }
}
