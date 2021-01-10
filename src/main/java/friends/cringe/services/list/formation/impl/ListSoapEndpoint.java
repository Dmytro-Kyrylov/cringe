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
  public SingleListResponse getList(@RequestPayload GetListRequest request) {
    SingleListResponse response = new SingleListResponse();
    response.setList(listMapper.toSoap(listService.get(request.getQualifier().longValue())));

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
  public SingleListResponse createList(@RequestPayload CreateListRequest request) {
    SingleListResponse response = new SingleListResponse();
    response.setList(listMapper.toSoap(listService.create(listMapper.toDto(request.getList()))));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "updateListRequest")
  @ResponsePayload
  public SingleListResponse updateList(@RequestPayload UpdateListRequest request) {
    SingleListResponse response = new SingleListResponse();
    response.setList(listMapper.toSoap(listService.update(request.getQualifier().longValue(), listMapper.toDto(request.getList()))));

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "deleteListRequest")
  @ResponsePayload
  public void deleteList(@RequestPayload DeleteListRequest request) {
    listService.delete(request.getQualifier().longValue());
  }

}
