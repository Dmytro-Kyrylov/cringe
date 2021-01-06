package friends.cringe.services.list.record.api;

import friends.cringe.services.list.formation.api.ListUrl;

public interface ListRecordUrl {

  String RESOURCE_NAME = "/record";

  String RATING_RESOURCE = "/reaction";

  String ID = "/{id}";

  String BASE = ListUrl.GET + RESOURCE_NAME;

  String GET = BASE + ID;

  String GET_ALL = BASE;

  String CREATE = BASE;

  String UPDATE = BASE + ID;

  String DELETE = BASE + ID;

  String UPDATE_REACTION = UPDATE + RATING_RESOURCE;

}
