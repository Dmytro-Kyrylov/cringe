package friends.cringe.services.graphql.list_record;

import friends.cringe.services.list.record.api.ListRecordService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ListRecordQueries implements GraphQLQueryResolver {

  @Setter(onMethod_ = @Autowired)
  private ListRecordService listRecordService;

  @Setter(onMethod_ = @Autowired)
  private ListRecordQLMapper listRecordQLMapper;

  public List<ListRecordQL> getAllListRecords(Long listQualifier) {
    return listRecordService.getAll(listQualifier).stream().map(listRecordQLMapper::toQL).collect(Collectors.toList());
  }

  public ListRecordQL getListRecord(Long listQualifier, UUID id) {
    return listRecordQLMapper.toQL(listRecordService.get(listQualifier, id));
  }

}
