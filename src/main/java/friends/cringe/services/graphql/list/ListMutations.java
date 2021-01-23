package friends.cringe.services.graphql.list;

import friends.cringe.services.list.formation.api.ListService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Component
public class ListMutations implements GraphQLMutationResolver {

  @Setter(onMethod_ = @Autowired)
  private ListService listService;

  @Setter(onMethod_ = @Autowired)
  private ListQLMapper listQLMapper;

  public ListQL createList(@Valid ListInputQL listInputQL) {
    return listQLMapper.toQL(listService.create(listQLMapper.toDto(listInputQL)));
  }

  public ListQL updateList(Long qualifier, @Valid ListInputQL listInputQL) {
    return listQLMapper.toQL(listService.update(qualifier, listQLMapper.toDto(listInputQL)));
  }

  public boolean deleteList(Long qualifier) {
    listService.delete(qualifier);
    return true;
  }
}
