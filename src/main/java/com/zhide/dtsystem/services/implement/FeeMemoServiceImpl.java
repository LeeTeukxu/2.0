package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.feeMemo;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.models.tbImageMemo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.feeMemoRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.repositorys.tbImageMemoRepository;
import com.zhide.dtsystem.services.define.IFeeMemoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeeMemoServiceImpl implements IFeeMemoService {
    @Autowired
    feeMemoRepository feeMemoRep;

    @Override
    public boolean SaveAll(List<feeMemo> memoList) {
        LoginUserInfo Info = CompanyContext.get();
        for (int i = 0; i < memoList.size(); i++) {
            feeMemo fee = memoList.get(i);
            if (fee.getId() == null) {
                fee.setCreateMan(Info.getUserIdValue());
                fee.setCreateTime(new Date());
            } else {
                fee.setUpdateMan(Info.getUserIdValue());
                fee.setUpdateTime(new Date());
            }
            feeMemoRep.save(fee);
        }
        return true;
    }

    @Override
    public List<feeMemo> GetData(int ID, String Type) {
        List<feeMemo> items = feeMemoRep.getAllByFeeIdAndType(ID, Type);
        for (int i = 0; i < items.size(); i++) {
            feeMemo item = items.get(i);
            int Days = DateTimeUtils.getDays(item.getCreateTime(), new Date());
            if (Days > 2) {
                item.setEdit(0);
            } else item.setEdit(1);
        }
        return items;
    }

    @Override
    public boolean DeleteByID(int ID) {
        feeMemoRep.deleteById(ID);
        return true;
    }
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    tbImageMemoRepository imageRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveImage(feeMemo memo) throws Exception {
        LoginUserInfo Info=CompanyContext.get();
        String ImageData=memo.getImageData();
        if(ImageData.length()>200){
            UploadUtils uploadUtils= UploadUtils.getInstance(Info.getCompanyId());
            byte[] Bs = Base64Utils.decodeFromString(ImageData.substring(ImageData.indexOf("base64,")+7));
            ByteArrayInputStream inputStream=new ByteArrayInputStream(Bs);
            uploadFileResult result= uploadUtils.uploadAttachment(UUID.randomUUID().toString()+".jpg",inputStream);
            if(result.isSuccess()){
                tbAttachment newOne=new tbAttachment();
                newOne.setName(Long.toString(System.currentTimeMillis())+".jpg");
                newOne.setType(1);
                newOne.setGuid(UUID.randomUUID().toString());
                newOne.setPath(result.getFullPath());
                newOne.setUploadMan(Info.getUserIdValue());
                newOne.setUploadManName(Info.getUserName());
                newOne.setUploadTime(new Date());
                newOne.setSize(ImageData.length());
                attRep.save(newOne);

                SaveImageObjects(Info,newOne.getGuid(),memo);
            } else throw new Exception("上传图片文件失败，操作被中止!");
        }
    }
    private feeMemo SaveImageObjects(LoginUserInfo Info,String AttID,feeMemo oneMemo) throws Exception{
        String Mx=oneMemo.getMemo();
        String sss= oneMemo.getIDS();
        if(StringUtils.isEmpty(sss))sss=Integer.toString(oneMemo.getFeeId());
        String Type=oneMemo.getType();
        feeMemo targetMemo=null;
        List<String>IDS= Arrays.stream(sss.split(",")).collect(Collectors.toList());
        for(String FeeID:IDS){
            feeMemo memo= ListUtils.clone(Arrays.asList(oneMemo)).get(0);
            String FID=memo.getFid();
            if(StringUtils.isEmpty(FID)){
                memo.setFid(UUID.randomUUID().toString());
                memo.setImageData("1");
                memo.setFeeId(Integer.parseInt(FeeID));
                memo.setCreateTime(new Date());
                memo.setCreateMan(Info.getUserIdValue());
                feeMemoRep.save(memo);
                targetMemo=memo;
            } else {
                List<feeMemo> findMemos=feeMemoRep.getAllByFeeIdAndType(Integer.parseInt(FeeID),Type);
                if(findMemos.size()>0){
                    feeMemo memo1=findMemos.get(0);
                    memo1.setMemo("");
                    memo1.setImageData("1");
                    memo1.setUpdateTime(new Date());
                    memo1.setUpdateMan(Info.getUserIdValue());
                    feeMemoRep.save(memo1);
                    targetMemo=memo1;
                }
            }
            tbImageMemo newImage=new tbImageMemo();
            newImage.setPid(targetMemo.getFid());
            newImage.setAttId(AttID);
            newImage.setMemo(Mx);
            newImage.setCreateManName(Info.getUserName());
            newImage.setCreateTime(new Date());
            imageRep.save(newImage);

            List<tbImageMemo> images=imageRep.findAllByPid(memo.getFid());
            Integer Index=1;
            List<String> XS=new ArrayList<>();
            for(tbImageMemo image:images){
                String mText=image.getMemo();
                if(StringUtils.isEmpty(mText)==false){
                    XS.add(Integer.toString(Index)+"、"+mText);
                    Index++;
                }
            }
            targetMemo.setMemo(String.join("<br/>",XS));
            feeMemoRep.save(targetMemo);
        }
        return oneMemo;
    }

    @Override
    public List<String> getImages(String MID) throws Exception {
        List<String> res=new ArrayList<>();
        List<tbImageMemo> images= imageRep.findAllByPid(MID);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日");
        for(tbImageMemo image :images){
            String attId=image.getAttId();
            Optional<tbAttachment> findAtts=attRep.findAllByGuid(attId);
            if(findAtts.isPresent()){
                tbAttachment att = findAtts.get();
                String fullPath=att.getPath();
                String fileName=UUID.randomUUID().toString()+".jpg";
                String uId= UUID.randomUUID().toString();
                String tempDir= Paths.get(CompanyPathUtils.getImages(),uId).toString();
                String tempFileName = Paths.get(tempDir, fileName).toString();

                DirectoryUtils.createNotExists(tempDir);
                String filePath = att.getPath();
                FTPUtil Ftp = new FTPUtil();
                if (Ftp.connect() == true) {
                    Ftp.download(filePath, tempFileName);
                    File tempFile = new File(tempFileName);
                    if (tempFile.exists()) {
                        String Memo=image.getMemo();
                        if(StringUtils.isEmpty(Memo))Memo="无备注"; else Memo="备注:"+Memo;
                        Memo=Memo.replace("|","");
                        Memo=att.getUploadManName()+"于:"+simpleDateFormat.format(att.getUploadTime())+"上传,"+Memo;
                        res.add("/images/"+uId+"/"+tempFile.getName()+"|"+Memo);
                    } else  throw new Exception("指定的文件在服务器上不存在,下载失败!");
                } else throw new Exception("连接Ftp服务器出错!");
            } else   throw new Exception("指定的文件在服务器上不存在,下载失败!");
        }
        return res;
    }
}
