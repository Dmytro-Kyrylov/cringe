package friends.cringe.services.user.api;

import friends.cringe.common.url.BaseUrl;

public interface UserUrl {

  String RESOURCE_NAME = "/user";

  String ID = "/{id}";

  String CURRENT = "/current";

  String GET = BaseUrl.BASE_REST_API + RESOURCE_NAME + CURRENT;

  String GET_BY_ID = BaseUrl.BASE_REST_API + RESOURCE_NAME + ID;

  String GET_ALL = BaseUrl.BASE_REST_API + RESOURCE_NAME;

}
