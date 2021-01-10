package friends.cringe.services.list.formation.api;

import friends.cringe.common.model.AuditableDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListDto extends AuditableDto {

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  private Long qualifier;

  @Length(min = 1, max = 200)
  @NotNull
  private String title;

  private String description;

}
