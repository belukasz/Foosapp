package org.belukasz.foos.sms;

import org.belukasz.foos.sms.spring.NotificationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppMain {
  
  public static void main(String[] args) {
        new AnnotationConfigApplicationContext(NotificationConfig.class);
  }

}
