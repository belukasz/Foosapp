package org.belukasz.foos.sms;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.util.Base64;
import org.junit.Test;

public class SmsServiceTest {

  @Test
  public void sampleMessage() throws UnirestException {
//https://promosms.com/api/rest/get-balance/bG9naW46cGFzc3dvcmQ=/xml
    byte[] key = ("lbombol@gmail.com" + ":" + "").getBytes();
    HttpResponse<JsonNode> node = Unirest.get("https://promosms.com/api/rest/get-balance/{key}/json")
        .routeParam("key", Base64.getEncoder().encodeToString(key))
        .asJson();

    System.out.println(node.getBody().toString());
  }

  @Test
  public void sampleMessage1() throws UnirestException {
    //https://promosms.com/api/rest/get-balance/bG9naW46cGFzc3dvcmQ=/xml
    //d9d8bcfb
    byte[] key = ("lbombol@gmail.com" + ":" + "").getBytes();

    HttpResponse<JsonNode> node = Unirest.post("https://promosms.com/api/rest/send-sms/{key}/json")
        .routeParam("key", Base64.getEncoder().encodeToString(key))
        .field("text", "Open Single: Grzegorz Zawiłowicz - Jarosław Wanatowicz stół nr.4")
        .field("type", "3")
        .field("sender","InfoSMS")
        .field("recipients", "506790073")
        .asJson();

    //https://promosms.com/api/rest/send-sms/bG9naW46cGFzc3dvcmQ=/json?text=Test%20message&type=3&recipients[]=500000001&recipients[]=500000002&sender=InfoSMS&special-chars=1&long-sms=1&date=1451602800&delivery-url=http%3A%2F%2Fexample.com%2Fsmsid%3D%25smsID%26report%3D%25report&user-index[]=testmsg1&user-index[]=testmsg2
   /* HttpResponse<JsonNode> node = Unirest.post("https://promosms.com/api/rest/send-sms/{key}/json?text={text}&type={type}&recipients[]={recipients}&sender=InfoSMS")
        .routeParam("key", Base64.getEncoder().encodeToString(key))
        .routeParam("text", "buziak")
        .routeParam("type", "0")
        .routeParam("recipients", "609231189")
        .asJson();
*/
    System.out.println(node.getStatusText());

  }
}