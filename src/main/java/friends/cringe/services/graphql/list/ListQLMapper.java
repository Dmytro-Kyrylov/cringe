package friends.cringe.services.graphql.list;

import friends.cringe.services.list.formation.api.ListDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListQLMapper {

  ListQL toQL(ListDto listDto);

  ListDto toDto(ListInputQL listInputQL);

}
