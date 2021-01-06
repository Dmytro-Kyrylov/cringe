package friends.cringe.services.list.record.api;

import friends.cringe.services.user.api.UserDto;

import java.util.List;
import java.util.UUID;

public interface ListRecordService {

  ListRecordDto get(String listName, UUID id);

  List<ListRecordDto> getAll(String listName);

  ListRecordDto create(String listName, ListRecordDto dto, UserDto userDto);

  ListRecordDto update(String listName, UUID id, ListRecordDto dto, UserDto userDto);

  ListRecordDto updateRating(String listName, UUID id, ListRecordReactionDto dto, UserDto userDto);

  void delete(String listName, UUID id);

}
