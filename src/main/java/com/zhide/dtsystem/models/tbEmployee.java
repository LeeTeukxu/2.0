package com.zhide.dtsystem.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbEmployee")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class tbEmployee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmpID")
    private Integer empId;
    @Column(name = "DepID")
    private Integer depId;
    @Column(name = "EmpCode")
    private String empCode;
    @Column(name = "EmpName")
    private String empName;
    @Column(name = "OrderSN")
    private Integer orderSn;
    @Column(name = "Sex")
    private Integer sex;
    @Column(name = "IDCard")
    private String idCard;
    @Column(name = "AreaCode")
    private Integer areaCode;
    @Column(name = "BornDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bornDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "WorkDate")
    private Date workDate;
    @Column(name = "Nation")
    private Integer nation;
    @Column(name = "Duty")
    private Integer duty;
    @Column(name = "Education")
    private Integer education;
    @Column(name = "WorkAddress")
    private String workAddress;
    @Column(name = "HomeAddress")
    private String homeAddress;
    @Column(name = "WorkTelphone")
    private String workTelphone;
    @Column(name = "HomeTelphone")
    private String homeTelphone;
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "Email")
    private String email;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "Photo")
    private String photo;
    @Column(name = "EmpState")
    private Integer empState;
    @Column(name = "EmpProject")
    private String empProject;
    @Column(name = "PostID")
    private Integer postId;
    @Column(name = "Department")
    private String department;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }


    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }


    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }


    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }


    public Integer getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(Integer orderSn) {
        this.orderSn = orderSn;
    }


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }


    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }


    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }


    public Integer getDuty() {
        return duty;
    }

    public void setDuty(Integer duty) {
        this.duty = duty;
    }


    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }


    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }


    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }


    public String getWorkTelphone() {
        return workTelphone;
    }

    public void setWorkTelphone(String workTelphone) {
        this.workTelphone = workTelphone;
    }


    public String getHomeTelphone() {
        return homeTelphone;
    }

    public void setHomeTelphone(String homeTelphone) {
        this.homeTelphone = homeTelphone;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public Integer getEmpState() {
        return empState;
    }

    public void setEmpState(Integer empState) {
        this.empState = empState;
    }


    public String getEmpProject() {
        return empProject;
    }

    public void setEmpProject(String empProject) {
        this.empProject = empProject;
    }


    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
