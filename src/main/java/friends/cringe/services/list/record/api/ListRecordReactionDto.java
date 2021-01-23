package friends.cringe.services.list.record.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListRecordReactionDto {

  @NotNull
  @Min(-1)
  @Max(1)
  private Long value;

}
