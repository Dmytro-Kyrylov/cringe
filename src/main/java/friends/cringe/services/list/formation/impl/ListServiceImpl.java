package friends.cringe.services.list.formation.impl;

import friends.cringe.common.config.CacheConfig;
import friends.cringe.common.exception.ExceptionType;
import friends.cringe.common.exception.ExternalException;
import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.services.list.formation.api.ListService;
import friends.cringe.services.user.api.UserDto;
import lombok.Setter;
import org.jooq.DSLContext;
import org.jooq.codegen.tables.records.ListRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.codegen.tables.List.LIST;

@Service
public class ListServiceImpl implements ListService {

  @Setter(onMethod_ = @Autowired)
  private DSLContext dsl;

  @Cacheable(CacheConfig.LIST_FORMATION_CACHE)
  @Override
  public ListDto get(String name) {
    return dsl.selectFrom(LIST).where(LIST.NAME.eq(name).and(LIST.DELETED.isFalse())).fetchOptional()
        .map(x ->
            ListDto.builder()
                .id(x.getId())
                .createdAt(x.getCreatedAt())
                .createdBy(x.getCreatedBy())
                .updatedAt(x.getUpdatedAt())
                .updatedBy(x.getUpdatedBy())
                .name(x.getName())
                .title(x.getTitle())
                .description(x.getDescription())
                .build()
        ).orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.LIST_NOT_FOUND)
                .arg("name", name)
                .build()
        );
  }

  @Override
  public List<ListDto> getAll() {
    return dsl.selectFrom(LIST)
        .where(LIST.DELETED.isFalse())
        .orderBy(LIST.UPDATED_AT.desc())
        .fetch().map(x ->
            ListDto.builder()
                .id(x.getId())
                .createdAt(x.getCreatedAt())
                .createdBy(x.getCreatedBy())
                .updatedAt(x.getUpdatedAt())
                .updatedBy(x.getUpdatedBy())
                .title(x.getTitle())
                .name(x.getName())
                .description(x.getDescription())
                .build()
        );
  }

  @Transactional
  @Override
  public ListDto create(ListDto dto, UserDto userDto) {
    if (dsl.fetchExists(LIST, LIST.NAME.eq(dto.getName()).and(LIST.DELETED.isFalse()))) {
      throw ExternalException.builder()
          .type(ExceptionType.LIST_ALREADY_EXISTS)
          .arg("name", dto.getName())
          .build();
    }

    ListRecord listRecord;

    Optional<ListRecord> listRecordOptional =
        dsl.fetchOptional(LIST, LIST.NAME.eq(dto.getName()).and(LIST.DELETED.isTrue()));
    if (listRecordOptional.isPresent()) {
      listRecord = listRecordOptional.get();
    } else {
      listRecord = dsl.newRecord(LIST);

      listRecord.setId(UUID.randomUUID());
      listRecord.setName(dto.getName());
      listRecord.setCreatedBy(userDto.getId());
    }

    initUpdateFields(dto, userDto.getId(), listRecord);

    listRecord.store();

    return get(dto.getName());
  }

  @CacheEvict(key = "#name", value = CacheConfig.LIST_FORMATION_CACHE)
  @Transactional
  @Override
  public ListDto update(String name, ListDto dto, UserDto userDto) {
    if (!dsl.fetchExists(LIST, LIST.NAME.eq(name).and(LIST.DELETED.isFalse()))) {
      throw ExternalException.builder()
          .type(ExceptionType.LIST_NOT_FOUND)
          .arg("name", name)
          .build();
    }

    ListRecord listRecord = dsl.fetchSingle(LIST, LIST.NAME.eq(name));

    initUpdateFields(dto, userDto.getId(), listRecord);

    return get(name);
  }

  @CacheEvict(key = "#name", value = CacheConfig.LIST_FORMATION_CACHE)
  @Transactional
  @Override
  public void delete(String name) {
    dsl.update(LIST).set(LIST.DELETED, true).where(LIST.NAME.eq(name)).execute();
  }

  private void initUpdateFields(ListDto dto, UUID userId, ListRecord listRecord) {
    listRecord.setUpdatedAt(LocalDateTime.now());
    listRecord.setUpdatedBy(userId);
    listRecord.setTitle(dto.getTitle());
    listRecord.setDescription(dto.getDescription());
    listRecord.setDeleted(false);
  }
}
