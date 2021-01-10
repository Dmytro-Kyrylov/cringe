package friends.cringe.services.list.formation.api;

import friends.cringe.common.url.BaseUrl;

public interface ListUrl {

  String RESOURCE_NAME = "/list";

  String NAME = "/{name}";

  String BASE = BaseUrl.BASE_REST_API + RESOURCE_NAME;

  String GET = BASE + NAME;

  String GET_ALL = BASE;

  String CREATE = BASE;

  String UPDATE = BASE + NAME;

  String DELETE = BASE + NAME;

}
