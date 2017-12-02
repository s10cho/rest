package com.rest.domain.entity;

import com.rest.util.domain.Entity;
import com.rest.util.domain.NameValue;
import com.rest.util.domain.NameValueList;
import com.rest.util.json.JsonUtil;

public class Comment extends Entity
{
    private String issueId;
    private String creator;
    private String content;
    private long createdTime;
    private long updatedTime;

    public Comment() {}

    public Comment(String id)
    {
        super(id);
    }

    public Comment(String issueId, String creator, String content)
    {
        this.issueId = issueId;
        this.creator = creator;
        this.content = content;

        long currTime = System.currentTimeMillis();
        this.createdTime = currTime;
        this.updatedTime = currTime;
    }

    public static Comment getSample()
    {
        Issue issue = Issue.getSample();
        Comment sample = new Comment(issue.getId(), "kmhan", "코멘트입니다.");

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
                case "content":
                    this.content = value;
                    updated = true;
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

    public static Comment fromJson(String json)
    {
        return JsonUtil.fromJson(json, Comment.class);
    }

    public String getIssueId()
    {
        return issueId;
    }

    public void setIssueId(String issueId)
    {
        this.issueId = issueId;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
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

    public static void main(String[] args)
    {
        //
        System.out.println(getSample());
    }
}
