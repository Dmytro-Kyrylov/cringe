package friends.cringe.common.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class ErrorDto {
  private final ExceptionType type;
  private final String message;
  private Map<String, String> args;

  public ErrorDto(ExternalException exception) {
    this.type = exception.getType();
    this.message = exception.getMessage();
    this.args = exception.getArgs();
  }

}
