package com.api.security;

import com.api.config.PermissionProperty;
import com.api.exception.AccessDeniedException;
import com.api.exception.InvalidTokenException;
import com.api.exception.TokenExpiredException;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.HashMap;

@Component
public class PermissionValidator
{
    @Resource
    PermissionProperty permissionProperty;

    public void validate(String uri, String method, String roleId) throws InvalidTokenException, TokenExpiredException
    {
        HashMap<String, String> permissionMap = permissionProperty.getPermissionMap(roleId);

        boolean accessAllowed = false;
        if (permissionMap != null)
        {
            for (String key : permissionMap.keySet())
            {
                String value = permissionMap.get(key);

                if (uri.startsWith(key) && value.indexOf(method) > -1)
                {
                    accessAllowed = true;
                    break;
                }
            }
        }

        if (!accessAllowed)
        {
            throw new AccessDeniedException(String.format("Access Denied[URI:%s, METHOD:%s, ROLEID:%s]", uri, method, roleId));
        }
    }

}
