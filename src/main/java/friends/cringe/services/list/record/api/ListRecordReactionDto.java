package friends.cringe.services.list.record.api;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class ListRecordReactionDto {

  @NotNull
  @Min(-1)
  @Max(1)
  private Long value;

}
