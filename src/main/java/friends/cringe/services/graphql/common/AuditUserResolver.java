package friends.cringe.services.graphql.common;

import friends.cringe.common.model.AuditableQL;
import friends.cringe.services.graphql.user.UserQL;
import friends.cringe.services.graphql.user.UserQLMapper;
import friends.cringe.services.user.api.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuditUserResolver<T extends AuditableQL> {

  @Setter(onMethod_ = @Autowired)
  private UserService userService;

  @Setter(onMethod_ = @Autowired)
  private UserQLMapper userQLMapper;

  public UserQL createdByUser(T auditableQLEntity) {
    return userQLMapper.toQl(userService.get(auditableQLEntity.getCreatedBy()));
  }

  public UserQL updatedByUser(T auditableQLEntity) {
    return userQLMapper.toQl(userService.get(auditableQLEntity.getUpdatedBy()));
  }

}
