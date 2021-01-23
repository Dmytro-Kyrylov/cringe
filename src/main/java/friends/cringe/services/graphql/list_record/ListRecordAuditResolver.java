package friends.cringe.services.graphql.list_record;

import friends.cringe.services.graphql.common.AuditUserResolver;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class ListRecordAuditResolver extends AuditUserResolver<ListRecordQL> implements GraphQLResolver<ListRecordQL> {

}
