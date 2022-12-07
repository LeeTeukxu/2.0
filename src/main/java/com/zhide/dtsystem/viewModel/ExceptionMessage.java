package com.zhide.dtsystem.viewModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ExceptionMessage
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月03日 9:51
 **/
public class ExceptionMessage implements Serializable {
    //详细出错信息
    String message;
    //请求的Url
    String url;
    //请求的参数
    String parameters;
    //方法类型
    String type;
    //方法名称
    String methodName;
    //exception中具体出错的代码行数。
    String detail;
    //发生时间
    Date createTime;
    String userName;
    String companyName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
