package friends.cringe.services.list.record.impl;

import friends.cringe.services.list.record.api.ListRecordDto;
import friends.cringe.services.list.record.api.ListRecordReactionDto;
import friends.cringe.soap.ListRecordReactionSoap;
import friends.cringe.soap.ListRecordSoap;
import friends.cringe.soap.RatingByUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ListRecordMapper {

  ListRecordReactionDto toDto(ListRecordReactionSoap listRecordReactionSoap);

  @Mapping(target = "id", source = "id", qualifiedByName = "uuidToString")
  @Mapping(target = "listId", source = "listId", qualifiedByName = "uuidToString")
  @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "uuidToString")
  @Mapping(target = "updatedBy", source = "updatedBy", qualifiedByName = "uuidToString")
  ListRecordSoap toSoap(ListRecordDto listRecordDto);

  @Mapping(target = "id", source = "id", qualifiedByName = "stringToUUID")
  @Mapping(target = "listId", source = "listId", qualifiedByName = "stringToUUID")
  @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "stringToUUID")
  @Mapping(target = "updatedBy", source = "updatedBy", qualifiedByName = "stringToUUID")
  ListRecordDto toDto(ListRecordSoap listRecordSoap);

  @Mapping(target = "userId", source = "userId", qualifiedByName = "stringToUUID")
  ListRecordDto.RatingByUser toDto(RatingByUser ratingSoap);

  @Mapping(target = "userId", source = "userId", qualifiedByName = "uuidToString")
  RatingByUser toSoap(ListRecordDto.RatingByUser ratingDto);

  @Named("uuidToString")
  default String uuidToString(UUID uuid) {
    return uuid == null ? null : uuid.toString();
  }

  @Named("stringToUUID")
  default UUID uuidToString(String uuid) {
    return uuid == null ? null : UUID.fromString(uuid);
  }
}
