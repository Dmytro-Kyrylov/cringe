package friends.cringe.services.list.record.impl;

import friends.cringe.common.exception.ExceptionType;
import friends.cringe.common.exception.ExternalException;
import friends.cringe.common.security.impl.SecurityServiceImpl;
import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.services.list.formation.api.ListService;
import friends.cringe.services.list.record.api.ListRecordDto;
import friends.cringe.services.list.record.api.ListRecordReactionDto;
import friends.cringe.services.list.record.api.ListRecordService;
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

  @Setter(onMethod_ = @Autowired)
  private SecurityServiceImpl securityService;

  @Override
  public ListRecordDto get(Long listQualifier, UUID id) {
    return dsl.select(getListRecordDtoSelectStatement())
        .from(LIST_RECORD)
        .leftJoin(LIST_RECORD_REACTION).onKey()
        .innerJoin(LIST).on(LIST.QUALIFIER.eq(LIST_RECORD.LIST_QUALIFIER))
        .where(LIST_RECORD.DELETED.isFalse().and(LIST.QUALIFIER.eq(listQualifier).and(LIST_RECORD.ID.eq(id))))
        .groupBy(LIST_RECORD.ID)
        .fetchOptionalInto(ListRecordDto.class)
        .orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.LIST_RECORD_NOT_FOUND)
                .arg("listQualifier", listQualifier.toString())
                .arg("id", id.toString())
                .build()
        );
  }

  @Override
  public List<ListRecordDto> getAll(Long listQualifier) {
    return dsl.select(getListRecordDtoSelectStatement())
        .from(LIST_RECORD)
        .leftJoin(LIST_RECORD_REACTION).onKey()
        .innerJoin(LIST).on(LIST.QUALIFIER.eq(LIST_RECORD.LIST_QUALIFIER))
        .where(LIST_RECORD.DELETED.isFalse().and(LIST.QUALIFIER.eq(listQualifier)))
        .groupBy(LIST_RECORD.ID)
        .fetchInto(ListRecordDto.class);
  }

  @Transactional
  @Override
  public ListRecordDto create(Long listQualifier, ListRecordDto dto) {
    ListDto listDto = listService.get(listQualifier);

    ListRecordRecord listRecordRecord = dsl.newRecord(LIST_RECORD);

    listRecordRecord.setId(UUID.randomUUID());
    listRecordRecord.setBody(dto.getBody());
    listRecordRecord.setListQualifier(listDto.getQualifier());
    listRecordRecord.setCreatedBy(securityService.getCurrentUserId());
    listRecordRecord.setUpdatedBy(securityService.getCurrentUserId());

    listRecordRecord.insert();

    return get(listQualifier, listRecordRecord.getId());
  }

  @Transactional
  @Override
  public ListRecordDto update(Long listQualifier, UUID id, ListRecordDto dto) {
    ListRecordRecord listRecordRecord = dsl.select(LIST_RECORD.fields())
        .from(LIST_RECORD)
        .innerJoin(LIST).on(LIST.QUALIFIER.eq(LIST_RECORD.LIST_QUALIFIER))
        .where(LIST_RECORD.DELETED.isFalse().and(LIST.QUALIFIER.eq(listQualifier)).and(LIST_RECORD.ID.eq(id)))
        .fetchOptionalInto(LIST_RECORD)
        .orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.LIST_RECORD_NOT_FOUND)
                .arg("listQualifier", listQualifier.toString())
                .arg("id", id.toString())
                .build()
        );

    listRecordRecord.setBody(dto.getBody());
    listRecordRecord.setUpdatedAt(LocalDateTime.now());
    listRecordRecord.setUpdatedBy(securityService.getCurrentUserId());

    listRecordRecord.update();

    return get(listQualifier, id);
  }

  @Transactional
  @Override
  public ListRecordDto updateRating(Long listQualifier, UUID id, ListRecordReactionDto dto) {
    ListRecordDto listRecordDto = get(listQualifier, id);

    if (!dsl.fetchExists(LIST_RECORD_REACTION,
        LIST_RECORD_REACTION.LIST_RECORD_ID.eq(listRecordDto.getId())
            .and(LIST_RECORD_REACTION.USER_ID.eq(securityService.getCurrentUserId())))) {
      dsl.insertInto(LIST_RECORD_REACTION)
          .set(LIST_RECORD_REACTION.USER_ID, securityService.getCurrentUserId())
          .set(LIST_RECORD_REACTION.LIST_RECORD_ID, listRecordDto.getId())
          .execute();
    }

    dsl.update(LIST_RECORD_REACTION)
        .set(LIST_RECORD_REACTION.RATING, LIST_RECORD_REACTION.RATING.add(dto.getValue()))
        .where(LIST_RECORD_REACTION.LIST_RECORD_ID.eq(listRecordDto.getId())
            .and(LIST_RECORD_REACTION.USER_ID.eq(securityService.getCurrentUserId())))
        .execute();

    return get(listQualifier, id);
  }

  @Transactional
  @Override
  public void delete(Long listQualifier, UUID id) {
    ListDto listDto = listService.get(listQualifier);
    dsl.update(LIST_RECORD)
        .set(LIST_RECORD.DELETED, true)
        .where(LIST_RECORD.ID.eq(id).and(LIST_RECORD.LIST_QUALIFIER.eq(listDto.getQualifier())))
        .execute();
  }

  private JSONObjectNullStep<JSON> getListRecordDtoSelectStatement() {
    return jsonObject(
        jsonEntry("id", LIST_RECORD.ID),
        jsonEntry("createdAt", LIST_RECORD.CREATED_AT),
        jsonEntry("createdBy", LIST_RECORD.CREATED_BY),
        jsonEntry("updatedAt", LIST_RECORD.UPDATED_AT),
        jsonEntry("updatedBy", LIST_RECORD.UPDATED_BY),
        jsonEntry("listQualifier", LIST_RECORD.LIST_QUALIFIER),
        jsonEntry("body", LIST_RECORD.BODY),
        jsonEntry("rating", DSL.coalesce(sum(LIST_RECORD_REACTION.RATING), 0)),
        jsonEntry("ratingByUsers",
            case_()
                .when(count(LIST_RECORD_REACTION.USER_ID).greaterThan(0),
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
