package com.rest.domain;

public enum Role
{
    Admin("admin"),
    Manager("manager"),
    User("user");

    private String code;

    Role(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
