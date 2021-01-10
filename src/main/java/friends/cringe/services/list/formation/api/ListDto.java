package friends.cringe.services.list.formation.api;

import friends.cringe.common.model.AuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListDto extends AuditableDto {

  @Pattern(regexp = "\\w+")
  @Length(min = 1, max = 200)
  @NotNull
  private String name;

  @Length(min = 1, max = 200)
  @NotNull
  private String title;

  private String description;

}
