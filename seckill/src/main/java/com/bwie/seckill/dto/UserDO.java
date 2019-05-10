package com.bwie.seckill.dto;

public class UserDO {
    private String id;

    private String name;

    private Integer age;

    private String telephone;

    private String registerMode;

    private String thirdPayId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode == null ? null : registerMode.trim();
    }

    public String getThirdPayId() {
        return thirdPayId;
    }

    public void setThirdPayId(String thirdPayId) {
        this.thirdPayId = thirdPayId == null ? null : thirdPayId.trim();
    }
}