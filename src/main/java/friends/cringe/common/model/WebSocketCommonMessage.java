package friends.cringe.common.model;

import lombok.Data;

import java.util.UUID;

@Data
public class WebSocketCommonMessage {
  private UUID senderId;
}
