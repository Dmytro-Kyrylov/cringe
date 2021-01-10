package friends.cringe.services.list.formation.impl;

import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.services.list.formation.api.ListService;
import friends.cringe.services.list.formation.api.ListUrl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ListController {

  @Setter(onMethod_ = @Autowired)
  private ListService listService;

  @Operation(summary = "Get list by qualifier")
  @GetMapping(ListUrl.GET)
  public ListDto get(@PathVariable Long qualifier) {
    return listService.get(qualifier);
  }

  @Operation(summary = "Get all lists")
  @GetMapping(ListUrl.GET_ALL)
  public List<ListDto> getAll() {
    return listService.getAll();
  }

  @Operation(summary = "Create new list")
  @PostMapping(ListUrl.CREATE)
  public ListDto create(@Valid @RequestBody ListDto listDto) {
    return listService.create(listDto);
  }

  @Operation(summary = "Update list")
  @PatchMapping(ListUrl.UPDATE)
  public ListDto update(@PathVariable Long qualifier, @Valid @RequestBody ListDto listDto) {
    return listService.update(qualifier, listDto);
  }

  @Operation(summary = "Delete list")
  @DeleteMapping(ListUrl.DELETE)
  public void update(@PathVariable Long qualifier) {
    listService.delete(qualifier);
  }

}
