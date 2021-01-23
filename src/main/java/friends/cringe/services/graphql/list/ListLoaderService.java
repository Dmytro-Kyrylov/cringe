package friends.cringe.services.graphql.list;

import friends.cringe.services.graphql.list_record.ListRecordQL;
import friends.cringe.services.graphql.list_record.ListRecordQLMapper;
import friends.cringe.services.list.record.api.ListRecordService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ListLoaderService {

  @Setter(onMethod_ = @Autowired)
  private ListRecordService listRecordService;

  @Setter(onMethod_ = @Autowired)
  private ListRecordQLMapper listRecordQLMapper;

  public Map<Long, List<ListRecordQL>> getRecordsForList(Set<Long> listQualifiers) {
    Map<Long, List<ListRecordQL>> res = new HashMap<>();
    for (Long listId : listQualifiers) {
      res.put(listId,
          listRecordService.getAll(listId).stream().map(listRecordQLMapper::toQL).collect(Collectors.toList()));
    }
    return res;
  }
}
