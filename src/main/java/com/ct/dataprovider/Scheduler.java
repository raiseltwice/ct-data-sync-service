package com.ct.dataprovider;

import com.ct.dataprovider.data.provider.EntityDataProvider;
import com.ct.dataprovider.db.CoronavirusEntityData;
import com.ct.dataprovider.db.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    private final EntityDataProvider entityDataProvider;
    private final DataStore dataStore;

    public Scheduler(EntityDataProvider entityDataProvider, DataStore dataStore) {
        this.entityDataProvider = entityDataProvider;
        this.dataStore = dataStore;
    }

    @Scheduled(cron = "${app.data.reinsert-cron}")
    public void loadDataAndStoreInDatabase() {
        LOGGER.info("Starting reloading data from [{}]...", entityDataProvider.getInfo());

        CoronavirusEntityData coronavirusData = entityDataProvider.getCoronavirusEntityData();
        dataStore.reInsertData(coronavirusData);
        LOGGER.info("Finished reloading data from [{}]...", entityDataProvider.getInfo());
    }
}
