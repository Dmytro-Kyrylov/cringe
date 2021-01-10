package friends.cringe.services.list.record.impl;

import friends.cringe.common.config.WebServiceConfig;
import friends.cringe.services.list.record.api.ListRecordService;
import friends.cringe.soap.*;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;
import java.util.stream.Collectors;

@Endpoint
public class ListRecordSoapEndpoint {

  @Setter(onMethod_ = @Autowired)
  private ListRecordService listRecordService;

  @Setter(onMethod_ = @Autowired)
  private ListRecordMapper listRecordMapper;

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "getListRecordRequest")
  @ResponsePayload
  public SingleListRecordResponse get(@RequestPayload GetListRecordRequest request) {
    SingleListRecordResponse response = new SingleListRecordResponse();
    response.setListRecord(
        listRecordMapper.toSoap(
            listRecordService.get(request.getName(), UUID.fromString(request.getId()))
        )
    );

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "getAllListRecordsRequest")
  @ResponsePayload
  public GetAllListRecordsResponse getAll(@RequestPayload GetAllListRecordsRequest request) {
    GetAllListRecordsResponse response = new GetAllListRecordsResponse();
    response.getListRecord().addAll(
        listRecordService.getAll(request.getName())
            .stream()
            .map(listRecordMapper::toSoap)
            .collect(Collectors.toSet())
    );

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "createListRecordRequest")
  @ResponsePayload
  public SingleListRecordResponse create(@RequestPayload CreateListRecordRequest request) {
    SingleListRecordResponse response = new SingleListRecordResponse();
    response.setListRecord(
        listRecordMapper.toSoap(
            listRecordService.create(request.getName(), listRecordMapper.toDto(request.getListRecord()))
        )
    );

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "updateListRecordRequest")
  @ResponsePayload
  public SingleListRecordResponse update(@RequestPayload UpdateListRecordRequest request) {
    SingleListRecordResponse response = new SingleListRecordResponse();
    response.setListRecord(
        listRecordMapper.toSoap(
            listRecordService.update(request.getName(), UUID.fromString(request.getId()),
                listRecordMapper.toDto(request.getListRecord()))
        )
    );

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "updateListRecordReactionRequest")
  @ResponsePayload
  public SingleListRecordResponse updateReaction(@RequestPayload UpdateListRecordReactionRequest request) {
    SingleListRecordResponse response = new SingleListRecordResponse();
    response.setListRecord(
        listRecordMapper.toSoap(
            listRecordService.updateRating(request.getName(), UUID.fromString(request.getId()),
                listRecordMapper.toDto(request.getListRecordReaction()))
        )
    );

    return response;
  }

  @PayloadRoot(namespace = WebServiceConfig.NAMESPACE, localPart = "deleteListRecordRequest")
  @ResponsePayload
  public void delete(@RequestPayload DeleteListRecordRequest request) {
    listRecordService.delete(request.getName(), UUID.fromString(request.getId()));
  }

}
