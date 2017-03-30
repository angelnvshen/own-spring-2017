package own.stu.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by dell on 2017/3/30.
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public MailSender mailSender(Environment environment){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getProperty("mailServer.host"));
        mailSender.setPort(Integer.parseInt(environment.getProperty("mailServer.port")));
        mailSender.setUsername(environment.getProperty("mailServer.username"));
        mailSender.setPassword(environment.getProperty("mailServer.password"));
        return mailSender;
    }
}
