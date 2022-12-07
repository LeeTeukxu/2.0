package com.zhide.dtsystem.models.xml;

@ChineseName(value = "联系人")
public class LXRXM {
    @ChineseName(value = "姓名")
    private String Name;
    @ChineseName(value = "电话")
    private String Phone;
    @ChineseName(value = "电子邮箱")
    private String Email;
    @ChineseName(value = "邮政编码")
    private String PostCode;
    @ChineseName(value = "省自治区直辖市名称")
    private String Province;
    @ChineseName(value = "市县名称")
    private String City;
    @ChineseName(value = "城区乡街道门牌号")
    private String Street;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getAddress() {
        return getStreet();
    }
}
