package com.lalin.test.site.blog.mix.one.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * Created by frzhao on 2017/11/1.
 */
@Component
public class EmailSenderUtil {
        @Autowired
        private JavaMailSender mailSender;

//        @PostConstruct
        public void sendSimpleEmail() throws MessagingException {

     /*       SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("761571595@qq.com");
            message.setTo("frzhao@nvidia.com"); //761571595@qq.com
            message.setSubject("这是测试邮件内容");  //getBytes("gbk"), "gb2312" )
            message.setText("这是邮件内容");  */

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("frzhao@nvidia.com");
            helper.setText("<html><body><p style='color:green'>hello</p></body></html>", true);

            mailSender.send(message);
            System.out.println("emial have sent !");
    }


}
