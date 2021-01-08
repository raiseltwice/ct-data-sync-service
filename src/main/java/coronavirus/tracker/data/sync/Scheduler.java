package coronavirus.tracker.data.sync;

import coronavirus.tracker.data.sync.provider.EntityDataProvider;
import coronavirus.tracker.data.sync.store.CoronavirusEntityData;
import coronavirus.tracker.data.sync.store.DataStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {

    private final EntityDataProvider entityDataProvider;
    private final DataStore dataStore;

    public Scheduler(EntityDataProvider entityDataProvider, DataStore dataStore) {
        this.entityDataProvider = entityDataProvider;
        this.dataStore = dataStore;
    }

    @Scheduled(cron = "${app.data.reinsert-cron}")
    public void loadDataAndStoreInDatabase() {
        log.info("Starting reloading data from [{}]...", entityDataProvider.getInfo());
        CoronavirusEntityData coronavirusData = entityDataProvider.getData();
        dataStore.reInsertData(coronavirusData);
        log.info("Finished reloading data from [{}]...", entityDataProvider.getInfo());
    }
}
