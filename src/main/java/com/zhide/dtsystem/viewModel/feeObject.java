package com.zhide.dtsystem.viewModel;

public class feeObject {
    private String notice_name;
    private String application_number;
    private String pay_deadline_date;
    private Double fee_payable;
    private Integer annual_year;

    public Double getFee_total() {
        return fee_total;
    }

    public void setFee_total(Double fee_total) {
        this.fee_total = fee_total;
    }

    private String cost_slow_flag;
    private Double fee_total;

    public String getNotice_name() {
        return notice_name;
    }

    public void setNotice_name(String notice_name) {
        this.notice_name = notice_name;
    }

    public String getApplication_number() {
        return application_number;
    }

    public void setApplication_number(String application_number) {
        this.application_number = application_number;
    }

    public String getPay_deadline_date() {
        return pay_deadline_date;
    }

    public void setPay_deadline_date(String pay_deadline_date) {
        this.pay_deadline_date = pay_deadline_date;
    }

    public Double getFee_payable() {
        return fee_payable;
    }

    public void setFee_payable(Double fee_payable) {
        this.fee_payable = fee_payable;
    }

    public Integer getAnnual_year() {
        return annual_year;
    }

    public void setAnnual_year(Integer annual_year) {
        this.annual_year = annual_year;
    }

    public String getCost_slow_flag() {
        return cost_slow_flag;
    }

    public void setCost_slow_flag(String cost_slow_flag) {
        this.cost_slow_flag = cost_slow_flag;
    }
}
