package friends.cringe.services.list.record.api;

import java.util.List;
import java.util.UUID;

public interface ListRecordService {

  ListRecordDto get(Long listQualifier, UUID id);

  List<ListRecordDto> getAll(Long listQualifier);

  ListRecordDto create(Long listQualifier, ListRecordDto dto);

  ListRecordDto update(Long listQualifier, UUID id, ListRecordDto dto);

  ListRecordDto updateRating(Long listQualifier, UUID id, ListRecordReactionDto dto);

  void delete(Long listQualifier, UUID id);

}
