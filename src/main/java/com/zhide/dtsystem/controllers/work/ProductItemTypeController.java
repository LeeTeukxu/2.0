package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.PageableUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.ProductItemTypeMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesYwAcceptRepository;
import com.zhide.dtsystem.repositorys.productItemTypeRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.repositorys.tbProductFilesRepository;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/work/productItemType")
public class ProductItemTypeController {
    @Autowired
    productItemTypeRepository proRep;
    @Autowired
    casesYwAcceptRepository accRep;
    @Autowired
    tbProductFilesRepository proFileRep;
    @Autowired
    ProductItemTypeMapper productItemTypeMapper;
    @Autowired
    tbAttachmentRepository attRep;

    @RequestMapping("/index")
    public String Index() {
        return "/work/productItemType/index";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(String Name, int pageIndex, int pageSize, String sortField, String sortOrder) {
        pageObject result = new pageObject();
        try {
            Pageable pageable = PageableUtils.create(pageIndex, pageSize, sortField, sortOrder);
            Page<productItemType> datas = null;
            if (StringUtils.isEmpty(Name)) {
                datas = proRep.findAll(pageable);
            } else datas = proRep.findAllByNameLike("%" + Name + "%", pageable);
            List<productItemType> ds=datas.getContent();
            List<String> Fs=ds.stream().map(f->f.getFid()).collect(Collectors.toList());
            List<tbProductFiles> allFiles=proFileRep.findAllByMainIdIn(Fs);
            for(int i=0;i<ds.size();i++){
                productItemType pd=ds.get(i);
                String ID=pd.getFid();
                List<tbProductFiles> pFiles=
                        allFiles.stream().filter(f->f.getMainId().equals(ID)).collect(Collectors.toList());
                List<tbProductFiles> blankFiles=
                        pFiles.stream().filter(f->f.getType().equals("blank")).collect(Collectors.toList());
                List<tbProductFiles> standardFiles=
                        pFiles.stream().filter(f->f.getType().equals("standard")).collect(Collectors.toList());
                List<tbProductFiles> normalFiles=
                        pFiles.stream().filter(f->f.getType().equals("normal")).collect(Collectors.toList());

                if(blankFiles.size()>0){
                    pd.setBlankFile(String.join(",",
                            blankFiles.stream().map(f->f.getAttId()).collect(Collectors.toList())));
                    pd.setBlankFileName(String.join(",",
                            blankFiles.stream().map(f->f.getAttName()).collect(Collectors.toList())));
                }
                if(standardFiles.size()>0){
                    pd.setStandardFile(String.join(",",
                            standardFiles.stream().map(f->f.getAttId()).collect(Collectors.toList())));
                    pd.setStandardFileName(String.join(",",
                            standardFiles.stream().map(f->f.getAttName()).collect(Collectors.toList())));
                }
                if(normalFiles.size()>0){
                    pd.setNormalFile(String.join(",",
                            normalFiles.stream().map(f->f.getAttId()).collect(Collectors.toList())));
                    pd.setNormalFileName(String.join(",",
                            normalFiles.stream().map(f->f.getAttName()).collect(Collectors.toList())));
                }
            }

            List<productItemType>  alls= proRep.findAll();
            for(int  i=0;i<alls.size();i++){
                productItemType p=alls.get(i);
                String KName=StringUtils.trim(p.getName());
                p.setName(KName);
            }
            proRep.saveAll(alls);
            result.setTotal(datas.getTotalElements());
            result.setData(ds);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult Save(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo logInfo = CompanyContext.get();
            productItemType item = JSON.parseObject(Data, productItemType.class);
            if (item.getId() == null) {
                item.setFid(UUID.randomUUID().toString());
                item.setCreateTime(new Date());
                item.setCreateMan(logInfo.getUserIdValue());
            }
            item.setName(StringUtils.trim(item.getName()));
            proRep.save(item);

           List<productItemType>  alls= proRep.findAll();
           for(int  i=0;i<alls.size();i++){
               productItemType p=alls.get(i);
               String KName=StringUtils.trim(p.getName());
               p.setName(KName);
           }
            proRep.saveAll(alls);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo logInfo = CompanyContext.get();
            List<productItemType> items = JSON.parseArray(Data, productItemType.class);
            for(int i=0;i<items.size();i++) {
                productItemType item=items.get(i);
                if (item.getId() == null) {
                    item.setFid(UUID.randomUUID().toString());
                    item.setCreateTime(new Date());
                    item.setCreateMan(logInfo.getUserIdValue());
                }
                item.setSn((i+1));
                item.setName(StringUtils.trim(item.getName()));
            }
            proRep.saveAll(items);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/remove")
    public successResult Remove(Integer id) {
        successResult result = new successResult();
        try {
            Optional<productItemType> findOne = proRep.findById(id);
            if (findOne.isPresent()) {
                productItemType one = findOne.get();
                List<casesYwAccept> Finds = accRep.findAllByYid(one.getFid());
                if (Finds.size() > 0) throw new Exception(one.getName() + "已在交单中引用，无法删除!");
                proRep.delete(one);
            } else throw new Exception("数据已不存在!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/ImportProductItemType")
    public String ImportProductItemType(String Code, String FileName, Map<String, Object> model) {
        StringBuilder sb = new StringBuilder();
        List<Map<String, String>> list = productItemTypeMapper.getProductItemTypeName();
        String str = JSON.toJSON(list).toString();
        JSONArray jsonArray = (JSONArray) JSONArray.parse(str);
        for (Object object : jsonArray) {
            JSONObject jo = (JSONObject) object;
            sb.append(jo.getString("Name") + ",");
        }
        model.put("Code", Code);
        model.put("FileName", FileName);
        model.put("AllProductItemType", sb.toString().substring(0, sb.length() - 1));
        return "/work/productItemType/ImportProductItemTypeData";
    }

    @RequestMapping("/getItems")
    @ResponseBody
    public List<ComboboxItem> getItems(){
        List<ComboboxItem> items=new ArrayList<>();
        List<productItemType> Cs=productItemTypeMapper.findDistinctByType();
        for(productItemType tb:Cs){
            ComboboxItem item=new ComboboxItem();
            item.setId(tb.getType());
            item.setText(tb.getType());
            items.add(item);
        }
        return items;
    }
    @RequestMapping("/saveSubFiles")
    @ResponseBody
    public successResult SaveSubFiles(String FID,String AttID,String Type){
        successResult result=new successResult();
        try {
            LoginUserInfo Info=CompanyContext.get();
            List<String>AttIDS= ListUtils.parse(AttID,String.class);
            List<tbProductFiles> ps=new ArrayList<>();
            for(int i=0;i<AttIDS.size();i++){
                String AID=AttIDS.get(i);
                tbProductFiles file=new tbProductFiles();
                file.setMainId(FID);
                file.setAttId(AID);
                file.setType(Type);
                file.setCreateMan(Info.getUserIdValue());
                Optional<tbAttachment> findFile=attRep.findFirstByGuid(AID);
                if(findFile.isPresent()){
                    file.setAttName(findFile.get().getName());
                }
                file.setCreateTime(new Date());
                ps.add(file);
            }
            proFileRep.saveAll(ps);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getSubFiles")
    @ResponseBody
    public successResult getSubFiles(String FID,String Type){
        successResult result=new successResult();
        try {
            List<tbProductFiles> files=proFileRep.findAllByMainIdAndType(FID,Type);
            result.setData(StringUtils.join(files.stream().map(f->f.getAttId()).collect(Collectors.toList()), ","));
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }

}

