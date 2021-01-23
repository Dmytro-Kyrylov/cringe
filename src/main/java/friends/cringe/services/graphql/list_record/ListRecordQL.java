package friends.cringe.services.graphql.list_record;

import friends.cringe.common.model.AuditableQL;
import friends.cringe.services.graphql.user.UserQL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListRecordQL extends AuditableQL {

  private String body;

  private Long listQualifier;

  private Long rating;

  private List<RatingByUserQL> ratingByUsers;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RatingByUserQL {
    private Long rating;
    private UUID userId;
    private UserQL user;
  }

}
