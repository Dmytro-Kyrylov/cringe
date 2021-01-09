package friends.cringe.services.list.formation.impl;

import friends.cringe.common.model.WebSocketCommonMessage;
import friends.cringe.services.list.formation.api.ListUrl;
import friends.cringe.services.user.api.UserDto;
import friends.cringe.services.user.api.UserService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ListWebSockController {

  @Setter(onMethod_ = @Autowired)
  private SimpMessagingTemplate messagingTemplate;

  @Setter(onMethod_ = @Autowired)
  private UserService userService;

  @MessageMapping(ListUrl.RESOURCE_NAME)
  public void processMessage(@Payload ListWebSockMessage message) {
    List<UserDto> allUsers = userService.getAll();
    allUsers.removeIf(x -> x.getId().equals(message.getSenderId()));

    for (UserDto user : allUsers) {
      messagingTemplate.convertAndSendToUser(user.getId().toString(), "/queue/messages", message);
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class ListWebSockMessage extends WebSocketCommonMessage {
    private UUID listId;
    private String listName;
  }
}
