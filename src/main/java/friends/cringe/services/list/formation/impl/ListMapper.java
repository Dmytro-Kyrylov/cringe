package friends.cringe.services.list.formation.impl;

import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.soap.ListSoap;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ListMapper {

  @Mapping(target = "id", source = "id", qualifiedByName = "uuidToString")
  @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "uuidToString")
  @Mapping(target = "updatedBy", source = "updatedBy", qualifiedByName = "uuidToString")
  ListSoap toSoap(ListDto listDto);

  @Mapping(target = "id", source = "id", qualifiedByName = "stringToUUID")
  @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "stringToUUID")
  @Mapping(target = "updatedBy", source = "updatedBy", qualifiedByName = "stringToUUID")
  ListDto toDto(ListSoap listSoap);

  @Named("uuidToString")
  default String uuidToString(UUID uuid) {
    return uuid == null ? null : uuid.toString();
  }

  @Named("stringToUUID")
  default UUID uuidToString(String uuid) {
    return uuid == null ? null : UUID.fromString(uuid);
  }


}
