package friends.cringe.services.user.impl;

import friends.cringe.common.config.CacheConfig;
import friends.cringe.common.exception.ExceptionType;
import friends.cringe.common.exception.ExternalException;
import friends.cringe.services.user.api.UserDto;
import friends.cringe.services.user.api.UserService;
import lombok.Setter;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.jooq.codegen.tables.User.USER;

@Service
public class UserServiceImpl implements UserService {

  @Setter(onMethod_ = @Autowired)
  private DSLContext dsl;

  @Cacheable(CacheConfig.USER_BY_ID_AND_NAME_CACHE)
  @Override
  public UserDto get(UUID id) {
    return dsl.fetchOptional(USER, USER.ID.eq(id))
        .map(x -> UserDto.builder()
            .id(x.getId())
            .username(x.getUsername())
            .password(x.getPassword())
            .build()
        ).orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.USER_NOT_FOUND)
                .arg("id", id.toString())
                .build()
        );
  }

  @Cacheable(CacheConfig.USER_BY_ID_AND_NAME_CACHE)
  @Override
  public UserDto get(String username) {
    return dsl.fetchOptional(USER, USER.USERNAME.eq(username))
        .map(x -> UserDto.builder()
            .id(x.getId())
            .username(x.getUsername())
            .password(x.getPassword())
            .build()
        ).orElseThrow(() ->
            ExternalException.builder()
                .type(ExceptionType.USER_NOT_FOUND)
                .arg("id", username)
                .build()
        );
  }

  @Override
  public List<UserDto> getAll() {
    return dsl.fetch(USER)
        .map(x -> UserDto.builder()
            .id(x.getId())
            .username(x.getUsername())
            .password(x.getPassword())
            .build()
        );
  }
}
