package org.belukasz.foos.sms;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.belukasz.foos.sms.persitance.MatchesLaunchedPersitanceService;
import org.springframework.scheduling.annotation.Scheduled;

public class Runner {

  private static final Logger LOG = Logger.getLogger(Runner.class);
  private static final Logger ASYNC_LOG = Logger.getLogger("RESPONSE_LOGGER");


  private ExecutorService executorService;
  private SmsService smsService;
  private MessageFactory messageFactory;
  private MatchesLaunchedPersitanceService matchesLaunchedPersitanceService;


  public Runner(SmsService smsService,
      MatchesLaunchedPersitanceService matchesLaunchedPersitanceService, MessageFactory messageFactory,
      ExecutorService executorService) {
    this.smsService = smsService;
    this.matchesLaunchedPersitanceService = matchesLaunchedPersitanceService;
    this.messageFactory = messageFactory;
    this.executorService = executorService;
  }

  @Scheduled(fixedRate = 5000)
  public void scan(){
    LOG.info("checking new launched matches");
    List<Future<HttpResponse<JsonNode>>> futures =  messageFactory.generateMessages(matchesLaunchedPersitanceService.getLaunchedPlayers()
        .collect(Collectors.toList())).stream().map(t -> smsService.sendMessage(t.getContent(), t.getNumber())).collect(Collectors.toList());

    executorService.execute(() -> futures.forEach(future -> {
      try {
        ASYNC_LOG.info(future.get().getBody());
      } catch (InterruptedException | ExecutionException e) {
        ASYNC_LOG.error(e);
      }
    }));

  }

}
