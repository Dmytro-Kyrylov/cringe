package friends.cringe.services.list.formation.impl;

import friends.cringe.common.config.CacheConfig;
import friends.cringe.common.exception.ExceptionType;
import friends.cringe.common.exception.ExternalException;
import friends.cringe.common.security.impl.SecurityServiceImpl;
import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.services.list.formation.api.ListService;
import lombok.Setter;
import org.jooq.DSLContext;
import org.jooq.codegen.Sequences;
import org.jooq.codegen.tables.records.ListRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.jooq.codegen.tables.List.LIST;

@Service
public class ListServiceImpl implements ListService {

  @Setter(onMethod_ = @Autowired)
  private DSLContext dsl;

  @Setter(onMethod_ = @Autowired)
  private SecurityServiceImpl securityService;

  @Cacheable(CacheConfig.LIST_FORMATION_CACHE)
  @Override
  public ListDto get(Long qualifier) {
    return dsl.selectFrom(LIST).where(LIST.QUALIFIER.eq(qualifier).and(LIST.DELETED.isFalse()))
        .fetchOptionalInto(ListDto.class)
        .orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.LIST_NOT_FOUND)
                .arg("qualifier", qualifier.toString())
                .build()
        );
  }

  @Override
  public List<ListDto> getAll() {
    return dsl.selectFrom(LIST)
        .where(LIST.DELETED.isFalse())
        .orderBy(LIST.UPDATED_AT.desc())
        .fetchInto(ListDto.class);
  }

  @Override
  public ListDto create(ListDto dto) {
    ListRecord listRecord = dsl.newRecord(LIST);

    Long newQualifier = dsl.nextval(Sequences.LIST_QUALIFIER_SEQ);

    listRecord.setId(UUID.randomUUID());
    listRecord.setQualifier(newQualifier);
    listRecord.setCreatedBy(securityService.getCurrentUserId());

    initUpdateFields(dto, securityService.getCurrentUserId(), listRecord);

    listRecord.insert();

    return get(newQualifier);
  }

  @CacheEvict(key = "#qualifier", value = CacheConfig.LIST_FORMATION_CACHE)
  @Transactional
  @Override
  public ListDto update(Long qualifier, ListDto dto) {
    if (!dsl.fetchExists(LIST, LIST.QUALIFIER.eq(qualifier).and(LIST.DELETED.isFalse()))) {
      throw ExternalException.builder()
          .type(ExceptionType.LIST_NOT_FOUND)
          .arg("qualifier", qualifier.toString())
          .build();
    }

    ListRecord listRecord = dsl.fetchSingle(LIST, LIST.QUALIFIER.eq(qualifier));

    initUpdateFields(dto, securityService.getCurrentUserId(), listRecord);

    listRecord.update();

    return get(qualifier);
  }

  @CacheEvict(key = "#qualifier", value = CacheConfig.LIST_FORMATION_CACHE)
  @Transactional
  @Override
  public void delete(Long qualifier) {
    dsl.update(LIST).set(LIST.DELETED, true).where(LIST.QUALIFIER.eq(qualifier)).execute();
  }

  private void initUpdateFields(ListDto dto, UUID userId, ListRecord listRecord) {
    listRecord.setUpdatedAt(LocalDateTime.now());
    listRecord.setUpdatedBy(userId);
    listRecord.setTitle(dto.getTitle());
    listRecord.setDescription(dto.getDescription());
    listRecord.setDeleted(false);
  }
}
