package friends.cringe.services.graphql.list_record;

import friends.cringe.services.list.record.api.ListRecordDto;
import friends.cringe.services.list.record.api.ListRecordReactionDto;
import friends.cringe.services.list.record.api.ListRecordService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
@Component
public class ListRecordMutation implements GraphQLMutationResolver {

  @Setter(onMethod_ = @Autowired)
  private ListRecordService listRecordService;

  @Setter(onMethod_ = @Autowired)
  private ListRecordQLMapper listRecordQLMapper;

  public ListRecordQL createListRecord(Long listQualifier, @NotNull @Length(min = 1) String body) {
    return listRecordQLMapper.toQL(listRecordService.create(listQualifier, new ListRecordDto(body)));
  }

  public ListRecordQL updateListRecord(Long listQualifier, UUID id, @NotNull @Length(min = 1) String body) {
    return listRecordQLMapper.toQL(listRecordService.update(listQualifier, id, new ListRecordDto(body)));
  }

  public ListRecordQL updateRatingForListRecord(Long listQualifier, UUID id, @NotNull @Min(-1) @Max(1) Long value) {
    return listRecordQLMapper.toQL(listRecordService.updateRating(listQualifier, id, new ListRecordReactionDto(value)));
  }

  public boolean deleteListRecord(Long listQualifier, UUID id) {
    listRecordService.delete(listQualifier, id);
    return true;
  }

}
