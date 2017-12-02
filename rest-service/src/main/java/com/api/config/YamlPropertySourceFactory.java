package com.api.config;

import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @PropertySource 으로 yml파일을 읽을수 있도록 생성하는 클래스
 * > @PropertySource(value = "classpath:/configuration.yml", factory = YamlPropertySourceFactory.class)
 */
public class YamlPropertySourceFactory implements PropertySourceFactory
{

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException
	{
		return name != null ? new PropertySourcesLoader().load(resource.getResource(), name, null) : new PropertySourcesLoader().load(
						resource.getResource(), getNameForResource(resource.getResource()), null);
	}

	private static String getNameForResource(Resource resource) {
		String name = resource.getDescription();
		if (!StringUtils.hasText(name)) {
			name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
		}
		return name;
	}
}