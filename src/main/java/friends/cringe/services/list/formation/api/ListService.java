package friends.cringe.services.list.formation.api;

import java.util.List;

public interface ListService {

  ListDto get(Long qualifier);

  List<ListDto> getAll();

  ListDto create(ListDto dto);

  ListDto update(Long qualifier, ListDto dto);

  void delete(Long qualifier);

}
