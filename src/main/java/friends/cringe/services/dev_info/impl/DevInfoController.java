package friends.cringe.services.dev_info.impl;

import friends.cringe.services.dev_info.api.DevInfoDto;
import friends.cringe.services.dev_info.api.DevInfoService;
import friends.cringe.services.dev_info.api.DevInfoUrl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DevInfoController {

  @Setter(onMethod_ = @Autowired)
  private DevInfoService devInfoService;

  @Operation(summary = "Get developer information")
  @GetMapping(DevInfoUrl.GET)
  public DevInfoDto get() {
    return devInfoService.get();
  }

}
