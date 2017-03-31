package own.stu.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.Properties;
import java.util.Set;

/**
 * Created by dell on 2017/3/30.
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public MailSender mailSender(Environment environment) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty("mailServer.host"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("mailServer.port")));
        mailSender.setUsername(environment.getProperty("mailServer.username"));
        mailSender.setPassword(environment.getProperty("mailServer.password"));
        return mailSender;
    }

    /**
     * velocityEngine
     * @return
     */
    @Bean
    public VelocityEngineFactoryBean velocityEngine() {

        VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
        Properties props = new Properties();
        props.setProperty("resource.loader", "class");
        props.setProperty("class.resource.loader.class", "ClasspathResourceLoader.class.getName()");
        velocityEngine.setVelocityProperties(props);
        return velocityEngine;
    }

    /**
     *  thymeleaf template
     */
    @Order(1)
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver(){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/mail/");
        resolver.setTemplateMode("LEGACYHTML5");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    /*@Order(2)
    @Bean
    public ServletContextTemplateResolver webTemplateResolver(){
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }*/

    @Bean
    public TemplateEngine templateEngine(Set<TemplateResolver> resolvers){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolvers(resolvers);
        return engine;
    }
}
