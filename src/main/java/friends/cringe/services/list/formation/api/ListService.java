package friends.cringe.services.list.formation.api;

import friends.cringe.services.user.api.UserDto;

import java.util.List;
import java.util.UUID;

public interface ListService {

  ListDto get(String name);

  List<ListDto> getAll();

  ListDto create(ListDto dto, UserDto userDto);

  ListDto update(String name, ListDto dto, UserDto userDto);

  void delete(String name);

}
