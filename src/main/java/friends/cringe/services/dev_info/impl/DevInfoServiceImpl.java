package friends.cringe.services.dev_info.impl;

import com.zaxxer.hikari.HikariDataSource;
import friends.cringe.common.exception.ExceptionType;
import friends.cringe.common.exception.ExternalException;
import friends.cringe.services.dev_info.api.DevInfoDto;
import friends.cringe.services.dev_info.api.DevInfoService;
import lombok.Setter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.codegen.Tables;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevInfoServiceImpl implements DevInfoService {

  @Setter(onMethod_ = @Autowired)
  private DSLContext dsl;

  @Setter(onMethod_ = @Autowired)
  private DataSource dataSource;

  @Override
  public DevInfoDto get() {
    DevInfoDto devInfoDto = new DevInfoDto();

    try {
      devInfoDto.setDatabaseSize(getDatabaseSize());
      devInfoDto.setDatabaseRecordsCount(getRecordsCount());
    } catch (Exception e) {
      throw ExternalException.builder()
          .type(ExceptionType.UNKNOWN)
          .message(e.getMessage())
          .arg("stacktrace", Arrays.toString(e.getStackTrace()))
          .build();
    }

    return devInfoDto;
  }

  private String getDatabaseSize() {
    String jdbcUrl = ((HikariDataSource) dataSource).getJdbcUrl();
    return dsl.fetchSingle(
        "select pg_size_pretty(pg_database_size('" + jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1) + "'))"
    ).map(x -> x.get(0)).toString();
  }

  private String getRecordsCount() {
    List<? extends TableImpl<?>> tableList = Arrays.stream(Tables.class.getDeclaredFields())
        .map(x -> {
          try {
            return ((TableImpl<?>) x.get(null));
          } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        })
        .collect(Collectors.toList());

    Field<Integer> subQuery = DSL.val(0);
    for (TableImpl<?> table : tableList) {
      subQuery = subQuery.add(DSL.selectCount().from(table).asField());
    }

    return dsl.select(subQuery).fetchSingle().map(x -> x.get(0)).toString();
  }
}
