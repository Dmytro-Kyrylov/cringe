package friends.cringe.common.security;

import friends.cringe.services.user.api.UserDto;
import org.springframework.security.core.Authentication;

public interface SecurityService {

  UserDto getByAuthentication(Authentication authentication);

}
