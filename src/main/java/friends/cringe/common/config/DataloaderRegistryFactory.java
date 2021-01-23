package friends.cringe.common.config;

import friends.cringe.services.graphql.list.ListLoaderService;
import friends.cringe.services.graphql.list_record.ListRecordQL;
import lombok.Setter;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class DataloaderRegistryFactory {

  @Setter(onMethod_ = @Autowired)
  private ListLoaderService listLoaderService;

  public static final String LIST_LIST_RECORD_DATA_LOADER = "LIST_LIST_RECORD_DATA_LOADER";

  private static final Executor listListRecordThreadPool =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  public DataLoaderRegistry dataLoaderRegistry() {
    DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();

    dataLoaderRegistry.register(LIST_LIST_RECORD_DATA_LOADER, createListListRecordDataLoader());

    return dataLoaderRegistry;
  }

  private DataLoader<Long, List<ListRecordQL>> createListListRecordDataLoader() {
    return DataLoader.newMappedDataLoader((Set<Long> listQualifiers) -> {
      return CompletableFuture.supplyAsync(() -> {
        return listLoaderService.getRecordsForList(listQualifiers);
      }, listListRecordThreadPool);
    });
  }

}
