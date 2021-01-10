package friends.cringe.common.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditableDto extends UUIDDto {

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  private UUID createdBy;

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createdAt;

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  private UUID updatedBy;

  @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_ONLY, readOnly = true)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime updatedAt;

}
