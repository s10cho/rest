package com.api.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/t")
public class ApiService
{
    @PostMapping("{param}")
    public String registerProject(@PathVariable String param)
    {
        return param;
    }

    @GetMapping("{param}")
    public String findProject(@PathVariable String param)
    {
        return param;
    }

    @PutMapping("{param}")
    public String modifyProject(@PathVariable String param)
    {
        return param;
    }
}
