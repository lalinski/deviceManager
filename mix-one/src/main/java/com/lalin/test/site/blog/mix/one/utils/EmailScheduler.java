package com.lalin.test.site.blog.mix.one.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by frzhao on 2017/11/7.
 */
@Component
public class EmailScheduler{

    ThreadPoolTaskScheduler scheduler;
    PeriodicTrigger trigger;
    CronTrigger cron;
    @Autowired
    EmailEmbeddThymeleaf emailEmbeddThymeleaf;


    public EmailScheduler(){

        scheduler = new ThreadPoolTaskScheduler();
        cron = new CronTrigger("0 8 15 * * MON");// FRI
        trigger = new PeriodicTrigger(100, TimeUnit.SECONDS);
        scheduler.setPoolSize(1);
        scheduler.initialize();
    }
    @PostConstruct
    public void  sendEmail(){
    //    scheduler.schedule(new EmailThread(0), new Date(System.currentTimeMillis() + 1000));
        scheduler.schedule(new EmailThread(0), cron);
    }




    private class EmailThread implements  Runnable{
        private int i;
        public EmailThread(int i){
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println(new Date().getTime() / 1000);
            System.out.println("This is " + i + " emails");
            try {
                emailEmbeddThymeleaf.sendMail();
                i++;
            } catch (MessagingException e) {
                e.printStackTrace();
            }


        }
    }
}
