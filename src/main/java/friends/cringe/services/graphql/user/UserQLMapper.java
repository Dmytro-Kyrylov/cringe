package friends.cringe.services.graphql.user;

import friends.cringe.services.user.api.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserQLMapper {

  UserQL toQl(UserDto userDto);

}
