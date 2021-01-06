package friends.cringe.common.security.impl;

import friends.cringe.common.security.SecurityService;
import friends.cringe.services.user.api.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

  @Override
  public UserDto getByAuthentication(Authentication authentication) {
    return ((UserDetailsImpl) authentication.getPrincipal()).getUserDto();
  }

}
