package com.zhide.dtsystem.services.instance.cpcPackage.processor;

import com.zhide.dtsystem.models.cpcFiles;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.services.instance.cpcPackage.ICPCFileProcessor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @ClassName: A10000701
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月27日 16:25
 **/
@Component
public class A10000701 extends ICPCFileProcessor {
    @Autowired
    tbAttachmentRepository attRep;

    @Override
    public String getCode() {
        return "10000701";
    }

    @Override
    public void baseCopy(cpcFiles file, String targetDir) throws Exception {
        Optional<tbAttachment> findAtts=attRep.findAllByGuid(file.getAttId());
        if(findAtts.isPresent()) {
            tbAttachment attachment=findAtts.get();
            String extName = file.getExtName();
            String findFile = file.getCode() + extName;
            String sourceFile = Paths.get(targetDir, findFile).toString();
            String targetFile = Paths.get(targetDir, "100007", attachment.getName()).toString();
            File sFile = new File(sourceFile);
            File tFile = new File(targetFile);
            FileUtils.copyFile(sFile, tFile);
            sFile.delete();
        }
    }
}
