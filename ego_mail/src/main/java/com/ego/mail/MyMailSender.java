package com.ego.mail;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.mail
 * @version: 1.0
 */
@Component
public class MyMailSender {
    @Value("${spring.mail.username}")
    private String setFrom;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private  FreeMarkerConfigurer freeMarkerConfigurer;
    public void sendMail(String to,String orderId){
        try {
            //整个邮件对象
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //让mimemessage使用更加方便的工具类
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,false,"utf-8");
            mimeMessageHelper.setFrom(setFrom);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("易购创建订单成功");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("freemarker.ftl");
            Map<String, Object> map = new HashMap<>();
            map.put("orderId",orderId);
            String page = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            //如果内容包含html，第二个参数一定要配置为true，表示解析html，否则hmtl被当作字符串处理了！
            mimeMessageHelper.setText(page,true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } 
    }
}
