package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.UploadUtils;
import com.zhide.dtsystem.common.uploadFileResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IAddSingleMemoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: AddSingleMemoServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年10月20日 14:21
 **/
@Service
public class AddSingleMemoServiceImpl implements IAddSingleMemoService {
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    casesMainRepository mainRep;
    @Autowired
    caseHighMainRepository highMainRep;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    caseHighSubRepository highSubRep;
    @Autowired
    casesOutSourceMainRepository outMainRep;
    @Autowired
    casesYwItemsRepository ywRep;
    @Autowired
    tbImageMemoRepository imageRep;
    @Autowired
    casesMemoRepository memoRep;

    @Override
    @Transactional
    public void SaveImage(simpleMemo memo) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        String imageData = memo.getImageData();
        if (StringUtils.isEmpty(imageData)) throw new Exception("图片数据为空，提交数据异常!");
        if (imageData.length() < 200) throw new Exception("图片数据异常，保存操作被中止!");
        UploadUtils uploadUtils = UploadUtils.getInstance(Info.getCompanyId());
        byte[] Bs = Base64Utils.decodeFromString(imageData.substring(imageData.indexOf("base64,") + 7));
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
            newOne.setSize(imageData.length());
            attRep.save(newOne);

            SaveImageContent(newOne.getGuid(), memo);
        }
    }

    @Override
    @Transactional
    public void SaveMemo(String SubID, List<simpleMemo> postMemos) throws Exception {
        List<String> IDS = Arrays.asList(SubID);
        if (SubID.indexOf(",") > -1) IDS = Arrays.stream(SubID.split(",")).collect(Collectors.toList());
        for (String ID : IDS) {
            postMemos.stream().forEach(f -> f.setPid(ID));
            SaveSingleMemo(ID, postMemos);
        }
    }

    private void SaveSingleMemo(String SubID, List<simpleMemo> postMemos) {
        Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
        Optional<caseHighSub> findHighSubs = highSubRep.findFirstBySubId(SubID);
        Optional<caseHighMain> findHighMains = highMainRep.findFirstByCasesId(SubID);
        Optional<casesOutSourceMain> findOuts = outMainRep.findFirstByOutId(SubID);
        Optional<casesYwItems> findYws = ywRep.findFirstBySubId(SubID);
        if (findOuts.isPresent()) {
            casesOutSourceMain main = findOuts.get();
            String X = main.getProcessText();
            List<simpleMemo> saveMemos = reBuildMemo1(postMemos, X);
            main.setProcessText(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            outMainRep.save(main);
        } else if (findSubs.isPresent()) {
            casesSub subOne = findSubs.get();
            String Y = subOne.getProcessText();
            List<simpleMemo> saveSubMemos = reBuildMemo1(postMemos, Y);
            subOne.setProcessText(JSON.toJSONStringWithDateFormat(saveSubMemos, "yyyy-MM-dd HH:mm:ss"));
            subRep.save(subOne);
        } else if (findYws.isPresent()) {
            casesYwItems ywOne = findYws.get();
            String Y = ywOne.getMemo();
            List<simpleMemo> saveYwMemos = reBuildMemo1(postMemos, Y);
            ywOne.setMemo(JSON.toJSONStringWithDateFormat(saveYwMemos, "yyyy-MM-dd HH:mm:ss"));
            ywRep.save(ywOne);
        } else if (findHighSubs.isPresent()) {
            caseHighSub subOne = findHighSubs.get();
            String Y = subOne.getProcessText();
            List<simpleMemo> saveSubMemos = reBuildMemo1(postMemos, Y);
            subOne.setProcessText(JSON.toJSONStringWithDateFormat(saveSubMemos, "yyyy-MM-dd HH:mm:ss"));
            highSubRep.save(subOne);
        } else if (findHighMains.isPresent()) {
            caseHighMain subOne = findHighMains.get();
            String Y = subOne.getProcessText();
            List<simpleMemo> saveSubMemos = reBuildMemo1(postMemos, Y);
            subOne.setProcessText(JSON.toJSONStringWithDateFormat(saveSubMemos, "yyyy-MM-dd HH:mm:ss"));
            highMainRep.save(subOne);
        }
    }

    @Override
    public List<simpleMemo> getData(String SubID) throws Exception {
        String X = "";
        if (StringUtils.isEmpty(SubID)) SubID = "";
        if (SubID.indexOf(",") == -1) {
            Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
            Optional<caseHighSub> findHighSubs = highSubRep.findFirstBySubId(SubID);
            Optional<caseHighMain> findHighMains = highMainRep.findFirstByCasesId(SubID);
            Optional<casesOutSourceMain> findOuts = outMainRep.findFirstByOutId(SubID);
            Optional<casesYwItems> findYws = ywRep.findFirstBySubId(SubID);
            if (findSubs.isPresent()) {
                casesSub sub1 = findSubs.get();
                X = sub1.getProcessText();
            } else if (findHighSubs.isPresent()) {
                caseHighSub sub2 = findHighSubs.get();
                X = sub2.getProcessText();
            } else if (findHighMains.isPresent()) {
                caseHighMain sub5 = findHighMains.get();
                X = sub5.getProcessText();
            } else if (findOuts.isPresent()) {
                casesOutSourceMain sub3 = findOuts.get();
                X = sub3.getProcessText();
            } else if (findYws.isPresent()) {
                casesYwItems sub4 = findYws.get();
                X = sub4.getMemo();
            }
        }
        if (StringUtils.isEmpty(X)) X = "[]";
        return JSON.parseArray(X, simpleMemo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void RemoveMemo(String SubID, List<String> IDS) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
        Optional<caseHighSub> findHighSubs = highSubRep.findFirstBySubId(SubID);
        Optional<caseHighMain> findHighMains = highMainRep.findFirstByCasesId(SubID);
        Optional<casesOutSourceMain> findOuts = outMainRep.findFirstByOutId(SubID);
        Optional<casesYwItems> findYws = ywRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesSub sub = findSubs.get();
            String ProcessText = sub.getProcessText();
            List<simpleMemo> saves = deleteMemos(ProcessText, IDS);
            String V = JSON.toJSONString(saves);
            sub.setProcessText(V);
            subRep.save(sub);
        } else if (findHighSubs.isPresent()) {
            caseHighSub sub = findHighSubs.get();
            String ProcessText = sub.getProcessText();
            List<simpleMemo> saves = deleteMemos(ProcessText, IDS);
            String V = JSON.toJSONString(saves);
            sub.setProcessText(V);
            highSubRep.save(sub);
        } else if (findHighMains.isPresent()) {
            caseHighMain sub = findHighMains.get();
            String ProcessText = sub.getProcessText();
            List<simpleMemo> saves = deleteMemos(ProcessText, IDS);
            String V = JSON.toJSONString(saves);
            sub.setProcessText(V);
            highMainRep.save(sub);
        } else if (findOuts.isPresent()) {
            casesOutSourceMain sub = findOuts.get();
            String ProcessText = sub.getProcessText();
            List<simpleMemo> saves = deleteMemos(ProcessText, IDS);
            String V = JSON.toJSONString(saves);
            sub.setProcessText(V);
            outMainRep.save(sub);
        } else if (findYws.isPresent()) {
            casesYwItems sub = findYws.get();
            String ProcessText = sub.getMemo();
            List<simpleMemo> saves = deleteMemos(ProcessText, IDS);
            String V = JSON.toJSONString(saves);
            sub.setMemo(V);
            ywRep.save(sub);
        }
    }

    private List<simpleMemo> deleteMemos(String ProcessText, List<String> IDS) {
        List<simpleMemo> memos = new ArrayList<>();
        if (StringUtils.isEmpty(ProcessText) == false) {
            memos = JSON.parseArray(ProcessText, simpleMemo.class);
            for (String ID : IDS) {
                Optional<simpleMemo> findOnes = memos.stream().filter(f -> f.getId().equals(ID)).findFirst();
                if (findOnes.isPresent()) {
                    memos.remove(findOnes.get());
                }
            }
        }
        return memos;
    }

    private void SaveImageContent(String AttID, simpleMemo memo) throws Exception {
        List<simpleMemo> allMemos = new ArrayList<>();
        String Y = JSON.toJSONString(memo);
        if (memo.getPid().indexOf(",") > -1) {
            String[] IDS = memo.getPid().split(",");
            for (String ID : IDS) {
                simpleMemo V = JSON.parseObject(Y, simpleMemo.class);
                V.setId("");
                V.setPid(ID);
                V.setXid(null);
                allMemos.add(V);
            }
        } else allMemos.add(memo);
        for (simpleMemo aMemo : allMemos) {
            SaveSingle(AttID, aMemo);
        }
    }

    private void SaveSingle(String AttID, simpleMemo memo) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Optional<casesSub> findSubs = subRep.findFirstBySubId(memo.getPid());
        Optional<caseHighSub> findHighSubs = highSubRep.findFirstBySubId(memo.getPid());
        Optional<caseHighMain> findHighMains = highMainRep.findFirstByCasesId(memo.getPid());
        Optional<casesOutSourceMain> findOuts = outMainRep.findFirstByOutId(memo.getPid());
        Optional<casesYwItems> findYws = ywRep.findFirstBySubId(memo.getPid());
        List<casesMemo> findMemos = memoRep.findAllByCasesid(memo.getPid());
        String XID = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(memo.getId())) {
            memo.setId(XID);
        }
        tbImageMemo newImage = new tbImageMemo();
        newImage.setPid(memo.getId());
        ;
        newImage.setAttId(AttID);
        newImage.setMemo(memo.getMemo());
        newImage.setCreateManName(Info.getUserName());
        newImage.setCreateTime(new Date());
        imageRep.save(newImage);

        List<tbImageMemo> images = imageRep.findAllByPid(memo.getId());
        Integer Index = 1;
        List<String> XS = new ArrayList<>();
        for (tbImageMemo image : images) {
            String mText = image.getMemo();
            if (StringUtils.isEmpty(mText) == false) {
                XS.add(Integer.toString(Index) + "、" + mText);
                Index++;
            }
        }
        memo.setMemo(String.join("<br/>", XS));
        List<simpleMemo> postMemos = Arrays.asList(memo);
        if (findSubs.isPresent()) {
            casesSub sub1 = findSubs.get();
            String ProcessText = sub1.getProcessText();
            List<simpleMemo> saveMemos = reBuildMemo(postMemos, ProcessText);
            sub1.setProcessText(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            subRep.save(sub1);
        } else if (findHighSubs.isPresent()) {
            caseHighSub sub2 = findHighSubs.get();
            String ProcessText = sub2.getProcessText();
            List<simpleMemo> saveMemos = reBuildMemo(postMemos, ProcessText);
            sub2.setProcessText(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            highSubRep.save(sub2);
        } else if (findHighMains.isPresent()) {
            caseHighMain sub5 = findHighMains.get();
            String ProcessText = sub5.getProcessText();
            List<simpleMemo> saveMemos = reBuildMemo(postMemos, ProcessText);
            sub5.setProcessText(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            highMainRep.save(sub5);
        } else if (findOuts.isPresent()) {
            casesOutSourceMain sub3 = findOuts.get();
            String ProcessText = sub3.getProcessText();
            List<simpleMemo> saveMemos = reBuildMemo(postMemos, ProcessText);
            sub3.setProcessText(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            outMainRep.save(sub3);
        } else if (findYws.isPresent()) {
            casesYwItems sub4 = findYws.get();
            String ProcessText = sub4.getMemo();
            List<simpleMemo> saveMemos = reBuildMemo(postMemos, ProcessText);
            sub4.setMemo(JSON.toJSONStringWithDateFormat(saveMemos, "yyyy-MM-dd HH:mm:ss"));
            ywRep.save(sub4);
        } else if (findMemos.size() > 0) {
            String SubID = memo.getId();
            String X = "";
            Optional<casesMemo> sub6s = findMemos.stream().filter(f -> f.getSubId().equals(SubID)).findFirst();
            if (sub6s.isPresent()) {
                casesMemo sub6 = sub6s.get();
                simpleMemo s = new simpleMemo();
                s.setMemo(sub6.getMemo());
                s.setPid(sub6.getCasesid());
                s.setId(sub6.getSubId());
                s.setImageData(sub6.getImageData());
                s.setCreateMan(sub6.getCreateMan());
                s.setUpdateMan(sub6.getUpdateMan());
                s.setCreateTime(sub6.getCreateTime());
                s.setUpdateTime(sub6.getUpdateTime());
                X = JSON.toJSONString(Arrays.asList(s));
            } else X = "[]";
            List<simpleMemo> saveMemos = reBuildMemo(postMemos, X);
            List<casesMemo> nows = new ArrayList<>();
            for (int i = 0; i < saveMemos.size(); i++) {
                simpleMemo m1 = saveMemos.get(i);
                String subId = m1.getId();
                if (StringUtils.isEmpty(subId)) subId = UUID.randomUUID().toString();
                Optional<casesMemo> fMemos = memoRep.findFirstBySubId(subId);
                if (fMemos.isPresent()) {
                    casesMemo old = fMemos.get();
                    old.setMemo(m1.getMemo());
                    old.setUpdateMan(Info.getUserIdValue());
                    old.setUpdateTime(new Date());
                    nows.add(old);
                } else {
                    casesMemo newOne = new casesMemo();
                    newOne.setMemo(m1.getMemo());
                    newOne.setCasesid(m1.getPid());
                    newOne.setSubId(subId);
                    newOne.setCreateMan(Info.getUserIdValue());
                    newOne.setCreateTime(new Date());
                    newOne.setImageData("1");
                    nows.add(newOne);
                }
            }
            memoRep.saveAll(nows);
        }
    }

    private List<simpleMemo> reBuildMemo(List<simpleMemo> postMemos, String X) {
        LoginUserInfo Info = CompanyContext.get();
        List<simpleMemo> saveMemos = JSON.parseArray(X, simpleMemo.class);
        if (saveMemos == null) saveMemos = new ArrayList<>();
        for (int i = 0; i < postMemos.size(); i++) {
            simpleMemo simple = postMemos.get(i);
            String ID = simple.getId();
            Optional<simpleMemo> cOnes = saveMemos.stream().filter(f -> f.getId().equals(ID)).findFirst();
            if (cOnes.isPresent()) {
                simpleMemo saveOne = cOnes.get();
                saveOne.setMemo(simple.getMemo());
                saveOne.setUpdateMan(Info.getUserIdValue());
                saveOne.setUpdateManName(Info.getUserName());
                saveOne.setUpdateTime(new Date());
            } else {
                simple.setCreateMan(Info.getUserIdValue());
                simple.setCreateManName(Info.getUserName());
                simple.setCreateTime(new Date());
                simple.setImageData("1");
                saveMemos.add(simple);
            }
        }
        return saveMemos;
    }

    private List<simpleMemo> reBuildMemo1(List<simpleMemo> postMemos, String X) {
        LoginUserInfo Info = CompanyContext.get();
        if(StringUtils.isEmpty(X))X="[]";
        List<simpleMemo> saveMemos = JSON.parseArray(X, simpleMemo.class);
        if (saveMemos == null) saveMemos = new ArrayList<>();
        for (int i = 0; i < postMemos.size(); i++) {
            simpleMemo simple = postMemos.get(i);
            if(StringUtils.isEmpty(simple.getId())) simple.setId(UUID.randomUUID().toString());
            String ID = simple.getId();
            Optional<simpleMemo> cOnes = saveMemos.stream().filter(f -> f.getId().equals(ID)).findFirst();
            if (cOnes.isPresent()) {
                simpleMemo saveOne = cOnes.get();
                saveOne.setUpdateMan(Info.getUserIdValue());
                saveOne.setUpdateManName(Info.getUserName());
                saveOne.setUpdateTime(new Date());
                saveOne.setMemo(simple.getMemo());
            } else {
                simple.setCreateMan(Info.getUserIdValue());
                simple.setCreateManName(Info.getUserName());
                simple.setCreateTime(new Date());
                simple.setImageData("0");
                simple.setId(ID);
                saveMemos.add(simple);
            }
        }
        return saveMemos;
    }
}
