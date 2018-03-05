package wundERP.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/*
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("authorization/login");
        registry.addViewController("/openDay").setViewName("day-open-form");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
*/