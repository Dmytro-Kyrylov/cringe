package friends.cringe.common.security.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityServiceImpl {

  public UUID getCurrentUserId() {
    return getUserDetails(getAuthentication()).getUserDto().getId();
  }

  public UserDetails getUserDetails() {
    return getUserDetails(getAuthentication());
  }

  private UserDetailsImpl getUserDetails(Authentication authentication) {
    return ((UserDetailsImpl) authentication.getPrincipal());
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

}
