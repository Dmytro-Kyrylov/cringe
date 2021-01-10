package friends.cringe.services.user.impl;

import friends.cringe.services.user.api.UserDto;
import friends.cringe.soap.UserSoap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "id", source = "id", qualifiedByName = "uuidToString")
  UserSoap toSoap(UserDto userDto);

  @Named("uuidToString")
  default String uuidToString(UUID uuid) {
    return uuid == null ? null : uuid.toString();
  }

}
