package com.rest.domain.entity;


import com.rest.domain.Role;
import com.rest.util.domain.Entity;
import com.rest.util.domain.NameValue;
import com.rest.util.domain.NameValueList;
import com.rest.util.json.JsonUtil;

public class Member extends Entity
{
    private String memberId;
    private String password; // mysql의 password 함수로 저장
    private String name;
    private String email;
    private String roleId;
    private long createdTime;
    private long updatedTime;

    transient private String jwt;

    public Member() {}

    public Member(String id)
    {
        super(id);
    }

    public Member(String memberId, String password, String name, String email, String roleId)
    {
        this.memberId = memberId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.roleId = roleId;

        long currTime = System.currentTimeMillis();
        this.createdTime = currTime;
        this.updatedTime = currTime;
    }

    public void setValues(NameValueList nameValues)
    {
        boolean updated = false;
        for (NameValue nameValue : nameValues.getList())
        {
            String value = nameValue.getValue();
            switch (nameValue.getName())
            {
                case "name":
                    this.name = value;
                    updated = true;
                    break;
                case "roleId":
                    this.roleId = value;
                    updated = true;
                    break;
                case "email":
                    this.email = value;
                    updated = true;
                    break;
            }
        }

        if (updated)
        {
            this.updatedTime = System.currentTimeMillis();
        }
    }

    public static Member getSample()
    {
        Member sample = new Member("hong", "1111", "홍길동", "hong@spectra.co.kr", Role.User.getCode());

        return sample;
    }

    public String toJson()
    {
        return JsonUtil.toJson(this);
    }

    public static Member fromJson(String json)
    {
        return JsonUtil.fromJson(json, Member.class);
    }

    public String toJWTJson()
    {
        this.setPassword(null);

        return JsonUtil.toJson(this);
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String memberId)
    {
        this.memberId = memberId;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public long getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(long createdTime)
    {
        this.createdTime = createdTime;
    }

    public long getUpdatedTime()
    {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public String getJwt()
    {
        return jwt;
    }

    public void setJwt(String jwt)
    {
        this.jwt = jwt;
    }

    public static void main(String[] args)
    {
        //
        System.out.println(getSample());
    }
}
