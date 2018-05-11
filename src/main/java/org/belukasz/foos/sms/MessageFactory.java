package org.belukasz.foos.sms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.belukasz.foos.sms.model.LaunchedPlayer;
import org.belukasz.foos.sms.model.Message;
import org.belukasz.foos.sms.persitance.PhoneNumberRepository;

public class MessageFactory {

  private Set<Integer> alreadyGenerated;
  private PhoneNumberRepository phoneNumberRepository;

  public MessageFactory(PhoneNumberRepository phoneNumberRepository) {
    this.alreadyGenerated = new HashSet<>();
    this.phoneNumberRepository = phoneNumberRepository;
  }

  public List<Message> generateMessages(List<LaunchedPlayer> launchedPlayers) {
    Map<Integer, List<LaunchedPlayer>> stringLaunchedPlayerMap =
        launchedPlayers.stream()
            .collect(Collectors.groupingBy(LaunchedPlayer::getMatchId));

    return stringLaunchedPlayerMap.keySet().stream().filter(id -> !alreadyGenerated.contains(id))
        .map(id -> generateMessagesForMatch(stringLaunchedPlayerMap.get(id), id))
        .flatMap(List::stream).collect(Collectors.toList());
  }

  private List<Message> generateMessagesForMatch(List<LaunchedPlayer> launchedPlayers, Integer id) {
    List<Message> messages = launchedPlayers.stream()
        .map(player -> generateForSinglePlayer(player, launchedPlayers))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    alreadyGenerated.add(id);
    return messages;
  }

  private Message generateForSinglePlayer(LaunchedPlayer launchedPlayer,
      List<LaunchedPlayer> launchedPlayers) {
    String phoneNumber = phoneNumberRepository.getPhoneNumber(launchedPlayer);
    if (phoneNumber != null) {
      Map<Integer, List<LaunchedPlayer>> collect =
          launchedPlayers.stream().collect(Collectors.groupingBy(LaunchedPlayer::getTeamId));
      List<String> mergedTeams = new ArrayList<>();
      collect.forEach((integer, launchedPlayers1) -> mergedTeams.add(launchedPlayers1.stream()
          .map(this::getPlayerFullName)
          .collect(Collectors.joining(" "))));
      return getMessage(launchedPlayer, phoneNumber, mergedTeams);
    }
    return null;
  }

  private Message getMessage(LaunchedPlayer launchedPlayer, String phoneNumber,
      List<String> mergedTeams) {
    if (mergedTeams.size() == 2) {
      String content = "Wywolanie: " + mergedTeams.get(0) + " - " + mergedTeams.get(1)
          + ", stol numer " + launchedPlayer.getTableNumber();
      return new Message(content, phoneNumber);
    }
    return null;
  }

  private String getPlayerFullName(LaunchedPlayer launchedPlayer) {
    return launchedPlayer != null ?
        launchedPlayer.getFirstName() + " " + launchedPlayer.getLastName() : "";
  }


}
