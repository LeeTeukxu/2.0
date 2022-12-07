package com.zhide.dtsystem.common;

import org.springframework.stereotype.Component;

/**
 * @ClassName: AllMessageCollections
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月19日 10:39
 **/
@Component
public class AllMessageCollections {
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *登录成功时发送信息

     * @return
     */
    public String LoginSuccess(){
        return "loginSuccess";
    }
    public String clientChagne(){
        return "clientChange";
    }
    public String casesMainChanged(){return "casesMainChanged";}
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *

     * @return
     */
    public String caseStateChanged(){return "caseStateChanged";}
    public String casesSubChanged(){return "casesSubChanged";}
    public String watchMethodChanged(){return "watchMethodChanged";}
    public String exceptionOccured(){return "exceptionOccured";}
    public String controllerProcessed(){return "controllerProcessed";}
}
