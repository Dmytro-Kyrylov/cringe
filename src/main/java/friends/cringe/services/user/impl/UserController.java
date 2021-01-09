package friends.cringe.services.user.impl;

import friends.cringe.common.security.SecurityService;
import friends.cringe.services.user.api.UserDto;
import friends.cringe.services.user.api.UserService;
import friends.cringe.services.user.api.UserUrl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

  @Setter(onMethod_ = @Autowired)
  private UserService userService;

  @Setter(onMethod_ = @Autowired)
  private SecurityService securityService;

  @Operation(summary = "Get current user")
  @GetMapping(UserUrl.GET)
  public UserDto get(Authentication authentication) {
    return userService.get(securityService.getByAuthentication(authentication).getId());
  }

  @Operation(summary = "Get user by id")
  @GetMapping(UserUrl.GET_BY_ID)
  public UserDto getById(@PathVariable UUID id) {
    return userService.get(id);
  }

  @Operation(summary = "Get all users")
  @GetMapping(UserUrl.GET_ALL)
  public List<UserDto> getAll() {
    return userService.getAll();
  }

}
