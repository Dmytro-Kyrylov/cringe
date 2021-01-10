package friends.cringe.services.user.impl;

import friends.cringe.common.config.WebServiceConfig;
import friends.cringe.services.user.api.UserService;
import friends.cringe.soap.GetAllUsersResponse;
import friends.cringe.soap.GetUserRequest;
import friends.cringe.soap.SingleUserResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;
import java.util.stream.Collectors;

@Endpoint
public class UserSoapEndpoint {

  @Setter(onMethod_ = @Autowired)
  private UserService userService;

  @Setter(onMethod_ = @Autowired)
  private UserMapper userMapper;

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "getCurrentUserRequest")
  @ResponsePayload
  public SingleUserResponse get() {
    SingleUserResponse response = new SingleUserResponse();
    response.setUser(userMapper.toSoap(userService.getCurrent()));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "getUserRequest")
  @ResponsePayload
  public SingleUserResponse getById(@RequestPayload GetUserRequest request) {
    SingleUserResponse response = new SingleUserResponse();
    response.setUser(userMapper.toSoap(userService.get(UUID.fromString(request.getId()))));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "getAllUsersRequest")
  @ResponsePayload
  public GetAllUsersResponse getAll() {
    GetAllUsersResponse response = new GetAllUsersResponse();
    response.getUsers().addAll(userService.getAll().stream().map(userMapper::toSoap).collect(Collectors.toSet()));

    return response;
  }
}
