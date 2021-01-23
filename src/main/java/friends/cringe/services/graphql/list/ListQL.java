package friends.cringe.services.graphql.list;

import friends.cringe.common.model.AuditableQL;
import friends.cringe.services.graphql.list_record.ListRecordQL;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListQL extends AuditableQL {

  private Long qualifier;

  private String title;

  private String description;

  private List<ListRecordQL> records;

}
