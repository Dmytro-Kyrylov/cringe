package friends.cringe.services.graphql.list_record;

import friends.cringe.services.list.record.api.ListRecordDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ListRecordQLMapper {

  ListRecordQL toQL(ListRecordDto dto);

}
