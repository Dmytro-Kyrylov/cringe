package friends.cringe.services.list.record.api;

import java.util.List;
import java.util.UUID;

public interface ListRecordService {

  ListRecordDto get(String listName, UUID id);

  List<ListRecordDto> getAll(String listName);

  ListRecordDto create(String listName, ListRecordDto dto);

  ListRecordDto update(String listName, UUID id, ListRecordDto dto);

  ListRecordDto updateRating(String listName, UUID id, ListRecordReactionDto dto);

  void delete(String listName, UUID id);

}
