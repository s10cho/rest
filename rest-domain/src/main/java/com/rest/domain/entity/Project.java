package com.rest.domain.entity;

import com.rest.domain.granule.MemberList;
import com.rest.util.domain.Entity;
import com.rest.util.domain.NameValue;
import com.rest.util.domain.NameValueList;
import com.rest.util.json.JsonUtil;


public class Project extends Entity
{
    private String name;
    private String owner;
    private MemberList members;
    private long createdTime;
    private long updatedTime;

    public Project() {}

    public Project(String id)
    {
        super(id);
    }

    public Project(String name, String owner)
    {
        this.name = name;
        this.owner = owner;
        this.members = new MemberList();
        long currTime = System.currentTimeMillis();
        this.createdTime = currTime;
        this.updatedTime = currTime;
    }

    public static Project getSample()
    {
        Project sample = new Project("버그관리 게시판", "kmhan");
        sample.getMembers().add(Member.getSample());

        return sample;
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
                case "owner":
                    this.owner = value;
                    updated = true;
                    break;
                case "members":
                    this.members = MemberList.fromJson(value);
                    break;
            }
        }

        if (updated)
        {
            this.updatedTime = System.currentTimeMillis();
        }
    }

    public String toJson()
    {
        return JsonUtil.toJson(this);
    }

    public static Project fromJson(String json)
    {
        return JsonUtil.fromJson(json, Project.class);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
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

    public MemberList getMembers()
    {
        return members;
    }

    public void setMembers(MemberList members)
    {
        this.members = members;
    }

    public static void main(String[] args)
    {
        //
        System.out.println(getSample());
    }
}
