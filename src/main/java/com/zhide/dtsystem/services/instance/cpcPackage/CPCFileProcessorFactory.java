package com.zhide.dtsystem.services.instance.cpcPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: CPCFileProcessorFactory
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月19日 14:02
 **/
@Component
public class CPCFileProcessorFactory {
    @Autowired
    List<ICPCFileProcessor> processorList;

    public ICPCFileProcessor getInstance(String code){
        for(ICPCFileProcessor processor:processorList){
            if(processor.accept(code)) return processor;
        }
        return null;
    }
}
