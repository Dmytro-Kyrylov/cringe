package friends.cringe.services.user.api;

import friends.cringe.common.url.BaseUrl;

public interface UserUrl {

  String RESOURCE_NAME = "/user";

  String ID = "/{id}";

  String GET = BaseUrl.BASE_API + RESOURCE_NAME + ID;

  String GET_ALL = BaseUrl.BASE_API + RESOURCE_NAME;

}
