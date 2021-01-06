package friends.cringe.common.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.Map;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ExternalException extends RuntimeException {

  private final ExceptionType type;

  private String message;

  @Singular
  private Map<String, String> args;

  public String getMessage() {
    if (message == null) {
      return type.getMessage();
    }
    return message;
  }

}