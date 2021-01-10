package friends.cringe.services.list.formation.api;

import java.util.List;

public interface ListService {

  ListDto get(String name);

  List<ListDto> getAll();

  ListDto create(ListDto dto);

  ListDto update(String name, ListDto dto);

  void delete(String name);

}
