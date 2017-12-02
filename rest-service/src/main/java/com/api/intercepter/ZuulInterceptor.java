package com.api.intercepter;

import com.api.config.PermissionProperty;
import com.api.security.JwtValidator;
import com.api.security.PermissionValidator;
import com.rest.domain.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ZuulInterceptor extends HandlerInterceptorAdapter
{
    @Resource
    PermissionProperty permissionProperty;

    @Autowired
    private JwtValidator jwtValidator;

    @Autowired
    private PermissionValidator rbacValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if(permissionProperty.isEnabled())
        {
            Member member = jwtValidator.validate(request);
            String method = request.getMethod(); // GET
            String uri = request.getRequestURI(); // /projects/12345

            // RBAC 체크
            rbacValidator.validate(uri, method, member.getRoleId());
        }
        return true;
    }
}
