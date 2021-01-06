package friends.cringe.common.security.impl;

import friends.cringe.common.exception.ExceptionType;
import friends.cringe.common.exception.ExternalException;
import friends.cringe.services.user.api.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Setter(onMethod_ = @Autowired)
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    try {
      return new UserDetailsImpl(userService.get(username));
    } catch (ExternalException e) {
      if (e.getType().equals(ExceptionType.USER_NOT_FOUND)) {
        throw new UsernameNotFoundException(e.getMessage());
      } else {
        throw e;
      }
    }
  }

}