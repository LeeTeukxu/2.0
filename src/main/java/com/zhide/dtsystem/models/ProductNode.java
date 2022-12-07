package com.zhide.dtsystem.models;

/**
 * @ClassName: ProductNode
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年08月2020-08-11日 10:52
 **/
public class ProductNode extends TreeListItem {
    private Double price;
    private Double cost;
    private Integer maxDays;
    private String required;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }
}
