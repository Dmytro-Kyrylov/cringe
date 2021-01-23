package friends.cringe.services.graphql.user;

import friends.cringe.services.user.api.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserQueries implements GraphQLQueryResolver {

  @Setter(onMethod_ = @Autowired)
  private UserService userService;

  @Setter(onMethod_ = @Autowired)
  private UserQLMapper userQLMapper;

  public UserQL getUserById(UUID id) {
    return userQLMapper.toQl(userService.get(id));
  }

  public UserQL getCurrentUser() {
    return userQLMapper.toQl(userService.getCurrent());
  }

  public List<UserQL> getAllUsers() {
    return userService.getAll().stream().map(userQLMapper::toQl).collect(Collectors.toList());
  }

}
