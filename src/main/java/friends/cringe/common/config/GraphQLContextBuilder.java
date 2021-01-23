package friends.cringe.common.config;

import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;

@Configuration
public class GraphQLContextBuilder implements GraphQLServletContextBuilder {

  @Setter(onMethod_ = @Autowired)
  private DataloaderRegistryFactory dataloaderRegistryFactory;

  @Override
  public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    return DefaultGraphQLServletContext.createServletContext()
        .with(httpServletRequest)
        .with(httpServletResponse)
        .with(dataloaderRegistryFactory.dataLoaderRegistry())
        .build();
  }

  @Override
  public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
    return null;
  }

  @Override
  public GraphQLContext build() {
    return null;
  }
}
