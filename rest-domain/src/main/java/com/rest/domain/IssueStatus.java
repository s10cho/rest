package com.rest.domain;

public enum IssueStatus
{
    Opened("100"),
    InProgress("200"),
    Closed("300");

    private String code;

    IssueStatus(String code)
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
