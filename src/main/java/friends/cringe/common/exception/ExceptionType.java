package friends.cringe.common.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {
  UNKNOWN("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
  BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST),

  LIST_NOT_FOUND("List not found", HttpStatus.NOT_FOUND),

  LIST_RECORD_NOT_FOUND("List record not found", HttpStatus.NOT_FOUND),

  USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND);

  @JsonValue
  private final String message;

  private final HttpStatus status;
}
