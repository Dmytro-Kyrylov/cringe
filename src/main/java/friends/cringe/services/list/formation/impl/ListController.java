package friends.cringe.services.list.formation.impl;

import friends.cringe.common.security.SecurityService;
import friends.cringe.services.list.formation.api.ListDto;
import friends.cringe.services.list.formation.api.ListService;
import friends.cringe.services.list.formation.api.ListUrl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ListController {

  @Setter(onMethod_ = @Autowired)
  private ListService listService;

  @Setter(onMethod_ = @Autowired)
  private SecurityService securityService;

  @Operation(summary = "Get list by name")
  @GetMapping(ListUrl.GET)
  public ListDto get(@PathVariable String name) {
    return listService.get(name);
  }

  @Operation(summary = "Get all lists")
  @GetMapping(ListUrl.GET_ALL)
  public List<ListDto> getAll() {
    return listService.getAll();
  }

  @Operation(summary = "Create new list")
  @PostMapping(ListUrl.CREATE)
  public ListDto create(@Valid @RequestBody ListDto listDto, Authentication authentication) {
    return listService.create(listDto, securityService.getByAuthentication(authentication));
  }

  @Operation(summary = "Update list")
  @PatchMapping(ListUrl.UPDATE)
  public ListDto update(@PathVariable String name, @Valid @RequestBody ListDto listDto, Authentication authentication) {
    return listService.update(name, listDto, securityService.getByAuthentication(authentication));
  }

  @Operation(summary = "Delete list")
  @DeleteMapping(ListUrl.DELETE)
  public void update(@PathVariable String name) {
    listService.delete(name);
  }

}
