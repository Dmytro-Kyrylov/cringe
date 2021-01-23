package friends.cringe.services.graphql.list;

import friends.cringe.services.graphql.common.AuditUserResolver;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class ListAuditResolver extends AuditUserResolver<ListQL> implements GraphQLResolver<ListQL> {
}
