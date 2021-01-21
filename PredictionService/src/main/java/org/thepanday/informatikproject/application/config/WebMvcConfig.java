////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 21.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.thepanday.informatikproject.application" })
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/js/**")
            .addResourceLocations("classpath:/js/")
            .setCachePeriod(31556926);
        registry
            .addResourceHandler("/favicon.ico")
            .addResourceLocations("classpath:/favicon.ico");
    }
}
