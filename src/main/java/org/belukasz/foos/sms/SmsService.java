package org.belukasz.foos.sms;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.util.Base64;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

public class SmsService {

  private String smsToken;
  private final static Logger LOG = Logger.getLogger(SmsService.class);

  public SmsService(String password, String user) {
    String toBeEncoded = user + ":" + password;
    this.smsToken = Base64.getEncoder().encodeToString(toBeEncoded.getBytes());
  }

  public Future<HttpResponse<JsonNode>> sendMessage(String message, String number) {

    LOG.info("number: " + number);
    LOG.info("message:" + message);

    return Unirest.post("https://promosms.com/api/rest/send-sms/{key}/json")
        .routeParam("key", smsToken)
        .field("text", message)
        .field("type", "1")
        .field("recipients", number)
        .asJsonAsync();
  }


}
