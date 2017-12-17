package com.lalin.test.site.blog.mix.one.utils;

import com.lalin.test.site.blog.mix.one.entity.BorrowingProbe;
import com.lalin.test.site.blog.mix.one.entity.DeviceBorrowList;
import com.lalin.test.site.blog.mix.one.service.BorrowingService;
import com.lalin.test.site.blog.mix.one.service.DeviceBorrowService;
import com.lalin.test.site.blog.mix.one.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sound.midi.SysexMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by frzhao on 2017/11/4.
 */
@Component
public class EmailEmbeddThymeleaf {

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private DeviceBorrowService deviceBorrowService;
    @Autowired
    private EmployeeService employeeService;

//    @PostConstruct
    public void sendThymeleaf(List<DeviceBorrowList> personEmail) throws MessagingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date());

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("frzhao@nvidia.com"));
//        helper.setTo("slli@nvidia.com");
        helper.setTo(employeeService.findByName(personEmail.get(0).getBorrower()).getEmail());
        helper.setSubject("Embedded thymeleaf");
        Context context = new Context();
        context.setVariable("borrowLists", personEmail);
        context.setVariable("currentDate", new Date());
        String text = springTemplateEngine.process("index/borrowByPerson", context);
        helper.setText(text, true);
        mailSender.send(message);

    }

    public List<DeviceBorrowList> sendEmailByPerson(List<DeviceBorrowList> deviceBorrowLists){
        System.out.println("deviceBorrowLists first " + deviceBorrowLists);
        List<DeviceBorrowList> personBorrowList = new ArrayList<DeviceBorrowList>();
        if(deviceBorrowLists.size() > 0) {
            String display_name = deviceBorrowLists.get(0).getBorrower();
            for (DeviceBorrowList deviceBorrowList : deviceBorrowLists) {
                if(deviceBorrowList.getBorrower().equals(display_name)){
                    personBorrowList.add(deviceBorrowList);
                }
            }
            deviceBorrowLists.removeAll(personBorrowList);
            System.out.println("deviceBorrowLists second " + deviceBorrowLists);
            System.out.println("personBorrowList first " + personBorrowList);
        }
        return personBorrowList;
    }
    public void sendMail() throws MessagingException {
        List<DeviceBorrowList> deviceBorrowLists = new ArrayList<DeviceBorrowList>(); //deviceBorrowService.findAll();  //findByDeivceLabelSortDate("DV-CRP-0002"); // findAll()
        List<BorrowingProbe> borrowingProbes = borrowingService.findAll();
        for(BorrowingProbe borrowingProbe : borrowingProbes){
            deviceBorrowLists.add(deviceBorrowService.findById(borrowingProbe.getBorrowListId()));
        }
        while(deviceBorrowLists.size() > 0) {
            System.out.println("deviceBorrowLists.size() in = " + deviceBorrowLists.size());
            List<DeviceBorrowList> personEmail = sendEmailByPerson(deviceBorrowLists);

            System.out.println("deviceBorrowLists.size() out = " + deviceBorrowLists.size());

            sendThymeleaf(personEmail);
        }
    }
    public void sendBorrowingInfo(DeviceBorrowList deviceBorrowList) throws MessagingException {
            List<DeviceBorrowList> deviceBorrowLists = new ArrayList<DeviceBorrowList>();
            deviceBorrowLists.add(deviceBorrowList);
            List<DeviceBorrowList> personEmail = sendEmailByPerson(deviceBorrowLists);

            System.out.println("deviceBorrowLists.size() out = " + deviceBorrowLists.size());

            sendThymeleaf(personEmail);

    }
}
