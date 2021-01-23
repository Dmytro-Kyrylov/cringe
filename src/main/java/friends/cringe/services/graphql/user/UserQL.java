package friends.cringe.services.graphql.user;

import friends.cringe.common.model.UUIDQL;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQL extends UUIDQL {

  private String username;

}
