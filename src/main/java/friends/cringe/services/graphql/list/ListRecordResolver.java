package friends.cringe.services.graphql.list;

import friends.cringe.common.config.DataloaderRegistryFactory;
import friends.cringe.services.graphql.list_record.ListRecordQL;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ListRecordResolver implements GraphQLResolver<ListQL> {

  public CompletableFuture<List<ListRecordQL>> records(ListQL listQL, DataFetchingEnvironment environment) {
    DataLoader<Long, List<ListRecordQL>> dataLoader =
        environment.getDataLoader(DataloaderRegistryFactory.LIST_LIST_RECORD_DATA_LOADER);
   return dataLoader.load(listQL.getQualifier());
  }

}
