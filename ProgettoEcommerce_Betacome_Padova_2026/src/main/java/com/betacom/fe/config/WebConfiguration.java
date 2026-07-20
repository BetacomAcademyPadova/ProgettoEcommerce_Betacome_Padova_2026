package com.betacom.fe.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	
	@Value("${app.upload.dir:uploads}")
    private String uploadDir;

	/*
	 * mapping della directory upload in images per disaccoppiare la direcory dal suo posto fisico 
	 */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        String uploadLocation = "file:" + uploadPath.toString() + "/";

        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadLocation);
    }
	
}