package com.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@PropertySource(value = "classpath:/application.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "permission")
public class PermissionProperty
{
    private boolean enabled;

	private Role role;

	public static class Role
	{
		private HashMap<String, String> admin;
		private HashMap<String, String> manager;
		private HashMap<String, String> user;

		public HashMap<String, String> getAdmin()
		{
			return admin;
		}

		public void setAdmin(HashMap<String, String> admin)
		{
			this.admin = admin;
		}

		public HashMap<String, String> getManager()
		{
			return manager;
		}

		public void setManager(HashMap<String, String> manager)
		{
			this.manager = manager;
		}

		public HashMap<String, String> getUser()
		{
			return user;
		}

		public void setUser(HashMap<String, String> user)
		{
			this.user = user;
		}
	}

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}


	public HashMap<String, String> getPermissionMap(String roleId)
	{
		HashMap<String, String> permissionMap = null;

		switch (roleId)
		{
			case "admin":
				permissionMap = this.role.admin;
				break;
			case "manager":
				permissionMap = this.role.manager;
				break;
			case "user":
				permissionMap = this.role.user;
				break;
		}

		return permissionMap;
	}
}
