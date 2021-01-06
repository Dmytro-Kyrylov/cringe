package friends.cringe.services.list.record.impl;

import friends.cringe.common.security.SecurityService;
import friends.cringe.services.list.record.api.ListRecordDto;
import friends.cringe.services.list.record.api.ListRecordReactionDto;
import friends.cringe.services.list.record.api.ListRecordService;
import friends.cringe.services.list.record.api.ListRecordUrl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class ListRecordController {

  @Setter(onMethod_ = @Autowired)
  private ListRecordService listRecordService;

  @Setter(onMethod_ = @Autowired)
  private SecurityService securityService;

  @Operation(summary = "Get list record by id and list name")
  @GetMapping(ListRecordUrl.GET)
  public ListRecordDto get(@PathVariable String name, @PathVariable UUID id) {
    return listRecordService.get(name, id);
  }

  @Operation(summary = "Get all list records list name")
  @GetMapping(ListRecordUrl.GET_ALL)
  public List<ListRecordDto> getAll(@PathVariable String name) {
    return listRecordService.getAll(name);
  }

  @Operation(summary = "Create new list record")
  @PostMapping(ListRecordUrl.CREATE)
  public ListRecordDto create(@PathVariable String name, @Valid @RequestBody ListRecordDto listRecordDto,
                              Authentication authentication) {
    return listRecordService.create(name, listRecordDto, securityService.getByAuthentication(authentication));
  }

  @Operation(summary = "Update list record")
  @PatchMapping(ListRecordUrl.UPDATE)
  public ListRecordDto update(@PathVariable String name, @PathVariable UUID id,
                              @Valid @RequestBody ListRecordDto listRecordDto, Authentication authentication) {
    return listRecordService.update(name, id, listRecordDto, securityService.getByAuthentication(authentication));
  }

  @Operation(summary = "Update user reaction for list record", description = "Change rating")
  @PatchMapping(ListRecordUrl.UPDATE_REACTION)
  public ListRecordDto update(@PathVariable String name, @PathVariable UUID id,
                              @Valid @RequestBody ListRecordReactionDto dto, Authentication authentication) {
    return listRecordService.updateRating(name, id, dto, securityService.getByAuthentication(authentication));
  }

  @Operation(summary = "Delete list record by id and list name")
  @DeleteMapping(ListRecordUrl.DELETE)
  public void update(@PathVariable String name, @PathVariable UUID id) {
    listRecordService.delete(name, id);
  }

}
