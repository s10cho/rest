package com.rest.domain.granule;

import com.rest.domain.entity.Member;
import com.rest.util.json.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MemberList
{
    private List<Member> members;

    public MemberList()
    {
        this.members = new ArrayList<>();
    }

    public MemberList(Member member)
    {
        this();
        this.members.add(member);
    }

    public static MemberList getSample()
    {
        MemberList sample = new MemberList(Member.getSample());

        return sample;
    }

    public String toJson()
    {
        return JsonUtil.toJson(this);
    }

    public static MemberList fromJson(String json)
    {
        return JsonUtil.fromJson(json, MemberList.class);
    }

    public void add(Member member)
    {
        this.members.add(member);
    }

    public void addAll(List<Member> members)
    {
        this.members.addAll(members);
    }

    public List<Member> getList()
    {
        return members;
    }

    public static void main(String [] args)
    {
        System.out.println(getSample());
        System.out.println(getSample().toJson());
        System.out.println(fromJson(getSample().toJson()));
    }
}
