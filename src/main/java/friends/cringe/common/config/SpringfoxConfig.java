package friends.cringe.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringfoxConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
        .ignoredParameterTypes(Authentication.class)
        .select()
        .apis(RequestHandlerSelectors.basePackage("friends.cringe.services"))
        .paths(PathSelectors.any())
        .build();
  }

}
