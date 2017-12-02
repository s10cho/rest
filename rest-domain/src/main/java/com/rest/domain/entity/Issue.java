package com.rest.domain.entity;

import com.rest.domain.IssueStatus;
import com.rest.util.domain.Aggregate;
import com.rest.util.domain.Entity;
import com.rest.util.domain.NameValue;
import com.rest.util.domain.NameValueList;
import com.rest.util.json.JsonUtil;

public class Issue extends Entity implements Aggregate
{
    //
    private String projectId;
    private String title;
    private String questionContent;
    private String answerContent;
    private String creator;
    private String assignee;
    private String status;
    private long resolvedTime;
    private long createdTime;
    private long updatedTime;

    public Issue() {}

    public Issue(String id)
    {
        super(id);
    }

    public Issue(String projectId, String title, String questionContent, String creator)
    {
        this.projectId = projectId;
        this.title = title;
        this.questionContent = questionContent;
        this.creator = creator;
        this.status = IssueStatus.Opened.getCode();

        long currTime = System.currentTimeMillis();
        this.createdTime = currTime;
        this.updatedTime = currTime;
    }

    public static Issue getSample()
    {
        Project project = Project.getSample();

        Issue sample = new Issue(project.getId(), "버그를 등록합니다.", "사이트에서 발생한 이슈이며 빠른 시간내에 처리가 필요합니다.", "kmhan");
        sample.setAnswerContent("해당 버그는 처리가 완료되었습니다. 감사합니다.");
        sample.setAssignee("ekpark");
        sample.setStatus(IssueStatus.Closed.getCode());

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
                case "answerContent":
                    this.answerContent = value;
                    updated = true;
                    break;
                case "assignee":
                    this.assignee = value;
                    updated = true;
                    break;
                case "status":
                    this.status = value;
                    updated = true;
                    break;
                case "resolvedTime":
                    this.resolvedTime = Long.parseLong(value);
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

    public static Issue fromJson(String json)
    {
        return JsonUtil.fromJson(json, Issue.class);
    }

    public String getProjectId()
    {
        return projectId;
    }

    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getQuestionContent()
    {
        return questionContent;
    }

    public void setQuestionContent(String questionContent)
    {
        this.questionContent = questionContent;
    }

    public String getAnswerContent()
    {
        return answerContent;
    }

    public void setAnswerContent(String answerContent)
    {
        this.answerContent = answerContent;
    }

    public String getCreator()
    {
        return creator;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getAssignee()
    {
        return assignee;
    }

    public void setAssignee(String assignee)
    {
        this.assignee = assignee;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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

    public long getResolvedTime()
    {
        return resolvedTime;
    }

    public void setResolvedTime(long resolvedTime)
    {
        this.resolvedTime = resolvedTime;
    }

    public static void main(String[] args)
    {
        //
        System.out.println(getSample());
    }
}
