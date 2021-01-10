package friends.cringe.services.list.record.impl;

import friends.cringe.services.list.record.api.ListRecordDto;
import friends.cringe.services.list.record.api.ListRecordReactionDto;
import friends.cringe.services.list.record.api.ListRecordService;
import friends.cringe.services.list.record.api.ListRecordUrl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class ListRecordController {

  @Setter(onMethod_ = @Autowired)
  private ListRecordService listRecordService;

  @Operation(summary = "Get list record by id and list qualifier")
  @GetMapping(ListRecordUrl.GET)
  public ListRecordDto get(@PathVariable Long qualifier, @PathVariable UUID id) {
    return listRecordService.get(qualifier, id);
  }

  @Operation(summary = "Get all list records list qualifier")
  @GetMapping(ListRecordUrl.GET_ALL)
  public List<ListRecordDto> getAll(@PathVariable Long qualifier) {
    return listRecordService.getAll(qualifier);
  }

  @Operation(summary = "Create new list record")
  @PostMapping(ListRecordUrl.CREATE)
  public ListRecordDto create(@PathVariable Long qualifier, @Valid @RequestBody ListRecordDto listRecordDto) {
    return listRecordService.create(qualifier, listRecordDto);
  }

  @Operation(summary = "Update list record")
  @PatchMapping(ListRecordUrl.UPDATE)
  public ListRecordDto update(@PathVariable Long qualifier, @PathVariable UUID id,
                              @Valid @RequestBody ListRecordDto listRecordDto) {
    return listRecordService.update(qualifier, id, listRecordDto);
  }

  @Operation(summary = "Update user reaction for list record", description = "Change rating")
  @PatchMapping(ListRecordUrl.UPDATE_REACTION)
  public ListRecordDto updateReaction(@PathVariable Long qualifier, @PathVariable UUID id,
                              @Valid @RequestBody ListRecordReactionDto dto) {
    return listRecordService.updateRating(qualifier, id, dto);
  }

  @Operation(summary = "Delete list record by id and list qualifier")
  @DeleteMapping(ListRecordUrl.DELETE)
  public void delete(@PathVariable Long qualifier, @PathVariable UUID id) {
    listRecordService.delete(qualifier, id);
  }

}
