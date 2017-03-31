package own.stu.spring;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/3/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    private static String from = "angelnvshen@163.com";
    private static String to = "646099684@qq.com";

    @Test
    public void testSendSimpleEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        //主题
        message.setSubject("hello world");
        message.setText("this is first time use spring mail to send message .");
        mailSender.send(message);
    }

    @Test
    public void testSendEmailWithAttachment() throws MessagingException {
        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl)mailSender;
        MimeMessage message = mailSenderImpl.createMimeMessage();
        //在这里是个布尔值true，表明这个消息是multipart类型的
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        //主题
        helper.setSubject("hello world");
        helper.setText("this is second time use spring mail to send message with attachment .");
        FileSystemResource image = new FileSystemResource("C:\\Users\\dell\\Downloads\\sss.jpg");
        //添加附件
        helper.addAttachment("coupon.jpg", image);
        mailSenderImpl.send(message);

    }

    @Test
    public void testsendRichEmail() throws MessagingException {
        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl)mailSender;
        MimeMessage message = mailSenderImpl.createMimeMessage();
        //在这里是个布尔值true，表明这个消息是multipart类型的
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        //主题
        helper.setSubject("hello world");
        helper.setText("<html><body><img src='cid:logo'/><br />this is third time use spring mail to send message with attachment .</body></html>", true);
        ClassPathResource image = new ClassPathResource("image.jpg");

        //addInline的第一个参数表明内联图片的标识符——与<img>标签的src属性所指定的相同。第二个参数是图片的资源引用
        helper.addInline("logo", image);
        mailSenderImpl.send(message);

    }

    @Test
    public void testsendRichEmailWithVelocityTemplate() throws MessagingException {
        JavaMailSenderImpl mailSenderImpl = (JavaMailSenderImpl)mailSender;
        MimeMessage message = mailSenderImpl.createMimeMessage();
        //在这里是个布尔值true，表明这个消息是multipart类型的
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);

        //主题
        helper.setSubject("hello world");

        // 声明Map对象，并填入用来填充模板文件的键值对
        Map<String, Object> model = new HashMap<>();
        model.put("name", "MZULE");
        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "emailTemplate.vm", model);
        helper.setText(emailText, true);

        ClassPathResource image = new ClassPathResource("image.jpg");

        //addInline的第一个参数表明内联图片的标识符——与<img>标签的src属性所指定的相同。第二个参数是图片的资源引用
        helper.addInline("logo", image);
        mailSenderImpl.send(message);

    }
}
