package org.belukasz.foos.sms.persitance;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.belukasz.foos.sms.model.LaunchedPlayer;
import org.belukasz.foos.sms.model.PlayerInfo;
import org.springframework.core.io.ClassPathResource;

public class PhoneNumberRepository {

  private Map<String, String> playerInfos;

  public PhoneNumberRepository() {
    initializeMapping();
  }

  public String getPhoneNumber(LaunchedPlayer launchedPlayer) {
    return playerInfos.get(launchedPlayer.getFirstName() + "." + launchedPlayer.getLastName());
  }

  public void initializeMapping() {
    try (Reader reader = Files
        .newBufferedReader(Paths.get(new ClassPathResource("phone_numbers.csv").getURI()))) {

      playerInfos = new HashMap<>();

      CsvToBean<PlayerInfo> csvToBean = new CsvToBeanBuilder(reader)
          .withType(PlayerInfo.class)
          .withIgnoreLeadingWhiteSpace(true)
          .build();

      csvToBean.parse().stream().forEach(
          playerInfo -> playerInfos.put(
              playerInfo.getName() + "." + playerInfo.getSurname().toUpperCase(), playerInfo.getNumber()
          ));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
