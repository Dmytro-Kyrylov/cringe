package friends.cringe.services.graphql.list;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class ListInputQL {

  @Length(min = 1, max = 200)
  @NotNull
  private String title;

  private String description;

}
