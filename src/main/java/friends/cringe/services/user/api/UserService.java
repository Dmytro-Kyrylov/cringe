package friends.cringe.services.user.api;

import java.util.List;
import java.util.UUID;

public interface UserService {

  UserDto get(UUID id);

  UserDto get(String username);

  List<UserDto> getAll();

}
