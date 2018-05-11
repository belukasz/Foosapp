package org.belukasz.foos.sms.spring;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.concurrent.Executors;
import javax.sql.DataSource;
import org.belukasz.foos.sms.MessageFactory;
import org.belukasz.foos.sms.Runner;
import org.belukasz.foos.sms.SmsService;
import org.belukasz.foos.sms.persitance.MatchesLaunchedPersitanceService;
import org.belukasz.foos.sms.persitance.PhoneNumberRepository;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("classpath:notification.properties")
public class NotificationConfig {

  @Value("${db.user}")
  private String dbUser;

  @Value("${db.password}")
  private String dbPassword;

  @Value("${db.url}")
  private String dbUrl;

  @Value("${db.jdbc.driver}")
  private String jdbcDriver;

  @Bean
  public DataSource dataSource(){
    MysqlDataSource mysqlDataSource = new MysqlDataSource();
    mysqlDataSource.setUser(dbUser);
    mysqlDataSource.setPassword(dbPassword);
    mysqlDataSource.setUrl(dbUrl);
    return mysqlDataSource;
  }


  @Value("${sms.user}")
  private String smsUser;

  @Value("${sms.password}")
  private String smsPassword;

  @Bean
  public MessageFactory messageFactory(){
    return new MessageFactory(phoneNumberRepository());
  }

  @Bean
  public SmsService smsService(){
    return new SmsService(smsPassword, smsUser);
  }

  @Bean
  public PhoneNumberRepository phoneNumberRepository(){
    return new PhoneNumberRepository();
  }

  @Bean
  public MatchesLaunchedPersitanceService matchesLaunchedPersitanceService(){
      return new MatchesLaunchedPersitanceService(DSL.using(dataSource(), SQLDialect.MYSQL_5_7));
  }

  @Bean
  public Runner runner(){
    return new Runner(smsService(), matchesLaunchedPersitanceService(), messageFactory(),
        Executors.newFixedThreadPool(5));
  }


}
