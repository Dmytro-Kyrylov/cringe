package friends.cringe.services.list.record.api;

import friends.cringe.common.model.AuditableDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListRecordDto extends AuditableDto {

  @Length(min = 1)
  @NotNull
  private String body;

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  private Long listQualifier;

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  private Long rating;

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  private List<RatingByUser> ratingByUsers = new ArrayList<>();

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class RatingByUser {
    private Long rating;
    private UUID userId;
  }

}
