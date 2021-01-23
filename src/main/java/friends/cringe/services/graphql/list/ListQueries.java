package friends.cringe.services.graphql.list;


import friends.cringe.services.list.formation.api.ListService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListQueries implements GraphQLQueryResolver {

  @Setter(onMethod_ = @Autowired)
  private ListService listService;

  @Setter(onMethod_ = @Autowired)
  private ListQLMapper listQLMapper;

  public List<ListQL> getAllLists() {
    return listService.getAll().stream().map(listQLMapper::toQL).collect(Collectors.toList());
  }

  public ListQL getList(Long qualifier) {
    return listQLMapper.toQL(listService.get(qualifier));
  }

}
