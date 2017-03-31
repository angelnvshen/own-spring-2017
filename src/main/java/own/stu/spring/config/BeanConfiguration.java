package own.stu.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.util.Properties;

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
}
