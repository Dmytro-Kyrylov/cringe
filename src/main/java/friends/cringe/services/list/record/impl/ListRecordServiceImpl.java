package friends.cringe.services.list.record.impl;

import friends.cringe.common.exception.ExceptionType;
import friends.cringe.common.exception.ExternalException;
import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.services.list.formation.api.ListService;
import friends.cringe.services.list.record.api.ListRecordDto;
import friends.cringe.services.list.record.api.ListRecordReactionDto;
import friends.cringe.services.list.record.api.ListRecordService;
import friends.cringe.services.user.api.UserDto;
import lombok.Setter;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.JSONObjectNullStep;
import org.jooq.codegen.tables.records.ListRecordRecord;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.jooq.codegen.tables.List.LIST;
import static org.jooq.codegen.tables.ListRecord.LIST_RECORD;
import static org.jooq.codegen.tables.ListRecordReaction.LIST_RECORD_REACTION;
import static org.jooq.impl.DSL.*;

@Service
public class ListRecordServiceImpl implements ListRecordService {

  @Setter(onMethod_ = @Autowired)
  private DSLContext dsl;

  @Setter(onMethod_ = @Autowired)
  private ListService listService;

  @Override
  public ListRecordDto get(String listName, UUID id) {
    return dsl.select(getListRecordDtoSelectStatement())
        .from(LIST_RECORD)
        .leftJoin(LIST_RECORD_REACTION).onKey()
        .innerJoin(LIST).onKey()
        .where(LIST_RECORD.DELETED.isFalse().and(LIST.NAME.eq(listName).and(LIST_RECORD.ID.eq(id))))
        .groupBy(LIST_RECORD.ID)
        .fetchOptionalInto(ListRecordDto.class)
        .orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.LIST_RECORD_NOT_FOUND)
                .arg("list", listName)
                .arg("id", id.toString())
                .build()
        );
  }

  @Override
  public List<ListRecordDto> getAll(String listName) {
    return dsl.select(getListRecordDtoSelectStatement())
        .from(LIST_RECORD)
        .leftJoin(LIST_RECORD_REACTION).onKey()
        .innerJoin(LIST).onKey()
        .where(LIST_RECORD.DELETED.isFalse().and(LIST.NAME.eq(listName)))
        .groupBy(LIST_RECORD.ID)
        .fetchInto(ListRecordDto.class);
  }

  @Transactional
  @Override
  public ListRecordDto create(String listName, ListRecordDto dto, UserDto userDto) {
    ListDto listDto = listService.get(listName);

    ListRecordRecord listRecordRecord = dsl.newRecord(LIST_RECORD);

    listRecordRecord.setId(UUID.randomUUID());
    listRecordRecord.setBody(dto.getBody());
    listRecordRecord.setListId(listDto.getId());
    listRecordRecord.setCreatedBy(userDto.getId());
    listRecordRecord.setUpdatedBy(userDto.getId());

    listRecordRecord.insert();

    return get(listName, listRecordRecord.getId());
  }

  @Transactional
  @Override
  public ListRecordDto update(String listName, UUID id, ListRecordDto dto, UserDto userDto) {
    ListRecordRecord listRecordRecord = dsl.select(LIST_RECORD.fields())
        .from(LIST_RECORD)
        .innerJoin(LIST).onKey()
        .where(LIST_RECORD.DELETED.isFalse().and(LIST.NAME.eq(listName)).and(LIST_RECORD.ID.eq(id)))
        .fetchOptionalInto(LIST_RECORD)
        .orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.LIST_RECORD_NOT_FOUND)
                .arg("list", listName)
                .arg("id", id.toString())
                .build()
        );

    listRecordRecord.setBody(dto.getBody());
    listRecordRecord.setUpdatedAt(LocalDateTime.now());
    listRecordRecord.setUpdatedBy(userDto.getId());

    listRecordRecord.update();

    return get(listName, id);
  }

  @Transactional
  @Override
  public ListRecordDto updateRating(String listName, UUID id, ListRecordReactionDto dto, UserDto userDto) {
    ListRecordDto listRecordDto = get(listName, id);

    if (!dsl.fetchExists(LIST_RECORD_REACTION,
        LIST_RECORD_REACTION.LIST_RECORD_ID.eq(listRecordDto.getId())
            .and(LIST_RECORD_REACTION.USER_ID.eq(userDto.getId())))) {
      dsl.insertInto(LIST_RECORD_REACTION)
          .set(LIST_RECORD_REACTION.ID, UUID.randomUUID())
          .set(LIST_RECORD_REACTION.USER_ID, userDto.getId())
          .set(LIST_RECORD_REACTION.LIST_RECORD_ID, listRecordDto.getId())
          .execute();
    }

    dsl.update(LIST_RECORD_REACTION)
        .set(LIST_RECORD_REACTION.RATING, LIST_RECORD_REACTION.RATING.add(dto.getValue()))
        .where(LIST_RECORD_REACTION.LIST_RECORD_ID.eq(listRecordDto.getId())
            .and(LIST_RECORD_REACTION.USER_ID.eq(userDto.getId())))
        .execute();

    return get(listName, id);
  }

  @Transactional
  @Override
  public void delete(String listName, UUID id) {
    ListDto listDto = listService.get(listName);
    dsl.update(LIST_RECORD)
        .set(LIST_RECORD.DELETED, true)
        .where(LIST_RECORD.ID.eq(id).and(LIST_RECORD.LIST_ID.eq(listDto.getId())))
        .execute();
  }

  private JSONObjectNullStep<JSON> getListRecordDtoSelectStatement() {
    return jsonObject(
        jsonEntry("id", LIST_RECORD.ID),
        jsonEntry("createdAt", LIST_RECORD.CREATED_AT),
        jsonEntry("createdBy", LIST_RECORD.CREATED_BY),
        jsonEntry("updatedAt", LIST_RECORD.UPDATED_AT),
        jsonEntry("updatedBy", LIST_RECORD.UPDATED_BY),
        jsonEntry("listId", LIST_RECORD.LIST_ID),
        jsonEntry("body", LIST_RECORD.BODY),
        jsonEntry("rating", DSL.coalesce(sum(LIST_RECORD_REACTION.RATING), 0)),
        jsonEntry("ratingByUsers",
            case_()
                .when(count(LIST_RECORD_REACTION.ID).greaterThan(0),
                    jsonArrayAgg(
                        jsonObject(
                            jsonEntry("rating", LIST_RECORD_REACTION.RATING),
                            jsonEntry("userId", LIST_RECORD_REACTION.USER_ID)
                        )
                    )
                ).else_(DSL.jsonArray())
        )
    );
  }
}
