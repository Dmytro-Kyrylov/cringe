package friends.cringe.services.list.formation.impl;

import friends.cringe.common.config.WebServiceConfig;
import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.services.list.formation.api.ListService;
import friends.cringe.soap.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class ListSoapEndpoint {

  @Setter(onMethod_ = @Autowired)
  private ListService listService;

  @Setter(onMethod_ = @Autowired)
  private ListMapper listMapper;

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "getListRequest")
  @ResponsePayload
  public GetListResponse getList(@RequestPayload GetListRequest request) {
    ListDto listDto = listService.get(request.getName());

    GetListResponse response = new GetListResponse();
    response.setList(listMapper.toSoap(listDto));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "getAllListsRequest")
  @ResponsePayload
  public GetAllListsResponse getAllLists() {
    List<ListDto> listDtoList = listService.getAll();

    GetAllListsResponse response = new GetAllListsResponse();
    response.getList().addAll(listDtoList.stream().map(listMapper::toSoap).collect(Collectors.toSet()));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "createListRequest")
  @ResponsePayload
  public CreateListResponse createList(@RequestPayload CreateListRequest request) {

    CreateListResponse response = new CreateListResponse();
    response.setList(listMapper.toSoap(listService.create(listMapper.toDto(request.getList()))));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "updateListRequest")
  @ResponsePayload
  public UpdateListResponse updateList(@RequestPayload UpdateListRequest request) {

    UpdateListResponse response = new UpdateListResponse();
    response.setList(listMapper.toSoap(listService.update(request.getName(), listMapper.toDto(request.getList()))));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "deleteListRequest")
  @ResponsePayload
  public void deleteList(@RequestPayload DeleteListRequest request) {
    listService.delete(request.getName());
  }

}
